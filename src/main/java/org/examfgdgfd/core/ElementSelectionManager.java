package org.examfgdgfd.core;

import org.examfgdgfd.AutomationEngine;
import org.examfgdgfd.FileManager;
import org.examfgdgfd.ui.dialogs.ActionDialog;
import org.examfgdgfd.ui.dialogs.LocatorDialog;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Manages element selection, action selection, and code generation.
 * Handles interaction between UI and business logic.
 */
public class ElementSelectionManager {
    private AutomationEngine engine;
    private FileManager fileManager;
    private JFrame parentFrame;
    private JTextArea console;

    public ElementSelectionManager(AutomationEngine engine, FileManager fileManager, JFrame parentFrame, JTextArea console) {
        this.engine = engine;
        this.fileManager = fileManager;
        this.parentFrame = parentFrame;
        this.console = console;
    }

    /**
     * Handle element selection with improved UI dialogs.
     */
    public void handleElementSelection(Map<String, Object> data, String projectPath, String folderName, String targetUrl, String varName) {
        try {
            // Generate locator options
            ArrayList<String> locators = generateLocators(data);

            // Show locator selection dialog
            LocatorDialog locatorDialog = new LocatorDialog(
                parentFrame,
                locators,
                "Tag: " + data.get("tag") + " | Text: " + (String) data.get("text")
            );
            locatorDialog.setVisible(true);

            if (!locatorDialog.isConfirmed()) {
                appendConsole("⚠ Locator selection cancelled\n");
                return;
            }

            String selectedLocator = locatorDialog.getSelectedLocator();

            // Show action selection dialog
            ActionDialog.ActionOption[] actions = new ActionDialog.ActionOption[]{
                new ActionDialog.ActionOption("[CLICK]", "Click", "Simulate a mouse click on the element"),
                new ActionDialog.ActionOption("[TYPE]", "Type", "Enter text into an input field"),
                new ActionDialog.ActionOption("[HOVER]", "Hover", "Hover over the element"),
                new ActionDialog.ActionOption("[SCROLL]", "ScrollIntoView", "Scroll element into view"),
                new ActionDialog.ActionOption("[ASSERT]", "Assert Visible", "Assert that element is visible"),
                new ActionDialog.ActionOption("[WAIT]", "Wait", "Wait for element to be ready (no action)"),
                new ActionDialog.ActionOption("[WAIT+CLICK]", "Wait & Click", "Wait for element then click")
            };

            ActionDialog actionDialog = new ActionDialog(parentFrame, actions);
            actionDialog.setVisible(true);

            if (!actionDialog.isConfirmed()) {
                appendConsole("⚠ Action selection cancelled\n");
                return;
            }

            String selectedAction = actionDialog.getSelectedAction();

            // Get wait state
            String[] waits = {"None", "be.visible", "exist", "be.enabled"};
            String selectedWait = (String) JOptionPane.showInputDialog(
                parentFrame,
                "⏳ Select wait condition:",
                "Wait State",
                JOptionPane.PLAIN_MESSAGE,
                null,
                waits,
                waits[1]
            );

            if (selectedWait == null) {
                appendConsole("⚠ Wait state selection cancelled\n");
                return;
            }

            // Generate code
            String specLine = generateFinalCode(varName, selectedAction, selectedWait);
            String pomLine = "get " + varName + "() { return " + selectedLocator + "; }";

            // Update files
            fileManager.updateFiles(projectPath, folderName, varName, pomLine, specLine, targetUrl);

            // Display results
            appendConsole("✓ [POM] " + pomLine + "\n");
            appendConsole("✓ [SPEC] " + specLine + "\n");
            appendConsole("═══════════════════════════════════════════════════\n");

        } catch (Exception e) {
            appendConsole("❌ Error: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    private ArrayList<String> generateLocators(Map<String, Object> data) {
        ArrayList<String> locs = new ArrayList<>();

        String id = (String) data.get("id");
        String text = (String) data.get("text");
        String tag = (String) data.get("tag");
        String className = (String) data.get("class");

        if (id != null && !id.isEmpty()) {
            locs.add("cy.get('#" + id + "')");
        }

        if (text != null && !text.isEmpty() && text.length() < 50) {
            locs.add("cy.contains('" + text.replace("'", "\\'") + "')");
        }

        locs.add("cy.get('" + tag + "')");

        if (className != null && !className.isEmpty()) {
            String[] classes = className.split(" ");
            if (classes.length > 0) {
                locs.add("cy.get('." + classes[0] + "')");
            }
        }

        return locs;
    }

    private String generateFinalCode(String varName, String action, String wait) {
        StringBuilder sb = new StringBuilder("page." + varName);

        if (!wait.equals("None")) {
            sb.append(".should('").append(wait).append("')");
        }

        boolean force = JOptionPane.showConfirmDialog(
            parentFrame,
            "💥 Force Action? (Override waiting conditions)",
            "Options",
            JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION;

        boolean multi = JOptionPane.showConfirmDialog(
            parentFrame,
            "🔀 Multiple Elements?",
            "Options",
            JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION;

        String opts = "";
        if (force && multi) {
            opts = "{ force: true, multiple: true }";
        } else if (force) {
            opts = "{ force: true }";
        } else if (multi) {
            opts = "{ multiple: true }";
        }

        switch (action) {
            case "Type":
                String val = JOptionPane.showInputDialog("Enter text to type:");
                if (val == null) return "";
                sb.append(".clear().type('").append(val.replace("'", "\\'")).append("', ").append(opts).append(");");
                break;
            case "Click":
                sb.append(".click(").append(opts).append(");");
                break;
            case "Hover":
                sb.append(".trigger('mouseover', ").append(opts).append(");");
                break;
            case "Wait":
                sb.append("; // Waiting for element to be ready");
                break;
            case "Wait & Click":
                sb.append(".click({ force: true, waitForAnimations: true }").append(");");
                break;
            case "Assert Visible":
                sb.append(".should('be.visible');");
                break;
            default:
                sb.append(".scrollIntoView();");
        }

        return sb.toString();
    }

    public void appendConsole(String text) {
        SwingUtilities.invokeLater(() -> {
            console.append(text);
            console.setCaretPosition(console.getDocument().getLength());
        });
    }
}

