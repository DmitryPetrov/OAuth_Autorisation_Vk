
package webim;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;

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

        String vkPesp = "";

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


        HttpSession vkSession = request.getSession();
        vkSession.setAttribute("OAuthCode", request.getParameter("code"));
        vkSession.setAttribute("vkPesp", vkPesp);
        
        UserAuthResponse authResponse = null;
        
        if ( request.getParameter("code") != null) {
            
            TransportClient transportClient = HttpTransportClient.getInstance(); 
            VkApiClient vk = new VkApiClient(transportClient);
            
            int APP_ID = 6843248;
            String CLIENT_SECRET = "friends";
            String REDIRECT_URI = "https://webim-test1.herokuapp.com/listener";
            String code = request.getParameter("code");
            
            
            
            try {
                authResponse = vk.oauth() 
                        .userAuthorizationCodeFlow(APP_ID, CLIENT_SECRET, REDIRECT_URI, code) 
                        .execute();
            } catch (ApiException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClientException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } 
            
            UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
            response.sendRedirect("/OAuthCode");
        } 
        if( request.getParameter("access_token") != null) {
            UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
            response.sendRedirect("/OAuthCode");
        }
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
