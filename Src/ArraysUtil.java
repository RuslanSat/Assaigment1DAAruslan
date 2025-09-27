package Src;

import java.util.Random;

public final class ArraysUtil {
    private static final Random RNG = new Random(42);

    private ArraysUtil() {}

    public static <T> void swap(T[] a, int i, int j) {
        T tmp = a[i]; a[i] = a[j]; a[j] = tmp;
    }

    public static void shuffle(int[] a) {
        for (int i = a.length - 1; i > 0; i--) {
            int j = RNG.nextInt(i + 1);
            int t = a[i]; a[i] = a[j]; a[j] = t;
        }
    }

    public static <T extends Comparable<T>> void insertionSort(T[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            T key = a[i];
            int j = i - 1;
            while (j >= lo && a[j].compareTo(key) > 0) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }
}
