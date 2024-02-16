
package com.ibsplc.icargo.business.master.defaults.proxy;

import java.rmi.RemoteException;
import java.util.List;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.shared.area.AreaBI;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportFilterVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportVO;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

public class SharedAreaProxy extends SubSystemProxy {

	public SharedAreaProxy() {
	}
	public List<AirportVO> findAirports(AirportFilterVO filterVO) 
			throws SystemException {
		try {
			AreaBI areaBI = (AreaBI) getService("SHARED_AREA");
			return areaBI.findAirportMasters(filterVO);
		} catch (ServiceNotAccessibleException e) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,e);
		} catch (RemoteException e) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,e);
		}
	}
}
