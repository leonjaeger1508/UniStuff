import java.util.Arrays;

public class KDTree {

	class KDNode {
		int dim;
		double t;
		int point;

		// inner node
		public KDNode(int dim, double t) {
			this.dim = dim;
			this.t = t;
			this.point = -1;
		}

		// leaf
		public KDNode(int point) {
			this.dim = -1;
			this.t = 0;
			this.point = point;
		}
	}

	KDNode[] nodes;
	double[][] data;

	public KDTree(double[][] data) {
		this.data = data;
		nodes = new KDNode[NextPowerOfTwo(data[0].length)];
		build(data);
	}

	static int NextPowerOfTwo(int n) {
		// Berechne für eine positive ganze zahl n
		// die nächste Zweierpotenz (z.B. 19 -> 32)
		if (n <= 0) {
			return 1;
		}
		int p = 1;
		while (p < n) {
			p *= 2;
		}
		return p;

		// FIXME
	}

	static double median(double[] data) {
		// Berechne den Median, d.h. den 'mittleren'
		// Wert eines Arrays, z.B.
		//
		// 10, -3, 4, 8, 6 -> Median 6.
		//
		// Erzeugen Sie sich hierfür eine Kopie der Daten
		// (z.B. mit System.arraycopy) und sortieren Sie
		// die Kopie (mit Arrays.sort).

		double[] dataCpy = new double[data.length];
		System.arraycopy(data, 0, dataCpy, 0, data.length);
		Arrays.sort(dataCpy);

		int mid = dataCpy.length / 2;
		if (dataCpy.length % 2 == 0) {

			return (dataCpy[mid - 1] + dataCpy[mid]) / 2;
		} else {

			return dataCpy[mid];
		}

		// FIXME
	}

	public void build(double[][] data) {
		// Empfehlung: Überladen Sie build() mit einer
		// rekursiven Variante build(double[][] data, ...)
		// mit weiteren Parametern.
		// Rufen Sie hier nur diese interne build()-Methode auf.

		build(data, 0, 0, data[0].length - 1);
		// FIXME
	}

	private void build(double[][] data, int nodeIndex, int start, int end) {
		if (start > end) {
			return;
		}

		int dim = nodeIndex % data.length;
		double[] values = new double[end - start + 1];
		for (int i = start; i <= end; i++) {
			values[i - start] = data[dim][i];
		}
		double t = median(values);

		int medianIndex = start + (end - start) / 2;
		nodes[nodeIndex] = new KDNode(dim, t);

		build(data, 2 * nodeIndex + 1, start, medianIndex - 1);
		build(data, 2 * nodeIndex + 2, medianIndex + 1, end);
	}

	static double dist(double[] p1, double[] p2) {
		// Berechnet die Euklidische Distanz zweier Punkte.

		double sum = 0;
		for (int i = 0; i < p1.length; i++) {
			sum += Math.pow(p2[i] - p1[i], 2);
		}
		sum = Math.sqrt(sum);

		return sum;

		// FIXME
	}

	double searchGreedy(double[] query) {
		// FIXME
	}

	double searchBacktracking(double[] query) {
		// Empfehlung: Überladen Sie searchBacktracking() mit einer
		// rekursiven Variante searchBacktracking(double[] query, ...)
		// mit weiteren Parametern.
		// Rufen Sie hier nur diese interne searchBacktracking()-Methode auf.

		// FIXME
	}

	public static void main(String[] args) {
		// Beispiel vom Übungsblatt.
		double data[][] = new double[][] { { 4, 7, 2, 5, 1 }, { 3, 5, 7, 1, 2 } };
		KDTree kdt = new KDTree(data);
		double result = kdt.searchGreedy(new double[] { 4, 2 });
	}
}
