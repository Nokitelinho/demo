package com.ibsplc.icargo.presentation.web.model.mail.operations;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.spring.model.ScreenModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.CarditFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.DSNDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.icargo.presentation.web.model.shared.defaults.common.OneTime;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.operations.CarditDsnEnquiryModel.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	04-Sep-2019		:	Draft
 */
public class CarditDsnEnquiryModel implements ScreenModel {

	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "operations";
	private static final String SCREENID = "mail.operations.ux.carditdsnenquiry";

	private CarditFilter carditFilter;
	private Map<String, Collection<OneTime>> oneTimeValues;
	private PageResult<DSNDetails> dsnDetailsCollectionPage;
	private Collection<DSNDetails> dsnDetailsCollection;
	private Collection<Mailbag> mailbagDetailsCollection;
	private Collection<MailbagVO> mailbagVoCollection;
	private String displayPage;
	private String pageSize;
	private String airportCode;
	private String totalWeight;
	private String totalCount;
	private String mailCountFromSyspar;
	public CarditFilter getCarditFilter() {
		return carditFilter;
	}

	public void setCarditFilter(CarditFilter carditFilter) {
		this.carditFilter = carditFilter;
	}

	public Map<String, Collection<OneTime>> getOneTimeValues() {
		return oneTimeValues;
	}

	public void setOneTimeValues(Map<String, Collection<OneTime>> oneTimeValues) {
		this.oneTimeValues = oneTimeValues;
	}

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

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public PageResult<DSNDetails> getDsnDetailsCollectionPage() {
		return dsnDetailsCollectionPage;
	}

	public void setDsnDetailsCollectionPage(PageResult<DSNDetails> dsnDetailsCollectionPage) {
		this.dsnDetailsCollectionPage = dsnDetailsCollectionPage;
	}

	public String getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public Collection<DSNDetails> getDsnDetailsCollection() {
		return dsnDetailsCollection;
	}

	public void setDsnDetailsCollection(Collection<DSNDetails> dsnDetailsCollection) {
		this.dsnDetailsCollection = dsnDetailsCollection;
	}

	public Collection<Mailbag> getMailbagDetailsCollection() {
		return mailbagDetailsCollection;
	}

	public void setMailbagDetailsCollection(Collection<Mailbag> mailbagDetailsCollection) {
		this.mailbagDetailsCollection = mailbagDetailsCollection;
	}

	public Collection<MailbagVO> getMailbagVoCollection() {
		return mailbagVoCollection;
	}

	public void setMailbagVoCollection(Collection<MailbagVO> mailbagVoCollection) {
		this.mailbagVoCollection = mailbagVoCollection;
	}
	public String getMailCountFromSyspar() {
		return mailCountFromSyspar;
	}
	public void setMailCountFromSyspar(String mailCountFromSyspar) {
		this.mailCountFromSyspar = mailCountFromSyspar;
	}
	
	
	

}
