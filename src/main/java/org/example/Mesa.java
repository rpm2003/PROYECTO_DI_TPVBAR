package org.example;

public class Mesa {
    private int numero;
    private boolean ocupada;

    public Mesa(int numero, boolean ocupada) {
        this.numero = numero;
        this.ocupada = ocupada;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }
}
