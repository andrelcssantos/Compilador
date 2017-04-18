/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Automatos;

import java.util.List;

/**
 *
 * @author GUILHERME
 */
public class Token {

    private int linha;
    private int coluna;
    private String cadeia;
    private PalavrasReservadas tipo;
    private String token;

    public Token() {
    }

    public Token(int l, int c, String cad, PalavrasReservadas t, String tk) {
        this.linha = l;
        this.coluna = c;
        this.cadeia = cad;
        this.tipo = t;
        this.token = tk;
    }

    public int getLinha() {
        return this.linha;
    }

    public int getColuna() {
        return this.coluna;
    }

    public String getCadeia() {
        return this.cadeia;
    }

    public PalavrasReservadas getTipo() {
        return this.tipo;
    }

    public String getToken() {
        return this.token;
    }

    public Token BuscaToken() {
        return new Token();
    }

    public Token Copia() {
        Token newToken = new Token();
        newToken.cadeia = this.cadeia;
        newToken.coluna = this.coluna;
        newToken.linha = this.linha;
        newToken.tipo = this.tipo;
        newToken.token = this.token;
        return newToken;
    }

    public boolean Contem(String t) {
        if (t != null) {
            return this.cadeia.contains(t);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.linha + "|" + this.coluna + "|" + this.cadeia + "|" + this.token;
    }
}
