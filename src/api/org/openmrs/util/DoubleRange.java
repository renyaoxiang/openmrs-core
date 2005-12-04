package org.openmrs.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Represents a bounded or unbounded numeric range.
 * By default the range is closed on the low end and open on the high end: mathematically "[low, high)".
 * @author djazayeri
 */
public class DoubleRange implements Comparable<DoubleRange> {

	protected Log log = LogFactory.getLog(getClass());
	
	private Double low;
	private Double high;
	private boolean closedLow = true; //TODO: add setters and getters for these
	private boolean closedHigh = false;
	
	public DoubleRange() { }
	
	public DoubleRange(Double low, Double high) {
		this.low = low == null ? new Double(Double.NEGATIVE_INFINITY) : low;
		this.high = high == null ? new Double(Double.POSITIVE_INFINITY) : high;
	}

	/**
	 * @return Returns the high.
	 */
	public Double getHigh() {
		return high;
	}

	/**
	 * @param high The high to set.
	 */
	public void setHigh(Double high) {
		this.high = high == null ? new Double(Double.POSITIVE_INFINITY) : high;
	}

	/**
	 * @return Returns the low.
	 */
	public Double getLow() {
		return low;
	}

	/**
	 * @param low The low to set.
	 */
	public void setLow(Double low) {
		this.low = low == null ? new Double(Double.NEGATIVE_INFINITY) : low;
	}

	/**
	 * first sorts according to low-bound (ascending) then according to high-bound (descending)
	 */
 	public int compareTo(DoubleRange other) {
		int temp = low.compareTo(other.low);
		if (temp == 0) {
			temp = other.high.compareTo(high); 
		}
		log.debug(this + (temp < 0 ? " < " : (temp > 0 ? " > " : " = ")) + other);
		return temp;
 	}
	
	public boolean contains(double d) {
		if (low != null) {
			if (closedLow) {
				if (d < low) {
					return false;
				}
			} else {
				if (d <= low) {
					return false;
				}
			}
		}
		if (high != null) {
			if (closedHigh) {
				if (d > high) {
					return false;
				}
			} else {
				if (d >= high) {
					return false;
				}
			}
		}
		return true;
	}
	
	public String toString() {
		StringBuffer ret = new StringBuffer();
		if (low != null) {
			ret.append(">");
			if (closedLow) {
				ret.append("=");
			}
			ret.append(" " + Format.format(low));
			if (high != null) {
				ret.append(" and ");
			}
		}
		if (high != null) {
			ret.append("<");
			if (closedHigh) {
				ret.append("=");
			}
			ret.append(" " + Format.format(high));
		}
		return ret.toString();
	}

	public boolean equals(Object o) {
		DoubleRange other = (DoubleRange) o;
		return low.equals(other.low) && high.equals(other.high);
	}
	
}
