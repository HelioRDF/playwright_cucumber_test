package nexoos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExtratoDeTexto {

    static String nomeDoArquivo = "Extratos/ExtratoNexos" + dataHoje() + ".txt";
    static List<String> linhasDoArquivo = new ArrayList<>();

    public static void main(String[] args) {

    }

    @SuppressWarnings("null")
    public static List<String> DadosDoArquivo() {
        try {
            Path caminhoDoArquivo = Paths.get(nomeDoArquivo);
            for (String allLines : Files.readAllLines(caminhoDoArquivo)) {
                linhasDoArquivo.add(allLines);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("\nResultado:" + linhasDoArquivo);
        return linhasDoArquivo;
    }

    public static String dataHoje() {

        LocalDateTime dataHoraAtual = LocalDateTime.now();
        // Crie um formatador com o padrão desejado
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("ddMMyyyy");
        // Formate a data e hora usando o formatador
        String dataHoraFormatada = dataHoraAtual.format(formatador);
        return dataHoraFormatada;
    }

    public static void salvarItem(String item, String valorAtual) {
        // Agora, as linhas do arquivo estão na variável 'linhasDoArquivo'
        BufferedWriter writer;

        try {
            writer = new BufferedWriter(new FileWriter(nomeDoArquivo));
            if (item != null) {
                linhasDoArquivo.add(item + " | Atual: " + valorAtual + " | " + dataHoje());
                for (String linha : linhasDoArquivo) {
                    if (!linha.isEmpty()) {
                        writer.write("\n" + linha);
                        // System.out.println(linha);
                    }
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void separdorDeLinhas() {
        BufferedWriter writer;
        // linhasDoArquivo2.clear();
        try {
            writer = new BufferedWriter(new FileWriter(nomeDoArquivo));
            linhasDoArquivo.add("-------------------------------------------------------------------------");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void salvarItemResumo(String item, String valorAtual) {
        // Agora, as linhas do arquivo estão na variável 'linhasDoArquivo'
        BufferedWriter writer;

        try {
            writer = new BufferedWriter(new FileWriter(nomeDoArquivo));
            if (item != null) {
                linhasDoArquivo.add(item + " | " + valorAtual);
                for (String linha : linhasDoArquivo) {
                    if (!linha.isEmpty()) {
                        writer.write("\n" + linha);
                        // System.out.println(linha);
                    }
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
