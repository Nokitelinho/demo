/**
 *	Java file	: 	com.ibsplc.icargo.business.product.defaults.ProductDefaultsControllerTest.java
 *
 *	Created on	:	Jan 25, 2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.product.defaults;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.ibsplc.icargo.business.products.defaults.BookingExistsException;
import com.ibsplc.icargo.business.products.defaults.DuplicateProductExistsException;
import com.ibsplc.icargo.business.products.defaults.InvalidProductException;
import com.ibsplc.icargo.business.products.defaults.Product;
import com.ibsplc.icargo.business.products.defaults.ProductDefaultsController;
import com.ibsplc.icargo.business.products.defaults.ProductPK;
import com.ibsplc.icargo.business.products.defaults.ProductSelectionRuleMaster;
import com.ibsplc.icargo.business.products.defaults.SubProduct;
import com.ibsplc.icargo.business.products.defaults.SubProductPK;
import com.ibsplc.icargo.business.products.defaults.proxy.SharedAgentProxy;
import com.ibsplc.icargo.business.products.defaults.proxy.SharedAreaProxy;
import com.ibsplc.icargo.business.products.defaults.proxy.SharedCommodityProxy;
import com.ibsplc.icargo.business.products.defaults.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.products.defaults.proxy.SharedSCCProxy;
import com.ibsplc.icargo.business.products.defaults.vo.ProductPriorityVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductSCCVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductSelectionRuleMasterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductTransportModeVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductsDefaultsAuditVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.products.defaults.ProductDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.products.defaults.ProductDefaultsPersistenceConstants;
import com.ibsplc.icargo.persistence.dao.products.defaults.ProductDefaultsSqlDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

/**
 *	Java file	: 	com.ibsplc.icargo.business.product.defaults.ProductDefaultsControllerTest.java
 *	This class is used for
 */
public class ProductDefaultsControllerTest extends AbstractFeatureTest{
	
	private ProductDefaultsController productDefaultsControllerspy;
	Product product;
	ProductVO productVo;
	Collection<ProductSCCVO> productScc;
	private ProductSelectionRuleMaster productSelectionRuleMaster; 
	private SharedSCCProxy sccProxy;
	private SharedCommodityProxy sharedCommodityProxy;
	private SharedAreaProxy sharedAreaProxy;
	private SharedAgentProxy sharedAgentProxy; 
	private SharedDefaultsProxy sharedDefaultsProxy;
	
	@Override
	public void setup() throws Exception {

		productDefaultsControllerspy = spy(ProductDefaultsController.class);
		product = spy(new Product());
		productSelectionRuleMaster = spy(new ProductSelectionRuleMaster());
		EntityManagerMock.mockEntityManager();
		productVo = new ProductVO();
		productScc = new ArrayList<>();
		sccProxy = mockProxy(SharedSCCProxy.class);
		sharedCommodityProxy = mockProxy(SharedCommodityProxy.class);
		sharedAreaProxy = mockProxy(SharedAreaProxy.class);
		sharedAgentProxy = mockProxy(SharedAgentProxy.class);
		sharedDefaultsProxy = mockProxy(SharedDefaultsProxy.class);
	}
	
