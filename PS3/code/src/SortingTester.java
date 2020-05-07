import org.w3c.dom.ls.LSOutput;

import javax.crypto.spec.PSource;
import java.util.concurrent.ThreadLocalRandom;

public class SortingTester {

//	for (int i = 0; i < size; i++) {
//		testArray[i] = new KeyValuePair(1, i);
//	}
//
//		sorter.sort(testArray);
//
//		for (int i = 0; i < size; i++) {
//		if (testArray[i].getValue() != i) {
//			return false;
//		}
//	}
//
//	KeyValuePair[] testArray2 = new KeyValuePair[size];

	// array created must have elements of equal keys but different values
//		for (int i = 1; i < size-1; i++) {
//		testArray2[0] = new KeyValuePair(2, 4);
//		testArray2[i] = new KeyValuePair(1, i);
//
//		testArray2[size-1] = new KeyValuePair(2, 3);
//	}
//
//		sorter.sort(testArray2);
//
//		for (int i = 0; i < size-2; i++) {
//		if (testArray2[size-2].getValue() != 4 || testArray2[size-1].getValue() != 3) {
//			return false;
//		}
//		if (testArray2[i].getValue() != i+1) {
//			return false;
//		}
//	}
//	  	return true;


	public static boolean checkSort(ISort sorter, int size){
		//TODO: implement this.

		int randomNum;

		KeyValuePair[] testArray = new KeyValuePair[size];

		for (int i = 0; i < size; i++) {
			randomNum = ThreadLocalRandom.current().nextInt(1, size + 1);

			testArray[i] = new KeyValuePair(randomNum, 10);
		}

		sorter.sort(testArray);

		for (int i = 0; i < size-1; i++) {

			if (testArray[i].getKey() > testArray[i+1].getKey()) {
				return false;
			}
		}

    	return true;
	}

	public static boolean isStable(ISort sorter, int size){
		//TODO: implement this.
		// stable means the order of equal key values are not mixed up after sorting,
		// ie the order of the values are not changed
		// check that the order of equal keys are not changed

		KeyValuePair[] testArray = new KeyValuePair[size];
		int randomNum;

		// array created must have elements of equal keys but different values
		for (int i = 0; i < size; i++) {
			randomNum = ThreadLocalRandom.current().nextInt(1, (size + 1)/2);
			testArray[i] = new KeyValuePair(randomNum, i);
		}

		sorter.sort(testArray);

		for (int i = 0; i < size-1; i++) {
			if (testArray[i].getKey() == testArray[i+1].getKey()) {
				if (testArray[i].getValue() > testArray[i+1].getValue()) {
					return false;
				}
			}
		}
		return true;
  	}

  	public static KeyValuePair[] generateArrWithRandomKeys(int size) {
		KeyValuePair[] testArr = new KeyValuePair[size];
		int randomNum;
		for (int i = 0; i < size; i++) {
			randomNum = ThreadLocalRandom.current().nextInt(1, 1000);
			testArr[i] = new KeyValuePair(randomNum, 1);
		}
		return testArr;
	}

	public static boolean testArrays(int numOfIterations, int randomArrSizeBound) {
		int randomNum;
		ISort sortingObject = new SorterC();

		for (int i = 0; i < numOfIterations; i++) {
			randomNum = ThreadLocalRandom.current().nextInt(1, randomArrSizeBound);
			if (SortingTester.checkSort(sortingObject, randomNum) == false) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args){
		//TODO: implement some sorting tests
		ISort sortingObject = new SorterD();
		System.out.println(checkSort(sortingObject, 10));

	}
}
