package gold.app.test;

import gold.framework.annotation.Controller;
import gold.framework.annotation.UrlMapping;

@Controller
public class TestController {

    @UrlMapping(value = "/profil", type = UrlMapping.TYPE.GET)
    public void profil() {
        System.out.println("[TestController] profil() appelé");
    }

    @UrlMapping(value = "/dashboard", type = UrlMapping.TYPE.GET)
    public void dashboard() {
        System.out.println("[TestController] dashboard() appelé");
    }

    @UrlMapping(value = "/settings", type = UrlMapping.TYPE.GET)
    public void settings() {
        System.out.println("[TestController] settings() appelé");
    }
}
