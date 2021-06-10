/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test3;

import Test2.*;
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
@WebServlet(name = "ProcessQuestion4T3", urlPatterns = {"/ProcessQuestion4T3"})
public class ProcessQuestion4T3 extends HttpServlet {

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
        String userName = LoginManager.getLoginName(request);
        if (userName != null) {
            String user_answer = request.getParameter("T3Q4");
            System.out.println("RESPUESTA DEL USUARIO P4: " + user_answer);
            String correct_mess;
            System.out.println(user_answer);
            if (!Test3Manager.isCorrect("4", user_answer)) {
                System.out.println("Respuesta incorrecta");
                correct_mess = "<font color=\"red\"><b>Respuesta incorrecta</b></font>. La respuesta correcta era: "
                        + Test3Manager.getCorrectAnswer("4");
            } else {
                String n = String.valueOf(Integer.parseInt(LoginManager.getCorrectAnswers(request)) + 1);
                LoginManager.setCorrectAnswers(request, n);
                correct_mess = "<font color=\"#6DC36D\"><b>Respuesta Correcta</b></font>";
            }
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
                    + "        <title>TEST 3</title>\n"
                    + "        <meta charset=\"UTF-8\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "        <script type=\"text/javascript\" src=\"./Scripts/noBackScript.js\"></script>\n"
                    + "        <link href=\"./Styles/mainStyle.css\" rel=\"stylesheet\" type=\"text/css\">\n"
                    + "    </head>\n"
                    + "    <body onload=\"noBack()\">\n"
                    + "        <nav>\n"
                    + "            <ul>\n"
                    + "                <li><br></li>\n"
                    + "                <li><p style=\"text-align: center; color:white;\">TEST3-" + userName + "</p></li>\n"
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
                    + "                                   style=\"background-color: #ccccff;\"\n"
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
                    + "                           onclick=\"location.href = 'ProcessTest3'\"\n"
                    + "                           style=\"position: absolute;  bottom: 50px;\"></li>\n"
                    + "\n"
                    + "            </ul>\n"
                    + "        </nav>\n"
                    + "        \n"
                    + "        <!--PREGUNTA 4-->\n"
                    + "        <form class=\"login-form\" action=\"Test3Q5\" method=\"post\">\n"
                    + "            <p class=\"login-head\">PREGUNTA 4</p>\n"
                    + "            <br>\n"
                    + "            <p class=\"login-head-text\">\n"
                    + "                Escoge la respuesta correcta. La sentencia \"nslookup dominio\" nos permite enviar "
                            + "       un mensaje al dominio especificado</p>\n"
                    + "            <br>\n"
                    + "            <hr>\n"
                    + "            <br>\n"
                    + "            <p>" + correct_mess + "</p>"
                    + "            <br>\n"
                    + "            <input class=\"login-button\" type=\"submit\" value=\"Siguiente pregunta\">\n"
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
