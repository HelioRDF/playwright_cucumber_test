import fs from "node:fs/promises";
import path from "node:path";
import process from "node:process";
import { fileURLToPath } from "node:url";

// Script que faz múltiplas buscas com variações e remove duplicatas

const TEXT_SEARCH_URL = "https://places.googleapis.com/v1/places:searchText";
const DETAILS_BASE_URL = "https://places.googleapis.com/v1/places";

// Centro aproximado de Embu Guaçu
const EMBU_GUACU_CENTER = {
  latitude: -23.8298,
  longitude: -46.8115,
};

const TEXT_SEARCH_FIELD_MASK = [
  "places.id",
  "places.displayName",
  "places.formattedAddress",
  "places.googleMapsUri",
  "places.primaryType",
  "places.types",
  "nextPageToken",
].join(",");

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
    .map((header) => `<Cell ss:StyleID="sHeader"><Data ss:Type="String">${xmlEscape(header)}</Data></Cell>`)
    .join("");

  const columnsXml = [
    220, 360, 120, 140, 220, 220, 260, 170, 130, 260,
  ]
    .map((width) => `<Column ss:AutoFitWidth="0" ss:Width="${width}"/>`)
    .join("\n      ");

  const rowsXml = rows
    .map((row) => {
      const values = [
        row.nome, row.endereco, row.telefone_nacional, row.telefone_internacional,
        row.site, row.instagram, row.maps_url, row.place_id, row.tipo_primario, row.tipos,
      ];

      const cells = values
        .map((value) => `<Cell ss:StyleID="sWrap"><Data ss:Type="String">${xmlEscape(value)}</Data></Cell>`)
        .join("");

      return `<Row>${cells}</Row>`;
    })
    .join("\n");

  return `<?xml version="1.0"?><?mso-application progid="Excel.Sheet"?><Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet" xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet" xmlns:html="http://www.w3.org/TR/REC-html40"><Styles><Style ss:ID="sHeader"><Font ss:Bold="1"/><Interior ss:Color="#FFD966" ss:Pattern="Solid"/><Alignment ss:Vertical="Center"/></Style><Style ss:ID="sWrap"><Alignment ss:Vertical="Top" ss:WrapText="1"/></Style></Styles><Worksheet ss:Name="Multi-Busca"><Table>${columnsXml}<Row>${headerXml}</Row>${rowsXml}</Table></Worksheet></Workbook>`;
}

function sanitizeFilePart(value) {
  return value
    .normalize("NFD")
    .replace(/[\u0300-\u036f]/g, "")
    .replace(/[^a-zA-Z0-9]+/g, "-")
    .replace(/^-+|-+$/g, "")
    .toLowerCase();
}

function formatTimestampForFile(date = new Date()) {
  const year = String(date.getFullYear());
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  const hour = String(date.getHours()).padStart(2, "0");
  const minute = String(date.getMinutes()).padStart(2, "0");

  return `${year}${month}${day}_${hour}${minute}`;
}

async function searchSingleQuery(query, apiKey) {
  console.log(`\n  Buscando: "${query}"`);

  const allPlaces = [];
  let pageToken = undefined;
  let pageCount = 0;
  const MAX_PAGES = 3; // Máx 3 páginas = até 60 resultados

  do {
    pageCount += 1;
    const data = await textSearch({ pageToken, query, apiKey });

    if (Array.isArray(data.places)) {
      allPlaces.push(...data.places);
      console.log(`    Página ${pageCount}: ${data.places.length} resultados`);
    }

    pageToken = data.nextPageToken;

    if (pageToken) {
      await sleep(500);
    }
  } while (pageToken && pageCount < MAX_PAGES);

  console.log(`    Total bruto: ${allPlaces.length} | IDs únicos: ${new Set(allPlaces.map(p => p.id)).size}`);
  return allPlaces;
}

