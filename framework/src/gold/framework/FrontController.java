package gold.framework;

import gold.framework.affichage.Affichage;
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

    private List<Class<?>> listeClassController;
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
        out.println("</head>");
        out.println("<body>");

        out.println("<h1>FrontController</h1>");
        out.println("<p>URL reçue : <code>" + url + "</code> (Method: " + request.getMethod() + ")</p>");

        Method methodConcernee = verifierUrl(out, request);
        if (methodConcernee == null) {
            return;
        }
        Affichage.urlDispoParController(listeMethode, out);

        out.println("</body></html>");
    }

    private Method verifierUrl(PrintWriter out, HttpServletRequest request) {
        return Affichage.verifierUrl(listeMethode, out, request);
    }
}
