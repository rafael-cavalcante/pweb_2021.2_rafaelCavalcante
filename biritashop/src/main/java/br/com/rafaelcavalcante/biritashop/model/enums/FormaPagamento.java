package br.com.rafaelcavalcante.biritashop.model.enums;

public enum FormaPagamento {
    DINHEIRO ("Dinheiro"),
    CREDITO ("Crédito"),
    PIX ("Pix");
    
    private final String tipos;

    FormaPagamento(String tipos){
        this.tipos = tipos;
    }

    public String getTipos(){
        return this.tipos;
    }
}
