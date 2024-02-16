/*
 * MaintainPrivilegeSessionImpl.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.products.defaults;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductServiceVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCommodityVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCustomerGroupVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionPaymentTermsVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionSegmentVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionStationVO;
import com.ibsplc.icargo.business.products.defaults.vo.SubProductVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommoditySccVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainSubProductSessionInterface;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1417
 *
 */
public class MaintainSubProductSessionImpl extends AbstractScreenSession
        implements MaintainSubProductSessionInterface {


	private static final String WEIGHT_CODE="weightDetails";
	private static final String KEY_STATUS="status";
	private static final String KEY_PRIORITY="priority";
	private static final String VOLUME_CODE="volumeDetails";
	private static final String RESTRICTED_PAYMENT_TERMS="restrictedPaymentTerms";
	private static final String KEY_SCREEN_ID = "products.defaults.maintainsubproducts";
	private static final String KEY_MODULE_NAME = "products.defaults";
	private static final String KEY_SUBPRODUCTVO = "subProductVO";
	private static final String KEY_COMMODITYVO = "commodityVOs";
	private static final String KEY_SEGMENTVO = "segmentVOs";
	private static final String KEY_STATIONVO = "stationVOs";
	private static final String KEY_CUSTGROUPVO = "custGroupVOs";
	private static final String KEY_SERVICE = "productService";
	private static final String PRODUCT_EVENT_VOS="producteventvos";
	private static final String SELECTED_COMMODITY_LOV_VOS="selectedcommoditylovvos";
	private static final String COMMODITY_LOV_VOS="commoditylovvos";
	private static final String NEXT_ACTION="nextaction";
	private static final String PRODUCT_COMMODITY_VOS="productcommodityvos";
	private static final String RESTRICTION_SEGMENT_VOS = "restrictionsegmentvos";
	private static final String SEGMENT_AFTER_LISTING = "segmentafterlisting";
	private static final String SELECED_RESTRICTION_TERMS = "selectedrestrictionterms";
	private static final String PRODUCT_SCC = "productScc";




	/**
     * This method returns the SCREEN ID for the Maintain Sub Product screen
     */


    public String getScreenID(){
        return KEY_SCREEN_ID;
    }



   /**
    * @return String
    */

    public String getModuleName(){
        return KEY_MODULE_NAME;
    }
    /**
     * @return Collection<CommoditySccVO>
     */
    public Collection<CommoditySccVO> getSelectedComodityLovVOs(){
		return (Collection<CommoditySccVO>)getAttribute(SELECTED_COMMODITY_LOV_VOS);
	}
   /**
    * @param selectedComdtyLovVOs
    */
	public void setSelectedComodityLovVOs(Collection<CommoditySccVO> selectedComdtyLovVOs){
		setAttribute(SELECTED_COMMODITY_LOV_VOS, (ArrayList<CommoditySccVO>)selectedComdtyLovVOs);
	}
	/**
	 * @return void
	 */
	public void removeSelectedComodityLovVOs(){
		removeAttribute(SELECTED_COMMODITY_LOV_VOS);
	}
	/**
	 * @return Page<CommoditySccVO>
	 */
	public Page<CommoditySccVO> getCommodityLovVOs(){
		return (Page<CommoditySccVO>)getAttribute(COMMODITY_LOV_VOS);
	}
	/**
	 * @param comdtyLovVOs
	 */
	public void setCommodityLovVOs(Page<CommoditySccVO> comdtyLovVOs){
		setAttribute(COMMODITY_LOV_VOS, (Page<CommoditySccVO>)comdtyLovVOs);
	}
	/**
	 * @return void
	 */
	public void removeCommodityLovVOs(){
		removeAttribute(COMMODITY_LOV_VOS);
	}
    /**
     * This method is used to get the weightDetails from the session
     * @return OneTimeVO
     */
	public Collection<OneTimeVO>  getWeight(){
	    return (Collection<OneTimeVO>)getAttribute(WEIGHT_CODE);
	}


	/**
	 * This method is used to set the weightDetails in session
	 * @param weightDetails
	 */
	public void setWeight(Collection<OneTimeVO>  weightDetails) {
	    setAttribute(WEIGHT_CODE, (ArrayList<OneTimeVO>)weightDetails);
	}
	/**
	 * @return void
	 */
	public void removeWeight(){
		removeAttribute("WEIGHT_CODE");
	}
	/**
	 * @return  Collection<RestrictionSegmentVO>
	 */
	public Collection<RestrictionSegmentVO> getSegmentAfterListing(){
		return (Collection<RestrictionSegmentVO>)getAttribute(SEGMENT_AFTER_LISTING);
	}
	/**
	 * @param segmentVOs
	 */
	public void setSegmentAfterListing(Collection<RestrictionSegmentVO> segmentVOs){
		setAttribute(SEGMENT_AFTER_LISTING, (ArrayList<RestrictionSegmentVO>)segmentVOs);
	}
	/**
	 * @return void
	 */
	public void removeSegmentAfterListing(){
		removeAttribute(SEGMENT_AFTER_LISTING);
	}
	/**
	 * @return String
	 */
	public String getProductScc(){
		return (String)getAttribute(PRODUCT_SCC);
	}
	/**
	 * @param productScc
	 */
	public void setProductScc(String productScc){
		setAttribute(PRODUCT_SCC, (String)productScc);
	}
	/**
	 * @return void
	 */
	public void removeProductScc(){
		removeAttribute(PRODUCT_SCC);
	}

	/**
     * This method is used to get the volumeDetails from the session
     * @return OneTimeVO
     */
	public Collection<OneTimeVO>  getVolume(){
	    return (Collection<OneTimeVO>)getAttribute(VOLUME_CODE);
	}

	/**
	 * This method is used to set the volumeDetails in session
	 * @param volumeDetails
	 */
	public void setVolume(Collection<OneTimeVO>  volumeDetails) {
	    setAttribute(VOLUME_CODE, (ArrayList<OneTimeVO>)volumeDetails);
	}
	/**
	 * @return void
	 */
	public void removeVolume(){
		removeAttribute("VOLUME_CODE");
	}
	/**
	 * @return OneTimeVO
	 */
	public Collection<OneTimeVO>  getPriority(){
	    return (Collection<OneTimeVO>)getAttribute(KEY_PRIORITY);
	}

	/**
	 * This method is used to set the volumeDetails in session
	 * @param priority
	 */
	public void setPriority(Collection<OneTimeVO>  priority) {
	    setAttribute(KEY_PRIORITY, (ArrayList<OneTimeVO>)priority);
	}
	/**
	 * @return void
	 */
	public void removePriority(){
		removeAttribute("KEY_PRIORITY");
	}
	/**
     * This method is used to get the restrictedPaymentTerms from the session
     * @return Collection<RestrictionPaymentTermsVO>
     */
	public Collection<RestrictionPaymentTermsVO>  getRestrictedPaymentTerms(){
	    return (Collection<RestrictionPaymentTermsVO>)getAttribute(RESTRICTED_PAYMENT_TERMS);
	}

	/**
	 * This method is used to set the restrictedPaymentTerms in session
	 * @param restrictedPaymentTerms
	 */
	public void setRestrictedPaymentTerms(Collection<RestrictionPaymentTermsVO>  restrictedPaymentTerms) {
	    setAttribute(RESTRICTED_PAYMENT_TERMS, (ArrayList<RestrictionPaymentTermsVO>)restrictedPaymentTerms);
	}
	/**
	 * @return void
	 */
	public void removeRestrictedPaymentTerms(){
		removeAttribute("RESTRICTED_PAYMENT_TERMS");
	}

	/**
     * This method is used to get the restrictedPaymentTerms from the session
     * @return SubProductVO
     */
	public SubProductVO  getSubProductVO(){
	    return (SubProductVO)getAttribute(KEY_SUBPRODUCTVO);
	}

	/**
	 * This method is used to set the restrictedPaymentTerms in session
	 * @param subProductVO
	 */
	public void setSubProductVO(SubProductVO  subProductVO) {
	    setAttribute(KEY_SUBPRODUCTVO, (SubProductVO)subProductVO);
	}
	/**
	 * @return void
	 */
	public void removeSubProductVO(){
		removeAttribute("KEY_SUBPRODUCTVO");
	}

	 /**
     * This method is used to get the status from the session
     * @return OneTimeVO
     */
	public Collection<OneTimeVO>  getStatus(){
	    return (Collection<OneTimeVO>)getAttribute(KEY_STATUS);
	}

	/**
	 * This method is used to set the status in session
	 * @param status
	 */
	public void setStatus(Collection<OneTimeVO>  status) {
	    setAttribute(KEY_STATUS, (ArrayList<OneTimeVO>)status);
	}
	/**
	 * @return void
	 */
	public void removeStatus(){
		removeAttribute("KEY_STATUS");
	}
	 /**
     * This method is used to get the status from the session
     * @return Collection<RestrictionCommodityVO>
     */
	public Collection<RestrictionSegmentVO>  getSegmentVOs(){
	    return (Collection<RestrictionSegmentVO>)getAttribute(KEY_SEGMENTVO);
	}

	/**
	 * This method is used to set the status in session
	 * @param status
	 */
	public void setSegmentVOs(Collection<RestrictionSegmentVO>  status) {
	    setAttribute(KEY_SEGMENTVO, (ArrayList<RestrictionSegmentVO>)status);
	}
	/**
	 * @return void
	 */
	public void removeSegmentVOs(){
		removeAttribute("KEY_SEGMENTVO");
	}
	 /**
     * This method is used to get the commodityVOs from the session
     * @return Collection<RestrictionCommodityVO>
     */
	public Collection<RestrictionCommodityVO>  getCommodityVOs(){
	    return (Collection<RestrictionCommodityVO>)getAttribute(KEY_COMMODITYVO);
	}

	/**
	 * This method is used to set the commodityVOs in session
	 * @param commodityVOs
	 */
	public void setCommodityVOs(Collection<RestrictionCommodityVO>  commodityVOs) {
	    setAttribute(KEY_COMMODITYVO, (ArrayList<RestrictionCommodityVO>)commodityVOs);
	}
	/**
	 * @return void
	 */
	public void removeCommodityVOs(){
		removeAttribute("KEY_COMMODITYVO");
	}
	 /**
     * This method is used to get the status from the session
     * @return Collection<RestrictionStationVO>
     */
	public Collection<RestrictionStationVO>  getStationVOs(){
	    return (Collection<RestrictionStationVO>)getAttribute(KEY_STATIONVO);
	}

	/**
	 * This method is used to set the status in session
	 * @param stationVOs
	 */
	public void setStationVOs(Collection<RestrictionStationVO>  stationVOs) {
	    setAttribute(KEY_STATIONVO, (ArrayList<RestrictionStationVO>)stationVOs);
	}
	/**
	 * @return void
	 */
	public void removeStationVOs(){
		removeAttribute("KEY_STATIONVO");
	}
	 /**
     * This method is used to get the status from the session
     * @return RestrictionCustomerGroupVO
     */
	public Collection<RestrictionCustomerGroupVO>  getCustGroupVOs(){
	    return (Collection<RestrictionCustomerGroupVO>)getAttribute(KEY_CUSTGROUPVO);
	}

	/**
	 * This method is used to set the status in session
	 * @param custGroupVOs
	 */
	public void setCustGroupVOs(Collection<RestrictionCustomerGroupVO>  custGroupVOs) {
	    setAttribute(KEY_CUSTGROUPVO, (ArrayList<RestrictionCustomerGroupVO>)custGroupVOs);
	}
	/**
	 * @return void
	 */
	public void removeCustGroupVOs(){
		removeAttribute("KEY_CUSTGROUPVO");
	}

	 /**
     * This method is used to get the status from the session
     * @return Collection<ProductServiceVO>
     */
	public Collection<ProductServiceVO>  getProductService(){
	    return (Collection<ProductServiceVO>)getAttribute(KEY_SERVICE);
	}

	/**
	 * This method is used to set the status in session
	 * @param productService
	 */
	public void setProductService(Collection<ProductServiceVO>  productService) {
	    setAttribute(KEY_SERVICE, (ArrayList<ProductServiceVO>)productService);
	}
	/**
	 * @return void
	 */
	public void removeProductService(){
		removeAttribute("KEY_SERVICE");
	}
	/**
	 * @return Collection<ProductEventVO>
	 */
	public Collection<ProductEventVO> getProductEventVOs(){
		return (Collection<ProductEventVO>)getAttribute(PRODUCT_EVENT_VOS);
	}
	/**
	 * @param eventVOs
	 */
	public void setProductEventVOs(Collection<ProductEventVO> eventVOs){
		setAttribute(PRODUCT_EVENT_VOS, (ArrayList<ProductEventVO>)eventVOs);
	}
	/**
	 * @return void
	 */
	public void removeProductEventVOs(){
		removeAttribute(PRODUCT_EVENT_VOS);
	}
	/**
	 * @return String
	 */
	public String getNextAction(){
		return (String)getAttribute(NEXT_ACTION);
	}
	/**
	 * @param nextAction
	 */
	public void setNextAction(String nextAction){
		setAttribute(NEXT_ACTION, (String)nextAction);
	}
	/**
	 * @return void
	 */
	public void removeNextAction(){
		removeAttribute(NEXT_ACTION);
	}

	/**
	 * @return Collection<RestrictionCommodityVO>
	 */
	public Collection<RestrictionCommodityVO> getProductCommodityVOs(){
		return (Collection<RestrictionCommodityVO>)getAttribute(PRODUCT_COMMODITY_VOS);
	}
	/**
	 * @param eventVOs
	 *
	 */
	public void setProductCommodityVOs(Collection<RestrictionCommodityVO> eventVOs){
		setAttribute(PRODUCT_COMMODITY_VOS, (ArrayList<RestrictionCommodityVO>)eventVOs);
	}
	/**
	 * @return void
	 */
	public void removeProductCommodityVOs(){
		removeAttribute(PRODUCT_COMMODITY_VOS);
	}
	/**
	 *
	 * @return Collection<RestrictionSegmentVO>
	 */
	public Collection<RestrictionSegmentVO> getProductSegmentVOs(){
		return (Collection<RestrictionSegmentVO>)getAttribute(RESTRICTION_SEGMENT_VOS);
	}
	/**
	 *
	 * @param segmentVOs
	 */
	public void setProductSegmentVOs(Collection<RestrictionSegmentVO> segmentVOs){
		setAttribute(RESTRICTION_SEGMENT_VOS, (ArrayList<RestrictionSegmentVO>)segmentVOs);
	}
	/**
	 * @return void
	 */
	public void removeProductSegmentVOs(){
		removeAttribute(RESTRICTION_SEGMENT_VOS);
	}
	/**
	 * @return Collection<RestrictionPaymentTermsVO>
	 */
	public Collection<RestrictionPaymentTermsVO> getSelectedRestrictedPaymentTerms(){
		return (Collection<RestrictionPaymentTermsVO>)getAttribute(SELECED_RESTRICTION_TERMS);

	}
	/**
	 * @param restrictionPaymentTerms
	 */
	public void setSelectedRestrictedPaymentTerms
					(Collection<RestrictionPaymentTermsVO> restrictionPaymentTerms){
		setAttribute(SELECED_RESTRICTION_TERMS, (ArrayList<RestrictionPaymentTermsVO>)restrictionPaymentTerms);
	}
	/**
	 * @return void
	 */
	public void removesetSelectedRestrictedPaymentTerms(){
		removeAttribute(SELECED_RESTRICTION_TERMS);
	}



	/*public void removeAllAttributes(){
		removeWeight();
		removeVolume();
		removeRestrictedPaymentTerms();
		removeStatus();
		removeProductService();
		removeProductEventVOs();
		removeCommodityVOs();
		removeSegmentVOs();
		removeStationVOs();
		removeCustGroupVOs();
		removeSubProductVO();
		removeSegmentAfterListing();
		removesetSelectedRestrictedPaymentTerms();
		removeProductScc();
		removePriority();

	}*/
}
