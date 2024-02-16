package com.ibsplc.icargo.business.reco.defaults;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGeographicLevelVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

@Staleable
@Table(name = "RECCMLGEODTL")
@Entity
public class EmbargoGeographicLevel {
	
	private EmbargoGeographicLevelPK embargoGeographicLevelPK;
	
	
	
	/** The is included. */
	private String isIncluded;
	
	/** The values. */
	private String geographicLevelValues;

	public EmbargoGeographicLevel() {
	}

	public EmbargoGeographicLevel(EmbargoGeographicLevelVO parameterVO, EmbargoRulesVO embargoVO)
			throws SystemException, CreateException {
		populatePk(embargoVO, parameterVO);
		populateAttributes(parameterVO);

	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "embargoReferenceNumber", column = @Column(name = "REFNUM")),
			@AttributeOverride(name = "embargoVersion", column = @Column(name = "EMBVER")),
			@AttributeOverride(name = "geographicLevel", column = @Column(name = "GEOLVL")),
			@AttributeOverride(name = "geographicLevelType", column = @Column(name = "GEOTYP"))
			})
	public EmbargoGeographicLevelPK getEmbargoGeographicLevelPK() {
		return embargoGeographicLevelPK;
	}

	public void setEmbargoGeographicLevelPK(
			EmbargoGeographicLevelPK embargoGeographicLevelPK) {
		this.embargoGeographicLevelPK = embargoGeographicLevelPK;
	}

	@Column(name = "APLFLG")
	public String getIsIncluded() {
		return isIncluded;
	}

	public void setIsIncluded(String isIncluded) {
		this.isIncluded = isIncluded;
	}

	@Column(name = "GEOVAL")
	public String getGeographicLevelValues() {
		return geographicLevelValues;
	}

	public void setGeographicLevelValues(String geographicLevelValues) {
		this.geographicLevelValues = geographicLevelValues;
	}
	
	public void populatePk(EmbargoRulesVO embargoVO,
			EmbargoGeographicLevelVO embargoGeographicLevelVO) throws SystemException {
		EmbargoGeographicLevelPK embargoGeographicLevelPK = new EmbargoGeographicLevelPK();
		embargoGeographicLevelPK.setCompanyCode(   embargoVO.getCompanyCode());
		embargoGeographicLevelPK.setEmbargoReferenceNumber(   embargoVO
				.getEmbargoReferenceNumber());
		embargoGeographicLevelPK.setEmbargoVersion(embargoVO.getEmbargoVersion());
		embargoGeographicLevelPK.setGeographicLevel(embargoGeographicLevelVO.getGeographicLevel());
		embargoGeographicLevelPK.setGeographicLevelType(embargoGeographicLevelVO.getGeographicLevelType());
		setEmbargoGeographicLevelPK(embargoGeographicLevelPK);
	}
	
	public void populateAttributes(EmbargoGeographicLevelVO embargoGeographicLevelVO)
	throws CreateException, SystemException {

		setGeographicLevelValues(embargoGeographicLevelVO.getGeographicLevelValues());
	
		setIsIncluded(embargoGeographicLevelVO.getGeographicLevelApplicableOn());
			
		PersistenceController.getEntityManager().persist(this);
	}
	
	public void update(EmbargoGeographicLevelVO embargoParameterVO)
	throws CreateException, SystemException {
		populateAttributes(embargoParameterVO);
	}
	
	public void remove() throws RemoveException, SystemException {

		PersistenceController.getEntityManager().remove(this);
	}
}