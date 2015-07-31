package org.webonise.multihostpoc.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Webonise on 31/07/15.
 */
public interface GenericDao<T> {

    void save( T object);

    List<T> readAll();

    void setIsReadOnlyFlag(boolean isReadOnlyFlag);

}
