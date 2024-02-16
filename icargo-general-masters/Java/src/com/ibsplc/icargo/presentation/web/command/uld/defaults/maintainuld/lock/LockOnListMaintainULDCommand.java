/*
 * LockOnListMaintainULDCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.maintainuld.lock;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.business.uld.defaults.vo.lock.ULDLockVO;
import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.lock.LockCommand;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MaintainULDForm;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.shared.lock.LockConstants;

/**
 * 
 * @author A-2547
 * 
 */
public class LockOnListMaintainULDCommand extends LockCommand {

	private Log log = LogFactory.getLogger("LIST MAINTAIN ULD LOCKING");

	/*
	 * The Module Name 
	 */
	private static final String MODULE_NAME = "uld.defaults";

	/*
	 * The ScreenID 
	 */
	private static final String SCREEN_ID = "uld.defaults.maintainuld";

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.web.command.lock.LockCommand#getLockDetails(com.ibsplc.icargo.framework.model.ScreenModel)
	 */
	@Override
	protected LockVO[] getLockDetails(ScreenModel screenModel) {
		log.entering("ULDListLockCommand", "getLockDetails");
		MaintainULDForm maintainULDForm = (MaintainULDForm) screenModel;
		MaintainULDSession maintainULDSession = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		LockVO[] lockVOs = getLock(maintainULDSession, maintainULDForm);
		 
		
		log.exiting("ULDListLockCommand", "getLockDetails");
		return lockVOs;
	}

	/**
	 * 
	 * @param maintainULDSession
	 * @return
	 */
	private LockVO[] getLock(MaintainULDSession maintainULDSession,
			MaintainULDForm maintainULDForm) {
		log.entering("ULDListLockCommand", "getLock");
		
		LockVO[] lockVOs = null; 
			
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		ULDVO uldVO =  maintainULDSession.getULDVO();
		
		log.log(Log.FINE, "Status Flag--->>", maintainULDForm.getStatusFlag());
		log.log(Log.FINE, "uldVO--->>", uldVO);
		if(uldVO != null){
			
			
			if("taken_request".equals(maintainULDForm.getStatusFlag())){
				log.log(Log.FINE, "!!!!!NEW ULD ");
				lockVOs = new LockVO[1];
				ULDLockVO uldLockVO = new ULDLockVO();
				
				
				uldLockVO.setAction(LockConstants.ACTION_LISTULD);
				uldLockVO.setClientType(ClientType.WEB);
				uldLockVO.setCompanyCode(logonAttributes.getCompanyCode());
				uldLockVO.setDescription("LISTULD_LOCK");
				uldLockVO.setRemarks("LISTULD LOCKING");
				uldLockVO.setScreenId(SCREEN_ID);
				uldLockVO.setStationCode(logonAttributes.getStationCode());
				uldLockVO.setUldNumber(uldVO.getUldNumber());
				log.log(Log.FINE, "!!!!!NEW ULD ", uldVO.getUldNumber());
				lockVOs[0] = uldLockVO;
				
			}
			
			//else if ("createNewUld".equals(maintainULDForm.getStatusFlag())){
			
			else if (ULDVO.OPERATION_FLAG_INSERT.equals(uldVO.getOperationalFlag())){
							
				log.log(Log.FINE, "!!!!!locking not needed");
				//no need of locking condition
			}else{
				
				log.log(Log.FINE, "!!!!!old uld");
				lockVOs = new LockVO[1];
				ULDLockVO uldLockVO = new ULDLockVO();
				
				
				uldLockVO.setAction(LockConstants.ACTION_LISTULD);
				uldLockVO.setClientType(ClientType.WEB);
				uldLockVO.setCompanyCode(logonAttributes.getCompanyCode());
				uldLockVO.setDescription("LISTULD_LOCK");
				uldLockVO.setRemarks("LISTULD LOCKING");
				uldLockVO.setScreenId(SCREEN_ID);
				uldLockVO.setStationCode(logonAttributes.getStationCode());
				uldLockVO.setUldNumber(uldVO.getUldNumber());
				log.log(Log.FINE, "!!!!!ULD NO", uldVO.getUldNumber());
				lockVOs[0] = uldLockVO;	
				
			}
			/*log.log(Log.FINE, "!!!!!old uld");
			lockVOs = new LockVO[1];
			ULDLockVO uldLockVO = new ULDLockVO();
			
			
			uldLockVO.setAction(LockConstants.ACTION_LISTULD);
			uldLockVO.setClientType(ClientType.WEB);
			uldLockVO.setCompanyCode(logonAttributes.getCompanyCode());
			uldLockVO.setDescription("LISTULD_LOCK");
			uldLockVO.setRemarks("LISTULD LOCKING");
			uldLockVO.setScreenId(SCREEN_ID);
			uldLockVO.setStationCode(logonAttributes.getStationCode());
			uldLockVO.setUldNumber(uldVO.getUldNumber());
			log.log(Log.FINE, "!!!!!NEW ULD "+uldVO.getUldNumber());
			lockVOs[0] = uldLockVO;*/
		}
		log.log(Log.FINE, "!!!!!lockVOs", lockVOs);
		return lockVOs;
		
	}

}
