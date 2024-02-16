/**
 *	Java file	: 	com.ibsplc.icargo.business.operations.flthandling.feature.savebreakdown.enrichers.AttachStorageUnitDetailsEnricher.java
 *
 *	Created by	:	A-8330
 *	Created on	:	12-Feb-2020
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.neoicargo.mail.component.feature.searchcontainerfilterquery.enrichers;


import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.component.feature.autoattachawbdetails.AutoAttachAWBDetailsFeatureConstants;
import com.ibsplc.neoicargo.mail.component.proxy.ProductDefaultsProxy;
import com.ibsplc.neoicargo.mail.component.proxy.StockcontrolDefaultsProxy;
import com.ibsplc.neoicargo.mail.dao.SearchContainerFilterQueryBuilder;
import com.ibsplc.neoicargo.mail.exception.StockcontrolDefaultsProxyException;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.neoicargo.mail.vo.MailManifestDetailsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mail.vo.OperationalFlightVO;
import com.ibsplc.neoicargo.mail.vo.SearchContainerFilterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;

import static com.ibsplc.neoicargo.mail.component.feature.autoattachawbdetails.AutoAttachAWBDetailsFeatureConstants.DOCUMENT_FILETR_ENRICHER;

/**
 * Java file :
 * com.ibsplc.icargo.business.operations.flthandling.feature.savebreakdown.enrichers.AttachStorageUnitDetailsEnricher.java
 * Version : Name : Date : Updation
 * --------------------------------------------------- 0.1 : A-8330 :
 * 12-Feb-2020 : Draft
 */
@Component("searchContainerFilterQueryEnricher")
public class SearchContainerFilterQueryEnricher extends Enricher<SearchContainerFilterVO> {



	@Override
	public void enrich(SearchContainerFilterVO searchContainerFilterVO) throws BusinessException {

		SearchContainerFilterQueryBuilder searchContainerFilterQuery = new SearchContainerFilterQueryBuilder();
		searchContainerFilterVO.setPgqry(( searchContainerFilterQuery.withBaseQuery(searchContainerFilterVO.getBaseQuery()).withIsOracleDataSource(searchContainerFilterVO.isOracleDataSource())
				.withSearchContainerFilterVO(searchContainerFilterVO).build()));
	}
}