package br.com.rafaelcavalcante.biritashop.model.enums;

public enum FormaPagamento {
    DINHEIRO("Dinheiro"),
    CREDITO("Crédito"),
    PIX("Pix");

    private String valor;

    FormaPagamento(String valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return this.valor;
    }
}
