/*
 * MailBagHistoryAttributeBuilder.java Created on Jan 08, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.mail.operations;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;

/**
 * @author A-1862
 *
 */
public class MailBagHistoryAttributeBuilder extends AttributeBuilderAdapter {

	private static final String MAIL_STATUS = "mailtracking.defaults.mailstatus";
	
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
		columns.add("DATES");
		columns.add("TIMES");
		columns.add("OPR");
		columns.add("AIRPORT");
		columns.add("CARR");
		columns.add("FLTNUM");
		columns.add("FLTDAT");
		columns.add("CONT");
		columns.add("POU");
		columns.add("USERID");
		columns.add("MALIDR");
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

		Iterator iterator = data.iterator();

		while(iterator.hasNext()) {
           
			MailbagHistoryVO mailbagHistoryVO  = (MailbagHistoryVO)iterator.next();
  
				Vector<Object> row = new Vector<Object>();
			
				row.add(mailbagHistoryVO.getScanDate().toDisplayFormat("dd-MMM-yyyy").toUpperCase());
				row.add(mailbagHistoryVO.getScanDate().toDisplayFormat("HH:mm"));
				
				Object oneTimeData = ((ArrayList<Object>)extraInfo).get(0);
				Map<String,Collection<OneTimeVO>> oneTimeDetails = 
					(Map<String,Collection<OneTimeVO>>)oneTimeData;
			
				Collection<OneTimeVO> frequencyonetime =
					(Collection<OneTimeVO>)oneTimeDetails.get(MAIL_STATUS);
				String oprdesc = formatOneTime(mailbagHistoryVO.getMailStatus(),frequencyonetime);
				row.add(oprdesc);
				
				row.add(mailbagHistoryVO.getScannedPort());
				row.add(mailbagHistoryVO.getCarrierCode());
				if(!"-1".equals(mailbagHistoryVO.getFlightNumber())){
					row.add(mailbagHistoryVO.getFlightNumber());
				}
				else{
					row.add("");
				}
				if(mailbagHistoryVO.getFlightDate()!=null){
				    row.add(mailbagHistoryVO.getFlightDate().toDisplayFormat("dd-MMM-yyyy").toUpperCase());
				} else {
					row.add("");
				}	
				row.add(mailbagHistoryVO.getContainerNumber());
				row.add(mailbagHistoryVO.getPou());
				row.add(mailbagHistoryVO.getScanUser());
				row.add(mailbagHistoryVO.getMailbagId());
				tableData.add(row);
			}
		//}
		return tableData;
	}

	public static String formatOneTime(Object value, Collection oneTime) {	
		//System.out.println("Inside formatOneTime!!!!!");
      String code=(String)value;
      String description="";
      Collection<OneTimeVO> oneTimeCollection=(Collection<OneTimeVO>)oneTime;
      if(oneTimeCollection!=null){
    	  if(oneTimeCollection.size()>0){
    		  for(OneTimeVO oneTimeVo:oneTimeCollection){
    			  if(oneTimeVo.getFieldValue().equals(code)){
    				  description=oneTimeVo.getFieldDescription();
    			  }
    		  }
    	  }
      }
      
      return description;
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
		//System.out.println("Inside AttributeBuilder!!!!!!");
		Vector reportParameters = new Vector();		

		
		Object dataParameters = ((ArrayList)parameters).get(0);
		//Modified by a-7871 for ICRD-227460
		Object dataExtraInfo = ((ArrayList)extraInfo).get(1);
		String logonAirportCode=(String)dataExtraInfo;
		MailbagHistoryVO mailbagHistoryVO = 
	    				(MailbagHistoryVO)dataParameters;
	//	String mailBagId =
			//oneTimeVO.getFieldType();
		 String ooe = mailbagHistoryVO.getOriginExchangeOffice();
		    String doe = mailbagHistoryVO.getDestinationExchangeOffice();
		    String catagory = mailbagHistoryVO.getMailCategoryCode();
			String mailSubClass = mailbagHistoryVO.getMailSubclass(); //modified by A-7929 as part of icrd-276109
			String year = String.valueOf(mailbagHistoryVO.getYear());
			String dsn = mailbagHistoryVO.getDsn();
			String rsn = mailbagHistoryVO.getRsn();
			String mailbagId = mailbagHistoryVO.getMailbagId();
			/*String weight = "";;
			if(Integer.parseInt(mailBagId.substring(25,26)) == 0){
				if(Integer.parseInt(mailBagId.substring(26,27)) == 0){
					weight = new StringBuffer(mailBagId.substring(27,28))
									.append(".").append(mailBagId.substring(28))
																		.toString();
				}else{
					weight = new StringBuffer(mailBagId.substring(26,28))
									.append(".").append(mailBagId.substring(28))
																		.toString();
				}
			}else{
				weight = new StringBuffer(mailBagId.substring(25,28))
									.append(".").append(mailBagId.substring(28))
																		.toString();
			}
			*/
			String weight=String.valueOf(mailbagHistoryVO.getWeight().getRoundedDisplayValue()); //modified by A-8164 for ICRD-258939 
			
			String yearPrefix = new LocalDate
					(logonAirportCode,Location.ARP,false).
												toDisplayFormat("yyyy").substring(0,3);	
			//added by A-7929 as part if icrd-276109
			String remarks = mailbagHistoryVO.getMailRemarks();
			String resdit = mailbagHistoryVO.getResiditSend();
			String resditFailReason = mailbagHistoryVO.getResiditFailReasonCode();
			LocalDate reqDeliveryTime = mailbagHistoryVO.getReqDeliveryTime();
			//Added by A-8527 ICRD-324700 Starts
			String reqDeliverytDate = null;
			if (reqDeliveryTime != null) {
				reqDeliverytDate = reqDeliveryTime.toDisplayFormat(false);
			}
			//Added by A-8527 ICRD-324700 Ends
			reportParameters.add(ooe);
			reportParameters.add(doe);
			reportParameters.add(catagory);
			reportParameters.add(mailSubClass);
			reportParameters.add(
					new StringBuffer(yearPrefix).append(year).toString());
			reportParameters.add(dsn);
			reportParameters.add(rsn);
			reportParameters.add(weight);
			reportParameters.add(remarks); //added by A-7929 as part if icrd-276109
			reportParameters.add(reqDeliverytDate); //added by A-7929 as part if icrd-276109
			reportParameters.add(resdit); //added by A-7929 as part if icrd-276109
			reportParameters.add(resditFailReason); //added by A-7929 as part if icrd-276109
			reportParameters.add(mailbagId);
		
	
		
	    return reportParameters;
	}
}

