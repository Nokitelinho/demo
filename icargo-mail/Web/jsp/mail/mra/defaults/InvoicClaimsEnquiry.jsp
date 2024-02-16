<%--/***********************************************************************
* Project	     	 : iCargo
* Module Code & Name 	 : Mailtracking.mra
* File Name          	 : InvoiceClaimsEnquiry.jsp
* Date                 	 : 01-Aug-2007
* Author(s)              : A-2270
*************************************************************************/
--%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.InvoicClaimsEnquiryForm"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="org.apache.struts.action.ActionMessages"%>
<%@ page import = "com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicClaimsEnquiryVO"%>
<%@ page import ="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>


		
	
<html:html>
<head>

	
		
	
         <common:include type="script" src="/js/mail/mra/defaults/InvoicClaimsEnquiry_Script.jsp" />
	<title>
		<common:message bundle="invoicClaimsEnquiry" key="mailtracking.mra.defaults.invoiccalaimsEnquiry.title" />
	</title>
	<meta name="decorator" content="mainpanel">

	<common:include type="script"  src="/js/calendar/formateDate.js" />

</head>

<body>
	
	
	
                  <business:sessionBean id="KEY_CLAIMSVO"
	  		moduleName="mailtracking.mra.defaults"
	  		screenID="mailtracking.mra.defaults.invoicclaimsenquiry" method="get"
		        attribute="mailInvoicClaimsEnquiryVOs" />

		        <business:sessionBean
					id="Key_Onetimevalues"
					moduleName="mailtracking.mra.defaults"
					screenID="mailtracking.mra.defaults.invoicclaimsenquiry"
					method="get"
		        attribute="oneTimeValues" />


   <!--CONTENT STARTS-->
