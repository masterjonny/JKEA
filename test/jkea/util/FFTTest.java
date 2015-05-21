package jkea.util;

import static org.junit.Assert.*;
import jkea.util.data.Complex;

import org.junit.Before;
import org.junit.Test;

public class FFTTest {

	Complex x[];

	@Before
	public void setUp() throws Exception {
		x = new Complex[4];
		x[0] = new Complex(-0.03480425839330703, 0);
		x[1] = new Complex(0.07910192950176387, 0);
		x[2] = new Complex(0.7233322451735928, 0);
		x[3] = new Complex(0.1659819820667019, 0);
	}

	@Test
	public void fftTest() {
		Complex[] y = FFT.fft(x);
		assertEquals(y[0].re(), 0.9336118983487516, 1e-5);
		assertEquals(y[1].re(), -0.7581365035668999, 1e-5);
		assertEquals(y[2].re(), 0.44344407521182005, 1e-5);
		assertEquals(y[3].re(), -0.7581365035668999, 1e-5);
		assertEquals(y[0].im(), 0, 1e-5);
		assertEquals(y[1].im(), 0.08688005256493803, 1e-5);
		assertEquals(y[2].im(), 0, 1e-5);
		assertEquals(y[3].im(), -0.08688005256493803, 1e-5);
	}

	@Test
	public void ifftTest() {
		Complex[] y = FFT.ifft(FFT.fft(x));
		assertEquals(y[0].re(), -0.03480425839330703, 1e-5);
		assertEquals(y[1].re(), 0.07910192950176387, 1e-5);
		assertEquals(y[2].re(), 0.7233322451735928, 1e-5);
		assertEquals(y[3].re(), 0.1659819820667019, 1e-5);
		assertEquals(y[0].im(), 0, 1e-5);
		assertEquals(y[1].im(), 0.1659819820667019E-18, 1e-5);
		assertEquals(y[2].im(), 0, 1e-5);
		assertEquals(y[3].im(), -2.6599344570851287E-18, 1e-5);
	}

	@Test
	public void circularConvolveTest() {
		Complex[] y = FFT.cconvolve(x, x);
		assertEquals(y[0].re(), 0.5506798633981853, 1e-5);
		assertEquals(y[1].re(), 0.23461407150576394, 1e-5);
		assertEquals(y[2].re(), -0.016542951108772352, 1e-5);
		assertEquals(y[3].re(), 0.10288019294318276, 1e-5);
		assertEquals(y[0].im(), 0, 1e-5);
		assertEquals(y[1].im(), -4.033186818023279E-18, 1e-5);
		assertEquals(y[2].im(), 0, 1e-5);
		assertEquals(y[3].im(), 4.033186818023279E-18, 1e-5);
	}

	@Test
	public void convolveTest() {
		Complex[] y = FFT.convolve(x, x);
		assertEquals(y[0].re(), 0.001211336402308083, 1e-5);
		assertEquals(y[1].re(), -0.005506167987577068, 1e-5);
		assertEquals(y[2].re(), -0.044092969479563274, 1e-5);
		assertEquals(y[3].re(), 0.10288019294318276, 1e-5);
		assertEquals(y[4].re(), 0.5494685269958772, 1e-5);
		assertEquals(y[5].re(), 0.240120239493341, 1e-5);
		assertEquals(y[6].re(), 0.02755001837079092, 1e-5);
		assertEquals(y[7].re(), 0, 1e-5);

		assertEquals(y[0].im(), -3.122502256758253E-17, 1e-5);
		assertEquals(y[1].im(), -5.058885073636224E-17, 1e-5);
		assertEquals(y[2].im(), 2.1934338938072244E-18, 1e-5);
		assertEquals(y[3].im(), -3.6147323062478115E-17, 1e-5);
		assertEquals(y[4].im(), 3.122502256758253E-17, 1e-5);
		assertEquals(y[5].im(), 4.655566391833896E-17, 1e-5);
		assertEquals(y[6].im(), -2.1934338938072244E-18, 1e-5);
		assertEquals(y[7].im(), 4.01805098805014E-17, 1e-5);
	}

}
