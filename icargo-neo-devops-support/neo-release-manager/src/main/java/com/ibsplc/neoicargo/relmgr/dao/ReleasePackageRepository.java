package com.ibsplc.neoicargo.relmgr.dao;

import com.ibsplc.neoicargo.relmgr.entity.ReleasePackage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			October 22, 2020 	  Binu K			First draft
 */
public interface ReleasePackageRepository extends CrudRepository<ReleasePackage,Long> {

    Optional<ReleasePackage> findByBldnumAndApplicationIdAndTenantIdAndEnvRef(long bldnum, String applicationId, String tenantId, String envRef);

    Optional<ReleasePackage> findByApplicationIdAndTenantIdAndEnvRefAndStatus(String applicationId, String tenantId, String envRef,String status);


    List<ReleasePackage> findByTenantIdAndStatusOrderByPlannedDateDesc(String tenantId,String status);
    List<ReleasePackage> findByTenantIdOrderByPlannedDateDesc(String tenantId);
}
