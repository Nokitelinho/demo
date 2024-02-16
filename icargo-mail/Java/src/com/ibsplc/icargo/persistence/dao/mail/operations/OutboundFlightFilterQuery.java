package com.ibsplc.icargo.persistence.dao.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class OutboundFlightFilterQuery  extends PageableNativeQuery<MailAcceptanceVO> {
	 private StringBuilder baseQuery1;
	 private String baseQuery2;
	private OperationalFlightVO operationalFlightVO;
	private boolean isOracleDataSource;
	
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	public OutboundFlightFilterQuery(int recordsPerPage,OperationalFlightVO operationalFlightVO,StringBuilder baseQuery1,String baseQuery2, boolean isOracleDataSource,OutboundFlightMapper mapper) throws SystemException {
	    super(recordsPerPage,0, mapper); 
		this.baseQuery1 = baseQuery1;
		this.baseQuery2 = baseQuery2;
		this.operationalFlightVO = operationalFlightVO;
		this.isOracleDataSource = isOracleDataSource;
	}
	@Override
	public String getNativeQuery() {
		log.entering("OutboundFlightFilterQuery", "Inside the NativeQuery");
		LogonAttributes logonAttributes = null;
		try {
			logonAttributes = ContextUtils.getSecurityContext()
					.getLogonAttributesVO();
		} catch (SystemException e) {
			log.log(Log.SEVERE, " logonAttributes exception");
		}
		int index =0;
		StringBuilder stringBuilder = new StringBuilder();  
	    stringBuilder.append(this.baseQuery1);
		String companyCode = operationalFlightVO.getCompanyCode(); 
		log.log(Log.FINE, "Company Code-------------->>>>>>>>>>", companyCode);
		String flightNumber = operationalFlightVO.getFlightNumber();
		LocalDate flightDate = operationalFlightVO.getFlightDate();
		int carrierId =operationalFlightVO.getCarrierId();
		LocalDate fromDate = operationalFlightVO.getFromDate();
		LocalDate toDate = operationalFlightVO.getToDate();
		String pou =operationalFlightVO.getPou();
		String	flightOperationalstatus=operationalFlightVO.getFlightOperationStatus();
		long flightSeqNum = operationalFlightVO.getFlightSequenceNumber();
		String	flightStatus=operationalFlightVO.getFlightStatus();
		String	operatingReference=operationalFlightVO.getOperatingReference();
		//added by as part of IASCB-56622
		boolean includeAllTrucks = operationalFlightVO.isIncludeAllMailTrucks();
		boolean  mailFlightOnly= operationalFlightVO.isMailFlightOnly();
		boolean isFlightFilterPresent = false;
		if (operationalFlightVO.getFlightNumber() != null && operationalFlightVO.getFlightNumber().trim().length() > 0 && operationalFlightVO.getFlightDate() != null) {
			isFlightFilterPresent = true;
		}

		stringBuilder.append(" ");
		
		
			

			
		
		
		
			

			
		
		
		stringBuilder.append(this.baseQuery2);
		this.setParameter(++index,companyCode);
        this.setParameter(++index,carrierId);
        //Added as part of performance testing
        if(isOracleDataSource){
      	  stringBuilder.append(" AND NVL(FLTCON.ACPFLG,'N') = (CASE WHEN FLTCON.ACPFLG IS NULL THEN 'N'  ELSE 'Y' END) ");  
        }else{
      	  stringBuilder.append("AND  CASE  WHEN FLTCON.ACPFLG IS NULL THEN 1=1   ELSE  FLTCON.ACPFLG    ='Y' END ");  
      	    
        }
		stringBuilder.append(" ");
		if(operationalFlightVO.getPol()!=null)
		{
			stringBuilder.append("AND FLTLEG.LEGORG = ?");
			this.setParameter(++index,operationalFlightVO.getPol());
		}
		
		
		if(flightNumber!=null && flightNumber.trim().length() > 0)
		{
			stringBuilder.append(" AND FLTMST.FLTNUM = ? ");
			this.setParameter(++index,flightNumber);
		}
		if(flightDate!=null)
		{
			stringBuilder.append(" AND TRUNC(FLTLEG.STD) = TO_DATE(?, 'yyyy-MM-dd')"); //Changed all flight date fetches, FLTDAT from FLTLEG
			this.setParameter(++index,flightDate.toSqlDate().toString());
		}
		if(flightSeqNum >0)
		{
			stringBuilder.append(" AND FLTMST.FLTSEQNUM = ? ");
			this.setParameter(++index,flightSeqNum);
		}
		if(pou!=null){
			stringBuilder.append(" AND FLTSEG.SEGDST = ? ");
			this.setParameter(++index,pou);
		}
		if(fromDate!=null && !isFlightFilterPresent)
		{
			stringBuilder.append(" AND FLTLEG.STD >= ? ");
			setParameter(++index, fromDate);			
		}
		
		if(toDate!=null && !isFlightFilterPresent)
		{
			stringBuilder.append(" AND FLTLEG.STD <= ? ");
			setParameter(++index, toDate);		
		
		}
		//modified as part of BUG IASCB-59964
		if(flightOperationalstatus!=null && flightOperationalstatus.trim().length()>0) {
			stringBuilder.append(" AND COALESCE(EXPCLSFLG,'N') IN ( ");
			String[] mailOperSta = flightOperationalstatus.split(",");
			int mailOperStsIndex=0;
			for(String flightStat:mailOperSta){
				stringBuilder.append("?");
				if(++mailOperStsIndex < mailOperSta.length){
					stringBuilder.append(",");
				}
				this.setParameter(++index, flightStat.trim());
			}
			stringBuilder.append(")");
				
		}
		
		if(flightStatus!=null && flightStatus.trim().length()!=0){
			stringBuilder.append(" AND FLTMST.FLTSTA IN( ");
			String[] flightStatuses = flightStatus.split(",");
			int flightStsIndex=0;
			for(String flightStat:flightStatuses){
				stringBuilder.append("?");
				if(++flightStsIndex < flightStatuses.length){
					stringBuilder.append(",");
				}
				this.setParameter(++index, flightStat.trim());
			}
			stringBuilder.append(")");
		}
		//added by A-7815 as part of IASCB-36551
		if(operatingReference!=null && operatingReference.trim().length()!=0){
			stringBuilder.append(" AND coalesce(FLTREF.REFCARCOD,'"+logonAttributes.getOwnAirlineCode()+"') IN ( ");
			String[] operatingReferences = operatingReference.split(",");
			int flightRefIndex=0;
			for(String fltRef:operatingReferences){
				stringBuilder.append("?");
				if(++flightRefIndex < operatingReferences.length){
					stringBuilder.append(",");
				}
				this.setParameter(++index, fltRef.trim());
			}
			stringBuilder.append(")");
		}
		//added by as part of IASCB-56622
		if(!includeAllTrucks) {
			stringBuilder.append(" AND CASE\r\n" + 
					"        WHEN FLTMST.FLTTYP IS NULL OR FLTMST.FLTTYP <> 'T' OR MALTRUKTYP.PARVAL IS NULL OR MALTRUKTYP.PARVAL = 'N'  THEN 1\r\n" + 
					"        WHEN PKG_FRMWRK.FUN_STRING_CHECK(MALTRUKTYP.PARVAL, FLTLEG.ACRTYP, ',') > 0 THEN 1\r\n" + 
					"     ELSE 0\r\n" + 
					"    END = 1 ");
		}
		stringBuilder.append(")");
		

					stringBuilder.append(" )INNERMST group by CMPCOD,DEPTIM,ARPCOD,FLTCARIDR,FLTNUM,FLTSEQNUM, FLTDAT, LEGSERNUM, FLTROU,FLTORG, FLTDST,FLTTYP,FLTSTA,ACRTYP,LEGORG, connam,EXPCLSFLG,DEPGTE,ARVGTE,FLTDATPREFIX ,STD,STA");
		if(mailFlightOnly) {
			stringBuilder.append("  HAVING COUNT(MALSEQNUM) > 0 ");
		}
				 stringBuilder.append(") MST");

			        stringBuilder.append(" GROUP BY  CMPCOD,DEPTIM,ARPCOD, FLTCARIDR,FLTNUM, FLTSEQNUM,FLTDAT,LEGSERNUM,FLTROU,FLTORG,FLTDST,FLTTYP,FLTSTA, ACRTYP,LEGORG,EXPCLSFLG,DEPGTE,ARVGTE,FLTDATPREFIX,STD,STA ");
		log.log(Log.FINE, "builder-------------->>>>>>>>>>", stringBuilder.toString());
		log.exiting("OutboundFlightFilterQuery", "getNativeQuery");
		return stringBuilder.toString();
		
	}
	
	
}
