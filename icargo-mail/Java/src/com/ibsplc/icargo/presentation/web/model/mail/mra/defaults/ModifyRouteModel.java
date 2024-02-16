package com.ibsplc.icargo.presentation.web.model.mail.mra.defaults;

import java.util.Collection;
import java.util.Map;


import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.spring.model.AbstractScreenModel;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.FlightDetail;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.ModifyRouteFilter;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.mra.defaults.ModifyRouteModel.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8061	:	10-Dec-2020	:	Draft
 */
public class ModifyRouteModel extends AbstractScreenModel{

	
	private Map<String, Collection<OneTimeVO>> oneTimeValues;

	
	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID = "mail.mra.defaults.ux.modifyroute";
	private Collection<FlightDetail> flightDetails ;
	private ModifyRouteFilter modifyRouteFilter;
	private String transferPA;
	private String transferAirline;
	
	
	
	@Override
	public String getProduct() {
		return PRODUCT;
	}
	
	/**
	 * 	Getter for flightDetails 
	 *	Added by : A-8061 on 10-Dec-2020
	 * 	Used for :
	 */
	public Collection<FlightDetail> getFlightDetails() {
		return flightDetails;
	}
	/**
	 *  @param flightDetails the flightDetails to set
	 * 	Setter for flightDetails 
	 *	Added by : A-8061 on 10-Dec-2020
	 * 	Used for :
	 */
	public void setFlightDetails(Collection<FlightDetail> flightDetails) {
		this.flightDetails = flightDetails;
	}
	@Override
	public String getScreenId() {
		return SCREENID;
		
	}
	@Override
	public String getSubProduct() {
		return SUBPRODUCT;
	}
	public Map<String, Collection<OneTimeVO>> getOneTimeValues() {
		return oneTimeValues;
	}
	public void setOneTimeValues(Map<String, Collection<OneTimeVO>> oneTimeValues) {
		this.oneTimeValues = oneTimeValues;
	}

	/**
	 * 	Getter for modifyRouteFilter 
	 *	Added by : A-8061 on 14-Dec-2020
	 * 	Used for :
	 */
	public ModifyRouteFilter getModifyRouteFilter() {
		return modifyRouteFilter;
	}

	/**
	 *  @param modifyRouteFilter the modifyRouteFilter to set
	 * 	Setter for modifyRouteFilter 
	 *	Added by : A-8061 on 14-Dec-2020
	 * 	Used for :
	 */
	public void setModifyRouteFilter(ModifyRouteFilter modifyRouteFilter) {
		this.modifyRouteFilter = modifyRouteFilter;
	}

	/**
	 * 	Getter for transferPA 
	 *	Added by : A-8061 on 30-Dec-2020
	 * 	Used for :
	 */
	public String getTransferPA() {
		return transferPA;
	}

	/**
	 *  @param transferPA the transferPA to set
	 * 	Setter for transferPA 
	 *	Added by : A-8061 on 30-Dec-2020
	 * 	Used for :
	 */
	public void setTransferPA(String transferPA) {
		this.transferPA = transferPA;
	}

	/**
	 * 	Getter for transferAirline 
	 *	Added by : A-8061 on 30-Dec-2020
	 * 	Used for :
	 */
	public String getTransferAirline() {
		return transferAirline;
	}

	/**
	 *  @param transferAirline the transferAirline to set
	 * 	Setter for transferAirline 
	 *	Added by : A-8061 on 30-Dec-2020
	 * 	Used for :
	 */
	public void setTransferAirline(String transferAirline) {
		this.transferAirline = transferAirline;
	}

}