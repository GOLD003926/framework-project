package gold.app;

import gold.framework.annotation.Controller;
import gold.framework.annotation.UrlMapping;

@Controller
public class TestServlet {

    @UrlMapping("/hello")
    public void hello() {
        System.out.println("[TestServlet] hello() appelé");
    }

    @UrlMapping("/")
    public void home() {
        System.out.println("[TestServlet] home() appelé");
    }
}
