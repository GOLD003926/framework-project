package gold.framework;

import gold.framework.annotation.UrlMapping;
import gold.framework.utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class FrontController extends HttpServlet {

    private List<Class<?>>              listeClassController;
    private Map<Class<?>, List<Method>> listeMethode;

    @Override
    public void init() throws ServletException {
        super.init();
        initialiserFramework();
    }

    private void initialiserFramework() {
        System.out.println("=== Initialisation du framework ===");

        // 1. Scan des controllers
        listeClassController = Utils.scanControllers();
        System.out.println("Nombre de controllers trouvés : " + listeClassController.size());

        // 2. Scan des méthodes @UrlMapping par controller
        listeMethode = Utils.scanMethodes(listeClassController);

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
        System.out.println("\n=== Framework initialisé ! ===");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = request.getRequestURI();
        System.out.println("[FrontController] URL reçue --> " + url);

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("  <meta charset='UTF-8'>");
        out.println("  <title>FrontController</title>");
        out.println("  <style>");
        out.println("    body { font-family: sans-serif; padding: 20px; }");
        out.println("    h1   { color: #333; }");
        out.println("    h2   { color: #555; margin-top: 30px; }");
        out.println("    h3   { margin-bottom: 6px; }");
        out.println("    .url-tag      { background: #e0f0ff; border-radius: 4px; padding: 2px 8px; font-family: monospace; }");
        out.println("    .method-tag   { color: #888; font-size: 0.9em; }");
        out.println("    li            { margin: 6px 0; }");
        out.println("    .ctrl-block   { border-left: 4px solid #4a90e2; padding-left: 12px; margin-bottom: 20px; }");
        out.println("    .found        { background: #e6ffe6; border-left: 4px solid #4caf50; padding: 10px 16px; margin: 16px 0; border-radius: 4px; }");
        out.println("    .not-found    { text-align: center; margin: 40px 0; color: #c0392b; }");
        out.println("    .not-found h2 { font-size: 4em; margin: 0; }");
        out.println("  </style>");
        out.println("</head>");
        out.println("<body>");

        out.println("<h1>FrontController</h1>");
        out.println("<p><strong>URL reçue :</strong> <span class='url-tag'>" + url + "</span></p>");

        verifierUrl(out, request);

        out.println("<h2>URLs disponibles par Controller :</h2>");

        if (listeMethode.isEmpty()) {
            out.println("<p><em>Aucun controller trouvé.</em></p>");
        } else {
            for (Map.Entry<Class<?>, List<Method>> entry : listeMethode.entrySet()) {
                Class<?>     classe   = entry.getKey();
                List<Method> methodes = entry.getValue();

                out.println("<div class='ctrl-block'>");
                out.println("  <h3>" + classe.getName() + "</h3>");
                out.println("  <ul>");

                if (methodes.isEmpty()) {
                    out.println("    <li><em>Aucune URL mappée</em></li>");
                } else {
                    for (Method m : methodes) {
                        String mappedUrl = m.getAnnotation(UrlMapping.class).value();
                        System.out.println("   @UrlMapping -> " + mappedUrl + "  [" + m.getName() + "]");
                        out.println("    <li>");
                        out.println("      <span class='url-tag'>" + mappedUrl + "</span>");
                        out.println("      <span class='method-tag'> → " + m.getName() + "()</span>");
                        out.println("    </li>");
                    }
                }

                out.println("  </ul>");
                out.println("</div>");
            }
        }

        out.println("</body></html>");
    }

    private void verifierUrl(PrintWriter out, HttpServletRequest request) {
        // Avec url-pattern "/*" : getServletPath() = "" et getPathInfo() = "/profil"
        // On prend pathInfo en priorité, fallback sur servletPath
        String pathInfo    = request.getPathInfo();
        String servletPath = request.getServletPath();
        String chemin      = (pathInfo != null && !pathInfo.isEmpty()) ? pathInfo : servletPath;

        System.out.println("[FrontController] verifierUrl -> chemin = " + chemin);

        for (Map.Entry<Class<?>, List<Method>> entry : listeMethode.entrySet()) {
            Class<?>     classe   = entry.getKey();
            List<Method> methodes = entry.getValue();

            for (Method m : methodes) {
                String mappedUrl = m.getAnnotation(UrlMapping.class).value();
                if (mappedUrl.equals(chemin)) {
                    System.out.println("[FrontController] URL trouvée : "
                            + classe.getSimpleName() + "#" + m.getName());
                    out.println("<div class='found'>");
                    out.println("  <strong>URL mappée :</strong>");
                    out.println("  <span class='url-tag'>" + mappedUrl + "</span>");
                    out.println("  &nbsp;→&nbsp;");
                    out.println("  <span class='method-tag'>"
                            + classe.getSimpleName() + "#" + m.getName() + "()</span>");
                    out.println("</div>");
                    return;
                }
            }
        }

        // URL non trouvée → 404
        System.out.println("[FrontController] URL non trouvée : " + chemin);
        out.println("<div class='not-found'>");
        out.println("  <h2>404</h2>");
        out.println("  <p>Aucune méthode mappée pour <span class='url-tag'>"
                + chemin + "</span></p>");
        out.println("</div>");
    }
}
