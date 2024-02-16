<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name			:  Mailtracking - MRA.Defaults
* File Name				:  ViewBillingLine.jsp
* Date					:  12-Mar-2007
* Author(s)				:  A-2398

*************************************************************************/
 --%>

<%@ page language="java" %>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ViewBillingLineForm"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>



	
<html:html>
<head>

	

<title><common:message key="mailtacking.mra.defaults.viewbillingline.title" bundle="viewbillingline" scope="request"/></title>
<meta name="decorator" content="mainpanelrestyledui">
<common:include type="script" src="/js/mail/mra/defaults/ViewBillingLine_Script.jsp"/>

</head>

<body>
	
	
  <!-- ************************ CONTENT STARTS***************************** -->

 <bean:define id="form" name="ViewBillingLineForm"
 	type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ViewBillingLineForm"
 	toScope="page" />
  <business:sessionBean id="billingLineDetailsPage"
 		moduleName="mailtracking.mra.defaults"
 		screenID="mailtracking.mra.defaults.viewbillingline"
 		method="get"
		attribute="billingLineDetails"/>

  <business:sessionBean id="KEY_ONETIMES"
 	  moduleName="mailtracking.mra.defaults"
 	  screenID="mailtracking.mra.defaults.viewbillingline"
 	  method="get" attribute="oneTimeVOs" />
  <div id="contentdiv" class="iCargoContent ic-masterbg ">
    <ihtml:form action="mailtracking.mra.defaults.viewbillingline.screenload.do">
    <input type="hidden" name="catCode"/>
    <input type="hidden" name="classCode"/>
     <html:hidden name="ViewBillingLineForm" property="selectedIndexes" />
     <html:hidden name="ViewBillingLineForm" property="fromPage" />
    <html:hidden name="ViewBillingLineForm" property="displayPage" />
    <html:hidden name="ViewBillingLineForm" property="lastPageNum" />
    <html:hidden name="ViewBillingLineForm" property="copyFlag" />
	<div class="ic-content-main">
		
			<span class="ic-page-title ic-display-none">
				<common:message key="mailtacking.mra.defaults.viewbillingline.pagetitle" scope="request"/>
			</span>
			<div class="ic-head-container">
			<div class="ic-filter-panel">
		<div class="ic-row">
				<h4><common:message key="mailtacking.mra.defaults.viewbillingline.searchcriteria" scope="request"/></h4>
		</div>
				<div class="ic-input-container ic-label-40">
					<div class="ic-row">
							<jsp:include page="ViewBillingLineFilter.jsp" /> 
					
						<div class="ic-button-container paddR5">
							<ihtml:nbutton accesskey = "L" property="btnList"
								componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_ListBtn" tabindex="14">
								<common:message key="mailtracking.mra.defaults.viewbillingline.button.List" scope="request" />
							</ihtml:nbutton>&nbsp
							<ihtml:nbutton property="btnClear" accesskey = "C"
								componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_ClearBtn" tabindex="15">
								<common:message key="mailtracking.mra.defaults.viewbillingline.button.Clear" scope="request" />
							</ihtml:nbutton>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="ic-main-container">
		<a class="panel upArrow"  collapseFilter="true"  collapseFilterCallbackFun="callbackViewBillingLine"  href="#"></a> <!--Added by A-7929 for ICRD - 224586  -->
			
				<div class="ic-row">

					<div class="ic-col-70">
						<logic:present name="billingLineDetailsPage">
							<common:paginationTag pageURL="mailtracking.mra.defaults.viewbillingline.list.do"
							name="billingLineDetailsPage"
							display="label"
							labelStyleClass="iCargoResultsLabel"
							lastPageNum="<%=((ViewBillingLineForm)form).getLastPageNum()%>" />
						</logic:present>
					</div>
					<div class="ic-col-30">
					<div class="ic-button-container paddR5">
						<logic:present name="billingLineDetailsPage">
							<common:paginationTag pageURL="javascript:submitPage('lastPageNum','displayPage')"
							linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
							name="billingLineDetailsPage" display="pages" lastPageNum="<%=(String)((ViewBillingLineForm)form).getLastPageNum()%>"
							exportToExcel="true"
							exportTableId="billingLineDetails"
							exportAction="mailtracking.mra.defaults.viewbillingline.list.do"/>
						</logic:present>
					</div>
					</div>
				</div>

				<div class="ic-row">
						<% int checkboxes=0; %>
						<!-- MODIFIED BY A-7938 FOR ICRD-245316-->
							<div class="tableContainer table-border-solid" id="div1" style="height:640px" >
								<table class="fixed-header-table"  id="billingLineDetails" style="width:200%">
									<thead>
										<tr class="ic-th-all">
												<th style="width: 1%;">
												<th style="width: 4%;">
												<th style="width: 3%;">
												<th style="width: 3%;">
												<th style="width: 3%;">
												<th style="width: 3%;">
												<th style="width: 3%;">
												<th style="width: 3%;">
												<th style="width: 3%;"><!-- Fixed by A-8464 for ICRD-277576-->

												<th style="width: 2.2%;">
												<th style="width: 2.2%;">
												<th style="width: 2.2%;">
												<th style="width: 2.2%;">

												<th style="width: 2.2%;">
												<th style="width: 2.2%;">
												<th style="width: 2.2%;">
												<th style="width: 2.2%;">
												

												<th style="width: 2.5%;"><!-- Fixed by A-8464 for ICRD-277576-->
												<th style="width: 2%;">
												<th style="width: 2.5%;">
												<th style="width: 2.5%;">
												<th style="width: 2.5%;">
												<th style="width: 2%;">
												<th style="width: 2%;">
												<th style="width: 3%;">
												<th style="width: 3%;">
												<th style="width: 3%;">
												<th style="width: 3%;">
												<th style="width: 4%;">
												<th style="width: 2%;">
												<th style="width: 2%;">
												<th style="width: 2.5%;">
												<th style="width: 3%;">
												<th style="width: 3%;">
												<th style="width: 3%;">
												<th style="width: 3%;"><!-- Fixed by A-8464 for ICRD-277576-->
												<th style="width: 3%;">
												<th style="width: 3%;">
												
												<th style="width: 3%;">
												<th style="width: 3%;">
												<th style="width: 2%;">
												<th style="width: 2%;">
												<!--<th style="width: 2%;">-->
												<!--<th style="width: 2%;">--> <!-- Fixed by A-8464 for ICRD-277576-->
		       		                	</tr>

		       							<tr>
		       								<td  class="iCargoTableHeader" rowspan="2" style="padding-top=10px;"><input type="checkbox" name="headChk" tabindex="16"/></td>
		       								<td  class="iCargoTableHeader" rowspan="3" ><span style="width:130px" readonly="true"><common:message  key="mailtracking.mra.defaults.viewbillingmatrix.tableheader.blgmatcode" scope="request"/></span></td>
											<td  class="iCargoTableHeader" rowspan="3" ><span style="width:70px" readonly="true"><common:message  key="mailtracking.mra.defaults.viewbillingmatrix.tableheader.ratlineID" scope="request"/></span></td>
		       								<td  class="iCargoTableHeader" colspan="4"><label><common:message  key="mailtracking.mra.defaults.viewbillingmatrix.tableheader.origin" scope="request"/></label></td>
		       								<td  class="iCargoTableHeader" colspan="4"><common:message  key="mailtracking.mra.defaults.viewbillingmatrix.tableheader.destination" scope="request"/></td>
		       								<td  class="iCargoTableHeader" colspan="3" ><common:message  key="mailtracking.mra.defaults.viewbillingmatrix.tableheader.uplift" scope="request"/></td>
		       								<td  class="iCargoTableHeader" colspan="3"><common:message  key="mailtracking.mra.defaults.viewbillingmatrix.tableheader.discharge" scope="request"/></td>
		       								<td  class="iCargoTableHeader" rowspan="2" ><span  readonly="true">Category</span></td>
		       								<td  class="iCargoTableHeader" rowspan="2" ><span readonly="true">Class</span></td>
		       								<td  class="iCargoTableHeader" rowspan="2" ><span readonly="true">SubClass</span></td>
											<td  class="iCargoTableHeader" rowspan="2"  ><span readonly="true">SubClass Group</span></td>
		       								<td  class="iCargoTableHeader" rowspan="2" ><span  readonly="true">ULD Type</span></td>
											<td  class="iCargoTableHeader" rowspan="2" ><span  readonly="true">ULD Grp.</span></td>
		       								<td  class="iCargoTableHeader" rowspan="2" ><span readonly="true">Flight No.</span></td>
											<td  class="iCargoTableHeader" rowspan="2" ><span readonly="true">Via Point</span></td>
											<td  class="iCargoTableHeader"  rowspan="2" ><span  readonly="true">Flown Carrier</span></td>
											<td  class="iCargoTableHeader"  rowspan="2" ><span  readonly="true">Transferred By</span></td>
											<td  class="iCargoTableHeader"  rowspan="2" ><span  readonly="true">Transferred PA</span></td>
		       								<td  class="iCargoTableHeader" rowspan="2" ><span  readonly="true">Invoice Sector</span></td>
											<td  class="iCargoTableHeader" rowspan="2" ><span  readonly="true">PA Built</span></td> <!--Added by a7540-->
	                                    	<td  class="iCargoTableHeader" rowspan="2" ><span  readonly="true">Service level</span></td><!--Added by a7540-->
		       								<td  class="iCargoTableHeader" rowspan="2" ><span  readonly="true">Currency</span></td>
		    		      					<td  class="iCargoTableHeader" colspan="3" ><span readonly="true"><common:message  key="mailtracking.mra.defaults.viewbillingmatrix.tableheader.mailchg" scope="request"/></span></td>
											<td class="iCargoTableHeader" rowspan="2"><span readonly="true"><common:message key="mailtracking.mra.defaults.viewbillingmatrix.tableheader.weight" scope="request"/></span></td>
											<td  class="iCargoTableHeader" rowspan="2" ><span  readonly="true"><common:message  key="mailtracking.mra.defaults.viewbillingmatrix.tableheader.surchgind" scope="request"/></span></td>
											<td  class="iCargoTableHeader" rowspan="2" ><span  readonly="true">Blg. Party</span></td>
		       								<td  class="iCargoTableHeader" rowspan="2" ><span  readonly="true">Rev/Exp</span></td>
		       								<td  class="iCargoTableHeader" rowspan="2" ><span readonly="true">Valid From</span></td>
		       								<td  class="iCargoTableHeader" rowspan="2" ><span  readonly="true">Valid To</span></td>
		       								<td  class="iCargoTableHeader" rowspan="2" ><span  readonly="true"> Status</span></td>
		       		                	</tr>
		       							<tr>
		       								<td class="iCargoTableHeader"><span readonly="true">Region</span></td>
		       								<td class="iCargoTableHeader"><span  readonly="true">Country</span></td>
		       								<td class="iCargoTableHeader"><span  readonly="true">City</span></td>
											<td class="iCargoTableHeader"><span  readonly="true">Airport</span></td> <!--Added by A-7540-->
		       								<td class="iCargoTableHeader"><span  readonly="true">Region</span></td>
		       								<td class="iCargoTableHeader"><span  readonly="true">Country</span></td>
		       								<td class="iCargoTableHeader"><span  readonly="true">City</span></td>
											<td class="iCargoTableHeader"><span  readonly="true">Airport</span></td> <!--Added by A-7540-->
		       								<td class="iCargoTableHeader"><span  readonly="true">Country</span></td>
										
		       								<td class="iCargoTableHeader"><span  readonly="true">City</span></td>
												<td class="iCargoTableHeader"><span  readonly="true">Airport</span></td>
		       								<td class="iCargoTableHeader"><span  readonly="true">Country</span></td>
										
		       								<td class="iCargoTableHeader"><span readonly="true">City</span></td>
											<td class="iCargoTableHeader"><span  readonly="true">Airport</span></td> 
											<td class="iCargoTableHeader"><span readonly="true"><common:message  key="mailtracking.mra.defaults.viewbillingmatrix.tableheader.ratingbasis" scope="request"/></span></td>
		       								<td class="iCargoTableHeader"><span readonly="true"><common:message  key="mailtracking.mra.defaults.viewbillingmatrix.tableheader.chgtype" scope="request"/></span></td>
		       								<td class="iCargoTableHeader"><span readonly="true"><common:message  key="mailtracking.mra.defaults.viewbillingmatrix.tableheader.rateorchg" scope="request"/></span></td>
		       							</tr>
		       						   </thead>
									   <tbody>

										 <logic:present name="billingLineDetailsPage">

											<% checkboxes=0; %>
											<logic:iterate id="billingLineVO" name="billingLineDetailsPage" type="com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO" indexId="nIndex">


												<tr>
													<td class="iCargoTableDataTd ic-center" >

															<html:checkbox property="checkboxes" value="<%= String.valueOf(checkboxes) %>" tabindex="<%=String.valueOf(checkboxes+17)%>"/>

													</td>
													<td  class="iCargoTableDataTd">
														<div>
														<logic:present name="billingLineVO" property="billingMatrixId">
															<bean:write name="billingLineVO" property="billingMatrixId"/>
														</logic:present>
														</div>

													</td>
													<td  class="iCargoTableDataTd">
														<div>
														<logic:present name="billingLineVO" property="billingLineSequenceNumber">
															<bean:write name="billingLineVO" property="billingLineSequenceNumber"/>
														</logic:present>
														</div>
													</td>
													<td  class="iCargoTableDataTd">
														<div>
														   <logic:present name="ViewBillingLineForm" property="orgRegion">
														   <% if (checkboxes < form.getOrgRegion().length){%>
															<%=(String)form.getOrgRegion()[checkboxes]%>
															<%}%>
														   </logic:present>
														</div>

													</td>
													<td  class="iCargoTableDataTd">
														<div>
														<logic:present name="ViewBillingLineForm" property="orgCountry">
														<% if (checkboxes < form.getOrgCountry().length){%>
															<%=(String)form.getOrgCountry()[checkboxes]%>
														<%}%>
														</logic:present>
														</div>

													</td>
													<td  class="iCargoTableDataTd">
														<div>
														<logic:present name="ViewBillingLineForm" property="orgCity">
														<% if (checkboxes < form.getOrgCity().length){%>
															<%=(String)form.getOrgCity()[checkboxes]%>
															<%}%>
														</logic:present>
														</div>

													</td>
													<!--added by a7540-->
													<td  class="iCargoTableDataTd">
														<div>
														<logic:present name="ViewBillingLineForm" property="orgAirport">
														<% if (checkboxes < form.getOrgAirport().length){%>
															<%=(String)form.getOrgAirport()[checkboxes]%>
															<%}%>
														</logic:present>
														</div>

													</td>
													<td  class="iCargoTableDataTd">
														<div>
														<logic:present name="ViewBillingLineForm" property="destRegion">
														<% if (checkboxes < form.getDestRegion().length){%>
															<%=(String)form.getDestRegion()[checkboxes]%>
															<%}%>
														</logic:present>
														</div>

													</td>
													<td  class="iCargoTableDataTd">
														<div>
														<logic:present name="ViewBillingLineForm" property="destCountry">
														<% if (checkboxes < form.getDestCountry().length){%>
															<%=(String)form.getDestCountry()[checkboxes]%>
														<%}%>
														</logic:present>
														</div>

													</td>
													<td  class="iCargoTableDataTd">
														<div>
														<logic:present name="ViewBillingLineForm" property="destCity">
														<% if (checkboxes < form.getDestCity().length){%>
															<%=(String)form.getDestCity()[checkboxes]%>
															<%}%>
														</logic:present>
														</div>

													</td>
													<!--added by a7540-->
													<td  class="iCargoTableDataTd">
														<div>
														<logic:present name="ViewBillingLineForm" property="desAirport">
														<% if (checkboxes < form.getDesAirport().length){%>
															<%=(String)form.getDesAirport()[checkboxes]%>
															<%}%>
														</logic:present>
														</div>

													</td>
													<td  class="iCargoTableDataTd">
														<div>
														<logic:present name="ViewBillingLineForm" property="upliftCountry">
														<% if (checkboxes < form.getUpliftCountry().length){%>
															<%=(String)form.getUpliftCountry()[checkboxes]%>
																<%}%>
														</logic:present>
														</div>

													</td>



													<td  class="iCargoTableDataTd">
														<div>
														<logic:present name="ViewBillingLineForm" property="upliftCity">
														<% if (checkboxes < form.getUpliftCity().length){%>
															<%=(String)form.getUpliftCity()[checkboxes]%>
															<%}%>
														</logic:present>
														</div>

													</td>
													
													<td  class="iCargoTableDataTd">
														<div>
														<logic:present name="ViewBillingLineForm" property="upliftAirport">
														<% if (checkboxes < form.getUpliftAirport().length){%>
															<%=(String)form.getUpliftAirport()[checkboxes]%>
																<%}%>
														</logic:present>
														</div>

													</td>


													<td  class="iCargoTableDataTd">
														<div>
														<logic:present name="ViewBillingLineForm" property="dischargeCountry">
														<% if (checkboxes < form.getDischargeCountry().length){%>
															<%=(String)form.getDischargeCountry()[checkboxes]%>
															<%}%>
														</logic:present>
														</div>

													</td>

													<td  class="iCargoTableDataTd">
														<div>
														<logic:present name="ViewBillingLineForm" property="dischargeCity">
														<% if (checkboxes < form.getDischargeCity().length){%>
															<%=(String)form.getDischargeCity()[checkboxes]%>
															<%}%>
														</logic:present>
														</div>

													</td>
													<td  class="iCargoTableDataTd">
														<div>
														<logic:present name="ViewBillingLineForm" property="dischargeAirport">
														<% if (checkboxes < form.getDischargeAirport().length){%>
															<%=(String)form.getDischargeAirport()[checkboxes]%>
															<%}%>
														</logic:present>
														</div>

													</td>

													<td  class="iCargoTableDataTd">
														<div>
														<logic:present name="ViewBillingLineForm" property="billingCategory">
														<% if (checkboxes < form.getBillingCategory().length){%>
															<%=(String)form.getBillingCategory()[checkboxes]%>
															<%}%>
														</logic:present>
														</div>

													</td>
													<td  class="iCargoTableDataTd">
														<div>

															<logic:present name="ViewBillingLineForm" property="billingClasses">
															<% if (checkboxes < form.getBillingClasses().length){%>
																<%=(String)form.getBillingClasses()[checkboxes]%><%System.out.println((String)form.getBillingClasses()[checkboxes]+"/*/*/*/*//*/*****/");%>
																<%}%>
															</logic:present>
														</div>

													</td>
													<td  class="iCargoTableDataTd" style="height:100px;word-wrap: break-word;">
														<div>
															<logic:present name="ViewBillingLineForm" property="billingSubClass">
															<% if (checkboxes < form.getBillingSubClass().length){%>
																<%=(String)form.getBillingSubClass()[checkboxes]%>
																<%}%>
															</logic:present>
														</div>

													</td>
													<td  class="iCargoTableDataTd">
														<div align="left">
															<logic:present name="ViewBillingLineForm" property="subClassGroup">
															<% if (checkboxes < form.getSubClassGroup().length){%>
																<%=(String)form.getSubClassGroup()[checkboxes]%>
																<%}%>
															</logic:present>
														</div>
													</td>
													<td  class="iCargoTableDataTd">
														<div>
															<logic:present name="ViewBillingLineForm" property="billingUldType">
															<% if (checkboxes < form.getBillingUldType().length){%>
																<%=(String)form.getBillingUldType()[checkboxes]%>
																<%}%>
															</logic:present>
														</div>

													</td>
													<td  class="iCargoTableDataTd">
														<div>
															<logic:present name="ViewBillingLineForm" property="billingUldGrp">
															<% if (checkboxes < form.getBillingUldGrp().length){%>
																<%=(String)form.getBillingUldGrp()[checkboxes]%>
																<%}%>
															</logic:present>
														</div>

													</td>
													<td  class="iCargoTableDataTd">
														<div>
															<logic:present name="ViewBillingLineForm" property="billingFlightNo">
															<% if (checkboxes < form.getBillingFlightNo().length){%>
																<%=(String)form.getBillingFlightNo()[checkboxes]%>
																<%}%>
															</logic:present>
														</div>

													</td>
													<!--added by a7540-->
													<td  class="iCargoTableDataTd">
														<div>
															<logic:present name="ViewBillingLineForm" property="viaPoint">
															<% if (checkboxes < form.getViaPoint().length){%>
																<%=(String)form.getViaPoint()[checkboxes]%>
																<%}%>
															</logic:present>
														</div>

													</td>
													<td  class="iCargoTableDataTd">
														<div align="left">
															<logic:present name="ViewBillingLineForm" property="flownCarrier">
															<% if (checkboxes < form.getFlownCarrier().length){%>
																<%=(String)form.getFlownCarrier()[checkboxes]%>
																<%}%>
															</logic:present>
														</div>
													</td>
													<td  class="iCargoTableDataTd">
														<div align="left">
															<logic:present name="ViewBillingLineForm" property="transferredBy">
															<% if (checkboxes < form.getTransferredBy().length){%>
																<%=(String)form.getTransferredBy()[checkboxes]%>
																<%}%>
															</logic:present>
														</div>
													</td>
													<td  class="iCargoTableDataTd">
														<div align="left">
															<logic:present name="ViewBillingLineForm" property="transferredPA">
															<% if (checkboxes < form.getTransferredPA().length){%>
																<%=(String)form.getTransferredPA()[checkboxes]%>
																<%}%>
															</logic:present>
														</div>
													</td>
													<td  class="iCargoTableDataTd">
														<div>

																<logic:present name="billingLineVO" property="billingSector">
																	<bean:define id="sector" name="billingLineVO" property="billingSector"/>
																<logic:present name="KEY_ONETIMES">
																	<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
																	<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
																	<logic:equal name="parameterCode" value="mailtracking.mra.billingSector">
																	<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
																	<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																	<logic:present name="parameterValue" property="fieldValue">

																		<logic:equal name="parameterValue" property="fieldValue" value="<%=String.valueOf(sector)%>">
																			<bean:write name="parameterValue" property="fieldDescription"/>
																		</logic:equal>
																	</logic:present>
																	</logic:iterate>
																	</logic:equal>
																	</logic:iterate>
																</logic:present>
																</logic:present>

														</div>

													</td>
													<!--added by a7540-->
													<td  class="iCargoTableDataTd" style="width:50px">
														<div align="center">
															<logic:present name="ViewBillingLineForm" property="paBuilt">
															<% if (checkboxes < form.getPaBuilt().length){%>
																<%=(String)form.getPaBuilt()[checkboxes]%>
																<%}%>
															</logic:present>
														</div>
													</td>
													
													<td class="iCargoTableDataTd">
														<div align="left">
															<logic:present name="ViewBillingLineForm" property="mailService">
															<% if (checkboxes < form.getMailService().length){%>
																<%=(String)form.getMailService()[checkboxes]%>
																<%}%>
															</logic:present>
														</div>
													</td>
				
						
													<td class="iCargoTableDataTd">
														<div>
														<logic:present name="billingLineVO" property="currencyCode" >
															&nbsp;<bean:write name="billingLineVO" property="currencyCode" />
														</logic:present>
														</div>
													</td>



														<logic:present	name="billingLineVO" property="billingLineDetails">
															<%
																if (billingLineVO.getBillingLineDetails().size() != 0) {
															%>
															<logic:iterate id="billingLineDetail" name="billingLineVO"
																property="billingLineDetails"
																type="com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineDetailVO">
														<logic:present	name="billingLineDetail" property="chargeType">

														<logic:equal name="billingLineDetail" property="chargeType" value="M">
																		<logic:present name="billingLineDetail"
																			property="billingLineCharges">
																			<logic:equal name="billingLineDetail" property="ratingBasis"
																				value="FR">
														<td width="33%" class="iCargoTableDataTd">Flat Rate</td>
																				<td></td>
																				<td class="iCargoTableDataTd"><logic:iterate
																					id="billingLineCharge" name="billingLineDetail"
																					property="billingLineCharges"
																					type="com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineChargeVO">
																					<bean:write name="billingLineCharge"
																						property="applicableRateCharge" />
																				</logic:iterate></td>
														</logic:equal>
																			<logic:equal name="billingLineDetail" property="ratingBasis"
																				value="FC">
														<td width="33%" class="iCargoTableDataTd">Flat Charge</td>
																				<td></td>
																				<td class="iCargoTableDataTd"><logic:iterate
																					id="billingLineCharge" name="billingLineDetail"
																					property="billingLineCharges"
																					type="com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineChargeVO">
																					<ibusiness:moneyDisplay showCurrencySymbol="false" name="billingLineCharge"  moneyproperty="aplRatChg" property="aplRatChg" />
																				</logic:iterate></td>
														</logic:equal>
																			<logic:equal name="billingLineDetail" property="ratingBasis"
																				value="WB">
														<td width="33%" class="iCargoTableDataTd">Weight Break</td>
																				<td><logic:iterate id="billingLineCharge"
																					name="billingLineDetail" property="billingLineCharges"
																					type="com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineChargeVO">
																					<%
																						if (billingLineCharge.getFrmWgt() <= 0) {
																							if (billingLineCharge.getFrmWgt() == -1) {
																					%>
																						<div class="iCargoTableDataDiv">M</div>
																					<%
																						}
																					%>
																					<%
																						if (billingLineCharge.getFrmWgt() == 0) {
																					%>
																						<div class="iCargoTableDataDiv">N</div>
																					<%
																						}

														}else{
														%>
																						<div class="iCargoTableDataDiv">+<bean:write
																						name="billingLineCharge" property="frmWgt" /></div>
														<%
														}
														%>
																				</logic:iterate></td>
																				<td><logic:iterate id="billingLineCharge"
																					name="billingLineDetail" property="billingLineCharges"
																					type="com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineChargeVO">
																					<%
																						if (billingLineCharge.getFrmWgt() <= 0) {
																							if (billingLineCharge.getFrmWgt() == -1) {
																					%>
																					<div class="iCargoTableDataDiv"><ibusiness:moneyDisplay showCurrencySymbol="false" name="billingLineCharge"  moneyproperty="aplRatChg" property="aplRatChg" /></div>
																					<%
																						}
																					%>
																					<%
																						if (billingLineCharge.getFrmWgt() == 0) {
																					%>
																					<div class="iCargoTableDataDiv"><bean:write
																						name="billingLineCharge" property="applicableRateCharge" /></div>
																					<%
																						}
																					}else {
																					%>
																					<div class="iCargoTableDataDiv"><bean:write
																						name="billingLineCharge" property="applicableRateCharge" /></div>
																					<%
																						}
																					%>
																				</logic:iterate></td>
														</logic:equal>
														<logic:equal name="billingLineDetail" property="ratingBasis"
																				value="US">
														<td width="33%" class="iCargoTableDataTd">USPS</td>
														              <td><logic:iterate id="billingLineCharge"
																					name="billingLineDetail" property="billingLineCharges"
																					type="com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineChargeVO">
														
														                            <%
																						if (billingLineCharge.getFrmWgt() == 1) {
				
																					%>
																						<div class="iCargoTableDataDiv marginT5">LH</div>
																					<%
																						}
																					%>
																					<%
																						if (billingLineCharge.getFrmWgt() == 2) {
																					%>
																						<div class="iCargoTableDataDiv">LH-N</div>
																					<%
																						}
																					%>
																					<%	
																					   if (billingLineCharge.getFrmWgt() == 3) {
																					%>
																					    <div class="iCargoTableDataDiv">TH</div>
																					<%
																						}
																					%>
																					<%
																						if (billingLineCharge.getFrmWgt() == 4) {
																					%>
																					<div class="iCargoTableDataDiv">THS</div>
																					<%
																						}
																					%>
																					<%	
																					   if (billingLineCharge.getFrmWgt() == 5) {
																					%>
																					<div class="iCargoTableDataDiv">Total</div>
																					<%
																						}
																					%>
																					<div></div>
																					<%	
																					   if (billingLineCharge.getFrmWgt() == 6) {
																					%>
																					
																					<div class="iCargoTableDataDiv marginT10">Cont. Disc</div>
																					<%
																						}
																					%>
												                                </logic:iterate></td>
																	<td class="iCargoTableDataTd"><logic:iterate
																					id="billingLineCharge" name="billingLineDetail"
																					property="billingLineCharges"
																					type="com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineChargeVO">
																					<%
																						if (billingLineCharge.getFrmWgt() == 1) {
																					%>
																					<div class="iCargoTableDataDiv marginT5"><bean:write name="billingLineCharge"
																						property="applicableRateCharge" /></div>
																						<%
																						}
																					    %>	
																					<%
																						if (billingLineCharge.getFrmWgt() == 2) {
																					%>
																					<div class="iCargoTableDataDiv"><bean:write name="billingLineCharge"
																						property="applicableRateCharge" /></div>
																						<%
																						}
																					    %>
																					<%
																						if (billingLineCharge.getFrmWgt() == 3) {
																					%>
																					<div class="iCargoTableDataDiv"><bean:write name="billingLineCharge"
																						property="applicableRateCharge" /></div>
																						<%
																						}
																					    %>
																					<%
																						if (billingLineCharge.getFrmWgt() == 4) {
																					%>
																					<div class="iCargoTableDataDiv"><bean:write name="billingLineCharge"
																						property="applicableRateCharge" /></div>
																						<%
																						}
																					    %>
																					<%
																						if (billingLineCharge.getFrmWgt() == 5) {
																					%>
																					<div class="iCargoTableDataDiv"><bean:write name="billingLineCharge"
																						property="applicableRateCharge" /></div>
																						<%
																						}
																					    %>
											
																					<%
																						if (billingLineCharge.getFrmWgt() == 6) {
																					%>
																					<div class="iCargoTableDataDiv marginT10"><bean:write name="billingLineCharge"
																						property="applicableRateCharge" /></div>
																						<%
																						}
																					    %>
									
																				</logic:iterate></td>
																					
													</logic:equal>

														</logic:present>
																	</logic:equal>
																</logic:present>
														</logic:iterate>
															<%
																} else {
															%>
															<td></td>
															<td></td>
															<td></td>
															<%
																}
															%>
														</logic:present>
														<logic:notPresent	name="billingLineVO" property="billingLineDetails">
														<td></td>
														<td></td>
														<td></td>
														</logic:notPresent>

													<td  class="iCargoTableDataTd">
														<div> 
														<logic:present name="billingLineVO" property="unitCode">
															<bean:define id="unitCode" name="billingLineVO" property="unitCode"/>
															<logic:present name="KEY_ONETIMES"> 
																<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
																<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
																<logic:equal name="parameterCode" value="mail.mra.gpabilling.untcod">
																<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
																<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																<logic:present name="parameterValue" property="fieldValue">
																	<logic:equal name="parameterValue" property="fieldValue" value="<%=String.valueOf(unitCode)%>">
																		<bean:write name="parameterValue" property="fieldDescription"/>
																	</logic:equal>
																</logic:present>
																</logic:iterate>
																</logic:equal>
																</logic:iterate>
															</logic:present>
															</logic:present>
														</div>
													</td>
													<td  class="iCargoTableDataTd">
														<div>
														<logic:present name="billingLineVO" property="surchargeIndicator" >
														<bean:write name="billingLineVO" property="surchargeIndicator" />
														<ihtml:hidden property="surchargeIndicator" value="Y"/>
							</logic:present>
														<logic:notPresent name="billingLineVO" property="surchargeIndicator" >
														N
														<ihtml:hidden property="surchargeIndicator" value="N"/>
														</logic:notPresent>
														</div>
													</td>
													 <td class="iCargoTableDataTd">
														<div>
														<logic:present name="billingLineVO" property="poaCode" >
															&nbsp;<bean:write name="billingLineVO" property="poaCode" />
														</logic:present>

														<logic:present name="billingLineVO" property="airlineCode" >&nbsp;
														<bean:write name="billingLineVO" property="airlineCode" />
														</logic:present>
														</div>

													</td>

													 <td class="iCargoTableDataTd">
													 <%
													 String rev="REVENUE";
													 String exp="EXPENDITURE";
													 %>
														<div>

															<logic:equal name="billingLineVO" property="revenueExpenditureFlag" value="R">
															<%=(String)rev%>
															</logic:equal>
															<logic:equal name="billingLineVO" property="revenueExpenditureFlag" value="E">
																<%=(String)exp%>
															</logic:equal>
														</div>

													</td>
													<td class="iCargoTableDataTd">
														<div>

														      <logic:present name="billingLineVO" property="validityStartDate">
																<bean:define id="startDate" name="billingLineVO" property="validityStartDate" />
																  <%
																	String strStartDate = ((LocalDate)startDate).toDisplayDateOnlyFormat();
																  %>
																<%=strStartDate%>
															</logic:present>
														</div>

													</td>

													<td class="iCargoTableDataTd">
														<div>

														      <logic:present name="billingLineVO" property="validityEndDate">
																<bean:define id="endDate" name="billingLineVO" property="validityEndDate" />
																  <%
																	String strEndDate =((LocalDate)endDate).toDisplayDateOnlyFormat();
																  %>
																<%=strEndDate%>
															</logic:present>
														</div>

													</td>
													<td class="iCargoTableDataTd">
														<div>
															<logic:present name="billingLineVO" property="billingLineStatus">
															<bean:define id="status" name="billingLineVO" property="billingLineStatus"/>
															<html:hidden name="ViewBillingLineForm" property="statusValue" value="<%=String.valueOf(status)%>" />
															<logic:present name="KEY_ONETIMES">
																<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
																<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
																<logic:equal name="parameterCode" value="mra.gpabilling.ratestatus">
																<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
																<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																<logic:present name="parameterValue" property="fieldValue">

																	<logic:equal name="parameterValue" property="fieldValue" value="<%=String.valueOf(status)%>">
																		<bean:write name="parameterValue" property="fieldDescription"/>
																	</logic:equal>

																</logic:present>
																</logic:iterate>
																</logic:equal>
																</logic:iterate>
															</logic:present>
															</logic:present>
														</div>

													</td>

												</tr>
												<% checkboxes++; %>



											</logic:iterate>
										</logic:present>


									</tbody>
								</table>
							</div>
					</div>

			</div>
		<div class="ic-foot-container paddR5">
			<div class="ic-button-container">
				<div class="ic-row">
					<ihtml:nbutton property="btnSurcharge" accesskey = "S"
				componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_SurchargeBtn" tabindex="<%=String.valueOf(checkboxes+18)%>">
				<common:message key="mailtracking.mra.defaults.viewbillingline.button.surcharge" />
		   </ihtml:nbutton>

			   <ihtml:nbutton property="btnActivate" accesskey = "A"
				componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_Activate" tabindex="<%=String.valueOf(checkboxes+18)%>">
				<common:message
					key="mailtracking.mra.defaults.maintainbillingmatrix.button.activate" />
				</ihtml:nbutton>
				<ihtml:nbutton property="btnInactivate" accesskey = "I"
				componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_InActivate" tabindex="<%=String.valueOf(checkboxes+19)%>" >
				<common:message
					key="mailtracking.mra.defaults.maintainbillingmatrix.button.inactivate" />
				</ihtml:nbutton>
				<ihtml:nbutton property="btnCancel" accesskey = "N"
				componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_Cancel" tabindex="<%=String.valueOf(checkboxes+20)%>">
				<common:message
					key="mailtracking.mra.defaults.maintainbillingmatrix.button.cancel" />
				</ihtml:nbutton>
			   <ihtml:nbutton property="btnCopy" accesskey = "P"
				componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_CopyBtn" tabindex="<%=String.valueOf(checkboxes+21)%>">
				<common:message key="mailtracking.mra.defaults.viewbillingline.button.copy" scope="request" />
			 </ihtml:nbutton>
			   <ihtml:nbutton property="btnClose" accesskey = "O"
				 componentID="CMP_Mailtracking_Mra_Defaults_ViewBillingLine_CloseBtn" tabindex="<%=String.valueOf(checkboxes+22)%>">
			 	<common:message key="mailtracking.mra.defaults.viewbillingline.button.close" scope="request" />
			 </ihtml:nbutton>
				</div>
			</div>
		</div>
	</div>

</ihtml:form>
</div>

	</body>
</html:html>
