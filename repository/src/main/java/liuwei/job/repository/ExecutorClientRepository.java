package liuwei.job.repository;

import liuwei.job.core.model.ExecutorClient;
import liuwei.job.core.model.emnu.ExecutorClientStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ExecutorClientRepository extends CrudRepository<ExecutorClient, Long> {

    List<ExecutorClient> findByHealthCheckFailedCountLessThan(int retryCount);

    List<ExecutorClient> findBySerialString(@Param("serialString") String serialString);

    @Modifying
    @Transactional
    @Query("update ExecutorClient set healthCheckStatus=:healthCheckStatus, healthCheckFailedCount=0 where id=:id")
    int updateHealthCheckSuccess(@Param("healthCheckStatus") ExecutorClientStatus healthCheckStatus,
                                 @Param("id") long id);

    @Modifying
    @Transactional
    @Query("update ExecutorClient set healthCheckStatus=:healthCheckStatus, healthCheckFailedCount=:healthCheckFailedCount where id=:id")
    int updateHealthCheckFailed(@Param("healthCheckStatus") ExecutorClientStatus healthCheckStatus,
                                @Param("healthCheckFailedCount") int healthCheckFailedCount,
                                @Param("id") long id);

}
