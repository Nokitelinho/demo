package com.ibsplc.neoicargo.relmgr.dao;

import com.ibsplc.neoicargo.relmgr.entity.ApplicationMaster;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			October 20, 2020 	  Binu K			First draft
 */
@Transactional
public interface ApplicationCatalogueRepository  extends CrudRepository<ApplicationMaster, ApplicationMaster.PK> {

}
