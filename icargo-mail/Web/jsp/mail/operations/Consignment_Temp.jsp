<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name 	 :  MailTracking
* File Name     	 :  Consignment_Temp.jsp
* Date          	 :  15-September-2006,27-Oct-2015
* Author(s)     	 :  Roopak V.S., Ananda Raj.R

*************************************************************************/
 --%>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm"%>
<%@ page import="com.ibsplc.xibase.server.framework.persistence.query.Page"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<%@ include file="/jsp/includes/tlds.jsp" %>

<bean:define id="ConsignmentForm" name="ConsignmentForm" type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm" toScope="page" scope="request"/>
<business:sessionBean id="conDocVOSession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="consignmentDocumentVO" />
<business:sessionBean id="oneTimeCatSession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="oneTimeCat" />
<business:sessionBean id="oneTimeRISession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="oneTimeRSN" />
<business:sessionBean id="oneTimeHNISession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="oneTimeHNI" />
<business:sessionBean id="oneTimeMailClassSession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="oneTimeMailClass" />
<business:sessionBean id="oneTimeTypeSession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="oneTimeType" />



<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>

<html>
	<body>
		<div class="ic-content-main">
			<div class="ic-main-container">
			  
			  <ihtml:form action="mailtracking.defaults.consignment.screenloadconsignment.do">

				<ihtml:hidden property="disableListSuccess" />
				<ihtml:hidden property="tableFocus" />
				<ihtml:hidden property="currentDialogOption" />
				<ihtml:hidden property="currentDialogId" />
			    <div id="_route">
				<div class="ic-row">
					<div class="tableContainer"> 
						<table class="fixed-header-table">
							<thead>
								<tr>
									   <td><input type="checkbox" name="masterRoute" onclick="updateHeaderCheckBox(this.form,this,this.form.selectRoute);"/></td>
									   <td><common:message key="mailtracking.defaults.consignment.lbl.flightno" /></td>
									   <td><common:message key="mailtracking.defaults.consignment.lbl.depdate" /></td>
									   <td><common:message key="mailtracking.defaults.consignment.lbl.pol" /></td>
									   <td><common:message key="mailtracking.defaults.consignment.lbl.pou" /></td>
								</tr>
							</thead>
							<tbody>
								 <%
									 int tabNum = 7;
								%>
							  <logic:present name="conDocVOSession" property="routingInConsignmentVOs">
								<bean:define id="routingInConsignmentVOs" name="conDocVOSession" property="routingInConsignmentVOs" scope="page" toScope="page"/>
								  <% int row = ((Collection)routingInConsignmentVOs).size();%>
							        <logic:iterate id="routingInConsignmentVO" name="routingInConsignmentVOs" indexId="index">
							          <% tabNum = tabNum + row * index; %>
							            <logic:notEqual name="routingInConsignmentVO" property="operationFlag" value="D">
								<tr>
									 <td>
										  <input type="checkbox" name="selectRoute" value="<%=String.valueOf(index)%>" onclick="toggleTableHeaderCheckbox(this,this.form.masterRoute);" />
									  </td>
									  <td>
											<logic:notPresent name="routingInConsignmentVO" property="onwardCarrierCode">
											<ihtml:text property="flightCarrierCode" componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGNMENT_CARRIERCODE" maxlength="3" value="" />
											</logic:notPresent>
											<logic:present name="routingInConsignmentVO" property="onwardCarrierCode">
												<bean:define id="carrierCode" name="routingInConsignmentVO" property="onwardCarrierCode" toScope="page"/>
												<ihtml:text property="flightCarrierCode" componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGNMENT_CARRIERCODE" maxlength="3" value="<%=(String)carrierCode%>" />
											</logic:present>
									  </td>
									 <td>
											<logic:notPresent name="routingInConsignmentVO" property="onwardFlightNumber">
												   <ibusiness:flightnumber id="fltNo" carrierCodeProperty="flightCarrierCode"  flightCodeProperty="flightNumber" carriercodevalue="" flightcodevalue=""  componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGNMENT_FLIGHTNO"/>
											</logic:notPresent>

											<logic:present name="routingInConsignmentVO" property="onwardCarrierCode">
											<logic:present name="routingInConsignmentVO" property="onwardFlightNumber">
													<bean:define id="carrierCode" name="routingInConsignmentVO" property="onwardCarrierCode" toScope="page"/>
													<bean:define id="flightNo" name="routingInConsignmentVO" property="onwardFlightNumber" toScope="page"/>
													<ibusiness:flightnumber id="fltNo" carrierCodeProperty="flightCarrierCode"  flightCodeProperty="flightNumber" carriercodevalue="<%=(String)carrierCode%>" flightcodevalue="<%=(String)flightNo%>"  componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGNMENT_FLIGHTNO"/>
											</logic:present>
											</logic:present>
									  </td>
									  <td>
											<logic:notPresent name="routingInConsignmentVO" property="pol">
											<ihtml:text property="pol" componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGNMENT_POL" maxlength="4" value="" />
											</logic:notPresent>
											<logic:present name="routingInConsignmentVO" property="pol">
											<bean:define id="pol" name="routingInConsignmentVO" property="pol" toScope="page"/>
											<ihtml:text property="pol" componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGNMENT_POL" maxlength="4" value="<%=(String)pol%>" />
											</logic:present>
											<img src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.pol.value,'Airport','1','pol','',<%=(String.valueOf(index))%>)">
									</td>
									  <td>
										<logic:notPresent name="routingInConsignmentVO" property="pou">
										<ihtml:text property="pou" componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGNMENT_POU" maxlength="4" value="" />
										</logic:notPresent>
										<logic:present name="routingInConsignmentVO" property="pou">
										<bean:define id="pou" name="routingInConsignmentVO" property="pou" toScope="page"/>
										<ihtml:text property="pou" componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGNMENT_POU" maxlength="4" value="<%=(String)pou%>" />
										</logic:present>
										<img src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.pou.value,'Airport','1','pou','',<%=(String.valueOf(index))%>)">
									  </td>
								</tr>
										</logic:notEqual>
									</logic:iterate>
								</logic:present>
							</tbody>
						</table>
					</div>
				</div>
				</div>
				<div id="_mail">
				<div class="ic-row">
					<div class="tableContainer"> 
						<table class="fixed-header-table">
							<thead>
								<tr>
									<td><input name="masterMail" type="checkbox" onclick="updateHeaderCheckBox(this.form,this,this.form.selectMail);" /></td>
									<td><common:message key="mailtracking.defaults.consignment.lbl.ooe"/></td>
									<td><common:message key="mailtracking.defaults.consignment.lbl.doe"/></td>
									<td><common:message key="mailtracking.defaults.consignment.lbl.cat"/></td>
									<td><common:message key="mailtracking.defaults.consignment.lbl.class"/></td>
									<td><common:message key="mailtracking.defaults.consignment.lbl.sc"/></td>
									<td><common:message key="mailtracking.defaults.consignment.lbl.yr"/></td>
									<td><common:message key="mailtracking.defaults.consignment.lbl.dsn"/></td>
									<td><common:message key="mailtracking.defaults.consignment.lbl.rsn"/></td>
									<td><common:message key="mailtracking.defaults.consignment.lbl.numbags"/></td>
									<td><common:message key="mailtracking.defaults.consignment.lbl.hni"/></td>
									<td><common:message key="mailtracking.defaults.consignment.lbl.ri"/></td>
									<td><common:message key="mailtracking.defaults.consignment.lbl.wt"/></td>
									<td><common:message key="mailtracking.defaults.consignment.lbl.uldnum"/></td>
								</tr>
							</thead>
							<tbody>
							
								 <logic:present name="conDocVOSession" property="mailInConsignmentVOs">
									<bean:define id="mailInConsignmentVOs" name="conDocVOSession" property="mailInConsignmentVOs" scope="page" toScope="page"/>
									<% int row = ((Collection)mailInConsignmentVOs).size();%>
										<logic:iterate id="mailInConsignmentVO" name="mailInConsignmentVOs" indexId="index">
										<% tabNum = tabNum + row * index; %>
										<%boolean toDisable = false;%>
										<%boolean toDisableWt = false;%>
										<logic:notEqual name="mailInConsignmentVO" property="operationFlag" value="D">
										<logic:notEqual name="mailInConsignmentVO" property="operationFlag" value="I">
											<% toDisable = true;%>
										</logic:notEqual>
										<logic:present name="mailInConsignmentVO" property="receptacleSerialNumber">
										<%
											if(toDisable){
												toDisableWt = true;
											}
										%>
										</logic:present>
								<tr>
								<td>
											<input name="selectMail" type="checkbox" value="<%=String.valueOf(index)%>" />
								</td>
								<td>
									<logic:notPresent name="mailInConsignmentVO" property="originExchangeOffice">
									<ihtml:text property="originOE" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_OOE" value="" maxlength="6"  readonly="<%=toDisable%>"/>
									</logic:notPresent>
									<logic:present name="mailInConsignmentVO" property="originExchangeOffice">
									<bean:define id="ooe" name="mailInConsignmentVO" property="originExchangeOffice" toScope="page"/>
									<ihtml:text property="originOE" value="<%=(String)ooe%>" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_OOE" maxlength="6"  readonly="<%=toDisable%>"/>
									</logic:present>
									<%if(toDisable){%>
									<img id="OOELov" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" onClick="displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.originOE.value,'OfficeOfExchange','1','originOE','',<%=(String.valueOf(index))%>)" disabled>
									<%}else{%>
									 <img id="OOELov" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" onClick="displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.originOE.value,'OfficeOfExchange','1','originOE','',<%=(String.valueOf(index))%>)" >
									<%}%>
								</td>
								<td>
									<logic:notPresent name="mailInConsignmentVO" property="destinationExchangeOffice">
									<ihtml:text property="destinationOE" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_DOE" value="" maxlength="6"  readonly="<%=toDisable%>"/>
									</logic:notPresent>
									<logic:present name="mailInConsignmentVO" property="destinationExchangeOffice">
									<bean:define id="doe" name="mailInConsignmentVO" property="destinationExchangeOffice" toScope="page"/>
									<ihtml:text property="destinationOE" value="<%=(String)doe%>" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_DOE" maxlength="6"  readonly="<%=toDisable%>"/>
									</logic:present>
									<%if(toDisable){%>
									<img id="DOELov" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" onClick="displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.destinationOE.value,'OfficeOfExchange','1','destinationOE','',<%=(String.valueOf(index))%>)" disabled>
									<%}else{%>
									 <img id="DOELov" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" onClick="displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.destinationOE.value,'OfficeOfExchange','1','destinationOE','',<%=(String.valueOf(index))%>)" >
									<%}%>
								</td>
								<td>
									<% String catValue = ""; %>
									<logic:present name="mailInConsignmentVO" property="mailCategoryCode">
									<bean:define id="mailCatCode" name="mailInConsignmentVO" property="mailCategoryCode" toScope="page"/>
									<% catValue = (String) mailCatCode; %>
									</logic:present>
									<ihtml:select property="category" componentID="CMB_MAILTRACKING_DEFAULTS_CONSIGNMENT_CAT" value="<%=catValue%>" style="width:35px"  disabled="<%=toDisable%>">
									<bean:define id="oneTimeCatSess" name="oneTimeCatSession" toScope="page" />
									<logic:iterate id="oneTimeCatVO" name="oneTimeCatSess" >
									  <bean:define id="fieldValue" name="oneTimeCatVO" property="fieldValue" toScope="page" />
										 <html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeCatVO" property="fieldValue"/></html:option>
									</logic:iterate>
									</ihtml:select>
								</td>
								<td>
									<% String classValue = ""; %>
									<logic:present name="mailInConsignmentVO" property="mailClass">
									<bean:define id="mailClass" name="mailInConsignmentVO" property="mailClass" toScope="page"/>
									<% classValue = (String) mailClass; %>
									</logic:present>
									<ihtml:select property="mailClass" componentID="CMB_MAILTRACKING_DEFAULTS_CONSIGNMENT_CLASS" value="<%=classValue%>" style="width:35px"  disabled="<%=toDisable%>">
									<bean:define id="oneTimeMailClassSess" name="oneTimeMailClassSession" toScope="page" />
									<logic:iterate id="oneTimeMailClassVO" name="oneTimeMailClassSess" >
									  <bean:define id="fieldValue" name="oneTimeMailClassVO" property="fieldValue" toScope="page" />
										 <html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeMailClassVO" property="fieldValue"/></html:option>
									</logic:iterate>
									</ihtml:select>
								</td>
								<td>

								<% String subclassValue = ""; %>
											<logic:notPresent name="mailInConsignmentVO" property="mailSubclass">
									<ihtml:text property="subClass" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_SC" value="" maxlength="2"  readonly="<%=toDisable%>"/>
									</logic:notPresent>
									<logic:present name="mailInConsignmentVO" property="mailSubclass">
									<bean:define id="mailSubclass" name="mailInConsignmentVO" property="mailSubclass" toScope="page"/>
									<% subclassValue = (String) mailSubclass;
									   int arrays=subclassValue.indexOf("_");
									   if(arrays==-1){%>
										<ihtml:text property="subClass" value="<%=(String)mailSubclass%>" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_SC" maxlength="2"  readonly="<%=toDisable%>"/>
									   <%}else{%>
										<ihtml:text property="subClass" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_SC" value="" maxlength="2" readonly="<%=toDisable%>"  />
									   <%}%>
										</logic:present>
										<%if(toDisable){%>
									 <img id="SCLov" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" onClick="displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.subClass.value,'OfficeOfExchange','1','subClass','',<%=(String.valueOf(index))%>)" disabled>
									<%}else{%>
									 <img id="SCLov" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" onClick="displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.subClass.value,'OfficeOfExchange','1','subClass','',<%=(String.valueOf(index))%>)">
									<%}%>
								</td>
								<td>
									<logic:notPresent name="mailInConsignmentVO" property="year">
									<ihtml:text property="year" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_YR" value="" maxlength="1" style="width:20px"  readonly="<%=toDisable%>"/>
									</logic:notPresent>
									<logic:present name="mailInConsignmentVO" property="year">
									<bean:define id="year" name="mailInConsignmentVO" property="year" toScope="page"/>
									<ihtml:text property="year" value="<%=year.toString()%>" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_YR" maxlength="1" style="width:20px"  readonly="<%=toDisable%>"/>
									</logic:present>
								</td>
								<td>
									<logic:notPresent name="mailInConsignmentVO" property="dsn">
									<ihtml:text property="dsn" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_DSN" value="" maxlength="4"  readonly="<%=toDisable%>"/>
									</logic:notPresent>
									<logic:present name="mailInConsignmentVO" property="dsn">
									<bean:define id="dsn" name="mailInConsignmentVO" property="dsn" toScope="page"/>
									<ihtml:text property="dsn" value="<%=(String)dsn%>" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_DSN" maxlength="4"  readonly="<%=toDisable%>"/>
									</logic:present>
								</td>
								<td>
									<logic:notPresent name="mailInConsignmentVO" property="receptacleSerialNumber">
									<ihtml:text property="rsn" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_RSN" value="" maxlength="3"  readonly="<%=toDisable%>"/>
									</logic:notPresent>
									<logic:present name="mailInConsignmentVO" property="receptacleSerialNumber">
									<bean:define id="rsn" name="mailInConsignmentVO" property="receptacleSerialNumber" toScope="page"/>
									<ihtml:text property="rsn" value="<%=(String)rsn%>" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_RSN" maxlength="3"  readonly="<%=toDisable%>"/>
									</logic:present>
								</td>
								<td>
									<logic:notPresent name="mailInConsignmentVO" property="statedBags">
									<ihtml:text property="numBags" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_NUMBAG" value="" maxlength="5"  readonly="<%=toDisableWt%>"/>
									</logic:notPresent>
									<logic:present name="mailInConsignmentVO" property="statedBags">
									<bean:define id="statedBags" name="mailInConsignmentVO" property="statedBags" toScope="page"/>
									<ihtml:text property="numBags" value="<%=statedBags.toString()%>" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_NUMBAG"  readonly="<%=toDisableWt%>" maxlength="5"/>
									</logic:present>
								</td>
								<td>
									<% String hniValue = ""; %>
									<logic:present name="mailInConsignmentVO" property="highestNumberedReceptacle">
									<bean:define id="hni" name="mailInConsignmentVO" property="highestNumberedReceptacle" toScope="page"/>
									<% hniValue = (String) hni; %>
									</logic:present>
									<ihtml:select property="mailHI" componentID="CMB_MAILTRACKING_DEFAULTS_CONSIGNMENT_HNI" value="<%=hniValue%>" style="width:35px"  disabled="<%=toDisable%>">
									<bean:define id="oneTimeHNISess" name="oneTimeHNISession" toScope="page" />
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									<logic:iterate id="oneTimeHNIVO" name="oneTimeHNISess" >
									  <bean:define id="fieldValue" name="oneTimeHNIVO" property="fieldValue" toScope="page" />
										 <html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeHNIVO" property="fieldValue"/></html:option>
									</logic:iterate>
									</ihtml:select>
								</td>
								<td>
									<% String riValue = ""; %>
									<logic:present name="mailInConsignmentVO" property="registeredOrInsuredIndicator">
									<bean:define id="ri" name="mailInConsignmentVO" property="registeredOrInsuredIndicator" toScope="page"/>
									<% riValue = (String) ri; %>
									</logic:present>
									<ihtml:select property="mailRI" componentID="CMB_MAILTRACKING_DEFAULTS_CONSIGNMENT_RI" value="<%=riValue%>" style="width:35px"  disabled="<%=toDisable%>">
									<bean:define id="oneTimeRISess" name="oneTimeRISession" toScope="page" />
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									<logic:iterate id="oneTimeRIVO" name="oneTimeRISess" >
									  <bean:define id="fieldValue" name="oneTimeRIVO" property="fieldValue" toScope="page" />
										 <html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeRIVO" property="fieldValue"/></html:option>
									</logic:iterate>
									</ihtml:select>
								</td>
								<td>
									<logic:notPresent name="mailInConsignmentVO" property="statedWeight">
										<ibusiness:unitdef id="weight" unitTxtName="weight" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_WT" label=""  unitReq = "true" 
											 unitValueStyle="iCargoEditableTextFieldRowColor1" title="Stated Weight"
											unitValue="0.0" style="background :'<%=color%>';text-align:right;"
											styleId="weight" readonly="<%=toDisableWt%>" />
									
									<%--
									<ihtml:text property="weight" indexId="rowCount" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_WT" value="" readonly="<%=toDisableWt%>"/>
									--%>
									</logic:notPresent>
									<logic:present name="mailInConsignmentVO" property="statedWeight">
									<bean:define id="statedWeight" name="mailInConsignmentVO" property="statedWeight" toScope="page" 
									type="com.ibsplc.icargo.framework.util.unit.Measure"/><!--added by A-7371-->
										<% request.setAttribute("sampleStdWt",statedWeight); %>
										<ibusiness:unitdef id="weight" unitTxtName="weight" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_WT" label=""  unitReq = "true" dataName="sampleStdWt"
											 unitValueStyle="iCargoEditableTextFieldRowColor1" title="Stated Weight"
											unitValue="<%=String.valueOf(statedWeight.getDisplayValue())%>" style="background :'<%=color%>';text-align:right"
											 styleId="weight" readonly="<%=toDisableWt%>" />
									<%--
									<ihtml:text property="weight" indexId="rowCount" value="<%=statedWeight.toString()%>" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_WT" readonly="<%=toDisableWt%>"/>
									<ihtml:text property="weight" value="<%=statedWeight.toString()%>" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_WT" readonly="<%=toDisableWt%>" maxlength="8"/>
									--%>
									</logic:present>
								</td>
								<td>
									<logic:notPresent name="mailInConsignmentVO" property="uldNumber">
									<ihtml:text property="uldNum" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_ULDNUM" value="" maxlength="9"  />
									</logic:notPresent>
									<logic:present name="mailInConsignmentVO" property="uldNumber">
									<bean:define id="uldNum" name="mailInConsignmentVO" property="uldNumber" toScope="page"/>
									<ihtml:text property="uldNum" value="<%=(String)uldNum%>" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_ULDNUM" maxlength="9" />
									</logic:present>
								</td>
								</tr>
										</logic:notEqual>
									</logic:iterate>
								</logic:present>
							</tbody>
						</table>
					</div>
					</div>
				</div>
			</ihtml:form>
				<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>
			</div>
		</div>
	</body>
</html>
