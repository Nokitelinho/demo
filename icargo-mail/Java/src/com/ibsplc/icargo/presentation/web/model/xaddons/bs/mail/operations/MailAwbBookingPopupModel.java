package com.ibsplc.icargo.presentation.web.model.xaddons.bs.mail.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.spring.model.ScreenModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.CarditFilter;
import com.ibsplc.icargo.presentation.web.model.shared.defaults.common.OneTime;
import com.ibsplc.icargo.presentation.web.model.xaddons.bs.mail.operations.common.AwbFilter;
import com.ibsplc.icargo.presentation.web.model.xaddons.bs.mail.operations.common.MailBookingDetail;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.xaddons.bs.mail.operations.MailAwbBookingPopupModel.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	08-Oct-2019		:	Draft
 */
public class MailAwbBookingPopupModel implements ScreenModel  {
	
	private static final String PRODUCT = "bsmail";
	private static final String SUBPRODUCT = "operations";
	private static final String SCREENID = "mail.operations.ux.mailawbbooking";
	
	private AwbFilter awbFilter;
	private Map<String, Collection<OneTime>> oneTimeValues;
	private PageResult<MailBookingDetail> mailBookingDetailsCollectionPage;
	private String displayPage;
	private String pageSize;
	private Collection<MailbagVO> selectedMailBagVOs;
	private ArrayList<MailBookingDetail> mailBookingDetailsCollection;
	private CarditFilter carditFilter;
    private String warningFlag;
	@Override
	public String getProduct() {
		return PRODUCT;
	}

	@Override
	public String getScreenId() {
		return SCREENID;
	}
	
	@Override
	public String getSubProduct() {
		return SUBPRODUCT;
	}

	public AwbFilter getAwbFilter() {
		return awbFilter;
	}

	public void setAwbFilter(AwbFilter awbFilter) {
		this.awbFilter = awbFilter;
	}

	public Map<String, Collection<OneTime>> getOneTimeValues() {
		return oneTimeValues;
	}

	public void setOneTimeValues(Map<String, Collection<OneTime>> oneTimeValues) {
		this.oneTimeValues = oneTimeValues;
	}

	public PageResult<MailBookingDetail> getMailBookingDetailsCollectionPage() {
		return mailBookingDetailsCollectionPage;
	}

	public void setMailBookingDetailsCollectionPage(PageResult<MailBookingDetail> mailBookingDetailsCollectionPage) {
		this.mailBookingDetailsCollectionPage = mailBookingDetailsCollectionPage;
	}

	public String getDisplayPage() {
		return displayPage;
	}

	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public Collection<MailbagVO> getSelectedMailBagVOs() {
		return selectedMailBagVOs;
	}

	public void setSelectedMailBagVOs(Collection<MailbagVO> selectedMailBagVOs) {
		this.selectedMailBagVOs = selectedMailBagVOs;
	}

	public ArrayList<MailBookingDetail> getMailBookingDetailsCollection() {
		return mailBookingDetailsCollection;
	}

	public void setMailBookingDetailsCollection(ArrayList<MailBookingDetail> mailBookingDetailsCollection) {
		this.mailBookingDetailsCollection = mailBookingDetailsCollection;
	}

	public CarditFilter getCarditFilter() {
		return carditFilter;
	}

	public void setCarditFilter(CarditFilter carditFilter) {
		this.carditFilter = carditFilter;
	}
	public String getWarningFlag() {
		return warningFlag;
	}
	public void setWarningFlag(String warningFlag) {
		this.warningFlag = warningFlag;
	}
	
	


}
