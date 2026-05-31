package com.tp.jpa.repository;

import com.tp.jpa.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T> {

    private final Class<T> entityClass;

    // Constructor que recibe la Class<T>

    public BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /*
     * 1. guardar(): persiste o actualiza la entidad usando merge()
     */

    public T guardar(T entity) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T merged = em.merge(entity);
            tx.commit();
            return merged;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error al guardar la entidad: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    /*
     * 2. buscarPorId(): retorna Optional<T>
     */

    public Optional<T> buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            T entity = em.find(entityClass, id);
            return Optional.ofNullable(entity);
        } finally {
            em.close();
        }
    }

    /*
     * 3. listarActivos(): retorna List<T> con eliminado = false
     */

    public List<T> listarActivos() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE coalesce(e.eliminado, false) = false";
            TypedQuery<T> query = em.createQuery(jpql, entityClass);
            return query.getResultList();
        } finally {
            em.close();  // Cierra en finally
        }
    }

    /*
     * 4. eliminarLogico(): busca por ID, setea eliminado = true y persiste
     * @return true si encontró y eliminó, false si no existe
     */

    public boolean eliminarLogico(Long id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T entity = em.find(entityClass, id);
            if (entity == null) {
                tx.rollback();
                return false;
            }

            String jpql = "UPDATE " + entityClass.getSimpleName() + " e SET e.eliminado = true WHERE e.id = :id";
            Query query = em.createQuery(jpql);
            query.setParameter("id", id);
            int actualizados = query.executeUpdate();
            tx.commit();
            return actualizados > 0;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }
}
