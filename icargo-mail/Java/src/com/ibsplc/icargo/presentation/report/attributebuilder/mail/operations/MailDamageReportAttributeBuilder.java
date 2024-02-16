/*
 * MailDamageReportAttributeBuilder.java Created on FEB 29, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.mail.operations;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.ibsplc.icargo.business.mail.operations.vo.DamageMailFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3353
 *
 */
public class MailDamageReportAttributeBuilder extends AttributeBuilderAdapter{
	
	private static final String CLASS_NAME = "MailDamageReportAttributeBuilder";
	private Log log = LogFactory.getLogger(CLASS_NAME);
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
		columns.add("ARPRT");
		columns.add("MLBGID");
		columns.add("FLTNUM");
		columns.add("DESTN");
		columns.add("FLTDAT");
		columns.add("DMGTYP");
		columns.add("RMRKS");
		//added by A-5844 for ICRD-67196
		columns.add("ORGEXGOFC");
		columns.add("POACOD");
		columns.add("DSN");
		columns.add("SUBCLSCOD");
		columns.add("SUBCLSGRP");
		columns.add("wgt");
		columns.add("dclval");
		columns.add("POL");
		columns.add("POU");
		columns.add("CURCOD");
		return columns;
		
	}
	/**
	 * Method to construct the report data. Each row in the details section of
	 * the report corresponds to one element in the outer Vector. Each element
	 * in the inner Vector corresponds to a field in the report. The order in
	 * which the data is returned should match the order in which the fields are
	 * laid out in the report
	 * @param data
	 * @param extraInfo
	 * @return Vector<Vector> the reportData
	 */
	@Override
	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {
		
		
		List<DamagedMailbagVO> damagedMailbagVOS = (ArrayList<DamagedMailbagVO>)data;
		Map<String,DamagedMailbagVO> noDupliMailVOS =new HashMap<String,DamagedMailbagVO>();
		Vector<Vector> reportData = new Vector<Vector>();
		Object extraInfor = ((ArrayList<OneTimeVO>)extraInfo).get(0);
		Collection<OneTimeVO> damageCodes = (Collection<OneTimeVO>)extraInfor;
		log.log(Log.FINE, "Entering Attribute --->>", data);
		log.log(Log.FINE, "extraInfo..", damageCodes);
		if (data != null && data.size() > 0){
			if(damagedMailbagVOS!=null && !damagedMailbagVOS.isEmpty()){
				for(DamagedMailbagVO damagedMailbagVO:damagedMailbagVOS){
					String key = damagedMailbagVO.getMailbagId();
					for (OneTimeVO oneTimeVO : damageCodes) {
						if (damagedMailbagVO.getDamageCode().equals(
								oneTimeVO.getFieldValue())) {
							damagedMailbagVO.setDamageCode(oneTimeVO
									.getFieldDescription());
						} 
					}
					if(!noDupliMailVOS.containsKey(damagedMailbagVO.getMailbagId()))
						{
						noDupliMailVOS.put(key, damagedMailbagVO);
						}
					else {
						DamagedMailbagVO damageMailbagVO = noDupliMailVOS.get(key);
						String damageCode = damagedMailbagVO.getDamageCode()+ "\n" + damageMailbagVO.getDamageCode();
						damagedMailbagVO.setDamageCode(damageCode);

						if (damagedMailbagVO.getRemarks() == null || 
								damagedMailbagVO.getRemarks().trim().length() == 0) {
							damagedMailbagVO.setRemarks("");
						}
						if (damageMailbagVO.getRemarks() == null || 
								damageMailbagVO.getRemarks().trim().length() == 0) {
							damageMailbagVO.setRemarks("");        
						}
							String remarks = damagedMailbagVO.getRemarks() + "\n" + damageMailbagVO.getRemarks();
							damagedMailbagVO.setRemarks(remarks);
						
						noDupliMailVOS.put(key, damagedMailbagVO);
					}						
				}
			}
			for(DamagedMailbagVO damagedMailbagVO:noDupliMailVOS.values()){
				Vector<Object> row = new Vector<Object>();
				row.add((damagedMailbagVO.getAirportCode()!= null)
						?damagedMailbagVO.getAirportCode()
						:ReportConstants.EMPTY_STRING);
				
				row.add((damagedMailbagVO.getMailbagId()!= null)
						?damagedMailbagVO.getMailbagId()
						:ReportConstants.EMPTY_STRING);
				damagedMailbagVO.setCarrierCode(damagedMailbagVO.getCarrierCode()!=null?damagedMailbagVO.getCarrierCode():ReportConstants.EMPTY_STRING);
				damagedMailbagVO.setFlightNumber(damagedMailbagVO.getFlightNumber()!=null?damagedMailbagVO.getFlightNumber():ReportConstants.EMPTY_STRING);
				if("-1".equals(damagedMailbagVO.getFlightNumber())){
					damagedMailbagVO.setFlightNumber(ReportConstants.EMPTY_STRING);
				}
				String str = new StringBuilder().append(damagedMailbagVO.getCarrierCode())
			       .append(" ").append(damagedMailbagVO.getFlightNumber()).toString();
				row.add((str!= null)?str:ReportConstants.EMPTY_STRING);
				row.add((damagedMailbagVO.getDestinationExchangeOffice()!= null)?damagedMailbagVO.getDestinationExchangeOffice():ReportConstants.EMPTY_STRING);
				row.add((damagedMailbagVO.getFlightDate()!= null)?damagedMailbagVO.getFlightDate().toDisplayFormat("dd-MMM-yyyy"):ReportConstants.EMPTY_STRING);
				damagedMailbagVO.setDamageCode(damagedMailbagVO.getDamageCode()!= null?damagedMailbagVO.getDamageCode():ReportConstants.EMPTY_STRING);
				row.add(damagedMailbagVO.getDamageCode());
				row.add((damagedMailbagVO.getRemarks()!= null)?damagedMailbagVO.getRemarks():ReportConstants.EMPTY_STRING);
				//added by A-5844 for ICRD-67196 starts
				row.add((damagedMailbagVO.getOriginExchangeOffice()!= null)?damagedMailbagVO.getOriginExchangeOffice():ReportConstants.EMPTY_STRING);
				row.add((damagedMailbagVO.getPaCode()!= null)?damagedMailbagVO.getPaCode():ReportConstants.EMPTY_STRING);
				row.add((damagedMailbagVO.getDsn()!= null)?damagedMailbagVO.getDsn():ReportConstants.EMPTY_STRING);
				row.add((damagedMailbagVO.getSubClassCode()!= null)?damagedMailbagVO.getSubClassCode():ReportConstants.EMPTY_STRING);
				row.add((damagedMailbagVO.getSubClassGroup()!= null)?damagedMailbagVO.getSubClassGroup():ReportConstants.EMPTY_STRING);
				row.add(String.valueOf(damagedMailbagVO.getWeight().getRoundedDisplayValue()));
				row.add((damagedMailbagVO.getDeclaredValue()!= 0.0)?damagedMailbagVO.getDeclaredValue():ReportConstants.EMPTY_STRING);
				row.add((damagedMailbagVO.getFlightOrigin()!= null)?damagedMailbagVO.getFlightOrigin():ReportConstants.EMPTY_STRING);
				String route[],destination="";
				if(damagedMailbagVO.getFlightDestination()!=null && damagedMailbagVO.getFlightDestination().trim().length()>0){
					route=damagedMailbagVO.getFlightDestination().split("-");
					destination = route[route.length-1];
					
				}
				row.add(destination);
				row.add((damagedMailbagVO.getCurrencyCode()!= null)?damagedMailbagVO.getCurrencyCode():ReportConstants.EMPTY_STRING);
				//added by A-5844 for ICRD-67196 ends
				reportData.add(row);
			}
		
		}
		log.log(Log.FINE, "Exiting Attribute ---->>", reportData);
		return reportData;
		
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
		log.log(Log.FINE, "parameters..", parameters);
		log.log(Log.FINE, "extraInfo..", extraInfo);
		Object dataParameters = ((ArrayList<Object>)parameters).get(0);
		Object extraInfor = ((ArrayList<OneTimeVO>)extraInfo).get(0);
		Collection<OneTimeVO> damageCodes = (Collection<OneTimeVO>)extraInfor;
		log.log(Log.FINE, "damagecodes..", damageCodes);
		DamageMailFilterVO damageMailFilterVO = (DamageMailFilterVO)dataParameters;
		
		reportParameters.add((damageMailFilterVO.getFromDate()!= null)?damageMailFilterVO.getFromDate().toDisplayFormat("dd-MMM-yyyy").toUpperCase():ReportConstants.EMPTY_STRING);
		reportParameters.add((damageMailFilterVO.getToDate()!= null)?damageMailFilterVO.getToDate().toDisplayFormat("dd-MMM-yyyy").toUpperCase():ReportConstants.EMPTY_STRING);
		reportParameters.add((damageMailFilterVO.getAirport()!= null)?damageMailFilterVO.getAirport():ReportConstants.EMPTY_STRING);
		damageMailFilterVO.setDamageCode(damageMailFilterVO.getDamageCode()!= null?damageMailFilterVO.getDamageCode():ReportConstants.EMPTY_STRING);
		for (OneTimeVO oneTimeVO : damageCodes) {
			if (damageMailFilterVO.getDamageCode().equals(
					oneTimeVO.getFieldValue())) {
				damageMailFilterVO.setDamageCode(oneTimeVO
						.getFieldDescription());
			} 
		}
		reportParameters.add(damageMailFilterVO.getDamageCode());
		//added by A-5844 for ICRD-67196 starts
		reportParameters.add((damageMailFilterVO.getFlightCarrierCode()!= null)?damageMailFilterVO.getFlightCarrierCode():ReportConstants.EMPTY_STRING);
		reportParameters.add((damageMailFilterVO.getFlightNumber()!= null)?damageMailFilterVO.getFlightNumber():ReportConstants.EMPTY_STRING);
		reportParameters.add((damageMailFilterVO.getFlightDate()!= null)?
				damageMailFilterVO.getFlightDate().toDisplayDateOnlyFormat():ReportConstants.EMPTY_STRING);
		reportParameters.add((damageMailFilterVO.getFlightOrigin()!= null)?damageMailFilterVO.getFlightOrigin():ReportConstants.EMPTY_STRING);
		reportParameters.add((damageMailFilterVO.getFlightDestination()!= null)?damageMailFilterVO.getFlightDestination():ReportConstants.EMPTY_STRING);
		reportParameters.add((damageMailFilterVO.getGpaCode()!= null)?damageMailFilterVO.getGpaCode():ReportConstants.EMPTY_STRING);
		reportParameters.add((damageMailFilterVO.getOriginOE()!= null)?damageMailFilterVO.getOriginOE():ReportConstants.EMPTY_STRING);
		reportParameters.add((damageMailFilterVO.getDestinationOE()!= null)?damageMailFilterVO.getDestinationOE():ReportConstants.EMPTY_STRING);
		reportParameters.add((damageMailFilterVO.getSubClassGroup()!= null)?damageMailFilterVO.getSubClassGroup():ReportConstants.EMPTY_STRING);
		reportParameters.add((damageMailFilterVO.getSubClassCode()!= null)?damageMailFilterVO.getSubClassCode():ReportConstants.EMPTY_STRING);
		Collection<DamagedMailbagVO> damagedMailbagVOs= (Collection<DamagedMailbagVO>) ((ArrayList<OneTimeVO>)extraInfo).get(1);
		double declaredValTot=0.0;
		double weightTot=0.0; 
		String totCurrencyCode = "";
		DecimalFormat decimalFormatter = new DecimalFormat("0.00"); 
		Set<String> mailBagSet=new HashSet<String>();
		for(DamagedMailbagVO damagedMailbagVO:damagedMailbagVOs){
			mailBagSet.add(damagedMailbagVO.getMailbagId());
			//weightTot+=damagedMailbagVO.getWeight();
			weightTot+=damagedMailbagVO.getWeight().getRoundedDisplayValue();//added by A-7371,modified by A-8353
			declaredValTot=declaredValTot+damagedMailbagVO.getDeclaredValueTot();
			totCurrencyCode=damagedMailbagVO.getTotCurrencyCode();
		}
		reportParameters.add(String.valueOf(mailBagSet.size()));
		reportParameters.add(decimalFormatter.format(weightTot));
		//added by A-5844 for the bug ICRD-80967
		if(declaredValTot==0.0){
			reportParameters.add(ReportConstants.EMPTY_STRING);
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}else{
		reportParameters.add(decimalFormatter.format(declaredValTot));
		reportParameters.add(totCurrencyCode);
		}	
		//added by A-5844 for ICRD-67196 ends
		reportParameters.add((damageMailFilterVO.getAirline()!= null)?damageMailFilterVO.getAirline():ReportConstants.EMPTY_STRING);
		
		log.log(Log.FINE, "report parameters...", reportParameters);
		return reportParameters;
	}
}
