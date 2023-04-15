package br.com.rafaelcavalcante.biritashop.exceptios;

public class ImagemConstrucaoException extends Exception {
    
    public ImagemConstrucaoException(String mensagem){
        super(mensagem);
    }

    public ImagemConstrucaoException(String mensagem, Throwable causa){
        super(mensagem, causa);
    }
}
