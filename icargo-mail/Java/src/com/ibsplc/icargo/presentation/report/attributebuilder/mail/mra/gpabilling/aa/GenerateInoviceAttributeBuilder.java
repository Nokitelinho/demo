/*
 * GPABillingInvoiceEnquiryReportAttributeBuilder.java Created on Mar 01, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.mail.mra.gpabilling.aa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceDetailsReportVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3108
 *
 */
public class GenerateInoviceAttributeBuilder extends AttributeBuilderAdapter{

	private Log log = LogFactory.getLogger("mailtracking_mra_gpabilling");

	
    /**
	 * Method to construct the report column names. The column names corresponds
	 * to the column names of the view used while laying out the report. The
	 * order of the column names should match the order in which the database
	 * fields are laid out in the report
	 * 
	 * @param parameters
	 * @param extraInfo
	 * @return Vector<String> the column names
	 */
	@Override
	public Vector<String> getReportColumns() {

		Vector<String> reportColumns = new Vector<String>();
		reportColumns.add("INVDAT");  
		reportColumns.add("BNKNAM1");  
		reportColumns.add("ARLADR");
		reportColumns.add("CORADR");
		reportColumns.add("SGNONE"); 
		reportColumns.add("DSGONE");
		reportColumns.add("SGNTWO");
		reportColumns.add("DSGTWO");
		reportColumns.add("CURCOD");
		reportColumns.add("BNKNAM");
		reportColumns.add("BNKBRC"); 
		reportColumns.add("ACCNUM"); 
		reportColumns.add("CTYNAM"); 
		reportColumns.add("CNTNAM"); 
		reportColumns.add("SWTCOD"); 
		reportColumns.add("IBNNUM"); 
		reportColumns.add("TOTAMTBLGCUR"); 
		reportColumns.add("BLDPRD");
		reportColumns.add("POANAM");
		reportColumns.add("POAADR");
		reportColumns.add("CITY");
		reportColumns.add("STATE");
		reportColumns.add("COUNTRY");
		reportColumns.add("INVNUM");
		reportColumns.add("RMK");
		log.log(Log.FINE, "reportColumns is ijj--------->", reportColumns);

		return reportColumns;
	}
	
