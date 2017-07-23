import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainPage extends JPanel implements ActionListener {
	Dimension d;
	JFrame j;
	JButton search,logout,showAll, save,changeUsername,changePass, java, C, SQL, Verilog, VHDL,addQuestion;
	JTextField question;
	private int b;
	private int m = 100;
	private int i;
	private int n;
	private Userz u;
	private Client c;
	public String[] interest = new String[5];
	JTextArea ja;

	public MainPage(Client client, Userz user) {
		u = user;
		c = client;
		d = Toolkit.getDefaultToolkit().getScreenSize();
		j = new JFrame();
		setSize(800, 500);
		j.setSize(800, 500);
		setLocation(0, 0);
		j.setLocation(90, 0);
		setLayout(null);
		j.setLayout(null);
		setFocusable(true);

                changeUsername= new JButton("changeUsername");
		changeUsername.setSize(100, 30);
		changeUsername.setLocation(680, 100);
                
                changePass= new JButton("changePassword");
		changePass.setSize(100, 30);
		changePass.setLocation(680, 150);
                
		search = new JButton("search");
		search.setSize(100, 30);
		search.setLocation(680, 200);
		
		addQuestion = new JButton("addQuestion");
		addQuestion.setSize(100, 30);
		addQuestion.setLocation(680, 250);
		
		
		save = new JButton("save");
		save.setSize(100, 30);
		save.setLocation(680, 300);

		showAll = new JButton("showAll");
		showAll.setSize(100, 30);
		showAll.setLocation(680, 350);

		logout = new JButton("logout");
		logout.setSize(100, 30);
		logout.setLocation(680, 400);

		question = new JTextField();
		question.setSize(550, 100);
		question.setLocation(10, 200);
		
                this.add(changeUsername);
		this.add(changePass);
		this.add(search);
		this.add(addQuestion);
		this.add(question);
		this.add(logout);
		
		//ja = new JTextArea();
		//ja.setSize(500, 300);
		//ja.setLocation(10, 70);
		//this.add(ja);

		C = new JButton("C");
		C.setSize(100, 30);
		C.setLocation(680, 120);

		SQL = new JButton("SQL");
		SQL.setSize(100, 30);
		SQL.setLocation(680, 160);

		Verilog = new JButton("Verilog");
		Verilog.setSize(100, 30);
		Verilog.setLocation(680, 200);

		VHDL = new JButton("VHDL");
		VHDL.setSize(100, 30);
		VHDL.setLocation(680, 240);

		//this.add(save);
		this.add(search);
		this.add(addQuestion);
		this.add(question);
		if (u.interest != null) {
			for (i = 0; i < u.interest.size(); i++) {
				//b = u.interest[i];
				//if (u.interest.get(i).equals("java")) {
					if (u.interest.get(i).equals("java")) {
						java = new JButton("java");
						java.setSize(90, 30);
						java.setLocation(m,100 );
						m = m + 100;
						this.add(java);
						java.addActionListener(this);
					}
					if (u.interest.get(i).equals("C")) {
						C = new JButton("C");
						C.setSize(90, 30);
						C.setLocation(m,100 );
						m = m + 100;
						this.add(C);
						C.addActionListener(this);
					}
					if (u.interest.get(i).equals("SQL")) {
						SQL = new JButton("SQL");
						SQL.setSize(90, 30);
						SQL.setLocation(m,100 );
						m = m + 100;
						this.add(SQL);
						SQL.addActionListener(this);
					}
					if (u.interest.get(i).equals("verilog")) {
						Verilog = new JButton("Verilog");
						Verilog.setSize(90, 30);
						Verilog.setLocation(m,100 );
						m = m + 100;
						this.add(Verilog);
						Verilog.addActionListener(this);
					}
					if (u.interest.get(i).equals("VHDL")) {
						VHDL = new JButton("VHDL");
						VHDL.setSize(90, 30);
						VHDL.setLocation(m,100 );
						m = m + 100;
						this.add(VHDL);
						VHDL.addActionListener(this);
					}
				}
			}
		
		changePass.addActionListener(this);
		changeUsername.addActionListener(this);
		search.addActionListener(this);
		addQuestion.addActionListener(this);
		save.addActionListener(this);
		logout.addActionListener(this);
		j.getContentPane().add(this);
		setVisible(true);
		j.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == search) {
			Question q = new Question(question.getText(),"", u.username,null,null,0);
			c.sendMessage(new Message(Message.SEARCH, q));
			//addQuestion m = new addQuestion(c,u);
			
		}
		if(arg0.getSource() == addQuestion )
		{
		    Question q = new Question(question.getText(),"", u.username,null,null,0);
			c.sendMessage(new Message(Message.ADD, q));
			addQuestion m = new addQuestion(c,u);
		}
                if(arg0.getSource() == java )
		{
		    Question q = new Question("java","", u.username,null,null,0);
			c.sendMessage(new Message(Message.SEARCH, q));
			
		}
                if(arg0.getSource() == C )
		{
		    Question q = new Question("C","", u.username,null,null,0);
			c.sendMessage(new Message(Message.SEARCH, q));
		}
		
                if(arg0.getSource() == SQL )
		{
		    Question q = new Question("SQL","", u.username,null,null,0);
			c.sendMessage(new Message(Message.SEARCH, q));
		}
                if(arg0.getSource() == showAll )
		{
		    Question q = new Question("C","", u.username,null,null,0);
	            c.sendMessage(new Message(Message.SHOWALL, q));
		}
                
		if(arg0.getSource() == changeUsername )
		{
                    Change s = new Change(c,11);
		    //Question q = new Question("VHDL","", u.username,null,null,null);
			//c.sendMessage(new Message(Message.SEARCH, q));
		
                }
                if(arg0.getSource() == changePass )
		{
                    Change s = new Change(c,12);
		    //Question q = new Question("VHDL","", u.username,null,null,null);
			//c.sendMessage(new Message(Message.SEARCH, q));
		}
                if(arg0.getSource() == VHDL )
		{
		    Question q = new Question("VHDL","", u.username,null,null,0);
			c.sendMessage(new Message(Message.SEARCH, q));
		}
                if(arg0.getSource() == Verilog )
		{
		    Question q = new Question("Verilog","", u.username,null,null,0);
			c.sendMessage(new Message(Message.SEARCH, q));
		}
                if(arg0.getSource() == logout )
		{
		    System.exit(0);
		    Frame m = new Frame();
		}
                
	}

}
