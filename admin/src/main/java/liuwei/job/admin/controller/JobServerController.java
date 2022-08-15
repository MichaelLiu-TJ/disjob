package liuwei.job.admin.controller;


import liuwei.job.admin.service.ClientService;
import liuwei.job.core.common.Constants;
import liuwei.job.core.common.Response;
import liuwei.job.core.common.StatusCode;
import liuwei.job.core.exception.RegisterJobException;
import liuwei.job.core.ro.RegisterClientJobRO;
import liuwei.job.core.ro.RegisterClientRO;
import liuwei.job.core.ro.RegisterJobRO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = Constants.API_SERVER_PREFIX)
public class JobServerController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClientService clientService;

    @PostMapping(value = "registerClient", produces = "application/json;charset=UTF-8")
    public Response<String> registerClient(@RequestBody RegisterClientRO registerClientRO) {
        try {
            return Response.success(clientService.registerClient(registerClientRO));
        } catch (Exception e) {
            return new Response<>(StatusCode.REGISTER_JOB_FAILED);
        }
    }

    @PostMapping(value = "registerJavaJob", produces = "application/json;charset=UTF-8")
    public Response<String> registerJavaJob(@RequestBody RegisterJobRO registerJobRO) {
        try {
            String result = clientService.registerJavaJob(registerJobRO);
            return Response.success(result);
        } catch (RegisterJobException e) {
            return new Response<>(StatusCode.REGISTER_JOB_FAILED);
        }
    }

    @PostMapping(value = "registerShellJob", produces = "application/json;charset=UTF-8")
    public Response<String> registerShellJob(@RequestBody RegisterJobRO registerJobRO) {
        // TODO
        return new Response<>(StatusCode.FUNCTION_NOT_IMPLEMENTATION);
    }

    @PostMapping(value = "registerPythonJob", produces = "application/json;charset=UTF-8")
    public Response<String> registerPythonJob(@RequestBody RegisterJobRO registerJobRO) {
        // TODO
        return new Response<>(StatusCode.FUNCTION_NOT_IMPLEMENTATION);
    }


    @PostMapping(value = "registerClientJob", produces = "application/json;charset=UTF-8")
    public Response<String> registerClientJob(@RequestBody RegisterClientJobRO registerClientJobRO) {
        try {
            return new Response<>(clientService.registerClientJob(registerClientJobRO));
        } catch (Exception e) {
            return new Response<>(StatusCode.REGISTER_CLIENT_JOB_FAILED);
        }
    }


}
