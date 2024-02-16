<%--

* Project	 		:  iCargo
* Module Code & Name:  Mailtracking
* File Name			:  DSNEnquiry.jsp
* Date				:  04-July-2006
* Author(s)			:  A-1861

 --%>


 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>
 <%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
 <%@ page import="java.util.Collection"%>
 <%@ page import="java.util.ArrayList"%>
 <%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
 

	 <html:html>

<head>
		
	


<title><common:message bundle="mailBagEnquiryResources"
		key="mailtracking.defaults.mailbagenquiry.lbl.pagetitle" /></title>
<meta name="decorator" content="mainpanelrestyledui">
<common:include type="script"
	src="/js/mail/operations/DSNEnquiry_Script.jsp" />
</head>

<body>
	
	

<div class="iCargoContent" id="pageDiv" style="width:100%;overflow:auto;">

<ihtml:form action="/mailtracking.defaults.dsnenquiry.screenload.do">

<bean:define id="form"
	name="DsnEnquiryForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DsnEnquiryForm"
	toScope="page"
	scope="request"/>

<business:sessionBean id="containerTypes"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.dsnEnquiry"
		  method="get"
		  attribute="containerTypes" />

<business:sessionBean id="operationTypes"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.dsnEnquiry"
		  method="get"
		  attribute="operationTypes" />

<business:sessionBean id="mailCategory"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.dsnEnquiry"
		  method="get"
		  attribute="mailCategory" />

<business:sessionBean id="mailClass"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.dsnEnquiry"
		  method="get"
		  attribute="mailClass" />

<business:sessionBean id="despatchDetailsVOPage"
		  moduleName="mail.operations"
		  screenID="mailtracking.defaults.dsnEnquiry"
		  method="get"
		  attribute="despatchDetailsVOs" />
 <business:sessionBean id="filterVO"
 		  moduleName="mail.operations"
 		  screenID="mailtracking.defaults.dsnEnquiry"
 		  method="get"
		  attribute="dsnEnquiryFilterVO" />

<logic:present name="despatchDetailsVOPage">
	<bean:define id="despatchDetailsPage" name="despatchDetailsVOPage" toScope="page"/>
</logic:present>