	@Test()
	public void findSubProductOnProductUpdate() throws DuplicateProductExistsException, BookingExistsException, SystemException, PersistenceException, FinderException  {
		
		ProductSCCVO productSCCVO = new ProductSCCVO();
		Collection<ProductPriorityVO>  productPriorityVOs = new ArrayList<>();
		Collection<ProductTransportModeVO>  productTransportModeVOs = new ArrayList<>();
		ProductPriorityVO productPriorityVO = new ProductPriorityVO();
		ProductTransportModeVO productTransportModeVO = new ProductTransportModeVO();
	
		ProductPK productPK = new ProductPK();
		
		
		productTransportModeVO.setTransportMode("A2A");
		productTransportModeVOs.add(productTransportModeVO);
		productPriorityVO.setPriority("H");
		productPriorityVOs.add(productPriorityVO);
		productVo.setOperationFlag(ProductVO.OPERATION_FLAG_UPDATE);
		productVo.setLastUpdateDate((new LocalDate(LocalDate.NO_STATION, Location.NONE, false)));
		productSCCVO.setScc("ABA");
		productScc.add(productSCCVO);
		productVo.setProductScc(productScc);
		productVo.setPriority(productPriorityVOs);
		productVo.setTransportMode(productTransportModeVOs);
		ProductsDefaultsAuditVO productsDefaultsAuditVo = new ProductsDefaultsAuditVO(
		    	ProductVO.PRODUCTSDEFAULTS_AUDIT_PRODUCTNAME,ProductVO.PRODUCTSDEFAULTS_AUDIT_MODULENAME,
		    	ProductVO.PRODUCTSDEFAULTS_AUDIT_ENTITYNAME);
		
		
		setSubProduct();
		
		product.setStatus(ProductVO.NEWSTATUS);
		productPK.setCompanyCode("AV");
		productPK.setProductCode("ABA");
		product.setProductPk(productPK);
		
		ProductDefaultsDAO dao = mock(ProductDefaultsSqlDAO.class);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(ProductDefaultsPersistenceConstants.MODULE_NAME);
		doReturn(false).when(dao).checkDuplicateForModify(any(String.class), any(String.class), any(String.class), any(LocalDate.class), any(LocalDate.class));
		doReturn(product).when(PersistenceController.getEntityManager()).find(eq(Product.class), any(ProductPK.class));
		
		doReturn(productsDefaultsAuditVo).when(productDefaultsControllerspy).setAuditOperationalShipment(product, productsDefaultsAuditVo);
		
		productDefaultsControllerspy.saveProductDetails(productVo);
	}
	/**
	 * 	Method		:	ProductDefaultsControllerTest.setSubProduct
	 *	Added on 	:	Jan 25, 2021
	 * 	Used for 	:
	 *	Parameters	:	 
	 *	Return type	: 	void
	 */
	private void setSubProduct() {
		
		Set<SubProduct> subProducts = new HashSet<>();
		SubProduct subProduct = new SubProduct();
		SubProduct subProducttwo = new SubProduct();
		
		SubProductPK subProductPK = new SubProductPK();
		SubProductPK subProductPKtwo = new SubProductPK();
		
		subProductPK.setCompanyCode("AV");
		subProductPK.setProductCode("ABA");
		subProductPK.setSubProductCode("HA2AABA");
		subProductPK.setVersionNumber(1);
		subProduct.setSubProductPk(subProductPK);
		subProduct.setProductScc("ABA");
		
		subProductPKtwo.setCompanyCode("AV");
		subProductPKtwo.setProductCode("ACA");
		subProductPKtwo.setSubProductCode("HA2AACA");
		subProductPKtwo.setVersionNumber(1);
		subProducttwo.setSubProductPk(subProductPKtwo);
		
		subProduct.setRestrictionCommodity(new HashSet<>());
		
		subProduct.setRestrictionCustomerGroup(new HashSet<>());
		subProduct.setRestrictionPaymentTerms(new HashSet<>());
		subProduct.setRestrictionSegment(new HashSet<>());
		subProduct.setRestrictionStation(new HashSet<>());
		subProduct.setSubProductEvent(new HashSet<>());
		subProduct.setSubProductService(new HashSet<>());
		
		
		subProducts.add(subProduct);
		subProducts.add(subProducttwo);
		
		product.setSubProducts(subProducts);
		
	}
	
	/**
	 * 	Method		:	ProductDefaultsControllerTest.findProductsForBookingFromProductSelectionRule
	 *	Added by 	:	Prashant Behera on Jul 1, 2022
	 * 	Used for 	:
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	void
	 */
	@Test
	public void findProductsForBookingFromProductSelectionRule() throws SystemException, PersistenceException{
		ProductDefaultsDAO dao = mock(ProductDefaultsSqlDAO.class);
		doReturn(new ArrayList<>()).when(sharedDefaultsProxy).findGeneralParameterValues(any(String.class), any(String.class));
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(ProductDefaultsPersistenceConstants.MODULE_NAME);
		productDefaultsControllerspy.findProductsForBookingFromProductSelectionRule(any(String.class),any(Map.class));
	}
	
