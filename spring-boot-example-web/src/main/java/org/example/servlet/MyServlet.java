package org.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = "/myservlet/", description = "Servlet demo")
public class MyServlet extends HttpServlet {

    private static final long serialVersionUID = -8685285401859800066L;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init() throws ServletException {
        super.init();
        logger.info(getClass().getName() + " init");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(">>>>>>>>>>doGet()<<<<<<<<<<<");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(">>>>>>>>>>doPost()<<<<<<<<<<<");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Hello World</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Hello World</h1>");
        out.println("</body>");
        out.println("</html>");
        out.flush();
    }
}
