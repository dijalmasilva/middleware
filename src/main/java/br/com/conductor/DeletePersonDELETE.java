package br.com.conductor;

import br.com.conductor.heimdall.middleware.enums.HttpStatus;
import br.com.conductor.heimdall.middleware.spec.Helper;
import br.com.conductor.heimdall.middleware.spec.Middleware;
import br.com.conductor.service.CallService;
import br.com.conductor.service.PersonService;
import br.com.conductor.util.JsonUtil;

public class DeletePersonDELETE implements Middleware {

    private PersonService personService;
    private CallService callService;

    @Override
    public void run(Helper helper) {
        initServices(helper);

        try {

            String cpf = helper.call().request().header().get("cpf");

            if (cpf != null && cpf.length() == 11) {
                if (personService.deleteByCpf(cpf)){
                    callService.response(JsonUtil.createResponseMessage("Person removed with success!"), HttpStatus.OK);
                } else {
                    callService.response(JsonUtil.createResponseError("Person not found!"), HttpStatus.NOT_FOUND);
                }
            } else {
                callService.response(JsonUtil.createResponseError("Please, input a cpf valid!"), HttpStatus.BAD_REQUEST);
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
