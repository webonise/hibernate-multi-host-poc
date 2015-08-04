package org.webonise.multihostpoc.dao;

import java.util.List;

/**
 * Created by Webonise on 31/07/15.
 */
public interface GenericDao<T> {

    void save( T object);

    List<T> readAll();

    T getById(Integer id);

    void setIsReadOnlyFlag(boolean isReadOnlyFlag);

    boolean isMasterDBConnection();

}
