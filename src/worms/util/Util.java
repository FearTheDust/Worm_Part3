package worms.util;

public class Util {
	public static final double DEFAULT_EPSILON = 1e-4;

	/**
	 * This function replaces % with more precision and is meant to be used when working with modulo Math.PI.
	 * This function returns the rest of the dividend / divisor.
	 * 
	 * @param dividend The dividend
	 * @param divisor The divisor
	 * 
	 * @return  The rest of dividend/divisor.
	 * 
	 * @return If dividend or divisor is Not a Number this returns Double.NaN
	 * 			| if(Double.isNaN(dividend) || Double.isNaN(divisor)) then
     *			| result == Double.NaN
     *
     * @return If dividend is infinite Double.NaN is returned.
     * 			| if(Double.isInfinite(dividend)) then
     *			| result == Double.NaN;	
     *
     * @return If dividend is not infinite and divisor is infinite 0 is returned.
     * 			| if(!Double.isInfinite(dividend) && Double.isInfinite(divisor))
     * 			| result == 0;
     * 
     * @return If divisor is 0 or 1 the dividend is returned.
     * 			| if(Util.fuzzyEquals(divisor, 0, 1E-9) || Util.fuzzyEquals(divisor, 1, 1E-9))
     * 			| result == dividend;
	 */
    public static double modulo(double dividend, double divisor) {
    	if(Double.isNaN(dividend) || Double.isNaN(divisor))
    		return Double.NaN;
    	
    	if(Double.isInfinite(dividend))
    		return Double.NaN;
    	
    	if(!Double.isInfinite(dividend) && Double.isInfinite(divisor))
    		return 0;
    		
    	if(Util.fuzzyEquals(divisor, 0, 1E-9) || Util.fuzzyEquals(divisor, 1, 1E-9))
    		return dividend;
    	
    	while(Math.abs(dividend / divisor) >= 1) {
    		if(dividend / divisor >= 1) {
    			dividend -= divisor;
    		} else if(dividend / divisor <= -1) {
    			dividend += divisor;
    		}
    	}
    	return dividend;
    }
    
	/**
	 * Returns true if x == y (with precision DEFAULT_EPSILON)
	 */
	public static boolean fuzzyEquals(double x, double y) {
		return fuzzyEquals(x, y, DEFAULT_EPSILON);
	}

	/**
	 * Returns true if x == y (with precision eps)
	 */
	public static boolean fuzzyEquals(double x, double y, double eps) {
		if (Double.isNaN(x) || Double.isNaN(y))
			return false;
		return Math.abs(x - y) <= eps
				|| Double.valueOf(x).equals(Double.valueOf(y));
	}

	/**
	 * Returns true if x <= y (with precision DEFAULT_EPSILON)
	 */
	public static boolean fuzzyLessThanOrEqualTo(double x, double y) {
		return fuzzyLessThanOrEqualTo(x, y, DEFAULT_EPSILON);
	}
	
	/**
	 * Returns true if a <= x <= b (with precision DEFAULT_EPSILON)
	 */
	public static boolean fuzzyBetween(double a, double b, double x) {
		return fuzzyBetween(a, b, x, DEFAULT_EPSILON);
	}
	
	/**
	 * Returns true if a <= x <= b (with precision eps)
	 */
	public static boolean fuzzyBetween(double a, double b, double x, double eps) {
		return fuzzyLessThanOrEqualTo(a, x, eps) && fuzzyLessThanOrEqualTo(x, b, eps);
	}

	/**
	 * Returns true if x <= y (with precision eps)
	 */
	public static boolean fuzzyLessThanOrEqualTo(double x, double y, double eps) {
		if (fuzzyEquals(x, y, eps)) {
			return true;
		} else {
			return Double.compare(x, y) < 0;
		}
	}

	/**
	 * Returns true if x >= y (with precision DEFAULT_EPSILON)
	 */
	public static boolean fuzzyGreaterThanOrEqualTo(double x, double y) {
		return fuzzyGreaterThanOrEqualTo(x, y, DEFAULT_EPSILON);
	}

	/**
	 * Returns true if x >= y (with precision eps)
	 */
	public static boolean fuzzyGreaterThanOrEqualTo(double x, double y,
			double eps) {
		if (fuzzyEquals(x, y, eps)) {
			return true;
		} else {
			return Double.compare(x, y) > 0;
		}
	}

	/**
	 * Returns the absolute error |expected - actual|
	 */
	public static double absoluteError(double expected, double actual) {
		return Math.abs(expected - actual);
	}

	/**
	 * Returns the relative error |expected - actual|/|expected|
	 */
	public static double relativeError(double expected, double actual) {
		if(excpected == 0)
			return Double.NaN;
		return absoluteError(expected, actual) / Math.abs(expected);
	}
	
	/**
	 * Retrieve a deepclone of the provided 'matrix'.
	 * @param matrix The 2-dimensional boolean array to clone.
	 * 
	 * @return The cloned 2-dimensional array.
	 * 
	 * @return If matrix is a null reference returns a null reference.
	 * 			| if(matrix == null) result == null
	 */
	public static boolean[][] deepClone(boolean[][] matrix) {
		if(matrix == null)
			return null;
			
		boolean[][] result = new boolean[matrix.length][];
		
		for(int row = 0; row < matrix.length; row++) {
			result[row] = matrix[row].clone();
		}
		
		return result;
	}

}
