/* Author: Christopher Lodzinski
 * Date: January 30, 2025
 * Purpose: This program will as the user for how many quarts a painting job will require. If the user inputs anything other 
 * than an integer the program will catch the error and ask the user to input an integer. The program will then calculate 
 * how many gallons and any remaining quarts the user will need for the job. The error that will be caught will be 
 * InputMismatchException. 
 */


import java.util.*;

public class QuartsToGallons {

	public static void main(String[] args) {
		// Global variable for how many quarts go into a gallon
		final int NUMBER_OF_QUARTS = 4;
		// Declaring the variable paintingJobQuarts. 
		int paintingJobQuarts;
		// Declaring the Scanner variable for the program
		Scanner input = new Scanner(System.in);
		
		// While loop to keep the user in the input section until they get an integer.
		while (true) {
			try {
				// Asking the user how many quarts of paint they need for the job.
				System.out.println("How many quarts of paint do you need for the painting job? ");
				// Saving user input into variable paintingJobQuarts.
				paintingJobQuarts = input.nextInt();
				break;			
			}
			// Catching InputMismatchException error
			catch (InputMismatchException e) {
				// Telling the user there is an error and to input an integer.
				System.out.println("Error! Please enter an integer.");
				input.nextLine();
			}
		}
		
		// Calculating how many gallons and remainder of paint from the user input of quarts.
		int gallons = paintingJobQuarts / NUMBER_OF_QUARTS;
		int quarts = paintingJobQuarts % NUMBER_OF_QUARTS;
		// Displaying how many quarts the user inputted and how many gallons and remainder will be needed.
		System.out.println("A painting job that needs to use " + paintingJobQuarts + " quarts requires "
				+ gallons + " gallons and " + quarts + " quarts of paint.");
	}
}
