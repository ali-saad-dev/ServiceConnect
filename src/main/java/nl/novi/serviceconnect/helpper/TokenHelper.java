package nl.novi.serviceconnect.helpper;

import jakarta.servlet.http.HttpServletRequest;
import nl.novi.serviceconnect.utils.JwtUtil;

public class TokenHelper {
    public static String getUserNameFromToken(HttpServletRequest request, JwtUtil jwtUtil){
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }
        return  jwtUtil.extractUsername(token);
    }
}
