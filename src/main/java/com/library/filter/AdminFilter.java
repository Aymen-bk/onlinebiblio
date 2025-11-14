package com.library.filter;

import com.library.model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("[AdminFilter] Initialized successfully");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            HttpSession session = httpRequest.getSession(false);

            boolean isAdmin = false;

            if (session != null) {
                User user = (User) session.getAttribute("user");
                if (user != null && user.isAdmin()) {
                    isAdmin = true;
                }
            }

            if (isAdmin) {
                chain.doFilter(request, response);
            } else {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.jsp?error=Access denied");
            }
        } catch (Exception e) {
            System.err.println("[AdminFilter] Error in filter: " + e.getMessage());
            e.printStackTrace();
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Filter error: " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        System.out.println("[AdminFilter] Destroyed");
    }
}
