

import org.junit.Test;

import static org.junit.Assert.assertEquals;



public class KDTreeTest {

	static double tolerance = 0.0001;

	@Test
	public void testNextPowerOfTwo1() {
		assertEquals( 32, KDTree.NextPowerOfTwo(32));
		assertEquals( 32, KDTree.NextPowerOfTwo(31));
		assertEquals( 1, KDTree.NextPowerOfTwo(-3));
	}

	@Test
	public void testNextPowerOfTwo2() {
		assertEquals( 32, KDTree.NextPowerOfTwo(32));
		assertEquals( 32, KDTree.NextPowerOfTwo(31));
		assertEquals( 32, KDTree.NextPowerOfTwo(23));
	}
	
	@Test
	public void testDist() {
		assertEquals( 5.0, KDTree.dist(new double[] {1,3}, new double[] {1,8}), tolerance);
		assertEquals( Math.sqrt(2), KDTree.dist(new double[] {4,7}, new double[] {5,6}), tolerance);
	}

	@Test
	public void testMedian1() {
		assertEquals( 5.0, KDTree.median(new double[] {1,3,5,7,9}), tolerance);
		assertEquals( 7.0, KDTree.median(new double[] {9,8,7,2,1}), tolerance);
	}

	@Test
	public void testMedian2() {
		assertEquals( 5.0, KDTree.median(new double[] {5,1,9,7,2}), tolerance);
	}

	@Test
	public void testMedian3() {
		assertEquals( 6.0, KDTree.median(new double[] {5,1,9,7,2,8}), tolerance);
	}

	@Test
	public void testBuild1() {
		// Baum vom Übungsblatt
		double data[][] = new double[][] { {4,7,2,5,1}, {3,5,7,1,2} };
		KDTree kdt = new KDTree(data);
		assertEquals( 16, kdt.nodes.length );
		assertEquals(  0, kdt.nodes[1].dim);
		assertEquals(  1, kdt.nodes[2].dim);
		assertEquals(  1, kdt.nodes[3].dim);
		assertEquals(  0, kdt.nodes[7].dim);
		assertEquals(  4.0, kdt.nodes[1].t, tolerance);
		assertEquals(  4.5, kdt.nodes[2].t, tolerance);
		assertEquals(  3.0, kdt.nodes[3].t, tolerance);
		assertEquals(  5.5, kdt.nodes[7].t, tolerance);
		assertEquals(  4, kdt.nodes[4].point);
		assertEquals(  2, kdt.nodes[5].point);
		assertEquals(  3, kdt.nodes[6].point);
		assertEquals(  0, kdt.nodes[14].point);
		assertEquals(  1, kdt.nodes[15].point);	
	}

	@Test
	public void testBuild2() {
		// Ein einfaches 3D-Beispiel
		double data[][] = new double[][] { {1,2,3,4,5,6,7,8}, 
			                               {1,2,3,4,5,6,7,8}, 
			                               {1,2,3,4,5,6,7,8} };
		KDTree kdt = new KDTree(data);
		assertEquals( 16, kdt.nodes.length );
		
		assertEquals(  0, kdt.nodes[1].dim);
		assertEquals(  1, kdt.nodes[2].dim);
		assertEquals(  1, kdt.nodes[3].dim);
		assertEquals(  2, kdt.nodes[4].dim);
		assertEquals(  2, kdt.nodes[5].dim);
		assertEquals(  2, kdt.nodes[6].dim);
		assertEquals(  2, kdt.nodes[7].dim);
		
		assertEquals(  4.5, kdt.nodes[1].t, 0.001);
		assertEquals(  2.5, kdt.nodes[2].t, 0.001);
		assertEquals(  6.5, kdt.nodes[3].t, 0.001);
		assertEquals(  1.5, kdt.nodes[4].t, 0.001);
		assertEquals(  3.5, kdt.nodes[5].t, 0.001);
		assertEquals(  5.5, kdt.nodes[6].t, 0.001);
		assertEquals(  7.5, kdt.nodes[7].t, 0.001);
		
		assertEquals(  0, kdt.nodes[8].point);
		assertEquals(  1, kdt.nodes[9].point);
		assertEquals(  2, kdt.nodes[10].point);
		assertEquals(  3, kdt.nodes[11].point);
		assertEquals(  4, kdt.nodes[12].point);
		assertEquals(  5, kdt.nodes[13].point);
		assertEquals(  6, kdt.nodes[14].point);
		assertEquals(  7, kdt.nodes[15].point);
	}


}
