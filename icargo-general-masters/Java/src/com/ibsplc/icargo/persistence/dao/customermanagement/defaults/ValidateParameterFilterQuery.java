/**
 * 
 */
package com.ibsplc.icargo.persistence.dao.customermanagement.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyParameterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1883
 *
 */
public class ValidateParameterFilterQuery extends NativeQuery {
	
	private Log log = LogFactory.getLogger("CUSTOMER MANAGEMENT");
	
	private LoyaltyProgrammeVO loyaltyProgrammeVO;
	
	private String baseQuery;
	/**
	 * Constructor
	 * @param loyaltyProgrammeVO
	 * @param baseQuery
	 * @throws SystemException
	 */
	public ValidateParameterFilterQuery(LoyaltyProgrammeVO loyaltyProgrammeVO,
			String baseQuery) throws SystemException {
		super();
		this.baseQuery = baseQuery;
		this.loyaltyProgrammeVO = loyaltyProgrammeVO;
	}
	/**
	 * This method is used to create query string
	 * @return String
	 */
	public String getNativeQuery() {
		log.entering("ValidateParameterFilterQuery","getNativeQuery");
		StringBuilder stringBuilder = new StringBuilder().append(baseQuery);
		int index = 1;
		boolean isFirstEntry = true ;
		LocalDate fromDate = loyaltyProgrammeVO.getFromDate();
		LocalDate toDate = loyaltyProgrammeVO.getToDate();
		Collection<LoyaltyParameterVO> parameterVOs = 
			loyaltyProgrammeVO.getLoyaltyParameterVOs();
		stringBuilder.append(" AND ( ");
		if(parameterVOs != null && parameterVOs.size() >0 ){
			HashMap<String,Collection<String>> params = createParameterMap(parameterVOs);
			for(String key:params.keySet()){
				if(!isFirstEntry){
					stringBuilder.append(" OR ");
				}
				stringBuilder.append(" parmst.ltyprgpar = ? AND  parmst.parval ");
				stringBuilder.append(getInWhereClause(params.get(key)));
				this.setParameter(++index,key); 
				isFirstEntry = false;
			 }
			stringBuilder.append(" ) ");
		}
		if(toDate != null ){
			stringBuilder.append(" AND prgmst.frmdat <= ? ");
			this.setParameter(++index,toDate.toCalendar());
		}
		if(fromDate != null){
			stringBuilder.append(" AND prgmst.toodat >= ? ");
			this.setParameter(++index,fromDate.toCalendar());
		}
		if(LoyaltyProgrammeVO.OPERATION_FLAG_UPDATE.equals(loyaltyProgrammeVO.getOperationFlag())){
			log.log(Log.FINE," Update Operation ");
			stringBuilder.append("  AND prgmst.ltyprgcod !=  ? ");
			this.setParameter(++index,loyaltyProgrammeVO.getLoyaltyProgrammeCode());
		}
		log.exiting("ValidateParameterFilterQuery","getNativeQuery");
		return stringBuilder.toString();
	}
	/**
	 * Creates Hash map like <parametercode,<val1,val2>>
	 * @param parameterVOs
	 * @return
	 */
	private HashMap<String,Collection<String>> createParameterMap(
			Collection<LoyaltyParameterVO> parameterVOs) {
		log.entering("ValidateParameterFilterQuery","createParameterMap");
		HashMap<String,Collection<String>> params = new HashMap<String,Collection<String>>();
		Collection<String> alreadyChecked = new ArrayList<String>();
		Collection<String> paramValues = null;
		boolean canEnter = true;
		for(LoyaltyParameterVO outerVO:parameterVOs){
			// No need of Validation for parameter to be deleted 
			//if(LoyaltyParameterVO.OPERATION_FLAG_INSERT.equals(outerVO.getOperationFlag())
				//	|| LoyaltyParameterVO.OPERATION_FLAG_UPDATE.equals(outerVO.getOperationFlag())){
			String parameterCode = outerVO.getParameterCode();
			canEnter = true;
			if(alreadyChecked.size() >0){
				for(String old : alreadyChecked){
					if(old.equals(parameterCode)){
						canEnter = false;
						break;
					}
				}
			}
			if(canEnter){
				alreadyChecked.add(parameterCode);
				paramValues = new ArrayList<String>();
				for(LoyaltyParameterVO innerVO:parameterVOs){
					//if(LoyaltyParameterVO.OPERATION_FLAG_INSERT.equals(innerVO.getOperationFlag())
						//	|| LoyaltyParameterVO.OPERATION_FLAG_UPDATE.equals(innerVO.getOperationFlag())){
						if(parameterCode.equals(innerVO.getParameterCode())){
							paramValues.add(innerVO.getParameterValue());
						}
					//}	
				}
				params.put(parameterCode,paramValues);
			}
		 //}			
		}
		log.exiting("ValidateParameterFilterQuery","createParameterMap");
		return params;
	}
	/**
	 *
	 * @param whereValues
	 * @return String
	 */
	private String getInWhereClause(Collection<String> whereValues) {
		String clause = null;
		log.entering("ValidateParameterFilterQuery","getInWhereClause");
		if ((whereValues != null) && (whereValues.size() > 0)) {
			StringBuilder sbul = new StringBuilder().append(" IN ( ");
			ArrayList<String> values = new ArrayList<String>(whereValues);
			String first = values.get(0);
			sbul.append("'").append(first).append("'");
			values.remove(0);
			for (String value : values) {
				sbul.append(",'").append(value).append("'");
			}
			sbul.append(")").toString();
			clause = sbul.toString();
		}
		log.exiting("ValidateParameterFilterQuery","getInWhereClause");
		return clause;
	}
}
