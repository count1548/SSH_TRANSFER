import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

public class transition_file {
	static Color boxColor;
	static LabelButton submit, share, exit;
	static windowsForm win;
	static SSH_transfer ssh;
	static findFile fList;
	static String []fileNameList;
	static fileIcon[] fIcon;
	static final String path = "C:\\Users\\USER\\Downloads\\chilkat-9.5.0-jdk8-x64\\";
	static boolean autoTrans = false;
	static fWatcher fw = null;
	
	static {
	    try {
	    	System.load("C:\\Users\\USER\\Downloads\\chilkat-9.5.0-jdk8-x64\\chilkat.dll");
	    } catch (UnsatisfiedLinkError e) {
	    	System.err.println("Native code library failed to load.\n" + e);
	    	System.exit(1);
		}
	}
	
	public static void main(String[] args) {
		final int width = 710, height = 600, posx = 30, posy = 30;
		win = new windowsForm(width, height, posx, posy) {
			public void exitFunction() {
				if(fw!= null) {
					fw.stopThread();
				}
				dispose();
			}
		};
		fList = new findFile(path);
		fileNameList = fList.getFolderList();
		
		JPanel ExplorerPane = new JPanel();
		ExplorerPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		ExplorerPane.setLayout(new FlowLayout());
		
		Color PaneColor = new Color(200, 200, 255);
		ExplorerPane.setBackground(PaneColor);
		ExplorerPane.setOpaque(true);
		
		JLabel title = new JLabel(path, JLabel.CENTER);
		title.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		JPanel folderBox = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		JPanel footerBox = new JPanel();
		submit = new LabelButton("SUBMIT");
		share = new LabelButton("SHARE");
		exit = new LabelButton("EXIT");
		
		footerBox.setLayout(new FlowLayout(FlowLayout.RIGHT));
		footerBox.setOpaque(false);
		
		footerBox.add(submit); footerBox.add(share); footerBox.add(exit);
		ExplorerPane.setLayout(new BorderLayout());
		ExplorerPane.add(title, BorderLayout.NORTH);
		ExplorerPane.add(folderBox, BorderLayout.CENTER);
		ExplorerPane.add(footerBox, BorderLayout.SOUTH);
		
		fIcon = new fileIcon[fList.getFileCount()];
		boxColor = folderBox.getBackground();
		fileIcon.setBackgroundColor(boxColor);
		fileIcon.setIconSize(50);
		
		Arrays.sort(fileNameList);
		
		for(int i = 0; i < fList.getFileCount(); i++) {
			fIcon[i] = new fileIcon(60);
		
			if(fileNameList[i].contains("d-")) fIcon[i].setIcon(0, fileNameList[i]);
			else fIcon[i].setIcon(1, fileNameList[i]);
			folderBox.add(fIcon[i]);
		}
		
		win.addScreen(ExplorerPane, "EXPLORER");
		win.showScreen("EXPLORER");
		
		win.setVisible(true);
		
		ssh = new SSH_transfer("1.235.60.111", 50); 
		if(ssh.connect) ssh.loginServer("mainuser", "3qufvv");
		else System.err.println("server not connect");
	}
	public static class LabelButton extends JLabel implements MouseListener {
		LabelButton(String buttonName) {
			super(buttonName, JLabel.CENTER);
			setPreferredSize(new Dimension(80, 30));
			setOpaque(true);
			setBackground(boxColor);
			setBorder(BorderFactory.createLineBorder(Color.black, 1));
			addMouseListener(this);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource().equals(submit) && ssh.connect) {
				for(int i = 0; i < fList.getFileCount(); i++) {
					if(fIcon[i].isClick()) {
						System.out.println("general Trans file " + fIcon[i].getFileName() + " Transfer request");
						ssh.transferFile(path, fIcon[i].getFileName());
						if(fIcon[i].isSpecialClick()) continue;
						fIcon[i].unClick();
					}
				}
			}
			else if(e.getSource().equals(share) && ssh.connect) {
				if(autoTrans) {
					if(fw != null) {
						fw.stopThread();
						fw = null;
					}
					autoTrans = false;
					setBackground(boxColor);
				}
				else {
					autoTrans = true;
					setBackground(boxColor.darker());
					try { fw = new fWatcher(path) {
						public void actionFunction() {
							for(int i = 0; i < fList.getFileCount(); i++) {
								if(fIcon[i].getFileName().equals(eventFName) && fIcon[i].isSpecialClick()) {
									ssh.transferFile(path, eventFName);
									System.out.println("auto Trans file " + eventFName + " Transfer Success");
								}
							}
						}
					}; } 
					catch (IOException e1) { e1.printStackTrace(); }
					fw.start();
				}
			}
			if(e.getSource().equals(exit)){
				if(fw != null) {
					fw.stopThread();
				}
				win.dispose();
			}
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
	
}
