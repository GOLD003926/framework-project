package mg.test.controller;

import mg.nandra.framework.annotation.Controller;
import mg.nandra.framework.annotation.UrlMapping;
import mg.nandra.framework.enumeration.HttpMethod;

@Controller
public class TestController {

    @UrlMapping(url="/hello", method=HttpMethod.GET)
    public String direBonjour() {
        return "Hello world!!";
    }
}