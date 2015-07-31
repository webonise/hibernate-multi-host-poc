package org.webonise.multihostpoc.daoimpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

import org.webonise.multihostpoc.dao.GenericDao;


/**
 * Created by Webonise on 31/07/15.
 */
public class GenericDaoImpl<T,PK extends Serializable> implements GenericDao<T> {

    private static final Logger LOG = LoggerFactory.getLogger(GenericDaoImpl.class);
    private Class<T> type;
    private final SessionFactory sessionFactory;
    private Session session = null;
    private boolean isReadOnlyFlag;

    public GenericDaoImpl(Class<T>type,SessionFactory factory){
        this.type = type;
        this.sessionFactory = factory;
    }


    @Override
    public void save(T object) {
        Session currentSession = getSession();
        prepSession(currentSession, isReadOnlyFlag());
        currentSession.getTransaction().begin();
        currentSession.saveOrUpdate(object);
        currentSession.getTransaction().commit();
    }

    @Override
    public List<T> readAll() {
        Session currentSession = getSession();
        prepSession(currentSession,isReadOnlyFlag());
        Criteria criteria = currentSession.createCriteria(type);
        List<T> result = (List<T>) criteria.list();
        return result;
    }

    private Session getSession(){
        if(this.session == null ){
            this.session = sessionFactory.openSession();
        }
        return  this.session;
    }

    private void prepSession(Session session,boolean readOnlyFlag) {
        try {
            SessionImpl sessionImpl = (SessionImpl) session;
            sessionImpl.connection().setReadOnly(readOnlyFlag);
        }
        catch (Exception e){
            LOG.error("Error occured in prepping the session.");
        }
    }

    public boolean isReadOnlyFlag() {
        return isReadOnlyFlag;
    }

    @Override
    public void setIsReadOnlyFlag(boolean isReadOnlyFlag) {
        this.isReadOnlyFlag = isReadOnlyFlag;
    }

}
