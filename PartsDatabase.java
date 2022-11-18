import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

class PartsDatabase extends PartsMenu {

	public static void exportData()
	{
		try {
			new FileWriter("orders.txt", false).close();
			if (orderArray == null)
			{
				return ;
			}
			myWriter = new FileWriter("orders.txt", true);
			bw = new BufferedWriter(myWriter);
			for (int i = 0; i < orderArray.length; i++)
			{
				bw.write(orderArray[i].getdateOrder() + ":"
				+ orderArray[i].getOrderNum() + ":"
				+ orderArray[i].getCustomerFirstName() + ":"
				+ orderArray[i].getCustomerLastName() + ":"
				+ orderArray[i].getPaymentMethod() + ":"
				+ orderArray[i].getPartName() + ":"
				+ orderArray[i].getpickedUp());
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
				tmpDeets.setdateOrder(details[0]);
				tmpDeets.setOrderNum(details[1]);
				tmpDeets.setCustomerFirstName(details[2]);
				tmpDeets.setCustomerLastName(details[3]);
				tmpDeets.setPaymentMethod(details[4]);
				tmpDeets.setPartName(details[5]);
				tmpDeets.setpickedUp(Boolean.parseBoolean(details[6]));
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

	public static void updateDatabase(OrderDetails tmpDeets)
	{
		try {
			myWriter = new FileWriter("orders.txt", true);
			bw = new BufferedWriter(myWriter);
			bw.write(tmpDeets.getdateOrder() + ":"
			+ tmpDeets.getOrderNum() + ":"
			+ tmpDeets.getCustomerFirstName() + ":"
			+ tmpDeets.getCustomerLastName() + ":"
			+ tmpDeets.getPaymentMethod() + ":"
			+ tmpDeets.getPartName() + ":"
			+ tmpDeets.getpickedUp());
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
	}
}