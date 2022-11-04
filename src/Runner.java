import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Runner {
	public static void main(String[] args) {
		// Gets user's input for a file of integers
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to Inversion Counter. What file you like to count the inversions for? ");
		String fileName = scan.nextLine();

		ArrayList<ArrayList<Integer>> dataSet = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> fileData = new ArrayList<Integer>(); // Arraylist initialized to hold file data
		String input;
		try {
			File file = new File(fileName);
			Scanner reader = new Scanner(file);

			// Loops through lines of the file
			while (reader.hasNextLine()) {
				input = reader.nextLine();
				String[] arrayInput = input.split(" "); // Split string by space

				// Loops through the array and converts them to integers
				for (String str: arrayInput) {
					fileData.add(Integer.parseInt(str));
				}
				// Add this lines data to the full data set
				dataSet.add(new ArrayList<>(fileData));
				fileData.clear();
			}
			reader.close();
		}
		catch (FileNotFoundException e) { // File opening error
			System.out.println("Could not open file");
		}
		catch (NumberFormatException e) {  // Integer parsing error
			System.out.println("Could not parse integer");
		}

		// Runs the program on each data set
		InversionReturn returned;
		for(int i = 0; i < dataSet.size(); i++) {
			returned = SortAndCount(new InversionReturn(dataSet.get(i)));
			System.out.println("Inversion count: " + returned.inversionCount + ".  Sorted array: " + returned.list + ".");
		}
	}

	/**
	 * Sorts integers by splitting a list and recursively putting it in order. Also counts the number of inversions that
	 * occur during this process.
	 */
	static InversionReturn SortAndCount(InversionReturn L) {
		// Base case
		if (L.list.size() == 1) {
			return new InversionReturn(L.list, 0);
		}
		int half = L.list.size() / 2; // Determines what index to split L on

		InversionReturn A;
		InversionReturn B;

		// Recursively sorts the 2 halves
		if (L.list.size() == 2) {
			A = SortAndCount(new InversionReturn(new ArrayList<>(L.list.subList(0, 1))));
			B = SortAndCount(new InversionReturn(new ArrayList<>(L.list.subList(1, 2))));
		}
		else {
			A = SortAndCount(new InversionReturn(new ArrayList<>(L.list.subList(0, half))));
			B = SortAndCount(new InversionReturn(new ArrayList<>(L.list.subList(half, L.list.size()))));
		}
		// Merges the two halves
		InversionReturn toReturn = MergeAndCount(A.list, B.list);

		// Add up all inversions
		toReturn.inversionCount = A.inversionCount + B.inversionCount + toReturn.inversionCount;

		return toReturn;
	}

	/**
	 * Merges 2 lists in chronological order while counting the number of inversions it takes
	 */
	static InversionReturn MergeAndCount(ArrayList<Integer> A, ArrayList<Integer> B) {
		ArrayList<Integer> mergedList = new ArrayList<Integer>(); // The merge list that is returned
		int count = 0; // Initialize the inversion counter to 0

		// Initialize variables used in the while loop
		int AInt, BInt;

		// While loop goes until either A or B has all been added to the merged list
		while (!(A.isEmpty()) && !(B.isEmpty())) {
			AInt = A.get(0);
			BInt = B.get(0);
			if (BInt < AInt) { // if B int is smaller
				mergedList.add(BInt);
				B.remove(0);
				count += A.size(); // counter is incremented because there is an inversion
			}
			else { // No inversion found
				mergedList.add(AInt);
				A.remove(0);
			}
		}

		// Merges rest of the list that still has ints to be added
		if (A.isEmpty()) {
			mergedList.addAll(B);
		}
		else {
			mergedList.addAll(A);
		}

		// Creates specialized return type to return the merged list and inversion counts
		return new InversionReturn(mergedList, count);
	}
}
