/*
 * ScreenloadCN51DetailsCommand.java Created on DEC 04, 2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn51;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN51Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN51Form;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3227 RENO K ABRAHAM
 */
public class ScreenloadCN51DetailsCommand  extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILTRACKING MRA AIRLINEBILLING DEFAULTS");
	private static final String CLASS_NAME = "ScreenloadCN51Details";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn51";
	private static final String SCREENLOAD_SUCCESS="screenload_success";
	private static final String SCREENLOAD_FAILURE="screenload_failure";
	
	/**
	 * Execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		CaptureCN51Session captureCN51Session = 
			(CaptureCN51Session) getScreenSession(MODULE_NAME, SCREEN_ID);
		CaptureCN51Form captureCN51Form=(CaptureCN51Form)invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		//Added for Unit Components weight,volume
		UnitRoundingVO unitRoundingVO = new UnitRoundingVO();
		captureCN51Session.setWeightRoundingVO(unitRoundingVO);
		captureCN51Session.setVolumeRoundingVO(unitRoundingVO);		
		setUnitComponent(logonAttributes.getStationCode(),captureCN51Session);	
		


		captureCN51Form.setCurrentPageNum("1");
		int currentIndex = Integer.parseInt(captureCN51Form.getCurrentPageNum())-1;	
		int displayPageNum = Integer.parseInt(captureCN51Form.getDisplayPage());
		String selectedRow = captureCN51Form.getSelectedRow();
		String[] primaryKey = null;
		if(selectedRow != null && selectedRow.trim().length() > 0) {
			primaryKey = selectedRow.split(",");
		}
		Page<AirlineCN51DetailsVO> mainCN51VOs = null ;
		ArrayList<AirlineCN51DetailsVO> selectedCN51VOs = new ArrayList<AirlineCN51DetailsVO>();
		AirlineCN51DetailsVO currentCN51VO = new AirlineCN51DetailsVO();
		
		AirlineCN51SummaryVO airlineCN51SummaryVO = new AirlineCN51SummaryVO();
		airlineCN51SummaryVO = captureCN51Session.getCn51Details();
		if(airlineCN51SummaryVO != null) {
			if(airlineCN51SummaryVO.getCn51DetailsPageVOs()!= null && 
					airlineCN51SummaryVO.getCn51DetailsPageVOs().size() > 0) {
				mainCN51VOs = airlineCN51SummaryVO.getCn51DetailsPageVOs();
			}else {
				mainCN51VOs = new Page<AirlineCN51DetailsVO>(
						new ArrayList<AirlineCN51DetailsVO>(), 0, 0, 0, 0, 0,false);
			}
		}
		int seqIdentifier=0;
		if(mainCN51VOs != null && mainCN51VOs.size() > 0){
			for(AirlineCN51DetailsVO mainCN51VO : mainCN51VOs) {
				mainCN51VO.setSeqIdentifier(seqIdentifier++);
			}
			if(primaryKey != null && primaryKey.length > 0) {
				for(int key=0;key<primaryKey.length;key++) {
					int position = Integer.parseInt(primaryKey[key]);
					AirlineCN51DetailsVO selectedCN51VO = 
						mainCN51VOs.get(position);
					if(selectedCN51VO != null) {
						selectedCN51VOs.add(selectedCN51VO);
					}					
				}
				if(selectedCN51VOs != null && selectedCN51VOs.size() > 0) {
					currentCN51VO = ((ArrayList<AirlineCN51DetailsVO>)selectedCN51VOs).get(currentIndex);
				}
			}
		}

		captureCN51Form.setTotalRecords(String.valueOf(selectedCN51VOs.size()));
		log.log(Log.FINE, "currentCN51VO-", currentCN51VO);
		log.log(Log.FINE, "selectedCN51VOs.size()-", selectedCN51VOs.size());
		log.log(Log.FINE, "mainCN51VOs.size()-", mainCN51VOs.size());
		captureCN51Session.setCurrentCn51Detail(currentCN51VO);
		captureCN51Session.setSelectedCn51DetailsVOs(selectedCN51VOs);
		
		captureCN51Form.setPopUpCloseFlag("N");
		invocationContext.target=SCREENLOAD_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
	}
	 private void setUnitComponent(String stationCode,
	   		  CaptureCN51Session captureCN51Session){
		UnitRoundingVO unitRoundingVO = null;
		try{
			log.log(Log.FINE, "station code is ----------->>", stationCode);
			unitRoundingVO=UnitFormatter.getUnitRoundingForUnitCode(UnitConstants.WEIGHT, UnitConstants.WEIGHT_UNIT_DEFAULT);			
			//unitRoundingVO=UnitFormatter.getSystemDefaultUnitVo(stationCode);
			log.log(Log.FINE, "unit vo for wt--in session---", unitRoundingVO);
			captureCN51Session.setWeightRoundingVO(unitRoundingVO);
			unitRoundingVO = UnitFormatter.getStationDefaultUnit(
					stationCode, UnitConstants.VOLUME);
			captureCN51Session.setVolumeRoundingVO(unitRoundingVO);
			log.log(Log.FINE, "UNIT IS",captureCN51Session.getWeightRoundingVO().getUnitCode());
			
		   }catch(UnitException unitException) {
	//printStackTrrace()();
		   }
		
	}
}
