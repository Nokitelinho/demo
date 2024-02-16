package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.mail.vo.ContainerVO;
import com.ibsplc.neoicargo.mail.vo.SearchContainerFilterVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.mail.dao.SearchContainerFilterQueryBuilder;
import com.ibsplc.xibase.util.time.TimeConvertor;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


@Slf4j
public class SearchContainerFilterQuery extends PageableNativeQuery<ContainerVO> {

	protected SearchContainerFilterVO searchContainerFilterVO;
	private static final String SEARCHMODE_DEST = "DESTN";
	private String baseQuery;
	private boolean isOracleDataSource;
	LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);


	public SearchContainerFilterQuery(String baseQuery,SearchContainerFilterVO searchContainerFilterVO,Mapper<ContainerVO> mapper,boolean isOracleDataSource){
		super(searchContainerFilterVO.getTotalRecords(), mapper, PersistenceController.getEntityManager().currentSession());
		this.searchContainerFilterVO = searchContainerFilterVO;
		this.baseQuery = baseQuery;
		this.isOracleDataSource = isOracleDataSource;

	}

	public SearchContainerFilterQuery(String baseQuery,SearchContainerFilterVO searchContainerFilterVO,MultiMapper<ContainerVO> multimapper,boolean isOracleDataSource){
		super(searchContainerFilterVO.getTotalRecords(), multimapper, PersistenceController.getEntityManager().currentSession());
		this.searchContainerFilterVO = searchContainerFilterVO;
		this.baseQuery = baseQuery;
		this.isOracleDataSource = isOracleDataSource;

	}

	public SearchContainerFilterQuery(String baseQuery, SearchContainerFilterVO searchContainerFilterVO,
									  ContainerMapperForDestination mapper,boolean isOracleDataSource, String fromScreen){
		super(searchContainerFilterVO.getPageSize(), -1, mapper, PersistenceController.getEntityManager().currentSession());
		this.searchContainerFilterVO = searchContainerFilterVO;
		this.baseQuery = baseQuery;
		this.isOracleDataSource = isOracleDataSource;
	}

	public SearchContainerFilterQuery(String baseQuery, SearchContainerFilterVO searchContainerFilterVO,
									  SearchContainerMultiMapper mapper,boolean isOracleDataSource, String fromScreen){
		super(searchContainerFilterVO.getPageSize(), -1, mapper, PersistenceController.getEntityManager().currentSession());
		this.searchContainerFilterVO = searchContainerFilterVO;
		this.baseQuery = baseQuery;
		this.isOracleDataSource = isOracleDataSource;
	}

	public String getNativeQuery (){
		log.info("SearchContainerFilterQuery", "getNativeQuery");
		String companyCode = searchContainerFilterVO.getCompanyCode();
		String containerNumber = searchContainerFilterVO.getContainerNumber();
		String flightNumber = searchContainerFilterVO.getFlightNumber();
		String assignedBy = searchContainerFilterVO.getAssignedUser();
		String filterPort = searchContainerFilterVO.getDeparturePort();
		String finalDestination = searchContainerFilterVO.getFinalDestination();
		ZonedDateTime fromDate = searchContainerFilterVO.getAssignedFromDate();
		ZonedDateTime toDate = searchContainerFilterVO.getAssignedToDate();
		ZonedDateTime flightdate = searchContainerFilterVO.getFlightDate();

		int carrierID = searchContainerFilterVO.getCarrierId();
		int destionationCarrierId = searchContainerFilterVO
				.getDestionationCarrierId();
		String operationType = searchContainerFilterVO.getOperationType();
		String searchMode = searchContainerFilterVO.getSearchMode();
		String containerDestination = searchContainerFilterVO.getDestination() ;
		String mailAcceptedFlag = searchContainerFilterVO.getMailAcceptedFlag() ;
		String notClosedFlag = searchContainerFilterVO.getNotClosedFlag() ;
		String showEmpty =searchContainerFilterVO.getShowEmptyContainer();
		StringBuilder builder = new StringBuilder(baseQuery);
		StringBuilder conFlightQuery = null;
		StringBuilder flightQuery = null;
		StringBuilder conDestinationQuery = null;
		StringBuilder containerQuery = null;
		boolean navigation = searchContainerFilterVO.isNavigation();
		String nonSQ= "OTHERS";


		int index = 0;

		/*
		 *The  Dynamic Query
		 *in case of  flight
		 *or flight+ container
		 * No Destination Present
		 */
		conFlightQuery = new StringBuilder()
				.append(",to_CHAR(RTG.ONWFLTDAT,'DD-Mon-YYYY') ONWFLTDAT,")
				.append("RTG.ONWFLTNUM,RTG.POU RTGPOU,RTG.RTGSERNUM,")
				.append("RTG.ONWFLTCARCOD,RTG.FLTCARIDR RTGFLTCARIDR ,RTG.ONWFLTCARIDR,")
				.append("ASG.FLTDAT FLTDAT,ASG.EXPCLSFLG CLSFLG, ");


		//Modified by A-5526 for bug ICRD-154686
		if(MailConstantsVO.OPERATION_INBOUND.equals(operationType) && !MailConstantsVO.FLAG_YES.equals(searchContainerFilterVO.getTransferStatus())) {
			conFlightQuery.append(" sum(SEG.RCVBAG) BAGCNT, sum(SEG.RCVWGT) BAGWGT ");
		} else {
			conFlightQuery.append("  sum( SEG.ACPBAG) BAGCNT,  sum(SEG.ACPWGT) BAGWGT ");
		}
		conFlightQuery.append(",'' RELFLG ,MST.LSTUPDTIM AS ULDLSTUPDTIM, MST.LSTUPDUSR AS ULDLSTUPDUSR ");
		//Added by A-8893
		conFlightQuery.append(", MIN(MST.FSTMALASGDAT) SCNDAT, MIN(MST.ASGPRT) SCNPRT");

		appendSelectQuery(conFlightQuery);

		if(searchContainerFilterVO.getSubclassGroup() != null && !searchContainerFilterVO.getSubclassGroup().equals(nonSQ)){
			if(isOracleDataSource){
				conFlightQuery.append("	,(CASE WHEN((SUM(CASE WHEN CLSMST.SUBCLSGRP = 'EMS'  THEN 1  ELSE 0 END) /COALESCE(NULLIF(COUNT(CLSMST.SUBCLSGRP),0),1)) > MIN(PAR.PARVAL/100)) THEN 'EMS' ELSE 'NONEMS' END )SUBCLSGRP");
			}else{
				conFlightQuery.append("	,(CASE WHEN((SUM(CASE WHEN CLSMST.SUBCLSGRP = 'EMS'  THEN 1  ELSE 0 END)::decimal /COALESCE(NULLIF(COUNT(CLSMST.SUBCLSGRP),0),1)) > MIN(PAR.PARVAL::numeric/100)) THEN 'EMS' ELSE 'NONEMS' END )SUBCLSGRP");
			}
			conFlightQuery.append(",  LODPLN.FLTNUM PLNFLTNUM, PLNLEG.STD PLNFLTDAT, ARLMST.TWOAPHCOD PLNFLTCARCOD  ");
		}
		conFlightQuery.append(", MST.ULDREFNUM ");

		appendProchagRatedCntAndSysparval(conFlightQuery);
		/*
		 *The  Dynamic Query
		 *in case of  flight
		 *or flight+ container
		 * No Destination Present
		 */
		flightQuery = new StringBuilder().append(" FROM    MALFLTCON MST ");
		//Modified by A-5526 for bug ICRD-154686
		if(MailConstantsVO.OPERATION_INBOUND.equals(operationType) && !MailConstantsVO.FLAG_YES.equals(searchContainerFilterVO.getTransferStatus())) {
			flightQuery.append(" INNER JOIN MALFLT ASG ON").append(
					"  ( (MST.ASGPRT=ASG.ARPCOD  AND ASG.EXPCLSFLG is not null AND MST.TRNFLG     ='Y') OR (MST.POU         =ASG.ARPCOD AND ASG.IMPCLSFLG is not null ) ) AND ");
			//"  ( (MST.ASGPRT=ASG.ARPCOD AND ASG.EXPCLSFLG is not null AND MST.TRNFLG     ='Y') OR (MST.POU         =ASG.ARPCOD AND ASG.IMPCLSFLG is not null AND MST.TRNFLG='Y') ) AND ");
			//" MST.POU = ASG.ARPCOD AND ASG.IMPCLSFLG is not null AND ");
		}else {
			flightQuery.append(" INNER JOIN MALFLT ASG ON ").append(
					" MST.ASGPRT= ASG.ARPCOD  AND ");
		}
		flightQuery
				.append(" MST.CMPCOD=ASG.CMPCOD  AND ")
				.append(" MST.FLTCARIDR=ASG.FLTCARIDR  AND ")
				.append(" MST.FLTNUM=ASG.FLTNUM  AND ")
				.append(" MST.FLTSEQNUM=ASG.FLTSEQNUM ");


		if(!navigation){
			flightQuery.append("  AND MST.ASGDATUTC =(SELECT MAX (ASGDATUTC) FROM MALFLTCON WHERE  CONNUM = MST.CONNUM AND CMPCOD=MST.CMPCOD )");
		}
		if(MailConstantsVO.OPERATION_OUTBOUND.equals(operationType)) {
			flightQuery.append(" AND MST.LEGSERNUM=ASG.LEGSERNUM ");
		}
		//Added for icrd-95515 to fetch pol required for fetching importmainfestedmaildetail
		if(MailConstantsVO.OPERATION_INBOUND.equals(operationType)) {
			//Modified by A-5526 for bug ICRD-154686
			flightQuery.append(" INNER JOIN MALFLTSEG ASGSEG ON ASG.FLTCARIDR = ASGSEG.FLTCARIDR AND ASG.FLTNUM= ASGSEG.FLTNUM AND ASG.FLTSEQNUM= ASGSEG.FLTSEQNUM");
			//Added by A-5526 for bug ICRD-154686 starts
			if(!MailConstantsVO.FLAG_YES.equals(searchContainerFilterVO.getTransferStatus())){

				flightQuery.append(" AND ( ASG.ARPCOD   = ASGSEG.POU OR ASG.ARPCOD=ASGSEG.POL) ");
				//flightQuery.append(" AND ASG.ARPCOD= ASGSEG.POU ");
			}
			//Added by A-5526 for bug ICRD-154686 ends
		}
		appendSysParvalInnerQuery(flightQuery);

		flightQuery
				.append(" LEFT OUTER JOIN MALFLTCONRTG RTG ON  ")
				.append(" MST.CMPCOD =  RTG.CMPCOD  AND ")
				.append(" MST.CONNUM =  RTG.CONNUM  AND ")
				.append(" MST.FLTCARIDR =  RTG.FLTCARIDR  AND ")
				.append(" MST.FLTNUM =  RTG.FLTNUM  AND ")
				.append(" MST.FLTSEQNUM =  RTG.FLTSEQNUM AND ");
		if(MailConstantsVO.OPERATION_OUTBOUND.equals(operationType)) {
			flightQuery.append(" MST.LEGSERNUM =  RTG.LEGSERNUM  AND ");
		}
		flightQuery
				.append(" MST.ASGPRT =  RTG.ASGPRT ");

		if (this.searchContainerFilterVO.isExcATDCapFlights()) {
			flightQuery.append(" LEFT OUTER JOIN FLTOPRLEG  LEG ON ").append(
					" mst.cmpcod = LEG.cmpcod ").append(
					" AND mst.fltcaridr = LEG.fltcaridr ").append(
					" AND mst.fltnum = LEG.fltnum ").append(
					" AND mst.fltseqnum = LEG.fltseqnum ").append(
					" AND mst.asgprt = LEG.legorg ");
		}
		if(searchContainerFilterVO.getSubclassGroup() != null && !searchContainerFilterVO.getSubclassGroup().equals(nonSQ)){
			flightQuery.append(" LEFT OUTER JOIN mallodplnfltcon lodpln ON mst.cmpcod = lodpln.cmpcod ")
					.append("  AND mst.connum = lodpln.connum AND mst.uldrefnum = lodpln.uldrefnum ")
					.append(" AND lodpln.lodplnsta = 'N' LEFT OUTER JOIN fltoprleg plnleg ON ")
					.append("  lodpln.cmpcod = plnleg.cmpcod  AND lodpln.fltcaridr = plnleg.fltcaridr " )
					.append(" AND lodpln.fltnum = plnleg.fltnum  AND lodpln.fltseqnum = plnleg.fltseqnum ")
					.append(" AND lodpln.segorg = plnleg.legorg  AND lodpln.lodplnsta = 'N' ")

					.append(" LEFT OUTER JOIN SHRARLMST ARLMST ON ")
					.append(" plnleg.FLTCARIDR  = ARLMST.ARLIDR  AND plnleg.CMPCOD = ARLMST.CMPCOD ");
		}
		flightQuery.append(" LEFT OUTER JOIN MALULDSEGDTL SEG ON  ")
				.append(" MST.CMPCOD=SEG.CMPCOD  AND  ")
				.append(" MST.CONNUM =  SEG.CONNUM  AND  ")
				.append(" MST.FLTCARIDR=SEG.FLTCARIDR  AND  ")
				.append(" MST.FLTNUM=SEG.FLTNUM  AND   ")
				.append(" MST.FLTSEQNUM=SEG.FLTSEQNUM  AND  ")
				.append(" MST.SEGSERNUM=SEG.SEGSERNUM  ");


		appendMalmragblAndMalmraprogblLeftJoinWithSeg(flightQuery);

		appendJoinQuery(flightQuery);

		if(searchContainerFilterVO.getSubclassGroup() != null && !searchContainerFilterVO.getSubclassGroup().equals(nonSQ)){
			flightQuery
					.append(" LEFT OUTER JOIN MALMST MALMST")
					.append(" ON SEG.CMPCOD =MALMST.CMPCOD")
					.append(" AND SEG.MALSEQNUM = MALMST.MALSEQNUM")
					.append(" LEFT OUTER JOIN  MALSUBCLSMST CLSMST ")
					.append("ON MALMST.CMPCOD = CLSMST.CMPCOD")
					.append(" AND MALMST.MALSUBCLS = CLSMST.SUBCLSCOD");

			flightQuery.append("  LEFT OUTER JOIN  SHRSYSPAR PAR")
					.append(" ON PAR.CMPCOD       = MALMST.CMPCOD")
					.append("  AND PAR.PARCOD       = 'mailtracking.defaults.emssubclasspercentage' ");
		}
		flightQuery.append(" WHERE ");

		/*
		 * The  Dynamic Query
		 * in case of  Destination
		 * or Destination+ container
		 * Note: No Flight Present
		 */
		conDestinationQuery = new StringBuilder();
		conDestinationQuery
				.append("")
				.append(" ,'' ONWFLTDAT,'' ONWFLTNUM, '' RTGPOU, 0 RTGSERNUM,'' ONWFLTCARCOD,0 RTGFLTCARIDR")
				.append(" ,0 ONWFLTCARIDR,NULL FLTDAT,'O' CLSFLG, SUM( ULD.ACPBAG) BAGCNT,SUM( ULD.ACPWGT) BAGWGT,'' RELFLG,MST.FLTCARCOD  ULDFLTCARCOD ")
				.append(" ,MST.LSTUPDTIM AS ULDLSTUPDTIM, MST.LSTUPDUSR AS ULDLSTUPDUSR ")
				.append(", MIN(MST.FSTMALASGDAT) SCNDAT, MIN(MST.ASGPRT) SCNPRT");

		appendSelectQuery(conDestinationQuery);
		if(searchContainerFilterVO.getSubclassGroup() != null && !searchContainerFilterVO.getSubclassGroup().equals(nonSQ)){
			if(isOracleDataSource){
				conDestinationQuery.append(",(CASE WHEN((SUM(CASE WHEN CLSMST.SUBCLSGRP = 'EMS'  THEN 1  ELSE 0 END) /COALESCE(NULLIF(COUNT(CLSMST.SUBCLSGRP),0),1)) > MIN(PAR.PARVAL/100)) THEN 'EMS' ELSE 'NONEMS' END )SUBCLSGRP");
			}else{
				conDestinationQuery.append(",(CASE WHEN((SUM(CASE WHEN CLSMST.SUBCLSGRP = 'EMS'  THEN 1  ELSE 0 END)::decimal /COALESCE(NULLIF(COUNT(CLSMST.SUBCLSGRP),0),1)) > MIN(PAR.PARVAL::numeric/100)) THEN 'EMS' ELSE 'NONEMS' END )SUBCLSGRP");
			}

			conDestinationQuery.append(", LODPLN.FLTNUM PLNFLTNUM, PLNLEG.STD PLNFLTDAT ").append(" , ARLMST.TWOAPHCOD PLNFLTCARCOD ");
		}

		conDestinationQuery.append(",MST.ULDREFNUM ");

		appendProchagRatedCntAndSysparval(conDestinationQuery);

		conDestinationQuery.append(" FROM MALFLTCON MST ");

		appendSysParvalInnerQuery(conDestinationQuery);


		conDestinationQuery.append("  LEFT OUTER JOIN MALARPULDDTL ULD  ")
				.append(" ON ")
				.append(" MST.CMPCOD=ULD.CMPCOD  AND ")
				.append(" MST.ASGPRT=ULD.ARPCOD  AND ")
				.append(" MST.CONNUM=ULD.CONNUM   ");

		appendMalmragblAndMalmraprogblLeftJoinWithUld(conDestinationQuery);


		appendJoinQuery(conDestinationQuery);
		if(searchContainerFilterVO.getSubclassGroup() != null && !searchContainerFilterVO.getSubclassGroup().equals(nonSQ)){


			conDestinationQuery.append(" LEFT OUTER JOIN mallodplnfltcon")
					.append(" lodpln ON")
					.append(" mst.cmpcod = lodpln.cmpcod ")
					.append(" AND mst.connum = lodpln.connum ")
					.append("AND mst.uldrefnum = lodpln.uldrefnum  AND lodpln.lodplnsta = 'N'  LEFT OUTER JOIN fltoprleg  plnleg ON ")
					.append(" lodpln.cmpcod = plnleg.cmpcod  AND lodpln.fltcaridr = plnleg.fltcaridr  AND lodpln.fltnum = plnleg.fltnum " )
					.append(" AND lodpln.fltseqnum = plnleg.fltseqnum  AND lodpln.segorg = plnleg.legorg  AND lodpln.lodplnsta = 'N' ")

					.append(" LEFT OUTER JOIN SHRARLMST ARLMST ON plnleg.FLTCARIDR  = ARLMST.ARLIDR  AND plnleg.CMPCOD    = ARLMST.CMPCOD ");

			conDestinationQuery
					.append("  LEFT OUTER JOIN MALMST MALMST")
					.append("  ON ULD.CMPCOD   =MALMST.CMPCOD")
					.append("  AND ULD.MALSEQNUM    = MALMST.MALSEQNUM")
					.append("  LEFT OUTER JOIN  MALSUBCLSMST CLSMST ")
					.append(" ON MALMST.CMPCOD = CLSMST.CMPCOD")
					.append("   AND MALMST.MALSUBCLS = CLSMST.SUBCLSCOD");
			conDestinationQuery.append("  LEFT OUTER JOIN  SHRSYSPAR PAR ")
					.append("  ON PAR.CMPCOD       = MALMST.CMPCOD")
					.append("  AND PAR.PARCOD       = 'mailtracking.defaults.emssubclasspercentage' ");
		}
		conDestinationQuery.append(" WHERE ");
		/*
		 * In case of the Search Mode Being ALL it is required to show all the
		 * Containers
		 * both Destination Assigned and Flight Assigned Containers .
		 *
		 */

		containerQuery = new StringBuilder();
		containerQuery
				.append(",to_CHAR(RTG.ONWFLTDAT,'DD-Mon-YYYY') ONWFLTDAT,")
				.append("RTG.ONWFLTNUM,RTG.POU RTGPOU,RTG.RTGSERNUM,")
				.append("RTG.ONWFLTCARCOD,RTG.FLTCARIDR RTGFLTCARIDR ,RTG.ONWFLTCARIDR,")
				.append("ASG.FLTDAT FLTDAT, ASG.EXPCLSFLG CLSFLG, ");


		//Modified by A-8893 for bug ICRD-337065
		if(searchContainerFilterVO.getContainersToList()!=null
				&& searchContainerFilterVO.getContainersToList().size()>0){
			containerQuery.append(" COUNT(SEG.MALSEQNUM) BAGCNT, SUM(SEG.WGT) BAGWGT ");
		}
		else{
			//Modified by A-5526 for bug ICRD-154686
			if(MailConstantsVO.OPERATION_INBOUND.equals(operationType) && !MailConstantsVO.FLAG_YES.equals(searchContainerFilterVO.getTransferStatus())) {
				containerQuery.append(" SUM(SEG.RCVBAG) BAGCNT, SUM(SEG.RCVWGT) BAGWGT ");
			} else {
				containerQuery.append(" sum( SEG.ACPBAG) BAGCNT, sum(SEG.ACPWGT) BAGWGT ");
			}
		}
		containerQuery.append(",'' RELFLG  ,MST.LSTUPDTIM AS ULDLSTUPDTIM, MST.LSTUPDUSR AS ULDLSTUPDUSR ");
		//Added by A-8893
		containerQuery.append(",MIN(MST.FSTMALASGDAT) SCNDAT, MIN(MST.ASGPRT) SCNPRT");

		appendSelectQuery(containerQuery);
		if(searchContainerFilterVO.getSubclassGroup() != null && !searchContainerFilterVO.getSubclassGroup().equals(nonSQ)){
			if(isOracleDataSource){
				containerQuery.append("	,(CASE WHEN((SUM(CASE WHEN CLSMST.SUBCLSGRP = 'EMS'  THEN 1  ELSE 0 END) /COALESCE(NULLIF(COUNT(CLSMST.SUBCLSGRP),0),1)) > MIN(PAR.PARVAL/100)) THEN 'EMS' ELSE 'NONEMS' END )SUBCLSGRP");
			}else{
				containerQuery.append("	,(CASE WHEN((SUM(CASE WHEN CLSMST.SUBCLSGRP = 'EMS'  THEN 1  ELSE 0 END)::decimal /COALESCE(NULLIF(COUNT(CLSMST.SUBCLSGRP),0),1)) > MIN(PAR.PARVAL::numeric/100)) THEN 'EMS' ELSE 'NONEMS' END )SUBCLSGRP");
			}
			containerQuery.append(",  LODPLN.FLTNUM PLNFLTNUM, PLNLEG.STD PLNFLTDAT , ARLMST.TWOAPHCOD PLNFLTCARCOD ");

		}
		containerQuery.append(",  MST.ULDREFNUM ");

		appendProchagRatedCntAndSysparval(containerQuery);

		containerQuery.append(" FROM    MALFLTCON MST INNER JOIN ");
		//Modified by A-5526 for bug ICRD-154686
		if(MailConstantsVO.OPERATION_INBOUND.equals(operationType) && !MailConstantsVO.FLAG_YES.equals(searchContainerFilterVO.getTransferStatus())) {
			containerQuery.append(" MALFLT ASG ").append(
					"  ON ( (MST.ASGPRT=ASG.ARPCOD AND ASG.EXPCLSFLG is not null AND MST.TRNFLG     ='Y') OR (MST.POU         =ASG.ARPCOD AND ASG.IMPCLSFLG is not null ) ) AND ");
			//" MST.POU = ASG.ARPCOD AND ASG.IMPCLSFLG is not null AND ");
			//" ON  MST.POU=ASG.ARPCOD  AND ASG.IMPCLSFLG is not null AND ");
		} else {
			containerQuery.append(" MALFLT ASG ").append(
					" ON  MST.ASGPRT=ASG.ARPCOD  AND ");
		}

		containerQuery.append("  MST.CMPCOD=ASG.CMPCOD AND ")
				.append(" MST.FLTCARIDR=ASG.FLTCARIDR  AND ").append(
						" MST.FLTNUM=ASG.FLTNUM  AND ").append(
						" MST.FLTSEQNUM=ASG.FLTSEQNUM ")
				.append("  AND MST.ASGDATUTC =(SELECT MAX (ASGDATUTC) FROM MALFLTCON WHERE  CONNUM = MST.CONNUM AND CMPCOD=MST.CMPCOD )");
		if(MailConstantsVO.OPERATION_OUTBOUND.equals(operationType)) {
			containerQuery.append(" AND MST.LEGSERNUM=ASG.LEGSERNUM   ");
		}
		//Added for icrd-95515 to fetch pol required for fetching importmainfestedmaildetail
		if(MailConstantsVO.OPERATION_INBOUND.equals(operationType)) {
			//Modified by A-5526 for bug ICRD-154686
			containerQuery.append(" INNER JOIN MALFLTSEG ASGSEG ON ASG.FLTCARIDR = ASGSEG.FLTCARIDR AND ASG.FLTNUM= ASGSEG.FLTNUM AND ASG.FLTSEQNUM= ASGSEG.FLTSEQNUM ");
			//Added by A-5526 for bug ICRD-154686 starts
			if(!MailConstantsVO.FLAG_YES.equals(searchContainerFilterVO.getTransferStatus())){
				containerQuery.append(" AND ( ASG.ARPCOD   = ASGSEG.POU OR ASG.ARPCOD=ASGSEG.POL) ");
				//containerQuery.append(" AND ASG.ARPCOD= ASGSEG.POU ");
			}
			//Added by A-5526 for bug ICRD-154686 ends
		}
		appendSysParvalInnerQuery(containerQuery);

		containerQuery.append(" LEFT OUTER JOIN   MALFLTCONRTG RTG ON ").append(
				" MST.CMPCOD =  RTG.CMPCOD AND ").append(
				" MST.CONNUM =  RTG.CONNUM  AND").append(
				" MST.FLTCARIDR =  RTG.FLTCARIDR  AND ").append(
				" MST.FLTNUM =  RTG.FLTNUM AND").append(
				" MST.FLTSEQNUM =  RTG.FLTSEQNUM  AND ");
		if(MailConstantsVO.OPERATION_OUTBOUND.equals(operationType)) {
			containerQuery.append(" MST.LEGSERNUM =  RTG.LEGSERNUM  AND ");
		}
		containerQuery.append(" MST.ASGPRT =  RTG.ASGPRT  ");

		if (this.searchContainerFilterVO.isExcATDCapFlights()) {
			containerQuery.append(" LEFT OUTER JOIN FLTOPRLEG  LEG ON ").append(
					" mst.cmpcod = LEG.cmpcod ").append(
					" AND mst.fltcaridr = LEG.fltcaridr ").append(
					" AND mst.fltnum = LEG.fltnum ").append(
					" AND mst.fltseqnum = LEG.fltseqnum ").append(
					" AND mst.asgprt = LEG.legorg ");
		}
		if(searchContainerFilterVO.getSubclassGroup() != null && !searchContainerFilterVO.getSubclassGroup().equals(nonSQ)){
			containerQuery.append(" LEFT OUTER JOIN mallodplnfltcon  lodpln ON ")
					.append("mst.cmpcod = lodpln.cmpcod ")
					.append(" AND mst.connum = lodpln.connum  AND lodpln.lodplnsta = 'N' ")
					.append(" AND mst.uldrefnum = lodpln.uldrefnum ")
					.append(" LEFT OUTER JOIN fltoprleg  plnleg ON ")
					.append("  lodpln.cmpcod = plnleg.cmpcod " )
					.append(" AND lodpln.fltcaridr = plnleg.fltcaridr ")
					.append(" AND lodpln.fltnum = plnleg.fltnum ")
					.append(" AND lodpln.fltseqnum = plnleg.fltseqnum ")
					.append(" AND lodpln.segorg = plnleg.legorg AND lodpln.lodplnsta = 'N'")
					.append(" LEFT OUTER JOIN SHRARLMST ARLMST ON plnleg.FLTCARIDR  = ARLMST.ARLIDR ")
					.append(" AND plnleg.CMPCOD    = ARLMST.CMPCOD ");
		}
		containerQuery.append(" LEFT OUTER JOIN   MALULDSEGDTL SEG ON ").append(
				" MST.CMPCOD=SEG.CMPCOD  AND").append(
				" MST.CONNUM =  SEG.CONNUM  AND").append(
				" MST.FLTCARIDR=SEG.FLTCARIDR  AND ").append(
				" MST.FLTNUM=SEG.FLTNUM  AND").append(
				" MST.FLTSEQNUM=SEG.FLTSEQNUM  AND ").append(
				" MST.SEGSERNUM=SEG.SEGSERNUM   " );

		appendMalmragblAndMalmraprogblLeftJoinWithSeg(containerQuery);

		appendJoinQuery(containerQuery);
		if(searchContainerFilterVO.getSubclassGroup() != null && !searchContainerFilterVO.getSubclassGroup().equals(nonSQ)){
			containerQuery
					.append("LEFT OUTER JOIN MALMST MALMST")
					.append(" ON SEG.CMPCOD =MALMST.CMPCOD")
					.append(" AND SEG.MALSEQNUM = MALMST.MALSEQNUM")
					.append(" LEFT OUTER JOIN  MALSUBCLSMST CLSMST ")
					.append("  ON MALMST.CMPCOD = CLSMST.CMPCOD")
					.append(" AND MALMST.MALSUBCLS = CLSMST.SUBCLSCOD");

			containerQuery.append("  LEFT OUTER JOIN  SHRSYSPAR PAR")
					.append(" ON PAR.CMPCOD       = MALMST.CMPCOD")
					.append("  AND PAR.PARCOD       = 'mailtracking.defaults.emssubclasspercentage' ");
		}
		containerQuery.append(" WHERE ");

		/*
		 * if container Number is given, get latest details of the given
		 * container number assigned from the current airport if container
		 * number is blank , from Date/to Date are given if Flight given : get
		 * all container assigned to the given flight details Select from
		 * MTKCONMST and MTKULDSEG (to take total bags /weight) if Destn details
		 * are given, select from MTKCONMST and MTKARPULD (to take total bags
		 * /weight) if both details are not given, select all containers
		 * assigned from the current airport for the given assigned date range.
		 *
		 * First step is to check if either Flight Or Destination is present to
		 * append the Join Tables MTKCONMST and MTKULDSEG /MTKCONMST and
		 * MTKARPULD based on Flight Or Destination present in the FilterVO
		 *
		 */

		if (MailConstantsVO.SEARCH_MODE_FLIGHT.equals(searchMode)) {
			builder.append(conFlightQuery);
			builder.append(flightQuery);

		} else if (MailConstantsVO.SEARCH_MODE_DESTN.equals(searchMode)) {
			builder.append(conDestinationQuery);


		} else {
			//Meaning tat the Search Mode is ALL
			builder.append(containerQuery);
		}

		builder.append("   MST.CMPCOD=?  ");
		this.setParameter(++index, companyCode);
		if ("OPR015".equals(searchContainerFilterVO.getSource()) && !"NA".equals(searchContainerFilterVO.getContainerView())) {
			if(isOracleDataSource) {
				builder.append("AND TO_NUMBER (TO_CHAR (MST.ASGDAT, 'YYYYMMDD')) BETWEEN (TO_NUMBER(TO_CHAR(sysdate, 'YYYYMMDD')))-"+searchContainerFilterVO.getContainerView()+" AND (TO_NUMBER(TO_CHAR(sysdate, 'YYYYMMDD')))");
			}else {
				builder.append("AND TO_NUMBER(TO_CHAR(mst.ASGDAT, 'YYYYMMDD')) >= (TO_NUMBER(TO_CHAR(SYSDATE() - ("+searchContainerFilterVO.getContainerView()+"::NUMERIC || ' days')::INTERVAL, 'YYYYMMDD')))");
			}
		}
		/*
		 * Wenever the Container Number is Specified ,
		 * Select the Conatiner Number with the
		 * maximum AssignmentDate ..
		 */
		if (containerNumber != null && containerNumber.trim().length() > 0) {
			builder.append("  AND MST.CONNUM=?  ");
			builder.append(" AND MST.ASGDAT = ");
			builder.append(" (SELECT MAX (ASGDAT) FROM MALFLTCON ").
					append(" WHERE CMPCOD = MST.CMPCOD ").
					append(" AND CONNUM = MST.CONNUM ").
					append(" AND ASGPRT = MST.ASGPRT) ");
			this.setParameter(++index, containerNumber);
		}

		//Added by A-8164 for mailinbound starts
		if(searchContainerFilterVO.getContainersToList()!=null
				&& searchContainerFilterVO.getContainersToList().size()>0){
			String containerAppend="AND MST.CONNUM IN (";
			for(int i=1;i<searchContainerFilterVO.getContainersToList().size();i++){
				containerAppend=new StringBuffer().append(containerAppend).append("?,").toString();
			}
			containerAppend=new StringBuffer().append(containerAppend).append("?)").toString();
			builder.append(containerAppend);
			for(String mailbag:searchContainerFilterVO.getContainersToList()){
				this.setParameter(++index, mailbag);
			}
			if(searchContainerFilterVO.isBulkPresent()){
				builder.append("  OR(  ");
				builder.append("  SEG.ULDNUM    = 'BULK-").append(filterPort).append("'");
				builder.append("  AND MST.FLTNUM    = ? ");
				builder.append("  AND MST.FLTSEQNUM    = ? ");
				builder.append("  AND MST.LEGSERNUM    = ?) ");
				this.setParameter(++index, searchContainerFilterVO.getFlightNumberFromInbound());
				this.setParameter(++index, searchContainerFilterVO.getFlightSeqNumber());
				this.setParameter(++index, searchContainerFilterVO.getLegSerialNumber());

			}
		}
		//Added by A-8164 for mailinbound ends

		/*
		 *  Added By Karthick V to fetch all the  Containers that can be
		 *  tranferred at the Current Port
		 *  The Containers to be Transferred can  be both from  the
		 *  Inbound Flight and the Outbound ..
		 *  Added the Query Changes to include the same .....
		 */

		int noNeed = 0;
		if((MailConstantsVO.SEARCH_MODE_ALL .equals(searchMode) ||MailConstantsVO.SEARCH_MODE_FLIGHT.equals(searchMode)) &&
				MailConstantsVO.FLAG_YES.equals(searchContainerFilterVO.getTransferStatus()) &&
				MailConstantsVO.OPERATION_INBOUND.equals(operationType) ){
			log.info("SearchContainerFilterQuery", "Append  Query  for  Transfer  Status");
			builder.append("  AND  MST.POU =  ?  ");
			noNeed =1;
			this.setParameter(++index, filterPort);
			builder.append(" AND   MST.DSTCOD  <>  ?  ");
			this.setParameter(++index, filterPort);
			builder.append(" AND   MST.TRAFLG = ?  ");
			this.setParameter(++index, MailConstantsVO.FLAG_NO);

		}

		if (filterPort != null && filterPort.trim().length() > 0) {
			if(MailConstantsVO.OPERATION_INBOUND.equals(operationType)) {
				if(noNeed==0){
					builder.append("  AND MST.POU=? ");
					this.setParameter(++index, filterPort);
				}
			} else {
				builder.append("  AND MST.ASGPRT=? ");
				this.setParameter(++index, filterPort);
			}
		}
		if(MailConstantsVO.OPERATION_OUTBOUND.equals(operationType) || MailConstantsVO.SEARCH_MODE_DESTN.equals(searchMode)){
			if(!navigation)
				builder.append(" AND MST.TXNCOD = 'ASG' ");
		}
		//Modified by A-5526 for bug ICRD-154686
		if(MailConstantsVO.OPERATION_INBOUND.equals(operationType) && !MailConstantsVO.FLAG_YES.equals(searchContainerFilterVO.getTransferStatus())){
			builder.append("  AND ( MST.ARRSTA = 'Y' ");
			builder.append("  OR MST.TRNFLG = 'Y' ) ");
		} else {
			if(!("Y".equals(mailAcceptedFlag)) && "Y".equals(showEmpty)){
				if(!navigation)
					builder.append(" AND coalesce(MST.ARRSTA,  'N')   = 'N'");
			}
			else {
				if(!navigation)
					builder.append(" AND coalesce(MST.ARRSTA,  'N')   = 'N' ");
			}
		}


		if (flightNumber != null && flightNumber.trim().length() > 0) {
			builder.append("  AND MST.FLTNUM=? ");
			this.setParameter(++index, flightNumber);
		}
		//Added as part of BUg ICRD-117388 by A-5526 starts
		if(("I".equals(searchContainerFilterVO.getOperationType()) && "Y".equals(searchContainerFilterVO.getTransferStatus()))||"Y".equals(searchContainerFilterVO.getShowUnreleasedContainer())) {
			builder.append("  AND MST.TRNFLG = 'Y' ");
		}
		//Added as part of BUg ICRD-117388 by A-5526 ends
		if (carrierID > 0) {
			builder.append(" AND  MST.FLTCARIDR=? ");
			this.setParameter(++index, carrierID);
		}

		if (assignedBy != null && assignedBy.trim().length() > 0) {
			builder.append(" AND  UPPER(MST.USRCOD) LIKE '%").append(
					assignedBy.toUpperCase()).append("%'");
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYYMMDD");
		if (fromDate != null) {
			builder.append(" AND  TO_NUMBER (to_char (MST.ASGDAT, 'YYYYMMDD')) >= ? ");
			this.setParameter(++index, Integer.parseInt(fromDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD))));
		}

		if (toDate != null) {
			builder.append(" AND  TO_NUMBER (to_char (MST.ASGDAT, 'YYYYMMDD')) <= ? ");
			this.setParameter(++index, Integer.parseInt(toDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD))));
		}
