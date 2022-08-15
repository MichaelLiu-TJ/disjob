package liuwei.job.repository;


import liuwei.job.core.model.JobGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobGroupRepository extends CrudRepository<JobGroup, Long> {

    List<JobGroup> findByGroupName(@Param("groupName") String groupName);

}
