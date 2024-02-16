<%--******************************************************************************************
* Project	 		: iCargo
* Module Code & Name		: AssignException.jsp
* Date				: 12-Feb-2007
* Author(s)			: A-2257, A-2245
 ******************************************************************************************--%>

<%@ include file="/jsp/includes/tlds.jsp" %>


<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.AssignExceptionsForm" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import="java.util.Formatter" %>
<%
   String FORMAT_STRING = "%1$-16.2f";
%>

	

<bean:define id="form" name="MRAAssignExceptionsForm" type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.AssignExceptionsForm" toScope="page" />

	
	
<html:html>
<head>
	
	
		
	
	<title><common:message bundle="<%=form.getBundle()%>" key="mailtracking.mra.gpareporting.assignexceptions.title.assignexceptions" />

	</title>

	<meta name="decorator" content="mainpanel">
	<common:include type="script" src="/js/mail/mra/gpareporting/AssignExceptions_Script.jsp"/>

</head>
<body style="overflow:auto;">
	
	
	
	
	<div id="pageDiv" class="iCargoContent" style="overflow:auto; width:100%;height:100%">

		<ihtml:form action="/mailtracking.mra.gpareporting.screenloadassignexceptions.do">
			<ihtml:hidden property="displayPage" />
			<ihtml:hidden property="lastPageNum" />
			<ihtml:hidden property="saveFlag" />
			<ihtml:hidden property="windowFlag" />
			<ihtml:hidden property="currentDialogOption" />
			<ihtml:hidden property="currentDialogId" />
			<business:sessionBean id="GpaReportingDetailVOPage"
			  moduleName="mailtracking.mra"
			  screenID="mailtracking.mra.gpareporting.assignexceptions"
			  method="get"
			  attribute="gpaReportingDetailVOs" />

			<div class="ic-content-main">
				<span class="ic-page-title ic-display-none"><common:message key="mailtracking.mra.gpareporting.assignexceptions.lbl.assignexceptions" /></span>
				<div class="ic-head-container">
					<div class="ic-filter-panel">
						<div class="ic-row ic-border">
							<div class="ic-input ic-split-40 ic-mandatory ">
								<label><common:message key="mailtracking.mra.gpareporting.assignexceptions.lbl.gpacode" /></label>
								<ihtml:text property="gpaCode" componentID="CMP_MRA_AssignExceptions_GPACode" maxlength="5" tabindex="1" />
								<img name="gpaCodeLov" id="gpaCodeLov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" tabindex="2" alt="" />
							</div>
							<div class="ic-input ic-split-40 ">
								<fieldset class="iCargoFieldSet">
									<legend class="iCargoLegend"><common:message key="mailtracking.mra.gpareporting.assignexceptions.lbl.reportingperiod" /></legend>
										<div class="ic-input ic-split-50 ic-mandatory ">
											<label><common:message key="mailtracking.mra.gpareporting.assignexceptions.lbl.fromdate" /></label>
											<ibusiness:calendar
												componentID="CMP_MRA_AssignExceptions_FromDate"
												property="fromDate"
												value="<%=form.getFromDate()%>"
												type="image"
												id="frmDate"
												tabindex="3" />
										</div>
										<div class="ic-input ic-split-50 ic-mandatory ">
											<label><common:message key="mailtracking.mra.gpareporting.assignexceptions.lbl.todate" /></label>
											<ibusiness:calendar
												componentID="CMP_MRA_AssignExceptions_ToDate"
												property="toDate"
												value="<%=form.getToDate()%>"
												type="image"
												id="toDate"
												tabindex="4" />
										</div>	
								</fieldset>	
							</div>
							<div class="ic-input ic-split-50 ">
								<common:message key="mailtracking.mra.gpareporting.assignexceptions.lbl.exceptioncode" />
								<business:sessionBean id="oneTimeValues"
								  moduleName="mailtracking.mra"
								  screenID="mailtracking.mra.gpareporting.assignexceptions"
								  method="get"
								  attribute="oneTimeVOs" />

								<ihtml:select property="exceptionCode" value="<%=form.getExceptionCode()%>" style="width:150px;" componentID="CMP_MRA_AssignExceptions__ExceptionCode" tabindex="5" >
									<html:option value=""><common:message key="combo.select"/></html:option>
									<logic:present name="oneTimeValues">
										<bean:define id="oneTimeValuesMap" name="oneTimeValues" type="java.util.HashMap" />
										<logic:iterate id="oneTimeValues" name="oneTimeValuesMap">
											<logic:equal name="oneTimeValues" property="key" value="mailtracking.mra.gpareporting.exceptioncodes" >
												<logic:iterate id="oneTimeVO" name="oneTimeValues" property="value" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
													<html:option value="<%=oneTimeVO.getFieldValue()%>"><%=oneTimeVO.getFieldDescription()%></html:option>
												</logic:iterate>
											</logic:equal>
										</logic:iterate>
									</logic:present>
							   </ihtml:select>
							</div>
							<div class="ic-input ic-split-50 ">
								<common:message key="mailtracking.mra.gpareporting.assignexceptions.lbl.assignee" />
								<ihtml:text property="assignee" componentID="CMP_MRA_AssignExceptions_Assignee" maxlength="7" tabindex="6" />
								<img name="assigneeLov" id="assigneeLov" height="16"  src="<%=request.getContextPath()%>/images/lov.gif" width="16" tabindex="7" alt="" />
							</div>
							<div class="ic-row">
								<div class="ic-button-container">
									<ihtml:nbutton property="btnList" accesskey = "L" componentID="CMP_MRA_AssignExceptions__BTNLIST" tabindex="8" >
										<common:message key="mailtracking.mra.gpareporting.assignexceptions.btn.list" />
								   </ihtml:nbutton>
								   <ihtml:nbutton property="btnClear" accesskey = "C" componentID="CMP_MRA_AssignExceptions__BTNCLEAR" tabindex="9" >
										<common:message key="mailtracking.mra.gpareporting.assignexceptions.btn.clear" />
								   </ihtml:nbutton>
								</div>
							</div>
						</div>	
					</div>
				</div>
				<div class="ic-main-container">
					<div class="ic-row">
						<h4><common:message key="mailtracking.mra.gpareporting.assignexceptions.assignexceptiontable" /></h4>		
					</div>
					<div class="ic-col-30">
						 <logic:present name="GpaReportingDetailVOPage">
						   <common:paginationTag pageURL="mailtracking.mra.gpareporting.listassignexceptions.do"
							name="GpaReportingDetailVOPage" display="label"
							labelStyleClass="iCargoResultsLabel"
							lastPageNum="<%=form.getLastPageNum() %>" />
						 </logic:present>
					</div>
					<div class="ic-right">
						<logic:present name="GpaReportingDetailVOPage">
							 <common:paginationTag pageURL="javascript:submitPage1('lastPageNum','displayPage')"
							  name="GpaReportingDetailVOPage"
							  display="pages"
							  lastPageNum="<%=form.getLastPageNum()%>" 
							  exportToExcel="true"
							  exportTableId="assignExcptnTable"
							  exportAction="mailtracking.mra.gpareporting.listassignexceptions.do"/>
						 </logic:present>
					</div>
					<div class="ic-row">
						<div class="ic-section ic-border">
						<div id="div1" class="tableContainer" style="width:100%;height:607px;">
							<table class="fixed-header-table" id="assignExcptnTable">
					  <thead>
					     <tr>
						  <td class="iCargoTableHeader" rowspan="2" >
						  <input type="checkbox" name="headChk" onclick="updateHeaderCheckBox(this.form,this.form.headChk,this.form.rowId)"/>
						  </td>

						  <td class="iCargoTableHeader" rowspan="2">
						  <common:message key="mailtracking.mra.gpareporting.assignexceptions.tblheader.mailbag" />
						  </td>

						  <td class="iCargoTableHeader" colspan="6">
						  <common:message key="mailtracking.mra.gpareporting.assignexceptions.tblheader.dispatch" />
						  </td>

						  <td class="iCargoTableHeader" rowspan="2">
						  <common:message key="mailtracking.mra.gpareporting.assignexceptions.tblheader.exception" />
						  </td>
						<td  class="iCargoTableHeader"  colspan="3">	
					<!--charge	-->
						 <common:message key="mailtracking.mra.gpareporting.assignexceptions.tblheader.charge" />
						</td>
						<!--  <td class="iCargoTableHeader" colspan="3">
						  <common:message key="mailtracking.mra.gpareporting.assignexceptions.tblheader.weight" />
						  </td>-->

						  <!--<td class="iCargoTableHeader" colspan="3">
						  <common:message key="mailtracking.mra.gpareporting.assignexceptions.tblheader.rate" />
						  </td>-->

						  <td class="iCargoTableHeader" rowspan="2">
						  <common:message key="mailtracking.mra.gpareporting.assignexceptions.tblheader.assignee" />
						  </td>

						  <td class="iCargoTableHeader" rowspan="2">
						  <common:message key="mailtracking.mra.gpareporting.assignexceptions.tblheader.assigneddate" />
						  </td>

						  <td class="iCargoTableHeader" rowspan="2">
						  <common:message key="mailtracking.mra.gpareporting.assignexceptions.tblheader.resolveddate" />
						  </td>
						  
						  <td class="iCargoTableHeader" rowspan="2">
						  
						 <!-- CCA Number-->
						  <common:message key="mailtracking.mra.gpareporting.assignexceptions.tblheader.ccanumber" />
						  </td>
						  
						  <td class="iCargoTableHeader" rowspan="2">
						  <!--CCA Status-->
						  <common:message key="mailtracking.mra.gpareporting.assignexceptions.tblheader.ccastatus" />
						  </td>

						 </tr>

					     <tr>

						  <td class="iCargoTableHeader"  >
						  <common:message key="mailtracking.mra.gpareporting.assignexceptions.tblheader.origin" />
						  </td>

						  <td class="iCargoTableHeader"  >
						  <common:message key="mailtracking.mra.gpareporting.assignexceptions.tblheader.destn" />
						  </td>

						  <td class="iCargoTableHeader"  >
						  <common:message key="mailtracking.mra.gpareporting.assignexceptions.tblheader.mailcat" />
						  </td>

						  <td class="iCargoTableHeader"  >
						  <common:message key="mailtracking.mra.gpareporting.assignexceptions.tblheader.mailsubcls" />
						  </td>

						  <td class="iCargoTableHeader"  >
						  <common:message key="mailtracking.mra.gpareporting.assignexceptions.tblheader.year" />
						  </td>

						  <td class="iCargoTableHeader"  >
						  <common:message key="mailtracking.mra.gpareporting.assignexceptions.tblheader.dsn" />
						  </td>

						  <td class="iCargoTableHeader" >
						  <common:message key="mailtracking.mra.gpareporting.assignexceptions.tblheader.ourvalue" />
						  </td>

						  <td class="iCargoTableHeader"  >
						  <common:message key="mailtracking.mra.gpareporting.assignexceptions.tblheader.reportedvalue" />
						  </td>

						  <td class="iCargoTableHeader"  >
						  <common:message key="mailtracking.mra.gpareporting.assignexceptions.tblheader.difference" />
						  </td>

						<!--  <td class="iCargoTableHeader" >
						  <common:message key="mailtracking.mra.gpareporting.assignexceptions.tblheader.ourvalue" />
						  </td>

						  <td class="iCargoTableHeader"  >
						  <common:message key="mailtracking.mra.gpareporting.assignexceptions.tblheader.reportedvalue" />
						  </td>

						  <td class="iCargoTableHeader"  >
						  <common:message key="mailtracking.mra.gpareporting.assignexceptions.tblheader.difference" />
						  </td>-->

						 </tr>

					  </thead>
					  <tbody>
					  <logic:present name="GpaReportingDetailVOPage">
						<logic:iterate id="GpaReportingDetailVO" name="GpaReportingDetailVOPage" indexId="rowCount" type="com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO">
						<%--
							System.out.println("GpaReportingDetailVO>>>>>>"+GpaReportingDetailVO);
						--%>
						<ihtml:hidden name="GpaReportingDetailVO" property="operationFlag" />
						<common:rowColorTag index="rowCount">
						 <tr bgcolor="<%=color%>">
						  <td>
						  <input type="checkbox" name="rowId" value="<%=String.valueOf(rowCount)%>" onclick="toggleTableHeaderCheckbox('rowId',document.forms[1].headChk)" />
						</td>
						  <td width="5%">&nbsp;

					
						  <logic:present name="GpaReportingDetailVO" property="billingBasis">
						  	<common:write name="GpaReportingDetailVO" property="billingBasis" />
						   </logic:present>
						  </td>
						  <td>
						  <logic:present name="GpaReportingDetailVO" property="originOfficeOfExchange">
							<common:write name="GpaReportingDetailVO" property="originOfficeOfExchange" />
						  </logic:present>
						  </td>
						  <td>
						  <logic:present name="GpaReportingDetailVO" property="destOfficeOfExchange">
							<common:write name="GpaReportingDetailVO" property="destOfficeOfExchange" />
						  </logic:present>
						  </td>
						  <td>
						  <logic:present name="GpaReportingDetailVO" property="mailCategoryCode">
						    <logic:present name="oneTimeValuesMap">
							    <logic:iterate id="oneTimeValues" name="oneTimeValuesMap">
							    <logic:equal name="oneTimeValues" property="key" value="mailtracking.defaults.mailcategory" >
							    <logic:iterate id="MailCategoryValue" name="oneTimeValues" property="value" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
							    	<logic:equal name="MailCategoryValue" property="fieldValue" value="<%=GpaReportingDetailVO.getMailCategoryCode()%>" >
									<common:write name="MailCategoryValue" property="fieldDescription" />
								</logic:equal>
							    </logic:iterate>
							    </logic:equal>
							    </logic:iterate>
						    </logic:present>
						  </logic:present>
						  </td>
						  <td width="4%" >
						  <logic:present name="GpaReportingDetailVO" property="mailSubClass">
							<common:write name="GpaReportingDetailVO" property="mailSubClass" />
						  </logic:present>
						  </td>
						  <td>
						  <logic:present name="GpaReportingDetailVO" property="year">
							<common:write name="GpaReportingDetailVO" property="year" />
						  </logic:present>
						  </td>
						  <td widht="8%" style="text-align:right">
						  <logic:present name="GpaReportingDetailVO" property="dsnNumber">
							<common:write name="GpaReportingDetailVO" property="dsnNumber" />
						  </logic:present>
						  </td>
						<td width="11%">
						  <logic:present name="GpaReportingDetailVO" property="exceptionCode">
						    <logic:present name="oneTimeValuesMap">
							    <logic:iterate id="oneTimeValues" name="oneTimeValuesMap">
							    <logic:equal name="oneTimeValues" property="key" value="mailtracking.mra.gpareporting.exceptioncodes" >
							    <logic:iterate id="oneTimeVO" name="oneTimeValues" property="value" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
							    <logic:equal name="oneTimeVO" property="fieldValue" value="<%=GpaReportingDetailVO.getExceptionCode()%>">
								<common:write name="oneTimeVO" property="fieldDescription" />
							    </logic:equal>
							    </logic:iterate>
							    </logic:equal>
							    </logic:iterate>
						    </logic:present>
						  </logic:present>
						  </td>
						  <td width="4%" style="text-align:right">
						  
						  <%double actualCharges=0.0;%>
						  <logic:present name="GpaReportingDetailVO" property="actualCharge">
						  	<bean:define id="actualCharge" name="GpaReportingDetailVO" property="actualCharge" type="java.lang.Double" />
						  	<%actualCharges=actualCharge;%>
							<common:write name="GpaReportingDetailVO" property="actualCharge" format="############.00" />
						  </logic:present>
						
						  </td>
						 <td width="5%" style="text-align:right">
						 
						  <%double reportedCharges=0.0;%>
						  <logic:present name="GpaReportingDetailVO" property="reportedCharge">
						  	<bean:define id="reportedCharge" name="GpaReportingDetailVO" property="reportedCharge" type="java.lang.Double" />
						  	<%reportedCharges=reportedCharge;%>
							<common:write name="GpaReportingDetailVO" property="reportedCharge" format="############.00" />
						  </logic:present>
						  
						  </td>
						 <td width="4%">
						  <div align="right">
						  <%-- =(actualCharges-reportedCharges) --%>
						  <%=new Formatter().format(FORMAT_STRING,(actualCharges-reportedCharges)).toString().trim()%>
						  </div>
						  </td>

						  <!--  <td>
						  <%double actualRt=0.0;%>
						  <div align="right">
						  <logic:present name="GpaReportingDetailVO" property="actualRate">
						  	<bean:define id="actualRate" name="GpaReportingDetailVO" property="actualRate" type="java.lang.Double" />
						  	<%actualRt=actualRate;%>
							<common:write name="GpaReportingDetailVO" property="actualRate" format="############.00" />
						  </logic:present>
						  </div>
						  </td>
						<td>
						  <%double reportedRt=0.0;%>
						  <div align="right">
						  <logic:present name="GpaReportingDetailVO" property="reportedRate">
						  	<bean:define id="reportedRate" name="GpaReportingDetailVO" property="reportedRate" type="java.lang.Double" />
						  	<%reportedRt=reportedRate;%>
							<common:write name="GpaReportingDetailVO" property="reportedRate" format="############.00" />
						  </logic:present>
						  </div>
						  </td>
						  <td>
						  <div align="right">
						  <%--=(actualRt-reportedRt)--%>
						  <%=new Formatter().format(FORMAT_STRING,(actualRt-reportedRt)).toString().trim()%>
						  </div>
						  </td>-->
						  <td width="8%">

						  <ihtml:text property="assignedUser" name="GpaReportingDetailVO" indexId="rowCount" componentID="CMP_MRA_AssignExceptions_AssigneeTable" style="text-transform : uppercase"  />
						  <% String showAssigneeLov = "showAssigneeLov("+rowCount+")"; %>
						  <img name="assigneeTableLov" id="assigneeTableLov" height="16"  src="<%=request.getContextPath()%>/images/lov.gif"
						  	onclick="<%=showAssigneeLov%>" width="15" alt="" />
						  </td>
						  <td width="8%">
						  <logic:present name="GpaReportingDetailVO" property="assignedDate">
							<%

							  String assignedLocalDate = TimeConvertor.toStringFormat(((LocalDate)GpaReportingDetailVO.getAssignedDate()).toCalendar(),"dd-MMM-yyyy");

							%>
							   <ibusiness:calendar
								componentID="CMP_MRA_AssignExceptions_AssigneeDate"
								property="assignedDate"
								value="<%=assignedLocalDate%>"
								 indexId="rowCount"
								type="image"
								id="assignedDate" />
						  </logic:present>
						  <logic:notPresent name="GpaReportingDetailVO" property="assignedDate">
							   <ibusiness:calendar
								componentID="CMP_MRA_AssignExceptions_AssigneeDate"
								property="assignedDate"
								value=""
								indexId="rowCount"
								type="image"
								id="assignedDate" />
						  </logic:notPresent>
						  </td>
						  <td width="7%">&nbsp;
							  <logic:present name="GpaReportingDetailVO" property="resolvedDate">
								<%
								  String resolvedLocalDate = TimeConvertor.toStringFormat(((LocalDate)GpaReportingDetailVO.getResolvedDate()).toCalendar(),"dd-MMM-yyyy");
								%>

							  	<ihtml:text property="resolvedDate" readonly="true"
									value="<%=resolvedLocalDate%>"
									componentID="CMP_MRA_AssignExceptions_ResolvedDate" indexId="rowCount" />
							  </logic:present>
							  <logic:notPresent name="GpaReportingDetailVO" property="resolvedDate">
							  	<ihtml:text property="resolvedDate" readonly="true"
									value=""
									componentID="CMP_MRA_AssignExceptions_ResolvedDate" indexId="rowCount" />
							  </logic:notPresent>
						  </td>
						    <td width="6%">&nbsp;

						  <logic:present name="GpaReportingDetailVO" property="ccaRefNum">
						  	<common:write name="GpaReportingDetailVO" property="ccaRefNum" />
						   </logic:present>
						  </td>
						  
						   <td  width="7%">&nbsp;
						   
						   <logic:present name="GpaReportingDetailVO" property="ccaStatus">
						    <logic:present name="oneTimeValuesMap">
							    <logic:iterate id="oneTimeValues" name="oneTimeValuesMap">
							    <logic:equal name="oneTimeValues" property="key" value="mra.defaults.ccastatus" >
							    <logic:iterate id="oneTimeVO" name="oneTimeValues" property="value" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
							    <logic:equal name="oneTimeVO" property="fieldValue" value="<%=GpaReportingDetailVO.getCcaStatus()%>">
								<common:write name="oneTimeVO" property="fieldDescription" />
							    </logic:equal>
							    </logic:iterate>
							    </logic:equal>
							    </logic:iterate>
						    </logic:present>
						  </logic:present>
						   

						 <!-- <logic:present name="GpaReportingDetailVO" property="ccaStatus">
						  	<common:write name="GpaReportingDetailVO" property="ccaStatus" />
						   </logic:present>-->
						  </td>
						  
						  
						  
						  
						</common:rowColorTag>
						</logic:iterate>
					</logic:present>

					  </tbody>
					</table>
				   </div>
				   </div>
					</div>
					</div>	
					<div class="ic-foot-container">
						<div class="ic-button-container">
							<ihtml:nbutton property="btnGPAReport" accesskey ="R" componentID="CMP_MRA_AssignExceptions_GPAReport" tabindex="10" >
								<common:message key="mailtracking.mra.gpareporting.assignexceptions.btn.gpareport" />
							</ihtml:nbutton>
						   <ihtml:nbutton property="btnProcess" accesskey ="P" componentID="CMP_MRA_AssignExceptions_Process" tabindex="11" >
								<common:message key="mailtracking.mra.gpareporting.assignexceptions.btn.process" />
						   </ihtml:nbutton>
						   <ihtml:nbutton property="btnSave" accesskey = "S" componentID="CMP_MRA_AssignExceptions__BTNSAVE" tabindex="12" >
								<common:message key="mailtracking.mra.gpareporting.assignexceptions.btn.save" />
						   </ihtml:nbutton>
						   <ihtml:nbutton property="btnClose" accesskey = "O" componentID="CMP_MRA_AssignExceptions__BTNCLOSE" tabindex="13" >
								<common:message key="mailtracking.mra.gpareporting.assignexceptions.btn.close" />
						   </ihtml:nbutton>
						</div>
					</div>	
				
			</div>
		</ihtml:form>	
	</div>		
</body>		



</html:html>
