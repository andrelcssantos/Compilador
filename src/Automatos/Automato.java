/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Automatos;

import eventos.Eventos;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 *
 * @author GUILHERME
 */
public class Automato {

    //Variáveis
    private int[][] mattrans;
    private Map<String, Integer> alfabeto;
    public boolean Comentario = false;
    public boolean quebraCadeia = true;
    public int Indice;
    private String arquivo;
    private List<Token> Tokens;
    private PalavrasReservadas pr = PalavrasReservadas.NULL;

    //Construtor vazio pra a inicialização da tela
    public Automato() {
    }

    //Construtor
    public Automato(String _arquivo) {
        arquivo = _arquivo;
        CarregaAutomato();
    }

    //classe carrega o automato
    private void CarregaAutomato() {
        int qtasLinhas, qtasColunas;
        try {
            //Carrega o Autômato
            BufferedReader aqv = new BufferedReader(new FileReader(arquivo));
            String linha;

            //Lê a quantidade de linhas e colunas -> Estados e Elementos no alfabeto
            linha = aqv.readLine();
            String[] st = linha.split(" ");
            qtasLinhas = Integer.parseInt(st[0]);
            qtasColunas = Integer.parseInt(st[1]);

            //Carrega o alfabeto
            alfabeto = new HashMap<String, Integer>();
            linha = aqv.readLine();
            st = linha.split(" ");
            int i = 0;
            while (st.length - 1 >= i) {
                if (st[i].equals("espaco")) {
                    alfabeto.put(" ", i);
                } else {
                    alfabeto.put(st[i], i);
                }
                i++;
            }

            //Carrega a Matriz de transição
            mattrans = new int[qtasLinhas][qtasColunas];
            int j = 0;
            for (i = 0; i < qtasLinhas; i++) {
                linha = aqv.readLine();
                st = linha.split(" ");
                for (j = 0; j < qtasColunas; j++) {
                    mattrans[i][j] = Integer.parseInt(st[j]);
                }
            }
            //Fecha o arquivo
            aqv.close();
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }

    //classe que processa o automato
    public void Processar(String _arquivo) {
        try {
            arquivo = _arquivo;
            BufferedReader aqv;
            aqv = new BufferedReader(new FileReader(arquivo));
            int nrLinha = 0;
            String linha = "\n";
            Tokens = new ArrayList<Token>();
            int estadoInicial = 0;
            int estadoFinal = 0;
            int i = 1;
            int nrLine = 1;

            while ((linha = aqv.readLine()) != null) {
                int nrColuna = 0;
                String cadeia = "";
                int cad = 0;
                int nrCol = 1;

                while (nrColuna < linha.length()) {
                    cadeia += linha.toCharArray()[nrColuna];

                    estadoInicial = ProcessaCadeia(cadeia);

                    if (estadoInicial == 0) {
                        cadeia = "";
                        nrCol = nrColuna;
                    }

                    if (estadoInicial == -1 || estadoInicial == -2) {
                        cad = cadeia.length() - 1;

                        if (cad != 0 && estadoInicial != -2) {
                            String newCadeia = cadeia.substring(0, cad);
                            String token = buscaToken(estadoFinal, newCadeia);
                            pr = buscaTipo(token);

                            if (!token.isEmpty()) {
                                if (pr == PalavrasReservadas.NULL) {
                                    Tokens.add(new Token(nrLine, nrCol, newCadeia, pr, token));
                                } else {
                                    Tokens.add(new Token(nrLine, nrCol, newCadeia, pr, pr.toString()));
                                }
                            }
                        } else {
                            String newCadeia = cadeia.substring(0, cadeia.length());
                            String token = buscaToken(estadoInicial, newCadeia);
                            pr = buscaTipo(token);

                            if (!token.isEmpty()) {
                                if (pr == PalavrasReservadas.NULL) {
                                    Tokens.add(new Token(nrLine, nrCol, newCadeia, pr, token));
                                } else {
                                    Tokens.add(new Token(nrLine, nrCol, newCadeia, pr, pr.toString()));
                                }
                            }
                        }
                        cadeia = "";
                        estadoInicial = 0;
                        if (cadeia.length() > 1) {
                            nrColuna--;
                        }
                        nrCol = nrColuna;
                    }
                    estadoFinal = estadoInicial;
                    cad++;
                    nrColuna++;
                }

                if (!cadeia.isEmpty()) {
                    cad = cadeia.length();
                    String newCadeia = cadeia.substring(0, cad);
                    String token = buscaToken(estadoFinal, newCadeia);
                    pr = buscaTipo(token);

                    if (!token.isEmpty()) {
                        if (pr == PalavrasReservadas.NULL) {
                            Tokens.add(new Token(nrLine, nrCol, newCadeia, pr, token));
                        } else {
                            Tokens.add(new Token(nrLine, nrCol, newCadeia, pr, pr.toString()));
                        }
                    }
                }

                nrLine++;
            }
            Eventos ev = new Eventos();
            ev.salvar(Tokens);
        } catch (Exception ex) {

        }
    }

    //envia a cadeia ao automato
    private int ProcessaCadeia(String cadeia) {
        int s1 = 0, i = 0;
        char c;

        for (i = 0; i < cadeia.length(); i++) {
            //Percorre o autômato até ocorrer encontrar um estado de parada ou terminar a cadeia
            c = proximo_caracter(cadeia, i);
            s1 = transicao(s1, String.valueOf(c));
            //Se encontrar um estado de parada, reinicia o autômato até ocorrer encontrar um novo estado de parada ou terminar a cadeia
            //E quebra a cadeia em duas partes
            if (s1 == -1) {
                break;
            }
        }
        return s1;
    }

    //Busca qual o tipo da cadeia
    private PalavrasReservadas buscaTipo(String cadeia) {
        switch (cadeia) {
            case "Iden_program":
                return PalavrasReservadas.PaRe_program;
            case "Iden_begin":
                return PalavrasReservadas.PaRe_begin;
            case "Iden_var":
                return PalavrasReservadas.PaRe_var;
            case "Iden_real":
                return PalavrasReservadas.PaRe_real;
            case "Iden_integer":
                return PalavrasReservadas.PaRe_integer;
            case "Iden_read":
                return PalavrasReservadas.PaRe_read;
            case "Iden_while":
                return PalavrasReservadas.PaRe_while;
            case "Iden_write":
                return PalavrasReservadas.PaRe_write;
            case "Iden_end":
                return PalavrasReservadas.PaRe_end;
            case "Iden_if":
                return PalavrasReservadas.PaRe_if;
            case "Iden_then":
                return PalavrasReservadas.PaRe_then;
            case "Iden_else":
                return PalavrasReservadas.PaRe_else;
            default:
                return PalavrasReservadas.NULL;
        }
    }

    //estados finais das cadeias validadas pelo automato
    private String buscaToken(int e, String cadeia) {
        switch (e) {
            case 2:
            case 3:
            case 4:
            case 5:
                return "SEsp_" + cadeia;
            case 6:
                return "Oper_" + cadeia;
            case 7:
                return "Digi_" + cadeia;
            case 8:
                return "DgPF_" + cadeia;
            case 9:
                return "Iden_" + cadeia;
            default:
                return "ERRO_" + cadeia;
        }

    }

    //Retorna o próximo caracter da cadeia
    private char proximo_caracter(String Entrada, int indice) {
        char c;
        c = Entrada.toCharArray()[indice];
        return c;
    }

    //Retorna o estado final da transação
    //Se estado for inexistente retorna -1
    private int transicao(int S, String c) {
        try {
            int simboloCol = alfabeto.get(c);
            S = mattrans[S][simboloCol];
        } catch (Exception e) {
            S = -1;
        }
        return S;
    }

    //retorna a lista
    public List<Token> getList() {
        if (Tokens == null) {
            Tokens = new ArrayList<Token>();
        }
        return Tokens;
    }

    //retorna o tamanho da lista
    public int sizeList() {
        return getList().size();
    }
}
