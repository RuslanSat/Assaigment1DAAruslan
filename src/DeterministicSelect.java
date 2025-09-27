package src;

public class DeterministicSelect {

    public static <T extends Comparable<T>> T select(T[] a, int k, Metrics m) {
        if (a == null || k < 0 || k >= a.length) throw new IllegalArgumentException("k out of range");
        m.startTimer();
        T res = select(a, 0, a.length - 1, k, m);
        m.stopTimer();
        return res;
    }

    private static <T extends Comparable<T>> T select(T[] a, int lo, int hi, int k, Metrics m) {
        while (true) {
            if (lo == hi) return a[lo];
            int pivotIndex = pivotOfMedians(a, lo, hi, m);
            pivotIndex = partition(a, lo, hi, pivotIndex, m);
            if (k == pivotIndex) return a[k];
            else if (k < pivotIndex) hi = pivotIndex - 1;
            else lo = pivotIndex + 1;
        }
    }

    private static <T extends Comparable<T>> int pivotOfMedians(T[] a, int lo, int hi, Metrics m) {
        int n = hi - lo + 1;
        if (n <= 5) {
            insertionSort(a, lo, hi, m);
            return lo + n / 2;
        }
        int numMedians = 0;
        for (int i = lo; i <= hi; i += 5) {
            int subHi = Math.min(i + 4, hi);
            insertionSort(a, i, subHi, m);
            int median = i + (subHi - i) / 2;
            swap(a, lo + numMedians, median);
            numMedians++;
        }
        return pivotOfMedians(a, lo, lo + numMedians - 1, m);
    }

    private static <T extends Comparable<T>> int partition(T[] a, int lo, int hi, int pivotIndex, Metrics m) {
        T pivot = a[pivotIndex];
        swap(a, pivotIndex, hi);
        int store = lo;
        for (int i = lo; i < hi; i++) {
            m.addComparison();
            if (a[i].compareTo(pivot) < 0) {
                swap(a, store, i);
                m.addSwap();
                store++;
            }
        }
        swap(a, store, hi);
        m.addSwap();
        return store;
    }

    private static <T extends Comparable<T>> void insertionSort(T[] a, int lo, int hi, Metrics m) {
        for (int i = lo + 1; i <= hi; i++) {
            T key = a[i];
            int j = i - 1;
            while (j >= lo) {
                m.addComparison();
                if (a[j].compareTo(key) > 0) {
                    a[j + 1] = a[j];
                    j--;
                } else break;
            }
            a[j + 1] = key;
        }
    }

    private static <T> void swap(T[] a, int i, int j) {
        T t = a[i]; a[i] = a[j]; a[j] = t;
    }
}
