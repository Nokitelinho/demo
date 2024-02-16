/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.operations.flthandling.RunnerFlightMultiMapper.java
 *
 *	Created by	:	A-7414
 *	Created on	:	19-Sep-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightULDVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.RunnerFlightMultiMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5526	:	12-Oct-2018	:	Draft
 */
public class RunnerFlightMultiMapper implements MultiMapper<RunnerFlightVO> {

	private Log log = LogFactory.getLogger("OPERATIONS FLTHANDLING");
	
	private String listType;
	
	/**
	 *	Constructor	: 	@param listType
	 *	Created by	:	A-7137
	 *	Created on	:	03-Jul-2020
	 */
	public RunnerFlightMultiMapper(String listType) {
		this.listType = listType;
	}

	/**
	 *	Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
	 *	Added by 			: A-5526 on 12-Oct-2018
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SQLException 
	 */
	@Override
	public List<RunnerFlightVO> map(ResultSet rs) throws SQLException {
		log.entering("RunnerFlightMultiMapper", "map");
		RunnerFlightVO runnerFlightVO = null;
		RunnerFlightULDVO runnerFlightUldVO = null;
		String flightKey = null;
		Map<String, RunnerFlightVO> runnerFlightMap = new HashMap<String, RunnerFlightVO>();
		List<RunnerFlightVO> runnerFlightVOs = new ArrayList<RunnerFlightVO>();
		List<RunnerFlightULDVO> runnerFlightULDVOs = new ArrayList<RunnerFlightULDVO>();
		Collection<RunnerFlightULDVO> presentUldVOs = new ArrayList<RunnerFlightULDVO>();
		while (rs.next()) {
			flightKey = new StringBuffer(rs.getString("CMPCOD")).append(rs.getString("ARPCOD"))
					.append(rs.getString("FLTCARIDR")).append(rs.getString("FLTNUM")).append(rs.getString("FLTSEQNUM"))
					.append(rs.getString("LEGSERNUM")).toString();
			if (!runnerFlightMap.containsKey(flightKey)) {
				runnerFlightVO = new RunnerFlightVO();
				runnerFlightVO.setCompanyCode(rs.getString("CMPCOD"));
				runnerFlightVO.setAirportCode(rs.getString("ARPCOD"));
				runnerFlightVO.setCarrierId(rs.getInt("FLTCARIDR"));
				runnerFlightVO.setCarrierCode(rs.getString("FLTCARCOD"));
				runnerFlightVO.setFlightNumber(rs.getString("FLTNUM"));
				runnerFlightVO
						.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getTimestamp("FLTDAT")));
				runnerFlightVO.setFlightDateIndicator(rs.getString("FLTDATIND"));
				runnerFlightVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
				runnerFlightVO.setLegSerialNumber(rs.getString("LEGSERNUM"));
				runnerFlightVO.setLegOrigin(rs.getString("LEGORG"));
				runnerFlightVO.setLegDestination(rs.getString("LEGDST"));
				runnerFlightVO.setOperationalGate(rs.getString("GATE"));
				runnerFlightVO.setAircraftType(rs.getString("ACRTYP"));
				runnerFlightVO.setDummyUnitCount(rs.getInt("UNTCNT"));
				runnerFlightVO.setGateClearanceStatus(rs.getString("GTECLRSTA"));
				/**
				 * populating uldVO for flight
				 */
				runnerFlightULDVOs = new ArrayList<RunnerFlightULDVO>();    
				runnerFlightUldVO = new RunnerFlightULDVO();
				runnerFlightUldVO.setCompanyCode(rs.getString("CMPCOD"));
				if(rs.getString("POU")!=null && !(rs.getString("POU").isEmpty())){
					if((RunnerFlightULDVO.FLAG_YES.equals(rs.getString("BARROWFLG")) ) && ((rs.getString("POU")).equals(rs.getString("ARPCOD")))){
						String contNum = new StringBuilder()
						.append(MailConstantsVO.CONST_BULK)
						.append(MailConstantsVO.SEPARATOR)
						.append(rs.getString("POU")).toString();
						runnerFlightUldVO.setUldNumber(contNum);
					}else{
						runnerFlightUldVO.setUldNumber(rs.getString("ULDNUM"));
					}
				}else{
					runnerFlightUldVO.setUldNumber(rs.getString("ULDNUM"));
				}
				//runnerFlightUldVO.setPou(rs.getString("POU"));commented to fix compilation issue
				runnerFlightUldVO.setContentId(rs.getString("CONIDR"));
				runnerFlightUldVO.setLocation(rs.getString("LOCCOD"));
				if (RunnerFlightULDVO.FLAG_YES.equals(rs.getString("BARROWFLG"))) {
					runnerFlightUldVO.setBarrowFlag(true);
				}
				if (RunnerFlightULDVO.FLAG_YES.equals(rs.getString("BDPCMPSTA"))) {
					runnerFlightUldVO.setBuildUpComplete(true);
				}
				runnerFlightUldVO.setUldPriority(rs.getInt("ULDPTY"));
				runnerFlightUldVO.setManifestedWeight(new Measure(UnitConstants.WEIGHT, rs.getDouble("MFGWGT")));
				runnerFlightUldVO.setActualULDWeight(new Measure(UnitConstants.WEIGHT, rs.getDouble("ACTULDWGT")));
				runnerFlightUldVO.setMailBagCount(rs.getInt("ACPBAG"));
				runnerFlightULDVOs.add(runnerFlightUldVO);
				runnerFlightVO.setRunnerFlightULDs(runnerFlightULDVOs);
				runnerFlightMap.put(flightKey, runnerFlightVO);
			} else {
				runnerFlightVO = runnerFlightMap.get(flightKey);
				presentUldVOs = runnerFlightVO.getRunnerFlightULDs();
				runnerFlightUldVO = new RunnerFlightULDVO();
				runnerFlightUldVO.setCompanyCode(rs.getString("CMPCOD"));
				runnerFlightUldVO.setUldNumber(rs.getString("ULDNUM"));
				//runnerFlightUldVO.setPou(rs.getString("POU")); commented to fix compilation issue.
				if(rs.getString("POU")!=null && !(rs.getString("POU").isEmpty())){
					if((RunnerFlightULDVO.FLAG_YES.equals(rs.getString("BARROWFLG")) ) && ((rs.getString("POU")).equals(rs.getString("ARPCOD")))){
						String contNum = new StringBuilder()
						.append(MailConstantsVO.CONST_BULK)
						.append(MailConstantsVO.SEPARATOR)
						.append(rs.getString("POU")).toString();
						runnerFlightUldVO.setUldNumber(contNum);
					}
				}
				runnerFlightUldVO.setContentId(rs.getString("CONIDR"));
				runnerFlightUldVO.setLocation(rs.getString("LOCCOD"));
				if (RunnerFlightULDVO.FLAG_YES.equals(rs.getString("BARROWFLG"))) {
					runnerFlightUldVO.setBarrowFlag(true);
				}
				if (RunnerFlightULDVO.FLAG_YES.equals(rs.getString("BDPCMPSTA"))) {
					runnerFlightUldVO.setBuildUpComplete(true);
				}
				runnerFlightUldVO.setUldPriority(rs.getInt("ULDPTY"));
				runnerFlightUldVO.setManifestedWeight(new Measure(UnitConstants.WEIGHT, rs.getDouble("MFGWGT")));
				runnerFlightUldVO.setActualULDWeight(new Measure(UnitConstants.WEIGHT, rs.getDouble("ACTULDWGT")));
				runnerFlightUldVO.setMailBagCount(rs.getInt("ACPBAG"));
				presentUldVOs.add(runnerFlightUldVO);
				runnerFlightVO.setRunnerFlightULDs(presentUldVOs);
			}
			
		}
		if(runnerFlightMap.values()!=null)    
			runnerFlightVOs.addAll(runnerFlightMap.values());
		/**
		 * Call Collections.sort to sort the returned collection
		 * based on the flight date
		 */
		if(runnerFlightVOs != null && runnerFlightVOs.size() > 0){
			if(RunnerFlightVO.LISTTYPE_INBOUND.equals(listType)){
				removeMultipleBulkUnits(runnerFlightVOs);
			}
			Collections.sort(runnerFlightVOs, new RunnerFlightComparator());
		}
		return runnerFlightVOs;
	}
	
	/**
	 * 	Method		:	RunnerFlightMultiMapper.removeMultipleBulkUnits
	 *	Added by 	:	A-7137 on 03-Jul-2020
	 * 	Used for 	:	IASCB-57132
	 *	Parameters	:	@param runnerFlightVOs 
	 *	Return type	: 	void
	 */
	private void removeMultipleBulkUnits(List<RunnerFlightVO> runnerFlightVOs) {
		log.entering("RunnerFlightMultiMapper", "removeMultipleBulkUnits");
		Collection<RunnerFlightULDVO> bulkUnits = null;
		for (RunnerFlightVO runnerFlightVO : runnerFlightVOs) {
			if(runnerFlightVO.getRunnerFlightULDs() != null && !runnerFlightVO.getRunnerFlightULDs().isEmpty()) {
				bulkUnits = new ArrayList<>();
				for (RunnerFlightULDVO runnerFlightULDVO : runnerFlightVO.getRunnerFlightULDs()) {
					if(runnerFlightULDVO.isBarrowFlag()) {
						bulkUnits.add(runnerFlightULDVO);
					}
				}
				if(bulkUnits.size() > 1) {
					RunnerFlightULDVO runnerFlightULDVO = bulkUnits.iterator().next().clone();
					runnerFlightVO.getRunnerFlightULDs().removeAll(bulkUnits);
					runnerFlightVO.getRunnerFlightULDs().add(runnerFlightULDVO);
				}
			}
		}
		log.exiting("RunnerFlightMultiMapper", "removeMultipleBulkUnits");
	}

	/**
	 * 
	 *	Java file	: 	com.ibsplc.icargo.business.warehouse.defaults.ramp.RampController.java
	 *	Version		:	Name	:	Date			:	Updation
	 * ---------------------------------------------------
	 *		0.1		:	A-7414	:	19-Jun-2019	:	Draft
	 */
	private class RunnerFlightComparator implements Comparator<RunnerFlightVO> {

		/**
		 * Overriding Method : @see
		 * java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 * Added by : A-7414 on 19-Jun-2019 Parameters : @param
		 * firstRunnerFlightVO Parameters : @param secondRunnerFlightVO
		 * Parameters : @return
		 */
		@Override
		public int compare(RunnerFlightVO firstRunnerFlightVO, RunnerFlightVO secondRunnerFlightVO) {

			if (firstRunnerFlightVO.getFlightDate() != null && secondRunnerFlightVO.getFlightDate() != null) {
				return firstRunnerFlightVO.getFlightDate().compareTo(secondRunnerFlightVO.getFlightDate());
			}
			return 0;
		}

	}

}
