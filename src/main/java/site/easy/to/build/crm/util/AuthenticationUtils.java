package site.easy.to.build.crm.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import site.easy.to.build.crm.entity.CustomerLoginInfo;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.service.customer.CustomerLoginInfoService;
import site.easy.to.build.crm.service.user.UserService;


@Component
public class AuthenticationUtils {

    private final UserService userService;
    private final CustomerLoginInfoService customerLoginInfoService;
    private final UserDetailsService crmUserDetails;
    private final UserDetailsService customerUserDetails;

    @Autowired
    public AuthenticationUtils(UserService userService, CustomerLoginInfoService customerLoginInfoService,
                               UserDetailsService crmUserDetails, UserDetailsService customerUserDetails) {
        this.userService = userService;
        this.customerLoginInfoService = customerLoginInfoService;
        this.crmUserDetails = crmUserDetails;
        this.customerUserDetails = customerUserDetails;
    }

    public int getLoggedInUserId(Authentication authentication) {
        User user;
        CustomerLoginInfo customerLoginInfo;
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UserDetailsService authenticatedUserDetailsService = getAuthenticatedUserDetailsService(authentication);
            String userName = authentication.getName();
            if (authenticatedUserDetailsService == crmUserDetails) {
                user = userService.findByUsername(userName).get(0);
                if (user == null) {
                    return -1;
                }
                return user.getId();
            } else if (authenticatedUserDetailsService == customerUserDetails) {
                customerLoginInfo = customerLoginInfoService.findByEmail(userName);
                if (customerLoginInfo == null) {
                    return -1;
                }
                return customerLoginInfo.getId();
            }
        }
        return -1;
    }

    public UserDetailsService getAuthenticatedUserDetailsService(Authentication authentication) {
        UserDetailsService authenticatedUserDetailsService = null;

        if (authentication != null && authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User authenticatedUser) {
            String authenticatedUsername = authenticatedUser.getUsername();

            if (authenticatedUsername != null) {
                try {
                    if (crmUserDetails != null && authenticatedUsername.equals(crmUserDetails.loadUserByUsername(authenticatedUsername).getUsername())) {
                        authenticatedUserDetailsService = crmUserDetails;
                    }
                } catch (UsernameNotFoundException e) {
                    // Swallow the exception and continue to the next condition
                }

                if (authenticatedUserDetailsService == null && customerUserDetails != null) {
                    try {
                        if (authenticatedUsername.equals(customerUserDetails.loadUserByUsername(authenticatedUsername).getUsername())) {
                            authenticatedUserDetailsService = customerUserDetails;
                        }
                    } catch (UsernameNotFoundException e) {
                        // Swallow the exception and continue to the next steps
                    }
                }
            }
        }

        return authenticatedUserDetailsService;
    }
}