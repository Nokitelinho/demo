package com.ibsplc.icargo.persistence.dao.uld.defaults;

import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMMessageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author 
 *
 */

public class ListULDDetailsForSCMFilterQuery  extends PageableNativeQuery  <ULDVO>{

	private static final String CLASS_NAME = "ListULDDetailsForSCMFilterQuery";
	private Log log = LogFactory.getLogger("ULD_DEfaults");
	private SCMMessageFilterVO filterVO;

	private String baseQuery;
	
	public ListULDDetailsForSCMFilterQuery(int totalRecordCount, String baseQuery, 
			ULDDetailsMapper mapper,SCMMessageFilterVO filterVO) throws SystemException {
		super(totalRecordCount, mapper);
		this.filterVO = filterVO;
		this.baseQuery = baseQuery;
		
	}

	public String getNativeQuery() {
		log.entering(CLASS_NAME, "getNativeQuery");
		
		int index = 0;
		this.setParameter(++index, filterVO.getCompanyCode());

		this.setParameter(++index, filterVO.getAirportCode());

		this.setParameter(++index, filterVO.getFlightCarrierIdentifier());

		this.setParameter(++index, filterVO.getStockControlDate());
		// added by A-6344 for ICRD-55460 starts
		StringBuilder queryFilter = new StringBuilder("");
		if (filterVO.getUldNumber() != null
				&& filterVO.getUldNumber().trim().length() > 0) {
			//Added by A-3791 for ICRD-192238
			String uldNumber = filterVO.getUldNumber().trim().toUpperCase();
			if(uldNumber.contains("*") || uldNumber.contains("%")){
				uldNumber = uldNumber.replace("*", "%");
				queryFilter.append(" AND ULDNUM LIKE ? ");
			}else{
			queryFilter.append(" AND ULDNUM=? ");
			}
			this.setParameter(++index, uldNumber);
		}
		//added as part of ICRD-270001 by A-4393 
		if(filterVO.isMissingDiscrepancyCaptured()){ 
		StringBuilder status = new StringBuilder("");
			if (filterVO.getUldStatus() != null
					&& filterVO.getUldStatus().length() != 0) {
				int count = 0;
				for (String s : filterVO.getUldStatus().split(",")) {
					if (count == 0)
						status.append("'" + s + "'");
					else
						status.append(",'" + s + "'");
					count++;
				}
				queryFilter.append(" AND DISCOD IN (" + status.toString() + ") ");
			}
		}else{
		StringBuilder status = new StringBuilder("");
		if (filterVO.getUldStatus() != null
				&& filterVO.getUldStatus().length() != 0) {
			int count = 0;
			for (String s : filterVO.getUldStatus().split(",")) {
				if (count == 0)
					{
					status.append("'" + s + "'");
					}
				else
					{
					status.append(",'" + s + "'");
					}
				count++;
			}
			queryFilter.append(" AND ULDSTKSTA IN (" + status.toString() + ") ");

		}
		}
		//added as part of ICRD-270001 by A-4393
		// added by A-6344 for ICRD-55460 end

		log.log(Log.FINE, "The Filter Values ", filterVO);
		if (filterVO.getFacility() != null
				&& filterVO.getFacility().trim().length() > 0) {
			queryFilter.append(" AND FACTYP=? ");
			this.setParameter(++index, filterVO.getFacility().trim());
		}
		if (filterVO.getLocation() != null
				&& filterVO.getLocation().trim().length() > 0) {
			queryFilter.append(" AND LOC=? ");
			this.setParameter(++index, filterVO.getLocation().trim());
		}

		StringBuilder finalQuery = new StringBuilder(baseQuery);
		finalQuery.append(queryFilter);
		log.log(Log.FINE, "The finalQuery---->", finalQuery.toString());
		log.exiting(CLASS_NAME, "getNativeQuery");
		return finalQuery.toString();

	}

}
