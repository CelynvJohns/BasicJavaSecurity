package net.jawsjawsbinks.security_user_admin_log_in.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
                // checks if a user is an admin
                boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
                // if the user is an admin it sends them to the admin home page
                if(isAdmin){
                    setDefaultTargetUrl("/admin/home");
                // if a user is not an admin they get sent to the user home page
                } else{
                    setDefaultTargetUrl("/user/home");
                }
                
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
