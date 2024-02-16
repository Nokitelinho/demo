<%--******************************************************************************************
* Project	 		: iCargo
* Module Code & Name		: CaptureGPAReport.jsp
* Date				: 12-Feb-2007
* Author(s)			: A-2257

 ******************************************************************************************--%>

<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.CaptureGPAReportForm" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import="java.util.Formatter" %>



<html:html locale="true">
<head>

	
	
	<title><common:message bundle="capturegpareport" key="mailtracking.mra.gpareporting.capturegpareport.title.capturegpareport" />

	</title>
	<meta name="decorator" content="mainpanel">
	<common:include type="css" src="../css/icargo.css" />
	<common:include type="script" src="/js/mail/mra/gpareporting/CaptureGPAReport_Script.jsp"/>


</head>
<body style="overflow:auto;">
	
	
	
	

<business:sessionBean id="GPAREPORTINGFILTERVO"
	moduleName="mailtracking.mra"
	screenID="mailtracking.mra.gpareporting.capturegpareport"
	method="get"
	attribute="gPAReportingFilterVO" />


<business:sessionBean id="GPAREPORTINGDETAILSPAGE"
	moduleName="mailtracking.mra"
	screenID="mailtracking.mra.gpareporting.capturegpareport"
	method="get"
	attribute="gPAReportingDetailsPage" />

<business:sessionBean id="MAILSTATUS_ONETIME"
	moduleName="mailtracking.mra"
	screenID="mailtracking.mra.gpareporting.capturegpareport"
	method="get"
	attribute="mailStatus" />

<business:sessionBean id="MAILCATEGORY_ONETIME"
	moduleName="mailtracking.mra"
	screenID="mailtracking.mra.gpareporting.capturegpareport"
	method="get"
	attribute="mailCategory" />

<business:sessionBean id="HIGHESTNUM_ONETIME"
	moduleName="mailtracking.mra"
	screenID="mailtracking.mra.gpareporting.capturegpareport"
	method="get"
	attribute="heighestNum" />

<business:sessionBean id="REGINSIND_ONETIME"
	moduleName="mailtracking.mra"
	screenID="mailtracking.mra.gpareporting.capturegpareport"
	method="get"
	attribute="regOrInsInd" />


  <!--CONTENT STARTS-->
 <div id="contentdiv" class="iCargoContent" >


  <bean:define id="form" name="CaptureGPAReportForm"  type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.CaptureGPAReportForm" toScope="page" />


 <ihtml:form action="/mailtracking.mra.gpareporting.capturegpareport.screenload.do">


 <ihtml:hidden property="displayPage" />
 <ihtml:hidden property="lastPageNum" />
 <ihtml:hidden property="screenFlag" />
 <ihtml:hidden property="popUpStatusFlag" />
 <ihtml:hidden property="selectedRows" />
 <ihtml:hidden property="screenStatusFlag" />
 <ihtml:hidden property="basistype" />
 <ihtml:hidden property="gpaselect" />
 <ihtml:hidden property="allProcessed"/>
 <ihtml:hidden property="currencyCode"/>
<ihtml:hidden property="accEntryFlag"/>
<ihtml:hidden property="showDsnPopUp"/>
<ihtml:hidden property="dsnFlag"/>


