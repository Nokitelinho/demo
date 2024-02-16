/**
 * 
 */
package com.ibsplc.icargo.persistence.dao.customermanagement.defaults;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ListCustomerPointsVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ListPointsAccumulatedFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1883
 *
 */
public class LoyaltyPointsForAwbFilterQuery extends PageableNativeQuery<ListCustomerPointsVO> {
	
	private Log log = LogFactory.getLogger("CUSTOMER MANAGEMENT");
	
	private String baseQuery;
	
	private ListPointsAccumulatedFilterVO filterVO;
	
	/*public LoyaltyPointsForAwbFilterQuery(ListPointsAccumulatedFilterVO filterVO,
			String baseQuery)throws SystemException{
		super();
		this.baseQuery = baseQuery;
		this.filterVO = filterVO;
	}
	*/
    //Added by : A-5175 on 24-Oct-2012	 for icrd-22065
    public LoyaltyPointsForAwbFilterQuery(int totalRecordCount,LoyaltyPointsForAwbMultiMapper awbMultiMapper,ListPointsAccumulatedFilterVO filterVO,
			String baseQuery)throws SystemException{
		super(totalRecordCount,awbMultiMapper);
		this.baseQuery = baseQuery;
		this.filterVO = filterVO;
	}
	
	/**
	 * This method is used to create query string
	 * @return String
	 */
	public String getNativeQuery() {
		log.entering("LoyaltyPointsForAwbFilterQuery","getNativeQuery");
		log.log(Log.FINE, "FilterVO : ", filterVO);
		StringBuilder stringBuilder = new StringBuilder().append(baseQuery);
		int index = 1;
		LocalDate fromDate = filterVO.getFromDate();
		LocalDate toDate = filterVO.getToDate();
		String customerCode = filterVO.getCustomerCode();
		String houseAwbNumber = filterVO.getHouseAwbNumber();
		int ownerIdentifier = filterVO.getMasterOwnerIdentifier();
		int dupNumber = filterVO.getAwbDuplicateNumber();
		int sequenceNumber = filterVO.getAwbSequenceNumber();
		String masterAwbNumber = filterVO.getAwbNumber();// Master AWB number
		if(customerCode != null && customerCode.trim().length() > 0){
			stringBuilder.append(" AND cuscod = ?  ");
			this.setParameter(++index,customerCode);
		}
		if(masterAwbNumber != null && masterAwbNumber.trim().length() > 0){
			stringBuilder.append(" AND mstawbnum = ? ");
			this.setParameter(++index,masterAwbNumber);
		}
		if(ownerIdentifier >= 0){
			stringBuilder.append(" AND docowridr = ? ");
			this.setParameter(++index,ownerIdentifier);
		}
		if(dupNumber >= 0){
			stringBuilder.append(" AND dupnum = ? ");
			this.setParameter(++index,dupNumber);
		}
		if(sequenceNumber >= 0){
			stringBuilder.append(" AND seqnum = ? ");
			this.setParameter(++index,sequenceNumber);
		}
		if(houseAwbNumber != null && houseAwbNumber.trim().length() > 0){
			stringBuilder.append(" AND awbnum = ? ");
			this.setParameter(++index,houseAwbNumber);
		}
		if(fromDate != null){
			stringBuilder.append(" AND crtdat >= ? ");
			this.setParameter(++index,fromDate.toCalendar());
		}
		if(toDate != null ){
			stringBuilder.append(" AND crtdat <= ? ");
			this.setParameter(++index,toDate.toCalendar());
		}
		stringBuilder.append(" ORDER BY mstawbnum,cuscod ");
		log.exiting("LoyaltyPointsForAwbFilterQuery","getNativeQuery");
		return stringBuilder.toString();
	}
}
