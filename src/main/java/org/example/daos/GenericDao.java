package org.example.daos;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public abstract class GenericDao <T , ID>{
    private Session session;
    private Transaction transaction;
    private  Class<T>aClass;

    protected GenericDao(Session session ,Class<T> aClass) {
        this.session = session;
        this.aClass = aClass;
    }

    protected T create(T entity){
        transaction = session.beginTransaction();
        try {
            session.persist(entity);
            transaction.commit();
        }catch (Exception e ){
            transaction.rollback();
            e.printStackTrace();
        }
        return entity;
    }
    protected T update(T entity){
        try {
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
        }
        return entity;
    }
    protected void delete(T entity){
        try {
            transaction = session.beginTransaction();
            session.remove(entity);
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
        }

    }
    public T findById(ID id){

        return Optional.ofNullable(session.find(aClass,id)).orElse(null);
    }
    public List<T> findAll(){
        String query = "select record from "+ aClass.getSimpleName()+" as record";
        Query<T> findAllQuery = session.createQuery(query);
        return findAllQuery.getResultList();

    }
}
