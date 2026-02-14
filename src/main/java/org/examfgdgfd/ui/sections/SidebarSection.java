package org.examfgdgfd.ui.sections;

import org.examfgdgfd.ui.components.*;
import org.examfgdgfd.util.PreferenceManager;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Sidebar section containing project configuration inputs and action buttons.
 */
public class SidebarSection extends JPanel {
    public ModernTextField pathInput;
    public ModernTextField urlInput;
    public ModernTextField folderInput;
    public ModernButton btnBrowse;
    public ModernButton btnLaunch;
    public ModernButton btnPlay;

    private static final Color PRIMARY = new Color(99, 102, 241);
    private static final Color SECONDARY = new Color(139, 92, 246);
    private static final Color ACCENT = new Color(6, 182, 212);
    private static final Color BG_DARK = new Color(15, 23, 42);
    private static final Color TEXT_PRIMARY = new Color(241, 245, 249);

    private volatile boolean fileChooserOpen = false;  // Prevent re-triggering

    public SidebarSection() {
        setPreferredSize(new Dimension(380, 0));
        setBackground(BG_DARK);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        initComponents();
    }

    private void initComponents() {
        // Card 1: Project Path
        GlassmorphicCard card1 = new GlassmorphicCard("[F] PROJECT PATH", TEXT_PRIMARY);
        pathInput = new ModernTextField();
        btnBrowse = new ModernButton("[>] Browse Folder", PRIMARY, ACCENT);
        card1.add(pathInput);
        card1.add(Box.createRigidArea(new Dimension(0, 8)));
        card1.add(btnBrowse);

        // Card 2: Target URL
        GlassmorphicCard card2 = new GlassmorphicCard("[W] TARGET URL", TEXT_PRIMARY);
        urlInput = new ModernTextField();
        urlInput.setText("https://dev.zeustra.com");
        card2.add(urlInput);

        // Card 3: Folder Name
        GlassmorphicCard card3 = new GlassmorphicCard("[D] FOLDER NAME", TEXT_PRIMARY);
        folderInput = new ModernTextField();
        folderInput.setText("My_Automation_Folder");
        card3.add(folderInput);

        // Add cards to sidebar
        add(card1);
        add(Box.createRigidArea(new Dimension(0, 12)));
        add(card2);
        add(Box.createRigidArea(new Dimension(0, 12)));
        add(card3);
        add(Box.createRigidArea(new Dimension(0, 20)));

        // Action Buttons
        btnLaunch = new ModernButton("[*] LAUNCH BROWSER", ACCENT, SECONDARY);
        btnPlay = new ModernButton("[+] START SPY MODE", PRIMARY, ACCENT);

        add(btnLaunch);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(btnPlay);
        add(Box.createVerticalGlue());

        // Browse button handler
        btnBrowse.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File(PreferenceManager.getLastOpenedDirectory()));
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int result = fc.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                String selectedPath = fc.getSelectedFile().getAbsolutePath();
                pathInput.setText(selectedPath);
                PreferenceManager.setLastOpenedDirectory(selectedPath);
            }

            // Properly dispose of file chooser
            fc.cancelSelection();
            fc = null;
        });
    }

    public String getProjectPath() {
        return pathInput.getText();
    }

    public String getTargetUrl() {
        return urlInput.getText();
    }

    public String getFolderName() {
        return folderInput.getText();
    }

    public void setProjectPath(String path) {
        pathInput.setText(path);
    }
}

