package de.hm.shop.user.integrationtest.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * 
 * @author Maximilian.Auch
 */
public class RegexMatcher extends TypeSafeMatcher<String> {

	private final String regex;


	/**
	 * 
	 * @param regex
	 */
	public RegexMatcher(final String regex) {
		this.regex = regex;
	}



	public void describeTo(final Description description) {
		description.appendText("matches regular expression=`" + regex + "`");
	}



	@Override
	public boolean matchesSafely(final String string) {
		return string.matches(regex);
	}


	/**
	 * 
	 * @param regex
	 * @return RegexMatcher
	 */
	public static RegexMatcher matchesRegex(final String regex) {
		return new RegexMatcher(regex);
	}
}
