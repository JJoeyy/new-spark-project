/**
 * Created by sb on 2017/5/9.
 */


import com.google.gson.Gson;
import spark.ModelAndView;
import spark.Redirect;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;
//import Spark statically;

public class Main {
    public static void main(String[] args) {
       //port(8080);

        //the public directory name is not included in the URL
        staticFiles.location("/public/css/style.css");

        staticFiles.externalLocation(System.getProperty("java.io.tmpdir"));

        int maxThreads = 8;
        int minThreads = 2;
        int timeOutMillis = 30000;
        threadPool(maxThreads,minThreads,timeOutMillis);

       // awaitInitialization(); // Wait for server to be initialized


        // matches "GET /hello/foo" and "GET /hello/bar"
        // request.params(":name") is 'foo' or 'bar'
       /* get("/hello/:name", (request, response) -> {
            return "Hello: " + request.params("name");

        });
*/
        // matches "GET /say/hello/to/world"
        // request.splat()[0] is 'hello' and request.splat()[1] 'world'
        /*get("/say*//*//*to*//*", (request, response) -> {
            return "Number of splat parameters: " + request.splat().length;
        });
*/
        get("/hello", (request, response)->{
            return "Hello World";
        });

        post("/hello",(request,response)->{
            return "Hello World";
        });

        get("/private",(request,response)->{
            return "Go Away";
        });

        get("user/:name",(request,response)->{
            request.queryMap().get("user").get("name").value();
            return "欢迎您："+request.params("name");
        });

        get("news/:section",(request,response)->{
            response.type("text/xml");
            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><news>"
                    + request.params("section") + "</news>";
        });

        get("/protected",(request,response)->{
            halt(403,"I don't think so!!!");
            return null;
        });

        get("/redirect",(req,resp)->{
            //resp.redirect("/news/world");
            //resp.redirect("/protected");
            resp.redirect("/news/world",300);
            return null;
        });

        get("/",(req,resp)->{
            return "root";
        });

        /*path("/api", () -> {
            before("*//**//*", (q, a) -> System.out.println("Received api call"));
            path("/email", () -> {
                post("/add",       EmailApi.addEmail);
                put("/change",     EmailApi.changeEmail);
                delete("/remove",  EmailApi.deleteEmail);
            });
            path("/username", () -> {
                post("/add",       UserApi.addUsername);
                put("/change",     UserApi.changeUsername);
                delete("/remove",  UserApi.deleteUsername);
            });
        });*/

        before((request, response) -> {
            boolean authenticated=true;
            // ... check if authenticated
            if (!authenticated) {
                halt(401, "You are not welcome here");
            }
        });

        after((req,resp)->{
            resp.header("foo","set by after filter");
            //System.out.println(req.headers("foo"));
        });
        before("/protected/*", (request, response) -> {
            // ... check if authenticated
            halt(401, "Go Away!");
        });
        //直接调用redirect方法，后面接route ,参数为fromPath和toPath
        //?? Remember to import Spark statically instead of prefixing it as Spark.redirect
        //redirect.get("/redirect2","/user/baby");

        //post
        //redirect.post("/redirect2", "/user/joey", Redirect.Status.SEE_OTHER);

        redirect.any("/redirect2", "/user/baby", Redirect.Status.MOVED_PERMANENTLY);//显示：欢迎您：baby

        //地址访问错误404设置
       //Using string/html Not found俩种方式都无法识别notFound（）方法
        notFound("<html><body><h1>Custom 404 handling</h1></body></html>");
        notFound((req, res) -> {
            res.type("application/json");
            return "{\"message\":\"Custom 404\"}";
        });

        //网络访问错误500设置
        // Using string/html  internalServerError 俩种方式 无法识别internalServerError（）方法。
        internalServerError("<html><body><h1>Custom 500 handling</h1></body></html>");
        // Using Route
        internalServerError((req, res) -> {
            res.type("application/json");
            return "{\"message\":\"Custom 500 handling\"}";
        });


       //异常映射
        get("/throwexception",(req,resp)->{
            throw new NotFoundException();
        });
        exception(NotFoundException.class,(exception, request, response)->{
            // Handle the exception here
            response.status(404);
            response.body("Resource not found");
        });
        //静态文件
        //staticFileLocation("/public");
        //Exception in thread "main" java.lang.IllegalStateException: This must be done before route mapping has begun
        //超时设置
        staticFiles.expireTime(600); // ten minutes
        //自定义header 相同的key将会覆盖对应的value值
        staticFiles.header("Key1","Value-1");
        staticFiles.header("Key1","Value-2");

        get("/hello2","application/json",(request,response)->{
            return new MyMessage("Hello Kitty");
        },new JsonTransformer());

        Gson gson=new Gson();
        get("/hello3",(req,resp)->new MyMessage("Hello motou"),gson::toJson);

        //  用VelocityTemplateEngine渲染视图 推荐使用do this

        get("template-example", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("message", "Hello Velocity");
//            return new VelocityTemplateEngine().render(
//                    new ModelAndView(model, "hello.html"));
            return VelocityRenderUtil.render(model,"hello.html");

        });

        get("template-example2",(req,resp)->{
            Map<String,Object> model=new HashMap<>();
            model.put("message","Hello FreeMarker");
            return FreeMarkerRenderUtil.render(model,"hello.html");
        });

        get("template-example3", (req, resp) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("message", "Hello Jade");
           /* try {
                return JadeRenderUtil.render(model,"hello.html");
            }catch (Exception e){
                e.printStackTrace();
            }*/
           return VelocityRenderUtil.render(model,"hello.html");
          //  return new ModelAndView(model, "hello.html"); // located in resources/templates directory
        });


    }
}
