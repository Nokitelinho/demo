/*
 * ProductDefaultsSqlDAO.java Created on Aug 27, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.products.defaults;


import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

import com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductFeedbackFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductFeedbackVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductLovFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductLovVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductModelMappingFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductModelMappingVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductODMappingVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductParamterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductPriorityVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductSCCVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductSelectionRuleMasterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductServiceVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductStockVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductSuggestionVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductTransportModeVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCommodityVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCustomerGroupVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionPaymentTermsVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionSegmentVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionStationVO;
import com.ibsplc.icargo.business.products.defaults.vo.StationAvailabilityFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.SubProductVO;
import com.ibsplc.icargo.business.shared.defaults.generalparameters.vo.GeneralParametersVO;
import com.ibsplc.icargo.framework.model.ImageModel;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.AbstractQueryDAO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.Query;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * ProductDefaultsSqlDAO is used for getting the details
 * @author A-1883
 *
 */
public class ProductDefaultsSqlDAO extends AbstractQueryDAO implements ProductDefaultsDAO {

	/**
	 * Field 	: INTDOMFLG	of type : String
	 * Used for :
	 */
	private static final String INTDOMFLG = "INTDOMFLG";

	/**
	 * Field 	: SCCGRPCOD	of type : String
	 * Used for :
	 */
	private static final String SCCGRPCOD = "SCCGRPCOD";

	/**
	 * Field 	: AGTGRPCOD	of type : String
	 * Used for :
	 */
	private static final String AGTGRPCOD = "AGTGRPCOD";

	/**
	 * Field 	: DSTCNTCOD	of type : String
	 * Used for :
	 */
	private static final String DSTCNTCOD = "DSTCNTCOD";

	/**
	 * Field 	: ORGCNTCOD	of type : String
	 * Used for :
	 */
	private static final String ORGCNTCOD = "ORGCNTCOD";

	/**
	 * Field 	: COMCOD	of type : String
	 * Used for :
	 */
	private static final String COMCOD = "COMCOD";

	/**
	 * Field 	: AGTCOD	of type : String
	 * Used for :
	 */
	private static final String AGTCOD = "AGTCOD";

	/**
	 * Field 	: SRCCOD	of type : String
	 * Used for :
	 */
	private static final String SRCCOD = "SRCCOD";

	private Log log = LogFactory.getLogger("PRODUCT DEFAULT SQL DAO");

	private static final String PRODUCT_DEFAULTS_FIND_PRODUCTS = "product.defaults.findProducts";
	private static final String PRODUCT_DEFAULTS_FIND_IMAGE = "product.defaults.findImage";
	private static final String PRD_DEFAULTS_VALIDATE_PRODUCT_NAME = "product.defaults.validateProductName";
	private static final String PRD_DEFAULTS_VALIDATE_PRODUCT_FOR_PERIOD =
			"product.defaults.validateProductForAPeriod";
	private static final String PRODUCT_DEFAULTS_FIND_SUBPRODUCTS = "product.defaults.findSubProducts";
	private static final String PRODUCT_DEFAULTS_FIND_SUBPRODUCT_DETAILS =
			"product.defaults.findSubProductDetails";
	private static final String PRD_DEFAULTS_FIND_PRODUCT_DETAILS = "product.defaults.findProductDetails";
	private static final String PRD_DEFAULTS_FIND_PRODUCT_SCC_DETAILS="product.defaults.findProductScc";
	private static final String PRD_DEFAULTS_FIND_PRODUCT_SERVICE_DETAILS="product.defaults.findProductService";
	private static final String PRD_DEFAULTS_FIND_PRODUCT_TRANSPORTMODE_DETAILS=
								"product.defaults.findProductTransportMode";
	private static final String PRD_DEFAULTS_FIND_PRODUCT_PRIORITY_DETAILS="product.defaults.findProductPriority";
	private static final String PRD_DEFAULTS_FIND_PRODUCT_RESTRICTION_COMMODITY=
								"product.defaults.findProductRestrictionCommodity";
	private static final String PRD_DEFAULTS_FIND_PRODUCT_RESTRICTION_CUSTOMER=
								"product.defaults.findProductRestrictionCustomerGroup";
	private static final String PRD_DEFAULTS_FIND_PRODUCT_RESTRICTION_PAYMENT=
								"product.defaults.findProductRestrictionPaymentTerms";
	private static final String PRD_DEFAULTS_FIND_PRODUCT_RESTRICTION_SEGMENT=
								"product.defaults.findProductRestrictionSegment";
	private static final String PRD_DEFAULTS_FIND_PRODUCT_RESTRICTION_STATION=
								"product.defaults.findProductRestrictionStation";
	private static final String PRD_DEFAULTS_FIND_PRODUCT_EVENT_DETAILS="product.defaults.findProductEvent";
	private static final String PRD_DEFAULTS_CHECK_DUPLICATE="product.defaults.checkDuplicate";
	private static final String PRD_DEFAULTS_FIND_PRODUCTS__BY_NAME="product.defaults.findProductsByName";
	private static final String PRODUCT_DEFAULTS_FIND_SUBPRODUCT_EVENTS="product.defaults.findSubProductEvent";
	private static final String PRODUCT_DEFAULTS_FIND_SUBPRODUCT_SERVICES="product.defaults.findSubProductServices";
	private static final String PRODUCT_DEFAULTS_FIND_SUBPRODUCT_RESTRICTION_STATION=
								"product.defaults.findSubProductRestrictionStation";
	private static final String PRODUCT_DEFAULTS_FIND_SUBPRODUCT_RESTRICTION_SEGMENT=
								"product.defaults.findSubProductRestrictionSegment";
	private static final String PRODUCT_DEFAULTS_FIND_SUBPRODUCT_RESTRICTION_PAYMENTTERMS=
			"product.defaults.findSubProductRestrictionPaymentTerms";
	private static final String PRODUCT_DEFAULTS_FIND_SUBPRODUCT_RESTRICTION_CUSTOMERGP=
			"product.defaults.findSubproductRestrictionCustomerGroup";
	private static final String PRODUCT_DEFAULTS_FIND_SUBPRODUCT_RESTRICTION_COMMODITY=
			"product.defaults.findSubproductRestrictionCommodity";
	private static final String PRD_DEFAULTS_FIND_PRODUCT_LOV="product.defaults.findProductLov";
	private static final String PRD_DEFAULTS_CHECK_DUPLICATE_MODIFY="product.defaults.checkDuplicateForModify";
	private static final String PRODUCTS_DEFAULTS_FINDPRODUCTSERVICES="product.defaults.findProductServices";
	private static final String PRODUCTS_DEFAULTS_VALIDATESUBPRODUCT="product.defaults.validatesubproduct";
	private static final String PRODUCTS_DEFAULTS_FINDPRODUCTICON= "product.defaults.findProductIcon";
	private static final String PRODUCTS_DEFAULTS_LIST_PRODUCT_FEEDBACK= "product.defaults.listProductFeedback";
	
	//Added by A-5201 as part for the ICRD-22065 starts
	private static final String PRODUCTS_DEFAULTS_DENSE_RANK_QUERY="SELECT RESULT_TABLE.* ,DENSE_RANK() OVER ( ORDER BY ";
    private static final String PRODUCTS_DEFAULTS_ROWNUM_RANK_QUERY="SELECT RESULT_TABLE.* ,ROW_NUMBER() OVER (ORDER BY null) AS RANK FROM(";
    private static final String PRODUCTS_DEFAULTS_SUFFIX_QUERY=") RESULT_TABLE";
  //Added by A-5201 as part for the ICRD-22065 end
    
	private static final String PRODUCTS_DEFAULTS_CHECK_STATION_AVAILABILITY =
		  	"product.defaults.checkStationAvailability";
    private static final String PRODUCTS_DEFAULTS_VALIDATE_PRODUCTS =
            "product.defaults.validateProducts";
    private static final String PRODUCTS_DEFAULTS_FINDSCCCODESFORPRODUCTS =
        	"product.defaults.findSccCodesForProduct";
    private static final String PRODUCTS_DEFAULTS_VALIDATEPRODUCT =
    	"product.defaults.validateProduct";
    private static final String PRODUCTS_DEFAULTS_FINDEVENTSFORSUBPRODCUT =
    	"product.defaults.findSubproductEventsForTracking";
    private static final String PRODUCTS_DEFAULTS_VALIDATEPRODUCTFORDOCTYPE=
    	"product.defaults.validateProductForDocType";
    private static final String PRODUCTS_DEFAULTS_FINDPRODUCTPKFORPRODUCTNAME=
    	"product.defaults.findProductPkForProductName";
    private static final String PRODUCT_DEFAULTS_VALIDATE_PRODUCTNAMES=
		"product.defaults.validateproductnames";
    private static final String PRODUCT_DEFAULTS_VALIDATE_PRODUCTS_FOR_LISTING=
        	"product.defaults.validateProductsForListing";
    private static final String PRODUCT_DEFAULTS_FINDPRIORITY_FOR_PRODUCT=
    	"product.defaults.findPriorityForProduct";
    private static final String PRODUCT_DEFAULTS_FIND_BOOKABLE_PRODUCTS=
    	"product.defaults.findBookableProducts";
    public static final String PRODUCT_DEFAULTS_FIND_SUGGESTED_PRODUCTS = "product.defaults.findSuggestedProducts";
    public static final String PRODUCT_DEFAULTS_CHECKIFDUPLICATEPRDPRIORITYEXIST = "product.defaults.checkIfDuplicatePrdPriorityExists";
    public static final String POSTGRES_PRODUCT_DEFAULTS_CHECKIFDUPLICATEPRDPRIORITYEXIST="product.defaults.checkIfDuplicatePrdPriorityExistsPostgres";
    public static final String PRODUCT_DEFAULTS_GET_PRODUCTMODEL_MAPPING = "product.defaults.getProductModelMapping";
    public static final String PRODUCT_FINDPRODUCTPARCODE ="product.defaults.findProductParametersbycode";
    private static final String COOL="COL";//Added for ICRD-166985 by A-5117
    private static final String PRD_DEFAULTS_FIND_PRODUCT_FOR_MASTER="product.defaults.productsformaster";
    
    private static final String PRODUCT_DEFAULTS_VALIDATE_PRODUCTSBYNAMES=
		"product.defaults.validateProductsByNames"; // Added by A-9025 for IASCB-10696
	private static final String PRDICO = "PRDICO";
    
    private static final String SCCCOD = "SCCCOD";
    private static final String REGEXP_COMMA ="[,]";
    private static final Pattern PATTERN_COMMA = Pattern.compile(REGEXP_COMMA);

