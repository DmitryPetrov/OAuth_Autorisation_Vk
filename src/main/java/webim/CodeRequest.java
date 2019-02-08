
package webim;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class WebimTest
 */
public class CodeRequest extends HttpServlet {
    private static final long serialVersionUID = 1L;

    
    /**
     * Default constructor.
     */
    public CodeRequest() {
        // TODO Auto-generated constructor stub
    }

    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();

        if (session.getAttribute("Authorization") != null) {
/*            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/Authorization");
            requestDispatcher.forward(request, response);*/
        }
               
        String redirectUrl = "https://oauth.vk.com/authorize?"
                            + "client_id=6843248"
                            + "&display=page"
                            + "&redirect_uri=https://webim-test1.herokuapp.com/listener"
                            + "&scope=friends"
                            + "&response_type=code"
                            + "&v=5.92";

            response.sendRedirect(redirectUrl);
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
