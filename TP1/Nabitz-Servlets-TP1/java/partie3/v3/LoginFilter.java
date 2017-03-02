package partie3.v3;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig arg0) throws ServletException {}
	
	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        
        String path = request.getServletPath();
        
        if (! path.endsWith("Partie3Pas1") && ! path.endsWith("Partie3Pas2") &&
        		(session == null
        			|| session.getAttribute("utilisateur") == null
        			|| ((String) session.getAttribute("utilisateur")).isEmpty())) {
        	
            response.sendRedirect(request.getContextPath() + "/partie3_v3/Partie3Pas1");
        }
        else {
            filterChain.doFilter(request, response);
        }
	}

}
