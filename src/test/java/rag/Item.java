package rag;

public class Item {
    String nome;
    int id;
    int precoRop;
    int bomPrecoRop;
    int precoZenny;
    int bomPrecoZenny;
    
    public Item(String nome, int id, int precoRop, int bomPrecoRop, int precoZenny, int bomPrecoZenny) {
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

    public int getPrecoRop() {
        return precoRop;
    }

    public void setPrecoRop(int precoRop) {
        this.precoRop = precoRop;
    }

    public int getBomPrecoRop() {
        return bomPrecoRop;
    }

    public void setBomPrecoRop(int bomPrecoRop) {
        this.bomPrecoRop = bomPrecoRop;
    }

    public int getPrecoZenny() {
        return precoZenny;
    }

    public void setPrecoZenny(int precoZenny) {
        this.precoZenny = precoZenny;
    }

    public int getBomPrecoZenny() {
        return bomPrecoZenny;
    }

    public void setBomPrecoZenny(int bomPrecoZenny) {
        this.bomPrecoZenny = bomPrecoZenny;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + " | id: " + id + " | precoRop: " + precoRop + " | bomPrecoRop: " + bomPrecoRop
                + " | precoZenny: " + precoZenny + " | bomPrecoZenny: " + bomPrecoZenny + "";
    }


 
}