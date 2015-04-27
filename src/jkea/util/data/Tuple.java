package jkea.util.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class Tuple implements Iterable<Object>, Serializable,
		Comparable<Tuple> {

	private static final long serialVersionUID = 5431085632328343101L;

	private final Object[] valueArray;
	private final List<Object> valueList;

	protected Tuple(final Object... values) {
		super();
		valueArray = values;
		valueList = Arrays.asList(values);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int compareTo(final Tuple o) {

		final int tLen = valueArray.length;
		final Object[] oValues = o.valueArray;
		final int oLen = oValues.length;

		for (int i = 0; (i < tLen) && (i < oLen); i++) {

			final Comparable tElement = (Comparable) valueArray[i];
			final Comparable oElement = (Comparable) oValues[i];

			final int comparison = tElement.compareTo(oElement);
			if (comparison != 0)
				return comparison;

		}

		return (Integer.valueOf(tLen)).compareTo(Integer.valueOf(oLen));

	}

	public final boolean contains(final Object value) {
		for (final Object val : valueList)
			if (val == null) {
				if (value == null)
					return true;
			} else if (val.equals(value))
				return true;
		return false;
	}

	public final boolean containsAll(final Collection<?> collection) {
		for (final Object value : collection)
			if (!contains(value))
				return false;
		return true;
	}

	public final boolean containsAll(final Object... values) {
		if (values == null)
			throw new IllegalArgumentException("Values array cannot be null");
		for (final Object value : values)
			if (!contains(value))
				return false;
		return true;
	}

	@Override
	public final boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Tuple other = (Tuple) obj;
		return valueList.equals(other.valueList);
	}

	public abstract int getSize();

	public final Object getValue(final int pos) {
		if (pos >= getSize())
			throw new IllegalArgumentException("Cannot retrieve position "
					+ pos + " in " + this.getClass().getSimpleName()
					+ ". Positions for this class start with 0 and end with "
					+ (getSize() - 1));
		return valueArray[pos];
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result)
				+ ((valueList == null) ? 0 : valueList.hashCode());
		return result;
	}

	public final int indexOf(final Object value) {
		int i = 0;
		for (final Object val : valueList) {
			if (val == null) {
				if (value == null)
					return i;
			} else if (val.equals(value))
				return i;
			i++;
		}
		return -1;
	}

	@Override
	public final Iterator<Object> iterator() {
		return valueList.iterator();
	}

	public final int lastIndexOf(final Object value) {
		for (int i = getSize() - 1; i >= 0; i--) {
			final Object val = valueList.get(i);
			if (val == null) {
				if (value == null)
					return i;
			} else if (val.equals(value))
				return i;
		}
		return -1;
	}

	public final Object[] toArray() {
		return valueArray.clone();
	}

	public final List<Object> toList() {
		return Collections.unmodifiableList(new ArrayList<Object>(valueList));
	}

	@Override
	public final String toString() {
		return valueList.toString();
	}

}