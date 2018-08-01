
package Authenticate;

import java.util.*;

public class pairs {

	public static void main(String[] args) {
		String tryuser = "tn";
		String trypass = "yu";
		boolean re = searchPairs(tryuser, trypass);
		System.out.println(re);

	}

	/**
	 * to store the username and password.
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean searchPairs(String username, String password) {
		Map<String, String> pairs = new HashMap<String, String>();
		String userX = "xu";
		String passX = "chao";
		String userS = "su";
		String passS = "yupeng";
		String userT = "tian";
		String passT = "yujun";
		String userW = "wang";
		String passW = "ye";
		pairs.put(userX, passX);
		pairs.put(userS, passS);
		pairs.put(userT, passT);
		pairs.put(userW, passW);
		if (pairs.containsKey(username)) {
			return password.equals(pairs.get(username));
		}
		return false;
	}

	private boolean newUser(String username, String password) {
		return true;
	}

}
