package com.library.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AuthFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("[AuthFilter] Initialized successfully");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            HttpSession session = httpRequest.getSession(false);
            boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

            if (isLoggedIn) {
                chain.doFilter(request, response);
            } else {
                logger.warn("Unauthorized access attempt to {}", httpRequest.getRequestURI());
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp?error=Please login to continue");
            }
        } catch (Exception e) {
            logger.error("[AuthFilter] Error in filter", e);
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Filter error: " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        logger.info("[AuthFilter] Destroyed");
    }
}
