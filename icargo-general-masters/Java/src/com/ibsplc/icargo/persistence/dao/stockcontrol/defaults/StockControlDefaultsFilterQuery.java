/*
 * StockControlDefaultsFilterQuery.java Created on Oct 16, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.stockcontrol.defaults;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2394
 */

public class StockControlDefaultsFilterQuery extends NativeQuery {

	private Log log = LogFactory.getLogger("STOCKCONTROL");
		private StockAgentVO stockAgentVo;
		private String baseQuery;

		public StockControlDefaultsFilterQuery(
				StockAgentVO stockAgentVo,String baseQuery)
		       throws SystemException {
			super();
			this.baseQuery = baseQuery;
			this.stockAgentVo = stockAgentVo;

		}

		@Override
		public String getNativeQuery() {
			log.entering("StockControlDefaultsFilterQuery", "getNativeQuery");
			int index=0;
			StringBuilder sbuilder=new StringBuilder().append(baseQuery);
			this.setParameter(++index,stockAgentVo.getCompanyCode());

			String agentCode=stockAgentVo.getAgentCode();
			String stockHolderCode=stockAgentVo.getStockHolderCode();

			if(agentCode!=null && agentCode.trim().length()>0){

				sbuilder.append(" AND  STK.AGTCOD=? ");
				this.setParameter(++index,stockAgentVo.getAgentCode().trim());

			}
			if(stockHolderCode!=null && stockHolderCode.trim().length()>0){
				
				sbuilder.append(" AND STK.STKHLDCOD=? ");
				this.setParameter(++index,stockAgentVo.getStockHolderCode().trim());
			}
			log.log(Log.INFO, "Rsfjkvgksf -->", sbuilder.toString());
		return sbuilder.toString();
		}

	}



