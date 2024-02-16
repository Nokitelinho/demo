/*
 * CN51ReportAttributeBuilder.java Created on Mar 01, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.mail.mra.gpabilling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import java.util.Map.Entry;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66VO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2270
 *
 */
public class CN51ReportAttributeBuilder extends AttributeBuilderAdapter {
	
	private Log log = LogFactory.getLogger("mailtracking_mra_gpabilling");	
	
	private static final String CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";
	
	
	/**
	 * Method to construct the report column names. The column names corresponds
	 * to the column names of the view used while laying out the report. The
	 * order of the column names should match the order in which the database
	 * fields are laid out in the report
	 * @param parameters
	 * @param extraInfo
	 * @return Vector<String> the column names
	 */
	@Override
	public Vector<String> getReportColumns() {

		Vector<String> columns = new Vector<String>();

		columns.add("SECTOR");		
		columns.add("CATCOD");
		columns.add("WGT");
		columns.add("RAT");	
		columns.add("VAT");
		columns.add("AMT");		
		//columns.add("TOTAMT");
		columns.add("MALSUBCLS");
		columns.add("MONTHFLAG");
		columns.add("BILFRM");
		columns.add("BILTO");
		columns.add("AMTLC");
		columns.add("AMTCP");
		columns.add("MAILRAT");
		columns.add("MAILAMT");
		columns.add("SURAMT");
		columns.add("GROSSAMT");
		columns.add("TAX");
		columns.add("STLCUR");
		columns.add("AMT");
		columns.add("OVRRND");//Added by A-7929 as part of ICRD-257249 
		return columns;
	}


