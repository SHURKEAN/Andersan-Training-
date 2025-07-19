package com.db;

import jakarta.persistence.*;

public final class JpaUtil {

    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("coworkingPU");

    public static EntityManager em() {
        return EMF.createEntityManager();
    }

    public static EntityManager getEm() {
        return null;
    }
}
