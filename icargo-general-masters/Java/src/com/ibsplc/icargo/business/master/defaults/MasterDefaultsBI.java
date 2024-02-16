package com.ibsplc.icargo.business.master.defaults;

import java.rmi.RemoteException;
import java.util.List;


import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interfaces.BusinessInterface;
import com.ibsplc.xibase.util.suggest.vo.SuggestRequestVO;
import com.ibsplc.xibase.util.suggest.vo.SuggestResponseVO;

public interface MasterDefaultsBI extends BusinessInterface{
	
	List<String> findCodes(String code)throws RemoteException, SystemException;
	
	List<SuggestResponseVO> findCodes(SuggestRequestVO code)throws RemoteException, SystemException;
}
