package src;
import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {

    public static class Point {
        public final double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }

    public static double closest(Point[] pts) {
        if (pts == null || pts.length < 2) return Double.POSITIVE_INFINITY;
        Point[] px = pts.clone();
        Arrays.sort(px, Comparator.comparingDouble(p -> p.x));
        Point[] aux = new Point[px.length];
        return recur(px, aux, 0, px.length - 1);
    }

    private static double recur(Point[] px, Point[] aux, int lo, int hi) {
        if (hi - lo <= 3) return brute(px, lo, hi);
        int mid = (lo + hi) >>> 1;
        double midx = px[mid].x;
        double dl = recur(px, aux, lo, mid);
        double dr = recur(px, aux, mid + 1, hi);
        double d = Math.min(dl, dr);

        int i = lo, j = mid + 1, k = lo;
        while (i <= mid && j <= hi) {
            if (px[i].y <= px[j].y) aux[k++] = px[i++];
            else aux[k++] = px[j++];
        }
        while (i <= mid) aux[k++] = px[i++];
        while (j <= hi) aux[k++] = px[j++];
        for (k = lo; k <= hi; k++) px[k] = aux[k];

        int m = 0;
        for (k = lo; k <= hi; k++) {
            if (Math.abs(px[k].x - midx) < d) aux[m++] = px[k];
        }
        for (i = 0; i < m; i++) {
            for (j = i + 1; j < m && (aux[j].y - aux[i].y) < d; j++) {
                d = Math.min(d, dist(aux[i], aux[j]));
            }
        }
        return d;
    }

    private static double brute(Point[] a, int lo, int hi) {
        double best = Double.POSITIVE_INFINITY;
        for (int i = lo; i <= hi; i++) {
            for (int j = i + 1; j <= hi; j++) {
                best = Math.min(best, dist(a[i], a[j]));
            }
        }
        return best;
    }

    private static double dist(Point a, Point b) {
        double dx = a.x - b.x, dy = a.y - b.y;
        return Math.hypot(dx, dy);
    }
}
