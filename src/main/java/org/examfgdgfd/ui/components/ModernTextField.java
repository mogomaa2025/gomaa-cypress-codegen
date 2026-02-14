package org.examfgdgfd.ui.components;

import javax.swing.*;
import java.awt.*;

/**
 * Custom Modern TextField with glassmorphism effect.
 * Provides a 2026-style text input component.
 */
public class ModernTextField extends JTextField {
    private static final Color TEXT_BG = new Color(30, 41, 59);
    private static final Color TEXT_BORDER = new Color(99, 102, 241);
    private static final Color TEXT_FG = new Color(241, 245, 249);
    private boolean focused = false;

    public ModernTextField() {
        super();
        initializeUI();
    }

    private void initializeUI() {
        setPreferredSize(new Dimension(0, 40));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        setBackground(TEXT_BG);
        setForeground(TEXT_FG);
        setCaretColor(TEXT_BORDER);
        setFont(new Font("Segoe UI", Font.PLAIN, 12));
        setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        setOpaque(false);

        addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                focused = true;
                repaint();
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                focused = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g2d.setColor(TEXT_BG);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);

        // Border glow effect
        int glowAlpha = focused ? 60 : 30;
        g2d.setColor(new Color(99, 102, 241, glowAlpha));
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);

        super.paintComponent(g);
    }
}

