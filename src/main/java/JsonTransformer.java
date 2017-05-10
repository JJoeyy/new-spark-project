import spark.ResponseTransformer;
import com.google.gson.Gson;
/**
 * Created by sb on 2017/5/9.
 */
public class JsonTransformer implements ResponseTransformer {
    private Gson gson=new Gson();
    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }
}
