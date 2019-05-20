package cc.kebei.expands.template;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * @author Kebei
 * @TODO
 */
public class TemplateTest {
    @Test
    public void testFreemarker() throws Exception {
        TemplateRender render = Template.freemarker.compile("test");
        assertEquals(render.render(new HashMap<>()),"test");
    }
}