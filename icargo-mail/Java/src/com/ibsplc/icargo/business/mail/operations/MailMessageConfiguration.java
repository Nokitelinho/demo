package com.ibsplc.icargo.business.mail.operations;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.ibsplc.icargo.business.mail.operations.vo.MailRuleConfigParameterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailRuleConfigVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * 
 * @author A-8923
 * Entity for storing mail related message configuration details.
 */
@Entity
@Table(name = "MALMSGCFG")
public class MailMessageConfiguration {

	private static final String MODULE = "mail.operations";
	private static Log log = LogFactory.getLogger("MAIL_operations");
	private MailMessageConfigurationPK mailMessageConfigurationPK;
	private String mailboxId;
	private String status;
	private Calendar fromDate;
	private Calendar toDate;
	private Set<MailMessageConfigurationParameter> mailMessageConfigurationParameters;
	
	/**
	 * @return the mailMessageConfigurationPK
	 */
	@EmbeddedId
	@AttributeOverrides({
    @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
    @AttributeOverride(name="messageConfigurationSequenceNumber", column=@Column(name="MSGCFGSEQNUM"))} )
  	public MailMessageConfigurationPK getMailMessageConfigurationPK() {
		return mailMessageConfigurationPK;
	}
	/**
	 * @param mailMessageConfigurationPK the mailMessageConfigurationPK to set
	 */
	public void setMailMessageConfigurationPK(MailMessageConfigurationPK mailMessageConfigurationPK) {
		this.mailMessageConfigurationPK = mailMessageConfigurationPK;
	}
	/**
	 * @return the mailboxId
	 */
	@Column(name="MALBOXID")
	public String getMailboxId() {
		return mailboxId;
	}
	/**
	 * @param mailboxId the mailboxId to set
	 */
	public void setMailboxId(String mailboxId) {
		this.mailboxId = mailboxId;
	}
	/**
	 * @return the status
	 */
	@Column(name="CFGSTA")
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the fromDate
	 */
	@Column(name="VLDFRMDAT")
	public Calendar getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Calendar fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	@Column(name="VLDTOODAT")
	public Calendar getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Calendar toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return the mailMessageConfigurationParameters
	 */
	 @OneToMany
	 @JoinColumns( {
	  @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
	  @JoinColumn(name="MSGCFGSEQNUM", referencedColumnName="MSGCFGSEQNUM", insertable=false, updatable=false)})
    public Set<MailMessageConfigurationParameter> getMailMessageConfigurationParameters() {
		return mailMessageConfigurationParameters;
	}
	/**
	 * @param mailMessageConfigurationParameters the mailMessageConfigurationParameters to set
	 */
	public void setMailMessageConfigurationParameters(
			Set<MailMessageConfigurationParameter> mailMessageConfigurationParameters) {
		this.mailMessageConfigurationParameters = mailMessageConfigurationParameters;
	}
	

	/**
	 * methods the DAO instance
	 * @return
	 * @throws SystemException
	 */

	public static MailTrackingDefaultsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailTrackingDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}
	}
	
	public static String findMailboxIdFromConfig(MailbagVO mailbagVO) throws SystemException {
		String mailBoxID=null;
		Page<OfficeOfExchangeVO> exchangeOfficeDetails;
	
		mailBoxID=constructDAO().findMailboxIdFromConfig(mailbagVO);
		
		if((mailBoxID==null || mailBoxID.isEmpty()) && mailbagVO.getOoe()!=null && !mailbagVO.getOoe().isEmpty()){
			exchangeOfficeDetails = new MailController().findOfficeOfExchange(mailbagVO.getCompanyCode(), mailbagVO.getOoe(),1);
			if(exchangeOfficeDetails!=null && !exchangeOfficeDetails.isEmpty()){
				OfficeOfExchangeVO officeOfExchangeVO = exchangeOfficeDetails.iterator().next();
				mailBoxID=officeOfExchangeVO.getMailboxId();
			}
		}
		if((mailBoxID==null || mailBoxID.isEmpty()) && mailbagVO.getPaCode()!=null&&!mailbagVO.getPaCode().isEmpty()){
			
				try {
					mailBoxID=constructDAO().findMailboxIdForPA( mailbagVO);
				} catch (PersistenceException e) {
					throw new SystemException("PersistenceException");
				}
			
		}
		return mailBoxID;
		
	}
	
	public MailMessageConfiguration(MailRuleConfigVO mailRuleConfigVO) throws SystemException,
	FinderException{
		populatePK(mailRuleConfigVO);
		populateAttributes(mailRuleConfigVO);
		try {
				PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
		mailRuleConfigVO.setMessageConfigurationSequenceNumber(this.mailMessageConfigurationPK.getMessageConfigurationSequenceNumber());
		mailRuleConfigVO.setCompanyCode(this.mailMessageConfigurationPK.getCompanyCode());
		populateChildren(mailRuleConfigVO);
		
	}
	private void populateChildren(MailRuleConfigVO mailRuleConfigVO) throws SystemException, FinderException {
		Set<MailMessageConfigurationParameter> mailMessageConfigurationParameterList = new HashSet<MailMessageConfigurationParameter>();
		Collection<MailRuleConfigParameterVO> detailVOs = mailRuleConfigVO.getMailRuleConfigParameters();
		if (!detailVOs.isEmpty()) {
			for (MailRuleConfigParameterVO detailVO : detailVOs){
				detailVO.setMessageConfigurationSequenceNumber(mailRuleConfigVO.getMessageConfigurationSequenceNumber());
				detailVO.setCompanyCode(mailRuleConfigVO.getCompanyCode());
				
				MailMessageConfigurationParameter mailMessageConfigurationParameter=new MailMessageConfigurationParameter(detailVO);
				mailMessageConfigurationParameterList.add(mailMessageConfigurationParameter);
			}
			this.setMailMessageConfigurationParameters(mailMessageConfigurationParameterList);
		}
	}
	private void populateAttributes(MailRuleConfigVO mailRuleConfigVO) {
		this.mailboxId = mailRuleConfigVO.getMailboxId();
		this.status = "INACTIVE";
		this.fromDate = mailRuleConfigVO.getFromDate();
		this.toDate =mailRuleConfigVO.getToDate();
		
	    
		
	}
	private void populatePK(MailRuleConfigVO mailRuleConfigVO) {
		MailMessageConfigurationPK mailMessageConfigurationPK = new MailMessageConfigurationPK();
		mailMessageConfigurationPK.setCompanyCode(mailRuleConfigVO.getCompanyCode());
		this.mailMessageConfigurationPK=mailMessageConfigurationPK;
		
	}
	
}