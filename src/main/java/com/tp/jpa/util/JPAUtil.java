package com.tp.jpa.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    // Nombre de la unidad de persistencia (debe coincidir con persistence.xml)
    private static final String PERSISTENCE_UNIT_NAME = "Segundo_Parcial";

    // EntityManagerFactory - se crea UNA SOLA VEZ (Singleton)
    private static final EntityManagerFactory emf;

    // Bloque estático: se ejecuta cuando se carga la clase
    static {
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Exception e) {
            System.err.println("Error al crear EntityManagerFactory: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Retorna el EntityManagerFactory (único)
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    /**
     * Crea y retorna un nuevo EntityManager
     */
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Cierra el EntityManagerFactory (llamar al finalizar la aplicación)
     */
    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}