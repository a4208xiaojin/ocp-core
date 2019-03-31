package com.nbd.ocp.core.repository;

import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaPersistableEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
//        repositoryBaseClass
public class OcpRepositoryImpl<T, ID extends Serializable>
  extends SimpleJpaRepository<T, ID> implements OcpRepository<T, ID> {
    private final   JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager em;
    private final PersistenceProvider provider;


    public OcpRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        Assert.notNull(entityInformation, "JpaEntityInformation must not be null!");
        Assert.notNull(entityManager, "EntityManager must not be null!");
        this.entityInformation = entityInformation;
        this.em = entityManager;
        this.provider = PersistenceProvider.fromEntityManager(entityManager);
    }
    public OcpRepositoryImpl(Class<T> domainClass, EntityManager em) {
        this(JpaPersistableEntityInformation.getEntityInformation(domainClass, em), em);
    }

    @Override
    public List<T> getA() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cQuery = builder.createQuery(getDomainClass());
        Root<T> root = cQuery.from(getDomainClass());
        cQuery.select(root);
        TypedQuery<T> query = em.createQuery(cQuery);



        return query.getResultList();
    }
}