package login;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import Test1.Test1Manager;
import Test2.Test2Manager;
import Test3.Test3Manager;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "MainPageServlet", urlPatterns = {"/MainPageServlet"})
public class MainPageServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String loginName = LoginManager.getLoginName(request);

        Map erros = new HashMap();
        if (loginName != null) { // El usuario ya se ha autentificado.
            //reseteamos valores
            LoginManager.resetCorrectAnswers(request);
            generateMainPage(response, loginName);
        } else { // El usuario no se ha autentificado.
            response.sendRedirect("ShowLoginServlet");
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }

    private void generateMainPage(HttpServletResponse response, String loginName) throws IOException {

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
                + "        <title>Página principal</title>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "        <link rel=\"stylesheet\" href=\"./Styles/mainStyle.css\">\n"
                + "    </head>\n"
                + "\n"
                + "    <body>\n"
                + "        <nav>\n"
                + "            <ul>\n"
                + "                <li><p class=\"login-head-text\">Menú</p></li>\n"
                + "                <li><p class=\"login-head-text\"><script>document.write(document.lastModified)</script></p></li>"
                + "                <li><div style=\"text-align: center; position: absolute; left:15%;  bottom: 5%;\" ><a href=\"#autor-copy\">Autor de la página</a></div></li>\n"
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
                + "                <li><div style=\"text-align: center\"><a href=\"Test2Main\">Test 2</a></div></li>\n"
                + "                <br>\n"
                + "                <li><div style=\"text-align: center\"><a href=\"Test3Main\">Test 3</a></div></li>\n"
                + "            </ul>\n"
                + "\n"
                + "        </nav>\n"
                + "        <br>\n"
                + "        <h1 style=\"text-align: center; color: white; text-decoration: underline\">SISTEMA TUTOR INFORMÁTICA</h1>\n"
                + "        <br><br>\n"
                + "        <p style=\"text-align: center; color:white\">En esta página se evaluarán tus conocimientos sobre informática. Para ello, en el menú de la <br>"
                + "izquierda se encuentran los distintos tests disponibles. Antes de comenzar a realizarlos, se abrirá una pestaña con información relativa <br>"
                + "al test (donde también se incluirán algunos enlaces para preparar el test)<br>, y luego podrá volver a la página principal o realizar dicho test. <font><b>Buena suerte " + loginName + "!!!</b></font></p>\n"
                + "        <br><br>\n"
                + "        <div align=\"center\"><img src=\"./Images/mainPagePict.jpg\"></div>\n"
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
    }
}
