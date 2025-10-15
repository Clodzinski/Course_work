import java.util.LinkedList;
import java.util.Iterator;

public class bookTester {
    public static void main(String[] args) {
        // Create a linked list and add 5 million integers
        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < 5000000; i++) {
            linkedList.add(i);
        }

        // Measure the time to traverse the list using an iterator
        long startTime = System.nanoTime();
        Iterator<Integer> iterator = linkedList.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        long endTime = System.nanoTime();
        long durationIterator = endTime - startTime;

        System.out.println("Start Time: " + startTime + " nanoseconds");
        System.out.println("Method: Using Iterator");
        System.out.println("Duration: " + durationIterator + " nanoseconds");

        // Measure the time to traverse the list using the get(index) method
        startTime = System.nanoTime();
        for (int i = 0; i < linkedList.size(); i++) {
            linkedList.get(i);
        }
        endTime = System.nanoTime();
        long durationGet = endTime - startTime;

        System.out.println("\nStart Time: " + startTime + " nanoseconds");
        System.out.println("Method: Using get(index)");
        System.out.println("Duration: " + durationGet + " nanoseconds");
    }
}