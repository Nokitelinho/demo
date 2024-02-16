package com.ibsplc.neoicargo.relmgr.dao;

import com.ibsplc.neoicargo.relmgr.entity.SystemParameter;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			October 28, 2020 	  Binu K			First draft
 */
public interface SystemParameterRepository extends CrudRepository<SystemParameter,String> {
}
