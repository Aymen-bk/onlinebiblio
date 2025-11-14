package com.library.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("[AuthFilter] Initialized successfully");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            HttpSession session = httpRequest.getSession(false);

            boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

            if (isLoggedIn) {
                chain.doFilter(request, response);
            } else {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp?error=Please login to continue");
            }
        } catch (Exception e) {
            System.err.println("[AuthFilter] Error in filter: " + e.getMessage());
            e.printStackTrace();
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Filter error: " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        System.out.println("[AuthFilter] Destroyed");
    }
}
