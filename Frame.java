
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class Frame extends JFrame implements ActionListener{
	Dimension d;
	JButton search , signin , signup;
	JTextField question;
	Panel p;
	Signin si;
	Signup sp;
	Client c;
	public Frame(){
		c = new Client ("127.0.0.1",8000,"signup");
		c.start();
                this.p = p;
		d=Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int)d.getWidth()/2,(int)d.getHeight()/2+100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation((int)d.getWidth()/4 , (int)d.getHeight()/4);
	 	setResizable(false);
	 	
	 	search = new JButton("search");
	 	search.setSize(100, 50);
	 	search.setLocation(570, 80);
	 	
	 	signin = new JButton("signin");
	 	signin.setSize(80, 30);
	 	signin.setLocation(580, 10);
	 	
	 	signup = new JButton("signup");
	 	signup.setSize(80, 30);
	 	signup.setLocation(480, 10);
	 	
	 	question = new JTextField();
	 	question.setSize(550, 50);
	 	question.setLocation(10, 80);
	 	
	 	getContentPane().add(search);
	 	getContentPane().add(signin);
	 	getContentPane().add(signup);
	 	getContentPane().add(question);
	 	search.addActionListener(this);
	 	signin.addActionListener(this);
	 	signup.addActionListener(this);
	 	p = new Panel(d,this);
	 	this.getContentPane().add(p);
	 	setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == search) {
			Question q = new Question(question.getText(),"", c.u.username,null,null,0);
			c.sendMessage(new Message(Message.SEARCH, q));
			//addQuestion m = new addQuestion(c,u);
			
		}
		
		if(arg0.getSource()==signin){
			si = new Signin(c);
		}
		
		if(arg0.getSource()==signup){
			sp = new Signup(c);
		}
	}

}
