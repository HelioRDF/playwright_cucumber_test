package rag;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class ItemOld {

    String nome;
    int id;
    double precoRop;
    double bomPrecoRop;
    double precoZenny;
    double bomPrecoZenny;

    public ItemOld(String nome, int id, double precoRop, double bomPrecoRop, double precoZenny, double bomPrecoZenny) {
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

    public static ArrayList<Item> listaDeItens() {
        // Equipamentos
        ArrayList<Item> itens = new ArrayList<>();
        itens.add(new Item("Crown of Beelzebub", 400110, 19000, 7000, 1, 1));
        itens.add(new Item("Nebula Suit of Concentration", 450171, 70000, 40000, 1, 1));
        itens.add(new Item("Pedaço de Pele do Guardião", 2554, 500, 400, 32000000, 25000000));
        itens.add(new Item("Refinadora Complexa", 33360, 67000, 50000, 1, 1));
        itens.add(new Item("Mini-Refinadora", 33362, 19990, 15000, 1, 1));
        itens.add(new Item("Morango Suculento", 19137, 1800, 1000, 1, 1));
        itens.add(new Item("Echarpe do Paraíso", 19381, 3500, 2000, 1, 1));
        itens.add(new Item("Echarpe Misteriosa", 19499, 7000, 5000, 1, 1));
        itens.add(new Item("Martelo de Fundição Sombrio", 23926, 28000, 20000, 1, 1));

        //WL
        itens.add(new Item("Mental Expansion Ring [1]", 490079, 10000, 8000, 1, 1));
        itens.add(new Item("Memento Mori [1]", 490207, 350000, 200000, 1, 1));

        // Moedas e Vips
        itens.add(new Item("Bolsa de Rubis [100kk]", 40400, 1900, 1600, 1, 1));
        itens.add(new Item("Bolsa de Diamantes [1b]", 40097, 19000, 16000, 1, 1));
        itens.add(new Item("[1000] Moeda ROPS", 40108, 1, 1, 62000000, 59000000));
        itens.add(new Item("Cartão VIP [30 Dias]", 37009, 11000, 10000, 1, 1));
        itens.add(new Item("Moeda de Instância", 37009, 25, 15, 5000000, 1000000));
        itens.add(new Item("[1000] Moeda de Pontos MvP", 50086, 2800, 1500, 5000000, 1000000));
        itens.add(new Item("[Extreme] Esfera da Fenda", 33390, 450, 290, 5000000, 1000000));
        itens.add(new Item("Cristal Shadow Arma Grade D", 44581, 480000, 200000, 5000000, 1000000));
        itens.add(new Item("Cristal Shadow Escudo Grade D", 44582, 20000, 10000, 5000000, 1000000));
        itens.add(new Item("Cristal Shadow Bota Grade D", 44589, 160.000, 100.000, 5000000, 1000000));

        // Cartas
        itens.add(new Item("The Hanged Man Card", 27366, 5000, 2500, 1, 1));
        itens.add(new Item("Carta Elvira", 4577, 4000, 2500, 1, 1));
        itens.add(new Item("Carta Ermitão Selvagem", 4232, 800, 500, 1, 1));
        itens.add(new Item("Carta Belzebu", 4145, 23000, 19000, 1, 1));
        itens.add(new Item("Carta Lorde Seyren", 4357, 33000, 28000, 1, 1));
        itens.add(new Item("Carta Belzebu Selada", 4486, 83000, 48000, 1, 1));
        itens.add(new Item("Carta Tao Gunka Selada", 4624, 60000, 40000, 1, 1));
        itens.add(new Item("Carta Feiticeira Celia", 4671, 6000, 4000, 1, 1));
        itens.add(new Item("Carta Shura Chen", 4672, 14000, 10000, 1, 1));
        itens.add(new Item("Carta Arcana Kathryne", 4678, 12000, 6000, 1, 1));
        itens.add(new Item("Carta Cavaleiro Rúnico Seyren", 4679, 14000, 6000, 1, 1));
        itens.add(new Item("Contaminated Wanderer Card", 27361, 2200, 1000, 1, 1));

        // Minerios
        itens.add(new Item("Omni-Oridecon", 6438, 700, 400, 1, 1));
        itens.add(new Item("Super Omni-Oridecon", 70001, 1400, 1200, 1, 1));
        itens.add(new Item("Minor Cast Stone(Garment)", 25170, 950, 490, 1, 1));
        itens.add(new Item("Cast Stone(Garment)", 25067, 2700, 1900, 1, 1));
        itens.add(new Item("Mega-Elunium", 6439, 490, 350, 12000000, 10000000));
        itens.add(new Item("Super Mega-Elunium", 70002, 3300, 2800, 1, 1));
        itens.add(new Item("Etel Amber", 1000328, 300, 200, 11000000, 10000000));

        itens.add(new Item("Black Candy", 70058, 40000, 20000, 0, 0));
        itens.add(new Item("Bênção do Ferreiro", 6635, 120, 100, 9000000, 7000000));
        itens.add(new Item("Manual de Mascar", 14799, 2200, 1700, 1, 1));
        itens.add(new Item("Caixa de Areia de Bruxa [30k]", 11167, 5000, 4000, 300000000, 280000000));
        itens.add(new Item("Elixir Carnavalesco", 11248, 2100, 1500, 1, 1));

        // Pets
        itens.add(new Item("Sakray", 92560, 300000, 200000, 1, 1));
        itens.add(new Item("Ceifador Ankou", 92570, 300000, 200000, 1, 1));
        itens.add(new Item("Sarah Irine", 92580, 300000, 200000, 1, 1));
        itens.add(new Item("Death Witch", 92590, 300000, 200000, 1, 1));
        itens.add(new Item("Piamette Pesadelo", 93110, 300000, 200000, 1, 1));
        itens.add(new Item("Deus Morroc", 92460, 300000, 200000, 1, 1));
        itens.add(new Item("Betelgeuse", 92500, 300000, 200000, 1, 1));
        itens.add(new Item("Freyja", 92530, 300000, 200000, 1, 1));
        itens.add(new Item("Malícia", 92600, 300000, 200000, 1, 1));
        itens.add(new Item("Corrupted Dark Lord", 92680, 300000, 200000, 1, 1));
        itens.add(new Item("Pássaro Elemental", 92710, 300000, 200000, 1, 1));
        itens.add(new Item("Reencarnação de Morroc", 92750, 300000, 200000, 1, 1));
        itens.add(new Item("Imperador Galapago", 92910, 300000, 200000, 1, 1));
        itens.add(new Item("Serpente do Equinócio", 92900, 300000, 200000, 1, 1));
        itens.add(new Item("Leviatã Abismal", 92890, 300000, 200000, 1, 1));
        itens.add(new Item("Dragão da Serenidade", 92880, 300000, 200000, 1, 1));
        itens.add(new Item("Rei Arthur", 92630, 300000, 200000, 1, 1));
        itens.add(new Item("Rainha Aby", 92850, 300000, 200000, 1, 1));
        itens.add(new Item("Juncea", 92820, 300000, 200000, 1, 1));
        itens.add(new Item("Mistic Fairy", 93060, 300000, 200000, 1, 1));
        itens.add(new Item("Jordan Guardian", 91440, 300000, 200000, 1, 1));
        itens.add(new Item("Deusa Eris", 93090, 300000, 200000, 1, 1));
        itens.add(new Item("Lilith", 92960, 300000, 200000, 1, 1));
        itens.add(new Item("Ovo de Yortus Bailiff", 93336, 300000, 200000, 1, 1));
        itens.add(new Item("Deusa Hera", 92960, 300000, 200000, 1, 1));

        return itens;
    }

}
