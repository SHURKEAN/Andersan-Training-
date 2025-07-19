package com.db;

import com.Reservation;
import com.Workspace;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class ReservationDao {

    private final EntityManager em = JpaUtil.getEm();

    public List<Reservation> findAll() {
        return em.createQuery(
                        "SELECT r FROM Reservation r ORDER BY r.id", Reservation.class)
                .getResultList();
    }

    public int insert(String customer,
                      Workspace workspace,
                      String date,
                      String start,
                      String end) {

        Reservation r = new Reservation(customer, workspace, date, start, end);

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(r);
        tx.commit();

        return r.getId();
    }

    public Reservation findById(int id) {
        return em.find(Reservation.class, id);
    }

    public Workspace delete(int id) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Reservation r = em.find(Reservation.class, id);
        if (r != null) {
            Workspace w = r.getWorkspace();
            em.remove(r);
            tx.commit();
            return w;
        }
        tx.rollback();
        return null;
    }
}
