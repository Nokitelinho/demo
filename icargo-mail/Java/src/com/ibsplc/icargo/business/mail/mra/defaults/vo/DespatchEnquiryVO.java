/*
 * DespatchEnquiryVO.java created onJul 10,2008
 *Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */

package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2391
 * 
 */
public class DespatchEnquiryVO extends AbstractVO {

	private String companyCode;

	private String csgDocNo;

	private String origin;
	
	private String destn;
	
	private String category;
	
	private String desClass;
	
	private String subClass;
	
	private int pieces;
	 
	private double weight;
	
	private LocalDate despatchDate;
	
	private String strDespatchDate;
	
	private String route;
	
	private String gpaCode;
	
	private String gpaName;
	
	private String currency;
	
	private int year;
	
	 private Collection<GPABillingDetailsVO> gpaBillingDetails;

	/**
	 * @return Returns the category.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category The category to set.
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the csgDocNo.
	 */
	public String getCsgDocNo() {
		return csgDocNo;
	}

	/**
	 * @param csgDocNo The csgDocNo to set.
	 */
	public void setCsgDocNo(String csgDocNo) {
		this.csgDocNo = csgDocNo;
	}

	/**
	 * @return Returns the currency.
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency The currency to set.
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return Returns the desClass.
	 */
	public String getDesClass() {
		return desClass;
	}

	/**
	 * @param desClass The desClass to set.
	 */
	public void setDesClass(String desClass) {
		this.desClass = desClass;
	}

	/**
	 * @return Returns the despatchDate.
	 */
	public LocalDate getDespatchDate() {
		return despatchDate;
	}

	/**
	 * @param despatchDate The despatchDate to set.
	 */
	public void setDespatchDate(LocalDate despatchDate) {
		this.despatchDate = despatchDate;
	}

	/**
	 * @return Returns the destn.
	 */
	public String getDestn() {
		return destn;
	}

	/**
	 * @param destn The destn to set.
	 */
	public void setDestn(String destn) {
		this.destn = destn;
	}

	/**
	 * @return Returns the gpaCode.
	 */
	public String getGpaCode() {
		return gpaCode;
	}

	/**
	 * @param gpaCode The gpaCode to set.
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	/**
	 * @return Returns the gpaName.
	 */
	public String getGpaName() {
		return gpaName;
	}

	/**
	 * @param gpaName The gpaName to set.
	 */
	public void setGpaName(String gpaName) {
		this.gpaName = gpaName;
	}

	/**
	 * @return Returns the origin.
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin The origin to set.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return Returns the pieces.
	 */
	public int getPieces() {
		return pieces;
	}

	/**
	 * @param pieces The pieces to set.
	 */
	public void setPieces(int pieces) {
		this.pieces = pieces;
	}

	/**
	 * @return Returns the route.
	 */
	public String getRoute() {
		return route;
	}

	/**
	 * @param route The route to set.
	 */
	public void setRoute(String route) {
		this.route = route;
	}

	/**
	 * @return Returns the subClass.
	 */
	public String getSubClass() {
		return subClass;
	}

	/**
	 * @param subClass The subClass to set.
	 */
	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}

	/**
	 * @return Returns the weight.
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight The weight to set.
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return Returns the year.
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year The year to set.
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return Returns the gpaBillingDetails.
	 */
	public Collection<GPABillingDetailsVO> getGpaBillingDetails() {
		return gpaBillingDetails;
	}

	/**
	 * @param gpaBillingDetails The gpaBillingDetails to set.
	 */
	public void setGpaBillingDetails(
			Collection<GPABillingDetailsVO> gpaBillingDetails) {
		this.gpaBillingDetails = gpaBillingDetails;
	}

	/**
	 * @return the strDespatchDate
	 */
	public String getStrDespatchDate() {
		return strDespatchDate;
	}

	/**
	 * @param strDespatchDate the strDespatchDate to set
	 */
	public void setStrDespatchDate(String strDespatchDate) {
		this.strDespatchDate = strDespatchDate;
	}
	



}
