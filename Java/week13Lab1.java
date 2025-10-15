/* Author: Christopher Lodzinski 
 * Date: 5/5/2025
 * Purpose: We were given a portion of code and we were asked to change it so it asks the user for a binary number and then convert it to a decimal
 * using stream's reduce method. 
 */

package Week13;
import java.util.*;

public class week13Lab1 {
	public static void main(String[] args) {
		// Asking user for a binary number
		System.out.print("Enter a binary number: ");
		Scanner input = new Scanner(System.in);
		String binary = input.nextLine();

		// Storing the binary number in an chararray
		char[] binaryChar = binary.toCharArray();
		ArrayList<Character> list = new ArrayList<>();
		for (char c: binaryChar) {
			list.add(c);
		}
		
		// Using streams reduce method to determine the decimal for the user's binary number
		int dec = list.stream().mapToInt(ch -> ch - '0').reduce(0,  (acc, val) -> acc * 2 + val);
		System.out.println("Decimal value: " + dec);
		}
	}