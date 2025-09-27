package src;

public class MergeSort {
    private static final int CUTOFF = 32;

    public static <T extends Comparable<T>> void sort(T[] a, Metrics m) {
        if (a == null || a.length <= 1) return;
        @SuppressWarnings("unchecked")
        T[] buf = (T[]) new Comparable[a.length];
        m.startTimer();
        sort(a, buf, 0, a.length - 1, m);
        m.stopTimer();
    }

    private static <T extends Comparable<T>> void sort(T[] a, T[] buf, int lo, int hi, Metrics m) {
        if (hi - lo + 1 <= CUTOFF) {
            ArraysUtil.insertionSort(a, lo, hi);
            return;
        }
        int mid = lo + ((hi - lo) >> 1);
        sort(a, buf, lo, mid, m);
        sort(a, buf, mid + 1, hi, m);

        // skip merge if already ordered
        if (a[mid].compareTo(a[mid + 1]) <= 0) return;

        int i = lo, j = mid + 1, k = lo;
        while (i <= mid || j <= hi) {
            if (i > mid) buf[k++] = a[j++];
            else if (j > hi) buf[k++] = a[i++];
            else {
                m.addComparison();
                if (a[i].compareTo(a[j]) <= 0) buf[k++] = a[i++];
                else buf[k++] = a[j++];
            }
        }
        for (k = lo; k <= hi; k++) a[k] = buf[k];
    }
}