<ihtml:hidden property="screenStatusFlag" />
<ihtml:hidden property="lastPageNum" />
<ihtml:hidden property="displayPage" />
<ihtml:hidden property="status" />
<ihtml:hidden property="loginAirport" />
<ihtml:hidden  property="currentDialogOption" />
<ihtml:hidden property="currentDialogId" />
<ihtml:hidden property="reList" />
<input type=hidden name="mySearchEnabled" />

			
<div class="ic-content-main">
	
	
	<div class="ic-head-container">
		<span class="ic-page-title ic-display-none"> <common:message
			key="mailtracking.defaults.mailbagenquiry.lbl.mailbagenquiry" />
		</span>
	
		<div class="ic-filter-panel">
		<div class="ic-row">
				<h4><common:message
					key="mailtracking.defaults.dsnenquiry.lbl.search" /></h4>
			</div>
			<div class="ic-row ">
				<div class="ic-input ic-split-20 ">
					<label> <common:message
						key="mailtracking.defaults.dsnenquiry.lbl.dsn" />
					</label>
					<ihtml:text property="dsn" maxlength="4"
						componentID="TXT_MAILTRACKING_DEFAULTS_DSNENQUIRY_DSN" />
				</div>
				<div class="ic-input ic-split-20 ">
					<label> <common:message
						key="mailtracking.defaults.dsnenquiry.lbl.consignmentno" />
					</label>
					<ihtml:text property="consignmentNo" maxlength="13"
						componentID="TXT_MAILTRACKING_DEFAULTS_DSNENQUIRY_CONSIGNNO" />
				</div>
				<div class="ic-input ic-split-20 ">
					<label> <common:message
						key="mailtracking.defaults.dsnenquiry.lbl.pacode" />
					</label>
					<ihtml:text property="postalAuthorityCode" maxlength="5"
						componentID="TXT_MAILTRACKING_DEFAULTS_DSNENQUIRY_PACODE" />
					<div class="lovImg"> <img id="paLOV"
						src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" value="pacode"/></div>
				</div>
				<div class="ic-input ic-split-20 ">
					<label> <common:message
						key="mailtracking.defaults.dsnenquiry.lbl.origin" />
					</label>
					<ihtml:text property="originCity" maxlength="4"
						componentID="TXT_MAILTRACKING_DEFAULTS_DSNENQUIRY_ORG" />
					<div class="lovImg"> <img id="originLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"value="org"/></div>
				</div>
				<div class="ic-input ic-split-20 ">
					<label> <common:message
						key="mailtracking.defaults.dsnenquiry.lbl.dstn" />
					</label>
					<ihtml:text property="destnCity" maxlength="4"
						componentID="TXT_MAILTRACKING_DEFAULTS_DSNENQUIRY_DSTN" />
					<div class="lovImg"> <img id="destinationLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" value="dstn"/></div>
				</div>
				<div class="ic-input ic-split-100 "></div>
				<div class="ic-input ic-split-20 ">
					<label> <common:message
						key="mailtracking.defaults.dsnenquiry.lbl.category" />
					</label>
					<ihtml:select property="category" componentID="COMBO_MAILTRACKING_DEFAULTS_DSNENQUIRY_CATAGORY" style="width:150px">
						<logic:present name="mailCategory">
							<bean:define id="category" name="mailCategory" toScope="page"/>
							<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
							<logic:iterate id="onetmvo" name="category">
								<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
								<bean:define id="value" name="onetimevo" property="fieldValue"/>
								<bean:define id="desc" name="onetimevo" property="fieldDescription"/>
								<html:option value="<%= value.toString() %>"><%= desc %></html:option>
							</logic:iterate>
						</logic:present>
					</ihtml:select>
				</div>
				<div class="ic-input ic-split-20 ">
					<label><common:message key="mailtracking.defaults.dsnenquiry.lbl.mailclas" /></label> 
					<ihtml:select property="mailClass" componentID="COMBO_MAILTRACKING_DEFAULTS_DSNENQUIRY_MAILCLAS"  style="width:85px">
						<logic:present name="mailClass">
							<bean:define id="mailclass" name="mailClass" toScope="page"/>
							<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
							<logic:iterate id="onetmvo" name="mailclass">
								<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
								<bean:define id="value" name="onetimevo" property="fieldValue"/>
								<bean:define id="desc" name="onetimevo" property="fieldDescription"/>
								<html:option value="<%= value.toString() %>"><%= desc %></html:option>
							</logic:iterate>
						</logic:present>
					</ihtml:select>
				</div>
				<div class="ic-input ic-split-40">
					<fieldset class="ic-field-set">
						<legend><common:message key="mailtracking.defaults.dsnenquiry.lbl.acceptanceDetails"/></legend>
						<div class="ic-input ic-mandatory ic-split-50">
							<label>
								<common:message key="mailtracking.defaults.dsnenquiry.lbl.fromdat"/>
							</label>
							<logic:present name="filterVO" property="fromDate">
								<bean:define id="listFilter"
									name="filterVO"  type="com.ibsplc.icargo.business.mail.operations.vo.DSNEnquiryFilterVO"
									toScope="page" />
								<%
									String fDate = "";
								   if(listFilter.getFromDate() != null) {
										fDate = TimeConvertor.toStringFormat(
											listFilter.getFromDate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
									}
								%>
								<ibusiness:calendar type="image" id="incalender1" componentID="CALENDAR_MAILTRACKING_DEFAULTS_DSNENQUIRY_FROMDAT" property="fromDate"/>
							</logic:present>
							<logic:notPresent name="filterVO" property="fromDate">
								<ibusiness:calendar property="fromDate" type="image" id="incalender1"
									value="<%=form.getFromDate()%>" componentID="CALENDAR_MAILTRACKING_DEFAULTS_DSNENQUIRY_FROMDAT" onblur="autoFillDate(this)"/>
							</logic:notPresent>
						</div>
						<div class="ic-input ic-mandatory ic-split-50">
							<label>
								<common:message key="mailtracking.defaults.dsnenquiry.lbl.todat"/>
							</label>
							<logic:present name="filterVO" property="toDate">
								<bean:define id="listFilter"
									name="filterVO"  type="com.ibsplc.icargo.business.mail.operations.vo.DSNEnquiryFilterVO"
									toScope="page" />
								<%
									String fDate = "";
								   if(listFilter.getToDate() != null) {
										fDate = TimeConvertor.toStringFormat(
											listFilter.getToDate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
									}
								%>
								<ibusiness:calendar type="image" id="incalender2" componentID="CALENDAR_MAILTRACKING_DEFAULTS_DSNENQUIRY_FROMDAT" property="toDate"/>
							</logic:present>
							<logic:notPresent name="filterVO" property="toDate">
								<ibusiness:calendar property="toDate" type="image" id="incalender2"
									value="<%=form.getToDate()%>" componentID="CALENDAR_MAILTRACKING_DEFAULTS_DSNENQUIRY_TODAT" onblur="autoFillDate(this)"/>
							</logic:notPresent>
						</div>
					</fieldset>	
				</div>
				<div class="ic-input ic-split-20">
					<ihtml:checkbox property="capNotAcp" id="capNotAcp" componentID="CHK_MAILTRACKING_DEFAULTS_DSNENQUIRY_CAP_NOT_ACP" />
					<common:message key="mailtracking.defaults.dsnenquiry.lbl.capturednotaccepted" />
				</div>
				<div class="ic-input ic-split-100 "></div>
				<div class="ic-input ic-mandatory ic-split-15">
					<label><common:message key="mailtracking.defaults.dsnenquiry.lbl.flightno" /></label>
					<ibusiness:flightnumber carrierCodeProperty="flightCarrierCode" id="flight"
						flightCodeProperty="flightNumber" carriercodevalue="<%=form.getFlightCarrierCode()%>"
						flightcodevalue="<%=form.getFlightNumber()%>" carrierCodeMaxlength="3"
						flightCodeMaxlength="5"	componentID="FLT_MAILTRACKING_DEFAULTS_DSNENQUIRY_FLIGHT"/>
				</div>
				<div class="ic-input ic-split-16">
					<label><common:message key="mailtracking.defaults.dsnenquiry.lbl.flightdate" /></label>
						<ibusiness:calendar property="flightDate" type="image" id="incalender3"
						value="<%=form.getFlightDate()%>" componentID="FLT_MAILTRACKING_DEFAULTS_DSNENQUIRY_FLIGHTDATE" onblur="autoFillDate(this)"/>
				</div>
				<div class="ic-input ic-split-17">
					<label><common:message key="mailtracking.defaults.dsnenquiry.lbl.operationtype" /></label>
					<logic:empty name="form" property="operationType" >
					<ihtml:select property="operationType" value="O" componentID="COMBO_MAILTRACKING_DEFAULTS_DSNENQUIRY_OPERNTYPE" style="width:72px;"  >
						<logic:present name="operationTypes">
						<bean:define id="operationtypes" name="operationTypes" toScope="page"/>
							<logic:iterate id="onetmvo" name="operationtypes">
								<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
								<bean:define id="value" name="onetimevo" property="fieldValue"/>
								<bean:define id="desc" name="onetimevo" property="fieldDescription"/>
								<html:option value="<%= value.toString() %>"><%= desc %></html:option>
							</logic:iterate>
						</logic:present>
					</ihtml:select>
					</logic:empty>
					<logic:notEmpty name="form" property="operationType" >
						<ihtml:select property="operationType" componentID="COMBO_MAILTRACKING_DEFAULTS_DSNENQUIRY_OPERNTYPE" style="width:72px;"  >
							<logic:present name="operationTypes">
							<bean:define id="operationtypes" name="operationTypes" toScope="page"/>
								<logic:iterate id="onetmvo" name="operationtypes">
									<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
									<bean:define id="value" name="onetimevo" property="fieldValue"/>
									<bean:define id="desc" name="onetimevo" property="fieldDescription"/>
									<html:option value="<%= value.toString() %>"><%= desc %></html:option>
								</logic:iterate>
							</logic:present>
						</ihtml:select>
					</logic:notEmpty>
					<bean:define id="operationtypes" name="operationTypes" toScope="page"/>
				</div>
				<div class="ic-input ic-split-11">
					<label><common:message key="mailtracking.defaults.dsnenquiry.lbl.port" /></label>
					<ihtml:text property="port" componentID="TXT_MAILTRACKING_DEFAULTS_DSNENQUIRY_PORT" maxlength="4" />
					<div class="lovImg"> <img  id="airportlov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"></div>
				</div>
				<div class="ic-input ic-split-14">
					<label><common:message key="mailtracking.defaults.dsnenquiry.lbl.containertype" /></label>
					<ihtml:select property="containerType" componentID="COMBO_MAILTRACKING_DEFAULTS_DSNENQUIRY_CONTTYPE" style="width:60px;"  onchange="selectCombo();">
						<logic:present name="containerTypes">
							<bean:define id="containertypes" name="containerTypes" toScope="page"/>
							<html:option value="ALL">All</html:option>
							<logic:iterate id="onetmvo" name="containertypes">
								<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
								<bean:define id="value" name="onetimevo" property="fieldValue"/>
								<bean:define id="desc" name="onetimevo" property="fieldDescription"/>
								<html:option value="<%= value.toString() %>"><%= desc %></html:option>
							</logic:iterate>
						</logic:present>
					</ihtml:select>
				</div>	
				<div class="ic-input ic-split-13">
					<label><common:message key="mailtracking.defaults.dsnenquiry.lbl.uldNo"/></label>
					<ibusiness:uld id="uldNo" uldProperty="uldNo" style="text-transform:uppercase;" componentID="TXT_MAILTRACKING_DEFAULTS_DSNENQUIRY_ULDNO"  maxlength="13"/>
				</div>
				<div class="ic-input ic-split-6">
					<ihtml:checkbox property="plt" componentID="CHK_MAILTRACKING_DEFAULTS_DSNENQUIRY_PLT" />
					<common:message key="mailtracking.defaults.dsnenquiry.lbl.plt" />
				</div>
				<div class="ic-input ic-split-6">
					<logic:notPresent name="DsnEnquiryForm" property="transit">
						<ihtml:checkbox property="transit" componentID="CHK_MAILTRACKING_DEFAULTS_DSNENQUIRY_TRANSIT"  />
					</logic:notPresent>
					<logic:present name="DsnEnquiryForm" property="transit">
						<ihtml:checkbox property="transit" value="Y" componentID="CHK_MAILTRACKING_DEFAULTS_DSNENQUIRY_TRANSIT" />
					</logic:present>
					<common:message key="mailtracking.defaults.dsnenquiry.lbl.transit" />
				</div>
				<div class="ic-input ic-split-100 "></div>
				<div class="ic-button-container">
					<ihtml:nbutton property="btList" componentID="BTN_MAILTRACKING_DEFAULTS_DSNENQUIRY_LIST" accesskey="L">
						<common:message key="mailtracking.defaults.dsnenquiry.btn.list" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btClear" componentID="BTN_MAILTRACKING_DEFAULTS_DSNENQUIRY_CLEAR" accesskey="E" >
						<common:message key="mailtracking.defaults.dsnenquiry.btn.clear" />
					</ihtml:nbutton>
				</div>
			</div>	
		</div>	
	</div>	
	<div class="ic-main-container">
		<div class="ic-row">
			<div>
				<h4>
					<common:message
						key="mailtracking.defaults.dsnenquiry.lbl.dsnDetails" />
				</h4>
			</div>
		</div>
			<div class="ic-row">
			<div class="ic-col-30">
					<%
						String lstPgNo = "";
					%>
					<logic:present name="DsnEnquiryForm" property="lastPageNum">
						<bean:define id="lastPg" name="DsnEnquiryForm" property="lastPageNum" scope="request" toScope="page" />
						<%
							lstPgNo = (String) lastPg;
						%>
					</logic:present>
					<logic:present name="despatchDetailsPage" >
						<bean:define id="pageObj" name="despatchDetailsPage"   toScope="page" />
						<common:paginationTag pageURL="mailtracking.defaults.dsnenquiry.listdetails.do?countTotalFlag=Y"
							name="pageObj"
							display="label"
							labelStyleClass="iCargoResultsLabel"
							lastPageNum="<%=lstPgNo%>" />
					</logic:present>
					</div>
					<div class="ic-col-70">
					<div class="ic-button-container">
					<logic:present name="despatchDetailsPage" >
						<bean:define id="pageObj1" name="despatchDetailsPage"   toScope="page" />
							<common:paginationTag
							linkStyleClass="iCargoLink"
							disabledLinkStyleClass="iCargoLink"
							pageURL="javascript:submitPage('lastPageNum','displayPage')"
							name="pageObj1"
							display="pages"
							lastPageNum="<%=lstPgNo%>"
							exportToExcel="true"
							exportTableId="dsnenquiry"
							exportAction="mailtracking.defaults.dsnenquiry.listdetails.do?countTotalFlag=Y"/>
					</logic:present>
					<logic:notPresent name="mailbagsPage">
							&nbsp;
					</logic:notPresent>
				</div>
		</div>
		</div>
		<div id="despatchEnquiryTable" >
			<div class="tableContainer" id="div1" style="height: 450px">
				<table  id="dsnenquiry"
								class="fixed-header-table" >
					<thead>
						<tr class="ic-th-all">
										<th style="width: 3%" />
										<th style="width: 6%" />
										<th style="width: 8%" />
										<th style="width: 8%" />

										<th style="width: 12%" />
										<th style="width: 8%" />
										<th style="width: 6%" />
										<th style="width: 10%" />
										<th style="width: 10%" />
										<th style="width: 10%" />
										<th style="width: 8%" />
										<th style="width: 10%" />
										<th style="width: 10%" />
										<th style="width: 7%" />
										<th style="width: 7%" />
										<th style="width: 7%" />
										<th style="width: 7%" />
									</tr>
						<tr>
						   <td class="iCargoTableHeaderLabel"><div align="center">
							 <input name="checkAll" type="checkbox" value="" /><div><span></span>
						   </td>
						   <td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.dsn" /><span></span></td>
						   <td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.ooe" /><span></span></td>
						   <td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.doe" /><span></span></td>
						   <td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.class" /><span></span></td>
						   <td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.SC" /><span></span></td>
						   <td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.year" /><span></span></td>
						   <td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.category" /><span></span></td>
						   <td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.uld" /><span></span></td>
						   <td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.consignmentno" /><span></span></td>
						   <td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.pacode" /><span></span></td>
						   <td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.fltordestn" /><span></span></td>
						   <td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.flightdate" /><span></span></td>
						   <td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.airport" /><span></span></td>
						   <td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.plt" /><span></span></td>
						   <td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.wt" /><span></span></td>
						   <td class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.dsnenquiry.lbl.noOfBags" /><span></span></td>
						</tr>
					</thead>
					<tbody>
						<logic:present name="despatchDetailsPage" >
						<%
						int i = 1;
							int count = 1;
						%>
					  <bean:define id="despatchDetailVOs" name="despatchDetailsPage" scope="page" toScope="page"/>
					   <% Collection<String> selectedrows = new ArrayList<String>(); %>

						 <logic:present name="DsnEnquiryForm" property="subCheck" >

							<%
							String[] selectedRows = form.getSubCheck();
							for (int j = 0; j < selectedRows.length; j++) {

								selectedrows.add(selectedRows[j]);
							}
							%>

						</logic:present>
						<logic:iterate id="despatchDetailVO" name="despatchDetailVOs" indexId="rowid">
						<common:rowColorTag index="rowid">
									<tr bgcolor="<%=color%>">
						  
						<td width="33"  align="center" class="iCargoTableDataTd">
						<div align="center">
						<%
							if(selectedrows.contains(String.valueOf(rowid))){
						%>

							<input type="checkbox" name="subCheck" value="<%= rowid.toString() %>" checked="true">
						<%
							}
							else{
						%>
							<input type="checkbox" name="subCheck" value="<%= rowid.toString() %>" />

						<%
							}
						%>
						  </div></td>
						<td width="69" class="iCargoTableDataTd" style="text-align:right"><bean:write name="despatchDetailVO" property="dsn"/></td>
						<td width="79" class="iCargoTableDataTd"><bean:write name="despatchDetailVO" property="originOfficeOfExchange"/></td>
						<td width="79" class="iCargoTableDataTd"><bean:write name="despatchDetailVO" property="destinationOfficeOfExchange"/></td>
						<td width="76" class="iCargoTableDataTd">

							<logic:present name="despatchDetailVO" property="mailClass">
							<bean:define id="mailsubclass" name="despatchDetailVO" property="mailClass" toScope="page"/>

								<logic:present name="mailClass">
								<bean:define id="mailclass" name="mailClass" toScope="page"/>

									<logic:iterate id="onetmvo" name="mailclass">
										<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
										<bean:define id="onetimedesc" name="onetimevo" property="fieldDescription"/>

										<logic:match name="onetimevo" property="fieldValue" value="<%= mailsubclass.toString() %>">
											<%= onetimedesc %>
										</logic:match>

									</logic:iterate>
								</logic:present>
							</logic:present>

						</td>

						<td width="79" class="iCargoTableDataTd">


						 <% String subclassValue = ""; %>
						  <logic:notPresent name="despatchDetailVO" property="mailSubclass">
							&nbsp;
						</logic:notPresent>
						<logic:present name="despatchDetailVO" property="mailSubclass">
						<bean:define id="despatchSubclass" name="despatchDetailVO" property="mailSubclass" toScope="page"/>
						<% subclassValue = (String) despatchSubclass;
									   int arrays=subclassValue.indexOf("_");
									   if(arrays==-1){%>

							<bean:write name="despatchDetailVO" property="mailSubclass"/>
							<%}else{%>
							&nbsp;
							<%}%>
						</logic:present>

						</td>
						<td width="60" class="iCargoTableDataTd" style="text-align:right">

							<bean:define id="year" name="despatchDetailVO" property="year" toScope="page"/>
							<%= year %>

						</td>
						<td width="79" class="iCargoTableDataTd">

							<logic:present name="despatchDetailVO" property="mailCategoryCode">
							<bean:define id="categorycode" name="despatchDetailVO" property="mailCategoryCode" toScope="page"/>

								<logic:present name="mailCategory">
								<bean:define id="mailcategory" name="mailCategory" toScope="page"/>

									<logic:iterate id="onetmvo" name="mailcategory">
										<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
										<bean:define id="onetimedesc" name="onetimevo" property="fieldDescription"/>

										<logic:equal name="onetimevo" property="fieldValue" value="<%= categorycode.toString() %>">
											<%= onetimedesc %>
										</logic:equal>

									</logic:iterate>
								</logic:present>
							</logic:present>

						</td>
						<td width="120" class="iCargoTableDataTd">
						 <logic:present name="despatchDetailVO" property="paBuiltFlag">
							 <logic:equal name="despatchDetailVO" property="paBuiltFlag" value="Y">
								<bean:write name="despatchDetailVO" property="containerNumber"/>
								<common:message key="mailtracking.defaults.dsnenquiry.lbl.sb"/>		      		
							 </logic:equal>
							 <logic:equal name="despatchDetailVO" property="paBuiltFlag" value="N">
								<bean:write name="despatchDetailVO" property="containerNumber"/>	      		
							 </logic:equal>		
						 </logic:present>
						 <logic:notPresent name="despatchDetailVO" property="paBuiltFlag">
							 <bean:write name="despatchDetailVO" property="uldNumber"/>
						 </logic:notPresent>    
						</td>
						<td width="89" class="iCargoTableDataTd"><bean:write name="despatchDetailVO" property="consignmentNumber"/></td>
						<td width="79" class="iCargoTableDataTd"><bean:write name="despatchDetailVO" property="paCode"/></td>
						<td width="90" class="iCargoTableDataTd">
							<bean:write name="despatchDetailVO" property="carrierCode"/>

							<logic:present name="despatchDetailVO" property="flightDate">
								<bean:define id="seqno" name="despatchDetailVO" property="flightSequenceNumber" toScope="page"/>
								<logic:notEqual name="seqno" value="-1">

								<bean:write name="despatchDetailVO" property="flightNumber"/>

								</logic:notEqual>
							</logic:present>

						</td>
						<td width="180" class="iCargoTableDataTd">

							<logic:present name="despatchDetailVO" property="flightDate">
								<bean:define id="seqno" name="despatchDetailVO" property="flightSequenceNumber" toScope="page"/>
								<logic:notEqual name="seqno" value="-1">

								<bean:define id="flightDate" name="despatchDetailVO" property="flightDate"/>
								<%=((LocalDate)flightDate).toDisplayFormat("dd-MMM-yyyy")%>

								</logic:notEqual>
							</logic:present>

						</td>
						<td width="71" class="iCargoTableDataTd">
							<logic:present name="despatchDetailVO" property="airportCode">
								<bean:define id="airport" name="despatchDetailVO" property="airportCode" toScope="page"/>
								<ihtml:hidden property="dsnPort"  value = "<%=(String)airport%>"/>
								<bean:write name="despatchDetailVO" property="airportCode"/>
							</logic:present>
								</td>
						<td width="33"  align="center" class="iCargoTableDataTd">
						  <div align="center">

							<logic:present name="despatchDetailVO" property="pltEnabledFlag">
								<logic:equal name="despatchDetailVO" property="pltEnabledFlag" value="true">
									<!--<input name="pltFlag" type="checkbox" checked="true" disabled/>-->
									<ihtml:hidden property="contNumber"  value = "true"/>
									<img id="isPltEnabled" src="<%=request.getContextPath()%>/images/icon_on.gif" />
								</logic:equal>
								<logic:equal name="despatchDetailVO" property="pltEnabledFlag" value="false">
									<input name="pltFlag" type="checkbox" disabled/>-->
									<!--<ihtml:hidden property="contNumber"  value = "false"/> -->
									<img id="isnotPltEnabled" src="<%=request.getContextPath()%>/images/icon_off.gif" />
								</logic:equal> 
							</logic:present>
							<logic:notPresent name="despatchDetailVO" property="pltEnabledFlag">
							<img id="isnotPltEnabled" src="<%=request.getContextPath()%>/images/icon_off.gif" />
							</logic:notPresent>


						  </div></td>
						<td width="70" class="iCargoTableDataTd" style="text-align:right">

							<logic:present name="despatchDetailVO" property="flightDate">
								<logic:equal name="form" property="operationType" value="I">
									<logic:equal name="despatchDetailVO" property="pltEnabledFlag" value="true">
									  <common:write name="despatchDetailVO" property="acceptedWeight" unitFormatting="true" />
										
									</logic:equal>
									<logic:notEqual name="despatchDetailVO" property="pltEnabledFlag" value="true">
									<common:write name="despatchDetailVO" property="receivedWeight" unitFormatting="true" />
									</logic:notEqual>
								</logic:equal>
								<logic:notEqual name="form" property="operationType" value="I">
						 <common:write name="despatchDetailVO" property="acceptedWeight" unitFormatting="true" />

								</logic:notEqual>
							</logic:present>
							<logic:notPresent name="despatchDetailVO" property="flightDate">
									  <common:write name="despatchDetailVO" property="acceptedWeight" unitFormatting="true" />
							</logic:notPresent>
						</td>
						<td width="94" class="iCargoTableDataTd" style="text-align:right">

							<logic:present name="despatchDetailVO" property="flightDate">
								<logic:equal name="form" property="operationType" value="I">
									<logic:equal name="despatchDetailVO" property="pltEnabledFlag" value="true">
										<bean:write name="despatchDetailVO" property="acceptedBags"/>
									</logic:equal>
									<logic:notEqual name="despatchDetailVO" property="pltEnabledFlag" value="true">
										<bean:write name="despatchDetailVO" property="receivedBags"/>
									</logic:notEqual>
								</logic:equal>
								<logic:notEqual name="form" property="operationType" value="I">
									<bean:write name="despatchDetailVO" property="acceptedBags"/>
								</logic:notEqual>
							</logic:present>
							<logic:notPresent name="despatchDetailVO" property="flightDate">
								<bean:write name="despatchDetailVO" property="acceptedBags"/>
							</logic:notPresent>

						</td>
					  </tr>
							</common:rowColorTag>
						</logic:iterate>
					</logic:present>

					</tbody>
			  </table>
			</div>
		</div>	
	</div>
</div>
</div>	
<div class="ic-foot-container">
	<div class="ic-button-container paddR5">
			  <ihtml:nbutton property="btTransferDsn" componentID="BTN_MAILTRACKING_DEFAULTS_DSNENQUIRY_TRANSFERDSN" accesskey="T">
				<common:message key="mailtracking.defaults.dsnenquiry.btn.transferdsn" />
			  </ihtml:nbutton>
			  <ihtml:nbutton property="btReturnDsn" componentID="BTN_MAILTRACKING_DEFAULTS_DSNENQUIRY_RETURNDSN" accesskey="R">
				<common:message key="mailtracking.defaults.dsnenquiry.btn.returndsn" />
			  </ihtml:nbutton>

			  <ihtml:nbutton property="btOffload" componentID="BTN_MAILTRACKING_DEFAULTS_DSNENQUIRY_OFFLOAD" accesskey="F">
			  	<common:message key="mailtracking.defaults.dsnenquiry.btn.offload" />
			  </ihtml:nbutton>

			  <ihtml:nbutton property="btViewMailBags" componentID="BTN_MAILTRACKING_DEFAULTS_DSNENQUIRY_VIEMAILBAGS" accesskey="B">
			  	<common:message key="mailtracking.defaults.dsnenquiry.btn.viewMailBags" />
			  </ihtml:nbutton>

			  <ihtml:nbutton property="btViewDamage" componentID="BTN_MAILTRACKING_DEFAULTS_DSNENQUIRY_VIEDAMAGE" accesskey="V">
			  	<common:message key="mailtracking.defaults.dsnenquiry.btn.viewdamage" />
			  </ihtml:nbutton>

			  <ihtml:nbutton property="btReassign" componentID="BTN_MAILTRACKING_DEFAULTS_DSNENQUIRY_REASSIGN" accesskey="N">
			  	<common:message key="mailtracking.defaults.dsnenquiry.btn.reassign" />
			  </ihtml:nbutton>

			  <ihtml:nbutton property="btClose" componentID="BTN_MAILTRACKING_DEFAULTS_DSNENQUIRY_CLOSE" accesskey="O">
			  	<common:message key="mailtracking.defaults.dsnenquiry.btn.close" />
			  </ihtml:nbutton>
		</div>
 </div>		
</div>
</ihtml:form>
</div>
		
	</body>
 </html:html>

