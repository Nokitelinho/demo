package com.ibsplc.neoicargo.mailmasters.component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.persistence.*;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import java.time.LocalDateTime;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mailmasters.vo.RoutingIndexLegVO;
import com.ibsplc.neoicargo.mailmasters.vo.RoutingIndexVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

/** 
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.RoutingIndex.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-7531	:	31-Aug-2018	:	Draft
 */
@Setter
@Getter
@Slf4j
@Entity
@IdClass(RoutingIndexPK.class)
@SequenceGenerator(name = "MALRTGIDXSEQ", initialValue = 1, allocationSize = 1, sequenceName = "malrtgidx_seq")
@Table(name = "MALRTGIDX")
public class RoutingIndex  extends BaseEntity implements Serializable {
	private static final String MODULE_NAME = "mail.operations";
	private static final String KEY_ROUTEINDEXSEQNUM_INTERCHANGE = "ROUTEINDEXSEQNUM_INTERCHANGE";
	@Column(name = "CLSOUTTIM")
	private String clsOutTim;
	@Column(name = "DAYCNT")
	private int dayCount;
	@Column(name = "CTRTYP")
	private String contractType;
	@Column(name = "DSTCOD")
	private String destination;
	@Column(name = "EQUTEN")
	private int equitableTender;
	@Column(name = "FNLDLVTIM")
	private String finalDlvTime;
	@Column(name = "HAZIND")
	private String hazardousIndicator;
	@Column(name = "MALCLS")
	private String mailClass;
	@Column(name = "MALCST")
	private int mailCost;
	@Column(name = "MAXWGT")
	private double maxWgt;
	@Column(name = "MINWGT")
	private double minWgt;
	@Column(name = "ONETIMPER")
	private int onetimePercent;
	@Column(name = "ORGCOD")
	private String origin;
	@Column(name = "PERIND")
	private String perishableIndicator;
	@Column(name = "PLDDISDAT")
	private LocalDateTime plannedDisDate;
	@Column(name = "PLDEFFDAT")
	private LocalDateTime plannedEffectiveDate;
	@Column(name = "PLNROUGENIND")
	private String planRouteGenInd;
	@Column(name = "PLNROUSCNIND")
	private String planRouteScnInd;
	@Column(name = "PRICOD")
	private String priorityCode;
	@Column(name = "TAGIDX")
	private int tagIndex;
	@Column(name = "WGTUNT")
	private String wgtUnit;
	@Column(name = "DAYOFOPR")
	private String dayOfOperation;
	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
			@JoinColumn(name = "RTGIDX", referencedColumnName = "RTGIDX", insertable=false, updatable=false),
			@JoinColumn(name = "RTGSEQNUM", referencedColumnName = "RTGSEQNUM", insertable=false, updatable=false)})
	private Set<RoutingIndexLeg> routingIndexLeg;

	@Id
	@Column(name = "RTGIDX")
	private String routingIndex;
	@Id
	@Column(name = "RTGSEQNUM")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALRTGIDXSEQ")
	private int routingSeqNum;

	public RoutingIndex() {
	}

	/** 
	* Constructor	: 	@param routingIndexVO Constructor	: 	@throws SystemException Created by	:	A-7531 Created on	:	11-Oct-2018
	*/
	public RoutingIndex(RoutingIndexVO routingIndexVO) {
		generateSequencesForPlannedRouteIndex(routingIndexVO);
		populatePK(routingIndexVO);
		populateAttributes(routingIndexVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
		populateChildTable(routingIndexVO);
		log.debug(MODULE_NAME + " : " + "RoutingIndex" + " Exiting");
	}

	/** 
	* Method		:	RoutingIndex.populateChildTable Added by 	:	A-7531 on 11-Oct-2018 Used for 	: Parameters	:	@param routingIndexVO  Return type	: 	void
	* @throws SystemException 
	*/
	private void populateChildTable(RoutingIndexVO routingIndexVO) {
		Collection<RoutingIndexLegVO> routingIndexLegVOs = routingIndexVO.getRoutingIndexLegVO();
		if (routingIndexLegVOs != null && routingIndexLegVOs.size() > 0) {
			Set<RoutingIndexLeg> routingIndexLeg = new HashSet<RoutingIndexLeg>();
			for (RoutingIndexLegVO routingIndexLegVO : routingIndexLegVOs) {
				routingIndexLegVO.setCompanyCode(routingIndexVO.getCompanyCode());
				routingIndexLegVO.setRoutingIndex(routingIndexVO.getRoutingIndex());
				routingIndexLegVO.setRoutingSeqNum(routingIndexVO.getRoutingSeqNum());
				RoutingIndexLeg routingIndexLegDetail = new RoutingIndexLeg(routingIndexLegVO);
				routingIndexLeg.add(routingIndexLegDetail);
			}
			this.setRoutingIndexLeg(routingIndexLeg);
		}
	}

	/** 
	* Method		:	RoutingIndex.populatePK Added by 	:	A-7531 on 11-Oct-2018 Used for 	: Parameters	:	@param routingIndexVO  Return type	: 	void
	*/
	private void populatePK(RoutingIndexVO routingIndexVO) {
		this.setCompanyCode(routingIndexVO.getCompanyCode());
		this.setRoutingIndex(routingIndexVO.getRoutingIndex());
		this.setRoutingSeqNum(routingIndexVO.getRoutingSeqNum());
	}

	/** 
	* Method		:	RoutingIndex.populateAttributes Added by 	:	A-7531 on 11-Oct-2018 Used for 	: Parameters	:	@param routingIndexVO  Return type	: 	void
	*/
	private void populateAttributes(RoutingIndexVO routingIndexVO) {
		this.setClsOutTim(routingIndexVO.getClsOutTim());
		this.setContractType(routingIndexVO.getContractType());
		this.setDayCount(routingIndexVO.getDayCount());
		this.setDestination(routingIndexVO.getDestination());
		this.setFinalDlvTime(routingIndexVO.getFinalDlvTime());
		this.setHazardousIndicator(routingIndexVO.getHazardousIndicator());
		this.setLastUpdatedUser(routingIndexVO.getLastUpdateUser());
		this.setLastUpdatedTime(Timestamp.valueOf(routingIndexVO.getLastUpdateTime()));
		this.setMailClass(routingIndexVO.getMailClass());
		this.setMailCost(routingIndexVO.getMailCost());
		this.setMaxWgt(routingIndexVO.getMaxWgt());
		this.setMinWgt(routingIndexVO.getMinWgt());
		this.setOrigin(routingIndexVO.getOrigin());
		this.setPerishableIndicator(routingIndexVO.getPerishableIndicator());
		this.setDayOfOperation(String.valueOf(routingIndexVO.getFrequency()));
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		//TODO: Neo to correct below code
//		LocalDateTime calender = Calendar.getInstance();
//		LocalDateTime calender1 = Calendar.getInstance();
		Date date = null;
		Date date1 = null;
		if (routingIndexVO.getPlannedDisDate() != null) {
			try {
				date1 = df.parse(routingIndexVO.getPlannedDisDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//TODO: Neo to correct below code
			//calender1.setTime(date1);
			//this.setPlannedDisDate(calender1);
		}
		if (routingIndexVO.getPlannedEffectiveDate() != null) {
			try {
				date = df.parse(routingIndexVO.getPlannedEffectiveDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//TODO: Neo to correct below code
			//calender.setTime(date);
			//this.setPlannedEffectiveDate(calender);
		}
		this.setPlanRouteGenInd(routingIndexVO.getPlanRouteGenInd());
		this.setPlanRouteScnInd(routingIndexVO.getPlanRouteScnInd());
		this.setPriorityCode(routingIndexVO.getPriorityCode());
		this.setEquitableTender(routingIndexVO.getEquitableTender());
		this.setWgtUnit(routingIndexVO.getWgtUnit());
		this.setTagIndex(routingIndexVO.getTagIndex());
		this.setOnetimePercent(routingIndexVO.getOnetimePercent());
	}

	/** 
	* Method		:	RoutingIndex.generateSequencesForPlannedRouteIndex Added by 	:	A-7531 on 03-Sep-2018 Used for 	: Parameters	:	@param routingIndexVO Parameters	:	@throws SystemException  Return type	: 	void
	*/
	private void generateSequencesForPlannedRouteIndex(RoutingIndexVO routingIndexVO) {
		log.debug("RoutingIndex" + " : " + "generateSequencesForPlannedRouteIndex" + " Entering");
		String keyCode = routingIndexVO.getCompanyCode().concat(routingIndexVO.getRoutingIndex());
		//TODO:NEO to correct key generation - refer classic
		//routingIndexVO.setRoutingSeqNum(Integer.parseInt(KeyUtils.getKey()));
		log.debug("RoutingIndex" + " : " + "generateSequencesForPlannedRouteIndex" + " Exiting");
	}

}
