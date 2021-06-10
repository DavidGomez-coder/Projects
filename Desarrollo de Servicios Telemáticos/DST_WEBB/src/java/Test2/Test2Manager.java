/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test2;

import Test1.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dgpv2
 */
public class Test2Manager {

    private static Map<String, String> questions;
    
    private Test2Manager() {
        
    }
    
    public static final void generateAnswers(){
        questions = new HashMap<>();
        //registramos las preguntas
        questions.put("1", "SI");
        questions.put("2", "SI");
        questions.put("3", "NO");
        questions.put("4", "NO");
        questions.put("5", "SI");
    }
    
    public static final boolean isCorrect(String questionNum, String user_response){
        if (questions.containsKey(questionNum)){
            return questions.get(questionNum).equals(user_response);
        }else{
            return false;
        }
    }
    
    public static final String getCorrectAnswer (String q){
        return questions.get(q);
    }
}
