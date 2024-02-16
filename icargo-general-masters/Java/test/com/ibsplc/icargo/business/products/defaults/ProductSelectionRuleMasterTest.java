/**
 *	Java file	: 	com.ibsplc.icargo.business.products.defaults.ProductSelectionRuleMasterTest.java
 *
 *	Created by	:	A-8146
 *	Created on	:	Jun 30, 2022
 *
 *  Copyright 2022 Copyright  IBS Software  (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright  IBS Software  (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.Matchers.any;

import org.hamcrest.core.IsNull;
import org.junit.Test;

import com.ibsplc.icargo.business.products.defaults.vo.ProductSelectionRuleMasterVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.persistence.dao.products.defaults.ProductDefaultsPersistenceConstants;
import com.ibsplc.icargo.persistence.dao.products.defaults.ProductDefaultsSqlDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

/**
 *	Java file	: 	com.ibsplc.icargo.business.products.defaults.ProductSelectionRuleMasterTest.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8146	:	Jun 30, 2022	:	Draft
 */
/**
 *	Java file	: 	com.ibsplc.icargo.business.products.defaults.ProductSelectionRuleMasterTest.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	Prashant Behera	:	Jul 1, 2022	:	Draft
 */
public class ProductSelectionRuleMasterTest extends AbstractFeatureTest {

	/** The product selection rule master. */
	private ProductSelectionRuleMaster productSelectionRuleMaster; 
	
	/** The product defaults sql DAO. */
	private ProductDefaultsSqlDAO productDefaultsSqlDAO; 
	
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.feature.AbstractFeatureTest#setup()
	 *	Added by 			: 	Prashant Behera on Jul 1, 2022
	 * 	Used for 	:
	 *	Parameters	:	@throws Exception 
	 */
	@Override
	public void setup() throws Exception {
		productSelectionRuleMaster = spy(new ProductSelectionRuleMaster());
		productDefaultsSqlDAO = mock(ProductDefaultsSqlDAO.class);
		EntityManagerMock.mockEntityManager();
		doReturn(productDefaultsSqlDAO).when(PersistenceController.getEntityManager()).getQueryDAO(ProductDefaultsPersistenceConstants.MODULE_NAME);
	}
	
	/**
	 * 	Method		:	ProductSelectionRuleMasterTest.productSelectionRuleMaster
	 *	Added by 	:	Prashant Behera on Jul 1, 2022
	 * 	Used for 	:
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	@Test
	public void productSelectionRuleMaster() throws SystemException{
		ProductSelectionRuleMasterVO productSelectionRuleMasterVO = new ProductSelectionRuleMasterVO();
		productSelectionRuleMaster = new ProductSelectionRuleMaster(productSelectionRuleMasterVO);
		populateAttributes(productSelectionRuleMasterVO);
		assertThat(productSelectionRuleMasterVO,  IsNull.notNullValue());
	}

	/**
	 * 	Method		:	ProductSelectionRuleMasterTest.populateAttributes
	 *	Added by 	:	Prashant Behera on Jul 1, 2022
	 * 	Used for 	:
	 *	Parameters	:	@param productSelectionRuleMasterVO 
	 *	Return type	: 	void
	 */
	private void populateAttributes(ProductSelectionRuleMasterVO productSelectionRuleMasterVO) {
		productSelectionRuleMasterVO.setAgentCode("A001");
		productSelectionRuleMasterVO.setCompanyCode("IBS");
	}
	
	/**
	 * 	Method		:	ProductSelectionRuleMasterTest.clearProductSelectionRuleMaster
	 *	Added by 	:	Prashant Behera on Jul 1, 2022
	 * 	Used for 	:
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	void
	 */
	@Test
	public void  clearProductSelectionRuleMaster() throws SystemException, PersistenceException{
		doReturn(productDefaultsSqlDAO).when(PersistenceController.getEntityManager()).getQueryDAO(ProductDefaultsPersistenceConstants.MODULE_NAME);
		productSelectionRuleMaster.clearProductSelectionRuleMaster();
	}
	
