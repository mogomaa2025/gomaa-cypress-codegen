package org.examfgdgfd.ui.components;

import javax.swing.*;
import java.awt.*;

/**
 * Card Panel with glassmorphism effect.
 * Used for grouping related UI elements.
 */
public class GlassmorphicCard extends JPanel {
    private String title;
    private Color textColor;

    public GlassmorphicCard(String title, Color textColor) {
        super();
        this.title = title;
        this.textColor = textColor;
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        titleLabel.setForeground(textColor);

        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 8)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Glassmorphism effect
        g2d.setColor(new Color(255, 255, 255, 8));
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);

        // Border
        g2d.setStroke(new BasicStroke(1f));
        g2d.setColor(new Color(255, 255, 255, 15));
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
    }
}

