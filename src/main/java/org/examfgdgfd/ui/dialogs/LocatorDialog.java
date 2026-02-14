package org.examfgdgfd.ui.dialogs;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Modern dialog for selecting element locators with preview and validation.
 */
public class LocatorDialog extends JDialog {
    private String selectedLocator;
    private JList<String> locatorList;
    private JTextArea preview;
    private boolean confirmed = false;

    public LocatorDialog(JFrame parent, ArrayList<String> locators, String elementInfo) {
        super(parent, "Select Element Locator", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setAutoRequestFocus(true);
        initUI(locators, elementInfo);
    }

    private void initUI(ArrayList<String> locators, String elementInfo) {
        setBackground(new Color(15, 23, 42));
        JPanel panel = new JPanel();
        panel.setBackground(new Color(15, 23, 42));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Title
        JLabel titleLabel = new JLabel("Element: " + elementInfo);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLabel.setForeground(new Color(241, 245, 249));
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Locator List
        JLabel listLabel = new JLabel("Available Locators:");
        listLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        listLabel.setForeground(new Color(148, 163, 184));
        panel.add(listLabel);

        locatorList = new JList<>(locators.toArray(new String[0]));
        locatorList.setBackground(new Color(30, 41, 59));
        locatorList.setForeground(new Color(99, 102, 241));
        locatorList.setFont(new Font("JetBrains Mono", Font.PLAIN, 11));
        locatorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (locators.size() > 0) locatorList.setSelectedIndex(0);

        locatorList.addListSelectionListener(e -> updatePreview());

        JScrollPane scrollPane = new JScrollPane(locatorList);
        scrollPane.setBackground(new Color(30, 41, 59));
        scrollPane.setPreferredSize(new Dimension(0, 100));
        panel.add(scrollPane);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Preview
        JLabel previewLabel = new JLabel("Code Preview:");
        previewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        previewLabel.setForeground(new Color(148, 163, 184));
        panel.add(previewLabel);

        preview = new JTextArea();
        preview.setBackground(new Color(10, 15, 30));
        preview.setForeground(new Color(6, 182, 212));
        preview.setFont(new Font("JetBrains Mono", Font.PLAIN, 10));
        preview.setEditable(false);
        preview.setLineWrap(true);
        preview.setWrapStyleWord(true);
        preview.setMargin(new Insets(8, 8, 8, 8));

        JScrollPane previewScroll = new JScrollPane(preview);
        previewScroll.setBackground(new Color(10, 15, 30));
        previewScroll.setPreferredSize(new Dimension(0, 80));
        panel.add(previewScroll);
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
            if (locatorList.getSelectedIndex() >= 0) {
                selectedLocator = locatorList.getSelectedValue();
                confirmed = true;
                dispose();
            }
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
        updatePreview();
    }

    private void updatePreview() {
        if (locatorList.getSelectedIndex() >= 0) {
            String locator = locatorList.getSelectedValue();
            preview.setText("page." + locator.replace("cy.", ""));
        }
    }

    public String getSelectedLocator() {
        return selectedLocator;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}