//added by A-5945 for ICRD-96261
		if(searchContainerFilterVO.getPartnerCarriers()!=null){

			builder.append("AND MST. FLTCARCOD IN (") ;
			ArrayList<String> carriervalue =searchContainerFilterVO.getPartnerCarriers();

			// String[] carriervalues = carriervalue.split(",");
			for(String s: carriervalue){
				builder.append("'");
				builder.append(s);
				builder.append("'");
				builder.append(",");
			}
			int i =	 builder.lastIndexOf(",");
			builder.deleteCharAt(i);
			builder.append(")");
		}

		if(MailConstantsVO.SEARCH_MODE_DESTN.equals(searchMode)){
			if(!"Y".equals(showEmpty)){
				builder.append(" AND coalesce(ULD.ACPBAG, 0) >0 ");
			}
		}else{
			if(!"Y".equals(showEmpty)){
				builder.append(" AND coalesce(SEG.ACPBAG, 0) >0 ");
			}

		}
		if(searchContainerFilterVO.isExcATDCapFlights() &&
				(MailConstantsVO.SEARCH_MODE_ALL .equals(searchMode) ||MailConstantsVO.SEARCH_MODE_FLIGHT.equals(searchMode))){
			builder.append (" AND leg.atd IS NULL ");
		}


		if("Y".equals(searchContainerFilterVO.getUldFulIndFlag())) {
			builder.append("AND MST.ULDFULIND='Y'");
		}

		appendWhereClause(builder);

		if (flightdate != null) {
			builder.append(" AND  TO_NUMBER (to_char (ASG.FLTDAT, 'YYYYMMDD')) = ?  ");
			this.setParameter(++index, Integer.parseInt(flightdate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD))));
		}


		if(destionationCarrierId > 0 ) {
			builder.append(" AND  MST.FLTCARIDR=? ");
			this.setParameter(++index, destionationCarrierId);

		}
		//CHANGED FOR BUG 49122
		if(finalDestination != null && finalDestination.trim().length() > 0) {
			log.info("SearchContainerFilterQuery", "FINAL DESTINATION1");
			builder.append("  AND MST.DSTCOD = ? ");
			this.setParameter(++index, finalDestination);
		}

		 /*if(containerDestination != null && containerDestination.trim().length() > 0) {
			  log.log(Log.INFO,"FINAL DESTINATION1");
			 builder.append("  AND MST.DSTCOD = ? ");
			 this.setParameter(++index, containerDestination);
		 } */

		if(MailConstantsVO.OPERATION_OUTBOUND.equals(operationType) &&"Y".equals(mailAcceptedFlag) && "N".equals(showEmpty)
				&& (MailConstantsVO.SEARCH_MODE_ALL .equals(searchMode) ||MailConstantsVO.SEARCH_MODE_FLIGHT.equals(searchMode))) {
			builder.append(" AND (EXISTS (SELECT 1 FROM MALULDSEGDTL MALSEG")
					.append(" WHERE MST.CMPCOD     =MALSEG.CMPCOD AND MST.CONNUM       =MALSEG.CONNUM")
					.append(" AND MST.FLTCARIDR    =MALSEG.FLTCARIDR AND MST.FLTNUM  =MALSEG.FLTNUM")
					.append(" AND MST.FLTSEQNUM    =MALSEG.FLTSEQNUM AND MST.SEGSERNUM      =MALSEG.SEGSERNUM")
					.append(" AND NOT EXISTS (SELECT 1 FROM MALULDSEGDTL MALSEG1 , MALFLTSEG FLTSEG,MALFLTSEG FLTSEG2")
					.append(" WHERE MALSEG.MALSEQNUM = MALSEG1.MALSEQNUM")
					.append(" AND MALSEG.CONNUM = MALSEG1.CONNUM")
					.append(" AND MALSEG.CMPCOD = MALSEG1.CMPCOD AND MALSEG1.CMPCOD = FLTSEG.CMPCOD")
					.append(" AND MALSEG1.FLTNUM = FLTSEG.FLTNUM AND MALSEG1. FLTSEQNUM =  FLTSEG.FLTSEQNUM")
					.append(" AND MALSEG1.SEGSERNUM =  FLTSEG.SEGSERNUM ")
					.append(" and FLTSEG.POU = MALSEG.SCNPRT and MALSEG1.TRAFLG = 'Y')  ))");

			 /*.append(" OR EXISTS(SELECT 1 FROM MTKDSNCSGCONSEG CONSEG  WHERE MST.CMPCOD =CONSEG.CMPCOD")
			 .append(" AND MST.CONNUM =CONSEG.CONNUM AND MST.FLTCARIDR =CONSEG.FLTCARIDR")
			 .append(" AND MST.FLTNUM =CONSEG.FLTNUM AND MST.FLTSEQNUM =CONSEG.FLTSEQNUM")
					 .append(" AND MST.SEGSERNUM =CONSEG.SEGSERNUM AND NOT EXISTS (SELECT 1 FROM MTKDSNCSGCONSEG CONSEG1 , MTKASGFLTSEG FLTSEG,MTKASGFLTSEG FLTSEG2")
					 .append(" WHERE CONSEG.DSN   = CONSEG1.DSN  AND CONSEG.ORGEXGOFC = CONSEG1.ORGEXGOFC")
					 .append(" AND CONSEG.DSTEXGOFC = CONSEG1.DSTEXGOFC  AND CONSEG.MALCTGCOD = CONSEG1.MALCTGCOD")
					 .append(" AND CONSEG.MALSUBCLS = CONSEG1.MALSUBCLS AND CONSEG.YER = CONSEG1.YER")
					 .append(" AND CONSEG.ULDNUM = CONSEG1.ULDNUM AND CONSEG.CMPCOD = CONSEG1.CMPCOD")
					 .append(" AND CONSEG1.CMPCOD = FLTSEG.CMPCOD AND CONSEG1.FLTNUM = FLTSEG.FLTNUM")
					 .append(" AND CONSEG1. FLTSEQNUM =  FLTSEG.FLTSEQNUM AND CONSEG1.SEGSERNUM =  FLTSEG.SEGSERNUM")
					 .append(" AND CONSEG.CMPCOD = FLTSEG2.CMPCOD AND CONSEG.FLTNUM = FLTSEG2.FLTNUM")
					 .append(" AND CONSEG. FLTSEQNUM =  FLTSEG2.FLTSEQNUM AND CONSEG.SEGSERNUM =  FLTSEG2.SEGSERNUM")
					 .append(" and FLTSEG.POU = FLTSEG2.POL and CONSEG1.TRAFLG = 'Y') )")


			 .append(" OR EXISTS(SELECT 1 FROM MTKDSNCSGULDSEG ULDSEG WHERE MST.CMPCOD =ULDSEG.CMPCOD")
			 .append(" AND MST.CONNUM         =ULDSEG.ULDNUM AND MST.FLTCARIDR      =ULDSEG.FLTCARIDR")
			 .append(" AND MST.FLTNUM         =ULDSEG.FLTNUM AND MST.FLTSEQNUM      =ULDSEG.FLTSEQNUM")
					 .append(" AND MST.SEGSERNUM =ULDSEG.SEGSERNUM AND NOT EXISTS (SELECT 1 FROM MTKDSNCSGULDSEG ULDSEG1 , MTKASGFLTSEG FLTSEG,MTKASGFLTSEG FLTSEG2")
					 .append(" WHERE ULDSEG.DSN   = ULDSEG1.DSN AND ULDSEG.ORGEXGOFC = ULDSEG1.ORGEXGOFC")
					 .append(" AND ULDSEG.DSTEXGOFC = ULDSEG1.DSTEXGOFC AND ULDSEG.MALCTGCOD = ULDSEG1.MALCTGCOD")
					 .append(" AND ULDSEG.MALSUBCLS = ULDSEG1.MALSUBCLS AND ULDSEG.YER = ULDSEG1.YER")
					 .append(" AND ULDSEG.ULDNUM = ULDSEG1.ULDNUM AND ULDSEG.CMPCOD = ULDSEG1.CMPCOD")
					 .append(" AND ULDSEG1.CMPCOD = FLTSEG.CMPCOD AND ULDSEG1.FLTNUM = FLTSEG.FLTNUM")
					 .append(" AND ULDSEG1. FLTSEQNUM =  FLTSEG.FLTSEQNUM AND ULDSEG1.SEGSERNUM =  FLTSEG.SEGSERNUM")
					 .append(" AND ULDSEG.CMPCOD = FLTSEG2.CMPCOD AND ULDSEG.FLTNUM = FLTSEG2.FLTNUM")
					 .append(" AND ULDSEG. FLTSEQNUM =  FLTSEG2.FLTSEQNUM AND ULDSEG.SEGSERNUM =  FLTSEG2.SEGSERNUM")
					 .append(" and FLTSEG.POU = FLTSEG2.POL and ULDSEG1.TRAFLG = 'Y') ))");*/



		}
		//Added By A-5945 for ICRD-100653 starts
		if(MailConstantsVO.OPERATION_INBOUND.equals(operationType)&& "N".equals(showEmpty)){
//			builder.append(" AND (EXISTS (SELECT 1 FROM MALMST MALMST ")
//			.append(" WHERE MALMST.CMPCOD =MST.CMPCOD AND MALMST.CONNUM   = MST.CONNUM ")
//			.append(" AND MALMST.MALSTA  IN ('ARR','DLV'))) " );
			/*.append("  OR EXISTS (SELECT 1  FROM MTKDSNCSGARPULD ARPULD ")
          .append("  WHERE ARPULD.CMPCOD = MST.CMPCOD  AND ARPULD.ULDNUM   = MST.CONNUM")
          .append(" AND ARPULD.ARPCOD   = MST.ASGPRT AND ARPULD.TXNCOD   = 'ARR' )")


          .append(" OR EXISTS  (SELECT 1  FROM MTKDSNCSGARPULD ARPULD ")
          .append("  WHERE ARPULD.CMPCOD = MST.CMPCOD AND ARPULD.ULDNUM   = MST.CONNUM")
          .append(" AND ARPULD.ARPCOD   = MST.ASGPRT AND ARPULD.TXNCOD   = 'ARR' )")


          .append(" OR EXISTS (SELECT 1  FROM MTKDSNCSGCONARP CONARP")
          .append("  WHERE CONARP.CMPCOD =MST.CMPCOD AND CONARP.CONNUM   =MST.CONNUM")
          .append(" AND CONARP.ARPCOD   = MST.ASGPRT AND CONARP.TXNCOD   = 'ARR' ))");*/


		}
		//Added By A-5945 for ICRD-100653 ends
		//added by A-5945 for ICRD-96261
		if(MailConstantsVO.OPERATION_OUTBOUND.equals(operationType) &&"N".equals(mailAcceptedFlag) && "N".equals(showEmpty)
				&& (MailConstantsVO.SEARCH_MODE_ALL .equals(searchMode) ||MailConstantsVO.SEARCH_MODE_FLIGHT.equals(searchMode))) {
			builder.append("AND (EXISTS (SELECT 1 FROM MALULDSEGDTL MALSEG ")
					.append(" WHERE MST.CMPCOD     =MALSEG.CMPCOD")
					.append(" AND MST.CONNUM         =MALSEG.CONNUM")
					.append(" AND MST.FLTCARIDR      =MALSEG.FLTCARIDR")
					.append(" AND MST.FLTNUM         =MALSEG.FLTNUM")
					.append(" AND MST.FLTSEQNUM      =MALSEG.FLTSEQNUM")
					.append(" AND MST.SEGSERNUM      =MALSEG.SEGSERNUM")
					/*.append(" OR EXISTS(SELECT 1 FROM MTKDSNCSGCONSEG CONSEG")
                    .append(" WHERE MST.CMPCOD     =CONSEG.CMPCOD")
                    .append(" AND MST.CONNUM         =CONSEG.CONNUM")
                    .append(" AND MST.FLTCARIDR      =CONSEG.FLTCARIDR")
                    .append(" AND MST.FLTNUM         =CONSEG.FLTNUM")
                    .append(" AND MST.FLTSEQNUM      =CONSEG.FLTSEQNUM")
                    .append(" AND MST.SEGSERNUM      =CONSEG.SEGSERNUM")
                    .append(" OR EXISTS(SELECT 1 FROM MTKDSNCSGULDSEG ULDSEG")
                    .append(" WHERE MST.CMPCOD     =ULDSEG.CMPCOD")
                    .append(" AND MST.CONNUM         =ULDSEG.ULDNUM")
                    .append(" AND MST.FLTCARIDR      =ULDSEG.FLTCARIDR")
                    .append(" AND MST.FLTNUM         =ULDSEG.FLTNUM")
                    .append(" AND MST.FLTSEQNUM      =ULDSEG.FLTSEQNUM")
                    .append("  AND MST.SEGSERNUM      =ULDSEG.SEGSERNUM ))")*/
					.append("))");
		}
		//Added by A-5945 for ICRD-96261 ends
		if( (MailConstantsVO.SEARCH_MODE_ALL .equals(searchMode)
				||MailConstantsVO.SEARCH_MODE_FLIGHT.equals(searchMode))) {
			if(notClosedFlag !=null && "Y".equals(notClosedFlag)){
				builder.append("  AND ASG.EXPCLSFLG = 'O' ");
			}

		}

		if(MailConstantsVO.SEARCH_MODE_DESTN.equals(searchMode) && "Y".equals(mailAcceptedFlag) && "N".equals(showEmpty)){
			builder.append("AND (EXISTS (SELECT 1 FROM MALMST MALMST WHERE MALMST.CMPCOD  =MST.CMPCOD")
					.append(" AND MALMST.CONNUM = MST.CONNUM AND MALMST.SCNPRT = MST.ASGPRT")
					.append(" AND MALMST.MALSTA IN ('ACP','ASG','DMG','OFL'))) ");
			  /*OR EXISTS(SELECT 1 FROM MTKDSNCSGARPULD ARPULD")
			  .append(" WHERE ARPULD.CMPCOD = MST.CMPCOD AND ARPULD.ULDNUM   = MST.CONNUM")
			  .append(" AND ARPULD.ARPCOD   = MST.ASGPRT AND ARPULD.TXNCOD  = 'ASG'")
			  .append(" ) ");
			  /*OR EXISTS(SELECT 1 FROM MTKDSNCSGCONARP CONARP ")
			  .append(" WHERE CONARP.CMPCOD  =MST.CMPCOD AND CONARP.CONNUM      =MST.CONNUM")
			  .append(" AND CONARP.ARPCOD      = MST.ASGPRT AND CONARP.TXNCOD  = 'ASG'))");*/

		}
		//Added by A-5945 for ICRD-96261 starts
		if(MailConstantsVO.SEARCH_MODE_DESTN.equals(searchMode) && "N".equals(mailAcceptedFlag) && "N".equals(showEmpty)){
			builder.append("AND (EXISTS (SELECT 1 FROM MALMST MALMST WHERE MALMST.CMPCOD  =MST.CMPCOD")
					.append(" AND MALMST.CONNUM = MST.CONNUM AND MALMST.SCNPRT = MST.ASGPRT")
					.append(" AND MALMST.MALSTA IN ('ACP','ASG','DMG','OFL','TRA')))");
			  /*OR EXISTS(SELECT 1 FROM MTKDSNCSGARPULD ARPULD")
			  .append(" WHERE ARPULD.CMPCOD = MST.CMPCOD AND ARPULD.ULDNUM   = MST.CONNUM")
			  .append(" AND ARPULD.ARPCOD   = MST.ASGPRT AND ARPULD.TXNCOD  = 'ASG'")
			  .append(" ) OR EXISTS(SELECT 1 FROM MTKDSNCSGCONARP CONARP ")
			  .append(" WHERE CONARP.CMPCOD  =MST.CMPCOD AND CONARP.CONNUM      =MST.CONNUM")
			  .append(" AND CONARP.ARPCOD      = MST.ASGPRT AND CONARP.TXNCOD  = 'ASG'))");*/

		}
		if( (MailConstantsVO.SEARCH_MODE_ALL .equals(searchMode)
				||MailConstantsVO.SEARCH_MODE_FLIGHT.equals(searchMode))) {
			builder.append("GROUP BY  MST.CMPCOD,  MST.CONNUM,  MST.FLTCARIDR, MST.FLTNUM, MST.FLTSEQNUM,  MST.LEGSERNUM,  MST.ASGPRT,")
					.append(" MST.ACPFLG,  MST.POAFLG,   MST.POU ,   MST.RMK,   MST.USRCOD,  MST.ASGDAT,MST.DSTCOD, MST.CONTYP, MST.FLTCARCOD , MST.SEGSERNUM,")
					.append(" MST.OFLFLG,  MST.ARRSTA, MST.TRAFLG, MST.LSTUPDTIM,   MST.LSTUPDUSR, MST.CONJRNIDR, MST.POACOD ,  MST.TRNFLG ,MST.CNTIDR, MST.ACTULDWGT, MST.ACTULDWGTDSP , "
							+ "MST.ACTULDWGTDSPUNT , MST.ULDFULIND, MST.ULDREFNUM,SYSPAR.PARVAL,");

			if("I".equals(searchContainerFilterVO.getOperationType())) {
				builder .append(" ASGSEG.POL  ,");
			}	 else {
				if ("DESTN".equals(searchContainerFilterVO.getSearchMode())) {
					// baseQry1 = new StringBuilder().append(baseQry).append(",NULL POL ").toString();
				}else {
					builder .append(" ASG.ARPCOD  ,");

				}
			}
			builder.append("TO_CHAR(RTG.ONWFLTDAT,'DD-Mon-YYYY') , RTG.ONWFLTNUM, RTG.POU ,RTG.RTGSERNUM, RTG.ONWFLTCARCOD, RTG.FLTCARIDR  , RTG.ONWFLTCARIDR,")
					.append(" ASG.FLTDAT ,  MST.LSTUPDTIM , MST.LSTUPDUSR, ASG.EXPCLSFLG ");


			if(searchContainerFilterVO.getSubclassGroup() != null && !searchContainerFilterVO.getSubclassGroup().equals(nonSQ)){
				builder.append(",lodpln.fltnum, ")
						.append(" plnleg.std ,  ARLMST.TWOAPHCOD  " );
			}
		}
		//Added by A-5945 for ICRd-96261 ends

		/*
		 * This block does the following
		 * Take the UNION  OF Destination AssignedContainers  with the already appended
		 * Query Required For Flight Details..
		 * Creates the Dynamic Query for Destination AssignedContainers only
		 * For Operation Type OutBound since there is no Inbound For Destination
		 *
		 */
		if (MailConstantsVO.SEARCH_MODE_ALL.equals(searchMode) &&
				MailConstantsVO.OPERATION_OUTBOUND.equals(operationType)	) {
			builder.append("UNION")
					.append(" (")
					.append(" SELECT   MST.CMPCOD,MST.CONNUM,MST.FLTCARIDR,MST.FLTNUM,MST.FLTSEQNUM,MST.LEGSERNUM,MST.ASGPRT,MST.ACPFLG,MST.POAFLG,")
					.append(" MST.POU ,MST.RMK,MST.USRCOD,MST.ASGDAT,MST.DSTCOD,MST.CONTYP,MST.FLTCARCOD ULDFLTCARCOD,MST.SEGSERNUM,MST.OFLFLG,MST.ARRSTA, ")
					.append(" '' TRAFLG ,MST.LSTUPDTIM,MST.LSTUPDUSR,MST.CONJRNIDR, MST.POACOD SBCODE,MST.TRNFLG,MST.CNTIDR,MST.ACTULDWGT, MST.ACTULDWGTDSP , MST.ACTULDWGTDSPUNT,MST.ULDFULIND, NULL POL,'' ONWFLTDAT,")
					.append(" '' ONWFLTNUM,'' RTGPOU,0 RTGSERNUM,")
					.append(" '' ONWFLTCARCOD,0  RTGFLTCARIDR ,0 ONWFLTCARIDR,")
					.append(" NULL FLTDAT,'O' CLSFLG,SUM(ULD.ACPBAG) BAGCNT ,SUM(ULD.ACPWGT)  BAGWGT, '' RELFLG, ")
					.append("  MST.LSTUPDTIM AS ULDLSTUPDTIM, MST.LSTUPDUSR AS ULDLSTUPDUSR ")
					.append(", MIN(MST.FSTMALASGDAT) SCNDAT, MIN(MST.ASGPRT) SCNPRT");

			appendSelectQuery(builder);
			if(searchContainerFilterVO.getSubclassGroup() != null && !searchContainerFilterVO.getSubclassGroup().equals(nonSQ)){
				if(isOracleDataSource){
					builder.append(",(CASE WHEN((SUM(CASE WHEN CLSMST.SUBCLSGRP = 'EMS'  THEN 1  ELSE 0 END) /COALESCE(NULLIF(COUNT(CLSMST.SUBCLSGRP),0),1)) > MIN(PAR.PARVAL/100)) THEN 'EMS' ELSE 'NONEMS' END )SUBCLSGRP");
				}else{
					builder.append(",(CASE WHEN((SUM(CASE WHEN CLSMST.SUBCLSGRP = 'EMS'  THEN 1  ELSE 0 END)::decimal /COALESCE(NULLIF(COUNT(CLSMST.SUBCLSGRP),0),1)) > MIN(PAR.PARVAL::numeric/100)) THEN 'EMS' ELSE 'NONEMS' END )SUBCLSGRP");
				}
				builder.append(",  LODPLN.FLTNUM PLNFLTNUM, PLNLEG.STD PLNFLTDAT , ARLMST.TWOAPHCOD PLNFLTCARCOD ");
			}
			builder.append(",   MST.ULDREFNUM ");

			appendProchagRatedCntAndSysparval(builder);


			builder.append(" FROM MALFLTCON MST ");

			appendSysParvalInnerQuery(builder);


			builder.append(" LEFT OUTER JOIN MALARPULDDTL ULD ")
					.append(" ON ")
					.append(" MST.CMPCOD=ULD.CMPCOD  AND ")
					.append(" MST.ASGPRT=ULD.ARPCOD  AND ")
					.append(" MST.CONNUM=ULD.CONNUM   ");


			appendMalmragblAndMalmraprogblLeftJoinWithUld(builder);

			appendJoinQuery(builder);
			if(searchContainerFilterVO.getSubclassGroup() != null && !searchContainerFilterVO.getSubclassGroup().equals(nonSQ)){
				builder.append(" LEFT OUTER JOIN mallodplnfltcon  lodpln ON ")
						.append("mst.cmpcod = lodpln.cmpcod ")
						.append("  AND mst.connum = lodpln.connum ")
						.append(" AND mst.uldrefnum = lodpln.uldrefnum ")
						.append(" AND lodpln.lodplnsta = 'N' ")
						.append(" LEFT OUTER JOIN fltoprleg  plnleg ON ")
						.append("  lodpln.cmpcod = plnleg.cmpcod " )
						.append(" AND lodpln.fltcaridr = plnleg.fltcaridr ")
						.append(" AND lodpln.fltnum = plnleg.fltnum ")
						.append(" AND lodpln.fltseqnum = plnleg.fltseqnum ")
						.append(" AND lodpln.segorg = plnleg.legorg ")
						.append(" AND lodpln.lodplnsta = 'N' ")
						.append(" LEFT OUTER JOIN SHRARLMST ARLMST ON plnleg.FLTCARIDR  = ARLMST.ARLIDR ")
						.append(" AND plnleg.CMPCOD    = ARLMST.CMPCOD ");
			}
			if(searchContainerFilterVO.getSubclassGroup() != null && !searchContainerFilterVO.getSubclassGroup().equals(nonSQ)){
				builder
						.append("LEFT OUTER JOIN MALMST MALMST")
						.append("  ON ULD.CMPCOD   =MALMST.CMPCOD")
						.append("  AND ULD.MALSEQNUM    = MALMST.MALSEQNUM")
						.append("  LEFT OUTER JOIN  MALSUBCLSMST CLSMST ")
						.append("  ON MALMST.CMPCOD = CLSMST.CMPCOD")
						.append("   AND MALMST.MALSUBCLS = CLSMST.SUBCLSCOD");
				builder.append("  LEFT OUTER JOIN  SHRSYSPAR PAR ")
						.append("  ON PAR.CMPCOD       = MALMST.CMPCOD")
						.append("  AND PAR.PARCOD       = 'mailtracking.defaults.emssubclasspercentage' ");
			}
			builder.append(" WHERE ");
			builder.append("   MST.CMPCOD=?  ");
			this.setParameter(++index, companyCode);
			if ("OPR015".equals(searchContainerFilterVO.getSource()) && !"NA".equals(searchContainerFilterVO.getContainerView())) {
				if(isOracleDataSource) {
					builder.append("AND TO_NUMBER (TO_CHAR (MST.ASGDAT, 'YYYYMMDD')) BETWEEN (TO_NUMBER(TO_CHAR(sysdate, 'YYYYMMDD')))-"+searchContainerFilterVO.getContainerView()+" AND (TO_NUMBER(TO_CHAR(sysdate, 'YYYYMMDD')))");
				}else {
					builder.append("AND TO_NUMBER(TO_CHAR(mst.ASGDAT, 'YYYYMMDD')) >= (TO_NUMBER(TO_CHAR(SYSDATE() - ("+searchContainerFilterVO.getContainerView()+"::NUMERIC || ' days')::INTERVAL, 'YYYYMMDD')))");
				}
			}
			if(containerNumber != null && containerNumber.trim().length() > 0) {
				builder.append("  AND MST.CONNUM=?  ");
				builder.append(" AND MST.ASGDAT = ");
				builder.append(" (SELECT MAX (ASGDAT) FROM MALFLTCON ").
						append(" WHERE CMPCOD = MST.CMPCOD ").
						append(" AND CONNUM = MST.CONNUM ").
						append(" AND ASGPRT = MST.ASGPRT) ");
				this.setParameter(++index, containerNumber);
			}


			/*CHANGE BY INDU FOR 49122*/
			//	if(destionationCarrierId>0){
			if(finalDestination != null && finalDestination.trim().length() > 0) {
				log.info("SearchContainerFilterQuery", "Final Destination");
				builder.append("  AND MST.DSTCOD=? ");
				this.setParameter(++index, finalDestination);
			}
			//		}
			//Added by A-3429 for ICRD-83340
			if(containerDestination != null && containerDestination.trim().length() > 0) {
				builder.append("  AND MST.DSTCOD = ? ");
				this.setParameter(++index, containerDestination);
			}
			if("Y".equals(mailAcceptedFlag) && "N".equals(showEmpty)){
				builder.append("AND (EXISTS (SELECT 1 FROM MALMST MALMST WHERE MALMST.CMPCOD  =MST.CMPCOD")
						.append(" AND MALMST.CONNUM = MST.CONNUM AND MALMST.SCNPRT = MST.ASGPRT")
						.append(" AND MALMST.MALSTA IN ('ACP','ASG','DMG','OFL')))");
					  /*OR EXISTS(SELECT 1 FROM MTKDSNCSGARPULD ARPULD")
					  .append(" WHERE ARPULD.CMPCOD = MST.CMPCOD AND ARPULD.ULDNUM   = MST.CONNUM")
					  .append(" AND ARPULD.ARPCOD   = MST.ASGPRT AND ARPULD.TXNCOD  = 'ASG'")
					  .append(" ) OR EXISTS(SELECT 1 FROM MTKDSNCSGCONARP CONARP ")
					  .append(" WHERE CONARP.CMPCOD  =MST.CMPCOD AND CONARP.CONNUM      =MST.CONNUM")
					  .append(" AND CONARP.ARPCOD      = MST.ASGPRT AND CONARP.TXNCOD  = 'ASG'))");*/

			}
			if("N".equals(mailAcceptedFlag) && "N".equals(showEmpty)){
				builder.append("AND (EXISTS (SELECT 1 FROM MALMST MALMST WHERE MALMST.CMPCOD  =MST.CMPCOD")
						.append(" AND MALMST.CONNUM = MST.CONNUM AND MALMST.SCNPRT = MST.ASGPRT")
						.append(" AND MALMST.MALSTA IN ('ACP','ASG','DMG','OFL','TRA')))");/* OR EXISTS(SELECT 1 FROM MTKDSNCSGARPULD ARPULD")
					  .append(" WHERE ARPULD.CMPCOD = MST.CMPCOD AND ARPULD.ULDNUM   = MST.CONNUM")
					  .append(" AND ARPULD.ARPCOD   = MST.ASGPRT AND ARPULD.TXNCOD  = 'ASG'")
					  .append(" ) OR EXISTS(SELECT 1 FROM MTKDSNCSGCONARP CONARP ")
					  .append(" WHERE CONARP.CMPCOD  =MST.CMPCOD AND CONARP.CONNUM      =MST.CONNUM")
					  .append(" AND CONARP.ARPCOD      = MST.ASGPRT AND CONARP.TXNCOD  = 'ASG'))");*/

			}

			if(filterPort != null && filterPort.trim().length() > 0) {
				builder.append("  AND MST.ASGPRT=? ");
				this.setParameter(++index, filterPort);
			}

			if(MailConstantsVO.OPERATION_OUTBOUND.equals(operationType)){
				if(!navigation)
					builder.append(" AND MST.TXNCOD = 'ASG' ");
			}

			//Always Appends the Filter Condition For OutBound
			if(!("Y".equals(mailAcceptedFlag)) && "Y".equals(showEmpty)){
				if(!navigation)
					builder.append(" AND coalesce(MST.ARRSTA,  'N')   = 'N'");
			}
			else {
				if(!navigation)
					builder.append(" AND coalesce(MST.ARRSTA,  'N')   = 'N'");
			}
			//Added as part of BUG ICRD-117388 by A-5526 starts
			if("I".equals(searchContainerFilterVO.getOperationType())||"Y".equals(searchContainerFilterVO.getShowUnreleasedContainer())) {
				builder.append("  AND MST.TRNFLG = 'Y' ");
			}
			//Added as part of BUG ICRD-117388 by A-5526 ends
			if(assignedBy != null && assignedBy.trim().length() > 0) {
				builder.append(" AND  UPPER(MST.USRCOD) LIKE '%").
						append(assignedBy.toUpperCase())
						.append("%'");
			}

			if(!"Y".equals(showEmpty)){
				builder.append(" AND coalesce(ULD.ACPBAG, 0) >0 ");
			}
			if (fromDate != null) {
				builder.append(" AND  TO_NUMBER (to_char (MST.ASGDAT, 'YYYYMMDD')) >= ? ");
				this.setParameter(++index, Integer.parseInt(fromDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD))));
			}

			if (toDate != null) {
				builder.append(" AND  TO_NUMBER (to_char (MST.ASGDAT, 'YYYYMMDD')) <= ? ");
				this.setParameter(++index, Integer.parseInt(toDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD))));
			}
