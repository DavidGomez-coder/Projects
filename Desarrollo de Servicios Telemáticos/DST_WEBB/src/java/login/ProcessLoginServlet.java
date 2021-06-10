package login;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lidia Fuentes
 */
public class ProcessLoginServlet extends HttpServlet {

    private static final String USERS_FILE_PATH = "users.txt";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String loginName = request.getParameter("loginName");
        String loginPass = request.getParameter("loginPass");
        if (parametersAreCorrect(loginName, loginPass)) {
            LoginManager.login(request, loginName.trim());
            response.sendRedirect(response.encodeURL("MainPageServlet"));
        } else {
            Map errors = new HashMap();
            errors.put("loginError", "Usuario/Contraseña incorrectos");
            request.setAttribute("errors", errors);
            forwardToShowLogin(request, response);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    private boolean parametersAreCorrect(String loginName, String loginPass) {
        boolean isRegister = false;
        String canonical_path = "";
        try {
            canonical_path = new java.io.File(".").getCanonicalPath();
        } catch (IOException io) {

        }
        try (Scanner sc = new Scanner(new File(canonical_path + "/" + USERS_FILE_PATH))) {
            //leemos de línea en línea
            try {
                while (sc.hasNextLine() && !isRegister) {
                    String line = sc.nextLine();
                    //hacemos un scanner sobre la linea
                    Scanner scline = new Scanner(line);
                    scline.useDelimiter("[ ]+");
                    String userName = scline.next();
                    String userPass = scline.next();
                    if (userName.equals(loginName) && userPass.equals(loginPass)) {
                        isRegister = true;
                        System.out.println("Usr: " + userName + ", Pswd: " + userPass);
                    }
                }
            } catch (Exception e) {
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProcessLoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            //creamos el fichero si este no está
            File f = new File(canonical_path + "/" + USERS_FILE_PATH);
        }
        return isRegister;
    }

    private void forwardToShowLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/ShowLoginServlet");

        requestDispatcher.forward(request, response);
    }
}
