package examples;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class VerificaInternet {

    public static void main(String[] args) {
    }

    public static Boolean acessaInternet() {
        String hostParaTestar = "www.google.com"; // Um host conhecido na internet
        int timeout = 5000; // Tempo limite em milissegundos (3 segundos)
        boolean internet=false;

        try {
            InetAddress endereco = InetAddress.getByName(hostParaTestar);
            if (endereco.isReachable(timeout)) {
                System.out.println("Acesso a internet OK!");
                internet = true;
            } else {
                System.out.println("Sem acesso a internet");
                internet = false;
            }
        } catch (UnknownHostException e) {
            System.err.println("Host desconhecido: " + hostParaTestar + ". Verifique o nome ou a conexão DNS.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Erro de E/S ao tentar verificar o acesso: " + e.getMessage());
            e.printStackTrace();
        }

        
        return internet;
    }
}