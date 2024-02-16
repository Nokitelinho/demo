package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedAirlineProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedAreaProxy;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentScreeningVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.area.city.vo.CityVO;
import com.ibsplc.icargo.business.shared.area.country.vo.CountryVO;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "MALCSGCSDDTL")
@Staleable
public class ConsignmentScreeningDetails {

	private static final String MODULE = "mail.operations";
	private static final String SOURCE = "SCREEN";
	private Log log = LogFactory.getLogger("MAIL");

    /**
     * Dummy constructor
     */
    public ConsignmentScreeningDetails() {
    	this.log.entering("ConsignmentScreeningDetails", "Dummy Constructor");
    }

    private String sourceIndicator;

    private String screeningLocation;


    private String screeningMethodCode;

    private String screeningAuthority;

    private String screeningRegulation;

    private String remarks;
    
	private int statedBags;
	
	private double statedWeight;
	
	private String additionalSecurityInfo;
	
	private Calendar securityStatusDate;
	
	private String securityStatusParty;
	
	private String source;

	private String airportCode;
	
	private String result;

	private String screenLevelValue;
	private String screenDetailType;
	private String agentType;
	private String agentID;
	private String countryCode;
	private String expiryDate;
	 private String consignmentNumber;
	 private String paCode;
	    private int consignmentSequenceNumber;
	     private String applicableRegTransportDirection;
	 	private String applicableRegBorderAgencyAuthority;
	 	private String applicableRegReferenceID;
	 	private String applicableRegFlag;
    private ConsignmentScreeningDetailsPK consignmentScreeningDetailsPK;

    private long malseqnum;
 
    private String seScreeningAuthority;
	private String seScreeningReasonCode;
	private String seScreeningRegulation;
	private long agentSerialNumber;
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
            @AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM"))})
    public ConsignmentScreeningDetailsPK getConsignmentScreeningDetailsPK() {
        return consignmentScreeningDetailsPK;
    }
    @Column(name="SCEAPLAUT")
    public String getSeScreeningAuthority() {
		return seScreeningAuthority;
	}
	public void setSeScreeningAuthority(String seScreeningAuthority) {
		this.seScreeningAuthority = seScreeningAuthority;
	}
	@Column(name="SCERSNCOD")
	public String getSeScreeningReasonCode() {
		return seScreeningReasonCode;
	}
	public void setSeScreeningReasonCode(String seScreeningReasonCode) {
		this.seScreeningReasonCode = seScreeningReasonCode;
	}
	@Column(name="SCEAPLREG")
	public String getSeScreeningRegulation() {
		return seScreeningRegulation;
	}
	public void setSeScreeningRegulation(String seScreeningRegulation) {
		this.seScreeningRegulation = seScreeningRegulation;
	}
    public void setConsignmentScreeningDetailsPK(ConsignmentScreeningDetailsPK consignmentScreeningDetailsPK) {
        this.consignmentScreeningDetailsPK = consignmentScreeningDetailsPK;
    }

    @Column(name="MALSEQNUM")
    public long getMalseqnum() {
		return malseqnum;
	}

	public void setMalseqnum(long malseqnum) {
		this.malseqnum = malseqnum;
	}

    @Column(name="SRCIND")
    public String getSourceIndicator() {
        return sourceIndicator;
    }

    public void setSourceIndicator(String sourceIndicator) {
        this.sourceIndicator = sourceIndicator;
    }

    @Column(name="SCRLOC")
    public String getScreeningLocation() {
        return screeningLocation;
    }

    public void setScreeningLocation(String screeningLocation) {
        this.screeningLocation = screeningLocation;
    }




    @Column(name="SECSCRMTHCOD")
    public String getScreeningMethodCode() {
        return screeningMethodCode;
    }

    public void setScreeningMethodCode(String screeningMethodCode) {
        this.screeningMethodCode = screeningMethodCode;
    }

    @Column(name="SCRAPLAUT")
    public String getScreeningAuthority() {
        return screeningAuthority;
    }

    public void setScreeningAuthority(String screeningAuthority) {
        this.screeningAuthority = screeningAuthority;
    }

    @Column(name="SCRAPLREG")
    public String getScreeningRegulation() {
        return screeningRegulation;
    }

    public void setScreeningRegulation(String screeningRegulation) {
        this.screeningRegulation = screeningRegulation;
    }

    @Column(name="RMK")
    public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	 @Column(name="STDBAG")
	public int getStatedBags() {
		return statedBags;
	}

	public void setStatedBags(int statedBags) {
		this.statedBags = statedBags;
	}

	 @Column(name="STDWGT")
	public double getStatedWeight() {
		return statedWeight;
	}

	public void setStatedWeight(double statedWeight) {
		this.statedWeight = statedWeight;
	}

	 @Column(name="ADLSECINF")
	public String getAdditionalSecurityInfo() {
		return additionalSecurityInfo;
	}

	public void setAdditionalSecurityInfo(String additionalSecurityInfo) {
		this.additionalSecurityInfo = additionalSecurityInfo;
	}

	 @Column(name="SECSTADAT")
	 public Calendar getSecurityStatusDate() {
			return securityStatusDate;
		}

