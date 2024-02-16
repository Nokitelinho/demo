
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.GPABillingDetailsMultiMapper;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


public class GPABillingEntriesFilterQuery extends PageableNativeQuery<DocumentBillingDetailsVO> {
	private Log log = LogFactory.getLogger("MRA_GPABilling");

    private String baseQuery;
    private GPABillingEntriesFilterVO gpaBillingEntriesFilterVO;

    public GPABillingEntriesFilterQuery(int defaultPageSize,int totalRecorCount, GPABillingDetailsMultiMapper detailsMultiMapper, String baseQuery, GPABillingEntriesFilterVO gpaBillingEntriesFilterVO) throws SystemException {
        super(defaultPageSize,totalRecorCount,detailsMultiMapper);
    	log.entering("GPABillingDetailsFilterQuery","constructor");
		this.gpaBillingEntriesFilterVO = gpaBillingEntriesFilterVO;
		this.baseQuery = baseQuery;
		log.exiting("GPABillingDetailsFilterQuery","constructor");

    }
/**
 * 
 *	Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery#getNativeQuery()
 *	Added by 			: A-8061 on 26-Sep-2019
 * 	Used for 	:
 *	Parameters	:	@return
 */
    public String getNativeQuery() {
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
    	
    	this.setParameter( ++index, companyCode );
    	

    	/*Commented as part of IASCB-65811
    	if ( gpaBillingEntriesFilterVO.getFromDate() != null && gpaBillingEntriesFilterVO.getToDate() != null  ) {
    		this.setParameter( ++index, gpaBillingEntriesFilterVO.getFromDate().toSqlDate().toString().replace("-", "") );
    		
    		this.setParameter( ++index, gpaBillingEntriesFilterVO.getToDate().toSqlDate().toString().replace("-", "") );
		
		}*/
    	  for(int i=1;i<=2;i++){
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
					csgQueryPart.append("('");
					csgQueryPart.append(gpaBillingEntriesFilterVO.getDsnNumber());
					csgQueryPart.append("')");
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
			 
			 
			    if(GPABillingEntriesFilterVO.FLAG_NO.equals(gpaBillingEntriesFilterVO.getMcaIndicator())){
			    	if(gpaBillingEntriesFilterVO.getMcaNumber()!=null&& 
			    			gpaBillingEntriesFilterVO.getMcaNumber().trim().length()>0){
			    		csgQueryPart.append(" AND COALESCE(INRCCADTLMCAREFNUM ,COALESCE(C66DTL.MCAREFNUM,'X')) IN ");
			    		csgQueryPart.append("(");
			    		csgQueryPart.append(gpaBillingEntriesFilterVO.getMcaNumber());
			    		csgQueryPart.append(")");   	    		
			    	}
			    }else if(GPABillingEntriesFilterVO.FLAG_YES.equals(gpaBillingEntriesFilterVO.getMcaIndicator())){
			    	if(gpaBillingEntriesFilterVO.getMcaNumber()!=null&& 
			    			gpaBillingEntriesFilterVO.getMcaNumber().trim().length()>0){
			    		
			    		csgQueryPart.append("AND (INRCCADTLMCAREFNUM is null OR INRCCADTLMCAREFNUM IN ");
			    		csgQueryPart.append("(");
			    		csgQueryPart.append(gpaBillingEntriesFilterVO.getMcaNumber());
			    		csgQueryPart.append("))"); 
			    		
			    		csgQueryPart.append("AND (C66DTL.MCAREFNUM is null OR C66DTL.MCAREFNUM IN ");
			    		csgQueryPart.append("(");
			    		csgQueryPart.append(gpaBillingEntriesFilterVO.getMcaNumber());
			    		csgQueryPart.append("))"); 
			    		
			    	}else{
			    		csgQueryPart.append("AND INRCCADTLMCAREFNUM IS NULL AND C66DTL.MCAREFNUM IS NULL");
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
			if (gpaBillingEntriesFilterVO.getPaBuilt()!= null &&
					gpaBillingEntriesFilterVO.getPaBuilt().trim().length() >0){
				sbul.append("AND MST.POAFLG = ");
				sbul.append("'");
				sbul.append(gpaBillingEntriesFilterVO.getPaBuilt());
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
    }

}
