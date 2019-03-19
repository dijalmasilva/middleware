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

public class AddImagesToPersonPUT implements Middleware {

    private PersonService personService;
    private CallService callService;

    @Override
    public void run(Helper helper) {
        initServices(helper);

        try {

            String id = helper.call().request().header().get("id");
            List<String> photos = helper.json().parse(helper.call().request().getBody(), List.class);

            if (id != null && !id.isEmpty()){
                Person personById = personService.findPersonById(id);
                personById.addPhotos(photos);
                Person updatedPerson = personService.updatePerson(personById);
                callService.response(PersonResponseDTO.buildPersonResponse(updatedPerson), HttpStatus.OK);
            } else {
                String cpf = helper.call().request().header().get("cpf");

                if (cpf != null) {
                    if (cpf.length() != 11) {
                        callService.response(JsonUtil.createResponseError("CPF not valid."), HttpStatus.BAD_REQUEST);
                    } else {
                        Person personByCpf = personService.findPersonByCpf(cpf);
                        personByCpf.addPhotos(photos);
                        Person updatedPerson = personService.updatePerson(personByCpf);
                        callService.response(PersonResponseDTO.buildPersonResponse(updatedPerson), HttpStatus.OK);
                    }
                } else {
                    String name = helper.call().request().header().get("name");

                    if (name == null || name.isEmpty()) {
                        callService.response(JsonUtil.createResponseError("Id, CPF or Name is required."), HttpStatus.BAD_REQUEST);
                    } else {
                        Person personByName = personService.findPersonByName(name);
                        personByName.addPhotos(photos);
                        Person updatedPerson = personService.updatePerson(personByName);
                        callService.response(PersonResponseDTO.buildPersonResponse(updatedPerson), HttpStatus.OK);
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            callService.response(JsonUtil.createResponseError(ex.getMessage()), HttpStatus.BAD_GATEWAY);
        }
    }

    private void initServices(Helper helper) {
        this.personService = new PersonService(helper);
        this.callService = new CallService(helper);
    }
}
