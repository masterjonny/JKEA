package jkea.util.data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ComplexTest {

	Complex a;
	Complex b;
	
	@Before
	public void setUp() throws Exception {
		a = new Complex(5.0, 6.0);
		b = new Complex(-3.0, 4.0);
	}

	@Test
	public void test() {
		assertEquals(a.re(), 5.0, 1e-5);
		assertEquals(a.im(), 6.0, 1e-5);
		assertEquals(Complex.plus(b, a).re(), 2.0, 1e-5);
		assertEquals(Complex.plus(b, a).im(), 10.0, 1e-5);
		assertEquals(a.minus(b).re(), 8.0, 1e-5);
		assertEquals(a.minus(b).im(), 2.0, 1e-5);
		assertEquals(a.times(b).re(), -39.0, 1e-5);
		assertEquals(a.times(b).im(), 2.0, 1e-5);
		assertEquals(b.times(a).re(), -39.0, 1e-5);
		assertEquals(b.times(a).im(), 2.0, 1e-5);
		assertEquals(a.divides(b).re(), 0.36, 1e-5);
		assertEquals(a.divides(b).im(), -1.52, 1e-5);
		assertEquals(a.divides(b).times(b).re(), 5.0, 1e-5);
		assertEquals(a.divides(b).times(b).im(), 6.0, 1e-5);
		assertEquals(a.conjugate().re(), 5.0, 1e-5);
		assertEquals(a.conjugate().im(), -6.0, 1e-5);
		assertEquals(a.abs(), 7.810249675906654, 1e-5);
		assertEquals(a.conjugate().tan().re(), -6.685231390246571E-6, 1e-5);
		assertEquals(a.conjugate().tan().im(), - 1.0000103108981198, 1e-5);
	}

}
