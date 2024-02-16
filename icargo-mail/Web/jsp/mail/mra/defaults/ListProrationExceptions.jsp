<%--/***********************************************************************
* Project	     	 : iCargo
* Module Code & Name 	 : MRA
* File Name          	 : List Proration Exceptions.jsp
* Date                 	 : 02-Sep-2008
* Author(s)              : a-3108

*************************************************************************/
--%>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListMailProrationExceptionsForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.Location"%>

<%@ page import="com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationExceptionVO" %>
<%@ page import = "java.util.Collection" %>
<%@ page import = "java.util.ArrayList" %>




		
	
<html:html>
<head>
		
		<%@ include file="/jsp/includes/customcss.jsp" %>
	
	


<title><common:message bundle="mralistprorationexceptions" key="mra.proration.listprorationexceptions.title" /></title>
<meta name="decorator" content="mainpanel">

<common:include type="script" src="/js/mail/mra/defaults/ListProrationExceptions_Script.jsp" />

</head>

<body>
	
	


<%@ include file="/jsp/includes/reports/printFrame.jsp" %>
  <!--CONTENT STARTS-->
	<div id="pageDiv" class="iCargoContent ic-masterbg" style="overflow:auto;height:100%;">

	<ihtml:form action="/mra.defaults.proration.screenloadlistprorationexception.do">
	<bean:define id="form"
	 name="ListMailProrationExceptionsForm"
	 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListMailProrationExceptionsForm"
	 toScope="page" scope="request"/>

	<ihtml:hidden property="displayPage" />
	<ihtml:hidden property="lastPageNum" />
        <ihtml:hidden property="calendarFormat" />
	<ihtml:hidden property="parentScreenId" />
	<ihtml:hidden property="operationFlag" />
	<ihtml:hidden property="closeFlag" />
	<input type="hidden" name="mySearchEnabled" />


<!-- SessionBeans -->
	<business:sessionBean id="KEY_ONETIMEVALUES"
		  moduleName="mailtracking.mra.defaults"
		  screenID="mailtracking.mra.defaults.listmailprorationexceptions"
		  method="get"
		  attribute="oneTimeValues" />
	<business:sessionBean id="ProrationExceptionVOPage"
		  moduleName="mailtracking.mra.defaults"
		  screenID="mailtracking.mra.defaults.listmailprorationexceptions"
		  method="get"
		  attribute="prorationExceptionVOs" />


  	<business:sessionBean id="KEY_SYSPARAMETERS"
		  moduleName="mailtracking.mra.defaults"
		  screenID="mailtracking.mra.defaults.listmailprorationexceptions"
		  method="get"
		  attribute="systemParametres" />

