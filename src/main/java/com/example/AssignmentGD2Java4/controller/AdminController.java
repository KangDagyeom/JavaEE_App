package com.example.AssignmentGD2Java4.controller;

import com.example.AssignmentGD2Java4.models.Books;
import com.example.AssignmentGD2Java4.models.BorrowRequests;
import com.example.AssignmentGD2Java4.models.Users;
import com.example.AssignmentGD2Java4.repository.BookDAO;
import com.example.AssignmentGD2Java4.repository.BorrowRequestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = {"/admin/admin-login", "/admin/adminDashboard", "/admin/borrow_requests", "/admin/update_request", "/admin/books/delete", "/admin/books/update", "/admin/books/add"})
public class AdminController extends HttpServlet {
    BorrowRequestDAO borrowRequestDAO = new BorrowRequestDAO();
    BookDAO bookDAO = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/admin/admin-login".equals(path)) {
            req.getRequestDispatcher("/views/admin/login.jsp").forward(req, resp);
        } else if ("/admin/adminDashboard".equals(path)) {
            req.setAttribute("books", bookDAO.getAll());
            req.getRequestDispatcher("/views/admin/bookList.jsp").forward(req, resp);
        } else if ("/admin/borrow_requests".equals(path)) {

            req.setAttribute("borrowRequests", borrowRequestDAO.getListOfBorrow());
            req.getRequestDispatcher("/views/admin/borrow_requests.jsp").forward(req, resp);
        } else if ("/admin/books/delete".equals(path)) {
            Integer id = Integer.valueOf(req.getParameter("id"));
            bookDAO.delete(id);
            resp.sendRedirect("/th04416/admin/adminDashboard");
        } else if ("/admin/books/update".equals(path)) {
            Integer id = Integer.valueOf(req.getParameter("id"));
            req.setAttribute("b", bookDAO.findById(id));
            req.getRequestDispatcher("/views/admin/updateBook.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/admin/admin-login".equals(path)) {
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            if ("admin@lib.com".equals(email) && "admin123".equals(password)) {
                Users admin = new Users();
                admin.setEmail(email);
                admin.setRole(Users.UserRole.ADMIN);
                req.getSession().setAttribute("currentUser", admin);
                resp.sendRedirect(req.getContextPath() + "/admin/adminDashboard");

            } else {
                req.setAttribute("error", "Sai thông tin đăng nhập");
                req.getRequestDispatcher("/views/admin/login.jsp").forward(req, resp);
            }
        } else if ("/admin/update_request".equals(path)) {
            Integer id = Integer.valueOf(req.getParameter("id"));
            BorrowRequests.RequestStatus requestStatus = BorrowRequests.RequestStatus.valueOf(req.getParameter("action"));
            borrowRequestDAO.updateRequestsStatus(id, requestStatus);
            resp.sendRedirect("/th04416/admin/borrow_requests");

        } else if ("/admin/books/add".equals(path)) {
            String ten = req.getParameter("title");
            String tacGia = req.getParameter("author");
            Integer quantity = Integer.valueOf(req.getParameter("quantity"));
            Books books = new Books();
            books.setTitle(ten);
            books.setAuthor(tacGia);
            books.setQuantity(quantity);
            bookDAO.save(books);
            resp.sendRedirect("/th04416/admin/adminDashboard");

        } else if ("/admin/books/update".equals(path)) {
            Integer id = Integer.valueOf(req.getParameter("id"));
            String ten = req.getParameter("title");
            String tacGia = req.getParameter("author");
            Integer quantity = Integer.valueOf(req.getParameter("quantity"));
            Books books = bookDAO.findById(id);
            books.setTitle(ten);
            books.setAuthor(tacGia);
            books.setQuantity(quantity);
            bookDAO.update(books);
            resp.sendRedirect("/th04416/admin/adminDashboard");

        }


    }
}
