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
@WebServlet(name = "ProcessQuestion2", urlPatterns = {"/ProcessQuestion2"})
public class ProcessQuestion2 extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String userName = LoginManager.getLoginName(request);
        if (userName != null) {
            String user_answer = request.getParameter("T1Q2");
            System.out.println("RESPUESTA DEL USUARIO P2: " + user_answer);
            String correct_mess;
            System.out.println(user_answer);
            if (!Test1Manager.isCorrect("2", user_answer)) {
                System.out.println("Respuesta incorrecta");
                correct_mess = "<font color=\"red\"><b>Respuesta incorrecta</b></font>. La respuesta correcta era: "
                        + Test1Manager.getCorrectAnswer("2");
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
                    + "        <!--PREGUNTA 2-->\n"
                    + "        <form class=\"login-form\" action=\"Test1Q3\" method=\"post\">\n"
                    + "            <p class=\"login-head\">PREGUNTA 2</p>\n"
                    + "            <br>\n"
                    + "            <p class=\"login-head-text\">\n"
                    + "                Escoge la respuesta correcta. ¿El comando \"pip [nombre_fichero]\""
                    + "        se utiliza para compilar y ejecutar ficheros en el lenguaje "
                    + "        python?</p>\n"
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

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }
}
