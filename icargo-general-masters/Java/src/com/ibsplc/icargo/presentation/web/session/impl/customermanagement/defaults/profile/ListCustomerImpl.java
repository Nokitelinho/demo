/*
 * ListCustomerImpl.java Created on Jun 02, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.customermanagement.defaults.profile;

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
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListCustomerSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * 
 * @author A-2046
 * 
 */
public class ListCustomerImpl extends AbstractScreenSession implements
		ListCustomerSession {

	private static final String SCREENID = "customermanagement.defaults.customerlisting";

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String KEY_CUSTOMERSTATUS = "customerstatus";
// CODE ADDED BY A-5219 FOR ICRD-18283 START
	private static final String KEY_LOCATIONTYPES = "locationtype";
	private static final String KEY_CUSTOMERTYPES = "customertype";
	// CODE ADDED BY A-5219 END
	private static final String KEY_SHIPMENTVOS = "shipmentVOs";

	private static final String KEY_CUSTOMERS = "customers";

	private static final String KEY_LOYALTYVOS = "loyaltyVos";

	private static final String KEY_CUSTOMERCODES = "customercodes";

	private static final String KEY_CUSTOMERNAMES = "customernames";

	private static final String KEY_CUSTOMERCONTACTPOINTSVOS = "customerContactPointsVO";

	private static final String KEY_CUSTOMERCONTACTVOS = "customerContactVO";

	private static final String KEY_LOYALTIES = "loyalties";
	
	private static final String KEY_INDEXMAP= "indexmap";
	
	private static final String KEY_POINTS = "points";

	private static final String KEY_LISTFILTERVO = "filterVO";

	private static final String KEY_SERVICES = "customer.redeempoints.services";

	private static final String KEY_CODE = "code";

	private static final String KEY_AWBFILTER = "awbfilter";

	private static final String KEY_AWBLOYALTY = "awbloyalty";

	private static final String KEY_POINTS_ACCRUDED = "pointsaccruded";

	private static final String KEY_POINTS_REDEEMED = "pointsredeemed";
	
	private static final String KEY_LISTEDPOINTS= "listedpoints";
	
	private static final String KEY_FILETYPE = "filetype";
	
	private static final String KEY_TOTAL_RECORDS = "totalRecords";

	/**
	 * return screen id
	 */
	public String getScreenID() {
		return SCREENID;
	}

	/**
	 * get module name
	 */
	public String getModuleName() {
		return MODULENAME;
	}

	/**
	 * @return
	 */
	public ArrayList<OneTimeVO> getCustomerStatus() {
		return getAttribute(KEY_CUSTOMERSTATUS);
	}

	/**
	 * @param customerStatus
	 */
	public void setCustomerStatus(ArrayList<OneTimeVO> customerStatus) {
		setAttribute(KEY_CUSTOMERSTATUS, customerStatus);
	}
	// CODE ADDED BY A-5219 FOR ICRD-18283 START
	/**
	 * 
	 */
	public void setLocationType(ArrayList<OneTimeVO> locationTypes) {
		setAttribute(KEY_LOCATIONTYPES, locationTypes);
	}
	/**
	 * 
	 */
	public ArrayList<OneTimeVO> getLocationType() {
		return getAttribute(KEY_LOCATIONTYPES);
	}
	// CODE ADDED BY A-5219 END
	/**
	 * @return Returns the shipmentVOs.
	 */
	public ArrayList<ShipmentVO> getShipmentVOs() {
		return getAttribute(KEY_SHIPMENTVOS);
	}

	/**
	 * @param shipmentVOs
	 *            The shipmentVOs to set.
	 */
	public void setShipmentVOs(ArrayList<ShipmentVO> shipmentVOs) {
		setAttribute(KEY_SHIPMENTVOS, shipmentVOs);
	}
/**
 * @return Page<CustomerVO>
 */
	public Page<CustomerVO> getCustomerVOs() {
		return getAttribute(KEY_CUSTOMERS);
	}
/**
 * @paramcustomers
 */
	public void setCustomerVOs(Page<CustomerVO> customers) {
		setAttribute(KEY_CUSTOMERS, customers);
	}
/**
 * 
 */
	public ArrayList<AttachLoyaltyProgrammeVO> getLoyaltyVOs() {
		return getAttribute(KEY_LOYALTYVOS);
	}
/**
 * @param loyaltyVOs
 */
	public void setLoyaltyVOs(ArrayList<AttachLoyaltyProgrammeVO> loyaltyVOs) {
		setAttribute(KEY_LOYALTYVOS, loyaltyVOs);
	}
/**
 * 
 */
	public ArrayList<String> getCustomerCodes() {
		return getAttribute(KEY_CUSTOMERCODES);
	}
/**
 * @param customercodes
 */
	public void setCustomerCodes(ArrayList<String> customercodes) {
		setAttribute(KEY_CUSTOMERCODES, customercodes);
	}
/**
 * 
 */
	public ArrayList<String> getCustomerNames() {
		return getAttribute(KEY_CUSTOMERNAMES);
	}
/**
 * @param customernames
 */
	public void setCustomerNames(ArrayList<String> customernames) {
		setAttribute(KEY_CUSTOMERNAMES, customernames);
	}
/**
 * @return Collection<CustomerContactPointsVO>
 */
	public Collection<CustomerContactPointsVO> getCustomerContactPointsVOs() {
		return (Collection<CustomerContactPointsVO>) getAttribute(KEY_CUSTOMERCONTACTPOINTSVOS);
	}
/**
 * @param customerContactPointsVO
 */
	public void setCustomerContactPointsVOs(
			Collection<CustomerContactPointsVO> customerContactPointsVO) {
		setAttribute(KEY_CUSTOMERCONTACTPOINTSVOS,
				(ArrayList<CustomerContactPointsVO>) customerContactPointsVO);
	}
/**
 * @return Collection<CustomerContactVO>
 */
	public Collection<CustomerContactVO> getCustomerContactVOs() {
		return (Collection<CustomerContactVO>) getAttribute(KEY_CUSTOMERCONTACTVOS);
	}
/**
 * @param customerContactPointsVO
 */
	public void setCustomerContactVOs(
			Collection<CustomerContactVO> customerContactPointsVO) {
		setAttribute(KEY_CUSTOMERCONTACTVOS,
				(ArrayList<CustomerContactVO>) customerContactPointsVO);
	}
/**
 * @param loyalties
 */
	public void setLoyaltyPrograms(ArrayList<String> loyalties) {
		setAttribute(KEY_LOYALTIES, loyalties);
	}
/**
 * 
 */
	public ArrayList<String> getLoyaltyPrograms() {
		return getAttribute(KEY_LOYALTIES);
	}

	/**
	 * @return 
	 */
	public HashMap<String, Collection<OneTimeVO>> getService() {
		return (HashMap<String, Collection<OneTimeVO>>) getAttribute(KEY_SERVICES);
	}

/**
 * @param status
 */
	public void setService(HashMap<String, Collection<OneTimeVO>> status) {
		setAttribute(KEY_SERVICES,
				(HashMap<String, Collection<OneTimeVO>>) status);
	}

	/**
	 * Methods for removing status
	 */
	public void removeService() {
		removeAttribute(KEY_SERVICES);
	}
/**
 * @param custCode
 */
	public void setCustomerCode(String custCode) {
		setAttribute(KEY_CODE, custCode);
	}
/**
 * 
 */
	public String getCustomerCode() {
		return getAttribute(KEY_CODE);
	}
/**
 * @param filterVO
 */
	public void setAwbFilterVO(AirWayBillLoyaltyProgramFilterVO filterVO) {
		setAttribute(KEY_AWBFILTER, filterVO);
	}
/**
 * 
 */
	public AirWayBillLoyaltyProgramFilterVO getAwbFilterVO() {
		return getAttribute(KEY_AWBFILTER);
	}
/***
 * @param loyaltyVos
 */
	public void setAwbLoyaltyVos(
			ArrayList<AirWayBillLoyaltyProgramVO> loyaltyVos) {
		setAttribute(KEY_AWBLOYALTY, loyaltyVos);
	}
/**
 * 
 */
	public ArrayList<AirWayBillLoyaltyProgramVO> getAwbLoyaltyVos() {
		return getAttribute(KEY_AWBLOYALTY);
	}
/**
 * @param index
 */
	public void setPointsAccruded(String index) {
		setAttribute(KEY_POINTS_ACCRUDED, index);
	}
/**
 * 
 */
	public String getPointsAccruded() {
		return getAttribute(KEY_POINTS_ACCRUDED);
	}
/**
 * @param index
 */
	public void setPointsRedeemed(String index) {
		setAttribute(KEY_POINTS_REDEEMED, index);
	}
/**
 * 
 */
	public String getPointsRedeemed() {
		return getAttribute(KEY_POINTS_REDEEMED);
	}
/**
 * 
 */
	public CustomerListFilterVO getListFilterVO() {
		return getAttribute(KEY_LISTFILTERVO);
	}
	/**
	 * @param filterVO
	 */
	public void setListFilterVO(CustomerListFilterVO filterVO){
		setAttribute(KEY_LISTFILTERVO,filterVO);
	}
	/**
	 * 
	 */
	public String getPointsForValidation(){
		return getAttribute(KEY_POINTS);
	}
	/**
	 * @param points
	 */
	public void setPointsForValidation(String points){
		setAttribute(KEY_POINTS,points);
		
	}
	/**
	 * 
	 */
	public String getListedPoints(){
		return getAttribute(KEY_LISTEDPOINTS);
	}
	/**
	 * @param listedPoints
	 */
	public void setListedPoints(String listedPoints){
		setAttribute(KEY_LISTEDPOINTS,listedPoints);
		}
	/**
	 * 
	 */
	public HashMap<String,String> getIndexMap(){
		return getAttribute(KEY_INDEXMAP);
	}
	/**
	 * @param indexMap
	 */
	public void setIndexMap(HashMap<String,String> indexMap){
		setAttribute(KEY_INDEXMAP,indexMap);
	}
	
	/**
	 * @return
	 */
	public ArrayList<OneTimeVO> getTSAFiletype() {
		return getAttribute(KEY_FILETYPE);
	}

	/**
	 * @param customerStatus
	 */
	public void setTSAFiletype(ArrayList<OneTimeVO> filetype) {
		setAttribute(KEY_FILETYPE, filetype);
	}
	
	
	/* Added by A-5173 for ICRD-3761 Starts */
	/**
	 * @return
	 */
	public Integer getTotalRecords(){
		return getAttribute(KEY_TOTAL_RECORDS);
	}
	/**
	 * @param totalRecords
	 */
	public void setTotalRecords(int totalRecords){
		setAttribute(KEY_TOTAL_RECORDS,totalRecords);
		}
	// CODE ADDED BY A-5219 FOR ICRD-18283 START
	/**
	 * 
	 */
	public ArrayList<OneTimeVO> getCustomerType() {
		return getAttribute(KEY_CUSTOMERTYPES);
	}
	/**
	 * 
	 */
	public void setCustomerType(ArrayList<OneTimeVO> customerTypes) {
		setAttribute(KEY_CUSTOMERTYPES, customerTypes);
	}
	// CODE ADDED BY A-5219 END
	/* Added by A-5173 for ICRD-3761 Ends */
}
