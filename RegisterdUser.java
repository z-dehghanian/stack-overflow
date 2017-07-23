import javax.swing.JPanel;
import javax.swing.JTextField;

public class RegisterdUser extends Client{
	
	String name;
	String username;
	String email;
	String password;
	JPanel panel;
	JTextField jfield;
	
	public RegisterdUser(String name, String username, String email, String password,
			String server, int port) {
		// TODO Auto-generated constructor stub
		super(server, port, username);
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		panel = new JPanel();
		panel.setSize(500, 500);
		
		jfield = new JTextField();
		
		panel.setVisible(true);
	}
	
	
	
	
}
