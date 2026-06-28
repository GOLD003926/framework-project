package gold.framework.affichage;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import gold.framework.annotation.UrlMapping;

public class Affichage {
    public static void urlDispoParController(Map<Class<?>, List<Method>> listeMethode, PrintWriter out) {
        out.println("<h2>URLs disponibles par Controller :</h2>");

        if (listeMethode.isEmpty()) {
            out.println("<p>Aucun controller trouvé.</p>");
        } else {
            for (Map.Entry<Class<?>, List<Method>> entry : listeMethode.entrySet()) {
                Class<?> classe = entry.getKey();
                List<Method> methodes = entry.getValue();

                out.println("<div>");
                out.println("  <h3>" + classe.getName() + "</h3>");
                out.println("  <ul>");

                if (methodes.isEmpty()) {
                    out.println("    <li>Aucune URL mappée</li>");
                } else {
                    for (Method m : methodes) {
                        String mappedUrl = m.getAnnotation(UrlMapping.class).value();
                        System.out.println("   @UrlMapping -> " + mappedUrl + "  [" + m.getName() + "]");
                        out.println("    <li><code>" + mappedUrl + "</code> → " + m.getName() + "()</li>");
                    }
                }

                out.println("  </ul>");
                out.println("</div>");
            }
        }
    }

    public static Object[]  verifierUrl(Map<Class<?>, List<Method>> listeMethode, PrintWriter out,
            HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        String servletPath = request.getServletPath();
        String chemin = (pathInfo != null && !pathInfo.isEmpty()) ? pathInfo : servletPath;
        String methodRequette = request.getMethod();

        System.out.println("[FrontController] verifierUrl -> chemin = " + chemin);

        Object[] retour = new Object[2];
        for (Map.Entry<Class<?>, List<Method>> entry : listeMethode.entrySet()) {
            Class<?> classe = entry.getKey();
            List<Method> methodes = entry.getValue();
            for (Method m : methodes) {
                String mappedUrl = m.getAnnotation(UrlMapping.class).value();
                UrlMapping.TYPE type = m.getAnnotation(UrlMapping.class).type();
                if (mappedUrl.equals(chemin) && type.toString().equalsIgnoreCase(methodRequette)) {
                    System.out.println("[FrontController] URL trouvée : "
                            + classe.getSimpleName() + " #" + m.getName());
                    out.println("<div>");
                    out.println("  <strong>URL mappée :</strong>");
                    out.println("  <code>" + mappedUrl + "</code>");
                    out.println("  → ");
                    out.println("  <code>" + classe.getSimpleName() + "#" + m.getName() + "()</code>");
                    out.println("</div>");
                    retour[0] = m;
                    retour[1] = classe;
                    return retour;
                }
            }
        }

        // URL non trouvée -> 404
        out.println("<div style=\"text-align: center;\">");
        out.println("  <h2>404</h2>");
        out.println("  <p>Aucune méthode mappée pour <code>"
                + chemin + "</code></p>");
        out.println("</div>");
        return null;
    }
}
