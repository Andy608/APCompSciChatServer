package com.sloverse.window;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.sloverse.util.SystemUtil;

public class HubFrame extends JFrame {

	private static final long serialVersionUID = -5130391741054544063L;

	private static final Dimension PREF_SIZE = new Dimension(SystemUtil.getScreenSize().width / 2, SystemUtil.getScreenSize().height / 2);
	
	public HubFrame(String frameTitle, int frameWidth, int frameHeight) {
		super(frameTitle);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(PREF_SIZE);
		setSize(frameWidth, frameHeight);
		setLocationRelativeTo(null);
	}
}