	/**
	 * 	Method		:	ProductDefaultsControllerTest.listProductSelectionRuleMaster
	 *	Added by 	:	Prashant Behera on Jul 1, 2022
	 * 	Used for 	:
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	void
	 */
	@Test
	public void  listProductSelectionRuleMaster() throws SystemException, PersistenceException{
		ProductDefaultsDAO dao = mock(ProductDefaultsSqlDAO.class);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(ProductDefaultsPersistenceConstants.MODULE_NAME);
		productDefaultsControllerspy.listProductSelectionRuleMaster("IBS");
	}
	
	/**
	 * 	Method		:	ProductDefaultsControllerTest.invokeSaveProductSelectionRuleMasterDetails
	 *	Added by 	:	Prashant Behera on Jul 1, 2022
	 * 	Used for 	:
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 * @throws PersistenceException 
	 */
	@Test
	public void invokeSaveProductSelectionRuleMasterDetails() throws SystemException, PersistenceException{
		ProductDefaultsDAO dao = mock(ProductDefaultsSqlDAO.class);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(ProductDefaultsPersistenceConstants.MODULE_NAME);
		productDefaultsControllerspy.saveProductSelectionRuleMasterDetails(new ArrayList<>());
	}
	
	/**
	 * 	Method		:	ProductDefaultsControllerTest.invokeSaveProductSelectionRuleMasterDetailsWithCollectionPopulated
	 *	Added by 	:	Prashant Behera on Jul 1, 2022
	 * 	Used for 	:
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	void
	 */
	@Test (expected = SystemException.class)
	public void invokeSaveProductSelectionRuleMasterDetailsWithCollectionPopulated() throws SystemException, PersistenceException{
		ProductDefaultsDAO dao = mock(ProductDefaultsSqlDAO.class);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(ProductDefaultsPersistenceConstants.MODULE_NAME);
		Collection<ProductSelectionRuleMasterVO> productSelectionRuleMasterVOs =  new ArrayList<>();
		ProductSelectionRuleMasterVO productSelectionRuleMasterVO = new ProductSelectionRuleMasterVO();
		productSelectionRuleMasterVO.setSourceCode("EBL");
		productSelectionRuleMasterVO.setSourceCode("QGO CLASSIC");
		productSelectionRuleMasterVO.setAgentCode("A001");
		productSelectionRuleMasterVO.setAgentGroupCode("A001");
		productSelectionRuleMasterVO.setCommodityCode("GEN");
		productSelectionRuleMasterVO.setDestinationCountryCode("LAX");
		productSelectionRuleMasterVO.setInternationalDomesticFlag("INT");
		productSelectionRuleMasterVO.setOriginCountryCode("SYD");
		productSelectionRuleMasterVO.setProductCode("PRD");
		productSelectionRuleMasterVO.setSccCode("GEN");
		productSelectionRuleMasterVO.setSccGroupCode("GENCARGO");
		productSelectionRuleMasterVOs.add(productSelectionRuleMasterVO);
		productDefaultsControllerspy.saveProductSelectionRuleMasterDetails(productSelectionRuleMasterVOs);
	}
	
