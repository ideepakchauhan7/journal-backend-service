package net.engineeringDigestt.JournalApp.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.engineeringDigestt.JournalApp.utils.JWTutils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class JWTfilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTutils jwtutils;




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationheader = request.getHeader("Authorization");
        String username=null;
        String jwt=null;
        if(authorizationheader != null && authorizationheader.startsWith("Bearer "))
        {
            jwt=authorizationheader.substring(7);
            username=jwtutils.extractUsername(jwt);

        }
        if(username!=null)
        {
            UserDetails userdetails=userDetailsService.loadUserByUsername(username);
                if(jwtutils.validToken(jwt, userdetails.getUsername()))
                {
                    UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(userdetails,null,userdetails.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
        }
        response.addHeader("admin","kapil");
        filterChain.doFilter(request,response);


    }
}
