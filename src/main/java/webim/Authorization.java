
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
        String code = (String) session.getAttribute("code");
        
        int APP_ID = 6843248;
        String CLIENT_SECRET = "4ZOc9BOTt8FBUonu0jxe";
        String REDIRECT_URI = "https://webim-test1.herokuapp.com/listener";
      
        TransportClient transportClient = HttpTransportClient.getInstance(); 
        VkApiClient vk = new VkApiClient(transportClient);
        UserAuthResponse authResponse = null;
        try {
            authResponse = vk.oauth() 
                    .userAuthorizationCodeFlow(APP_ID, CLIENT_SECRET, REDIRECT_URI, code) 
                    .execute();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        } 
        UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
        session.setAttribute("actor", actor.toString());
        
        
        try {
            GetResponse getResponse = vk.friends().get(actor)
                    .userId(actor.getId())
                    .count(10)
                    .order(FriendsGetOrder.HINTS)
                    .execute();
            
            List<Integer> friends = getResponse.getItems();
            if (friends == null) {
                return;
            }
            
            

            List<UserXtrCounters> userInfo;
            
            List<UserField> fields = new ArrayList<UserField>();
            fields.add(UserField.PHOTO_200);
            fields.add(UserField.DOMAIN);


            
            
            for(int i = 0; i < friends.size(); i++) {
                
                String id = " + ";
                
                userInfo = vk.users().get(actor)
                        .userIds(id)
                        .fields(fields)
                        .execute();
                
                String userInfoStr = "";
                
                for(int j = 0; j < userInfo.size(); j++) {
                    userInfoStr += userInfo.get(j) + " ";
                }
                
                session.setAttribute("friend" + i, userInfoStr);      
            }
            

            
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
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
