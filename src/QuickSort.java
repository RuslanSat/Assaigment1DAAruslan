package src;
import java.util.Random;
public class QuickSort {
    private static final Random RNG = new Random(1234);

    public static <T extends Comparable<T>> void sort(T[] a, Metrics m) {
        if (a == null || a.length <= 1) return;
        m.startTimer();
        shuffle(a);
        quicksort(a, 0, a.length - 1, m);
        m.stopTimer();
    }

    private static <T extends Comparable<T>> void shuffle(T[] a) {
        for (int i = a.length - 1; i > 0; i--) {
            int j = RNG.nextInt(i + 1);
            T tmp = a[i]; a[i] = a[j]; a[j] = tmp;
        }
    }

    private static <T extends Comparable<T>> void quicksort(T[] a, int lo, int hi, Metrics m) {
        while (lo < hi) {
            m.pushDepth();
            int pivotIdx = lo + RNG.nextInt(hi - lo + 1);
            T pivot = a[pivotIdx];
            swap(a, pivotIdx, hi);

            int p = partition(a, lo, hi, pivot, m);

            int leftSize = p - lo;
            int rightSize = hi - p;

            if (leftSize < rightSize) {
                quicksort(a, lo, p - 1, m);
                lo = p + 1;
            } else {
                quicksort(a, p + 1, hi, m);
                hi = p - 1;
            }
            m.popDepth();
        }
    }

    private static <T extends Comparable<T>> int partition(T[] a, int lo, int hi, T pivot, Metrics m) {
        int store = lo;
        for (int i = lo; i < hi; i++) {
            m.addComparison();
            if (a[i].compareTo(pivot) < 0) {
                T tmp = a[store]; a[store] = a[i]; a[i] = tmp;
                m.addSwap();
                store++;
            }
        }
        T tmp = a[store]; a[store] = a[hi]; a[hi] = tmp;
        m.addSwap();
        return store;
    }

    private static <T> void swap(T[] a, int i, int j) {
        T tmp = a[i]; a[i] = a[j]; a[j] = tmp;
    }
}
