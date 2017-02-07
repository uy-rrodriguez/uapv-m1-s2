package partie2;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	private static final String COOKIE_USER = "UserName";
	//private static final String COOKIE_PATH = "Partie2Pas2";

	// Récupération de la cookie (peut être null)
	public static Cookie getUserCookie(HttpServletRequest req) {
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals(COOKIE_USER)) {
					return c;
				}
			}
		}
		return null;
	}
	
	public static void setUserCookie(String userName, HttpServletResponse resp) {
		resp.addCookie(new Cookie(COOKIE_USER, userName));
	}
	
	public static void delUserCookie(HttpServletResponse resp) {
		Cookie c = new Cookie(COOKIE_USER, "");
		c.setMaxAge(0);
		resp.addCookie(c);
	}
}
