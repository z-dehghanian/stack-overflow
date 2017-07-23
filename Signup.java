
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Signup extends JPanel implements ActionListener {

    Dimension d;
    JButton signup;
    JTextField name, username, password, email;
    JLabel lname, luser, lpass, lemail, linterest;
    JCheckBox java, C, SQL, Verilog, VHDL;
    JFrame j;
    MainPage mp;
    public int n;
    public ArrayList<String> interest = new ArrayList<>();

    Client c;

    public Signup(Client cl) {
	c = cl;
	d = Toolkit.getDefaultToolkit().getScreenSize();
	j = new JFrame();
	setSize(800, 560);
	j.setSize(600, 560);
	setLocation(0, 0);
	j.setLocation(90, 0);
	setLayout(null);
	j.setLayout(null);
	setFocusable(true);
	j.getContentPane().add(this);

	signup = new JButton("signup");
	signup.setSize(100, 30);
	signup.setLocation(220, 330);

	name = new JTextField();
	name.setSize(300, 30);
	name.setLocation(140, 50);

	username = new JTextField();
	username.setSize(300, 30);
	username.setLocation(140, 120);

	password = new JTextField();
	password.setSize(300, 30);
	password.setLocation(140, 190);

	email = new JTextField();
	email.setSize(300, 30);
	email.setLocation(140, 260);

	lname = new JLabel("name");
	lname.setSize(100, 50);
	lname.setLocation(20, 50);

	linterest = new JLabel("Interests:");
	linterest.setSize(150, 30);
	linterest.setLocation(470, 50);

	luser = new JLabel("usrname");
	luser.setSize(100, 50);
	luser.setLocation(20, 120);

	lpass = new JLabel("password");
	lpass.setSize(100, 50);
	lpass.setLocation(20, 190);

	lemail = new JLabel("email");
	lemail.setSize(100, 50);
	lemail.setLocation(20, 260);

	java = new JCheckBox("java");
	java.setSize(100, 30);
	java.setLocation(470, 90);

	C = new JCheckBox("C");
	C.setSize(100, 30);
	C.setLocation(470, 130);

	SQL = new JCheckBox("SQL");
	SQL.setSize(100, 30);
	SQL.setLocation(470, 170);

	Verilog = new JCheckBox("Verilog");
	Verilog.setSize(100, 30);
	Verilog.setLocation(470, 210);

	VHDL = new JCheckBox("VHDL");
	VHDL.setSize(100, 30);
	VHDL.setLocation(470, 250);

	this.add(signup);
	this.add(name);
	this.add(username);
	this.add(password);
	this.add(email);
	this.add(luser);
	this.add(lpass);
	this.add(lemail);
	this.add(lname);
	this.add(java);
	this.add(C);
	this.add(SQL);
	this.add(Verilog);
	this.add(VHDL);
	this.add(linterest);
	signup.addActionListener(this);
	java.addActionListener(this);
	C.addActionListener(this);
	SQL.addActionListener(this);
	Verilog.addActionListener(this);
	VHDL.addActionListener(this);
	setVisible(true);
	j.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
	//System.out.println("click :)");IF

	if (arg0.getSource() == signup) {
	    if(name.getText().isEmpty()){
				 JOptionPane.showMessageDialog(null, "Error:you should enter your name");
			}
			if(username.getText().isEmpty()){
				  JOptionPane.showMessageDialog(null, "Error:you should enter your username");
			}
			if(password.getText().isEmpty()){
				  JOptionPane.showMessageDialog(null, "Error:you should enter your password");
			}
			if(email.getText().isEmpty()){
				  JOptionPane.showMessageDialog(null, "Error:you should enter your email");
			}
	    if (java.isSelected()) {
		interest.add("java");
	    } else {
		//interest[0] = 0;
	    }
	    if (C.isSelected()) {
		interest.add("C");
	    } else {
		//interest[1] = 0;
	    }
	    if (SQL.isSelected()) {
		interest.add("SQL");
	    } else {
		//interest[2] = 0;
	    }
	    if (Verilog.isSelected()) {
		interest.add("verilog");
	    } else {
		//interest[3] = 0;
	    }
	    if (VHDL.isSelected()) {
		interest.add("VHDL");
	    } else {
		//interest[4] = 0;
	    }
	    Userz u = new Userz(name.getText(), username.getText(), email.getText(), password.getText(),interest);
	    c.sendMessage(new Message(Message.REGISTER, u));
	    if (c.sOutput.equals("ErrUsrExist")) {
		errorExist();
	    }
	    else
	    {
		mp = new MainPage(c,u);
	    }
	}
    }

    void errorExist() {
	 JOptionPane.showMessageDialog(null, "Error:this username was exist,try another one");
	System.out.println("error existance");
    }
}