	/**
	 * Find All Products for the specied filter criteria
	 * @param productFilterVo filter values
	 * @param displayPage
	 * @return Page<ProductVO> Object wrapping collection of ProductVO
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Page<ProductVO> findProducts(ProductFilterVO productFilterVo,int displayPage)
	throws PersistenceException, SystemException {
		log.entering("findProducts", "enter");
		String baseQry = getQueryManager().getNamedNativeQueryString(PRODUCT_DEFAULTS_FIND_PRODUCTS);
		//Added by A-5201 as part for the ICRD-22065 starts
		StringBuilder rankQuery = new StringBuilder().append(PRODUCTS_DEFAULTS_DENSE_RANK_QUERY);
		rankQuery.append("RESULT_TABLE.PRDCOD,RESULT_TABLE.CMPCOD )AS RANK FROM(");
		rankQuery.append(baseQry);
		
		PageableNativeQuery<ProductVO> pageableQuery = new ProductDefaultsFilterQuery(productFilterVo,rankQuery.toString(),new ProductMapper());
		//Added by A-5201 as part for the ICRD-22065 end
		return pageableQuery.getPage(displayPage);

	}
	/**
	 * Find All SubProducts for the specied filter criteria
	 *
	 * @param productFilterVo filter values
	 * @param displayPage
	 * @return Page Object wrapping collection of SubProductVO
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Page<SubProductVO> findSubProducts(ProductFilterVO productFilterVo,
			int displayPage) throws SystemException,PersistenceException,
			SystemException {
		log.entering("findSubProducts", "enter");
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(PRODUCTS_DEFAULTS_ROWNUM_RANK_QUERY);
		String baseQry = getQueryManager().getNamedNativeQueryString(PRODUCT_DEFAULTS_FIND_SUBPRODUCTS);
		rankQuery.append(baseQry); 
		PageableNativeQuery<SubProductVO> pgqry = new SubProductDefaultsFilterQuery(productFilterVo,rankQuery.toString(),new SubProductMapper());
		pgqry.append(" ORDER BY UPPER(TRAMOD) ");
		pgqry.append(")RESULT_TABLE");
		//PageableQuery<SubProductVO> pageableQuery = new PageableQuery<SubProductVO>(query, new SubProductMapper());
		//return pageableQuery.getPage(displayPage);
		return pgqry.getPage(productFilterVo.getPageNumber());
	}

	/**
	 * Checks whether the given product name exists in the database. Returns the
	 * product code if the product exiss. Returns NULL otherwise
	 * @param companyCode
	 * @param productName
	 * @param startDate
	 * @param endDate
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public String validateProductName(String companyCode, String productName,Calendar startDate,Calendar endDate)
	throws PersistenceException, SystemException {
		log.entering("ProductDefaultsSqlDAO","validateProductName");
		Query qry = getQueryManager().createNamedNativeQuery(
				PRD_DEFAULTS_VALIDATE_PRODUCT_NAME);
		qry.setParameter(1, companyCode);
		qry.setParameter(2, productName.toUpperCase());
		qry.setParameter(3, startDate);
		qry.setParameter(4, endDate);
		Mapper<String> productCodeMapper = new Mapper<String>() {
			/**
			 * Method will return the productcode
			 * @param resultSet
			 * @return String
			 * @exception SQLException
			 */
			public String map(ResultSet resultSet) throws SQLException {
				String productCode=null;
				return resultSet.getString("PRDCOD");
			}
		};
		log.exiting("ProductDefaultsSqlDAO","validateProductName");
		return qry.getSingleResult(productCodeMapper);
	}

	/**
	 * Checks whether the given product name exists in the database. Returns the
	 * product code if the product exiss. Returns NULL otherwise
	 * @param companyCode
	 * @param productName
	 * @param startDate
	 * @param endDate
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Collection<ProductValidationVO> validateProductForAPeriod(String companyCode, String productName,Calendar startDate,Calendar endDate)
	throws PersistenceException, SystemException {
		log.entering("ProductDefaultsSqlDAO","validateProductForAPeriod");
		Query qry = getQueryManager().createNamedNativeQuery(
				PRD_DEFAULTS_VALIDATE_PRODUCT_FOR_PERIOD);
		qry.setParameter(1, companyCode);
		qry.setParameter(2, productName.toUpperCase());
		qry.setParameter(3, startDate);
		qry.setParameter(4, endDate);
		Collection<ProductValidationVO> products = qry.getResultList(new ValidateProductMapper());
		log.exiting("ProductDefaultsSqlDAO","validateProductForAPeriod");
		log.log(Log.FINE, "The obtained Product codes :", products);
		return products;
	}
	/**
	 *
	 * @author A-1885
	 *
	 */
	private class ValidateProductMapper implements Mapper<ProductValidationVO>{
		public ProductValidationVO map(ResultSet resultSet)throws SQLException{
			ProductValidationVO validVo = new ProductValidationVO();
			validVo.setProductName(resultSet.getString("PRDNAM"));
			validVo.setProductCode(resultSet.getString("PRDCOD"));
			validVo.setStartDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE,resultSet.getDate("PRDSTRDAT")));
			validVo.setEndDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE,resultSet.getDate("PRDENDDAT")));
			validVo.setDescription(resultSet.getString("PRDDES"));
			if(ProductValidationVO.FLAG_YES.equals(resultSet.getString("BKGIND"))){
				validVo.setBookingMandatory(true);
			}
			else{
				validVo.setBookingMandatory(false);
			}
			return validVo;
		}

	}

	/**
	 * check for duplicate product
	 * @param productName
	 * @param companyCode
	 * @param startDate
	 * @param endDate
	 * @return boolean
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public boolean checkDuplicate(String productName,String companyCode,Calendar startDate,Calendar endDate)
	throws PersistenceException, SystemException{
		boolean duplicate=false;
		log.entering("checkDuplicate","--Enter");
		Query qry = getQueryManager().createNamedNativeQuery(
				PRD_DEFAULTS_CHECK_DUPLICATE);
		qry.setParameter(1, productName.toUpperCase());
		qry.setParameter(2, companyCode);
		qry.setParameter(3, startDate);
		qry.setParameter(4, endDate);
		qry.setParameter(5, startDate);
		qry.setParameter(6, endDate);
		qry.setParameter(7, startDate);
		qry.setParameter(8, endDate);
		Mapper<String> checkDuplicateMapper = new Mapper<String>() {
			/**
			 * @param rs
			 * @return String
			 * @throws SQLException
			 */
			public String map(ResultSet rs) throws SQLException {
				String  count;
				return rs.getString("PRDCOD");
			}
		};
		if(qry.getSingleResult(checkDuplicateMapper)==null){
			duplicate=false;
		}
		else{
			duplicate=true;
		}
		return duplicate;
	}
	/**
	 * check for duplicate product during modify
	 * @param productName
	 * @param companyCode
	 * @param productCode
	 * @param startDate
	 * @param endDate
	 * @return boolean
	 * @exception PersistenceException
	 * @exception SystemException
	 */
	public boolean checkDuplicateForModify(String productName,String companyCode,String productCode,
			Calendar startDate,Calendar endDate)throws PersistenceException, SystemException{
		log.entering("checkDuplicateForModify","--Enter");
		boolean duplicate=false;
		Query qry = getQueryManager().createNamedNativeQuery(
				PRD_DEFAULTS_CHECK_DUPLICATE_MODIFY);
		qry.setParameter(1, productName.toUpperCase());
		qry.setParameter(2, companyCode);
		qry.setParameter(3, startDate);
		qry.setParameter(4, endDate);
		qry.setParameter(5, startDate);
		qry.setParameter(6, endDate);
		qry.setParameter(7, startDate);
		qry.setParameter(8, endDate);
		qry.setParameter(9, productCode);
		Mapper<String> checkDuplicateMapper = new Mapper<String>() {
			/**
			 * @param rs
			 * @return String
			 * @throws SQLException
			 */
			public String map(ResultSet rs) throws SQLException {
				String  count;
				return rs.getString("PRDCOD");
			}
		};
		if(qry.getSingleResult(checkDuplicateMapper)==null){
			duplicate=false;
		}
		else{
			duplicate=true;
		}
		log.exiting("checkDuplicateForModify","--Exit");
		return duplicate;
	}

	/**
	 * @param companyCode
	 * @param productName
	 * @throws PersistenceException
	 * @throws SystemException
	 * @return Collection<ProductValidationVO>
	 */
	public Collection<ProductValidationVO> findProductsByName(String companyCode, String productName)
	throws PersistenceException, SystemException {
		Collection<ProductValidationVO> productValidationList=new ArrayList<ProductValidationVO>();
		Query qry = getQueryManager().createNamedNativeQuery(
				PRD_DEFAULTS_FIND_PRODUCTS__BY_NAME);
		qry.setParameter(1, companyCode);
		qry.setParameter(2, productName.toUpperCase());
		return qry.getResultList(new FindProductMapper());

	}
	/**
	 * FindProductMapper is for mapping the productDetails
	 *
	 */
	private class FindProductMapper implements Mapper<ProductValidationVO>{
		/**
		 * mapper for finding a product
		 * @param resultSet
		 * @exception SQLException
		 * @return ProductValidationVO
		 *
		 */
		public ProductValidationVO map(ResultSet resultSet) throws SQLException{
			log.entering("FindProductMapper", "enter");
			ProductValidationVO productValidationVO=new ProductValidationVO();
			productValidationVO.setProductName(resultSet.getString("PRDNAM"));
			productValidationVO.setProductCode(resultSet.getString("PRDCOD"));
			productValidationVO.setStartDate(new LocalDate(
					LocalDate.NO_STATION,Location.NONE,resultSet.getDate("PRDSTRDAT")));
			productValidationVO.setEndDate(new LocalDate(
					LocalDate.NO_STATION,Location.NONE,resultSet.getDate("PRDENDDAT")));
			return productValidationVO;
		}
	}

	/**
	 * Used to fetch the details of a particular product
	 * @param companyCode
	 * @param productCode
	 * @return collection of ProductLovVO
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	private ProductVO findProductSqlDetails(String companyCode, String productCode)
	throws SystemException,PersistenceException{
		log.entering("findProductSqlDetails", "enter");
		ProductVO productList= null;
		Query qry = getQueryManager().createNamedNativeQuery(
				PRD_DEFAULTS_FIND_PRODUCT_DETAILS);
		qry.setParameter(1, companyCode);
		qry.setParameter(2, productCode);
		return qry.getSingleResult(new ProductDetailsMapper());
	}
	/**
	 * Method for finding productDetails
	 * @param companyCode
	 * @param productCode
	 * @throws SystemException
	 * @throws PersistenceException
	 * @return ProductVO
	 */
	public ProductVO findProductDetails(String companyCode,String productCode)
	throws SystemException,PersistenceException{
		log.entering("findProductDetails", "enter "); 
		ProductVO productVO=new ProductVO();
		productVO=findProductSqlDetails(companyCode,productCode);
		if(productVO!=null){
			productVO.setProductScc(findProductScc(companyCode,productCode));
			productVO.setPriority(findProductPriority(companyCode,productCode));
			productVO.setTransportMode(findProductTransportMode(companyCode,productCode));
			productVO.setServices(findProductService(companyCode,productCode));
			productVO.setProductEvents(findProductEvent(companyCode,productCode));
			productVO.setRestrictionCommodity(findProductCommodtiy(companyCode,productCode));
			productVO.setRestrictionCustomerGroup(findProductCustomerGroup(companyCode,productCode));
			productVO.setRestrictionPaymentTerms(findProductPaymentTerms(companyCode,productCode));
			productVO.setRestrictionSegment(findProductSegment(companyCode,productCode));
			productVO.setRestrictionStation(findProductStation(companyCode,productCode));
			productVO.setProductIconPresent(findProductIcon(companyCode,productCode));
			//Added for ICRD-259237 by A-7740
			productVO.setProductParamters(findProductParameters(companyCode, productCode));
			if(productVO.getProductParamters()==null){
				productVO.setProductParamters(new ArrayList<ProductParamterVO>());
			}
			Collection<ProductParamterVO> productParameters=findAllProductParameters(companyCode);
			if(productParameters!=null && !productParameters.isEmpty()){				
				Map<String,ProductParamterVO> productParameterMap=new HashMap<String,ProductParamterVO>();
				for(ProductParamterVO productParamterVO:productVO.getProductParamters()){
					productParameterMap.put(productParamterVO.getParameterCode(), productParamterVO);
				}
				for(ProductParamterVO productParamterVO:productParameters){
					if(!productParameterMap.containsKey(productParamterVO.getParameterCode())){
						productVO.getProductParamters().add(productParamterVO);
					}					
				}
			}
		}
		return productVO;
	}
	/**
	 * 	Method		:	ProductDefaultsSqlDAO.findProductParameters
	 *	Added by 	:	A-7740 on 04-Oct-2018
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param productCode
	 *	Parameters	:	@return 
	 *	Return type	: 	Object
	 * @throws SystemException 
	 */
	private Collection<ProductParamterVO> findProductParameters(String companyCode, String productCode) throws SystemException {
		this.log.entering("ProductSqlDao", "findProductParameters");
		Query query = getQueryManager().createNamedNativeQuery("product.defaults.findProductParameters");
		query.setParameter(1,companyCode);
		query.setParameter(2,productCode);
		return query.getResultList(new ProductParameterMapper());
	}
	/**
	 * This method will find the details of a Subproduct
	 * @param subProductVO
	 * @return SubProductVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public SubProductVO findSubProductDetails(SubProductVO subProductVO)
	throws SystemException,PersistenceException{
		log.entering("findSubProductDetails", "enter");
		SubProductVO subProductVo=new SubProductVO();
		subProductVo=getSubProductDetails(subProductVO);
		subProductVo.setRestrictionCommodity(findRestrictionCommodity(subProductVO));
		subProductVo.setRestrictionCustomerGroup(findRestrictionCustomerGroup(subProductVO));
		subProductVo.setRestrictionPaymentTerms(findRestrictionPaymentTerms(subProductVO));
		subProductVo.setRestrictionSegment(findRestrictionSegment(subProductVO));
		subProductVo.setRestrictionStation(findRestrictionStation(subProductVO));
		subProductVo.setServices(findServices(subProductVO));
		subProductVo.setEvents(findEvents(subProductVO));
	 return subProductVo;
	}
	/**
	 * This method will return all events associated with the subproduct
	 * @param subProductVO
	 * @return subProductServiceVOs
	 * @throws SystemException
	 */
	private Collection<ProductEventVO> findEvents(SubProductVO subProductVO)throws SystemException{
		Query query = getQueryManager().createNamedNativeQuery(PRODUCT_DEFAULTS_FIND_SUBPRODUCT_EVENTS);
		query.setParameter(1,subProductVO.getCompanyCode());
		query.setParameter(2,subProductVO.getProductCode());
		query.setParameter(3,subProductVO.getSubProductCode());
		query.setParameter(4,subProductVO.getVersionNumber());
		return query.getResultList(new ProductEventMapper());
	}

	/**
	 * This method will return all services associated with the subproduct
	 * @param subProductVO
	 * @return subProductServiceVOs
	 * @throws SystemException
	 */
	private Collection<ProductServiceVO> findServices(SubProductVO subProductVO)throws SystemException{
		Query query = getQueryManager().createNamedNativeQuery(PRODUCT_DEFAULTS_FIND_SUBPRODUCT_SERVICES);
		query.setParameter(1,subProductVO.getCompanyCode());
		query.setParameter(2,subProductVO.getProductCode());
		query.setParameter(3,subProductVO.getSubProductCode());
		query.setParameter(4,subProductVO.getVersionNumber());
		return query.getResultList(new SubProductServiceMapper());
	}
	/**
	 * @author A-1883
	 * This mapper is used for findSubProductServices()
	 */
	class SubProductServiceMapper implements Mapper<ProductServiceVO>{
		/**
		 * This method is used to map the result of query to ProductServiceVO
		 * @param resultSet
		 * @throws SQLException
		 * @return ProductServiceVO
		 */
		public ProductServiceVO map(ResultSet resultSet) throws SQLException {

			ProductServiceVO subProductServiceVO=new ProductServiceVO();
			subProductServiceVO.setServiceCode(resultSet.getString("SERCOD"));
			subProductServiceVO.setServiceDescription(resultSet.getString("SERDES"));
			if(("Y").equals(resultSet.getString("TRANSPLAN"))){
				subProductServiceVO.setTransportationPlanExist(true);
			}
			else{
				subProductServiceVO.setTransportationPlanExist(false);
			}
		return subProductServiceVO;
		}
	}
	/**
	 * This method will return all restricted stations of the corresponding subproduct
	 * @param subProductVO
	 * @return Collection <restrictionStationVOs>
	 * @throws SystemException
	 */
	private Collection<RestrictionStationVO> findRestrictionStation(SubProductVO subProductVO)
	throws SystemException{
		Query query = getQueryManager().createNamedNativeQuery(PRODUCT_DEFAULTS_FIND_SUBPRODUCT_RESTRICTION_STATION);
		query.setParameter(1,subProductVO.getCompanyCode());
		query.setParameter(2,subProductVO.getProductCode());
		query.setParameter(3,subProductVO.getSubProductCode());
		query.setParameter(4,subProductVO.getVersionNumber());
		return query.getResultList(new RestrictionStationMapper());
	}
	/**
	 * @author A-1883
	 * This mapper is used for findSubProductRestrictionStation()
	 */
	class RestrictionStationMapper  implements Mapper<RestrictionStationVO>{
		/**
		 * This method is used to map the result of query to RestrictionStationVO
		 * @param resultSet
		 * @return RestrictionStationVO
		 * @throws SQLException
		 *
		 */
		public RestrictionStationVO map(ResultSet resultSet) throws SQLException {
			RestrictionStationVO restrictionStationVO= new RestrictionStationVO();
			restrictionStationVO.setStation(resultSet.getString("STNCOD"));
			if(RestrictionStationVO.FLAG_YES.equals(resultSet.getString("ORGFLG"))){
				restrictionStationVO.setIsOrigin(true);
			}
			else{
				restrictionStationVO.setIsOrigin(false);
			}
			//To be reviewed
			if(RestrictionStationVO.FLAG_YES.equals(resultSet.getString("RSTFLG"))){
				restrictionStationVO.setIsRestricted(true);
			}
			else{
				restrictionStationVO.setIsRestricted(false);
			}
			return restrictionStationVO;
		}
	}
	/**
	 * This method will return all restricted segment Associated with the subproduct
	 * @author A-1885
	 * @param subProductVO
	 * @return restrictionSegmentVOs
	 * @throws SystemException
	 */
	private Collection<RestrictionSegmentVO> findRestrictionSegment(SubProductVO subProductVO)
	throws SystemException{
		Query query = getQueryManager().createNamedNativeQuery(PRODUCT_DEFAULTS_FIND_SUBPRODUCT_RESTRICTION_SEGMENT);
		query.setParameter(1,subProductVO.getCompanyCode());
		query.setParameter(2,subProductVO.getProductCode());
		query.setParameter(3,subProductVO.getSubProductCode());
		query.setParameter(4,subProductVO.getVersionNumber());
		return query.getResultList(new RestrictionSegmentMapper());
	}
	/**
	 * @author A-1883
	 * This mapper is used for findSubProductRestrictionSegment()
	 */
	class RestrictionSegmentMapper implements Mapper<RestrictionSegmentVO>{
		/**
		 * This method is used to map the result of query to RestrictionSegmentVO
		 * @param resultSet
		 * @throws SQLException
		 * @return RestrictionSegmentVO
		 */
		public RestrictionSegmentVO map(ResultSet resultSet) throws SQLException {
			RestrictionSegmentVO restrictionSegmentVO= new RestrictionSegmentVO();
			restrictionSegmentVO.setOrigin(resultSet.getString("ORGCOD"));
			restrictionSegmentVO.setDestination(resultSet.getString("DSTCOD"));
			//To be reviewed
			if(RestrictionSegmentVO.FLAG_YES.equals(resultSet.getString("RSTFLG"))){
				restrictionSegmentVO.setIsRestricted(true);
			}
			else{
				restrictionSegmentVO.setIsRestricted(false);
			}
			return restrictionSegmentVO;
		}
	}
	/**
	 * This method will return all restricted PaymentTerms Associated with the subproduct
	 * @param subProductVO
	 * @return restrictionPaymentTermsVOs
	 * @throws SystemException
	 */
	private Collection<RestrictionPaymentTermsVO> findRestrictionPaymentTerms(SubProductVO subProductVO)
	throws SystemException{
		Query query = getQueryManager().createNamedNativeQuery(
					PRODUCT_DEFAULTS_FIND_SUBPRODUCT_RESTRICTION_PAYMENTTERMS);
		query.setParameter(1,subProductVO.getCompanyCode());
		query.setParameter(2,subProductVO.getProductCode());
		query.setParameter(3,subProductVO.getSubProductCode());
		query.setParameter(4,subProductVO.getVersionNumber());
		return query.getResultList(new RestrictionPaymentTermsMapper());
	}
	/**
	 * @author A-1883
	 * This mapper is used for findSubProductRestrictionPaymentTerms()
	 */
	class RestrictionPaymentTermsMapper implements Mapper<RestrictionPaymentTermsVO>{
		/**
		 * This method is used to map the result of query to RestrictionPaymentTermsVO
		 * @param resultSet
		 * @return RestrictionPaymentTermsVO
		 * @throws SQLException
		 */
		public RestrictionPaymentTermsVO map(ResultSet resultSet) throws SQLException {
			RestrictionPaymentTermsVO restrictionPaymentTermsVO= new RestrictionPaymentTermsVO();
			restrictionPaymentTermsVO.setPaymentTerm(resultSet.getString("PMTTRM"));
			//To be reviewed
			if(RestrictionPaymentTermsVO.FLAG_YES.equals(resultSet.getString("RSTFLG"))){
				restrictionPaymentTermsVO.setIsRestricted(true);
			}
			else{
				restrictionPaymentTermsVO.setIsRestricted(false);
			}
			return restrictionPaymentTermsVO;
		}
	}
	/**
	 * This method will return all restricted CustomerGroups Associated with the subproduct
	 * @param subProductVO
	 * @return restrictionCustomerGroupVOs
	 * @throws SystemException
	 */
	private Collection<RestrictionCustomerGroupVO> findRestrictionCustomerGroup(SubProductVO subProductVO)
	throws SystemException{
		Query query = getQueryManager().createNamedNativeQuery(
						PRODUCT_DEFAULTS_FIND_SUBPRODUCT_RESTRICTION_CUSTOMERGP);
		query.setParameter(1,subProductVO.getCompanyCode());
		query.setParameter(2,subProductVO.getProductCode());
		query.setParameter(3,subProductVO.getSubProductCode());
		query.setParameter(4,subProductVO.getVersionNumber());
		return query.getResultList(new RestrictionCustomerGPMapper());
	}
	/**
	 * @author A-1883
	 * This mapper is used for findSubProductRestrictionCustomerGP()
	 */
	class RestrictionCustomerGPMapper implements Mapper<RestrictionCustomerGroupVO>{
		/**
		 * This method is used to map the result of query to RestrictionCustomerGroupVO
		 * @param resultSet
		 * @throws SQLException
		 * @return RestrictionCustomerGroupVO
		 */
		public RestrictionCustomerGroupVO map(ResultSet resultSet) throws SQLException {
			RestrictionCustomerGroupVO restrictionCustomerGroupVO= new RestrictionCustomerGroupVO();
			restrictionCustomerGroupVO.setCustomerGroup(resultSet.getString("CUSGRP"));
			if(RestrictionCustomerGroupVO.FLAG_YES.equals(resultSet.getString("RSTFLG"))){
				restrictionCustomerGroupVO.setIsRestricted(true);
			}
			else{
				restrictionCustomerGroupVO.setIsRestricted(false);
			}
			return restrictionCustomerGroupVO;
		}
	}
	/**
	 * This method will return all restricted Commodities Associated with the subproduct
	 * @param subProductVO
	 * @return restrictionCommodityVOs
	 * @throws SystemException
	 */
	private Collection<RestrictionCommodityVO> findRestrictionCommodity(SubProductVO subProductVO)
	throws SystemException{

		Query query = getQueryManager().createNamedNativeQuery(
					PRODUCT_DEFAULTS_FIND_SUBPRODUCT_RESTRICTION_COMMODITY);
		query.setParameter(1,subProductVO.getCompanyCode());
		query.setParameter(2,subProductVO.getProductCode());
		query.setParameter(3,subProductVO.getSubProductCode());
		query.setParameter(4,subProductVO.getVersionNumber());
		return query.getResultList(new RestrictionCommodityMapper());
	}
	/**
	 *
	 * @author A-1883
	 * This mapper is used for findSubProductRestrictionCommodity()
	 */
	class RestrictionCommodityMapper implements Mapper<RestrictionCommodityVO>{
		/**
		 * This method is used to map the result of query to RestrictionCommodityVO
		 * @param resultSet
		 * @throws SQLException
		 * @return RestrictionCommodityVO
		 */
		public RestrictionCommodityVO map(ResultSet resultSet) throws SQLException {
			RestrictionCommodityVO restrictionCommodityVO = new RestrictionCommodityVO();
			restrictionCommodityVO.setCommodity(resultSet.getString("CMDCOD"));
			if(RestrictionCommodityVO.FLAG_YES.equals(resultSet.getString("RSTFLG"))){
				restrictionCommodityVO.setIsRestricted(true);
			}
			else{
				restrictionCommodityVO.setIsRestricted(false);
			}
			return restrictionCommodityVO;
		}
	}
	/**
	 * This method will return details of subproduct of specified subproduct
	 * @param subProductVO
	 * @return SubProductVO
	 * @throws SystemException
	 */
	private SubProductVO getSubProductDetails(SubProductVO subProductVO)throws SystemException{

		Query query = getQueryManager().createNamedNativeQuery(PRODUCT_DEFAULTS_FIND_SUBPRODUCT_DETAILS);
		query.setParameter(1,subProductVO.getCompanyCode());
		query.setParameter(2,subProductVO.getProductCode());
		query.setParameter(3,subProductVO.getSubProductCode());
		query.setParameter(4,subProductVO.getVersionNumber());
		List<SubProductVO> list = query.getResultList(new SubProductDetailsMapper());
		return list.get(0);
	}
	/**
	 * @author A-1883
	 *Mapper used for findSubproductDetails()
	 *
	 */
	class SubProductDetailsMapper implements Mapper<SubProductVO> {
		/**
		 * This method is used to map the result of query to SubProductVO
		 * @param resultSet
		 * @throws SQLException
		 * @return SubProductVO
		 */
		public SubProductVO map(ResultSet resultSet) throws SQLException {
			SubProductVO subProductVO= new SubProductVO();
			subProductVO.setCompanyCode(resultSet.getString("CMPCOD"));
			subProductVO.setProductCode(resultSet.getString("PRDCOD"));
			subProductVO.setSubProductCode(resultSet.getString("SUBPRDCOD"));
			subProductVO.setVersionNumber(resultSet.getInt("VERNUM"));
			subProductVO.setStatus(resultSet.getString("SUBPRDSTA"));
			subProductVO.setProductTransportMode(resultSet.getString("TRAMOD"));
			subProductVO.setProductScc(resultSet.getString("SCCCOD"));
			subProductVO.setProductPriority(resultSet.getString("PRYCOD"));
			//To be reviewed chnage in "Y"
			if(SubProductVO.FLAG_YES.equals(resultSet.getString("MODFLG"))){
				subProductVO.setAlreadyModifed(true);
			}
			else{
				subProductVO.setAlreadyModifed(false);
			}
			subProductVO.setHandlingInfo(resultSet.getString("PRDHDLINF"));
			subProductVO.setRemarks(resultSet.getString("PRDRMK"));
			subProductVO.setMinimumWeight(resultSet.getDouble("MINWGTRES"));
			subProductVO.setMaximumWeight(resultSet.getDouble("MAXWGTRES"));
			subProductVO.setMinimumVolume(resultSet.getDouble("MINVOLRES"));
			subProductVO.setMaximumVolume(resultSet.getDouble("MAXVOLRES"));
			subProductVO.setAdditionalRestrictions(resultSet.getString("PRDADLRES"));
			// To be reviewed date issue
			subProductVO.setLastUpdateDate(new LocalDate(
					LocalDate.NO_STATION,Location.NONE,
					resultSet.getTimestamp("LSTUPDTIM")));
			subProductVO.setLastUpdateUser(resultSet.getString("LSTUPDUSR"));
			subProductVO.setMaximumVolumeDisplay(resultSet.getDouble("MAXDISVOLRES"));
			subProductVO.setMaximumWeightDisplay(resultSet.getDouble("MAXDISWGTRES"));
			subProductVO.setMinimumVolumeDisplay(resultSet.getDouble("MINDISVOLRES"));
			subProductVO.setMinimumWeightDisplay(resultSet.getDouble("MINDISWGTRES"));
			subProductVO.setVolumeUnit(resultSet.getString("DISVOLCODRES"));
			subProductVO.setWeightUnit(resultSet.getString("DISWGTCODRES"));
			//Added as part of ICRD-232462 begins
			subProductVO.setMaximumDimensionDisplay(resultSet.getDouble("MAXDISDIMRES"));
			subProductVO.setMinimumDimensionDisplay(resultSet.getDouble("MINDISDIMRES"));
			subProductVO.setDisplayDimensionCode(resultSet.getString("DISDIMCODRES"));
			//Added as part of ICRD-232462 ends
		return subProductVO;
		}

	}
	/**
	 * @author A-1883
	 * Mapper class for findProducts()
	 */

	class ProductMaterMapper implements Mapper<ProductLovVO> {
		/**
		 * This method is used to map the result of query to ProductVO
		 * @param resultSet
		 * @throws SQLException
		 * @return ProductVO
		 */
		public ProductLovVO map(ResultSet resultSet) throws SQLException {
			ProductLovVO productVO = new ProductLovVO();
			productVO.setProductCode(resultSet.getString("PRDCOD"));
			productVO.setProductName(resultSet.getString("PRDNAM"));
			productVO.setStartDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE,resultSet.getDate("PRDSTRDAT")));
			productVO.setEndDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE,resultSet.getDate("PRDENDDAT")));
			return productVO;
		}
	}

	/**
	 * @author A-1883
	 * Mapper class for findProducts()
	 */

	class ProductMapper implements Mapper<ProductVO> {
		/**
		 * This method is used to map the result of query to ProductVO
		 * @param resultSet
		 * @throws SQLException
		 * @return ProductVO
		 */
		public ProductVO map(ResultSet resultSet) throws SQLException {
			ProductVO productVO = new ProductVO();
			productVO.setProductCode(resultSet.getString("PRDCOD"));
			productVO.setProductName(resultSet.getString("PRDNAM"));
			productVO.setDescription(resultSet.getString("PRDDES"));
			productVO.setStatus(resultSet.getString("PRDSTA"));
			productVO.setCompanyCode(resultSet.getString("CMPCOD"));
			productVO.setStartDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE,resultSet.getDate("PRDSTRDAT")));
			productVO.setEndDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE,resultSet.getDate("PRDENDDAT")));
			if(ProductVO.FLAG_YES.equals(resultSet.getString("RATDEF"))){
				productVO.setIsRateDefined(true);
			}else{
				productVO.setIsRateDefined(false);
			}
			//Added for ICRD-352832
			if(ProductVO.FLAG_YES.equals(resultSet.getString("PRTDSP"))){
				productVO.setDisplayInPortal(true);
			}
			else{
				productVO.setDisplayInPortal(false);
			}
			//Added for ICRD-350746
			productVO.setPrdPriority(resultSet.getString("PRDPTY"));
			productVO.setLastUpdateUser(resultSet.getString("LSTUPDUSR"));
			productVO.setLastUpdateDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE,resultSet.getTimestamp("LSTUPDTIM")));
			productVO.setProductCategory(resultSet.getString("PRDCTG"));//Added for ICRD-166985 by A-5117
			return productVO;
		}
	}

	/**
	 * @author A-1883
	 * Mapper class for findSubProducts()
	 *
	 */
	class SubProductMapper implements Mapper<SubProductVO>{
		/**
		 * This method is used to map the result of query to SubProductVO
		 * @param resultSet
		 * @throws SQLException
		 * @return SubProductVO
		 */
		public SubProductVO map(ResultSet resultSet) throws SQLException{
			SubProductVO subProductVO=new SubProductVO();
			subProductVO.setProductTransportMode(resultSet.getString("TRAMOD"));
			subProductVO.setProductPriority(resultSet.getString("PRYCOD"));
			subProductVO.setProductScc(resultSet.getString("SCCCOD"));
			subProductVO.setStatus(resultSet.getString("SUBPRDSTA"));
			subProductVO.setProductCode(resultSet.getString("PRDCOD"));
			subProductVO.setCompanyCode(resultSet.getString("CMPCOD"));
			subProductVO.setSubProductCode(resultSet.getString("SUBPRDCOD"));
			subProductVO.setVersionNumber(resultSet.getInt("VERNUM"));
			subProductVO.setLastUpdateDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE,resultSet.getTimestamp("LSTUPDTIM")));
			subProductVO.setLastUpdateUser(resultSet.getString("LSTUPDUSR"));
			return subProductVO;
		}
	}
	/**
	 * ProductDetailsMapper is for getting product attributes
	 * @author A-1885
	 *
	 */
	private class ProductDetailsMapper implements Mapper<ProductVO>{
		/**
		 * Method for mapping Product
		 * @param resultSet
		 * @throws SQLException
		 * @return ProductVO
		 * */
		public ProductVO map(ResultSet resultSet) throws SQLException{
			ProductVO productVO =new ProductVO();
			String productCategory = resultSet.getString("PRDCTG");//Added for ICRD-166985 by A-5117
			productVO.setCompanyCode(resultSet.getString("CMPCOD"));
			productVO.setProductCode(resultSet.getString("PRDCOD"));
			productVO.setDescription(resultSet.getString("PRDDES"));
			productVO.setAdditionalRestrictions(resultSet.getString("PRDADLRES"));
			productVO.setRemarks(resultSet.getString("PRDRMK"));
			productVO.setDetailedDescription(resultSet.getString("PRDDETDES"));
			productVO.setDisplayVolumeCode(resultSet.getString("DISVOLCODRES"));
			productVO.setDisplayWeightCode(resultSet.getString("DISWGTCODRES"));
			productVO.setDisplayDimensionCode(resultSet.getString("DISDIMCODRES"));
			productVO.setMaximumVolume(resultSet.getDouble("MAXVOLRES"));
			productVO.setMinimumVolume(resultSet.getDouble("MINVOLRES"));
			productVO.setMaximumWeight(resultSet.getDouble("MAXWGTRES"));
			productVO.setMinimumWeight(resultSet.getDouble("MINWGTRES"));
			productVO.setMaximumVolumeDisplay(resultSet.getDouble("MAXDISVOLRES"));
			productVO.setMinimumVolumeDisplay(resultSet.getDouble("MINDISVOLRES"));
			productVO.setMaximumWeightDisplay(resultSet.getDouble("MAXDISWGTRES"));
			productVO.setMinimumWeightDisplay(resultSet.getDouble("MINDISWGTRES"));
			//Added as part of ICRD-232462
			productVO.setMinimumDimensionDisplay(resultSet.getDouble("MINDISDIMRES"));
			productVO.setMaximumDimensionDisplay(resultSet.getDouble("MAXDISDIMRES"));
			productVO.setHandlingInfo(resultSet.getString("PRDHDLINF"));
			productVO.setLastUpdateUser(resultSet.getString("LSTUPDUSR"));
			productVO.setProductName(resultSet.getString("PRDNAM"));
			productVO.setStatus(resultSet.getString("PRDSTA"));
			//Added as part of CR ICRD-237928 by A-8154
			productVO.setOverrideCapacity(resultSet.getString("ISSOVRCAP"));
			if(ProductVO.FLAG_YES.equals(resultSet.getString("RATDEF"))){
				productVO.setIsRateDefined(true);
			}
			else{
				productVO.setIsRateDefined(false);
			}
			productVO.setProductCategory(productCategory);
			if(productCategory != null && productCategory.length() > 0){//Added for ICRD-166985 by A-5117
				String productcodes [] = productCategory.split(",");
				if(productcodes != null &&productcodes.length > 0){
					for(String category: productcodes){
						if(COOL.equals(category)){
							productVO.setCoolProduct(true);
							break;
						}
					}
				}
			}else{
				productVO.setCoolProduct(false);
			}
			//Added for ICRD-352832
			if(ProductVO.FLAG_YES.equals(resultSet.getString("PRTDSP"))){
				productVO.setDisplayInPortal(true);
			}
			else{
				productVO.setDisplayInPortal(false);
			}
			//Added for ICRD-350746
			productVO.setPrdPriority(resultSet.getString("PRDPTY"));
			/*if(ProductVO.FLAG_YES.equals(resultSet.getString("COLIND"))){
				productVO.setCoolProduct(true);
			}
			else{
				productVO.setCoolProduct(false);
			}*/
			if(ProductVO.FLAG_YES.equals(resultSet.getString("BKGIND"))){
				productVO.setBookingMandatory(true);
			}
			else{
				productVO.setBookingMandatory(false);
			}
			productVO.setProactiveMilestoneEnabled(resultSet.getString("MILMGTFLG"));
			productVO.setStartDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE,resultSet.getDate("PRDSTRDAT")));
			productVO.setEndDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE,resultSet.getDate("PRDENDDAT")));
			productVO.setLastUpdateDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE,resultSet.getTimestamp("LSTUPDTIM")));

			//@author A-1944
			//to set the doctype and docsubtype
			productVO.setDocumentType(resultSet.getString("DOCTYP"));
			productVO.setDocumentSubType(resultSet.getString("DOCSUBTYP"));
			//@author A-1944 ENDS
			return productVO;
		}
	}
	/**
	 * ProductSCCMapper is used for getting ProductSCC details
	 * @author A-1885
	 */
	private class ProductSCCMapper implements Mapper<ProductSCCVO>{
		/**
		 * Method for mapping ProductSCC
		 * @param resultSet
		 * @throws SQLException
		 * @return ProductSCCVO
		 * */
		public ProductSCCVO map(ResultSet resultSet) throws SQLException{
			ProductSCCVO productSccVO=new ProductSCCVO();
			productSccVO.setScc(resultSet.getString("PRDSCC"));
			return productSccVO;
		}
	}
	/**
	 * ProductPrioriyMapper is used for getting productPriorityDetails
	 * @author A-1885
	 *
	 */
	private class ProductPriorityMapper implements Mapper<ProductPriorityVO>{
		/**
		 * Method for mapping ProductPriority
		 * @param resultSet
		 * @return ProductPriorityVO
		 * @throws SQLException
		 */
		public ProductPriorityVO map(ResultSet resultSet) throws SQLException{
			ProductPriorityVO productPriorityVO=new ProductPriorityVO();
			productPriorityVO.setPriority(resultSet.getString("PRDPRY"));
			return productPriorityVO;
		}
	}
	/**
	 * ProudctServiceMapper is for getting ProductServiceDetails
	 * @author A-1885
	 *
	 */
	private class ProductServiceMapper implements Mapper<ProductServiceVO>{
		/**
		 * Method for mapping ProductService
		 * @param resultSet
		 * @throws SQLException
		 * @return ProductServiceVO
		 */
		public ProductServiceVO map(ResultSet resultSet) throws SQLException{
			ProductServiceVO productServiceVO=new ProductServiceVO();
			productServiceVO.setServiceCode(resultSet.getString("SERCODE"));
			productServiceVO.setServiceDescription(resultSet.getString("SERNAME"));
			if(("Y").equals(resultSet.getString("TRANSPLAN"))){
				productServiceVO.setTransportationPlanExist(true);
			}
			else
			{
				productServiceVO.setTransportationPlanExist(false);
			}
		return productServiceVO;
		}
	}
	/**
	 * ProductTransportModeMapper is for getting ProductTransportMode
	 * @author A-1885
	 *
	 */
	private class ProductTransportModeMapper implements Mapper<ProductTransportModeVO>{
		/**
		 * Method for mapping TransportMode
		 * @param resultSet
		 * @throws SQLException
		 * @return ProductTransportModeVO
		 */
		public ProductTransportModeVO map(ResultSet resultSet) throws SQLException{
			ProductTransportModeVO productTransprotModeVO=new ProductTransportModeVO();
			productTransprotModeVO.setTransportMode(resultSet.getString("PRDTRAMOD"));
			return productTransprotModeVO;
		}
	}
	/**
	 * ProductCommodityMapper is for getting ProductCommodity Details
	 * @author A-1885
	 *
	 */
	private class ProductCommodityMapper implements Mapper<RestrictionCommodityVO>{
		/**
		 * Method for mapping restrictionCommodity
		 * @param resultSet
		 * @throws SQLException
		 * @return RestrictionCommodityVO
		 */
		public RestrictionCommodityVO map(ResultSet resultSet) throws SQLException{
			RestrictionCommodityVO restrictionCommodityVO=new RestrictionCommodityVO();
			restrictionCommodityVO.setCommodity(resultSet.getString("CMDCOD"));
			if(RestrictionCommodityVO.FLAG_YES.equals(resultSet.getString("RSTFLG"))){
				restrictionCommodityVO.setIsRestricted(true);
			}else{
				restrictionCommodityVO.setIsRestricted(false);
			}
			return restrictionCommodityVO;
		}
	}
	/**
	 * ProductCustomerGroupMapper is for getting ProductCustomerGroup details
	 * @author A-1885
	 *
	 */
	private class ProductCustomerGroupMapper implements Mapper<RestrictionCustomerGroupVO>{
		/**
		 * Method for mapping restrictionCustomerGroup
		 * @param resultSet
		 * @throws SQLException
		 * @return RestrictionCustomerGroupVO
		 */
		public RestrictionCustomerGroupVO map(ResultSet resultSet) throws SQLException{
			RestrictionCustomerGroupVO restrictionCustomerGroupVO=new RestrictionCustomerGroupVO();
			restrictionCustomerGroupVO.setCustomerGroup(resultSet.getString("CUSGRP"));
			if(RestrictionCustomerGroupVO.FLAG_YES.equals(resultSet.getString("RSTFLG"))){
				restrictionCustomerGroupVO.setIsRestricted(true);
			}else{
				restrictionCustomerGroupVO.setIsRestricted(false);
			}

			return restrictionCustomerGroupVO;
		}
	}

	/**
	 * ProductPaymentTermsMapper is for getting restrictionPaymentTerms details
	 * @author A-1885
	 *
	 */
	private class ProductPaymentTermsMapper implements Mapper<RestrictionPaymentTermsVO>{
		/**
		 * Method for Mapping restrictionPaymentTerms
		 * @param resultSet
		 * @throws SQLException
		 * @return RestrictionPaymentTermsVO
		 */
		public RestrictionPaymentTermsVO map(ResultSet resultSet) throws SQLException{
			RestrictionPaymentTermsVO restrictionPaymentTermsVO=new RestrictionPaymentTermsVO();
			restrictionPaymentTermsVO.setPaymentTerm(resultSet.getString("PMTTRM"));
			if(RestrictionPaymentTermsVO.FLAG_YES.equals(resultSet.getString("RSTFLG"))){
				restrictionPaymentTermsVO.setIsRestricted(true);
			}else{
				restrictionPaymentTermsVO.setIsRestricted(false);
			}

			return restrictionPaymentTermsVO;
		}
	}
	/**
	 * ProductSegmentMapper is for getting productSegment details
	 * @author A-1885
	 *
	 */
	private class ProductSegmentMapper implements Mapper<RestrictionSegmentVO>{
		/**
		 * Method for mapping restrictionPayment
		 * @param resultSet
		 * @throws SQLException
		 * @return RestrictionSegmentVO
		 */
		public RestrictionSegmentVO map(ResultSet resultSet) throws SQLException{
			RestrictionSegmentVO restrictionSegmentVO=new RestrictionSegmentVO();
			restrictionSegmentVO.setOrigin(resultSet.getString("ORGCOD"));
			restrictionSegmentVO.setDestination(resultSet.getString("DSTCOD"));
			if(RestrictionSegmentVO.FLAG_YES.equals(resultSet.getString("RSTFLG"))){
				restrictionSegmentVO.setIsRestricted(true);
			}else{
				restrictionSegmentVO.setIsRestricted(false);
			}
			return restrictionSegmentVO;
		}
	}
	/**
	 * ProductStationMapper is for getting productStation details
	 * @author A-1885
	 *
	 */
	private class ProductStationMapper implements Mapper<RestrictionStationVO>{
		/**
		 * Method for mapping restrictionStation
		 * @param resultSet
		 * @throws SQLException
		 * @return RestrictionStationVO
		 */
		public RestrictionStationVO map(ResultSet resultSet) throws SQLException{
			RestrictionStationVO restrictionStationVO=new RestrictionStationVO();
			restrictionStationVO.setStation(resultSet.getString("STNCOD"));
			if(RestrictionStationVO.FLAG_YES.equals(resultSet.getString("ORGFLG"))){
				restrictionStationVO.setIsOrigin(true);
			}else{
				restrictionStationVO.setIsOrigin(false);
			}
			if(RestrictionStationVO.FLAG_YES.equals(resultSet.getString("RSTFLG"))){
				restrictionStationVO.setIsRestricted(true);
			}else{
				restrictionStationVO.setIsRestricted(false);
			}

			return restrictionStationVO;
		}
	}
	/**
	 * ProductEventMapper is for getting ProductEvent Details
	 * @author A-1885
	 *
	 */
	private class ProductEventMapper implements Mapper<ProductEventVO>{
		/**
		 * Method for mapping ProductEvent
		 * @param resultSet
		 * @throws SQLException
		 * @return ProductEventVO
		 */
		public ProductEventVO map(ResultSet resultSet) throws SQLException{
			ProductEventVO productEventVO=new ProductEventVO();
			productEventVO.setEventCode(resultSet.getString("EVTCOD"));
			productEventVO.setEventType(resultSet.getString("EVTTYP"));
			productEventVO.setAlertTime(resultSet.getDouble("EVTALTTIM"));
			productEventVO.setChaserTime(resultSet.getDouble("EVTCHRTIM"));
			productEventVO.setDuration(resultSet.getInt("EVTDUR"));
			if(ProductEventVO.FLAG_YES.equals(resultSet.getString("EXTFLG"))){
				productEventVO.setExternal(true);
			}
			else
			{
				productEventVO.setExternal(false);
			}
			if(ProductEventVO.FLAG_YES.equals(resultSet.getString("INTFLG"))){
				productEventVO.setInternal(true);
			}
			else{
				productEventVO.setInternal(false);
			}
			productEventVO.setIsExport(resultSet.getString("EXPFLG"));
			productEventVO.setMaximumTime(resultSet.getDouble("MAXTIM"));
			productEventVO.setMinimumTime(resultSet.getDouble("MINTIM"));
			productEventVO.setChaserFrequency(resultSet.getDouble("EVTCHRFRQ"));
			productEventVO.setMaxNoOfChasers(resultSet.getInt("EVTMAXCHR"));
			productEventVO.setTransit((ProductEventVO.FLAG_YES.
					equals(resultSet.getString("TRNFLG"))) ? true :false);
			return productEventVO;
		}
	}

	/**
	 * Method for finding Product Scc's
	 * @author A-1885
	 * @param companyCode
	 * @param productCode
	 * @return List<ProductSCCVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	private List<ProductSCCVO> findProductScc(String companyCode, String productCode)
							throws SystemException,PersistenceException{
		List<ProductSCCVO> productSccList=new ArrayList<ProductSCCVO>();
		Query qry = getQueryManager().createNamedNativeQuery(
				PRD_DEFAULTS_FIND_PRODUCT_SCC_DETAILS);
		qry.setParameter(1, companyCode);
		qry.setParameter(2, productCode);
		return qry.getResultList(new ProductSCCMapper());
	}

	/**
	 * Method for finding ProductPriority
	 * @author A-1885
	 * @param companyCode
	 * @param productCode
	 * @return List<ProductPriorityVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	private List<ProductPriorityVO> findProductPriority(String companyCode, String productCode)
	throws SystemException,PersistenceException{
		List<ProductPriorityVO> productPriorityList=new ArrayList<ProductPriorityVO>();
		Query qry = getQueryManager().createNamedNativeQuery(
				PRD_DEFAULTS_FIND_PRODUCT_PRIORITY_DETAILS);
		qry.setParameter(1, companyCode);
		qry.setParameter(2, productCode);
		return qry.getResultList(new ProductPriorityMapper());
	}
	/**
	 * Method for getting ProductServices
	 * @author A-1885
	 * @param companyCode
	 * @param productCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	private List<ProductServiceVO> findProductService(String companyCode, String productCode)
	throws SystemException,PersistenceException{
		List<ProductServiceVO> productServiceList=new ArrayList<ProductServiceVO>();
		Query qry = getQueryManager().createNamedNativeQuery(
				PRD_DEFAULTS_FIND_PRODUCT_SERVICE_DETAILS);
		qry.setParameter(1, companyCode);
		qry.setParameter(2, productCode);
		return qry.getResultList(new ProductServiceMapper());
	}
	/**
	 * Method for getting ProductTransportMode
	 * @author A-1885
	 * @param companyCode
	 * @param productCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	private List<ProductTransportModeVO> findProductTransportMode(String companyCode, String productCode)
	throws SystemException,PersistenceException{
		List<ProductTransportModeVO> productTransportModeList=new ArrayList<ProductTransportModeVO>();
		Query qry = getQueryManager().createNamedNativeQuery(
				PRD_DEFAULTS_FIND_PRODUCT_TRANSPORTMODE_DETAILS);
		qry.setParameter(1, companyCode);
		qry.setParameter(2, productCode);
		return qry.getResultList(new ProductTransportModeMapper());
	}

	/**
	 * Method for getting ProductEvent
	 * @author A-1885
	 * @param companyCode
	 * @param productCode
	 * @return List<ProductEventVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public List<ProductEventVO> findProductEvent(String companyCode, String productCode)
	throws SystemException,PersistenceException{
		List<ProductEventVO> productEventList=new ArrayList<ProductEventVO>();
		Query qry = getQueryManager().createNamedNativeQuery(
				PRD_DEFAULTS_FIND_PRODUCT_EVENT_DETAILS);
		qry.setParameter(1, companyCode);
		qry.setParameter(2, productCode);
		productEventList= qry.getResultList(new ProductEventMapper());
		log.log(Log.FINE, "---B4 GOING TO ARRANGE IN ORDER----",
				productEventList);
		return arrangeMilestones(productEventList);
	}
	/**
	 * @author A-1885
	 * @param events
	 * @return resultSorted
	 */
	private List<ProductEventVO> arrangeMilestones(List<ProductEventVO> events){
		Collection<ProductEventVO> resultExport = null;
		Collection<ProductEventVO> resultImport = null;
		Collection<ProductEventVO> resultSorted = null;
		if(events!=null && events.size()>0){
			for(ProductEventVO eventVo : events){
				if("Export".equals(eventVo.getEventType())){
					if(resultExport==null){
						resultExport = new ArrayList<ProductEventVO>();
					}
					resultExport.add(eventVo);
				}
				if(("Import".equals(eventVo.getEventType()))){
					if(resultImport==null){
						resultImport = new ArrayList<ProductEventVO>();
					}
					resultImport.add(eventVo);
				}
			}
		}
		log.log(Log.FINE, "----B4 GOING FOR SORTING EXPORT EVENTS:",
				resultExport);
		log.log(Log.FINE, "----B4 GOING FOR SORTING IMPORT EVENTS:",
				resultImport);
		if(resultExport!=null && resultExport.size()>0){
			resultSorted = new ArrayList<ProductEventVO>();
			resultSorted.addAll(sortExportEvents(
					(ArrayList<ProductEventVO>)resultExport));
		}
		if(resultImport!=null && resultImport.size()>0){
			if(resultSorted==null){
				resultSorted = new ArrayList<ProductEventVO>();
			}
			resultSorted.addAll(sortImportEvents
					((ArrayList<ProductEventVO>)resultImport));
		}
		log.log(Log.FINE, "----THE SORTED EVENTS -----:", resultSorted);
		return (ArrayList<ProductEventVO>)resultSorted;
	}
	/**
	 * @author A-1885
	 * @param exportEvents
	 * @return exportEvents
	 */
	private List<ProductEventVO> sortExportEvents(List<ProductEventVO> exportEvents){
		if(exportEvents!=null && exportEvents.size()>0){
			Collections.sort((List<ProductEventVO>)
					exportEvents,
					new DataComparatorForExportEvents());
		}
		return exportEvents;
	}

	/**
	 * @author A-1885
	 * @param importEvents
	 * @return importEvents
	 */
	private List<ProductEventVO> sortImportEvents(List<ProductEventVO> importEvents){
		if(importEvents!=null && importEvents.size()>0){
			Collections.sort((List<ProductEventVO>)
					importEvents,
					new DataComparatorForImportEvents());
		}
		return importEvents;
	}
	/**
    *
    * @author A-1885
    *
    */
   private class DataComparatorForExportEvents implements Comparator<Object>{
	  /**
	   * Method for comparing two date objects
	   * @author A-1885
	   * @param o1
	   * @param o2
	   * @return result
	   */
	public int compare (Object o1, Object o2){
		log.log(Log.FINE,"--------******INSIDE  EXPORT EVENT COMPARATOR******-----");
		ProductEventVO cal1 = (ProductEventVO)o1;
		ProductEventVO cal2 = (ProductEventVO)o2;
		int result = 0;
			if((0-cal1.getMaximumTime())>(0-cal2.getMaximumTime())){
				return 1;
			}
			else if((0-cal1.getMaximumTime())<(0-cal2.getMaximumTime())){
				return -1;
			}
			return result;
		}
   }

	/**
   *
   * @author A-1885
   *
   */
  private class DataComparatorForImportEvents implements Comparator<Object>{
	  /**
	   * Method for comparing two date objects
	   * @author A-1885
	   * @param o1
	   * @param o2
	   * @return result	   */
	public int compare (Object o1, Object o2){
		log.log(Log.FINE,"--------******INSIDE IMPORT EVENT COMPARATOR******-----");
		ProductEventVO cal1 = (ProductEventVO)o1;
		ProductEventVO cal2 = (ProductEventVO)o2;
		int result = 0;
			if((0.1+cal1.getMaximumTime())<(0.1+cal2.getMaximumTime())){
				return 1;
			}
			else if((0.1+cal1.getMaximumTime())>(0.1+cal2.getMaximumTime())){
				return -1;
			}
			return result;
		}
  }
	/**
	 * Method for getting ProductRestrictedCommodity
	 * @author A-1885
	 * @param companyCode
	 * @param productCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	private List<RestrictionCommodityVO> findProductCommodtiy(String companyCode, String productCode)
	throws SystemException,PersistenceException{
		List<RestrictionCommodityVO> restrictionCommodityList=new ArrayList<RestrictionCommodityVO>();
		Query qry = getQueryManager().createNamedNativeQuery(
				PRD_DEFAULTS_FIND_PRODUCT_RESTRICTION_COMMODITY);
		qry.setParameter(1, companyCode);
		qry.setParameter(2, productCode);
		return qry.getResultList(new ProductCommodityMapper());
	}
	/**
	 * Method for getting ProductRestrictedCustomerGroup
	 * @author A-1885
	 * @param companyCode
	 * @param productCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	private List<RestrictionCustomerGroupVO> findProductCustomerGroup(String companyCode, String productCode)
	throws SystemException,PersistenceException{
		List<RestrictionCustomerGroupVO> restrictionCustomerGroupList=new ArrayList<RestrictionCustomerGroupVO>();
		Query qry = getQueryManager().createNamedNativeQuery(
				PRD_DEFAULTS_FIND_PRODUCT_RESTRICTION_CUSTOMER);
		qry.setParameter(1, companyCode);
		qry.setParameter(2, productCode);
		return qry.getResultList(new ProductCustomerGroupMapper());
	}

	/**
	 * Method for getting ProductRestrictedPaymentTerms
	 * @author A-1885
	 * @param companyCode
	 * @param productCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	private List<RestrictionPaymentTermsVO> findProductPaymentTerms(String companyCode, String productCode)
	throws SystemException,PersistenceException{
		List<RestrictionPaymentTermsVO> restrictionPaymentTermsList=new ArrayList<RestrictionPaymentTermsVO>();
		Query qry = getQueryManager().createNamedNativeQuery(
				PRD_DEFAULTS_FIND_PRODUCT_RESTRICTION_PAYMENT);
		qry.setParameter(1, companyCode);
		qry.setParameter(2, productCode);
		return qry.getResultList(new ProductPaymentTermsMapper());
	}
	/**
	 * Method for getting ProductRestricted Segments
	 * @author A-1885
	 * @param companyCode
	 * @param productCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	private List<RestrictionSegmentVO> findProductSegment(String companyCode, String productCode)
	throws SystemException,PersistenceException{
		List<RestrictionSegmentVO> restrictionSegmentList=new ArrayList<RestrictionSegmentVO>();
		Query qry = getQueryManager().createNamedNativeQuery(
				PRD_DEFAULTS_FIND_PRODUCT_RESTRICTION_SEGMENT);
		qry.setParameter(1, companyCode);
		qry.setParameter(2, productCode);
		return qry.getResultList(new ProductSegmentMapper());
	}
	/**
	 * Method for getting ProductRestricted Stations
	 * @author A-1885
	 * @param companyCode
	 * @param productCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	private List<RestrictionStationVO> findProductStation(String companyCode, String productCode)
	throws SystemException,PersistenceException{
		List<RestrictionStationVO> restrictionStationList=new ArrayList<RestrictionStationVO>();
		Query qry = getQueryManager().createNamedNativeQuery(
				PRD_DEFAULTS_FIND_PRODUCT_RESTRICTION_STATION);
		qry.setParameter(1, companyCode);
		qry.setParameter(2, productCode);
		return qry.getResultList(new ProductStationMapper());
	}

	/**
	 * Method for getting ProductLov
	 * @author A-1885
	 * @param productLovFilterVO
	 * @param displayPage
	 * @return Page<ProductLovVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<ProductLovVO> findProductLov(ProductLovFilterVO productLovFilterVO, int displayPage)
			throws SystemException, PersistenceException {
		// modified the method by A-5103 for ICRD-32647
		StringBuffer masterQuery = new StringBuffer();
		masterQuery.append(PRODUCTS_DEFAULTS_ROWNUM_RANK_QUERY);
		String queryString = getQueryManager().getNamedNativeQueryString(PRD_DEFAULTS_FIND_PRODUCT_LOV);
		masterQuery.append(queryString);
		PageableNativeQuery<ProductLovVO> pgNativeQuery = new PageableNativeQuery<ProductLovVO>(productLovFilterVO.getDefaultPageSize(),productLovFilterVO.getDefaultPageSize(), 
				masterQuery.toString(), new ProductLovPageAwareMapper());
		int index = 0;
		String companyCode = productLovFilterVO.getCompanyCode();
		String productName = productLovFilterVO.getProductName();
		pgNativeQuery.setParameter(++index, companyCode);
		
		if (productName != null && productName.trim().length() != 0) {
			productName = productName.replace('*', '%');
			
			if (productName.contains(",")) {
				String[] productNames = productName.split(",");
				StringBuilder qstrA = new StringBuilder().append("AND UPPER(PRDNAM) IN ");
				String productQuery = getWhereClause(productNames);
				qstrA.append(productQuery).append(")");
				pgNativeQuery.append(qstrA.toString());
			} else {
				pgNativeQuery.append(" AND  UPPER(PRDNAM) like ? ");
				pgNativeQuery.setParameter(++index, productName.toUpperCase());
			}
		}
		
		if (productLovFilterVO.getStartDate() != null) {
			pgNativeQuery.append(" AND ? >= TRUNC(PRDSTRDAT) ");
			pgNativeQuery.append(" AND ? <= TRUNC(PRDENDDAT) ");
			pgNativeQuery.setParameter(++index, productLovFilterVO.getStartDate());
			pgNativeQuery.setParameter(++index, productLovFilterVO.getStartDate());
		}
		
		if (productLovFilterVO.getEndDate() != null) {
			pgNativeQuery.append(" AND ? <= TRUNC(PRDENDDAT) ");
			pgNativeQuery.append(" AND ? >= TRUNC(PRDSTRDAT) ");
			pgNativeQuery.setParameter(++index, productLovFilterVO.getEndDate());
			pgNativeQuery.setParameter(++index, productLovFilterVO.getEndDate());
		}
		
		if (productLovFilterVO.getisProductNotExpired()) {
			
			if (productLovFilterVO.getCurrentDate() != null) {
				pgNativeQuery.append(" AND TRUNC(PRDENDDAT) >= ? ");
				pgNativeQuery.setParameter(++index, productLovFilterVO.getCurrentDate());
			}
		}
		
		if (productLovFilterVO.getBkgDate() != null) {
			pgNativeQuery.append(" AND ? >= TRUNC(PRDSTRDAT) ");
			pgNativeQuery.append(" AND ? <= TRUNC(PRDENDDAT)");
			pgNativeQuery.setParameter(++index, productLovFilterVO.getBkgDate());
			pgNativeQuery.setParameter(++index, productLovFilterVO.getBkgDate());
		}
		
		if (productLovFilterVO.isActive()) {
			pgNativeQuery.append(" AND PRDSTA = 'A'");
		}
		//Added by A-8146 for ICRD-254587 starts 
		if(productLovFilterVO.getSccCode()!=null && !"".equalsIgnoreCase(productLovFilterVO.getSccCode())){
			List<String> sccCodes=Arrays.asList(productLovFilterVO.getSccCode().split(","));
			pgNativeQuery.append("AND EXISTS ("
					+"select PRDSCC from PRDSCC PRD Where" 
					+" PRD.CMPCOD=MST.CMPCOD"
					+" AND PRD.PRDCOD=MST.PRDCOD"
					+" AND PRD.PRDSCC IN "+getWhereClause(sccCodes)+")");
		}
		//Added by A-8146 for ICRD-254587 ends
		if (Objects.nonNull(productLovFilterVO.getCustomerCode()) && !productLovFilterVO.getCustomerCode().isEmpty()) {
			pgNativeQuery.append("and coalesce(( select count(*) from SHRCUSPRF where CUSCOD = ? and PRFCOD = 'PRODUCTS'),0)")
					.append(" = coalesce(( select 1 from SHRCUSPRF PRF where PRF.CMPCOD = MST.CMPCOD and PRF.CUSCOD = ? and PRF.PRFCOD = 'PRODUCTS' and INSTR (upper (PRF.PRFVAL), MST.PRDNAM) > 0 ), 0)");
			pgNativeQuery.setParameter(++index, productLovFilterVO.getCustomerCode());
			pgNativeQuery.setParameter(++index, productLovFilterVO.getCustomerCode());

		}
		pgNativeQuery.append(" ORDER BY PRDNAM");
		pgNativeQuery.append(PRODUCTS_DEFAULTS_SUFFIX_QUERY);
		return pgNativeQuery.getPage(displayPage);
	}

	/**
	 * Gets the where clause.
	 *
	 * @param sccCodes the scc codes
	 * @return the where clause
	 */
	private String getWhereClause(String[] sccCodes) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("('");

		for (String code : sccCodes) {
			buffer.append(code).append("','");
		}
		int len = buffer.length();
		return buffer.toString().substring(0, len - 3).trim() + "'";
	}
	
	/**
     * added by A-1648 for capacity-booking submodule
	 * This method finds the services for a product.
     * @param companyCode
     * @param productCode
     * @param transportationMode
     * @param productPriority
     * @param primaryScc
     * @return List<ProductServiceVO>
     * @throws PersistenceException
     * @throws SystemException
     */
	public List<ProductServiceVO> findProductServices
	(String companyCode, String productCode, String transportationMode,
			String productPriority, String primaryScc)
		throws PersistenceException, SystemException {
		int index=0;
		Query query = getQueryManager().createNamedNativeQuery(PRODUCTS_DEFAULTS_FINDPRODUCTSERVICES);
		query.append(" AND PRD.PRDSTA = 'A'  ");
		query.append(" AND MST.SUBPRDSTA = 'A'  ");
		query.setParameter(++index,companyCode);
		query.setParameter(++index,productCode);
		query.setParameter(++index,transportationMode);
		query.setParameter(++index,productPriority);
		query.setParameter(++index,primaryScc);
		//for subquery
		query.setParameter(++index,companyCode);
		query.setParameter(++index,productCode);
		query.setParameter(++index,transportationMode);
		query.setParameter(++index,productPriority);
		query.setParameter(++index,primaryScc);

		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		LocalDate currentDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
		query.setParameter(++index,Integer.parseInt(currentDate.toStringFormat("yyyyMMdd").substring(0, 8)));

		return query.getResultList(new Mapper<ProductServiceVO>() {
				public ProductServiceVO map(ResultSet rs) throws SQLException {
					ProductServiceVO productServiceVo = new ProductServiceVO();
					productServiceVo.setProductCode(rs.getString("PRDCOD"));
					productServiceVo.setServiceCode(rs.getString("SRVCOD"));
					productServiceVo.setServiceName(rs.getString("SRVNAM"));
					productServiceVo.setServiceDescription(rs.getString("SRVDES"));
					return productServiceVo;
				}
			});
	}

	/**
	    * added by A-1648 for capacity-booking submodule
		* This method validates a subproduct.
	    * @param companyCode
	    * @param productCode
	    * @param transportationMode
	    * @param productPriority
	    * @param primaryScc
	    * @return SubProductVO
	    * @throws PersistenceException
	    * @throws SystemException
	    */
	   public SubProductVO validateSubProduct
	   		(String companyCode, String productCode, String transportationMode,
				String productPriority, String primaryScc)
	   				throws PersistenceException, SystemException {
		   Query query = getQueryManager().createNamedNativeQuery(PRODUCTS_DEFAULTS_VALIDATESUBPRODUCT);
		   query.append(" AND MST.PRDSTA = 'A'  ");
		   query.append(" AND SUB.SUBPRDSTA = 'A'  ");
		  
		   // Added by @author A-3351 for  bug(92603) fix
		   query.append(" ORDER BY VERNUM DESC ");
		   
		   int index = 0;
		   query.setParameter(++index,companyCode);
		   query.setParameter(++index,productCode);
		   query.setParameter(++index,transportationMode);
		   query.setParameter(++index,productPriority);
		   query.setParameter(++index,primaryScc);

		   LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		   LocalDate currentDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
		   query.setParameter(++index,Integer.parseInt(currentDate.toStringFormat("yyyyMMdd").substring(0, 8)));

		   return query.getSingleResult(new Mapper<SubProductVO>() {
			   public SubProductVO map(ResultSet rs) throws SQLException {
				   SubProductVO subProductVo = new SubProductVO();
				   subProductVo.setCompanyCode(rs.getString("CMPCOD"));
				   subProductVo.setProductCode(rs.getString("PRDCOD"));
				   subProductVo.setSubProductCode(rs.getString("SUBPRDCOD"));
				   subProductVo.setVersionNumber(rs.getInt("VERNUM"));
				   return subProductVo;
			   }
		   	});
	   }
	   /**
	    * @author A-1885
	    * Method for checking for any icon present for the product
	    * @param companyCode
	    * @param productCode
	    * @return
	    * @throws SystemException
	    */
	   private boolean findProductIcon(String companyCode,String productCode)
	   throws SystemException{
		   Query query = getQueryManager().createNamedNativeQuery(PRODUCTS_DEFAULTS_FINDPRODUCTICON);
		   query.setParameter(1,companyCode);
		   query.setParameter(2,productCode);
		   String prdCod = query.getSingleResult(new ProductIconMapper());
		   boolean isProductIconPresent = false;
		   if(prdCod!=null){
			   isProductIconPresent = true;
		   }
		   return isProductIconPresent;
	   }
	   /**
	    *
	    * @author A-1885
	    *
	    */
	   class ProductIconMapper implements Mapper<String>{
		   public String map(ResultSet resultSet) throws SQLException{
			   return resultSet.getString("PRDCOD");
		   }
	   }
	   /**
	    * @author A-1883
	    * @param productFeedbackFilterVO
	    * @param displayPage
	    * @return Page<ProductFeedbackVO>
	    * @throws SystemException
	    * @throws PersistenceException
	    */
	 public Page<ProductFeedbackVO> listProductFeedback(
				ProductFeedbackFilterVO productFeedbackFilterVO,int displayPage)
				throws SystemException,PersistenceException{
		 		log.entering("ProductDefaultsSqlDAO","listProductFeedback()");			 	
			 	String query = getQueryManager().getNamedNativeQueryString(PRODUCTS_DEFAULTS_LIST_PRODUCT_FEEDBACK);
			 	
			 	StringBuilder rankQuery = new StringBuilder().append(PRODUCTS_DEFAULTS_DENSE_RANK_QUERY);
				rankQuery.append("RESULT_TABLE.CMPCOD,RESULT_TABLE.FEDBCKIDR,RESULT_TABLE.PRDCOD )AS RANK FROM(");
				rankQuery.append(query);
				PageableNativeQuery<ProductFeedbackVO> pageableQuery = new PageableNativeQuery<ProductFeedbackVO>(productFeedbackFilterVO.getTotalRecords(),rankQuery.toString(), new ProductFeedbackMapper());
				pageableQuery.setParameter(1, productFeedbackFilterVO.getCompanyCode());
				int index = 1;
				 String productName = productFeedbackFilterVO.getProductCode();
				if (productName != null && productName.trim().length()!=0) {
					pageableQuery.append(" AND UPPER(prdmst.PRDNAM) = ? ");
					pageableQuery.setParameter(++index,productName.toUpperCase());
				}
				if(productFeedbackFilterVO.getStartDate()!=null){
					pageableQuery.append(" AND TRUNC(fedbck.FEDBCKDAT) >= ? ");
					pageableQuery.setParameter(++index,productFeedbackFilterVO.getStartDate());
				}
				if(productFeedbackFilterVO.getEndDate()!=null){
					pageableQuery.append(" AND TRUNC(fedbck.FEDBCKDAT) <= ? ");
					pageableQuery.setParameter(++index,productFeedbackFilterVO.getEndDate());
				}
				
				/*log.exiting("ProductDefaultsSqlDAO","listProductFeedback()");
				return pageableQuery.getPage(displayPage);*/
				
				pageableQuery.append(PRODUCTS_DEFAULTS_SUFFIX_QUERY);
				log.log(Log.INFO, "query", pageableQuery);
				return pageableQuery.getPage(displayPage);//Added by A-5201 as part for the ICRD-22065
			
	 }
	 /**
		 * @author A-1883
		 * Mapper class for listProductFeedback()
		 */

	private	class ProductFeedbackMapper implements Mapper<ProductFeedbackVO> {
			/**
			 * This method is used to map the result of query to ProductFeedbackVO
			 * @param resultSet
			 * @throws SQLException
			 * @return ProductFeedbackVO
			 */
			public ProductFeedbackVO map(ResultSet resultSet) throws SQLException {
				log.entering("ProductDefaultsSqlDAO","map()");
				ProductFeedbackVO productFeedbackVO = new ProductFeedbackVO();
				productFeedbackVO.setCompanyCode(resultSet.getString("CMPCOD"));
				productFeedbackVO.setProductCode(resultSet.getString("PRDCOD"));
				productFeedbackVO.setProductName(resultSet.getString("PRDNAM"));
				productFeedbackVO.setFeedbackId(resultSet.getLong("FEDBCKIDR"));
				productFeedbackVO.setName(resultSet.getString("SNDNAME"));
				productFeedbackVO.setEmailId(resultSet.getString("SNDEMLADR"));
				productFeedbackVO.setAddress(resultSet.getString("SNDADR"));
				productFeedbackVO.setRemarks(resultSet.getString("RMK"));
				productFeedbackVO.setLastupdatedUser(resultSet.getString("LSTUPDUSR"));
				productFeedbackVO.setLastupdatedTime(new LocalDate(LocalDate.NO_STATION,
						Location.NONE,resultSet.getDate("LSTUPDTIM")));
				productFeedbackVO.setFeedbackDate(new LocalDate(LocalDate.NO_STATION,
						Location.NONE,resultSet.getDate("FEDBCKDAT")));
				log.exiting("ProductDefaultsSqlDAO","map()");
				return productFeedbackVO;
			}
		}
	/**
	 * This method is used check whether any restriction exists or not
	 * @author A-1883
	 * @param stationAvailabilityFilterVO
	 * @return boolean
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public boolean checkStationAvailability(StationAvailabilityFilterVO stationAvailabilityFilterVO)
	throws SystemException,PersistenceException {
		log.entering("Products Sql DAO","checkStationAvailability");
		String companyCode  = stationAvailabilityFilterVO.getCompanyCode();
		String productCode = stationAvailabilityFilterVO.getProductCode();
		String commodity = stationAvailabilityFilterVO.getCommodity();
		String origin = stationAvailabilityFilterVO.getOrigin();
		String destination = stationAvailabilityFilterVO.getDestination();
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				PRODUCTS_DEFAULTS_CHECK_STATION_AVAILABILITY);
		query.setParameter(++index,companyCode);
		query.setParameter(++index,productCode);
		query.setParameter(++index,commodity);
		query.setParameter(++index,StationAvailabilityFilterVO.FLAG_YES);
		query.setParameter(++index,commodity);
		query.setParameter(++index,StationAvailabilityFilterVO.FLAG_NO);
		query.setParameter(++index,companyCode);
		query.setParameter(++index,productCode);
		query.setParameter(++index,commodity);
		if(origin != null && origin.trim().length() != 0){
			query.append(" UNION ALL	SELECT 'Origin Restricted' STATUS FROM PRDRSTSTN ");
			query.append("	WHERE CMPCOD = ? AND   PRDCOD = ? AND  ORGFLG = ? AND ");
			query.append(" ((UPPER(STNCOD) = ?  AND RSTFLG = ?) OR  (UPPER(STNCOD) <> ?  AND RSTFLG = ?  AND ");
			query.append(" 	NOT EXISTS (SELECT 'X' FROM PRDRSTSTN WHERE CMPCOD = ?  AND PRDCOD = ?  AND ");
			query.append("  ORGFLG = ? AND  UPPER(STNCOD) = ?	))) ");
			query.setParameter(++index,companyCode);
			query.setParameter(++index,productCode);
			query.setParameter(++index,StationAvailabilityFilterVO.FLAG_YES);
			query.setParameter(++index,origin);
			query.setParameter(++index,StationAvailabilityFilterVO.FLAG_YES);
			query.setParameter(++index,origin);
			query.setParameter(++index,StationAvailabilityFilterVO.FLAG_NO);
			query.setParameter(++index,companyCode);
			query.setParameter(++index,productCode);
			query.setParameter(++index,StationAvailabilityFilterVO.FLAG_YES);
			query.setParameter(++index,origin);
		}
		if(destination != null && destination.trim().length() != 0){
			query.append(" UNION ALL	SELECT 'Destination Restrictedd' STATUS FROM PRDRSTSTN ");
			query.append("	WHERE CMPCOD = ? AND   PRDCOD = ? AND  ORGFLG = ? AND ");
			query.append(" ((STNCOD = ?  AND RSTFLG = ?) OR  (STNCOD <> ?  AND RSTFLG = ?  AND ");
			query.append(" 	NOT EXISTS (SELECT 'X' FROM PRDRSTSTN WHERE CMPCOD = ?  AND PRDCOD = ?  AND ");
			query.append("  ORGFLG = ? AND  STNCOD = ?	))) ");
			query.setParameter(++index,companyCode);
			query.setParameter(++index,productCode);
			query.setParameter(++index,StationAvailabilityFilterVO.FLAG_NO);
			query.setParameter(++index,destination);
			query.setParameter(++index,StationAvailabilityFilterVO.FLAG_YES);
			query.setParameter(++index,destination);
			query.setParameter(++index,StationAvailabilityFilterVO.FLAG_NO);
			query.setParameter(++index,companyCode);
			query.setParameter(++index,productCode);
			query.setParameter(++index,StationAvailabilityFilterVO.FLAG_NO);
			query.setParameter(++index,destination);
		}
		String status = query.getSingleResult(new CheckStationAvailabilityMapper());
		log.exiting("Products Sql DAO","checkStationAvailability");
		log.log(Log.FINE, "status--->", status);
		if( status == null){
			return true;
		}
		return false;
	}
	/**
	 * @author A-1883
	 */
	private class CheckStationAvailabilityMapper implements Mapper<String> {
		/**
		 * @param resultSet
		 * @throws SQLException
		 * @return ProductFeedbackVO
		 */
		public String map(ResultSet resultSet) throws SQLException {
			log.entering("Products SqlDAO","map");
			return resultSet.getString("STATUS");
		}
	}

	/**
	 * Find All Products for the specied filter criteria
	 * @param productFilterVo filter values
	 * @return ProductVO
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public ProductVO findImage(ProductFilterVO productFilterVo)
	throws PersistenceException, SystemException {

		ProductVO productVO=new ProductVO();
		return findSqlDetailsForImage(productFilterVo);

	}
    /**
     * Added by A-1945
     * @param companyCode
     * @param productCodes
     * @return Collection<ProductValidationVO>
     * @throws SystemException
     */
    public Collection<ProductValidationVO> validateProducts(
            String companyCode, Collection<String> productCodes )
            throws SystemException {
        log.entering("ProductDefaultsSqlDAO", "validateProducts");
        Query query = getQueryManager().createNamedNativeQuery(
                PRODUCTS_DEFAULTS_VALIDATE_PRODUCTS);
        query.append(getWhereClause(productCodes));
        query.setParameter(1, companyCode);
        return query.getResultList(new Mapper<ProductValidationVO>() {
            public ProductValidationVO map(ResultSet rs)
                    throws SQLException {
                ProductValidationVO validationVO = new ProductValidationVO();
                validationVO.setProductCode(rs.getString("PRDCOD"));
                validationVO.setProductName(rs.getString("PRDNAM"));
                validationVO.setStartDate(
                        new LocalDate(LocalDate.NO_STATION,
                        		Location.NONE,rs.getDate("PRDSTRDAT")));
                validationVO.setEndDate(
                        new LocalDate(LocalDate.NO_STATION,
                        		Location.NONE,rs.getDate("PRDENDDAT")));
                return validationVO;
            }
        });
    }

    private String getWhereClause(
            Collection<String> productCodes) {
        StringBuilder stringBuilder = new StringBuilder("(");
        for(String productCode : productCodes) {
            stringBuilder.append("'").append(productCode).append("',");
        }
        return stringBuilder.replace(stringBuilder.lastIndexOf(","),
                              stringBuilder.lastIndexOf(",") + 1,
                              ")").toString();
    }
    /**
	 *
	 * @param productFilterVo
	 * @return ProductVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	private ProductVO findSqlDetailsForImage(ProductFilterVO productFilterVo)
	   throws SystemException,
			PersistenceException{
		log.entering(
				"****ProductDefaultsSqlDao =======*","findSqlDetailsForImage");

		Query qry = getQueryManager().createNamedNativeQuery(
				PRODUCT_DEFAULTS_FIND_IMAGE);
		qry.setParameter(1, productFilterVo.getProductCode());
		qry.setParameter(2, productFilterVo.getCompanyCode());
		log.exiting(
				"****ProductDefaultsSqlDao =======*","findSqlDetailsForImage");
		return qry.getSingleResult(new PrdImageDetailsMapper());
	}

	/**
	 * @author A-1870
	 */
	private class PrdImageDetailsMapper implements Mapper<ProductVO>{
		/**
		 * Method for mapping Product
		 * @param resultSet
		 * @throws SQLException
		 * @return ProductVO
		 * */
		public ProductVO map(ResultSet resultSet) throws SQLException{
			log.entering("****PrdImageDetailsMapper =======*","map");
			ProductVO productVO =new ProductVO();
			ImageModel image = new ImageModel();
			image.setContentType("image/gif");
			byte[] imageData = null;
			if(Objects.nonNull(resultSet.getObject(PRDICO))) {
				if(isOracleDataSource()) {
					Blob blob = resultSet.getBlob(PRDICO);
					int sizeOFBlob = (int)blob.length();
					long startIndex=1;
					imageData = blob.getBytes(startIndex,sizeOFBlob);
					image.setSize(sizeOFBlob);
					log.log(Log.FINE, "Image length is ", sizeOFBlob);
				} else {
					imageData = resultSet.getBytes(PRDICO);
					image.setSize(imageData.length);
					log.log(Log.FINE, "Image length is ", imageData.length);
				}
				image.setData(imageData);
				productVO.setImage(image);

			}
			log.exiting("***********PrdImageDetailsMapper =======*","map");
			return productVO;
		}
	}
	private class ProductParameterMapper implements Mapper<ProductParamterVO>{
		/**
		 * Method for mapping ProductSCC
		 * @param resultSet
		 * @throws SQLException
		 * @return ProductSCCVO
		 * */
		public ProductParamterVO map(ResultSet resultSet) throws SQLException{
			ProductParamterVO vo = new ProductParamterVO();
				vo.setCompanyCode(resultSet.getString("CMPCOD"));
				vo.setParameterCode(resultSet.getString("PARCOD"));
				vo.setParameterDescription(resultSet.getString("PARDES"));
				vo.setParameterValue(resultSet.getString("PARVAL"));
				if(resultSet.getString("POSVAL")!=null){
					vo.setLov(Arrays.asList(resultSet.getString("POSVAL").split(",")));
				}
				return vo;
		}
	}
	/**
     * @author A-1958
     * @param companyCode
     * @param products
     * @return Collection<String>
     * @throws SystemException
     * @throws PersistenceException
     */
    public Collection<String> findSccCodesForProducts(String companyCode,
    		Collection<String> products) throws SystemException, PersistenceException {
    	log.entering("ProductDefaultsSqlDAO","findSccCodesForProducts");
    	Query query = getQueryManager().createNamedNativeQuery(PRODUCTS_DEFAULTS_FINDSCCCODESFORPRODUCTS);
    	StringBuilder qry =null;
    	if(products.size() > 0) {
    		int index = products.size();
    		qry = new StringBuilder();
    		query.append("  AND MST.PRDNAM IN ( ");
    		for(String product : products) {
    			qry.append(" '").append(product).append("' ");
    			index--;
    			if(index != 0){
    				qry.append(" , ");
    			}
    		}
    		qry.append(" ) ");
    	}
    	if(qry!=null){
    		query.append(qry.toString());
    	}
    	log.log(Log.FINE, "Query ------->>>>>", query.toString());
		return query.getResultList(new SccForProductMapper());
    }
	/**
	*
	* @author A-1958
	*
	*/
	class SccForProductMapper implements Mapper<String>{
	   public String map(ResultSet resultSet) throws SQLException{
			   return resultSet.getString("PRDSCC");
	   }
	}
	/**
     *
     * @param companyCode
     * @param productName
     * @param shpgDate
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    public String validateProduct(String companyCode, String productName, LocalDate startDate, LocalDate endDate)
    throws SystemException, PersistenceException {
    	log.entering("ProductDefaultsSqlDAO","validateProduct");
    	int index = 0;
    	Query query = getQueryManager().createNamedNativeQuery(PRODUCTS_DEFAULTS_VALIDATEPRODUCT);
    	query.setParameter(++index,companyCode);
		query.setParameter(++index,productName);
		query.setParameter(++index,startDate);
		query.setParameter(++index,endDate);
		log.log(Log.FINE, "Query ------->>>>>", query.toString());
		return query.getSingleResult(new ValidateProductForBookingMapper());

    }
    /**
	*
	* @author A-1958
	*
	*/
	class ValidateProductForBookingMapper implements Mapper<String>{
	   public String map(ResultSet resultSet) throws SQLException{
			   return resultSet.getString("PRDCOD");
	   }
	}
	/**
	 * @author a-1885
	 */
	public Collection<ProductStockVO> findProductsForStock
    (String companyCode,String docType,String docSubType)throws SystemException,
    PersistenceException{
		log.entering("ProductDefaultsSqlDAO","findProductsForStock");
		Collection<ProductStockVO> productStocks = null;
		Query query = getQueryManager().createNamedNativeQuery
		(ProductDefaultsPersistenceConstants.PRODUCT_DEFAULTS_FINDPRODUCTSFORSTOCK);
		query.setParameter(1,companyCode);
		query.setParameter(2,docType);
		query.setParameter(3,docSubType);
		productStocks = query.getResultList(new ProductForStockMapper());
		log.log(Log.FINE, "----The ProductStockVOs------", productStocks);
		return productStocks;
	}
	/**
	 *
	 * @author a-1885
	 *
	 */
	private class ProductForStockMapper implements MultiMapper<ProductStockVO>{
		/**
		 * @author a-1885
		 */
		public List<ProductStockVO> map(ResultSet rs)throws SQLException{
			ArrayList<ProductStockVO> stocks = null;
			ProductStockVO stockVo = null;
			while(rs.next()){
					stockVo = new ProductStockVO();
					stockVo.setProductCode(rs.getString("PRDCOD"));
					stockVo.setProductName(rs.getString("PRDNAM"));
					stockVo.setDescription(rs.getString("PRDDES"));
					stockVo.setDetailedDescription(rs.getString("PRDDETDES"));
					stockVo.setStartDate(new LocalDate(LocalDate.NO_STATION,
							Location.NONE,rs.getDate("PRDSTRDAT")));
					stockVo.setEndDate(new LocalDate(LocalDate.NO_STATION,
							Location.NONE,rs.getDate("PRDENDDAT")));
					stockVo.setStatus(rs.getString("PRDSTA"));
					stockVo.setTransportModeCode(rs.getString("MOD"));
					stockVo.setSccCode(rs.getString("SCC"));
					stockVo.setPriorityCode(rs.getString("PRY"));
					if(stocks == null){
						stocks = new ArrayList<ProductStockVO>();
					}
					stocks.add(stockVo);
			}
			return stocks;
		}
	}
	/**
	 * @author a-1885
	 */
	public Collection<ProductEventVO> findSubProductEventsForTracking
	    (String companyCode,String productCode,String productScc,
	    		String productTransportMode,String productPriority)throws
	    		SystemException,PersistenceException{
		Query query = getQueryManager().createNamedNativeQuery
		(PRODUCTS_DEFAULTS_FINDEVENTSFORSUBPRODCUT);
		int index =0;
		StringBuilder subproductCode = new StringBuilder();
		subproductCode.append(productPriority).append(productTransportMode)
		.append(productScc);
		String subProductCodeStr = null;
		subProductCodeStr = subproductCode.toString();
		query.setParameter(++index,companyCode);
		query.setParameter(++index,productCode);
		query.setParameter(++index,subProductCodeStr);
		return query.getResultList(new EventForSubProductMapper());
	}
	/**
	 *
	 * @author a-1885
	 *
	 */
	private class EventForSubProductMapper implements Mapper<ProductEventVO>{

		public ProductEventVO map(ResultSet resultSet)throws SQLException{
			ProductEventVO eventVo = new ProductEventVO();
			eventVo.setEventCode(resultSet.getString("EVTCOD"));
			eventVo.setMaximumTime(resultSet.getDouble("MAXTIM"));
			eventVo.setMinimumTime(resultSet.getDouble("MINTIM"));
			eventVo.setEventType(resultSet.getString("EVTTYP"));
			if("Y".equals(resultSet.getString("EXTFLG"))){
				eventVo.setExternal(true);
			}
			else{
				eventVo.setExternal(false);
			}
			eventVo.setIsExport(resultSet.getString("EXPFLG"));
			if("Y".equals(resultSet.getString("INTFLG"))){
				eventVo.setInternal(true);
			}
			else{
				eventVo.setInternal(false);
			}
			if("Y".equals(resultSet.getString("TRNFLG"))){
				eventVo.setTransit(true);
			}
			else{
				eventVo.setTransit(false);
			}
			return eventVo;
		}
	}
	/**
	 * @author a-1885
	 * companyCode
	 * productCode
	 * docType
	 * docSubType
	 */
	public Collection<ProductStockVO> validateProductForDocType
    (String companyCode,String productCode)
    throws SystemException,PersistenceException{
		int index = 0;
		Collection<ProductStockVO> docTypes = null;
		Query query = getQueryManager().createNamedNativeQuery
		(PRODUCTS_DEFAULTS_VALIDATEPRODUCTFORDOCTYPE);
		query.setParameter(++index,companyCode);
		query.setParameter(++index,productCode);
		docTypes = query.getResultList(new ValidateProductForDocTypeMapper());
		log.log(Log.FINE, "---The Mapper Value--", docTypes);
		return docTypes;
	}
	/**
	 *
	 * @author a-1885
	 *
	 */
	private class ValidateProductForDocTypeMapper implements Mapper
	<ProductStockVO> {
		/**
		 * @author a-1885
		 */
		public ProductStockVO map(ResultSet rs)throws SQLException{
			ProductStockVO stockVo = new ProductStockVO();
				stockVo.setDocumentType(rs.getString("DOCTYP"));
				stockVo.setDocumentSubType(rs.getString("DOCSUBTYP"));
			return stockVo;
		}
	}
	/**
	 * @author a-1885
	 */
	public ProductLovVO findProductPkForProductName
	(String companyCode,String productName,LocalDate startDate,LocalDate endDate)throws
	SystemException,PersistenceException{
		List<ProductLovVO> productList = null;
		ProductLovVO lovVo = null;
		Query query = getQueryManager().createNamedNativeQuery
		(PRODUCTS_DEFAULTS_FINDPRODUCTPKFORPRODUCTNAME);
		log.log(Log.FINE, "---QUERY---", query);
		query.setParameter(1,companyCode);
		query.setParameter(2,productName);
		query.setParameter(3,startDate);
		query.setParameter(4,endDate);
		log.log(Log.FINE, "=========", query);
		productList = query.getResultList(new ProductPkMultiMapper());
		if(productList!=null && productList.size()>0){
			lovVo = productList.get(0);
		}
		if(lovVo!=null){
			log.log(Log.FINE, "---findProductPkForProductName----", lovVo.getProductCode());
			log.log(Log.FINE, "--SCC--", lovVo.getProductScc());
		}
		return lovVo;
	}
	/**
	 *
	 * @author a-1885
	 *
	 */
	private class ProductPkMultiMapper implements MultiMapper
	<ProductLovVO>{

		public List<ProductLovVO> map(ResultSet resultSet)throws
		SQLException{
			List<ProductLovVO> productLovList = null;
			Set<String> transMode = null;
			Set<String> priorities = null;
			Set<String> sccs = null;
			String isOverrideCapacity = null;
			ProductLovVO lovVo = null;
			while(resultSet.next()){
				lovVo = new ProductLovVO();
				lovVo.setProductCode(resultSet.getString("PRDCOD"));
				if(transMode==null){
					transMode = new HashSet<String>();
				}
				transMode.add(resultSet.getString("PRDTRAMOD"));
				if(priorities==null){
					priorities = new HashSet<String>();
				}
				priorities.add(resultSet.getString("PRDPRY"));
				if(sccs==null){
					sccs = new HashSet<String>();
				}
				sccs.add(resultSet.getString("PRDSCC"));
				isOverrideCapacity = resultSet.getString("ISSOVRCAP"); //Added as part of CR ICRD-237928
			}
			if(lovVo!=null){
				lovVo.setProductTransportMode(transMode);
				lovVo.setProductPriority(priorities);
				lovVo.setProductScc(sccs);
				lovVo.setOverrideCapacity(isOverrideCapacity); //Added as part of CR ICRD-237928
				productLovList = new ArrayList<ProductLovVO>();
				productLovList.add(lovVo);
			}
			return productLovList;

		}
	}
	/**
	 * @author a-4823
	 * @param companyCode
	 * @param productNames
	 * @return 
	 */
	public Collection<ProductVO> validateProductNames(String companyCode, 
			Collection<String> productNames)
			throws SystemException,PersistenceException{

		Query baseQuery = getQueryManager().
		createNamedNativeQuery(PRODUCT_DEFAULTS_VALIDATE_PRODUCTNAMES);
		int index = 0;
		baseQuery.setParameter(++index,companyCode);
		String prdQuery = getWhereClause(productNames);
		baseQuery.append(prdQuery.trim());	
		// Modified by A-5253 as part of BUG ICRD-37260
		baseQuery.append(" AND (? BETWEEN (PRDSTRDAT) AND (PRDENDDAT))");   //IASCB-61815 - postgres migration
		
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		baseQuery.setParameter(++index, currentDate.toSqlDate());

		log.log(Log.FINE, "the query is>>>>>> ", baseQuery);
		return baseQuery.getResultList(new Mapper<ProductVO>() {
				/* (non-Javadoc)
				 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
				 */

				public ProductVO map(ResultSet rs) throws SQLException {
					ProductVO vo = new ProductVO();
					vo.setCompanyCode(rs.getString("CMPCOD"));
					vo.setProductCode(rs.getString("PRDCOD"));
					vo.setProductName(rs.getString("PRDNAM"));
				vo.setStartDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getDate("PRDSTRDAT")));
				vo.setEndDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getDate("PRDENDDAT")));
					vo.setDescription(rs.getString("PRDDES"));
					vo.setStatus(rs.getString("PRDSTA"));					
					return vo;
				}

			});
	}
	
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.products.defaults.ProductDefaultsDAO#validateProductsForListing(com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO)
	 *	Added by 			: A-8041 on 02-Nov-2017
	 * 	Used for 	:	validateProductsForListing
	 *	Parameters	:	@param productFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 */
	public  Collection<ProductVO> validateProductsForListing(ProductFilterVO productFilterVO)
			throws SystemException,PersistenceException{

		Query baseQuery = getQueryManager().createNamedNativeQuery(PRODUCT_DEFAULTS_VALIDATE_PRODUCTS_FOR_LISTING);
		int index = 0;
		baseQuery.setParameter(++index, productFilterVO.getCompanyCode());
		if (productFilterVO.getStatus() != null) {
			String[] productStatus = productFilterVO.getStatus().split(",");
			List<String> prdStatList = Arrays.asList(productStatus);
			String prdStat = getWhereClause(prdStatList);
			baseQuery.append("  AND PRDSTA IN ");
			baseQuery.append(prdStat);
		}
		String prdQuery = getWhereClause(productFilterVO.getProductNames());
		if (prdQuery != null) {
			baseQuery.append(" AND PRDNAM IN ");
			baseQuery.append(prdQuery.trim().toUpperCase());
		}
		if (productFilterVO.getFromDate() != null) {
			baseQuery.append(" AND PRDSTRDAT <= ? ");
			baseQuery.setParameter(++index, productFilterVO.getFromDate());
		}
		if (productFilterVO.getToDate() != null) {
			baseQuery.append(" AND PRDENDDAT >= ? ");
			baseQuery.setParameter(++index, productFilterVO.getToDate());
		}
			
		log.log(Log.FINE, "the query is>>>>>> ", baseQuery);
	return baseQuery.getResultList(new Mapper<ProductVO>() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#
			 * map(java.sql.ResultSet)
			 */

			public ProductVO map(ResultSet rs) throws SQLException {
				ProductVO vo = new ProductVO();
				vo.setCompanyCode(rs.getString("CMPCOD"));
				vo.setProductCode(rs.getString("PRDCOD"));
				vo.setProductName(rs.getString("PRDNAM"));
				vo.setStartDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getDate("PRDSTRDAT")));
				vo.setEndDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getDate("PRDENDDAT")));
				vo.setDescription(rs.getString("PRDDES"));
				vo.setStatus(rs.getString("PRDSTA"));
				return vo;
			}

		});
	}
	/**
	 * 
	 * @author A-5257
	 * @param companyCode
	 * @param productNames
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ProductVO> findPriorityForProducts(Collection<ProductFilterVO> productFilterVos)
		throws SystemException,PersistenceException{	
		Query baseQuery = getQueryManager().createNamedNativeQuery(PRODUCT_DEFAULTS_FINDPRIORITY_FOR_PRODUCT);
		int index = 0;		
		boolean flag = true;
		if(productFilterVos == null || productFilterVos.size()== 0){
			return null;
		}	
		baseQuery.append("AND (");	
		for(ProductFilterVO productFilterVO:productFilterVos){				
			if(flag){
				baseQuery.setParameter(++index,productFilterVO.getCompanyCode());
				flag = false;
			}else{
				baseQuery.append("OR");
			}
			baseQuery.append("MST.PRDNAM = ? ");
			baseQuery.setParameter(++index,productFilterVO.getProductName());
		}
		baseQuery.append(")");
		return baseQuery.getResultList(new MultiMapper<ProductVO>(){
			public List<ProductVO> map(ResultSet rs) throws SQLException{
				ArrayList<ProductVO> productVos = new ArrayList<ProductVO>();
				ProductVO productVo = null;
				ProductPriorityVO productPriorityVO= null;
				String productKey = null;
				String priorityDetailKey = null;						
				Map<String,ProductVO> productMap = new HashMap<String, ProductVO>();
				Map<String,ProductPriorityVO> productPriorityMap = new HashMap<String, ProductPriorityVO>();
				while(rs.next()){   
					productKey = new StringBuilder().append(rs.getString("CMPCOD")).append(rs.getString("PRDCOD")).toString();
					if(!productMap.containsKey(productKey)){
						productVo = new ProductVO();					
						productVo.setProductName(rs.getString("PRDNAM"));
						productMap.put(productKey, productVo);
					}				
					priorityDetailKey = new StringBuilder().append(productKey).append(rs.getString("PRDPRY")).toString();				
					if(!productPriorityMap.containsKey(priorityDetailKey)){
						productVo = productMap.get(productKey);
						if(productVo.getPriority() == null){
							productVo.setPriority(new ArrayList<ProductPriorityVO>());
						}
						productPriorityVO = new ProductPriorityVO();					
						productPriorityVO.setPriority(rs.getString("PRDPRY"));					
						productVo.getPriority().add(productPriorityVO);
						productPriorityMap.put(priorityDetailKey, productPriorityVO);
					}				
				}
				productVos.addAll(productMap.values());
				return productVos;
			}
		});
	}
	/**
	 * 
	 * @author A-5111
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public void deleteProductSuggestConfigurations(String companyCode)
			throws SystemException, PersistenceException {
		log.entering("ProductDefaultsSqlDAO", "deleteProductSuggestConfigurations");
	    Query query = getQueryManager().createNamedNativeQuery(ProductDefaultsPersistenceConstants.PRODUCT_DEFAULTS_DELETE_PRODUCTSUGGESTCONFIGURATIONS);
	    query.setParameter(1,companyCode);
	    query.executeUpdate();
	    log.exiting("ProductDefaultsSqlDAO", "deleteProductSuggestConfigurations");
	}
	/**
	 * 
	 * @author A-5111
	 * @param companyCode
	 * @param sccCodes
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String findProductMappings(String companyCode,String sccCodes) throws SystemException, PersistenceException {
		 log.entering("ProductDefaultsSqlDAO", "findProductMappings");
		 Query query = getQueryManager().createNamedNativeQuery(ProductDefaultsPersistenceConstants.PRODUCT_DEFAULTS_FIND_PRODUCTMAPPINGS);
		 //Added by A-8230 for ICRD-130352
		 StringBuilder scc=new StringBuilder();
		 for(String sccCode : sccCodes.split(",")){
			 scc.append("").append(sccCode).append(",");  //Modified by A-8160 for ICRD-288601
		 }
		 query.setParameter(1,companyCode);
		 query.setParameter(2,scc.substring(0,scc.length()-1));
		 String productName= query.getSingleResult(getStringMapper("PRDNAM"));
		 //List<HashMap<String, String>> productMappings = query.getResultList(new ProductMappingDetailsMultiMapper());
		 log.exiting("ProductDefaultsSqlDAO", "findProductMappings");
		 return productName; 
	}
	/**
	 * 
	 * @author A-5111
	 * @param companyCode
	 * @param sccCodes
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public ArrayList<ProductSuggestionVO> findProductSuggestions(ProductSuggestionVO productSuggestionVO) throws SystemException, PersistenceException {
		 log.entering("ProductDefaultsSqlDAO", "findProductMappings");
		 int i = 0;
		 String sccCodes = productSuggestionVO.getSccCodes();
		 
		 Query query = getQueryManager().createNamedNativeQuery(ProductDefaultsPersistenceConstants.
				 PRODUCT_DEFAULTS_SUGGEST_PRODUCTOD_FORSERVICE);
		 boolean isFirstScc=true;
		 StringBuilder scc=new StringBuilder();
		 for(String sccCode:sccCodes.split(",")){
			 if(isFirstScc){
				 scc.append("'").append(sccCode);
				 isFirstScc=false;
			 }else{
				 scc.append("','").append(sccCode);
	 		 }
		 }
		 scc.append("'");
		 query.append(" (").append(scc.toString()).append(") ) ) ");
		 query.setParameter(++i,productSuggestionVO.getCompanyCode());
		 if(productSuggestionVO.getSource()!=null && productSuggestionVO.getSource().trim().length() > 0){
			 query.append(" AND CFGMST.SRCIND = ? ");
			 query.setParameter(++i,productSuggestionVO.getSource());
		 }else{
			 query.append(" AND CFGMST.SRCIND is NULL ");
		 }
		 ArrayList<ProductSuggestionVO> productSuggestions = (ArrayList<ProductSuggestionVO>) query.getResultList(new ProductSuggestionsMultiMapper());
		 log.exiting("ProductDefaultsSqlDAO", "findProductMappings");
		 return productSuggestions; 
	}
	
	/** 
	 * 
	 * @author A-5111
	 * ProductMappingDetailsMultiMapper to get the Mapping Product and scc combination with priorities
	 */
	private class ProductSuggestionsMultiMapper implements
			MultiMapper<ProductSuggestionVO> {
		public List<ProductSuggestionVO> map(ResultSet resultSet)
				throws SQLException {
			 log.entering("ProductDefaultsSqlDAO", "ProductMappingDetailsMultiMapper");

			List<ProductSuggestionVO> productSuggestions = new ArrayList<ProductSuggestionVO>();
			while (resultSet.next()) {
				ProductSuggestionVO productSuggestionVo = new ProductSuggestionVO();
				productSuggestionVo.setCompanyCode(resultSet.getString("CMPCOD"));
				productSuggestionVo.setProductName(resultSet.getString("PRDNAM"));
				productSuggestionVo.setParameterValue(resultSet.getString("PARVAL"));
				productSuggestionVo.setProductConfigurationString(resultSet.getString("PRDCNDSTR"));
				
				productSuggestions.add(productSuggestionVo);
			}
			
			 log.exiting("ProductDefaultsSqlDAO", "ProductMappingDetailsMultiMapper");
			return productSuggestions;
		}
	}
	
	/** 
	 * 
	 * @author A-5111
	 * ProductMappingDetailsMultiMapper to get the Mapping Product and scc combination with priorities
	 */
	private class ProductMappingDetailsMultiMapper implements
			MultiMapper<HashMap<String, String>> {
		public List<HashMap<String, String>> map(ResultSet resultSet)
				throws SQLException {
			 log.entering("ProductDefaultsSqlDAO", "ProductMappingDetailsMultiMapper");
			String key = null;
			String value = null;
			HashMap<String, String> productMap = new HashMap<String, String>();
			List<HashMap<String, String>> productMappings = new ArrayList<HashMap<String, String>>();
			while (resultSet.next()) {
				value = resultSet.getString("PRDNAM");
				key = resultSet.getString("PARVAL");
				if (!productMap.containsKey(key)) {
					productMap.put(key, value);
				} else {
					value = productMap.get(key) + value;
					productMap.put(key, value);
				}
			}
			productMappings.add(productMap);
			 log.exiting("ProductDefaultsSqlDAO", "ProductMappingDetailsMultiMapper");
			return productMappings;
		}
	}

	
	
	/**
	 * 
	 * @author A-5867
	 * @param ProductFilterVO
	 * @return Collection<ProductVO>
	 * @throws SystemException
	 */
	public Collection<ProductVO> findBookableProducts(ProductFilterVO productFilterVO) throws SystemException{
		Query baseQuery = getQueryManager().createNamedNativeQuery(PRODUCT_DEFAULTS_FIND_BOOKABLE_PRODUCTS);
		int index = 0;
		baseQuery.setParameter(++index,productFilterVO.getCompanyCode());
		baseQuery.append(" AND MST.PRDSTA =? ");	
		baseQuery.setParameter(++index,"A");
		if(null !=productFilterVO.getScc() && !productFilterVO.getScc().isEmpty()){
			baseQuery.append("AND SCC.PRDSCC = ? ");	
			baseQuery.setParameter(++index,productFilterVO.getScc());
		}
		return baseQuery.getResultList(new MultiMapper<ProductVO>(){
			public List<ProductVO> map(ResultSet rs) throws SQLException{
				List<ProductVO> productVos = new ArrayList<ProductVO>();
				Collection<SubProductVO>  subProducts =null;
				SubProductVO subProduct=null;
				Collection<ProductServiceVO>  productServices =null;
				ProductServiceVO productServiceVO=null;
				ProductVO productVo = null;
				String productKey = null;
				String subProducttKey = null;	
				String serviceKey = null;	
				Map<String,ProductVO> productMap = new HashMap<String, ProductVO>();
				Map<String,SubProductVO> subProductMap = new HashMap<String, SubProductVO>();
				Map<String,ProductServiceVO> serviceMap = new HashMap<String, ProductServiceVO>();
				while(rs.next()){   
					productKey = new StringBuilder().append(rs.getString("CMPCOD"))
					.append(rs.getString("PRDCOD")).append(rs.getString("PRDPRY")).toString();
					
					if(!productMap.containsKey(productKey)){
						productVo = new ProductVO();
						productVo.setCompanyCode(rs.getString("CMPCOD"));
						productVo.setProductCode(rs.getString("PRDCOD"));
						productVo.setProductName(rs.getString("PRDNAM"));
						productVo.setDescription(rs.getString("PRDDES"));
						productVo.setProductPriority(rs.getString("PRDPRY"));
						productVo.setCharge(100);
						subProducts= new ArrayList<SubProductVO>();
						if(null !=rs.getString("SUBPRDCOD")){
							subProducttKey = new StringBuilder().append(productKey)
							.append(rs.getString("SUBPRDCOD")).toString();
							subProduct= new SubProductVO();
							subProduct.setCompanyCode(rs.getString("CMPCOD"));
							subProduct.setProductCode(rs.getString("PRDCOD"));
							subProduct.setSubProductCode(rs.getString("SUBPRDCOD"));
							subProducts.add(subProduct);
							subProductMap.put(subProducttKey, subProduct);
						}					
						productVo.setSubProducts(subProducts);						
						productServices= new ArrayList<ProductServiceVO>();
						if(null !=rs.getString("PRDSERCOD")){
							serviceKey = new StringBuilder().append(productKey)
							.append(rs.getString("PRDSERCOD")).toString();
							subProduct= new SubProductVO();
							productServiceVO= new ProductServiceVO();
							productServiceVO.setServiceCode(rs.getString("PRDSERCOD"));
							productServiceVO.setServiceName(rs.getString("SRVNAM"));
							productServices.add(productServiceVO);
							serviceMap.put(serviceKey, productServiceVO);
						}
						productVo.setServices(productServices);
						productMap.put(productKey, productVo);
					}else{
						productVo=productMap.get(productKey);
						subProducts=productVo.getSubProducts();
						if(null !=rs.getString("SUBPRDCOD")){
							subProducttKey = new StringBuilder().append(productKey)
							.append(rs.getString("SUBPRDCOD")).toString();
							if(!subProductMap.containsKey(subProducttKey)){
								subProduct= new SubProductVO();
								subProduct.setCompanyCode(rs.getString("CMPCOD"));
								subProduct.setProductCode(rs.getString("PRDCOD"));
								subProduct.setSubProductCode(rs.getString("SUBPRDCOD"));
								subProducts.add(subProduct);
								productVo.setSubProducts(subProducts);
								subProductMap.put(subProducttKey, subProduct);
							}
						}
						
						productServices= productVo.getServices();
						if(null !=rs.getString("PRDSERCOD")){
							serviceKey = new StringBuilder().append(productKey)
							.append(rs.getString("PRDSERCOD")).toString();
							if(!serviceMap.containsKey(serviceKey)){
								productServiceVO= new ProductServiceVO();
								productServiceVO.setServiceCode(rs.getString("PRDSERCOD"));
								productServiceVO.setServiceName(rs.getString("SRVNAM"));
								productServices.add(productServiceVO);
								serviceMap.put(serviceKey, productServiceVO);
								productVo.setServices(productServices);
							}
						}
						productMap.put(productKey, productVo);
					}				
				}
				productVos.addAll(productMap.values());
				return productVos;
			}
		});
	}
	/**
	 * Added for ICRD_350746
	 * For checking if another product exist with the same priority
	 * @param productFilterVO
	 * @return
	 * @throws SystemException
	 */
	public String checkIfDuplicatePrdPriorityExists(ProductVO productVO)
																		throws SystemException{
		log.entering("ProductDefaultsSqlDAO", "checkIfDuplicatePrdPriorityExists   ");
		int index = 0;
		Query query;
		if(isOracleDataSource()) {
		 query = getQueryManager().createNamedNativeQuery(PRODUCT_DEFAULTS_CHECKIFDUPLICATEPRDPRIORITYEXIST);
		query.setParameter(++index,productVO.getCompanyCode());	
		query.setParameter(++index,Integer.parseInt(productVO.getPrdPriority()));           
		query.setParameter(++index,productVO.getProductName());
		}
		else {
	    query = getQueryManager().createNamedNativeQuery(POSTGRES_PRODUCT_DEFAULTS_CHECKIFDUPLICATEPRDPRIORITYEXIST);
		query.setParameter(++index,productVO.getCompanyCode());	
		query.setParameter(++index,productVO.getPrdPriority());           
		query.setParameter(++index,productVO.getProductName());
		}
		Mapper<String> checkDuplicatePriorityMapper = new Mapper<String>() {
			/**
			 * @param rs
			 * @return String
			 * @throws SQLException
			 */
			public String map(ResultSet rs) throws SQLException {
				String productNames = "";
				productNames = rs.getString("PRDNAM");
				return productNames;
			}
		};         
		return query.getSingleResult(checkDuplicatePriorityMapper);
		
	}
	/**
	 * 
	 * @author A-5867
	 * @param companyCode
	 * @param parameterCode
	 * @param sccCodes
	 * @return 
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public HashMap<String, String> findSuggestedProducts(String companyCode,String parameterCode,String parameterValue) throws SystemException, PersistenceException {
		 log.entering("ProductDefaultsSqlDAO", "findSuggestedProducts   ");
		 Query query = getQueryManager().createNamedNativeQuery(PRODUCT_DEFAULTS_FIND_SUGGESTED_PRODUCTS);
		 int index = 0;
		 query.setParameter(++index,companyCode);		
		 if(null !=parameterCode && !parameterCode.isEmpty()){
			 query.append("AND CFGMST.PARCOD  = ? ");	
			 query.setParameter(++index,parameterCode);
			}
		 if(null !=parameterValue && !parameterValue.isEmpty()){
			 query.append("AND CFGMST.PARVAL  LIKE ? ");	
			 query.setParameter(++index,"%"+parameterValue+"%");
			}
		 List<HashMap<String, String>> productMappings = query.getResultList(new ProductMappingDetailsMultiMapper());
		 log.exiting("ProductDefaultsSqlDAO", "findSuggestedProducts");
		 return productMappings.get(0); 
	}
	/**
	 * 
	 * @author A-5642
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public void deleteProductCommodityGroupMappings(String companyCode)
			throws SystemException, PersistenceException {
		log.entering("ProductDefaultsSqlDAO", "deleteProductCommodityGroupMappings");
	    Query query = getQueryManager().createNamedNativeQuery(
	    		ProductDefaultsPersistenceConstants.PRODUCT_DEFAULTS_DELETE_PRODUCTCOMMODITYGROUPMAPPINGS);
	    query.setParameter(1,companyCode);
	    query.executeUpdate();
	    log.exiting("ProductDefaultsSqlDAO", "deleteProductCommodityGroupMappings");
	}
	/**
	 * 
	 * @author A-6843
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public void deleteProductAttributePriorities(String companyCode)
			throws SystemException, PersistenceException {
		log.entering("ProductDefaultsSqlDAO", "deleteProductCommodityGroupMappings");
	    Query query = getQueryManager().createNamedNativeQuery(
	    		ProductDefaultsPersistenceConstants.PRODUCT_DEFAULTS_DELETE_PRODUCTATTRIBUTEPRIORITIES);
	    query.setParameter(1,companyCode);
	    query.executeUpdate();
	    log.exiting("ProductDefaultsSqlDAO", "deleteProductCommodityGroupMappings");
	}
	/**
	 * 
	 * @author A-6843
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public void deleteProductAttributeMappings(String companyCode)
			throws SystemException, PersistenceException {
		log.entering("ProductDefaultsSqlDAO", "deleteProductAttributeMappings");
	    Query query = getQueryManager().createNamedNativeQuery(
	    		ProductDefaultsPersistenceConstants.PRODUCT_DEFAULTS_DELETE_PRODUCTATTRIBUTEMAPPINGS);
	    query.setParameter(1,companyCode);
	    query.executeUpdate();
	    log.exiting("ProductDefaultsSqlDAO", "deleteProductAttributeMappings");
	}
	/**
	 * 
	 * @author A-6843
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public void deleteProductGroupRecommendations(String companyCode)
			throws SystemException, PersistenceException {
		log.entering("ProductDefaultsSqlDAO", "deleteProductGroupRecommendations");
	    Query query = getQueryManager().createNamedNativeQuery(
	    		ProductDefaultsPersistenceConstants.PRODUCT_DEFAULTS_DELETE_PRODUCTGROUPRECOMMENDATIONS);
	    query.setParameter(1,companyCode);
	    query.executeUpdate();
	    log.exiting("ProductDefaultsSqlDAO", "deleteProductGroupRecommendations");
	}
	
	public Collection<ProductModelMappingVO> getProductModelMapping(ProductModelMappingFilterVO productModelMappingFilterVO) 
			throws SystemException{
			int index = 0;
			Query query = getQueryManager().createNamedNativeQuery(PRODUCT_DEFAULTS_GET_PRODUCTMODEL_MAPPING);
			query.setParameter(++index,productModelMappingFilterVO.getCompanyCode());
			if(null != productModelMappingFilterVO.getCommodityCode()&&  !productModelMappingFilterVO.getCommodityCode().isEmpty()){
	            query.append(" AND COMGRPDTL.GRPENT  = ? ");     
	            query.setParameter(++index, productModelMappingFilterVO. getCommodityCode ());
			}
			if (productModelMappingFilterVO.getProductGroupName() != null && !productModelMappingFilterVO.getProductGroupName().isEmpty()) {
			      query.append(" AND PRDGRPMST.GRPNAM  = ? ");
			      query.setParameter(++index, productModelMappingFilterVO.getProductGroupName());
			}
			if(productModelMappingFilterVO.isProductValidityCheckForChannelSoco()){
	            query.append(" AND (PRDATR.SOLCFG is NULL OR PRDATR.SOLCFG ='Y') ");
			}
			if(null != productModelMappingFilterVO.getProductCode() &&  !productModelMappingFilterVO.getProductCode().isEmpty()){
	            query.append(" AND MST.PRDNAM  = ? ");     
	            query.setParameter(++index, productModelMappingFilterVO. getProductCode ());
			}
			log.log(Log.INFO, "Query is", query.toString());
			return query.getResultList(new ProductModelMappingMapper());
	}
	/**
	 * Method for getting ProductLov
	 * @param productLovFilterVO
	 * @param displayPage
	 * @return Collection<ProductLovVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ProductLovVO> findProductForMaster(ProductLovFilterVO productLovFilterVO)
		throws SystemException,PersistenceException {
		log.entering("CommoditySqlDAO", "=========>>>getAllCommodities ");
		int index = 0;
		Query baseQuery = getQueryManager().createNamedNativeQuery(
				PRD_DEFAULTS_FIND_PRODUCT_FOR_MASTER);
		baseQuery.setParameter(++index, productLovFilterVO.getCompanyCode());
		if (productLovFilterVO.getProductName() != null) {
			baseQuery.append(" AND MST.PRDNAM LIKE '"
					+ productLovFilterVO.getProductName() + "%'");
			// baseQuery.setParameter(++index,
			// commodityFilterVO.getCommodityCode());
		}
		if (productLovFilterVO.getStartDate() != null) {
			baseQuery.append(" AND ? >= TRUNC(MST.PRDSTRDAT) ");
			baseQuery.append(" AND ? <= TRUNC(MST.PRDENDDAT) ");
			baseQuery.setParameter(++index, productLovFilterVO.getStartDate());
			baseQuery.setParameter(++index, productLovFilterVO.getStartDate());
		}
		if (productLovFilterVO.getEndDate() != null) {
			baseQuery.append(" AND ? <= TRUNC(PRDENDDAT) ");
			baseQuery.append(" AND ? >= TRUNC(PRDSTRDAT) ");
			baseQuery.setParameter(++index, productLovFilterVO.getEndDate());
			baseQuery.setParameter(++index, productLovFilterVO.getEndDate());
		}
		if (productLovFilterVO.getisProductNotExpired()) {

			if (productLovFilterVO.getCurrentDate() != null) {
				baseQuery.append(" AND TRUNC(PRDENDDAT) >= ? ");
				baseQuery.setParameter(++index, productLovFilterVO.getCurrentDate());
			}
		}
		return baseQuery.getResultList(new ProductMaterMapper());

	}
	
	/**
	 * 
	 */
	public void removeProductODMapping(String companyCode)
			throws SystemException, PersistenceException {
		log.entering("ProductDefaultsSqlDAO", "removeProductODMapping");
	    Query query = getQueryManager().createNamedNativeQuery(
	    		ProductDefaultsPersistenceConstants.PRODUCT_DEFAULTS_DELETE_PRODUCTODMAPPINGS);
	    query.setParameter(1,companyCode);
	    query.executeUpdate();
	    log.exiting("ProductDefaultsSqlDAO", "removeProductODMapping");
	}
	public Collection<ProductODMappingVO> getProductODMapping(String companyCode)
		throws SystemException {
			int index = 0;
			
			 Query query = getQueryManager().createNamedNativeQuery(
		    		ProductDefaultsPersistenceConstants.PRODUCT_DEFAULTS_GET_PRODUCTOD_MAPPING);
			query.setParameter(++index, companyCode);
			log.log(Log.INFO, "Query is", query.toString());
			return query.getResultList(new ProductODMappingMapper());
	}
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.products.defaults.ProductDefaultsDAO#findProducts(com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO)
	 *	Added by 			: A-7548 on 19-Jan-2018
	 * 	Used for 	:
	 *	Parameters	:	@param productFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException
	 */
	public Collection<ProductVO> findProducts(ProductFilterVO productFilterVO) throws SystemException{
		Query baseQry = getQueryManager().createNamedNativeQuery(PRODUCT_DEFAULTS_FIND_PRODUCTS);
		return baseQry.getResultList(new ProductMapper());
	}
    /**
     * @author A-7740
   	 * @param companyCode
   	 * @param product
   	 * @param parameterCodes
   	 * @return
   	 * @throws SystemException
   	 */
	public Map<String,String> findProductParametersByCode(String companyCode,
			   String productCode,Collection<String> parameterCodes)
		throws SystemException{
		Query query=getQueryManager().createNamedNativeQuery(
				PRODUCT_FINDPRODUCTPARCODE);
			int index =0;
			query.setParameter(++index,companyCode);
	    	query.setParameter(++index,productCode);
	    	if( parameterCodes != null && parameterCodes.size() > 0 ) {
				query.append("(");
				String productQuery = getParameters(parameterCodes);
				query.append(productQuery);
				query.append(")");
			}
			for(String parCode : parameterCodes){
				query.setParameter(++index,parCode);
			}
	    	 return query.getResultList(new ProductParMapper()).get(0);
	}
  	private String getParameters(Collection<String> parCodes) {
    	StringBuilder buffer = new StringBuilder();
    	for(String parCode : parCodes) {
    		buffer.append("?").append(",");
    	}
    	int len = buffer.length();
    	return buffer.toString().substring(0, len-1).trim();
	}
  	/**
  	 * 
  	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.products.defaults.ProductDefaultsDAO#findProductsByParameters(java.lang.String, java.util.Map)
  	 *	Added by 			: A-8146 on 23-Nov-2018
  	 * 	Used for 	:
  	 *	Parameters	:	@param companyCode
  	 *	Parameters	:	@param parametersAndParValue
  	 *	Parameters	:	@return
  	 *	Parameters	:	@throws SystemException
  	 */
  	public Collection<ProductVO> findProductsByParameters(String companyCode,
			 Map<String,String> parametersAndParValue)
			throws  SystemException {
  		Query query=getQueryManager().createNamedNativeQuery(
				"product.defaults.findProductsByParameters");
  		int index=0;
  		query.setParameter(++index,companyCode);
  		query.setParameter(++index,"A");
  		if(parametersAndParValue!=null && parametersAndParValue.size()>0){
  			
  			for(Map.Entry<String, String> parameters:parametersAndParValue.entrySet()){
  	  			query.append(" AND ");
  	  			query.append(" PAR.PARCOD= ? AND  PAR.PARVAL =?");
  	  			query.setParameter(++index,parameters.getKey());
  	  			query.setParameter(++index,parameters.getValue());
  	  		}	
  		} 
  		
  		 return (Collection<ProductVO>) query.getResultList(new MultiMapper<ProductVO>() {

			@Override
			public List<ProductVO> map(ResultSet rs) throws SQLException {
				List<ProductVO> products=new ArrayList<ProductVO>();
				Map<String, ProductVO> productVoMap = new HashMap<String, ProductVO>();
				Map<String, ProductSCCVO> productSccVoMap = new HashMap<String, ProductSCCVO>();
				while(rs.next()){
					String parentId = rs.getString("CMPCOD") + rs.getString("PRDCOD");
					String sccId = null;
					ProductVO productVO=null;
					ProductSCCVO productSCCVO =null;
					if (rs.getString("PRDSCC") != null) {
						sccId = parentId + rs.getString("PRDSCC");
					}
					if (!productVoMap.containsKey(parentId)) {
						productVO=new ProductVO();
						productVO.setProductCode(rs.getString("PRDCOD"));
						productVO.setProductName(rs.getString("PRDNAM"));
						productVO.setDescription(rs.getString("PRDDES"));
						products.add(productVO);
						productVoMap.put(parentId, productVO);
					}else{
						productVO=productVoMap.get(parentId);
					}
					if (sccId != null) {						
						if (!productSccVoMap.containsKey(sccId)) {
							productSCCVO=new ProductSCCVO();
							productSCCVO.setScc(rs.getString("PRDSCC"));
							if(productVO.getProductScc()==null){
								productVO.setProductScc(new ArrayList<ProductSCCVO>());
							}
							productVO.getProductScc().add(productSCCVO);
							productSccVoMap.put(sccId, productSCCVO);
						}
					}					
				}
				return products;
			}
  			
		});
  		
  	}
    /**
	 * @author A-9025
	 * @param companyCode
	 * @param productNames
	 * @return 
	 */
	public Collection<ProductVO> validateProductsByNames(String companyCode, Collection<String> productNames)
			throws SystemException,PersistenceException {

		Query baseQuery = getQueryManager().createNamedNativeQuery(PRODUCT_DEFAULTS_VALIDATE_PRODUCTSBYNAMES);
		int index = 0;
		baseQuery.setParameter(++index,companyCode);
		String prdQuery = getWhereClause(productNames);
		baseQuery.append(prdQuery.trim());	

		log.log(Log.FINE, "the query is>>>>>> ", baseQuery);
		Collection<ProductVO> validProductNames = baseQuery.getResultList(new Mapper<ProductVO>() {
			/* (non-Javadoc)
			 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
			 */
			public ProductVO map(ResultSet rs) throws SQLException {
				ProductVO vo = new ProductVO();
				vo.setCompanyCode(rs.getString("CMPCOD"));
				vo.setProductCode(rs.getString("PRDCOD"));
				vo.setProductName(rs.getString("PRDNAM"));
				vo.setStartDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getDate("PRDSTRDAT")));
				vo.setEndDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getDate("PRDENDDAT")));
				vo.setDescription(rs.getString("PRDDES"));
				vo.setStatus(rs.getString("PRDSTA"));					
				return vo;
	}
		});
		return validProductNames;
  	}
  	/**
	 * 
	 * 	Method		:	ProductDefaultsDAO.findProductsByParameters
	 *	Added by 	:	A-8130 on 10-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<ProductParamterVO>
	 */
	public Collection<ProductParamterVO> findAllProductParameters(String companyCode)
			throws  SystemException {
		Query query=getQueryManager().createNamedNativeQuery(
				"product.defaults.findAllProductParameters");
		int index=0;
		query.setParameter(++index,companyCode);
		 return (Collection<ProductParamterVO>) query.getResultList(new MultiMapper<ProductParamterVO>() {
				@Override
				public List<ProductParamterVO> map(ResultSet rs) throws SQLException {
					List<ProductParamterVO> productParamterVOs=new ArrayList<ProductParamterVO>();
					while(rs.next()){
						ProductParamterVO productParamterVO=new ProductParamterVO();
						productParamterVO.setParameterDescription(rs.getString("PARDES"));
						productParamterVO.setParameterCode(rs.getString("PARCOD"));
						if(rs.getString("POSVAL")!=null){
							productParamterVO.setLov((Arrays.asList(rs.getString("POSVAL").split(","))));
						}
						productParamterVOs.add(productParamterVO);
					}
					return productParamterVOs;
				}
			});
  	}
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.products.defaults.ProductDefaultsDAO#clearProductSelectionRuleMaster(java.lang.String)
	 *	Added by 			:   Prashant Behera on Jun 29, 2022
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@throws SystemException
	 */
	@Override
	public void clearProductSelectionRuleMaster(String companyCode) throws SystemException {
		log.entering(this.getClass().getSimpleName(), "clearProductSelectionRuleMaster");
		Query query = getQueryManager().createNativeQuery(" DELETE FROM PRDSELRULMST WHERE CMPCOD= ?  ");
		query.setParameter(1, companyCode);
		query.executeUpdate();
		log.exiting(this.getClass().getSimpleName(), "clearProductSelectionRuleMaster");
	}
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.products.defaults.ProductDefaultsDAO#listProductSelectionRuleMaster(java.lang.String)
	 *	Added by 			: 	Prashant Behera on Jun 29, 2022
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 */
	@Override
	public Collection<ProductSelectionRuleMasterVO> listProductSelectionRuleMaster(String companyCode)
			throws SystemException {
		log.entering(this.getClass().getSimpleName(), "listProductSelectionRuleMaster");
		Query query = getQueryManager().createNativeQuery(" SELECT * FROM PRDSELRULMST WHERE CMPCOD= ?  ");
		query.setParameter(1, companyCode);
		log.exiting(this.getClass().getSimpleName(), "listProductSelectionRuleMaster");
		return query.getResultList(new ProductSelectionRuleMasterMultiMapper());
	}
	@Override
	public Collection<ProductSelectionRuleMasterVO> findProductsForBookingFromProductSelectionRule(String companyCode,
			Map<String, String> filterConditions,Collection<GeneralParametersVO> generalParametersVOs) throws SystemException {
		log.entering(this.getClass().getSimpleName(), "findProductsForBookingFromProductSelectionRule");
		int index = 0;
		Query query = getQueryManager().createNativeQuery(" SELECT * FROM PRDSELRULMST WHERE CMPCOD= ?  ");
		query.setParameter(++index, companyCode);
		if(filterConditions!=null && !filterConditions.isEmpty()) {
			for (Map.Entry<String, String> entry : filterConditions.entrySet()) {
				if (SRCCOD.equals(entry.getKey())) {
					query.append(" AND SRCCOD LIKE '%" + entry.getValue() +"%'");
				}
				if (SCCCOD.equals(entry.getKey()) && entry.getValue()!=null) {
					query.append(" AND (SCCCOD IN ('" +PATTERN_COMMA.matcher(entry.getValue()).replaceAll("','") + "')  OR SCCCOD IS NULL )" );
				}else if(SCCCOD.equals(entry.getKey())) {
					query.append(" AND (SCCCOD IS NULL )" );
				}else {/* Do nothing */}
				if (AGTCOD.equals(entry.getKey()) && entry.getValue()!=null) {
					query.append(" AND (AGTCOD = ? OR AGTCOD IS NULL )" );
					query.setParameter(++index, entry.getValue());
				}else if(AGTCOD.equals(entry.getKey())){
					query.append(" AND ( AGTCOD IS NULL )" );
				} else {/* Do nothing */}
				if (COMCOD.equals(entry.getKey()) && entry.getValue()!=null ) {
					query.append(" AND (COMCOD IN ('" +PATTERN_COMMA.matcher(entry.getValue()).replaceAll("','") + "')  OR COMCOD IS NULL )" );
				}else if(COMCOD.equals(entry.getKey())){
					query.append(" AND ( COMCOD IS NULL )" );
				} else {/* Do nothing */}
				if (ORGCNTCOD.equals(entry.getKey()) && entry.getValue()!=null) {
					query.append(" AND (ORGCNTCOD = ? OR ORGCNTCOD IS NULL )" );
					query.setParameter(++index, entry.getValue());
				}else if(ORGCNTCOD.equals(entry.getKey())) {
					query.append(" AND ( ORGCNTCOD IS NULL )" );
				} else {/* Do nothing */}
				if (DSTCNTCOD.equals(entry.getKey()) && entry.getValue()!=null) {
					query.append(" AND (DSTCNTCOD = ? OR DSTCNTCOD IS NULL )" );
					query.setParameter(++index, entry.getValue());
				}else if(DSTCNTCOD.equals(entry.getKey())){
					query.append(" AND ( DSTCNTCOD IS NULL )" );
				} else {/* Do nothing */}
				if (AGTGRPCOD.equals(entry.getKey()) && entry.getValue()!=null) {
					query.append(" AND (AGTGRPCOD IS NULL OR AGTGRPCOD IN ('" +PATTERN_COMMA.matcher(entry.getValue()).replaceAll("','") + "')  )" );
				}else if(AGTGRPCOD.equals(entry.getKey())){
					query.append(" AND ( AGTGRPCOD IS NULL  )" );
				}else {/* Do nothing */}
				if (SCCGRPCOD.equals(entry.getKey()) && entry.getValue()!=null) {
					query.append(" AND (SCCGRPCOD IS NULL OR SCCGRPCOD IN  ('" +PATTERN_COMMA.matcher(entry.getValue()).replaceAll("','") + "')  )" );
				}else if(SCCGRPCOD.equals(entry.getKey())){
					query.append(" AND ( SCCGRPCOD IS NULL  )" );
				}else {/* Do nothing */}
				if (INTDOMFLG.equals(entry.getKey()) && entry.getValue()!=null) {
					query.append(" AND (INTDOMFLG IS NULL OR INTDOMFLG = ( ? ) )" );
					query.setParameter(++index, entry.getValue());
				}else if(INTDOMFLG.equals(entry.getKey())) {
					query.append(" AND ( INTDOMFLG IS NULL )" );
				}else {/* Do nothing */}
			}
		}
		log.exiting(this.getClass().getSimpleName(), "findProductsForBookingFromProductSelectionRule");
		Collection<ProductSelectionRuleMasterVO> productSelectionRuleMasterVOs = query.getResultList(new ProductSelectionRuleMasterMultiMapper());
		if(productSelectionRuleMasterVOs!=null && !productSelectionRuleMasterVOs.isEmpty() && generalParametersVOs!=null && !generalParametersVOs.isEmpty()) {
			Map<String, Integer> parameterPriorityMap = new LinkedHashMap<>();
			for (GeneralParametersVO generalParametersVO : generalParametersVOs) {
				parameterPriorityMap.put(
						generalParametersVO.getParameterCode(),
						generalParametersVO.getPriorityOrder());
			}

			List<Integer> parameterPriorityKeyList = new ArrayList<>(
					parameterPriorityMap.values());
			Collections.reverse(parameterPriorityKeyList);
			int i = 0;
			int powerValue;
			for (String key : parameterPriorityMap.keySet()) {
				powerValue = (int) Math.pow(2,
						parameterPriorityKeyList.get(i++));
				parameterPriorityMap.put(key, powerValue);
			}
			HashMap<Integer, ProductSelectionRuleMasterVO> largestRankProductSelectionRuleMasterVO =  new HashMap<>();
			int rank = 0;
			int largestRank = 0;
			for (ProductSelectionRuleMasterVO productSelectionRuleMasterVO : productSelectionRuleMasterVOs) {
				if (productSelectionRuleMasterVO.getOriginCountryCode() != null
						&& productSelectionRuleMasterVO.getOriginCountryCode().length() != 0
						&& parameterPriorityMap.get(ORGCNTCOD) != null) {
					rank += parameterPriorityMap.get(ORGCNTCOD);
				}
				if (productSelectionRuleMasterVO.getDestinationCountryCode() != null
						&& productSelectionRuleMasterVO.getDestinationCountryCode().length() != 0
						&& parameterPriorityMap.get(DSTCNTCOD) != null) {
					rank += parameterPriorityMap.get(DSTCNTCOD);
				}
				if (productSelectionRuleMasterVO.getCommodityCode() != null
						&& productSelectionRuleMasterVO.getCommodityCode().length() != 0
						&& parameterPriorityMap.get(COMCOD) != null) {
					rank += parameterPriorityMap.get(COMCOD);
				}
				if (productSelectionRuleMasterVO.getSccCode() != null
						&& productSelectionRuleMasterVO.getSccCode().length() != 0
						&& parameterPriorityMap.get(SCCCOD) != null) {
					rank += parameterPriorityMap.get(SCCCOD);
				}
				if (productSelectionRuleMasterVO.getAgentCode() != null
						&& productSelectionRuleMasterVO.getAgentCode().length() != 0
						&& parameterPriorityMap.get(AGTCOD) != null) {
					rank += parameterPriorityMap.get(AGTCOD);
				}
				if (productSelectionRuleMasterVO.getAgentGroupCode() != null
						&& productSelectionRuleMasterVO.getAgentGroupCode().length() != 0
						&& parameterPriorityMap.get(AGTGRPCOD) != null) {
					rank += parameterPriorityMap.get(AGTGRPCOD);
				}
				if (productSelectionRuleMasterVO.getSccGroupCode() != null
						&& productSelectionRuleMasterVO.getSccGroupCode().length() != 0
						&& parameterPriorityMap.get(SCCGRPCOD) != null) {
					rank += parameterPriorityMap.get(SCCGRPCOD);
				}
				if (productSelectionRuleMasterVO.getInternationalDomesticFlag() != null
						&& productSelectionRuleMasterVO.getInternationalDomesticFlag().length() != 0
						&& parameterPriorityMap.get(INTDOMFLG) != null) {
					rank += parameterPriorityMap.get(INTDOMFLG);
				}
				if (largestRank < rank || largestRankProductSelectionRuleMasterVO.isEmpty()) {
					largestRank = rank;
					largestRankProductSelectionRuleMasterVO.put(rank, productSelectionRuleMasterVO);
				} else if (largestRank == rank) {
					largestRankProductSelectionRuleMasterVO.remove(rank);
					// if multiple rules are returned of same rank- rule
					// config should be rejected and normal flow to be
					// considered
				}else {
					// Do nothing
				}
				rank = 0;
			}
			
			if (largestRankProductSelectionRuleMasterVO!=null && largestRankProductSelectionRuleMasterVO.size() > 0 ) {
					List<Integer> ranks= new ArrayList<>();
					ranks.addAll(largestRankProductSelectionRuleMasterVO.keySet());
					Collections.sort(ranks);
					ProductSelectionRuleMasterVO largestProductRule =largestRankProductSelectionRuleMasterVO.get(Collections.max(ranks));
					productSelectionRuleMasterVOs.clear();
					productSelectionRuleMasterVOs.add(largestProductRule);
			}
			
		}
		return productSelectionRuleMasterVOs;
	}
	
}
