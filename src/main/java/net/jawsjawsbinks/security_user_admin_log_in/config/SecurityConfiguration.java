package net.jawsjawsbinks.security_user_admin_log_in.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import net.jawsjawsbinks.security_user_admin_log_in.model.SecureUserDetailService;

// enables configuration
@Configuration
// enables web security access
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private SecureUserDetailService userDetailsService;
    
    // customize security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
        // allows for post mapping, by default it's disabled incase of cross-site request forgery
        .csrf(AbstractHttpConfigurer::disable) // doesn't like JSP files but worked just fine in .html, will look into this later but project fully works
        .authorizeHttpRequests (registry -> {
            // home page accessable to all
            registry.requestMatchers("/home", "/register").permitAll();
            // admin pages restricted to admins only
            registry.requestMatchers("/admin/**").hasRole("ADMIN");
            // user pages restricted to users only
            registry.requestMatchers("/user/**").hasRole("USER");
            //any request that isn't here won't be allowed
            registry.anyRequest().authenticated();
        })
        // BASIC LOGIN: .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
        //Custom login
        .formLogin(httpSecurityFormLoginConfigurer -> {
            httpSecurityFormLoginConfigurer
            // takes people to custom login page
            .loginPage("/login")
            //when someone is logged in they are taken to their home
            .successHandler(new  net.jawsjawsbinks.security_user_admin_log_in.security.AuthenticationSuccessHandler() {
                
            })
            .permitAll();
        }) 

        .build();
    }

 /*    // create in-memory users
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails normalUser = User.builder()
            .username("moomoo")
            //Use bcryptencoder online to get the password encoded (colby23)
            .password("$2a$12$BYcAHplOqq0eZUEnJmN7y.8znyjZjkWIlneVxktCt3Z8HKTpJxqPu")
            .roles("USER")
            .build();
        UserDetails adminUser = User.builder()
            .username("admoon")
            // jackattack32
            .password("$2a$12$hElTSAsHkeLLaH0WrCjmFuzAaUwq6EJ.ZvOGj1.NtF0nQTbMztD5K")
            // allows admin and user access
            .roles("ADMIN", "USER")
            .build();
        return new InMemoryUserDetailsManager(normalUser, adminUser);

    } */

    // uses created UserDetailsService to make sure the user is a user
    @Bean
    public UserDetailsService userDetailsService(){
        return userDetailsService;
    }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    
    // many types of password Encoders
    @Bean
    public PasswordEncoder passwordEncoder(){
        //best for web applications and Legacy systems
        return new BCryptPasswordEncoder();
    }

    /* PASSWORD ENCODERS AND WHEN TO USE:
     *  - Argon2 = memory-hard password hashing function (PHF)... best for offline password storage and high-security systems... the most secure option
     *  - BCrypt = key derived function (KDF) has password hashing support... best for Legacy Systems and web applications... best used only if Argon2 isn't avaliable or if the memory usage is TOO high
     *  - Scrypt = Memory-hard PHF... best for a balance of security and performance... a good alt to Argon2
     *  - PBKDF2 = KDF w/ hashing support ... best for simple applications, has backward compatibility but not recomended for new applications bc powerful GPU's can crack the password easily compared to others
     * 
     */
}
