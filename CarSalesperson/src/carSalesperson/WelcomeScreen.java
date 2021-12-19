package carSalesperson;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class WelcomeScreen extends JFrame implements ActionListener {
	private static final int WINDOW_WIDTH = 550, WINDOW_HEIGHT = 300, 
						TEXTFIELD_WIDTH = 200, TEXTFIELD_HEIGHT = 25;
	private static final String[] states = {"--Select--","Alabama","Alaska","Arizona","Arkansas","California","Colorado",
									"Connecticut","Delaware","District of Columbia","Florida","Georgia",
									"Hawaii","Idaho","Illinois","Indiana","Iowa","Kansas","Kentucky",
									"Louisiana","Maine","Maryland","Massachusetts","Michigan","Minnesota",
									"Mississippi","Missouri","Montana","Nebraska","Nevada","New Hampshire",
									"New Jersey","New Mexico","New York","North Carolina","North Dakota",
									"Ohio","Oklahoma","Oregon","Pennsylvania","Rhode Island","South Carolina",
									"South Dakota","Tennessee","Texas","Utah","Vermont","Virginia","Washington",
									"West Virginia","Wisconsin","Wyoming"};
	private JLabel accNumber, nameLabel, addressLabel, phoneNumberLabel, prompt, 
				  pnParenthesis, error, cityLabel, stateLabel, zipLabel;
	private JTextField nField, aField, cField, zField, 
				 pnField1, pnField2, pnField3;
	private JComboBox stateSelection;
	private JButton enter;
	private String pn, number, userAddress;
	
	public WelcomeScreen(String name, String address, String phoneNumber, String accountNumber) {
		
		// Adding the account number label to the window
		if (accountNumber == null) {					// If there is no previous account number, then a new one will be generated
			setAccNumber();
			accNumber = new JLabel("Account Number: " + number);
		}
		else {											// The previous account number is set as the current
			accNumber = new JLabel("Account Number: " + accountNumber);
			number = accountNumber;
		}
		
		// Assigning the account number label to a position and fixing its size
		Dimension anSize = accNumber.getPreferredSize();
		accNumber.setBounds(30,10,anSize.width,anSize.height);
		
		// Adding prompt label
		prompt = new JLabel("Please Enter the Following Information");
		Dimension pSize = prompt.getPreferredSize();
		prompt.setBounds((WINDOW_WIDTH-220)/2,40,220,pSize.height);
		
		// Adding the name label
		nameLabel = new JLabel("Name");
		Dimension nSize = nameLabel.getPreferredSize();
		nameLabel.setBounds(43,70,nSize.width,nSize.height);
	
		// Adding the name text field
		nField = new JTextField();
		if (name != null)
			nField.setText(name);
		nField.setBounds(43,90,TEXTFIELD_WIDTH,TEXTFIELD_HEIGHT);
		
		// Adding the address label
		addressLabel = new JLabel("Street Address");
		Dimension aSize = addressLabel.getPreferredSize();
		addressLabel.setBounds((WINDOW_WIDTH + 15)/2,70,aSize.width,aSize.height);
		
		// Adding the address text field
		aField = new JTextField();
		aField.setBounds((WINDOW_WIDTH + 15)/2,90,TEXTFIELD_WIDTH,TEXTFIELD_HEIGHT);
		
		cityLabel = new JLabel("City");
		cityLabel.setBounds((WINDOW_WIDTH + 15)/ 2, 115, 50,20);
		cField = new JTextField();
		cField.setBounds((WINDOW_WIDTH + 15) / 2, 135, TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
		
		zipLabel = new JLabel("Zip Code");
		zipLabel.setBounds((WINDOW_WIDTH + 15)/ 2, 160, 50,20);
		zField = new JTextField();
		zField.setBounds((WINDOW_WIDTH + 15) / 2, 180, 100, TEXTFIELD_HEIGHT);
		
		stateLabel = new JLabel("State");
		stateLabel.setBounds((WINDOW_WIDTH + 15) / 2, 203, 50, 20);
		stateSelection = new JComboBox(states);
		stateSelection.setBounds((WINDOW_WIDTH + 15)/2, 223, 140, TEXTFIELD_HEIGHT);
		
		if (address != null)	{			// If there is a previous address, the address fields are set to the previous address
			// The street address is extracted from the address string and set as the text
			aField.setText(address.substring(0,address.indexOf(',')));		
			
			// The city is extracted from the string and set as the text of the city text field
			cField.setText(address.substring(address.indexOf(',') + 1, address.lastIndexOf(',')).trim());
			
			// Extracts the zip code from the address and sets it as the text in the zip code text field
			zField.setText(address.substring(address.length() - 6, address.length()).trim());
			
			boolean b = false;
			int i = 0;
			String state = address.substring(address.lastIndexOf(',') + 1, address.length() - 6).trim();	// Extracts the selected state from the address
			
			// Finds what state the user previously selected and sets it as the selected state
			do {
				if (state.equals(states[i])) {
					b = true;
					--i;
				}
				i++;
			} while (!b);
			stateSelection.setSelectedIndex(i);
		}
		
		// Adding the phone number label
		phoneNumberLabel = new JLabel("Phone Number");
		Dimension pnSize = phoneNumberLabel.getPreferredSize();
		phoneNumberLabel.setBounds(43,125,85,pnSize.height);
		
		// Adding the phone number text field
		pnField1 = new JTextField();
		pnField2 = new JTextField();
		pnField3 = new JTextField();
		
		// If there is a previous phone number, it is set as the text for the phone number text fields
		if (phoneNumber != null) {			
			for (int i = 0; i < 3; i++)
				pnField1.setText(pnField1.getText() + phoneNumber.charAt(i));
			for (int i = 3; i < 6; i++)
				pnField2.setText(pnField2.getText() + phoneNumber.charAt(i));
			for (int i = 6; i < 10; i++) 
				pnField3.setText(pnField3.getText() + phoneNumber.charAt(i));
		}
		pnField1.setBounds(49,145,25,TEXTFIELD_HEIGHT);
		pnField2.setBounds(85,145,25,TEXTFIELD_HEIGHT);
		pnField3.setBounds(127,145,33,TEXTFIELD_HEIGHT);
		
		// Adding parenthesis and a hyphen on the phone number text fields to show the formatting 
		pnParenthesis = new JLabel("(     )      -");
		pnParenthesis.setFont(new Font("Arial", Font.PLAIN, 20));
		pnParenthesis.setBounds(40,146,200,20);
		
		// Adding the enter button
		enter = new JButton("Next");
		enter.setBackground(Color.GREEN);
		enter.addActionListener(this);
		enter.setBounds(430,223,70,25);
		
		// Add the error label
		error = new JLabel();
		error.setForeground(Color.RED);
		error.setVisible(false);
		error.setBounds(35,240,300,20);
				
		// Add all of the components to the window
		add(accNumber);add(prompt);add(nameLabel);add(nField);
		add(addressLabel);add(aField);add(cityLabel);add(cField);
		add(zipLabel);add(zField);add(stateLabel);add(stateSelection);
		add(phoneNumberLabel);add(pnField1);add(pnField2);add(pnField3);
		add(pnParenthesis);add(enter);add(error);
		
		setTitle("Car Selector");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/** 
	 * Asks the user if the information entered is correct
	 * If user presses yes, the next window is opened
	 * If user presses no, the confirmation is closed
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			int decision;	// The user's decision to move onto the next screen or not
			String stateSelected;
			// If the name text field or the address text field do not have any contents, an error is thrown
			nField.setText(nField.getText().trim());
			if (nField.getText().isEmpty() || nField.getText().isBlank())
				throw new InvalidInput("Name");
			if (aField.getText().isEmpty() || aField.getText().isBlank())
				throw new InvalidInput("Street Address");
			if (cField.getText().isEmpty() || cField.getText().isBlank())
				throw new InvalidInput("City");
			if (zField.getText().isEmpty() || zField.getText().isBlank())
				throw new InvalidInput("Zip Code");
			
			// Trims the address fields of leading and trailing spaces
			aField.setText(aField.getText().trim());
			cField.setText(cField.getText().trim());
			zField.setText(zField.getText().trim());
			
			// Sets the error text and checks if there are any commas in the address text fields
			// Because the code relies on the use of commas, any additional commas would not allow it to work
			// And there are no commas in addresses
			// Does not check for commas in the zip code field, because it will check later when parsing it to an int
			error.setText("Error: Commas Not Allowed in Address");
			for (int i = 0; i < aField.getText().length(); i++)
				if (aField.getText().charAt(i) == ',')
					throw new Exception();
			for (int i = 0; i < cField.getText().length(); i ++)
				if (cField.getText().charAt(i) == ',')
					throw new Exception();
			
			// Sets the error text and parses the zip code text to an integer
			// If the zip code text contains anything other than an integer, then 
			// an error will occur and the error text will display
			error.setText("Error: Invalid Zip Code");
			Integer.parseInt(zField.getText());
			if (zField.getText().length() != 5)
				throw new InvalidInput(1,"Zip Code");
			stateSelected = (String) stateSelection.getItemAt(stateSelection.getSelectedIndex());
			if (stateSelected.equals(states[0])) {
				error.setText("Error: No State Selected");
				throw new Exception();
			}
			
			// Trims the phone number fields for any leading or trailing white spaces
			pnField1.setText(pnField1.getText().trim());
			pnField2.setText(pnField2.getText().trim());
			pnField3.setText(pnField3.getText().trim());
			
			// If one of the phone number fields is not the correct length, an error is thrown
			if (pnField1.getText().length() != 3 || pnField2.getText().length() != 3 || pnField3.getText().length() != 4) {
				throw new InvalidInput(1, "Phone Number");
			}
			else {
				error.setText("Error: Invalid Input");
				Integer.parseInt(pnField1.getText());
				Integer.parseInt(pnField2.getText());
				Integer.parseInt(pnField3.getText());
			}
			
			// Adds all of the phone number fields to get a complete phone number string
			pn = pnField1.getText() + pnField2.getText() + pnField3.getText();
			userAddress = aField.getText() + ", " + cField.getText() + ", " + stateSelected + " " + zField.getText();
			error.setVisible(false);				// Sets the error text to false, because no more errors can occur
			
			// Asks the user if they would like to move onto the next page
			// and confirms if the information is correct
			decision = JOptionPane.showConfirmDialog(this, "Name: " + nField.getText() 
											+ "\nAddress: " + userAddress 
											+ "\nPhone Number: "+ pn);
			
			// If the user clicks yes, a new window is generated and the current window is closed
			if (decision == JOptionPane.YES_OPTION) {
				new PackageScreen(nField.getText(),pn,userAddress, String.valueOf(number), null, 0.0, 0.0);
				dispose();
			}
		}
		catch (InvalidInput er) {
			error.setText(er.getMessage());
			error.setVisible(true);
		}
		catch (Exception er) {
			error.setVisible(true);
		}
	}
	
	/**
	 * Creates a random five digit account number by adding random numbers
	 */
	public void setAccNumber() {
		int n = 0;		// Account number
		int n1;			// Temporary number created by random number generator
		boolean t;		// created for loop
		Random randNum = new Random();
		
		// a will act as the exponent and it will decrease every loop so the next number
		// will take up the next space
		for (int a = 4; a >= 0; a--){
			do {
				t = true;	// Initializes t to true so infinite loop does not occur
				
				// Generates a random number between 0 and 9 multiplies it depending on what digit it will take
				n1 = randNum.nextInt(10) * (int) Math.pow(10, a);
				
				// If n is 0 and it is the first or last digit, the number will not be five digits, so 
				// t is set to false and the loop runs again
				if (n1 == 0) {
					if (a == 4)
						t = false;
					else if (a == 0)
						t = false;
				}
			} while(!t);
			
			// n is added to the account number and represents one digit in the account number
			n += n1;
		}
		// takes the string value of the number and assigns it to the number field
		number = String.valueOf(n);
	}	
}