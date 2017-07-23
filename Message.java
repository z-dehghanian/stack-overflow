/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.*;

public class Message implements Serializable {

    static final int SEARCH = 0, LOGIN = 1, LOGOUT = 2, REGISTER = 3, ADD = 4, GETKEYWORDS = 5,SEARCHFORADD=6,ADDCOMMENTTOANSWER=7,ADDANS=8,UPDATECOMMENT=9,UPDATEANS=10,CHENAGEUSER=11,CHANGEPASS=12,ADDCOMMENTTOQUESTION = 13,UPDATEQ = 14,SUB2Q = 15 ,SUB1Q = 16,ADD2Q = 17,ADD1Q = 18,UPDATEQUESTION = 19,DELETEQ = 20,DELETEA = 21,DELETEC = 22,SHOWALL = 23;
    private int type;
    private String message;
    Object messageObject ;

    // constructor1
    Message(int type, String message) {
        this.type = type;
        this.message = message;
    }
     Message(int type, Object message) {
        this.type = type;
        this.messageObject = message;
    }


    // getters
    int getType() {
        return type;
    }
    String getMessage() {
        return message;
    }
}

