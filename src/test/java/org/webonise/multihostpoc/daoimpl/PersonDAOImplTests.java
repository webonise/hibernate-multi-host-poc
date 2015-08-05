package org.webonise.multihostpoc.daoimpl;



import org.hibernate.exception.GenericJDBCException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.webonise.multihostpoc.dao.GenericDao;
import org.webonise.multihostpoc.models.Person;
import org.webonise.multihostpoc.session.SessionFactoryBuilder;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;


/**
 * Created by webonise on 30/7/15.
 */
public class PersonDAOImplTests {
    private static final Logger LOG = LoggerFactory.getLogger(PersonDAOImplTests.class);

    private final SessionFactoryBuilder builder =  new SessionFactoryBuilder();
    private final GenericDao<Person> personDaoImpl =  new PersonDaoImpl(Person.class,builder.getSessionFactory());

    @Test
    public void testSessionFactoryCreationSucceeds(){
        Assert.assertNotNull(builder.getSessionFactory());
    }

    @Test
    public void testSaveData(){
        LOG.info("running the save data");
        Person p = getFixture();
        personDaoImpl.setIsReadOnlyFlag(false);
        personDaoImpl.save(p);

    }

    @Test
    public void testCaching(){
        LOG.info("running the save data");
        Person p = getFixture();
        personDaoImpl.setIsReadOnlyFlag(false);
        personDaoImpl.save(p);
        personDaoImpl.setIsReadOnlyFlag(true);
        Assert.assertFalse(personDaoImpl.isMasterDBConnection());
        Person record = personDaoImpl.getById(p.getId());
        Assert.assertTrue(p == record);
    }

    @Test(expectedExceptions = GenericJDBCException.class)
    public void testSaveDataFailure(){
        LOG.info("running the save data");
        Person p = getFixture();
        personDaoImpl.setIsReadOnlyFlag(true);
        personDaoImpl.save(p);

    }

    @Test
    public void testReadData(){
        LOG.info("running the read data");
        personDaoImpl.setIsReadOnlyFlag(true);
        List<Person> result =  personDaoImpl.readAll();
        Assert.assertNotNull(result);
        LOG.info("Population is " + result.size());
        for(Person p : result){
            LOG.info(p.toString());
        }

    }

    @Test
    public void testSlaveDBForReadConnection(){
        personDaoImpl.setIsReadOnlyFlag(true);
        Assert.assertFalse(personDaoImpl.isMasterDBConnection());
    }

    @Test
    public void testMasterDBForWriteConnection() {
        personDaoImpl.setIsReadOnlyFlag(false);
        Assert.assertTrue(personDaoImpl.isMasterDBConnection());
    }


    private Person getFixture() {
        Person person = new Person();
        SecureRandom random = new SecureRandom();

        person.setName("John");
        person.setSurname("Doe");
        person.setAddress("Timbuktoo");
        person.setNationality("Timbuktooin");
        person.setOccupation("Forager");
        person.setSocialId(new BigInteger(130, random).toString(32)); //just generating a fancy random string
        return person;
    }

    /*TODO:
    Mock out the prepSessionMethod to set the connection.readOnly to true and call the save method
    */
}
