package rag;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ManipularArquivo {

    static String nomeDoArquivo = "History/History_Link.txt";
    static List<String> linhasDoArquivo = new ArrayList<>();

    public static void main(String[] args) {

        /*
         * linhasDoArquivo=DadosDoArquivo();
         * linhasDoArquivo.add("XXX");
         * linhasDoArquivo.add("XXX");
         * System.out.println(linhasDoArquivo);
         * salvarLinks(linhasDoArquivo);
         * DadosDoArquivo();
         */ }

    @SuppressWarnings("null")
    public static List<String> DadosDoArquivo() {
        List<String> linhasDoArquivo = new ArrayList<>();
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

    public static void salvarLinks(List<String> linhasDoArquivo) {
        // Agora, as linhas do arquivo estão na variável 'linhasDoArquivo'
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(nomeDoArquivo));
            if (linhasDoArquivo != null) {
                // System.out.println("\nConteúdo do arquivo (linha por linha):");
                for (String linha : linhasDoArquivo) {
                    if (!linha.isEmpty()) {
                        writer.write("\n" + linha);
                        // System.out.println(linha);
                    }
                }
            }
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
