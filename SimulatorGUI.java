import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Random;

public class SimulatorGUI extends JFrame {
    private JTextField txtFrames;
    private JTextField txtQtdPages;
    private JTextField txtPages;
    private JCheckBox cbFIFO, cbLRU, cbClock, cbOptimal;
    private JTextArea txtResult;
    private ChartPanel chartPanel;

    // Paleta de Cores Tema Claro
    private Color bgLight = new Color(245, 245, 245);
    private Color bgPanel = Color.WHITE;
    private Color fgText = new Color(30, 30, 30);
    private Color btnGreen = new Color(46, 204, 113);
    private Color btnGray = new Color(220, 220, 220);
    private Color borderGray = new Color(200, 200, 200);

    public SimulatorGUI() {
        setTitle("SDPM - Simulador de Algoritmos de Páginas");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(bgLight);
        setLayout(new BorderLayout(10, 10));

        // Tenta usar o visual nativo do sistema para o tema claro
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}

        // Painel Superior para Entradas (Inputs)
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBackground(bgPanel);
        inputPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(borderGray), 
                "Fornecer dados", TitledBorder.LEFT, TitledBorder.TOP, 
                new Font("Arial", Font.BOLD, 14), new Color(0, 150, 60)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Linha 1: Tamanho da memória
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        inputPanel.add(createLabel("* Tamanho da memória (Frames):"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtFrames = createTextField("3");
        inputPanel.add(txtFrames, gbc);

        // Linha 2: Quantidade de páginas
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        inputPanel.add(createLabel("* Quantidade de páginas (Random):"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtQtdPages = createTextField("15");
        inputPanel.add(txtQtdPages, gbc);

        // Linha 3: Fila de páginas
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        inputPanel.add(createLabel("* Fila de páginas (referências):"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtPages = createTextField("1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5");
        inputPanel.add(txtPages, gbc);

        // Linha 4: Algoritmos
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        inputPanel.add(createLabel("* Algoritmos:"), gbc);
        
        JPanel pnlAlgos = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlAlgos.setBackground(bgPanel);
        cbFIFO = createCheckBox("Algoritmo FIFO", true);
        cbLRU = createCheckBox("Algoritmo LRU", true);
        cbClock = createCheckBox("Algoritmo Relógio", true);
        cbOptimal = createCheckBox("Algoritmo Ótimo", true);
        pnlAlgos.add(cbFIFO); pnlAlgos.add(cbLRU); pnlAlgos.add(cbClock); pnlAlgos.add(cbOptimal);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        inputPanel.add(pnlAlgos, gbc);

        // Linha 5: Botões de Ação
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlButtons.setBackground(bgPanel);

        JButton btnSimular = new JButton("Simular");
        btnSimular.setBackground(btnGreen);
        btnSimular.setForeground(Color.WHITE);
        btnSimular.setFocusPainted(false);
        btnSimular.setFont(new Font("Arial", Font.BOLD, 13));
        btnSimular.addActionListener(e -> simular());

        JButton btnGerar = createButton("Gerar dados aleatórios");
        btnGerar.addActionListener(e -> gerarDados());

        JButton btnLimpar = createButton("Limpar");
        btnLimpar.addActionListener(e -> limpar());

        pnlButtons.add(btnSimular);
        pnlButtons.add(btnGerar);
        pnlButtons.add(btnLimpar);

        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 1.0;
        inputPanel.add(pnlButtons, gbc);

        add(inputPanel, BorderLayout.NORTH);

        // Painel Central: Resultados e Gráfico
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        centerPanel.setBackground(bgLight);
        centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Área de Texto dos Resultados
        txtResult = new JTextArea();
        txtResult.setEditable(false);
        txtResult.setBackground(bgPanel);
        txtResult.setForeground(fgText);
        txtResult.setFont(new Font("Monospaced", Font.BOLD, 15));
        txtResult.setMargin(new Insets(10, 10, 10, 10));
        
        JScrollPane scrollResult = new JScrollPane(txtResult);
        scrollResult.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(borderGray), 
                "Resultado da simulação", TitledBorder.LEFT, TitledBorder.TOP, 
                new Font("Arial", Font.BOLD, 14), fgText));
        scrollResult.getViewport().setBackground(bgPanel);

        // Painel do Gráfico
        chartPanel = new ChartPanel();
        chartPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(borderGray), 
                "Gráfico Comparativo (Faltas de Página)", TitledBorder.LEFT, TitledBorder.TOP, 
                new Font("Arial", Font.BOLD, 14), fgText));

        centerPanel.add(scrollResult);
        centerPanel.add(chartPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(fgText);
        lbl.setFont(new Font("Arial", Font.PLAIN, 13));
        return lbl;
    }

    private JTextField createTextField(String text) {
        JTextField txt = new JTextField(text);
        txt.setBackground(Color.WHITE);
        txt.setForeground(Color.BLACK);
        txt.setCaretColor(Color.BLACK);
        txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderGray, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        txt.setPreferredSize(new Dimension(300, 32));
        return txt;
    }

    private JCheckBox createCheckBox(String text, boolean selected) {
        JCheckBox cb = new JCheckBox(text, selected);
        cb.setBackground(bgPanel);
        cb.setForeground(fgText);
        cb.setFocusPainted(false);
        return cb;
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(btnGray);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        return btn;
    }

    private void gerarDados() {
        try {
            int qtd = Integer.parseInt(txtQtdPages.getText().trim());
            if (qtd <= 0) throw new Exception();
            Random rand = new Random();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < qtd; i++) {
                sb.append(rand.nextInt(10)); // páginas de 0 a 9
                if (i < qtd - 1) sb.append(", ");
            }
            txtPages.setText(sb.toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Informe uma quantidade válida e maior que zero.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void limpar() {
        txtPages.setText("");
        txtFrames.setText("");
        txtQtdPages.setText("");
        txtResult.setText("");
        chartPanel.setValues(0, 0, 0, 0);
    }

    private void simular() {
        try {
            String inputPages = txtPages.getText().trim();
            int frames = Integer.parseInt(txtFrames.getText().trim());

            if (frames <= 0) {
                JOptionPane.showMessageDialog(this, "O tamanho da memória (frames) deve ser maior que 0.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (inputPages.isEmpty()) {
                JOptionPane.showMessageDialog(this, "A fila de páginas não pode estar vazia.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] strPages = inputPages.split(",");
            int[] pages = new int[strPages.length];
            for (int i = 0; i < strPages.length; i++) {
                pages[i] = Integer.parseInt(strPages[i].trim());
            }

            int faltasFIFO = cbFIFO.isSelected() ? PageReplacementAlgorithms.runFIFO(pages, frames) : 0;
            int faltasLRU = cbLRU.isSelected() ? PageReplacementAlgorithms.runLRU(pages, frames) : 0;
            int faltasRelogio = cbClock.isSelected() ? PageReplacementAlgorithms.runClock(pages, frames) : 0;
            int faltasOtimo = cbOptimal.isSelected() ? PageReplacementAlgorithms.runOptimal(pages, frames) : 0;

            StringBuilder sb = new StringBuilder();
            sb.append(">>> RESULTADO DA SIMULAÇÃO <<<\n\n");
            
            if (cbFIFO.isSelected()) sb.append("- Método FIFO - ").append(faltasFIFO).append(" faltas de página\n");
            if (cbLRU.isSelected()) sb.append("- Método LRU - ").append(faltasLRU).append(" faltas de página\n");
            if (cbClock.isSelected()) sb.append("- Método do Relógio - ").append(faltasRelogio).append(" faltas de página\n");
            if (cbOptimal.isSelected()) sb.append("- Método Ótimo - ").append(faltasOtimo).append(" faltas de página\n");

            txtResult.setText(sb.toString());

            // Atualizar o gráfico
            chartPanel.setValues(
                cbFIFO.isSelected() ? faltasFIFO : -1,
                cbLRU.isSelected() ? faltasLRU : -1,
                cbClock.isSelected() ? faltasRelogio : -1,
                cbOptimal.isSelected() ? faltasOtimo : -1
            );

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Formato inválido. Use números inteiros separados por vírgula na fila de páginas.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimulatorGUI().setVisible(true));
    }

    // Painel customizado para desenhar o gráfico de barras
    class ChartPanel extends JPanel {
        private int fifo = -1, lru = -1, clock = -1, optimal = -1;

        public ChartPanel() {
            setBackground(bgPanel);
        }

        public void setValues(int f, int l, int c, int o) {
            this.fifo = f; this.lru = l; this.clock = c; this.optimal = o;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int max = Math.max(Math.max(fifo, lru), Math.max(clock, optimal));
            if (max <= 0) return; // Nada a desenhar

            int width = getWidth();
            int height = getHeight();
            int padding = 40;
            
            // Conta quantos estão ativos para calcular a largura da barra dinamicamente
            int activeBars = 0;
            if (fifo != -1) activeBars++;
            if (lru != -1) activeBars++;
            if (clock != -1) activeBars++;
            if (optimal != -1) activeBars++;

            if (activeBars == 0) return;

            int barWidth = (width - 2 * padding) / (activeBars * 2);

            int[] values = {fifo, lru, clock, optimal};
            String[] labels = {"FIFO", "LRU", "Relógio", "Ótimo"};
            
            // Cores para o gráfico no tema claro
            Color[] colors = {new Color(239, 83, 80), new Color(66, 165, 245), new Color(255, 167, 38), new Color(102, 187, 106)};

            int startX = padding;
            for (int i = 0; i < 4; i++) {
                if (values[i] == -1) continue; // Pula algoritmos não selecionados

                int barHeight = (int) (((double) values[i] / max) * (height - 2 * padding - 20));
                if (barHeight == 0 && values[i] > 0) barHeight = 5; // Altura mínima se houver faltas

                int x = startX;
                int y = height - padding - barHeight;

                // Desenha a sombra da barra
                g2.setColor(new Color(0, 0, 0, 20));
                g2.fillRect(x + 3, y + 3, barWidth, barHeight);

                // Desenha a barra
                g2.setColor(colors[i]);
                g2.fillRect(x, y, barWidth, barHeight);

                // Contorno da barra
                g2.setColor(colors[i].darker());
                g2.drawRect(x, y, barWidth, barHeight);

                // Textos
                g2.setColor(fgText);
                g2.setFont(new Font("Arial", Font.BOLD, 12));
                FontMetrics fm = g2.getFontMetrics();
                
                // Desenha o número exato de faltas em cima da barra
                String valStr = String.valueOf(values[i]);
                g2.drawString(valStr, x + (barWidth - fm.stringWidth(valStr)) / 2, y - 5);

                // Desenha o nome do algoritmo embaixo
                g2.drawString(labels[i], x + (barWidth - fm.stringWidth(labels[i])) / 2, height - padding + 15);

                startX += barWidth + 30; // Espaçamento entre as barras
            }
        }
    }
}
