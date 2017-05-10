import spark.ModelAndView;

import java.util.Map;

/**VelocityTemplateEngine视图渲染工具类
 * Created by sb on 2017/5/9.
 */
public class VelocityRenderUtil {
    public static String render(Map<String, Object> model, String templatePath) {
        return new VelocityTemplateEngine().render(new ModelAndView(model, templatePath));
    }
}
