package org.examfgdgfd.ui.components;

import javax.swing.*;
import java.awt.*;

/**
 * Console Panel with gradient border and glassmorphism.
 */
public class ConsolePanel extends JPanel {
    private Color accentColor;
    private Color primaryColor;
    private JTextArea console;

    public ConsolePanel(Color accentColor, Color primaryColor) {
        this.accentColor = accentColor;
        this.primaryColor = primaryColor;
        setOpaque(false);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        initConsole();
    }

    private void initConsole() {
        JLabel consoleTitle = new JLabel("[*] OUTPUT CONSOLE");
        consoleTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        consoleTitle.setForeground(new Color(241, 245, 249));
        add(consoleTitle, BorderLayout.NORTH);

        console = new JTextArea();
        console.setBackground(new Color(10, 15, 30));
        console.setForeground(accentColor);
        console.setFont(new Font("JetBrains Mono", Font.PLAIN, 12));
        console.setMargin(new Insets(10, 10, 10, 10));
        console.setLineWrap(true);
        console.setWrapStyleWord(true);
        console.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(console);
        scrollPane.setBackground(new Color(10, 15, 30));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(10, 15, 30));

        add(scrollPane, BorderLayout.CENTER);
    }

    public void appendText(String text) {
        console.append(text);
        console.setCaretPosition(console.getDocument().getLength());
    }

    public void clearConsole() {
        console.setText("");
    }

    public JTextArea getConsole() {
        return console;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Glassmorphism background
        g2d.setColor(new Color(255, 255, 255, 5));
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

        // Gradient border
        GradientPaint gp = new GradientPaint(0, 0, accentColor, getWidth(), getHeight(), primaryColor);
        g2d.setPaint(gp);
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
    }
}

