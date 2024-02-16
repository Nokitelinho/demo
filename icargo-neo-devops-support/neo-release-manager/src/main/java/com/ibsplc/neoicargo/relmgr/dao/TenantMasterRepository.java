package com.ibsplc.neoicargo.relmgr.dao;

import com.ibsplc.neoicargo.relmgr.entity.TenantMaster;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			November 26, 2020 	  Binu K			First draft
 */
public interface TenantMasterRepository extends CrudRepository<TenantMaster,String> {

    List<TenantMaster> findByIsActive(boolean isActive);
}
