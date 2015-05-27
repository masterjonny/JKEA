package jkea;

/**
 * Thresholds to use when comparing floating-point values and when sampling
 * statistics.
 */
public class TestThresholds {

	/**
	 * The number of samples or trials to run when collecting statistical data.
	 */
	public static final int SAMPLES = 10000;

	/**
	 * The floating-point threshold when checking statistical results.
	 */
	public static final double STATISTICS_EPS = 0.05;

	/**
	 * Private constructor to prevent instantiation.
	 */
	private TestThresholds() {
		super();
	}

}
