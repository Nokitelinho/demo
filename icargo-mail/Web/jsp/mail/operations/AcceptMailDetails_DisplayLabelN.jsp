<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name :  MailTracking
* File Name     	 :  AcceptMailDetails_DisplayLabelN.jsp
* Date          	 :  31-MAY-2017

*************************************************************************/
--%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>

<%@ page import="java.util.Set"%>

 <% 
  boolean toDisable = (Boolean)request.getAttribute("toDisable");
  String index = request.getAttribute("index").toString();
  com.ibsplc.icargo.business.mail.operations.vo.MailbagVO mailDetailsVO =
   (com.ibsplc.icargo.business.mail.operations.vo.MailbagVO)request.getAttribute("mailDetailsVO");
%>	
<td class="iCargoTableDataTd">
						<logic:notPresent name="mailDetailsVO" property="ooe">
							<ihtml:text property="mailOOE" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILORIGIN" value="" maxlength="6" readonly="<%=toDisable%>" />
						</logic:notPresent>
						<logic:present name="mailDetailsVO" property="ooe">
							<bean:define id="ooe" name="mailDetailsVO" property="ooe" toScope="page"/>
							<ihtml:text property="mailOOE" value="<%=(String)ooe%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILORIGIN" maxlength="6" readonly="<%=toDisable%>" />
						</logic:present>
						<%if(toDisable){%>
					<div class= "lovImgTbl">
							<img name="mailOOELov valignT" id="mailOOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" disabled>
							</div>
						<%}else{%>
						<div class= "lovImgTbl">
							<img name="mailOOELov valignT" id="mailOOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16">
							</div>
						<%}%>
					</td>
					<td class="iCargoTableDataTd">
						<logic:notPresent name="mailDetailsVO" property="doe">
							<ihtml:text property="mailDOE" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILDESTN" value="" maxlength="8" readonly="<%=toDisable%>" />
						</logic:notPresent>
						<logic:present name="mailDetailsVO" property="doe">
							<bean:define id="doe" name="mailDetailsVO" property="doe" toScope="page"/>
							<ihtml:text property="mailDOE" value="<%=(String)doe%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILDESTN" maxlength="8" readonly="<%=toDisable%>" />
						</logic:present>
						<%if(toDisable){%>
							 <img name="mailDOELov valignT" id="mailDOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" disabled>
						<%}else{%>
							 <img name="mailDOELov valignT" id="mailDOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16">
						<%}%>
					</td>
					<td class="iCargoTableDataTd">
						<% String catValue = ""; %>
						<logic:present name="mailDetailsVO" property="mailCategoryCode">
							<bean:define id="mailCtgyCode" name="mailDetailsVO" property="mailCategoryCode" toScope="page"/>
							<% catValue = (String) mailCtgyCode; %>
						</logic:present>
						<ihtml:select property="mailCat" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_CAT" value="<%=catValue%>" disabled="<%=toDisable%>"  style="width:35px">
							<bean:define id="oneTimeCatSess" name="oneTimeCatSession" toScope="page" />
							<logic:iterate id="oneTimeCatVO" name="oneTimeCatSess" >
								<bean:define id="fieldValue" name="oneTimeCatVO" property="fieldValue" toScope="page" />
								<html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeCatVO" property="fieldValue"/></html:option>
							</logic:iterate>
						</ihtml:select>
					</td>
					<td class="iCargoTableDataTd">
						<logic:notPresent name="mailDetailsVO" property="mailSubclass">
							<ihtml:text property="mailSC" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILSC" value="" maxlength="2" readonly="<%=toDisable%>"  />
						</logic:notPresent>
						<logic:present name="mailDetailsVO" property="mailSubclass">
							<bean:define id="mailSubclass" name="mailDetailsVO" property="mailSubclass" toScope="page"/>
							<ihtml:text property="mailSC" value="<%=(String)mailSubclass%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILSC" maxlength="2" readonly="<%=toDisable%>" />
						</logic:present>
						<%if(toDisable){%>
						<div class= "lovImgTbl">
							 <img name="mailSCLov valignT" id="mailSCLov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" disabled>
							 </div>
						<%}else{%>
						<div class= "lovImgTbl">
							 <img name="mailSCLov valignT" id="mailSCLov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16">
							 </div>
						<%}%>
					</td>
					<td class="iCargoTableDataTd">
						<logic:notPresent name="mailDetailsVO" property="year">
							<ihtml:text property="mailYr" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILYR" value="" maxlength="1" readonly="<%=toDisable%>" />
						</logic:notPresent>
						<logic:present name="mailDetailsVO" property="year">
							<bean:define id="year" name="mailDetailsVO" property="year" toScope="page"/>
							<ihtml:text property="mailYr" value="<%=year.toString()%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILYR" maxlength="1" readonly="<%=toDisable%>" />
						</logic:present>
					</td>
					<td class="iCargoTableDataTd">
						<logic:notPresent name="mailDetailsVO" property="despatchSerialNumber">
							<ihtml:text property="mailDSN" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILDSN" value="" maxlength="4" readonly="<%=toDisable%>" />
						</logic:notPresent>
						<logic:present name="mailDetailsVO" property="despatchSerialNumber">
							<bean:define id="despatchSerialNumber" name="mailDetailsVO" property="despatchSerialNumber" toScope="page"/>
							<ihtml:text property="mailDSN" value="<%=(String)despatchSerialNumber%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILDSN" maxlength="4" readonly="true" />
						</logic:present>
					</td>
					<td class="iCargoTableDataTd">
						<logic:notPresent name="mailDetailsVO" property="receptacleSerialNumber">
							<ihtml:text property="mailRSN" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILRSN" value="" maxlength="3" readonly="<%=toDisable%>" />
						</logic:notPresent>
						<logic:present name="mailDetailsVO" property="receptacleSerialNumber">
							<bean:define id="rsn" name="mailDetailsVO" property="receptacleSerialNumber" toScope="page"/>
							<ihtml:text property="mailRSN" value="<%=(String)rsn%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILRSN" maxlength="3" readonly="<%=toDisable%>" />
						</logic:present>
					</td>
					<td class="iCargoTableDataTd">
						<% String hniValue = ""; %>
						<logic:present name="mailDetailsVO" property="highestNumberedReceptacle">
							<bean:define id="hni" name="mailDetailsVO" property="highestNumberedReceptacle" toScope="page"/>
							<% hniValue = (String) hni;%>
						</logic:present>
						<ihtml:select property="mailHNI" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_HNI" value="<%=hniValue%>" disabled="<%=toDisable%>"  style="width:35px">
							<bean:define id="oneTimeHNISess" name="oneTimeHNISession" toScope="page" />
							<logic:iterate id="oneTimeHNIVO" name="oneTimeHNISess" >
								<bean:define id="fieldValue" name="oneTimeHNIVO" property="fieldValue" toScope="page" />
								 <html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeHNIVO" property="fieldValue"/></html:option>
							</logic:iterate>
						</ihtml:select>
					</td>
					<td class="iCargoTableDataTd">
						<% String riValue = ""; %>
						<logic:present name="mailDetailsVO" property="registeredOrInsuredIndicator">
							<bean:define id="ri" name="mailDetailsVO" property="registeredOrInsuredIndicator" toScope="page"/>
							<% riValue = (String) ri; %>
						</logic:present>
						<ihtml:select property="mailRI" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_RI" value="<%=riValue%>" disabled="<%=toDisable%>"  style="width:35px">
							<bean:define id="oneTimeRISess" name="oneTimeRISession" toScope="page" />
							<logic:iterate id="oneTimeRIVO" name="oneTimeRISess" >
								  <bean:define id="fldValue" name="oneTimeRIVO" property="fieldValue" toScope="page" />
								 <html:option value="<%=(String)fldValue %>"><bean:write name="oneTimeRIVO" property="fieldValue"/></html:option>
							</logic:iterate>
						</ihtml:select>
					</td>
					<td class="iCargoTableDataTd">
						<logic:notPresent name="mailDetailsVO" property="strWeight">
			             <bean:define id="revGrossWeightID" name="mailDetailsVO" property="strWeight" type="com.ibsplc.icargo.framework.util.unit.Measure" />
																						<% request.setAttribute("mailWt",revGrossWeightID); %>
						<ibusiness:unitdef id="mailWt" unitTxtName="mailWt" label="" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILWT"  unitReq = "false" dataName="mailWt"
																							 title="Revised Gross Weight"
																							unitValue="0.0" style="text-align:right"
																							indexId="index" styleId="mailWt" />
							<!--<ihtml:text property="mailWt" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILWT" value="" readonly="<%=toDisable%>"  maxlength="4"  />-->
								
						</logic:notPresent>
						<logic:present name="mailDetailsVO" property="strWeight">
						
						<bean:define id="revGrossWeightID" name="mailDetailsVO" property="strWeight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/><!--modified by A-7371-->
						<% request.setAttribute("mailWt",revGrossWeightID); %>
																									<ibusiness:unitdef id="mailWt" unitTxtName="mailWt" label="" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILWT"  unitReq = "false" dataName="mailWt"
																									 title="Revised Gross Weight"
						unitValue="<%=String.valueOf(revGrossWeightID.getDisplayValue())%>" style="text-align:right"
																									indexId="index" styleId="mailWt"/>
						
						
						
						
						
							<!--<bean:define id="weight" name="mailDetailsVO" property="strWeight" toScope="page" />
							<ihtml:text property="mailWt" value="<%=(String)weight%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILWT" readonly="<%=toDisable%>" maxlength="4" />-->
						</logic:present>
					</td>
					<td class="iCargoTableDataTd">
						<logic:notPresent name="mailDetailsVO" property="volume">
							
								<bean:define id="sampleStdVolVo" name="mailDetailsVO" property="volume" type="com.ibsplc.icargo.framework.util.unit.Measure"/>
								<% request.setAttribute("sampleStdVol",sampleStdVolVo); %>
								<ibusiness:unitdef id="mailVolume" unitTxtName="mailVolume" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILVOLUME" label=""  unitReq = "false" dataName="sampleStdVol"
									unitValueStyle="iCargoEditableTextFieldRowColor1" title="Stated Volume"
									unitValue="0.0" style="background :'<%=color%>'"
									indexId="index" styleId="stdVolume" readonly="<%=toDisable%>" />
						
							<%--
							<ihtml:text property="mailVolume" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILVOLUME" value=""  readonly="true" maxlength="6"  />
							--%>
						</logic:notPresent>
						<logic:present name="mailDetailsVO" property="volume">
							<bean:define id="volume" name="mailDetailsVO" property="volume" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/>
									<% request.setAttribute("sampleStdVol",volume); %>
									<ibusiness:unitdef id="mailVolume" unitTxtName="mailVolume" label="" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILVOLUME"  unitReq = "false" dataName="sampleStdVol"
										unitValueStyle="iCargoEditableTextFieldRowColor1" title="Stated Volume"
										unitValue="<%=volume.toString()%>" style="background :'<%=color%>'"
										indexId="index" styleId="stdVolume" readonly="true" />
							
							<%--
							<ihtml:text property="mailVolume" value="<%=volume.toString()%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILVOLUME" readonly="<%=toDisable%>" maxlength="6" />
							--%>
						</logic:present>
					</td>
					<td class="iCargoTableDataTd">
						<logic:notPresent name="mailDetailsVO" property="scannedDate">
							<ibusiness:calendar property="mailScanDate" id="mailScanDate" indexId="index" type="image" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_SCANDATE" value="" />
						</logic:notPresent>
						<logic:present name="mailDetailsVO" property="scannedDate">
						<bean:define id="scannedDate" name="mailDetailsVO" property="scannedDate" toScope="page"/>
						<%String scanDate=TimeConvertor.toStringFormat(((LocalDate)scannedDate).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
							<ibusiness:calendar property="mailScanDate"  indexId="index" id="mailScanDate" value="<%=(String)scanDate%>" type="image" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_SCANDATE"/>
						</logic:present>
					</td>
					<td class="iCargoTableDataTd">
					   	<logic:notPresent name="mailDetailsVO" property="scannedDate">
							<ibusiness:releasetimer property="mailScanTime" indexId="index" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_SCANTIME" id="scanTime"  type="asTimeComponent" value=""/>
					   	</logic:notPresent>
					   	<logic:present name="mailDetailsVO" property="scannedDate">
					    		<bean:define id="scannedDate" name="mailDetailsVO" property="scannedDate" toScope="page"/>
					      		<%String scanTime=TimeConvertor.toStringFormat(((LocalDate)scannedDate).toCalendar(),"HH:mm");%>
					      		<ibusiness:releasetimer property="mailScanTime" indexId="index" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_SCANTIME" id="scanTime"  type="asTimeComponent" value="<%=(String)scanTime%>"/>
					   	</logic:present>
					</td>
					<td class="iCargoTableDataTd">
					   	<logic:notPresent name="mailDetailsVO" property="transferFromCarrier">
							<ihtml:text property="mailCarrier" indexId="index" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILCARRIER" value="" maxlength="3" style="width:40px"/>
					   	</logic:notPresent>
					   	<logic:present name="mailDetailsVO" property="transferFromCarrier">
							<bean:define id="transferCarrier" name="mailDetailsVO" property="transferFromCarrier" toScope="page"/>
							<ihtml:text property="mailCarrier" indexId="index" value="<%=(String)transferCarrier%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_MAILCARRIER" maxlength="3" style="width:40px"/>
					   	</logic:present>
						<div class= "lovImgTbl valignT">
					   	<img name="mailCarrierLov" id="mailCarrierLov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16">
						</div>
					</td>