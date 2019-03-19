package br.com.conductor.service;

import br.com.conductor.heimdall.middleware.spec.Helper;
import br.com.conductor.model.Person;
import br.com.conductor.repository.PersonRepository;
import org.bson.types.ObjectId;

public class PersonService {

    private PersonRepository repository;

    public PersonService(Helper helper) {
        this.init(helper);
    }

    public Person save(Person person) {
        Person pCriteria = new Person();
        pCriteria.setCpf(person.getCpf());
        if (this.repository.findOneWithCriteria(pCriteria) != null){
            return null;
        }
        person.setName(person.getName().toUpperCase());
        return this.repository.save(person);
    }

    public String findById(String id) {
        Person person = new Person();
        person.setId(new ObjectId(id));
        Person p = this.repository.findOne(person);
        return p == null ? null : p.getPersonId();
    }

    public String findPersonIdByName(String name) {
        Person person = new Person();
        person.setName(name.toUpperCase());
        Person p = this.repository.findOneWithCriteria(person);
        return p == null ? null : p.getPersonId();
    }

    public String findPersonIdByCpf(String cpf) {
        Person person = new Person();
        person.setCpf(cpf);
        Person p = this.repository.findOneWithCriteria(person);
        return p == null ? null : p.getPersonId();
    }

    public Person findPersonById(String id) {
        Person person = new Person();
        person.setId(new ObjectId(id));
        return this.repository.findOne(person);
    }

    public Person findPersonByName(String name) {
        Person person = new Person();
        person.setName(name.toUpperCase());
        return this.repository.findOneWithCriteria(person);
    }

    public Person findPersonByCpf(String cpf) {
        Person person = new Person();
        person.setCpf(cpf);
        return this.repository.findOneWithCriteria(person);
    }

    public Person updatePerson(Person p) {
        return this.repository.save(p);
    }

    public boolean deleteByCpf(String cpf) {
        Person personByCpf = findPersonByCpf(cpf);
        if (personByCpf != null)
            return this.repository.delete(personByCpf);

        return false;
    }

    private void init(Helper helper) {
        repository = new PersonRepository(helper);
    }
}
