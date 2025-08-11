package com.example.AssignmentGD2Java4.controller;

import com.example.AssignmentGD2Java4.models.Users;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class AutheFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getServletPath();

        Users users = (Users) request.getSession().getAttribute("currentUser");

        System.out.println("PATH = " + path);

        boolean isDontNeedToLogin =
                path.equals("/login") ||
                        path.equals("/register") ||
                        path.equals("/admin/admin-login");

        // Nếu là trang không cần login → cho qua luôn
        if (isDontNeedToLogin) {
            filterChain.doFilter(request, response);
            return;
        }

        // Chưa login → redirect đúng loại user
        if (users == null) {
            if (path.startsWith("/admin")) {
                response.sendRedirect(request.getContextPath() + "/admin/admin-login");
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
            }
            return;
        }

        // Chặn student vào admin
        if (users.getRole() == Users.UserRole.STUDENT && path.startsWith("/admin")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập.");
            return;
        }

        // Chặn admin vào student
        if (users.getRole() == Users.UserRole.ADMIN &&
                (path.startsWith("/dashboard") || path.startsWith("/borrow") || path.startsWith("/login"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Admin không thể truy cập trang student.");
            return;
        }

        filterChain.doFilter(request, response);
    }
}


