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
		build(data);
	}
	
	static int NextPowerOfTwo(int n) {
		int power = 1;	
		while(power < n) {
			power *= 2;	
		}
		return power;
	}

	static double median(double[] data) {
		
		double [] dataCpy =new double [data.length];
		System.arraycopy(data, 0, dataCpy, 0, data.length);
		Arrays.sort(dataCpy);
		
		int mid = dataCpy.length /2;
		if (dataCpy.length % 2 == 0) {
			 
			return (dataCpy[mid - 1] + dataCpy[mid]) / 2;
		} else {
		    	
			return dataCpy[mid];
		}
		
	}

	public void build(double[][] data) {
		
		
		
		// Empfehlung: Überladen Sie build() mit einer
		// rekursiven Variante build(double[][] data, ...)
		// mit weiteren Parametern.
		// Rufen Sie hier nur diese interne build()-Methode auf.

		// FIXME
	}
	static double dist(double[] p1, double[] p2) {
		// Berechnet die Euklidische Distanz zweier Punkte.
		
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
		double data[][] = new double[][] { {4,7,2,5,1}, {3,5,7,1,2} };
		KDTree kdt = new KDTree(data);
		double result = kdt.searchGreedy(new double[] {4,2});
	}
}