	/**
	 * Method to construct the report data. Each row in the details section of
	 * the report corresponds to one element in the outer Vector. Each element
	 * in the inner Vector corresponds to a field in the report. The order in
	 * which the data is returned should match the order in which the fields are
	 * laid out in the report
	 * @param parameters
	 * @param extraInfo
	 * @return Vector<Vector> the report data
	 */
	@Override
	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {
		Vector<Vector> tableData = new Vector<Vector>();
		
		log.log(Log.INFO, "extraInfo-->", extraInfo);
		Object extraInfor = ((ArrayList<OneTimeVO>) extraInfo).get(0);
		Collection<OneTimeVO> categories = (Collection<OneTimeVO>) extraInfor;
		
		log.log(Log.INFO, "categories-->", categories);
		Object dataRecords = ((ArrayList<Object>)extraInfo).get(1);
		CN51CN66VO cN51CN66VO = (CN51CN66VO) dataRecords; 
		
		log.log(Log.INFO, "cN51CN66VO-->", cN51CN66VO);
		Collection<CN51DetailsVO> cn51vos = cN51CN66VO.getCn51DetailsVOs();
		//log.log(Log.FINE, "\n\n CN51DetailsVOs----in reports----->  "+cn51vos);
		
		HashMap<String, Collection<CN51DetailsVO>> cn51DetailsVOMap = null;
		cn51DetailsVOMap  = new HashMap<String, Collection<CN51DetailsVO>>();
		
		
		
   		for(CN51DetailsVO cn51VO: cn51vos){
   		
   			String origin = cn51VO.getOrigin();
   			String destination = cn51VO.getDestination();
   			String category = cn51VO.getMailCategoryCode();
   			String companyCode = cn51VO.getCompanyCode();
   			
   			// commented by a-3434 for bug 101429 and shifted this to CN51ReportPrintCommand
   			
   			//String description = fetchOneTimeDetails(companyCode,category);
   			//cn51VO.setMailCategoryCode(description);
   			
   			String keyForCompare = origin.concat("-").concat(destination);
   			log.log(Log.INFO, "keyForCompare-->", keyForCompare);
			log.log(Log.INFO, "origin-->", origin);
			boolean isAccountName = cn51DetailsVOMap.containsKey(keyForCompare);
   			log.log(Log.INFO, "isAccountName present-->", isAccountName);
			if(isAccountName){
   				Collection<CN51DetailsVO> collnExists = cn51DetailsVOMap.get(keyForCompare);
   				collnExists.add(cn51VO);
   			}else{
   				Collection<CN51DetailsVO> collnToAdd = new ArrayList<CN51DetailsVO>();
   				collnToAdd.add(cn51VO);
   				cn51DetailsVOMap.put(keyForCompare,collnToAdd);
   			}
   			
   			if (category != null && categories != null) {
   				for (OneTimeVO oneTimeVO : categories) {
   					if (category.equals(oneTimeVO.getFieldValue())) {
   						cn51VO.setMailCategoryCode(oneTimeVO.getFieldDescription());
   					}
   				}
   				
   			} 
   		}
   		Vector<Object> row =  null;

				
		
		
   		
//   		for(String key : cn51DetailsVOMap.keySet()){
//   			log.log(Log.INFO,"inside loop1-->");
//   			 Collection<CN51DetailsVO> cn51VosFromMap = cn51DetailsVOMap.get(key);
//   			  String firstLoop = "";
//   			  for(CN51DetailsVO cn51vo :cn51VosFromMap){
//   				log.log(Log.INFO,"inside loop2-->");
//   				 row= new Vector<Object>();
//   				
//   				if(!firstLoop.equals("x")){
//   					row.add(key);
//   				}else{
//   					row.add("");
//   				}
//   				//row.add("");
//   				row.add(cn51vo.getMailSubclass());
//   				//row.add(cn51vo.getMailSubclass());
//   				row.add(cn51vo.getTotalWeight());
//   				row.add(cn51vo.getApplicableRate());
//   				row.add(cn51vo.getTotalAmount());
//   				row.add(cn51vo.getTotalBilledAmount());
//   				firstLoop = "x";
//   				tableData.add(row);
//   			  }
//   		}
   		
   		
   		for(Entry e : cn51DetailsVOMap.entrySet()){
   			log.log(Log.INFO,"inside loop1-->");
   			int preCnt=0;
   			String N="N";
   			String key = e.getKey().toString();
   			 Collection<CN51DetailsVO> cn51VosFromMap = (ArrayList<CN51DetailsVO>)e.getValue();
   			  String firstLoop = "";
   			  for(CN51DetailsVO cn51vo :cn51VosFromMap){
   				log.log(Log.INFO,"inside loop2-->");
   				 row= new Vector<Object>();
   				/*if ("P".equals(cn51vo.getMonthFlag())){
   					preCnt++;
   				}
   				if(!"x".equals(firstLoop) ){
   					row.add(key);
   				}else{
   					if ("C".equals(cn51vo.getMonthFlag())){
   						row.add("");
   					}
   					else{	
   						if ("P".equals(cn51vo.getMonthFlag()) && preCnt>1 ) {
							row.add("");
						} else {
							row.add(key);
						}
   					}
   				}*/
   				 row.add(key);
   				//row.add("");
   				/*columns.add("SECTOR");
   				columns.add("CATCOD");
   				columns.add("WGT");
   				columns.add("RAT");
   				columns.add("AMT");
   				columns.add("TOTAMT");
   				columns.add("MALSUBCLS");*/
   				 //added by a-7871 for ICRD-214766
   				if(N.equalsIgnoreCase(cn51vo.getOverrideRounding()))
   				{
   				row.add(cn51vo.getMailCategoryCode());//whtr airmail   				
   				row.add(cn51vo.getTotalWeight());
   				row.add(cn51vo.getApplicableRate());
   				//row.add(cn51vo.getTotalAmount());
   				row.add(cn51vo.getVatAmount().toString());
   				//row.add(cn51vo.getTotalBilledAmount().getAmount());
   				row.add(cn51vo.getTotalAmount().toString());  
   				row.add(cn51vo.getMailSubclass());
   				row.add(cn51vo.getMonthFlag());
   				row.add(cn51vo.getOrigin());
   				row.add(cn51vo.getDestination());
   				row.add(cn51vo.getTotalAmtinLC().toString());
   				row.add(cn51vo.getTotalAmtinCP().toString());
   				row.add(cn51vo.getApplicableRate());
   				row.add(cn51vo.getMailCharge());
   				row.add(cn51vo.getSurCharge());
   				row.add(cn51vo.getGrossAmount());
   				row.add(cn51vo.getServiceTax());
   				row.add(cn51vo.getBillingCurrencyCode());
   				row.add(cn51vo.getTotalAmount().toString()); 
   				row.add(cn51vo.getOverrideRounding());
   				}
   				else
   				{
   					row.add(cn51vo.getMailCategoryCode());//whtr airmail   				
   	   				row.add(cn51vo.getTotalWeight());
   	   				row.add(cn51vo.getApplicableRate());
   	   				//row.add(cn51vo.getTotalAmount());
   	   				row.add(cn51vo.getVatAmt());
   	   				//row.add(cn51vo.getTotalBilledAmount().getAmount());
   	   			 row.add(cn51vo.getTotalNetAmount());  
   	   				row.add(cn51vo.getMailSubclass());
   	   				row.add(cn51vo.getMonthFlag());
   	   				row.add(cn51vo.getOrigin());
   	   				row.add(cn51vo.getDestination());
   	   				row.add(cn51vo.getTotalAmountLC());
   	   				row.add(cn51vo.getTotalAmountCP());
   	   				row.add(cn51vo.getApplicableRate());
   	   				row.add(cn51vo.getMailCharge());
   	   				row.add(cn51vo.getSurCharge());
   	   				row.add(cn51vo.getGrossAmount());
   	   				row.add(cn51vo.getServiceTax());
   	   				row.add(cn51vo.getBillingCurrencyCode());
   	   			row.add(cn51vo.getTotalNetAmount());  
   	   			row.add(cn51vo.getOverrideRounding());
   				}
   				firstLoop = "x";
   				tableData.add(row);
   			  }
   		}
   		
   		
   		log.log(Log.INFO, "tableData-->", tableData);
	return tableData;
	}
	
	
	
