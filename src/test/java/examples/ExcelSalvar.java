package examples;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class ExcelSalvar {
    private static final String fileName = "C:\\travian\\";
    public static void main(String[] args) throws IOException {
		List<String> listaCampos = new ArrayList<String>();
		listaCampos.add("A");
		listaCampos.add("B");
		ExcelSalvar.salvarDadosExcel(listaCampos);
    }

    public static void salvarDadosExcel(List<String> listaCampos){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheetAldeia = workbook.createSheet("Aldeia X");


        int rownum = 0;
        Row row = sheetAldeia.createRow(rownum++);
        Cell textoId = row.createCell(0);
        textoId.setCellValue("id");

        Cell textoNvl = row.createCell(1);
        textoNvl.setCellValue("nivel");

        Cell textoLink = row.createCell(2);
        textoLink.setCellValue("link");

    }
/*
    public static void salvarDadosCampos() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheetAldeia = workbook.createSheet("Aldeia X");

        int rownum = 0;
        Row row = sheetAldeia.createRow(rownum++);
        Cell textoId = row.createCell(0);
        textoId.setCellValue("id");

        Cell textoNvl = row.createCell(1);
        textoNvl.setCellValue("nivel");

        Cell textoLink = row.createCell(2);
        textoLink.setCellValue("link");

        for (Campo obj : listaCampos) {
            row = sheetAldeia.createRow(rownum++);
            int cellnum = 0;
            Cell cellId = row.createCell(cellnum++);
            cellId.setCellValue(obj.getId());
            Cell cellNvl = row.createCell(cellnum++);
            cellNvl.setCellValue(obj.getNivel());
            Cell cellLink = row.createCell(cellnum++);
            cellLink.setCellValue(obj.getLink());
        }

        try {
            LocalTime horaAtual = LocalTime.now();
            FileOutputStream out = new FileOutputStream(
                    new File(ExcelSalvar.fileName + "-" + horaAtual.getHour() + "-" + horaAtual.getMinute() + ".xlsx"));
            workbook.write(out);
            out.close();
            workbook.close();
            System.out.println("Arquivo Excel criado com sucesso!\n");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Arquivo não encontrado!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro na edição do arquivo!");
        }
    }


    public static void salvarDadosFarmes(List<AldeiaFarme>  aldeiaFarme, String nomeArquivo) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheetFarmes = workbook.createSheet("Farmes");



        int rownum = 0;
        Row row = sheetFarmes.createRow(rownum++);
        Cell textoNomeAldeia = row.createCell(0);
        textoNomeAldeia.setCellValue("NomeAldeia");

        Cell textoJogador = row.createCell(1);
        textoJogador.setCellValue("Jogador");

        Cell textoLink = row.createCell(2);
        textoLink.setCellValue("Link");

        Cell textoHabitantes= row.createCell(3);
        textoHabitantes.setCellValue("Habitantes");

        Cell textoDistancia = row.createCell(4);
        textoDistancia.setCellValue("Distancia");

        for (AldeiaFarme obj : aldeiaFarme) {
            row = sheetFarmes.createRow(rownum++);
            int cellnum = 0;
            Cell cellAldeia = row.createCell(cellnum++);
            cellAldeia.setCellValue(obj.getNomeAldeia());

            Cell cellJogador= row.createCell(cellnum++);
            cellJogador.setCellValue(obj.getJogador());

            Cell cellLink = row.createCell(cellnum++);
            cellLink.setCellValue(obj.getLink());

            Cell cellHabitantes = row.createCell(cellnum++);
            cellHabitantes.setCellValue(obj.getHabitantes());

            Cell cellDistancia = row.createCell(cellnum++);
            cellDistancia.setCellValue(obj.getDistancia());
        }

        try {
            FileOutputStream out = new FileOutputStream(
                    new File(ExcelSalvar.fileName +nomeArquivo +".xlsx"));
            workbook.write(out);
            out.close();
            workbook.close();
            System.out.println("Arquivo Excel criado com sucesso!\n");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Arquivo não encontrado!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro na edição do arquivo!");
        }
    }
*/
}