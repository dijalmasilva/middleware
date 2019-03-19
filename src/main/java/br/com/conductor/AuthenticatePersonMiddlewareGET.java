package br.com.conductor;

import br.com.conductor.heimdall.middleware.enums.HttpStatus;
import br.com.conductor.heimdall.middleware.spec.Helper;
import br.com.conductor.heimdall.middleware.spec.Middleware;
import br.com.conductor.service.CallService;
import br.com.conductor.service.PersonService;
import br.com.conductor.util.JsonUtil;

public class AuthenticatePersonMiddlewareGET implements Middleware {

    private PersonService personService;
    private CallService callService;

    @Override
    public void run(Helper helper) {
        initServices(helper);

        try {

            String id = helper.call().request().header().get("id");

            if (id != null && !id.isEmpty()){
                createResponseToPersonFound(personService.findById(id));
            } else {
                String cpf = helper.call().request().header().get("cpf");

                if (cpf != null) {
                    if (cpf.length() != 11) {
                        callService.response(JsonUtil.createResponseError("CPF not valid."), HttpStatus.BAD_REQUEST);
                    } else {
                        createResponseToPersonFound(personService.findPersonIdByCpf(cpf));
                    }
                } else {
                    String name = helper.call().request().header().get("name");

                    if (name == null || name.isEmpty()) {
                        callService.response(JsonUtil.createResponseError("Id, CPF or Name is required."), HttpStatus.BAD_REQUEST);
                    } else {
                        String personId = personService.findPersonIdByName(name);
                        createResponseToPersonFound(personId);
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            callService.response(JsonUtil.createResponseError(ex.getMessage()), HttpStatus.BAD_GATEWAY);
        }
    }

    private void createResponseToPersonFound(String personId){
        if (personId != null) {
            callService.response(JsonUtil.createResponsePersonId(personId), HttpStatus.OK);
        } else {
            callService.response(JsonUtil.createResponseError("Person not found."), HttpStatus.NOT_FOUND);
        }
    }

    private void initServices(Helper helper) {
        this.personService = new PersonService(helper);
        this.callService = new CallService(helper);
    }
}
