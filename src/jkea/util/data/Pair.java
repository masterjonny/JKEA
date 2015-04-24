package jkea.util.data;

import java.util.Collection;
import java.util.Iterator;

public final class Pair<A, B> extends Tuple {

	private static final long serialVersionUID = 2438099850625502138L;

	private static final int SIZE = 2;

	public static <X> Pair<X, X> fromArray(final X[] array) {
		if (array == null)
			throw new IllegalArgumentException("Array cannot be null");
		if (array.length != 2)
			throw new IllegalArgumentException(
					"Array must have exactly 2 elements in order to create a Pair. Size is "
							+ array.length);
		return new Pair<X, X>(array[0], array[1]);
	}
	public static <X> Pair<X, X> fromCollection(final Collection<X> collection) {
		return fromIterable(collection);
	}

	public static <X> Pair<X, X> fromIterable(final Iterable<X> iterable) {
		return fromIterable(iterable, 0, true);
	}

	public static <X> Pair<X, X> fromIterable(final Iterable<X> iterable,
			int index) {
		return fromIterable(iterable, index, false);
	}

	private static <X> Pair<X, X> fromIterable(final Iterable<X> iterable,
			int index, final boolean exactSize) {

		if (iterable == null)
			throw new IllegalArgumentException("Iterable cannot be null");

		boolean tooFewElements = false;

		X element0 = null;
		X element1 = null;

		final Iterator<X> iter = iterable.iterator();

		int i = 0;
		while (i < index) {
			if (iter.hasNext())
				iter.next();
			else
				tooFewElements = true;
			i++;
		}

		if (iter.hasNext())
			element0 = iter.next();
		else
			tooFewElements = true;

		if (iter.hasNext())
			element1 = iter.next();
		else
			tooFewElements = true;

		if (tooFewElements && exactSize)
			throw new IllegalArgumentException(
					"Not enough elements for creating a Pair (2 needed)");

		if (iter.hasNext() && exactSize)
			throw new IllegalArgumentException(
					"Iterable must have exactly 2 available elements in order to create a Pair.");

		return new Pair<X, X>(element0, element1);

	}

	public static <A, B> Pair<A, B> with(final A value0, final B value1) {
		return new Pair<A, B>(value0, value1);
	}

	private final A val0;

	private final B val1;

	public Pair(final A value0, final B value1) {
		super(value0, value1);
		this.val0 = value0;
		this.val1 = value1;
	}

	@Override
	public int getSize() {
		return SIZE;
	}

	public A getValue0() {
		return this.val0;
	}

	public B getValue1() {
		return this.val1;
	}
}