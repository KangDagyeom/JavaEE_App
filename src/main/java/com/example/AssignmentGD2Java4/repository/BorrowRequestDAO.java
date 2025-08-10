package com.example.AssignmentGD2Java4.repository;

import com.example.AssignmentGD2Java4.models.BorrowRequests;
import com.example.AssignmentGD2Java4.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class BorrowRequestDAO {
    private Session session;

    public BorrowRequestDAO() {
        session = HibernateUtil.getSession();
    }

    public void insert(BorrowRequests borrowRequests) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(borrowRequests);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }


    public List<BorrowRequests> findAllRequestByUserId(Integer id) {
        return session.createQuery("from BorrowRequests b where b.user.id = :id", BorrowRequests.class).setParameter("id", id).list();
    }
}
