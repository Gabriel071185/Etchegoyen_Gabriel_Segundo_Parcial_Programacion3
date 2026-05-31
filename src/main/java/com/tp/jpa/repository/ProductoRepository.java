package com.tp.jpa.repository;

import com.tp.jpa.model.Producto;
import com.tp.jpa.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ProductoRepository extends BaseRepository<Producto> {

    // Constructor que llama a super con la Class de Producto
    public ProductoRepository() {
        super(Producto.class);
    }

    /*
     * Consulta JPQL para buscar productos activos por categoría.
     *
     * La consulta filtra productos donde:
     * - La categoría coincida con el ID proporcionado (parámetro nombrado :categoriaId)
     * - El producto esté activo (eliminado = false)
     * - Se usa TypedQuery<Producto> sin casteos manuales
     *
     * @param categoriaId ID de la categoría a filtrar
     * @return Lista de productos activos de esa categoría
     */

    public List<Producto> buscarPorCategoria(Long categoriaId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            // JPQL con parámetro nombrado :categoriaId
            String jpql = "SELECT p FROM Producto p WHERE p.categoria.id = :categoriaId AND p.eliminado = false";

            // TypedQuery<Producto> - sin casteo manual
            TypedQuery<Producto> query = em.createQuery(jpql, Producto.class);

            // Setear el parámetro nombrado
            query.setParameter("categoriaId", categoriaId);

            return query.getResultList();
        } finally {
            em.close();
        }
    }
}