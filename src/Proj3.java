import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Proj3 {
    // Sorting Method declarations
    // Merge Sort
    public static <T extends Comparable> void mergeSort(ArrayList<T> a, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(a, left, mid);
            mergeSort(a, mid + 1, right);
            merge(a, left, mid, right);
        }
    }

    public static <T extends Comparable> void merge(ArrayList<T> a, int left, int mid, int right) {
        ArrayList<T> temp = new ArrayList<>(right - left + 1);
        int i = left, j = mid + 1;

        while (i <= mid && j <= right) {
            if (a.get(i).compareTo(a.get(j)) <= 0) {
                temp.add(a.get(i++));
            } else {
                temp.add(a.get(j++));
            }
        }

        while (i <= mid) {
            temp.add(a.get(i++));
        }

        while (j <= right) {
            temp.add(a.get(j++));
        }

        for (i = left; i <= right; i++) {
            a.set(i, temp.get(i - left));
        }
    }

    // Quick Sort
    public static <T extends Comparable> void quickSort(ArrayList<T> a, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(a, left, right);
            quickSort(a, left, pivotIndex - 1);
            quickSort(a, pivotIndex + 1, right);
        }
    }

    public static <T extends Comparable> int partition (ArrayList<T> a, int left, int right) {
        T pivot = a.get(right);
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (a.get(j).compareTo(pivot) <= 0) {
                i++;
                swap(a, i, j);
            }
        }
        swap(a, i + 1, right);
        return i + 1;
    }

    static <T> void swap(ArrayList<T> a, int i, int j) {
        T temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }

    // Heap Sort
    public static <T extends Comparable> void heapSort(ArrayList<T> a, int left, int right) {
        for (int i = (right - left) / 2; i >= left; i--) {
            heapify(a, i, right);
        }

        for (int i = right; i > left; i--) {
            swap(a, left, i);
            heapify(a, left, i - 1);
        }
    }

    public static <T extends Comparable> void heapify (ArrayList<T> a, int left, int right) {
        int largest = left;
        int leftChild = 2 * left + 1;
        int rightChild = 2 * left + 2;

        if (leftChild <= right && a.get(leftChild).compareTo(a.get(largest)) > 0) {
            largest = leftChild;
        }

        if (rightChild <= right && a.get(rightChild).compareTo(a.get(largest)) > 0) {
            largest = rightChild;
        }

        if (largest != left) {
            swap(a, left, largest);
            heapify(a, largest, right);
        }
    }

    // Bubble Sort
    public static <T extends Comparable> int bubbleSort(ArrayList<T> a, int size) {
        boolean swapped;
        int comparisons = 0;
        for (int i = 0; i < size - 1; i++) {
            swapped = false;
            for (int j = 0; j < size - i - 1; j++) {
                comparisons++;
                if (a.get(j).compareTo(a.get(j + 1)) > 0) {
                    swap(a, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
        return comparisons;
    }

    // Odd-Even Transposition Sort
    public static <T extends Comparable> int transpositionSort(ArrayList<T> a, int size) {
        boolean sorted = false;
        int comparisons = 0;
        while (!sorted) {
            sorted = true;
            for (int i = 1; i < size - 1; i += 2) {
                comparisons++;
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                    swap(a, i, i + 1);
                    sorted = false;
                }
            }
            for (int i = 0; i < size - 1; i += 2) {
                comparisons++;
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                    swap(a, i, i + 1);
                    sorted = false;
                }
            }
        }
        return comparisons;
    }

    public static void main(String [] args)  throws IOException {

        // clear the sorted list, so that we can overwrite
        try (PrintWriter sortedWriter = new PrintWriter(new FileOutputStream("sorted.txt", false))) {
            sortedWriter.println("");
        }

        if (args.length != 3) {
            System.err.println("Usage: java TestAvl <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        String sortingType = args[1];
        int numLines = Integer.parseInt(args[2]);

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();

        ArrayList<Song> dataList = new ArrayList<>();

        int count = 0;
        // Read data from the file
        for (int i = 0; i < numLines && inputFileNameScanner.hasNextLine(); i++) {
            String line = inputFileNameScanner.nextLine();
            String[] parts = line.split(",");

            // Trim whitespace from each part
            for (int j = 0; j < parts.length; j++) {
                parts[j] = parts[j].trim();
            }

            try {
                String trackName = parts[0];
                String artistName = parts[1];
                int releaseYear = Integer.parseInt(parts[2]);
                long streams = Long.parseLong(parts[3]);
                int bpm = Integer.parseInt(parts[4]);
                int danceability = Integer.parseInt(parts[5]);
                dataList.add(new Song(trackName, artistName, releaseYear, streams, bpm, danceability));
            } catch (NumberFormatException e) {
            }
        }

        inputFileNameScanner.close();

        // sort shuffled and reverse the dataset
        ArrayList<Song> sortedList = new ArrayList<>(dataList);
        ArrayList<Song> randomizedList = new ArrayList<>(dataList);
        ArrayList<Song> reverseList = new ArrayList<>(dataList);
        Collections.sort(sortedList);
        Collections.shuffle(randomizedList);
        Collections.sort(reverseList, Collections.reverseOrder());

        long startTime, endTime;
        int sortedComparison = 0;
        int shuffleComparison = 0;
        int reverseComparison = 0;

        long sorted = 0;
        long shuffle = 0;
        long reverse = 0;

        // Sorted List
        System.out.println("Sorting sorted list...");
        switch (sortingType.toLowerCase()) {
            case "bubble":
                startTime = System.nanoTime();
                sortedComparison = bubbleSort(sortedList, sortedList.size());
                endTime = System.nanoTime();
                break;
            case "merge":
                startTime = System.nanoTime();
                mergeSort(sortedList, 0, sortedList.size() - 1);
                endTime = System.nanoTime();
                break;
            case "quick":
                startTime = System.nanoTime();
                quickSort(sortedList, 0, sortedList.size() - 1);
                endTime = System.nanoTime();
                break;
            case "heap":
                startTime = System.nanoTime();
                heapSort(sortedList, 0, sortedList.size() - 1);
                endTime = System.nanoTime();
                break;
            case "transposition":
                startTime = System.nanoTime();
                sortedComparison = transpositionSort(sortedList, sortedList.size());
                endTime = System.nanoTime();
                break;
            default:
                System.out.println("Invalid sorting algorithm type");
                return;
        }
        sorted = endTime - startTime;
        System.out.println("Sorted List - Number of lines evaluated: " + sortedList.size());
        System.out.println("Time taken (nanoseconds): " + sorted);
        if (sortingType.equals("bubble") || sortingType.equals("transposition")) {
            System.out.println("Number of comparisons: " + sortedComparison);
        }

        // Reverse List
        System.out.println("Sorting reverse list...");
        switch (sortingType.toLowerCase()) {
            case "bubble":
                startTime = System.nanoTime();
                reverseComparison = bubbleSort(reverseList, reverseList.size());
                endTime = System.nanoTime();
                break;
            case "merge":
                startTime = System.nanoTime();
                mergeSort(reverseList, 0, reverseList.size() - 1);
                endTime = System.nanoTime();
                break;
            case "quick":
                startTime = System.nanoTime();
                quickSort(reverseList, 0, reverseList.size() - 1);
                endTime = System.nanoTime();
                break;
            case "heap":
                startTime = System.nanoTime();
                heapSort(reverseList, 0, reverseList.size() - 1);
                endTime = System.nanoTime();
                break;
            case "transposition":
                startTime = System.nanoTime();
                reverseComparison = transpositionSort(reverseList, reverseList.size());
                endTime = System.nanoTime();
                break;
            default:
                System.out.println("Invalid sorting algorithm type");
                return;
        }
        reverse = endTime - startTime;
        System.out.println("Reverse List - Number of lines evaluated: " + reverseList.size());
        System.out.println("Time taken (nanoseconds): " + reverse);
        if (sortingType.equals("bubble") || sortingType.equals("transposition")) {
            System.out.println("Number of comparisons: " + reverseComparison);
        }

        // Randomized List
        System.out.println("Sorting randomized list...");
        switch (sortingType.toLowerCase()) {
            case "bubble":
                startTime = System.nanoTime();
                shuffleComparison = bubbleSort(randomizedList, randomizedList.size());
                endTime = System.nanoTime();
                break;
            case "merge":
                startTime = System.nanoTime();
                mergeSort(randomizedList, 0, randomizedList.size() - 1);
                endTime = System.nanoTime();
                break;
            case "quick":
                startTime = System.nanoTime();
                quickSort(randomizedList, 0, randomizedList.size() - 1);
                endTime = System.nanoTime();
                break;
            case "heap":
                startTime = System.nanoTime();
                heapSort(randomizedList, 0, randomizedList.size() - 1);
                endTime = System.nanoTime();
                break;
            case "transposition":
                startTime = System.nanoTime();
                shuffleComparison = transpositionSort(randomizedList, randomizedList.size());
                endTime = System.nanoTime();
                break;
            default:
                System.out.println("Invalid sorting algorithm type");
                return;
        }
        shuffle = endTime - startTime;
        System.out.println("Randomized List - Number of lines evaluated: " + randomizedList.size());
        System.out.println("Time taken (nanoseconds): " + shuffle);
        if (sortingType.equals("bubble") || sortingType.equals("transposition")) {
            System.out.println("Number of comparisons: " + shuffleComparison);
        }

        // printing the sorted lists for all three lists to the sorted.txt
        try (PrintWriter writer = new PrintWriter(new FileOutputStream("sorted.txt", true))) {
            writer.println("Sorted List after sorting");
            for (Song song : sortedList) {
                writer.println(song);
            }
            writer.println("Shuffled List after sorting");
            for (Song song : randomizedList) {
                writer.println(song);
            }
            writer.println("Reverse Sorted List after sorting");
            for (Song song : reverseList) {
                writer.println(song);
            }
        }

        // write the information in the analysis file
        try (PrintWriter writers = new PrintWriter(new FileOutputStream("analysis.txt", true))) {
        writers.println("sorting type: " + sortingType);
        writers.println("numLines: " + numLines);
        if (sortingType.equals("bubble") || sortingType.equals("transposition")) {
            writers.println("numbers of swaps for sorted list: " + sortedComparison);
            writers.println("numbers of swaps for shuffled list: " + shuffleComparison);
            writers.println("numbers of swaps for shuffled list: " + reverseComparison);
        }
        writers.println("SortedList Running times: " + sorted);
        writers.println("RandomizedList Running times: " + shuffle);
        writers.println("ReverseList Running times: " + reverse);

    }

    }
}
