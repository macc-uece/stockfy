// package br.com.uece.frameworks.stockfy;

// import java.security.Principal;
// import java.util.Collection;
// import java.util.HashSet;

// import org.apache.commons.lang3.StringUtils;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.mock.web.MockHttpSession;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.context.web.WebAppConfiguration;


// @SpringBootTest(classes = Application.class)
// @WebAppConfiguration
// @ActiveProfiles("testing") // make the active profile "testing
// public class BaseApplicationTests {

//     public MockHttpSession makeAuthSession(String username, String... roles) {
//         if (StringUtils.isEmpty(username)) {
//             username = "admin";
//         }
//         MockHttpSession session = new MockHttpSession();
//         session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
//         Collection<GrantedAuthority> authorities = new HashSet<>();
//         if (roles != null && roles.length > 0) {
//             for (String role : roles) {
//                 authorities.add(new SimpleGrantedAuthority(role));
//             }
//         }
        
//         Principal principal = new MyOAuthAuthenticationHandler.NamedOAuthPrincipal(username, authorities,
//                 "key", "signature", "HMAC-SHA-1", "signaturebase", "token");
//         Authentication authToken = new UsernamePasswordAuthenticationToken(principal, null, authorities);
//         SecurityContextHolder.getContext().setAuthentication(authToken);
//         return session;
//     }
    
// }