package br.com.conductor.service;

import br.com.conductor.heimdall.middleware.enums.HttpStatus;
import br.com.conductor.heimdall.middleware.spec.Helper;
import br.com.conductor.util.Constants;

public class CallService {

    private Helper helper;

    public CallService(Helper helper) {

        this.helper = helper;
    }

    public void response(Object body, HttpStatus status) {
        helper.call().response().header().add(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON);
        helper.call().response().setStatus(status.value());
        helper.call().response().setBody(helper.json().parse(body));
    }

}
