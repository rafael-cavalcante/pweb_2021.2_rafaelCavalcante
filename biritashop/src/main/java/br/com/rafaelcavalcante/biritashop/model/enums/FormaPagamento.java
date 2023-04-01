package br.com.rafaelcavalcante.biritashop.model.enums;

public enum FormaPagamento {
    DINHEIRO ("Dinheiro"),
    CREDITO ("Crédito"),
    PIX ("Pix");
    
    private final String tipo;

    FormaPagamento(String tipo){
        this.tipo = tipo;
    }

    public String getTipo(){
        return this.tipo;
    }
}
