package liuwei.job.admin.service;

import liuwei.job.core.common.StatusCode;
import liuwei.job.core.exception.RegisterJobException;
import liuwei.job.core.ro.RegisterClientJobRO;
import liuwei.job.core.ro.RegisterClientRO;
import liuwei.job.core.ro.RegisterJobRO;

public interface ClientService {

    String registerClient(RegisterClientRO registerClientRO);

    String registerJavaJob(RegisterJobRO registerJobRO) throws RegisterJobException;

    StatusCode registerClientJob(RegisterClientJobRO registerClientJobRO);

}
