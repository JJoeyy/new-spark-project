import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.JadeTemplate;
import spark.ModelAndView;
import spark.TemplateEngine;
import spark.template.jade.loader.SparkClasspathTemplateLoader;

import java.io.IOException;
import java.util.Map;

/**JadeTemplateEngine实类化
 * Created by sb on 2017/5/9.
 */
public class JadeTemplateEngine extends TemplateEngine {
    private JadeConfiguration configuration;

    /**
     * Construct a jade template engine with 'templates' as root directory.
     */
    public JadeTemplateEngine() {
        this("templates");
    }

    /**
     * Construct a jade template engine with specified root.
     *
     * @param templateRoot the template root directory to use
     */
    public JadeTemplateEngine(String templateRoot) {
        configuration = new JadeConfiguration();
        configuration.setTemplateLoader(new SparkClasspathTemplateLoader(templateRoot));
    }

    /**
     * Construct a jade template engine from a raw JadeConfiguration
     *
     * @param configuration the raw JadeConfiguration to use
     */
    public JadeTemplateEngine(JadeConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * Access to the internal JadeConfiguration
     *
     * @return The JadeConfiguration used by this engine
     */
    public JadeConfiguration configuration() {
        return configuration;
    }

    /**
     * {@inheritDoc}
     */
    @Override

    public String render(ModelAndView modelAndView) {
        try {
            JadeTemplate template = configuration.getTemplate(modelAndView.getViewName());
            return configuration.renderTemplate(template, (Map<String, Object>) modelAndView.getModel());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
