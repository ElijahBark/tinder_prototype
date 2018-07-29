package it.dan.utilits;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

public class FreeMarkerObject {
    public static void run(Map<String, Object> model, String pageName, Writer out, Class servletClass) throws IOException {

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        cfg.setClassLoaderForTemplateLoading(servletClass.getClassLoader(), "");

        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);

        Template template = cfg.getTemplate(pageName);
        try {
            template.process(model, out);

        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
