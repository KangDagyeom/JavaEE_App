package com.example.AssignmentGD2Java4.repository;

import com.example.AssignmentGD2Java4.models.Books;
import com.example.AssignmentGD2Java4.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

    public void save(Books books) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(books);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public void update(Books books) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(books);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public void delete(Integer id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getFactory().openSession()) {
            tx = session.beginTransaction();
            Books book = session.get(Books.class, id);
            if (book != null) {
                session.remove(book);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}
