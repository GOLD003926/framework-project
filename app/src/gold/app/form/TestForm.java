package gold.app.form;

import gold.framework.annotation.Controller;
import gold.framework.annotation.UrlMapping;

@Controller
public class TestForm {
    @UrlMapping(value = "/validerForm", type = UrlMapping.TYPE.POST)
    public void validerForm(){
        System.out.println("Validation du formulaire");
    }
}
