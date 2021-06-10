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
@WebServlet(urlPatterns = {"/ProcessTest1"})
public class ProcessTest1 extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String userName = LoginManager.getLoginName(request);
        if (userName != null) {
            //miramos el número de fallos
            Integer ok_q = Integer.parseInt(LoginManager.getCorrectAnswers(request));
            //número de fallos (son 5 preguntas). Las preguntas sin contestar o saltadas
            //se consideran incorrectas
            double fails = 5 - ok_q;
            double degree = ((double) ok_q / 5) * 100;

            String msg = "";
            if (degree <= 33) {
                msg += "<p>Su calificación ha sido del <font color=\"red\"><b>" + degree + "%</b></font></p>";
                msg += "<p>Se recomienda <font color=\"red\"><b>NO</b></font> presentarse al exámen</p> ";
            } else if (degree > 33 && degree <= 66) {
                msg += "<p>Su calificación ha sido del <font color=\"orange\"><b>" + degree + "%</b></font></p>";
                msg += "<p>Se recomienda <font color=\"orange\"><b>ESTUDIAR MÁS</b></font> para presentarse al exámen</p> ";
            } else if (degree > 66) {
                msg += "<p>Su calificación ha sido del <font color=\"#6DC36D\"><b>" + degree + "%</b></font></p>";
                msg += "<p>Se recomienda <font color=\"#6DC36D\"><b>PRESENTARSE</b></font> al exámen</p> ";
                msg += "<p>Buena suerte " + userName + "</p>";
            }

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
                    + "        <script type=\"text/javascript\" src=\"./Scripts/noBackScript.js\"></script>\n"
                    + "        <link href=\"./Styles/mainStyle.css\" rel=\"stylesheet\" type=\"text/css\">\n"
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
                    + "                                   style=\"background-color: #ccccff;\"\n"
                    + "                                   disabled=\"true\">\n"
                    + "                        </td>\n"
                    + "                    </tr>\n"
                    + "                    <tr>\n"
                    + "                        <!--PREGUNTA 5 -->\n"
                    + "                        <td align=\"center\">\n"
                    + "                            <input type=\"button\" class=\"login-button\" value=\"5\"\n"
                    + "                                   onclick=\"location.href = '#'\"\n"
                    + "                                   style=\"background-color: #ccccff;\"\n"
                    + "                                   disabled=\"true\">\n"
                    + "                        </td>\n"
                    + "                    </tr>\n"
                    + "                </table>\n"
                    + "        <hr><br>\n"
                    + "         <p><font color=\"white\"><b>Nº aciertos:</b></font> <font color=\"#6DC36D\"><b>" + ok_q + "</b></font></p>\n "
                    + "         <p><font color=\"white\"><b>Nº fallos:</b></font> <font color=\"red\"><b>" + (5 - (ok_q)) + "</b></font></p>\n "
                    + "\n"
                    + "\n"
                    + "            </ul>\n"
                    + "        </nav>\n"
                    + "        \n"
                    + "        <!--PREGUNTA 1-->\n"
                    + "         <form class=\"login-form\">\n"
                    + "            <p class=\"login-head\">CALIFICACIÓN FINAL</p>\n"
                    + "            <br>\n"
                    + "            <hr>\n"
                    + "            <br>\n" + msg + "<br><hr><br>"
                    + "         </form>\n"
                    + "            <form class=\"login-form\" action=\"MainPageServlet\">"
                    + "                 <input class=\"login-button\" type=\"submit\" value=\"Página Principal\">"
                    + "             </form>"
                    + "             <form class=\"login-form\" action=\"ProcessLogoutServlet\">"
                    + "                 <input class=\"login-button\" type=\"submit\" value=\"Logout\" >\n"
                    + "             </form>"
                    + "    </body>\n"
                    + "</html>");
            out.close();
            LoginManager.resetCorrectAnswers(request);
        } else {
            response.sendRedirect("MainPageServlet");
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

}
