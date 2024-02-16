/*
 * MaintainProductSessionInterface.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults;

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
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1366
 *
 */
public interface MaintainProductSessionInterface extends ScreenSession {
	/**
	 * Methods for getting weightCode
	 */
	public Collection<OneTimeVO> getWeightCode();
	/**
	 * Methods for setting weightCode
	 */
	public void setWeightCode(Collection<OneTimeVO> weightCode);
	/**
	 * Methods for removing Weight
	 */
	public void removeWeightCode();
	/**
	 * Methods for getting volumeCode
	 */
	public Collection<OneTimeVO> getVolumeCode();
	/**
	 * Methods for setting volumeCode
	 */
	public void setVolumeCode(Collection<OneTimeVO> volumeCode);
	/**
	 * Methods for removing volumeCode
	 */
	public void removeVolumeCode();
	/**
	 * Methods for getting statusOneTime
	 */
	public Collection<OneTimeVO> getStatusOneTime();
	/**
	 * Methods for setting statusOneTime
	 */
	public void setStatusOneTime(Collection<OneTimeVO> statusOneTime);
	/**
	 * Methods for removing statusOneTime
	 */
	public void removeStatusOneTime();	
	/**
	 * Methods for getting priority
	 */
	public Collection<OneTimeVO> getPriorityOneTIme();
	/**
	 * Methods for setting priority
	 */
	public void setPriorityOneTIme(Collection<OneTimeVO> prty);
	/**
	 * Methods for removing priority
	 */
	public void removePriorityOneTIme();
	/**
	 * Methods for getting restrictionPaymentTerms
	 */
	public Collection<RestrictionPaymentTermsVO> getRestrictionPaymentTerms();
	/**
	 * Methods for setting restrictionPaymentTerms
	 */
	public void setRestrictionPaymentTerms(Collection<RestrictionPaymentTermsVO> restrictionPaymentTerms);
	/**
	 * Methods for removing restrictionPaymentTerms
	 */
	public void removeRestrictionPaymentTerms();
	/**
	 * Methods for getting transportModeVOs
	 */
	public Collection<ProductTransportModeVO> getProductTransportModeVOs();
	/**
	 * Methods for setting transportModeVOs
	 */
	public void setProductTransportModeVOs(Collection<ProductTransportModeVO> transportModeVOs);
	/**
	 * Methods for removing transportModeVOs
	 */
	public void removeProductTransportModeVOs();
	/**
	 * Methods for getting priorityVOs
	 */
	public Collection<ProductPriorityVO> getProductPriorityVOs();
	/**
	 * Methods for setting priorityVOs
	 */
	public void setProductPriorityVOs(Collection<ProductPriorityVO> priorityVOs);
	/**
	 * Methods for removing priorityVOs
	 */
	public void removeProductPriorityVOs();
	/**
	 * Methods for getting sccVOs
	 */
	public Collection<ProductSCCVO> getProductSccVOs();
	/**
	 * Methods for etting sccVOs
	 */
	public void setProductSccVOs(Collection<ProductSCCVO> sccVOs);
	/**
	 * Methods for removing sccVOs
	 */
	public void removeProductSccVOs();
	/**
	 * Methods for getting serviceVOs
	 */	
	public Collection<ProductServiceVO> getProductServiceVOs();
	/**
	 * Methods for setting serviceVOs
	 */
	public void setProductServiceVOs(Collection<ProductServiceVO> serviceVOs);
	/**
	 * Methods for removing serviceVOs
	 */
	public void removeProductServiceVOs();
	/**
	 * Methods for getting eventVOs
	 */
	public Collection<ProductEventVO> getProductEventVOs();
	/**
	 * Methods for setting eventVOs
	 */
	public void setProductEventVOs(Collection<ProductEventVO> eventVOs);
	/**
	 * Methods for removing eventVOs
	 */
	public void removeProductEventVOs();
	/**
	 * Methods for getting commodityVOs
	 */
	public Collection<RestrictionCommodityVO> getProductCommodityVOs();
	/**
	 * Methods for setting commodityVOs
	 */
	public void setProductCommodityVOs(Collection<RestrictionCommodityVO> commodityVOs);
	/**
	 * Methods for removing commodityVOs
	 */
	public void removeProductCommodityVOs();
	/**
	 * Methods for getting stationVOs
	 */
	public Collection<RestrictionStationVO> getProductStationVOs();
	/**
	 * Methods for setting stationVOs
	 */
	public void setProductStationVOs(Collection<RestrictionStationVO> stationVOs);
	/**
	 * Methods for removing stationVOs
	 */
	public void removeProductStationVOs();	
	/**
	 * Methods for getting custGrpVOs
	 */
	public Collection<RestrictionCustomerGroupVO> getProductCustGrpVOs();
	/**
	 * Methods for setting custGrpVOs
	 */
	public void setProductCustGrpVOs(Collection<RestrictionCustomerGroupVO> custGrpVOs);
	/**
	 * Methods for removing custGrpVOs
	 */
	public void removeProductCustGrpVOs();
	/**
	 * Methods for getting segmentVOs
	 */
	public Collection<RestrictionSegmentVO> getProductSegmentVOs();
	/**
	 * Methods for setting segmentVOs
	 */
	public void setProductSegmentVOs(Collection<RestrictionSegmentVO> segmentVOs);
	/**
	 * Methods for removing segmentVOs
	 */
	public void removeProductSegmentVOs();	
	/**
	 * Methods for getting restrictionPaymentTerms
	 */
	public Collection<RestrictionPaymentTermsVO> getSelectedRestrictedPaymentTerms();
	/**
	 * Methods for setting restrictionPaymentTerms
	 */
	public void setSelectedRestrictedPaymentTerms(Collection<RestrictionPaymentTermsVO> restrictionPaymentTerms);
	/**
	 * Methods for removing restrictionPaymentTerms
	 */
	public void removeSelectedRestrictedPaymentTerms();	
	/**
	 * Methods for getting productValidationVOs
	 */
	public Collection<RestrictionSegmentVO> getSegmentAfterListing();
	/**
	 * Methods for setting productValidationVOs
	 */
	public void setSegmentAfterListing(Collection<RestrictionSegmentVO> productValidationVOs);
	/**
	 * Methods for removing productValidationVOs
	 */
	public void removeSegmentAfterListing();	
	/**
	 * Methods for getting segmentVOs
	 */
	public Collection<ProductValidationVO> getProductValidationVos();
	/**
	 * Methods for setting segmentVOs
	 */
	public void setProductValidationVos(Collection<ProductValidationVO> segmentVOs);
	/**
	 * Methods for removing segmentVOs
	 */
	public void removeProductValidationVos();		
	/**
	 * Methods for getting 
	 */
	public Collection<SubProductVO> getSubProductVOs();
	/**
	 * Methods for getting 
	 */
	public void setSubProductVOs(Collection<SubProductVO> subproductVOs);
	/**
	 * Methods for removing 
	 */
	public void removeSubProductVOs();
	/**
	 * Methods for Transportmode lov begins
	 */
	public Collection<OneTimeVO> getTransModeLovVOs();
	/**
	 * Methods for getting 
	 */
	public void setTransModeLovVOs(Collection<OneTimeVO> transpModeLovVOs);
	/**
	 * Methods for removing 
	 */
	public void removeTransModeLovVOs();
	/**
	 * Methods for getting 
	 */
	public Collection<OneTimeVO> getSelectedTransModeLovVOs();
	/**
	 * Methods for getting 
	 */
	public void setSelectedTransModeLovVOs(Collection<OneTimeVO> selectedTransModeLovVOs);
	/**
	 * Methods for removing 
	 */
	public void removeSelectedTransModeLovVOs();
	/**
	 * Methods for getting 
	 */
	public String getNextAction();
	/**
	 * Methods for getting 
	 */
	public void setNextAction(String nextAction);
	/**
	 * Methods for removing 
	 */
	public void removeNextAction();
	/**
	 * Methods for Transportmode lov ends
	 */
	
