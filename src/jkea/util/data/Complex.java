package jkea.util.data;

public class Complex {

	public static Complex plus(Complex a, Complex b) {
		final double real = a.re + b.re;
		final double imag = a.im + b.im;
		final Complex sum = new Complex(real, imag);
		return sum;
	}

	private final double im; 

	private final double re; 

	public Complex(double real, double imag) {
		re = real;
		im = imag;
	}


	public double abs() {
		return Math.hypot(re, im);
	} 
	
	public Complex conjugate() {
		return new Complex(re, -im);
	}

	public Complex cos() {
		return new Complex(Math.cos(re) * Math.cosh(im), -Math.sin(re)
				* Math.sinh(im));
	}

	public Complex divides(Complex b) {
		final Complex a = this;
		return a.times(b.reciprocal());
	}

	public Complex exp() {
		return new Complex(Math.exp(re) * Math.cos(im), Math.exp(re)
				* Math.sin(im));
	}

	public double im() {
		return im;
	}

	public Complex minus(Complex b) {
		final Complex a = this;
		final double real = a.re - b.re;
		final double imag = a.im - b.im;
		return new Complex(real, imag);
	}

	public double phase() {
		return Math.atan2(im, re);
	} 
	
	public Complex plus(Complex b) {
		final Complex a = this; // invoking object
		final double real = a.re + b.re;
		final double imag = a.im + b.im;
		return new Complex(real, imag);
	}

	public double re() {
		return re;
	}

	public Complex reciprocal() {
		final double scale = (re * re) + (im * im);
		return new Complex(re / scale, -im / scale);
	}

	public Complex sin() {
		return new Complex(Math.sin(re) * Math.cosh(im), Math.cos(re)
				* Math.sinh(im));
	}

	public Complex tan() {
		return sin().divides(cos());
	}

	public Complex times(Complex b) {
		final Complex a = this;
		final double real = (a.re * b.re) - (a.im * b.im);
		final double imag = (a.re * b.im) + (a.im * b.re);
		return new Complex(real, imag);
	}

	public Complex times(double alpha) {
		return new Complex(alpha * re, alpha * im);
	}

	@Override
	public String toString() {
		if (im == 0)
			return re + "";
		if (re == 0)
			return im + "i";
		if (im < 0)
			return re + " - " + (-im) + "i";
		return re + " + " + im + "i";
	}
}
