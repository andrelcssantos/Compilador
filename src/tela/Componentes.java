/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tela;

import eventos.Eventos;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Andre
 */
public class Componentes {

    private JPanel panelN, panelS, panelL, panelO;
    private Eventos ev = new Eventos();
    private JScrollPane scroll;

    //Botões
    public JPanel montaTelaNorte() {
        panelN = new JPanel();
        panelN.add(ev.getTxtTabela());
        panelN.add(ev.getTxtArquivo());
        panelN.add(ev.getBtnTabela());
        panelN.add(ev.getBtnAbrir());
        panelN.add(ev.getBtnCompilar());
        return panelN;
    }

    //Tabela Erro
    public JPanel montaTelaSul() {
        panelS = new JPanel();
        panelS.add(ev.criaTabela2());
        return panelS;
    }

    //JArea Arquivo
    public JPanel montaTelaLeste() {
        panelL = new JPanel();
        JTextArea txtArq = ev.getTxtTexto();
        scroll = new JScrollPane(txtArq,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setAutoscrolls(true);
        propriedadeNormal(txtArq);
        panelL.add(scroll);
        return panelL;
    }

    //Tabela Tokens
    public JPanel montaTelaOeste() {
        panelO = new JPanel();
        panelO.add(ev.criaTabela1());
        return panelO;
    }

    //Cria as propriedades para os campos de texto do centro da tela
    private void propriedadeNormal(JTextArea jArea) {
        jArea.setEditable(false);
        jArea.setFont(new Font("Serif", Font.PLAIN, 15));
        jArea.setBorder(BorderFactory.createLineBorder(Color.black));
        jArea.setForeground(Color.black);
        jArea.setBackground(Color.white);
        jArea.setLineWrap(true);
        jArea.setWrapStyleWord(true);
    }
}
