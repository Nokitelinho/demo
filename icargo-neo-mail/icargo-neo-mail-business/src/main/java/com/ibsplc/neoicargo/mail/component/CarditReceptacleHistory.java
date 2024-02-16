package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.mail.vo.CarditReceptacleVO;
import com.ibsplc.neoicargo.mail.vo.CarditVO;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/** 
 * @author A-3429Maintains history for a mailbag. This entity stores all details regarding the mailbag transactions. 
 */
@Setter
@Getter
@Slf4j
@IdClass(CarditReceptacleHistoryPK.class)
@SequenceGenerator(name = "MALCDTRCPHISSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALCDTRCPHIS_SEQ")
@Entity
@Table(name = "MALCDTRCPHIS")
public class CarditReceptacleHistory extends BaseEntity implements Serializable {

	@Id
	@Transient
	private String companyCode;
	@Id
	@Column(name = "MALSEQNUM")
	private long mailSeqNum;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALCDTRCPHISSEQ")
	@Column(name = "SEQNUM")
	private int sequenceNumber;
	@Column(name = "CDTKEY")
	private String carditKey;
	@Column(name = "RCPSTA")
	private String receptacleStatus;
	@Column(name = "UPDTIM")
	private LocalDateTime updatedTime;

	public CarditReceptacleHistory() {
	}

	/** 
	* @author A-1739
	* @param carditVO
	* @param carditReceptacleVO
	* @return CarditReceptacleHistory
	* @throws SystemException
	*/
	public CarditReceptacleHistory(CarditVO carditVO, CarditReceptacleVO carditReceptacleVO) {
		populatePK(carditVO, carditReceptacleVO);
		populateAttributes(carditVO, carditReceptacleVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(), createException.getMessage(), createException);
		}
	}

	/** 
	* @author A-3429
	* @param carditVO
	* @param carditReceptacleVO
	* @throws SystemException
	*/
	private void populatePK(CarditVO carditVO, CarditReceptacleVO carditReceptacleVO) {
		this.setCompanyCode(carditVO.getCompanyCode());
		long mailSeq = carditReceptacleVO.getMailSeqNum() == 0
				? Mailbag.findMailBagSequenceNumberFromMailIdr(carditReceptacleVO.getReceptacleId(),
						carditVO.getCompanyCode())
				: carditReceptacleVO.getMailSeqNum();
		this.setMailSeqNum(mailSeq);
		carditReceptacleVO.setMailSeqNum(mailSeq);
	}

	/** 
	* A-3429
	* @param carditVO
	* @param carditReceptacleVO
	*/
	private void populateAttributes(CarditVO carditVO, CarditReceptacleVO carditReceptacleVO) {
		setCarditKey(carditVO.getCarditKey());
		setReceptacleStatus(carditReceptacleVO.getReceptacleStatus());
		setUpdatedTime(carditReceptacleVO.getUpdatedTime().toLocalDateTime());
	}
}
