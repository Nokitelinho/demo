/*
 * GenerateRebillAttributeBuilder.java Created on May 15, 2019
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
import com.ibsplc.icargo.business.shared.airline.vo.AirlineVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;
/**
 * @author A-3108
 *
 */
public class GenerateRebillAttributeBuilder extends AttributeBuilderAdapter{

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
	    columns1.add("MALCTGCOD");
	    columns1.add("TOTWGT");
	    columns1.add("APLRAT");
	    columns1.add("VATAMT");
	    columns1.add("NETAMT");
	    columns1.add("MALSUBCLS");
	    columns1.add("MONTHFLAG");
	    columns1.add("TOTAMTLC");
	    columns1.add("TOTAMTCP");
	    columns1.add("INVNUM");
	    columns1.add("POACOD");
	    columns1.add("BILLFRM");
	    columns1.add("UNITCOD");
    
	    Vector <String> columns2 = new Vector<String> ();

	    columns2.add("MALCTGCOD");
	    columns2.add("DSN");
	    columns2.add("RSN");
	    columns2.add("CCAREFNUM");
	    columns2.add("ORGCOD");
	    columns2.add("DSTCOD");
	    columns2.add("SECTOR");
	    columns2.add("FLTNUM");
	    columns2.add("TOTWGT");
	    columns2.add("APLRAT");
	    columns2.add("BLDAMT");
	    columns2.add("SRVTAX");
	    columns2.add("VATAMT");
	    columns2.add("NETAMT");
	    columns2.add("BLGCURCOD");
	    columns2.add("BLDPRD");
	    columns2.add("TOTWGTCP");
	    columns2.add("TOTWGTLC");
	    columns2.add("TOTWGTEMS");
	    columns2.add("TOTWGTSV");
	    columns2.add("FLTDAT");
	    columns2.add("REMARKS");
	    columns2.add("MALIDR");	    



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

	      if (cn51listVO.getValCharges() > 0.0)
	    	  row1.add(cn51listVO.getValCharges());
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
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }
	      if (cn51listVO.getUnitCode() != null)
	    	  row1.add(cn51listVO.getUnitCode());
		      else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }

	      subReportDataOne.add(row1);
	      this.log.log(5, new Object[] { " \n sUB rEPORT dAtA--", cn51listVO });
	    }
	    for (CN66DetailsVO cn66listVO : cn66listVOs) {
	    	row1 = new Vector<Object>();

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
		      if (cn66listVO.getAmount()  > 0.0D) {
		    	  row1.add(String.valueOf(cn66listVO.getAmount()));
		      }
		      else {
		    	  row1.add("0.0");
		      }
		      if (cn66listVO.getServiceTax()  > 0.0D) {
		    	  row1.add(String.valueOf(cn66listVO.getServiceTax()));
		      }
		      else {
		    	  row1.add("0.0");
		      }
		      
		      if (cn66listVO.getValCharges()  > 0.0D) {
		    	  row1.add(String.valueOf(cn66listVO.getValCharges()));
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

		      if (cn66listVO.getBillingPeriod()  !=null) {
		    	  row1.add(String.valueOf(cn66listVO.getBillingPeriod()));
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
		      if (cn66listVO.getWeightEMS() > 0.0D) {
		    	  row1.add(String.valueOf(cn66listVO.getWeightEMS()));
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
		      if (cn66listVO.getFlightDate()!=null) {
		    	  row1.add(String.valueOf(cn66listVO.getFlightDate()));
		      }
		      else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }
		      if(cn66listVO.getRemarks() != null && cn66listVO.getRemarks().trim().length() > 0){
		    	  row1.add(String.valueOf(cn66listVO.getRemarks()));
		      }else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
		      }
		      if(cn66listVO.getBillingBasis() != null && cn66listVO.getBillingBasis().trim().length() > 0){
		    	  row1.add(String.valueOf(cn66listVO.getBillingBasis()));
		      }else {
		    	  row1.add(ReportConstants.EMPTY_STRING);
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
			//Modified as part of ICRD-336851
			if (listVO.getBillingCurrencyCode()!= null) {
				row.add(listVO.getBillingCurrencyCode());
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
