<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name 	 :  MailTracking
* File Name     	 :  ViewMailDetails.jsp
* Date          	 :  30-July-2006
* Author(s)     	 :  Roopak V.S.

*************************************************************************/
 --%>

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
<title><common:message bundle="mailManifestResources" key="mailtracking.defaults.maildetails.lbl.title" /></title>
<meta name="decorator" content="popup_panel">
<common:include type="script" src="/js/mail/operations/ViewMailDetails_Script.jsp"/>

</head>

<body id="bodyStyle">
	
	
	
<bean:define id="MailManifestForm" name="MailManifestForm"
   type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm"
   toScope="page" scope="request"/>

<business:sessionBean id="ContainerDetailsVOSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailmanifest" method="get" attribute="containerDetailsVO" />
	<logic:present name="ContainerDetailsVOSession">
		<bean:define id="ContainerDetailsVOSession" name="ContainerDetailsVOSession" toScope="page"/>
	</logic:present>

	<business:sessionBean id="ONETIME_MAIL_STATUS"
		moduleName="mail.operations"
		screenID="mailtracking.defaults.mailmanifest" method="get"
		attribute="mailStatus" />

<div id="divmain" class="iCargoPopUpContent ic-masterbg">
<ihtml:form action="/mailtracking.defaults.mailmanifest.viewmaildetails.do"  styleClass="ic-main-form" >
<ihtml:hidden property="selectTab" />
<jsp:include page="/jsp/includes/tab_support.jsp" />

	<div class="ic-content-main">
		<span class="ic-page-title ic-display-none">
			<common:message key="mailtracking.defaults.maildetails.lbl.pagetitle" />
		</span>
		<div class="ic-head-container">
			<div class="ic-filter-panel">
				<div class="ic-input-container">
					<fieldset class="ic-field-set">
						<legend><common:message key="mailtracking.defaults.maildetails.lbl.contdtls" /></legend>
							<div class="ic-row">
								<div class="ic-input ic-split-25">
									<common:message key="mailtracking.defaults.maildetails.lbl.contno" />
									 <logic:notPresent name="ContainerDetailsVOSession" property="containerNumber">
									  <ihtml:text property="containerNo" componentID="TXT_MAILTRACKING_DEFAULTS_MAILMANIFEST_CONTAINERNO" value="" readonly="true"/>
									</logic:notPresent>
									<logic:present name="ContainerDetailsVOSession" property="containerNumber">
									  <bean:define id="containerNum" name="ContainerDetailsVOSession" property="containerNumber" toScope="page" />
									  <ihtml:text property="containerNo" componentID="TXT_MAILTRACKING_DEFAULTS_MAILMANIFEST_CONTAINERNO" value="<%=(String)containerNum%>" readonly="true" />
									</logic:present>
								</div>
								<div class="ic-input ic-split-25">
									<logic:present name="ContainerDetailsVOSession" property="paBuiltFlag">
									 <logic:equal name="ContainerDetailsVOSession" property="paBuiltFlag" value="Y">
										  <!--<input type="checkbox" name="checkbox42" value="checkbox" checked disabled="true">-->
										  <img id="isPABuiltFlagEnabled" src="<%=request.getContextPath()%>/images/icon_on.gif" />
									 </logic:equal>
									 <logic:equal name="ContainerDetailsVOSession" property="paBuiltFlag" value="N">
										  <!--<input type="checkbox" name="checkbox42" value="checkbox" disabled="true">-->
										  <img id="isPABuiltFlagNotEnabled" src="<%=request.getContextPath()%>/images/icon_off.gif" />
									 </logic:equal>
								    </logic:present>
								    <logic:notPresent name="ContainerDetailsVOSession" property="paBuiltFlag">
										<!--<input type="checkbox" name="checkbox42" value="checkbox" disabled="true">-->
										<img id="isPABuiltFlagNotEnabled" src="<%=request.getContextPath()%>/images/icon_off.gif" />
								    </logic:notPresent>

								    <common:message key="mailtracking.defaults.maildetails.lbl.pabuilt" />

								</div>
								<div class="ic-input ic-split-50">
									<common:message key="mailtracking.defaults.maildetails.lbl.onwardflights" />
									 <logic:notPresent name="ContainerDetailsVOSession" property="route">
									   <ihtml:text property="onwardFlights" styleClass="iCargoTextFieldExtraLong" value="" readonly="true"/>
								 </logic:notPresent>
								 <logic:present name="ContainerDetailsVOSession" property="route">
								 <bean:define id="route" name="ContainerDetailsVOSession" property="route" toScope="page" />
									   <ihtml:text property="onwardFlights" styleClass="iCargoTextFieldExtraLong" value="<%=(String)route%>" readonly="true" />
								 </logic:present>
								</div>
							</div>
					</fieldset>
				</div>
			</div>
		</div>
		<div class="ic-main-container">	
			<div class="ic-row">
				<fieldset class="ic-field-set">
						<legend><common:message key="mailtracking.defaults.maildetails.lbl.maildtls" /></legend>
							<div id="container1" class="tab1-container">
								<ul class="tabs">
								<button type="button" id="tab2" onClick="return showPane(event,'pane2', this);" accesskey="m" class="tab"><common:message key="mailtracking.defaults.maildetails.lbl.mailtag" /></button>
									  <button type="button" id="tab1" onClick="return showPane(event,'pane1', this);" accesskey="d" class="tab"><common:message key="mailtracking.defaults.maildetails.lbl.despatch" /></button>
								</ul>
								<div class="tab-panes">
									<div id="pane1" >
										<div class="tableContainer" id="div1"   style="height:210px;width:100%;">
											<table  class="fixed-header-table">
												<thead>
													<tr class="iCargoTableHeadingLeft" height="25px">
													  <td width="10%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.maildetails.lbl.condocno" /> </td>
													  <td width="10%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.maildetails.lbl.date" /> </td>
													  <td width="10%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.maildetails.lbl.pa" /></td>
													  <td width="10%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.maildetails.lbl.origin" /></td>
													  <td width="10%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.maildetails.lbl.destination" /></td>
													  <td width="6%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.maildetails.lbl.cat" /></td>
													  <td width="7%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.maildetails.lbl.class" /></td>
													  <td width="7%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.maildetails.lbl.sc" /></td>
													  <td width="5%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.maildetails.lbl.dsn" /></td>
													  <td width="5%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.maildetails.lbl.year" /></td>
													  <td width="5%"  class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.maildetails.lbl.stdbags" /></td>
													  <td width="5%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.maildetails.lbl.stdwt" /></td>
													  <td width="5%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.maildetails.lbl.accbags" /></td>
													  <td width="5%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.maildetails.lbl.accwt" /></td>
													</tr>
												 </thead>
												<tbody>

												   <logic:present name="ContainerDetailsVOSession" property="desptachDetailsVOs">
													 <bean:define id="desptachDetailsVOsColl" name="ContainerDetailsVOSession" property="desptachDetailsVOs" scope="page" toScope="page"/>
													 <logic:iterate id="desptachDetailsVO" name="desptachDetailsVOsColl">

												<tr class="iCargoTableCellsLeftRowColor1">

														 <td><bean:write name="desptachDetailsVO" property="consignmentNumber"/></td>
														 <td>
																<logic:present name="desptachDetailsVO" property="consignmentDate">
														<bean:define id="consignmentDate" name="desptachDetailsVO" property="consignmentDate" toScope="page"/>
														<%String consignDt=TimeConvertor.toStringFormat(((LocalDate)consignmentDate).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
														<%=consignDt%>
														</logic:present>
														 </td>
														 <td><bean:write name="desptachDetailsVO" property="paCode"/></td>
														 <td><bean:write name="desptachDetailsVO" property="originOfficeOfExchange"/></td>
														 <td><bean:write name="desptachDetailsVO" property="destinationOfficeOfExchange"/></td>
														 <td><bean:write name="desptachDetailsVO" property="mailCategoryCode"/></td>
														 <td><bean:write name="desptachDetailsVO" property="mailClass"/></td>
														 <td>
															 <logic:present name="desptachDetailsVO" property="mailSubclass">
															 <bean:define id="mailSubclass" name="desptachDetailsVO" property="mailSubclass"/>
																<% if(((String)mailSubclass).indexOf("_") < 0) {%>
																	<bean:write name="desptachDetailsVO" property="mailSubclass"/>
																<% }%>
															 </logic:present>
														 </td>
														 <td><bean:write name="desptachDetailsVO" property="dsn"/></td>
														 <td><bean:write name="desptachDetailsVO" property="year" format="####"/></td>
														 <td><bean:write name="desptachDetailsVO" property="statedBags" format="####"/></td>
														 <td><common:write name="desptachDetailsVO" property="statedWeight" unitFormatting="true"/></td><!-- added by A-7371-->
														 <td><bean:write name="desptachDetailsVO" property="acceptedBags" format="####"/></td>
														 <td><common:write name="desptachDetailsVO" property="acceptedWeight" unitFormatting="true"/></td><!-- added by A-7371-->

												 </tr>
													</logic:iterate>
												</logic:present>
												</tbody>
											</table>
										</div>
									</div>
									<div id="pane2">
										<div class="tableContainer" id="div2"   style="height:210px;width:100%;" >
											<table  class="fixed-header-table">
												  <thead>
													<tr class="iCargoTableHeadingLeft" height="25px">
													  <td width="12%"><common:message key="mailtracking.defaults.maildetails.lbl.latestOperation" /></td>
													  <td width="12%"><common:message key="mailtracking.defaults.maildetails.lbl.origin" /></td>
													  <td width="11%"><common:message key="mailtracking.defaults.maildetails.lbl.destination" /></td>
													  <td width="8%"><common:message key="mailtracking.defaults.maildetails.lbl.cat" /></td>
													  <td width="9%"><common:message key="mailtracking.defaults.maildetails.lbl.sc" /></td>
													  <td width="6%"><common:message key="mailtracking.defaults.maildetails.lbl.yr" /></td>
													  <td width="6%"><common:message key="mailtracking.defaults.maildetails.lbl.dsn" /></td>
													  <td width="6%"><common:message key="mailtracking.defaults.maildetails.lbl.rsn" /></td>
													  <td width="6%"><common:message key="mailtracking.defaults.maildetails.lbl.hni" /></td>
													  <td width="6%"><common:message key="mailtracking.defaults.maildetails.lbl.ri" /></td>
													  <td width="8%"><common:message key="mailtracking.defaults.maildetails.lbl.wt" /></td>
													  <td width="10%"><common:message key="mailtracking.defaults.maildetails.lbl.scandate" /></td>
													  <td width="8%"><common:message key="mailtracking.defaults.maildetails.lbl.damaged" /></td>
													</tr>
												  </thead>
												<tbody>

												 <logic:present name="ContainerDetailsVOSession" property="mailDetails">
												<bean:define id="mailDetailsColln" name="ContainerDetailsVOSession" property="mailDetails" scope="page" toScope="page"/>
													  <logic:iterate id="mailDetailsVO" name="mailDetailsColln" >

													<tr class="iCargoTableCellsLeftRowColor1">

													   <td class="iCargoTableDataTd"><logic:present
															name="mailDetailsVO" property="latestStatus">

															<bean:define id="latestStatus" name="mailDetailsVO"
																property="latestStatus" />
															<logic:present name="ONETIME_MAIL_STATUS">

																<logic:iterate id="mailStatus" name="ONETIME_MAIL_STATUS"
																	type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																	<logic:present name="mailStatus"
																		property="fieldValue">

																		<bean:define id="fieldValue" name="mailStatus"
																			property="fieldValue" />

																		<logic:equal name="mailStatus"
																			property="fieldValue" value="<%=(String) latestStatus%>">

																			<bean:write name="mailStatus"
																				property="fieldDescription" />

																		</logic:equal>

																	</logic:present>

																</logic:iterate>

															</logic:present>
														</logic:present></td> 
													   
													   
													  
													  
													    
														<td><bean:write name="mailDetailsVO" property="ooe"/></td>
														<td><bean:write name="mailDetailsVO" property="doe"/></td>
														<td><bean:write name="mailDetailsVO" property="mailCategoryCode"/></td>
														<td><bean:write name="mailDetailsVO" property="mailSubclass"/></td>
														<td><bean:write name="mailDetailsVO" property="year"/></td>
														<td><bean:write name="mailDetailsVO" property="despatchSerialNumber"/></td>
														<td><bean:write name="mailDetailsVO" property="receptacleSerialNumber"/></td>
														<td><bean:write name="mailDetailsVO" property="highestNumberedReceptacle"/></td>
														<td><bean:write name="mailDetailsVO" property="registeredOrInsuredIndicator"/></td>
														<td><common:write name="mailDetailsVO" property="weight" unitFormatting="true"/></td>
														<td>
															<logic:present name="mailDetailsVO" property="scannedDate">
																<bean:define id="scannedDate" name="mailDetailsVO" property="scannedDate" toScope="page"/>
																<%String scanDate=TimeConvertor.toStringFormat(((LocalDate)scannedDate).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
																<%=scanDate%>
															</logic:present>
														</td>
														<td>
														<div >
														 <logic:notPresent name="mailDetailsVO" property="damageFlag">
															<!--<input type="checkbox" name="mailDamaged" disabled="true"/>-->
															<img id="isNotDamaged" src="<%=request.getContextPath()%>/images/icon_off.gif" />
														 </logic:notPresent>
														 <logic:present name="mailDetailsVO" property="damageFlag">
															 <logic:equal name="mailDetailsVO" property="damageFlag" value="Y" >
																	 <!--<input type="checkbox" name="mailDamaged" value="true" checked disabled="true"/>-->
																	 <img id="isDamaged" src="<%=request.getContextPath()%>/images/icon_on.gif" />
															 </logic:equal>
															 <logic:equal name="mailDetailsVO" property="damageFlag" value="N">
																	 <!--<input type="checkbox" name="mailDamaged" value="false" disabled="true"/>-->
																	 <img id="isNotDamaged" src="<%=request.getContextPath()%>/images/icon_off.gif" />
															 </logic:equal>
														 </logic:present>
														</div>
														</td>
													</tr>
													</logic:iterate>
												</logic:present>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
				</fieldset>
			</div>
		</div>
		<div class="ic-foot-container">
			<div class="ic-button-container">	
			<ihtml:nbutton property="btnOK" componentID="BTN_MAILTRACKING_DEFAULTS_MAILMANIFEST_OK" tabindex="9">
			<common:message key="mailtracking.defaults.maildetails.btn.ok" />
			</ihtml:nbutton>

			</div>
		</div>

	</div>
</ihtml:form>
</div>
			
		   
				
	
	</body>
</html:html>
