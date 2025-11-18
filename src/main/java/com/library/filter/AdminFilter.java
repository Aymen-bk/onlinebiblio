package com.library.filter;

import com.library.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AdminFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AdminFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("[AdminFilter] Initialized successfully");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
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
                logger.warn("Access denied for non-admin user trying to access {}", httpRequest.getRequestURI());
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.jsp?error=Access denied");
            }
        } catch (Exception e) {
            logger.error("[AdminFilter] Error in filter", e);
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Filter error: " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        logger.info("[AdminFilter] Destroyed");
    }
}
