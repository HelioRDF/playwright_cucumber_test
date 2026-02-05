package rag;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class Item {

    String nome;
    int id;
    double precoRop;
    double bomPrecoRop;
    double precoZenny;
    double bomPrecoZenny;

    public Item(String nome, int id, double precoRop, double bomPrecoRop, double precoZenny, double bomPrecoZenny) {
        this.nome = nome;
        this.id = id;
        this.precoRop = precoRop;
        this.bomPrecoRop = bomPrecoRop;
        this.precoZenny = precoZenny;
        this.bomPrecoZenny = bomPrecoZenny;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrecoRop() {
        return precoRop;
    }

    public void setPrecoRop(double precoRop) {
        this.precoRop = precoRop;
    }

    public double getBomPrecoRop() {
        return bomPrecoRop;
    }

    public void setBomPrecoRop(double bomPrecoRop) {
        this.bomPrecoRop = bomPrecoRop;
    }

    public double getPrecoZenny() {
        return precoZenny;
    }

    public void setPrecoZenny(double precoZenny) {
        this.precoZenny = precoZenny;
    }

    public double getBomPrecoZenny() {
        return bomPrecoZenny;
    }

    public void setBomPrecoZenny(double bomPrecoZenny) {
        this.bomPrecoZenny = bomPrecoZenny;
    }

    @Override
    public String toString() {
        String precoRopFormatado = valorFormatado(precoRop);
        String precoZennyFormatado = valorFormatado(precoZenny);

        return "Nome: " + nome + " | id: " + id + " | precoRop: " + precoRopFormatado + "c | precoZenny: " + precoZennyFormatado + "z";
    }

    public static String valorFormatado(double valor) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        DecimalFormat formatadorMilhar = new DecimalFormat("#,###", symbols);

        DecimalFormatSymbols symbolsDecimais = new DecimalFormatSymbols();
        symbolsDecimais.setGroupingSeparator('.');
        symbolsDecimais.setDecimalSeparator(',');

        String valorFormatado = formatadorMilhar.format(valor);
        return valorFormatado;
    }

    public static ArrayList<Item> listaDeItens1() {
        ArrayList<Item> itens = new ArrayList<>();

        itens.add(new Item("Moeda de Instância", 30010, 25, 15, 5000000, 1000000));
        itens.add(new Item("Bolsa de Rubis [100kk]", 40400, 1900, 1600, 1, 1));
        itens.add(new Item("Bolsa de Diamantes [1b]", 40097, 19000, 16000, 1, 1));
        itens.add(new Item("[1000] Moeda ROPS", 40108, 1, 1, 62000000, 59000000));
        itens.add(new Item("[1000] Moeda de Pontos MvP", 50086, 2800, 1500, 5000000, 1000000));

        itens.add(new Item("Hero", 29509, 190000, 100000, 1, 1));
        itens.add(new Item("Arcana", 29585, 100000, 90000, 1, 1));
        itens.add(new Item("Pedra de Encantamento Epifania", 47777, 80000, 70000, 1, 1));
        itens.add(new Item("Raiva JRO", 610198, 80000, 70000, 1, 1));
        itens.add(new Item("Essência Primordial", 50145, 335, 300, 9000000, 7000000));
        itens.add(new Item("Essência Lapine 1ª Linha ", 47083, 20000, 5000, 9000000, 7000000));

        itens.add(new Item("Refinadora Complexa", 33360, 67000, 50000, 1, 1));
        itens.add(new Item("Mini-Refinadora", 33362, 19990, 11000, 1, 1));

        itens.add(new Item("Ametista", 719, 23, 14, 1.000_000, 1));
        itens.add(new Item("Etel Dust", 1000322, 2, 1, 1.000_000, 1000));
        itens.add(new Item("Etel Stone", 1000323, 24, 17, 1.000_000, 1000));
        itens.add(new Item("Etel Amber", 1000328, 300, 175, 1.000_000, 1000));
        // itens.add(new Item("Etel Amethyst", 1000327, 130, 110, 1.000_000, 1000));
        //itens.add(new Item("Etel Topaz", 1000326, 130, 75, 1.000_000, 1000));
        //itens.add(new Item("Etel Aquamarine", 1000325, 95, 50, 1.000_000, 1000));
        // itens.add(new Item("Bolinho de Cerejeira", 23317, 95, 50, 1.000_000, 1000));

        itens.add(new Item("[Extreme] Esfera da Fenda", 33390, 450, 290, 5000000, 1000000));
        itens.add(new Item("Bênção do Ferreiro", 6635, 120, 100, 9000000, 7000000));

        itens.add(new Item("Jewelry Ant Card", 300006, 4200, 4100, 1.000_000, 1000));

        itens.add(new Item("Fones de Ouvido da Bruxa ", 410176, 79000, 50000, 1, 1));
        itens.add(new Item("Temporal Cape-LT [1] ", 480312, 10000, 8000, 1, 1));

        itens.add(new Item("Ovo de Corrupted Dark Lord", 92680, 430000, 429000, 9000000, 7000000));
        return itens;
    }

    public static ArrayList<Item> listaDeItens2() {
        ArrayList<Item> itens = new ArrayList<>();

        itens.add(new Item("Moeda de Instância", 30010, 33, 30, 5000000, 2000000));
        itens.add(new Item("Temporal Cape-LT [1] ", 480312, 10000, 8000, 1, 1));
        itens.add(new Item("Refinadora Complexa", 33360, 67000, 45000, 1, 1));
        itens.add(new Item("Bolsa de Diamantes [1b]", 40097, 12000, 11000, 1, 1));
        itens.add(new Item("Bolsa de Rubis [100kk]", 40400, 1200, 1100, 1, 1));
        itens.add(new Item("Bolinho de Cerejeira", 23317, 95, 40, 1.000_000, 1000));
        return itens;
    }

    public static ArrayList<Item> listaDeItens3() {
        ArrayList<Item> itens = new ArrayList<>();
        itens.add(new Item("Moeda de Instância", 30010, 40, 38, 5000000, 2000000));
        itens.add(new Item("Anti Tudo [Ultimate] II", 70146, 395, 394, 1, 1));
        itens.add(new Item("Ametista", 719, 23, 22, 1.000_000, 1));
        itens.add(new Item("Etel Amber", 1000328, 130, 129, 1.000_000, 1000));
        itens.add(new Item("Etel Amethyst", 1000327, 100, 99, 1.000_000, 1000));
        itens.add(new Item("Etel Topaz", 1000326, 55, 51, 1.000_000, 1000));
        itens.add(new Item("Etel Aquamarine", 1000325, 29, 27, 1.000_000, 1000));
        // itens.add(new Item("Bolsa de Diamantes [1b]", 40097, 11400, 11399, 1, 1));
        // itens.add(new Item("Bolsa de Rubis [100kk]", 40400, 1290, 1289, 1, 1));
        itens.add(new Item("Jewelry Ant Card", 300006, 6650, 6649, 1.000_000, 1000));
        itens.add(new Item("Essência Lapine 1ª Linha ", 47083, 9999, 9998, 9000000, 7000000));

        return itens;
    }

}
