package gold.framework;

import gold.framework.annotation.UrlMapping;
import gold.framework.utils.Utils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class FrameworkListener implements ServletContextListener {

    /** Clés utilisées pour stocker les données dans le ServletContext */
    public static final String KEY_CONTROLLERS = "listeClassController";
    public static final String KEY_METHODES    = "listeMethode";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        System.out.println("=== [FrameworkListener] Initialisation du framework ===");

        // 1. Scan des controllers
        List<Class<?>> listeClassController = Utils.scanControllers();
        System.out.println("Nombre de controllers trouvés : " + listeClassController.size());

        // 2. Scan des méthodes @UrlMapping par controller
        Map<Class<?>, List<Method>> listeMethode = Utils.scanMethodes(listeClassController);

        // 3. Log récapitulatif
        for (Class<?> classe : listeClassController) {
            System.out.println("\n[Controller] " + classe.getName());
            List<Method> methodes = listeMethode.get(classe);
            if (methodes == null || methodes.isEmpty()) {
                System.out.println("   (aucune URL mappée)");
            } else {
                for (Method m : methodes) {
                    String url = m.getAnnotation(UrlMapping.class).value();
                    System.out.println("   @UrlMapping -> " + url + "  [méthode: " + m.getName() + "]");
                }
            }
        }

        // 4. Stockage dans le ServletContext pour que FrontController puisse y accéder
        ctx.setAttribute(KEY_CONTROLLERS, listeClassController);
        ctx.setAttribute(KEY_METHODES,    listeMethode);

        System.out.println("\n=== [FrameworkListener] Framework initialisé ! ===");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("=== [FrameworkListener] Arrêt du framework ===");
        sce.getServletContext().removeAttribute(KEY_CONTROLLERS);
        sce.getServletContext().removeAttribute(KEY_METHODES);
    }
}
