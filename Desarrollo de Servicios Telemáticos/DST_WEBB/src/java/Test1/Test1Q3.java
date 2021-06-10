/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test1;

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
@WebServlet(name = "Test1Q3", urlPatterns = {"/Test1Q3"})
public class Test1Q3 extends HttpServlet {

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
        //obtenemos nombre del usuario
        String userName = LoginManager.getLoginName(request);
        if (userName != null) {
            //iniciamos las respuestas
            String ok_q = LoginManager.getCorrectAnswers(request);
            //generamos la página
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>\n"
                    + "<!--\n"
                    + "To change this license header, choose License Headers in Project Properties.\n"
                    + "To change this template file, choose Tools | Templates\n"
                    + "and open the template in the editor.\n"
                    + "-->\n"
                    + "<html>\n"
                    + "    <head>\n"
                    + "        <title>TEST 1</title>\n"
                    + "        <meta charset=\"UTF-8\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "        <link href=\"./Styles/mainStyle.css\" rel=\"stylesheet\" type=\"text/css\">\n"
                    + "        <script type=\"text/javascript\" src=\"./Scripts/noBackScript.js\"></script>\n"
                    + "    </head>\n"
                    + "    <body onload=\"noBack()\">\n"
                    + "        <nav>\n"
                    + "            <ul>\n"
                    + "                <li><br></li>\n"
                    + "                <li><p style=\"text-align: center; color:white;\">TEST1-" + userName + "</p></li>\n"
                    + "                <li><br></li>\n"
                    + "                <hr>\n"
                    + "                <br>\n"
                    + "                <li><p class=\"login-head-text\">Preguntas</p></li>\n"
                    + "                <br>\n"
                    + "                <!-- PREGUNTAS -->\n"
                    + "                <table cellspacing=\"10\" cellpading=\"10\" border=\"3\">\n"
                    + "                    <tr>\n"
                    + "                        <!--PREGUNTA 1 -->\n"
                    + "                        <td align=\"center\">\n"
                    + "                            <input type=\"button\" class=\"login-button\" value=\"1\"\n"
                    + "                                   onclick=\"location.href = '#'\"\n"
                    + "                                   style=\"background-color: #ccccff;\"\n"
                    + "                                   disabled=\"true\">\n"
                    + "                        </td>\n"
                    + "                        <!--PREGUNTA 2 -->\n"
                    + "                        <td align=\"center\">\n"
                    + "                            <input type=\"button\" class=\"login-button\" value=\"2\"\n"
                    + "                                   onclick=\"location.href = '#'\"\n"
                    + "                                   style=\"background-color: #ccccff;\"\n"
                    + "                                   disabled=\"true\">\n"
                    + "                        </td>\n"
                    + "                    </tr>\n"
                    + "                    <tr>\n"
                    + "                        <!--PREGUNTA 3 -->\n"
                    + "                        <td align=\"center\">\n"
                    + "                            <input type=\"button\" class=\"login-button\" value=\"3\"\n"
                    + "                                   onclick=\"location.href = '#'\"\n"
                    + "                                   style=\"background-color: #ccccff;\"\n"
                    + "                                   disabled=\"true\">\n"
                    + "                        </td>\n"
                    + "                        <!--PREGUNTA 4 -->\n"
                    + "                        <td align=\"center\">\n"
                    + "                            <input type=\"button\" class=\"login-button\" value=\"4\"\n"
                    + "                                   onclick=\"location.href = '#'\"\n"
                    + "                                   disabled=\"true\">\n"
                    + "                        </td>\n"
                    + "                    </tr>\n"
                    + "                    <tr>\n"
                    + "                        <!--PREGUNTA 5 -->\n"
                    + "                        <td align=\"center\">\n"
                    + "                            <input type=\"button\" class=\"login-button\" value=\"5\"\n"
                    + "                                   onclick=\"location.href = '#'\"\n"
                    + "                                   disabled=\"true\">\n"
                    + "                        </td>\n"
                    + "                    </tr>\n"
                    + "                </table>\n"
                    + "        <hr><br>\n"
                    + "         <font color=\"white\"><b>Nº aciertos:</b></font> <font color=\"#6DC36D\"><b>" + ok_q + "</b></font>\n "
                    + "\n"
                    + "                <li><input type=\"button\" class=\"login-button\" value=\"TERMINAR TEST\" \n"
                    + "                           onclick=\"location.href = 'ProcessTest1'\"\n"
                    + "                           style=\"position: absolute;  bottom: 50px;\"></li>\n"
                    + "\n"
                    + "            </ul>\n"
                    + "        </nav>\n"
                    + "        \n"
                    + "        <!--PREGUNTA 3-->\n"
                    + "        <form class=\"login-form\" action=\"ProcessQuestion3\"\n"
                    + " name=\"T1Form\" value=\"T1Q3\" method=\"post\">\n"
                    + "            <p class=\"login-head\">PREGUNTA 3</p>\n"
                    + "            <br>\n"
                    + "            <p class=\"login-head-text\">\n"
                    + "                Escoge la respuesta correcta. Al ejecutar el comando"
                    + "        \"gcc programa.c -o programa\" para compilar ficheros en C,"
                    + "        este se compilará y se creará otro fichero \"programa\" que "
                    + "        se podrá ejecutar.</p>\n"
                    + "            <br>\n"
                    + "            <hr>\n"
                    + "            <br>\n"
                    + "            <div>\n"
                    + "                <label><input type=\"radio\" name=\"T1Q3\" value=\"SI\"> a) SI</label><br>\n"
                    + "                <label><input type=\"radio\" name=\"T1Q3\" value=\"NO\"> b) NO</label><br>\n"
                    + "                <label><input type=\"radio\" name=\"T1Q3\" value=\"A VECES\"> c) A VECES</label><br>\n"
                    + "            </div>\n"
                    + "            <br>\n"
                    + "            <input class=\"login-button\" type=\"submit\" value=\"Contestar pregunta\">\n"
                    + "        </form>\n"
                    + "    </body>\n"
                    + "</html>");
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
