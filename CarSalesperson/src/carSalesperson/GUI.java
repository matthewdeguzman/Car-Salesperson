package carSalesperson;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;

public class GUI implements ActionListener {
	int number;
	static int WINDOW_WIDTH = 500;
	static int WINDOW_HEIGHT = 300;
	static int TEXTFIELD_WIDTH = 200;
	static int TEXTFIELD_HEIGHT = 25;
	JFrame window;
	JLabel accNumber, name, address, phoneNumber,prompt;
	JTextField nField, aField, pnField;
	JButton enter;
	
	public GUI() {
		
		// Adding the account number label to the window
		setAccNumber();
		accNumber = new JLabel("Account Number: " + number);
		Dimension anSize = accNumber.getPreferredSize();
		accNumber.setBounds(30,10,anSize.width,anSize.height);
		
		// Adding prompt label
		prompt = new JLabel("Please Enter the Following Information");
		Dimension pSize = prompt.getPreferredSize();
		prompt.setBounds((WINDOW_WIDTH-220)/2,40,220,pSize.height);
		
		// Adding the name label
		name = new JLabel("Name");
		Dimension nSize = name.getPreferredSize();
		name.setBounds(30,70,nSize.width,nSize.height);
	
		// Adding the name textfield
		nField = new JTextField();
		nField.setBounds(30,90,TEXTFIELD_WIDTH,TEXTFIELD_HEIGHT);
		
		// Adding the address label
		address = new JLabel("Address");
		Dimension aSize = address.getPreferredSize();
		address.setBounds((WINDOW_WIDTH + 15)/2,70,aSize.width,aSize.height);
		
		// Adding the address textfield
		aField = new JTextField();
		aField.setBounds((WINDOW_WIDTH + 15)/2,90,TEXTFIELD_WIDTH,TEXTFIELD_HEIGHT);

		// Adding the phone number label
		phoneNumber = new JLabel("Phone Number");
		Dimension pnSize = phoneNumber.getPreferredSize();
		phoneNumber.setBounds(30,125,85,pnSize.height);
		
		// Adding the phone number text field
		pnField = new JTextField();
		pnField.setBounds(30,145,TEXTFIELD_WIDTH,TEXTFIELD_HEIGHT);
		
		// Adding the enter button
		enter = new JButton("Enter");
		enter.setBounds((WINDOW_WIDTH - 80)/2,WINDOW_HEIGHT-100,80,25);
		enter.addActionListener(this);
		
		window = new JFrame("Welcome Screen");		// Create the window
		
		// Add all of the components to the window
		window.add(accNumber);window.add(prompt);
		window.add(address);window.add(aField);
		window.add(phoneNumber);window.add(pnField);
		window.add(name);window.add(nField);
		window.add(enter);
		
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setLayout(null);
		window.setLocationRelativeTo(null); 
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		int o;
		o = JOptionPane.showConfirmDialog(window, "Name: " + nField.getText() 
									+ "\nAddress: " + aField.getText() 
									+ "\nPhone Number : "+ pnField.getText());
		if (o == JOptionPane.YES_OPTION) {
			
			new Options();
		}
	}
	
	/**
	 * Creates a random five digit account number by adding random numbers
	 */
	public void setAccNumber() {
		int num = 0;	// The account number that will be created
		int n;			// Temporary number created by random number generator
		Random randNum = new Random();
		
		// a will act as the exponent and it will decrease every loop so the next number
		// will take up the next space
		for (int a = 4; a >= 0; a--){
			n = randNum.nextInt(10) * (int) Math.pow(10, a);
			
			// If the last digit is zero, then it redoes the loop because zero would not show if it were the last digit
			if (n == 0 && a == 0)
				a = 1;
			
			num += n;
		}
		// the account number is now updated with the newly created number
		number = num;
	}
	
}
