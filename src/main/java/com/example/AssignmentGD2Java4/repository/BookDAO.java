package com.example.AssignmentGD2Java4.repository;

import com.example.AssignmentGD2Java4.models.Books;
import com.example.AssignmentGD2Java4.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class BookDAO {
    private Session session;

    public BookDAO() {
        session = HibernateUtil.getSession();
    }

    public List<Books> getAll() {
        return session.createQuery("from Books ").list();

    }

    public List<Books> findByNameOrAuthor(String keyword) {
        String keyFormat = "%" + keyword.toLowerCase() + "%";
        return session.createQuery(
                "from Books b where lower(b.title) like :keyword or lower(b.author) like :keyword", Books.class)
                .setParameter("keyword", keyFormat)
                .list();
    }

    public Books findById(Integer id) {
        return session.createQuery(
                "from Books b where b.id = :id", Books.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
