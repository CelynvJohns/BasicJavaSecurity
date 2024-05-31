package net.jawsjawsbinks.security_user_admin_log_in.model;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecureUserDetailService implements UserDetailsService {
    // allows us to call the SecureUserRepository more easily
    @Autowired
    private SecureUserRepository repository;

    // gets user's detais
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SecureUser> user = repository.findByUsername(username);
        if (user.isPresent()) {
            var userObject = user.get();
            return User.builder()
                    .username(userObject.getUsername())
                    .password(userObject.getPassword())
                    .roles(getRoles(userObject))
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    // gets the roles
    private String[] getRoles(SecureUser user){
        // makes the default role USER
        if(user.getRole() == null){
            return new String[]{"USER"};
        }
        // splits it when there is a comma
        return user.getRole().split(",");

    }

}
