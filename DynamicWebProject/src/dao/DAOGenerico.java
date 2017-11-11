package dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

public abstract class DAOGenerico<E> implements IDAOGenerico<E> {
	private EntityManagerFactory entityManagerFactory = DAOFactory.getInstance();
	private Class<E> persistentClass;
			
	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	@SuppressWarnings("unchecked")
	public DAOGenerico(EntityManagerFactory emf){
		this.setEntityManagerFactory(emf);
		this.entityManagerFactory = emf;
		ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();  
	    persistentClass = (Class<E>) parameterizedType.getActualTypeArguments()[0];  
	}
	
	@Override
	public void inserir(E e) {
		EntityManager em = this.entityManagerFactory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.persist(e);
			tx.commit();
			em.close();
		} catch (PersistenceException ex) {
			tx.rollback();
		}

	}

	@Override
	public void excluir(E e) {
		EntityManager em = this.entityManagerFactory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		// Este merge foi incluido para permitir a exclusao de objetos no estado
		// Detached
		e = em.merge(e);

		em.remove(e);

		tx.commit();

		em.close();

	}

	@Override
	public void alterar(E e) {
		EntityManager em = this.entityManagerFactory.createEntityManager();
		em.refresh(e);
		em.close();
	}

	@Override
	public E buscarPorID(Long id) {
		E instance = null;
		EntityManager em = this.entityManagerFactory.createEntityManager();
		try {
			instance = (E) em.find(getPersistentClass(), id);
		} catch (RuntimeException re) {
			re.printStackTrace();
		}
		em.close();
		return instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> listar() {
		List<E> instance = null;
		EntityManager em = this.entityManagerFactory.createEntityManager();
		try {
			instance = ((List<E>) em.createQuery("from " + getPersistentClass().getName()).getResultList());
		} catch (RuntimeException re) {
			re.printStackTrace();
		}
		em.close();
		return instance;
	}

	protected final Class<E> getPersistentClass() {
		return persistentClass;
	}
}
