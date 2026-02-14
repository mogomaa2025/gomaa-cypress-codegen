package org.examfgdgfd.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Custom Modern Button with gradient background, hover effects, and glassmorphism.
 * Provides a 2026-style button component for the UI.
 */
public class ModernButton extends JButton {
    private Color baseColor;
    private Color accentColor;
    private boolean hovered = false;
    private boolean pressed = false;

    public ModernButton(String text, Color baseColor, Color accentColor) {
        super(text);
        this.baseColor = baseColor;
        this.accentColor = accentColor;
        initializeUI();
        setupMouseHandlers();
    }

    private void initializeUI() {
        setPreferredSize(new Dimension(0, 45));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        setForeground(new Color(241, 245, 249));
        setFont(new Font("Segoe UI", Font.BOLD, 12));
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
    }

    private void setupMouseHandlers() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                pressed = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                pressed = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                pressed = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        float alpha = hovered ? (pressed ? 1.0f : 0.95f) : 0.85f;
        GradientPaint gp = new GradientPaint(0, 0, baseColor, getWidth(), getHeight(), accentColor);
        g2d.setPaint(gp);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

        if (hovered) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f));
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        }

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Custom border rendering
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.setColor(new Color(255, 255, 255, 10));
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
    }
}

