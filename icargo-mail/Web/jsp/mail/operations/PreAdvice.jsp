<%--
/***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  PreAdvice.jsp
* Date					:  29-June-2006
* Author(s)				:  A-2047
*************************************************************************/
--%>


 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PreAdviceForm"%>
 <%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.PreAdviceVO"%>
 <%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.PreAdviceDetailsVO"%>
 <%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>

	 <html:html>

 <head> 
		
		<%@ include file="/jsp/includes/customcss.jsp" %>
 	<title><common:message bundle="preAdviceResources" key="mailtracking.defaults.preadvice.lbl.title" /></title>
 	<meta name="decorator" content="popup_panel">
 	<common:include type="script" src="/js/mail/operations/PreAdvice_Script.jsp"/>
 </head>

  <body>
	
	
  	 <bean:define id="form"
		 name="PreAdviceForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PreAdviceForm"
 		 toScope="page" />

 	 <business:sessionBean id="PREADVICEVO"
		 moduleName="mail.operations"
		 screenID="mailtracking.defaults.preadvice"
		 method="get"
 	 	 attribute="preAdviceVO"/>

	 <business:sessionBean id="flightValidationVO"
	 	 moduleName="mail.operations"
	 	 screenID="mailtracking.defaults.preadvice"
	 	 method="get"
	 	 attribute="flightValidationVO" />

	<div class="iCargoPopUpContent ic-masterbg" style="overflow:auto;height:500px;">
		<ihtml:form action="/mailtracking.defaults.preadvice.screenload.do" styleClass="ic-main-form">
		<div class="ic-content-main">
					<span class="ic-page-title ic-display-none">
		                    		<common:message key="mailtracking.defaults.preadvice.lbl.pagetitle" />
		            </span>	
					<div class="ic-head-container">	
							<div class="ic-filter-panel">
								<div class="ic-row">
									<b><common:message key="mailtracking.defaults.preadvice.lbl.flightdatials" /></b>
								</div>
								
								<!--DWLayoutTable-->
											
										<div class="ic-round-border ">
											<div class="ic-row">
											
												<div class="ic-input ic-split-20 ">
													<label><common:message key="mailtracking.defaults.preadvice.lbl.flightno" /></label>
												
													<logic:present name="PREADVICEVO">
														<bean:write name="PREADVICEVO" property="carrierCode"/>
														<bean:write name="PREADVICEVO" property="flightNumber"/>
													</logic:present>
													<logic:notPresent name="PREADVICEVO">
														&nbsp;
													</logic:notPresent>
												</div>
												<div class="ic-input ic-split-25 ">
													<label><common:message key="mailtracking.defaults.preadvice.lbl.flightdate" /></label>
													<logic:present name="PREADVICEVO" property="flightDate">
													<bean:define id="flightDate" name="PREADVICEVO" property="flightDate"/>
														<%=((LocalDate)flightDate).toDisplayFormat("dd-MMM-yyyy")%>
													</logic:present>
													<logic:notPresent name="PREADVICEVO" property="flightDate">
														&nbsp;
													</logic:notPresent>
												</div>
												<div class="ic-input ic-split-55">
														<logic:present name="flightValidationVO" property="flightRoute">
															<common:displayFlightStatus flightStatusDetails="flightValidationVO" />
														</logic:present>
														<logic:notPresent name="flightValidationVO" property="flightRoute">
															&nbsp;
														</logic:notPresent>
													</div>
												</div>
											</div>
										</div>
								</div>
					<div class="ic-main-container">			
								<div class="ic-row">
										<b><common:message key="mailtracking.defaults.preadvice.lbl.preadvicedetails"/></b>
								</div>
								<div class="tableContainer" id="div1"  style="width:100%; height:150px; ">
									<table  style="width:100%; " class="fixed-header-table">
											  <!--DWLayoutTable-->
											<thead>
												<tr>
													<td width="15%" height="25" class="iCargoTableHeader">
														<common:message key="mailtracking.defaults.preadvice.lbl.mailcatogory"/>
													</td>
													<td width="25%" class="iCargoTableHeader">
														<common:message key="mailtracking.defaults.preadvice.lbl.ooe"/>
													</td>
													<td width="24%" class="iCargoTableHeader">
														<common:message key="mailtracking.defaults.preadvice.lbl.doe"/>
													</td>
													<td width="17%" class="iCargoTableHeader">
														<common:message key="mailtracking.defaults.preadvice.lbl.uldno"/>
													</td>
													<td width="11%" class="iCargoTableHeader">
														<common:message key="mailtracking.defaults.preadvice.lbl.noofbags"/>
													</td>
													<td width="9%" class="iCargoTableHeader">
														<common:message key="mailtracking.defaults.preadvice.lbl.wt"/>
													</td>
												</tr>
											</thead>
											<tbody>
													<logic:present name="PREADVICEVO" property="preAdviceDetails">
														<bean:define id="preAdviceDetails" name="PREADVICEVO" property="preAdviceDetails"/>
														<logic:iterate id ="preAdviceDetailsVO" name="preAdviceDetails" type="PreAdviceDetailsVO" indexId="rowCount">
																<tr>
																	<td class="iCargoTableDataTd ic-center">
																		<bean:write name="preAdviceDetailsVO" property="mailCategory"/>
																	</td>
																	<td class="iCargoTableDataTd ic-center">
																		<bean:write name="preAdviceDetailsVO" property="originExchangeOffice"/>
																	</td>
																	<td class="iCargoTableDataTd ic-center">
																		<bean:write name="preAdviceDetailsVO" property="destinationExchangeOffice"/>
																	</td>
																	<td class="iCargoTableDataTd ic-center">
																		<bean:write name="preAdviceDetailsVO" property="uldNumbr"/>
																	</td>
																	<td class="iCargoTableDataTd ic-center">
																		<bean:write name="preAdviceDetailsVO" property="totalbags"/>
																	</td>
																	<td class="iCargoTableDataTd ic-center">
																		<common:write name="preAdviceDetailsVO" property="totalWeight" unitFormatting="true"/><!--modified by A-7371-->
																	</td>
																</tr>
														</logic:iterate>
													</logic:present>
											</tbody>
											<tfoot>
												  <tr >
													<td width="10%" class="iCargoTableDataTd"><h3>Total Mails</h3></td>
													<td width="20%"></td>
													<td width="20%"></td>
													<td width="6%"></td>															
													<logic:present name="PREADVICEVO">
														<td width="10%"><div class="ic-right"><bean:write name="PREADVICEVO" property="totalBags" format="####"/></div></td>
														<td width="10%"><div class="ic-right"><common:write name="PREADVICEVO" property="totalWeight" unitFormatting="true"/></div></td><!--modified by A-7371-->
													</logic:present>
													<logic:notPresent name="PREADVICEVO">
														<td width="10%"></td>
														<td width="10%"></td>
													</logic:notPresent>
													<td></td>
												  </tr>
											</tfoot>														
									</table>
								</div>
					</div>
					<div class="ic-foot-container">
							<div class="ic-button-container">
							<!--DWLayoutTable-->
											<ihtml:button property="btOk" componentID="BUT_MAILTRACKING_DEFAULTS_PREADVICE_OK">
												<common:message key="mailtracking.defaults.preadvice.btn.ok" />
											</ihtml:button>
									
							</div>
					</div>
			</div>
		</ihtml:form>
	</div>
	</body>

 </html:html>



