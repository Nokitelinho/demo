/*
 * MailProrationExceptionsAttributeBuilder.java Created on Sep, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.mail.mra.defaults;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Vector;


import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationExceptionsFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationExceptionVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;

import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;


/**
 * @author A-3108
 *
 */
public class MailProrationExceptionsAttributeBuilder extends AttributeBuilderAdapter {

private static final String CLASS_NAME 
	= "ProrationExceptionsAttributeBuilder";
private static final String BLANKSPACE ="";

private Log log = LogFactory.getLogger("ProrationExceptions");

private static final String KEY_EXCEPTION = "mra.proration.exceptions";
private static final String KEY_STATUS = "mra.proration.exceptionstatus";
private static final String KEY_TRIGGERPOINT = "mailtracking.mra.proration.triggerpoint";
private static final String KEY_ASSIGNED_STATUS="mra.proration.assignedstatus";


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
columns.add("DISPATCHNUM");
columns.add("EXPCOD");
columns.add("SEGORG");
columns.add("SEGDST");
columns.add("EXPDAT");
columns.add("TRIGERPNT");
columns.add("PROFCT");
columns.add("FLTNUM");
columns.add("FLTDAT");
columns.add("ROUTE");
columns.add("CSGDOCNUM");
columns.add("PCS");
columns.add("ASDUSR");
columns.add("ASGDAT");
columns.add("RSDDAT");
columns.add("EXPSTA");

return columns;

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
@Override
public Vector<Object> getReportParameters(Collection parameters,
Collection extraInfo) {
log.entering("ProrationExceptionsAttributeBuilder", "getReportParameters");

Vector<Object> reportParameters = new Vector<Object>();

Object dataParameters = ((ArrayList<Object>) parameters).get(0);

ProrationExceptionsFilterVO prorationFilterVO = (ProrationExceptionsFilterVO) dataParameters;
log.log(Log.FINE, "printing the report parameter=====> ", prorationFilterVO);
// retreive tthe one time values from the extra Info
Map<String, Collection<OneTimeVO>> oneTimeDetails = null;
Collection<OneTimeVO> exception = null;
Collection<OneTimeVO> status = null;
Collection<OneTimeVO> assignedStatus=null;

if (extraInfo != null && extraInfo.size() > 0) {
Object onetimes = ((ArrayList<Object>) extraInfo).get(0);
oneTimeDetails = (Map<String, Collection<OneTimeVO>>) onetimes;
exception = oneTimeDetails.get(KEY_EXCEPTION);
status = oneTimeDetails.get(KEY_STATUS);
assignedStatus=oneTimeDetails.get(KEY_ASSIGNED_STATUS);

}
log.log(Log.FINE, "==printing the exception==>\n\n ", exception);
log.log(Log.FINE, "==printing the status==>\n\n ", status);
log.log(Log.FINE, "==printing the assignedstatus==>\n\n ", assignedStatus);
// comparing Exception
if (prorationFilterVO.getExceptionCode() != null
&& prorationFilterVO.getExceptionCode().trim().length() > 0) {
String exceptionCode = prorationFilterVO.getExceptionCode();
log.log(Log.FINE, "==printing the exceptioncode==>\n\n ", exceptionCode);
if (exception != null && exception.size() > 0) {
for (OneTimeVO oneTimeVo : exception) {
if (oneTimeVo.getFieldValue().equals(exceptionCode)) {
reportParameters.add(oneTimeVo.getFieldDescription());
}
}
} 
} else {
reportParameters.add(BLANKSPACE);
}
log.log(Log.FINE, "==printing the exception1", reportParameters);
//comparing flight number
if (prorationFilterVO.getFlightNumber() != null
&& prorationFilterVO.getFlightNumber().trim().length() > 0) {
String flightNumber = prorationFilterVO.getFlightNumber();
String carrierCode=prorationFilterVO.getCarrierCode();
String fltnumber=carrierCode.concat(flightNumber);
reportParameters.add(fltnumber);
}

else {
reportParameters.add(BLANKSPACE);
}
log.log(Log.FINE, "==printing the exception2", reportParameters);
//flight date
if (prorationFilterVO.getFlightDate() != null) {

	String flightTime=TimeConvertor.toStringFormat(
			prorationFilterVO.getFlightDate().toCalendar(),
			"dd-MMM-yyyy");
reportParameters.add(flightTime);
} else {
reportParameters.add(BLANKSPACE);
}
log.log(Log.FINE, "==printing the exception3", reportParameters);
//origin
if (prorationFilterVO.getSegmentOrigin() != null
&& prorationFilterVO.getSegmentOrigin().trim().length() > 0) {
String origin = prorationFilterVO.getSegmentOrigin();
reportParameters.add(origin);

} else {
reportParameters.add(BLANKSPACE);
}
log.log(Log.FINE, "==printing the exception4", reportParameters);
//destination
if (prorationFilterVO.getSegmentDestination() != null
&& prorationFilterVO.getSegmentDestination().trim().length() > 0) {
String destination = prorationFilterVO.getSegmentDestination();
reportParameters.add(destination);

} else {
reportParameters.add(BLANKSPACE);
}
log.log(Log.FINE, "==printing the exception5", reportParameters);
//
if (prorationFilterVO.getDispatchNo()!= null
&& prorationFilterVO.getDispatchNo().trim().length() > 0) {


reportParameters.add(prorationFilterVO.getDispatchNo());

} else {
reportParameters.add(BLANKSPACE);
}
log.log(Log.FINE, "==printing the exception6", reportParameters);
//status
if (prorationFilterVO.getStatus() != null
&& prorationFilterVO.getStatus().trim().length() > 0) {
String statusFilter = prorationFilterVO.getStatus();

if (status != null && status.size() > 0) {
	for (OneTimeVO oneTimeVo : status) {
		if (oneTimeVo.getFieldValue().equals(statusFilter)) {
reportParameters.add(oneTimeVo.getFieldDescription());
}
}
} 

} else {
reportParameters.add(BLANKSPACE);
}
log.log(Log.FINE, "==printing the exception3", reportParameters);
//assignedStatus
if (prorationFilterVO.getAssignedStatus() != null
&& prorationFilterVO.getAssignedStatus().trim().length() > 0) {
String assignedStatusFilter = prorationFilterVO.getAssignedStatus();

if (assignedStatus != null && assignedStatus.size() > 0) {
	for (OneTimeVO oneTimeVo : assignedStatus) {
		if (oneTimeVo.getFieldValue().equals(assignedStatusFilter)) {
reportParameters.add(oneTimeVo.getFieldDescription());
}
}
} 
} else {
reportParameters.add(BLANKSPACE);
}
log.log(Log.FINE, "==printing the exception8", reportParameters);
if (prorationFilterVO.getAsignee()!= null
		&& prorationFilterVO.getAsignee().trim().length() > 0) {


		reportParameters.add(prorationFilterVO.getAsignee());

		} else {
		reportParameters.add(BLANKSPACE);
}
log.log(Log.FINE, "==printing the exception9", reportParameters);
//From date
if (prorationFilterVO.getFromDate() != null) {

	String fromTime=TimeConvertor.toStringFormat(
			prorationFilterVO.getFromDate().toCalendar(),
			"dd-MMM-yyyy");
reportParameters.add(fromTime);
log.log(Log.FINE, "FROM DATE ", fromTime);
} else {
reportParameters.add(BLANKSPACE);
}
log.log(Log.FINE, "==printing the exception10", reportParameters);
// to date
if (prorationFilterVO.getToDate() != null) {

	String toTime=TimeConvertor.toStringFormat(
			prorationFilterVO.getToDate().toCalendar(),
			"dd-MMM-yyyy");
reportParameters.add(toTime);
log.log(Log.FINE, "TO DATE ", toTime);
} else {
reportParameters.add(BLANKSPACE);
}
log.log(Log.FINE, "==printing the exception11", reportParameters);
log.log(Log.FINE, "ReportpaRAMETESR===> ", reportParameters);
log.exiting("ProrationExceptionsAttributeBuilder", "getReportParameters");
return reportParameters;
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
public Vector<Vector> getReportData(Collection data, Collection extraInfo) {
log.entering("ProrationExceptionsAttributeBuilder", "getReportData");
Vector<Vector> tableData = new Vector<Vector>();
log.log(Log.FINE, "Size of collection is ===> ", data.size());
// retrieive tthe one time values from the extra Info
Map<String, Collection<OneTimeVO>> oneTimeDetails = null;
Collection<OneTimeVO> exception = null;
Collection<OneTimeVO> status = null;
Collection<OneTimeVO> triggerpointCol =null;
	if (extraInfo != null && extraInfo.size() > 0) {
		log.log(Log.FINE, "extraInfo in attribute builder", extraInfo);
		Object onetimes = ((ArrayList<Object>) extraInfo).get(0);
		oneTimeDetails = (Map<String, Collection<OneTimeVO>>) onetimes;
		exception = oneTimeDetails.get(KEY_EXCEPTION);
		status = oneTimeDetails.get(KEY_STATUS);
		triggerpointCol = oneTimeDetails.get("mailtracking.mra.proration.triggerpoint");
	}
log.log(Log.FINE, "==printing the exception==>\n\n ", exception);
log.log(Log.FINE, "==printing the status==>\n\n ", status);
log.log(Log.FINE, "==printing the trigger pionts==>\n\n ", triggerpointCol);
if (data != null) {
	
for (ProrationExceptionVO prorationVO : ((Collection<ProrationExceptionVO>) data)) {
Vector<Object> row = new Vector<Object>();
log.log(Log.FINE, "data in attribute builder", data);

if (prorationVO.getMailbagId()!= null) {
row.add(prorationVO.getMailbagId());

}else if(prorationVO.getDispatchNo()!= null){
	row.add(prorationVO.getDispatchNo());
}else {
row.add(BLANKSPACE);
}
// comparing Exception
if (prorationVO.getExceptionCode() != null
&& prorationVO.getExceptionCode().trim().length() > 0) {
	for(OneTimeVO oneTimeVO : exception){
		if(oneTimeVO.getFieldValue().equals(prorationVO.getExceptionCode())){
			row.add(oneTimeVO.getFieldDescription());
				}
		}	
	} else {
	row.add(BLANKSPACE);
	}

if (prorationVO.getSegmentOrigin() != null) {
row.add(prorationVO.getSegmentOrigin());
} else {
row.add(BLANKSPACE);
}
if (prorationVO.getSegmentDestination() != null) {
row.add(prorationVO.getSegmentDestination());
} else {
row.add(BLANKSPACE);
}
if (prorationVO.getTriggerPoint() != null
		&& prorationVO.getTriggerPoint().trim().length() > 0) {
		boolean isCodePresent = false;
			for(OneTimeVO oneTimeVO : triggerpointCol){
				if(oneTimeVO.getFieldValue().equals(prorationVO.getTriggerPoint())){
					row.add(oneTimeVO.getFieldDescription());
					isCodePresent=true;
				}
			}
			if(!isCodePresent)
				{
				row.add(BLANKSPACE);
				}
		}
		else {
		row.add(BLANKSPACE);
		}
if (prorationVO.getDate() != null) {
row.add(prorationVO.getDate().toDisplayDateOnlyFormat());
} else {
row.add(BLANKSPACE);
}


if (prorationVO.getProrateFactor() != 0) {
	row.add(prorationVO.getProrateFactor());
	} else {
	row.add(BLANKSPACE);
	}

if (prorationVO.getFlightNumber() != null) {
	
	String fltNumber=prorationVO.getFlightNumber();
	String carrierCode=prorationVO.getCarrierCode();
	String fltnumber=carrierCode.concat(fltNumber);
	row.add(fltnumber);
} else {
row.add(BLANKSPACE);
}
if (prorationVO.getFlightDate() != null) {
row.add(prorationVO.getFlightDate().toDisplayDateOnlyFormat());
} else {
row.add(BLANKSPACE);
}
if (prorationVO.getRoute() != null) {
row.add(prorationVO.getRoute());
} else {
row.add(BLANKSPACE);
}
if (prorationVO.getConsDocNo() != null) {
row.add(prorationVO.getConsDocNo());
} else {
row.add(BLANKSPACE);
}
if (prorationVO.getNoOfBags() !=0) {
	row.add(String.valueOf(prorationVO.getNoOfBags()));
	} else {
	row.add(BLANKSPACE);
	}
if (prorationVO.getAssignedUser() != null) {
row.add(prorationVO.getAssignedUser());
} else {
row.add(BLANKSPACE);
}
if (prorationVO.getAssignedTime() != null) {
row.add(prorationVO.getAssignedTime().toDisplayDateOnlyFormat());
} else {
row.add(BLANKSPACE);
}
if (prorationVO.getResolvedTime() != null) {
row.add(prorationVO.getResolvedTime().toDisplayDateOnlyFormat());
} else {
row.add(BLANKSPACE);
}
// status
if (prorationVO.getStatus() != null
&& prorationVO.getStatus().trim().length() > 0) {
	for(OneTimeVO oneTimeVO : status){
		if(oneTimeVO.getFieldValue().equals(prorationVO.getStatus())){
			row.add(oneTimeVO.getFieldDescription());
		}
}	
} else {
row.add(BLANKSPACE);
}


tableData.add(row);
}
} else {
Vector<Object> emptyRow = new Vector<Object>();
emptyRow.add(BLANKSPACE);
emptyRow.add(BLANKSPACE);
emptyRow.add(BLANKSPACE);
emptyRow.add(BLANKSPACE);
emptyRow.add(BLANKSPACE);
emptyRow.add(BLANKSPACE);
emptyRow.add(BLANKSPACE);
emptyRow.add(BLANKSPACE);
emptyRow.add(BLANKSPACE);
emptyRow.add(BLANKSPACE);
emptyRow.add(BLANKSPACE);
emptyRow.add(BLANKSPACE);
emptyRow.add(BLANKSPACE);
emptyRow.add(BLANKSPACE);
emptyRow.add(BLANKSPACE);
emptyRow.add(BLANKSPACE);
tableData.add(emptyRow);
}
log.exiting("ProrationExceptionsAttributeBuilder", "getReportData");
return tableData;
}


}
