package org.webonise.multihostpoc.session;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.io.File;

/**
 * Created by Webonise on 31/07/15.
 */
public class SessionFactoryBuilder {

    private final SessionFactory sessionFactory;

    public SessionFactoryBuilder(){
        this.sessionFactory = initSessionFactory();
    }
    private SessionFactory initSessionFactory() {
        // create configuration using hibernate API
        Configuration configuration = new Configuration();
        configuration = configuration.addResource("hibernate.cfg.xml");
        configuration = configuration.configure();
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public SessionFactory getSessionFactory(){
        return this.sessionFactory;
    }



}