<logic:present name="KEY_SYSPARAMETERS">

		<logic:iterate id="sysPar" name="KEY_SYSPARAMETERS">
			<bean:define id="parameterCode" name="sysPar" property="key"/>
			
			<logic:equal name="parameterCode" value="mailtracking.mra.noofrecordsallowedforbulkrouteupdate">
				<bean:define id="parameterValue" name="sysPar" property="value"/>
				<input type="hidden"  id="modifyRouteDataLimit" value="<%=parameterValue%>">
			</logic:equal>
		</logic:iterate>
	</logic:present>
	
	<div class="ic-content-main">
		 <span class="ic-page-title">
			<common:message key="mra.proration.listprorationexceptions.pagetitle" />
		</span>

		<div class="ic-head-container">
			<div class="ic-filter-panel">
				<div class="ic-input-container">
					<div class="ic-section">
						<div class="ic-row">
							<div class="ic-input ic-split-16 ic-label-40">
								<label><common:message key="mra.proration.listprorationexceptions.exceptions" /></label>
										<ihtml:select componentID="CMP_MRA_Proration_ListProrationExceptions_Exception" property="exceptionCode" style="width:143px;">
											<ihtml:option value=""><common:message  key="combo.select"/></ihtml:option>
												<logic:present name="KEY_ONETIMEVALUES">
													<bean:define id="OneTimeValuesMap" name="KEY_ONETIMEVALUES" type="java.util.HashMap" />
														<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
															<logic:equal name="oneTimeValue" property="key" value="mra.proration.exceptions">
																<bean:define id="mraExceptionValues" name="oneTimeValue" property="value" />
																	<logic:iterate id="mraExceptionValue" name="mraExceptionValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																		<logic:present name="mraExceptionValue" property="fieldValue">
																			<bean:define id="fieldValue" name="mraExceptionValue" property="fieldValue"/>
																			<bean:define id="fieldDescription" name="mraExceptionValue" property="fieldDescription"/>
																			<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></ihtml:option>
																		</logic:present>
																	</logic:iterate>
															</logic:equal>
														</logic:iterate>
													</logic:present>
										</ihtml:select>
							</div>
							<div class="ic-input ic-split-16 ic-label-30">
								<label><common:message key="mra.proration.listprorationexceptions.fltNo" /></label>
								<ibusiness:flightnumber	carrierCodeProperty="carrierCode"
								id="flightCode"
								flightCodeProperty="flightNumber"
								componentID="CMP_MRA_Proration_ListProrationExceptions_FltNo"
								carrierCodeStyleClass="iCargoTextFieldVerySmall"
								flightCodeStyleClass="iCargoTextFieldSmall"
								readonly=""
								/>
							</div>


								<!--<td  class="iCargoLabelRightAligned">
									<common:message key="mra.proration.listprorationexceptions.fltDate" />
								 </td>
								<td>
									<ibusiness:calendar componentID="CMP_MRA_Proration_ListProrationExceptions_FltDate" property="flightDate" type="image" id="flightDate" />
								</td>-->

							<div class="ic-input ic-split-16">
								<label><common:message key="mra.proration.listprorationexceptions.fltDate" /></label>
								<ibusiness:calendar componentID="CMP_MRA_Proration_ListProrationExceptions_FltDate" property="flightDate" type="image" id="flightDate" />
							</div>
							<div class="ic-input ic-split-16">
								<label><common:message key="mra.proration.listprorationexceptions.origin" /></label>
								 <ihtml:text property="origin"  componentID="CMP_MRA_Proration_ListProrationExceptions_Origin"  maxlength="4" style="text-transform : uppercase;" />
								 <div class= "lovImg"><img id="originLov" src="<%=request.getContextPath()%>/images/lov.png" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.origin.value,'Airport','1','origin','',0)"  width="22" height="22" tabindex="8"/>
							</div>
							</div>
							<div class="ic-input ic-split-16">
							    <label><common:message key="mra.proration.listprorationexceptions.destination" /></label>
							   	<ihtml:text property="destination"  componentID="CMP_MRA_Proration_ListProrationExceptions_Destination" maxlength="4" style="text-transform : uppercase;" />
							  	<div class= "lovImg"><img id="destnLov" src="<%=request.getContextPath()%>/images/lov.png" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.destination.value,'Airport','1','destination','',0)" width="22" height="22" tabindex="10"/>
								</div>
							</div>
							<div class="ic-input ic-split-16">
								<label><common:message key="mra.proration.listprorationexceptions.mailbagId" /></label>
								<ihtml:text property="mailbagID" componentID="CMP_MRA_Proration_ListProrationExp_MAILBAGID" readonly="false" maxlength="29" style="width:205px" /> <!--modified. A8164 for ICRD 257674-->	
							</div>
						</div>
							<!--<td  class="iCargoLabelRightAligned">
								<common:message key="mra.proration.listprorationexceptions.dispatchNumber" />
							 </td>
							 <td>
								<ihtml:text componentID="CMP_MRA_Proration_ListProrationExceptions_DespatchNumber"  property="dispatchNo" maxlength="4" />

							 </td>-->
						<div class="ic-row" >
												
							<div class="ic-input ic-split-16">
								<label><common:message key="mra.proration.listprorationexceptions.orgoe" /></label>
								<ihtml:text property="originOfficeOfExchange" componentID="CMP_MRA_Proration_ListProrationExp_OOE" readonly="false" maxlength="6" style="width:50px" />
								<div class= "lovImg"><img  id="mailOOELov" src="<%=request.getContextPath()%>/images/lov.png" onclick="displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.originOfficeOfExchange.value,'OfficeOfExchange','1','originOfficeOfExchange','',0)" width="22" height="22">
							</div>
							</div>
							<div class="ic-input ic-split-16">
								<label><common:message key="mra.proration.listprorationexceptions.destoe" /></label>
								<ihtml:text property="destinationOfficeOfExchange" componentID="CMP_MRA_Proration_ListProrationExp_DOE" readonly="false" maxlength="6" style="width:50px" />
								<div class= "lovImg"><img  id="mailDOELov" src="<%=request.getContextPath()%>/images/lov.png" onclick="displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.destinationOfficeOfExchange.value,'OfficeOfExchange','1','destinationOfficeOfExchange','',0)"  width="22" height="22">
							</div>
							</div>
							<div class="ic-input ic-split-16">
								<label><common:message key="mra.proration.listprorationexceptions.mailcategory" /></label>
									<ihtml:select componentID="CMP_MRA_Proration_ListProrationExp_category" property="mailCategory" style="width:70px">
										<ihtml:option value=""><common:message  key="combo.select"/></ihtml:option>
											<logic:present name="KEY_ONETIMEVALUES">
												<logic:iterate id="oneTimeValue" name="KEY_ONETIMEVALUES">
													<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
														<logic:equal name="parameterCode" value="mailtracking.defaults.mailcategory">
															<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
																<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																	<logic:present name="parameterValue" property="fieldValue">
																		<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																		<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																		<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>"><%=String.valueOf(fieldDescription)%></ihtml:option>
																	</logic:present>
																</logic:iterate>
														</logic:equal>
												</logic:iterate>
											</logic:present>
									</ihtml:select>
							</div>
							<div class="ic-input ic-split-16">
								<label><common:message key="mra.proration.listprorationexceptions.subclass" /></label>
								<ihtml:text property="subClass" componentID="CMP_MRA_Proration_ListProrationExp_SUBCLASS" maxlength="2" style="width:30px"/>
								<div class= "lovImg"><img  id="subClassFilterLOV" value="subClassLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" />
								</div>
							</div>
							<div class="ic-input ic-split-6">
								<label><common:message key="mra.proration.listprorationexceptions.year" /></label>
								<ihtml:text property="year" componentID="CMP_MRA_Proration_ListProrationExp_YEAR" readonly="false" maxlength="1"  style="width:30px"/>
							</div>
							<div class="ic-input ic-split-10">
								<label><common:message key="mra.proration.listprorationexceptions.dispatchNumber" /></label>
								<ihtml:text property="dispatchNo" componentID="CMP_MRA_Proration_ListProrationExp_DSN" readonly="false" maxlength="4" style="width:45px"/>
							</div>
							<div class="ic-input ic-split-7">
								<label><common:message key="mra.proration.listprorationexceptions.rsn" /></label>
								<ihtml:text property="receptacleSerialNumber" componentID="CMP_MRA_Proration_ListProrationExp_RSN" readonly="false" maxlength="3" style="width:35px"/>
							</div>
							<div class="ic-input ic-split-6">
								<label><common:message key="mra.proration.listprorationexceptions.hni" /></label>
								<ihtml:text property="highestNumberIndicator" componentID="CMP_MRA_Proration_ListProrationExp_HNI" readonly="false" maxlength="1" style="width:30px" />
							</div>
							<div class="ic-input ic-split-6">
								<label><common:message key="mra.proration.listprorationexceptions.RI" /></label>
								<ihtml:text property="registeredIndicator" componentID="CMP_MRA_Proration_ListProrationExp_RI" readonly="false" maxlength="1" style="width:30px"/>
							</div>
						</div>
						<div class="ic-row">
							<div class="ic-input ic-split-16 ic-label-40">
								<label><common:message key="mra.proration.listprorationexceptions.assignedStatus" /></label>
									<ihtml:select componentID="CMP_MRA_Proration_ListProrationExceptions_Status" property="assignedStatus">
									<ihtml:option value=""><common:message  key="combo.select"/></ihtml:option>
										<logic:present name="KEY_ONETIMEVALUES">
										<bean:define id="OneTimeValuesMap" name="KEY_ONETIMEVALUES" type="java.util.HashMap" />
										<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
											<logic:equal name="oneTimeValue" property="key" value="mra.proration.assignedstatus">
											<bean:define id="mraStatusValues" name="oneTimeValue" property="value" />
											<logic:iterate id="mraStatusValue" name="mraStatusValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
													<logic:present name="mraStatusValue" property="fieldValue">
														<bean:define id="fieldValue" name="mraStatusValue" property="fieldValue"/>
														<bean:define id="fieldDescription" name="mraStatusValue" property="fieldDescription"/>
														<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></ihtml:option>
													</logic:present>
											</logic:iterate>
											</logic:equal>
										</logic:iterate>
										</logic:present>
									</ihtml:select>
							</div>
							<div class="ic-input ic-split-16 ic-label-30">
								<label><common:message key="mra.proration.listprorationexceptions.tblheader.assignee" /></label>
								<ihtml:text componentID="CMP_MRA_Proration_ListProrationExceptions_Asignee_Filter"  property="asignee" maxlength="15" />
								<div class= "lovImg"><img id="asigneeLov" src="<%=request.getContextPath()%>/images/lov.png" onclick="showAsigneeLOV()" width="22" height="22"/>
						    </div>
						    </div>
						    <div class="ic-input ic-split-16">
								<label><common:message key="mra.proration.listprorationexceptions.fromDate" /></label>
								<ibusiness:calendar componentID="CMP_MRA_Proration_ListProrationExceptions_FromDate" property="fromDate" type="image" id="fromDate" />
						    </div>
						    <div class="ic-input ic-split-16">
								<label><common:message key="mra.proration.listprorationexceptions.toDate" /></label>
								<ibusiness:calendar componentID="CMP_MRA_Proration_ListProrationExceptions_ToDate" property="toDate" type="image" id="toDate" />
						    </div>
							<div class="ic-input ic-split-16 ">
								<label><common:message key="mra.proration.listprorationexceptions.lbl.gpacode" /></label>
								<ihtml:text property="gpaCode" componentID="CMP_MRA_Proration_ListProrationExceptions_GPACode" maxlength="5" tabindex="8" />
								<div class= "lovImg">
								<img name="gpaCodeLov" id="gpaCodeLov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" tabindex="2" alt="" />
								</div>
							
							</div>
							<div class="ic-input ic-split-16 ic-label-40">
								<label><common:message key="mra.proration.listprorationexceptions.status" /></label>
									<ihtml:select componentID="CMP_MRA_Proration_ListProrationExceptions_ExpStatus" property="status">
									<ihtml:option value=""><common:message  key="combo.select"/></ihtml:option>
										<logic:present name="KEY_ONETIMEVALUES">
										<bean:define id="OneTimeValuesMap" name="KEY_ONETIMEVALUES" type="java.util.HashMap" />
										<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
											<logic:equal name="oneTimeValue" property="key" value="mra.proration.exceptionstatus">
											<bean:define id="mraStatusValues" name="oneTimeValue" property="value" />
											<logic:iterate id="mraStatusValue" name="mraStatusValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
													<logic:present name="mraStatusValue" property="fieldValue">
														<bean:define id="fieldValue" name="mraStatusValue" property="fieldValue"/>
														<bean:define id="fieldDescription" name="mraStatusValue" property="fieldDescription"/>
														<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></ihtml:option>
													</logic:present>
											</logic:iterate>
											</logic:equal>
										</logic:iterate>
										</logic:present>
									</ihtml:select>
							</div>
							</div>
							<div class="ic-row">
							<div class="ic-input ic-split-20">
								<label><common:message key="mra.proration.listprorationexceptions.csgDocNumber" /></label>
								<ihtml:text property="csgDocNum" componentID="CMP_MRA_Proration_ListProrationExp_CSGDOCNUM" readonly="false" maxlength="29" style="width:250px" /> <!--modified. A8331-->	
							</div>
							<div class="ic-button-container">
								<ihtml:nbutton property="btnList" componentID="CMP_MRA_Proration_ListProrationExceptions_List" accesskey="L" >
								  <common:message key="mra.proration.listprorationexceptions.btn.list" />
								</ihtml:nbutton>
								<ihtml:nbutton property="btnClear" componentID="CMP_MRA_Proration_ListProrationExceptions_Clear" accesskey="C" >
								  <common:message key="mra.proration.listprorationexceptions.btn.clear" />
								</ihtml:nbutton>
						   </div>
						 </div>
					</div>
				</div>
			</div>
		</div>
		 <!---Added for Pagination starts--->

	<div class="ic-main-container">
	<div class="ic-row">
	<a class="panel upArrow"  collapseFilter="true"  collapseFilterCallbackFun="callbackListProrationExceptions"  href="#"></a> <!--Added by A-7929 for ICRD - 224586  -->
