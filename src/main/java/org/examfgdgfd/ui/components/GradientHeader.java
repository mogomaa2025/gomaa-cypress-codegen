package org.examfgdgfd.ui.components;

import javax.swing.*;
import java.awt.*;

/**
 * Gradient Header Panel with professional styling.
 */
public class GradientHeader extends JPanel {
    private Color primaryColor;
    private Color secondaryColor;

    public GradientHeader(Color primaryColor, Color secondaryColor) {
        super();
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        setOpaque(false);
        setPreferredSize(new Dimension(0, 70));
        setLayout(new BorderLayout(10, 0));
        setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint gp = new GradientPaint(0, 0, primaryColor, getWidth(), 0, secondaryColor);
        g2d.setPaint(gp);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
    }

    public void setHeaderContent(String title, String subtitle) {
        removeAll();

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(241, 245, 249));

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(148, 163, 184));

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);

        add(titlePanel, BorderLayout.WEST);
        revalidate();
    }
}

