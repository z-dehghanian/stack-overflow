
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Your Name < zahra dehghanian>
 */
public class Answer implements Serializable{
    String answer;
    String u;
    int mark;
    int updatedQuestion;
    ArrayList <Comment> comment;
    ArrayList <String> history;
    public Answer (String answer,String u,int mark,ArrayList <Comment> comment,int uq){
	this.comment = comment;
	this.answer = answer ;
	this.u = u;
	this.answer = answer;
	this.mark = mark;
        this.updatedQuestion = uq;
    }
}
