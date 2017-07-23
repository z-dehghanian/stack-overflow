
import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {

    String content;
    ArrayList<String> keywords;
    ArrayList<Comment> comment;
    ArrayList<Answer> answer;
    int mark;
    String q,u ;
    
    String writer;
    int id;
    
    public Question(String content, String keyword, String writer,
	    ArrayList<Comment> comment, ArrayList<Answer> answer, int mark) {
	// TODO Auto-generated constructor stub
	keywords = new ArrayList<>();
	String str[] = null;
	if (keyword != null) {
	    str = keyword.split(",");
	    for (int i = 0; i < str.length; i++) {
		keywords.add(str[i]);
	    }
	}
	this.answer = answer;
	this.content = content;
	this.writer = writer;
	this.id = id;

    }

    Question(String questionResult) {
	splitString(questionResult);

    }

    public void splitString(String questionResult) {

    }
}
