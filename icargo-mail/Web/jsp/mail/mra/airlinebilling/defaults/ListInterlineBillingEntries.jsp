<%--/***********************************************************************
* Project	     	 : iCargo
* Module Code & Name 	 : mra
* File Name          	 : ListInterlineBillingEntries.jsp
* Date                 	 : 12-Feb-2016
* Author(s)              : A-5219
*************************************************************************/
--%>
<%@ page language="java" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesForm" %>
<%@ page import = "com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.AirlineBillingFilterVO" %>

<bean:define id="form"
	name="ListInterlineBillingEntriesForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesForm"
	toScope="page" />

<business:sessionBean id="airlineBillingFilterVO"
moduleName="mailtracking.mra.airlinebilling"
screenID="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries"
method="get"
attribute="AirlineBillingFilterVO" />





<html:html>
	<head>




		<%@ include file="/jsp/includes/customcss.jsp" %>
		<title><common:message bundle="<%=form.getBundle()%>" key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.title" /></title>
		<meta name="decorator" content="mainpanel">
		<common:include type="script" src="/js/mail/mra/airlinebilling/defaults/ListInterlineBillingEntries_Script.jsp" />
	</head>

<body>





<div id="pageDiv" class="iCargoContent">
<div id="pageDiv" class="iCargoContent ic-masterbg">


<ihtml:form action="/mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.onScreenLoad.do">

<ihtml:hidden property="select"/>

<ihtml:hidden property="isReviewEnabledFlag"/>
<ihtml:hidden property="lastPageNum"/>
<ihtml:hidden property="displayPage"/>
<ihtml:hidden property="selectedrows"/>
<ihtml:hidden property="showPopup"/>
 <input type="hidden" name="mySearchEnabled" />

<business:sessionBean id="documentBillingDetailsVO"
moduleName="mailtracking.mra.airlinebilling"
screenID="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries"
method="get" attribute="documentBillingDetailVOs" />

