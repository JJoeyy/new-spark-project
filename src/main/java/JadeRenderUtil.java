import spark.ModelAndView;

import java.util.Map;

/**
 * Created by sb on 2017/5/9.
 */
public class JadeRenderUtil {
    public static String render(Map<String, Object> model, String templatePath) {
        return new JadeTemplateEngine().render(new ModelAndView(model, templatePath));
    }
}
