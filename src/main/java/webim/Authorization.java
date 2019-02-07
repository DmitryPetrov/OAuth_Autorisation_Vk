
package webim;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.vk.api.sdk.objects.friends.responses.GetResponse;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.friends.FriendsGetMutualOrder;
import com.vk.api.sdk.queries.friends.FriendsGetOrder;
import com.vk.api.sdk.queries.users.UserField;
import com.vk.api.sdk.queries.users.UsersGetQuery;

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
        
        
        List<Integer> friends = null;
        try {
            GetResponse getResponse = vk.friends().get(actor)
                    .userId(actor.getId())
                    .count(10)
                    .order(FriendsGetOrder.HINTS)
                    .execute();
            
            friends = getResponse.getItems();
            
            session.setAttribute("actor", actor.toString());

            List<UserXtrCounters> user_info;
            
            List<UserField> fields = new ArrayList<UserField>();
            fields.add(UserField.PHOTO_200);
            fields.add(UserField.DOMAIN);

            if (friends != null) {
                int j = 1;
                for(Integer i: friends) {
                    user_info = vk.users().get(actor)
                            .userIds(i.toString())
                            .fields(fields)
                            .execute();
                    
                    session.setAttribute("friend" + j, user_info);
                    j++;
                    
                }
            }

            
        } catch (ApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        

        
        
        
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

}