	 /**
	 * Helper method to get the one time data.
	 * @param companyCode String
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	//Commented by vivek for BUG 101429 @JNB starts
	/*private String fetchOneTimeDetails(String companyCode,String category) {

		log.entering("CN51ReportAttributeBuilder", "fetchOneTimeDetails");
		 String description = "";
		 Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(CATEGORY_ONETIME);
		Map<String, Collection<OneTimeVO>> hashMap =
					new HashMap<String, Collection<OneTimeVO>>();
			try {
					SharedDefaultsDelegate sharedDefaultsDelegate =
												new SharedDefaultsDelegate();
					hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
							oneTimeList);
			} catch (BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
			   }
			Collection<OneTimeVO> oneTimeVOs = hashMap.get(CATEGORY_ONETIME);
				for(OneTimeVO oneTimeVo :oneTimeVOs){
					 if(oneTimeVo.getFieldValue().equalsIgnoreCase(category)){
					    description= oneTimeVo.getFieldDescription();
					 }
					}
			log.exiting("CN51ReportAttributeBuilder", "fetchOneTimeDetails");
			
			return description;
	}*/
	//Commented by vivek for BUG 101429 @JNB ends
	
	
	
	/**
	 * Method to construct the report parameters. The report parameters
	 * corresponds to the parameter fields in the report. The order of the
	 * parameters should match the order in which the parameter fields are laid
	 * out in the report
	 * @param parameters the parameter data
	 * @param extraInfo information required for formatting the parameters
	 * @return Vector the report parameters
	 */
//	@Override
//	public Vector<Object> getReportParameters(
//			Collection parameters, Collection extraInfo) {
//		//System.out.println("Inside AttributeBuilder!!!!!!");
//		Vector<Object> reportParameters = new Vector<Object>();		
//		
//		Object dataParameters = ((ArrayList<Object>)parameters).get(0);
//		MailManifestVO mailManifestVO = 
//				(MailManifestVO)dataParameters;
//		
//		reportParameters.add(mailManifestVO.getFlightCarrierCode());
//		reportParameters.add(mailManifestVO.getFlightNumber());
//		reportParameters.add(mailManifestVO.getDepDate().toDisplayFormat("dd-MMM-yyyy"));
//		reportParameters.add(String.valueOf(mailManifestVO.getTotalbags()));
//		reportParameters.add(String.valueOf(mailManifestVO.getTotalWeight()));
//	
//		return reportParameters;
//	}
	
	@Override
	public Vector<Object> getReportParameters(
			Collection parameters, Collection extraInfo) {
		//System.out.println("Inside AttributeBuilder!!!!!!");
		
		Vector<Object> reportParameters = new Vector<Object>();
		Page<CN51DetailsVO> cn51vos =null; 
		
		Object dataParameters = ((ArrayList<Object>)extraInfo).get(1);
		CN51CN66VO cN51CN66VO = (CN51CN66VO) dataParameters; 
		
		cn51vos=cN51CN66VO.getCn51DetailsVOs();
		reportParameters.add(cn51vos.iterator().next().getInvoiceNumber());
		reportParameters.add(cn51vos.iterator().next().getGpaCode());
		//Added by indu
		reportParameters.add(cN51CN66VO.getAirlineCode());
	return reportParameters;
	}
	
	
	
