
package webim;

import java.io.IOException;
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
 * Servlet implementation class Authorization
 */
public class Authorization extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Authorization() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        TransportClient transportClient = HttpTransportClient.getInstance(); 
        VkApiClient vk = new VkApiClient(transportClient);
        
        int APP_ID = 6843248;
        String CLIENT_SECRET = "4ZOc9BOTt8FBUonu0jxe";
        String REDIRECT_URI = "https://webim-test1.herokuapp.com/listener";
        String code = (String) session.getAttribute("code");
        
 /*        String redirectUrl = "https://oauth.vk.com/access_token?"
                + "client_id=6843248"
                + "&client_secret=4ZOc9BOTt8FBUonu0jxe"
                + "&redirect_uri=https://webim-test1.herokuapp.com/listener"
                + "&code=" + code;

        response.sendRedirect(redirectUrl);*/
        
       UserAuthResponse authResponse = null;
        
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

}
