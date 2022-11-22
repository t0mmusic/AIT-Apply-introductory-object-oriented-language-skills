/* Allows user to modify existing orders. */
abstract class ModifyOrder extends PartsMenu {

	public static boolean addOrder(OrderDetails order)
	{
		if (order.getCustomerFirstName() != null
			&& order.getCustomerLastName() != null
			&& order.getPartName() != null
			&& order.getPaymentMethod() != null)
		{
			PartsDatabase.updateDatabase(order);
			return (true);
		}
		System.out.println("Order not valid!");
		return(false);

	}

	/* Compares an order ID to the database to see if it exists.
	 * If it does, it will check to see if there are two orders
	 * with the same ID from different dates and ask the user which
	 * of these they would like to select.
	 */
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
			if (order.equals(orderArray[i].getOrderNum()))
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

	/* Allows user to add a new order. */
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
		PartsDatabase.updateDatabase(tmpDeets);
		return (tmpDeets);
	}

	/* Allows user to edit an order. */
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
					choice.setpickedUp(!choice.getpickedUp());
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
				if (choice.getOrderNum().equals(orderArray[i].getOrderNum())
					&& choice.getdateOrder().equals(orderArray[i].getdateOrder()))
				{
					orderArray[i] = choice;
					break ;
				}
			}
			PartsDatabase.exportData();
		}
	}
}
