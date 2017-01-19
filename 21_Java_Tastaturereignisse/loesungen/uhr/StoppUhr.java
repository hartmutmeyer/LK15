import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StoppUhr extends Uhr implements KeyListener {
	
	UhrAnwendung anwendung;
	
	public StoppUhr(int x, int y, UhrAnwendung anwendung) {
		super(x, y);
		this.anwendung = anwendung;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_S:
			anwendung.timer.start();
			break;
		case KeyEvent.VK_E:
			anwendung.timer.stop();
			break;
		case KeyEvent.VK_R:
			z1.reset();
			z2.reset();
			z3.reset();
			z4.reset();
			anwendung.repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

}
