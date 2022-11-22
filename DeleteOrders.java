/* Removes orders from array and database. */
abstract class DeleteOrders extends PartsMenu {

	/* Allows the user of the command line program to delete an order. */
	public static void deleteData()
	{
		OrderDetails choice = ModifyOrder.compareArrays();
		int	i;

		if (choice == null)
			return ;
		for (i = 0; i < orderArray.length; i++)
		{
			if (choice.getOrderNum().equals(orderArray[i].getOrderNum())
				&& choice.getdateOrder().equals(orderArray[i].getdateOrder()))
				break ;
		}
		System.out.println("Confirm Delete? y/n");
		if (sc.nextLine().equals("y"))
		{
			orderArray = removeElement(orderArray, i);
			PartsDatabase.exportData();
		}
	}

	/* Removes an object from orderArray using the index of the array. */
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
}