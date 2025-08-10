package com.example.AssignmentGD2Java4.repository;

import com.example.AssignmentGD2Java4.models.Users;
import com.example.AssignmentGD2Java4.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDAO {
    private Session session;

    public UserDAO() {
        session = HibernateUtil.getSession();

    }

    public Boolean checkEmailExist(String email) {
        Long count = (Long) session.createQuery("select count(u) from Users u where u.email= :email").setParameter("email", email).getSingleResult();
        return count != null && count > 0;

    }

    public Users login(String email) {
        return session.createQuery("from Users u where u.email = :email", Users.class).setParameter("email", email).getSingleResult();
    }

    public void insert(Users users) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            session.save(users);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
