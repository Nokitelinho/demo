/*
 * MaintainPrivilegeSessionInterface.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults;

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
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1366
 *
 */
public interface MaintainSubProductSessionInterface extends ScreenSession {
	
	/**
	 * Methods for getting Weight
	 */
	public Collection<OneTimeVO>  getWeight();
	/**
	 * Methods for setting Weight
	 */
	public void setWeight(Collection<OneTimeVO> weightDetails);
	/**
	 * Methods for removing Weight
	 */
	public void removeWeight();
	/**
	 * Methods for getting Volume
	 */
	public Collection<OneTimeVO>  getVolume();
	/**
	 * Methods for setting  Volume
	 */
	public void setVolume(Collection<OneTimeVO> volumeDetails);
	/**
	 * Methods for removing  Volume
	 */
	public void removeVolume();
	/**
	 * Methods for getting Priority
	 */
	public Collection<OneTimeVO>  getPriority();
	/**
	 * Methods for setting priority
	 */
	public void setPriority(Collection<OneTimeVO> priority);
	/**
	 * Methods for removing priority 
	 */
	public void removePriority();
	/**
	 * Methods for getting status
	 */
	public Collection<OneTimeVO>  getStatus();
	/**
	 * Methods for setting status
	 */
	public void setStatus(Collection<OneTimeVO> status);
	/**
	 * Methods for removing status 
	 */
	public void removeStatus();
	/**
	 * Methods for getting restrictedPaymentTerms
	 */
	public Collection<RestrictionPaymentTermsVO> getRestrictedPaymentTerms();
	/**
	 * Methods for setting restrictedPaymentTerms 
	 */
	public void setRestrictedPaymentTerms(Collection<RestrictionPaymentTermsVO> restrictedPaymentTerms);
	/**
	 * Methods for removing restrictedPaymentTerms 
	 */
	public void removeRestrictedPaymentTerms();
	/**
	 * Methods for getting subProductVO
	 */
	public SubProductVO getSubProductVO();
	/**
	 * Methods for setting subProductVO
	 */
	public void setSubProductVO(SubProductVO subProductVO);
	/**
	 * Methods for removing subProductVO 
	 */
	public void removeSubProductVO();
	/**
	 * Methods for getting commodityVOs
	 */
	public Collection<RestrictionCommodityVO>  getCommodityVOs();
	/**
	 * Methods for setting commodityVOs
	 */
	public void setCommodityVOs(Collection<RestrictionCommodityVO> commodityVOs);
	/**
	 * Methods for removing commodityVOs 
	 */
	public void removeCommodityVOs();
	/**
	 * Methods for getting segmentVOs
	 */
	public Collection<RestrictionSegmentVO>  getSegmentVOs();
	/**
	 * Methods for setting segmentVOs
	 */
	public void setSegmentVOs(Collection<RestrictionSegmentVO> segmentVOs);
	/**
	 * Methods for removing  segmentVOs
	 */
	public void removeSegmentVOs();
	/**
	 * Methods for getting stationVOs
	 */
	public Collection<RestrictionStationVO>  getStationVOs();
	/**
	 * Methods for setting  stationVOs
	 */
	public void setStationVOs(Collection<RestrictionStationVO> stationVOs);
	/**
	 * Methods for removing stationVOs
	 */
	public void removeStationVOs();
	/**
	 * Methods for getting  custGroupVOs
	 */
	public Collection<RestrictionCustomerGroupVO>  getCustGroupVOs();
	/**
	 * Methods for setting custGroupVOs 
	 */
	public void setCustGroupVOs(Collection<RestrictionCustomerGroupVO> custGroupVOs);
	/**
	 * Methods for removing custGroupVOs
	 */
	public void removeCustGroupVOs();
	/**
	 * Methods for getting productServiceVO
	 */	
	public Collection<ProductServiceVO>  getProductService();
	/**
	 * Methods for setting productServiceVO
	 */
	public void setProductService(Collection<ProductServiceVO> productServiceVO);
	/**
	 * Methods for removing productServiceVO
	 */
	public void removeProductService();
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
	 * Methods for getting nextAction
	 */
	public String getNextAction();
	/**
	 * Methods for setting nextAction
	 */
	public void setNextAction(String nextAction);
	/**
	 * Methods for removing nextAction
	 */
	public void removeNextAction();
	/**
	 * Methods for comdty lov begins
	 */
	public Page<CommoditySccVO> getCommodityLovVOs();
	/**
	 * Methods for setting CommodityLovVOs
	 */
	public void setCommodityLovVOs(Page<CommoditySccVO> comdtyLovVOs);
	/**
	 * Methods for removing CommodityLovVOs
	 *
	 */
	public void removeCommodityLovVOs();
	/**
	 * Methods for getting SelectedComodityLovVOs
	 */
	public Collection<CommoditySccVO> getSelectedComodityLovVOs();
	/**
	 * Methods for setting SelectedComodityLovVOs
	 */
	public void setSelectedComodityLovVOs(Collection<CommoditySccVO> selectedComdtyLovVOs);
	/**
	 * Methods for setting SelectedComodityLovVOs
	 */
	public void removeSelectedComodityLovVOs();
	/**
	 * Methods for getting ProductCommodityVOs
	 */
	public Collection<RestrictionCommodityVO> getProductCommodityVOs();
	/**
	 * Methods for setting ProductCommodityVOs
	 */
	public void setProductCommodityVOs(Collection<RestrictionCommodityVO> eventVOs);
	/**
	 * method for removing commodityVOs
	 */
	public void removeProductCommodityVOs();
	/**
	 * method for getting scc
	 */
	public String getProductScc();
	/**
	 *  method for setting SCC
	 */
	public void setProductScc(String productScc);
	/**
	 *  method for removing scc
	 */
	public void removeProductScc();
	/**
	 * Methods for comdty lov ends
	 */
	public Collection<RestrictionPaymentTermsVO> getSelectedRestrictedPaymentTerms();
	/**
	 *  method for setting SelectedRestrictedPaymentTerms
	 */
	public void setSelectedRestrictedPaymentTerms(Collection<RestrictionPaymentTermsVO> restrictionPaymentTerms);
	/**
	 * method for  removing SelectedRestrictedPaymentTerms
	 */
	public void removesetSelectedRestrictedPaymentTerms();
	/**
	 * method for getting SegmentAfterListing
	 */
	public Collection<RestrictionSegmentVO> getSegmentAfterListing();
	/**
	 * method for setting SegmentAfterListing
	 */
	public void setSegmentAfterListing(Collection<RestrictionSegmentVO> productValidationVOs);
	/**
	 * method for SegmentAfterListing
	 */
	public void removeSegmentAfterListing();
	
}
