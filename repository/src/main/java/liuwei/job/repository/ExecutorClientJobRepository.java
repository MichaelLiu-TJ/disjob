package liuwei.job.repository;

import liuwei.job.core.model.ExecutorClientJob;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExecutorClientJobRepository extends CrudRepository<ExecutorClientJob, Long> {

    List<ExecutorClientJob> findByJobId(@Param("jobId")long jobId);

}
