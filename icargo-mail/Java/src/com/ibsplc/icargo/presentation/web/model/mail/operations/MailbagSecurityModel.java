package com.ibsplc.icargo.presentation.web.model.mail.operations;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.framework.web.spring.model.AbstractScreenModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ConsignmentScreeningDetail;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ConsignorDetailpopup;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ScreeningDetailpopup;
import com.ibsplc.icargo.presentation.web.model.shared.defaults.common.OneTime;

public class MailbagSecurityModel extends AbstractScreenModel {

	/*
	 * The constant variable for product mail
	 */
	private static final String PRODUCT = "mail";
	/*
	 * The constant for sub product operations
	 */
	private static final String SUBPRODUCT = "operations";
	/*
	 * The constant for screen id
	 */
	private static final String SCREENID = "mail.operations.ux.mailbagsecuritydetails";

	private String mailbagId;
	private String origin;
	private String destination;
	private long malseqnum;
	private String securityStatusCode;
	private String airportCode;
	private String loginUser;
	private boolean warningFlag;
	
	public boolean isWarningFlag() {
		return warningFlag;
	}

	public void setWarningFlag(boolean warningFlag) {
		this.warningFlag = warningFlag;
	}

	public String getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}
	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	private Collection<ConsignmentScreeningDetail> consignmentScreeningDetail;
	private Map<String, Collection<OneTime>> oneTimeValues;

	private ScreeningDetailpopup screeningDetailpopup;
	
	private ConsignorDetailpopup consignorDetailpopup;

	public long getMalseqnum() {
		return malseqnum;
	}

	public void setMalseqnum(long malseqnum) {
		this.malseqnum = malseqnum;
	}

	public ConsignorDetailpopup getConsignorDetailpopup() {
		return consignorDetailpopup;
	}

	public void setConsignorDetailpopup(ConsignorDetailpopup consignorDetailpopup) {
		this.consignorDetailpopup = consignorDetailpopup;
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

	public String getMailbagId() {
		return mailbagId;
	}

	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	
	public ScreeningDetailpopup getScreeningDetailpopup() {
		return screeningDetailpopup;
	}

	public void setScreeningDetailpopup(ScreeningDetailpopup screeningDetailpopup) {
		this.screeningDetailpopup = screeningDetailpopup;
	}

	public Collection<ConsignmentScreeningDetail> getConsignmentScreeningDetail() {
		return consignmentScreeningDetail;
	}

	public void setConsignmentScreeningDetail(Collection<ConsignmentScreeningDetail> consignmentScreeningDetail) {
		this.consignmentScreeningDetail = consignmentScreeningDetail;
	}

	public Map<String, Collection<OneTime>> getOneTimeValues() {
		return oneTimeValues;
	}

	public void setOneTimeValues(Map<String, Collection<OneTime>> oneTimeValues) {
		this.oneTimeValues = oneTimeValues;
	}
	public String getSecurityStatusCode() {
		return securityStatusCode;
	}
	public void setSecurityStatusCode(String securityStatusCode) {
		this.securityStatusCode = securityStatusCode;
	}
	private String timeZone;
	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	

}
