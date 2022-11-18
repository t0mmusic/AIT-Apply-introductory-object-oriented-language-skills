import javax.swing.*;

import java.awt.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class Gui extends OrderDetails {

	private static JFrame			frame = new JFrame("Spare Parts Store");
	private static JPanel			addPanel = new JPanel();
	private static JPanel			lowerPanel = new JPanel();
	private static JPanel			buttonPanel = new JPanel();
	private static JLabel			info = new JLabel();
	private static GridLayout 		menuLayout = new GridLayout(13,1);
	private static GridLayout 		listLayout = new GridLayout(1,1);
	private static GridLayout 		buttonLayout = new GridLayout(1,3);
	private static JLabel			fNameLabel = new JLabel("Enter First Name");
	private static JLabel			lNameLabel = new JLabel("Enter Last Name");
	private static JLabel			pNameLabel = new JLabel("Enter Part Name");
	private static JLabel			pMethodLabel = new JLabel("Enter Payment Method");
	private static JLabel			title = new JLabel("Fill in the fields below or select an order from the list.");
	private static JTextField		firstName = new JTextField(null);
	private static JTextField		lastName = new JTextField(null);
	private static JTextField		partName = new JTextField(null);
	private static JTextField		paymentMethod = new JTextField(null);
	private static JRadioButton		genuinePart = new JRadioButton("Genuine Part");
	private static JRadioButton		pickedUp = new JRadioButton("Picked Up");
	private static JButton			addButton = new JButton("Add new order");
	private static JButton			removeButton = new JButton("Delete order");
	private static JButton			modifyButton = new JButton("Modify order");
	private static JList<String>	orderList;
	private static JScrollPane		scrollableList;

	public static String []orderToStrings( )
	{
		if (orderArray == null)
			return (null);
		String orders[] = new String[orderArray.length];
		for (int i = 0; i < orders.length; i++)
		{
			orders[i] = orderToString(orderArray[i]);
		}
		return (orders);
	}

	public static String orderToString(OrderDetails order)
	{
		return (order.getdateOrder() + " : " +
			order.getOrderNum() + " : " +
			order.getCustomerFirstName() + " " +
			order.getCustomerLastName() + " : " +
			order.getPaymentMethod() + " : " +
			order.getPartName() + " : Picked up: " +
			order.getpickedUp());
	}

	public static void	addOrder( ) {
		Boolean			genuine = false;

		if (firstName.getText().equals("")
			|| lastName.getText().equals("")
			|| partName.getText().equals("")
			|| paymentMethod.getText().equals(""))
			{
				info.setText("Order could not be added. Check inputs.");
				return ;
			}
		if (genuinePart.isSelected())
			genuine = true;
		OrderDetails	newOrder = new OrderDetails(
			firstName.getText(),
			lastName.getText(),
			partName.getText(),
			paymentMethod.getText(),
			genuine);
			newOrder.setpickedUp(pickedUp.isSelected());

		if (ModifyOrder.addOrder(newOrder))
			info.setText("Successfully added order.");
		else
			info.setText("Order could not be added. Check inputs.");

	}

	public static OrderDetails ModifyOrder(OrderDetails order) {
		order.setCustomerFirstName(firstName.getText());
		order.setCustomerLastName(lastName.getText());
		order.setPartName(partName.getText());
		order.setPaymentMethod(paymentMethod.getText());
		order.setpickedUp(pickedUp.isSelected());
		return (order);
	}

	public static JList<String> updateScroll(boolean first) {
		if (orderArray == null)
		{
			lowerPanel.remove(scrollableList);
			lowerPanel.updateUI();
			return (null);
		}
		if (!first)
		{
			lowerPanel.remove(scrollableList);
			lowerPanel.updateUI();
		}
		orderList = new JList<>(orderToStrings());
		scrollableList = new JScrollPane(orderList);
		lowerPanel.add(scrollableList);
		lowerPanel.updateUI();
		orderList.updateUI();
		orderList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent evt) {

				int i = orderList.getSelectedIndex();

				firstName.setText(orderArray[i].getCustomerFirstName());
				lastName.setText(orderArray[i].getCustomerLastName());
				partName.setText(orderArray[i].getPartName());
				paymentMethod.setText(orderArray[i].getPaymentMethod());
				pickedUp.setSelected(orderArray[i].getpickedUp());
				if (orderArray[i].getOrderNum().charAt(0) == '1')
					genuinePart.setSelected(true);
				else
					genuinePart.setSelected(false);
            }
        });
		return (orderList);
	}

	public static JList<String> updateGui( ) {
		if (orderArray == null)
		{
			scrollableList = new JScrollPane();
		}
		else
		{
			orderList = updateScroll(true);
		}
		scrollableList.setSize(500, 500);
		addPanel.setLayout(menuLayout);
		buttonPanel.setLayout(buttonLayout);
		lowerPanel.setLayout(listLayout);
		addPanel.add(title);
		addPanel.add(fNameLabel);
		addPanel.add(firstName);
		addPanel.add(lNameLabel);
		addPanel.add(lastName);
		addPanel.add(pNameLabel);
		addPanel.add(partName);
		addPanel.add(pMethodLabel);
		addPanel.add(paymentMethod);
		addPanel.add(genuinePart);
		addPanel.add(pickedUp);
		addPanel.add(info);
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		buttonPanel.add(modifyButton);
		addPanel.add(BorderLayout.NORTH, buttonPanel);
		frame.getContentPane().add(BorderLayout.NORTH, addPanel);
		frame.getContentPane().add(BorderLayout.SOUTH, lowerPanel);
		frame.setVisible(true);
		return (orderList);
	}

	public static void viewModify( ) {
		addButton.addActionListener(e ->
		{
			addOrder();
			orderList = updateScroll(false);
		});

		removeButton.addActionListener(e ->
		{
			if (orderList == null)
			{
				info.setText("No order selected!");
				return ;
			}
			int i = orderList.getSelectedIndex();
			if (i < 0)
			{
				info.setText("No order selected!");
				return ;
			}
			orderArray = DeleteOrders.removeElement(orderArray, i);
			PartsDatabase.exportData();
			orderList = updateScroll(false);
			info.setText("Order Successfully Deleted!");
		});

		modifyButton.addActionListener(e ->
		{
			if (orderList == null)
			{
				info.setText("No order selected!");
				return ;
			}
			int i = orderList.getSelectedIndex();
			if (i < 0)
			{
				info.setText("No order selected!");
				return ;
			}
			orderArray[i] = ModifyOrder(orderArray[i]);
			PartsDatabase.exportData();
			orderList = updateScroll(false);
			info.setText("Order Successfully Modified!");
		});
		
	}

	public static void main(String args[]){
		PartsDatabase.importData();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,600);
		frame.setVisible(true);
		updateGui();
		viewModify();
	}
}