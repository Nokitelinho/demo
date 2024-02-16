	/*
	 * WorkflowDefaultsProxy.java Created on Jan 22 2014
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
package com.ibsplc.icargo.business.reco.defaults.proxy;

import java.util.Collection;
import com.ibsplc.icargo.business.rules.defaults.vo.RuleDefinitionVO;
import com.ibsplc.icargo.business.rules.defaults.vo.RuleFilterVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


	/**
	 * The Class RuleDefaultsProxy.
	 *
	 * @author A-5183
	 */
	@Module( "rules" )
	@SubModule( "defaults" )
	public class RuleDefaultsProxy extends ProductProxy {
		
		
		private Log log = LogFactory.getLogger("RuleDefaultsProxy"); 

		
	    /**
	     * 
	     * @param filterVO
	     * @return
	     * @throws SystemException
	     * @throws ProxyException
	     */
		public Collection<RuleDefinitionVO> findRuleDetails(RuleFilterVO filterVO) throws SystemException,ProxyException {
			log.log(Log.FINE, "findRuleDetails in RuleDefaultsProxy");
			return despatchRequest("findRuleDetails", filterVO);
	    }
	  }
