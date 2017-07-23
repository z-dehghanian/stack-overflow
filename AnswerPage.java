



import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AnswerPage extends JPanel implements ActionListener {

    Dimension d;
    JFrame j;
    JButton search, save;
    JTextField question, ans,num;
    private int b,type;
    private int m = 100;
    private int i;
    private int n;
    private Userz u;
    private Client c;
    public String[] interest = new String[5];
    //JTextArea ja;
    JCheckBox java, C, SQL, Verilog, VHDL;
    ArrayList<Question> q;
    public AnswerPage (Client client,ArrayList<Question> q,int type) {
	//u = user;
	this.q = q;
        this.type = type;
	c = client;
	d = Toolkit.getDefaultToolkit().getScreenSize();
	j = new JFrame();
	setSize(800, 560);
	j.setSize(800, 560);
	setLocation(0, 0);
	j.setLocation(90, 0);
	setLayout(null);
	j.setLayout(null);
	setFocusable(true);

	System.out.println(type);
	save = new JButton("save");
	save.setSize(100, 30);
	save.setLocation(680, 200);

	question = new JTextField();
	question.setSize(300, 50);
	question.setLocation(10, 200);

	ans = new JTextField();
	ans.setSize(300, 50);
	ans.setLocation(10, 280);
	if(type == 6 || type == 7 || type == 8){}
	else
	    this.add(ans);
	
        num = new JTextField();
	num.setSize(300, 50);
	num.setLocation(10, 120);
	if(type == 1 || type == 4 || type == 5 || type == 6){}
	    
	else    
	    this.add(num);
	
	this.add(save);
	save.addActionListener(this);
		
	//this.add(search);
	this.add(question);
	j.getContentPane().add(this);
		setVisible(true);
		j.setVisible(true);
	
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
	if (arg0.getSource() == save) {
            if(type == 1){ // add ans   
	    //Question q = new Question(question.getText(),keyword.getText(),u.username,null,null,null);
                Question qu = q.get(Integer.parseInt(question.getText()));
                ArrayList <Comment> ac = new ArrayList <>();
                if(qu.answer == null)
                    qu.answer = new ArrayList<>();
                qu.answer.add(new Answer (ans.getText(),c.u.username,0,ac,0)); 
               
            //String s = new String (q.get(Integer.parseInt(question.getText())).content+"*"+ans.getText()+"*"+c.u);
                System.out.println(q.get(Integer.parseInt(question.getText())).content.length());
	    
                c.sendMessage(new Message(Message.ADDANS, qu));
            }
            if(type == 2){  //update ans
	    //Question q = new Question(question.getText(),keyword.getText(),u.username,null,null,null);
                Question qu = q.get(Integer.parseInt(question.getText()));
                ArrayList <Comment> ac = new ArrayList <>();
                if(qu.answer == null)
                    qu.answer = new ArrayList<>();
                 qu.answer.add(new Answer (ans.getText(),c.u.username,0,ac,Integer.parseInt(num.getText()))); 
	    
            //String s = new String (q.get(Integer.parseInt(question.getText())).content+"*"+ans.getText()+"*"+c.u);
                System.out.println(qu.content.length());
	    
                c.sendMessage(new Message(Message.UPDATEANS, qu));
            }
	    if(type == 3){  //update ans
	    //Question q = new Question(question.getText(),keyword.getText(),u.username,null,null,null);
                Question qu = q.get(Integer.parseInt(question.getText()));
               // ArrayList <Comment> ac = new ArrayList <>();
                if(qu.answer == null)
                    qu.answer = new ArrayList<>();
		Answer a = qu.answer.get(Integer.parseInt(num.getText()));
		qu.answer.remove(a);
		qu.answer.add(a);
                qu.answer.get(qu.answer.size() - 1).comment.add(new Comment (ans.getText(),c.u.username,0));
	        c.sendMessage(new Message(Message.ADDCOMMENTTOANSWER, qu));
            }
	    if(type == 4){  //COMMENT Q
	        Question qu = q.get(Integer.parseInt(question.getText()));
                if(qu.comment == null)
                    qu.comment = new ArrayList<>();
		qu.comment.add(new Comment (ans.getText(),c.u.username,0));    
                c.sendMessage(new Message(Message.ADDCOMMENTTOQUESTION, qu));
            }
	    if(type == 5){  //update Q
	        Question qu = q.get(Integer.parseInt(question.getText()));
		qu.u = c.u.username;
                qu.q = ans.getText();
		c.sendMessage(new Message(Message.UPDATEQ, qu));
            
            }
	    if(type == 6){  //DELETE Q
	        Question qu = q.get(Integer.parseInt(question.getText()));
		qu.u = c.u.username;
                //qu.q = ans.getText();
		c.sendMessage(new Message(Message.DELETEQ, qu));
            
            }
	    if(type == 7){  //DELETE C
	        Question qu = q.get(Integer.parseInt(question.getText()));
		Comment toBeDeleted;
		if(qu.comment != null){
		    toBeDeleted = qu.comment.get(Integer.parseInt(num.getText()));
		    qu.comment.remove(toBeDeleted);
		    qu.comment.add(toBeDeleted);
		}
		qu.u = c.u.username;
                //qu.q = ans.getText();
		c.sendMessage(new Message(Message.DELETEC, qu));
            
            }
	    
	    if(type == 8){  //DELETE A
	        Question qu = q.get(Integer.parseInt(question.getText()));
		Answer toBeDeleted = qu.answer.get(Integer.parseInt(num.getText()));
		qu.answer.remove(toBeDeleted);
		qu.answer.add(toBeDeleted);
		
		qu.u = c.u.username;
                //qu.q = ans.getText();
		c.sendMessage(new Message(Message.DELETEA, qu));
            
            }
	}
    }

}
    

