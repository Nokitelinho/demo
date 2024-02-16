/**MaintainULDDiscrepancySessionImpl.java Created on Dec 01, 2011
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * 
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainUldDiscrepancySession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author a-4823
 *
 */
public class MaintainULDDiscrepancySessionImpl extends AbstractScreenSession implements
MaintainUldDiscrepancySession {

	private static final String SCREENID = "uld.defaults.maintainulddiscrepancies";

	private static final String MODULE = "uld.defaults";

	private static final String KEY_DETAILS = "ULDDiscrepancyVODetails";

	private static final String KEY_LIST = "ULDDiscrepancyFilterVOs";

	private static final String KEY_PAGEURL = "pageurl";

	private static final String KEY_CLOSEFLAG = "closeflag";

	private static final String KEY_ULDDISCREPANCYVOS = "uldDiscrepancyVOs";

	private static final String KEY_ULDDISCREPANCYCODE = "discrepancyCode";

	private static final String KEY_DISCREPANCYDETAILS = "discrepancyDetails";
	private static final String KEY_SCMDETAILS = "scmDetails";

	private static final String KEY_FACILITY_TYPE = "facilityType";

	private static final String KEY_ONETIMEVALUES = "oneTimeValues";

	private static final String KEY_POL_LOCATION = "polLocation";

	private static final String KEY_POU_LOCATION = "pouLocation";

	private static final String KEY_FACILITY_TYPES = "facilityTypes";

	private static final String KEY_ULDNUMBER = "uldnumber";

	private static final String KEY_MODE = "mode";
	/**
	 * @return
	 */
	public String getScreenID() {
		return SCREENID;

	}
	/**
	 * @return
	 */
	public String getModuleName() {
		return MODULE;

	}
	/**
	 * @return
	 */
	public Page<ULDDiscrepancyVO> getULDDiscrepancyVODetails() {
		return (Page<ULDDiscrepancyVO>) getAttribute(KEY_DETAILS);

	}
	/**
	 * @param productDetails
	 */
	public void setULDDiscrepancyVODetails(Page<ULDDiscrepancyVO> productDetails) {
		setAttribute(KEY_DETAILS, (Page<ULDDiscrepancyVO>) productDetails);
	}
	/**
	 * 
	 */
	public void removeULDDiscrepancyVODetails() {
		removeAttribute(KEY_DETAILS);
	}
	/**
	 * @return
	 */
	public ULDDiscrepancyFilterVO getULDDiscrepancyFilterVODetails() {
		return (ULDDiscrepancyFilterVO) getAttribute(KEY_LIST);

	}
	/**
	 * @param productDetails 
	 */
	public void setULDDiscrepancyFilterVODetails(
			ULDDiscrepancyFilterVO productDetails) {
		setAttribute(KEY_LIST, (ULDDiscrepancyFilterVO) productDetails);
	}
	/**
	 * 
	 */
	public void removeULDDiscrepancyFilterVODetails() {
		removeAttribute(KEY_LIST);
	}
	/**
	 * @return
	 */
	public String getPageURL() {
		return getAttribute(KEY_PAGEURL);
	}
	/**
	 * @param pageUrl
	 */
	public void setPageURL(String pageUrl) {
		setAttribute(KEY_PAGEURL, pageUrl);
	}
	/**
	 * @return
	 */
	public String getCloseFlag() {
		return getAttribute(KEY_CLOSEFLAG);
	}
	/**
	 * @param pageUrl
	 */
	public void setCloseFlag(String pageUrl) {
		setAttribute(KEY_CLOSEFLAG, pageUrl);
	}
	/**
	 * @return
	 */
	public ArrayList<ULDDiscrepancyVO> getULDDiscrepancyVOs() {
		return getAttribute(KEY_ULDDISCREPANCYVOS);
	}
	/**
	 * @param uldDiscrepancyVOs
	 */
	public void setULDDiscrepancyVOs(
			ArrayList<ULDDiscrepancyVO> uldDiscrepancyVOs) {
		setAttribute(KEY_ULDDISCREPANCYVOS, uldDiscrepancyVOs);
	}
	/**
	 * @return
	 */
	public ULDDiscrepancyVO getDiscrepancyDetails() {
		return (ULDDiscrepancyVO) getAttribute(KEY_DISCREPANCYDETAILS);
	}
	/**
	 * @param productDetails
	 */
	public void setDiscrepancyDetails(ULDDiscrepancyVO productDetails) {
		setAttribute(KEY_DISCREPANCYDETAILS, (ULDDiscrepancyVO) productDetails);
	}
	/**
	 * 
	 */
	public void removeDiscrepancyDetails() {
		removeAttribute(KEY_DISCREPANCYDETAILS);
	}
	/**
	 * @return
	 */
	public Collection<OneTimeVO> getDiscrepancyCode() {
		return (Collection<OneTimeVO>) getAttribute(KEY_ULDDISCREPANCYCODE);
	}
	/**
	 * @param discrepancyCode
	 */
	public void setDiscrepancyCode(Collection<OneTimeVO> discrepancyCode) {
		setAttribute(KEY_ULDDISCREPANCYCODE,
				(ArrayList<OneTimeVO>) discrepancyCode);
	}
	/**
	 * @return
	 */
	public ULDSCMReconcileDetailsVO getSCMULDReconcileDetailsVO() {
		return getAttribute(KEY_SCMDETAILS);
	}
	/**
	 * @param scmDetailsVO
	 */
	public void setSCMULDReconcileDetailsVO(
			ULDSCMReconcileDetailsVO scmDetailsVO) {
		setAttribute(KEY_SCMDETAILS, scmDetailsVO);
	}

	/**
	 * @return Returns the oneTimeValues.
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeValues() {
		return getAttribute(KEY_ONETIMEVALUES);
	}

	/**
	 * @param oneTimeValues
	 *            The oneTimeValues to set.
	 */
	public void setOneTimeValues(
			HashMap<String, Collection<OneTimeVO>> oneTimeValues) {
		setAttribute(KEY_ONETIMEVALUES, oneTimeValues);
	}
	/**
	 * @return
	 */
	public String getPolLocation() {
		return getAttribute(KEY_POL_LOCATION);
	}
	/**
	 * @param polLocation
	 */
	public void setPolLocation(String polLocation) {
		setAttribute(KEY_POL_LOCATION, polLocation);

	}
	/**
	 * @return
	 */
	public String getPouLocation() {
		return getAttribute(KEY_POU_LOCATION);
	}
	/**
	 * @param pouLocation
	 */
	public void setPouLocation(String pouLocation) {
		setAttribute(KEY_POU_LOCATION, pouLocation);
	}
	public String getFacilityType() {
		return getAttribute(KEY_FACILITY_TYPE);
	}
	public void setFacilityType(String facilityType) {
		setAttribute(KEY_FACILITY_TYPE, facilityType);
	}
	public Page<ULDDiscrepancyVO> getFacilityTypes() {
		return (Page<ULDDiscrepancyVO>)getAttribute(KEY_FACILITY_TYPES);
	}
	public void setFacilityTypes(Page<ULDDiscrepancyVO> facilityTypes) {
		setAttribute(KEY_FACILITY_TYPES, (Page<ULDDiscrepancyVO>) facilityTypes);

	}
	public String getUldNumber() {
		return getAttribute(KEY_ULDNUMBER);
	}
	public void setUldNumber(String uldNumber) {
		setAttribute(KEY_ULDNUMBER,uldNumber);

	}
	public String getMode() {
		return getAttribute(KEY_MODE);
	}
	public void setMode(String mode) {
		setAttribute(KEY_MODE,mode);

	}
}
