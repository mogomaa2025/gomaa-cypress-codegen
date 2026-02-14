package org.examfgdgfd.ui.dialogs;

import javax.swing.*;
import java.awt.*;

/**
 * Modern dialog for selecting actions with icons and descriptions.
 */
public class ActionDialog extends JDialog {
    private String selectedAction;
    private boolean confirmed = false;
    private JComboBox<ActionOption> actionCombo;

    public static class ActionOption {
        public String name;
        public String icon;
        public String description;

        public ActionOption(String icon, String name, String description) {
            this.icon = icon;
            this.name = name;
            this.description = description;
        }

        @Override
        public String toString() {
            return icon + " " + name;
        }
    }

    public ActionDialog(JFrame parent, ActionOption[] actions) {
        super(parent, "Select Action", true);
        setSize(450, 320);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initUI(actions);
    }

    private void initUI(ActionOption[] actions) {
        setBackground(new Color(15, 23, 42));
        JPanel panel = new JPanel();
        panel.setBackground(new Color(15, 23, 42));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Title
        JLabel titleLabel = new JLabel("[*] What action do you want to perform?");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        titleLabel.setForeground(new Color(241, 245, 249));
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Dropdown
        actionCombo = new JComboBox<>(actions);
        actionCombo.setBackground(new Color(30, 41, 59));
        actionCombo.setForeground(new Color(241, 245, 249));
        actionCombo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        actionCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        actionCombo.addActionListener(e -> updateDescription());

        panel.add(actionCombo);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Description
        JLabel descLabel = new JLabel("Description:");
        descLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        descLabel.setForeground(new Color(148, 163, 184));
        panel.add(descLabel);

        JTextArea descArea = new JTextArea();
        descArea.setBackground(new Color(10, 15, 30));
        descArea.setForeground(new Color(6, 182, 212));
        descArea.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        descArea.setEditable(false);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setMargin(new Insets(8, 8, 8, 8));

        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setBackground(new Color(10, 15, 30));
        descScroll.setPreferredSize(new Dimension(0, 80));
        panel.add(descScroll);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(15, 23, 42));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        JButton confirmBtn = new JButton("OK - Confirm");
        confirmBtn.setBackground(new Color(99, 102, 241));
        confirmBtn.setForeground(new Color(241, 245, 249));
        confirmBtn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        confirmBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        confirmBtn.addActionListener(e -> {
            selectedAction = ((ActionOption) actionCombo.getSelectedItem()).name;
            confirmed = true;
            dispose();
        });

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBackground(new Color(107, 114, 128));
        cancelBtn.setForeground(new Color(241, 245, 249));
        cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        cancelBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        cancelBtn.addActionListener(e -> dispose());

        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(confirmBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(cancelBtn);

        panel.add(buttonPanel);
        setContentPane(panel);

        // Store reference to update description
        final JTextArea finalDescArea = descArea;
        actionCombo.addActionListener(e -> {
            ActionOption selected = (ActionOption) actionCombo.getSelectedItem();
            if (selected != null) {
                finalDescArea.setText(selected.description);
            }
        });

        updateDescription();
    }

    private void updateDescription() {
        // Will be called through listener
    }

    public String getSelectedAction() {
        return selectedAction;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}