	@Test
	public void invokeSaveProductSelectionRuleMasterDetailsWithCollectionPopulatedWithMockProxy() throws SystemException, PersistenceException, ProxyException, InvalidProductException{
		ProductDefaultsDAO dao = mock(ProductDefaultsSqlDAO.class);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(ProductDefaultsPersistenceConstants.MODULE_NAME);
		Collection<ProductSelectionRuleMasterVO> productSelectionRuleMasterVOs =  new ArrayList<>();
		ProductSelectionRuleMasterVO productSelectionRuleMasterVO = new ProductSelectionRuleMasterVO();
		productSelectionRuleMasterVO.setSourceCode("EBL");
		productSelectionRuleMasterVO.setSourceCode("QGO CLASSIC");
		productSelectionRuleMasterVO.setAgentCode("A001");
		productSelectionRuleMasterVO.setAgentGroupCode("A001");
		productSelectionRuleMasterVO.setCommodityCode("GEN");
		productSelectionRuleMasterVO.setDestinationCountryCode("LAX");
		productSelectionRuleMasterVO.setInternationalDomesticFlag("INT");
		productSelectionRuleMasterVO.setOriginCountryCode("SYD");
		productSelectionRuleMasterVO.setProductCode("PRD");
		productSelectionRuleMasterVO.setSccCode("GEN");
		productSelectionRuleMasterVO.setSccGroupCode("GENCARGO");
		productSelectionRuleMasterVOs.add(productSelectionRuleMasterVO);
		doReturn(new ArrayList<>()).when(sccProxy).validateSCCCodes(any(String.class),any(Collection.class));
		doReturn(new HashMap<>()).when(sharedCommodityProxy).validCommodityCodes(any(String.class),any(Collection.class));
		doReturn(new HashMap<>()).when(sharedAreaProxy).validateCountryCodes(any(String.class),any(Collection.class));
		doReturn(new HashMap<>()).when(sharedAgentProxy).validateAgents(any(String.class),any(Collection.class));
		doReturn(new HashMap<>()).when(productDefaultsControllerspy).validateProductNames(any(String.class),any(Collection.class));
		productDefaultsControllerspy.saveProductSelectionRuleMasterDetails(productSelectionRuleMasterVOs);
	}
	
	@Test (expected = SystemException.class)
	public void doThrowProxyExceptionSaveProductSelectionRuleMasterDetailsWithCollectionPopulatedWithMockProxy() throws SystemException, PersistenceException, ProxyException{
		ProductDefaultsDAO dao = mock(ProductDefaultsSqlDAO.class);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(ProductDefaultsPersistenceConstants.MODULE_NAME);
		Collection<ProductSelectionRuleMasterVO> productSelectionRuleMasterVOs =  new ArrayList<>();
		ProductSelectionRuleMasterVO productSelectionRuleMasterVO = new ProductSelectionRuleMasterVO();
		productSelectionRuleMasterVO.setSourceCode("EBL");
		productSelectionRuleMasterVO.setSourceCode("QGO CLASSIC");
		productSelectionRuleMasterVO.setAgentCode("A001");
		productSelectionRuleMasterVO.setAgentGroupCode("A001");
		productSelectionRuleMasterVO.setCommodityCode("GEN");
		productSelectionRuleMasterVO.setDestinationCountryCode("LAX");
		productSelectionRuleMasterVO.setInternationalDomesticFlag("INT");
		productSelectionRuleMasterVO.setOriginCountryCode("SYD");
		productSelectionRuleMasterVO.setProductCode("PRD");
		productSelectionRuleMasterVO.setSccCode("GEN");
		productSelectionRuleMasterVO.setSccGroupCode("GENCARGO");
		productSelectionRuleMasterVOs.add(productSelectionRuleMasterVO);
		doThrow(ProxyException.class).when(sccProxy).validateSCCCodes(any(String.class),any());
		doThrow(ProxyException.class).when(sharedCommodityProxy).validCommodityCodes(any(String.class),any());
		doThrow(ProxyException.class).when(sharedAreaProxy).validateCountryCodes(any(String.class),any());
		doThrow(ProxyException.class).when(sharedAgentProxy).validateAgents(any(String.class),any());
		productDefaultsControllerspy.saveProductSelectionRuleMasterDetails(productSelectionRuleMasterVOs);
	}
	
