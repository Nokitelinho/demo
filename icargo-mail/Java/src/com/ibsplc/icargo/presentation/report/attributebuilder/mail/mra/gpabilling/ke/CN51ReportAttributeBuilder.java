/*
 * CN51ReportAttributeBuilder.java Created on Jan 09, 2018 by A-7794
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.mail.mra.gpabilling.ke;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import java.util.Map.Entry;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-7794
 *
 */
public class CN51ReportAttributeBuilder extends AttributeBuilderAdapter {

	private Log log = LogFactory.getLogger("mailtracking_mra_gpabilling");	



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
		columns.add("ORG-DES");
		columns.add("CATCOD");
		columns.add("MALSUBCLS");
		columns.add("WGT");
		columns.add("MAILRAT");
		columns.add("MAILAMT");
		columns.add("SURAMT");
		columns.add("GROSSAMT");
		columns.add("TAX");
		columns.add("AMT");	
		columns.add("MONTHFLAG");
		columns.add("BILFRM");
		columns.add("BILTO");
		columns.add("AMTLC");
		columns.add("AMTCP");
		columns.add("STLCUR");

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
		Iterable<CN51DetailsVO> cn51detailsVO = (Iterable<CN51DetailsVO>) dataRecords;

		Collection<CN51DetailsVO> cn51vos = (Collection<CN51DetailsVO>) cn51detailsVO;

		HashMap<String, Collection<CN51DetailsVO>> cn51DetailsVOMap = null;
		cn51DetailsVOMap  = new HashMap<String, Collection<CN51DetailsVO>>();

		for(CN51DetailsVO cn51VO: cn51vos){
			String origin = cn51VO.getOrigin();
			String destination = cn51VO.getDestination();
			String category = cn51VO.getMailCategoryCode();
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

		for(Entry e : cn51DetailsVOMap.entrySet()){
			log.log(Log.INFO,"inside loop1-->");
			int preCnt=0;
			String key = e.getKey().toString();
			Collection<CN51DetailsVO> cn51VosFromMap = (ArrayList<CN51DetailsVO>)e.getValue();
			String firstLoop = "";
			for(CN51DetailsVO cn51vo :cn51VosFromMap){
				log.log(Log.INFO,"inside loop2-->");
				row= new Vector<Object>();
				row.add(key);
				row.add(key);
				row.add(cn51vo.getMailCategoryCode()); 
				row.add(cn51vo.getMailSubclass());
				row.add(cn51vo.getTotalWeight());
				row.add(cn51vo.getApplicableRate());
				//Modified by A-7794 as part of ICRD-267539
				row.add(Double.parseDouble(cn51vo.getMailCharge()));
				row.add(cn51vo.getSurCharge());
				row.add(cn51vo.getGrossAmount());
				row.add(cn51vo.getServiceTax());
				row.add(cn51vo.getTotalAmount().getAmount()); 
				row.add(cn51vo.getMonthFlag());
				row.add(cn51vo.getOrigin());
				row.add(cn51vo.getDestination());
				row.add(cn51vo.getTotalAmtinLC());
				row.add(cn51vo.getTotalAmtinCP());
				
				row.add(cn51vo.getBillingCurrencyCode());
				firstLoop = "x";
				tableData.add(row);
			}
		}
		log.log(Log.INFO, "tableData-->", tableData);
		return tableData;
	}
	/**
	 * Method to construct the report parameters. The report parameters
	 * corresponds to the parameter fields in the report. The order of the
	 * parameters should match the order in which the parameter fields are laid
	 * out in the report
	 * @param parameters the parameter data
	 * @param extraInfo information required for formatting the parameters
	 * @return Vector the report parameters
	 */

	@Override
	public Vector<Object> getReportParameters(
			Collection parameters, Collection extraInfo) {
		Vector<Object> reportParameters = new Vector<Object>();
		Object dataRecords = ((ArrayList<Object>)extraInfo).get(1);
		Iterable<CN51DetailsVO> data = (Iterable<CN51DetailsVO>) dataRecords;
		CN51DetailsVO vo =  data.iterator().next();

		reportParameters.add(vo.getInvoiceNumber());
		reportParameters.add(vo.getGpaCode());
		reportParameters.add(vo.getAirlineCode());
		return reportParameters;
	}
}

