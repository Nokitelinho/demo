package com.ibsplc.icargo.services.master.defaults;

import java.rmi.RemoteException;
import java.util.List;

import com.ibsplc.icargo.business.master.defaults.MasterController;
import com.ibsplc.icargo.business.master.defaults.MasterDefaultsBI;

import com.ibsplc.xibase.server.framework.ejb.AbstractFacadeEJB;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.suggest.vo.SuggestRequestVO;
import com.ibsplc.xibase.util.suggest.vo.SuggestResponseVO;

/**
 * 
 * @author a-4801
 * This class expose the methods in master defaults subsystem
 * @ejb.bean description="MasterDefaultsServices"
 *           display-name="MasterDefaultsServices"
 *           jndi-name="com.ibsplc.icargo.services.master.defaults.MasterDefaultsServicesHome"
 *           name="MasterDefaultsServices"
 *           type="Stateless"
 *           view-type="remote"
 *           remote-business-interface="com.ibsplc.icargo.business.master.defaults.MasterDefaultsBI"
 *
 * @ejb.transaction type="Supports"
 *
 */
public class MasterDefaultsServicesEJB extends AbstractFacadeEJB implements MasterDefaultsBI{

	@Override
	public List<String> findCodes(String code) throws RemoteException,
			SystemException {
		return new MasterController().findCodes(code);
	}
	
	@Override
	public List<SuggestResponseVO> findCodes(SuggestRequestVO code) throws RemoteException,
			SystemException {
		return new MasterController().findCodes(code);
	}

}
