
package webim;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Listener
 */
// @WebServlet("/listener")
public class Listener extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Listener() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String session_id = (String) request.getParameter("session");
        HttpSession userSession;
        HttpSession vkSession = request.getSession();
/*        if (session_id != null) {
            ServletContext context = getServletConfig().getServletContext();
            userSession = (HttpSession) context.getAttribute(session_id);
            
            userSession.setAttribute("userSession", userSession.getId());
            userSession.setAttribute("vkSession", request.getSession().getId());
            
            userSession.setAttribute("OAuthCode", request.getParameter("code"));
            userSession.setAttribute("vkURL", request.getParameter("vkURL"));

            vkSession.setAttribute("userSession", userSession.getId());
            vkSession.setAttribute("vkSession", vkSession.getId());
            
            vkSession.setAttribute("OAuthCode", request.getParameter("code"));
            vkSession.setAttribute("vkURL", request.getParameter("vkURL"));
            
            response.sendRedirect("/OAuthCode");
        } else {
            return;
        }*/
        
        
        String vkPesp = "";
        String vkURL = "\n<p>URL request: " + request.getRequestURL() + "?"
                + request.getQueryString() + "</p>";

        vkPesp += "\n<p>URL request: " + request.getRequestURL() + "?"
                + request.getQueryString() + "</p>";

        vkPesp += "<li>METHOD: " + request.getMethod() + "</li>";
        vkPesp += "<li>Protocol: " + request.getProtocol() + "</li>";
        vkPesp += "<li>Port: " + request.getLocalPort() + "</li>";
        vkPesp += "<li>URL: " + request.getRequestURL() + "</li>";
        vkPesp += "<li>URI: " + request.getRequestURI() + "</li>";
        vkPesp += getHeaders(request);

        vkPesp += "\n\n<p>Request parameters: " + request.getQueryString()
                + "</p>";

        vkPesp += getParameters(request);

        vkPesp += "<form method=\"get\"action=\"/Authorization\">"
                + "<input type=\"submit\" value=\"Submit\">" + "</form>";

        ServletContext context = getServletConfig().getServletContext();
 /*       
        context.setAttribute("userSession", context.getAttribute("session"));
        context.setAttribute("vkSession", vkSession.getId());
        
        context.setAttribute("OAuthCode", request.getParameter("code"));
        context.setAttribute("vkPesp", request.getParameter("vkPesp"));*/

        vkSession.setAttribute("userSession", context.getAttribute("session"));
        vkSession.setAttribute("vkSession", vkSession.getId());
        
        vkSession.setAttribute("OAuthCode", request.getParameter("code"));
        vkSession.setAttribute("vkPesp", vkPesp);
        
        response.sendRedirect("/OAuthCode");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

    protected String getParameters(HttpServletRequest request)
            throws ServletException, IOException {
        String result = "";

        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            result += ("<p>" + parameterName + ": "
                    + request.getParameter(parameterName) + "</p>\n");
        }

        return result;
    }

    protected String getHeaders(HttpServletRequest request)
            throws ServletException, IOException {
        String result = "";

        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            result += ("<li>" + headerName + ": "
                    + request.getHeader(headerName) + "</li>\n");
        }

        return result;
    }

}