	/**
	 * sub report  column
	 */
	@Override
	public Vector<Vector<String>> getSubReportColumns() {
		Vector<Vector<String>> subReportColumns = new Vector<Vector<String>>();
		Vector<String> columnsForSubRpt = new Vector<String>();
		columnsForSubRpt.add("SECTOR");
		columnsForSubRpt.add("MALSUBCLS");
		columnsForSubRpt.add("WEIGHT");
		columnsForSubRpt.add("AMOUNT");
		columnsForSubRpt.add("MONTHFLAG");
		columnsForSubRpt.add("BILFRM");
		columnsForSubRpt.add("BILTO");
		columnsForSubRpt.add("OVRRND");//added by a-7871
		subReportColumns.add(columnsForSubRpt);
		log.log(Log.INFO, "**************columns1*************",
				columnsForSubRpt);
		Vector<String> columnsForSubRpt2 = new Vector<String>();
		columnsForSubRpt2.add("SECTOR");
		columnsForSubRpt2.add("MALSUBCLS");
		columnsForSubRpt2.add("WEIGHT");
		columnsForSubRpt2.add("AMOUNT");
		columnsForSubRpt2.add("MONTHFLAG");
		columnsForSubRpt2.add("BILFRM");
		columnsForSubRpt2.add("BILTO");
		columnsForSubRpt2.add("OVRRND");//added by a-7871
		subReportColumns.add(columnsForSubRpt2);
		log.log(Log.INFO, "**************columns2*************",
				columnsForSubRpt2);
		return subReportColumns;
	}
	/**
	 * subreport data
	 * @param data
	 * @param extraInfo
	 */
	@Override
	public Vector<Vector<Vector>> getSubReportData(Collection data, Collection extraInfo) {
		//Vector<Vector> subReportDataOne = new Vector<Vector>();
		Vector<Vector<Vector>> subReportDatas = new Vector<Vector<Vector>>();
		Vector<Vector> subReportOneDatas = new Vector<Vector>();
		Vector<Vector> subReportOneDatas2 = new Vector<Vector>();
		 ArrayList subreportColln = (ArrayList)data;
			String N="N";
		// ArrayList<CN51DetailsVO> subreportColln=new  ArrayList<CN51DetailsVO>(subReportTwoDataArray.get(0));
		 HashMap<String, Collection<CN51DetailsVO>> cn51DetailsVOMap = null;
			cn51DetailsVOMap  = new HashMap<String, Collection<CN51DetailsVO>>();
			for(CN51DetailsVO cn51VO: (Collection<CN51DetailsVO>)subreportColln.get(0)){
	   			String origin = cn51VO.getOrigin();
	   			String destination = cn51VO.getDestination();
	   			String keyForCompare = origin.concat("-").concat(destination);
	   			log.log(Log.INFO, "keyForCompare-->", keyForCompare);
				log.log(Log.INFO, "origin-->", origin);
				boolean isAccountName = cn51DetailsVOMap.containsKey(keyForCompare);
	   			log.log(Log.INFO, "isAccountName present-->", isAccountName);
				if(isAccountName){
	   				Collection<CN51DetailsVO> collnExists = cn51DetailsVOMap.get(keyForCompare);
	   				collnExists.add(cn51VO);
	   			}else{
	   				Collection<CN51DetailsVO> collnToAdd = new ArrayList<CN51DetailsVO>();
	   				collnToAdd.add(cn51VO);
	   				cn51DetailsVOMap.put(keyForCompare,collnToAdd);
	   			}
	   		}
			Vector<Object> row =  null;
			for(Entry e : cn51DetailsVOMap.entrySet()){
	   			log.log(Log.INFO,"inside loop1-->");
	   			int preCnt=0;
	   			String key = e.getKey().toString();
	   			 Collection<CN51DetailsVO> cn51VosFromMap = (ArrayList<CN51DetailsVO>)e.getValue();
	   			  String firstLoop = "";
	   			  for(CN51DetailsVO cn51vo :cn51VosFromMap){
	   				log.log(Log.INFO,"inside loop2-->");
	   				 row= new Vector<Object>();
	   				if ("P".equals(cn51vo.getMonthFlag())){
	   					preCnt++;
	   				}
	   				if(!"x".equals(firstLoop) ){
	   					row.add(key);
	   				}else{
	   					if ("C".equals(cn51vo.getMonthFlag())){
	   						row.add("");
	   					}
	   					else{	
	   						if ("P".equals(cn51vo.getMonthFlag()) && preCnt>1 ) {
								row.add("");
							} else {
								row.add(key);
							}
	   					}
	   				}
	   				row.add(cn51vo.getActualSubCls());
	   				row.add(cn51vo.getTotalWeight());
	   				if(N.equalsIgnoreCase(cn51vo.getOverrideRounding()))
	   				{
	   				row.add(cn51vo.getTotalBilledAmount().toString());
	   				}
	   				else
	   					{
	   					row.add(cn51vo.getTotAmt());  	
	   					}  	
	   				row.add(cn51vo.getMonthFlag());
	   				row.add(cn51vo.getOrigin());
	   				row.add(cn51vo.getDestination());
	   				row.add(cn51vo.getOverrideRounding());
	   				firstLoop = "x";
	   				subReportOneDatas.add(row);
	   				subReportOneDatas2.add(row);
	   			  }
	   		}
			subReportDatas.add(subReportOneDatas);
			subReportDatas.add(subReportOneDatas2);
	   		log.log(Log.INFO, "subReportDatas-->", subReportDatas);
		return subReportDatas;
		}
}

