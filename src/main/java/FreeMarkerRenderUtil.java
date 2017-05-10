import spark.ModelAndView;

import java.util.Map;

/**FreeMarkerEngine视图渲染工具类
 * Created by sb on 2017/5/9.
 */
public class FreeMarkerRenderUtil {
    public static String render(Map<String, Object> model, String templatePath) {
        return new FreeMarkerEngine().render(new ModelAndView(model, templatePath));
    }
}