	/**
	 * 	Method		:	ProductSelectionRuleMasterTest.listProductSelectionRuleMaster
	 *	Added by 	:	Prashant Behera on Jul 1, 2022
	 * 	Used for 	:
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	void
	 */
	@SuppressWarnings("static-access")
	@Test
	public void  listProductSelectionRuleMaster() throws SystemException, PersistenceException{
		doReturn(productDefaultsSqlDAO).when(PersistenceController.getEntityManager()).getQueryDAO(ProductDefaultsPersistenceConstants.MODULE_NAME);
		productSelectionRuleMaster.listProductSelectionRuleMaster("IBS");
	}
	
	/**
	 * 	Method		:	ProductSelectionRuleMasterTest.findProductsForBookingFromProductSelectionRule
	 *	Added by 	:	Prashant Behera on Jul 1, 2022
	 * 	Used for 	:
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	void
	 */
	@SuppressWarnings("static-access")
	@Test
	public void findProductsForBookingFromProductSelectionRule() throws SystemException, PersistenceException{
		doReturn(productDefaultsSqlDAO).when(PersistenceController.getEntityManager()).getQueryDAO(ProductDefaultsPersistenceConstants.MODULE_NAME);
		productSelectionRuleMaster.findProductsForBookingFromProductSelectionRule("IBS",new HashMap<>(),new ArrayList<>());
	}
	
	/**
	 * 	Method		:	ProductSelectionRuleMasterTest.throwCreateExceptionExceptionWhilePeristing
	 *	Added by 	:	Prashant Behera on Jul 1, 2022
	 * 	Used for 	:
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws CreateException 
	 *	Return type	: 	void
	 */
	@Test (expected = SystemException.class)
	public void throwCreateExceptionExceptionWhilePeristing() throws SystemException, CreateException{
		doThrow(new CreateException()).when(PersistenceController.getEntityManager()).persist(any(ProductSelectionRuleMaster.class));
		ProductSelectionRuleMasterVO productSelectionRuleMasterVO = new ProductSelectionRuleMasterVO();
		productSelectionRuleMaster = new ProductSelectionRuleMaster(productSelectionRuleMasterVO);
		populateAttributes(productSelectionRuleMasterVO);
	}
	
	/**
	 * 	Method		:	ProductSelectionRuleMasterTest.throwPersistenceExceptionExceptionWhileConstructDAO
	 *	Added by 	:	Prashant Behera on Jul 1, 2022
	 * 	Used for 	:
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	void
	 */
	@SuppressWarnings("static-access")
	@Test (expected = SystemException.class)
	public void throwPersistenceExceptionExceptionWhileConstructDAO() throws SystemException, PersistenceException{
		doThrow(new PersistenceException()).when(PersistenceController.getEntityManager()).getQueryDAO(ProductDefaultsPersistenceConstants.MODULE_NAME);
		productSelectionRuleMaster.findProductsForBookingFromProductSelectionRule("IBS",new HashMap<>(),new ArrayList<>());
	}
	
	@Test 
	public void checkGetterMethodsForEntity() throws SystemException {
		ProductSelectionRuleMasterVO productSelectionRuleMasterVO = new ProductSelectionRuleMasterVO();
		productSelectionRuleMaster = new ProductSelectionRuleMaster(productSelectionRuleMasterVO);
		populateAttributes(productSelectionRuleMasterVO);
		productSelectionRuleMaster.getAgentCode();
		productSelectionRuleMaster.getAgentGroupCode();
		productSelectionRuleMaster.getCommodityCode();
		productSelectionRuleMaster.getCompanyCode();
		productSelectionRuleMaster.getDestinationCountryCode();
		productSelectionRuleMaster.getInternationalDomesticFlag();
		productSelectionRuleMaster.getOriginCountryCode();
		productSelectionRuleMaster.getProductCode();
		productSelectionRuleMaster.getSccCode();
		productSelectionRuleMaster.getSccGroupCode();
		productSelectionRuleMaster.getSerialNumber();
		productSelectionRuleMaster.getSourceCode();
	}
}