<% String FORMAT_STRING = "%1$-16.2f";    %>
<% String FORMAT_STRING_RATE = "%1$-16.4f"; %>

  <div class="ic-content-main">
	<span class="ic-page-title"><common:message key="mailtracking.mra.gpareporting.capturegpareport.capturegpareport" /></span>
		<div class="ic-head-container">
			<div class="ic-filter-panel">
				<div class="ic-input-container">
					<div class="ic-border">
						
							<div class="ic-col-30 ic-mandatory ">
							<div class="ic-row ic-label-45">
							<label><common:message key="mailtracking.mra.gpareporting.capturegpareport.gpacode" /></label>
			
								<logic:present name="GPAREPORTINGFILTERVO" property="poaCode">
								<bean:define id="poaCode" name="GPAREPORTINGFILTERVO" property="poaCode" />
									<ihtml:text property="gpaCode" value="<%=(String)poaCode%>" maxlength="5" componentID="CMP_MRA_CaptureGPAReport_GPACode" style="text-transform : uppercase"  />
								</logic:present>
								<logic:notPresent name="GPAREPORTINGFILTERVO" property="poaCode">
									<ihtml:text property="gpaCode" value="" maxlength="5" componentID="CMP_MRA_CaptureGPAReport_GPACode" style="text-transform : uppercase"  />
								</logic:notPresent>
									<img name="gpaCodeLov" id="gpaCodeLov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" alt="" />
							</div>
							</div>

							<div class="ic-col-70" >
								<fieldset class="ic-field-set">
								<legend><common:message key="mailtracking.mra.gpareporting.capturegpareport.reportingperiod" /></legend>
									<div class="ic-row ic-label-45">
										<div class="ic-input  ic-mandatory ic-split-50">
											<label><common:message key="mailtracking.mra.gpareporting.capturegpareport.fromdate" /></label>
								 
											<logic:present name="GPAREPORTINGFILTERVO" property="reportingPeriodFrom">
											<bean:define id="reportingPeriodFrom" name="GPAREPORTINGFILTERVO"  property="reportingPeriodFrom" type="com.ibsplc.icargo.framework.util.time.LocalDate" />

											   <ibusiness:calendar
												componentID="CMP_MRA_CaptureGPAReport_FromDate"
												property="frmDate"
												value="<%=reportingPeriodFrom.toDisplayDateOnlyFormat() %>"
												type="image"
												id="frmDate" />
											 </logic:present>
											 <logic:notPresent name="GPAREPORTINGFILTERVO" property="reportingPeriodFrom">

											   <ibusiness:calendar
												componentID="CMP_MRA_CaptureGPAReport_FromDate"
												property="frmDate"
												value=""
												type="image"
												id="frmDate" />
											 </logic:notPresent>
						     </div>
						    <div class="ic-input  ic-mandatory ic-split-50"> 
							<label><common:message key="mailtracking.mra.gpareporting.capturegpareport.toDate" /></label>

						    <logic:present name="GPAREPORTINGFILTERVO" property="reportingPeriodTo">
						    <bean:define id="reportingPeriodTo" name="GPAREPORTINGFILTERVO"  property="reportingPeriodTo" type="com.ibsplc.icargo.framework.util.time.LocalDate" />

							<ibusiness:calendar
								componentID="CMP_MRA_CaptureGPAReport_ToDate"
								property="toDate"
								value="<%=reportingPeriodTo.toDisplayDateOnlyFormat() %>"
								type="image"
								id="toDate" />
						    </logic:present>
						     <logic:notPresent name="GPAREPORTINGFILTERVO" property="reportingPeriodTo">
							<ibusiness:calendar
								componentID="CMP_MRA_CaptureGPAReport_ToDate"
								property="toDate"
								value=""
								type="image"
								id="toDate" />
						    </logic:notPresent>
						   </div>
						    </div>
				   </fieldset>

				   </div>

				

			
				<div class="ic-row ">
				<div class="ic-button-container">			
					   <ihtml:nbutton property="btnList" accesskey = "L" componentID="CMP_MRA_CaptureGPAReport__BTNLIST" >
						<common:message key="mailtracking.mra.gpareporting.capturegpareport.list" />
					   </ihtml:nbutton>
					   <ihtml:nbutton property="btnClear" accesskey = "C" componentID="CMP_MRA_CaptureGPAReport__BTNCLEAR" >
						<common:message key="mailtracking.mra.gpareporting.capturegpareport.clear" />
					   </ihtml:nbutton>
					  </div>
					</div>
				
			</div>
		</div>
	</div>	
</div>
		
