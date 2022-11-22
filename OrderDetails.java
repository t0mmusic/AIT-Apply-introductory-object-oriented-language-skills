import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/* This class allows creation of OrderDetails objects which will be used to store the
 * details of customer orders. Uses a default and overloaded constructor, as well as
 * getters and setters to access the private class members.
 */
class OrderDetails extends PartsMenu {
	private String	orderNum;
	private String	CustomerFirstName;
	private String	CustomerLastName;
	private String	PartName;
	private String	PaymentMethod;
	private String	dateOrder;
	private boolean	pickedUp;

	public OrderDetails( ) { }

	public OrderDetails(String firstName, String lastName, String PartName, String payment, boolean genuine) {
		this.setCustomerFirstName(firstName);
		this.setCustomerLastName(lastName);
		this.setPartName(PartName);
		this.setPaymentMethod(payment);
		this.setOrderNum(genuine);
		this.setdateOrder();
		this.setpickedUp(false);
	}

	public boolean forbiddenChar( String input )
	{
		if (input.indexOf(":") != -1)
		{
			System.out.println("Forbidden Character Found!");
			return (false);
		}
		return (true);
	}

	/* setters no paramater */

	public void setOrderNum( ){
		boolean genuine = false;
		System.out.print("Is ordered part genuine? y/n: ");
		if (sc.nextLine().equals("y"))
			genuine = true;
		this.orderNum = String.format("%06d", GenerateRandom.getRandomOrderNum(genuine));
	}

	public void setCustomerFirstName( ){
		String set;

		System.out.print("Enter Customer First Name: ");
		set = sc.nextLine();
		while (!forbiddenChar(set))
		{
			System.out.print("Enter Customer First Name: ");
			set = sc.nextLine();
		}
		this.CustomerFirstName = set;
	}

	public void setCustomerLastName( ){
		String set;

		System.out.print("Enter Customer Last Name: ");
		set = sc.nextLine();
		while (!forbiddenChar(set))
		{
			System.out.print("Enter Customer Last Name: ");
			set = sc.nextLine();
		}
		this.CustomerLastName = set;
	}

	public void setPartName( ){
		String set;

		System.out.print("Enter Part Name: ");
		set = sc.nextLine();
		while (!forbiddenChar(set))
		{
			System.out.print("Enter Part Name: ");
			set = sc.nextLine();
		}
		this.PartName = set;
	}

	public void setPaymentMethod( ){
		String set;

		System.out.print("Enter Payment Method: ");
		set = sc.nextLine();
		while (!forbiddenChar(set))
		{
			System.out.print("Enter Payment Method: ");
			set = sc.nextLine();
		}
		this.PaymentMethod = set;
	}

	public void setdateOrder( ){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDateTime now = LocalDateTime.now();
		this.dateOrder = dtf.format(now);
	}

	/* setters with paramater */

	public void setOrderNum( String orderNum ){
		if (forbiddenChar(orderNum))
			this.orderNum = orderNum;
	}

	public void setOrderNum( boolean genuine ){
		this.orderNum = String.format("%06d", GenerateRandom.getRandomOrderNum(genuine));
	}

	public void setCustomerFirstName( String firstName ){
		if (forbiddenChar(firstName))
			this.CustomerFirstName = firstName;
	}

	public void setCustomerLastName( String lastName ){
		if (forbiddenChar(lastName))
			this.CustomerLastName = lastName;
	}

	public void setPartName( String partName ){
		if (forbiddenChar(partName))
			this.PartName = partName;
	}

	public void setPaymentMethod( String paymentMethod ){
		if (forbiddenChar(paymentMethod))
			this.PaymentMethod = paymentMethod;
	}

	public void setpickedUp( boolean picked ){
		this.pickedUp = picked;
	}

	public void setdateOrder( String dateOrder ){
		if (forbiddenChar(dateOrder))
			this.dateOrder = dateOrder;
	}

	/* Getters */

	public String getOrderNum( ){
		return (this.orderNum);
	}

	public String getCustomerFirstName( ){
		return (this.CustomerFirstName);
	}

	public String getCustomerLastName( ){
		return (this.CustomerLastName);
	}

	public String getPartName( ){
		return (this.PartName);
	}

	public String getPaymentMethod( ){
		return (this.PaymentMethod);
	}

	public String getdateOrder( ){
		return (this.dateOrder);
	}

	public boolean getpickedUp( ){
		return (this.pickedUp);
	}
}