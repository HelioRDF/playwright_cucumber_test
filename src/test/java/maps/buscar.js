import fs from "node:fs/promises";
import path from "node:path";
import process from "node:process";
import { fileURLToPath } from "node:url";

// Node 18+ já tem fetch nativo.
// Agora o script tenta carregar .env automaticamente, então basta: node buscar.js

const TEXT_SEARCH_URL = "https://places.googleapis.com/v1/places:searchText";
const DETAILS_BASE_URL = "https://places.googleapis.com/v1/places";

// Bounding box aproximado do município de São Paulo.
// Use apenas se quiser forçar os resultados para dentro dessa área.
const SP_RECTANGLE = {
  low: { latitude: -23.82, longitude: -46.83 },
  high: { latitude: -23.35, longitude: -46.36 },
};

// Centro aproximado de Embu Guaçu para priorizar resultados locais
// sem bloquear estabelecimentos relevantes no entorno.
const EMBU_GUACU_CENTER = {
  latitude: -23.8298,
  longitude: -46.8115,
};

// Campos retornados pelo Text Search.
// Dá para pedir telefone/site já aqui, mas eu também reforço no Details.
const TEXT_SEARCH_FIELD_MASK = [
  "places.id",
  "places.displayName",
  "places.formattedAddress",
  "places.googleMapsUri",
  "nextPageToken",
].join(",");

