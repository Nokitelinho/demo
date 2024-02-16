/*
 * ListCustomerSession.java Created on Jun 02, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AirWayBillLoyaltyProgramFilterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AirWayBillLoyaltyProgramVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AttachLoyaltyProgrammeVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerListFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactPointsVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * 
 * @author A-2046
 * 
 */
public interface ListCustomerSession extends ScreenSession {

	/**
	 * 
	 * @return
	 */
	public String getScreenID();

	/**
	 * 
	 * @return
	 */
	public String getModuleName();

	/**
	 * 
	 * @return
	 */
	public ArrayList<OneTimeVO> getCustomerStatus();
	/**
	 * 
	 * @return
	 */ 
	
	// CODE ADDED BY A-5219 FOR ICRD-18283 START
	public ArrayList<OneTimeVO> getLocationType();
	/**
	 * 
	 * @param locationTypes
	 */
	public void setLocationType(ArrayList<OneTimeVO> locationTypes);
	/**
	 * 
	 * @return
	 */ 
	public ArrayList<OneTimeVO> getCustomerType();
	/**
	 * 
	 * @param locationTypes
	 */
	public void setCustomerType(ArrayList<OneTimeVO> customerTypes);
	/**
	 * 
	 * @param agreementStatus
	 */
	
	//CODE ADDED BY A-5219 END
	public void setCustomerStatus(ArrayList<OneTimeVO> customerStatus);

	/**
	 * @return Returns the shipmentVOs.
	 */
	public ArrayList<ShipmentVO> getShipmentVOs();

	/**
	 * @param shipmentVOs
	 *            The shipmentVOs to set.
	 */
	public void setShipmentVOs(ArrayList<ShipmentVO> shipmentVOs);

	/**
	 * 
	 * @return
	 */
	public Page<CustomerVO> getCustomerVOs();

	/**
	 * 
	 * @param customerVOs
	 */
	public void setCustomerVOs(Page<CustomerVO> customerVOs);

	/**
	 * 
	 * @return
	 */
	public ArrayList<AttachLoyaltyProgrammeVO> getLoyaltyVOs();

	/**
	 * 
	 * @param loyaltyVOs
	 */
	public void setLoyaltyVOs(ArrayList<AttachLoyaltyProgrammeVO> loyaltyVOs);

	public ArrayList<String> getCustomerCodes();

	public void setCustomerCodes(ArrayList<String> customercodes);

	public ArrayList<String> getCustomerNames();

	public void setCustomerNames(ArrayList<String> customernames);

	public Collection<CustomerContactPointsVO> getCustomerContactPointsVOs();

	public void setCustomerContactPointsVOs(
			Collection<CustomerContactPointsVO> customerContactPointsVO);

	public Collection<CustomerContactVO> getCustomerContactVOs();

	public void setCustomerContactVOs(
			Collection<CustomerContactVO> customerContactPointsVO);

	public ArrayList<String> getLoyaltyPrograms();

	public void setLoyaltyPrograms(ArrayList<String> loyalties);

	public HashMap<String, Collection<OneTimeVO>> getService();

	public void setService(HashMap<String, Collection<OneTimeVO>> status);

	public void removeService();

	public String getCustomerCode();

	public void setCustomerCode(String custCode);

	public void setAwbFilterVO(AirWayBillLoyaltyProgramFilterVO filterVO);

	public AirWayBillLoyaltyProgramFilterVO getAwbFilterVO();
	
	public void setAwbLoyaltyVos(ArrayList<AirWayBillLoyaltyProgramVO> loyaltyVos);
	
	public ArrayList<AirWayBillLoyaltyProgramVO> getAwbLoyaltyVos();
	
	public void setPointsAccruded(String index);
	
	public String getPointsAccruded();
	
	public void setPointsRedeemed(String index);
	
	public String getPointsRedeemed();
	/**
	 * 
	 * @return
	 */
	public CustomerListFilterVO getListFilterVO();
	/**
	 * 
	 * @param filterVO
	 */
	public void setListFilterVO(CustomerListFilterVO filterVO);
	/**
	 * 
	 * @return
	 */
	public String getPointsForValidation();
	/**
	 * 
	 * @param points
	 */
	public void setPointsForValidation(String points);
	/**
	 * 
	 * @return
	 */
	public String getListedPoints();
	/**
	 * 
	 *
	 */
	public void setListedPoints(String listedPoints);
	
	public HashMap<String,String> getIndexMap();
	
	public void setIndexMap(HashMap<String,String> indexMap);
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<OneTimeVO> getTSAFiletype();

	/**
	 * 
	 * @param TSAFileType
	 */
	public void setTSAFiletype(ArrayList<OneTimeVO> filetype);
	
	/* Added by A-5173 for ICRD-3761 Starts */
	
	public Integer getTotalRecords();
	
	public void setTotalRecords(int totalRecords);
	
	
	/* Added by A-5173 for ICRD-3761 Ends */

	
}
