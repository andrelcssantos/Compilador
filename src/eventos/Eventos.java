/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventos;

import Automatos.Automato;
import Automatos.Token;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Andre
 */
public class Eventos {

    private JScrollPane scroll;
    private JTable tabela;
    private JTable tabelaErro;
    private String[] nomeColunas2 = {"n", "Tipo", "Erro", "Linha", "Coluna"};
    private String[] nomeColunas1 = {"n", "Cadeia", "Token", "Linha", "Coluna"};
    private JTextField txtArquivo = null;
    private JTextField txtTabela = null;
    private JTextArea txtTexto = null;
    private JButton btnAbrir = null;
    private JButton btnTabTrans = null;
    private JButton btnSalvar = null;
    private JButton btnCompilar = null;
    private JPanel pnlBotoes = null;
    private String sArquivo = null;
    private String sTabTrans = null;
    private StringBuilder texto;
    private JFileChooser fc = new JFileChooser(".\\");
    private FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
    private Automato at;

    //ActionListener no botão Abrir
    public JButton getBtnAbrir() {
        if (btnAbrir != null) {
            return btnAbrir;
        }
        btnAbrir = new JButton();
        btnAbrir.setText("Arquivo");
        btnAbrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrir();
            }
        });
        return btnAbrir;
    }

    //ActionListener no botão Abrir
    public JButton getBtnTabela() {
        if (btnTabTrans != null) {
            return btnTabTrans;
        }
        btnTabTrans = new JButton();
        btnTabTrans.setText("Autômato");
        btnTabTrans.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pegaTabela();
            }
        });
        return btnTabTrans;
    }

    //ActionListener no botão Compilar
    public JButton getBtnCompilar() {
        if (btnCompilar != null) {
            return btnCompilar;
        }
        btnCompilar = new JButton();
        btnCompilar.setText("Analisar");
        btnCompilar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                at = new Automato(sTabTrans);
                at.Processar(sArquivo);
                setRowsTabelaLex();
            }
        });
        return btnCompilar;
    }

    //Exibe no topo da tela o caminho do arquivo escolhido
    public JTextField getTxtTabela() {
        if (txtTabela != null) {
            return txtTabela;
        }
        txtTabela = new JTextField(30);
        txtTabela.setEditable(false);
        return txtTabela;
    }

    //Exibe no topo da tela o caminho do arquivo da tabela escolhida
    public JTextField getTxtArquivo() {
        if (txtArquivo != null) {
            return txtArquivo;
        }
        txtArquivo = new JTextField(30);
        txtArquivo.setEditable(false);
        return txtArquivo;
    }

    //Exibe no topo da tela o caminho do arquivo escolhido
    public JTextArea getTxtTexto() {
        if (txtTexto != null) {
            return txtTexto;
        }
        txtTexto = new JTextArea(25, 45);
        return txtTexto;
    }

    //Método de abrir o arquivo para jogar na tela
    public void abrir() {
        fc.setFileFilter(filter);
        if (fc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        getTxtArquivo().setText(fc.getSelectedFile().getAbsolutePath());
        sArquivo = fc.getSelectedFile().getAbsolutePath();
        texto = new StringBuilder();
        try {
            Scanner scan = new Scanner(fc.getSelectedFile());
            while (scan.hasNextLine()) {
                texto.append(scan.nextLine()).append("\n");
            }
            getTxtTexto().setText("");
            getTxtTexto().setText(texto.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível ler o arquivo solicitado.");
            e.printStackTrace();
        }
    }

    //Pega o caminho do arquivo da tabela para passar para a classe automato
    public void pegaTabela() {
        fc.setFileFilter(filter);
        if (fc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        getTxtTabela().setText(fc.getSelectedFile().getAbsolutePath());
        sTabTrans = fc.getSelectedFile().getAbsolutePath();
    }

    //Método de Salvar o arquivo depois de compilado
    public void salvar(List<Token> t) {
        if (fc.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(fc.getSelectedFile());
            boolean pl = true;

            for (Token T : t) {
                Scanner scan;
                if (pl) {
                    scan = new Scanner(T.toString());
                    pl = false;
                } else {
                    scan = new Scanner("\n" + T.toString());
                }
                while (scan.hasNextLine()) {
                    pw.print(scan.nextLine());
                    //Evita a inserção de uma linha em branco no final do arquivo
                    if (scan.hasNextLine()) {
                        pw.println();
                    }
                }
            }
            pw.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível salvar o arquivo.");
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    //Cria tabela de tokens
    public JScrollPane criaTabela1() {

        tabela = new JTable(new DefaultTableModel(0, 5));

        scroll = new JScrollPane(tabela,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        for (int i = 0; i < nomeColunas1.length; i++) {
            javax.swing.table.TableColumn tc = tabela.getColumnModel().getColumn(i);
            tc.setHeaderValue(nomeColunas1[i]);
            DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
            dtcr.setHorizontalAlignment(SwingConstants.CENTER);
            tc.setCellRenderer(dtcr);

        }
        scroll.setPreferredSize(new Dimension(470, 500));
        tabela.setPreferredScrollableViewportSize(tabela.getPreferredSize());
        tabela.setFillsViewportHeight(true);
        tabela.setShowGrid(true);
        tabela.setEnabled(false);
        scroll.setAutoscrolls(true);
        return scroll;
    }

    //Cria tabela de erros
    public JScrollPane criaTabela2() {
        tabelaErro = new JTable(new DefaultTableModel(0, 5));
        scroll = new JScrollPane(tabelaErro,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        for (int i = 0; i < nomeColunas2.length; i++) {
            javax.swing.table.TableColumn tc = tabelaErro.getColumnModel().getColumn(i);
            tc.setHeaderValue(nomeColunas2[i]);
            DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
            dtcr.setHorizontalAlignment(SwingConstants.CENTER);
            tc.setCellRenderer(dtcr);
        }
        scroll.setPreferredSize(new Dimension(985, 130));
        tabelaErro.setForeground(Color.red);
        tabelaErro.setPreferredScrollableViewportSize(tabelaErro.getPreferredSize());
        tabelaErro.setFillsViewportHeight(true);
        tabelaErro.setShowGrid(true);
        tabelaErro.setEnabled(false);
        scroll.setAutoscrolls(true);
        return scroll;
    }

    //Inseri as informações nas tabelas
    private void setRowsTabelaLex() {
        DefaultTableModel model = new DefaultTableModel(nomeColunas1, 0);
        tabela.setModel(model);
        int count = 1;
        for (Token tk : at.getList()) {
            if (!tk.getToken().toString().substring(0, 4).equals("ERRO")) {
                model.addRow(new Object[]{count++, tk.getCadeia(), tk.getToken(), tk.getLinha(), tk.getColuna()});
            }
        }

        DefaultTableModel modelERRO = new DefaultTableModel(nomeColunas2, 0);
        tabelaErro.setModel(modelERRO);
        count = 1;
        for (Token tk : at.getList()) {
            if (tk.getToken().toString().substring(0, 4).equals("ERRO")) {
                modelERRO.addRow(new Object[]{count++, tk.getToken(), tk.getCadeia(), tk.getLinha(), tk.getColuna()});
            }
        }
    }

    //cria tamanho da lista
    private int sizeList() {
        if (at == null) {
            at = new Automato();
        }
        return at.sizeList();
    }
}
