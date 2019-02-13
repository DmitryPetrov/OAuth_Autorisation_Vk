
package webim;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vk.api.sdk.client.actors.UserActor;

/**
 * Servlet Filter implementation class AutorisationCheckFilter
 */
public class AutorisationCheckFilter implements Filter {

    /**
     * Default constructor.
     */
    public AutorisationCheckFilter() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        UserActor userAccount = (UserActor) session.getAttribute("userAccount");
        
        if(session.isNew()) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect("/RequestFriends");
        }
        
/*        String url = req.getRequestURL().toString();
        if(url.lastIndexOf("/fizz") > -1) //fix this as it could be /fizz33 too
        {
            RequestDispatcher dispatcher = request.getRequestDispatcher("fizz.jsp"); //or whatever page..
            dispatcher.forward(req, resp);
        } else {
            fc.doFilter(req, res);
        }*/
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

}
