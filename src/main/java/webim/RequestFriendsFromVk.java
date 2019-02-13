
package webim;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import com.vk.api.sdk.objects.friends.responses.GetResponse;
import com.vk.api.sdk.objects.users.UserFull;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.users.UserField;

/**
 * Servlet implementation class ShowFriends
 */
public class RequestFriendsFromVk extends HttpServlet {
    private static final long serialVersionUID = 1L;

    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestFriendsFromVk() {
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

        UserActor userAccount = (UserActor) session.getAttribute("userAccount");
        String page = "/show_friends";
        if (userAccount == null) {
            page = "/index.html";
        } else {
            getInfoFromVk(userAccount, session);    
            page = "/show_friends";
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request,response);
    }
    
    
    private void getInfoFromVk(UserActor userAccount, HttpSession session){
        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);
       
        try {
            List<UserXtrCounters> userAccountFields = getAccountFields(vk,
                    userAccount, userAccount.getId().toString());
    
            Map<String, String> userAccountInfo =
                    getAccountInfo(userAccountFields.get(0));
    
            String UserAccountHtml = getHtmlAccountInfo(userAccountInfo);
    
            session.setAttribute("user", UserAccountHtml);
    
            List<Integer> friends = getFriends(vk, userAccount);
            if (friends == null) {
                return;
            }
    
            Random random = new Random();
    
            for (int i = 0; i < 5; i++) {
    
                int randomFriend = random.nextInt((friends.size() - i));
    
                List<UserXtrCounters> friendAccountFields = getAccountFields(vk,
                        userAccount, friends.remove(randomFriend).toString());
    
                Map<String, String> friendAccountInfo =
                        getAccountInfo(friendAccountFields.get(0));
    
                String friendAccountHtml =
                        getHtmlAccountInfo(friendAccountInfo);
    
                session.setAttribute("friend" + i, friendAccountHtml);
            }
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
    

    private List<UserXtrCounters> getAccountFields(VkApiClient vk,
            UserActor userAccount, String accountId)
            throws ApiException, ClientException {

        List<UserField> fields = new ArrayList<UserField>();
        fields.add(UserField.PHOTO_200);
        fields.add(UserField.DOMAIN);

        List<UserXtrCounters> accountInfo = vk.users().get(userAccount)
                .userIds(accountId).fields(fields).execute();

        return accountInfo;
    }

    
    private Map<String, String> getAccountInfo(UserXtrCounters item) {
        UserFull userFull = (UserFull) item;

        Map<String, String> info = new HashMap<String, String>();
        info.put("firstName", userFull.getFirstName());
        info.put("lastName", userFull.getLastName());
        info.put("photo", userFull.getPhoto200());
        info.put("domain", userFull.getDomain());

        return info;
    }

    
    private List<Integer> getFriends(VkApiClient vk, UserActor userAccount)
            throws ApiException, ClientException {

        GetResponse getResponse = vk.friends().get(userAccount)
                .userId(userAccount.getId()).execute();

        return getResponse.getItems();
    }

    
    private String getHtmlAccountInfo(Map<String, String> userAccountInfo) {
        StringBuilder table = new StringBuilder();
        table.append("<div=\"account\">");
        table.append("<img src=\"" + userAccountInfo.remove("photo") + "\">");
        table.append("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
        table.append(userAccountInfo.remove("firstName"));
        table.append("&nbsp");
        table.append(userAccountInfo.remove("lastName"));
        table.append("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
        table.append("<a href=\"https://vk.com/" + userAccountInfo.get("domain")
                + "\">https://vk.com/" + userAccountInfo.remove("domain")
                + "</a>");
        table.append("</div>");

        return table.toString();
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