//Added by A-5945 for ICRD-96261
			if(searchContainerFilterVO.getPartnerCarriers()!=null){

				builder.append("AND MST. FLTCARCOD IN (") ;

				ArrayList<String> carriervalue =searchContainerFilterVO.getPartnerCarriers();

				// String[] carriervalues = carriervalue.split(",");
				for(String s: carriervalue){
					builder.append("'");
					builder.append(s);
					builder.append("'");
					builder.append(",");
				}
				int i =	 builder.lastIndexOf(",");
				builder.deleteCharAt(i);
				builder.append(")");
			}


			if (destionationCarrierId > 0) {
				builder.append(" AND  MST.FLTCARIDR=? ");
				this.setParameter(++index, destionationCarrierId);
			}


			/*
			 * Logical statement to neglect the FlightDetails where we expect
			 * only destination assigned Containers..
			 */
			builder.append(" AND  MST.FLTNUM='-1' ");
			if("Y".equals(searchContainerFilterVO.getUldFulIndFlag())) {
				builder.append("AND MST.ULDFULIND='Y'");
			}

			appendWhereClause(builder);
			builder.append("GROUP BY MST.CMPCOD, MST.CONNUM, MST.FLTCARIDR, MST.FLTNUM, MST.FLTSEQNUM,MST.LEGSERNUM, MST.ASGPRT, MST.ACPFLG,MST.POAFLG,MST.POU , MST.RMK,")
					.append("MST.USRCOD, MST.ASGDAT, MST.DSTCOD, MST.CONTYP,MST.FLTCARCOD,MST.SEGSERNUM, MST.OFLFLG, MST.ARRSTA, MST.LSTUPDTIM,MST.LSTUPDUSR,")
					.append("MST.CONJRNIDR, MST.POACOD , MST.TRNFLG,  MST.LSTUPDTIM , MST.LSTUPDUSR,MST.CNTIDR, MST.ACTULDWGT , MST.ACTULDWGTDSP , MST.ACTULDWGTDSPUNT,MST.ULDFULIND,MST.ULDREFNUM,SYSPAR.PARVAL ");

			if(searchContainerFilterVO.getSubclassGroup() != null && !searchContainerFilterVO.getSubclassGroup().equals(nonSQ)){
				builder.append(", lodpln.fltnum, ")
						.append(" plnleg.std ,  ARLMST.TWOAPHCOD  " );
			}
			builder.append(")");

		}
		/*
		 * Logical statement to neglect the FlightDetails where we expect only
		 * destination assigned Containers
		 */

		if (MailConstantsVO.SEARCH_MODE_DESTN.equals(searchMode)) {
			builder.append(" AND  MST.FLTNUM='-1' ");
			builder.append("GROUP BY MST.CMPCOD, MST.CONNUM, MST.FLTCARIDR, MST.FLTNUM, MST.FLTSEQNUM,MST.LEGSERNUM, MST.ASGPRT, MST.ACPFLG,MST.POAFLG,MST.POU , MST.RMK,")
					.append("MST.USRCOD, MST.ASGDAT, MST.DSTCOD, MST.CONTYP,MST.FLTCARCOD,MST.SEGSERNUM, MST.OFLFLG, MST.ARRSTA, MST.TRAFLG, MST.LSTUPDTIM,MST.LSTUPDUSR,")
					.append("MST.CONJRNIDR, MST.POACOD , MST.TRNFLG,  MST.LSTUPDTIM , MST.LSTUPDUSR, MST.CNTIDR, MST.ACTULDWGT, MST.ACTULDWGTDSP , MST.ACTULDWGTDSPUNT,MST.ULDFULIND,MST.ULDREFNUM,SYSPAR.PARVAL ");
			//builder.append(")");

			if(searchContainerFilterVO.getSubclassGroup() != null && !searchContainerFilterVO.getSubclassGroup().equals(nonSQ)){
				builder.append(", lodpln.fltnum, ")
						.append(" plnleg.std  ,  ARLMST.TWOAPHCOD " );
			}
		}
		/*
		 * Alias name required for subquery in postgre **/
		if (this.searchContainerFilterVO.getSubclassGroup() != null && !searchContainerFilterVO.getSubclassGroup().equals(nonSQ)) {
			if(isOracleDataSource) {
				builder.append(") ");
			}else {
				builder.append(" ) as LSTSUBQRY ");
			}		      }
		if (this.searchContainerFilterVO.getSubclassGroup() != null && searchContainerFilterVO.getSubclassGroup().equals("EMS")) {
			builder.append(" WHERE SUBCLSGRP = 'EMS' ");
		}
		if (this.searchContainerFilterVO.getSubclassGroup() != null && searchContainerFilterVO.getSubclassGroup().equals("NONEMS") && searchContainerFilterVO.isUnplannedContainers()) {
			builder.append("  WHERE ");
		}
		if(searchContainerFilterVO.isUnplannedContainers() && searchContainerFilterVO.getSubclassGroup().equals("EMS")){
			builder.append(" AND ");
		}
		if(searchContainerFilterVO.isUnplannedContainers()) {
			builder.append(" PLNFLTNUM IS NULL ");
		}


		/*
		 * Since Different  Dynamic Queries are Constructed
		 * This Block includes the Various Order By statemants tat will be appended for different
		 * Queries Constructed based on the Following Conditions.
		 */
		if (MailConstantsVO.SEARCH_MODE_ALL.equals(searchMode)) {
			builder
					.append(" ORDER BY CMPCOD,CONNUM,ASGPRT,FLTCARIDR,FLTNUM,FLTSEQNUM,LEGSERNUM,RTGSERNUM");
		} else if (MailConstantsVO.SEARCH_MODE_DESTN.equals(searchMode)) {
			builder
					.append(" ORDER BY CMPCOD,CONNUM,ASGPRT,FLTCARIDR,FLTNUM,FLTSEQNUM,LEGSERNUM ");
		} else {
			builder
					.append(" ORDER BY CMPCOD,CONNUM,ASGPRT,FLTCARIDR,FLTNUM,FLTSEQNUM,LEGSERNUM,RTGSERNUM ");
		}

		builder.append(")RESULT_TABLE");//added by A-5201 for CR ICRD-21098
		return builder.toString();
	}

	protected void appendWhereClause(StringBuilder builder) {
		//to override in child class
	}
	protected void appendSelectQuery(StringBuilder query) {
		//to override in child class
	}
	protected void appendJoinQuery(StringBuilder query) {
		//to override in child class
	}

	private void appendProchagRatedCntAndSysparval(StringBuilder query ) {
		query.append(",  SUM(COALESCE (BLGDTL.NETAMTBASCUR,PROBLGDTL.NETAMTBASCUR)) PROCHG " );
		query.append(",  SUM(CASE WHEN COALESCE(BLGDTL.NETAMTBASCUR,PROBLGDTL.NETAMTBASCUR) IS NOT NULL  AND COALESCE(BLGDTL.NETAMTBASCUR,PROBLGDTL.NETAMTBASCUR) >0 THEN 1 ELSE 0 END) RATEDCOUNT ");
		query.append(",  SYSPAR.PARVAL  BASCURCOD ");
	}

	private void appendSysParvalInnerQuery(StringBuilder query ) {
		query
				.append(" INNER JOIN SHRSYSPAR SYSPAR ON ")
				.append(" MST.CMPCOD     = SYSPAR.CMPCOD AND ")
				.append(" SYSPAR.PARCOD ='shared.airline.basecurrency' ");
	}

	private void appendMalmragblAndMalmraprogblLeftJoinWithSeg(StringBuilder query ) {
		query.append(" LEFT OUTER JOIN MALMRABLGDTL BLGDTL ON  ")
				.append(" BLGDTL.CMPCOD=SEG.CMPCOD  AND  ")
				.append(" BLGDTL.MALSEQNUM =  SEG.MALSEQNUM  AND  ")
				.append(" (BLGDTL.PAYFLG = 'R' OR (BLGDTL.PAYFLG = 'T' AND  BLGDTL.JONFLG = 'Y'))  ");

		query.append(" LEFT OUTER JOIN MALMRAPROBLGDTL PROBLGDTL ON  ")
				.append(" PROBLGDTL.CMPCOD=SEG.CMPCOD  AND  ")
				.append(" PROBLGDTL.MALSEQNUM =  SEG.MALSEQNUM  AND  ")
				.append(" (PROBLGDTL.PAYFLG = 'R' OR (PROBLGDTL.PAYFLG = 'T' AND  PROBLGDTL.JONFLG = 'Y'))  ");
	}

	private void appendMalmragblAndMalmraprogblLeftJoinWithUld(StringBuilder query ) {
		query.append(" LEFT OUTER JOIN MALMRABLGDTL BLGDTL ON  ")
				.append(" BLGDTL.CMPCOD=ULD.CMPCOD  AND  ")
				.append(" BLGDTL.MALSEQNUM =  ULD.MALSEQNUM  AND  ")
				.append(" (BLGDTL.PAYFLG = 'R' OR (BLGDTL.PAYFLG = 'T' AND  BLGDTL.JONFLG = 'Y'))  ");

		query.append(" LEFT OUTER JOIN MALMRAPROBLGDTL PROBLGDTL ON  ")
				.append(" PROBLGDTL.CMPCOD=ULD.CMPCOD  AND  ")
				.append(" PROBLGDTL.MALSEQNUM =  ULD.MALSEQNUM  AND  ")
				.append(" (PROBLGDTL.PAYFLG = 'R' OR (PROBLGDTL.PAYFLG = 'T' AND  PROBLGDTL.JONFLG = 'Y'))  ");
	}



}
