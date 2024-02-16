/*
 * ArtifactTestMasterRepository.java Created on 04/10/21
 *
 * Copyright 2021 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.relmgr.dao;

import com.ibsplc.neoicargo.relmgr.entity.ArtifactTestMaster;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author jens
 */
public interface ArtifactTestMasterRepository extends CrudRepository<ArtifactTestMaster, ArtifactTestMaster.PK> {

    List<ArtifactTestMaster> findByPkArtifactIdAndPkTenantId(String artifactId, String tenantId);

}
