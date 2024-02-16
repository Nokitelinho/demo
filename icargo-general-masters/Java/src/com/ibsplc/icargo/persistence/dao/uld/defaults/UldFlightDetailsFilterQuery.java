package com.ibsplc.icargo.persistence.dao.uld.defaults;

import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-5125
 * 
 *
 */
public class UldFlightDetailsFilterQuery extends NativeQuery{

	
	private Log log = LogFactory.getLogger(" ULD_DEFAULTS");
	 
	

	private ULDFlightMessageFilterVO uldFltmsgFilterVO;
	private String baseQuery ;

	
	
	
	public UldFlightDetailsFilterQuery(
				ULDFlightMessageFilterVO uldFltmsgFilterVO, String baseQuery)
				throws SystemException {
			super();
			this.uldFltmsgFilterVO = uldFltmsgFilterVO;
			this.baseQuery = baseQuery;
		}


	public String getNativeQuery() {
	
		
		log.entering("UldFlightDetailsFilterQuery","getNativeQuery");
    	int index = 0 ;
    	StringBuilder stbld = new StringBuilder().append(baseQuery);
    	this.setParameter(++index , uldFltmsgFilterVO.getCompanyCode());
    	this.setParameter(++index , uldFltmsgFilterVO.getAirportCode());
    	
    	if(uldFltmsgFilterVO.getUldFlightStatus() != null 
    			&& uldFltmsgFilterVO.getUldFlightStatus().trim().length() > 0){
    		stbld.append(" AND ULDFLTSTA = ? ");
    		this.setParameter(++index , uldFltmsgFilterVO.getUldFlightStatus());
    	}
    	
    	if(uldFltmsgFilterVO.getUldNOsForMessageReconcile()!=null
    			&& uldFltmsgFilterVO.getUldNOsForMessageReconcile().size()>0){
    		stbld.append("AND ULDNUM IN (");
    		int count=0; 
    		for(String uldNumber:uldFltmsgFilterVO.getUldNOsForMessageReconcile()){
    			stbld.append("?");
    			this.setParameter(++index, uldNumber);
    			 count++;
    			 if(count<uldFltmsgFilterVO.getUldNOsForMessageReconcile().size()){
    				 stbld.append(",");
    			 }else{
    				 stbld.append(")");
    			 }
    		 }
    		
    	}
    
    	
    	return stbld.toString();	
	}

}
