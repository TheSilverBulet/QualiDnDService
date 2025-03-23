package com.silverbullet.qualidnd.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * Utility class with helpers to perform different BCrypt related functions
 *
 * @author Batman
 *
 */
public class BCryptUtils {

	private static final Logger LOG = LogManager.getLogger(BCryptUtils.class);

	private BCryptUtils() {
	}

	/**
	 * Encrypt a given string using BCrypt encryption
	 *
	 * @param toEncrypt the string to encrypt
	 * @return the encrypted string
	 */
	public static String encrpyt(String toEncrpyt) {
		if (StringUtils.isNotBlank(toEncrpyt)) {
			return BCrypt.hashpw(toEncrpyt, BCrypt.gensalt(12));
		}
		LOG.warn("passed string is not viable for hashing");
		return StringUtils.EMPTY;
	}

	/**
	 * Helper method to determine whether a provided String's encrypted hash matches
	 * another. BCrypt encryption will always encrypt a String to the same hash,
	 * however BCrypt encrypted Strings cannot be decrypted which is why it's
	 * necessary to check if the final hashes match
	 *
	 * @param given  the String to check
	 * @param fromDB the String from the DB to check against
	 * @return true if match, else false
	 */
	public static boolean isMatch(String given, String fromDB) {
		return StringUtils.isNotBlank(given) && StringUtils.isNotBlank(fromDB) && BCrypt.checkpw(given, fromDB);
	}
}
