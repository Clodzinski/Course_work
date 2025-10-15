package week6;
import java.util.Scanner;

public class lab2 {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		while(true) {
			System.out.println("HashCode for strings");
			System.out.print("Enter string to get hashcode: ");
			String str = input.nextLine();
			System.out.println(hashCodeForString(str));
			System.out.println("Continue(0/1): ");
			int end = input.nextInt();
			if (end == 1) {
				break;
			}
		}
	}

	
	public static int hashCodeForString(String s) {
		int b = 31;
		int hashCode = 0;
		for (int i = 0; i < s.length(); i++) { 
			hashCode = b * hashCode + (int)s.charAt(i); 
		}
		return hashCode;
	}
}
