
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
        vkPesp += getParameters(request);
        vkPesp += "<form method=\"get\"action=\"/AcceptAccessToken\">"
                + "<input type=\"submit\" value=\"Submit\">" + "</form>";


        HttpSession vkSession = request.getSession();
        vkSession.setAttribute("code", request.getParameter("code"));
        vkSession.setAttribute("access_token", request.getParameter("access_token"));
        vkSession.setAttribute("vkPesp", vkPesp);
        //response.sendRedirect("/Authorization");
        UserAuthResponse authResponse = null;
        response.sendRedirect("/OAuthCode");
        
        
        
        
        
        
        
        
        /*
        if (request.getParameter("code") != null) {
            
            TransportClient transportClient = HttpTransportClient.getInstance(); 
            VkApiClient vk = new VkApiClient(transportClient);
            
            int APP_ID = 6843248;
            String CLIENT_SECRET = "n6qiMy0lvv7DaUVxTXpe";
            String REDIRECT_URI = "https://webim-test1.herokuapp.com/AcceptAccessToken";
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
            
            //UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
            //response.sendRedirect("/OAuthCode");
        } 
        if( request.getParameter("access_token") != null) {
            UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
            response.sendRedirect("/OAuthCode");
        }*/
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
