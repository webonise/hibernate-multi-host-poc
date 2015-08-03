package org.webonise.multihostpoc.daoimpl;

import org.hibernate.SessionFactory;
import org.webonise.multihostpoc.models.Person;

/**
 * Created by Webonise on 03/08/15.
 */
public class PersonDaoImpl extends GenericDaoImpl<Person,Integer> {
    public PersonDaoImpl(Class type, SessionFactory factory) {
        super(type, factory);
    }
}
