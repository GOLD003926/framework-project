package gold.app;

import gold.framework.annotation.Controller;
import gold.framework.annotation.UrlMapping;

@Controller
public class TestServlet {

    @UrlMapping(value = "/hello", type = UrlMapping.TYPE.GET)
    public void hello() {
        System.out.println("[TestServlet] hello() appelé");
    }

    @UrlMapping(value = "/", type = UrlMapping.TYPE.GET)
    public void home() {
        System.out.println("[TestServlet] home() appelé");
    }
}