	/**
	 * Methods for priority lov begins
	 */
	public Collection<OneTimeVO> getPriorityLovVOs();
	/**
	 * Methods for getting 
	 */
	public void setPriorityLovVOs(Collection<OneTimeVO> priorityLovVOs);
	/**
	 * Methods for removing 
	 */
	public void removePriorityLovVOs();
	/**
	 * Methods for getting 
	 */
	public Collection<OneTimeVO> getSelectedPriorityLovVOs();
	/**
	 * Methods for getting 
	 */
	public void setSelectedPriorityLovVOs(Collection<OneTimeVO> selectedpriorityLovVOs);
	/**
	 * Methods for removing 
	 */
	public void removeSelectedPriorityLovVOs();
	/**
	 * Methods for priority lov ends
	 */
	
	/**
	 * Methods for SCC lov begins
	 */
	public Page<SCCLovVO> getSccLovVOs();
	/**
	 * Methods for getting 
	 */
	public void setSccLovVOs(Page<SCCLovVO> sccLovVOs);
	/**
	 * Methods for removing 
	 */
	public void removeSccLovVOs();
	/**
	 * Methods for getting 
	 */
	public Collection<SCCLovVO> getSelectedSccLovVOs();
	/**
	 * Methods for getting 
	 */
	public void setSelectedSccLovVOs(Collection<SCCLovVO> selectedSccLovVOs);
	/**
	 * Methods for removing 
	 */
	public void removeSelectedSccLovVOs();
	/**
	 * Methods for Scc lov ends
	 */
	
