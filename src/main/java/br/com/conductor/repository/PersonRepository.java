package br.com.conductor.repository;

import br.com.conductor.heimdall.middleware.spec.DBMongo;
import br.com.conductor.heimdall.middleware.spec.Helper;
import br.com.conductor.heimdall.middleware.util.Page;
import br.com.conductor.model.Person;
import br.com.conductor.util.KeyVariables;

import java.util.List;

public class PersonRepository {

    private DBMongo dbMongo;

    public PersonRepository(Helper helper) {
        dbMongo = helper.dbMongo(helper.call().environment().getVariable(KeyVariables.DATABASE_NAME_MIDDLEWARES));
    }

    public Person save(Person person) {
        return dbMongo.save(person);
    }

    public Person findOne(Person person) {
        return dbMongo.findOne(person);
    }

    public Person findOneWithCriteria(Person person) {
        Page<Person> pageableFound = dbMongo.find(person, 0, 1);
        if (!pageableFound.hasContent) {
            return null;
        }
        return pageableFound.content.get(0);
    }

    public boolean delete(Person person) {
        return dbMongo.delete(person);
    }
}
