package com.ibsplc.neoicargo.mail.component;

import javax.persistence.*;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.CarditTotalVO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/** 
 * This entity persists the totals information in a Cardit
 * @author A-1739
 */
@Setter
@Getter
@Entity
@IdClass(CarditTotalPK.class)
@Table(name = "MALCDTTOT")
public class CarditTotal extends BaseEntity implements Serializable {
	/** 
	* Number of receptacles if value is null default value is set as -1
	*/
	@Column(name = "NUMRCP")
	private long numberOfReceptacles;
	/** 
	* Weight of receptacles If value is null default value is set as -1
	*/
	@Column(name = "RCPWGT")
	private double weightOfReceptacles;

	@Id
	@Transient
	private String companyCode;
	@Id
	@Column(name = "CDTKEY")
	private String carditKey;
	@Id
	@Column(name = "MALCLS")
	private String mailClassCode;

	public CarditTotal() {
	}

	public CarditTotal(CarditPK carditPK, CarditTotalVO carditTotalVO) {
		populatePK(carditPK, carditTotalVO);
		populateAttributes(carditTotalVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(), createException.getMessage(), createException);
		}
	}

	/** 
	* @param carditPK
	* @param carditTotalVO
	*/
	private void populatePK(CarditPK carditPK, CarditTotalVO carditTotalVO) {

		this.setCompanyCode(carditPK.getCompanyCode());
		this.setCarditKey(carditPK.getCarditKey());
		this.setMailClassCode(carditTotalVO.getMailClassCode());
	}

	/** 
	* @param carditTotalVO
	*/
	private void populateAttributes(CarditTotalVO carditTotalVO) {
		if (null != carditTotalVO.getNumberOfReceptacles()) {
			setNumberOfReceptacles(Long.parseLong(carditTotalVO.getNumberOfReceptacles()));
		}
		if (carditTotalVO.getWeightOfReceptacles() != null) {
			setWeightOfReceptacles(
					Double.parseDouble(String.valueOf(carditTotalVO.getWeightOfReceptacles().getValue())));
		}

	}

	/** 
	* @author A-3227
	* @throws SystemException
	*/
	public void remove() {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getMessage(), removeException.getMessage(), removeException);
		}
	}

}
