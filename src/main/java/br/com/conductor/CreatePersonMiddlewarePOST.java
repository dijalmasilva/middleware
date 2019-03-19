package br.com.conductor;

import br.com.conductor.dto.PersonResponseDTO;
import br.com.conductor.heimdall.middleware.enums.HttpStatus;
import br.com.conductor.heimdall.middleware.spec.Helper;
import br.com.conductor.heimdall.middleware.spec.Middleware;
import br.com.conductor.model.Person;
import br.com.conductor.service.CallService;
import br.com.conductor.service.PersonService;
import br.com.conductor.util.JsonUtil;

import java.util.List;

public class CreatePersonMiddlewarePOST implements Middleware {

    private PersonService personService;
    private CallService callService;

    @Override
    public void run(Helper helper) {
        initServices(helper);

        try {
            String cpf = helper.call().request().header().get("cpf");
            String name = helper.call().request().header().get("name");
            String personId = helper.call().request().header().get("personId");
            List<String> photos = helper.json().parse(helper.call().request().getBody(), List.class);

            if (!verifyAttributesIsNull(cpf, name, personId, photos)) {
                Person person = new Person();
                person.setCpf(cpf);
                person.setName(name);
                person.setPersonId(personId);
                person.setPhotos(photos);
                Person saved = personService.save(person);
                
                if (saved != null) {
                    callService.response(PersonResponseDTO.buildPersonResponse(saved), HttpStatus.CREATED);
                } else {
                    callService.response(JsonUtil.createResponseError("CPF already register."), HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            callService.response(JsonUtil.createResponseError(e.getMessage()), HttpStatus.BAD_GATEWAY);
        }
    }

    private boolean verifyAttributesIsNull(String cpf, String name, String personId, List<String> photos) {
        if (cpf == null || cpf.length() != 11) {
            this.callService.response(JsonUtil.createResponseError("CPF valid is required."), HttpStatus.BAD_REQUEST);
            return true;
        }

        if (name == null || name.isEmpty()) {
            this.callService.response(JsonUtil.createResponseError("Name is required."), HttpStatus.BAD_REQUEST);
            return true;
        }

        if (personId == null || personId.isEmpty()){
            this.callService.response(JsonUtil.createResponseError("PersonId is required."), HttpStatus.BAD_REQUEST);
            return true;
        }

        if (photos == null || photos.size() == 0){
            this.callService.response(JsonUtil.createResponseError("Photos is required."), HttpStatus.BAD_REQUEST);
            return true;   
        }

        return false;
    }

    private void initServices(Helper helper) {
        this.personService = new PersonService(helper);
        this.callService = new CallService(helper);
    }
}
