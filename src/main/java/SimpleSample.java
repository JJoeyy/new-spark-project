/**
 * Created by sb on 2017/5/9.
 */
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class SimpleSample {
    //main()方法写错
   public static void main(){
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
           return "欢迎您："+request.params("name");
       });
   }
}
