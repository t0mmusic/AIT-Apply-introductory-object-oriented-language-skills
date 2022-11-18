import java.util.*;
import java.io.FileWriter;
import java.io.BufferedWriter;

class PartsMenu {
	protected static OrderDetails	orderArray[];
	protected static GenerateRandom	orderNum;
	protected static FileWriter		myWriter;
	protected static BufferedWriter	bw;
	protected static Scanner 		sc = new Scanner(System.in);

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


	public static void main(String args[]) {
		String	Exit;

		PartsDatabase.importData();
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
				ModifyOrder.addOrder();
			else if (Exit.equals("S"))
				ViewOrders.searchOrder();
			else if (Exit.equals("P"))
				ViewOrders.printAll();
			else if (Exit.equals("M"))
				ModifyOrder.modifyOrders();
			else if (Exit.equals("D"))
				DeleteOrders.deleteData();
			else if (Exit.equals("E"))
				break ;
			else
			System.out.println("Please Choose a Valid Option!");
		}
		sc.close();
	}
}
