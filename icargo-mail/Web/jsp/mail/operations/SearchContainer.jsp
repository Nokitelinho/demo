<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name :  MailTracking
* File Name     	 :  SearchContainer.jsp
* Date          	 :  12-June-2006
* Author(s)     	 :  Roopak V.S.

*************************************************************************/
 --%>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchContainerForm"%>
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
<title><common:message bundle="searchContainerResources" key="mailtracking.defaults.searchcontainer.lbl.title" /></title>
	<meta name="decorator" content="mainpanel">
	<common:include type="script" src="/js/mail/operations/SearchContainer_Script.jsp" />

</head>

<body id="bodyStyle">



<bean:define id="SearchContainerForm"
	name="SearchContainerForm"
    type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchContainerForm"
    toScope="page" scope="request"/>

<business:sessionBean
	id="oneTimeValues"
	moduleName="mail.operations" screenID="mailtracking.defaults.searchContainer"
	method="get"
	attribute="oneTimeValues" />

<business:sessionBean id="containerVOcollection" moduleName="mail.operations" screenID="mailtracking.defaults.searchContainer" method="get" attribute="listContainerVOs" />
	<logic:present name="containerVOcollection">
		<bean:define id="containerVOcollection" name="containerVOcollection" toScope="page"/>
	</logic:present>
<business:sessionBean id="searchContainerFilterVO" moduleName="mail.operations" screenID="mailtracking.defaults.searchContainer" method="get" attribute="searchContainerFilterVO" />
	<logic:present name="searchContainerFilterVO">
		<bean:define id="searchContainerFilterVO" name="searchContainerFilterVO" toScope="page"/>
	</logic:present>

<div id="pageDiv" class="iCargoContent ic-masterbg" style="width:100%;height:100%; overflow:auto;" >

<ihtml:form action="mailtracking.defaults.searchcontainer.screenloadsearchcontainer.do">

<ihtml:hidden property="lastPageNum" />
<ihtml:hidden property="displayPage" />
<ihtml:hidden property="currentDialogId" />
<ihtml:hidden property="currentDialogOption" />
<ihtml:hidden property="reassignFlag" />
<ihtml:hidden property="status" />
<ihtml:hidden property="screenStatusFlag" />
<ihtml:hidden property="reList" />
<ihtml:hidden property="warningFlag" />
<input type=hidden name="mySearchEnabled" />
<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />


