package com.ibsplc.neoicargo.mail.component.proxy;

import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.ULDDefaultsAsyncEProxy;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.ULDDefaultsEProxy;
import com.ibsplc.neoicargo.mail.exception.ULDDefaultsProxyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** 
 * @author A-1883RevisionHistory ------------------------------------------------------------------------- Version Date Author Description ------------------------------------------------------------------------ 0.1 Mar 8, 2007 A-1883 Created
 */
@Component
@Slf4j
public class ULDDefaultsProxy {
	@Autowired
	private ULDDefaultsEProxy uLDDefaultsEProxy;

	@Autowired
	private ULDDefaultsAsyncEProxy uldDefaultsAsyncEProxy;

	private static final String ULD_DOES_NOT_EXISTS = "uld.defaults.ulddoesnot.exists";
	private static final String ULD_IS_NOT_IN_AIRPORT = "uld.defaults.uldisnotinairport";
	private static final String ULD_IS_NOT_IN_THE_SYSTEM = "uld.defaults.uldisnotinthesystem";
	private static final String ULD_IS_NOT_OPERATIONAL = "uld.defaults.uldisnotoperational";
	private static final String ULD_DOES_NOT_EXISTS_MAIL = "mailtracking.defaults.warn.ulddoesnot.exists";
	private static final String ULD_IS_NOT_IN_AIRPORT_MAIL = "mailtracking.defaults.warn.uldisnotinairport";
	private static final String ULD_IS_NOT_IN_THE_SYSTEM_MAIL = "mailtracking.defaults.warn.uldisnotinthesystem";
	private static final String ULD_IS_NOT_OPERATIONAL_MAIL = "mailtracking.defaults.warn.uldisnotoperational";
	/** 
	* ERROR_ULDISNOTINAIRPORT
	*/
	private static final String ULD_DEFAULTS_ERROR_ULDISNOTINAIRPORT = "uld.defaults.error.uldisnotinairport";
	/** 
	* WARNING_ULDISNOTINAIRPORT
	*/
	private static final String ULD_DEFAULTS_WARNING_ULDISNOTINAIRPORT = "uld.defaults.warning.uldisnotinairport";
	/** 
	* ERROR_ULDISNOTINTHESYSTEM
	*/
	private static final String ULD_DEFAULTS_ERROR_ULDISNOTINTHESYSTEM = "uld.defaults.error.uldisnotinthesystem";
	/** 
	* WARNING_ULDISNOTINTHESYSTEM
	*/
	private static final String ULD_DEFAULTS_WARNING_ULDISNOTINTHESYSTEM = "uld.defaults.warning.uldisnotinthesystem";
	/** 
	* ERROR_ULDISNOTOPERATIONAL
	*/
	private static final String ULD_DEFAULTS_ERROR_ULDISNOTOPERATIONAL = "uld.defaults.error.uldisnotoperational";
	/** 
	* WARNING_ULDISNOTOPERATIONAL
	*/
	private static final String ULD_DEFAULTS_WARNING_ULDISNOTOPERATIONAL = "uld.defaults.warning.uldisnotoperational";
	/** 
	* ERROR_ULDISLOST
	*/
	private static final String ULD_DEFAULTS_ERROR_ULDISLOST = "uld.defaults.error.uldislost";
	/** 
	* WARNING_ULDISLOST
	*/
	private static final String ULD_DEFAULTS_WARNING_ULDISLOST = "uld.defaults.warning.uldislost";
	/** 
	* ERROR_ULDNOTINAIRLINESTOCK
	*/
	private static final String ULD_DEFAULTS_ERROR_ULDNOTINAIRLINESTOCK = "uld.defaults.error.uldnotinairlinestock";
	/** 
	* WARNING_ULDNOTINAIRLINESTOCK
	*/
	private static final String ULD_DEFAULTS_WARNING_ULDNOTINAIRLINESTOCK = "uld.defaults.warning.uldnotinairlinestock";
	/** 
	* ERROR_ULDISNOTINAIRPORT
	*/
	private static final String MAIL_DEFAULTS_ERROR_ULDISNOTINAIRPORT = "mail.defaults.error.uldisnotinairport";
	/** 
	* WARNING_ULDISNOTINAIRPORT
	*/
	private static final String MAIL_DEFAULTS_WARNING_ULDISNOTINAIRPORT = "mail.defaults.warning.uldisnotinairport";
	/** 
	* ERROR_ULDISNOTINTHESYSTEM
	*/
	private static final String MAIL_DEFAULTS_ERROR_ULDISNOTINTHESYSTEM = "mail.defaults.error.uldisnotinthesystem";
	/** 
	* WARNING_ULDISNOTINTHESYSTEM
	*/
	private static final String MAIL_DEFAULTS_WARNING_ULDISNOTINTHESYSTEM = "mail.defaults.warning.uldisnotinthesystem";
	/** 
	* ERROR_ULDISNOTOPERATIONAL
	*/
	private static final String MAIL_DEFAULTS_ERROR_ULDISNOTOPERATIONAL = "mail.defaults.error.uldisnotoperational";
	/** 
	* WARNING_ULDISNOTOPERATIONAL
	*/
	private static final String MAIL_DEFAULTS_WARNING_ULDISNOTOPERATIONAL = "mail.defaults.warning.uldisnotoperational";
	/** 
	* ERROR_ULDISLOST
	*/
	private static final String MAIL_DEFAULTS_ERROR_ULDISLOST = "mail.defaults.error.uldislost";
	/** 
	* WARNING_ULDISLOST
	*/
	private static final String MAIL_DEFAULTS_WARNING_ULDISLOST = "mail.defaults.warning.uldislost";
	/** 
	* ERROR_ULDNOTINAIRLINESTOCK
	*/
	private static final String MAIL_DEFAULTS_ERROR_ULDNOTINAIRLINESTOCK = "mail.defaults.error.uldnotinairlinestock";
	/** 
	* WARNING_ULDNOTINAIRLINESTOCK
	*/
	private static final String MAIL_DEFAULTS_WARNING_ULDNOTINAIRLINESTOCK = "mail.defaults.warning.uldnotinairlinestock";

	/** 
	* @return
	* @throws SystemException
	* @throws ULDDefaultsProxyException
	*/
	public void validateULDsForOperation(FlightDetailsVO flightDetailsVo) throws ULDDefaultsProxyException {
		log.debug("ULDDefaultsProxy" + " : " + "validateULDsForOperation" + " Entering");
		log.debug("" + "The Flight Details Vo" + " " + flightDetailsVo);
		uLDDefaultsEProxy.validateULDsForOperation(flightDetailsVo);
	}

	/** 
	* @param flightDetailsVO
	* @throws SystemException
	* @throws ULDDefaultsProxyException
	*/
	public void updateULDForOperations(FlightDetailsVO flightDetailsVO) throws ULDDefaultsProxyException {
		log.debug("" + "\n\n\nflightDetailsVO in Proxy-------->" + " " + flightDetailsVO);
		uldDefaultsAsyncEProxy.updateULDForOperations(flightDetailsVO);

	}

	/** 
	* @param companyCode
	* @param uldNumber
	* @return
	* @throws SystemException
	* @throws ULDDefaultsProxyException
	*/
	public ULDVO findULDDetails(String companyCode, String uldNumber) throws ULDDefaultsProxyException {
		try {
			return uLDDefaultsEProxy.findULDDetails(companyCode, uldNumber);
		} catch(ServiceException e){
			log.error(e.getMessage());
			throw  new ULDDefaultsProxyException();
		}
	}
}
