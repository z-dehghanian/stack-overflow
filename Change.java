



import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Change extends JPanel implements ActionListener {

    Dimension d;
    JFrame j;
    JButton  save;
    JTextField oldz, newz;
    private int b,type;
   
    private Client c;
   // public String[] interest = new String[5];
    //JTextArea ja;
    //JCheckBox java, C, SQL, Verilog, VHDL;
    //ArrayList<Question> q;
    public Change (Client client,int type) {
	//u = user;
	//this.q = q;
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

	
	save = new JButton("save");
	save.setSize(100, 30);
	save.setLocation(680, 200);

	oldz = new JTextField();
	oldz.setSize(300, 50);
	oldz.setLocation(10, 200);

	newz = new JTextField();
	newz.setSize(300, 50);
	newz.setLocation(10, 280);
	this.add(newz);
	
        //num = new JTextField();
	//num.setSize(300, 50);
	//num.setLocation(10, 120);
	//this.add(num);
	
	this.add(save);
	save.addActionListener(this);
		
	//this.add(search);
	this.add(oldz);
	j.getContentPane().add(this);
		setVisible(true);
		j.setVisible(true);
	
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
	if (arg0.getSource() == save) {
	    //Question q = new Question(question.getText(),keyword.getText(),u.username,null,null,null);
                //Question qu = q.get(Integer.parseInt(question.getText()));
               // ArrayList <Comment> ac = new ArrayList <>();
               // if(qu.answer == null)
                 //   qu.answer = new ArrayList<>();
                //qu.answer.add(new Answer (ans.getText(),c.u.username,0,ac,0)); 
               
            //String s = new String (q.get(Integer.parseInt(question.getText())).content+"*"+ans.getText()+"*"+c.u);
               // System.out.println(q.get(Integer.parseInt(question.getText())).content.length());
	    Userz w = new Userz(newz.getText(),c.u.username,null,c.u.pass,null);
                c.sendMessage(new Message(type,w));
            
	}

    }

}
    