<div class="ic-content-main">
				<div class="ic-head-container">
					<span class="ic-page-title ic-display-none">
						<common:message key="mailtracking.defaults.searchcontainer.lbl.pagetitle" />
					</span>
					<div class="ic-round-border">
						
						<div class="ic-row">
							<div class="ic-filter-panel">
							<div class="ic-row">
							<h4><common:message key="mailtracking.defaults.searchcontainer.lbl.searchcriteria" /></h4>
						</div>
							<div class="ic-row marginR6">
								<div class="ic-input ic-split-20 marginT10 marginL5">
								<label><common:message key="mailtracking.defaults.searchcontainer.lbl.containerno" /></label>
								<logic:notPresent name="searchContainerFilterVO" property="containerNumber">
								<ihtml:text property="containerNo" style="text-transform:uppercase;width:110px;" componentID="TXT_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_CONTAINERNO" maxlength="13" />
								</logic:notPresent>
								<logic:present name="searchContainerFilterVO" property="containerNumber">
									<bean:define id="containerNo" name="searchContainerFilterVO" property="containerNumber" toScope="page"/>
									<ihtml:text property="containerNo" style="text-transform:uppercase;width:110px;" componentID="TXT_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_CONTAINERNO" maxlength="13" value="<%=(String)containerNo%>" />
								</logic:present>
								</div>
								<fieldset class="ic-field-set" style="margin-right:5px;">
									<legend><common:message key="mailtracking.defaults.searchcontainer.lbl.assigndetails" /></legend>
									<div class="ic-col-30 booked_flight">
										<label><common:message key="mailtracking.defaults.searchcontainer.lbl.fromdate" /></label>
										<logic:notPresent name="searchContainerFilterVO" property="strFromDate">
										<ibusiness:calendar property="fromDate" id="fromDate" type="image" value="" componentID="CMP_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_FROMDATE" />
										</logic:notPresent>
										<logic:present name="searchContainerFilterVO" property="strFromDate">
										<bean:define id="frmDate" name="searchContainerFilterVO" property="strFromDate" toScope="page"/>
										<ibusiness:calendar property="fromDate" id="fromDate" type="image" value="<%=(String)frmDate%>" componentID="CMP_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_FROMDATE" />
										</logic:present>
									</div>
									<div class="ic-col-30 booked_flight">
									<label><common:message key="mailtracking.defaults.searchcontainer.lbl.todate" /></label>
										<logic:notPresent name="searchContainerFilterVO" property="strToDate">
										<ibusiness:calendar property="toDate" id="toDate" type="image" value="" componentID="CMP_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_TODATE" />
										</logic:notPresent>
										<logic:present name="searchContainerFilterVO" property="strToDate">
										<bean:define id="tDate" name="searchContainerFilterVO" property="strToDate" toScope="page"/>
										<ibusiness:calendar property="toDate" id="toDate" type="image" value="<%=(String)tDate%>" componentID="CMP_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_TODATE" />
										</logic:present>
									</div>
									<div class="ic-col-30 ic-input booked_flight">
									<label><common:message key="mailtracking.defaults.searchcontainer.lbl.depport" /></label>
									<logic:notPresent name="searchContainerFilterVO" property="departurePort">
									<ihtml:text property="departurePort"  maxlength="4" componentID="CMP_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_DEPPORT" />
									</logic:notPresent>
									<logic:present name="searchContainerFilterVO" property="departurePort">
									<bean:define id="departurePort" name="searchContainerFilterVO" property="departurePort" toScope="page"/>
									<ihtml:text property="departurePort"   maxlength="4" componentID="CMP_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_DEPPORT" value="<%=(String)departurePort%>" />
									</logic:present>
									<div class="lovImg">
									<img  id="airportlov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" border="0"/>
									</div>
									</div>
									<div class="ic-col-10 booked_flight">
									<label><common:message key="mailtracking.defaults.searchcontainer.lbl.assignedby" /></label>
									<logic:notPresent name="searchContainerFilterVO" property="assignedUser">
									<ihtml:text property="assignedBy"  maxlength="13" value="" componentID="TXT_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_ASSIGNEDBY" />
									</logic:notPresent>
									<logic:present name="searchContainerFilterVO" property="assignedUser">
									<bean:define id="assignUser" name="searchContainerFilterVO" property="assignedUser" toScope="page"/>
									<ihtml:text property="assignedBy"  maxlength="13" value="<%=(String)assignUser%>" componentID="TXT_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_ASSIGNEDBY" />
									</logic:present>
									</div>
								</fieldset>
								</div>
								<div class="ic-border marginT5">
									<div class="ic-input ic-split-45">
									<label><common:message key="mailtracking.defaults.searchcontainer.lbl.assignto" /></label>
										<% String mode="";%>
										<logic:notPresent name="searchContainerFilterVO" property="searchMode">
										<% mode="";%>
										</logic:notPresent>
										<logic:present name="searchContainerFilterVO" property="searchMode">
										<bean:define id="searchMode" name="searchContainerFilterVO" property="searchMode" toScope="page"/>
										<% mode=(String)searchMode; %>
										</logic:present>
										<ihtml:select property="assignedTo" componentID="CMP_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_ASSIGNTYPE" onchange="togglePanel(1,this);" value="<%=(String)mode%>" >
										<logic:present name="oneTimeValues">
											<logic:iterate id="oneTimeValue" name="oneTimeValues">
											<bean:define id="parameterCode" name="oneTimeValue" property="key" />
												<logic:equal name="parameterCode" value="mailtracking.defaults.containersearchmode">
												<bean:define id="parameterValues" name="oneTimeValue" property="value" />
													<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
														<logic:present name="parameterValue">
														<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
														<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
														<html:option value="<%=(String)fieldValue%>">
														<%=(String)fieldDescription%>
														</html:option>
														</logic:present>
													</logic:iterate>
												</logic:equal>
											</logic:iterate>
										</logic:present>
										</ihtml:select>
									</div>
									<div class="ic-input ic-split-45">
										<div id="FLT" >
											<div class="ic-row">
												<div class="ic-col-30">
													<label><common:message key="mailtracking.defaults.searchcontainer.lbl.flightno" /></label>
													<logic:notPresent name="searchContainerFilterVO" property="flightCarrierCode">
													<ibusiness:flightnumber id="fltNo" carrierCodeProperty="flightCarrierCode"  flightCodeProperty="flightNumber" carriercodevalue="" flightcodevalue=""  componentID="CMP_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_FLIGHTNO"/>
													</logic:notPresent>
													<logic:present name="searchContainerFilterVO" property="flightCarrierCode">
													<logic:present name="searchContainerFilterVO" property="flightNumber">
													<bean:define id="carrierCode" name="searchContainerFilterVO" property="flightCarrierCode" toScope="page"/>
													<bean:define id="flightNo" name="searchContainerFilterVO" property="flightNumber" toScope="page"/>
													<ibusiness:flightnumber id="fltNo"
													carrierCodeProperty="flightCarrierCode"  flightCodeProperty="flightNumber"
													carriercodevalue="<%=(String)carrierCode%>"
													flightcodevalue="<%=(String)flightNo%>"
													componentID="CMP_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_FLIGHTNO"/>
													</logic:present>
													<logic:notPresent name="searchContainerFilterVO" property="flightNumber">
													<bean:define id="carrierCode" name="searchContainerFilterVO" property="flightCarrierCode" toScope="page"/>
													<ibusiness:flightnumber id="fltNo"
													carrierCodeProperty="flightCarrierCode"  flightCodeProperty="flightNumber"
													carriercodevalue="<%=(String)carrierCode%>"
													flightcodevalue=""
													componentID="CMP_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_FLIGHTNO"/>
													</logic:notPresent>
													</logic:present>
												</div>
												<div class="ic-col-40">
												<label><common:message key="mailtracking.defaults.searchcontainer.lbl.flightdate" /></label>
												<logic:notPresent name="searchContainerFilterVO" property="strFlightDate">
												<ibusiness:calendar property="flightDate" id="flightDate" type="image" value="" componentID="CMP_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_FLIGHTDATE" />
												</logic:notPresent>
												<logic:present name="searchContainerFilterVO" property="strFlightDate">
												<bean:define id="fltDate" name="searchContainerFilterVO" property="strFlightDate" toScope="page"/>
												<ibusiness:calendar property="flightDate" id="flightDate" type="image" value="<%=(String)fltDate%>" componentID="CMP_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_FLIGHTDATE" />
												</logic:present>
												</div>
												<div class="ic-col-30">
												<label><common:message key="mailtracking.defaults.searchcontainer.lbl.flttype" /></label>
												<% String type="";%>
												<logic:notPresent name="searchContainerFilterVO" property="operationType">
												<% type="";%>
												</logic:notPresent>
												<logic:present name="searchContainerFilterVO" property="operationType">
												<bean:define id="operationType" name="searchContainerFilterVO" property="operationType" toScope="page"/>
												<% type=(String)operationType; %>
												</logic:present>
												<ihtml:select property="operationType" componentID="CMP_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_FLTTYPE" value="<%=(String)type%>">
												<logic:present name="oneTimeValues">
												<logic:iterate id="oneTimeValue" name="oneTimeValues">
												<bean:define id="parameterCode" name="oneTimeValue" property="key" />
												<logic:equal name="parameterCode" value="mailtracking.defaults.operationtype">
												<bean:define id="parameterValues" name="oneTimeValue" property="value" />
												<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
												<logic:present name="parameterValue">
												<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
												<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
												<html:option value="<%=(String)fieldValue%>">
												<%=(String)fieldDescription%>
												</html:option>
												</logic:present>
												</logic:iterate>
												</logic:equal>
												</logic:iterate>
												</logic:present>
												</ihtml:select>
												</div>
											</div>
										</div>
										<div id="DESTN" >
											<div class="ic-row ic-label-30">
												<div class="ic-left">
												<label><common:message key="mailtracking.defaults.searchcontainer.lbl.carriercode" /></label>
												<logic:notPresent name="searchContainerFilterVO" property="carrierCode">
													<ihtml:text property="carrier" maxlength="3" value="" componentID="CMP_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_CARRIERCODE" />
												</logic:notPresent>
												<logic:present name="searchContainerFilterVO" property="carrierCode">
													<bean:define id="carCode" name="searchContainerFilterVO" property="carrierCode" toScope="page"/>
													<ihtml:text property="carrier" maxlength="3" value="<%=(String)carCode%>" componentID="CMP_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_CARRIERCODE" />
												</logic:present>
												<div class="lovImg">
												<img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" border="0" onclick="displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.carrier.value,'Airline','1','carrier','',0)"/>
												</div>
												</div>
											</div>
										</div>
										<div id="ALL">
											<div class="ic-row ic-label-30">
												<div class="ic-left">
												<label><common:message key="mailtracking.defaults.searchcontainer.lbl.flttype" /></label>
													<% String optype="";%>
													<logic:notPresent name="searchContainerFilterVO" property="operationType">
													<% optype="";%>
													</logic:notPresent>
													<logic:present name="searchContainerFilterVO" property="operationType">
													<bean:define id="operationType" name="searchContainerFilterVO" property="operationType" toScope="page"/>
													<% optype=(String)operationType; %>
													</logic:present>
													<ihtml:select property="operationTypeAll" componentID="CMP_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_FIELDTYPEALL" value="<%=(String)optype%>" >
													<logic:present name="oneTimeValues">
													<logic:iterate id="oneTimeValue" name="oneTimeValues">
													<bean:define id="parameterCode" name="oneTimeValue" property="key" />
													<logic:equal name="parameterCode" value="mailtracking.defaults.operationtype">
													<bean:define id="parameterValues" name="oneTimeValue" property="value" />
													<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
													<logic:present name="parameterValue">
													<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
													<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
													<html:option value="<%=(String)fieldValue%>">
													<%=(String)fieldDescription%>
													</html:option>
													</logic:present>
													</logic:iterate>
													</logic:equal>
													</logic:iterate>
													</logic:present>
													</ihtml:select>
												</div>
											</div>
										</div>
									</div>
									<div class="ic-input ic-split-10 ic-label ic-right">
										<label><common:message key="mailtracking.defaults.searchcontainer.lbl.destination" /></label>
										<logic:notPresent name="searchContainerFilterVO" property="finalDestination">
											<ihtml:text property="destination" maxlength="4" value="" componentID="CMP_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_DESTINATION" />
										</logic:notPresent>
										<logic:present name="searchContainerFilterVO" property="finalDestination">
											<bean:define id="finalDest" name="searchContainerFilterVO" property="finalDestination" toScope="page"/>
											<ihtml:text property="destination" maxlength="4" value="<%=(String)finalDest%>" componentID="CMP_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_DESTINATION" />
										</logic:present>
										<div class="lovImg">
										<img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" border="0" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.destination.value,'Airport','1','destination','',0)"/>
									</div>
								</div>
								</div>
								<div class="ic-row" style="display:inline-block">
								<div class="ic-row ic-split-100">
									<div class="ic-input ic-split-13 inline_filedset marginT20 marginL5">
									 <logic:notPresent name="searchContainerFilterVO" property="transferStatus">
										<input type="checkbox" name="transferable" title="To Be Transfered">
									 </logic:notPresent>
									 <logic:present name="searchContainerFilterVO" property="transferStatus">
										<logic:equal name="searchContainerFilterVO" property="transferStatus" value="Y" >
											<input type="checkbox" name="transferable" title="To Be Transfered" checked>
										</logic:equal>
										<logic:equal name="searchContainerFilterVO" property="transferStatus" value="N" >
											<input type="checkbox" name="transferable" title="To Be Transfered">
										</logic:equal>
									 </logic:present>
									 <label><common:message key="mailtracking.defaults.searchcontainer.lbl.tobetransfered" /></label>
									</div>
									<div class="ic-input ic-split-13 inline_filedset marginT20">
									<logic:notPresent name="searchContainerFilterVO" property="notClosedFlag">
										<input type="checkbox" name="notClosedFlag" title="Not Closed">
									 </logic:notPresent>
									 <logic:present name="searchContainerFilterVO" property="notClosedFlag">
										<logic:equal name="searchContainerFilterVO" property="notClosedFlag" value="Y" >
											<input type="checkbox" name="notClosedFlag" title="To Be Transfered" checked>
										</logic:equal>
										<logic:equal name="searchContainerFilterVO" property="notClosedFlag" value="N" >
											<input type="checkbox" name="notClosedFlag" title="Not Closed">
										</logic:equal>
									 </logic:present>
										<label><common:message key="mailtracking.defaults.searchcontainer.lbl.notclosed" /></label>
									</div>
									<div class="ic-input ic-split-13 inline_filedset marginT20">
									 <logic:notPresent name="searchContainerFilterVO" property="mailAcceptedFlag">
										<input type="checkbox" name="mailAcceptedFlag" title="mail Accepted">
									 </logic:notPresent>
									 <logic:present name="searchContainerFilterVO" property="mailAcceptedFlag">
										<logic:equal name="searchContainerFilterVO" property="mailAcceptedFlag" value="Y" >
											<input type="checkbox" name="mailAcceptedFlag" title="Mail Accepted" checked>
										</logic:equal>
										<logic:equal name="searchContainerFilterVO" property="mailAcceptedFlag" value="N" >
											<input type="checkbox" name="mailAcceptedFlag" title="Mail Accepted">
										</logic:equal>
									 </logic:present>
										<label><common:message key="mailtracking.defaults.searchcontainer.lbl.mailaccepted" /></label>
									</div>
									<div class="ic-input ic-split-13 inline_filedset marginT20">
									<logic:notPresent name="searchContainerFilterVO" property="showEmptyContainer">
									<input type="checkbox" name="showEmptyContainer" title="Show Empty">
									</logic:notPresent>
									<logic:present name="searchContainerFilterVO" property="showEmptyContainer">

									<logic:equal name="searchContainerFilterVO" property="showEmptyContainer" value="Y" >
									<input type="checkbox" name="showEmptyContainer" title="Show Empty" checked>
									</logic:equal>
									<logic:equal name="searchContainerFilterVO" property="showEmptyContainer" value="N" >
									<input type="checkbox" name="showEmptyContainer" title="Show Empty">
									</logic:equal>
									</logic:present>
									<label><common:message key="mailtracking.defaults.searchcontainer.lbl.showemptycontainer" /></label>
									</div>
									<div class="ic-input ic-split-25 marginT10">
									&nbsp
									</div>
									
									<div class="ic-input ic-split-22 marginT15">
									<common:xgroup>
									<common:xsubgroup id="SINGAPORE_SPECIFIC">
									<label><common:message key="mailtracking.defaults.searchcontainer.lbl.subclassgroup" /></label>
									<% String subclsGroup="";%>
									<logic:notPresent name="searchContainerFilterVO" property="subclassGroup">
										<% subclsGroup="";%>
									</logic:notPresent>
									<logic:present name="searchContainerFilterVO" property="subclassGroup">
										<bean:define id="subclassGroup" name="searchContainerFilterVO" property="subclassGroup" toScope="page"/>
											<% subclsGroup=(String)subclassGroup; %>
									</logic:present>
									<ihtml:select property="subclassGroup" componentID="CMP_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_SUBCLASSGROUP" value="<%=(String)subclsGroup%>">
										<logic:present name="oneTimeValues">
											<logic:iterate id="oneTimeValue" name="oneTimeValues">
												<bean:define id="parameterCode" name="oneTimeValue" property="key" />
												<logic:equal name="parameterCode" value="mailtracking.defaults.subclassgroup">
												<bean:define id="parameterValues" name="oneTimeValue" property="value" />
													<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
														<logic:present name="parameterValue">
														<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
														<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
															<html:option value="<%=(String)fieldValue%>">
																		<%=(String)fieldDescription%>
															</html:option>
														</logic:present>
													</logic:iterate>
												</logic:equal>
											</logic:iterate>
										</logic:present>
									</ihtml:select>
									</common:xsubgroup>
									</common:xgroup >
									<div class="ic-button-container" style="margin-right:-8px">
										<ihtml:nbutton property="btnList" componentID="BTN_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_LIST" accesskey="L">
											<common:message key="mailtracking.defaults.searchcontainer.btn.list" />
										</ihtml:nbutton>

										<ihtml:nbutton property="btnClear"   componentID="BTN_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_CLEAR" accesskey="E" >
											<common:message key="mailtracking.defaults.searchcontainer.btn.clear" />
										</ihtml:nbutton>
									</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				</div>
				<div class="ic-main-container">
				 <a class="panel upArrow"  collapseFilter="true"  collapseFilterCallbackFun="callbackSearchContainer"  href="#"> </a> <!--Added by A-7929 for ICRD - 224586  -->
				<div class="ic-row">
				
				 </div>
					<div class="ic-row" style="display: inline-block">
							<%String lstPgNo = "";%>
							<logic:present name="SearchContainerForm" property="lastPageNum">
							<bean:define id="lastPg" name="SearchContainerForm" property="lastPageNum" scope="request"  toScope="page" />
							<%
							lstPgNo = (String) lastPg;
							%>
							</logic:present>
							<logic:present name="containerVOcollection" >
							<bean:define id="pageObj" name="containerVOcollection"  />
							<common:paginationTag pageURL="mailtracking.defaults.searchcontainer.listsearchcontainer.do"
							name="pageObj"
							display="label"
							labelStyleClass="iCargoResultsLabel"
							lastPageNum="<%=lstPgNo%>" />
							</logic:present>
							<logic:notPresent name="containerVOcollection" >
							&nbsp;
							</logic:notPresent>
						<div class="ic-button-container paddR5">
							<logic:present name="containerVOcollection" >
							<bean:define id="pageObj1" name="containerVOcollection"  />
							<common:paginationTag
							linkStyleClass="iCargoLink"
							disabledLinkStyleClass="iCargoLink"
							pageURL="javascript:submitPage('lastPageNum','displayPage')"
							name="pageObj1"
							display="pages"
							lastPageNum="<%=lstPgNo%>"
							exportToExcel="true"
							exportTableId="searchcontainer"
							exportAction="mailtracking.defaults.searchcontainer.listsearchcontainer.do"/>
							</logic:present>
							<logic:notPresent name="containerVOcollection" >
							&nbsp;
							</logic:notPresent>
						</div>
						</div>
					<div class="ic-row">
						<div class="tableContainer" id="div1"  style="height:366px" > <!--Modified by A-7929 for ICRD - 224586  -->
      <table class="fixed-header-table" id="searchcontainer">
          <thead>
            <tr class="iCargoTableHeadingLeft">
				<td width="2%"><input type="checkbox" name="masterContainer" onclick="updateHeaderCheckBox(this.form,this,this.form.selectContainer);"/><span></span></td>
				<td width="2%"><span>&nbsp;</span></td>
				<td width="8%"><common:message key="mailtracking.defaults.searchcontainer.lbl.containerno" /><span></span></td>
				<td width="7%"><common:message key="mailtracking.defaults.searchcontainer.lbl.fltcarr" /><span></span></td>
				<td width="8%"><common:message key="mailtracking.defaults.searchcontainer.lbl.flightdate" /><span></span></td>
				<td width="4%"><common:message key="mailtracking.defaults.searchcontainer.lbl.currentport" /><span></span></td>
				<td width="4%"><common:message key="mailtracking.defaults.searchcontainer.lbl.pol" /><span></span></td>
				<td width="4%"><common:message key="mailtracking.defaults.searchcontainer.lbl.pou" /><span></span></td>
				<td width="5%"><common:message key="mailtracking.defaults.searchcontainer.lbl.finaldest" /><span></span></td>
				<td width="8%"><common:message key="mailtracking.defaults.searchcontainer.lbl.assignedon" /><span></span></td>
				<td width="6%"><common:message key="mailtracking.defaults.searchcontainer.lbl.assignedby" /><span></span></td>
				<td width="10%"><common:message key="mailtracking.defaults.searchcontainer.lbl.onwardroute" /><span></span></td>
				<td width="5%"><common:message key="mailtracking.defaults.searchcontainer.lbl.numbags" /><span></span></td>
				<td width="5%"><common:message key="mailtracking.defaults.searchcontainer.lbl.wt" /><span></span></td>
			<common:xgroup>
			<common:xsubgroup id="SINGAPORE_SPECIFIC">
				<td width="5%"><common:message key="mailtracking.defaults.searchcontainer.lbl.noofdays" /><span></span></td>
				<td width="7%"><common:message key="mailtracking.defaults.searchcontainer.lbl.subclassgroup" /><span></span></td>
			</common:xsubgroup>
			</common:xgroup >
				<td width="10%"><common:message key="mailtracking.defaults.searchcontainer.lbl.remarks" /><span></span></td>
            </tr>
          </thead>
          <tbody>
          <logic:present name="containerVOcollection" >
          <% int i = 1;
             int count = 1;
          %>
	  <bean:define id="containerVOsColl" name="containerVOcollection" scope="page" toScope="page"/>

	    <% Collection<String> selectedrows = new ArrayList<String>(); %>

		 <logic:present name="SearchContainerForm" property="selectContainer" >

			<%
			String[] selectedRows = SearchContainerForm.getSelectContainer();
			for (int j = 0; j < selectedRows.length; j++) {
				selectedrows.add(selectedRows[j]);
			}
			%>

	    </logic:present>

	    <logic:iterate id="containerVO" name="containerVOsColl" indexId="rowCount">
	    <tr>


            <bean:define id="compcode" name="containerVO" property="companyCode" toScope="page"/>
	    	<% String primaryKey=(String)compcode+(String.valueOf(count));%>

	       <td class="iCargoTableDataTd ic-center">

			<%
				if(selectedrows.contains(primaryKey)){
			%>

				<input type="checkbox" name="selectContainer" value="<%=primaryKey%>" checked="true">
			<%
				}
				else{
			%>
				<input type="checkbox" name="selectContainer" value="<%=primaryKey%>" />

			<%
				}
			%>

	      </td>

              <td>
				<bean:define id="uldtype" name="containerVO" property="type" />
				<html:hidden property="uldType"	value="<%=(String) uldtype%>" /> <!--added by A-8149 for ICRD-270524-->
				<logic:equal name="containerVO" property="type" value="U">
					<logic:present name="containerVO" property="offloadCount">
						<logic:notEqual name="containerVO" property="offloadCount" value="0">

						<img align="right" id="offload_link_<%=String.valueOf(rowCount)%>" src="<%= request.getContextPath()%>/images/info.gif" width="16" height="16"  name="offload_link" onclick="prepareAttributes(event,this,'offload_','offloadDetails',500,90)"/>


						</logic:notEqual>
						<logic:equal name="containerVO" property="offloadCount" value="0">
						 &nbsp;
						</logic:equal>
					</logic:present>
				</logic:equal>
				<div style="display:none" id="offload_<%=String.valueOf(rowCount)%>" name="offloadDetails" tabindex="0">
				<table  class="fixed-header-table">
				<thead>
					<tr>
						<td class="iCargoTableDataRow2" colspan="2" style="text-align:left"><b>
						<common:message key="mailtracking.defaults.searchcontainer.lbl.offloadinfo"/></b></td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="iCargoTableDataRow2" style="text-align:left">
						<common:message key="mailtracking.defaults.searchcontainer.lbl.nooftimesoffloaded"/></td>
						<td style="text-align:left;height:5%">
						<logic:present name="containerVO" property="offloadCount">
						<bean:write name="containerVO" property="offloadCount"/>
						</logic:present></td>
					</tr>
					<tr>
					<td  class="iCargoTableHeaderLabel">
					<common:message key="mailtracking.defaults.searchcontainer.lbl.offloadflightinfo"/></td>
					<td class="iCargoTableDataTd" style="text-align:left;height:5%">
					<table>
					<tbody>
					<logic:present name="containerVO" property="offloadedInfo">
					 <bean:define id="offloadedInfoColl" name="containerVO"  property="offloadedInfo" scope="page" toScope="page"/>
					 <logic:iterate id="offloadedInfo" name="offloadedInfoColl" indexId="rowCount">
					<tr>
					<td class="iCargoTableDataTd" style="text-align:left;height:5%">
							<bean:write name="offloadedInfo"/>
					</td>
					</tr>
					 </logic:iterate>
					</logic:present>
					</tbody>
					</table>
					</td>
					</tr>
				</tbody>
				</table>
				</div>
				</td>
				 <td>
				<logic:present name="containerVO" property="paBuiltFlag">
					<logic:equal name="containerVO" property="paBuiltFlag" value="Y">
						<bean:write name="containerVO" property="containerNumber"/>
						<common:message key="mailtracking.defaults.searchcontainer.lbl.shipperBuild" />
					</logic:equal>
					<logic:equal name="containerVO" property="paBuiltFlag" value="N">
						<bean:write name="containerVO" property="containerNumber"/>
					</logic:equal>
				</logic:present>
				<logic:notPresent name="containerVO" property="paBuiltFlag">
					<bean:write name="containerVO" property="containerNumber"/>
				</logic:notPresent>
		    </td>
              <td><bean:write name="containerVO" property="carrierCode"/>
			<logic:notEqual name="containerVO" property="flightNumber" value="-1">
				<bean:write name="containerVO" property="flightNumber"/>
			</logic:notEqual>
              </td>
              <td>
		  <logic:present name="containerVO" property="flightDate">
			<bean:define id ="fltDate" name = "containerVO" property="flightDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
				<%String fltDt=TimeConvertor.toStringFormat(fltDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
				<%=fltDt%>
		  </logic:present>
              </td>
              <td>
                  <logic:notEqual name="containerVO" property="arrivedStatus" value="Y">
                      <bean:write name="containerVO" property="assignedPort"/>
		  </logic:notEqual>
		  <logic:equal name="containerVO" property="arrivedStatus" value="Y">
		      <bean:write name="containerVO" property="pou"/>
		  </logic:equal>
	      </td>
              <td><bean:write name="containerVO" property="assignedPort"/></td>
              <td><bean:write name="containerVO" property="pou"/></td>

              <td><bean:write name="containerVO" property="finalDestination"/></td>
	      <td>
		  <logic:present name="containerVO" property="assignedDate">
			<bean:define id ="assignDate" name = "containerVO" property="assignedDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
			<%String asgnDate=TimeConvertor.toStringFormat(assignDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
				<%=asgnDate%>
		  </logic:present>
	      </td>
              <td><bean:write name="containerVO" property="assignedUser"/></td>
              <td><bean:write name="containerVO" property="onwardRoute"/></td>
              <td><div align="right"><bean:write name="containerVO" property="bags" format="####"/></div></td>
	      	  <td><div align="right"> <common:write name="containerVO" property="weight" unitFormatting="true" />
			  </div></td>
			<common:xgroup>
			 <common:xsubgroup id="SINGAPORE_SPECIFIC">
			  <td><div align="right"><bean:write name="containerVO" property="noOfDaysInCurrentLoc"/></div></td>
			  <td><div align="center"><bean:write name="containerVO" property="subclassGroup"/></div></td>
			 </common:xsubgroup>
			</common:xgroup >
              <td><bean:write name="containerVO" property="remarks"/></td>
            </tr>
            <%if(i == 1){
                 i = 2;
              }else{
                 i = 1;
              }
              count++;
            %>
            </logic:iterate>
           </logic:present>
          </tbody>
        </table>
      </div>
					</div>
				</div>
				<div class="ic-foot-container">
					<div class="ic-button-container paddR5">
					<ihtml:nbutton property="btnViewMailBag" componentID="BTN_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_VIEWMAILBAG" accesskey="V">
							<common:message key="mailtracking.defaults.searchcontainer.btn.viewmailbag" />
				   </ihtml:nbutton>

				   <ihtml:nbutton property="btnTransfer" componentID="BTN_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_TRANSFER" accesskey="T">
							<common:message key="mailtracking.defaults.searchcontainer.btn.transfer" />
				   </ihtml:nbutton>

				   <ihtml:nbutton property="btnOffload" componentID="BTN_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_OFFLOAD" accesskey="F">
							<common:message key="mailtracking.defaults.searchcontainer.btn.offload" />
				   </ihtml:nbutton>

				   <ihtml:nbutton property="btnReassign" componentID="BTN_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_REASSIGN" accesskey="N">
							<common:message key="mailtracking.defaults.searchcontainer.btn.reassign" />
				   </ihtml:nbutton>

				   <ihtml:nbutton property="btnUnassign" componentID="BTN_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_UNASSIGN" accesskey="U">
							<common:message key="mailtracking.defaults.searchcontainer.btn.unassign" />
				   </ihtml:nbutton>

				   <ihtml:nbutton property="btnClose" componentID="BTN_MAILTRACKING_DEFAULTS_SEARCHCONTAINER_CLOSE" accesskey="O">
							<common:message key="mailtracking.defaults.searchcontainer.btn.close" />
				   </ihtml:nbutton>
					</div>
				</div>
</div>





</ihtml:form>

</div>


	</body>
</html:html>
