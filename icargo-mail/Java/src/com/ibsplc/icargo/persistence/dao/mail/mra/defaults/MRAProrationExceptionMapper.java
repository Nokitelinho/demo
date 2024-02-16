
/*

*

* MRAProrationExceptionMapper.java Created on Sep 2008

*

* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.

*

* This software is the proprietary information of IBS
*  Software Services (P) Ltd.

* Use is subject to license terms.

*/
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationExceptionVO;

import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.time.LocalDate;


/**
* @author A-3108
*
*/
public class MRAProrationExceptionMapper implements Mapper<ProrationExceptionVO> {
	
	private static final String CLASS_NAME  = "MRAProrationExceptionMapper";
	private Log log = LogFactory.getLogger("MRA Defaults");

	/**
	 * The method for mapping the DB columns to the VO fields
	 * @param resultSet
	 * @return List
	 * @throws SQLException
	 */
	
	

   public ProrationExceptionVO map( ResultSet resultSet ) 
     throws SQLException {
   	log.entering( CLASS_NAME, "map" );
  
   	ProrationExceptionVO prorationExceptionVO =prorationExceptionVO=new ProrationExceptionVO();
   
   		if(resultSet.getString("CMPCOD")!=null){
   		   	prorationExceptionVO.setCompanyCode(resultSet.getString("CMPCOD"));
   		   	
   		   	}
   		if(resultSet.getString("MALSEQNUM")!=null){
   		   	prorationExceptionVO.setMailSequenceNumber(resultSet.getLong("MALSEQNUM"));
   		   	
   		   	}
   		   	
   		if(resultSet.getString("ORGEXGOFC")!=null){
		   		prorationExceptionVO.setOriginOfficeOfExchange(resultSet.getString("ORGEXGOFC"));
		   	}
   		if(resultSet.getString("DSTEXGOFC")!=null){
		   		prorationExceptionVO.setDestinationOfficeOfExchange(resultSet.getString("DSTEXGOFC"));
		   	}
   		if(resultSet.getString("MALCTGCOD")!=null){
		   		prorationExceptionVO.setMailCategory(resultSet.getString("MALCTGCOD"));
		   	}
   		if(resultSet.getString("MALSUBCLS")!=null){
		   		prorationExceptionVO.setSubClass(resultSet.getString("MALSUBCLS"));
		   	}
   		if(resultSet.getString("YER")!=null){
		   		prorationExceptionVO.setYear(resultSet.getString("YER"));
		   	}
   		if(resultSet.getString("RSN")!=null){
		   		prorationExceptionVO.setReceptacleSerialNumber(resultSet.getString("RSN"));
		   	}
   		if(resultSet.getString("HSN")!=null){
		   		prorationExceptionVO.setHighestNumberIndicator(resultSet.getString("HSN"));
		   	}
   		if(resultSet.getString("REGIND")!=null){
		   		prorationExceptionVO.setRegisteredIndicator(resultSet.getString("REGIND"));
		   	}
   			//Added for ICRD-205027 starts
   		   	if(resultSet.getString("MALIDR")!=null){
   		   		prorationExceptionVO.setMailbagId(resultSet.getString("MALIDR"));
   		   	}
   		   	if(resultSet.getString("DSN")!=null){
   		   		prorationExceptionVO.setDispatchNo(resultSet.getString("DSN"));
   		   	}
   		   	//Added for ICRD-205027 ends
   		   	if(resultSet.getString("CSGDOCNUM")!=null){
   		   		prorationExceptionVO.setConsDocNo(resultSet.getString("CSGDOCNUM"));
   		   	}
   			if(resultSet.getString("CSGSEQNUM")!=null){
   				prorationExceptionVO.setConsSeqNo(Integer.parseInt(resultSet.getString("CSGSEQNUM")));
   			}
   			
   			if(resultSet.getString("POACOD")!=null){
   				prorationExceptionVO.setPoaCode(resultSet.getString("POACOD"));
   			}
   			if(resultSet.getString( "EXPSEQNUM" )!=null){
   			prorationExceptionVO.setExceptionSerialNumber(Integer.parseInt(resultSet.getString( "EXPSEQNUM" )));
   			}
   			if(resultSet.getString( "EXPCOD" )!=null){
   		   	prorationExceptionVO.setExceptionCode( resultSet.getString( "EXPCOD" ) );
   			}
   			if(resultSet.getString( "SECFRM" )!=null){
   			prorationExceptionVO.setSegmentOrigin( resultSet.getString( "SECFRM" ) );
   			}
   			if(resultSet.getString( "SECTOO" )!=null){
   		   	prorationExceptionVO.setSegmentDestination( resultSet.getString( "SECTOO" ) );
   			}
   			
   			if(resultSet.getString( "TRGPNT" )!=null){
   		   	prorationExceptionVO.setTriggerPoint( resultSet.getString( "TRGPNT" ) );
   			}
   			if(resultSet.getDate( "RCVDAT" )!=null){
   		   	prorationExceptionVO.setDate(new LocalDate( LocalDate.NO_STATION, Location.NONE,resultSet.getDate( "RCVDAT" ) ) );
   			}
   			if(resultSet.getString( "PROFCT" )!=null){
   		   	prorationExceptionVO.setProrateFactor(resultSet.getDouble("PROFCT" ));
   			}
   			if(resultSet.getString( "FLTCARCOD" )!=null){
   		   	prorationExceptionVO.setCarrierCode( resultSet.getString( "FLTCARCOD" ) );
   			}
   			if( resultSet.getString( "FLTNUM" )!=null){
   		   	prorationExceptionVO.setFlightNumber( resultSet.getString( "FLTNUM" ) );
   			}
   			if(resultSet.getDate( "FLTDAT" ) !=null){
   			prorationExceptionVO.setFlightDate(
   		   			new LocalDate( LocalDate.NO_STATION, Location.NONE, resultSet.getDate( "FLTDAT" ) ) );
   			}
   		//Added as part of BUG ICRD-106470 by A-5526 starts
   			if( resultSet.getString( "FLTCARIDR" )!=null){
   	   		   	prorationExceptionVO.setFlightCarrierIdentifier( resultSet.getInt( "FLTCARIDR" ) );
   	   			}
   			if( resultSet.getString( "FLTSEQNUM" )!=null){
   	   		   	prorationExceptionVO.setFlightSequenceNumber( resultSet.getInt( "FLTSEQNUM" ) );
   	   			}
   		//Added as part of BUG ICRD-106470 by A-5526 ends
   			if(resultSet.getString( "PCS" )!=null){
   			prorationExceptionVO.setNoOfBags(Integer.parseInt(resultSet.getString( "PCS" )));
   			}
   			if(resultSet.getString( "ASDUSR" )!=null){
   		   	prorationExceptionVO.setAssignedUser(resultSet.getString( "ASDUSR" ));
   			}
   			if ( resultSet.getDate( "ASGDAT" ) != null ) {
   		       	prorationExceptionVO.setAssignedTime(
   		       		new LocalDate( LocalDate.NO_STATION, Location.NONE, resultSet.getDate( "ASGDAT" ) ) );
   		     }
   			if ( resultSet.getDate( "RSDDAT" ) != null ) {
   		       	prorationExceptionVO.setResolvedTime(
   		       		new LocalDate( LocalDate.NO_STATION, Location.NONE, resultSet.getDate( "RSDDAT" ) ) );
   		     }
   			if(resultSet.getString("EXPSTA") !=null){
   			prorationExceptionVO.setStatus(resultSet.getString("EXPSTA"));
   			}
   			if(resultSet.getString( "LSTUPDUSR" )!=null){
   		   	prorationExceptionVO.setLastUpdatedUser( resultSet.getString( "LSTUPDUSR" ) );
   			}
   		   	if ( resultSet.getTimestamp( "LSTUPDTIM" ) != null ) {
   		       	prorationExceptionVO.setLastUpdatedTime(
   		       		new LocalDate( LocalDate.NO_STATION, Location.NONE, resultSet.getTimestamp( "LSTUPDTIM" ) ) );
   		     }   	
   		 if(resultSet.getString( "ROU" )!=null){
    		   	prorationExceptionVO.setRoute( resultSet.getString( "ROU" ) );
    			}
   	
   	
	log.exiting( CLASS_NAME, "map" );
	return prorationExceptionVO;
}
}