async function main() {
  await loadEnvFromNearestFile();

  const apiKey = process.env.GOOGLE_MAPS_API_KEY?.trim();
  const cidade = process.env.CIDADE?.trim() || "Embu Guaçu, SP";
  const categoria = process.env.CATEGORIA?.trim() || "pizzaria";

  if (!apiKey) {
    console.error("Erro: defina GOOGLE_MAPS_API_KEY no .env");
    process.exit(1);
  }

  console.log(`\n=====================================`);
  console.log(`MÚLTIPLAS BUSCAS CONSOLIDADAS`);
  console.log(`=====================================`);
  console.log(`Cidade: ${cidade}`);
  console.log(`Categoria: ${categoria}\n`);

  // Variações de busca para evitar limite de 60 resultados
  // Cada uma pode retornar até 60, mas muitas terão duplicatas
  const queries = [
    `${categoria} em ${cidade}`,
    `${categoria} ${cidade}`,
    `restaurante ${categoria} ${cidade}`,
    `${categoria} perto de ${cidade}`,
  ];

  // Armazena resultados de cada busca
  const resultsByQuery = {};
  // Armazena IDs globais para evitar duplicatas
  const allIds = new Set();
  // Armazena todos os places únicos
  const allUniquePlaces = [];

  console.log(`Iniciando ${queries.length} variações de busca...\n`);

  for (let i = 0; i < queries.length; i++) {
    const query = queries[i];
    try {
      const places = await searchSingleQuery(query, apiKey);
      resultsByQuery[query] = places;

      // Conta quantos são novos (não repetidos)
      let newCount = 0;
      for (const place of places) {
        if (!allIds.has(place.id)) {
          allIds.add(place.id);
          allUniquePlaces.push(place);
          newCount++;
        }
      }

      console.log(`    ✓ ${newCount} novo(s) resultado(s) agregado(s) à lista global`);
    } catch (err) {
      console.error(`    ✗ Erro: ${err.message}`);
    }

    // Delay entre buscas
    if (i < queries.length - 1) {
      await sleep(1000);
    }
  }

  // Gera relatório
  console.log(`\n=====================================`);
  console.log(`RESULTADO CONSOLIDADO`);
  console.log(`=====================================`);

  for (const [query, places] of Object.entries(resultsByQuery)) {
    const uniqueInThisQuery = new Set(places.map(p => p.id)).size;
    console.log(`"${query}": ${uniqueInThisQuery} IDs únicos`);
  }

  console.log(`\n-------------------------------------`);
  console.log(`TOTAL FINAL: ${allIds.size} IDs únicos encontrados`);
  console.log(`Redução de duplicatas: ${
    Object.values(resultsByQuery).reduce((sum, arr) => sum + arr.length, 0) - allIds.size
  } estabelecimentos removidos como duplicata`);
  console.log(`=====================================`);

  // Salva resultado em JSON para referência
  const report = {
    cidade,
    categoria,
    timestamp: new Date().toISOString(),
    queries: Object.entries(resultsByQuery).map(([query, places]) => ({
      query,
      resultCount: places.length,
      uniqueIds: new Set(places.map(p => p.id)).size,
    })),
    consolidatedStats: {
      totalResultsAcrossAllQueries: Object.values(resultsByQuery).reduce((sum, arr) => sum + arr.length, 0),
      totalUniqueIds: allIds.size,
      duplicatesRemoved: Object.values(resultsByQuery).reduce((sum, arr) => sum + arr.length, 0) - allIds.size,
    },
  };

  const reportFile = path.resolve(`multiBusca_report_${new Date().toISOString().split('T')[0]}.json`);
  await fs.writeFile(reportFile, JSON.stringify(report, null, 2), "utf8");

  console.log(`\nRelatório JSON salvo em: ${reportFile}`);

  // Buscar detalhes completos de cada lugar único
  console.log(`\nBuscando detalhes de ${allIds.size} estabelecimentos únicos...\n`);
  const excelRows = [];
  const errors = [];

  for (let i = 0; i < allUniquePlaces.length; i++) {
    const place = allUniquePlaces[i];
    const progress = `[${i + 1}/${allUniquePlaces.length}]`;

    try {
      const details = await placeDetails(place.id, apiKey);

      excelRows.push({
        nome: details.displayName?.text ?? "",
        endereco: details.formattedAddress ?? "",
        telefone_nacional: details.nationalPhoneNumber ?? "",
        telefone_internacional: details.internationalPhoneNumber ?? "",
        site: details.websiteUri ?? "",
        instagram: "",
        maps_url: details.googleMapsUri ?? "",
        place_id: details.id ?? place.id,
        tipo_primario: details.primaryType ?? "",
        tipos: Array.isArray(details.types) ? details.types.join(" | ") : "",
      });

      console.log(`${progress} ✓ ${details.displayName?.text ?? "Sem nome"} - Tel: ${(details.nationalPhoneNumber || details.internationalPhoneNumber || "N/A").substring(0, 20)}`);
      await sleep(150);
    } catch (err) {
      errors.push({ placeId: place.id, error: err.message });
      console.error(`${progress} ✗ Erro em ${place.id}: ${err.message}`);
    }
  }

  // Exibe resultados em tabela
  console.log(`\n${'='.repeat(37)}`);
  console.log(`DETALHES DOS ESTABELECIMENTOS`);
  console.log(`${'='.repeat(37)}\n`);
  console.table(excelRows.map(row => ({
    Nome: row.nome,
    Telefone: row.telefone_nacional || row.telefone_internacional || "N/A",
    Site: row.site ? "Sim" : "Não",
    Instagram: row.instagram ? "Sim" : "Não",
  })));

  // Exportar para Excel XML
  console.log(`\nPreparando exportação para Excel...`);
  const excelXml = toExcelXml(excelRows);
  const cityFilePart = sanitizeFilePart(cidade);
  const timestamp = formatTimestampForFile();
  const excelFile = path.resolve(`multiBusca_${cityFilePart}_${timestamp}.xml`);
  await fs.writeFile(excelFile, excelXml, "utf8");

  console.log(`XML para Excel salvo em: ${excelFile}`);

  // Salva erros se houver
  if (errors.length > 0) {
    const errorsFile = path.resolve(`multiBusca_erros_${timestamp}.json`);
    await fs.writeFile(errorsFile, JSON.stringify(errors, null, 2), "utf8");
    console.log(`\n⚠️  ${errors.length} erro(s) encontrado(s). Veja: ${errorsFile}`);
  }

  console.log(`\n✅ Concluído: ${excelRows.length} estabelecimentos com detalhes completos exportados para Excel.`);
}

main().catch((err) => {
  console.error("Erro:", err.message);
  process.exit(1);
});