	@Override
	public Vector <Vector<String>> getSubReportColumns(){
		Vector <Vector<String>>subReportColumns1 = new Vector<Vector<String>>();
	    Vector <String> columns1 = new Vector<String> ();

	    columns1.add("SECTOR");
	    columns1.add("CATCOD");
	    columns1.add("WGT");
	    columns1.add("RAT");
	    columns1.add("VAT");
	    columns1.add("AMT");
	    columns1.add("MALSUBCLS");
	    columns1.add("MONTHFLAG");
	    columns1.add("AMTLC");
	    columns1.add("AMTCP");
	    columns1.add("MAILRAT");
	    columns1.add("MAILAMT");
	    columns1.add("SURAMT");
	    columns1.add("GROSSAMT");
	    columns1.add("TAX");
	    columns1.add("AMT");
	    //Below columns are used for the subreport mapped to invoice, gpacode and airline
	    columns1.add("BILTO");
	    columns1.add("STLCUR");
	    columns1.add("BILFRM");
	    //columns are used for the subreport mapped to invoice, gpacode and airline ends
	    columns1.add("OVRRND");
	    columns1.add("UNITCOD");
	    
	    Vector <String> columns2 = new Vector<String> ();
	    columns2.add("BLDAMT1");
	    columns2.add("MALCTGCOD");
	    columns2.add("DSN");
	    columns2.add("RSN");
	    columns2.add("CCAREFNUM");
	    columns2.add("ORGCOD");
	    columns2.add("DSTCOD");
	    columns2.add("SECTOR");
	    columns2.add("FLTDAT");
	    columns2.add("FLTNUM");
	    columns2.add("TOTWGT");
	    columns2.add("APLRAT");
	    columns2.add("OVRRND");
	    columns2.add("BLDAMT");
	    columns2.add("BLDPRD");
	    columns2.add("SRVTAX");
	    columns2.add("NETAMT");
	    columns2.add("BLGCURCOD");
	    columns2.add("TOTWGTCP");
	    columns2.add("TOTWGTLC");
	    columns2.add("TOTWGTSV");
	    columns2.add("TOTWGTEMS");
	    columns2.add("HNI");
	    columns2.add("RI");   
	    columns2.add("SUMBLDAMT");  
	    columns2.add("UNITCOD");
	    columns2.add("MALSUBCLS"); 
	    columns2.add("WEIGHT"); 
	    columns2.add("CNTIDR");
	    subReportColumns1.add(columns1);
	    subReportColumns1.add(columns2);
		return subReportColumns1;
	}
	
	
	/**
	 * Method to construct the report data. Each row in the details section of
	 * the report corresponds to one element in the outer Vector. Each element
	 * in the inner Vector corresponds to a field in the report. The order in
	 * which the data is returned should match the order in which the fields are
	 * laid out in the report
	 * 
	 * @param data
	 * @param extraInfo
	 * @return Vector<Vector> the report data
	 */
	@Override
	public Vector<Vector<Vector>> getSubReportData(Collection data, Collection extraInfo)
	  {
	    Vector <Vector<Vector>>subReportData = new Vector<Vector<Vector>>();
	    ArrayList dataCollns = (ArrayList)data;
	    Collection<CN51DetailsVO> cn51listVOs = new ArrayList<CN51DetailsVO>();
	    cn51listVOs = (ArrayList)dataCollns.get(0);
	    Collection<CN66DetailsVO> cn66listVOs = new ArrayList<CN66DetailsVO>();
	    cn66listVOs= (ArrayList)dataCollns.get(1);
	   
	    Vector<Object> row1 = new Vector<Object>();
	    Vector<Vector> subReportDataOne = new Vector<Vector>();
	    Vector <Vector>subReportDataTwo = new Vector<Vector>();
	    for (CN51DetailsVO cn51listVO : cn51listVOs) {
	    	row1 = new Vector<Object>();
	      if (cn51listVO.getSector() != null) {
	    	  row1.add(String.valueOf(cn51listVO.getSector()));
	      }
	      else {
	    	  row1.add(ReportConstants.EMPTY_STRING);
	      }

	      if (cn51listVO.getMailCategoryCode() != null)
	    	  row1.add(cn51listVO.getMailCategoryCode());
	      else {
	    	  row1.add(ReportConstants.EMPTY_STRING);
	      }

	      if (cn51listVO.getTotalWeight() >0.0)
	    	  row1.add(cn51listVO.getTotalWeight());
	      else {
	    	  row1.add("0.0");
	      }

	      if (cn51listVO.getApplicableRate() > 0.0)
	    	  row1.add(cn51listVO.getApplicableRate());
	      else {
	    	  row1.add("0.0");
	      }
	      if (cn51listVO.getVatAmount().getAmount()>0.0)
	    	  row1.add(cn51listVO.getVatAmount().getAmount());
	      else {
	    	  row1.add("0.0");
	      }
	      if (cn51listVO.getTotalAmount() != null)
	    	  row1.add(cn51listVO.getTotalAmount().getAmount());
		      else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }
	      if (cn51listVO.getMailSubclass() != null)
	    	  row1.add(cn51listVO.getMailSubclass());
		      else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }
	      if (cn51listVO.getMonthFlag() != null)
	    	  row1.add(cn51listVO.getMonthFlag());
		      else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }
	      if (cn51listVO.getTotalAmountLC() > 0.0)
	    	  row1.add(cn51listVO.getTotalAmountLC());
		      else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }
	      if (cn51listVO.getTotalAmountCP() > 0.0)
	    	  row1.add(cn51listVO.getTotalAmountCP());
		      else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }
	      if (cn51listVO.getApplicableRate() > 0.0)
	    	  row1.add(cn51listVO.getApplicableRate());
	      else {
	    	  row1.add("0.0");
	      }
	      if (cn51listVO.getMailCharge()!=null)
	    	  row1.add(cn51listVO.getMailCharge());
	      else {
	    	  row1.add(ReportConstants.EMPTY_STRING);
	      }
	      if (cn51listVO.getSurCharge()!=null)
	    	  row1.add(cn51listVO.getSurCharge());
	      else {
	    	  row1.add(ReportConstants.EMPTY_STRING);
	      }
	      if (cn51listVO.getGrossAmount() > 0.0)
	    	  row1.add(cn51listVO.getGrossAmount());
		      else {
		    	  row1.add("0.0");
		      }
	      if (cn51listVO.getServiceTax()> 0.0)
	    	  row1.add(cn51listVO.getServiceTax());
	      else {
	    	  row1.add("0.0");
	      }
	     if (cn51listVO.getTotalAmount() != null)
	    	  row1.add(cn51listVO.getTotalAmount().getAmount());
		      else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }
	      if (cn51listVO.getInvoiceNumber() != null)
	    	  row1.add(cn51listVO.getInvoiceNumber());
		      else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }
	      if (cn51listVO.getGpaCode() != null)
	    	  row1.add(cn51listVO.getGpaCode());
		      else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }
	      if (cn51listVO.getAirlineCode() != null)
	    	  row1.add(cn51listVO.getAirlineCode());
		      else {
			    	  row1.add(cn51listVO.getCompanyCode());
		      }
	      row1.add(cn51listVO.getOverrideRounding());
	      if (cn51listVO.getUnitCode() != null)
	    	  row1.add(cn51listVO.getUnitCode());
		      else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }
	      subReportDataOne.add(row1);
	      this.log.log(5, new Object[] { " \n sUB rEPORT dAtA_1--", cn51listVO });
	    }
	    double sumBldAmt=0.0D;
	    for (CN66DetailsVO cn66listVO : cn66listVOs) {
	    	row1 = new Vector<Object>();
	    	 if (cn66listVO.getAmount()>  0.0D ) {
		    	  row1.add(String.valueOf(cn66listVO.getAmount()));
		      }
		      else {
		    	  row1.add("0.0");
		      }
		      if (cn66listVO.getMailCategoryCode() != null) {
		    	  row1.add(String.valueOf(cn66listVO.getMailCategoryCode()));
		      }
		      else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }
		      
		      if (cn66listVO.getDsn() != null) {
		    	  row1.add(String.valueOf(cn66listVO.getDsn()));
		      }
		      else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }
		      if (cn66listVO.getRsn() != null) {
		    	  row1.add(String.valueOf(cn66listVO.getRsn()));
		      }
		      else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }
		      if (cn66listVO.getCcaRefNo() != null) {
		    	  row1.add(String.valueOf(cn66listVO.getCcaRefNo()));
		      }
		      else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }
		      if (cn66listVO.getOrigin() != null) {
		    	  row1.add(String.valueOf(cn66listVO.getOrigin()));
		      }
		      else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }
		      if (cn66listVO.getDestination() != null) {
		    	  row1.add(String.valueOf(cn66listVO.getDestination()));
		      }
		      else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }
		      if (cn66listVO.getSector() != null) {
		    	  row1.add(String.valueOf(cn66listVO.getSector()));
		      }
		      else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }
		      if (cn66listVO.getFlightDate()!=null) {
		    	  LocalDate flttDate = new LocalDate(cn66listVO.getFlightDate(),false);
		    	  row1.add(String.valueOf(flttDate.toDisplayFormat("dd-MMM-yy")));
		      }
		      else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }
		      if (cn66listVO.getFlightNumber() != null) {
		    	  row1.add(String.valueOf(cn66listVO.getFlightNumber()));
		      }
		      else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }
		      if (cn66listVO.getTotalWeight() > 0.0D) {
		    	  row1.add(String.valueOf(cn66listVO.getTotalWeight()));
		      }
		      else {
		    	  row1.add("0.0");
		      }
		      if (cn66listVO.getApplicableRate() > 0.0D) {
		    	  row1.add(String.valueOf(cn66listVO.getApplicableRate()));
		      }
		      else {
		    	  row1.add("0.0");
		      } 
		      row1.add(cn66listVO.getOverrideRounding());
		    	  
		      if (cn66listVO.getActualAmount().getAmount() > 0.0D) {
		    	  row1.add(String.valueOf(cn66listVO.getActualAmount().getAmount()));
		      }
		      else {
		    	  row1.add("0.0");
		      }
		      if (cn66listVO.getBillingPeriod()  !=null) {
		    	  row1.add(String.valueOf(cn66listVO.getBillingPeriod()));
		      }
		      else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }
		      if (cn66listVO.getServiceTax()  > 0.0D) {
		    	  row1.add(String.valueOf(cn66listVO.getServiceTax()));
		      
		      }
		      else {
		    	  row1.add("0.0");
		      }
		      if (cn66listVO.getNetAmount()  !=null) {
		    	  row1.add(String.valueOf(cn66listVO.getNetAmount().getAmount()));
		      }
		      else {
		    	  row1.add("0.0");
		      }
		      if (cn66listVO.getCurrencyCode()  !=null) {
		    	  row1.add(String.valueOf(cn66listVO.getCurrencyCode()));
		      }
		      else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }
		     
		      if (cn66listVO.getWeightCP() > 0.0D) {
		    	  row1.add(String.valueOf(cn66listVO.getWeightCP()));
		      }
		      else {
		    	  row1.add("0.0");
		      }
		      if (cn66listVO.getWeightLC() > 0.0D) {
		    	  row1.add(String.valueOf(cn66listVO.getWeightLC()));
		      }
		      else {
		    	  row1.add("0.0");
		      }
		      if (cn66listVO.getWeightSV() > 0.0D) {
		    	  row1.add(String.valueOf(cn66listVO.getWeightSV()));
		      }
		      else {
		    	  row1.add("0.0");
		      }
		      if (cn66listVO.getWeightEMS() > 0.0D) {
		    	  row1.add(String.valueOf(cn66listVO.getWeightEMS()));
		      }
		      else {
		    	  row1.add("0.0");
		      }
		      if(cn66listVO.getHsn() != null && cn66listVO.getHsn().trim().length() > 0){
		    	  row1.add(String.valueOf(cn66listVO.getHsn()));
		      }else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }
		      if(cn66listVO.getRegInd() != null && cn66listVO.getRegInd().trim().length() > 0){
		    	  row1.add(String.valueOf(cn66listVO.getRegInd()));
		      }else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }
		      if (cn66listVO.getAmount()>  0.0D ) {
		    	  sumBldAmt = sumBldAmt+cn66listVO.getAmount()+cn66listVO.getServiceTax();
		    	  if(sumBldAmt>0.0D){
		    		  row1.add(String.valueOf(sumBldAmt));  
		    	  }
		      }
		      if (cn66listVO.getUnitcode()!=null ) {
		    	  row1.add(String.valueOf(cn66listVO.getUnitcode()));
		      }
		      else {
		    	  row1.add("");
		      }
		      if (cn66listVO.getMailSubclass() !=null) {
		    	  row1.add(String.valueOf(cn66listVO.getMailSubclass()));
		      }
		      else {
		    	  row1.add("");
		      }
		      if (cn66listVO.getWeight()>  0.0D ) {
		    	  row1.add(String.valueOf(cn66listVO.getWeight()+" " + cn66listVO.getUnitcode()));
		      }
		      else {
		    	  row1.add("0.0");
		      }
		      if (cn66listVO.getContainerNumber()!=null ) {
		    	  row1.add(String.valueOf(cn66listVO.getContainerNumber()));
		      }
		      else {
		    	  row1.add("");
		      }
		      subReportDataTwo.add(row1);
	    }
	    subReportData.add(subReportDataOne);
	    subReportData.add(subReportDataTwo);
	    return subReportData;
	  }
	@Override
	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {

		Vector<Vector> cN51SummaryVOs = new Vector<Vector>();
		Vector<Object> row = null;

		Collection<InvoiceDetailsReportVO> listVOs = new ArrayList<InvoiceDetailsReportVO>();

		row = new Vector<Object>();
		listVOs = new ArrayList<InvoiceDetailsReportVO>(data);  
		for (InvoiceDetailsReportVO listVO : listVOs) {

			row = new Vector<Object>();

			if (listVO.getToDateString() != null) {
				row.add(listVO.getToDateString().toUpperCase());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getDuedays() >0) {
				
				row.add(String.valueOf(listVO.getDuedays()+" Days"));
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getAirlineAddress() != null) {
				row.add(listVO.getAirlineAddress());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getCorrespondenceAddress() != null) {
				row.add(listVO.getCorrespondenceAddress());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getSignatorOne() != null) {
				row.add(listVO.getSignatorOne());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getDesignatorOne() != null) {
				row.add(listVO.getDesignatorOne());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			
			if (listVO.getSignatorTwo() != null) {
				row.add(listVO.getSignatorTwo());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getDesignatorTwo() != null) {
				row.add(listVO.getDesignatorTwo());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			
			if (listVO.getCurrency()!= null) {
				row.add(listVO.getCurrency());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (listVO.getBankName()!= null) { 
				row.add(listVO.getBankName());        
			} else{
				row.add(ReportConstants.EMPTY_STRING);   
			}
			if (listVO.getBranch()!= null) { 
				row.add(listVO.getBranch());        
			} else{
				row.add(ReportConstants.EMPTY_STRING);   
			}
			if (listVO.getAccNo()!= null) { 
				row.add(listVO.getAccNo());        
			} else{
				row.add(ReportConstants.EMPTY_STRING);   
			}
			if (listVO.getBankCity()!= null) { 
				row.add(listVO.getBankCity());        
			} else{
				row.add(ReportConstants.EMPTY_STRING);   
			}
			if (listVO.getBankCountry()!= null) { 
				row.add(listVO.getBankCountry());        
			} else{
				row.add(ReportConstants.EMPTY_STRING);   
			}
			if (listVO.getSwiftCode()!= null) { 
				row.add(listVO.getSwiftCode());        
			} else{
				row.add(ReportConstants.EMPTY_STRING);   
			}
			if (listVO.getIbanNo()!= null) { 
				row.add(listVO.getIbanNo());        
			} else{
				row.add(ReportConstants.EMPTY_STRING);   
			}
			if (listVO.getTotalAmountinBillingCurrency()!= 0.0) { 
				row.add(listVO.getTotalAmountinBillingCurrency());        
			} else{
				row.add(ReportConstants.EMPTY_STRING);   
			}
			if (listVO.getBilledDateString()!= null) { 
				row.add(listVO.getBilledDateString());        
			} else{
				row.add(ReportConstants.EMPTY_STRING);   
			}
			if (listVO.getPaName()!= null) { 
				row.add(listVO.getPaName());        
			} else{
				row.add(ReportConstants.EMPTY_STRING);   
			}
			if (listVO.getAddress()!= null) { 
				row.add(listVO.getAddress());        
			} else{
				row.add(ReportConstants.EMPTY_STRING);   
			}
			if (listVO.getCity()!= null) { 
				row.add(listVO.getCity());        
			} else{
				row.add(ReportConstants.EMPTY_STRING);   
			}
			if (listVO.getState()!= null) { 
				row.add(listVO.getState());        
			} else{
				row.add(ReportConstants.EMPTY_STRING);   
			}
			if (listVO.getCountry()!= null) { 
				row.add(listVO.getCountry());        
			} else{
				row.add(ReportConstants.EMPTY_STRING);   
			}
			if (listVO.getInvoiceNumber()!= null) { 
				row.add(listVO.getInvoiceNumber());        
			} else{
				row.add(ReportConstants.EMPTY_STRING);   
			}
			if (listVO.getFreeText()!= null) { 
				row.add(listVO.getFreeText());        
			} else{
				row.add(ReportConstants.EMPTY_STRING);   
			}
			cN51SummaryVOs.add(row);

			log.log(Log.INFO, " \n xxxxxx", listVO);
		}

		log.log(Log.INFO, "cCADetailsVOs", cN51SummaryVOs);
		return cN51SummaryVOs;
	}

	/**
	 * Method to construct the report parameters. The report parameters
	 * corresponds to the parameter fields in the report. The order of the
	 * parameters should match the order in which the parameter fields are laid
	 * out in the report
	 * 
	 * @param parameters
	 *            the parameter data
	 * @param extraInfo
	 *            information required for formatting the parameters
	 * @return Vector the report parameters
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilder#getReportParameters(java.util.Collection,
	 *      java.util.Collection)
	 */
	public Vector<Object> getReportParameters(Collection parameters,
			Collection extraInfo) {

		Vector<Object> reportParameters = new Vector<Object>();
		Object dataParameters = ((ArrayList<Object>) parameters).get(0);
		
		CN51CN66FilterVO cN51SummaryFilterVO = (CN51CN66FilterVO) dataParameters;
		log.log(Log.INFO, "listccafilterVo", cN51SummaryFilterVO);
		
		if (cN51SummaryFilterVO.getCompanyCode() != null) {
			if("AA".equals(cN51SummaryFilterVO.getCompanyCode())){
			reportParameters.add("AMERICAN AIRLINES");
			}
		} else {
				reportParameters.add(ReportConstants.EMPTY_STRING);
			}
		log.log(Log.FINE, "reportParameters is ----------------->>>>>>",
				reportParameters);
		return reportParameters;
	}
	
	
					

}
