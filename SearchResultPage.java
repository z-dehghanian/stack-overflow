
/**
 *
 * @author Your Name < zahra dehghanian>
 */
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;



public class SearchResultPage extends JPanel implements ActionListener {

    Dimension d;
    JFrame j;
    JButton answer, Acomment, Qcomment, updateQ, up, down, updateA,deleteA,deleteQ,deleteC;
    //JTextField question;
    private int b;
    private int m = 100;
    private int i;
    private int n;
    private Userz u;
    private Client c;
    public String[] interest = new String[5];
    JTextArea which, qac;
    ArrayList<Question> question;
    ArrayList<Boolean> isUp, isDown;

    public SearchResultPage(Client client, ArrayList<Question> question) {
	this.question = question;

	isUp = new ArrayList<>();
	isDown = new ArrayList<>();
	for (int i = 0; i < question.size(); i++) {
	    isUp.add(false);
	    isDown.add(false);
	}
	//u = user;
	c = client;
	d = Toolkit.getDefaultToolkit().getScreenSize();
	j = new JFrame();
	setSize(800, 800);
	j.setSize(700, 600);
	setLocation(0, 0);
	j.setLocation(90, 0);
	setLayout(null);
	j.setLayout(null);
	setFocusable(true);

	answer = new JButton("answer");
	answer.setSize(100, 30);
	answer.setLocation(570, 100);

	down = new JButton("dislike");
	down.setSize(100, 30);
	down.setLocation(200, 10);

	up = new JButton("like");
	up.setSize(100, 30);
	up.setLocation(50, 10);

	which = new JTextArea();
	which.setSize(100, 30);
	which.setLocation(350, 10);

	Acomment = new JButton("Acomment");
	Acomment.setSize(100, 30);
	Acomment.setLocation(570, 150);

	Qcomment = new JButton("Qcomment");
	Qcomment.setSize(100, 30);
	Qcomment.setLocation(570, 200);

	updateQ = new JButton("update q");
	updateQ.setSize(100, 30);
	updateQ.setLocation(570, 250);

	updateA = new JButton("update A");
	updateA.setSize(100, 30);
	updateA.setLocation(570, 300);
	
	deleteQ = new JButton("deleteQ");
	deleteQ.setSize(100, 30);
	deleteQ.setLocation(570, 350);

	deleteA = new JButton("deleteA");
	deleteA.setSize(100, 30);
	deleteA.setLocation(570, 400);

	deleteC = new JButton("deleteC");
	deleteC.setSize(100, 30);
	deleteC.setLocation(570, 450);

	qac = new JTextArea();
	qac.setSize(400, 400);
	qac.setLocation(50, 100);

	this.add(answer);
	this.add(Acomment);
	this.add(updateQ);
	this.add(Qcomment);
	this.add(updateA);
	this.add(qac);
	this.add(up);
	this.add(down);
	this.add(which);
	this.add(deleteQ);
	this.add(deleteC);
	this.add(deleteA);
	
	answer.addActionListener(this);
	Acomment.addActionListener(this);
	Qcomment.addActionListener(this);

	j.getContentPane().add(this);
	setVisible(true);
	j.setVisible(true);
	down.addActionListener(this);
	up.addActionListener(this);
	updateQ.addActionListener(this);
	updateA.addActionListener(this);
	deleteA.addActionListener(this);
	deleteC.addActionListener(this);
	deleteQ.addActionListener(this);
	
	//SQL.addActionListener(this);
	//Verilog.addActionListener(this);
	//VHDL.addActionListener(this);
	showQ();
    }

