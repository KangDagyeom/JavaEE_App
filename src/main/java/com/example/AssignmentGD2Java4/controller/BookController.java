package com.example.AssignmentGD2Java4.controller;

import com.example.AssignmentGD2Java4.models.Books;
import com.example.AssignmentGD2Java4.models.BorrowRequests;
import com.example.AssignmentGD2Java4.models.Users;
import com.example.AssignmentGD2Java4.repository.BookDAO;
import com.example.AssignmentGD2Java4.repository.BorrowRequestDAO;
import com.example.AssignmentGD2Java4.repository.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(value = {"/dashboard", "/books", "/borrow"})
public class BookController extends HttpServlet {
    BookDAO bookDAO = new BookDAO();
    BorrowRequestDAO borrowRequestDAO = new BorrowRequestDAO();
    UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getServletPath();
        Users currentUser = (Users) req.getSession().getAttribute("currentUser");

        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        if ("/dashboard".equals(path) || "/books".equals(path)) {
            String keyword = req.getParameter("keyword");
            if (keyword != null && !keyword.trim().isEmpty()) {
                req.setAttribute("books", bookDAO.findByNameOrAuthor(keyword.trim()));
            } else {
                req.setAttribute("books", bookDAO.getAll());
            }
            req.setAttribute("user", currentUser);
            req.getRequestDispatcher("/views/dashboard.jsp").forward(req, resp);
        } else if ("/borrow".equals(path)) {
            req.setAttribute("borrowRequests", borrowRequestDAO.findAllRequestByUserId(currentUser.getId()));
            req.getRequestDispatcher("/views/borrowRequest.jsp").forward(req, resp);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Users currentUser = (Users) req.getSession().getAttribute("currentUser");

        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        String path = req.getServletPath();
        if ("/borrow".equals(path)) {
            Integer bookId = Integer.valueOf(req.getParameter("id"));
            Books book = bookDAO.findById(bookId);
            if (book == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Book not found");
                return;
            }
            BorrowRequests borrowRequests = new BorrowRequests();
            Users users = userDAO.findById(currentUser.getId());
            borrowRequests.setUser(users);
            borrowRequests.setBook(book);
            borrowRequests.setStatus(BorrowRequests.RequestStatus.PENDING);
            borrowRequests.setRequestDate(LocalDateTime.now());
            borrowRequestDAO.insert(borrowRequests);
            resp.sendRedirect("/th04416/borrow");
        }


    }
}

