package cc.kebei.expands.office.word;


import cc.kebei.expands.office.word.support.template.DOCXTemplateReader;
import cc.kebei.utils.file.FileUtils;

import java.io.InputStream;

/**
 * Created by Kebei on 16-6-6.
 */
public class TestReader {

    public static void main(String[] args) throws Exception {
        try (InputStream template = FileUtils.getResourceAsStream("docx/template.docx");
             InputStream data = FileUtils.getResourceAsStream("docx/template_data.docx")
        ) {
            DOCXTemplateReader reader = new DOCXTemplateReader(template, data);
            System.out.println(reader.read());
        }

    }
}
