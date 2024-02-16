/*
 * ULDInventoryMultiMapper.java Created on May 27, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.uld.defaults.stock.vo.InventoryULDVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDInventoryDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2883
 *
 */

public class ULDInventoryMultiMapper implements MultiMapper<InventoryULDVO>{
	
	 private Log log = LogFactory.getLogger(" ULD_DEFAULTS");
	 private static final String ULD = "ULD";
  /**
   * @param rs
   * @return ULDAgreementVO
   * @throws SQLException
   */
    public List<InventoryULDVO> map(ResultSet resultSet)
    throws SQLException{
    	log.entering("ULDInventoryMapper","map");
    	
    	List<InventoryULDVO> listVOs = new ArrayList<InventoryULDVO>();
    	InventoryULDVO inventoryULDVO = new InventoryULDVO();
		Collection<ULDInventoryDetailsVO> coll = new ArrayList<ULDInventoryDetailsVO>();
    	String previousPK = "";
    	String previousChildKey="";
    	boolean flag = false;
    	while(resultSet.next()){
			String currentPK = new StringBuilder(resultSet.getString("CMPCOD"))
    			.append(resultSet.getString("ARPCOD"))
    			.append(resultSet.getString("ULDTYP"))	
    			.append(resultSet.getString("SEQNUM")).toString();
			if(!currentPK.equals(previousPK)){
    			if(flag){
    				inventoryULDVO.setUldInventoryDetailsVOs(coll);
    				listVOs.add(inventoryULDVO);
    			}
    			inventoryULDVO = new InventoryULDVO();
    			coll=new ArrayList<ULDInventoryDetailsVO>();
    			previousPK=currentPK;
    			inventoryULDVO.setCompanyCode(resultSet.getString("CMPCOD"));
    	    	inventoryULDVO.setAirportCode(resultSet.getString("ARPCOD"));
    	    	inventoryULDVO.setUldType(resultSet.getString("ULDTYP"));
    	    	inventoryULDVO.setSequenceNumber(resultSet.getString("SEQNUM"));
    	    	inventoryULDVO.setParentPrimaryKey(currentPK);
    	    	inventoryULDVO.setDetailsFlag(ULD);
    	    	flag=true;
    	    	
    	    	}
    		String currentChildKey = new StringBuilder(resultSet.getString("CMPCOD"))
			.append(resultSet.getString("ARPCOD"))
			.append(resultSet.getString("ULDTYP"))
			.append(resultSet.getString("SEQNUM"))
			.append(resultSet.getString("SERNUM")).toString();
    		if(!currentChildKey.equals(previousChildKey)){
    			//increment();
    			previousChildKey = currentChildKey;
    			ULDInventoryDetailsVO childVO = new ULDInventoryDetailsVO();
    			childVO.setCompanyCode(resultSet.getString("CMPCOD"));
    	    	childVO.setAirportCode(resultSet.getString("ARPCOD"));
    	    	childVO.setUldType(resultSet.getString("ULDTYP"));
    	    	childVO.setRequiredULD(resultSet.getInt("REQQTY"));
    	    	childVO.setRemarks(resultSet.getString("RMK"));
    	    	childVO.setSerialNumber(resultSet.getString("SERNUM"));
    	    	childVO.setSequenceNumber(resultSet.getString("SEQNUM"));
    	    	childVO.setChildPrimaryKey(currentChildKey);
    	    	childVO.setDetailsFlag(ULD);
    	    	Date invdate = resultSet.getDate("DAT");
    	    	if(invdate != null ){
    	    		childVO.setInventoryDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,invdate));
    	    		childVO.setDisplayDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,invdate).toDisplayFormat());
    	    	}
    	    	coll.add(childVO);
    		}
    	}
    	//for setting the last PK rows
    	if(flag){
    		inventoryULDVO.setUldInventoryDetailsVOs(coll);
    		listVOs.add(inventoryULDVO);
    	}
    	
		
    	log.exiting("ULDInventoryMapper", "map");
    	return listVOs;
    }

}