</div>
		<div class="ic-col-30">
			<logic:present name="ProrationExceptionVOPage">
			<common:paginationTag pageURL="mra.defaults.proration.listprorationexceptionslist.do"
			name="ProrationExceptionVOPage"
			display="label"
			labelStyleClass="iCargoResultsLabel"
			lastPageNum="<%=((ListMailProrationExceptionsForm)form).getLastPageNum()%>" />
			</logic:present>
			<logic:notPresent name="ProrationExceptionVOPage">
			&nbsp;
			</logic:notPresent>
		</div>
		<div class="ic-right paddR5">
			<logic:present name="ProrationExceptionVOPage">

			<common:paginationTag pageURL="javascript:submitPage1('lastPageNum','displayPage')"
			linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
			name="ProrationExceptionVOPage" display="pages" lastPageNum="<%=form.getLastPageNum()%>"
			exportToExcel="true"
			exportTableId="listProrationTable"
			exportAction="mra.defaults.proration.listprorationexceptionslist.do"
			fetchCount="50"/>
			</logic:present>
			<logic:notPresent name="ProrationExceptionVOPage">
			&nbsp;
			</logic:notPresent>

		</div>

<!---Added for Pagination ends--->

		<div class="ic-row">
			<div class="tableContainer" id="div1" style="height:730px">
				<table  class="fixed-header-table" id="listProrationTable" style="width:120%;height:45px;">
					<thead>
						<tr>
							<td class="iCargoTableHeaderLabel ic-middle"  width="2%"><input type="checkbox" id= "allCheck" name="allCheck" value="checkbox" onclick="updateHeaderCheckBox(this.form,targetFormName.allCheck,targetFormName.rowId)" /><span></span></td>
							<td class="iCargoTableHeaderLabel ic-middle" width="17%"><common:message key="mra.proration.listprorationexceptions.mailbagId" /><span></span></td> <!--modified. A8164 for ICRD 257674-->	
							<td class="iCargoTableHeaderLabel ic-middle" width="4%"><common:message key="mra.proration.listprorationexceptions.orgoe" /><span></span></td>
							<td class="iCargoTableHeaderLabel ic-middle" width="4%"><common:message key="mra.proration.listprorationexceptions.destoe" /><span></span></td>
							<td class="iCargoTableHeaderLabel ic-middle" width="4%"><common:message key="mra.proration.listprorationexceptions.mailcategory" /><span></span></td>
							<td class="iCargoTableHeaderLabel ic-middle" width="4%"><common:message key="mra.proration.listprorationexceptions.subclass" /><span></span></td>
							<td class="iCargoTableHeaderLabel ic-middle" width="2%"><common:message key="mra.proration.listprorationexceptions.year" /><span></span></td>
						    <td class="iCargoTableHeaderLabel ic-middle" width="4%"><common:message key="mra.proration.listprorationexceptions.tblheader.dsn" /><span></span></td>
							<td class="iCargoTableHeaderLabel ic-middle" width="2%"><common:message key="mra.proration.listprorationexceptions.rsn" /><span></span></td>
							<td class="iCargoTableHeaderLabel ic-middle" width="2%"><common:message key="mra.proration.listprorationexceptions.hni" /><span></span></td>
							<td class="iCargoTableHeaderLabel ic-middle" width="2%"><common:message key="mra.proration.listprorationexceptions.RI" /><span></span></td>
							<td class="iCargoTableHeaderLabel ic-middle" width="5%"><common:message key="mra.proration.listprorationexceptions.tblheader.exception" /><span></span></td>
						    <td class="iCargoTableHeaderLabel ic-middle" width="4%"><common:message key="mra.proration.listprorationexceptions.tblheader.sector" /><span></span></td>
							<td class="iCargoTableHeaderLabel ic-middle" width="4%"><common:message key="mra.proration.listprorationexceptions.tblheader.triggerPoint" /><span></span></td>
							<td class="iCargoTableHeaderLabel ic-middle" width="5%"><common:message key="mra.proration.listprorationexceptions.tblheader.date" /><span></span></td>
							<td class="iCargoTableHeaderLabel ic-middle" width="4%"><common:message key="mra.proration.listprorationexceptions.tblheader.prorateFactor" /><span></span></td>
							<td class="iCargoTableHeaderLabel ic-middle" width="4%"><common:message key="mra.proration.listprorationexceptions.tblheader.flightNo" /><span></span></td>
						    <td class="iCargoTableHeaderLabel ic-middle" width="5%"><common:message key="mra.proration.listprorationexceptions.tblheader.flightDate" /><span></span></td>
							<td class="iCargoTableHeaderLabel ic-middle" width="6%"><common:message key="mra.proration.listprorationexceptions.tblheader.route" /><span></span></td>
						    <td class="iCargoTableHeaderLabel ic-middle" width="7%"><common:message key="mra.proration.listprorationexceptions.tblheader.consDocNo" /><span></span></td>
							<td class="iCargoTableHeaderLabel ic-middle" width="3%"><common:message key="mra.proration.listprorationexceptions.tblheader.noOfBags" /><span></span></td>
							<td class="iCargoTableHeaderLabel ic-middle" width="8%"><common:message key="mra.proration.listprorationexceptions.tblheader.status" /><span></span></td>
							<td class="iCargoTableHeaderLabel ic-middle" width="8%"><common:message key="mra.proration.listprorationexceptions.tblheader.assignee" /><span></span></td>
							<td class="iCargoTableHeaderLabel ic-middle" width="8%"><common:message key="mra.proration.listprorationexceptions.tblheader.assignedDate" /><span></span></td>
						 </tr>
				   	</thead>
						<!--  <td class="iCargoTableHeader" width="7%">
							<common:message key="mra.proration.listprorationexceptions.tblheader.resolvedDate" />
