package examples;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class BuscarData {

    public static void main(String[] args) {
        dataDeOntemFormatada();

    }

    public static String dataAtualFormatada() {
        // Pegar a data e hora atual
        Date hoje = new Date();
        // Formatar a data
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String hojeFormatado = formatter.format(hoje);
        return hojeFormatado;

    }

    public static String dataDeOntemFormatada() {
        // Pegar a data e hora atual
        Date hoje = new Date();
        // Formatar a data
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        
        // Criar um objeto Calendar e definir a data para a data atual
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(hoje); // Define a data do calendário para a data atual

        // Diminuir um dia do calendário
        calendario.add(Calendar.DAY_OF_MONTH, -1);

        // Obter a nova data (que foi modificada)
        Date dataOntem = calendario.getTime();

        //System.out.println("Data menos um dia: " + formatter.format(dataOntem));
        return formatter.format(dataOntem).toString();

    }

    
    public static String dataDe2DiasFormatada() {
        // Pegar a data e hora atual
        Date hoje = new Date();
        // Formatar a data
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        
        // Criar um objeto Calendar e definir a data para a data atual
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(hoje); // Define a data do calendário para a data atual

        // Diminuir um dia do calendário
        calendario.add(Calendar.DAY_OF_MONTH, -2);

        // Obter a nova data (que foi modificada)
        Date dataOntem = calendario.getTime();

       // System.out.println("Data menos um dia: " + formatter.format(dataOntem));
        return formatter.format(dataOntem).toString();

    }


}