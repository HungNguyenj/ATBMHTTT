package com.ltweb_servlet_ecommerce.controller.web.shared;

import com.restfb.*;
import com.restfb.exception.*;
import com.restfb.scope.FacebookPermissions;
import com.restfb.scope.ScopeBuilder;
import com.restfb.types.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet(urlPatterns = {"/oauth2-facebook"})
public class OAuth2FacebookController extends HttpServlet {
    private static final String APP_ID = "264883263010053";
    private static final String APP_SECRET = "7d2de40591e7eda1f4bea88c132434f9";
    private static final String REDIRECT_URI = "http://localhost:8080/oauth2-facebook";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code != null) {
            try {
                FacebookClient.AccessToken accessToken = getFacebookAccessToken(code);
                // Get user details
                FacebookClient facebookClient = new DefaultFacebookClient(accessToken.getAccessToken(), Version.LATEST);
                User user = facebookClient.fetchObject("me", User.class);
                response.getWriter().println("Facebook login successful. User ID: " + user);

            } catch (FacebookJsonMappingException e) {
                // Looks like this API method didn't really return a list of users
            } catch (FacebookNetworkException e) {
                // An error occurred at the network level
                System.out.println("API returned HTTP status code " );
            } catch (FacebookOAuthException e) {
                // Authentication failed - bad access token?
            } catch (FacebookGraphException e) {
                // The Graph API returned a specific error
                System.out.println("Call failed. API says: " + e.getErrorMessage());
            } catch (FacebookResponseStatusException e) {
                // Old-style Facebook error response.
                // The Graph API only throws these when FQL calls fail.
                // You'll see this exception more if you use the Old REST API
                // via LegacyFacebookClient.
                if (e.getErrorCode() == 200)
                    System.out.println("Permission denied!");
            } catch (FacebookException e) {
                // This is the catchall handler for any kind of Facebook exception
            }
            // Exchange code for access token


            // Use user information as needed
        } else {
            // Redirect to Facebook login
            ScopeBuilder scopeBuilder = new ScopeBuilder();
            scopeBuilder.addPermission(FacebookPermissions.EMAIL);
            scopeBuilder.addPermission(FacebookPermissions.PUBLIC_PROFILE);
            FacebookClient client = new DefaultFacebookClient(Version.LATEST);
            String loginDialogUrlStringString = client.getLoginDialogUrl(APP_ID, REDIRECT_URI, scopeBuilder);
            response.sendRedirect(loginDialogUrlStringString);
        }
    }



    private FacebookClient.AccessToken getFacebookAccessToken(String code) {

        FacebookClient facebookClient = new DefaultFacebookClient(Version.LATEST);
        return facebookClient.obtainUserAccessToken(APP_ID, APP_SECRET, REDIRECT_URI,code);
    }

}
