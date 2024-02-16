package com.ibsplc.neoicargo.mailmasters.component;

import javax.persistence.*;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mailmasters.vo.MailHandoverFilterVO;
import com.ibsplc.neoicargo.mailmasters.vo.MailHandoverVO;
import com.ibsplc.neoicargo.mailmasters.dao.MailMastersDAO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author A-6986
 */
@Setter
@Getter
@Entity
@IdClass(MailHandoverTimePK.class)
@SequenceGenerator(name = "MALHNDTIMSEQ", initialValue = 1, allocationSize = 1, sequenceName = "malhndtim_seq")
@Table(name = "MALHNDTIM")
public class MailHandoverTime  extends BaseEntity implements Serializable {
	private static final String PRODUCT_NAME = "mail.masters";
	private static final String CLASS_NAME = "MailHandoverTime";
	@Column(name = "HNDTIM")
	private String handoverTime;
	@Column(name = "ARPCOD")
	private String airport;
	@Column(name = "EXGOFC")
	private String exchangeOffice;
	@Column(name = "MALCLS")
	private String mailClass;
	@Column(name = "MALSUBCLS")
	private String mailSubClass;
	@Column(name = "GPACOD")
	private String paCode;

	@Id
	@Column(name = "SERNUM")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALHNDTIMSEQ")
	private int serialNumber;

	/** 
	* @return the handoverTime
	*/
	public String getHandoverTime() {
		return handoverTime;
	}

	public MailHandoverTime() {
	}

	public MailHandoverTime(MailHandoverVO mailHandoverVO) {
		try {
			populatePK(mailHandoverVO);
			populateAttributes(mailHandoverVO);
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(), createException.getMessage(), createException);
		}
	}

	private void populatePK(MailHandoverVO mailHandoverVO) {
		this.setCompanyCode(mailHandoverVO.getCompanyCode());
		this.setSerialNumber(mailHandoverVO.getSerialNumber());
	}

	private void populateAttributes(MailHandoverVO mailHandoverVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		this.handoverTime = mailHandoverVO.getHandoverTimes();
		this.airport = mailHandoverVO.getHoAirportCodes();
		this.exchangeOffice = mailHandoverVO.getExchangeOffice();
		this.mailClass = mailHandoverVO.getMailClass();
		this.mailSubClass = mailHandoverVO.getMailSubClass();
		this.paCode = mailHandoverVO.getGpaCode();
		this.setLastUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
		this.setLastUpdatedUser(mailHandoverVO.getLastUpdateUser());
	}

	public static MailHandoverTime find(String companyCode, int serialNumber) throws FinderException {
		MailHandoverTimePK mailHandoverTimePK = new MailHandoverTimePK();
		mailHandoverTimePK.setCompanyCode(companyCode);
		mailHandoverTimePK.setSerialNumber(serialNumber);
		return PersistenceController.getEntityManager().find(MailHandoverTime.class, mailHandoverTimePK);
	}

	public void update(MailHandoverVO mailHandoverVO) {
		populateAttributes(mailHandoverVO);
	}

	/** 
	* @throws SystemException
	* @throws RemoveException
	*/
	public void remove() throws RemoveException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	/** 
	* Page This method is used to remove the DAOS Instance
	* @return
	* @throws SystemException
	*/
	public static MailMastersDAO constructDAO() {
		try {
			return MailMastersDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(PRODUCT_NAME));
		} catch (PersistenceException exception) {
			throw new SystemException("No dao impl found", exception.getMessage(), exception);
		}
	}

	public static Page<MailHandoverVO> findMailHandoverDetails(MailHandoverFilterVO mailHandoverFilterVO,
			int pageNumber) {
		try {
			return constructDAO().findMailHandoverDetails(mailHandoverFilterVO, pageNumber);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	public static MailHandoverTime findMailHandoverTime(MailHandoverVO mailHandoverVO) {
		MailHandoverTime mailHandoverTime = null;
		MailHandoverFilterVO filterVO = new MailHandoverFilterVO();
		int serialNumber = 0;
		filterVO.setCompanyCode(mailHandoverVO.getCompanyCode());
		filterVO.setAirportCode(mailHandoverVO.getHoAirportCodes());
		filterVO.setMailClass(mailHandoverVO.getMailClass());
		filterVO.setMailSubClass(mailHandoverVO.getMailSubClass());
		filterVO.setGpaCode(mailHandoverVO.getGpaCode());
		filterVO.setExchangeOffice(mailHandoverVO.getExchangeOffice());
		filterVO.setDefaultPageSize(10);
		Page<MailHandoverVO> mailHandoverVOs = findMailHandoverDetails(filterVO, 1);
		if (mailHandoverVOs != null && mailHandoverVOs.size() > 0)
			for (MailHandoverVO vo : mailHandoverVOs) {
				serialNumber = vo.getSerialNumber();
			}
		try {
			if (mailHandoverVO.getCompanyCode() != null)
				mailHandoverTime = find(mailHandoverVO.getCompanyCode(), serialNumber);
		} catch (FinderException e) {
		}
		return mailHandoverTime;
	}

}
