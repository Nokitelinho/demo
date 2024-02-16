/*
 * FlownDespatchMultiMapper.java Created on Feb 13, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.mail.mra.defaults.vo.DespatchVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailFlownVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3295
 *
 */
public class FlownDespatchMultiMapper implements MultiMapper<DespatchVO>{

	private Log log = LogFactory.getLogger("MAILTRACKING MRADEFAULTS");

	/**
	 * @author a-3295
	 * @param rs
	 * @throws SQLException
	 * @return
	 */
	public List<DespatchVO> map(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		log.entering("MAILTRACKING","inside the FlownDespatchMultiMapper");
		List<DespatchVO> despatchVOs =  new ArrayList<DespatchVO>();
		DespatchVO despatchVO = new DespatchVO();
		Collection<MailFlownVO> mailFlownVOs =  new ArrayList<MailFlownVO>();
		MailFlownVO mailFlownVO =new MailFlownVO();
		Collection<String> flightNumbers = new ArrayList<String>();
		String prevDsn= "";
		String dsn = "";	
		while(rs.next()){
			//To populate despatchVO
			despatchVO.setCategory(rs.getString("MALCTGCOD"));
			despatchVO.setSubClass(rs.getString("MALSUBCLS"));
			despatchVO.setGpaCode(rs.getString("GPACOD"));
			despatchVO.setOrigin(rs.getString("ORG"));
			despatchVO.setDestination(rs.getString("DSTN"));

			dsn= rs.getString("DSN");	
			log.log(Log.FINE, " dsn", dsn);
			log.log(Log.FINE, "previous mailid", prevDsn);
			if(dsn.equals(prevDsn)){	
				if(mailFlownVOs !=null && mailFlownVOs.size()>0){
					for(MailFlownVO vo : mailFlownVOs){
						if(vo.getDsn().equals(dsn)){
							flightNumbers.add( rs.getString("FLTNUM"));
							vo.setFlightNumber(flightNumbers);						
							vo.setSectorTo(rs.getString("POU"));							
						}
					}
				}				
				prevDsn = dsn;
			}else{	
				log.log(Log.INFO, "dsn---------------------", rs.getString("DSN"));
				mailFlownVO=new MailFlownVO();
				
				mailFlownVO.setDsn(rs.getString("DSN"));	
				mailFlownVO.setWeight(rs.getDouble("WGT"));
				mailFlownVO.setSectorFrom(rs.getString("POL"));		
				mailFlownVO.setSectorTo(rs.getString("POU"));	
				flightNumbers.add( rs.getString("FLTNUM"));
				mailFlownVO.setFlightNumber(flightNumbers);
				mailFlownVO.setFlightDate(rs.getDate("FLTDAT") != null ? new 
						LocalDate(LocalDate
								.NO_STATION,Location
								.NONE,rs
								.getDate("FLTDAT")):null);
				mailFlownVOs.add(mailFlownVO);			
				prevDsn = dsn;
			}

		}
		despatchVO.setMailFlowndetails(mailFlownVOs);
		despatchVOs.add(despatchVO);
		log.log(Log.FINE, "despatch VO obtained from resultset", despatchVOs);
		log.exiting("MAILTRACKING","inside the FlownDespatchMultiMapper");
		return despatchVOs;		

	}
}