	/**
	 * Methods for service lov begins
	 */
	public Page<ServiceLovVO> getServiceLovVOs();
	/**
	 * Methods for getting 
	 */
	public void setServiceLovVOs(Page<ServiceLovVO> serviceLovVOs);
	/**
	 * Methods for removing 
	 */
	public void removeServiceLovVO();
	/**
	 * Methods for getting 
	 */
	public Collection<ServiceLovVO> getSelectedServiceLovVOs();
	/**
	 * Methods for getting 
	 */
	public void setSelectedServiceLovVOs(Collection<ServiceLovVO> selectedSccLovVOs);
	/**
	 * Methods for removing 
	 */
	public void removeSelectedServiceLovVOs();
	
	/**
	 * Methods for service lov ends
	 */
	
	/**
	 * Methods for milestone lov begins
	 */
	public Collection<EventLovVO> getMileStoneLovVos();
	/**
	 * Methods for getting 
	 */
	public void setMileStoneLovVos(Collection<EventLovVO> milestoneLovVOs);
	/**
	 * Methods for removing 
	 */
	public void removeMileStoneLovVos();
	/**
	 * Methods for getting 
	 */
	public Collection<EventLovVO> getSelectedMileStoneLovVos();
	/**
	 * Methods for getting 
	 */
	public void setSelectedMileStoneLovVos(Collection<EventLovVO> selectedMilestoneLovVOs);
	/**
	 * Methods for removing 
	 */
	public void removeSelectedMileStoneLovVos();
	/**
	 * Methods for service lov ends
	 */
	
	/**
	 * Methods for comdty lov begins
	 */
	public Page<CommoditySccVO> getCommodityLovVOs();
	/**
	 * Methods for getting 
	 */
	public void setCommodityLovVOs(Page<CommoditySccVO> comdtyLovVOs);
	/**
	 * Methods for removing 
	 */
	public void removeCommodityLovVOs();
	/**
	 * Methods for getting 
	 */
	
	public Collection<CommoditySccVO> getSelectedComodityLovVOs();
	/**
	 * Methods for getting 
	 */
	public void setSelectedComodityLovVOs(Collection<CommoditySccVO> selectedComdtyLovVOs);
	/**
	 * Methods for removing 
	 */
	public void removeSelectedComodityLovVOs();
	
	/**
	 * Methods for comdty lov ends
	 */
	
	/**
	 * Methods for station lov begins
	 */
	public Page<AirportLovVO> getStationLovVOs();
	/**
	 * Methods for getting 
	 */
	public void setStationLovVOs(Page<AirportLovVO> stationLovVOs);
	/**
	 * Methods for removing 
	 */
	public void removeStationLovVOs();
	/**
	 * Methods for getting 
	 */
	public Collection<AirportLovVO> getSelectedStationLovVOs();
	/**
	 * Methods for setting 
	 */
	public void setSelectedStationLovVOs(Collection<AirportLovVO> selectedStationLovVOs);
	/**
	 * Methods for removing 
	 */
	public void removeSelectedStationLovVOs();
	
	/**
	 * Methods for station lov ends
	 */
	

	
	/**
	 * Methods for customer grp lov begins
	 */
	/**
	 * Methods for getting 
	 */
	public Collection<OneTimeVO> getCustGrpLovVOs();
	/**
	 * Methods for getting 
	 */
	public void setCustGrpLovVOs(Collection<OneTimeVO> custGrpLovVOs);
	/**
	 * Methods for removing 
	 */
	public void removeCustGrpLovVOs();
	/**
	 * Methods for getting selectedcustGrpLovVOs
	 */
	public Collection<OneTimeVO> getSelectedCustGrpLovVOs();
	/**
	 * Methods for setting selectedcustGrpLovVOs
	 */
	public void setSelectedCustGrpLovVOs(Collection<OneTimeVO> selectedcustGrpLovVOs);
	/**
	 * Methods for removing selectedcustGrpLovVOs
	 */
	public void removeSelectedCustGrpLovVOs();
	
	public String getBooleanForProductIcon();

	public void setBooleanForProductIcon(String prty);
	
	public void removeBooleanForProductIcon();
	
	
    public String getButtonStatusFlag();
    public void setButtonStatusFlag(String buttonStatusFlag);
    /**
	 * Methods for getting 
	 */
	public Collection<SCCLovVO> getAllSccLovVOs();
	/**
	 * Methods for getting 
	 */
	public void setAllSccLovVOs(Collection<SCCLovVO> allSccLovVOs);
	/**
	 * Methods for removing 
	 */
	public void removeALLSccLovVOs();
    
    
    /**
	 * Methods for getting 
	 */
	public Collection<AirportLovVO> getAllAirportLovVO();
	/**
	 * Methods for getting 
	 */
	public void setAllAirportLovVO(Collection<AirportLovVO> allAirportLovVO);
	/**
	 * Methods for removing 
	 */
	public void removeALLAirportLovVO();
	
	
	
	
	
