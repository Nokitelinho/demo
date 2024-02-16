/*
 * CopyRateCardCommand.java Created on Feb 13, 2013
 *
 * Copyright 2013 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.*/

package com.ibsplc.icargo.business.mail.mra.proxy;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.mail.mra.SharedProxyException;
import com.ibsplc.icargo.business.shared.citypair.CityPairBI;
import com.ibsplc.icargo.business.shared.citypair.CityPairBusinessException;
import com.ibsplc.icargo.business.shared.citypair.vo.CityPairVO;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import java.rmi.RemoteException;
import java.util.Collection;
/**
 * @author A-5166
 *
 */
public class SharedCitypairProxy extends SubSystemProxy
{
	 private Log log;
    public SharedCitypairProxy()
    {
    	 log = LogFactory.getLogger("SharedCitypairProxy ");
    }

    public Collection<CityPairVO> findCityPair(String companyCode, Collection<String> cityPair)
        throws SystemException, SharedProxyException, CityPairBusinessException
    {	
    	log.entering("SharedCitypairProxy", "findCityPair");
        try
        {
            CityPairBI citypairBI = (CityPairBI)getService("SHARED_CITYPAIR");
            return citypairBI.findCityPair(companyCode, cityPair);
        }
        catch(ServiceNotAccessibleException e)
        {
            throw new SystemException(e.getMessage(), e);
        }
        catch(RemoteException e)
        {
            throw new SystemException(e.getMessage(), e);
        }
    }

  
}

