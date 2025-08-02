package sgel_final;

/**
 * Classe principal do Sistema de Gestão Escolar de Livros
 * @author Tiago
 */
public class SGEL_FINAL {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaLogin.telaLogin(); 
            }
        });
    }
}