<div class="iCargoContent" style="overflow:auto;height:100%;width:100%">
       		<ihtml:form action="/mailtracking.mra.defaults.invoicClaimsEnquiry.onScreenLoad.do" focus="gpaCode">
       		<ihtml:hidden property="lastPageNum"/>
       		<ihtml:hidden property="absoluteIndex"/>
		<ihtml:hidden property="displayPage" />
		<input type="hidden" name="mySearchEnabled" />
     <bean:define id="form" name="InvoicClaimsEnquiryForm" type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.InvoicClaimsEnquiryForm" toScope="page"/>
	<div class="ic-content-main">		
		<span class="ic-page-title ic-display-none">
			<common:message key="mailtracking.mra.defaults.invoicclaimsenquiry.title"/>
		</span>
		  
				<div class="ic-head-container">	
					<div class="ic-filter-panel">
						<div class="ic-row">
							<div class="ic-input-container">												
								<div class="ic-input ic-mandatory ic-label-30 ic-split-30">
									<label>
										<common:message key="mailtracking.mra.defaults.invoicclaimsenquiry.gpacode"/>
									
									</label>
									<ihtml:text property="gpaCode" componentID="CMP_MRA_DEFAULTS_INVOICCLAIMSENQUIRY_GPACODE"
									  maxlength="5"/>
									<img name="gpacodelov" id="gpacodelov"
									src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" alt="" />
								</div>
								<div class="ic-input ic-mandatory ic-label-30 ic-split-35">
									<label>
										<common:message key="mailtracking.mra.defaults.invoicclaimsenquiry.fromdate" />&nbsp;
									</label>									
									<ibusiness:calendar id="claimfrmdat"
									property="fromDate"
									componentID="CMP_MRA_DEFAULTS_INVOICCLAIMSENQ_FROMDATE"
									type="image"
									maxlength="11"
									/>
								</div>
								<div class="ic-input ic-mandatory ic-label-30 ic-split-35">
									<common:message key="mailtracking.mra.defaults.invoicclaimsenquiry.todate" />&nbsp;
									<span class="iCargoMandatoryFieldIcon">*</span>
									<ibusiness:calendar id="claimtodat"
									property="toDate"
									componentID="CMP_MRA_DEFAULTS_INVOICCLAIMSENQ_TOODATE"
									type="image"
									maxlength="11"
									/>
								</div>
							</div>
						</div>
						<div class="ic-row">
							<div class="ic-input-container">												
								<div class="ic-input  ic-label-30 ic-split-30">
									<label>
										<common:message key="mailtracking.mra.defaults.invoicclaimsenquiry.claimStatus"/>
									</label>
									 <!-- combo boxes for status-->
									   <%HashMap hashExpsStatus = new HashMap();%>
									<ihtml:select property="claimStatus" componentID="CMP_MRA_DEFAULTS_INVOICCLAIMSENQ_STATUS">
									<logic:present name="Key_Onetimevalues">
									<html:option value=""><common:message key="combo.select"/></html:option>
										<logic:iterate id="oneTimeValue" name="Key_Onetimevalues">
											<bean:define id="parameterCode" name="oneTimeValue" property="key" />
											<logic:equal name="parameterCode" value="mailtracking.mra.defaults.claimstatus">
												<bean:define id="parameterValues" name="oneTimeValue" property="value" />
												<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
													<logic:present name="parameterValue">
													<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
													<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
														<html:option value="<%=(String)fieldValue%>">
															<%=(String)fieldDescription%>
														</html:option>
														<%= hashExpsStatus.put(fieldValue,fieldDescription)%>
													</logic:present>
												</logic:iterate>
											</logic:equal>
										</logic:iterate>
									</logic:present>
									</ihtml:select>
									<!-- combo boxes for status ends -->
							    </div>
								<div class="ic-input  ic-label-30 ic-split-35">
									<label>	
										<common:message key="mailtracking.mra.defaults.invoicclaimsenquiry.claimType"/>
									</label>	
									       <%HashMap hashExpsType = new HashMap();%>
										<ihtml:select property="claimType" componentID="CMP_MRA_DEFAULTS_INVOICCLAIMSENQ_TYPE">
										<logic:present name="Key_Onetimevalues">
										<html:option value=""><common:message key="combo.select"/></html:option>
											<logic:iterate id="oneTimeValue" name="Key_Onetimevalues">
												<bean:define id="parameterCode" name="oneTimeValue" property="key" />
												<logic:equal name="parameterCode" value="mailtracking.mra.defaults.claimtype">
													<bean:define id="parameterValues" name="oneTimeValue" property="value" />
													<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
														<logic:present name="parameterValue">
														<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
														<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
															<logic:notEqual name="fieldValue" value="RVLD">
															<logic:notEqual name="fieldValue" value="RVHD">
															<html:option value="<%=(String)fieldValue%>">
																<%=(String)fieldDescription%>
															</html:option>
															</logic:notEqual>
															</logic:notEqual>
															<%= hashExpsType.put(fieldValue,fieldDescription)%>
														</logic:present>
													</logic:iterate>
												</logic:equal>
											</logic:iterate>
										</logic:present>
										</ihtml:select>
							      </div>
								<div class="ic-input ic-split-30">
							       <div class="ic-button-container">
										<ihtml:nbutton property="btnList" accesskey = "L" componentID="CMP_MRA_DEFAULTS_INVOICCLAIMSENQ_BTNDETAILS">
										<common:message key="mailtracking.mra.defaults.invoicclaimsenquiry.btn.details" />
										</ihtml:nbutton>
									<ihtml:nbutton property="btnClear" accesskey = "C" componentID="CMP_MRA_DEFAULTS_INVOICCLAIMSENQ_BTNCLEAR">
										<common:message key="mailtracking.mra.defaults.invoicclaimsenquiry.btn.clear" />
										</ihtml:nbutton>
								  </div>
							   </div>
						 </div>
					 </div>
				</div>
			</div>	
			<div class="ic-main-container">	
				<div  class="ic-row">	
					<div class="ic-col-50 ic-left">
						<logic:present name="KEY_CLAIMSVO">
						<common:paginationTag pageURL="/mailtracking.mra.defaults.invoicClaimsEnquiry.onList.do"
						name="KEY_CLAIMSVO" display="label"
						labelStyleClass="iCargoResultsLabel"
						lastPageNum="<%=form.getLastPageNum() %>" />

						</logic:present>
					</div>
					<div class="ic-col-50 ic-right">
						<logic:present name="KEY_CLAIMSVO">
							<common:paginationTag pageURL="javascript:submitPage('lastPageNum','displayPage')"
							name="KEY_CLAIMSVO"
							display="pages"
							lastPageNum="<%=form.getLastPageNum()%>"
							exportToExcel="true"
							exportTableId="invoiceClaimsTable"
							exportAction="/mailtracking.mra.defaults.invoicClaimsEnquiry.onList.do"/>

						</logic:present>
					</div>
				</div>
				    <!---PAGINATION TAG ENDS-->
				<div  class="ic-row">	
					<div id="div1" class="tableContainer"  style="height:720px;">
						<table class="fixed-header-table" id="invoiceClaimsTable">
							<thead>
								<tr>
									<td class="iCargoTableHeader" width="18%"><common:message key="mailtracking.mra.defaults.invoicclaimsenquiry.receptacleID"/><span></span></td>

									<td class="iCargoTableHeader" width="6%"><common:message key="mailtracking.mra.defaults.invoicclaimsenquiry.Sector"/><span></span></td>

									<td class="iCargoTableHeader" width="10%"><common:message key="mailtracking.mra.defaults.invoicclaimsenquiry.Flight"/><span></span></td>


									<td class="iCargoTableHeader" width="4%" ><common:message key="mailtracking.mra.defaults.invoicclaimsenquiry.ClaimType"/><span></span></td>

									<td class="iCargoTableHeader" width="4%" ><common:message key="mailtracking.mra.defaults.invoicclaimsenquiry.ClaimStatus"/><span></span></td>

									<td class="iCargoTableHeader" width="6%" ><common:message key="mailtracking.mra.defaults.invoicclaimsenquiry.ClaimDate"/><span></span></td>

									<td class="iCargoTableHeader" width="6%" ><common:message key="mailtracking.mra.defaults.invoicclaimsenquiry.TransferDate"/><span></span></td>
								</tr>
							</thead>
							<tbody>
							      <logic:present name="KEY_CLAIMSVO">
								<logic:iterate id="claimVO" name="KEY_CLAIMSVO" indexId="rowId" type="com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicClaimsEnquiryVO">
								<tr class="iCargoTableDataRow1">
									 
									<td >&nbsp;<common:write name="claimVO" property="receptacleIdentifier"/></td>
									<td >&nbsp;<common:write name="claimVO" property="sector"/></td>
									<td >&nbsp;<common:write name="claimVO" property="carrierCode"/></td>
									<td>
										<logic:present name="claimVO" property="calimType">
												<bean:define id="type" name="claimVO" property="calimType"/>
												<%=hashExpsType.get(type)%>
										</logic:present>
									</td>
									<td>
										<logic:present name="claimVO" property="claimStatus">
												<bean:define id="status" name="claimVO" property="claimStatus"/>
												<%=hashExpsStatus.get(status)%>
										</logic:present>
									</td>
									<td>
									<logic:present name="claimVO" property="claimDate">
										<bean:define id="date" name="claimVO" property="claimDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
										<%
										  String receivedDate = TimeConvertor.toStringFormat(date.toCalendar(),"dd-MMM-yyyy"); %>
									       <%=receivedDate%>
									</logic:present>
									</td>
									<td>
										<logic:present name="claimVO" property="transferDate">
											<bean:define id="tdate" name="claimVO" property="transferDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
											<%
											  String tarnsDate = TimeConvertor.toStringFormat(tdate.toCalendar(),"dd-MMM-yyyy"); %>
										       <%=tarnsDate%>
										</logic:present>
									</td>
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
				<div class="ic-button-container">
					<ihtml:nbutton property="btnClose" accesskey = "O" tabindex="19" componentID="CMP_MRA_DEFAULTS_INVOICCLAIMSENQ_CLOSEBTN" >
					<common:message key="mailtracking.mra.defaults.invoicclaimsenquiry.lbl.close"/>
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