<business:sessionBean id="onetimemap"
moduleName="mailtracking.mra.airlinebilling"
screenID="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries"
method="get" attribute="OneTimeMap" />
	 <div class="ic-content-main">
	    <span class="ic-page-title ic-display-none">
		    <common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.pagetitle" />
		</span>
		    <div class="ic-head-container">
			    <div class="ic-filter-panel">
				<div class="ic-input ic-split-100 ">
					<fieldset class="ic-field-set">
						<legend class="iCargoLegend"><common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.dsnmailbag" /></legend>
						<div class="ic-input ic-split-12 ">
							<label>
								<common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.orgoe" />
							</label>
							<ihtml:text property="originOfficeOfExchange" componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_OOE" readonly="false" maxlength="6" />
							<div class="lovImg"><img name="mailOOELov" id="mailOOELov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"></div>
						</div>
						<div class="ic-input ic-split-12 ">
							<label>
								<common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.destoe" />
							</label>
							<ihtml:text property="destinationOfficeOfExchange" componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_DOE" readonly="false" maxlength="6" />
							<div class="lovImg"><img name="mailDOELov" id="mailDOELov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"></div>
						</div>
						<div class="ic-input ic-split-15 ">
							<label><common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.category" /></label>
							<ihtml:select componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_category" property="mailCategory">
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								<logic:present name="onetimemap">
									<logic:iterate id="oneTimeValue" name="onetimemap">
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
						<div class="ic-input ic-split-10 ">
							<label><common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.subclass" /></label>
							<ihtml:text property="subClass" componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_SUBCLASS" maxlength="2"/>
							<div class="lovImg"><img name="subClassFilterLOV" id="subClassFilterLOV" value="subClassLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" /></div>
						</div>
						<div class="ic-input ic-split-10 ">
							<label><common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.year" /></label>
							<ihtml:text property="year" componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_YEAR" readonly="false" maxlength="1" />
						</div>
						<div class="ic-input ic-split-10 ">
							<label><common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.dsn" /></label>
							<ihtml:text property="dsn" componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_DSN" readonly="false" maxlength="4" />
						</div>
						<div class="ic-input ic-split-10 ">
							<label><common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.rsn" /></label>
							<ihtml:text property="receptacleSerialNumber" componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_RSN" readonly="false" maxlength="3" />
						</div>
						<div class="ic-input ic-split-10 ">
							<label><common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.hni" /></label>
							<ihtml:text property="highestNumberIndicator" componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_HNI" readonly="false" maxlength="1" />
						</div>
						<div class="ic-input ic-split-10 ">
							<label><common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.ri" /></label>
							<ihtml:text property="registeredIndicator" componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_RI" readonly="false" maxlength="1" />
						</div>
					</fieldset>
					</div>
					<div class="ic-input ic-split-100 "></div>
					<div class="ic-row">
						<div class="ic-input ic-split-12 marginL10">
							<label><common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.airlinecode"/></label>
							<logic:present name="airlineBillingFilterVO" property="airlineCode">
								<ihtml:text componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_AIRLINECODE" property="airlineCode"
								maxlength="3" />
								<div class="lovImg"><img name="airlinecodelov" id="airlinecodelov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" /></div>
							</logic:present>
							<logic:notPresent  name="airlineBillingFilterVO" property="airlineCode">
								<ihtml:text componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_AIRLINECODE" property="airlineCode"
								 value="" maxlength="3" />
								<div class="lovImg"> <img name="airlinecodelov" id="airlinecodelov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" /></div>
							</logic:notPresent>
						</div>
						<div class="ic-input ic-split-11 marginR5">
							<label><common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.billingtype" /></label>
							<ihtml:select componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_BILLINGTYPE" property="billingType" >
								<ihtml:option value=""></ihtml:option>
								<logic:present name="onetimemap">
									<logic:iterate id="oneTimeValue" name="onetimemap">
										<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
										<logic:equal name="parameterCode" value="mailtracking.mra.billingtype">
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
						<div class="ic-input ic-split-15 marginL5">
							<label><common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.billingstatus" /></label>
							<ihtml:select componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_BILLINGSTATUS" property="billingStatus" >
								<ihtml:option value=""></ihtml:option>
								 <logic:present name="onetimemap">
									<logic:iterate id="oneTimeValue" name="onetimemap">
										<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
										<logic:equal name="parameterCode" value="mra.airlinebilling.billingstatus">
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
	   					 <div class="ic-input ic-split-10 ">
							<label><common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.sectfrom"/></label>
							<logic:present name="airlineBillingFilterVO" property="sectorFrom">
								<ihtml:text componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_SECTFROM" property="sectorFrom"
									maxlength="4"  value="<%=form.getSectorFrom()%>"/>
								<div class="lovImg"><img name="sectfromlov" id="sectfromlov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" /></div>
							</logic:present>
							<logic:notPresent  name="airlineBillingFilterVO" property="sectorFrom">
								<ihtml:text componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_SECTFROM" property="sectorFrom"
									maxlength="4"  value=""/>
								<div class="lovImg"><img name="sectfromlov" id="sectfromlov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" /></div>
							</logic:notPresent>
						</div>
			     		<div class="ic-input ic-split-10 ">
							<label><common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.sectto"/></label>
							<logic:present name="airlineBillingFilterVO" property="sectorTo">
								<ihtml:text componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_SECTTO" property="sectorTo"
									maxlength="4" value="<%=form.getSectorTo()%>"/>
								<div class="lovImg"><img name="secttolov" id="secttolov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" /></div>
							</logic:present>
							<logic:notPresent name="airlineBillingFilterVO" property="sectorTo">
								<ihtml:text componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_SECTTO" property="sectorTo"
									maxlength="4"  value=""/>
								<div class="lovImg"><img name="secttolov" id="secttolov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" /></div>
							</logic:notPresent>
						</div>
						<div class="ic-input ic-split-30">
							<fieldset class="ic-field-set inline_filedset">
								<legend class="iCargoLegend"><common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.daterange" />  </legend>
								<div class="ic-input ic-split-50 ic-mandatory">
									<label><common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.from" scope="request"/></label>
									<logic:present name="airlineBillingFilterVO" property="fromDate">
										<ibusiness:calendar
										property="fromDate"
										componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_FROMDATE"
										type="image"
										id="fromDate"
										maxlength="11"

										value="<%=form.getFromDate()%>"/>
									</logic:present>
									<logic:notPresent  name="airlineBillingFilterVO" property="fromDate">
										<ibusiness:calendar
										property="fromDate"
										componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_FROMDATE"
										type="image"
										id="fromDate"
										maxlength="11"

										value=""/>
									</logic:notPresent>
								</div>
								<div class="ic-input ic-split-50 ic-mandatory">
									<label><common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.to" /></label>
									<logic:present name="airlineBillingFilterVO" property="toDate">
										<ibusiness:calendar componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_TODATE"
										property="toDate" type="image" id="toDate"
										maxlength="11"

										value="<%=form.getToDate()%>"/>
									</logic:present>
									<logic:notPresent  name="airlineBillingFilterVO" property="toDate">
										<ibusiness:calendar componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_TODATE"
										property="toDate" type="image" id="toDate"
										maxlength="11"  value=""/>
									</logic:notPresent>
								</div>
							</fieldset>
						</div>
						</div>
						<div class="ic-row">
						<div class="ic-button-container">
							<ihtml:nbutton property="btnList"  componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_LIST" accesskey="L" >
								<common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.button.list" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btnClear"  componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_CLEAR" accesskey="C" >
								<common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.button.clear" />
							</ihtml:nbutton>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-main-container">
				<div class="ic-col-30">
					<logic:present name="documentBillingDetailsVO">
						<common:paginationTag pageURL="javascript:submitPage1('lastPageNum','displayPage')"
						name="documentBillingDetailsVO"
						display="label"
						labelStyleClass="iCargoResultsLabel"
						lastPageNum="<%=((ListInterlineBillingEntriesForm)form).getLastPageNum() %>" />
					</logic:present>
					<logic:notPresent name="documentBillingDetailsVO">&nbsp;</logic:notPresent>
				</div>
				<div class="ic-right paddR5">
					<logic:present name="documentBillingDetailsVO">
						<common:paginationTag
						pageURL="javascript:submitPage1('lastPageNum','displayPage')"
						name="documentBillingDetailsVO"
						display="pages"
						linkStyleClass="iCargoLink"
						disabledLinkStyleClass="iCargoLink"
						lastPageNum="<%=((ListInterlineBillingEntriesForm)form).getLastPageNum() %>"
						exportToExcel="true"
						exportTableId="listInterlineTable"
						exportAction="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.list.do"/>
					</logic:present>
					<logic:notPresent name="documentBillingDetailsVO">&nbsp;</logic:notPresent>
				</div>
				<div class="ic-row">
					<div id="div1" class="tableContainer"  style="width:100%;height:510px;">

						<table class="fixed-header-table" id="listInterlineTable">
							<thead>
								<tr class="ic-th-all">
									<th style="width: 2%" />
									<th style="width: 3%" />
									<th style="width: 5%" />
									<th style="width: 5%" />
									<th style="width: 9%" />
									<th style="width: 4%" />
									<th style="width: 4%" />
									<th style="width: 4%" />
									<th style="width: 4%" />
									<th style="width: 4%" />
									<th style="width: 4%" />
									<th style="width: 4%" />
									<th style="width: 4%" />
									<th style="width: 4%" />
									<th style="width: 4%" />
									<th style="width: 4%" />
									<th style="width: 4%" />
									<th style="width: 4%" />
									<th style="width: 4%" />
									<th style="width: 8%" />
									<th style="width: 8%" />
									<th style="width: 8%" />
									<th style="width: 8%" />
								</tr>
								<tr class="iCargoTableHeadingLeft">
									<td class="iCargoTableHeader" align="center" rowspan="2"> <input type=checkbox name="checkHead" value="checkbox" > <span></span></td>
									<td align="center" rowspan="2"> <common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.airlinecode" /> <span></span></td>
									<td width="8%" colspan="2">	<common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.sector" /><span></span></td>
									<td width="9%" align="center" rowspan="2"> <common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.consdocno"/><span></span></td>
									<td width="4%"align="center"  rowspan="2"> <common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.orgoe"/><span></span></td>
									<td width="4%"align="center"  rowspan="2"> <common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.destoe"/><span></span></td>
									<td width="4%" align="center" rowspan="2"> <common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.category" /> <span></span></td>
									<td width="4%" align="center" rowspan="2"> <common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.subclass" /> <span></span></td>
									<td width="4%" align="center" rowspan="2"> <common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.year" /> <span></span></td>
									<td width="4%" align="center" rowspan="2"> <common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.dsn" /> <span></span></td>
									<td width="4%" align="center" rowspan="2"> <common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.rsn" /> <span></span></td>
									<td width="4%" align="center" rowspan="2"> <common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.hni" /> <span></span></td>
									<td width="4%" align="center" rowspan="2"> <common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.ri" /> <span></span></td>

									<td width="4%" align="center" rowspan="2"> <common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.noofmailbags" /> <span></span></td>
									<td width="4%" align="center" rowspan="2"> <common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.wt" /> <span></span></td>
									<td width="4%" align="center" rowspan="2"> <common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.currency" /> <span></span></td>
									<td width="4%" align="center" rowspan="2"> <common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.amt" /> <span></span></td>
									<td width="4%" align="center" rowspan="2"> <common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.reviewed" /> <span></span></td>
									<td width="8%" align="center" rowspan="2"> <common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.blgstatus" /> <span></span></td>
									<td width="8%" align="center" rowspan="2"> <common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.invno" /> <span></span></td>
									<td width="8%" align="center" rowspan="2"> <common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.ccarefno" /> <span></span></td>
									<td width="8%" align="center" rowspan="2"> <common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.remarks" /> <span></span></td>
								</tr>
								<tr>
									<td width="4%">  <common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.from" /> </td>
									<td width="4%">  <common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.to" /> </td>
								</tr>
							</thead>
							<tbody>
								<logic:present name="documentBillingDetailsVO">
									<logic:iterate id="iterator" name="documentBillingDetailsVO" type="com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO" indexId="rowCount">
										<common:rowColorTag index="rowCount">
											<tr class="iCargoTableDataRow1" bgcolor="<%=color%>">
												<td class="iCargoTableTd"> <div align="center">
													<input type=checkbox name="check"  value="<%=String.valueOf(rowCount)%>" onclick="checkBoxValidate('check',<%=rowCount%>)"/> </div></td>

												<td style="text-align:left">
													<logic:present  name="iterator" property="airlineCode">
													<bean:write name="iterator" property="airlineCode"/>
													</logic:present>
												</td>
												<td style="text-align:left">
													<logic:present  name="iterator" property="sectorFrom">
													<bean:write name="iterator" property="sectorFrom"/>
													</logic:present>
												</td>
												<td style="text-align:left">
													<logic:present  name="iterator" property="sectorTo">
													<bean:write name="iterator" property="sectorTo"/>
													</logic:present>
												</td>
												<td style="text-align:right">
													<logic:present  name="iterator" property="csgDocumentNumber">
													<bean:write name="iterator" property="csgDocumentNumber"/>
													</logic:present>
												</td>
												<td style="text-align:left">
													<logic:present  name="iterator" property="orgOfficeOfExchange">
													<bean:write name="iterator" property="orgOfficeOfExchange"/>
													</logic:present>
												</td>
												<td style="text-align:left">
													<logic:present  name="iterator" property="destOfficeOfExchange">
													<bean:write name="iterator" property="destOfficeOfExchange"/>
													</logic:present>
												</td>
												<td style="text-align:left">
													<logic:present  name="iterator" property="category">
													<bean:write name="iterator" property="category"/>
													</logic:present>
												</td>
												<td style="text-align:left">
													<logic:present  name="iterator" property="subClass">
													<bean:write name="iterator" property="subClass"/>
													</logic:present>
												</td>
												<td >
													<center>
													<logic:present  name="iterator" property="year">
													<bean:write name="iterator" property="year"/>
													</logic:present>
													</center>
												</td>
												<td >
													<center>
													<logic:present  name="iterator" property="dsn">
													<bean:write name="iterator" property="dsn"/>
													<ihtml:hidden name="iterator" property="dsn"/>
													</logic:present>
													</center>
												</td>
												<td >
													<center>
													<logic:present  name="iterator" property="rsn">
													<bean:write name="iterator" property="rsn"/>
													<ihtml:hidden name="iterator" property="rsn"/>
													</logic:present>
													</center>
												</td>
												<td >
													<center>
													<logic:present  name="iterator" property="hni">
													<bean:write name="iterator" property="hni"/>
													<ihtml:hidden name="iterator" property="hni"/>
													</logic:present>
													</center>
												</td>
												<td >
													<center>
													<logic:present  name="iterator" property="regInd">
													<bean:write name="iterator" property="regInd"/>
													<ihtml:hidden name="iterator" property="regInd"/>
													</logic:present>
													</center>
												</td>
												<td >
													<center>
													<logic:present  name="iterator" property="noofMailbags">
													<bean:write name="iterator" property="noofMailbags"/>
													</logic:present>
													</center>
												</td>
												<td style="text-align:right">
													<logic:present  name="iterator" property="weight">
													<common:write name="iterator" property="weight" unitFormatting="true" />

													</logic:present>
												</td>
												<td style="text-align:right">
													<logic:present  name="iterator" property="currency">
													<bean:write name="iterator" property="currency"/>
													</logic:present>
												</td>
												<td style="text-align:right">
													<logic:present  name="iterator" property="amount">
													<bean:write name="iterator" property="amount"/>
													</logic:present>
												</td>

												<td >
													<div align="center">
													<logic:present name="iterator" property="reviewCheck">
													<logic:equal name="iterator" property="reviewCheck" value="Y" >
													<input type="checkbox" name="reviewCheck" value="true" checked disabled="true"  /><!--Modified for ICRD-104522-->
													</logic:equal>
													<logic:equal name="iterator" property="reviewCheck" value="N">
													<input type="checkbox" name="reviewCheck" value="false" disabled="true" />
													</logic:equal>
													</logic:present>
													<logic:notPresent name="iterator" property="reviewCheck">
													<input type="checkbox" name="reviewCheck" value="false" disabled="true"/>
													</logic:notPresent>

													<ihtml:hidden property="review" />


													</div>
												</td>
												<td >
													<center>

														<logic:present  name="iterator" property="billingStatus">
														<logic:present name="onetimemap">
														<logic:iterate id="oneTimeValue" name="onetimemap">
														<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
														<logic:equal name="parameterCode" value="mra.airlinebilling.billingstatus">
														<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
														<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
														<logic:present name="parameterValue" property="fieldValue">

														<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
														<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
														<bean:define id="billingStatus" name="iterator" property="billingStatus"/>
														<%String field=billingStatus.toString();%>


														<logic:equal name="parameterValue" property="fieldValue" value="<%=field%>">
														<ihtml:hidden property="saveBillingStatus" value="<%=field%>"/>
														<common:write name="parameterValue" property="fieldDescription" />
														</logic:equal>
														</logic:present>
														</logic:iterate>
														</logic:equal>
														</logic:iterate>
														</logic:present>
														</logic:present>
														<logic:notPresent name="iterator" property="billingStatus">
														<ihtml:hidden property="saveBillingStatus" value=""/>
														</logic:notPresent>
													</center>
												</td>
												<td >
													<center>
													<logic:present  name="iterator" property="invoiceNumber">
													<bean:write name="iterator" property="invoiceNumber"/>
													</logic:present>
													</center>
												</td>
												<td >
													<center>
													<logic:present  name="iterator" property="ccaRefNumber">
													<ihtml:hidden property="ccaReferenceNumber" value="<%=iterator.getCcaRefNumber()%>"/>
													<bean:write name="iterator" property="ccaRefNumber"/>
													</logic:present>
													</center>
												</td>
												<td >
													<center>
													<logic:present  name="iterator" property="remarks">
													<bean:write name="iterator" property="remarks"/>

													</logic:present>
													</center>
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
			<div class="ic-foot-container">
				<div class="ic-button-container paddR5">
						<ihtml:nbutton property="btnVoid" accesskey="V" componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_BTNVOID" >
	                         <common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.button.void" />
	                    </ihtml:nbutton>
				        <ihtml:nbutton property="btnRerate" accesskey="E" componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_BTNRERATE" >
	                         <common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.button.rerate" />
	                    </ihtml:nbutton>
						<ihtml:nbutton property="btnReviewed"  componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_REVIEWED" accesskey="R" >
						<common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.button.reviewed" />
						</ihtml:nbutton>
					<ihtml:nbutton property="btnViewProrate"  componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_PRORATE" accesskey="V" >
						<common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.button.prorate" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btnChangeBillingStatus"  componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_CHANGESTATUS" accesskey="B" >
						<common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.button.changestatus" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btnClose"  componentID="MRA_AIRLINEBLG_DEFAULT_LISTBILLINGENTRY_CLOSE" accesskey="O" >
						<common:message key="mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.button.close" />
					</ihtml:nbutton>
				</div>
			</div>
		</div>
	</ihtml:form>
</div>






	</body>
</html:html>