		public void setSecurityStatusDate(Calendar securityStatusDate) {
			this.securityStatusDate = securityStatusDate;
		}

	 @Column(name="SECSTAPTY")
	public String getSecurityStatusParty() {
		return securityStatusParty;
	}

	public void setSecurityStatusParty(String securityStatusParty) {
		this.securityStatusParty = securityStatusParty;
	}
    
	 @Column(name="CSGSRC")
    public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name="ARPCOD")
    public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	
	@Column(name="SCRRES")
	public String getResult() {
		return result;
	}
public void setResult(String result) {
		this.result = result;
	}

	@Column(name="SCRLVL")
	public String getScreenLevelValue() {
		return screenLevelValue;
	}
	public void setScreenLevelValue(String screenLevelValue) {
		this.screenLevelValue = screenLevelValue;
	}
	@Column(name="SCRDTLTYP")
	public String getScreenDetailType() {
		return screenDetailType;
	}
	public void setScreenDetailType(String screenDetailType) {
		this.screenDetailType = screenDetailType;
	}
	@Column(name="AGTTYP")
	public String getAgentType() {
		return agentType;
	}
	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}
	@Column(name="AGTIDR")
	public String getAgentID() {
		return agentID;
	}
	public void setAgentID(String agentID) {
		this.agentID = agentID;
	}
	@Column(name="CNTCOD")
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	@Column(name="EXPDAT")
	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	

	@Column(name="CSGDOCNUM")
    public String getConsignmentNumber() {
		return consignmentNumber;
	}
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}
	@Column(name="POACOD")
	public String getPaCode() {
		return paCode;
	}
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	@Column(name="CSGSEQNUM")
	public int getConsignmentSequenceNumber() {
		return consignmentSequenceNumber;
	}
	public void setConsignmentSequenceNumber(int consignmentSequenceNumber) {
		this.consignmentSequenceNumber = consignmentSequenceNumber;
	}
	@Column(name="APLREGTRPDIR")
	public String getApplicableRegTransportDirection() {
		return applicableRegTransportDirection;
	}
	public void setApplicableRegTransportDirection(String applicableRegTransportDirection) {
		this.applicableRegTransportDirection = applicableRegTransportDirection;
	}
	@Column(name="APLREGBRDAGYAUT")
	public String getApplicableRegBorderAgencyAuthority() {
		return applicableRegBorderAgencyAuthority;
	}
	public void setApplicableRegBorderAgencyAuthority(String applicableRegBorderAgencyAuthority) {
		this.applicableRegBorderAgencyAuthority = applicableRegBorderAgencyAuthority;
	}
	@Column(name="APLREGREFIDR")
	public String getApplicableRegReferenceID() {
		return applicableRegReferenceID;
	}
	public void setApplicableRegReferenceID(String applicableRegReferenceID) {
		this.applicableRegReferenceID = applicableRegReferenceID;
	}
	@Column(name = "APLREGFLG")
	public String getApplicableRegFlag() {
		return applicableRegFlag;
	}
	public void setApplicableRegFlag(String applicableRegFlag) {
		this.applicableRegFlag = applicableRegFlag;
	}
	@Column(name = "AGTSERNUM")
    public long getAgentSerialNumber() {
		return agentSerialNumber;
	}
	public void setAgentSerialNumber(long agentSerialNumber) {
		this.agentSerialNumber = agentSerialNumber;
	}
    public ConsignmentScreeningDetails(ConsignmentScreeningVO consignmentScreeningVO) throws SystemException {
        populatePk(consignmentScreeningVO);
        populateAttributes(consignmentScreeningVO);
        try {
            PersistenceController.getEntityManager().persist(this);
        } catch (CreateException exception) {
            exception.getErrorCode();
            throw new SystemException(exception.getMessage(), exception);
        }
    }

    private void populatePk(ConsignmentScreeningVO consignmentScreeningVO){

        ConsignmentScreeningDetailsPK consignmentScreeningDetailsPK = new ConsignmentScreeningDetailsPK();

        consignmentScreeningDetailsPK.setCompanyCode(consignmentScreeningVO.getCompanyCode());

        this.setConsignmentScreeningDetailsPK(consignmentScreeningDetailsPK);
    }

    private void populateAttributes(ConsignmentScreeningVO consignmentScreeningVO){

        this.setSourceIndicator(consignmentScreeningVO.getSourceIndicator());
        this.setScreeningAuthority(consignmentScreeningVO.getScreeningAuthority());
        this.setScreeningMethodCode(consignmentScreeningVO.getScreeningMethodCode());
        this.setScreeningLocation(consignmentScreeningVO.getScreeningLocation());
        this.setScreeningRegulation(consignmentScreeningVO.getScreeningRegulation());
        this.setStatedBags(consignmentScreeningVO.getStatedBags());
        if(consignmentScreeningVO.getStatedWeight()!=null){
        this.setStatedWeight(consignmentScreeningVO.getStatedWeight().getSystemValue());
        }
        this.setSecurityStatusParty(consignmentScreeningVO.getSecurityStatusParty());
        if(consignmentScreeningVO.getSecurityStatusDate()!=null){
        this.setSecurityStatusDate(consignmentScreeningVO.getSecurityStatusDate().toCalendar());
        }
        this.setAdditionalSecurityInfo(consignmentScreeningVO.getAdditionalSecurityInfo());
        this.setRemarks(consignmentScreeningVO.getRemarks());
        this.setResult(consignmentScreeningVO.getResult());
        if(!consignmentScreeningVO.OPERATION_FLAG_UPDATE.equals(consignmentScreeningVO.getOpFlag())){
        if(consignmentScreeningVO.getAirportCode()!=null){
        this.setAirportCode(consignmentScreeningVO.getAirportCode());
        }
        else{
        	LogonAttributes logonVO;
			try {
				logonVO = ContextUtils.getSecurityContext().getLogonAttributesVO();
				this.setAirportCode(logonVO.getAirportCode());
			} catch (SystemException e) {
				e.getMessage();
			}
        	
        }
        }
        if(((ConsignmentScreeningVO.OPERATION_FLAG_INSERT).equals(consignmentScreeningVO.getOpFlag()))){
        	this.setSource(SOURCE);
        }
        else{
        	if(consignmentScreeningVO.getSource()!=null){
        	this.setSource(consignmentScreeningVO.getSource());
        	}
        }
        this.setPaCode(consignmentScreeningVO.getPaCode());
        this.setConsignmentNumber(consignmentScreeningVO.getConsignmentNumber());
        this.setConsignmentSequenceNumber(consignmentScreeningVO.getConsignmentSequenceNumber());
        this.setScreenLevelValue(consignmentScreeningVO.getScreenLevelValue());
        this.setScreenDetailType(consignmentScreeningVO.getSecurityReasonCode());
        this.setApplicableRegTransportDirection(consignmentScreeningVO.getApplicableRegTransportDirection());
        this.setApplicableRegBorderAgencyAuthority(consignmentScreeningVO.getApplicableRegBorderAgencyAuthority());
        this.setApplicableRegReferenceID(consignmentScreeningVO.getApplicableRegReferenceID());
        this.setApplicableRegFlag(consignmentScreeningVO.getApplicableRegFlag());
        this.setSecurityStatusDate(consignmentScreeningVO.getSecurityStatusDate());
   this.setAgentID(consignmentScreeningVO.getAgentID());
        this.setAgentType(consignmentScreeningVO.getAgentType());
        this.setCountryCode(consignmentScreeningVO.getIsoCountryCode());
        this.setExpiryDate(consignmentScreeningVO.getExpiryDate());
        this.setMalseqnum(consignmentScreeningVO.getMalseqnum());
        this.setSeScreeningAuthority(consignmentScreeningVO.getSeScreeningAuthority());
        this.setSeScreeningReasonCode(consignmentScreeningVO.getSeScreeningReasonCode());
        this.setSeScreeningRegulation(consignmentScreeningVO.getSeScreeningRegulation());
    }

    public void remove()throws SystemException{
        try {
            PersistenceController.getEntityManager().remove(this);
        } catch(RemoveException removeException) {
            removeException.getErrorCode();
            throw new SystemException(removeException.getMessage(), removeException);
        }
    }
    
    public static ConsignmentScreeningDetails find(String companyCode,long serialNumber) throws FinderException, SystemException {
		ConsignmentScreeningDetailsPK consignmentScreeningDetailsPK = new ConsignmentScreeningDetailsPK();
		consignmentScreeningDetailsPK.setCompanyCode(companyCode);
		consignmentScreeningDetailsPK.setSerialNumber(serialNumber);
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(ConsignmentScreeningDetails.class, consignmentScreeningDetailsPK);
	}

	public void update(ConsignmentScreeningVO consignmentScreeningVO) {
		populateAttributes(consignmentScreeningVO);
	}
	
	private static MailTrackingDefaultsDAO constructDAO()
			throws SystemException {
		MailTrackingDefaultsDAO mailTrackingDefaultsDAO = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mailTrackingDefaultsDAO = MailTrackingDefaultsDAO.class.cast(em
					.getQueryDAO(MODULE));
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
		return mailTrackingDefaultsDAO;
	}

	
	public static ConsignmentDocumentVO generateSecurityReport(
			String consignmentNumber,String paCode) throws SystemException {
		return constructDAO().generateSecurityReport(
				consignmentNumber,paCode);
    }	
	/**
	 * @author A-8353
	 * @param consignmentScreeningVO
	 * @return
	 * @throws SystemException
	 */
	public  long  findLatestRegAgentIssuing(ConsignmentScreeningVO consignmentScreeningVO)
    		throws SystemException{
    	return constructDAO().findLatestRegAgentIssuing(consignmentScreeningVO);
    }
	/**
	 * @author A-8353
	 * @param consignmentScreeningVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<ConsignmentScreeningVO> findScreeningMethodsForStampingRegAgentIssueMapping(ConsignmentScreeningVO consignmentScreeningVO) throws SystemException{
		return constructDAO().findScreeningMethodsForStampingRegAgentIssueMapping(consignmentScreeningVO);
	}

}
