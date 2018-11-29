/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.exceptions.NonexistentEntityException;
import Controlador.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import persitencia.Prestamo;
import persitencia.Usuarios;
import persitencia.Productos;

/**
 *
 * @author jozaf
 */
public class PrestamoJpaController implements Serializable {

    public PrestamoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Prestamo prestamo) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuarios usuarioEmpelado = prestamo.getUsuarioEmpelado();
            if (usuarioEmpelado != null) {
                usuarioEmpelado = em.getReference(usuarioEmpelado.getClass(), usuarioEmpelado.getNombreUsuario());
                prestamo.setUsuarioEmpelado(usuarioEmpelado);
            }
            Productos idProducto = prestamo.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getId());
                prestamo.setIdProducto(idProducto);
            }
            em.persist(prestamo);
            if (usuarioEmpelado != null) {
                usuarioEmpelado.getPrestamoCollection().add(prestamo);
                usuarioEmpelado = em.merge(usuarioEmpelado);
            }
            if (idProducto != null) {
                idProducto.getPrestamoCollection().add(prestamo);
                idProducto = em.merge(idProducto);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Prestamo prestamo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Prestamo persistentPrestamo = em.find(Prestamo.class, prestamo.getId());
            Usuarios usuarioEmpeladoOld = persistentPrestamo.getUsuarioEmpelado();
            Usuarios usuarioEmpeladoNew = prestamo.getUsuarioEmpelado();
            Productos idProductoOld = persistentPrestamo.getIdProducto();
            Productos idProductoNew = prestamo.getIdProducto();
            if (usuarioEmpeladoNew != null) {
                usuarioEmpeladoNew = em.getReference(usuarioEmpeladoNew.getClass(), usuarioEmpeladoNew.getNombreUsuario());
                prestamo.setUsuarioEmpelado(usuarioEmpeladoNew);
            }
            if (idProductoNew != null) {
                idProductoNew = em.getReference(idProductoNew.getClass(), idProductoNew.getId());
                prestamo.setIdProducto(idProductoNew);
            }
            prestamo = em.merge(prestamo);
            if (usuarioEmpeladoOld != null && !usuarioEmpeladoOld.equals(usuarioEmpeladoNew)) {
                usuarioEmpeladoOld.getPrestamoCollection().remove(prestamo);
                usuarioEmpeladoOld = em.merge(usuarioEmpeladoOld);
            }
            if (usuarioEmpeladoNew != null && !usuarioEmpeladoNew.equals(usuarioEmpeladoOld)) {
                usuarioEmpeladoNew.getPrestamoCollection().add(prestamo);
                usuarioEmpeladoNew = em.merge(usuarioEmpeladoNew);
            }
            if (idProductoOld != null && !idProductoOld.equals(idProductoNew)) {
                idProductoOld.getPrestamoCollection().remove(prestamo);
                idProductoOld = em.merge(idProductoOld);
            }
            if (idProductoNew != null && !idProductoNew.equals(idProductoOld)) {
                idProductoNew.getPrestamoCollection().add(prestamo);
                idProductoNew = em.merge(idProductoNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = prestamo.getId();
                if (findPrestamo(id) == null) {
                    throw new NonexistentEntityException("The prestamo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Prestamo prestamo;
            try {
                prestamo = em.getReference(Prestamo.class, id);
                prestamo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The prestamo with id " + id + " no longer exists.", enfe);
            }
            Usuarios usuarioEmpelado = prestamo.getUsuarioEmpelado();
            if (usuarioEmpelado != null) {
                usuarioEmpelado.getPrestamoCollection().remove(prestamo);
                usuarioEmpelado = em.merge(usuarioEmpelado);
            }
            Productos idProducto = prestamo.getIdProducto();
            if (idProducto != null) {
                idProducto.getPrestamoCollection().remove(prestamo);
                idProducto = em.merge(idProducto);
            }
            em.remove(prestamo);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Prestamo> findPrestamoEntities() {
        return findPrestamoEntities(true, -1, -1);
    }

    public List<Prestamo> findPrestamoEntities(int maxResults, int firstResult) {
        return findPrestamoEntities(false, maxResults, firstResult);
    }

    private List<Prestamo> findPrestamoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Prestamo.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Prestamo findPrestamo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Prestamo.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrestamoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Prestamo> rt = cq.from(Prestamo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
