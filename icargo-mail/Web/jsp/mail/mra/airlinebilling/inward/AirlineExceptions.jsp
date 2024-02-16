<%--
* Project	 		: iCargo
* Module Code & Name: mra-airlinebilling
* File Name			: AirlineExceptions.jsp
* Date				: 13-Feb-2007
* Author(s)			: A-2105, A-2521
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.AirlineExceptionsForm" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsVO" %>



	
	
<html:html locale="true">
<head>
	
	
		
	
	<title><bean:message bundle="airlineExceptions" key="mra.inwardbilling.airlineexceptions.title.airlineexceptions" /></title>
	<meta name="decorator" content="mainpanel">
	<common:include type="script" src="/js/mail/mra/airlinebilling/inward/AirlineExceptions_Script.jsp" />
 <%@ include file="/jsp/includes/customcss.jsp" %> 
</head>
<body>
	
	
	
	
	<%@include file="/jsp/includes/reports/printFrame.jsp" %>
	<bean:define id="form" name="AirlineExceptionsForm"  type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.AirlineExceptionsForm" toScope="page" />
	<div id="pageDiv" class="iCargoContent ic-masterbg" style="overflow:auto; width:100%;height:100%">
		<ihtml:form action="/mailtracking.mra.airlinebilling.inward.airlineexceptions.onScreenLoad.do">
			<ihtml:hidden property="fromScreenFlag"/>
			<ihtml:hidden property="saveStatus"/>
			<ihtml:hidden property="closeStatusFlag"/>
			<html:hidden property="displayPage" />
			<html:hidden property="lastPageNumber" />  
			<input type="hidden" name="mySearchEnabled" /> 
			<business:sessionBean id="KEY_EXCPVOS" moduleName="mailtracking.mra.airlinebilling" screenID="mailtracking.mra.airlinebilling.inward.airlineexceptions" method="get" attribute="airlineExceptionsVOs" />
			<business:sessionBean id="KEY_EXCPCODS" moduleName="mailtracking.mra.airlinebilling" screenID="mailtracking.mra.airlinebilling.inward.airlineexceptions" method="get" attribute="exceptionsOneTime" />
			<div class="ic-content-main">
				<span class="ic-page-title ic-display-none"><common:message key="mra.inwardbilling.airlineexceptions.pagetitle.airlineexceptions" scope="request"/></span>
				<div class="ic-head-container">
					<div class="ic-filter-panel">
						<div class="ic-row">
							<div class="ic-input ic-split-45">
								<!--<fieldset class="iCargoFieldSet	">-->
								<fieldset class="ic-field-set">
									<legend class="iCargoLegend"><common:message key="mra.inwardbilling.airlineexceptions.fieldsetlabel.details" scope="request"/></legend>
									<div class="ic-input ic-split-50 ic-mandatory">
										<label><common:message key="mra.inwardbilling.airlineexceptions.lbl.fromdate" scope="request"/></label>
										<ibusiness:calendar componentID="CMP_MRA_INWARDBILLING_ASSIGNEXCEPTIONS_FROMDATE"
											property="fromDate" type="image" id="fromDate" value="<%=(String)form.getFromDate()%>"/>
									</div>
									<div class="ic-input ic-split-50 ic-mandatory">
										<label><common:message key="mra.inwardbilling.airlineexceptions.lbl.todate" scope="request"/></label>
										<ibusiness:calendar componentID="CMP_MRA_INWARDBILLING_ASSIGNEXCEPTIONS_TODATE"
										property="toDate" type="image" id="toDate" value="<%=(String)form.getToDate()%>"/>	
									</div>
								</fieldset>	
							</div>
							<div class="ic-input ic-split-15 ic-mandatory marginT20">	
								<label><common:message key="mra.inwardbilling.airlineexceptions.lbl.airlineCode" scope="request"/></label>
								<ihtml:text property="airlineCode" name="form" componentID="CMP_MRA_INWARDBILLING_ASSIGNEXCEPTIONS_AIRLINECODE"  maxlength="3" value="<%=(String)form.getAirlineCode()%>"/>
								<div class= "lovImg"><img name="airlineCodelov" id="airlineCodelov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" alt="" /></div>
							</div>
							<div class="ic-input ic-split-15 marginT20">	
								<label><common:message key="mra.inwardbilling.airlineexceptions.lbl.exceptioncode" scope="request"/></label>
								<%HashMap hashExps = new HashMap();%>
								<ihtml:select componentID="CMP_MRA_INWARDBILLING_ASSIGNEXCEPTIONS_EXCEPTIONCODE" property="exceptionCode">
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									<logic:present name="KEY_EXCPCODS">
										<logic:iterate id="oneTimeValue" name="KEY_EXCPCODS">
												<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
												<logic:equal name="parameterCode" value="mailtracking.mra.airlinebilling.exceptioncodes">
											<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
											<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
												<logic:present name="parameterValue" property="fieldValue">
													<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
													<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
													<logic:notEqual name="parameterValue" property="fieldValue"  value="DNF">
														<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>"><%=String.valueOf(fieldDescription)%></ihtml:option>
														<%= hashExps.put(fieldValue,fieldDescription)%>
													</logic:notEqual>
												</logic:present>
											</logic:iterate>
												</logic:equal>
										</logic:iterate>
									</logic:present>
								</ihtml:select>
							</div>
							<div class="ic-input ic-split-15 marginT20">	
								<label><common:message key="mra.inwardbilling.airlineexceptions.lbl.invrefno" scope="request"/></label>
								<ihtml:text property="invoiceRefNo" name="form" componentID="CMP_MRA_INWARDBILLING_ASSIGNEXCEPTIONS_INVREFNO"   value="<%=(String)form.getInvoiceRefNo()%>"/>
								<div class= "lovImg"><img name="invoiceRefNolov" id="invoiceRefNolov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" alt="" /></div>
							</div>
						</div>
						<div class="ic-row marginT10">
								<div class="ic-input ic-split-10 ">	
									<label><common:message key="mra.inwardbilling.airlineexceptions.lbl.orgoe" /></label>
									<ihtml:text property="originOfficeOfExchange" componentID="CMP_MRA_INWARDBILLING_ASSIGNEXCEPTIONS_OOE" readonly="false" maxlength="6" />
									<div class= "lovImg"><img name="mailOOELov" id="mailOOELov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"></div>
								</div>
								<div class="ic-input ic-split-10 ">	
									<label><common:message key="mra.inwardbilling.airlineexceptions.lbl.destoe" /></label>
									<ihtml:text property="destinationOfficeOfExchange" componentID="CMP_MRA_INWARDBILLING_ASSIGNEXCEPTIONS_DOE" readonly="false" maxlength="6" />
									<div class= "lovImg"><img name="mailDOELov" id="mailDOELov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"></div>
								</div>
								<div class="ic-input ic-split-15">	
									<label><common:message key="mra.inwardbilling.airlineexceptions.lbl.category" /></label>
									<!--<div class="select-style">-->
									<ihtml:select componentID="CMP_MRA_INWARDBILLING_ASSIGNEXCEPTIONS_category" property="mailCategory">
										<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
										<logic:present name="KEY_EXCPCODS">
											<logic:iterate id="oneTimeValue" name="KEY_EXCPCODS">
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
									<!--</div>-->
								</div>
								<div class="ic-input ic-split-10 ">	
								   <label><common:message key="mra.inwardbilling.airlineexceptions.lbl.subclass" /></label>
								   <ihtml:text property="subClass" componentID="CMP_MRA_INWARDBILLING_ASSIGNEXCEPTIONS_SUBCLASS" maxlength="2"/>
								   <div class= "lovImg"><img name="subClassFilterLOV" id="subClassFilterLOV" value="subClassLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" /></div>
								</div>
								<div class="ic-input ic-split-10 ">	
									<label><common:message key="mra.inwardbilling.airlineexceptions.lbl.year" /></label>
									 <ihtml:text property="year" componentID="CMP_MRA_INWARDBILLING_ASSIGNEXCEPTIONS_YEAR" readonly="false" maxlength="1" />				          
								</div>
								<div class="ic-input ic-split-10 ">	
									 <label><common:message key="mra.inwardbilling.airlineexceptions.lbl.dsn" /></label>
									  <ihtml:text property="dsn" componentID="CMP_MRA_INWARDBILLING_ASSIGNEXCEPTIONS_DSN" readonly="false" maxlength="4" />				          
								</div>
								<div class="ic-input ic-split-10 ">	
									<label><common:message key="mra.inwardbilling.airlineexceptions.lbl.rsn" /></label>
									<ihtml:text property="receptacleSerialNumber" componentID="CMP_MRA_INWARDBILLING_ASSIGNEXCEPTIONS_RSN" readonly="false" maxlength="3" />				          
								</div>
								<div class="ic-input ic-split-10 ">	
									<label><common:message key="mra.inwardbilling.airlineexceptions.lbl.hni" /></label>
									<ihtml:text property="highestNumberIndicator" componentID="CMP_MRA_INWARDBILLING_ASSIGNEXCEPTIONS_HNI" readonly="false" maxlength="1" />				          
								</div>
								<div class="ic-input ic-split-10 ">	
									<label><common:message key="mra.inwardbilling.airlineexceptions.lbl.ri" /></label>
									<ihtml:text property="registeredIndicator" componentID="CMP_MRA_INWARDBILLING_ASSIGNEXCEPTIONS_RI" readonly="false" maxlength="1" />				          
								</div>
							
							<div class="ic-input ic-split-100 "></div>
							<div class= "ic-row">							
								<div class="ic-button-container">
								<ihtml:nbutton property="btnList" componentID="CMP_MRA_INWARDBILLING_ASSIGNEXCEPTIONS_LIST" accesskey="L">
									<common:message key="mra.inwardbilling.airlineexceptions.btn.lbl.list" />
								</ihtml:nbutton>
								<ihtml:nbutton property="clearButton" componentID="CMP_MRA_INWARDBILLING_ASSIGNEXCEPTIONS_CLEAR" accesskey="C">
									<common:message key="mra.inwardbilling.airlineexceptions.btn.lbl.clear" />
								</ihtml:nbutton>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="ic-main-container">
					<div class="ic-row paddL5">
						<h4><common:message key="mra.inwardbilling.airlineexceptions.headinglabel.details" /></h4>		
					</div>
					<div class="ic-input ic-split-75 paddL5">
						<logic:present name="KEY_EXCPVOS">
							<bean:define id="airlineExceptionPage" name="KEY_EXCPVOS"/>
							<common:paginationTag pageURL="mailtracking.mra.airlinebilling.inward.airlineexceptions.onList.do"
									name="airlineExceptionPage"
									display="label"
									labelStyleClass="iCargoResultsLabel"
									lastPageNum="<%=form.getLastPageNumber() %>"/>
						</logic:present>
						<logic:notPresent name="airlineExceptionPage">
							&nbsp;
						</logic:notPresent>
					</div>
					<div class="ic-input ic-split-25" style="text-align:right;">
						<logic:present name="airlineExceptionPage">
							<common:paginationTag pageURL="javascript:submitPage('lastPageNum','displayPage')"
							name="airlineExceptionPage"
							display="pages"
							lastPageNum="<%=form.getLastPageNumber()%>"  
							linkStyleClass="iCargoLink"
                                    disabledLinkStyleClass="iCargoLink"
							exportToExcel="true"
							exportTableId="AssignExceptions" 
							exportAction = "mailtracking.mra.airlinebilling.inward.airlineexceptions.onList.do"/>
						</logic:present>
						<logic:notPresent name="airlineExceptionPage">
							&nbsp;
						</logic:notPresent>
					</div>
					<div class="ic-row">
						<div class="ic-section">
							<div id="div1" class="tableContainer" style="width:100%;height:550px;">
					            <table class="fixed-header-table"  id="AssignExceptions" >
					              <thead>
					                   <tr>
										<td  class="iCargoTableHeader"  rowspan="2"  width="2%;text-align:center;"><input type="checkbox" name="headChk"  value="checkbox" onclick="updateHeaderCheckBox(this.form,this.form.headChk,this.form.selectedRows)"/></td>
										<td class="iCargoTableHeader" rowspan="2" width="5%">
										<common:message key="mra.inwardbilling.airlineexceptions.tablehead.invrefno" />
										</td>

										<td class="iCargoTableHeader" rowspan="2" width="5%">
										<common:message key="mra.inwardbilling.airlineexceptions.tablehead.clearanceperiod" />
										</td>

										
										<td class="iCargoTableHeader" rowspan="2" width="5%">
										<common:message key="mra.inwardbilling.airlineexceptions.lbl.orgoe" />
										</td>

										<td class="iCargoTableHeader" rowspan="2" width="5%">
										<common:message key="mra.inwardbilling.airlineexceptions.lbl.destoe" />
										</td>
										<td class="iCargoTableHeader" rowspan="2" width="5%">
										<common:message key="mra.inwardbilling.airlineexceptions.lbl.category" />
										</td>
										<td class="iCargoTableHeader" rowspan="2" width="5%">
										<common:message key="mra.inwardbilling.airlineexceptions.lbl.subclass" />
										</td>
										<td class="iCargoTableHeader" rowspan="2" width="3%">
										<common:message key="mra.inwardbilling.airlineexceptions.lbl.year" />
										</td>
								
										<td class="iCargoTableHeader" rowspan="2" width="5%">
										<common:message key="mra.inwardbilling.airlineexceptions.lbl.dsn" />
										</td>
										<td class="iCargoTableHeader" rowspan="2" width="4%">
										<common:message key="mra.inwardbilling.airlineexceptions.lbl.rsn" />
										</td>
										<td class="iCargoTableHeader" rowspan="2" width="3%">
										<common:message key="mra.inwardbilling.airlineexceptions.lbl.hni" />
										</td>
										
										
										<td class="iCargoTableHeader" rowspan="2" width="3%">
										<common:message key="mra.inwardbilling.airlineexceptions.lbl.ri" />
										</td>

										<td class="iCargoTableHeader" rowspan="2" width="5%">
										<common:message key="mra.inwardbilling.airlineexceptions.tablehead.exceptioncode" />
										</td>

										<td class="iCargoTableHeader" colspan="2"width="10%">
										<common:message key="mra.inwardbilling.airlineexceptions.tablehead.provisional" />
										</td>

										<td class="iCargoTableHeader" colspan="2" width="10%">
										<common:message key="mra.inwardbilling.airlineexceptions.tablehead.reported" />
										</td>

										<td class="iCargoTableHeader" rowspan="2" width="5%">
										<common:message key="mra.inwardbilling.airlineexceptions.tablehead.memcod" />
										</td>

										<td class="iCargoTableHeader" rowspan="2" width="5%">
											<common:message key="mra.inwardbilling.airlineexceptions.tablehead.memstatus" />
										</td>

										<td class="iCargoTableHeader" rowspan="2" width="10%">
										<common:message key="mra.inwardbilling.airlineexceptions.tablehead.assignee" />
										</td>

										<td class="iCargoTableHeader" rowspan="2" width="10%">
										<common:message key="mra.inwardbilling.airlineexceptions.tablehead.assigneddate" />
										</td>
										<td  class="iCargoTableHeader" rowspan="2" width="5%">
											<common:message key="mra.inwardbilling.airlineexceptions.tablehead.remarks"/>
										</td>
									 </tr>
									 <tr>
										 <!--<td class="iCargoTableHeader">
											<common:message key="mra.inwardbilling.airlineexceptions.tablehead.provrate" />
										 </td>-->
										 <td class="iCargoTableHeader">
										 	<common:message key="mra.inwardbilling.airlineexceptions.tablehead.provwght" />
										 </td>
										 <td class="iCargoTableHeader">
										 	<common:message key="mra.inwardbilling.airlineexceptions.tablehead.provamt" />
										 </td>
										 <!--<td class="iCargoTableHeader">
											<common:message key="mra.inwardbilling.airlineexceptions.tablehead.rptdrate" />
										 </td>-->
										 <td class="iCargoTableHeader">
											<common:message key="mra.inwardbilling.airlineexceptions.tablehead.rptdwght" />
										 </td>
										 <td class="iCargoTableHeader">
											<common:message key="mra.inwardbilling.airlineexceptions.tablehead.rptdamt" />
										 </td>
									 </tr>
									</thead>
									<tbody>

									<logic:present name="KEY_EXCPVOS">
									<logic:iterate id="excpVO" name="KEY_EXCPVOS" indexId="rowIndex">


									<tr class="iCargoTableDataRow1">

										<ihtml:hidden  property="operationalFlag"  name="excpVO"/>
										<td  class="iCargoTableTd"> <ihtml:checkbox property="selectedRows"  onclick="toggleTableHeaderCheckbox('selectedRows',this.form.headChk)" value="<%=String.valueOf(rowIndex)%>" /></td>

										<td>&nbsp;<common:write name="excpVO" property="invoiceNumber"/></td>
										<td>&nbsp;<common:write name="excpVO" property="clearancePeriod"/></td>
										
										<td>&nbsp;<common:write name="excpVO" property="orgOfficeOfExchange"/></td>
										<td>&nbsp;<common:write name="excpVO" property="destOfficeOfExchange"/></td>
										<td>&nbsp;<common:write name="excpVO" property="mailCategoryCode"/></td>
										<td>&nbsp;<common:write name="excpVO" property="mailSubClass"/></td>
										<td>&nbsp;<common:write name="excpVO" property="year"/></td>
										<td>&nbsp;<common:write name="excpVO" property="despatchSerNo"/></td>
										<td>&nbsp;<common:write name="excpVO" property="receptacleSerialNumber"/></td>
										<td>&nbsp;<common:write name="excpVO" property="highestNumberIndicator"/></td>
										
										<td>&nbsp;<common:write name="excpVO" property="registeredIndicator"/></td>
										<td iCargoLabelLeftAligned>

										<logic:present name="excpVO" property="exceptionCode">
												<bean:define id="expCode" name="excpVO" property="exceptionCode"/>
												<%=hashExps.get(expCode)%>
										</logic:present>

										</td>
										<!--<td><div align="right">&nbsp;<common:write name="excpVO" property="provRate"/></div></td>-->
										<td><div align="right">&nbsp;<common:write name="excpVO" property="provWeight" unitFormatting="true" />
										</div></td>
										<td style="text-align:right">
										
										<logic:present name="excpVO" property="provAmt">
											 <ibusiness:moneyDisplay showCurrencySymbol="false" name="excpVO" moneyproperty="provAmt" property="provAmt" />
										</logic:present>
										<logic:notPresent name="excpVO" property="provAmt">
											  &nbsp;
										</logic:notPresent>

										
										</td>
										<!--<td><div align="right">&nbsp;<common:write name="excpVO" property="reportedRate"/></div></td>-->
										<td><div align="right">&nbsp;<common:write name="excpVO" property="rptdWeight" unitFormatting="true" />
										</div></td>
										<td style="text-align:right">
										
										<logic:present name="excpVO" property="reportedAmt">
											 <ibusiness:moneyDisplay showCurrencySymbol="false" name="excpVO" moneyproperty="reportedAmt" property="reportedAmt" />
										</logic:present>
										<logic:notPresent name="excpVO" property="reportedAmt">
											  &nbsp;
										</logic:notPresent>
										
										</td>
										<td>&nbsp;<common:write name="excpVO" property="memCode"/></td>
										<td>
										<logic:present name="excpVO" property="memStaus">
											<logic:present name="KEY_EXCPCODS">
											<logic:iterate id="oneTimeValue" name="KEY_EXCPCODS">
											<bean:define id="parameterCode" name="oneTimeValue" property="key" />
												<logic:equal name="parameterCode" value="mra.airlinebilling.billingstatus">
												<bean:define id="parameterValues" name="oneTimeValue" property="value" />
													<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
														<logic:present name="parameterValue">
														<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
															<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																<logic:equal name="excpVO" property="memStaus" value="<%=(String)fieldValue%>">
																<%=(String)fieldDescription%>
																</logic:equal>
														</logic:present>
													</logic:iterate>
												</logic:equal>
											</logic:iterate>
											</logic:present>
										</logic:present>
										</td>

										<td class="ic-input">
											<ihtml:text indexId="rowIndex" name="excpVO" componentID="CMP_MRA_INWARDBILLING_ASSIGNEXCEPTIONS_ASSIGNEE" property="assigneeCode"
											maxlength="10" readonly="false"/>
											  <% String showAssigneeLov = "showAssigneeLov("+rowIndex+")"; %>
											   <div class="lovImgTbl"><img name="assigneeTableLov" id="assigneeTableLov" height="16"  src="<%=request.getContextPath()%>/images/lov.png" onclick="<%=showAssigneeLov%>" width="16" alt="" />
											   </div>
											<!--
											<img name="assigneelov" indexId="rowIndex" id="assigneelov"
											src="<%=request.getContextPath()%>/images/lov.gif"	onClick="displayLOV('showUserLOV.do','N','Y','showUserLOV.do',document.forms[1].assigneeCode.value,'Assignee Code','1','assigneeCode','','<%=rowIndex%>')" alt="" />
										    -->
										</td>

										<td>
											<logic:present name="excpVO" property="assignedDate">
												<bean:define id="assnDate" name="excpVO" property="assignedDate" type="LocalDate"/>
												<% String assnDate_str = TimeConvertor.toStringFormat(((LocalDate)assnDate).toCalendar(),"dd-MMM-yyyy"); %>
												<ibusiness:calendar  indexId="rowIndex" componentID="CMP_MRA_INWARDBILLING_ASSIGNEXCEPTIONS_ASSIGNEDDATE"  property="assignedDate" value="<%= assnDate_str %>" type="image" id="assnDateTB" />
											</logic:present>

											<logic:notPresent name="excpVO" property="assignedDate">
												<ibusiness:calendar  indexId="rowIndex" componentID="CMP_MRA_INWARDBILLING_ASSIGNEXCEPTIONS_ASSIGNEDDATE" property="assignedDate" value="" type="image" id="assnDateTB" />
											</logic:notPresent>
										</td>

										<td>
											<common:write name="excpVO" property="remark"/>
										</td>
										<logic:present name="excpVO" property="exceptionStatus">
											<bean:define id="expStatus" name="excpVO" property="exceptionStatus" />
											<ihtml:hidden  property="expStatus" value="<%=(String)expStatus%>"/>
										</logic:present>

										<logic:notPresent name="excpVO" property="exceptionStatus">
											<ihtml:hidden  property="expStatus" />
										</logic:notPresent>

									</tr>

									</logic:iterate>
									</logic:present>

									</tbody>
									</table>
									</div>
						</div>
					</div>	
				</div>
				<div class="ic-foot-container">
					<div class="ic-button-container paddR5">	
						<ihtml:nbutton property="btnAccept" componentID="CMP_MRA_INWARDBILLING_ASSIGNEXCEPTIONS_ACCEPT" accesskey="T">
							<common:message key="mra.inwardbilling.airlineexceptions.btn.lbl.accept" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnIssueRejectionMemo" componentID="CMP_MRA_INWARDBILLING_ASSIGNEXCEPTIONS_ISSUEREJECTIONMEMO" accesskey="M">
							<common:message key="mra.inwardbilling.airlineexceptions.btn.lbl.issuerejectionmemo" />
						</ihtml:nbutton>
						<!--Added by Indu for print starts-->
						<ihtml:nbutton property="printExpDetailButton" componentID="CMP_MRA_INWARDBILLING_ASSIGNEXCEPTIONS_PRINTEXPDTL" accesskey="P">
						<common:message key="mra.inwardbilling.airlineexceptions.btn.lbl.printexpdtl" />
						</ihtml:nbutton>
						<!--Added by Indu for print ends-->

						<ihtml:nbutton property="saveButton" componentID="CMP_MRA_INWARDBILLING_ASSIGNEXCEPTIONS_SAVE" accesskey="S">
						  <common:message key="mra.inwardbilling.airlineexceptions.btn.lbl.save" />
						</ihtml:nbutton>
						<ihtml:nbutton property="closeButton" componentID="CMP_MRA_INWARDBILLING_ASSIGNEXCEPTIONS_CLOSE" accesskey="O">
							<common:message   key="mra.inwardbilling.airlineexceptions.btn.lbl.close" />
						</ihtml:nbutton>
					</div>
				</div>	
			</div>
		</ihtml:form>
	</div>
</body>		
					
	</body>
</html:html>
