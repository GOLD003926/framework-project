package gold.app;

import gold.framework.annotation.Controller;
import gold.framework.annotation.UrlMapping;

@Controller
public class TestServlet {

    @UrlMapping(value = "/", type = UrlMapping.TYPE.GET)
    public String home() {
        System.out.println("[TestServlet] home() appelé");
        return "goldFramework";
    }
}
