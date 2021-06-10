package login;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lidia Fuentes
 */
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class ShowLoginServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String loginName = "";

        // Gestion de login=null
        String autenticationError = "";
        Map errors = (Map) request.getAttribute("errors");
        if (errors != null) {
            String errorHeader = "<font color=\"red\"><b>";
            String errorFooter = "</b></font>";
            if (errors.containsKey("loginError")) {
                autenticationError = errorHeader
                        + errors.get("loginError") + errorFooter;
            }
        }


        /* Generar respuesta. */
        response.setContentType("text/html; charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        /* Inicio. */
        out.println("<html>\n"
                + "    <head>\n"
                + "        <title>Inicio de Sesión</title>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "        <link rel=\"stylesheet\" href=\"./Styles/mainStyle.css\">\n"
                + "    </head>\n"
                + "    <body>\n"
                + "\n"
                + "        <form class=\"login-form\" method=\"post\" action=\"ProcessLoginServlet\">\n"
                + "            <p class = \"login-head\"> INICIAR SESIÓN </p> <br>\n"
                + "            <!-- PROCESAMOS EL NOMBRE DE USUARIO Y LA CONTRASEÑA PARA INICIAR SESIÓN -->\n"
                + "            <!-- NOMBRE DE USUARIO -->\n"
                + "            <input required class=\"login-text\" type=\"text\" name=\"loginName\" placeholder=\"Usuario\"\n"
                + "                   autocomplete=\"off\" maxlength=\"16\" minlength=\"1\">\n"
                + "            <br>\n"
                + "            <!-- CONTRASEÑA -->\n"
                + "            <input required class=\"login-text\" type=\"password\" name=\"loginPass\" placeholder=\"Contraseña\"\n"
                + "                   autocomplete=\"off\" maxlength=\"16\" minlength=\"5\">\n"
                + "            <br>\n"
                + "             <p style=\"text-align: center\">" + autenticationError + "</p>\n"
                + "            <br> <br>\n"
                + "            <input class=\"login-button\" type=\"submit\" value=\"INICIAR SESIÓN\">\n"
                + "            <br> <br>\n"
                + "            <input class=\"login-button\" type=\"button\" onclick=\"location.href = 'ShowRegisterForm'\" value=\"NUEVO USUARIO\">\n"
                + "        </form> \n"
                + "           <div>\n"
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
        /* Cerrar el descriptor de salida". */
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
