package gold.app.form;

import gold.framework.annotation.Controller;
import gold.framework.annotation.UrlMapping;

@Controller
public class TestForm {
    @UrlMapping(value = "/validerForm", type = UrlMapping.TYPE.POST)
    public String validerForm() {
        System.out.println("Validation du formulaire");
        return "formulaireOk";
    }

    @UrlMapping(value = "/form", type = UrlMapping.TYPE.GET)
    public String form() {
        return "formulaire";
    }
}
