import fs from "node:fs/promises";
import path from "node:path";
import process from "node:process";
import { fileURLToPath } from "node:url";

// Script que apenas conta quantos resultados foram encontrados
// sem fazer o download completo dos detalhes.

const TEXT_SEARCH_URL = "https://places.googleapis.com/v1/places:searchText";

// Centro aproximado de Embu Guaçu para priorizar resultados locais
const EMBU_GUACU_CENTER = {
  latitude: -23.8298,
  longitude: -46.8115,
};

const TEXT_SEARCH_FIELD_MASK = [
  "places.id",
  "places.displayName",
  "places.formattedAddress",
  "nextPageToken",
].join(",");

async function sleep(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

function collectParentDirs(startDir) {
  const dirs = [];
  let current = path.resolve(startDir);

  while (true) {
    dirs.push(current);
    const parent = path.dirname(current);
    if (parent === current) {
      break;
    }
    current = parent;
  }

  return dirs;
}

function parseEnvLine(line) {
  const trimmed = line.trim();
  if (!trimmed || trimmed.startsWith("#")) {
    return null;
  }

  const separatorIndex = trimmed.indexOf("=");
  if (separatorIndex <= 0) {
    return null;
  }

  const key = trimmed.slice(0, separatorIndex).trim();
  let value = trimmed.slice(separatorIndex + 1).trim();

  if (
    (value.startsWith('"') && value.endsWith('"')) ||
    (value.startsWith("'") && value.endsWith("'"))
  ) {
    value = value.slice(1, -1);
  }

  return { key, value };
}

async function loadEnvFromNearestFile() {
  const scriptDir = path.dirname(fileURLToPath(import.meta.url));
  const candidates = [...new Set([
    ...collectParentDirs(process.cwd()).map((dir) => path.join(dir, ".env")),
    ...collectParentDirs(scriptDir).map((dir) => path.join(dir, ".env")),
  ])];

  for (const envFile of candidates) {
    try {
      const content = await fs.readFile(envFile, "utf8");
      const lines = content.split(/\r?\n/);

      for (const line of lines) {
        const parsed = parseEnvLine(line);
        if (!parsed) {
          continue;
        }

        if (!process.env[parsed.key]) {
          process.env[parsed.key] = parsed.value;
        }
      }

      return envFile;
    } catch {
      // Arquivo não existe ou não pode ser lido; tenta próximo candidato.
    }
  }

  return null;
}

async function textSearch({ pageToken, query, apiKey }) {
  const body = {
    textQuery: query,
    pageSize: 20,
    languageCode: "pt-BR",
    regionCode: "BR",
    rankPreference: "RELEVANCE",
    locationBias: {
      circle: {
        center: EMBU_GUACU_CENTER,
        radius: 12000,
      },
    },
  };

  if (process.env.USE_LOCATION_RESTRICTION === "true") {
    body.locationRestriction = {
      rectangle: {
        low: { latitude: -23.82, longitude: -46.83 },
        high: { latitude: -23.35, longitude: -46.36 },
      },
    };
    delete body.locationBias;
  }

  if (pageToken) {
    body.pageToken = pageToken;
  }

  const res = await fetch(TEXT_SEARCH_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "X-Goog-Api-Key": apiKey,
      "X-Goog-FieldMask": TEXT_SEARCH_FIELD_MASK,
    },
    body: JSON.stringify(body),
  });

  if (!res.ok) {
    const text = await res.text();
    throw new Error(`Text Search falhou: ${res.status} - ${text}`);
  }

  return res.json();
}

async function main() {
  const envFile = await loadEnvFromNearestFile();

  if (envFile) {
    console.log(`✓ Variáveis carregadas de: ${envFile}`);
  } else {
    console.log(`⚠ Nenhum arquivo .env encontrado; usando variáveis de ambiente do sistema.`);
  }

  const apiKey = process.env.GOOGLE_MAPS_API_KEY?.trim();
  const cidade = process.env.CIDADE?.trim() || "Embu Guaçu, SP";
  const categoria = process.env.CATEGORIA?.trim() || "pizzaria";
  const query = process.env.SEARCH_QUERY?.trim() || `${categoria} em ${cidade}`;

  console.log(`\nVariáveis detectadas:`);
  console.log(`  CIDADE: ${cidade}`);
  console.log(`  CATEGORIA: ${categoria}`);
  console.log(`  SEARCH_QUERY: ${query}`);
  console.log(`  API_KEY presente: ${apiKey ? "Sim" : "Não "}\n`);

  if (!apiKey) {
    console.error("Erro: defina GOOGLE_MAPS_API_KEY no .env");
    process.exit(1);
  }

  console.log(`Contando resultados para: ${query}`);
  console.log(`\nBuscando...`);

  const allPlaces = [];
  let pageToken = undefined;
  let pageCount = 0;

  // Não há limite neste script; vai contar tudo que a API retornar.
  // Você pode ajustar se quiser um máximo.
  const MAX_PAGES = process.env.MAX_PAGES ? parseInt(process.env.MAX_PAGES) : 100;

  do {
    pageCount += 1;
    console.log(`  Página ${pageCount}...`);

    const data = await textSearch({ pageToken, query, apiKey });

    if (Array.isArray(data.places)) {
      const pageSize = data.places.length;
      allPlaces.push(...data.places);
      console.log(`    ✓ ${pageSize} resultado(s) nesta página | Total acumulado: ${allPlaces.length}`);
    } else {
      console.log(`    ⚠ Nenhum resultado retornado nesta página`);
    }

    pageToken = data.nextPageToken;

    if (pageToken) {
      console.log(`    NextPageToken detectado. Aguardando 1s...`);
      await sleep(1000);
    } else {
      console.log(`    Nenhum nextPageToken. Busca concluída.`);
    }
  } while (pageToken && pageCount < MAX_PAGES);

  // Remove duplicados por ID
  const uniqueIds = [...new Set(allPlaces.map((p) => p.id).filter(Boolean))];

  console.log(`\n=====================================`);
  console.log(`CONTAGEM DE RESULTADOS:`);
  console.log(`  - Páginas percorridas: ${pageCount}`);
  console.log(`  - Total de resultados brutos: ${allPlaces.length}`);
  console.log(`  - IDs únicos encontrados: ${uniqueIds.length}`);
  console.log(`=====================================`);
  console.log(`\nPróximas ações:`);
  console.log(`  - Execute "node buscar.js" para baixar os detalhes.`);
  console.log(`  - Todos os ${uniqueIds.length} resultados serão incluídos.`);
}

main().catch((err) => {
  console.error("Erro:", err.message);
  process.exit(1);
});
