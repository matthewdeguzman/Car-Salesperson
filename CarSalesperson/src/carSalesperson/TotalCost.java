package carSalesperson;

public class TotalCost {
	private static final double TAX = 0.06, TTL_AND_TGS = 325.0, DISCOUNT = 750.0;
	private static double taxCost, totalCost;
	private static String cost;
	/**
	 * Returns a string filled with the total cost of the car and everything 
	 * that makes up the cost
	 * @param car - car type
	 * @param carPrice - car price
	 * @param packagePrice - package price
	 * @param metallicPaint - metallic paint price
	 * @param tradeIn - trade in price
	 * @param downPayment - down payment
	 * @param cash - if the user gets the applied discount or not
	 * @return - a string of the receipt
	 */
	public static String getTotalCost(String car, double carPrice, double packagePrice, double metallicPaint, double tradeIn, double downPayment, boolean cash) {
		// If the car price has not been sent, it will be set to the correct price
		if (carPrice == 0.00)
			carPrice = getCarPrice(car);
		
		// gets the total cost and tax on the car
		totalCost = getPrice(carPrice, packagePrice, metallicPaint, tradeIn, downPayment, cash);
		taxCost = getTax(carPrice, packagePrice, metallicPaint, tradeIn, downPayment, cash);	
		
		// Initializes the cost string to the first line
		cost = String.format(car + "%26s%,.2f","$",carPrice) + "\n";
		
		if (packagePrice > 0.00)	// If the user has purchased a package, it will be added
			cost += String.format("Package" + "%18s%,.2f","$",packagePrice) + "\n";
		if (metallicPaint > 0.00)	// If the user has purchased metallic paint, it will be added
			cost +=  String.format("Metallic Paint" + "%11s%,.2f","$",metallicPaint) + "\n";
		
		cost += String.format("Titles and Tags" + "%7s%,.2f","$",TTL_AND_TGS) + "\n";	// Titles and tags is added
		if (tradeIn > 0.00)			// If the user has traded in a car, it will be deducted from the price
			cost += String.format("Price of Trade-In" + "%5s%,.2f", "-$",tradeIn) + "\n";
		if (cash)					// If the user is paying in all cash, the discount will be shown
			cost += String.format("Discount" + "%18s%.2f","-$",DISCOUNT) + "\n";
		
		cost += String.format("Sales Tax" + "%16s%,.2f","$",taxCost) + "\n";	// The sales tax is added
		if (downPayment > 0.00)		// If the user has paid a down payment it will be deducted from the total cost
			cost += String.format("Down Payment" + "%7s%,.2f", "-$", downPayment) + "\n";
		
		// The total cost is added to the string
		cost +=  "___________________________\n" + 
				String.format("Total Cost:" + "%15s%,.2f", "$", totalCost);
		
		return cost;			
	}
	
	/**
	 * Returns the price of the car
	 * @param car - car type
	 * @return - price of car
	 */
	public static double getCarPrice(String car) {
		if (car.equals("s40"))
			return 27700.0;
		else if (car.equals("s50"))
			return 32500.0;
		else if (car.equals("s70"))
			return 36000.0;
		else
			return 44000.0;
	}
	
	/**
	 * returns the total price of the car
	 * @param carPrice - car price
	 * @param packagePrice - package price
	 * @param metallicPaint - metallic paint price
	 * @param tradeIn - trade in price
	 * @param downPayment - down payment
	 * @param cash - if the user is paying fully in cash
	 * @return - the total price of the car
	 */
	public static double getPrice(double carPrice, double packagePrice, double metallicPaint, double tradeIn, double downPayment, boolean cash) {
		double p;	// variable to hold the price
		if (cash)	// If ther user is paying fully in cash, the discount is applied
			p = ((1.0 + TAX) * (carPrice + packagePrice + metallicPaint + TTL_AND_TGS - tradeIn - DISCOUNT)) - downPayment;
		else
			p = ((1.0 + TAX) * (carPrice + packagePrice + metallicPaint + TTL_AND_TGS - tradeIn )) - downPayment;
		
		if (p < 0.00)	// If the price is negative, 0.00 is returned
			return 0.00;
		else return p;
	}
	
	/**
	 * returns the total tax on the car
	 * @param carPrice - car price
	 * @param packagePrice - package price
	 * @param metallicPaint - metallic paint price
	 * @param tradeIn - trade in price
	 * @param downPayment - down payment
	 * @param cash - if the user is paying fully in cash
	 * @return - the total tax on the car
	 */
	public static double getTax(double carPrice, double packagePrice, double metallicPaint, double tradeIn, double downPayment, boolean cash) {
		double t;	// variable to hold the tax
		if (cash)	// If the user is paying fully in cash, the discount is applied to there cost
			t = TAX * (carPrice + packagePrice + metallicPaint + TTL_AND_TGS - tradeIn - DISCOUNT);
		else
			t = TAX * (carPrice + packagePrice + metallicPaint + TTL_AND_TGS - tradeIn);
		
		if (t < 0.00) // If the tax is negative, 0.00 is returned
			return 0.00;
		else return t;
	}
}
