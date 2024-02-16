/*
 * UpdateInventoryDetailsCommand.java Created on May 27, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.inventory;


import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.uld.defaults.stock.vo.InventoryULDVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDInventoryDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.stock.ULDInventorySession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.InventoryULDForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author a-2883
 * 
 */

public class UpdateInventoryDetailsCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ULDMANAGEMENT");
	private static final String SCREEN_ID = 
			"uld.defaults.stock.inventoryuld";
	private static final String MODULE_NAME = "uld.defaults";
	private static final String UPDATE = "fromUpdate";
	private static final String FLAG_FLIGHT = "FLIGHT";
	private static final String FLAG_ULD = "ULD";
	
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering("UpdateInventoryDetailsCommand", "execute");
		InventoryULDForm form = 
			(InventoryULDForm)invocationContext.screenModel;
		ULDInventorySession session =
			getScreenSession(MODULE_NAME, SCREEN_ID);
		log.log(Log.FINE, " \n #@!@!@#!@0000");
		log.log(Log.FINE, " \n #@!@!@#!@", session.getListInventoryULDDetails().size());
		Collection<InventoryULDVO> coll = session.getListInventoryULDDetails();
		LinkedHashMap<String,InventoryULDVO> map = new LinkedHashMap<String, InventoryULDVO>();
		InventoryULDVO parentVO = new InventoryULDVO();
		ULDInventoryDetailsVO updatedchildVO = new ULDInventoryDetailsVO();
		List<ULDInventoryDetailsVO> newChildCol = new ArrayList<ULDInventoryDetailsVO>();
		log.log(Log.FINE, " \n #@!@!@#!@", coll.size());
		for(InventoryULDVO vo : coll){
			log.log(Log.FINE, " \n #@!@!@#!@11111");
			map.put(vo.getParentPrimaryKey(), vo);
		}
		for(InventoryULDVO vo : coll){
			log.log(Log.FINE, " \n #@!@!@#!22222");
			if(vo.getParentPrimaryKey().equals(form.getParentPrimaryKey())){
				log.log(Log.FINE, " \n #@!@!@#!33333");
				parentVO = vo;
			}
		}
		Collection<ULDInventoryDetailsVO> childCol = parentVO.getUldInventoryDetailsVOs();
		int pos=0;
		int ind =0;
		for(ULDInventoryDetailsVO child : childCol){
			log.log(Log.FINE, " \n #@!@!@#!444444", childCol.size());
			if(!child.getChildPrimaryKey().equals(form.getChildPrimaryKey())){
				log.log(Log.FINE, " \n #@!@!@#!55555");
				newChildCol.add(child);
			}else{
				log.log(Log.FINE, " \n #@!@!@#!66666");
				ind=pos;
				updatedchildVO=child;
				if(FLAG_ULD.equals(parentVO.getDetailsFlag())){
					log.log(Log.FINE, " \n #@!@!@#!@17777");
					updatedchildVO.setOpFlag(OPERATION_FLAG_UPDATE);
				}else{
					log.log(Log.FINE, " \n #@!@!@#!88888");
					updatedchildVO.setOpFlag(OPERATION_FLAG_INSERT);
				}
			}
			pos++;
		}
		log.log(Log.FINE, " \n #@!@!hhhh");
		updatedchildVO.setRequiredULD(form.getDetailRequiredULD());
		updatedchildVO.setRemarks(form.getDetailRemarks());
		updatedchildVO.setCompanyCode(form.getCompanyCode());
		log.log(Log.FINE, " \n #@!@!gggggg");
		LocalDate invdate=
			new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
		updatedchildVO.setInventoryDate(invdate.setDate(form.getDisplayDate()));
		log.log(Log.FINE, " \n #@!@!ffffff", newChildCol.size());
		log.log(Log.FINE, " \n #@!@jjjjjj", ind);
		newChildCol.add(ind,updatedchildVO);
		parentVO.setUldInventoryDetailsVOs(newChildCol);
		log.log(Log.FINE, " \n #@!@!ddddddd");
		if(FLAG_ULD.equals(parentVO.getDetailsFlag())){
			parentVO.setOpFlag(OPERATION_FLAG_UPDATE);
		}else{
			parentVO.setOpFlag(OPERATION_FLAG_INSERT);
		}
		log.log(Log.FINE, " \n #@!@eeeee");
		map.put(parentVO.getParentPrimaryKey(), parentVO);
		log.log(Log.FINE, " \n #@!@!@ccccc", map.size());
		List<InventoryULDVO> result = new ArrayList<InventoryULDVO>();
		Iterator itr = map.entrySet().iterator();
		while(itr.hasNext()){
			log.log(Log.FINE, " \n #@!@!@999999");
			Map.Entry entry = (Map.Entry)itr.next();
			result.add((InventoryULDVO)entry.getValue());
		}
		session.setListInventoryULDDetails((ArrayList<InventoryULDVO>)result);
		List<InventoryULDVO> disvo =new ArrayList<InventoryULDVO>();
		InventoryULDVO vovalue = new InventoryULDVO();
		log.log(Log.FINE, " \n tarunbbbb", form.getDisplayPage());
		vovalue = result.get(Integer.parseInt(form.getDisplayPage())-1);
		disvo.add(vovalue);
		session.setDisplayInventoryDetails((ArrayList<InventoryULDVO>)disvo);
		session.setStatusFlag(UPDATE);
		log.log(Log.FINE, " \n tarunsession", session.getInventoryPageFlag());
		form.setStatusFlag(UPDATE);
		log.exiting("UpdateInventoryDetailsCommand", "execute");
		invocationContext.target = "forward";
		
	}
}