    public void showQ() {

	for (int i = 0; i < question.size(); i++) {
	    qac.append(question.get(i).content + "   ");
	    qac.append(question.get(i).mark + "");
	    //System.out.println(question.get(i).content+"   ");
	    if (question.get(i).answer != null) {
		for (int j = 0; j < question.get(i).answer.size(); j++) {
		    //System.out.println(question.get(i).answer.get(j)+" /  ");
		    qac.append(question.get(i).answer.get(j).answer + " /  ");

		}
	    }
	    if (question.get(i).comment != null) {
		for (int j = 0; j < question.get(i).comment.size(); j++) {
		    qac.append(question.get(i).comment.get(j).comment + " /  ");

		}
	    }
	    
	    

	}
	
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
	AnswerPage a;
	if (arg0.getSource() == answer) {
	    if (c.u != null && c.u.isLogin) {
		a = new AnswerPage(c, question, 1);
	    } else {
		JOptionPane.showMessageDialog(null, "plz sign in and try again");
	    }
	}

	if (arg0.getSource() == updateA) {
	    if (c.u != null && c.u.isLogin) {
		a = new AnswerPage(c, question, 2);
	    } else {
		JOptionPane.showMessageDialog(null, "plz sign in and try again");
	    }
	}

	if (arg0.getSource() == Acomment) {
	    if (c.u != null && c.u.isLogin) {
		a = new AnswerPage(c, question, 3);
	    } else {
		JOptionPane.showMessageDialog(null, "plz sign in and try again");
	    }
	}

	if (arg0.getSource() == Qcomment) {
	    if (c.u != null && c.u.isLogin) {
		a = new AnswerPage(c, question, 4);
	    } else {
		JOptionPane.showMessageDialog(null, "plz sign in and try again");
	    }
	}

	if (arg0.getSource() == updateQ) {
	    if (c.u != null && c.u.isLogin) {
		a = new AnswerPage(c, question, 5);
	    } else {
		JOptionPane.showMessageDialog(null, "plz sign in and try again");
	    }
	}
	if (arg0.getSource() == deleteQ) {
	    if (c.u != null && c.u.isLogin) {
		a = new AnswerPage(c, question, 6);
	    } else {
		JOptionPane.showMessageDialog(null, "plz sign in and try again");
	    }
	}
	if (arg0.getSource() == deleteC) {
	    if (c.u != null && c.u.isLogin) {
		a = new AnswerPage(c, question, 7);
	    } else {
		JOptionPane.showMessageDialog(null, "plz sign in and try again");
	    }
	}
	if (arg0.getSource() == deleteA) {
	    if (c.u != null && c.u.isLogin) {
		a = new AnswerPage(c, question, 8);
	    } else {
		JOptionPane.showMessageDialog(null, "plz sign in and try again");
	    }
	}
	if (arg0.getSource() == down) {
	    if (c.u != null && c.u.isLogin) {
		a = new AnswerPage(c, question, 3);
	    } else {
		JOptionPane.showMessageDialog(null, "plz sign in and try again");
	    }
	}
	if (arg0.getSource() == up) {
	    if (c.u != null && c.u.isLogin) {
		if (isDown.get(Integer.parseInt(which.getText()))) {
		    Question qu = question.get(Integer.parseInt(which.getText()));
//                ArrayList <Comment> ac = new ArrayList <>();
//                if(qu.answer == null)
//                    qu.answer = new ArrayList<>();
//                qu.answer.add(new Answer (ans.getText(),c.u.username,0,ac,0));
//
		    //String s = new String (q.get(Integer.parseInt(question.getText())).content+"*"+ans.getText()+"*"+c.u);
		    //       System.out.println(q.get(Integer.parseInt(question.getText())).content.length());

		    isUp.set(Integer.parseInt(which.getText()), true);
		    isDown.set(Integer.parseInt(which.getText()), false);
		    c.sendMessage(new Message(Message.ADD2Q, qu));

		} else if (!isUp.get(Integer.parseInt(which.getText()))) {
		    Question qu = question.get(Integer.parseInt(which.getText()));
//                ArrayList <Comment> ac = new ArrayList <>();
//                if(qu.answer == null)
//                    qu.answer = new ArrayList<>();
//                qu.answer.add(new Answer (ans.getText(),c.u.username,0,ac,0));
//
		    //String s = new String (q.get(Integer.parseInt(question.getText())).content+"*"+ans.getText()+"*"+c.u);
		    //       System.out.println(q.get(Integer.parseInt(question.getText())).content.length());

		    c.sendMessage(new Message(Message.ADD1Q, qu));
		     isUp.set(Integer.parseInt(which.getText()), true);
		    isDown.set(Integer.parseInt(which.getText()), false);

		} else {
		    JOptionPane.showMessageDialog(null, "you can not like more than once");
		}
	    } else {
		JOptionPane.showMessageDialog(null, "plz sign in and try again");
	    }
	}
	if (arg0.getSource() == down) {
	    if (c.u != null && c.u.isLogin) {
		if (isUp.get(Integer.parseInt(which.getText()))) {
		    Question qu = question.get(Integer.parseInt(which.getText()));
//                ArrayList <Comment> ac = new ArrayList <>();
//                if(qu.answer == null)
//                    qu.answer = new ArrayList<>();
//                qu.answer.add(new Answer (ans.getText(),c.u.username,0,ac,0));
//
		    //String s = new String (q.get(Integer.parseInt(question.getText())).content+"*"+ans.getText()+"*"+c.u);
		    //       System.out.println(q.get(Integer.parseInt(question.getText())).content.length());

		    isDown.set(Integer.parseInt(which.getText()), true);
		    isUp.set(Integer.parseInt(which.getText()), false);
		    c.sendMessage(new Message(Message.SUB2Q, qu));

		} else if (!isDown.get(Integer.parseInt(which.getText()))) {
		    Question qu = question.get(Integer.parseInt(which.getText()));
//                ArrayList <Comment> ac = new ArrayList <>();
//                if(qu.answer == null)
//                    qu.answer = new ArrayList<>();
//                qu.answer.add(new Answer (ans.getText(),c.u.username,0,ac,0));
//
		    //String s = new String (q.get(Integer.parseInt(question.getText())).content+"*"+ans.getText()+"*"+c.u);
		    //       System.out.println(q.get(Integer.parseInt(question.getText())).content.length());
		    isDown.set(Integer.parseInt(which.getText()), true);
		    isUp.set(Integer.parseInt(which.getText()), false);
		    c.sendMessage(new Message(Message.SUB1Q, qu));

		} else {
		    JOptionPane.showMessageDialog(null, "you can not deslike more than once");
		}
	    } else {
		JOptionPane.showMessageDialog(null, "plz sign in and try again");
	    }
	}
//  if (arg0.getSource() == addQuestion) {
//      Question q = new Question(question.getText(), "", u.username, null, null, null);
//      c.sendMessage(new Message(Message.ADD, q));
//      addQuestion m = new addQuestion(c, u);
//  }
    }

}

//	if (arg0.getSource() == addQuestion) {
//	    Question q = new Question(question.getText(), "", u.username, null, null, null);
//	    c.sendMessage(new Message(Message.ADD, q));
//	    addQuestion m = new addQuestion(c, u);
//	}

