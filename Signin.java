import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Signin extends JPanel implements ActionListener {

    Dimension d;
    JFrame j;
    JButton signup, signin;
    JTextField username, password;
    JLabel luser, lpass;
    Signup sp;
    MainPage mp;
    public int n;
    public String[] interest = new String[5];
    Client c;

    public Signin(Client cl) {
	c = cl;
	d = Toolkit.getDefaultToolkit().getScreenSize();
	j = new JFrame();
	setSize(600, 560);
	j.setSize(600, 560);
	setLocation(0, 0);
	j.setLocation(90, 0);
	setLayout(null);
	j.setLayout(null);
	setFocusable(true);
	j.getContentPane().add(this);

	signin = new JButton("signin");
	signin.setSize(100, 30);
	signin.setLocation(180, 150);

	username = new JTextField();
	username.setSize(300, 30);
	username.setLocation(140, 50);

	password = new JTextField();
	password.setSize(300, 30);
	password.setLocation(140, 100);

	luser = new JLabel("usrname");
	luser.setSize(100, 50);
	luser.setLocation(20, 50);

	lpass = new JLabel("password");
	lpass.setSize(100, 50);
	lpass.setLocation(20, 100);

	this.add(signin);
	this.add(username);
	this.add(password);
	this.add(luser);
	this.add(lpass);
	signin.addActionListener(this);
	setVisible(true);
	j.setVisible(true);
    }

    public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
	if (arg0.getSource() == signin) {
	    if (username.getText().isEmpty()) {
		JOptionPane.showMessageDialog(null, "Error:you should enter your username");
	    }
	    if (password.getText().isEmpty()) {
		JOptionPane.showMessageDialog(null, "Error:you should enter your password");
	    }
	    Userz u = new Userz("", username.getText(), "", password.getText(), null);
	    
//	    try {
//		u.interest = (ArrayList<String>) c.sInput.readObject();
//	    } catch (IOException ex) {
//		Logger.getLogger(Signin.class.getName()).log(Level.SEVERE, null, ex);
//	    } catch (ClassNotFoundException ex) {
//		Logger.getLogger(Signin.class.getName()).log(Level.SEVERE, null, ex);
//	    }
			c.sendMessage(new Message(Message.LOGIN, u));
//			if (c.sInput.equals("WrongUserOrPassword")) {
//				JOptionPane.showMessageDialog(null, "wrong username or password");
//			} else {
//				mp = new MainPage(c, u);
//
//			}
	}

    }

}