<span></span></td>
						  <td class="iCargoTableHeader"  width="6%">
							<common:message key="mra.proration.listprorationexceptions.tblheader.status" />
<span></span></td>-->


					<tbody id="shipmentDetailsTable">
						<logic:present name="ProrationExceptionVOPage">
							<%String rowIdValue = "";%>
							
							<bean:define id="totalRecordCount" name="ProrationExceptionVOPage"
												property="totalRecordCount" />
					
								<input type="hidden" id="totalRecordCount" value="<%=totalRecordCount%>"/>					
												
								<logic:iterate id="ProrationExceptionVO" name="ProrationExceptionVOPage"
								type="com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationExceptionVO"
								indexId="rowCount">

								<tr>
									<td class="iCargoTableDataTd ic-center"><!-- for spa tree table -->
										<%rowIdValue = String.valueOf(rowCount);%> <logic:present
											name="ProrationExceptionVO" property="exceptionSerialNumber">
											<bean:define id="eCode" name="ProrationExceptionVO"
												property="exceptionSerialNumber" />
										<input type="checkbox" name="rowId"  value="<%=rowIdValue%>" onclick="toggleTableHeaderCheckbox('rowId',targetFormName.allCheck)" />
										</logic:present>
									</td>
									<td>
									<logic:present name="ProrationExceptionVO" property="mailbagId">
																			<common:write name="ProrationExceptionVO" property="mailbagId" />
									<bean:define id="malseqnum" name="ProrationExceptionVO" property="mailSequenceNumber" />
                                                                            <input type="hidden" name="malseqnums" value="<%=malseqnum%>" >
									</logic:present>

									</td>									

									<td>
									<logic:present name="ProrationExceptionVO" property="originOfficeOfExchange">
																			<common:write name="ProrationExceptionVO" property="originOfficeOfExchange" />
									</logic:present>

									</td>
									<td>
									<logic:present name="ProrationExceptionVO" property="destinationOfficeOfExchange">
																			<common:write name="ProrationExceptionVO" property="destinationOfficeOfExchange" />
									</logic:present>

									</td>
									<td>
									<logic:present name="ProrationExceptionVO" property="mailCategory">
																			<common:write name="ProrationExceptionVO" property="mailCategory" />
									</logic:present>

									</td>
									<td>
									<logic:present name="ProrationExceptionVO" property="subClass">
																			<common:write name="ProrationExceptionVO" property="subClass" />
									</logic:present>

									</td>
									<td>
									<logic:present name="ProrationExceptionVO" property="year">
																			<common:write name="ProrationExceptionVO" property="year" />
									</logic:present>

									</td>
									<td>
									<logic:present name="ProrationExceptionVO" property="dispatchNo">
																			<common:write name="ProrationExceptionVO" property="dispatchNo" />
									</logic:present>

									</td>
									<td>
									<logic:present name="ProrationExceptionVO" property="receptacleSerialNumber">
																			<common:write name="ProrationExceptionVO" property="receptacleSerialNumber" />
									</logic:present>

									</td>







									<td>
									<logic:present name="ProrationExceptionVO" property="highestNumberIndicator">
																			<common:write name="ProrationExceptionVO" property="highestNumberIndicator" />
									</logic:present>

									</td>

									<td>
									<logic:present name="ProrationExceptionVO" property="registeredIndicator">
																			<common:write name="ProrationExceptionVO" property="registeredIndicator" />
									</logic:present>

									</td>









									<td>  <logic:present name="ProrationExceptionVO" property="exceptionCode">




												<logic:present name="ProrationExceptionVO">
												<bean:define id="OneTimeValuesMap" name="KEY_ONETIMEVALUES" type="java.util.HashMap" />
													<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">

														<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
															<logic:equal name="parameterCode" value="mra.proration.exceptions">

																<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
																<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																	<logic:present name="parameterValue" property="fieldValue">
																		<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>

																		<logic:equal name="ProrationExceptionVO" property="exceptionCode" value="<%=(String)fieldValue%>" >
																			<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																			<bean:write name="parameterValue" property="fieldDescription" />

											</logic:equal>
											</logic:present>
										</logic:iterate>
															</logic:equal>
													</logic:iterate>
										</logic:present>

									</logic:present>  </td>

								<td>  <logic:present name="ProrationExceptionVO" property="segmentOrigin">
										<common:write name="ProrationExceptionVO" property="segmentOrigin" />
									</logic:present>
									<%String h="-"; %>
									<%=h %>
									<logic:present name="ProrationExceptionVO" property="segmentDestination">
										<common:write name="ProrationExceptionVO" property="segmentDestination" />
									</logic:present>
									</td>
									<td>  <logic:present name="ProrationExceptionVO" property="triggerPoint">

																		<logic:present name="KEY_ONETIMEVALUES">
																		<bean:define id="OneTimeValuesMap" name="KEY_ONETIMEVALUES" type="java.util.HashMap" />
																		<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
																			<logic:equal name="oneTimeValue" property="key" value="mailtracking.mra.proration.triggerpoint">
																			<bean:define id="mraTriggerValues" name="oneTimeValue" property="value" />
																				<logic:iterate id="mraTriggerValue" name="mraTriggerValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																					<logic:present name="mraTriggerValue" property="fieldValue">
																						<logic:equal name="mraTriggerValue" property="fieldValue" value="<%=ProrationExceptionVO.getTriggerPoint()%>" >
																						<common:write name="mraTriggerValue" property="fieldDescription"/>
																						</logic:equal>
																					</logic:present>
																				</logic:iterate>
																			</logic:equal>
																			</logic:iterate>
																			</logic:present>
																		</logic:present>

																		  </td>


									<td>   <logic:present name="ProrationExceptionVO"
										property="date">
										<bean:define id="date" name="ProrationExceptionVO"	property="date" />
										<%
								  String dateLocalDate = TimeConvertor.toStringFormat(((LocalDate)date).toCalendar(),form.getCalendarFormat());
								%>
										<%=String.valueOf(dateLocalDate)%>
									</logic:present>  </td>


									<td><logic:present name="ProrationExceptionVO"
										property="prorateFactor">
										<common:write name="ProrationExceptionVO" property="prorateFactor" localeFormat="true"/>
									</logic:present></td>

									<td>  <logic:present name="ProrationExceptionVO"
										property="flightNumber">
										<logic:present name="ProrationExceptionVO"
										property="carrierCode">
										<common:write name="ProrationExceptionVO" property="carrierCode" />-
										</logic:present>
										<common:write name="ProrationExceptionVO" property="flightNumber" />
									</logic:present>  </td>


									<td>   <logic:present name="ProrationExceptionVO"
										property="flightDate">
										<bean:define id="flightDate" name="ProrationExceptionVO"	property="flightDate" />
										<%
								  String flightLocalDate = TimeConvertor.toStringFormat(((LocalDate)flightDate).toCalendar(),form.getCalendarFormat());
								%>
										<%=String.valueOf(flightLocalDate)%>
									</logic:present>  </td>

									<td>  <logic:present name="ProrationExceptionVO" property="route">
										<common:write name="ProrationExceptionVO" property="route" />
									</logic:present>
									  </td>

									<td>   <logic:present name="ProrationExceptionVO"
										property="consDocNo">
										<common:write name="ProrationExceptionVO" property="consDocNo" />
									</logic:present>  </td>


									<td>
									<logic:present name="ProrationExceptionVO" property="noOfBags">
										<common:write name="ProrationExceptionVO" property="noOfBags" />
									</logic:present></td>

									<td class="iCargoTableDataTd ic-center">
									<logic:present name="ProrationExceptionVO" property="status">
										<bean:define id="exceptionStatus" name="ProrationExceptionVO" property="status"/>
										<%String field=exceptionStatus.toString();%>
										<common:write name="ProrationExceptionVO" property="status" />
										<ihtml:hidden property="exceptionStatus" value="<%=field%>"/>
									</logic:present></td>
									<td class="iCargoTableDataTd">
									<div class="ic-input">
										<logic:present name="ProrationExceptionVO" property="assignedUser">
											<bean:define id="assignedUser" name="ProrationExceptionVO"	property="assignedUser" />
											<ihtml:text componentID="CMP_MRA_Proration_ListProrationExceptions_Assignee"  style="width:80px" property="assignedUser"  indexId="rowCount" value="<%=assignedUser.toString()%>" maxlength="15" />
											  <% String showAssigneeLov = "showAssigneeLovInTable("+rowCount+")"; %>
											  <div class= "lovImgTbl"><img  id="assigneeTableLov" height="16"
											  	src="<%=request.getContextPath()%>/images/lov.png"	onclick="<%=showAssigneeLov%>" width="16" alt="" />
											  </div>
										</logic:present>
										<logic:notPresent name="ProrationExceptionVO" property="assignedUser">
										   	 <ihtml:text componentID="CMP_MRA_Proration_ListProrationExceptions_Assignee" style="width:70px"  property="assignedUser" indexId="rowCount" value="" maxlength="15" /><!--Modified as part of ICRD-283843-->
											  <% String showAssigneeLov = "showAssigneeLovInTable("+rowCount+")"; %>
											  <div class= "lovImgTbl"><img  id="assigneeTableLov" height="16"
											  	src="<%=request.getContextPath()%>/images/lov.png"	onclick="<%=showAssigneeLov%>" width="16" alt="" />
											  </div>
										</logic:notPresent>


										</div>
									  </td>


									<td>
									<logic:present name="ProrationExceptionVO" property="assignedTime">
									<bean:define id="assignedTime" name="ProrationExceptionVO"	property="assignedTime" />
											<%
									  String assgLocalDate = TimeConvertor.toStringFormat(((LocalDate)assignedTime).toCalendar(),form.getCalendarFormat());
										%>
										<ibusiness:calendar id="assignedTime" style="width:80px"
											property="assignedTime"
											indexId="rowCount"
											componentID="CMP_MRA_Proration_ListProrationExp_AssignedTime"
											value="<%=String.valueOf(assgLocalDate)%>"
											type="image"
											maxlength="11"
											disabled="false"/>
									</logic:present>

									<logic:notPresent name="ProrationExceptionVO" property="assignedTime">
										<%
										 String currentAutoDate = TimeConvertor.toStringFormat(new LocalDate(LocalDate.NO_STATION, Location.NONE, false).toCalendar(),form.getCalendarFormat());
										%>
										<ibusiness:calendar id="assignedTime" style="width:80px"
											property="assignedTime"
											indexId="rowCount"
											componentID="CMP_MRA_Proration_ListProrationExp_AssignedTime"
											value="<%=String.valueOf(currentAutoDate)%>"
											type="image"
											maxlength="11"/>
									</logic:notPresent>
									  </td>

									<!--<td>  <logic:present name="ProrationExceptionVO"
										property="resolvedTime">
										<bean:define id="resolvedTime" name="ProrationExceptionVO"	property="resolvedTime" />
										<%
								  String resLocalDate = TimeConvertor.toStringFormat(((LocalDate)resolvedTime).toCalendar(),form.getCalendarFormat());
								%>
										<%=String.valueOf(resLocalDate)%>
									</logic:present>  </td>

									<td class="iCargoTableDataTd" >

									<logic:present name="KEY_ONETIMEVALUES">
									<bean:define id="OneTimeValuesMap" name="KEY_ONETIMEVALUES" type="java.util.HashMap" />
									<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
										<logic:equal name="oneTimeValue" property="key" value="mra.proration.exceptionstatus">
										<bean:define id="mraStatusValues" name="oneTimeValue" property="value" />
										<logic:iterate id="mraStatusValue" name="mraStatusValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
												<logic:present name="mraStatusValue" property="fieldValue">
													<bean:define id="fieldValue" name="mraStatusValue" property="fieldValue"/>
													<bean:define id="fieldDescription" name="mraStatusValue" property="fieldDescription"/>
													<logic:equal name="ProrationExceptionVO" property="status" value="<%=(String)fieldValue%>" >

													<%=(String)fieldDescription%>
													</logic:equal>
												</logic:present>
										</logic:iterate>
										</logic:equal>
									</logic:iterate>
									</logic:present>



									</td>-->
								</tr>

						</logic:iterate>
					</logic:present>
				</tbody>
			</table>
		</div>
	</div>
	</div>
		<div class="ic-foot-container">
			<div class="ic-row">
				<div class="ic-button-container paddR5">
				
				
					<ihtml:nbutton property="modifyRoute" componentID="CMP_MRA_Proration_ListProrationExceptions_ModifyRoute" accesskey="B" >
					  <common:message key="mra.proration.listprorationexceptions.btn.modifyroute" />
					</ihtml:nbutton>
				
					<ihtml:nbutton property="btnPrint" componentID="CMP_MRA_Proration_ListProrationExceptions_Print" accesskey="P" >
					  <common:message key="mra.proration.listprorationexceptions.btn.print" />
					</ihtml:nbutton>
					<!--<ihtml:nbutton property="btnDispatchEnquiry" componentID="CMP_MRA_Proration_ListProrationExceptions_Enquiry" accesskey="D" >
					  <common:message key="mra.proration.listprorationexceptions.btn.enquiry" />
					</ihtml:nbutton>-->
					<ihtml:nbutton property="btnRouting" componentID="CMP_MRA_Proration_ListProrationExceptions_Routing" accesskey="R" >
					  <common:message key="mra.proration.listprorationexceptions.btn.routing" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btnMaintainPF" componentID="CMP_MRA_Proration_ListProrationExceptions_MaintainPF" accesskey="M" >
					  <common:message key="mra.proration.listprorationexceptions.btn.maintianPF" />
					</ihtml:nbutton>

					<ihtml:nbutton property="btnSave" componentID="CMP_MRA_Proration_ListProrationExceptions_Save" accesskey="S" >
					  <common:message key="mra.proration.listprorationexceptions.btn.save" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btnVoid" componentID="CMP_MRA_Proration_ListProrationExceptions_Void" accesskey="V" >
					  <common:message key="mra.proration.listprorationexceptions.btn.void" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btnProrate" componentID="CMP_MRA_Proration_ListProrationExceptions_Prorate" accesskey="A" >
					  <common:message key="mra.proration.listprorationexceptions.btn.proration" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btnClose" componentID="CMP_MRA_Proration_ListProrationExceptions_Close" accesskey="O" >
					  <common:message key="mra.proration.listprorationexceptions.btn.close" />
					</ihtml:nbutton>
				</div>
			</div>
		</div>
	</div>
</ihtml:form>
</div>
<!---CONTENT ENDS-->

	</body>
</html:html>
