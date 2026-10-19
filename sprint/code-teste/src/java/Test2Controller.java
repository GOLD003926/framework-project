package mg.test.controller;

import models.*;

import mg.nandra.framework.annotation.Controller;
import mg.nandra.framework.annotation.UrlMapping;
import mg.nandra.framework.annotation.JsonResponse;
import mg.nandra.framework.annotation.Param;
import mg.nandra.framework.enumeration.HttpMethod;

import mg.nandra.framework.model.ModelAndView;
import mg.nandra.framework.model.MySession;

@Controller
public class Test2Controller {

    @UrlMapping(url = "/test", method = HttpMethod.GET)
    public ModelAndView testerUrl() {
        ModelAndView mv = new ModelAndView();
        mv.addAttribute("personne", new Artiste(1, "Rakoto", "Jean",null));
        mv.setView("accueil");
        return mv;
    }

    @UrlMapping(url = "/api/v1", method = HttpMethod.GET)
    @JsonResponse
    public Artiste testApiv1() {
        return new Artiste(1, "Rakoto", "Jean",null);
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
    public Artiste submitPersonneObject(Artiste personne) {
        new PersonneDAO().save(personne);
        return personne;
    }
    @UrlMapping(url = "/login", method = HttpMethod.POST)
    public String login(@Param("nom") String nom, MySession session) {
        session.set("user", nom);
        return "Connecté : " + nom;
    }

    @UrlMapping(url = "/profil", method = HttpMethod.GET)
    public String profil(MySession session) {
        String user = session.get("user", String.class);
        if (user == null) return "Non connecté";
        return "Bonjour " + user;
    }

    @UrlMapping(url = "/logout", method = HttpMethod.GET)
    public String logout(MySession session) {
        session.invalidate();
        return "Déconnecté";
    }
}
