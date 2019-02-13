
package webim;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
        String page = "/RequestFriends";
        
        String code = (String) session.getAttribute("code");
        if(code == null) {
            String access_token = (String) session.getAttribute("access_token");
            if(access_token == null) {
                page = "/index.html";
            } else {         
                Integer user_id = (Integer) session.getAttribute("user_id");
                
                UserActor userAccount = getUserAccount(user_id, access_token);
                session.setAttribute("userAccount", userAccount);
            }
        } else {
            UserActor userAccount = getUserAccount(session, code);
            session.setAttribute("userAccount", userAccount);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request,response);
    }

    
    private UserActor getUserAccount(HttpSession session, String code){
        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);
        
        int APP_ID = 6843248;
        String CLIENT_SECRET = "4ZOc9BOTt8FBUonu0jxe";
        String REDIRECT_URI = "https://webim-test1.herokuapp.com/listener";

        UserAuthResponse authResponse = null;
        try {
            authResponse = vk.oauth().userAuthorizationCodeFlow(APP_ID,
                    CLIENT_SECRET, REDIRECT_URI, code).execute();
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        } catch (ClientException e) {
            e.printStackTrace();
            return null;
        }
        
        Integer user_id = authResponse.getUserId();
        session.setAttribute("user_id", user_id);
        
        String access_token = authResponse.getAccessToken();
        session.setAttribute("access_token", access_token);
        
        UserActor userAccount = getUserAccount(user_id, access_token);
        
        return userAccount;
    }
    
    private UserActor getUserAccount (Integer user_id, String access_token) {
        return new UserActor(user_id, access_token);
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
