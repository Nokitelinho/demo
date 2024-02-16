/*
 * ModifyCommand.java Created on Jan 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn66;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CN66DetailsForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-2408
 *
 */
public class ModifyCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("AirlineBilling CloseCommand");

	private static final String CLASS_NAME = "Modify";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn66";
	private static final String SCREEN_SUCCESS = "screenload_success";
	private static final String SCREENSTATUS_NEXT="next";
	private static final String SCREENSTATUS_ONNEXT="nonext";

	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering(CLASS_NAME, "execute");
    	CaptureCN66Session session=(CaptureCN66Session)getScreenSession(MODULE_NAME, SCREEN_ID);
    	CN66DetailsForm form=(CN66DetailsForm)invocationContext.screenModel;
    	session.removeCn66Details();
    	HashMap<String,Collection<AirlineCN66DetailsVO>> cn66details=null;
    	Page<AirlineCN66DetailsVO> cn66PageVos = new Page<AirlineCN66DetailsVO>(
				new ArrayList<AirlineCN66DetailsVO>(), 0, 0, 0, 0, 0,false);
    	HashMap<String,Collection<AirlineCN66DetailsVO>> modifiedcn66map=new HashMap<String,Collection<AirlineCN66DetailsVO>>();
    	ArrayList<AirlineCN66DetailsVO> cn66s=new ArrayList<AirlineCN66DetailsVO>();
    	ArrayList<AirlineCN66DetailsVO> keyValues=null;
    	ArrayList<AirlineCN66DetailsVO> modifiedcn66s=new ArrayList<AirlineCN66DetailsVO>();
    	ArrayList<String> keyvals=new ArrayList<String>();
    	String innerrow=form.getInnerRowSelected();
    	
    	
    	
    	String[] innerrows=new String[innerrow.length()];
    	
    	StringTokenizer innertok = new StringTokenizer(innerrow,",");
    	
    	
    	int num=0;
    	while(innertok.hasMoreTokens()){
    		innerrows[num]=innertok.nextToken();
    		num++;
    	}
    	
    	if(session.getAirlineCN66DetailsVOs()!=null && session.getAirlineCN66DetailsVOs().size()>0){
    		cn66PageVos=session.getAirlineCN66DetailsVOs();
    		if(cn66PageVos!=null && cn66PageVos.size()>0){
    			
    		/*ArrayList<Collection<AirlineCN66DetailsVO>> vos=new ArrayList<Collection<AirlineCN66DetailsVO>>(cn66details.values());
    		int valsize=cn66details.values().size();
    		for(int i=0;i<valsize;i++){
    			cn66s.addAll((ArrayList<AirlineCN66DetailsVO>)vos.get(i));
    			
    		}*/
    		}
    	}
    
    	for(int j=innerrows.length-1;j>-1;j--){
    		if(innerrows[j]!=null && innerrows[j].trim().length()>0){    		
    			if(cn66PageVos != null && cn66PageVos.size() > 0) {
    				modifiedcn66s.add(cn66PageVos.get(Integer.parseInt(innerrows[j])));
    				//cn66PageVos.remove(cn66PageVos.get(Integer.parseInt(innerrows[j])));
    			}
    		}
    	}
    	log.log(Log.INFO, " removed collection.....", cn66PageVos);
		log.log(Log.INFO, " modified collection.....", modifiedcn66s);
		/*ArrayList<AirlineCN66DetailsVO> revmodifiedcn66s=new ArrayList<AirlineCN66DetailsVO>();
    	for(int i=modifiedcn66s.size()-1;i>-1;i--){
    		revmodifiedcn66s.add(modifiedcn66s.get(i));
    	}*/
    	for(AirlineCN66DetailsVO vo:modifiedcn66s){
    		if(vo.getOperationFlag()==null) {
				vo.setOperationFlag(OPERATION_FLAG_UPDATE);
			}
    	}
    	session.setCn66Details(modifiedcn66s);
    	log.log(Log.INFO, "not modified collection", cn66s);
		session.setPreviousCn66Details(cn66s);
    	//String firstKey="";
    	if(modifiedcn66s!=null && modifiedcn66s.size()>0){
    		
    		
    		form.setCarriageFrom(modifiedcn66s.get(0).getCarriageFrom());
    		form.setCarriageTo(modifiedcn66s.get(0).getCarriageTo());
    	for(AirlineCN66DetailsVO vo:modifiedcn66s){
    		
			String key=new StringBuilder().append(vo.getCarriageFrom())
								.append("-").append(vo.getCarriageTo()).toString();
			
			//keyValues.add(key);
			if(!(modifiedcn66map.containsKey(key))){
				//System.out.println("inside loop"+key);
				keyvals.add(key);
				keyValues=new ArrayList<AirlineCN66DetailsVO>();
				keyValues.add(vo);
				modifiedcn66map.put(key,keyValues);
			}
			else{
				modifiedcn66map.get(key).add(vo);
			}
			
		}
    	
    	}
    	//int count=0;
    	
    	//System.out.println("firstkey"+keyvals.get(0));
    	session.setCn66DetailsModifiedMap(modifiedcn66map);
    	session.setKeyValues(keyvals);
    	if(modifiedcn66map.containsKey(keyvals.get(0))){
    		if(modifiedcn66map.get(keyvals.get(0))!=null) {
				session.setCn66Details((ArrayList<AirlineCN66DetailsVO>)modifiedcn66map.get(keyvals.get(0)));
			}
    	
    	if(modifiedcn66map.size()==1){
    		form.setScreenStatus(SCREENSTATUS_ONNEXT);
    	}
    	else{
    		form.setScreenStatus(SCREENSTATUS_NEXT);
    	}
    	
    	}
    	//System.out.println("screenstatus"+form.getScreenStatus());
    	
    	//System.out.println("nobs"+innerrows);
    	//System.out.println("map to be modified"+modifiedcn66map);
    	
    	//System.out.println("previous collection"+session.getPreviousCn66Details());
    	
    	
   		invocationContext.target = SCREEN_SUCCESS;	// sets target
		log.exiting(CLASS_NAME,"execute");
    	
    	
    	
    	
    }
}
