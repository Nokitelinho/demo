package com.ibsplc.icargo.persistence.dao.mail.operations;



import com.ibsplc.icargo.business.mail.operations.vo.MailOnHandDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.SearchContainerFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

public class MailOnHandListFilterQuery extends PageableNativeQuery<MailOnHandDetailsVO>{

	
	private SearchContainerFilterVO searchContainerFilterVO;
	
	private String baseQuery;
	 private static final String ASSSIGNED_ALL = "ALL";	
	

	//added by A-5201 for CR ICRD-21098
	public MailOnHandListFilterQuery(String baseQuery,SearchContainerFilterVO searchContainerFilterVO,MultiMapper<MailOnHandDetailsVO> multimapper) throws SystemException{
		super(searchContainerFilterVO.getTotalRecords(), multimapper);
		this.searchContainerFilterVO = searchContainerFilterVO;
        this.baseQuery = baseQuery;
		
	}
	
	
	
	 public String getNativeQuery() {		 
		 
		 log.entering("SearchContainerFilterQuery", "Inside the NativeQuery");		
		 String companyCode = searchContainerFilterVO.getCompanyCode();
		 String DeparturePort = searchContainerFilterVO.getDeparturePort();
		 String fromDate = searchContainerFilterVO.getStrFromDate();
		 String todate = searchContainerFilterVO.getStrToDate();
		 String assignedUser=searchContainerFilterVO.getSearchMode();		 
			
		 if((fromDate!=null&&fromDate.trim().length()>1)&&(todate!=null&&todate.trim().length()>1))
		 {			
			 int idx = 0;
			 log.entering("Query by log outputss ", "Inside the NativeQuery   "+baseQuery);
			 if(ASSSIGNED_ALL.equalsIgnoreCase(assignedUser.trim()))
				{
				 this.setParameter(++idx, companyCode);
				 this.setParameter(++idx, DeparturePort);
				 this.setParameter(++idx, fromDate);
				 this.setParameter(++idx, todate);		 
				 this.setParameter(++idx, companyCode);
				 this.setParameter(++idx, DeparturePort);
				 this.setParameter(++idx, fromDate);
				 this.setParameter(++idx, todate);
				}
			 else
			 {
				 this.setParameter(++idx, companyCode);
				 this.setParameter(++idx, DeparturePort);
				 this.setParameter(++idx, fromDate);
				 this.setParameter(++idx, todate);		 					 
				
			 }		
		 }		
		 else
		 {	 int idx = 0;
			 baseQuery= baseQuery.replace("AND TRUNC(MST.ASGDAT) <= to_date(?, 'DD-MM-yyyy')", " ");
			 baseQuery= baseQuery.replace("AND TRUNC(MST.ASGDAT) >= to_date(?, 'DD-MM-yyyy')", " ");			 
			 log.entering("Query by logsss y the output with Date removed", "Inside the NativeQuery   "+baseQuery);
			 if(ASSSIGNED_ALL.equalsIgnoreCase(assignedUser.trim()))
				{
				 this.setParameter(++idx, companyCode);
				 this.setParameter(++idx, DeparturePort);				 
				 this.setParameter(++idx, companyCode);
				 this.setParameter(++idx, DeparturePort);
				}
			 else
			 {
				 this.setParameter(++idx, companyCode);
				 this.setParameter(++idx, DeparturePort);				 
				
			 }	 
	 
		 }
	
		  return baseQuery;
	 }
}
