package mg.test.controller;

import models.Personne;

import mg.nandra.framework.annotation.Controller;
import mg.nandra.framework.annotation.UrlMapping;
import mg.nandra.framework.annotation.JsonResponse;
import mg.nandra.framework.annotation.Param;
import mg.nandra.framework.enumeration.HttpMethod;

import mg.nandra.framework.model.ModelAndView;

@Controller
public class Test2Controller {

    @UrlMapping(url = "/test", method = HttpMethod.GET)
    public ModelAndView testerUrl() {
        ModelAndView mv = new ModelAndView();
        mv.addAttribute("personne", new Personne(1, "Rakoto", "Jean", "S4"));
        mv.setView("accueil");
        return mv;
    }

    @UrlMapping(url = "/api/v1", method = HttpMethod.GET)
    @JsonResponse
    public Personne testApiv1() {
        return new Personne(1, "Rakoto", "Jean", "S4");
    }

    @UrlMapping(url = "/api/v3", method = HttpMethod.GET)
    @JsonResponse
    public String testApiv2() {
        return "{ message: Ceci n'est qu'un test }";
    }

    @UrlMapping(url = "/form-personne", method = HttpMethod.GET)
    public ModelAndView formPersonne() {
        ModelAndView mv = new ModelAndView();
        mv.setView("form-personne");
        return mv;
    }

    @UrlMapping(url = "/submit-personne", method = HttpMethod.POST)
    public String submitPersonne(@Param("id") int id, @Param("nom") String nom,
            @Param("prenom") String prenom, @Param("classe") String classe) {
        return "[id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", classe=" + classe + "]";
    }

    @UrlMapping(url = "/submit-personne-object", method = HttpMethod.POST)
    @JsonResponse
    public Personne submitPersonneObject(Personne personne) {
        return personne;
    }
}
