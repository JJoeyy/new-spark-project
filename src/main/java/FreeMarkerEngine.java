import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import spark.ModelAndView;
import spark.TemplateEngine;

import java.io.StringWriter;

/**FreeMarkerEngine实列
 * Created by sb on 2017/5/9.
 */
public class FreeMarkerEngine extends TemplateEngine{
    /**
     * The FreeMarker configuration
     */
    private Configuration configuration;

    /**
     * Creates a FreeMarkerEngine
     */
    public FreeMarkerEngine() {
        this.configuration = createDefaultConfiguration();
    }

    /**
     * Creates a FreeMarkerEngine with the specified configuration
     *
     * @param configuration The Freemarker configuration
     */
    public FreeMarkerEngine(Configuration configuration) {
        this.configuration = configuration;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String render(ModelAndView modelAndView) {
        try {
            StringWriter stringWriter = new StringWriter();
            Template template = configuration.getTemplate(modelAndView.getViewName());
            template.process(modelAndView.getModel(), stringWriter);
            return stringWriter.toString();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Sets FreeMarker configuration.
     * Note: If configuration is not set the default configuration will be used.
     *
     * @param configuration the configuration to set
     */
    @Deprecated // use constructor
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    private Configuration createDefaultConfiguration() {
        Configuration configuration = new Configuration(new Version(2, 3, 23));
        configuration.setClassForTemplateLoading(FreeMarkerEngine.class, "");
        return configuration;
    }
}
