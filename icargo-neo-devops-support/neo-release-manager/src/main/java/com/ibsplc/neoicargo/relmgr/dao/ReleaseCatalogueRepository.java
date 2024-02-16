/*
 * ReleaseCatalogueRepository.java Created on 04/10/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.relmgr.dao;

import org.springframework.data.repository.CrudRepository;
import com.ibsplc.neoicargo.relmgr.entity.ReleaseCatalogue;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jens
 */
@Transactional
public interface ReleaseCatalogueRepository extends CrudRepository<ReleaseCatalogue, ReleaseCatalogue.PK> {
}
