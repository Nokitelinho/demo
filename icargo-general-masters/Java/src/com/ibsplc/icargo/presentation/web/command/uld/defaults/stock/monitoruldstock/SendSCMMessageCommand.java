/*
 * SendSCMMessageCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.monitoruldstock;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockListVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MonitorULDStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MonitorULDStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2046
 * 
 */
public class SendSCMMessageCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MonitorULD Stock");

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.monitoruldstock";
	
	private static final String SCMSEND_SUCCESS="uld.defaults.scmsendsuccessfully";

	private static final String SEND_SUCCESS = "send_success";

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("Monitor Stock", "Send SCM Message Command");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		String airportCode=logonAttributes.getAirportCode();
		MonitorULDStockSession monitorULDStockSession = (MonitorULDStockSession) getScreenSession(
				MODULE, SCREENID);
		MonitorULDStockForm monitorULDStockForm = (MonitorULDStockForm) invocationContext.screenModel;

		Page<ULDStockListVO> stockListVOs = monitorULDStockSession
				.getULDStockListVO();
		ULDStockConfigFilterVO uldStockConfigFilterVO =  monitorULDStockSession.getULDStockConfigFilterVO();
		String[] checked = monitorULDStockForm.getSelectedRows();
		log.log(Log.FINE, "Selected rows size------------>", checked.length);
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		if (stockListVOs != null && stockListVOs.size() > 0) {
			ULDStockListVO stockListVO = stockListVOs.get(Integer
					.parseInt(checked[0]));
			log.log(Log.FINE, "Selected Stock list VO------------->",
					stockListVO);
			//String airportCode = stockListVO.getStationCode();
			String uldIdentifier = stockListVO.getUldTypeCode();
			int airlineIdentifier = stockListVO.getAirlineIdentifier();
			log.log(Log.FINE, "Airline Identifier---------------->",
					stockListVO.getAirlineIdentifier());
			LocalDate stockDate = new LocalDate(airportCode, Location.ARP, true); 
			stockListVO.setStockDate(stockDate);
			//Added as part of ICRD-334152
			if(uldStockConfigFilterVO!=null && uldStockConfigFilterVO.getOwnerAirlineIdentifier()>0) {
				stockListVO.setOwnerAirlineIdentifier(uldStockConfigFilterVO.getOwnerAirlineIdentifier());
			}
			if ((stockListVO.getUldTypeCode()==null || stockListVO.getUldTypeCode().trim().length()==0)
					&& uldStockConfigFilterVO.getUldTypeCode()!=null && uldStockConfigFilterVO.getUldTypeCode().trim().length()>0) {
				stockListVO.setUldTypeCode(uldStockConfigFilterVO.getUldTypeCode());
			}
			if ((stockListVO.getUldNature()==null || stockListVO.getUldNature().trim().length()==0)
					&& uldStockConfigFilterVO.getUldNature()!=null && uldStockConfigFilterVO.getUldNature().trim().length()>0) {
				stockListVO.setUldNature(uldStockConfigFilterVO.getUldNature());
			}
			log.log(Log.FINE, "The stockDate is ", stockListVO.getStockDate());
			ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
			
			try {
				//delegate.generateSCMFromMonitorULD(companyCode, airportCode,
						//airlineIdentifier, uldIdentifier, stockDate);
				delegate.generateSCMFromMonitorULD(companyCode , stockListVO);
				invocationContext.addError(new ErrorVO(SCMSEND_SUCCESS));
			} catch (BusinessDelegateException ex) {
				ex.getMessage();
				error = handleDelegateException(ex);
			}
		}

		invocationContext.target = SEND_SUCCESS;
		invocationContext.addAllError(error);
	}
}