	@Test (expected = SystemException.class)
	public void doThrowSystemExceptionSaveProductSelectionRuleMasterDetailsWithCollectionPopulatedWithMockProxy() throws SystemException, PersistenceException, ProxyException, InvalidProductException{
		ProductDefaultsDAO dao = mock(ProductDefaultsSqlDAO.class);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(ProductDefaultsPersistenceConstants.MODULE_NAME);
		Collection<ProductSelectionRuleMasterVO> productSelectionRuleMasterVOs =  new ArrayList<>();
		ProductSelectionRuleMasterVO productSelectionRuleMasterVO = new ProductSelectionRuleMasterVO();
		productSelectionRuleMasterVO.setAgentCode("A001");
		productSelectionRuleMasterVO.setAgentGroupCode("A001");
		productSelectionRuleMasterVO.setCommodityCode("GEN");
		productSelectionRuleMasterVO.setDestinationCountryCode("LAX");
		productSelectionRuleMasterVO.setInternationalDomesticFlag("INT");
		productSelectionRuleMasterVO.setOriginCountryCode("SYD");
		productSelectionRuleMasterVO.setProductCode("PRD");
		productSelectionRuleMasterVO.setSccCode("GEN");
		productSelectionRuleMasterVO.setSccGroupCode("GENCARGO");
		productSelectionRuleMasterVOs.add(productSelectionRuleMasterVO);
		SystemException systemException = new SystemException("unknown");
		doThrow(systemException).when(sccProxy).validateSCCCodes(any(String.class),any());
		doThrow(systemException).when(sharedCommodityProxy).validCommodityCodes(any(String.class),any());
		doThrow(systemException).when(sharedAreaProxy).validateCountryCodes(any(String.class),any());
		doThrow(systemException).when(sharedAgentProxy).validateAgents(any(String.class),any());
		doThrow(systemException).when(productDefaultsControllerspy).validateProductNames(any(String.class),any());
		productDefaultsControllerspy.saveProductSelectionRuleMasterDetails(productSelectionRuleMasterVOs);
	}
	
	@Test (expected = SystemException.class)
	public void invokeSaveProductSelectionRuleMasterDetailsWithEmptySourceCodeAndNoProduct() throws SystemException, PersistenceException, ProxyException, InvalidProductException{
		ProductDefaultsDAO dao = mock(ProductDefaultsSqlDAO.class);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(ProductDefaultsPersistenceConstants.MODULE_NAME);
		Collection<ProductSelectionRuleMasterVO> productSelectionRuleMasterVOs =  new ArrayList<>();
		ProductSelectionRuleMasterVO productSelectionRuleMasterVO = new ProductSelectionRuleMasterVO();
		productSelectionRuleMasterVO.setSourceCode("");
		productSelectionRuleMasterVO.setAgentCode("A001");
		productSelectionRuleMasterVO.setAgentGroupCode("A001");
		productSelectionRuleMasterVO.setCommodityCode("GEN");
		productSelectionRuleMasterVO.setDestinationCountryCode("LAX");
		productSelectionRuleMasterVO.setInternationalDomesticFlag("INT");
		productSelectionRuleMasterVO.setOriginCountryCode("SYD");
		productSelectionRuleMasterVO.setSccCode("GEN");
		productSelectionRuleMasterVO.setSccGroupCode("GENCARGO");
		productSelectionRuleMasterVOs.add(productSelectionRuleMasterVO);
		doReturn(new ArrayList<>()).when(sccProxy).validateSCCCodes(any(String.class),any(Collection.class));
		doReturn(new HashMap<>()).when(sharedCommodityProxy).validCommodityCodes(any(String.class),any(Collection.class));
		doReturn(new HashMap<>()).when(sharedAreaProxy).validateCountryCodes(any(String.class),any(Collection.class));
		doReturn(new HashMap<>()).when(sharedAgentProxy).validateAgents(any(String.class),any(Collection.class));
		doReturn(new HashMap<>()).when(productDefaultsControllerspy).validateProductNames(any(String.class),any(Collection.class));
		productDefaultsControllerspy.saveProductSelectionRuleMasterDetails(productSelectionRuleMasterVOs);
	}
	
