
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class addQuestion extends JPanel implements ActionListener {

    Dimension d;
    JFrame j;
    JButton search, save;
    JTextField question, keyword;
    private int b;
    private int m = 100;
    private int i;
    private int n;
    private Userz u;
    private Client c;
    public String[] interest = new String[5];
    //JTextArea ja;
    JCheckBox java, C, SQL, Verilog, VHDL;

    public addQuestion(Client client,Userz user) {
	u = user;
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

	search = new JButton("search");
	search.setSize(100, 30);
	search.setLocation(570, 200);

	save = new JButton("save");
	save.setSize(100, 30);
	save.setLocation(680, 200);

	question = new JTextField();
	question.setSize(300, 50);
	question.setLocation(10, 200);

	keyword = new JTextField();
	keyword.setSize(300, 50);
	keyword.setLocation(10, 280);
	this.add(keyword);
	
	this.add(save);
	//this.add(search);
	this.add(question);

	java = new JCheckBox("java");
	java.setSize(100, 30);
	java.setLocation(420, 90);
	this.add(java);
	C = new JCheckBox("C");
	C.setSize(100, 30);
	C.setLocation(420, 130);
	this.add(C);
	SQL = new JCheckBox("SQL");
	SQL.setSize(100, 30);
	SQL.setLocation(420, 170);
	this.add(SQL);
	Verilog = new JCheckBox("Verilog");
	Verilog.setSize(100, 30);
	Verilog.setLocation(420, 210);
	this.add(Verilog);
	VHDL = new JCheckBox("VHDL");
	VHDL.setSize(100, 30);
	VHDL.setLocation(420, 250);
	this.add(VHDL);
	search.addActionListener(this);
	save.addActionListener(this);
	j.getContentPane().add(this);
	setVisible(true);
	j.setVisible(true);
	java.addActionListener(this);
	C.addActionListener(this);
	SQL.addActionListener(this);
	Verilog.addActionListener(this);
	VHDL.addActionListener(this);
	
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
	if (arg0.getSource() == save) {

	    Question q = new Question(question.getText(),keyword.getText(),u.username,null,null,0);
	    c.sendMessage(new Message(Message.ADD, q));
	}

    }

}
