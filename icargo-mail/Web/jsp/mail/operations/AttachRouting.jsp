<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name 	 :  MailTracking
* File Name     	 :  AttachRouting.jsp
* Date          	 :  15-JUN-2009
* Author(s)     	 :  INDU V.K.

*************************************************************************/
 --%>



<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm"%>
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

<html:html locale="true">
<head> 
	
	
	
	<%@ include file="/jsp/includes/customcss.jsp" %>
<title><common:message bundle="mailManifestResources" key="mailtracking.defaults.attachrouting.lbl.title" /></title>

<meta name="decorator" content="popup_panel">
 <common:include type="script" src="/js/mail/operations/AttachRouting_Script.jsp"/>


</head>

<body id="bodyStyle">
	
	

<bean:define id="MailManifestForm" name="MailManifestForm" type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm" toScope="page" scope="request"/>

<business:sessionBean id="conDocVOSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailmanifest" method="get" attribute="consignmentDocumentVO" />
<business:sessionBean id="oneTimeTypeSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailmanifest" method="get" attribute="oneTimeType" />



<div class="iCargoPopUpContent ic-masterbg">

<ihtml:form action="mailtracking.defaults.mailmanifest.attachroutingscreenload.do"   styleClass="ic-main-form">
<ihtml:hidden name="MailManifestForm" property="routingStatus"/>
<ihtml:hidden name="MailManifestForm" property="newRoutingFlag"/>
<ihtml:hidden name="MailManifestForm" property="fromScreen"/>

	<div class="ic-content-main">
		<span class="ic-page-title ic-display-none">
			<common:message key="mailtracking.defaults.attachrouting.lbl.pagetitle" />
		</span>
		<div class="ic-head-container">
			<div class="ic-filter-panel">
				<div class="ic-input-container">
					<fieldset class="ic-field-set">
						<legend>Document Details</legend>
							<div class="ic-row">
								<div class="ic-input ic-split-33 ic-mandatory">
									<label><common:message key="mailtracking.defaults.attachrouting.lbl.condocno" /></label>
									<ihtml:text property="conDocNo" componentID="TXT_MAILTRACKING_DEFAULTS_ATTACHROUTING_CONDOCNO"
									value="<%=MailManifestForm.getConDocNo()%>"  style="width:100px" maxlength="13"/>
								</div>
								<div class="ic-input ic-split-33 ic-mandatory">
									<label><common:message key="mailtracking.defaults.attachrouting.lbl.pa" /></label>
									<ihtml:text property="paCode" componentID="TXT_MAILTRACKING_DEFAULTS_ATTACHROUTING_PA" 			value="<%=MailManifestForm.getPaCode()%>" maxlength="5" />
									<div class= "lovImg">
									<img id="paCodeLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onClick="displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.paCode.value,'PA','1','paCode','',0)">
									</div>
								</div>
								<div class="ic-button-container">
									<ihtml:nbutton property="btnList" componentID="BTN_MAILTRACKING_DEFAULTS_ATTACHROUTING_LIST" >
									<common:message key="mailtracking.defaults.attachrouting.btn.list" />
									</ihtml:nbutton>
									<ihtml:nbutton property="btnClear" componentID="BTN_MAILTRACKING_DEFAULTS_ATTACHROUTING_CLEAR" >
									<common:message key="mailtracking.defaults.attachrouting.btn.clear" />
									</ihtml:nbutton>

								</div>
							</div>
					</fieldset>		
				</div>
			</div>
		</div>
		<div class="ic-main-container">	
			<div class="ic-row">
				<div class="ic-col-25"><!--modified by A-7929-->
					<div class="ic-row">
						<div class="ic-input ic-split-50">
							<ihtml:radio property="direction" value="I" componentID="CMP_MAILTRACKING_DEFAULTS_ATTACHROUTING_RADIOINBOUND"/>
							<common:message key="mailtracking.defaults.attachrouting.lbl.inbound"/>
						</div>

						<div class="ic-input ic-split-50">
							<ihtml:radio property="direction" value="O" componentID="CMP_MAILTRACKING_DEFAULTS_ATTACHROUTING_RADIOOUTBOUND"/>
							<common:message key="mailtracking.defaults.attachrouting.lbl.outbound"/>
						</div>
					</div>
					<div class="ic-row ic-label-35">
						<div class="ic-input ic-split-100 ic-mandatory">
							<label><common:message key="mailtracking.defaults.attachrouting.lbl.condate" /></label>
							<logic:notPresent name="conDocVOSession" property="consignmentDate">
								<ibusiness:calendar property="conDate" id="conDate" type="image" value="" componentID="CMP_MAILTRACKING_DEFAULTS_ATTACHROUTING_CONDATE" />
							</logic:notPresent>
							<logic:present name="conDocVOSession" property="consignmentDate">
								<bean:define id="conDate" name="conDocVOSession" property="consignmentDate" toScope="page"/>
								<%String conDt=TimeConvertor.toStringFormat(((LocalDate)conDate).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
								<ibusiness:calendar property="conDate" id="conDate" type="image" value="<%=conDt%>" componentID="CMP_MAILTRACKING_DEFAULTS_ATTACHROUTING_CONDATE" />
							</logic:present>
						</div>
					</div>
					<div class="ic-row ic-label-35">
						<div class="ic-input ic-split-100 ic-mandatory">
						<label><common:message key="mailtracking.defaults.attachrouting.lbl.type" /></label>
						
							<%
								String typeValue = "" ;
							%>
							<logic:present name="conDocVOSession" property="type">	
							
								<bean:define id="type" name="conDocVOSession" property="type" toScope="page"/>
								<%
									typeValue = (String) type;
								%>
							</logic:present>
							<ihtml:select property="contype" componentID="CMB_MAILTRACKING_DEFAULTS_ATTACHROUTING_TYPE" value="<%=typeValue%>" >
								<html:option value=""> <common:message key="mailtracking.defaults.attachrouting.lbl.select" /> </html:option>											
								<bean:define id="oneTimeTypeSess" name="oneTimeTypeSession" toScope="page" />
								<logic:iterate id="typeVO" name="oneTimeTypeSess" >
									<bean:define id="fieldValue" name="typeVO" property="fieldValue" toScope="page" />
									<html:option value="<%=(String)fieldValue %>"><%=(String)fieldValue %></html:option>
								</logic:iterate>
							</ihtml:select>
						</div>
					</div>
				</div>	
				<div class="ic-col-75"><!--modified by A-7929-->
					<fieldset class="ic-field-set">
						<legend><common:message key="mailtracking.defaults.attachrouting.lbl.onwardrouting" /></legend>
							<div class="ic-row">
								<div class="ic-button-container">
									<a class="iCargoLink" href="#" id="lnkAddRoute" value="lnkAddRoute" name="lnkAddRoute">
									<common:message key="mailtracking.defaults.attachrouting.lnk.add" />
								</a>
								<a class="iCargoLink" href="#" id="lnkDeleteRoute" value="lnkDeleteRoute" name="lnkDeleteRoute">
									<common:message key="mailtracking.defaults.attachrouting.lnk.delete" />
								</a>
								</div>
							</div>
							<div class="ic-row">
								<div id="div2" class="tableContainer" style="height:90px">
									<table  class="fixed-header-table">
										<thead>
											<tr class="iCargoTableHeadingLeft">
												<td width="5%"  class="iCargoTableHeaderLabel ">
													<input type="checkbox" name="masterRoute" 	onclick="updateHeaderCheckBox(this.form,this,this.form.selectRoute);"/>
												</td>
												<td width="30%" class="iCargoTableHeaderLabel ">
													<label><common:message key="mailtracking.defaults.attachrouting.lbl.flightno" /></label>
												</td>
												<td width="25%" class="iCargoTableHeaderLabel " >
													<label><common:message key="mailtracking.defaults.attachrouting.lbl.depdate" /></label>
												</td>
												<td width="20%" class="iCargoTableHeaderLabel ">
													<label><common:message key="mailtracking.defaults.attachrouting.lbl.pol" /></label>
												</td>
												<td width="20%" class="iCargoTableHeaderLabel ">
													<label><common:message key="mailtracking.defaults.attachrouting.lbl.pou" /></label>
												</td>
											</tr>
										</thead>
										<tbody id="routeTableBody">
											<% int tabNum = 7; %>
										<logic:present name="conDocVOSession" property="routingInConsignmentVOs">
										  <logic:notEmpty name="conDocVOSession" property="routingInConsignmentVOs">
											<bean:define id="routingInConsignmentVOs" name="conDocVOSession" property="routingInConsignmentVOs" scope="page" toScope="page"/>
											<% int row = ((Collection)routingInConsignmentVOs).size();%>
											<logic:iterate id="routingInConsignmentVO" name="routingInConsignmentVOs" indexId="index">
												<% tabNum = tabNum + row * index; %>	
												<logic:notEqual name="routingInConsignmentVO" property="operationFlag" value="D">		
													<logic:present name="routingInConsignmentVO" property="operationFlag">
														<bean:define id="operationFlag" name="routingInConsignmentVO" property="operationFlag" toScope="request" />
														<ihtml:hidden property="routeOpFlag" value="<%=((String)operationFlag)%>" />
													</logic:present>
													<logic:notPresent name="routingInConsignmentVO" property="operationFlag">
														<ihtml:hidden property="routeOpFlag" value="N" />		 
													</logic:notPresent>		
													<tr>
														<td class="iCargoTableDataTd">
															<input type="checkbox" name="selectRoute" value="<%=String.valueOf(index)%>" onclick="toggleTableHeaderCheckbox(this,this.form.masterRoute);"/>
														</td>
														<td class="iCargoTableDataTd" >
															<logic:notPresent name="routingInConsignmentVO" property="onwardFlightNumber">
																   <ibusiness:flightnumber id="fltNo" carrierCodeProperty="flightRouteCarrierCode"  flightCodeProperty="flightRouteNumber" carriercodevalue="" flightcodevalue=""  componentID="CMP_MAILTRACKING_DEFAULTS_ATTACHROUTING_FLIGHTNO"/>
															</logic:notPresent>

															<logic:present name="routingInConsignmentVO" property="onwardCarrierCode">
															<logic:present name="routingInConsignmentVO" property="onwardFlightNumber">
																	<bean:define id="carrierCode" name="routingInConsignmentVO" property="onwardCarrierCode" toScope="page"/>
																	<bean:define id="flightNo" name="routingInConsignmentVO" property="onwardFlightNumber" toScope="page"/>
																	<ibusiness:flightnumber id="fltNo" carrierCodeProperty="flightRouteCarrierCode"  flightCodeProperty="flightRouteNumber" carriercodevalue="<%=(String)carrierCode%>" flightcodevalue="<%=(String)flightNo%>"  componentID="CMP_MAILTRACKING_DEFAULTS_ATTACHROUTING_FLIGHTNO"/>
															</logic:present>
															</logic:present>
														</td>
														<td class="iCargoTableDataTd" style = "text-align:center">
															<logic:notPresent name="routingInConsignmentVO" property="onwardFlightDate">
																<ibusiness:calendar property="depRouteDate" indexId="rowCount" id="depDate" type="image" value="" componentID="CMP_MAILTRACKING_DEFAULTS_ATTACHROUTING_DEPDATE" />
															</logic:notPresent>
															<logic:present name="routingInConsignmentVO" property="onwardFlightDate">
																<bean:define id="depDate" name="routingInConsignmentVO" property="onwardFlightDate" toScope="page"/>
																<%String dpDt=TimeConvertor.toStringFormat(((LocalDate)depDate).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
																<ibusiness:calendar property="depRouteDate" indexId="rowCount" id="depDate" type="image" value="<%=dpDt%>" componentID="CMP_MAILTRACKING_DEFAULTS_ATTACHROUTING_DEPDATE" />
															</logic:present>
														</td>
														<td class="iCargoTableDataTd" >
															<logic:notPresent name="routingInConsignmentVO" property="pol">
																<ihtml:text property="polRoute" indexId="rowCount" componentID="CMP_MAILTRACKING_DEFAULTS_ATTACHROUTING_POL" maxlength="4" value="" />
															</logic:notPresent>
															<logic:present name="routingInConsignmentVO" property="pol">
																<bean:define id="pol" name="routingInConsignmentVO" property="pol" toScope="page"/>
																<ihtml:text property="polRoute" indexId="rowCount" componentID="CMP_MAILTRACKING_DEFAULTS_ATTACHROUTING_POL" maxlength="4" value="<%=(String)pol%>" />
															</logic:present>
															<div class= "lovImgTbl"><img name="polLov" id="polLov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16"></div>
														</td>
														<td class="iCargoTableDataTd" >
															<logic:notPresent name="routingInConsignmentVO" property="pou">
																<ihtml:text property="pouRoute" indexId="rowCount" componentID="CMP_MAILTRACKING_DEFAULTS_ATTACHROUTING_POU" maxlength="4" value="" />
															</logic:notPresent>
															<logic:present name="routingInConsignmentVO" property="pou">
																<bean:define id="pou" name="routingInConsignmentVO" property="pou" toScope="page"/>
																<ihtml:text property="pouRoute" indexId="rowCount" componentID="CMP_MAILTRACKING_DEFAULTS_ATTACHROUTING_POU" maxlength="4" value="<%=(String)pou%>" />
															</logic:present>
															<div class= "lovImgTbl"><img name="pouLov" id="pouLov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16"></div>
														</td>
													</tr>
												</logic:notEqual>
												<logic:equal name="routingInConsignmentVO" property="operationFlag" value="D">
												
												   <bean:define id="onwardCarrierCode" name="routingInConsignmentVO" property="onwardCarrierCode" toScope="page"/>
												   <bean:define id="onwardFlightNumber" name="routingInConsignmentVO" property="onwardFlightNumber" toScope="page"/>
												   <bean:define id="onwardFlightDate" name="routingInConsignmentVO" property="onwardFlightDate" toScope="page"/>
												   <bean:define id="pol" name="routingInConsignmentVO" property="pol" toScope="page"/>											      
												   <bean:define id="pou" name="routingInConsignmentVO" property="pouRoute" toScope="page"/>		
													
													<ihtml:hidden property="flightRouteCarrierCode" value="<%=onwardCarrierCode.toString()%>"/>
													<ihtml:hidden property="flightRouteNumber" value="<%=onwardFlightNumber.toString()%>"/>												
													<ihtml:hidden property="depRouteDate" value="<%= onwardFlightDate.toString().substring(0,11) %>"/>
													<ihtml:hidden property="polRoute" value="<%=pol.toString()%>"/>
													<ihtml:hidden property="pouRoute" value="<%=pou.toString()%>"/>	
													
													<ihtml:hidden property="routeOpFlag" value="D" />		 		
																						
												</logic:equal>
											</logic:iterate>
										  </logic:notEmpty>
										</logic:present>
										<!-- templateRow -->
											<tr template="true" id="routeTemplateRow" style="display:none">
												<ihtml:hidden property="routeOpFlag" value="NOOP" />
												<td style = "text-align:center" class="iCargoTableDataTd">
													<input type="checkbox" name="selectRoute" >
												</td>
												 <td class="iCargoTableDataTd multi-input">
														<ibusiness:flightnumber id="fltNo" carrierCodeProperty="flightRouteCarrierCode"  flightCodeProperty="flightRouteNumber" carriercodevalue="" flightcodevalue="" componentID="CMP_MAILTRACKING_DEFAULTS_ATTACHROUTING_FLIGHTNO" readonly="false" />
												 </td>
												<td class="iCargoTableDataTd" style = "text-align:center">
													<ibusiness:calendar property="depRouteDate" indexId="index" id="depDate" type="image" value="" componentID="CMP_MAILTRACKING_DEFAULTS_ATTACHROUTING_DEPDATE" />
												</td>
												<td class="iCargoTableDataTd">
													<ihtml:text property="polRoute" componentID="CMP_MAILTRACKING_DEFAULTS_ATTACHROUTING_POL" maxlength="4" value="" />
													<div class= "lovImgTbl"><img name="polLov" id="polLov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16"><div>
												</td>
												<td class="iCargoTableDataTd">
													<ihtml:text property="pouRoute" componentID="CMP_MAILTRACKING_DEFAULTS_ATTACHROUTING_POU" maxlength="4" value="" />
													<div class= "lovImgTbl"><img name="pouLov" id="pouLov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16"></div>
												</td>

											</tr>
										<!--template row ends-->
										</tbody>
									</table>
								</div>
							</div>
					</fieldset>
				</div>
			</div>
			<div class="ic-row ic-label-7 ic-input ic-split-70 "><!--modified by A-7929-->
				<label><common:message key="mailtracking.defaults.attachrouting.lbl.remarks" /></label>
				
					<logic:notPresent name="conDocVOSession" property="remarks">
						<ihtml:textarea property="remarks" cols="80" rows="3" style="height:70px;" componentID="TXT_MAILTRACKING_DEFAULTS_ATTACHROUTING_REMARKS" value="" ></ihtml:textarea>
					</logic:notPresent><!--modified by A-7929-->
					<logic:present name="conDocVOSession" property="remarks">
						<bean:define id="remarks" name="conDocVOSession" property="remarks" toScope="page" />
						<ihtml:textarea property="remarks" cols="80" rows="3" style="height:70px;" value="<%=(String)remarks%>" componentID="TXT_MAILTRACKING_DEFAULTS_ATTACHROUTING_REMARKS" ></ihtml:textarea>
					</logic:present><!--modified by A-7929-->
			</div>
			 <!--Modified by A-7938 for ICRD-243746-->
			<div class="ic-row" >
							&nbsp;
							</div>
			
		</div>
		<div class="ic-foot-container">
			<div class="ic-button-container">	
					<ihtml:nbutton property="btnSave"   componentID="BTN_MAILTRACKING_DEFAULTS_ATTACHROUTING_SAVE" >
						<common:message key="mailtracking.defaults.attachrouting.btn.save" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btnClose"   componentID="BTN_MAILTRACKING_DEFAULTS_ATTACHROUTING_CLOSE" >
						<common:message key="mailtracking.defaults.attachrouting.btn.close" />
					</ihtml:nbutton>
		   </div>
		</div>
	</div>
</ihtml:form>

</div>
		
				
		  
	</body>
</html:html>