	@Test (expected = SystemException.class)
	public void invokeSaveProductSelectionRuleMasterDetailsWithEmptySourceCodeAndProductEmpty() throws SystemException, PersistenceException, ProxyException, InvalidProductException{
		ProductDefaultsDAO dao = mock(ProductDefaultsSqlDAO.class);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(ProductDefaultsPersistenceConstants.MODULE_NAME);
		Collection<ProductSelectionRuleMasterVO> productSelectionRuleMasterVOs =  new ArrayList<>();
		ProductSelectionRuleMasterVO productSelectionRuleMasterVO = new ProductSelectionRuleMasterVO();
		productSelectionRuleMasterVO.setSourceCode("");
		productSelectionRuleMasterVO.setProductCode("");
		productSelectionRuleMasterVO.setAgentCode("");
		productSelectionRuleMasterVO.setAgentGroupCode("");
		productSelectionRuleMasterVO.setCommodityCode("");
		productSelectionRuleMasterVO.setDestinationCountryCode("");
		productSelectionRuleMasterVO.setInternationalDomesticFlag("");
		productSelectionRuleMasterVO.setOriginCountryCode("");
		productSelectionRuleMasterVO.setSccCode("");
		productSelectionRuleMasterVO.setSccGroupCode("");
		productSelectionRuleMasterVOs.add(productSelectionRuleMasterVO);
		doReturn(new ArrayList<>()).when(sccProxy).validateSCCCodes(any(String.class),any(Collection.class));
		doReturn(new HashMap<>()).when(sharedCommodityProxy).validCommodityCodes(any(String.class),any(Collection.class));
		doReturn(new HashMap<>()).when(sharedAreaProxy).validateCountryCodes(any(String.class),any(Collection.class));
		doReturn(new HashMap<>()).when(sharedAgentProxy).validateAgents(any(String.class),any(Collection.class));
		doReturn(new HashMap<>()).when(productDefaultsControllerspy).validateProductNames(any(String.class),any(Collection.class));
		productDefaultsControllerspy.saveProductSelectionRuleMasterDetails(productSelectionRuleMasterVOs);
	}
	
	@Test (expected = SystemException.class)
	public void invokeSaveProductSelectionRuleMasterDetailsWithEmptySourceCodeProductEmptyAndOtherFieldsNull() throws SystemException, PersistenceException, ProxyException, InvalidProductException{
		ProductDefaultsDAO dao = mock(ProductDefaultsSqlDAO.class);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(ProductDefaultsPersistenceConstants.MODULE_NAME);
		Collection<ProductSelectionRuleMasterVO> productSelectionRuleMasterVOs =  new ArrayList<>();
		ProductSelectionRuleMasterVO productSelectionRuleMasterVO = new ProductSelectionRuleMasterVO();
		productSelectionRuleMasterVO.setSourceCode("");
		productSelectionRuleMasterVO.setProductCode("");
		productSelectionRuleMasterVOs.add(productSelectionRuleMasterVO);
		productSelectionRuleMasterVO = new ProductSelectionRuleMasterVO();
		productSelectionRuleMasterVO.setSourceCode("");
		productSelectionRuleMasterVO.setProductCode("");
		productSelectionRuleMasterVOs.add(productSelectionRuleMasterVO);
		doReturn(new ArrayList<>()).when(sccProxy).validateSCCCodes(any(String.class),any(Collection.class));
		doReturn(new HashMap<>()).when(sharedCommodityProxy).validCommodityCodes(any(String.class),any(Collection.class));
		doReturn(new HashMap<>()).when(sharedAreaProxy).validateCountryCodes(any(String.class),any(Collection.class));
		doReturn(new HashMap<>()).when(sharedAgentProxy).validateAgents(any(String.class),any(Collection.class));
		doReturn(new HashMap<>()).when(productDefaultsControllerspy).validateProductNames(any(String.class),any(Collection.class));
		productDefaultsControllerspy.saveProductSelectionRuleMasterDetails(productSelectionRuleMasterVOs);
	}
}
