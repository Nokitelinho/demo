/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.webservices.mapper.USPSResditReceiptMapper.java
 *
 *	Created by	:	A-7540
 *	Created on	:	08-May-2019
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.webservices.mapper;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date; 
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ResditReceiptVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.services.jaxws.proxy.mapper.TypeMapper;
import com.ibsplc.icargo.services.mail.mra.webservices.rest.external.aa.USPSResditReceiptResponse;
import com.ibsplc.icargo.services.mail.mra.webservices.rest.external.aa.USPSResditScanModeResponse;

import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-7540
 *
 */
public class USPSResditReceiptMapper implements TypeMapper{
	private static final String CLASSNAME = "USPSResditReceiptMapper";
	private final Log log = LogFactory.getLogger("USPSResditReceiptMapper");

   @Override
	public Object[] mapParameters(Object... param) throws SystemException {
		log.entering(CLASSNAME, "mapParameters");
		String mailbagId = (String) param[0];
		return new Object[] {mailbagId};
	}

	@Override
	public <T> T mapResult(T response) throws SystemException {
		USPSResditReceiptResponse resditResponse = (USPSResditReceiptResponse) response;
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();

		if(resditResponse != null){
			
			ResditReceiptVO resditReceiptVO = new ResditReceiptVO();
			resditReceiptVO.setCarrierCode(resditResponse.getCarrierCode());
			resditReceiptVO.setMailbagId(resditResponse.getReceptacleID());	
			Collection<ResditReceiptVO> resditReceiptVOs = new ArrayList<ResditReceiptVO>(); 
			
			if(resditResponse.getScanModeResponses() != null){
				for(USPSResditScanModeResponse modeResponse : resditResponse.getScanModeResponses()){					ResditReceiptVO resditReceiptvo = new ResditReceiptVO();

					resditReceiptvo.setExceptionCode(modeResponse.getExceptionCode());
					resditReceiptvo.setValidForPayIndicator(modeResponse.getValidForPayInd());
				    String[] cmpDate = modeResponse.getScanUtcDtm().split("T");
					String date = cmpDate[0];
					String[] splitDate = date.split("-");
					String time = cmpDate[1];
					String year = splitDate[0];
					String month = splitDate[1];		
					String day = splitDate[2];
					String[] splitTime = time.split(":");
					String hours = splitTime[0];
					String mins = splitTime[1];
					
					StringBuilder snrDate = new StringBuilder().append(day).append("-")
							.append(month).append("-").append(year).append(" ").append(hours)
                            .append(":").append(mins);
					String utcDate = snrDate.toString();
					Date scanDate = null;
					try {
						scanDate=new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(utcDate);
					} catch (ParseException e) {
                    	e.getMessage();
					}
					SimpleDateFormat formatting = new SimpleDateFormat("dd-MMM-yy HH:mm");
					String scanDateUtc = formatting.format(scanDate);
					resditReceiptvo.setScanTimeUTC(scanDateUtc);
					resditReceiptvo.setScannedModeCode(modeResponse.getScanModeCode());
					resditReceiptvo.setScannedport(modeResponse.getScanSiteID());
					resditReceiptVOs.add(resditReceiptvo);
				}
			}
			
			log.exiting(CLASSNAME, "mapResult");
			return (T) resditReceiptVOs;
		}
		else{
			log.exiting(CLASSNAME, "mapResult");
			return null;
		}
	}

}
