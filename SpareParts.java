import java.util.*;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

class AddOrders {
	public static OrderDetails		orderArray[];
	public static GenerateRandom	orderNum;
	public static FileWriter		myWriter;
	public static BufferedWriter	bw;

	public static OrderDetails []removeElement(OrderDetails[] arr, int removedIdx) {
		int	i = 0;
		int	j = 0;
		if (arr.length == 1)
			return (null);
		OrderDetails newArray[] = new OrderDetails[arr.length - 1];

		for (i = 0; i < arr.length; i++)
		{
			if (i != removedIdx)
			{
				newArray[j] = arr[i];
				j++;
			}
		}
		return (newArray);
	}

	public static void exportData()
	{
		if (orderArray == null)
			return ;
		try {
			new FileWriter("orders.txt", false).close();
			myWriter = new FileWriter("orders.txt", true);
			bw = new BufferedWriter(myWriter);
			for (int i = 0; i < orderArray.length; i++)
			{
				bw.write(orderArray[i].dateOrder + ":"
				+ orderArray[i].orderNum + ":"
				+ orderArray[i].CustomerFirstName + ":"
				+ orderArray[i].CustomerLastName + ":"
				+ orderArray[i].PaymentMethod + ":"
				+ orderArray[i].PartName + ":"
				+ orderArray[i].pickedUp);
				bw.newLine();
			}
			bw.close();
			myWriter.close();
		}
    	catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public static OrderDetails chooseOrder( OrderDetails tmpArray[] )
	{
		OrderDetails	choice = null;
		int	i = 0;

		for (i = 0; i < tmpArray.length; i++)
		{
			System.out.println("Index: " + i);
			ViewOrders.printDetails(tmpArray[i]);
		}
		System.out.println("Choose an order using index, E to return to main menu: ");
		String selection = sc.nextLine();
		if (selection.equals("E"))
			return (choice);
		int	selInt = Integer.parseInt(selection);
		if (selInt < 0 && selInt > i)
		{
			System.out.println("Please make a valid selection.");
			return (chooseOrder(tmpArray));
		}
		return (tmpArray[selInt]);
	}

	public static OrderDetails compareArrays()
	{
		String			order;
		OrderDetails	tmpArray[] = null;
		OrderDetails	choice;
		int				count = 0;
		int				i = 0;

		if (orderArray == null)
		{
			System.out.println("No Orders Found.");
			return (null);	
		}
		System.out.println("Enter Order ID: ");
		order = sc.nextLine();
		for (i = 0; i < orderArray.length; i++)
		{
			if (order.equals(orderArray[i].orderNum))
			{
				tmpArray = updateArray(orderArray[i], tmpArray);
				count++;
			}
		}
		switch(count) {
			case 0:
			{
				System.out.println("No matching order was found.");
				return (null);
			}
			case 1:
			{
				choice = tmpArray[0];
				break ;
			}
			default:
			{
				choice = chooseOrder(tmpArray);

				if (choice == null)
					return (null);
			}
		}
		ViewOrders.printDetails(choice);
		return (choice);
	}

	public static void deleteData()
	{
		OrderDetails choice = compareArrays();
		int	i;

		if (choice == null)
			return ;
		for (i = 0; i < orderArray.length; i++)
		{
			if (choice.orderNum.equals(orderArray[i].orderNum) && choice.dateOrder.equals(orderArray[i].dateOrder))
				break ;
		}
		System.out.println("Confirm Delete? y/n");
		if (sc.nextLine().equals("y"))
		{
			orderArray = removeElement(orderArray, i);
			exportData();
		}
	}

	public static void importData()
	{
		BufferedReader 		myReader = null;
		String				line;
		String				details[];
		
		try {
			myReader = new BufferedReader(new FileReader("orders.txt"));
			while ((line = myReader.readLine()) != null)
			{
				OrderDetails 		tmpDeets = new OrderDetails();
				details = line.split(":");
				tmpDeets.dateOrder = details[0];
				tmpDeets.orderNum = details[1];
				tmpDeets.CustomerFirstName = details[2];
				tmpDeets.CustomerLastName = details[3];
				tmpDeets.PaymentMethod = details[4];
				tmpDeets.PartName = details[5];
				tmpDeets.pickedUp = Boolean.parseBoolean(details[6]);
				orderArray = updateArray(tmpDeets, orderArray);
			}
			myReader.close();
		}
		catch (FileNotFoundException ex) {
			return ;
		}
		catch (IOException ex) {
			System.out.println("DataBase could not be Accessed!");
			return ;
		}
	}

	public static OrderDetails []updateArray(OrderDetails order, OrderDetails orderArr[])
	{
		if (orderArr == null)
		orderArr = new OrderDetails[1];
		int	n = orderArr.length;
	
		if (n == 1 && orderArr[0] == null)
		{
			orderArr[0] = order;
			return (orderArr);
		}
		OrderDetails newarr[] = new OrderDetails[n + 1];
		for (int i = 0; i < n; i++)
			newarr[i] = orderArr[i];
	
		newarr[n] = order;
		orderArr = newarr;
		return (orderArr);
	}

	static Scanner sc = new Scanner(System.in);
	public static OrderDetails addOrder()
	{
		OrderDetails 	tmpDeets = new OrderDetails();

		tmpDeets.setdateOrder();
		tmpDeets.setOrderNum();
		tmpDeets.setCustomerFirstName();
		tmpDeets.setCustomerLastName();
		tmpDeets.setPaymentMethod();
		tmpDeets.setPartName();
		tmpDeets.setpickedUp(false);
		try {
			myWriter = new FileWriter("orders.txt", true);
			bw = new BufferedWriter(myWriter);
			bw.write(tmpDeets.dateOrder + ":"
			+ tmpDeets.orderNum + ":"
			+ tmpDeets.CustomerFirstName + ":"
			+ tmpDeets.CustomerLastName + ":"
			+ tmpDeets.PaymentMethod + ":"
			+ tmpDeets.PartName + ":"
			+ tmpDeets.pickedUp);
			bw.newLine();
			bw.close();
			myWriter.close();
		}
    	catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		orderArray = updateArray(tmpDeets, orderArray);
		ViewOrders.printDetails(tmpDeets);
		return (tmpDeets);
	}
	
	public static void main( String args[] ) {
		String	Exit;

		importData();
		while (true)
		{
			System.out.println("Options: ");
			System.out.println("A to Add");
			System.out.println("S to Search");
			System.out.println("P to Print all orders");
			System.out.println("M to Modify order");
			System.out.println("D to Delete order");
			System.out.println("E to Exit");
			Exit = sc.nextLine();
			if (Exit.equals("A"))
				addOrder();
			else if (Exit.equals("S"))
				ViewOrders.searchOrder();
			else if (Exit.equals("P"))
				ViewOrders.printAll();
			else if (Exit.equals("M"))
				ModifyOrder.modifyOrders();
			else if (Exit.equals("D"))
				deleteData();
			else if (Exit.equals("E"))
				break ;
			else
			System.out.println("Please Choose a Valid Option!");
		}
		sc.close();
	}
}

class ModifyOrder extends AddOrders {

