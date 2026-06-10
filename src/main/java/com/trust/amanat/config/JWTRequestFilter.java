package com.trust.amanat.config;

import com.trust.amanat.common.constants.AppConstants;
import com.trust.amanat.entity.AdminEntity;
import com.trust.amanat.entity.SuperAdminEntity;
import com.trust.amanat.entity.UserEntity;
import com.trust.amanat.repository.AdminRepository;
import com.trust.amanat.repository.SuperAdminRepository;
import com.trust.amanat.repository.UserSignInRepository;
import com.trust.amanat.serviceImpl.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserSignInRepository userSignInRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private SuperAdminRepository superAdminRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getServletPath();

        if (path.equals("/") ||
                path.equals("/contact.html") ||
                path.equals("/about.html") ||
                path.equals("/team.html") ||
                path.equals("/index.html") ||
                path.equals("/login.html") ||
                path.equals("/signup.html") ||
                path.equals("/welcome.html") ||
                path.startsWith("/css/") ||
                path.startsWith("/js/") ||
                path.equals("/userdetails.html") ||
                path.startsWith("/images") ||
                path.startsWith("/signIn") ||
                path.startsWith("/members/allmembers") ||
                path.startsWith("/members.html") ||
                path.startsWith("/admin.html") ||
                path.startsWith("/expenditure/") ||
                path.startsWith("/expenditure.html") ||
                path.startsWith("/incomedet.html") ||
                path.equals("/incomeDet/addPayment") ||
                path.startsWith("/cover") ||
                path.equals("/scan&pay.html") ||
                path.equals("/recpdfgen.html") ||
                path.startsWith("/recpdfgen/") ||
                path.startsWith("/showIncomeDet.html")||
                path.startsWith("/incomeDet/monthly-report") ||
                path.startsWith("/common.html") ||
                path.startsWith("/members/addMember") ||
                path.startsWith("/incomeDet/searchMember")||
                path.startsWith("/members/updateMember/") ||
                path.startsWith("/beneficiary/allBeneficiaries") ||
                path.startsWith("/beneficiary/") ||
                path.equals("/beneficiary.html") ||
                path.startsWith("/help-requests/") ||
                path.equals("/help-requests.html") ||
                path.startsWith("/create-admin.html") ||
                path.startsWith("/admin-login.html") ||
                path.startsWith("/admin-detail.html") ||
                path.startsWith("/admins/create") ||
                path.startsWith("/admins/login") ||
                path.startsWith("/report/")||
                path.startsWith("/uploads/")||
                path.startsWith("/feedback/addFeedback")||
                path.startsWith("/feedback.html")||
                path.startsWith("/feedback-view.html")||
                path.startsWith("/super-admin.html")||
                path.startsWith("/super-admin/create-super-admin")||
                path.startsWith("/super-admin/super-admin-login")||
                path.startsWith("/super-admin-login.html")||
                path.startsWith("/manualDetailUpdate.html")||
                path.startsWith("/signUp/forgotPassword")||
                path.startsWith("/forgot-password.html")||
                path.startsWith("/change-password.html")||
                path.startsWith("/api/cloudinary/uploadtest")||
                path.startsWith("/actuator")||
                path.startsWith("/captcha/generate")||
                path.startsWith("/scan&pay/allPayments")||
                path.startsWith("/scan&pay/paymentsByStatus")||


                path.equals("/signUp/addUser")) {

            filterChain.doFilter(request, response);
            return;
        }
        String tokenHeader = request.getHeader(AppConstants.Message.AUTH_HEADER);
        if (tokenHeader == null || !tokenHeader.startsWith(AppConstants.Message.BEARER_PREFIX)) {

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;

        }
            String token = tokenHeader.substring(7);
            String username = jwtService.getUserName(token);
            String role = jwtService.getRole(token);

            List<UserEntity> opUser = userSignInRepository.findByUserName(username);
        if (!opUser.isEmpty()) {

                UserEntity user = opUser.get(0);
                UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(user,null,List.of(new SimpleGrantedAuthority(role)));
                authenticationToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            } else {
            AdminEntity admin = adminRepository.findByUserId(username);
            if (admin != null) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(admin, null, List.of(new SimpleGrantedAuthority(role)));
                auth.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {

                SuperAdminEntity superAdmin = superAdminRepository.findByUsername(username);
                if (superAdmin != null) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken
                            (superAdmin, null, List.of(new SimpleGrantedAuthority(role)));
                    auth.setDetails(new WebAuthenticationDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }

            }
        }
        filterChain.doFilter(request, response);
    }

}
