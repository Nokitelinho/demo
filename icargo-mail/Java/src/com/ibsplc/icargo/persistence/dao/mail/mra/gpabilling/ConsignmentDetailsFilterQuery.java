/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.ConsignmentDetailsFilterQuery.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Feb 8, 2019
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.ConsignmentDetailsFilterQuery.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Feb 8, 2019	:	Draft
 */
public class ConsignmentDetailsFilterQuery extends PageableNativeQuery<ConsignmentDocumentVO>{
	
	
    private String baseQuery;
    private GPABillingEntriesFilterVO gpaBillingEntriesFilterVO;  
    /**
     *	Constructor	: 	@param defaultPageSize
     *	Constructor	: 	@param recordCount
     *	Constructor	: 	@param consignemntMapper
     *	Constructor	: 	@param baseQuery
     *	Constructor	: 	@param gpaBillingEntriesFilterVO
     *	Constructor	: 	@throws SystemException
     *	Created by	:	A-4809
     *	Created on	:	Feb 8, 2019
     */
	public ConsignmentDetailsFilterQuery(int defaultPageSize, int recordCount, ConsignmentDetailsMapper consignemntMapper,String baseQuery, GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)
			throws SystemException {
		super(defaultPageSize,recordCount,consignemntMapper); 
    	log.entering("GPABillingDetailsFilterQuery","constructor");
		this.gpaBillingEntriesFilterVO = gpaBillingEntriesFilterVO;
		this.baseQuery = baseQuery;
		log.exiting("GPABillingDetailsFilterQuery","constructor");
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery#getNativeQuery()
	 *	Added by 			: A-4809 on Feb 8, 2019
	 * 	Used for 	:
	 *	Parameters	:	@return
	 */
    public String getNativeQuery() {
    	log.entering("GPABillingDetailsFilterQuery","getNativeQuery");

    	//Added  for IASCB-21493 Begin
    	log.entering("GPABillingDetailsFilterQuery","getNativeQuery");
    	String companyCode = gpaBillingEntriesFilterVO.getCompanyCode();
    	String status=gpaBillingEntriesFilterVO.getBillingStatus();
    	String country=gpaBillingEntriesFilterVO.getCountryCode();

    	int index = 0;
    	StringBuilder csgQueryPart = null;
    	if ( gpaBillingEntriesFilterVO.getFromDate() != null && gpaBillingEntriesFilterVO.getToDate() != null  ) {
    		this.setParameter( ++index,Integer.parseInt(gpaBillingEntriesFilterVO.getFromDate().toStringFormat("yyyyMMdd").substring(0, 8)));
    		this.setParameter( ++index,Integer.parseInt(gpaBillingEntriesFilterVO.getToDate().toStringFormat("yyyyMMdd").substring(0, 8)));
		}
    	
   
      for(int i=1;i<=3;i++){
      	this.setParameter( ++index, companyCode );
      	}

		StringBuilder sbul = new StringBuilder(baseQuery);

    	if ( status != null && status.trim().length() > 0  ) {
			sbul.append( "and  CASE WHEN DTL.BLGSTA IN ('PB', 'BD') AND INRCCADTLMCAREFNUM IS NULL THEN C66DTL.BLGSTA WHEN INRCCADTLMCAREFNUM IS NOT NULL THEN INRCCADTLBLGSTA ELSE DTL.BLGSTA END= ? ");
    		this.setParameter( ++index, status );
    	}

    	if ( country != null && country.trim().length() > 0  ) {
    		sbul.append( "AND DTL.CNTCOD = ? ");
    		this.setParameter( ++index, country );
    	}

		if(GPABillingEntriesFilterVO.FLAG_NO.equals(gpaBillingEntriesFilterVO.getFromCsgGroup())){
		    if ((gpaBillingEntriesFilterVO.getIsUSPSPerformed() != null) && 
		    	      (gpaBillingEntriesFilterVO.getIsUSPSPerformed().trim().length() > 0)){
		    	if(!"ALL".equals(gpaBillingEntriesFilterVO.getIsUSPSPerformed())){
		    		  StringBuilder sbldr = new StringBuilder();
		    		  sbldr.append(" AND MST.MALPERFLG = ");
		    		  sbldr.append("'");
		    		  sbldr.append(gpaBillingEntriesFilterVO.getIsUSPSPerformed());
		    	      sbldr.append("'");
		    	      sbul.append(sbldr.toString());
		    	}
		    	    }
			}
    	if (gpaBillingEntriesFilterVO.getConDocNumber() !=null &&
    			gpaBillingEntriesFilterVO.getConDocNumber().trim().length() >0){
				if(csgQueryPart==null){
					csgQueryPart = new StringBuilder();
					csgQueryPart.append("AND MST.CSGDOCNUM IN ");
					csgQueryPart.append("(");
					csgQueryPart.append(gpaBillingEntriesFilterVO.getConDocNumber());
					csgQueryPart.append(")");
				}
    	}
    	if (gpaBillingEntriesFilterVO.getDsnNumber() !=null &&
    			gpaBillingEntriesFilterVO.getDsnNumber().trim().length() >0){
				if(csgQueryPart==null){
					csgQueryPart = new StringBuilder();
					csgQueryPart.append("AND MST.DSN IN "); //fix for ICRD-282734
					csgQueryPart.append("(");
					csgQueryPart.append(String.valueOf(gpaBillingEntriesFilterVO.getDsnNumber()));
					csgQueryPart.append(")");
				}else{
					csgQueryPart.append("AND MST.DSN IN "); //fix for ICRD-282734
					csgQueryPart.append("('");
					csgQueryPart.append(gpaBillingEntriesFilterVO.getDsnNumber());
					csgQueryPart.append("')");
				}
			}
			if(gpaBillingEntriesFilterVO.getRateFilter()!=null 
					&& gpaBillingEntriesFilterVO.getRateFilter().trim().length()>0){
				if(csgQueryPart==null){
					csgQueryPart = new StringBuilder();
					csgQueryPart.append("AND DTL.APLRAT IN "); 
					csgQueryPart.append("(");
					csgQueryPart.append(gpaBillingEntriesFilterVO.getRateFilter());
					csgQueryPart.append(")");
				}else{
					csgQueryPart.append("AND DTL.APLRAT IN "); 
					csgQueryPart.append("(");
					csgQueryPart.append(gpaBillingEntriesFilterVO.getRateFilter());
					csgQueryPart.append(")");			
				}
			}
			if(GPABillingEntriesFilterVO.FLAG_YES.equals(gpaBillingEntriesFilterVO.getFromCsgGroup())){
				if(gpaBillingEntriesFilterVO.getGpaCode()!=null &&
						gpaBillingEntriesFilterVO.getGpaCode().trim().length()>0){
				if(csgQueryPart==null){
					csgQueryPart = new StringBuilder();
					csgQueryPart.append("and  CASE WHEN DTL.BLGSTA IN ('PB', 'BD') AND INRCCADTLMCAREFNUM IS NULL THEN C66DTL.GPACOD  WHEN INRCCADTLMCAREFNUM IS NOT NULL THEN INRCCADTLREVGPACOD ELSE DTL.UPDBILTOOPOA END  IN "); 
					csgQueryPart.append("(");
					csgQueryPart.append(gpaBillingEntriesFilterVO.getGpaCode());
					csgQueryPart.append(")");
				}else{
					csgQueryPart.append("and  CASE WHEN DTL.BLGSTA IN ('PB', 'BD') AND INRCCADTLMCAREFNUM IS NULL THEN C66DTL.GPACOD  WHEN INRCCADTLMCAREFNUM IS NOT NULL THEN INRCCADTLREVGPACOD ELSE DTL.UPDBILTOOPOA END  IN "); 
					csgQueryPart.append("(");
					csgQueryPart.append(gpaBillingEntriesFilterVO.getGpaCode());
					csgQueryPart.append(")");			
				}			
    	}
    	if(gpaBillingEntriesFilterVO.getOriginOfficeOfExchange()!=null &&
    			gpaBillingEntriesFilterVO.getOriginOfficeOfExchange().trim().length() >0){
				if(csgQueryPart==null){
						csgQueryPart = new StringBuilder();
						csgQueryPart.append("AND MST.ORGEXGOFC IN "); 
						csgQueryPart.append("(");
						csgQueryPart.append(gpaBillingEntriesFilterVO.getOriginOfficeOfExchange());
						csgQueryPart.append(")");
					}else{
						csgQueryPart.append("AND MST.ORGEXGOFC IN "); 
						csgQueryPart.append("(");
						csgQueryPart.append(gpaBillingEntriesFilterVO.getOriginOfficeOfExchange());
						csgQueryPart.append(")");			
					}
    	}
    	if(gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange()!=null &&
    			gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange().trim().length() >0){
					if(csgQueryPart==null){
						csgQueryPart = new StringBuilder();
						csgQueryPart.append("AND MST.DSTEXGOFC IN "); 
						csgQueryPart.append("(");
						csgQueryPart.append(gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange());
						csgQueryPart.append(")");
					}else{
						csgQueryPart.append("AND MST.DSTEXGOFC IN "); 
						csgQueryPart.append("(");
						csgQueryPart.append(gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange());
						csgQueryPart.append(")");			
					}			
				}
				if(gpaBillingEntriesFilterVO.getMailCategoryCode()!=null &&
						gpaBillingEntriesFilterVO.getMailCategoryCode().trim().length() >0){
					if(csgQueryPart==null){
						csgQueryPart = new StringBuilder();
						csgQueryPart.append("AND MST.MALCTGCOD IN "); 
						csgQueryPart.append("(");
						csgQueryPart.append(gpaBillingEntriesFilterVO.getMailCategoryCode());
						csgQueryPart.append(")");
					}else{
						csgQueryPart.append("AND MST.MALCTGCOD IN "); 
						csgQueryPart.append("(");
						csgQueryPart.append(gpaBillingEntriesFilterVO.getMailCategoryCode());
						csgQueryPart.append(")");
					}				
				}
				if(gpaBillingEntriesFilterVO.getMailSubclass()!=null &&
						gpaBillingEntriesFilterVO.getMailSubclass().trim().length() >0){
					if(csgQueryPart==null){
						csgQueryPart = new StringBuilder();
						csgQueryPart.append("AND MST.MALSUBCLS IN "); 
						csgQueryPart.append("(");
						csgQueryPart.append(gpaBillingEntriesFilterVO.getMailSubclass());
						csgQueryPart.append(")");
					}else{
						csgQueryPart.append("AND MST.MALSUBCLS IN "); 
						csgQueryPart.append("(");
						csgQueryPart.append(gpaBillingEntriesFilterVO.getMailSubclass());
						csgQueryPart.append(")");			
					}				
				}
				if(gpaBillingEntriesFilterVO.getIsUSPSPerformed()!=null&&
						gpaBillingEntriesFilterVO.getIsUSPSPerformed().trim().length()>0){
					if(csgQueryPart==null){
						csgQueryPart = new StringBuilder();
						csgQueryPart.append("AND MST.MALPERFLG IN "); 
						csgQueryPart.append("(");
						csgQueryPart.append(gpaBillingEntriesFilterVO.getIsUSPSPerformed());
						csgQueryPart.append(")");
					}else{
						csgQueryPart.append("AND MST.MALPERFLG IN "); 
						csgQueryPart.append("(");
						csgQueryPart.append(gpaBillingEntriesFilterVO.getIsUSPSPerformed());
						csgQueryPart.append(")");			
					}				
				}
			 if(gpaBillingEntriesFilterVO.getCurrencyCode()!=null &&
					 gpaBillingEntriesFilterVO.getCurrencyCode().trim().length()>0){
					if(csgQueryPart==null){
						csgQueryPart = new StringBuilder();
						csgQueryPart.append("AND DTL.CTRCURCOD IN "); 
						csgQueryPart.append("(");
						csgQueryPart.append(gpaBillingEntriesFilterVO.getCurrencyCode());
						csgQueryPart.append(")");
					}else{
						csgQueryPart.append("AND DTL.CTRCURCOD IN "); 
						csgQueryPart.append("(");
						csgQueryPart.append(gpaBillingEntriesFilterVO.getCurrencyCode());
						csgQueryPart.append(")");			
					}				
				}
			}else{
				if(gpaBillingEntriesFilterVO.getGpaCode()!=null && 
						gpaBillingEntriesFilterVO.getGpaCode().trim().length()>0){
					if(csgQueryPart==null){
					csgQueryPart = new StringBuilder();
					csgQueryPart.append("and  CASE WHEN DTL.BLGSTA IN ('PB', 'BD') AND INRCCADTLMCAREFNUM IS NULL THEN C66DTL.GPACOD  WHEN INRCCADTLMCAREFNUM IS NOT NULL THEN INRCCADTLREVGPACOD ELSE DTL.UPDBILTOOPOA END   = ");
					csgQueryPart.append("'");
					csgQueryPart.append(gpaBillingEntriesFilterVO.getGpaCode());
					csgQueryPart.append("'");			
					}else{
					csgQueryPart.append("and  CASE WHEN DTL.BLGSTA IN ('PB', 'BD') AND INRCCADTLMCAREFNUM IS NULL THEN C66DTL.GPACOD  WHEN INRCCADTLMCAREFNUM IS NOT NULL THEN INRCCADTLREVGPACOD ELSE DTL.UPDBILTOOPOA END   = ");  
					csgQueryPart.append("'");
					csgQueryPart.append(gpaBillingEntriesFilterVO.getGpaCode());
					csgQueryPart.append("'");
					}			
				}
				if(gpaBillingEntriesFilterVO.getOriginOfficeOfExchange()!=null &&
						gpaBillingEntriesFilterVO.getOriginOfficeOfExchange().trim().length() >0){
					if(csgQueryPart==null){
					csgQueryPart = new StringBuilder();
					csgQueryPart.append("AND MST.ORGEXGOFC  = ");
					csgQueryPart.append("'");
					csgQueryPart.append(gpaBillingEntriesFilterVO.getOriginOfficeOfExchange());
					csgQueryPart.append("'");			
					}else{
					csgQueryPart.append("AND MST.ORGEXGOFC  = ");
					csgQueryPart.append("'");
					csgQueryPart.append(gpaBillingEntriesFilterVO.getOriginOfficeOfExchange());
					csgQueryPart.append("'");
					}
				}
				if(gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange()!=null &&
						gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange().trim().length() >0){
					if(csgQueryPart==null){
						csgQueryPart = new StringBuilder();
						csgQueryPart.append("AND MST.DSTEXGOFC  = ");
						csgQueryPart.append("'");
						csgQueryPart.append(gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange());
						csgQueryPart.append("'");				
					}else{
						csgQueryPart.append("AND MST.DSTEXGOFC  = ");
						csgQueryPart.append("'");
						csgQueryPart.append(gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange());
						csgQueryPart.append("'");
						}
				}
				if(gpaBillingEntriesFilterVO.getMailCategoryCode()!=null &&
						gpaBillingEntriesFilterVO.getMailCategoryCode().trim().length() >0){
					if(!"ALL".equals(gpaBillingEntriesFilterVO.getMailCategoryCode())){
					if(csgQueryPart==null){
						csgQueryPart = new StringBuilder();
						csgQueryPart.append("AND MST.MALCTGCOD   = ");
						csgQueryPart.append("'");
						csgQueryPart.append(gpaBillingEntriesFilterVO.getMailCategoryCode());
						csgQueryPart.append("'");				
					}else{
						csgQueryPart.append("AND MST.MALCTGCOD   = ");
						csgQueryPart.append("'");
						csgQueryPart.append(gpaBillingEntriesFilterVO.getMailCategoryCode());
						csgQueryPart.append("'");
						}
						}
				}
				if(gpaBillingEntriesFilterVO.getMailSubclass()!=null &&
						gpaBillingEntriesFilterVO.getMailSubclass().trim().length() >0){
					if(csgQueryPart==null){
						csgQueryPart = new StringBuilder();
						csgQueryPart.append("AND MST.MALSUBCLS    = ");
						csgQueryPart.append("'");
						csgQueryPart.append(gpaBillingEntriesFilterVO.getMailSubclass());
						csgQueryPart.append("'");				
					}else{
						csgQueryPart.append("AND MST.MALSUBCLS    = "); 
						csgQueryPart.append("'");
						csgQueryPart.append(gpaBillingEntriesFilterVO.getMailSubclass());
						csgQueryPart.append("'");
						}
				}		
			}
			
			if(csgQueryPart!=null){
				sbul.append(csgQueryPart.toString()); 
			}
			
			
    	if(gpaBillingEntriesFilterVO.getMailbagId()!=null &&
    			gpaBillingEntriesFilterVO.getMailbagId().trim().length() >0){
				sbul.append("AND MST.MALIDR = ");    
				sbul.append("'");
				String mailbagId=gpaBillingEntriesFilterVO.getMailbagId();
				if(mailbagId.contains("'")){
					mailbagId=mailbagId.replace("'", "''");
				}
				sbul.append(mailbagId);
				sbul.append("'");
    	}
    	if(gpaBillingEntriesFilterVO.getRsn()!=null &&
    			gpaBillingEntriesFilterVO.getRsn().trim().length() >0){
				sbul.append("AND MST.RSN = ");
				sbul.append("'");
				sbul.append(gpaBillingEntriesFilterVO.getRsn());
				sbul.append("'");
    	}
    	if(gpaBillingEntriesFilterVO.getHni()!=null &&
    			gpaBillingEntriesFilterVO.getHni().trim().length() >0){
				sbul.append("AND MST.HSN = ");
				sbul.append("'");
				sbul.append(gpaBillingEntriesFilterVO.getHni());
				sbul.append("'");
    	}
    	if(gpaBillingEntriesFilterVO.getRegInd()!=null &&
    			gpaBillingEntriesFilterVO.getRegInd().trim().length() >0){
				sbul.append("AND MST.REGIND = ");
				sbul.append("'");
				sbul.append(gpaBillingEntriesFilterVO.getRegInd());
				sbul.append("'");
			}
			if (gpaBillingEntriesFilterVO.getYear()!= null &&
					gpaBillingEntriesFilterVO.getYear().trim().length() >0){
				sbul.append("AND MST.YER = ");
				sbul.append("'");
				sbul.append(gpaBillingEntriesFilterVO.getYear());
				sbul.append("'");
			}
			if (gpaBillingEntriesFilterVO.getPaBuilt()!= null &&
					gpaBillingEntriesFilterVO.getPaBuilt().trim().length() >0){
				sbul.append("AND MST.POAFLG= ");
				sbul.append("'");
				sbul.append(gpaBillingEntriesFilterVO.getPaBuilt());
				sbul.append("'");
			}
			
					if (gpaBillingEntriesFilterVO.getContractRate() != null
							&& gpaBillingEntriesFilterVO.getContractRate().trim().length() > 0
							&& gpaBillingEntriesFilterVO.getUPURate() == null) {
						sbul.append("and  CASE WHEN DTL.BLGSTA IN ('PB', 'BD')  THEN C66DTL.RATTYP  ELSE DTL.RATTYP END = ");
						sbul.append("'");
						sbul.append(gpaBillingEntriesFilterVO.getContractRate());
						sbul.append("'");
					}
					if (gpaBillingEntriesFilterVO.getUPURate() != null
							&& gpaBillingEntriesFilterVO.getUPURate().trim().length() > 0
							&& gpaBillingEntriesFilterVO.getContractRate() == null) {
						sbul.append("and  CASE WHEN DTL.BLGSTA IN ('PB', 'BD')  THEN C66DTL.RATTYP  ELSE DTL.RATTYP END = ");
						sbul.append("'");
						sbul.append(gpaBillingEntriesFilterVO.getUPURate());
						sbul.append("'");
					}
					else if ((gpaBillingEntriesFilterVO.getUPURate() != null
							&& gpaBillingEntriesFilterVO.getUPURate().trim().length() > 0
							&& gpaBillingEntriesFilterVO.getContractRate() != null && gpaBillingEntriesFilterVO
							.getContractRate().trim().length() > 0)) {
						sbul.append("and  CASE WHEN DTL.BLGSTA IN ('PB', 'BD')  THEN C66DTL.RATTYP  ELSE DTL.RATTYP END IN ");
						sbul.append("(").append("'");
						sbul.append(gpaBillingEntriesFilterVO.getUPURate());
						sbul.append("'").append(",").append("'");
						sbul.append(gpaBillingEntriesFilterVO.getContractRate());
						sbul.append("'").append(")");
					}
					

    	if(gpaBillingEntriesFilterVO.getOrigin()!=null && gpaBillingEntriesFilterVO.getOrigin().trim().length()>0){
					sbul.append("AND MST.ORGCTYCOD = ");
					sbul.append("'");
					sbul.append(gpaBillingEntriesFilterVO.getOrigin());
					sbul.append("'");
    	}
    	if(gpaBillingEntriesFilterVO.getDestination()!=null && gpaBillingEntriesFilterVO.getDestination().trim().length()>0){
					sbul.append("AND MST.DSTCTYCOD = ");
					sbul.append("'");
					sbul.append(gpaBillingEntriesFilterVO.getDestination());
					sbul.append("'");	
    	}
    	return sbul.toString();
    	
//Added for IASCB-21493 End
    
 //Commented for IASCB-21493 Begin		
//    	String companyCode = gpaBillingEntriesFilterVO.getCompanyCode();
//    	String status=gpaBillingEntriesFilterVO.getBillingStatus();
//    	String gpaCode=gpaBillingEntriesFilterVO.getGpaCode();
//    	String country=gpaBillingEntriesFilterVO.getCountryCode();
//    	String category=gpaBillingEntriesFilterVO.getMailCategoryCode();
//    	String contractRate=gpaBillingEntriesFilterVO.getContractRate();
//    	String upuRate=gpaBillingEntriesFilterVO.getUPURate();
//    	int index = 0;
//    	this.setParameter( ++index, companyCode );
//    	StringBuilder sbul = new StringBuilder(baseQuery);
//    	if ( gpaBillingEntriesFilterVO.getFromDate() != null && gpaBillingEntriesFilterVO.getToDate() != null  ) {
//    		sbul.append( "AND TO_NUMBER(TO_CHAR(MST.RCVDAT,'YYYYMMDD')) BETWEEN ");
//    		sbul.append(gpaBillingEntriesFilterVO.getFromDate().toSqlDate().toString().replace("-", ""));
//    		sbul.append(" AND ");
//    		sbul.append(gpaBillingEntriesFilterVO.getToDate().toSqlDate().toString().replace("-", ""));
//    		sbul.append(" ");
//    	}
//    	if ( status != null && status.trim().length() > 0  ) {
//    		sbul.append( "AND DTL.BLGSTA = ? ");
//    		this.setParameter( ++index, status );
//    	}
//    	if ( gpaCode != null && gpaCode.trim().length() > 0  ) {
//    		sbul.append( "AND DTL.UPDBILTOOPOA = ? ");
//    		this.setParameter( ++index, gpaCode );
//    	}
//    	if ( country != null && country.trim().length() > 0  ) {
//    		sbul.append( "AND DTL.CNTCOD = ? ");
//    		this.setParameter( ++index, country );
//    	}
//    	if (gpaBillingEntriesFilterVO.getConDocNumber() !=null &&
//    			gpaBillingEntriesFilterVO.getConDocNumber().trim().length() >0){
//    		sbul.append("AND MST.CSGDOCNUM IN ( ?");
//    		String csg = gpaBillingEntriesFilterVO.getConDocNumber().replace("'", "");  
//    		this.setParameter(++index, csg);     
//    		sbul.append(")");  
//    	}
//    	if (gpaBillingEntriesFilterVO.getDsnNumber() !=null &&
//    			gpaBillingEntriesFilterVO.getDsnNumber().trim().length() >0){
//    		sbul.append("AND MST.DSN IN ( ?");
//    		String dsn = gpaBillingEntriesFilterVO.getDsnNumber().replace("'", ""); 
//    		this.setParameter(++index, dsn);
//    		sbul.append(")"); 
//    	}
//    	if(gpaBillingEntriesFilterVO.getOriginOfficeOfExchange()!=null &&
//    			gpaBillingEntriesFilterVO.getOriginOfficeOfExchange().trim().length() >0){
//    		sbul.append("AND MST.ORGEXGOFC  = ?");
//    		this.setParameter(++index, gpaBillingEntriesFilterVO.getOriginOfficeOfExchange());
//    	}
//    	if(gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange()!=null &&
//    			gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange().trim().length() >0){
//    		sbul.append("AND MST.DSTEXGOFC  = ?");
//    		this.setParameter(++index, gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange());
//    	}
//    	if(gpaBillingEntriesFilterVO.getMailbagId()!=null &&
//    			gpaBillingEntriesFilterVO.getMailbagId().trim().length() >0){
//    		sbul.append("AND MST.MALIDR  = ?");
//    		this.setParameter(++index, gpaBillingEntriesFilterVO.getMailbagId());
//    	}
//    	if(category!=null &&
//    			category.trim().length() >0){
//    	if(!"ALL".equals(category)){
//    		sbul.append("AND MST.MALCTGCOD   = ?");
//    		this.setParameter(++index, gpaBillingEntriesFilterVO.getMailCategoryCode());
//    	}
//    	}
//    	if(gpaBillingEntriesFilterVO.getMailSubclass()!=null &&
//    			gpaBillingEntriesFilterVO.getMailSubclass().trim().length() >0){
//    		sbul.append("AND MST.MALSUBCLS    = ?");
//    		this.setParameter(++index, gpaBillingEntriesFilterVO.getMailSubclass());
//    	}
//    	if(gpaBillingEntriesFilterVO.getYear()!=null &&
//    			gpaBillingEntriesFilterVO.getYear().trim().length() >0){
//    		sbul.append("AND MST.YER = ?");
//    		this.setParameter(++index, gpaBillingEntriesFilterVO.getYear());
//    	}
//    	if(gpaBillingEntriesFilterVO.getRsn()!=null &&
//    			gpaBillingEntriesFilterVO.getRsn().trim().length() >0){
//    		sbul.append("AND MST.RSN = ?");
//    		this.setParameter(++index, gpaBillingEntriesFilterVO.getRsn());
//    	}
//    	if(gpaBillingEntriesFilterVO.getHni()!=null &&
//    			gpaBillingEntriesFilterVO.getHni().trim().length() >0){
//    		sbul.append("AND MST.HSN  = ?");
//    		this.setParameter(++index, gpaBillingEntriesFilterVO.getHni());
//    	}
//    	if(gpaBillingEntriesFilterVO.getRegInd()!=null &&
//    			gpaBillingEntriesFilterVO.getRegInd().trim().length() >0){
//    		sbul.append("AND MST.REGIND = ?");
//    		this.setParameter(++index, gpaBillingEntriesFilterVO.getRegInd());
//    	}
//    	if (contractRate != null
//    			&& contractRate.trim().length() > 0
//    			&& upuRate == null) {
//    		sbul.append("AND DTL.RATTYP = ?");
//    		this.setParameter(++index,
//    				gpaBillingEntriesFilterVO.getContractRate());
//    	}
//    	if (upuRate != null
//    			&& upuRate.trim().length() > 0
//    			&& contractRate == null) {
//    		sbul.append("AND DTL.RATTYP = ?");
//    		this.setParameter(++index, gpaBillingEntriesFilterVO.getUPURate());
//    	}
//    	if ((upuRate != null
//    			&& upuRate.trim().length() > 0
//    			&& contractRate != null && contractRate.trim().length() > 0)) {
//    		sbul.append("AND DTL.RATTYP IN (?,?)");
//    		this.setParameter(++index, gpaBillingEntriesFilterVO.getUPURate());
//    		this.setParameter(++index,
//    				gpaBillingEntriesFilterVO.getContractRate());
//    	}
//    	if(gpaBillingEntriesFilterVO.getOrigin()!=null && gpaBillingEntriesFilterVO.getOrigin().trim().length()>0){
//    		sbul.append("AND MST.ORGCTYCOD = ?");
//    		index++;
//    		setParameter(index, this.gpaBillingEntriesFilterVO.getOrigin());
//    	}
//    	if(gpaBillingEntriesFilterVO.getDestination()!=null && gpaBillingEntriesFilterVO.getDestination().trim().length()>0){
//    		sbul.append("AND MST.DSTCTYCOD = ?");
//    		index++;
//    		setParameter(index, this.gpaBillingEntriesFilterVO.getDestination());	
//    	}
//    	return sbul.toString();
//Commented for IASCB-21493 End 
    	
    }
}
