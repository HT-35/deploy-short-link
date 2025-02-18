package com.example.short_link.shared.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityContexUtil {


    // lấy email từ JWT khi người dùng gửi token lên server.
    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    // Trích xuất thông tin username từ đối tượng Authentication
    private static String extractPrincipal(Authentication authentication) {
        // Nếu authentication null, trả về null
        if (authentication == null) {
            return null;
            // Nếu principal là một UserDetails (đối tượng của Spring Security), trả về
            // username
        } else if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {

            String email =  springSecurityUser.getUsername();

           return springSecurityUser.getUsername();
            // Nếu principal là một Jwt (JSON Web Token), trả về subject của token
        } else if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getSubject();
            // Nếu principal là một String, trả về giá trị String này
        } else if (authentication.getPrincipal() instanceof String s) {
            return s;
        }
        // Nếu không khớp bất kỳ trường hợp nào, trả về null
        return null;
    }

    /**
     * Lấy JWT (JSON Web Token) của người dùng hiện tại .
     *
     * Optional chứa JWT của người dùng hiện tại.
     */
    public static Optional<String> getCurrentUserJWT() {
        // Lấy SecurityContext hiện tại từ SecurityContextHolder
        SecurityContext securityContext = SecurityContextHolder.getContext();
        // Lấy Authentication, kiểm tra credentials có phải String không, nếu có thì trả
        // về JWT
        return Optional.ofNullable(securityContext.getAuthentication())
                .filter(authentication -> authentication.getCredentials() instanceof String)
                .map(authentication -> (String) authentication.getCredentials());
    }

    /**
     * Kiểm tra xem người dùng có được xác thực hay không.
     *
     * @return true nếu người dùng đã được xác thực, false nếu không.
     */

    // public static boolean isAuthenticated() {
    // // Lấy Authentication từ SecurityContextHolder
    // Authentication authentication =
    // SecurityContextHolder.getContext().getAuthentication();
    // // Kiểm tra authentication khác null và không chứa quyền ANONYMOUS
    // return authentication != null &&
    // getAuthorities(authentication).noneMatch(AuthoritiesConstants.ANONYMOUS::equals);
    // }

    /**
     * Kiểm tra người dùng hiện tại có bất kỳ quyền (authorities) nào trong danh
     * sách không.
     *
     * @param authorities danh sách quyền cần kiểm tra.
     * @return true nếu người dùng có bất kỳ quyền nào trong danh sách, false nếu
     *         không.
     */

    // public static boolean hasCurrentUserAnyOfAuthorities(String... authorities) {
    // // Lấy Authentication từ SecurityContextHolder
    // Authentication authentication =
    // SecurityContextHolder.getContext().getAuthentication();
    // // Kiểm tra xem người dùng hiện tại có bất kỳ quyền nào trong danh sách
    // authorities không
    // return (
    // authentication != null && getAuthorities(authentication).anyMatch(authority
    // -> Arrays.asList(authorities).contains(authority))
    // );
    // }

    /**
     * Kiểm tra người dùng hiện tại không có quyền nào trong danh sách authorities.
     *
     * @param authorities danh sách quyền cần kiểm tra.
     * @return true nếu người dùng không có bất kỳ quyền nào, false nếu có.
     */
    // public static boolean hasCurrentUserNoneOfAuthorities(String... authorities)
    // {
    // // Ngược lại của hasCurrentUserAnyOfAuthorities
    // return !hasCurrentUserAnyOfAuthorities(authorities);
    // }

    /**
     * Kiểm tra người dùng hiện tại có quyền cụ thể nào đó không.
     *
     * @param authority quyền cần kiểm tra.
     * @return true nếu người dùng có quyền đó, false nếu không.
     */
    // public static boolean hasCurrentUserThisAuthority(String authority) {
    // // Kiểm tra quyền cụ thể dựa trên hasCurrentUserAnyOfAuthorities
    // return hasCurrentUserAnyOfAuthorities(authority);
    // }

    // private static Stream<String> getAuthorities(Authentication authentication) {
    // // Lấy danh sách các quyền của người dùng từ Authentication và chuyển thành
    // Stream
    // return
    // authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority);
    // }

}
