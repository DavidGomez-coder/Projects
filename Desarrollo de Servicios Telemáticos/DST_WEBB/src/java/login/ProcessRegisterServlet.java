package login;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = {"/ProcessRegisterServlet"})
public class ProcessRegisterServlet extends HttpServlet {
private static final String USERS_FILE_PATH = "users.txt";  
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        String userName = request.getParameter("userName");
        String userPass = request.getParameter("userPass");
        if (!isRegister(userName)){
            newUserRegister(userName, userPass);
            response.sendRedirect("MainPageServlet");
        }else{
              Map errors = new HashMap();
              errors.put("userName", "Nombre de usuario no disponible");
              request.setAttribute("errors", errors);
              forwardToShowForm(request, response);
        }
        

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }
    
    private void newUserRegister (String userName, String userPass){
        String canonical_path = "";
        try {
            canonical_path = new java.io.File(".").getCanonicalPath();
            System.out.println("Registrando nuevo usuario en: " + canonical_path+"/"+USERS_FILE_PATH);
            FileWriter fw = new FileWriter(canonical_path+"/"+USERS_FILE_PATH, true);
            fw.write(userName + " " + userPass + "\n");
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(ProcessRegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            //creamos un nuevo fichero con este nombre si este no existe
            File f = new File(canonical_path+"/"+USERS_FILE_PATH);
        }
        
    }
    
    private boolean isRegister(String loginName) {
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
                    if (userName.equals(loginName)) {
                        isRegister = true;
                        System.out.println("Usr: " + userName + ", Pswd: " + userPass);
                    }
                }
            } catch (Exception e) {
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProcessRegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            //creamos el fichero si este no está
            File f = new File(canonical_path+"/"+USERS_FILE_PATH);
        }
        return isRegister;
    }

    private void forwardToShowForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/ShowRegisterForm");
        requestDispatcher.forward(request, response);
    }

}
