package com.trust.amanat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private JWTRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/index.html",
                                "/login.html",
                                "/signup.html",
                                "/welcome.html",
                                "/userdetails.html",
                                "/images/**",
                                "/css/**",
                                "/js/**",
                                "/uploads/**",
                                "/signIn/**",
                                "/signUp/addUser",
                                "/about.html",
                                "/about/**",
                                "/team.html",
                                "/contact.html",
                                "/members/allmembers",
                                "/members/addMember",
                                "/admin.html",
                                "/expenditure/**",
                                "/expenditure.html/**",
                                "/members.html/**",
                                "/incomedet.html",
                                "/incomeDet/addPayment",
                                "/scan&pay.html",
                                "/scan&pay/**",
                                "/recpdfgen.html",
                                "/recpdfgen/**",
                                "/cover.html",
                                "/cover/**",
                                "/showIncomeDet.html",
                                "/postholder/**",
                                "/getAllPostHolders",
                                "/postholder.html",
                                "/incomeDet/monthly-report",
                                "/incomeDet/searchMember",
                                "/common.html",
                                "/scan&pay/addPayee",
                                "/beneficiary/**",
                                "/members/updateMember/**",
                                "/help-requests/",
                                "/help-requests/**",
                                "/help-requests.html",
                                "/create-admin.html",
                                "/admins/create",
                                "/beneficiary.html",
                                "/admin-login.html",
                                "/admin-detail.html",
                                "/admins/login",
                                "/admins/getAllAdmins",
                                "/feedback/",
                                "/feedback/addFeedback",
                                "/feedback.html",
                                "/feedback/allFeedback",
                                "/feedback-view.html",
                                "/super-admin.html",
                                "/report/**",
                                "/admins/resign/**",
                                "/uploads/**",
                                "/super-admin/create-super-admin",
                                "/super-admin/super-admin-login",
                                "/super-admin-login.html",
                                "/manualDetailUpdate.html",
                                "/admins/accept-resignation/**"



                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtRequestFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