	public static void modifyOrders( )
	{
		OrderDetails	choice = compareArrays();

		if (choice == null)
			return ;
		while (true)
		{
			System.out.println("What field would you like to modify?");
			System.out.println("F for First Name");
			System.out.println("L for Last Name");
			System.out.println("P for Part Name");
			System.out.println("S for Status of order");
			System.out.println("M for payment Method");
			System.out.println("E to Exit to main menu");
			String input = sc.nextLine();
			switch(input) {
				case "F":
				{
					choice.setCustomerFirstName();
					break ;
				}
				case "L":
				{
					choice.setCustomerLastName();
					break ;
				}
				case "P":
				{
					choice.setPartName();
					break ;
				}
				case "S":
				{
					choice.setpickedUp(!choice.pickedUp);
					break ;
				}
				case "M":
				{
					choice.setPaymentMethod();
					break ;
				}
				case "E":
				{
					return ;
				}
				default:
				{
					System.out.println("Invalid Selection!");
				}
			}
			ViewOrders.printDetails(choice);
			int	i;

			for (i = 0; i < orderArray.length; i++)
			{
				if (choice.orderNum.equals(orderArray[i].orderNum) && choice.dateOrder.equals(orderArray[i].dateOrder))
				{
					orderArray[i] = choice;
					break ;
				}
			}
			exportData();
		}
	}
}

class GenerateRandom extends AddOrders {	

