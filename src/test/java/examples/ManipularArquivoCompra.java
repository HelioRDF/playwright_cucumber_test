package examples;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import rag.Item;

public class ManipularArquivoCompra {
    static String nomeDoArquivo = "History_Oportunidades.txt";
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

    public static void salvarItem(Item item, String valorAtual) {
        DadosDoArquivo();
        // Agora, as linhas do arquivo estão na variável 'linhasDoArquivo'
        BufferedWriter writer;
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        // Crie um formatador com o padrão desejado
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        // Formate a data e hora usando o formatador
        String dataHoraFormatada = dataHoraAtual.format(formatador);

        try {
            writer = new BufferedWriter(new FileWriter(nomeDoArquivo));
            if (item != null) {
                linhasDoArquivo.add(item + " | Atual: " + valorAtual + " | " + dataHoraFormatada);
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