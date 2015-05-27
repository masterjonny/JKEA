package jkea.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Localization {

	/**
	 * Returns {@code true} if a localized string exists for the given key;
	 * {@code false} otherwise. This method automatically finds the correct
	 * resource bundle and key prefix appropriate for the class.
	 *
	 * @param type
	 *            the class requesting the localized string
	 * @param key
	 *            the key (minus the class prefix)
	 * @return {@code true} if a localized string exists for the given key;
	 *         {@code false} otherwise
	 */
	public static boolean containsKey(Class<?> type, String key) {
		return getLocalization(type).containsKey(
				type.getSimpleName() + "." + key);
	}

	/**
	 * Returns the localization object for the given class.
	 *
	 * @param type
	 *            the class requesting the localization object
	 * @return the localization object for the given class
	 */
	public static Localization getLocalization(Class<?> type) {
		return getLocalization(type.getPackage().getName());
	}

	/**
	 * Returns the localization object for the given class and locale.
	 *
	 * @param type
	 *            the class requesting the localization object
	 * @param locale
	 *            the target locale
	 * @return the localization object for the given class
	 */
	public static Localization getLocalization(Class<?> type, Locale locale) {
		return getLocalization(type.getPackage().getName(), locale);
	}

	/**
	 * Returns the localization object for the given package.
	 *
	 * @param packageName
	 *            the name of the package
	 * @return the localization object for the given package
	 */
	public static Localization getLocalization(String packageName) {
		return getLocalization(packageName, Locale.getDefault());
	}

	/**
	 * Returns the localization object for the given package and locale.
	 *
	 * @param packageName
	 *            the name of the package
	 * @param locale
	 *            the target locale
	 * @return the localization object for the given package
	 */
	public static Localization getLocalization(String packageName, Locale locale) {
		ResourceBundle bundle = null;

		try {
			bundle = ResourceBundle.getBundle(packageName + ".LocalStrings",
					locale);
		} catch (MissingResourceException e) {
			// bundle remains null, so the localization returns the key to
			// provide a visual clue for debugging
		}

		return new Localization(bundle);
	}

	/**
	 * Returns the localized string for the given key. This method automatically
	 * finds the correct resource bundle and key prefix appropriate for the
	 * class. For example, calling
	 * {@code Localization.getString(MainGUI.class, "title")} returns the
	 * localized string for the key {@code "MainGUI.title"}.
	 *
	 * @param type
	 *            the class requesting the localized string
	 * @param key
	 *            the key (minus the class prefix)
	 * @return the localized string for the given key
	 */
	public static String getString(Class<?> type, String key) {
		return getLocalization(type)
				.getString(type.getSimpleName() + "." + key);
	}

	/**
	 * Returns the localized string for the given key and formatting arguments.
	 * This method automatically finds the correct resource bundle and key
	 * prefix appropriate for the class. For example, calling
	 * {@code Localization.getString(MainGUI.class, "title")} returns the
	 * localized string for the key {@code "MainGUI.title"}.
	 *
	 * @param type
	 *            the class requesting the localized string
	 * @param key
	 *            the key (minus the class prefix)
	 * @param arguments
	 *            the formatting arguments
	 * @return the localized string for the given key
	 */
	public static String getString(Class<?> type, String key,
			Object... arguments) {
		return getLocalization(type).getString(
				type.getSimpleName() + "." + key, arguments);
	}

	/**
	 * The underlying resource bundle storing the key-value resource mapping.
	 */
	private final ResourceBundle bundle;

	/**
	 * Constructs a new localization instance based on the specified resource
	 * bundle. If this resource bundle is {@code null}, then the
	 * {@code getString} methods return the {@code key}.
	 *
	 * @param bundle
	 *            the resource bundle storing the key-value resource mappings
	 *            used by this localization object
	 */
	private Localization(ResourceBundle bundle) {
		super();
		this.bundle = bundle;
	}

	/**
	 * Returns {@code true} if a localized string exists for the given key;
	 * {@code false} otherwise.
	 *
	 * @param key
	 *            the key for the desired string
	 * @return {@code true} if a localized string exists for the given key;
	 *         {@code false} otherwise
	 */
	public boolean containsKey(String key) {
		if (bundle == null) {
			return false;
		}

		return bundle.containsKey(key);
	}

	/**
	 * Returns the underlying resource bundle.
	 *
	 * @return the underlying resource bundle
	 */
	public ResourceBundle getBundle() {
		return bundle;
	}

	/**
	 * Returns the locale of this localization.
	 *
	 * @return the locale of this localization
	 */
	public Locale getLocale() {
		return bundle.getLocale();
	}

	/**
	 * Returns the localized string for the given key. If the key is not found,
	 * then this methods returns the key itself.
	 *
	 * @param key
	 *            the key for the desired string
	 * @return the localized string for the given key
	 */
	public String getString(String key) {
		try {
			if (bundle == null) {
				return key;
			} else {
				return bundle.getString(key);
			}
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Returns the localized string for the given key and formatting arguments.
	 * This method uses {@link MessageFormat} for formatting the arguments. If
	 * the key is not found, then this methods returns the key itself.
	 *
	 * @param key
	 *            the key for the desired string
	 * @param arguments
	 *            the formatting arguments
	 * @return the localized string for the given key and formatting arguments
	 */
	public String getString(String key, Object... arguments) {
		MessageFormat format = new MessageFormat(getString(key), getLocale());

		return format.format(arguments, new StringBuffer(), null).toString();
	}

}