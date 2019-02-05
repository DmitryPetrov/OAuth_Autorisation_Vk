
package webim;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;

/**
 * Servlet implementation class WebimTest
 */
public class WebimTest extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public WebimTest() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String vkresp = (String) session.getAttribute("resp");

        if (vkresp == null) {
            HttpClient client = HttpClientBuilder.create().build();
            HttpUriRequest httpUriRequest =
                    new HttpGet("https://oauth.vk.com/authorize?"
                            + "client_id=6843248"
                            + "&display=page"
                            + "&redirect_uri=https://webim-test.herokuapp.com/listener"
                            + "&scope=friends"
                            + "&response_type=code"
                            + "&v=5.92");
    
            HttpResponse resp = client.execute(httpUriRequest);
            //response.getWriter().write("" + resp);
            //System.out.println("Response:" + resp);
    
            
    /*        TransportClient transportClient = HttpTransportClient.getInstance();
            VkApiClient vk = new VkApiClient(transportClient);
    
            vk.
    
                    UserAuthResponse authResponse = vk.oauth()
                            .userAuthorizationCodeFlow("6843248",
                                    "lqX6TLf0hwmacIQXRQk5", REDIRECT_URI, code)
                            .execute();
    
            UserActor actor = new UserActor(authResponse.getUserId(),
                    authResponse.getAccessToken());*/
        
        } else {
            response.getWriter().write("" + vkresp);
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

}
