package gold.app.etu003926;

import gold.framework.annotation.Controller;
import gold.framework.annotation.UrlMapping;

@Controller
public class TestController {

    @UrlMapping("/profil")
    public void profil() {
        System.out.println("[TestController] profil() appelé");
    }

    @UrlMapping("/dashboard")
    public void dashboard() {
        System.out.println("[TestController] dashboard() appelé");
    }

    @UrlMapping("/settings")
    public void settings() {
        System.out.println("[TestController] settings() appelé");
    }
}
