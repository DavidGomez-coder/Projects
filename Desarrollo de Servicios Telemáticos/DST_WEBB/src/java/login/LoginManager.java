package login;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Lidia Fuentes
 */
import java.io.IOException;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;

public final class LoginManager {

    private final static String LOGIN_NAME_ATTRIBUTE = "loginName";
    private final static String C_ANSW_ATTRIBUTE = "0";

    private LoginManager() {
    }

    /* MÉTODO PARA INICIAR SESIÓN */
    public final static void login(HttpServletRequest request, String loginName) {
        HttpSession session = request.getSession(true);
        session.setAttribute(LOGIN_NAME_ATTRIBUTE, loginName);
        session.setAttribute(C_ANSW_ATTRIBUTE, "0");
        System.out.println("USUARIO: " + loginName + " ha iniciado sesión");
    }
    
    /* MÉTODO PARA CERRAR SESIÓN */
    public final static void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute(LOGIN_NAME_ATTRIBUTE, null);
            session.invalidate();
        }
    }

    /* MÉTODOS CORRESPONDIENTES PARA LAS RESPUESTAS CORRECTAS */
    public final static String getCorrectAnswers(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        } else {
            return (String) session.getAttribute(C_ANSW_ATTRIBUTE);
        }
    }

    public final static void setCorrectAnswers(HttpServletRequest request, String n) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute(C_ANSW_ATTRIBUTE, n);
        }
    }
    
    public final static void resetCorrectAnswers (HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session!=null){
            session.setAttribute(C_ANSW_ATTRIBUTE, "0");
        }
    }
    
    /* MÉTODO PARA OBTENER EL NOMBRE DE USUARIO */
    public final static String getLoginName(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        } else {
            return (String) session.getAttribute(LOGIN_NAME_ATTRIBUTE);
        }
    }
}
