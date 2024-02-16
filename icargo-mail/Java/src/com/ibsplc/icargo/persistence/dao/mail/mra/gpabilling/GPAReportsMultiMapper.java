package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceDetailsReportVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
/**
 * 
 * @author A-5219
 *
 */
public class GPAReportsMultiMapper
  implements MultiMapper<InvoiceDetailsReportVO>{
	/**
	 * 
	 */
  public List<InvoiceDetailsReportVO> map(ResultSet resultset)
    throws SQLException
  {
    InvoiceDetailsReportVO invoiceDetailsReportVO = new InvoiceDetailsReportVO();
    Map<String,InvoiceDetailsReportVO> invoiceDetailsReportVOsMap = new HashMap<String,InvoiceDetailsReportVO>();
    List<InvoiceDetailsReportVO> invoiceDetailsReportVOs = new ArrayList<InvoiceDetailsReportVO>();
    List<InvoiceDetailsReportVO> invoiceDetailsReportVOs1 = new ArrayList<InvoiceDetailsReportVO>();
    List<InvoiceDetailsReportVO> invoiceDetailsReportVOs2 = new ArrayList<InvoiceDetailsReportVO>();
    String companyCode = "";
    String invNum = "";
    String gpaCode = "";
    String key = "";
    while (resultset.next()) {
      companyCode = resultset.getString("CMPCOD");
      invNum = resultset.getString("INVNUM");
      gpaCode = resultset.getString("GPACOD");
      if(resultset.getString("CNTCOD")!=null)
    	  {
    	  key = companyCode + invNum + gpaCode+resultset.getString("CNTCOD");
    	  }
      else
    	  key = companyCode + invNum + gpaCode;
      if (!invoiceDetailsReportVOsMap.containsKey(key)) {
    	  invoiceDetailsReportVO=new InvoiceDetailsReportVO();
        invoiceDetailsReportVO.setBillingSiteCode(resultset.getString("BLGSITCOD"));
        invoiceDetailsReportVO.setBillingSite(resultset.getString("BLGSITNAM"));
        invoiceDetailsReportVO.setAirlineAddress(resultset.getString("ARLADR"));
        invoiceDetailsReportVO.setCorrespondenceAddress(resultset.getString("CORADR"));
        invoiceDetailsReportVO.setDesignatorOne(resultset.getString("DSGONE"));
        invoiceDetailsReportVO.setDesignatorTwo(resultset.getString("DSGTWO"));
        invoiceDetailsReportVO.setSignatorOne(resultset.getString("SGNONE"));
        invoiceDetailsReportVO.setSignatorTwo(resultset.getString("SGNTWO"));
        invoiceDetailsReportVO.setFreeText(resultset.getString("RMK"));
        invoiceDetailsReportVO.setClearComId(resultset.getString("PARVAL"));
        if(resultset.getDate("INVDAT")!=null)
        	{
        	invoiceDetailsReportVO.setToDateString(new LocalDate(LocalDate.NO_STATION,Location.NONE,resultset.getDate("INVDAT")).toDisplayDateOnlyFormat());
        	}
        invoiceDetailsReportVO.setCountryCode(resultset.getString("CNTCOD"));
        if(resultset.getString("CURCOD")!=null && ("USD".equals(resultset.getString("CURCOD")) ||  "EUR".equals(resultset.getString("CURCOD"))))
        	{
        	invoiceDetailsReportVO = setBankDetails(invoiceDetailsReportVO,resultset);
        	}
        if (resultset.getString("POANAM") != null) {
          invoiceDetailsReportVO.setPaName(resultset.getString("POANAM"));
        }

        if (resultset.getString("POAADR") != null) {
          invoiceDetailsReportVO.setAddress(resultset.getString("POAADR"));
        }

        if (resultset.getString("CITY") != null) {
          invoiceDetailsReportVO.setCity(resultset.getString("CITY"));
        }

        if (resultset.getString("STATE") != null) {
          invoiceDetailsReportVO.setState(resultset.getString("STATE"));
        }

        if (resultset.getString("COUNTRY") != null) {
          invoiceDetailsReportVO.setCountry(resultset.getString("COUNTRY"));
        }

       if (resultset.getString("FAX") != null) {
          invoiceDetailsReportVO.setFax(resultset.getString("FAX"));
        }

        if (resultset.getString("VATNUM") != null) {
          invoiceDetailsReportVO.setVatNumber(resultset.getString("VATNUM"));
        }

        if (resultset.getLong("DUEDAY") >0) {
            invoiceDetailsReportVO.setDuedays(resultset.getLong("DUEDAY"));
          }
        if (resultset.getString("SECTOR") != null) {
          invoiceDetailsReportVO.setSector(resultset.getString("SECTOR"));
        }
        if (resultset.getString("BLGCURCOD") != null) {
          invoiceDetailsReportVO.setBillingCurrencyCode(resultset.getString("BLGCURCOD"));
        }
        if (resultset.getString("INVNUM") != null) {
          invoiceDetailsReportVO.setInvoiceNumber(resultset.getString("INVNUM"));
        }

        if (resultset.getString("BLDPRD") != null)
        {
          invoiceDetailsReportVO.setBilledDateString(resultset.getString("BLDPRD"));
        }

        if (resultset.getString("MALCTGCOD") != null) {
          invoiceDetailsReportVO.setMailCategoryCode(resultset.getString("MALCTGCOD"));
        }
        if (resultset.getString("LSTUPDUSR") != null) {
            invoiceDetailsReportVO.setLastUpdatedUser(resultset.getString("LSTUPDUSR"));
        }

        invoiceDetailsReportVO.setTotalAmountinBillingCurrency(resultset.getDouble("TOTAMTBLGCUR"));
        invoiceDetailsReportVO.setTotalAmountinContractCurrency(resultset.getDouble("TOTAMTCRTCUR"));
        invoiceDetailsReportVO.setTotalAmountinsettlementCurrency(resultset.getDouble("TOTAMTSTLCUR"));
        invoiceDetailsReportVOs1.add(invoiceDetailsReportVO);
        invoiceDetailsReportVOsMap.put(key, invoiceDetailsReportVO);
      }
      else {
    	if(resultset.getString("CURCOD")!=null || ( "USD".equals(resultset.getString("CURCOD")) ||  "EUR".equals(resultset.getString("CURCOD")))){
    		invoiceDetailsReportVO =invoiceDetailsReportVOsMap.get(key);
    		invoiceDetailsReportVO = setBankDetails(invoiceDetailsReportVO,resultset);
      		invoiceDetailsReportVOs1.add(invoiceDetailsReportVO);
    	}
      }
    }
    if(invoiceDetailsReportVOsMap!=null && invoiceDetailsReportVOsMap.values()!=null && invoiceDetailsReportVOsMap.values().size()>0 )
    	{
    	invoiceDetailsReportVOs = new ArrayList<InvoiceDetailsReportVO>(invoiceDetailsReportVOsMap.values());
    	}
    else
    	{
    	invoiceDetailsReportVOs.add(invoiceDetailsReportVO);
    	}
    for(InvoiceDetailsReportVO reportVO:invoiceDetailsReportVOs1){
    	if(reportVO.getCountryCode()!=null && reportVO.getCountryCode().trim().length()>0){
    		invoiceDetailsReportVOs2.add(reportVO);
    	}
    }
    if(invoiceDetailsReportVOs2.isEmpty())
    	{
    	return invoiceDetailsReportVOs;
    	}
    else
    	{
    	return invoiceDetailsReportVOs2;
    	}
  }
  /**
   * 
   * @param invoiceDetailsReportVO
   * @param resultset
   * @return
   * @throws SQLException  
   */
  public InvoiceDetailsReportVO setBankDetails(InvoiceDetailsReportVO invoiceDetailsReportVO, ResultSet resultset) throws SQLException{
	  if("USD".equals(resultset.getString("CURCOD"))){
	        invoiceDetailsReportVO.setCurrency(resultset.getString("CURCOD"));
	        invoiceDetailsReportVO.setBankName(resultset.getString("BNKNAM"));
	        invoiceDetailsReportVO.setBranch(resultset.getString("BNKBRN"));
	        invoiceDetailsReportVO.setAccNo(resultset.getString("ACCNUM"));
	        invoiceDetailsReportVO.setBankCity(resultset.getString("CTYNAM"));
	        invoiceDetailsReportVO.setBankCountry(resultset.getString("CNTNAM"));
	        invoiceDetailsReportVO.setSwiftCode(resultset.getString("SWTCOD"));
	        invoiceDetailsReportVO.setIbanNo(resultset.getString("IBNNUM"));
	    }
  	  else if("EUR".equals(resultset.getString("CURCOD"))){
      	     invoiceDetailsReportVO.setCurrencyOne(resultset.getString("CURCOD"));
     	     invoiceDetailsReportVO.setBankNameOne(resultset.getString("BNKNAM"));
 		     invoiceDetailsReportVO.setBranchOne(resultset.getString("BNKBRN")); 
 		     invoiceDetailsReportVO.setAccNoOne(resultset.getString("ACCNUM"));
 		     invoiceDetailsReportVO.setBankCityOne(resultset.getString("CTYNAM"));
 		     invoiceDetailsReportVO.setBankCountryOne(resultset.getString("CNTNAM"));
 		     invoiceDetailsReportVO.setSwiftCodeOne(resultset.getString("SWTCOD"));
 		     invoiceDetailsReportVO.setIbanNoOne(resultset.getString("IBNNUM"));
  	}
	  
	  if(resultset.getString("CURCOD")!=null){
   	         invoiceDetailsReportVO.setBankCurrency(resultset.getString("CURCOD"));
  	         invoiceDetailsReportVO.setNameOfBank(resultset.getString("BNKNAM"));
		     invoiceDetailsReportVO.setBankBranch(resultset.getString("BNKBRN")); 
		     invoiceDetailsReportVO.setAccountNumber(resultset.getString("ACCNUM"));
		     invoiceDetailsReportVO.setCityOfBank(resultset.getString("CTYNAM"));
		     invoiceDetailsReportVO.setCountryOfBank(resultset.getString("CNTNAM"));
		     invoiceDetailsReportVO.setSwiftCodeOfBank(resultset.getString("SWTCOD"));
		     invoiceDetailsReportVO.setIbanNoOfBank(resultset.getString("IBNNUM"));
	}
	  
	  
	  
	  return invoiceDetailsReportVO;
  }
}
