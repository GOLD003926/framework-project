package gold.framework;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import gold.framework.utils.Utils;

public class FrontController extends HttpServlet {
    private List<Class<?>> listeClassController;

    @Override
    public void init() throws ServletException {
        super.init();
        initialiserFramework();
    }

    private void initialiserFramework() {
        System.out.println("Initialisation du framework...");
        listeClassController = Utils.scanControllers();
        System.out.println("Nombre de controllers trouvés : " + listeClassController.size());
        System.out.println("Framework initialisé !");
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
        System.out.println("[FrontController] URL --> " + url);
        for (Class<?> classe : listeClassController) {
            System.out.println("Controller dispo : " + classe.getName());
        }
    }
}