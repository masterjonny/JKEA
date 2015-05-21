package jkea.core;

import java.util.ArrayList;

import jkea.util.cipher.Cipher;
import jkea.util.data.LeakPoint;

public interface Distinguisher {

	double[][] updatePrior(double[][] prior, final short[] plain,
			final double[] leak, Cipher cipher, ArrayList<LeakPoint> templates);

}
