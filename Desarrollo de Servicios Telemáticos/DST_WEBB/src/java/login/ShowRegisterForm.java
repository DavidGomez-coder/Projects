package login;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dgpv2
 */
@WebServlet(urlPatterns = {"/ShowRegisterForm"})
public class ShowRegisterForm extends HttpServlet {

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
        /* Para los errores */
        String loginNameError = "";
        Map errors = (Map) request.getAttribute("errors");
        if (errors != null) {
            String errorHeader = "<font color=\"red\"><b>";
            String errorFooter = "</b></font>";
            if (errors.containsKey("userName")) {
                loginNameError = errorHeader
                        + errors.get("userName") + errorFooter;
            }
        }
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>\n"
                    + "    <head>\n"
                    + "        <title>Registro</title>\n"
                    + "        <meta charset=\"UTF-8\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "        <link href=\"./Styles/mainStyle.css\" rel=\"stylesheet\" type=\"text/css\">\n"
                    + "        <script type=\"text/javascript\" src=\"./Scripts/registerVal.js\"></script>\n"
                    + "    </head>\n"
                    + "\n"
                    + "    \n"
                    + "    <body>\n"
                    + "        <form class=\"login-form\" method=\"post\" action=\"ProcessRegisterServlet\" name=\"registerForm\" onsubmit=\"return isValid()\">\n"
                    + "            <p class=\"login-head\">NUEVO USUARIO</p>\n"
                    + "            <br>\n"
                    + "            <p class=\"login-head-text\">Datos generales del usuario</p><br>\n"
                    + "            <input required class=\"login-text\" type=\"text\" name=\"userMail\"\n"
                    + "                   placeholder=\"Dirección email *\" autocomplete=\"off\" maxlength=\"35\">\n"
                    + "            <br>\n"
                    + "            <input required class=\"login-text\" type=\"text\" name=\"userName\" \n"
                    + "                   placeholder=\"Nombre de usuario *\" autocomplete=\"off\" maxlength=\"16\" minlength=\"1\">\n"
                    + "            <p style=\"text-align: center\">" + loginNameError + "</p>"
                    + "            <br>\n"
                    + "            <input class=\"login-text\" type=\"text\" name=\"userFirstName\"\n"
                    + "                   placeholder=\"Apellido\" autocomplete=\"off\" maxlength=\"16\">\n"
                    + "            <br>\n"
                    + "            <input required class=\"login-text\" type=\"password\" name=\"userPass\"\n"
                    + "                   placeholder=\"Contraseña *\" autocomplete=\"off\" maxlength=\"16\"\n"
                    + "                   minlength=\"5\">\n"
                    + "            <br>\n"
                    + "            <input required class=\"login-text\" type=\"password\" name=\"userPassConf\"\n"
                    + "                   placeholder=\"Repita la contraseña *\" autocomplete=\"off\" maxlength=\"16\"\n"
                    + "                   minlength=\"5\">\n"
                    + "            <br>\n"
                    + "            <p class=\"login-head-text\"> Palabra clave para recuperar contraseña </p><br>\n"
                    + "            <input required class=\"login-text\" type=\"text\" name=\"passRecover\" \n"
                    + "                   placeholder=\"Clave *\" autocomplete=\"off\" maxlength=\"16\"\n"
                    + "                   minlength=\"4\">\n"
                    + "            <br>\n"
                    + "            <p class=\"login-head-text\">Fecha de nacimiento</p><br>\n"
                    + "            <input required class=\"login-date\" type=\"date\" name=\"userBirth\">\n"
                    + "            <br><br>\n"
                    + "            <input class=\"login-button\" type=\"submit\" value=\"REGISTRAR\">\n"
                    + "            <br> <br>\n"
                    + "            <input class=\"login-button\" type=\"button\" value=\"VOLVER\"\n"
                    + "                   onclick=\"location.href = 'MainPageServlet'\">\n"
                    + "            <br><br>\n <p class=\"login-head-text\">* No hay que ser mayor de edad para registrarse</p><br>\n"                                                      
                    + "        </form>\n"
                    + "        <div>\n"
                    + "            <footer class=\"autor\">\n"
                    + "                <hr>\n"
                    + "                <br><br>\n"
                    + "                <div>David Gómez Pérez (Universidad de Málaga)</div>\n"
                    + "                <div>Copyright © 2021</div>\n"
                    + "                <br><br>\n"
                    + "            </footer>\n"
                    + "        </div>\n"
                    + "    </body>\n"
                    + "</html>");
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
