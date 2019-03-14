import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class fileIcon extends JLabel implements MouseListener {
		private static int size;
		private ImageIcon iconImg;
		private Image originImg;
		private String typeName;
		private final static String path = "C:\\Users\\USER\\eclipse-workspace\\ssh_trans\\src\\";
		private JLabel icon, text;
		private boolean autoTrans, click;
		private String fileName;
		private static Color bgColor;
		
		fileIcon(int iconSize) {
			setLayout(new BorderLayout());
			setOpaque(true);
			addMouseListener(this);
		}
		
		public static void setBackgroundColor(Color tbgColor) { bgColor = tbgColor; }
		public static void setIconSize(int iconsize) { size = iconsize; }
		
		public void setIcon(int type, String filename) {
			setPreferredSize(new Dimension(size, size + 20));
			setBackground(bgColor);
			
			typeName = (type == 0) ? "folder.png" : "file.png"; /*0 is folder*/
			originImg = Toolkit.getDefaultToolkit().getImage(path + typeName);
			originImg = originImg.getScaledInstance(size, size, Image.SCALE_SMOOTH);
			iconImg = new ImageIcon(originImg);
			fileName = filename.replace("d-", "").replace("f-", "");
			icon = new JLabel(iconImg);
			text = new JLabel(fileName, JLabel.CENTER);
			
			add(icon, BorderLayout.CENTER);
			add(text, BorderLayout.SOUTH);
		}
		public String getFileName() { return fileName; }
		public void click() { 
			setBackground(new Color(150, 150, 150));
			click = true;
		}
		public void specialClick() {
			setBackground(new Color(230, 150, 150));
			click = true;
			autoTrans = true;
		}
		public void unClick() {
			setBackground(bgColor);
			click = false;
			autoTrans = false;
		}
		public boolean isClick() { return click; }
		public boolean isSpecialClick() { return autoTrans; }
		@Override
		public void mouseClicked(MouseEvent e) {
			if(click) { unClick(); return; }
			if(e.isMetaDown()) specialClick(); 
			else click(); 
		}
		@Override
		public void mouseEntered(MouseEvent e) { }
		@Override
		public void mouseExited(MouseEvent e) { }
		@Override
		public void mousePressed(MouseEvent e) { }
		@Override
		public void mouseReleased(MouseEvent e) { }
	}