	public static boolean checkUnused(int orderNum)
	{
		if (orderArray == null)
			return (true);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDateTime now = LocalDateTime.now();
		String currentDate = dtf.format(now);

		for (int i = 0; i < orderArray.length; i++)
		{
			if (currentDate.equals(orderArray[i].dateOrder))
			{
				if (orderNum == Integer.parseInt(orderArray[i].orderNum))
				{
					if (i + 1 == orderArray.length)
					{
						System.out.println("No more orders can be Added!");
						System.exit(1);
					}
					return (false);
				}
			}
		} 
		return (true);
	}
	
	public static int getRandomOrderNum(boolean genuine)
	{
		Random	rand = new Random();
		int		end_digits;
		int		first_digit = 1;
		int		final_value;
		
		if (!genuine)
		{
			while (first_digit == 1)
			first_digit = rand.nextInt(10);
		}
		end_digits = rand.ints(10000, 100000).findFirst().getAsInt();
		final_value = first_digit * 100000 + end_digits;
		if (checkUnused(final_value))
		{
			return (final_value);
		}
		else
			return (getRandomOrderNum(genuine));
	}
	
}

class ViewOrders extends AddOrders {
	
	public static void printAll()
	{
		if (orderArray == null)
		{
			searchOrder();
			return ;
		}
		printDetails(orderArray);
	}

	public static void printDetails(OrderDetails order[])
	{
		System.out.println("********************************************");
		for (int i = 0; i < order.length; i++)
		{
			System.out.print(order[i].dateOrder + ", ");
			System.out.print(order[i].orderNum + ", ");
			System.out.print(order[i].CustomerFirstName + ", ");
			System.out.print(order[i].CustomerLastName + ", ");
			System.out.print(order[i].PaymentMethod + ", ");
			System.out.print(order[i].PartName + ", ");
			System.out.print(order[i].pickedUp + "\n");
		}
		System.out.println("********************************************");
	}

	public static void printDetails(OrderDetails order)
	{
		System.out.println("********************************************");
		System.out.println("Order Date: " + order.dateOrder);
		System.out.println("Order Number: " + order.orderNum);
		System.out.println("Customer First Name: " + order.CustomerFirstName);
		System.out.println("Customer Last Name: " + order.CustomerLastName);
		System.out.println("Payment Method: " + order.PaymentMethod);
		System.out.println("Part Name: " + order.PartName);
		System.out.println("Picked Up: " + order.pickedUp);
		System.out.println("********************************************");
	}

	public static void findOrder( String orderNum ) {
		boolean flag = false;

		for (int i = 0; i < orderArray.length; i++)
		{
			if (orderArray[i].orderNum.equals(orderNum))
			{
				printDetails(orderArray[i]);
				flag = true;
			}
		}
		if (!flag)
			System.out.println("There is no order matching the input details.");
	}
	
	public static void searchOrder()
	{
		if (orderArray == null)
		{
			System.out.println("No orders found in database.");
			return ;
		}
		System.out.print("Enter the order number you are searching for: ");
		findOrder(sc.nextLine());
	}
}

class OrderDetails extends AddOrders {
	String	orderNum;
	String	CustomerFirstName;
	String	CustomerLastName;
	String	PartName;
	String	PaymentMethod;
	String	dateOrder;
	boolean	pickedUp;

	public boolean forbiddenChar( String input )
	{
		if (input.indexOf(":") != -1)
		{
			System.out.println("Forbidden Character Found!");
			return (false);
		}
		return (true);
	}

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

	public void setpickedUp( boolean picked ){
		this.pickedUp = picked;
	}

	public void setdateOrder( ){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDateTime now = LocalDateTime.now();
		this.dateOrder = dtf.format(now);
	}
}
