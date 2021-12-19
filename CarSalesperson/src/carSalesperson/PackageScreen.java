package carSalesperson;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PackageScreen extends JFrame {
	
	private static final String[] cars = {"--Select--","s40 ($27,770)", 
			"s50 ($32,500)", "s70 ($36,000)", "s80 ($44,000)"};
	private static final ImageIcon civic = new ImageIcon("honda_civic.png"), 
			accord = new ImageIcon("honda_accord.png"), 
			crv = new ImageIcon("honda_crv.png"), 
			pilot = new ImageIcon("honda_pilot.png");
	private JLabel accNumber, promptA, promptB, 
				   promptC, carSelection, packagePrompt,
				   carDisplay;
	private JComboBox carOptions;
	private JButton select, next, back, reset;
	private JRadioButton packA, packB;
	private ButtonGroup pack;
	private JCheckBox metallicPaint;
	
	public PackageScreen(String name, String phoneNumber, String address, String accountNumber, String car, 
						double packagePrice, double paint) {
		
		// Creating account number label
		accNumber = new JLabel("Account Number: " + accountNumber);
		accNumber.setBounds(30,10,150,20);
		
		// Creating prompt labels to tell the user what to chose and information about the cars
		promptA = new JLabel("Please Select a Car");
		promptB = new JLabel("Note: Package A Available For All Cars");
		promptC = new JLabel("Package B Available For s70 and s80 Only");
		promptA.setBounds(30,40,150,20);
		promptB.setBounds(30,215,225,20);
		promptC.setBounds(63,230,235,20);
		
		// Creating a combo box for the car options
		carOptions = new JComboBox(cars);
		carOptions.setBounds(30,65,100,20);
		
		// Creating a car selection label
		carSelection = new JLabel();
		carSelection.setBounds(140,95,170,15);
		
		// Creating a label to prompt the user to select a package
		packagePrompt = new JLabel("Please Select a Choice (Optional)");
		packagePrompt.setBounds(303,40,200,20);
		packagePrompt.setVisible(false);			// Hiding the package prompt, because it will only be visible
													// once the user has selected a car
		
		// Creating the radio buttons for the package
		packA = new JRadioButton("Package A ($2,200)");
		packB = new JRadioButton("Package B ($3,250)");
		packA.setBounds(300,65,150,20);
		packB.setBounds(300,85,150,20);
		pack = new ButtonGroup();			// Adding the radio buttons to a button group
		pack.add(packA);pack.add(packB);
		packA.setVisible(false);			// Hiding the buttons
		packB.setVisible(false);
		
		// Creating a metallic paint option
		metallicPaint = new JCheckBox("Metalic Paint ($650)");
		metallicPaint.setBounds(300,135,150,20);
		metallicPaint.setVisible(false);	// Hiding the check box
		
		carDisplay = new JLabel();
		carDisplay.setBounds(60,55, 200, 200);
		
		// Create a reset option button
		reset = new JButton("Reset Selection");
		reset.setBounds(304,108,130,20);
		reset.setBackground(Color.RED);
		reset.setForeground(Color.WHITE);
		
		// Clears the button selections for the package if the user accidentally clicked it
		reset.addActionListener(e-> {
			pack.clearSelection();		// Clears any selections made
		});
		reset.setVisible(false);	// Hides button until user has selected a car
		
		// Creating the back button
		back = new JButton("Back");
		back.setBackground(Color.WHITE);
		back.setBounds(345,223,70,25);
		
		// Brings the user to the previous screen and closes the current window
		back.addActionListener(e-> {
				// calls the WelcomeScreen constructor to go back
				new WelcomeScreen(name, address, phoneNumber, accountNumber);
				dispose();
		});
		
		// Creating the next button
		next = new JButton("Next");
		next.setBounds(430,223,70,25);
		next.setBackground(Color.GREEN);
		next.setVisible(false);
		next.addActionListener(new ActionListener() {
			/**
			 * Determines what car and options the user has selected. Creates a new window to go
			 * to the next screen and closes the current window
			 */
			public void actionPerformed(ActionEvent e) {
				// Retrieves what car was selected
				String car = carSelection.getText().substring(carSelection.getText().length() - 3, carSelection.getText().length());
				double packagePrice;
				double paint;
					
				// Determines what package was selected
				if (packA.isSelected())
					packagePrice = 2200.0;
				else if(packB.isSelected()) 
					packagePrice = 3250.0;
				else
					packagePrice = 0.00;
				
				// Determines if the paint was selected
				if (metallicPaint.isSelected())
					paint = 650.00;
				else
					paint = 0.00;
					
				new TotalCostScreen(name, address, phoneNumber, accountNumber, car, packagePrice, paint, null);
				dispose();	// Closes the current window
			}
		});
		
		// Creating the select button
		select = new JButton("Select");
		select.setBounds(140,65,80,25);
		select.setBackground(Color.LIGHT_GRAY);
		
		// Tells the user what car they have selected and allows them to choose options
		// for their selected car
		select.addActionListener(e-> {
			String selection = (String) carOptions.getItemAt(carOptions.getSelectedIndex());
			if (!selection.equals(cars[0])) {
				
				setCarDisplay(selection);
				
				// Retrieves the selected car
				carSelection.setText("Car Selected: " + selection.substring(0,3));
				
				// Sets the package prompt, radio buttons, and check boxes visible so the user may select their packages and options
				packagePrompt.setVisible(true);
				packA.setVisible(true);
				metallicPaint.setVisible(true);
				next.setVisible(true);
				reset.setVisible(true);
				
				// Unselects any buttons that may have been selected
				pack.clearSelection();
				metallicPaint.setSelected(false);
				
				// If the s40 or s50 have been selected, package b will not show because it is not available for them
				// But if the s70 or s80 have been selected, package b will show
				if (selection.equals(cars[1]) || selection.equals(cars[2]))
					// Clears selection if it
					packB.setVisible(false);
				else
					packB.setVisible(true);		
			}
			
			// Hides most of the buttons and car options because there is not a selected car
			else {
				carSelection.setText("No Car Selected");
				packagePrompt.setVisible(false);
				packA.setVisible(false);
				metallicPaint.setSelected(false);
				metallicPaint.setVisible(false);
				next.setVisible(false);
				reset.setVisible(false);
				packA.setVisible(false);
				packB.setVisible(false);
				carDisplay.setIcon(null);
			}
		});
		
		// If the package price is not 0.00, then a package has been previously selected
		// and it selects the package and makes the buttons, prompts, reset buttons, and
		// selection text visible as well as the next button
		if (packagePrice != 0.00) {			
			if (packagePrice == 2200.00)
				packA.setSelected(true);
			if (packagePrice == 3250.00) {
				packB.setSelected(true);
				packB.setVisible(true);
			}
		}
		
		// If there was a previously selected car, it is set as the selected car
		if (car != null)	{
			packagePrompt.setVisible(true);
			packA.setVisible(true);
			metallicPaint.setVisible(true);
			reset.setVisible(true);
			next.setVisible(true);
			carSelection.setVisible(true);
				
			carSelection.setText("Car Selected: " + car);		
			for (int i = 1; i < cars.length; i++)				// starts at i = 1, because the car cannot equal --select--
				if (car.equals(cars[i].substring(0,3))) {
					carOptions.setSelectedIndex(i);
					setCarDisplay(cars[i]);	// Sets the picture as the selected car
					if (cars[i].equals(cars[3]) || cars[i].equals(cars[4]))
						packB.setVisible(true);
				}
		}
		// If the paint options was previously selected, the paint options is set to true
		if (paint != 0.00)
			metallicPaint.setSelected(true);
		
		// Creating the window
		// Adding all the components
		add(accNumber);add(promptA);add(promptB);add(promptC);
		add(carOptions);add(carSelection);add(packagePrompt);
		add(packA);add(packB);add(metallicPaint);add(carDisplay);
		add(reset);add(back);add(next);add(select);
		
		setSize(550,300);		
		setTitle("Car Selector");
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	/**
	 * Displays an image of the selected car
	 * @param selection - The selected car
	 */
	private void setCarDisplay(String selection) {
		if (selection.equals(cars[1]))		// The s40 is displayed as a Honda Accord
			carDisplay.setIcon(accord);
		else if (selection.equals(cars[2]))	// The s50 is displayed as a Honda Civic
			carDisplay.setIcon(civic);
		else if (selection.equals(cars[3]))	// The s70 is displayed as a Honda Pilot
			carDisplay.setIcon(pilot);
		else
			carDisplay.setIcon(crv);		// The s80 is displayed as a Honda CR-V
	}
}