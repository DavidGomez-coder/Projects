/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test2;

import Test1.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import login.LoginManager;

/**
 *
 * @author dgpv2
 */
@WebServlet(name = "Test2Main", urlPatterns = {"/Test2Main"})
public class Test2Main extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String loginName = LoginManager.getLoginName(request);
        if (loginName != null) {
            //generamos preguntas
            Test2Manager.generateAnswers();
            response.setContentType("text/html; charset=ISO-8859-1");
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>\n"
                    + "<!--\n"
                    + "To change this license header, choose License Headers in Project Properties.\n"
                    + "To change this template file, choose Tools | Templates\n"
                    + "and open the template in the editor.\n"
                    + "-->");
            out.println("<html>\n"
                    + "    <head>\n"
                    + "\n"
                    + "        <title>TEST2-INICIO</title>\n"
                    + "        <meta charset=\"UTF-8\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "        <link rel=\"stylesheet\" href=\"./Styles/mainStyle.css\">\n"
                    + "    </head>\n"
                    + "\n"
                    + "    <body>\n"
                    + "        <nav>\n"
                    + "            <ul>\n"
                    + "                <li><p class=\"login-head-text\">Menú</p></li>\n"
                    + "                <li><br></li>\n"
                    + "                <li><hr></li>\n"
                    + "                <li><br></li>\n"
                    + "                <li><p style=\"text-align: center; color:white; "
                    + "                        \">Bienvenido \"" + loginName + "\"</p></li>\n"
                    + "                <li><br></li>\n"
                    + "                <input class=\"login-button\" type=\"button\" onclick=\"location.href = 'ProcessLogoutServlet'\" value=\"Logout\">\n"
                    + "                </li>\n"
                    + "                <li><br></li>\n"
                    + "                <li><hr></li>\n"
                    + "                <li><br></li>\n"
                    + "                <li><p class=\"login-head-text\">Tests</p></li>\n"
                    + "                <br>\n"
                    + "                <li><div style=\"text-align: center\"><a href=\"Test1Main\">Test 1</a></div></li>\n"
                    + "                <br>\n"
                    + "                <li><div style=\"text-align: center\"><a href=\"#\">Test 2</a></div></li>\n"
                    + "                <br>\n"
                    + "                <li><div style=\"text-align: center\"><a href=\"Test3Main\">Test 3</a></div></li>\n"
                            +" <li><input type=\"button\" class=\"login-button\" value=\"ATRÁS\" \n"
                    + "                           onclick=\"location.href = 'MainPageServlet'\"\n"
                    + "                           style=\"position: absolute;  bottom: 50px;\"></li>\n"
                    + "            </ul>\n"
                    + "\n"
                    + "        </nav>\n"
                    + "        <br>\n"
                    + "        <h1 style=\"text-align: center; color: white; text-decoration: underline\">SISTEMA TUTOR INFORMÁTICA</h1>\n"
                    + "        <br><br>\n"
                    + "        <h2 style=\"text-align: center; color: white;\">Información acerca del TEST 2</h2>\n "
                    + "        <form class=\"login-form\" action=\"Test2Q1\">"
                    + "        <p class=\"login-head-text\">Este es el segundo test. Aquí se pondrán a prueba tus conocimientos sobre la ejecución de procesos"
                            + "      en primer y segundo plano en sistemas operativos basados en UNIX (linux). Se recomienda leer"
                    + "             la documentación disponible en los enlaces de abajo antes de continuar. Pulsar \"Continuar con el test\" cuando estés preparad@."
                            + "Se recuerda, que la marcha atrás durante las preguntas se encuentra bloqueada (tanto en la interfaz como en el propio navegador)</p> "
                            + "<br><hr><br>"
                    + "         <input class=\"login-button\" type=\"submit\" value=\"Continuar con el test\">\n"
                            + "  <br><br><hr><br>"
                            + "<p class=\"login-head-text\"> Información pre-test</p>"
                            + "<br>"
                            + "<div style=\"text-align: center\"><a href=\"https://www.guia-ubuntu.com/index.php/Comandos\">Comandos Ubuntu</a></div><br>"
                            + "<div style=\"text-align: center\"><a href=\"http://www.jagar.es/unix/procesos.htm\">Procesos UNIX</a></div><br>"
                            + "<div style=\"text-align: center\"><a href=\"https://es.wikipedia.org/wiki/Ps_(Unix)\">Procesos UNIX (WikiPedia)</a></div>"
                    + "</form>\n"
                    + "        <br><br>\n"
                    + "        \n"
                    + "        \n"
                    + "        <div><footer id=\"autor-copy\" class=\"autor\">\n"
                    + "                <hr>\n"
                    + "                <br><br>\n"
                    + "                <div>David Gómez Pérez (Universidad de Málaga)</div>\n"
                    + "                <div>Copyright © 2021</div>\n"
                    + "                <br><br>\n"
                    + "        </footer></div>\n"
                    + "    </body>\n"
                    + "</html>\n");
            out.close();
        } else {
            response.sendRedirect("MainPageServlet");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
