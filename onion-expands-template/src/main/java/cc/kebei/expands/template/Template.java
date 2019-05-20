package cc.kebei.expands.template;

import cc.kebei.expands.template.freemarker.FreemarkerTemplateRender;

public enum Template {
    freemarker() {
        FreemarkerTemplateRender render = new FreemarkerTemplateRender(2, 3, 23);

        @Override
        public TemplateRender compile(String template) {
            return render.compile(template);
        }

    };

    public abstract TemplateRender compile(String template);

}
