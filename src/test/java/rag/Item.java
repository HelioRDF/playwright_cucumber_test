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
    boolean ativo;

    public Item(String nome, int id, double precoRop, double bomPrecoRop, double precoZenny, double bomPrecoZenny, boolean ativo) {
        this.nome = nome;
        this.id = id;
        this.precoRop = precoRop;
        this.bomPrecoRop = bomPrecoRop;
        this.precoZenny = precoZenny;
        this.bomPrecoZenny = bomPrecoZenny;
        this.ativo = ativo;
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

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
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

    public static ArrayList<Item> listaDeItens() {
        ArrayList<Item> itens = new ArrayList<>();

        //Consumivéis
        itens.add(new Item("Moeda de Instância", 30010, 33, 20, 5000000, 2000000, true));
        itens.add(new Item("Bolsa de Rubis [100kk]", 40400, 1200, 800, 1, 1, true));
        itens.add(new Item("Bolsa de Diamantes [1b]", 40097, 12000, 8000, 1, 1, true));
        itens.add(new Item("Essência Primordial", 50145, 335, 200, 9000000, 7000000, true));
        itens.add(new Item("[Extreme] Esfera da Fenda", 33390, 450, 290, 5000000, 1000000, true));
        itens.add(new Item("Essência Lapine 1ª Linha ", 47083, 9999, 9998, 9000000, 7000000, false));
        itens.add(new Item("Anti Tudo [Ultimate] II", 70146, 395, 394, 1, 1, false));
        itens.add(new Item("[1000] Moeda de Pontos MvP", 50086, 2800, 1500, 5000000, 1000000, false));
        itens.add(new Item("[1000] Moeda ROPS", 40108, 1, 1, 62000000, 59000000, false));
        itens.add(new Item("Bênção do Ferreiro", 6635, 120, 60, 9000000, 7000000, false));
        itens.add(new Item("Bolinho de Cerejeira", 23317, 95, 40, 1.000_000, 1000, false));

        //Equipamentos
        itens.add(new Item("Temporal Cape-LT [1] ", 480312, 10000, 8000, 1, 1, true));
        itens.add(new Item("Refinadora Complexa", 33360, 67000, 30000, 1, 1, true));
        itens.add(new Item("Mini-Refinadora", 33362, 19990, 5000, 1, 1, true));
        itens.add(new Item("Crown of Beelzebub", 400110, 19000, 6000, 1, 1, true));
        itens.add(new Item("Curativo YSF01 [1]", 19446, 11000, 5000, 1, 1, true));
        itens.add(new Item("Ancient Morocc Noble Jewelry", 410254, 329000, 150000, 1, 1, true));
        itens.add(new Item("Ghost Fire", 420199, 270000, 150000, 1, 1, true));
        itens.add(new Item("Ameretat [1]", 490290, 140000, 100000, 1, 1, true));
        itens.add(new Item("Fones de Ouvido da Bruxa ", 410176, 79000, 50000, 1, 1, false));
        //Cartas
        itens.add(new Item("Carta Flor do Luar", 4131, 15000, 10000, 1, 1, true));
        itens.add(new Item("Carta Belzebu", 4145, 40000, 10000, 1, 1, true));
        itens.add(new Item("Contaminated Wanderer Card", 27361, 40000, 15000, 1, 1, false));
        itens.add(new Item("Jewelry Ant Card", 300006, 4200, 4100, 1.000_000, 1000, false));

        //Ovo
        itens.add(new Item("Ovo de Corrupted Dark Lord", 92680, 430000, 100000, 9000000, 7000000, true));
        itens.add(new Item("White Knight Egg", 9134, 80000, 20000, 9000000, 7000000, true));

        //Encantamentos
        itens.add(new Item("Cristal Shadow Colar Grade A", 44498, 294000, 100000, 1.000_000, 1, true));
        itens.add(new Item("Cristal Shadow Armadura Grade A", 44502, 52000, 30000, 1.000_000, 1, true));
        itens.add(new Item("Cristal Shadow Arma Grade A", 44587, 890000, 200000, 1.000_000, 1, true));
        itens.add(new Item("Cristal Shadow Escudo Grade A", 44588, 15000, 10000, 1.000_000, 1, true));
        itens.add(new Item("Cristal Shadow Bota Grade A", 44592, 140000, 60000, 1.000_000, 1, true));
        itens.add(new Item("Hero", 29509, 190000, 40000, 1, 1, true));
        itens.add(new Item("Arcana", 29585, 100000, 40000, 1, 1, true));
        itens.add(new Item("Pedra de Encantamento Epifania", 47777, 40000, 70000, 1, 1, true));
        itens.add(new Item("Raiva JRO", 610198, 80000, 40000, 1, 1, true));
        itens.add(new Item("Champion Stone II(Upper) ", 29613, 9999, 9998, 9000000, 7000000, false));
        itens.add(new Item("High Wizard Stone II(Lower)", 310183, 9999, 9998, 9000000, 7000000, false));

        //Pedras
        itens.add(new Item("Ametista", 719, 23, 14, 1.000_000, 1, false));
        itens.add(new Item("Etel Dust", 1000322, 2, 1, 1.000_000, 1000, false));
        itens.add(new Item("Etel Stone", 1000323, 24, 17, 1.000_000, 1000, false));
        itens.add(new Item("Etel Amber", 1000328, 300, 175, 1.000_000, 1000, false));
        itens.add(new Item("Etel Amethyst", 1000327, 130, 110, 1.000_000, 1000, false));
        itens.add(new Item("Etel Topaz", 1000326, 130, 75, 1.000_000, 1000, false));
        itens.add(new Item("Etel Aquamarine", 1000325, 95, 50, 1.000_000, 1000, false));

        return itens;
    }

}
