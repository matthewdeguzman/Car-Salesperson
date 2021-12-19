package carSalesperson;

import javax.swing.*;
import java.awt.*;

public class ThankYouScreen extends JFrame {
	private JLabel thankYou;
	private JButton exit;
	public ThankYouScreen() {
		
		// Creates the thank you label
		thankYou = new JLabel("Thank you for your purchase!");
		thankYou.setBounds(192,140,165,20);
		
		// Creates an exit button
		exit = new JButton("Exit");
		exit.setBounds(225,160,100,25);
		exit.setBackground(Color.RED);
		
		// Closes the window 
		exit.addActionListener(e-> {
			dispose();
		});
		
		add(thankYou);add(exit);
		setSize(550,300);
		setTitle("Car Selector");
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);	
	}
}