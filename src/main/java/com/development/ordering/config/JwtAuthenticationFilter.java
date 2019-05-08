package com.development.ordering.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

import static com.development.ordering.model.Constants.HEADER_STRING;
import static com.development.ordering.model.Constants.SIGNING_KEY;
import static com.development.ordering.model.Constants.TOKEN_PREFIX;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        Map<String, String> map = new HashMap<String, String>();

        Enumeration headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = req.getHeader(key);
            map.put(key, value);
        }

        String header = req.getHeader(HEADER_STRING);
        String username = null;
        String authToken = null;

        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX, "");
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("Wrong username", e);
            } catch (ExpiredJwtException e) {
                logger.warn("token is expired", e);
            } catch(SignatureException e){
                logger.error("invalid credentials.");
            }
        } else {
            logger.warn("Header is empty");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthentication(authToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);
                //UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(req, res);
    }

    //
//        if(header == null || !header.startsWith(TOKEN_PREFIX)) {
//            chain.doFilter(req, res);  		// If not valid, go to the next filter.
//            return;
//        }
//
//        String authToken = header.replace(TOKEN_PREFIX, "");
//
//        try {    // exceptions might be thrown in creating the claims if for example the token is expired
//
//            // 4. Validate the token
//            Claims claims = Jwts.parser()
//                    .setSigningKey(SIGNING_KEY)
//                    .parseClaimsJws(authToken)
//                    .getBody();
//
//            String username = claims.getSubject();
//
//            if (username != null) {
//                List<String> authorities = (List<String>) claims.get("authorities");
//
//                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
//                        username, null, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
//
//                // 6. Authenticate the user
//                // Now, user is authenticated
//                SecurityContextHolder.getContext().setAuthentication(auth);
//            }
//        }
//        catch (Exception e){
//            SecurityContextHolder.clearContext();
//        }
}
