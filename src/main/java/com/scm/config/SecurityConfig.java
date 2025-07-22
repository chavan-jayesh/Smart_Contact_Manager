package com.scm.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
// import com.scm.services.SecurityCustomUserDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Configuration
public class SecurityConfig {
   
    // @Autowired
    // private SecurityCustomUserDetailService securityCustomUserDetailService;

    @Autowired
    private OAuthAuthenticationSuccessHandler handler;

    //Configuration for Authentication Provider

    // @Bean
    // public DaoAuthenticationProvider authenticationProvider(){
    //     DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        
    //     daoAuthenticationProvider.setUserDetailsService(securityCustomUserDetailService);
    //     daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    //     return daoAuthenticationProvider;
    // }

    @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
            return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        //Here we have configured URLs that which will be public and which will be private..
        httpSecurity.authorizeHttpRequests(authorize -> {
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });


        // //form default login
        // httpSecurity.formLogin(Customizer.withDefaults());

        httpSecurity.formLogin(formLogin -> {

            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.defaultSuccessUrl("/user/profile");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");

            formLogin.failureHandler(new AuthenticationFailureHandler() {

                @Override
                public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                        AuthenticationException exception) throws IOException, ServletException {
                   if(exception instanceof DisabledException){
                        //user is disabled

                        HttpSession session = request.getSession();
                        session.setAttribute("message", Message.builder()
                                                                    .content("User is Disabled! Verification link sent on registered email Id!")
                                                                    .messageType(MessageType.red).build());

                        response.sendRedirect("/login");
                   }
                   else{
                        response.sendRedirect("/login?error=true");
                   }
                }
                
            });

        });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        //Oauth Configuration
        
        httpSecurity.oauth2Login(oauth -> {
            oauth.loginPage("/login");
            oauth.successHandler(handler);
        });
        
        httpSecurity.logout(logoutForm -> {
            logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
