/*
 * MaintainProductSessionImpl.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.products.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;



import com.ibsplc.icargo.business.products.defaults.vo.EventLovVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductParamterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductPriorityVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductSCCVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductServiceVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductTransportModeVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCommodityVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCustomerGroupVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionPaymentTermsVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionSegmentVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionStationVO;
import com.ibsplc.icargo.business.products.defaults.vo.SubProductVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportLovVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommoditySccVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.scc.vo.SCCLovVO;
import com.ibsplc.icargo.business.shared.service.vo.ServiceLovVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;

import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
/**
 * @author A-1366
 *
 */
public class MaintainProductSessionImpl extends AbstractScreenSession
        implements MaintainProductSessionInterface {


	//private static final String KEY_MODEL="maintainproductmodel";
	/*private static final String COMMODITY_LOV_MODEL="commoditylovmodel";
	private static final String CUSTGRP_LOV_MODEL="custgrplovmodel";
	private static final String MILESTONE_LOV_MODEL="milestonelovmodel";
	private static final String PRIORITY_LOV_MODEL="prioritylovmodel";
	private static final String SCC_LOV_MODEL="scclovmodel";
	private static final String SERVICE_LOV_MODEL="servicelovmodel";
	private static final String STATION_LOV_MODEL="stationlovmodel";
	private static final String TRAMOD_LOV_MODEL="tramodlovmodel";*/
	private static final String WEIGHT_CODE="weightcode";
	private static final String VOLUME_CODE="volumecode";
	private static final String STATUS_ONETIME="statusonetime";
	private static final String RESTRICTION_PAYMENT_TERMS="restrictionpaymentterms";
	private static final String TRANSPORT_MODE_LOV_VOS = "transportmodelovvos";
	private static final String SELECTED_TRANSPORT_MODE_LOV_VOS = "selectedtransportmodelovvos";
	private static final String NEXT_ACTION="nextaction";
	private static final String PRIORITY_LOV_VOS = "prioritylovvos";
	private static final String SELECTED_PRIORITY_LOV_VOS = "selectedprioritylovvos";
	private static final String PRODUCT_TRANSPORT_MODE_VOS = "producttransportmodevos";
	private static final String PRODUCT_PRIORITY_VOS = "productpriorityvos";
	private static final String SCC_LOV_VOS="scclovvos";
	private static final String SELCETED_SCC_LOV_VOS="selectedscclovvos";
	private static final String ALL_SCC_LOV_VOS="allscclovvos";
	private static final String ALL_SERVICE_LOV_VOS="allservicelovvos";
	
	private static final String ALL_AIRPORT_LOV_VO="allairportlovvo";
	private static final String PRODUCT_SCC_VOS="productsccvos";
	private static final String SERVICE_LOV_VOS="servicelovvos";
	private static final String SELCETED_SERVICE_LOV_VOS="selectedservicelovvos";
	private static final String PRODUCT_SERVICE_VOS="productservicevos";
	private static final String MILESTONE_LOV_VOS="milestonelovvos";
	private static final String SELCETED_MILESTONE_LOV_VOS="selectedmilestonelovvos";
	private static final String PRODUCT_EVENT_VOS="producteventvos";
	private static final String COMMODITY_LOV_VOS="commoditylovvos";
	private static final String SELECTED_COMMODITY_LOV_VOS="selectedcommoditylovvos";
	private static final String PRODUCT_COMMODITY_VOS="productcommodityvos";
	private static final String STATION_LOV_VO = "stationlovvos";
	private static final String SELECTED_STATION_LOV_VO = "selectedstationlovvo";
	private static final String RESTRICTION_STATION_VO ="restrictionstationvo";
	private static final String CUSTOMER_GROUP_LOV_VO = "customergrouplovvos";
	private static final String SELECTED_CUSTOMER_GROUP_LOV_VO = "selectedcustomergrouplovvo";
	private static final String RESTRICTION_CUSTOMER_GROUP_VO ="restrictioncustomergroupvo";
	private static final String RESTRICTION_SEGMENT_VOS = "restrictionsegmentvos";
	private static final String SELECED_RESTRICTION_TERMS = "selectedrestrictionterms";
	private static final String SEGMENT_AFTER_LISTING = "segmentafterlisting";
	private static final String PRODUCT_VALIDATION_VO = "productvalidationvo";
	private static final String SUB_PRODUCT_VO = "subproductvo";
	private static final String PRIORITY_ONE_TIME = "priorityonetime";
	private static final String GET_ICON = "producticon";
	private static final String PRODUCT_ICON = "producticoninsession";
	private static final String DYNAMIC_DOC_TYPE = "dynamic_doctype";
	private static final String KEY_BUTTONSTATUSFLAG = "ButtonStatusFlag";
	private static final String PRODUCT_VO = "productvo";
	private static final String KEY_VOLUMEVO = "sample_vol_vo";
	private static final String KEY_WEIGHTVO = "sample_wt_vo";
	private static final String KEY_DIMENSIONVO = "sample_dim_vo";
	private static final String DIMENSION_CODE="dimensioncode";
	private static final String PRDCTG_ONE_TIME = "productcategoryonetime";//Added for ICRD-166985 by A-5117

	public static final String KEY_PARAMETERVOS = "parameterVOs";
	/**
     * This method returns the SCREEN ID for the Maintain Product screen
     */
	/**
	 * @param 
	 * @return String
	 * */
    public String getScreenID(){
        return null;
    }

    /**
     * This method returns the MODULE name for the Maintain Product screen
     */
    /**
	 * @param 
	 * @return String
	 * */
    public String getModuleName(){
        return null;
    }
    /**
	 * @param 
	 * @return Collection<OneTimeVO>
	 * */
    public Collection<OneTimeVO> getWeightCode(){
    	return (Collection<OneTimeVO>)getAttribute(WEIGHT_CODE);
    }
    /**
	 * @param weightCode
	 * @return 
	 * */
	public void setWeightCode(Collection<OneTimeVO> weightCode){
		setAttribute(WEIGHT_CODE, (ArrayList<OneTimeVO>)weightCode);
	}
	 /**
	 * @param 
	 * @return 
	 * */
	public void removeWeightCode(){
		removeAttribute(WEIGHT_CODE);
	}
	/**
	 * @param 
	 * @return Collection<OneTimeVO>
	 * */
	public Collection<OneTimeVO> getVolumeCode(){
		return (Collection<OneTimeVO>)getAttribute(VOLUME_CODE);
	}
	/**
	 * @param volumeCode
	 * @return 
	 * */
	public void setVolumeCode(Collection<OneTimeVO> volumeCode){
		setAttribute(VOLUME_CODE, (ArrayList<OneTimeVO>)volumeCode);
	}
	/**
	 * @param
	 * @return 
	 * */
	public void removeVolumeCode(){
		removeAttribute(VOLUME_CODE);
	}
	/**
	 * @param 
	 * @return Collection<OneTimeVO>
	 * */
	public Collection<OneTimeVO> getStatusOneTime(){
		return (Collection<OneTimeVO>)getAttribute(STATUS_ONETIME);
	}
	/**
	 * @param statusOneTime
	 * @return 
	 * */
	public void setStatusOneTime(Collection<OneTimeVO> statusOneTime){
		setAttribute(STATUS_ONETIME, (ArrayList<OneTimeVO>)statusOneTime);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeStatusOneTime(){
		removeAttribute(STATUS_ONETIME);
	}

	/**
	 * @param 
	 * @return Collection<RestrictionPaymentTermsVO>
	 * */
	public Collection<RestrictionPaymentTermsVO> getRestrictionPaymentTerms(){
		return (Collection<RestrictionPaymentTermsVO>)getAttribute(RESTRICTION_PAYMENT_TERMS);
	}
	/**
	 * @param restrictionPaymentTerms
	 * @return 
	 * */
	public void setRestrictionPaymentTerms(Collection<RestrictionPaymentTermsVO> restrictionPaymentTerms){
		setAttribute(RESTRICTION_PAYMENT_TERMS, (ArrayList<RestrictionPaymentTermsVO>)restrictionPaymentTerms);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeRestrictionPaymentTerms(){
		removeAttribute(RESTRICTION_PAYMENT_TERMS);
	}
	/**
	 * @param 
	 * @return Collection<ProductPriorityVO>
	 * */
	public Collection<ProductPriorityVO> getProductPriorityVOs(){
		return (Collection<ProductPriorityVO>)getAttribute(PRODUCT_PRIORITY_VOS);
	}
	/**
	 * @param priorityVOs
	 * @return 
	 * */
	public void setProductPriorityVOs(Collection<ProductPriorityVO> priorityVOs){
		setAttribute(PRODUCT_PRIORITY_VOS, (ArrayList<ProductPriorityVO>)priorityVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeProductPriorityVOs(){
		removeAttribute(PRODUCT_PRIORITY_VOS);
	}
	/**
	 * @param 
	 * @return Collection<ProductTransportModeVO>
	 * */
	public Collection<ProductTransportModeVO> getProductTransportModeVOs(){
		return (Collection<ProductTransportModeVO>)getAttribute(PRODUCT_TRANSPORT_MODE_VOS);
	}
	/**
	 * @param transportModeVOs
	 * @return 
	 * */
	public void setProductTransportModeVOs(Collection<ProductTransportModeVO> transportModeVOs){
		setAttribute(PRODUCT_TRANSPORT_MODE_VOS, (ArrayList<ProductTransportModeVO>)transportModeVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeProductTransportModeVOs(){
		removeAttribute(PRODUCT_TRANSPORT_MODE_VOS);
	}

	/**
	 * @param 
	 * @return Collection<ProductSCCVO>
	 * */
	public Collection<ProductSCCVO> getProductSccVOs(){
		return (Collection<ProductSCCVO>)getAttribute(PRODUCT_SCC_VOS);
	}
	/**
	 * @param sccVOs
	 * @return
	 * */
	public void setProductSccVOs(Collection<ProductSCCVO> sccVOs){
		setAttribute(PRODUCT_SCC_VOS, (ArrayList<ProductSCCVO>)sccVOs);
	}
	/**
	 * @param 
	 * @return
	 * */
	public void removeProductSccVOs(){
		removeAttribute(PRODUCT_SCC_VOS);
	}

	/**
	 * @param 
	 * @return Collection<ProductServiceVO>
	 * */
	public Collection<ProductServiceVO> getProductServiceVOs(){
		return (Collection<ProductServiceVO>)getAttribute(PRODUCT_SERVICE_VOS);
	}
	/**
	 * @param sccVOs
	 * @return 
	 * */
	public void setProductServiceVOs(Collection<ProductServiceVO> sccVOs){
		setAttribute(PRODUCT_SERVICE_VOS, (ArrayList<ProductServiceVO>)sccVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeProductServiceVOs(){
		removeAttribute(PRODUCT_SERVICE_VOS);
	}

	/**
	 * @param 
	 * @return Collection<ProductEventVO>
	 * */
	public Collection<ProductEventVO> getProductEventVOs(){
		return (Collection<ProductEventVO>)getAttribute(PRODUCT_EVENT_VOS);
	}
	/**
	 * @param eventVOs
	 * @return 
	 * */
	public void setProductEventVOs(Collection<ProductEventVO> eventVOs){
		setAttribute(PRODUCT_EVENT_VOS, (ArrayList<ProductEventVO>)eventVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeProductEventVOs(){
		removeAttribute(PRODUCT_EVENT_VOS);
	}

	/**
	 * @param 
	 * @return Collection<RestrictionCommodityVO>
	 * */
	public Collection<RestrictionCommodityVO> getProductCommodityVOs(){
		return (Collection<RestrictionCommodityVO>)getAttribute(PRODUCT_COMMODITY_VOS);
	}
	/**
	 * @param eventVOs
	 * @return 
	 * */
	public void setProductCommodityVOs(Collection<RestrictionCommodityVO> eventVOs){
		setAttribute(PRODUCT_COMMODITY_VOS, (ArrayList<RestrictionCommodityVO>)eventVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeProductCommodityVOs(){
		removeAttribute(PRODUCT_COMMODITY_VOS);
	}
	/**
	 * @param 
	 * @return Collection<RestrictionStationVO>
	 * */
	public Collection<RestrictionStationVO> getProductStationVOs(){
		return (Collection<RestrictionStationVO>)getAttribute(RESTRICTION_STATION_VO);
	}
	/**
	 * @param stationVOs
	 * @return 
	 * */
	public void setProductStationVOs(Collection<RestrictionStationVO> stationVOs){
		setAttribute(RESTRICTION_STATION_VO, (ArrayList<RestrictionStationVO>)stationVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeProductStationVOs(){
		removeAttribute(RESTRICTION_STATION_VO);
	}

	/**
	 * @param 
	 * @return Collection<RestrictionCustomerGroupVO>
	 * */
	public Collection<RestrictionCustomerGroupVO> getProductCustGrpVOs(){
		return (Collection<RestrictionCustomerGroupVO>)getAttribute(RESTRICTION_CUSTOMER_GROUP_VO);
	}
	/**
	 * @param custGrpVOs
	 * @return 
	 * */
	public void setProductCustGrpVOs(Collection<RestrictionCustomerGroupVO> custGrpVOs){
		setAttribute(RESTRICTION_CUSTOMER_GROUP_VO, (ArrayList<RestrictionCustomerGroupVO>)custGrpVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeProductCustGrpVOs(){
		removeAttribute(RESTRICTION_CUSTOMER_GROUP_VO);
	}
	/**
	 * @param 
	 * @return Collection<RestrictionSegmentVO>
	 * */
	public Collection<RestrictionSegmentVO> getProductSegmentVOs(){
		return (Collection<RestrictionSegmentVO>)getAttribute(RESTRICTION_SEGMENT_VOS);
	}
	/**
	 * @param segmentVOs
	 * @return 
	 * */
	public void setProductSegmentVOs(Collection<RestrictionSegmentVO> segmentVOs){
		setAttribute(RESTRICTION_SEGMENT_VOS, (ArrayList<RestrictionSegmentVO>)segmentVOs);
	}
	/**
	 * @param
	 * @return 
	 * */
	public void removeProductSegmentVOs(){
		removeAttribute(RESTRICTION_SEGMENT_VOS);
	}


	/**
	 * @param 
	 * @return Collection<RestrictionPaymentTermsVO>
	 * */
	public Collection<RestrictionPaymentTermsVO> getSelectedRestrictedPaymentTerms(){
		return (Collection<RestrictionPaymentTermsVO>)getAttribute(SELECED_RESTRICTION_TERMS);

	}
	/**
	 * @param restrictionPaymentTerms
	 * @return 
	 * */
	public void setSelectedRestrictedPaymentTerms
					(Collection<RestrictionPaymentTermsVO> restrictionPaymentTerms){
		setAttribute(SELECED_RESTRICTION_TERMS, (ArrayList<RestrictionPaymentTermsVO>)restrictionPaymentTerms);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeSelectedRestrictedPaymentTerms(){
		removeAttribute(SELECED_RESTRICTION_TERMS);
	}

	/**
	 * @param 
	 * @return Collection<RestrictionSegmentVO>
	 * */
	public Collection<RestrictionSegmentVO> getSegmentAfterListing(){
		return (Collection<RestrictionSegmentVO>)getAttribute(SEGMENT_AFTER_LISTING);
	}
	/**
	 * @param segmentVOs
	 * @return 
	 * */
	public void setSegmentAfterListing(Collection<RestrictionSegmentVO> segmentVOs){
		setAttribute(SEGMENT_AFTER_LISTING, (ArrayList<RestrictionSegmentVO>)segmentVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeSegmentAfterListing(){
		removeAttribute(SEGMENT_AFTER_LISTING);
	}
	/**
	 * @param 
	 * @return Collection<ProductValidationVO>
	 * */
	public Collection<ProductValidationVO> getProductValidationVos(){
		return (Collection<ProductValidationVO>)getAttribute(PRODUCT_VALIDATION_VO);
	}
	/**
	 * @param productValidationVOs
	 * @return 
	 * */
	public void setProductValidationVos(Collection<ProductValidationVO> productValidationVOs){
		setAttribute(PRODUCT_VALIDATION_VO, (ArrayList<ProductValidationVO>)productValidationVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeProductValidationVos(){
		removeAttribute(PRODUCT_VALIDATION_VO);
	}
	/**
	 * @param 
	 * @return Collection<SubProductVO>
	 * */
	public Collection<SubProductVO> getSubProductVOs(){
		return (Collection<SubProductVO>)getAttribute(SUB_PRODUCT_VO);
	}
	/**
	 * @param subproductVOs
	 * @return 
	 * */
	public void setSubProductVOs(Collection<SubProductVO> subproductVOs){
		setAttribute(SUB_PRODUCT_VO, (ArrayList<SubProductVO>)subproductVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeSubProductVOs(){
		removeAttribute(SUB_PRODUCT_VO);
	}
    /**
     * Transport mode lov session implementation starts
     */
	/**
	 * @param 
	 * @return Collection<OneTimeVO>
	 * */
	public Collection<OneTimeVO> getTransModeLovVOs(){
		return (Collection<OneTimeVO>)getAttribute(TRANSPORT_MODE_LOV_VOS);
	}
	/**
	 * @param transpModeLovVOs
	 * @return 
	 * */
	public void setTransModeLovVOs(Collection<OneTimeVO> transpModeLovVOs){
		setAttribute(TRANSPORT_MODE_LOV_VOS, (ArrayList<OneTimeVO>)transpModeLovVOs);
	}
	/**
	 * @param 
	 * @return
	 * */
	public void removeTransModeLovVOs(){
		removeAttribute(TRANSPORT_MODE_LOV_VOS);
	}
	/**
	 * @param 
	 * @return Collection<OneTimeVO>
	 * */
	public Collection<OneTimeVO> getSelectedTransModeLovVOs(){
		return (Collection<OneTimeVO>)getAttribute(SELECTED_TRANSPORT_MODE_LOV_VOS);
	}
	/**
	 * @param selectedTransModeLovVOs
	 * @return 
	 * */
	public void setSelectedTransModeLovVOs(Collection<OneTimeVO> selectedTransModeLovVOs){
		setAttribute(SELECTED_TRANSPORT_MODE_LOV_VOS, (ArrayList<OneTimeVO>)selectedTransModeLovVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeSelectedTransModeLovVOs(){
		removeAttribute(SELECTED_TRANSPORT_MODE_LOV_VOS);
	}
	/**
	 * @param 
	 * @return String
	 * */
	public String getNextAction(){
		return (String)getAttribute(NEXT_ACTION);
	}
	/**
	 * @param nextAction
	 * @return 
	 * */
	public void setNextAction(String nextAction){
		setAttribute(NEXT_ACTION, (String)nextAction);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeNextAction(){
		removeAttribute(NEXT_ACTION);
	}

    /**
     * Transport mode lov session implementation ends
     */


	/**
	 * Methods for priority lov begins
	 */
	/**
	 * @param 
	 * @return Collection<OneTimeVO>
	 * */
	public Collection<OneTimeVO> getPriorityLovVOs(){
		return (Collection<OneTimeVO>)getAttribute(PRIORITY_LOV_VOS);
	}
	/**
	 * @param priorityLovVOs
	 * @return 
	 * */
	public void setPriorityLovVOs(Collection<OneTimeVO> priorityLovVOs){
		setAttribute(PRIORITY_LOV_VOS, (ArrayList<OneTimeVO>)priorityLovVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removePriorityLovVOs(){
		removeAttribute(PRIORITY_LOV_VOS);
	}
	/**
	 * @param 
	 * @return Collection<OneTimeVO>
	 * */
	public Collection<OneTimeVO> getSelectedPriorityLovVOs(){
		return (Collection<OneTimeVO>)getAttribute(SELECTED_PRIORITY_LOV_VOS);
	}
	/**
	 * @param selectedpriorityLovVOs
	 * @return 
	 * */
	public void setSelectedPriorityLovVOs(Collection<OneTimeVO> selectedpriorityLovVOs){
		setAttribute(SELECTED_PRIORITY_LOV_VOS, (ArrayList<OneTimeVO>)selectedpriorityLovVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeSelectedPriorityLovVOs(){
		removeAttribute(SELECTED_PRIORITY_LOV_VOS);
	}

	/**
	 * Methods for priority lov ends
	 */


	/**
	 * Methods for SCC lov begins
	 */
	/**
	 * @param 
	 * @return Page<SCCLovVO>
	 * */
	public Page<SCCLovVO> getSccLovVOs(){
		return (Page<SCCLovVO>)getAttribute(SCC_LOV_VOS);
	}
	/**
	 * @param sccLovVOs
	 * @return 
	 * */
	public void setSccLovVOs(Page<SCCLovVO> sccLovVOs){
		setAttribute(SCC_LOV_VOS, (Page<SCCLovVO>)sccLovVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeSccLovVOs(){
		removeAttribute(SCC_LOV_VOS);
	}
	
	/**
	 * @param 
	 * @return Collection<SCCLovVO>
	 * */
	public Collection<SCCLovVO> getAllSccLovVOs(){
		return (Collection<SCCLovVO>)getAttribute(ALL_SCC_LOV_VOS);
	}
	/**
	 * @param selectedSccLovVOs
	 * @return 
	 * */
	public void setAllSccLovVOs(Collection<SCCLovVO> allSccLovVOs){
		setAttribute(ALL_SCC_LOV_VOS, (ArrayList<SCCLovVO>)allSccLovVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeALLSccLovVOs(){
		removeAttribute(ALL_SCC_LOV_VOS);
	}
	
	/**
	 * @param 
	 * @return Collection<SCCLovVO>
	 * */
	public Collection<ServiceLovVO> getAllServiceLovVOs(){
		return (Collection<ServiceLovVO>)getAttribute(ALL_SERVICE_LOV_VOS);
	}
	/**
	 * @param selectedSccLovVOs
	 * @return 
	 * */
	public void setAllServiceLovVOs(Collection<ServiceLovVO> allServiceLovVOs){
		setAttribute(ALL_SERVICE_LOV_VOS, (ArrayList<ServiceLovVO>)allServiceLovVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeALLServiceLovVOs(){
		removeAttribute(ALL_SERVICE_LOV_VOS);
	}
	
	
	
	
	/**
	 * @param 
	 * @return Collection<SCCLovVO>
	 * */
	public Collection<AirportLovVO> getAllAirportLovVO(){
		return (Collection<AirportLovVO>)getAttribute(ALL_AIRPORT_LOV_VO);
	}
	/**
	 * @param selectedSccLovVOs
	 * @return 
	 * */
	public void setAllAirportLovVO(Collection<AirportLovVO> allAirportLovVO){
		setAttribute(ALL_AIRPORT_LOV_VO, (ArrayList<AirportLovVO>)allAirportLovVO);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeALLAirportLovVO(){
		removeAttribute(ALL_AIRPORT_LOV_VO);
	}
	
	
	/**
	 * @param 
	 * @return Collection<SCCLovVO>
	 * */
	public Collection<SCCLovVO> getSelectedSccLovVOs(){
		return (Collection<SCCLovVO>)getAttribute(SELCETED_SCC_LOV_VOS);
	}
	/**
	 * @param selectedSccLovVOs
	 * @return 
	 * */
	public void setSelectedSccLovVOs(Collection<SCCLovVO> selectedSccLovVOs){
		setAttribute(SELCETED_SCC_LOV_VOS, (ArrayList<SCCLovVO>)selectedSccLovVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeSelectedSccLovVOs(){
		removeAttribute(SELCETED_SCC_LOV_VOS);
	}
	/**
	 * Methods for Scc lov ends
	 */
	/**
	 * Methods for service lov begins
	 */
	
	/**
	 * @param 
	 * @return Page<ServiceLovVO>
	 */
	public Page<ServiceLovVO> getServiceLovVOs(){
		return (Page<ServiceLovVO>)getAttribute(SERVICE_LOV_VOS);
	}
	/**
	 * @param serviceLovVOs
	 * @return 
	 * */
	public void setServiceLovVOs(Page<ServiceLovVO> serviceLovVOs){
		setAttribute(SERVICE_LOV_VOS, (Page<ServiceLovVO>)serviceLovVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeServiceLovVO(){
		removeAttribute(SERVICE_LOV_VOS);
	}
	/**
	 * @param 
	 * @return Collection<ServiceLovVO>
	 * */
	public Collection<ServiceLovVO> getSelectedServiceLovVOs(){
		return (Collection<ServiceLovVO>)getAttribute(SELCETED_SERVICE_LOV_VOS);
	}
	/**
	 * @param selectedSccLovVOs
	 * @return 
	 * */
	public void setSelectedServiceLovVOs(Collection<ServiceLovVO> selectedSccLovVOs){
		setAttribute(SELCETED_SERVICE_LOV_VOS, (ArrayList<ServiceLovVO>)selectedSccLovVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeSelectedServiceLovVOs(){
		removeAttribute(SELCETED_SERVICE_LOV_VOS);
	}

	/**
	 * Methods for service lov ends
	 */

	/**
	 * Methods for milestone lov begins
	 */
	/**
	 * @param 
	 * @return Collection<EventLovVO>
	 * */
	public Collection<EventLovVO> getMileStoneLovVos(){
		return (Collection<EventLovVO>)getAttribute(MILESTONE_LOV_VOS);
	}
	/**
	 * @param milestoneLovVOs
	 * @return 
	 * */
	public void setMileStoneLovVos(Collection<EventLovVO> milestoneLovVOs){
		setAttribute(MILESTONE_LOV_VOS, (ArrayList<EventLovVO>)milestoneLovVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeMileStoneLovVos(){
		removeAttribute(MILESTONE_LOV_VOS);
	}
	/**
	 * @param 
	 * @return Collection<EventLovVO> 
	 * */
	public Collection<EventLovVO> getSelectedMileStoneLovVos(){
		return (Collection<EventLovVO>)getAttribute(SELCETED_MILESTONE_LOV_VOS);
	}
	/**
	 * @param selectedMilestoneLovVOs
	 * @return 
	 * */
	public void setSelectedMileStoneLovVos(Collection<EventLovVO> selectedMilestoneLovVOs){
		setAttribute(SELCETED_MILESTONE_LOV_VOS, (ArrayList<EventLovVO>)selectedMilestoneLovVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeSelectedMileStoneLovVos(){
		removeAttribute(SELCETED_MILESTONE_LOV_VOS);
	}

	/**
	 * Methods for service lov ends
	 */

	/**
	 * Methods for comdty lov begins
	 */
	/**
	 * @param 
	 * @return Page<CommoditySccVO>
	 * */
	public Page<CommoditySccVO> getCommodityLovVOs(){
		return (Page<CommoditySccVO>)getAttribute(COMMODITY_LOV_VOS);
	}
	/**
	 * @param comdtyLovVOs
	 * @return 
	 * */
	public void setCommodityLovVOs(Page<CommoditySccVO> comdtyLovVOs){
		setAttribute(COMMODITY_LOV_VOS, (Page<CommoditySccVO>)comdtyLovVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeCommodityLovVOs(){
		removeAttribute(COMMODITY_LOV_VOS);
	}
	/**
	 * @param 
	 * @return Collection<CommoditySccVO>
	 * */
	public Collection<CommoditySccVO> getSelectedComodityLovVOs(){
		return (Collection<CommoditySccVO>)getAttribute(SELECTED_COMMODITY_LOV_VOS);
	}
	/**
	 * @param selectedComdtyLovVOs
	 * @return 
	 * */
	public void setSelectedComodityLovVOs(Collection<CommoditySccVO> selectedComdtyLovVOs){
		setAttribute(SELECTED_COMMODITY_LOV_VOS, (ArrayList<CommoditySccVO>)selectedComdtyLovVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeSelectedComodityLovVOs(){
		removeAttribute(SELECTED_COMMODITY_LOV_VOS);
	}

	/**
	 * Methods for comdty lov ends
	 */
	/**
	 * Methods for station lov begins
	 */
	/**
	 * @param 
	 * @return Page<AirportLovVO>
	 * */
	public Page<AirportLovVO> getStationLovVOs(){
		return (Page<AirportLovVO>)getAttribute(STATION_LOV_VO);
	}
	/**
	 * @param stationLovVOs
	 * @return
	 * */
	public void setStationLovVOs(Page<AirportLovVO> stationLovVOs){
		setAttribute(STATION_LOV_VO, (Page<AirportLovVO>)stationLovVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeStationLovVOs(){
		removeAttribute(STATION_LOV_VO);
	}
	/**
	 * @param 
	 * @return Collection<AirportLovVO>
	 * */
	public Collection<AirportLovVO> getSelectedStationLovVOs(){
		return (Collection<AirportLovVO>)getAttribute(SELECTED_STATION_LOV_VO);
	}
	/**
	 * @param selectedStationLovVOs
	 * @return 
	 * */
	public void setSelectedStationLovVOs(Collection<AirportLovVO> selectedStationLovVOs){
		setAttribute(SELECTED_STATION_LOV_VO, (ArrayList<AirportLovVO>)selectedStationLovVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeSelectedStationLovVOs(){
		removeAttribute(SELECTED_STATION_LOV_VO);
	}

	/**
	 * Methods for station lov ends
	 */


	/**
	 * Methods for customer grp lov begins
	 */
	/**
	 * @param 
	 * @return Collection<OneTimeVO> 
	 * */
	public Collection<OneTimeVO> getCustGrpLovVOs(){
		return (Collection<OneTimeVO>)getAttribute(CUSTOMER_GROUP_LOV_VO);
	}
	/**
	 * @param custGrpLovVOs
	 * @return 
	 * */
	public void setCustGrpLovVOs(Collection<OneTimeVO> custGrpLovVOs){
		setAttribute(CUSTOMER_GROUP_LOV_VO, (ArrayList<OneTimeVO>)custGrpLovVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeCustGrpLovVOs(){
		removeAttribute(CUSTOMER_GROUP_LOV_VO);
	}
	/**
	 * @param 
	 * @return Collection<OneTimeVO>
	 * */
	public Collection<OneTimeVO> getSelectedCustGrpLovVOs(){
		return (Collection<OneTimeVO>)getAttribute(SELECTED_CUSTOMER_GROUP_LOV_VO);
	}
	/**
	 * @param selectedcustGrpLovVOs
	 * @return 
	 * */
	public void setSelectedCustGrpLovVOs(Collection<OneTimeVO> selectedcustGrpLovVOs){
		setAttribute(SELECTED_CUSTOMER_GROUP_LOV_VO, (ArrayList<OneTimeVO>)selectedcustGrpLovVOs);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeSelectedCustGrpLovVOs(){
		removeAttribute(SELECTED_CUSTOMER_GROUP_LOV_VO);
	}




	/**
	 * Methods for customer grp lov ends
	 */
	/**
	 * @param 
	 * @return Collection<OneTimeVO>
	 * */

	public Collection<OneTimeVO> getPriorityOneTIme(){
		return (Collection<OneTimeVO>)getAttribute(PRIORITY_ONE_TIME);
	}
	/**
	 * @param prty
	 * @return 
	 * */
	public void setPriorityOneTIme(Collection<OneTimeVO> prty){
		setAttribute(PRIORITY_ONE_TIME, (ArrayList<OneTimeVO>)prty);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removePriorityOneTIme(){
		removeAttribute(PRIORITY_ONE_TIME);
	}
	/**
	 * @param 
	 * @return String
	 * */
	public String getBooleanForProductIcon(){
		return (String)getAttribute(GET_ICON);
	}
	/**
	 * @param prty
	 * @return 
	 * */
	public void setBooleanForProductIcon(String prty){
		setAttribute(GET_ICON, (String)prty);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeBooleanForProductIcon(){
		removeAttribute(GET_ICON);
	}

	
	public String  getButtonStatusFlag(){
	    return (String)getAttribute(KEY_BUTTONSTATUSFLAG);
	}
	public void setButtonStatusFlag(String  buttonStatusFlag) {
	    setAttribute(KEY_BUTTONSTATUSFLAG, (String)buttonStatusFlag);
	}
	
	/*public void removeAllAttributes(){
		removeWeightCode();
		removeVolumeCode();
		removeRestrictionPaymentTerms();
		removeStatusOneTime();
		removeProductTransportModeVOs();
		removeProductPriorityVOs();
		removeProductSccVOs();
		removeProductServiceVOs();
		removeProductEventVOs();
		removeProductCommodityVOs();
		removeProductSegmentVOs();
		removeProductStationVOs();
		removeProductCustGrpVOs();
		removeSubProductVOs();
		removeProductValidationVos();
		removePriorityOneTIme();
	}*/
	
    /**
     * @author A-1944 
     * DynamicOptionList - for DocType and subType
     */
    
    /**
     * @param dynamicOptionList
     */
    public void setDynamicDocType(HashMap<String, Collection<String>> dynamicOptionList){
    	setAttribute(DYNAMIC_DOC_TYPE, dynamicOptionList);
    }
    
    /**
     * 
     * @return HashMap<String, Collection<String>>
     */
    public HashMap<String, Collection<String>> getDynamicDocType(){
    	return getAttribute(DYNAMIC_DOC_TYPE);
    }
    
	/**
	 * @param 
	 * @return ProductVO
	 */
	public ProductVO getProductVO(){
		return (ProductVO)getAttribute(PRODUCT_VO);
	}
	/**
	 * @param productVO
	 * @return 
	 * */
	public void setProductVO(ProductVO productVO){
		setAttribute(PRODUCT_VO, (ProductVO)productVO);
	}

	public UnitRoundingVO getWeightVO() {
		return getAttribute(KEY_WEIGHTVO);
	}
	public void removeWeightVO(String key) {

		removeAttribute(KEY_WEIGHTVO);	
	}
	public void setWeightVO(UnitRoundingVO unitRoundingVO) {

		setAttribute(KEY_WEIGHTVO,unitRoundingVO);
	}	
	public void setVolumeVO(UnitRoundingVO unitRoundingVO) {
    	setAttribute(KEY_VOLUMEVO, unitRoundingVO);
    }

    /**
     * @return KEY_VOLUMEVO volumeVO
     */
    public UnitRoundingVO getVolumeVO() {
    	return getAttribute(KEY_VOLUMEVO);
    }
    /**
     * @param key String
     */
    public void removeVolumeVO(String key) {
	}
    
    //Added for ICRD-166985 by A-5117 --starts
    public Collection<OneTimeVO> getProductCategories(){
		return (Collection<OneTimeVO>)getAttribute(PRDCTG_ONE_TIME);
	}
	/**
	 * @param prty
	 * @return 
	 * */
	public void setProductCategories(Collection<OneTimeVO> productCategories){
		setAttribute(PRDCTG_ONE_TIME, (ArrayList<OneTimeVO>)productCategories);
	}
	/**
	 * @param 
	 * @return 
	 * */
	public void removeProductCategories(){
		removeAttribute(PRDCTG_ONE_TIME);
	}
	//Added for ICRD-166985 by A-5117 -->> ends
	
	//added as part of CR- ICRD-232462 begins
	/**
	 * 	Getter for keyDimensionvo 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :ICRD-232462
	 */
	public UnitRoundingVO getDimensionVO() {
		return getAttribute(KEY_DIMENSIONVO);
	}
	
	public void setDimensionVO(UnitRoundingVO unitRoundingVO) {

		setAttribute(KEY_DIMENSIONVO,unitRoundingVO);
	}
	public void removeDimensionVO(String key) {

		removeAttribute(KEY_DIMENSIONVO);	
	}
	public Collection<OneTimeVO> getDimensionCode(){
	    	return (Collection<OneTimeVO>)getAttribute(DIMENSION_CODE);
	    }
	    /**
		 * @param dimensionCode
		 * @return 
		 * */
	public void setDimensionCode(Collection<OneTimeVO> dimensionCode){
			setAttribute(DIMENSION_CODE, (ArrayList<OneTimeVO>)dimensionCode);
		}
	
	public void removeDimensionCode(){
		removeAttribute(DIMENSION_CODE);
	}
	//added as part of CR- ICRD-232462 ends
	/**
     * @return stationParameterVOs
     */
 	public Collection<ProductParamterVO> getProductParameterVOs() {
 		return (Collection<ProductParamterVO>)getAttribute(KEY_PARAMETERVOS);
    }
    /**
     * @param stationParameterVOs
     */
 	public void setProductParameterVOs(Collection<ProductParamterVO> productParameterVOs){
 		setAttribute(KEY_PARAMETERVOS,(
 				ArrayList<ProductParamterVO>)productParameterVOs);
 	}
}
