
import java.io.Serializable;

/**
 *
 * @author Your Name < zahra dehghanian>
 */
public class Comment implements Serializable{
    String comment;
    String u;
    int mark;
    public Comment (String comment,String u,int mark){
	this.u = u;
	this.comment = comment;
	this.mark = mark;
    }
}
