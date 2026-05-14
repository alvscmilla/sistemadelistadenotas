import javax.swing.SwingUtilities;

public class main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaAlunos tela = new TelaAlunos();
            tela.setVisible(true);
        });
    }
}