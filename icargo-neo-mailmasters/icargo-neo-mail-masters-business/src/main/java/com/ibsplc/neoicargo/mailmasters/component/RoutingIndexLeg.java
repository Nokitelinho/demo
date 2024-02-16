package com.ibsplc.neoicargo.mailmasters.component;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.mailmasters.vo.RoutingIndexLegVO;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;

/** 
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.RoutingIndexLeg.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-7531	:	31-Aug-2018	:	Draft
 */
@Setter
@Getter
@Slf4j
@IdClass(RoutingIndexLegPK.class)
@SequenceGenerator(name = "MALRTGIDXLEGSEQ", initialValue = 1, allocationSize = 1, sequenceName = "malrtgidxleg_seq")
@Entity
@Table(name = "MALRTGIDXLEG")
public class RoutingIndexLeg  extends BaseEntity implements Serializable {
	private static final String MAIL_OPERATIONS = "mail.operations";
	private static final String KEY_ROUTEINDEXSERNUM_INTERCHANGE = "ROUTEINDEXSERNUM_INTERCHANGE";
	@Column(name = "LEGAIRSTPSCNIND")
	private String legAirStopScanInd;
	@Column(name = "LEGARVTIM")
	private String legArvTime;
	@Column(name = "LEGDEPTIM")
	private String legDepTime;
	@Column(name = "LEGDLVTIM")
	private String legDlvTime;
	@Column(name = "LEGDST")
	private String legDstn;
	@Column(name = "LEGEQP")
	private String legEqpmnt;
	@Column(name = "LEGFLT")
	private String legFlight;
	@Column(name = "LEGNUMSTP")
	private int legStopNum;
	@Column(name = "LEGORG")
	private String legOrg;
	@Column(name = "LEGRTG")
	private String legRoute;
	@Column(name = "LEGTSPCOD")
	private String legTranportCode;
	@Column(name = "TAGIDX")
	private int tagIndex;

	@Id
	@Column(name = "RTGIDX")
	private String routingIndex;
	@Id
	@Column(name = "RTGSEQNUM")
	private int routingSeqNum;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALRTGIDXLEGSEQ")
	@Column(name = "RTGSERNUM")
	private int routingSerNum;

	public RoutingIndexLeg() {
	}

	/** 
	* Constructor	: 	@param routingIndexLegVO Constructor	: 	@throws SystemException Created by	:	A-7531 Created on	:	12-Oct-2018
	*/
	public RoutingIndexLeg(RoutingIndexLegVO routingIndexLegVO) {
		generateSernumForPlannedRouteIndex(routingIndexLegVO);
		populatePK(routingIndexLegVO);
		populateAttributes(routingIndexLegVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
	}

	/** 
	* Method		:	RoutingIndexLeg.populatePK Added by 	:	A-7531 on 12-Oct-2018 Used for 	: Parameters	:	@param routingIndexLegVO  Return type	: 	void
	* @throws SystemException 
	*/
	private void populatePK(RoutingIndexLegVO routingIndexLegVO) {
		this.setCompanyCode(routingIndexLegVO.getCompanyCode());
		this.setRoutingIndex(routingIndexLegVO.getRoutingIndex());
		this.setRoutingSeqNum(routingIndexLegVO.getRoutingSeqNum());
		this.setRoutingSerNum(routingIndexLegVO.getRoutingSerNum());
	}

	/** 
	* Method		:	RoutingIndexLeg.populateAttributes Added by 	:	A-7531 on 12-Oct-2018 Used for 	: Parameters	:	@param routingIndexLegVO  Return type	: 	void
	*/
	private void populateAttributes(RoutingIndexLegVO routingIndexLegVO) {
		this.setLegAirStopScanInd(routingIndexLegVO.getLegAirStopScanInd());
		this.setLegArvTime(routingIndexLegVO.getLegArvTime());
		this.setLegDepTime(routingIndexLegVO.getLegDepTime());
		this.setLegDlvTime(routingIndexLegVO.getLegDlvTime());
		this.setLegDstn(routingIndexLegVO.getLegDstn());
		this.setLegEqpmnt(routingIndexLegVO.getLegEqpmnt());
		this.setLastUpdatedUser(routingIndexLegVO.getLastUpdateUser());
		this.setLastUpdatedTime(Timestamp.valueOf(routingIndexLegVO.getLastUpdateTime()));
		this.setLegFlight(routingIndexLegVO.getLegFlight());
		this.setLegStopNum(routingIndexLegVO.getLegStopNum());
		this.setLegOrg(routingIndexLegVO.getLegOrg());
		this.setLegRoute(routingIndexLegVO.getLegRoute());
		this.setLegTranportCode(routingIndexLegVO.getLegTranportCode());
		this.setTagIndex(routingIndexLegVO.getTagIndex());
	}

	private void generateSernumForPlannedRouteIndex(RoutingIndexLegVO routingIndexLegVO) {
		log.debug("RoutingIndexLeg" + " : " + "generateSequencesForPlannedRouteIndex" + " Entering");
		String keyCode = routingIndexLegVO.getCompanyCode().concat(
				routingIndexLegVO.getRoutingIndex().concat(String.valueOf(routingIndexLegVO.getRoutingSeqNum())));
		//TODO: Neo to correct sequence generation - refer classic
		//routingIndexLegVO.setRoutingSerNum(Integer.parseInt(KeyUtils.getKey()));
		log.debug("RoutingIndex" + " : " + "generateSequencesForPlannedRouteIndex" + " Exiting");
	}

}
