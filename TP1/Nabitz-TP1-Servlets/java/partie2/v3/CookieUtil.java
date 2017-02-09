package partie2.v3;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	private static final String COOKIE_USER = "UserName";
	//private static final String COOKIE_PATH = "Partie2Pas2";

	// Récupération de la cookie (peut étre null)
	public static String getUserNameByCookie(HttpServletRequest req) {
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals(COOKIE_USER)) {
					try {
						return URLDecoder.decode (c.getValue(), "UTF-8");
					}
					catch (Exception e) {
						return e.getMessage();
					}
				}
			}
		}
		return null;
	}
	
	public static void setUserCookie(String userName, HttpServletResponse resp) {
		try {
			resp.addCookie(new Cookie(COOKIE_USER, URLEncoder.encode(userName, "UTF-8")));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public static void delUserCookie(HttpServletResponse resp) {
		Cookie c = new Cookie(COOKIE_USER, "");
		c.setMaxAge(0);
		resp.addCookie(c);
	}
}
