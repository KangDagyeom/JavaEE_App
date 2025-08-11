package com.example.AssignmentGD2Java4.controller;

import com.example.AssignmentGD2Java4.models.Users;
import com.example.AssignmentGD2Java4.repository.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet(value = {"/register", "/login", "/dang-xuat"})
public class UserController extends HttpServlet {
    UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contextPath = req.getContextPath();
        String url = req.getRequestURI();
        String path = url.substring(contextPath.length());
        if ("/register".equals(path)) {
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
        } else if ("/login".equals(path)) {
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        } else if ("/dang-xuat".equals(path)) {
            // Hoac dung req.getSession().invalidate();
            req.getSession().removeAttribute("currentUser");
            resp.sendRedirect("/th04416/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contextPath = req.getContextPath();
        String url = req.getRequestURI();
        String path = url.substring(contextPath.length());
        if ("/register".equals(path)) {
            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String hashedPass = BCrypt.hashpw(password, BCrypt.gensalt());
            if (userDAO.checkEmailExist(email)) {
                req.setAttribute("error", "Email đã tồn tại");
                req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
                return;
            }

            Users newUser = new Users();
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setPassword(hashedPass);
            newUser.setRole(Users.UserRole.STUDENT);
            userDAO.insert(newUser);
            req.setAttribute("message", "Đăng ký thành công");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
        } else if ("/login".equals(path)) {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            Users user = userDAO.login(email);
            if (user != null && BCrypt.checkpw(password, user.getPassword())) {
                req.getSession().setAttribute("currentUser", user);
                resp.sendRedirect(req.getContextPath() + "/dashboard");
            } else {
                req.setAttribute("error", "Email hoặc mật khẩu không đúng");
                req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            }
        }
    }
}
