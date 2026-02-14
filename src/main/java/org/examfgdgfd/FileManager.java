package org.examfgdgfd;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class FileManager {
    public void updateFiles(String rootPath, String folder, String name, String pomLine, String specLine, String url) throws Exception {
        String basePath = rootPath + "/" + folder;
        File pagesDir = new File(basePath + "/cypress/pages");
        File testsDir = new File(basePath + "/cypress/e2e");

        if (!pagesDir.exists()) pagesDir.mkdirs();
        if (!testsDir.exists()) testsDir.mkdirs();

        // 1. Smart Append for Page Object
        File pomFile = new File(pagesDir, "PageObjects.js");
        String pomContent = pomFile.exists() ? Files.readString(pomFile.toPath()) : "export class PageObjects {\n}";
        int lastBracePOM = pomContent.lastIndexOf("}");
        pomContent = pomContent.substring(0, lastBracePOM) + "  " + pomLine + "\n}";
        Files.writeString(pomFile.toPath(), pomContent);

        // 2. Smart Append for Spec File
        File specFile = new File(testsDir, "generated_test.cy.js");
        String specContent;
        if (!specFile.exists()) {
            specContent = "import { PageObjects } from '../pages/PageObjects';\n\n" +
                    "describe('Automation Suite', () => {\n" +
                    "  const page = new PageObjects();\n" +
                    "  it('Generated User Flow', () => {\n" +
                    "    cy.visit('" + url + "');\n" +
                    "  });\n" +
                    "});";
        } else {
            specContent = Files.readString(specFile.toPath());
        }

        // البحث عن آخر إغلاق للـ it block قبل إغلاق الـ describe
        int lastItClosure = specContent.lastIndexOf("});"); // إغلاق الـ it
        int lastDescribeClosure = specContent.lastIndexOf("});"); // قد يتشابهان، لذا نأخذ مكان ذكي

        // نضع السطر الجديد قبل آخر إغلاقين (داخل الـ it block)
        String firstPart = specContent.substring(0, specContent.lastIndexOf("  });"));
        specContent = firstPart + "    " + specLine + "\n  });\n});";

        Files.writeString(specFile.toPath(), specContent);
    }
}