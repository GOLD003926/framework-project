package gold.framework;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String url = req.getRequestURI();
        System.out.println("[FrontController] GET  --> " + url);
        // Plus tard : routing, dispatch vers controllers, etc.
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String url = req.getRequestURI();
        System.out.println("[FrontController] POST --> " + url);
    }
}