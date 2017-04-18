package tela;

import eventos.Eventos;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Andre
 */
public class TelaPrincipal extends JFrame {

    private Componentes cp = new Componentes();
    private Eventos ev = new Eventos();

    public TelaPrincipal() {
        super("Compiladores - Analisador Léxico");
    }

    public void mostraTela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(cp.montaTelaNorte(), BorderLayout.NORTH);
        add(cp.montaTelaSul(), BorderLayout.SOUTH);
        add(cp.montaTelaLeste(), BorderLayout.WEST);
        add(cp.montaTelaOeste(), BorderLayout.EAST);
        setSize(1024, 768);
        setResizable(false);
        setLocationRelativeTo(new TelaPrincipal());
        setVisible(true);
    }
}
