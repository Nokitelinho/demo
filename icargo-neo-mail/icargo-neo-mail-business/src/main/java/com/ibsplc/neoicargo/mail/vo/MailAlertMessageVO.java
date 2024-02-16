package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.icargo.business.msgbroker.message.vo.BaseMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;

/** 
 * @author A-5991
 */
@Setter
@Getter
public class MailAlertMessageVO extends BaseMessageVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	/** 
	* The Message Types
	*/
	public static final String MAILALERT = "MAILALERT";
	/** 
	* The Party Type
	*/
	public static final String PARTY_TYPE = "US";
	/** 
	* The Party Type
	*/
	public static final String MESSAGE_STANDARD = "IFCSTD";
	/** 
	* The HEADL
	*/
	public static final String HEADL = "Mail Alert - Flight Closed";
	/** 
	* The FLTNUML
	*/
	public static final String FLTNUML = "Flight No";
	/** 
	* The Space
	*/
	public static final String SPC1 = "           ";
	/** 
	* The FLTNUMD
	*/
	public static final String FLTNUMD = "FLTNUMD";
	/** 
	* The FLTDATL
	*/
	public static final String FLTDATL = "Departure Date";
	/** 
	* The Space
	*/
	public static final String SPC2 = "      ";
	/** 
	* The FLTNUMD
	*/
	public static final String FLTDATD = "FLTDATD";
	/** 
	* The DEPTPORTL
	*/
	public static final String DEPTPORTL = "Departure Port";
	/** 
	* The SPC3
	*/
	public static final String SPC3 = "      ";
	/** 
	* The DEPTPORTD
	*/
	public static final String DEPTPORTD = "DEPTPORTD";
	/** 
	* The ROUTEL
	*/
	public static final String ROUTEL = "Route";
	/** 
	* The SPC4
	*/
	public static final String SPC4 = "               ";
	/** 
	* The ROUTED
	*/
	public static final String ROUTED = "ROUTED";
	/** 
	* The PAR1
	*/
	public static final String PAR1 = "PAR1";
	/** 
	* The CONDETL
	*/
	public static final String CONDETL = "Container Details(Container Name,POU,No of Bags,Weight)";
	/** 
	* The FLTNUMD
	*/
	public static final String UNDL1 = "-------------------------------------------------------";
	/** 
	* The CONTNAMED
	*/
	public static final String CONTNAMED = "CONTNAMED";
	/** 
	* The POUD
	*/
	public static final String POUD = "POUD";
	/** 
	* The NOBD
	*/
	public static final String NOBD = "NOBD";
	/** 
	* The WGTD
	*/
	public static final String WGTD = "WGTD";
	/** 
	* The PAR2
	*/
	public static final String PAR2 = "PAR2";
	/** 
	* The DISINFOL
	*/
	public static final String DISINFOL = "Dispatch Information(DSN,Origin OE,Dest OE,Category,No Of Bags,Weight(Unit),AWB Number,POU)";
	/** 
	* The UNDL2
	*/
	public static final String UNDL2 = "-------------------------------------------------------------------------------------------";
	/** 
	* The DSND
	*/
	public static final String DSND = "DSND";
	/** 
	* The OOED
	*/
	public static final String OOED = "OOED";
	/** 
	* The DOED
	*/
	public static final String DOED = "DOED";
	/** 
	* The CATD
	*/
	public static final String CATD = "CATD";
	/** 
	* The NOIBD
	*/
	public static final String NOIBD = "NOIBD";
	/** 
	* The WGTUNTD
	*/
	public static final String WGTUNTD = "WGTUNTD";
	/** 
	* The AWBNOD
	*/
	public static final String AWBNOD = "AWBNOD";
	/** 
	* The POUIND
	*/
	public static final String POUIND = "POUIND";
	private String flightnum;
	private ZonedDateTime departureDate;
	private String deptport;
	private String route;
	private Collection<ContainerDetailsVO> condatails;
	private String airlinecode;
	private Collection<MessageDespatchDetailsVO> messageDespatchDetailsVOs;
	private Collection<String> stations;
}
