import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.*;

/* Generates random numbers. */
abstract class GenerateRandom extends PartsMenu {	

	/* Checks to see if the randomly generated number has already been used
	 * in that same day.
	 */
	public static boolean checkUnused(int orderNum)
	{
		if (orderArray == null)
			return (true);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDateTime now = LocalDateTime.now();
		String currentDate = dtf.format(now);

		for (int i = 0; i < orderArray.length; i++)
		{
			if (currentDate.equals(orderArray[i].getdateOrder()))
			{
				if (orderNum == Integer.parseInt(orderArray[i].getOrderNum()))
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
	
	/* Generates a random 6 digit number. If genuine is true, it will begin
	 * with a 1. Otherwise it will start with anything but 1.
	 */
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