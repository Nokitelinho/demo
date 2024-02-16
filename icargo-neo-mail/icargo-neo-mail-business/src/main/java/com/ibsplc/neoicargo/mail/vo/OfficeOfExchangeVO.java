package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-3109
 */
@Setter
@Getter
public class OfficeOfExchangeVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	/** 
	* Variable indicating the exchange office code
	*/
	private String code;
	/** 
	* Variable storing the exchange office description
	*/
	private String codeDescription;
	/** 
	* Variable indicating the countryCode
	*/
	private String countryCode;
	/** 
	* Variable indicating the city code
	*/
	private String cityCode;
	/** 
	* Flag indicating if the data is active or not
	*/
	private boolean isActive;
	private String operationFlag;
	/** 
	* Flag indicating if the data is active or not
	*/
	private String officeCode;
	/** 
	* The postal administration code
	*/
	private String poaCode;
	private String mailboxId;
	private String companyCode;
	private ZonedDateTime lastUpdateTime;
	private String lastUpdateUser;
	private String airportCode;

	/** 
	* @param isActive The isActive to set.
	*/
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
