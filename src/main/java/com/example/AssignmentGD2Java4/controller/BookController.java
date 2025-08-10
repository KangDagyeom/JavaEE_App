package com.example.AssignmentGD2Java4.controller;

import com.example.AssignmentGD2Java4.models.Users;
import com.example.AssignmentGD2Java4.repository.BookDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = {"/dashboard", "/books"})
public class BookController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        BookDAO bookDAO = new BookDAO();
        Users currentUser = (Users) req.getSession().getAttribute("currentUser");

        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String keyword = req.getParameter("keyword");

        if (keyword != null && !keyword.trim().isEmpty()) {
            req.setAttribute("books", bookDAO.findByNameOrAuthor(keyword.trim()));
        } else {
            req.setAttribute("books", bookDAO.getAll());
        }

        req.setAttribute("user", currentUser);
        req.getRequestDispatcher("/views/dashboard.jsp").forward(req, resp);
    }
}