	/**
	 * Methods for getting 
	 */
	public Collection<ServiceLovVO> getAllServiceLovVOs();
	/**
	 * Methods for getting 
	 */
	public void setAllServiceLovVOs(Collection<ServiceLovVO> allServiceLovVOs);
	/**
	 * Methods for removing 
	 */
	public void removeALLServiceLovVOs();
    
	
	
	
    
	
    
    
    /**
     * @author A-1944 
     * DynamicOptionList - for DocType and subType
     */
    
    /**
     * @param dynamicOptionList
     */
    public void setDynamicDocType(HashMap<String, Collection<String>> dynamicOptionList);
    
    /**
     * 
     * @return HashMap<String, Collection<String>>
     */
    public HashMap<String, Collection<String>> getDynamicDocType();
    
		
	/**
	 * Methods for customer grp lov ends
	 */
	
/*	public MaintainProductModel getMaintainProductModel();
    public void setMaintainProductModel(MaintainProductModel maintainProductModel);
    
    public CommodityLovModel getCommodityLovModel();
    public void setCommodityLovModel(CommodityLovModel commodityLovModel);
    public void removeCommodityLovModel();
    
    public CustomerGroupLovModel getCustomerGroupLovModel();
    public void setCustomerGroupLovModel(CustomerGroupLovModel customerGroupLovModel);
    public void removeCustomerGroupLovModel();
    
    public MileStoneLovModel getMileStoneLovModel();
    public void setMileStoneLovModel(MileStoneLovModel mileStoneLovModel);
    public void removeMileStoneLovModel();
    
    public PriorityLovModel getPriorityLovModel();
    public void setPriorityLovModel(PriorityLovModel priorityLovModel);
    public void removePriorityLovModel();
    
    public SccLovModel getSccLovModel();
    public void setSccLovModel(SccLovModel sccLovModel);
    public void removeSccLovModel();
    
    public ServiceLovModel getServiceLovModel();
    public void setServiceLovModel(ServiceLovModel serviceLovModel);
    public void removeServiceLovModel();
    
    public StationLovModel getStationLovModel();
    public void setStationLovModel(StationLovModel stationLovModel);
    public void removeStationLovModel();
    
    public TransportModeLovModel getTransportModeLovModel();
    public void setTransportModeLovModel(TransportModeLovModel transportModeLovModel);
    public void removeTransportModeLovModel();*/
	/**
	 * Methods for getting productVO
	 */
	public ProductVO getProductVO();
	/**
	 * Methods for setting productVO
	 */
	public void setProductVO(ProductVO productVO);
	
	/**
	  * @param weightVO weightVO
	  */
	 public void setWeightVO(UnitRoundingVO unitRoundingVO);
	 /**
	  * @return KEY_WEIGHTVO weightVO
	  */
	 public UnitRoundingVO getWeightVO() ;
	 /**
	  * @param
	  */
	 public void removeWeightVO(String key) ;	
	 
 /**
     * @param volumeVO VolumeVO
     */
    void setVolumeVO(UnitRoundingVO unitRoundingVO);

    /**
     * @return KEY_VOLUMEVO volumeVO
     */
    UnitRoundingVO getVolumeVO() ;

    /**
     * @param key String
     */
    void removeVolumeVO(String key) ;
    
    //Added for ICRD-166985 by A-5117--starts
    /**
	 * Methods for ProdcutCategories  begins
	 */
	public Collection<OneTimeVO> getProductCategories();
	/**
	 * Methods for getting 
	 */
	public void setProductCategories(Collection<OneTimeVO> productCateogries);
	/**
	 * Methods for removing 
	 */
	public void removeProductCategories();
	//Added for ICRD-166985 by A-5117-->> ends
	
	//added by A-7896 for ICRD-232462 begins
	 public void setDimensionVO(UnitRoundingVO unitRoundingVO);
	 
	 public UnitRoundingVO getDimensionVO() ;
	 
	 public void removeDimensionVO(String key) ;
	 
	 public Collection<OneTimeVO> getDimensionCode();
		/**
		 * Methods for setting DimensionCode
		 */
	public void setDimensionCode(Collection<OneTimeVO> dimensionCode);
		/**
		 * Methods for removing Dimension
		 */
	public void removeDimensionCode();
	//added by A-7896 for ICRD-232462 ends	
	/**
     * @return productParameterVOs
     */
 	public Collection<ProductParamterVO> getProductParameterVOs();
    /**
     * @param productParameterVOs
     */
 	public void setProductParameterVOs(
 			Collection<ProductParamterVO> productParameterVOs);
}
