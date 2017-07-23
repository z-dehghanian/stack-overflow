
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class Panel extends JPanel implements KeyListener, Runnable {
	private Dimension d;
	private Frame f;
	// JFrame j;

	public Panel(Dimension d, Frame f) {
		this.d = d;
		this.f = f;
		// j = new JFrame();
		setFocusable(true);
		// j.setFocusable(true);
		setLayout(null);
		// j.setLayout(null);
		setSize(f.WIDTH, f.HEIGHT);
		// j.setSize(arg0, arg1);
		setLocation((int) d.getWidth() / 2, (int) d.getHeight() / 2);
		addKeyListener(this);

	}

	public static void main(String[] args) {
		Frame f = new Frame();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
