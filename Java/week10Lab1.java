/* Author: Christopher Lodzinski
 * Date: 4/13/2025	
 * Purpose: This program will ask the user for any string/input, and in return the program will display the hash code for that string.
 * The program will ask the user if they want to continue once the hash code is shown. 0 will continue and 1 will end the program.
 */

package week10;
import java.util.Scanner;

public class week10Lab1 {
	public static void main(String[] args) {
		// Giving scanner a variable
		Scanner input = new Scanner(System.in);
		
		// While loop to keep asking the user if they want to input another string.
		while(true) {
			// Displaying message to the user and asking for a string.
			System.out.println("HashCode for strings");
			System.out.println("Enter string to get HashCode: ");
			
			// Saving user input into variable str.
			String str = input.nextLine();
			
			// Displaying and running function hashCodeForString.
			System.out.println(hashCodeForString(str));
			System.out.println("Continue(0/1): ");
			
			// Saving user input if they wish to continue or not.
			int end = input.nextInt();
			input.nextLine();
			
			// Ending program if the user selects 1.
			if (end == 1) {
				break;
			}
		}
	}
	
	public static int hashCodeForString(String s) {
		// saving variables b and hashCode.
		int b = 31;
		int hashCode = 0;
		// Loop to run for each character in the string, for the length of the string.
		for (int i = 0; i < s.length(); i++) {
			hashCode = b * hashCode + (int)s.charAt(i);
		}
		// Returning calculated hash code
		return hashCode;
	}
}
