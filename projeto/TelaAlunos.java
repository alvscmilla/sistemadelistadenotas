import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class TelaAlunos extends JFrame {

    // Pilha principal do sistema
    private TAD_Pilha<Aluno> pilhaAlunos;

    // Campos de entrada
    private JTextField campoNome;
    private JTextField campoMat;
    private JTextField campoPort;
    private JTextField campoGeo;
    private JTextField campoHist;
    private JTextField campoCien;

    // Area de exibicao da pilha
    private JTextArea areaPilha;

    // Rodape com informacoes
    private JLabel labelStatus;
    private JLabel labelTamanho;

    public TelaAlunos() {
        pilhaAlunos = new Pilha<>(50);
        configurarJanela();
        inicializarComponentes();
    }

    private void configurarJanela() {
        setTitle("Sistema de Cadastro de Alunos - Pilha LIFO");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void inicializarComponentes() {
        // Painel principal com margem
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));
        painelPrincipal.setBackground(new Color(245, 245, 250));

        // ── Titulo ──
        JLabel titulo = new JLabel("Sistema de Notas de Alunos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(new Color(33, 37, 41));
        titulo.setBorder(new EmptyBorder(0, 0, 10, 0));
        painelPrincipal.add(titulo, BorderLayout.NORTH);

        // ── Centro: formulario + pilha ──
        JPanel painelCentro = new JPanel(new GridLayout(1, 2, 10, 0));
        painelCentro.setOpaque(false);
        painelCentro.add(criarPainelFormulario());
        painelCentro.add(criarPainelPilha());
        painelPrincipal.add(painelCentro, BorderLayout.CENTER);

        // ── Rodape com status ──
        painelPrincipal.add(criarPainelStatus(), BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 210)),
                "Dados do Aluno",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12),
                new Color(80, 80, 100)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(5, 8, 5, 8);
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Nome
        campoNome = new JTextField();
        adicionarCampo(painel, gbc, "Nome:", campoNome, 0);

        // Notas
        campoMat  = new JTextField();
        campoPort = new JTextField();
        campoGeo  = new JTextField();
        campoHist = new JTextField();
        campoCien = new JTextField();

        adicionarCampo(painel, gbc, "Matematica (0-10):", campoMat,  1);
        adicionarCampo(painel, gbc, "Portugues (0-10):",  campoPort, 2);
        adicionarCampo(painel, gbc, "Geografia (0-10):",  campoGeo,  3);
        adicionarCampo(painel, gbc, "Historia (0-10):",   campoHist, 4);
        adicionarCampo(painel, gbc, "Ciencias (0-10):",   campoCien, 5);

        // Botoes
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(12, 8, 5, 8);
        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 8, 0));
        painelBotoes.setOpaque(false);

        JButton btnPush = criarBotao("Push (Empilhar)", new Color(37, 99, 235));
        JButton btnPop  = criarBotao("Pop (Remover)",   new Color(220, 38, 38));

        btnPush.addActionListener(e -> executarPush());
        btnPop.addActionListener(e -> executarPop());

        painelBotoes.add(btnPush);
        painelBotoes.add(btnPop);
        painel.add(painelBotoes, gbc);

        // Botao ver topo
        gbc.gridy = 7;
        gbc.insets = new Insets(4, 8, 8, 8);
        JButton btnTop = criarBotao("Top (Ver Topo)", new Color(107, 114, 128));
        btnTop.addActionListener(e -> executarTop());
        painel.add(btnTop, gbc);

        // Espaco vazio para empurrar tudo para cima
        gbc.gridy   = 8;
        gbc.weighty = 1.0;
        painel.add(new JLabel(), gbc);

        return painel;
    }

    private void adicionarCampo(JPanel painel, GridBagConstraints gbc,
                                String rotulo, JTextField campo, int linha) {
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = linha;
        gbc.weightx = 0;
        JLabel label = new JLabel(rotulo);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        painel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 210)),
                new EmptyBorder(4, 6, 4, 6)));
        painel.add(campo, gbc);
    }

    private JPanel criarPainelPilha() {
        JPanel painel = new JPanel(new BorderLayout(0, 8));
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 210)),
                "Pilha de Alunos (LIFO)",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12),
                new Color(80, 80, 100)));

        areaPilha = new JTextArea();
        areaPilha.setEditable(false);
        areaPilha.setFont(new Font("Consolas", Font.PLAIN, 12));
        areaPilha.setBackground(new Color(248, 249, 252));
        areaPilha.setBorder(new EmptyBorder(8, 8, 8, 8));
        areaPilha.setText("[ Pilha vazia ]");
        areaPilha.setForeground(new Color(100, 100, 120));

        JScrollPane scroll = new JScrollPane(areaPilha);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 230)));
        painel.add(scroll, BorderLayout.CENTER);

        labelTamanho = new JLabel("Tamanho: 0 / 50");
        labelTamanho.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        labelTamanho.setForeground(new Color(120, 120, 140));
        labelTamanho.setBorder(new EmptyBorder(0, 4, 4, 0));
        painel.add(labelTamanho, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel criarPainelStatus() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setOpaque(false);
        painel.setBorder(new EmptyBorder(8, 0, 0, 0));

        labelStatus = new JLabel("Pronto.");
        labelStatus.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        labelStatus.setForeground(new Color(100, 100, 120));
        painel.add(labelStatus, BorderLayout.WEST);

        return painel;
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(8, 12, 8, 12));
        return btn;
    }

    // ── Operacoes da pilha ──────────────────────────────────────

    private void executarPush() {
        String nome = campoNome.getText().trim();
        if (nome.isEmpty()) {
            mostrarErro("O campo nome nao pode estar vazio.");
            return;
        }

        double mat, port, geo, hist, cien;
        try {
            mat  = Double.parseDouble(campoMat.getText().trim());
            port = Double.parseDouble(campoPort.getText().trim());
            geo  = Double.parseDouble(campoGeo.getText().trim());
            hist = Double.parseDouble(campoHist.getText().trim());
            cien = Double.parseDouble(campoCien.getText().trim());
        } catch (NumberFormatException e) {
            mostrarErro("Digite apenas numeros nas notas.");
            return;
        }

        if (mat < 0 || mat > 10 || port < 0 || port > 10 || geo < 0 || geo > 10
                || hist < 0 || hist > 10 || cien < 0 || cien > 10) {
            mostrarErro("As notas devem estar entre 0 e 10.");
            return;
        }

        if (pilhaAlunos.isFull()) {
            mostrarErro("Pilha cheia! Capacidade maxima: 50 alunos.");
            return;
        }

        Aluno aluno = new Aluno(nome, mat, port, geo, hist, cien);
        pilhaAlunos.push(aluno);

        limparCampos();
        atualizarAreaPilha();
        labelStatus.setText("push() — \"" + nome + "\" empilhado com sucesso.");
        labelStatus.setForeground(new Color(21, 128, 61));
    }

    private void executarPop() {
        if (pilhaAlunos.isEmpty()) {
            mostrarErro("Pilha vazia! Nao e possivel executar pop().");
            return;
        }

        Aluno removido = pilhaAlunos.pop();
        atualizarAreaPilha();
        labelStatus.setText("pop() — \"" + removido.getNome() + "\" removido. Media: "
                + String.format("%.2f", removido.calcularMedia())
                + " | " + removido.verificarSituacao());
        labelStatus.setForeground(new Color(185, 28, 28));
    }

    private void executarTop() {
        if (pilhaAlunos.isEmpty()) {
            labelStatus.setText("top() — pilha vazia, retorna null.");
            labelStatus.setForeground(new Color(100, 100, 120));
            return;
        }

        Aluno topo = pilhaAlunos.top();
        labelStatus.setText("top() — topo: \"" + topo.getNome()
                + "\" | Media: " + String.format("%.2f", topo.calcularMedia()));
        labelStatus.setForeground(new Color(37, 99, 235));
    }

    private void atualizarAreaPilha() {
        labelTamanho.setText("Tamanho: " + pilhaAlunos.size() + " / 50");

        if (pilhaAlunos.isEmpty()) {
            areaPilha.setText("[ Pilha vazia ]");
            areaPilha.setForeground(new Color(100, 100, 120));
            return;
        }

        // Mostra a pilha de cima para baixo (topo primeiro)
        // Usamos uma pilha auxiliar para inverter a ordem de exibicao
        Pilha<Aluno> auxiliar = new Pilha<>(50);
        StringBuilder sb = new StringBuilder();

        // Desempilha para a auxiliar (inverte)
        while (!pilhaAlunos.isEmpty()) {
            auxiliar.push(pilhaAlunos.pop());
        }

        boolean primeiro = true;
        while (!auxiliar.isEmpty()) {
            Aluno a = auxiliar.pop();
            if (primeiro) {
                sb.append("-> [TOPO]\n");
                primeiro = false;
            }
            sb.append(String.format("   %-20s | Media: %5.2f | %s%n",
                    a.getNome(), a.calcularMedia(), a.verificarSituacao()));
            pilhaAlunos.push(a); // devolve para a pilha original
        }
        sb.append("-> [BASE]");

        areaPilha.setText(sb.toString());
        areaPilha.setForeground(new Color(33, 37, 41));
    }

    private void limparCampos() {
        campoNome.setText("");
        campoMat.setText("");
        campoPort.setText("");
        campoGeo.setText("");
        campoHist.setText("");
        campoCien.setText("");
        campoNome.requestFocus();
    }

    private void mostrarErro(String msg) {
        labelStatus.setText("Erro: " + msg);
        labelStatus.setForeground(new Color(185, 28, 28));
    }
}