<div class="ic-main-container">
	<div class="ic-row">
		<h4>Report Details</h4>
	</div>

		 <div class="ic-row">
					<logic:present name="GPAREPORTINGDETAILSPAGE">

					   <common:paginationTag pageURL="mailtracking.mra.gpareporting.listcapturegpareport"
						name="GPAREPORTINGDETAILSPAGE" display="label"
						labelStyleClass="iCargoResultsLabel"
						lastPageNum="<%=form.getLastPageNum() %>" />
					 </logic:present>
					 
			<div class="ic-button-container">
	

				      <logic:present name="GPAREPORTINGDETAILSPAGE" >

					  <a href="#" class="iCargoLink" id="addLink" >
					     Add
					   </a> |
					  <a href="#" class="iCargoLink"  id="modifyLink" >
					     Modify
					  </a> |
					  <a href="#" class="iCargoLink"  id="deleteLink" >
					     Delete
					  </a>

				    </logic:present>
				    <logic:notPresent name="GPAREPORTINGDETAILSPAGE" >
					<logic:equal name="form" property="operationFlag" value="I">
						 <a href="#" class="iCargoLink" id="addLink" value="" >
						     Add
						   </a> |
					  </logic:equal>
					  <logic:notEqual name="form" property="operationFlag" value="I">
						 <a href="#" class="iCargoLink" id="addLink" value="" disabled="true">
						     Add
						   </a> |
					  </logic:notEqual>
					  <a href="#" class="iCargoLink" value=""  id="modifyLink" disabled="true">
					     Modify
					  </a> |
					  <a href="#" class="iCargoLink" value=""  id="deleteLink" disabled="true">
					    Delete
					 </a>
				    </logic:notPresent>
					
				    <logic:present name="GPAREPORTINGDETAILSPAGE">
				    ||
					 <common:paginationTag pageURL="javascript:selectNextDetails('lastPageNum','displayPage')"
					  name="GPAREPORTINGDETAILSPAGE"
					  linkStyleClass="iCargoLink"
					  disabledLinkStyleClass="iCargoLink"
					  display="pages"
					  lastPageNum="<%=form.getLastPageNum()%>" 
					  exportToExcel="true"
					  exportTableId="captureGPAReport"
					  exportAction="mailtracking.mra.gpareporting.listcapturegpareport.do"/>
				 </logic:present>
				 
				</div>
		</div>

		 <div class="ic-row">
		    
			    <div id="div1" class="tableContainer"    align="center" style="height:601px">
			    <table class="fixed-header-table" id="captureGPAReport" >
				  <thead>
				     <tr>
					    <td class="iCargoTableHeader" rowspan="2"  width="4%"></td>
						<td class="iCargoTableHeader" rowspan="2" width="6%">Date</td>

						<td class="iCargoTableHeader" rowspan="2" width="5%">Origin O.E</td>
						<td class="iCargoTableHeader" rowspan="2" width="5%">Destn. O.E</td>

						<td class="iCargoTableHeader"  rowspan="2" width="5%">Mail Category</td>

						<td class="iCargoTableHeader"  rowspan="2" width="5%">Mail Sub Class</td>
						<td class="iCargoTableHeader"  rowspan="2" width="5%">Year</td>
						<td class="iCargoTableHeader" rowspan="2" width="5%">DSN</td>
						<td class="iCargoTableHeader" rowspan="2" width="5%">RSN</td>
						<td class="iCargoTableHeader" rowspan="2" width="5%">HN</td>
						<td class="iCargoTableHeader" rowspan="2" width="5%">RI</td>
						<td style="iCargoLabelCenterAligned"  rowspan="2" width="5%">No of Mail Bags</td>
						<td class="iCargoTableHeader"  rowspan="2" width="5%">Wt. (K.G)</td>
						<td class="iCargoTableHeader"  rowspan="2" width="5%">Rate</td>
						<td class="iCargoTableHeader"  rowspan="2" width="6%">Amount</td>
						<td class="iCargoTableHeader"  rowspan="2" width="6%">Tax</td>
						<td class="iCargoTableHeader"  rowspan="2" width="6%">Total</td>
						<td class="iCargoTableHeader"  rowspan="2" width="6%">Mail Reporting Status</td>
						<td class="iCargoTableHeader"  width="6%" >Flight Details</td>

					 </tr>

				  </thead>
				  <tbody>

				  <% int index=0;  %>

				  <%

					int totNoOfMailBags=0;
					double totweight=0;
					double totrate=0;
					double totamount=0;
					double tottax=0;
					double tottot=0;


				  %>

					<logic:present name="GPAREPORTINGDETAILSPAGE" >
					<bean:define id="GPAREPORTINGDETAILSPAGE" name="GPAREPORTINGDETAILSPAGE" type="java.util.Collection"/>

					<logic:iterate id="GPAREPORTINGDETAILSVO" name="GPAREPORTINGDETAILSPAGE" type="com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingDetailsVO" indexId="rowCount">


					<logic:notEqual name="GPAREPORTINGDETAILSVO" property="operationFlag" value="D" >

					

					<tr>

					       <td class="iCargoTableDataTd" >

							<ihtml:checkbox property="rowCount"  value="<%=String.valueOf(rowCount)%>"/>
						</td>


						<td class="iCargoTableDataTd ">
							<logic:present name="GPAREPORTINGDETAILSVO" property="dsnDate">
								<%
									String dsnDate ="";
									if(GPAREPORTINGDETAILSVO.getDsnDate() != null) {
									dsnDate = GPAREPORTINGDETAILSVO.getDsnDate().toDisplayDateOnlyFormat();
									}
								%>
								 <%= dsnDate %>
							</logic:present>

						</td>
						<td class="iCargoTableDataTd">
							<logic:present name="GPAREPORTINGDETAILSVO" property="originOfficeExchange">
								<bean:write name="GPAREPORTINGDETAILSVO" property="originOfficeExchange"/>
							</logic:present>
						</td>
						<td class="iCargoTableDataTd ">
							<logic:present name="GPAREPORTINGDETAILSVO" property="destinationOfficeExchange">
								<bean:write name="GPAREPORTINGDETAILSVO" property="destinationOfficeExchange"/>
							</logic:present>
						</td>
						<td class="iCargoTableDataTd ">
							<logic:present name="GPAREPORTINGDETAILSVO" property="mailCategory">

							<logic:iterate id="mailCat" name="MAILCATEGORY_ONETIME">
							<bean:define id="mailCatvalue" name="mailCat" property="fieldValue" />
							     <logic:equal name="GPAREPORTINGDETAILSVO" property="mailCategory" value="<%=(String)mailCatvalue %>" >

								<bean:write name="mailCat" property="fieldDescription"/>
							     </logic:equal>
							</logic:iterate>
							</logic:present>
						</td>
						<td class="iCargoTableDataTd ">
							<logic:present name="GPAREPORTINGDETAILSVO" property="actualMailSubClass">
								<bean:write name="GPAREPORTINGDETAILSVO" property="actualMailSubClass"/>
							</logic:present>
						</td>
						<td class="iCargoTableDataTd ">
							<logic:present name="GPAREPORTINGDETAILSVO" property="year">
								<bean:write name="GPAREPORTINGDETAILSVO" property="year"/>
							</logic:present>
						</td>
						<td class="iCargoTableDataTd ">
							<logic:present name="GPAREPORTINGDETAILSVO" property="dsnNumber">
								<bean:write name="GPAREPORTINGDETAILSVO" property="dsnNumber"/>
							</logic:present>
						</td>
						<td class="iCargoTableDataTd ">
							<logic:present name="GPAREPORTINGDETAILSVO" property="receptacleSerialNumber">
								<bean:write name="GPAREPORTINGDETAILSVO" property="receptacleSerialNumber"/>
							</logic:present>
						</td>
						<td class="iCargoTableDataTd ">
							<logic:present name="GPAREPORTINGDETAILSVO" property="highestNumberedReceptacle">
								<logic:iterate id="highestNum" name="HIGHESTNUM_ONETIME">
								<bean:define id="highestNumvalue" name="highestNum" property="fieldValue" />
								     <logic:equal name="GPAREPORTINGDETAILSVO" property="highestNumberedReceptacle" value="<%=String.valueOf(highestNumvalue)%>" >

									<bean:write name="highestNum" property="fieldDescription"/>
								     </logic:equal>
								</logic:iterate>
							</logic:present>
						</td>
						<td class="iCargoTableDataTd " >
							<logic:present name="GPAREPORTINGDETAILSVO" property="registeredOrInsuredIndicator">
								<logic:iterate id="regInsInd" name="REGINSIND_ONETIME">
								<bean:define id="regInsIndvalue" name="regInsInd" property="fieldValue" />
								     <logic:equal name="GPAREPORTINGDETAILSVO" property="registeredOrInsuredIndicator" value="<%=String.valueOf(regInsIndvalue)%>" >

									<bean:write name="regInsInd" property="fieldDescription"/>
								     </logic:equal>
								</logic:iterate>

							</logic:present>
						</td>
						<td class="iCargoTableDataTd ">
						
							<logic:present name="GPAREPORTINGDETAILSVO" property="noOfMailBags">
							<bean:define id="noOfMailBags" name="GPAREPORTINGDETAILSVO" property="noOfMailBags" />
							<bean:write name="GPAREPORTINGDETAILSVO" property="noOfMailBags"/>
							</logic:present>
						
						</td>
						<td class="iCargoTableDataTd ">
						
							<logic:present name="GPAREPORTINGDETAILSVO" property="weight">

								<bean:define id="weight" name="GPAREPORTINGDETAILSVO" property="weight" />								
								<bean:define id="weightValue" value="<%=new Formatter().format(FORMAT_STRING,weight).toString().trim()%>" />
								<common:write name="weightValue" unitFormatting="true" />
								
								<%

								totweight = totweight + GPAREPORTINGDETAILSVO.getWeight();

								%>
							</logic:present>
						
						</td>
						<td class="iCargoTableDataTd ">
						
						
							<logic:present name="GPAREPORTINGDETAILSVO" property="rate">
								<bean:define id="rate" name="GPAREPORTINGDETAILSVO" property="rate" />
								<bean:define id="rateValue" value="<%=new Formatter().format(FORMAT_STRING_RATE,rate).toString().trim()%>" />
								<bean:write name="rateValue" localeformat="true" />						
								<%

								totrate = totrate + GPAREPORTINGDETAILSVO.getRate();

								%>
							</logic:present>
						
						</td>

						<td class="iCargoTableDataTd ">
						
							<logic:present name="GPAREPORTINGDETAILSVO" property="amount">
								<ibusiness:moneyDisplay showCurrencySymbol="false" name="GPAREPORTINGDETAILSVO" moneyproperty="amount"  property="amount" />

								<%

								totamount = totamount + GPAREPORTINGDETAILSVO.getAmount().getRoundedAmount();

								%>
							</logic:present>
						
						</td>
						<td class="iCargoTableDataTd ">
						
							<logic:present name="GPAREPORTINGDETAILSVO" property="tax">
								<bean:define id="tax" name="GPAREPORTINGDETAILSVO" property="tax" />								
								<bean:define id="taxValue" value="<%=new Formatter().format(FORMAT_STRING,tax).toString().trim()%>" />
								<bean:write name="taxValue" localeformat="true" />
								<%

								tottax = tottax + GPAREPORTINGDETAILSVO.getTax();

								%>
							</logic:present>
						
						</td>
						<td class="iCargoTableDataTd ">
						
							<logic:present name="GPAREPORTINGDETAILSVO" property="total">
								<ibusiness:moneyDisplay showCurrencySymbol="false" name="GPAREPORTINGDETAILSVO" moneyproperty="total"  property="total" />

								<%

								tottot = tottot + GPAREPORTINGDETAILSVO.getTotal().getRoundedAmount();

								%>
							</logic:present>
						</td>
						<td class="iCargoTableDataTd ">
							<logic:present name="GPAREPORTINGDETAILSVO" property="reportingStatus">
							<logic:iterate id="mailStatus" name="MAILSTATUS_ONETIME">
								<bean:define id="mailStatusvalue" name="mailStatus" property="fieldValue" />
								<logic:equal name="GPAREPORTINGDETAILSVO" property="reportingStatus" value="<%=(String)mailStatusvalue %>" >

								<ihtml:hidden name="form" property="mailStatus" value="<%=(String)mailStatusvalue%>"/>
									<%  System.out.println("mailsta***********"+mailStatusvalue); %>

								      <bean:write name="mailStatus"	property="fieldDescription"/>
								</logic:equal>
							</logic:iterate>
							</logic:present>

						</td>
						<td class="iCargoTableDataTd ">
						<logic:present name="GPAREPORTINGDETAILSVO" property="flightDetails">
						<bean:define id="flightDetails" name="GPAREPORTINGDETAILSVO" property="flightDetails" />
						<ihtml:text property="fltdetails" value="<%=(String)flightDetails%>" styleClass="iCargoTextFieldMedium" readonly="true" title="<%=(String)flightDetails%>" style="text-transform : uppercase"  />
						</logic:present>
						</td >


				      </tr>
				      
				      </logic:notEqual>
				       <% index++;  %>
				      </logic:iterate>
				      </logic:present>

				</tbody>
				<tfoot>

					<tr>
						<td >&nbsp;</td>
						<td >&nbsp;</td>
						<td >&nbsp;</td>
						<td >&nbsp;</td>
						<td >&nbsp;</td>
						<td >&nbsp;</td>
						<td >&nbsp;</td>
						<td >&nbsp;</td>
						<td >&nbsp;</td>
						<td >&nbsp;</td>
						<td >&nbsp;</td>
						<td >&nbsp;</td>
						
						<td >
						
							<bean:define id="totweightsum" value="<%=new Formatter().format(FORMAT_STRING,totweight).toString().trim()%>"/>
							<!--common:write name="totweightsum" / -->
							<common:write name="totweightsum" unitFormatting="true" />
							
						</td>

						<td >
						
							<bean:define id="totratesum" value="<%=new Formatter().format(FORMAT_STRING_RATE,totrate).toString().trim()%>"/>
							<!--common:write name="totratesum" / -->
							<bean:write name="totratesum" localeformat="true" />
						</td>
						<td >
						
							<bean:define id="totamountsum" value="<%=new Formatter().format(FORMAT_STRING,totamount).toString().trim()%>"/>
							<!-- common:write name="totamountsum" / -->
							<bean:write name="totamountsum" localeformat="true" />
						</td>
						<td >
						
							<bean:define id="tottaxsum" value="<%=new Formatter().format(FORMAT_STRING,tottax).toString().trim()%>"/>
							<!-- common:write name="tottaxsum" / -->
							<bean:write name="tottaxsum" localeformat="true" />
						</td>
						<td >
						
							<bean:define id="tottotsum" value="<%=new Formatter().format(FORMAT_STRING,tottot).toString().trim()%>"/>
							<!-- common:write name="tottotsum" / -->
							<bean:write name="tottotsum" localeformat="true" />
						</td>
						<td >&nbsp;</td>
						<td >&nbsp;</td>

					</tr>
					
			       </tfoot>

				</table>
			   </div>
			 </div>
			  </div>
		<div class="ic-foot-container">
			<div class="ic-row">
				<div class="ic-button-container">
			  <ihtml:nbutton property="btnViewAccount" accesskey = "A" componentID="CMP_MRA_GPAREPORTING_CAPTUREGPAREPORT_VIEWACC">
				<common:message key="mailtracking.mra.gpareporting.capturegpareport.btn.viewaccentry"/>
			   </ihtml:nbutton>
			   <ihtml:nbutton property="btnProcess" accesskey = "P" componentID="CMP_MRA_CaptureGPAReport__BTNPROCESS" >
				<common:message key="mailtracking.mra.gpareporting.capturegpareport.process" />
			   </ihtml:nbutton>
			   <ihtml:nbutton property="btnSave" id="btnSave" accesskey = "S" componentID="CMP_MRA_CaptureGPAReport__BTNSAVE" >
				<common:message key="mailtracking.mra.gpareporting.capturegpareport.save" />
			   </ihtml:nbutton>
			   <ihtml:nbutton property="btnClose"  id="btnClose" accesskey = "O" componentID="CMP_MRA_CaptureGPAReport__BTNCLOSE" >
				<common:message key="mailtracking.mra.gpareporting.capturegpareport.close" />
			   </ihtml:nbutton>

			</div>
		</div>
	</div>
</div>
</ihtml:form>
</div>

	</body>
</html:html>
