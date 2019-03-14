import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

public class windowsForm extends JFrame implements MouseMotionListener, MouseListener {
	container content;
	windowsForm() { this(400, 400, 10, 10); }
	windowsForm(int width, int height, int posx, int posy) { 
		setUndecorated(true);
		setBounds(posx, posy, width, height);
		
		setLayout(new BorderLayout(0, 0));
		titleBar title = new titleBar();
		content = new container();
		
		add(title, BorderLayout.NORTH);
		add(content, BorderLayout.CENTER);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		setAlwaysOnTop(true);
	}
	Color barColor;
	public Color getBackground() { return barColor; }
	public class titleBar extends JPanel {
		controlbutton exitButton;
		titleBar() {
			setPreferredSize(new Dimension(0, 25));			
			barColor = new  Color(20, 20, 20);
			setBackground(barColor); setOpaque(true);
			
			FlowLayout flow = new FlowLayout(0, 0, 0);
			flow.setAlignment(FlowLayout.RIGHT);
			
			setLayout(flow);
			exitButton = new controlbutton("X");
			add(exitButton);
		}
		public class controlbutton extends JLabel implements MouseListener {
			controlbutton() { this("-"); }
			controlbutton(String icon) {
				super(icon, JLabel.CENTER);
				setOpaque(true);
				addMouseListener(this);
				setBackground(barColor);
				setForeground(Color.white);
				setPreferredSize(new Dimension(40, 20));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getSource().equals(exitButton)) { exitFunction(); }
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				if(e.getSource().equals(exitButton)) exitButton.setBackground(Color.RED); 
			}
			@Override
			public void mouseExited(MouseEvent e) { 
				if(e.getSource().equals(exitButton)) exitButton.setBackground(barColor); 
			}
			@Override
			public void mousePressed(MouseEvent e) { }
			@Override
			public void mouseReleased(MouseEvent e) { }
		}
	}
	public void exitFunction() { dispose(); }
	public class container extends JPanel {
		JPanel content;
		CardLayout cout;
		container() {
			setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
			setLayout(cout = new CardLayout());
			setBackground(barColor); setOpaque(true);
			content = new JPanel(); content.setBackground(this.getBackground().brighter());
			content.setOpaque(true);
			add(content, "MAINSCREEN");
		}
	}
	public void addScreen(JPanel panel, String name) { content.add(panel, name); }
	public void showScreen(String name) { content.cout.show(content, name); }
	public Dimension getContainerSize() { return new Dimension(this.getWidth() - 20, this.getHeight() - (25 + 10)); }
	Point winPos = new Point();
	
	@Override
	public void mouseDragged(MouseEvent e) { setLocation(getLocation().x + (e.getX() - winPos.x), getLocation().y + (e.getY() - winPos.y)); }
	@Override
	public void mouseMoved(MouseEvent e) { }
	@Override
	public void mouseClicked(MouseEvent e) { }
	@Override
	public void mouseEntered(MouseEvent e) { }
	@Override
	public void mouseExited(MouseEvent e) { }
	@Override
	public void mousePressed(MouseEvent e) { winPos.setLocation(e.getPoint()); }
	@Override
	public void mouseReleased(MouseEvent e) { }
	
}