// Campos do Place Details.
const DETAILS_FIELDS = [
  "id",
  "displayName",
  "formattedAddress",
  "nationalPhoneNumber",
  "internationalPhoneNumber",
  "websiteUri",
  "googleMapsUri",
  "primaryType",
  "types",
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
      rectangle: SP_RECTANGLE,
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

async function placeDetails(placeId, apiKey) {
  const url = new URL(`${DETAILS_BASE_URL}/${placeId}`);
  url.searchParams.set("fields", DETAILS_FIELDS);
  url.searchParams.set("key", apiKey);
  url.searchParams.set("languageCode", "pt-BR");
  url.searchParams.set("regionCode", "BR");

  const res = await fetch(url);

  if (!res.ok) {
    const text = await res.text();
    throw new Error(`Place Details falhou para ${placeId}: ${res.status} - ${text}`);
  }

  return res.json();
}

function csvEscape(value) {
  if (value === null || value === undefined) return "";
  const str = String(value).replace(/"/g, '""');
  return `"${str}"`;
}

function toCsv(rows) {
  const headers = [
    "nome",
    "endereco",
    "telefone_nacional",
    "telefone_internacional",
    "site",
    "maps_url",
    "place_id",
    "tipo_primario",
    "tipos",
  ];

  const lines = [headers.join(",")];

  for (const row of rows) {
    lines.push([
      csvEscape(row.nome),
      csvEscape(row.endereco),
      csvEscape(row.telefone_nacional),
      csvEscape(row.telefone_internacional),
      csvEscape(row.site),
      csvEscape(row.maps_url),
      csvEscape(row.place_id),
      csvEscape(row.tipo_primario),
      csvEscape(row.tipos),
    ].join(","));
  }

  return lines.join("\n");
}

function xmlEscape(value) {
  if (value === null || value === undefined) return "";
  return String(value)
    .replace(/&/g, "&amp;")
    .replace(/</g, "&lt;")
    .replace(/>/g, "&gt;")
    .replace(/\"/g, "&quot;")
    .replace(/'/g, "&apos;");
}

function toExcelXml(rows) {
  const headers = [
    "nome",
    "endereco",
    "telefone_nacional",
    "telefone_internacional",
    "site",
    "instagram",
    "maps_url",
    "place_id",
    "tipo_primario",
    "tipos",
  ];

  const headerXml = headers
    .map((header) => `<Cell ss:StyleID=\"sHeader\"><Data ss:Type=\"String\">${xmlEscape(header)}</Data></Cell>`)
    .join("");

  const columnsXml = [
    220, // nome
    360, // endereco
    120, // telefone_nacional
    140, // telefone_internacional
    220, // site
    220, // instagram
    260, // maps_url
    170, // place_id
    130, // tipo_primario
    260, // tipos
  ]
    .map((width) => `<Column ss:AutoFitWidth=\"0\" ss:Width=\"${width}\"/>`)
    .join("\n      ");

  const rowsXml = rows
    .map((row) => {
      const values = [
        row.nome,
        row.endereco,
        row.telefone_nacional,
        row.telefone_internacional,
        row.site,
        row.instagram,
        row.maps_url,
        row.place_id,
        row.tipo_primario,
        row.tipos,
      ];

      const cells = values
        .map((value) => `<Cell ss:StyleID=\"sWrap\"><Data ss:Type=\"String\">${xmlEscape(value)}</Data></Cell>`)
        .join("");

      return `<Row>${cells}</Row>`;
    })
    .join("\n");

  return `<?xml version="1.0"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
  xmlns:o="urn:schemas-microsoft-com:office:office"
  xmlns:x="urn:schemas-microsoft-com:office:excel"
  xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
  xmlns:html="http://www.w3.org/TR/REC-html40">
  <Styles>
    <Style ss:ID="sHeader">
      <Font ss:Bold="1"/>
      <Interior ss:Color="#D9E1F2" ss:Pattern="Solid"/>
      <Alignment ss:Vertical="Center"/>
    </Style>
    <Style ss:ID="sWrap">
      <Alignment ss:Vertical="Top" ss:WrapText="1"/>
    </Style>
  </Styles>
  <Worksheet ss:Name="Resultados">
    <Table>
      ${columnsXml}
      <Row>${headerXml}</Row>
      ${rowsXml}
    </Table>
  </Worksheet>
</Workbook>`;
}

function formatTimestampForFile(date = new Date()) {
  const year = String(date.getFullYear());
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  const hour = String(date.getHours()).padStart(2, "0");
  const minute = String(date.getMinutes()).padStart(2, "0");

  return `${year}${month}${day}_${hour}${minute}`;
}

function sanitizeFilePart(value) {
  return value
    .normalize("NFD")
    .replace(/[\u0300-\u036f]/g, "")
    .replace(/[^a-zA-Z0-9]+/g, "-")
    .replace(/^-+|-+$/g, "")
    .toLowerCase();
}

async function main() {
  const envFile = await loadEnvFromNearestFile();

  const apiKey = process.env.GOOGLE_MAPS_API_KEY?.trim();
  const cidade = process.env.CIDADE?.trim() || "Embu Guaçu, SP";
  const categoria = process.env.CATEGORIA?.trim() || "pizzaria";
  const query = process.env.SEARCH_QUERY?.trim() || `${categoria} em ${cidade}`;

  if (!apiKey) {
    console.error("Erro: defina GOOGLE_MAPS_API_KEY no .env");
    console.error("Dica: crie .env na raiz do projeto com GOOGLE_MAPS_API_KEY=sua_chave");
    process.exit(1);
  }

  console.log(`Buscando: ${query}`);
  console.log(`Cidade: ${cidade}`);
  if (envFile) {
    console.log(`Variáveis carregadas de: ${envFile}`);
  }

  const basicPlaces = [];
  let pageToken = undefined;
  let pageCount = 0;

  // Ajuste este limite conforme seu orçamento e necessidade.
  const MAX_PAGES = 5;

  do {
    pageCount += 1;
    console.log(`Página ${pageCount}...`);

    const data = await textSearch({ pageToken, query, apiKey });

    if (Array.isArray(data.places)) {
      basicPlaces.push(...data.places);
    }

    pageToken = data.nextPageToken;

    // Em algumas APIs do Google, o token pode precisar de um pequeno delay.
    if (pageToken) {
      await sleep(2000);
    }
  } while (pageToken && pageCount < MAX_PAGES);

  console.log(`Resultados brutos: ${basicPlaces.length}`);

  // Remove duplicados por ID
  const uniqueIds = [...new Set(basicPlaces.map((p) => p.id).filter(Boolean))];
  console.log(`IDs únicos: ${uniqueIds.length}`);

  const finalRows = [];
  const errors = [];

  // Controle simples de taxa para não estourar requisições.
  for (let i = 0; i < uniqueIds.length; i++) {
    const placeId = uniqueIds[i];
    console.log(`Detalhes ${i + 1}/${uniqueIds.length}: ${placeId}`);

    try {
      const details = await placeDetails(placeId, apiKey);

      finalRows.push({
        nome: details.displayName?.text ?? "",
        endereco: details.formattedAddress ?? "",
        telefone_nacional: details.nationalPhoneNumber ?? "",
        telefone_internacional: details.internationalPhoneNumber ?? "",
        site: details.websiteUri ?? "",
        instagram: "",
        maps_url: details.googleMapsUri ?? "",
        place_id: details.id ?? placeId,
        tipo_primario: details.primaryType ?? "",
        tipos: Array.isArray(details.types) ? details.types.join(" | ") : "",
      });

      await sleep(150);
    } catch (err) {
      errors.push({ placeId, error: err.message });
      console.error(`Erro em ${placeId}: ${err.message}`);
    }
  }

  if (finalRows.length > 0) {
    console.log("\nTabela de resultados:");
    console.table(finalRows);
  } else {
    console.log("\nNenhum resultado válido para exibir.");
  }

  const excelXml = toExcelXml(finalRows);
  const cityFilePart = sanitizeFilePart(cidade);
  const categoryFilePart = sanitizeFilePart(categoria);
  const timestamp = formatTimestampForFile();
  const outFile = path.resolve(`${categoryFilePart}_${cityFilePart}_${timestamp}.xml`);
  await fs.writeFile(outFile, excelXml, "utf8");

  if (errors.length > 0) {
    await fs.writeFile(
      path.resolve("erros.json"),
      JSON.stringify(errors, null, 2),
      "utf8"
    );
  }

  console.log(`\nConcluído.`);
  console.log(`XML salvo em: ${outFile}`);
  console.log(`=====================================`);
  console.log(`RESUMO FINAL:`);
  console.log(`  - Total de resultados brutos: ${basicPlaces.length}`);
  console.log(`  - IDs únicos encontrados: ${uniqueIds.length}`);
  console.log(`  - Registros válidos obtidos: ${finalRows.length}`);
  console.log(`  - Erros na busca de detalhes: ${errors.length}`);
  console.log(`=====================================`);
}

main().catch((err) => {
  console.error("Falha geral:", err);
  process.exit(1);
});