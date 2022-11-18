class ViewOrders extends PartsMenu {
	
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
			System.out.print(order[i].getdateOrder() + ", ");
			System.out.print(order[i].getOrderNum() + ", ");
			System.out.print(order[i].getCustomerFirstName() + ", ");
			System.out.print(order[i].getCustomerLastName() + ", ");
			System.out.print(order[i].getPaymentMethod() + ", ");
			System.out.print(order[i].getPartName() + ", ");
			System.out.print(order[i].getpickedUp() + "\n");
		}
		System.out.println("********************************************");
	}

	public static void printDetails(OrderDetails order)
	{
		System.out.println("********************************************");
		System.out.println("Order Date: " + order.getdateOrder());
		System.out.println("Order Number: " + order.getOrderNum());
		System.out.println("Customer First Name: " + order.getCustomerFirstName());
		System.out.println("Customer Last Name: " + order.getCustomerLastName());
		System.out.println("Payment Method: " + order.getPaymentMethod());
		System.out.println("Part Name: " + order.getPartName());
		System.out.println("Picked Up: " + order.getpickedUp());
		System.out.println("********************************************");
	}

	public static void findOrder( String orderNum ) {
		boolean flag = false;

		for (int i = 0; i < orderArray.length; i++)
		{
			if (orderArray[i].getOrderNum().equals(orderNum))
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
