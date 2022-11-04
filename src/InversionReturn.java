import java.util.ArrayList;

/**
 * A specialized return type for this project so that algorithms can return a sorted list and then count of inversions
 * it took to generate that list
 */
public class InversionReturn {
	public ArrayList<Integer> list;
	public int inversionCount;

	/**
	 * Constructor
	 * @param list the sorted list for this return type
	 * @param inversionCount the number of inversion it has taken so far to generate the sorted list
	 */
	InversionReturn(ArrayList<Integer> list, int inversionCount) {
		this.list = list;
		this.inversionCount = inversionCount;
	}

	/**
	 * Alternative constructor sets the inversion counter to 0
	 */
	InversionReturn(ArrayList<Integer> list) {
		this.list = list;
		this.inversionCount = 0;
	}
}
