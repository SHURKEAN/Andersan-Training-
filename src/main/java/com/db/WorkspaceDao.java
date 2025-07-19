package com.db;

import com.Workspace;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class WorkspaceDao {

    /** Persist a new workspace and return its generated ID */
    public int insert(String type, boolean available) {
        EntityManager em = JpaUtil.em();
        try {
            em.getTransaction().begin();
            Workspace w = new Workspace(type, available);
            em.persist(w);
            em.getTransaction().commit();
            return w.getId();
        } finally {
            em.close();
        }
    }

    /** Remove a workspace; returns true if deleted */
    public boolean delete(int id) {
        EntityManager em = JpaUtil.em();
        try {
            Workspace w = em.find(Workspace.class, id);
            if (w == null) return false;
            em.getTransaction().begin();
            em.remove(w);
            em.getTransaction().commit();
            return true;
        } finally {
            em.close();
        }
    }

    /** Fetch every workspace (ordered by ID in JPQL) */
    public List<Workspace> findAll() {
        EntityManager em = JpaUtil.em();
        try {
            TypedQuery<Workspace> q =
                    em.createQuery("from Workspace order by id", Workspace.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /** Toggle availability */
    public void updateAvailability(int id, boolean available) {
        EntityManager em = JpaUtil.em();
        try {
            Workspace w = em.find(Workspace.class, id);
            if (w == null) return;
            em.getTransaction().begin();
            w.setAvailable(available);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
