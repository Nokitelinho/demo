<%-- 
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name			:  Mailtracking MRA
* File Name				:  MaintainBillingMatrix.jsp
* Date					:  28-Feb-2007
* Author(s)				:  A-2398, A-4823

*************************************************************************/
 --%>
<%@ page language="java"%>

<%@ include file="/jsp/includes/tlds.jsp"%>

<%@ page import="org.apache.struts.Globals"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingMatrixForm"%>
<%@ page import="com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>



<html:html>
<head>





<title><common:message key="mailtacking.mra.defaults.maintainbillingmatrix.title" bundle="billingmatrix" scope="request" /></title>

<meta name="decorator" content="mainpanelrestyledui">
<common:include type="script" src="/js/mail/mra/defaults/MaintainBillingMatrix_Script.jsp" />
</head>

<body>




<bean:define id="form" name="BillingMatrixForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingMatrixForm"
	toScope="page" />
<logic:present name="KEY_SYSPARAMETERS">
		<logic:iterate id="sysPar" name="KEY_SYSPARAMETERS">
			<bean:define id="parameterCode" name="sysPar" property="key"/>
			<logic:equal name="parameterCode" value="mailtracking.mra.overrideroundingvalue">
				<bean:define id="parameterValue" name="sysPar" property="value"/>			
					<logic:notEqual name="parameterValue" value="N">
						<%form.setOverrideRounding("Y");%>
					</logic:notEqual>
			</logic:equal>
		</logic:iterate>
	</logic:present>
	<div id="contentdiv" class="iCargoContent ic-masterbg" style="overflow:auto;">
<ihtml:form action="mailtracking.mra.defaults.maintainbillingmatrix.screenload.do">
	<business:sessionBean id="billingMatrixDetailVO"
		moduleName="mailtracking.mra.defaults"
		screenID="mailtracking.mra.defaults.maintainbillingmatrix"
		method="get" attribute="billingMatrixVO" />
	<business:sessionBean id="billingLineDetailsPage"
		moduleName="mailtracking.mra.defaults"
		screenID="mailtracking.mra.defaults.maintainbillingmatrix"
		method="get" attribute="billingLineDetails" />
	<business:sessionBean id="KEY_ONETIMES"
		moduleName="mailtracking.mra.defaults"
		screenID="mailtracking.mra.defaults.maintainbillingmatrix"
		method="get" attribute="oneTimeVOs" />
	<logic:present name="billingMatrixDetailVO">
		<bean:define id="billingMatrixVO" name="billingMatrixDetailVO"
			toScope="page" />
	</logic:present>

	<html:hidden name="BillingMatrixForm" property="lastPageNum" />
	<html:hidden name="BillingMatrixForm" property="displayPage" />
	<html:hidden name="BillingMatrixForm" property="selectedIndex" />
	<html:hidden name="BillingMatrixForm" property="isModified" />
	<html:hidden name="BillingMatrixForm" property="formStatusFlag" />
	<html:hidden name="BillingMatrixForm" property="airlineIdentfier" />
	<html:hidden name="BillingMatrixForm" property="idValue" />
	<html:hidden name="BillingMatrixForm" property="copyFlag" />
	<html:hidden name="BillingMatrixForm" property="operationFlag" />

	<html:hidden name="BillingMatrixForm" property="actionType" />
	<html:hidden name="BillingMatrixForm" property="selectedIndexes" />
	<html:hidden name="BillingMatrixForm" property="validateStatus" />
	<html:hidden name="BillingMatrixForm" property="disableButton" />
	<html:hidden name="BillingMatrixForm" property="disableActive" />
	<html:hidden name="BillingMatrixForm" property="notSave" />
	<html:hidden name="BillingMatrixForm" property="fromDateFlag"/>
	<!--CONTENT STARTS-->

	<div class="ic-content-main">
		<div class="ic-head-container">
			<span class="ic-page-title ic-display-none">
				<common:message key="mailtacking.mra.defaults.maintainbillingmatrix.pageTitle" scope="request" />
			</span>
			<div class="ic-filter-panel">
			<div class="ic-row">
					<div class="ic-input ic-split-25 ic-mandatory">
						<label>
							<common:message key="mailtacking.mra.defaults.maintainbillingmatrix.blgmtx" scope="request" />
						</label>
						<ihtml:text property="blgMatrixID" componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_BlgMatrixID" maxlength="50" readonly="false"  />
						<div class="lovImg">
						<img src="<%=request.getContextPath()%>/images/lov.png" id="blgMatrixIDLov" height="22" width="22" alt="" /></div>
					</div>
					<div class="ic-input ic-split-25">
						<label>
							<common:message key="mailtacking.mra.defaults.maintainbillingmatrix.gpacode" />
						</label>
						<ihtml:text property="gpaCode" componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_GPACode" maxlength="5"/>
						<div class="lovImg">
						<img src="<%=request.getContextPath()%>/images/lov.png" id="gpaCodelov" height="22" width="22" alt="" /></div>
					</div>
					<div class="ic-input ic-split-25">
						<label>
							<common:message key="mailtacking.mra.defaults.maintainbillingmatrix.airlinecode" scope="request"/>
						</label>
						<ihtml:text property="airlineCode" componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_AirlineCode" maxlength="2" />
						<div class="lovImg">
						<img src="<%=request.getContextPath()%>/images/lov.png" id="airlinelov" height="22" width="22" alt=""/></div>
					</div>
					<div class="ic-button-container paddR5">
						<ihtml:nbutton property="btnList" componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_ListBtn">
						<common:message key="mailtacking.mra.defaults.maintainbillingmatrix.button.List" scope="request" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClear" componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_ClearBtn">
						<common:message key="mailtacking.mra.defaults.maintainbillingmatrix.button.Clear" scope="request" />
						</ihtml:nbutton>
					</div>
				</div>
			</div>
		</div>
		<div class="ic-main-container">
		  <div class="ic-row">
				<div class="ic-input-container ic-border">
						<div class="ic-input ic-split-25" >
							<label ><common:message key="mailtacking.mra.defaults.maintainbillingmatrix.description" scope="request" /></label>
							<ihtml:text property="description" componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_Description"
							maxlength="100" readonly="false" style="text-transform:true;" />
						</div>
						<div class="ic-input ic-mandatory ic-split-25" >
							<label ><common:message key="mailtacking.mra.defaults.maintainbillingmatrix.from" scope="request" /></label>
							<% String readonly="false";%>
							<logic:present name="BillingMatrixForm" property="operationFlag">
							<logic:equal property="operationFlag" name="BillingMatrixForm" value="I">
								<ibusiness:calendar id="validFrom" property="validFrom" componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_ValidFrom" type="image" maxlength="11"  />
							</logic:equal>
							<logic:notEqual property="operationFlag" name="BillingMatrixForm" value="I">
								<ibusiness:calendar id="validFrom" property="validFrom" componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_ValidFrom" type="image" disabled="true" maxlength="11"  />
							</logic:notEqual>
							</logic:present>
							<logic:notPresent name="BillingMatrixForm" property="operationFlag">
							<ibusiness:calendar id="validFrom" property="validFrom" componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_ValidFrom" type="image" disabled="true" maxlength="11"  />
							</logic:notPresent>
						</div>
						<div class="ic-input ic-mandatory ic-split-25" >
							<label ><common:message key="mailtacking.mra.defaults.maintainbillingmatrix.to" scope="request" /></label>
								<ibusiness:calendar id="validTo" property="validTo" componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_ValidTo" type="image" maxlength="11"  />
						</div>
						<div class="ic-input ic-split-25 marginT15" >
							<common:message key="mailtacking.mra.defaults.maintainbillingmatrix.status" scope="request" />
							 <ihtml:text property="status" componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_Status" maxlength="100" readonly="true" style="display:none" />
							<logic:present name="billingMatrixDetailVO">
								<common:display name="billingMatrixDetailVO" property="billingMatrixStatus" />
							</logic:present>
						</div>
				</div>
		  </div>
		  <div class="ic-border marginT5">
		  <div class="ic-row">
		  <div class="ic-col-55">
			<logic:present name="billingLineDetailsPage">
				<common:paginationTag pageURL="mailtracking.mra.defaults.maintainbillingmatrix.list.do" name="billingLineDetailsPage" display="label"
				labelStyleClass="iCargoResultsLabel"
				lastPageNum="<%=((BillingMatrixForm)form).getLastPageNum()%>" />
			</logic:present>
			<logic:notPresent name="roleGroupDetailsPage">
				&nbsp;
			</logic:notPresent>
		  </div>
		  <div class="ic-col-33"> <!-- Modified by A-8236 as part of ICRD-249921 issue 1-->
				<div class="ic-button-container paddR5">
					<logic:present
						name="billingLineDetailsPage">

						<common:paginationTag
							pageURL="javascript:submitPage('lastPageNum','displayPage')"
							linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
							name="billingLineDetailsPage" display="pages"
							lastPageNum="<%=(String)((BillingMatrixForm)form).getLastPageNum()%>"
							exportToExcel="false"
							exportTableId="captureAgtSettlementMemo"
							exportAction="mailtracking.mra.defaults.maintainbillingmatrix.list.do"/>
					</logic:present>
					<logic:notPresent name="billingLineDetailsPage">
						&nbsp;
					</logic:notPresent>
				</div>
				</div>
				<div class="ic-col-11"> <!-- Modified by A-8236 as part of ICRD-249921 issue 1-->
				<div class="ic-button-container paddR5">
					<a href="#" class="iCargoLink" id="addLink"  type="linkSubmit" >Add</a>
					<a href="#" class="iCargoLink" id="modifyLink" type="linkSubmit" >Modify</a>
					<a href="#"class="iCargoLink" id="deleteLink" type="linkSubmit" >Delete</a>
				</div>
				</div>
			</div>
			<div class="ic-row">
			<!--modified by A-7938 for /ICRD-248696-->
			<div class="tableContainer table-border-solid" id="div1" style="height:618px">
					<table class="fixed-header-table" id="captureAgtSettlementMemo">
						<thead>
							<tr class="ic-th-all">
												<th style="width:2%"/>
												<th style="width:6%"/>
												<th style="width:13%"/><!-- Modified by A-8236 as part of ICRD-255861 issue 2-->
												<th style="width:8%"/>

												<th style="width:8%"/>
												<th style="width:5%"/>
												<th style="width:6%"/>
												<th style="width:6%"/>
												<th style="width:6%"/>
												<th style="width:3%"/><!-- Modified by A-8236 as part of ICRD-255861 issue 2-->
												<th style="width:6%"/>
												<th style="width:6%"/>
												<th style="width:8%"/>
												<th style="width:6%"/>
							</tr>
							<tr>
								<td class="iCargoTableHeader ic_inline_chcekbox ic-center" tabindex="-1" rowspan="2"><input type="checkbox" name="headChk"  /></td>
								<td class="iCargoTableHeaderLabel"  rowspan="2"><common:message key="mailtracking.mra.defaults.maintainbillingmatrix.ratelineid" /><span></span></td>
								<td class="iCargoTableHeaderLabel"  rowspan="2"><common:message key="mailtracking.mra.defaults.maintainbillingmatrix.parameter" /><span></span></td>
								<td class="iCargoTableHeaderLabel"  rowspan="2"><common:message key="mailtracking.mra.defaults.maintainbillingmatrix.billedsector" /><span></span></td>
								<td class="iCargoTableHeaderLabel"  colspan="3"><common:message key="mailtracking.mra.defaults.maintainbillingmatrix.mailcharge" /><span></span></td>
								<td class="iCargoTableHeaderLabel"  rowspan="2"><common:message key="mailtracking.mra.defaults.maintainbillingmatrix.surcharge" /><span></span></td>
								<td class="iCargoTableHeaderLabel"  rowspan="2"><common:message key="mailtracking.mra.defaults.maintainbillingmatrix.pay" /><span></span></td>
								<td class="iCargoTableHeaderLabel"  rowspan="2"><common:message key="mailtracking.mra.defaults.maintainbillingmatrix.billingpty" /><span></span></td>
								<td class="iCargoTableHeaderLabel"  rowspan="2"><common:message key="mailtracking.mra.defaults.maintainbillingmatrix.contcurr" /><span></span></td>
								<td class="iCargoTableHeaderLabel"  rowspan="2"><common:message key="mailtracking.mra.defaults.maintainbillingmatrix.unit" /><span></span></td>
								<td class="iCargoTableHeaderLabel"  rowspan="2"><common:message key="mailtracking.mra.defaults.maintainbillingmatrix.rateinc" /><span></span></td>
								<td class="iCargoTableHeaderLabel"  rowspan="2"><common:message key="mailtracking.mra.defaults.maintainbillingmatrix.validfrom" /><span></span></td>
								<td class="iCargoTableHeaderLabel"  rowspan="2"><common:message key="mailtracking.mra.defaults.maintainbillingmatrix.validto" /><span></span></td>
								<td class="iCargoTableHeaderLabel"  rowspan="2"><common:message key="mailtracking.mra.defaults.maintainbillingmatrix.status" /><span></span></td>
							</tr>
							<tr>
								<td class="iCargoTableHeaderLabel"><common:message key="mailtracking.mra.defaults.maintainbillingmatrix.ratingbasis" /><span></span></td>
								<td class="iCargoTableHeaderLabel"><common:message key="mailtracking.mra.defaults.maintainbillingmatrix.chargetype" /><span></span></td>
								<td class="iCargoTableHeaderLabel"><common:message key="mailtracking.mra.defaults.maintainbillingmatrix.rate" /><span></span></td>
								<!--modified by A-7938 for /ICRD-248696-->
								<!--<td></td>-->
							</tr>
						</thead>
						<tbody>
							<logic:present name="billingLineDetailsPage">
								<% int checkboxes=0; %>
								<logic:iterate id="billingLineVO" name="billingLineDetailsPage"
									type="com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO"
									indexId="nIndex">
								<logic:notEqual name="billingLineVO" property="operationFlag" value="D">
											<td class="iCargoTableDataTd ic_inline_chcekbox ic-center">
											<html:checkbox property="checkboxes" value="<%= String.valueOf(checkboxes) %>"/>
												</td>
											<td class="iCargoTableDataTd"><div align="center"><bean:write name="billingLineVO" property="billingLineSequenceNumber" /></div></td>
												<td>
											<table class="iCargoBorderLessTable" style="width:100%;">
													<logic:present	name="BillingMatrixForm" property="orgCity">
													<% if (checkboxes < form.getOrgCity().length
													&& form.getOrgCity()[checkboxes].trim().length()>0 ) {%>
													<tr>
														<td class="iCargoTableDataTd">Org.City
														</td>
														<td class="iCargoTableDataTd">

														<%=(String)form.getOrgCity()[checkboxes]%>
														<%}%>
														</td>
													</tr>
													</logic:present>

													<logic:present	name="BillingMatrixForm" property="orgCountry">
													<% if (checkboxes < form.getOrgCountry().length
													&& form.getOrgCountry()[checkboxes].trim().length()>0 ){%>

													<tr>
														<td class="iCargoTableDataTd">Org.Ctry
														</td>
														<td class="iCargoTableDataTd">
															<%=(String)form.getOrgCountry()[checkboxes]%>
														<%}%>
														</td>
													</tr>
													</logic:present>

													<logic:present	name="BillingMatrixForm" property="orgRegion">
														<% if (checkboxes < form.getOrgRegion().length
														&& form.getOrgRegion()[checkboxes].trim().length()>0 ){%>
													<tr>
														<td class="iCargoTableDataTd">Org.Regn
														</td>
														<td class="iCargoTableDataTd">

														<%=(String)form.getOrgRegion()[checkboxes]%>
														<%}%>
														</td>
													</tr>
													</logic:present>
													<!--Added by a-7540 for icrd-232319-->
													<logic:present	name="BillingMatrixForm" property="orgAirport">                                                    
														<% if (checkboxes < form.getOrgAirport().length
														&& form.getOrgAirport()[checkboxes].trim().length()>0 ){%>
													<tr>
														<td class="iCargoTableDataTd">Org.Airport
														</td>
														<td class="iCargoTableDataTd">
														<%=(String)form.getOrgAirport()[checkboxes]%>
														<%}%>		
														</td>
													</tr>
													</logic:present>

													<logic:present	name="BillingMatrixForm" property="destCity">
													<% if (checkboxes < form.getDestCity().length
													&& form.getDestCity()[checkboxes].trim().length()>0 ){%>
													<tr>
														<td class="iCargoTableDataTd">Dest.City
														</td>
														<td class="iCargoTableDataTd">

														<%=(String)form.getDestCity()[checkboxes]%>
														<%}%>
														</td>
													</tr>
													</logic:present>

													<logic:present	name="BillingMatrixForm" property="destCountry">
														<% if (checkboxes < form.getDestCountry().length
														&& form.getDestCountry()[checkboxes].trim().length()>0 ){%>
													<tr>
														<td class="iCargoTableDataTd">Dest.Ctry
														</td>
														<td class="iCargoTableDataTd">

														<%=(String)form.getDestCountry()[checkboxes]%>
														<%}%>
														</td>
													</tr>
													</logic:present>

													<logic:present	name="BillingMatrixForm" property="destRegion">
													<% if (checkboxes < form.getDestRegion().length
													&& form.getDestRegion()[checkboxes].trim().length()>0 ){%>
													<tr>
														<td class="iCargoTableDataTd">Dest.Regn
														</td>
														<td class="iCargoTableDataTd">

														<%=(String)form.getDestRegion()[checkboxes]%>
														<%}%>
														</td>
													</tr>
													</logic:present>

													<!--Added by a-7540 for icrd-232319-->
													<logic:present	name="BillingMatrixForm" property="desAirport">                                                    
														<% if (checkboxes < form.getDesAirport().length
														&& form.getDesAirport()[checkboxes].trim().length()>0 ){%>
													<tr>
														<td class="iCargoTableDataTd">Des.Airport
														</td>
														<td class="iCargoTableDataTd">
														<%=(String)form.getDesAirport()[checkboxes]%>
														<%}%>		
														</td>
													</tr>
													</logic:present>

													<logic:present	name="BillingMatrixForm" property="upliftCity">
													<% if (checkboxes < form.getUpliftCity().length
													&& form.getUpliftCity()[checkboxes].trim().length()>0){%>
													<tr>
														<td class="iCargoTableDataTd">Upl.City
														</td>
														<td class="iCargoTableDataTd">

														<%=(String)form.getUpliftCity()[checkboxes]%>
														<%}%>
														</td>
													</tr>
													</logic:present>
													<logic:present	name="BillingMatrixForm" property="uplAirport">                                                    
														<% if (checkboxes < form.getUplAirport().length
														&& form.getUplAirport()[checkboxes].trim().length()>0 ){%>
													<tr>
														<td class="iCargoTableDataTd">Upl.Airport
														</td>
														<td class="iCargoTableDataTd">
														<%=(String)form.getUplAirport()[checkboxes]%>
														<%}%>		
														</td>
													</tr>
													</logic:present>
													<logic:present	name="BillingMatrixForm" property="agentCode">   <!--Added by a-7531 for icrd-253606-->
													<% if (checkboxes < form.getAgentCode().length
													&& form.getAgentCode()[checkboxes].trim().length()>0){%>
													<tr>
														<td class="iCargoTableDataTd">Agent
														</td>
														<td class="iCargoTableDataTd">

														<%=(String)form.getAgentCode()[checkboxes]%>
														<%}%>
														</td>
													</tr>
													</logic:present>

													<logic:present	name="BillingMatrixForm" property="viaPoint">                                                    
													<% if (checkboxes < form.getViaPoint().length
														&& form.getViaPoint()[checkboxes].trim().length()>0 ){%>
													<tr>
														<td class="iCargoTableDataTd">Via Point
														</td>
														<td class="iCargoTableDataTd">
														<%=(String)form.getViaPoint()[checkboxes]%>
														<%}%>		
														</td>
													</tr>
													</logic:present>

													<logic:present	name="BillingMatrixForm" property="upliftCountry">
													<% if (checkboxes < form.getUpliftCountry().length
													&& form.getUpliftCountry()[checkboxes].trim().length()>0){%>
													<tr>
														<td class="iCargoTableDataTd">Upl.Ctry
														</td>
														<td class="iCargoTableDataTd">

														<%=(String)form.getUpliftCountry()[checkboxes]%>
														<%}%>
														</td>
													</tr>
													</logic:present>

													<logic:present	name="BillingMatrixForm" property="dischargeCity">
													<% if (checkboxes < form.getDischargeCity().length
													&& form.getDischargeCity()[checkboxes].trim().length()>0){%>
													<tr>
														<td class="iCargoTableDataTd">Dis.City
														</td>
														<td class="iCargoTableDataTd">

														<%=(String)form.getDischargeCity()[checkboxes]%>
														<%}%>
														</td>
													</tr>
													</logic:present>

													<logic:present	name="BillingMatrixForm" property="dischargeCountry">
													<% if (checkboxes < form.getDischargeCountry().length
													&& form.getDischargeCountry()[checkboxes].trim().length()>0){%>
													<tr>
														<td class="iCargoTableDataTd">Dis.Ctry
														</td>
														<td class="iCargoTableDataTd">

														<%=(String)form.getDischargeCountry()[checkboxes]%>
														<%}%>
														</td>
													</tr>
													</logic:present>
													<logic:present	name="BillingMatrixForm" property="disAirport">                                                    
														<% if (checkboxes < form.getDisAirport().length
														&& form.getDisAirport()[checkboxes].trim().length()>0 ){%>
													<tr>
														<td class="iCargoTableDataTd">Dis.Airport
														</td>
														<td class="iCargoTableDataTd">
														<%=(String)form.getDisAirport	()[checkboxes]%>
														<%}%>		
														</td>
													</tr>
													</logic:present>

													<logic:present	name="BillingMatrixForm" property="billingCategory">
													<% if (checkboxes < form.getBillingCategory().length
													&& form.getBillingCategory()[checkboxes].trim().length()>0){%>
													<tr>
														<td class="iCargoTableDataTd">Category
														</td>
														<td class="iCargoTableDataTd">

														<%=(String)form.getBillingCategory()[checkboxes]%>
														<%}%>
														</td>
													</tr>
													</logic:present>

													<logic:present	name="BillingMatrixForm" property="billingClass">
														<% if (checkboxes < form.getBillingClass().length
														&& form.getBillingClass()[checkboxes].trim().length()>0){%>

													<tr>
														<td class="iCargoTableDataTd">Class
														</td>
														<td class="iCargoTableDataTd">

														<%=(String)form.getBillingClass()[checkboxes]%>
														<%}%>
														</td>
													</tr>
													</logic:present>

													<logic:present	name="BillingMatrixForm" property="subClass">
													<% if (checkboxes < form.getSubClass().length
													&& form.getSubClass()[checkboxes].trim().length()>0){%>
													<tr>
													<td class="iCargoTableDataTd">Sub Class
													</td>
													<td class="iCargoTableDataTd" style="height:100px;word-wrap: break-word;">

													<%=(String)form.getSubClass()[checkboxes]%>
													<%}%>
													</td>
													</tr>
													</logic:present>

													<logic:present	name="BillingMatrixForm" property="billingSubClass">
													<% if (checkboxes < form.getBillingSubClass().length
													&& form.getBillingSubClass()[checkboxes].trim().length()>0){%>
													<tr>
														<td class="iCargoTableDataTd">Sub Class Group
														</td>
														<td class="iCargoTableDataTd">

														<%=(String)form.getBillingSubClass()[checkboxes]%>
														<%}%>
														</td>
													</tr>
													</logic:present>

													<!--Added by a-7540 for icrd-232319-->
													<logic:present	name="BillingMatrixForm" property="mailService">
													<% if (checkboxes < form.getMailService().length
													&& form.getMailService()[checkboxes].trim().length()>0){%>
													<tr>
														<td class="iCargoTableDataTd">Mail Service
														</td>
														<td class="iCargoTableDataTd">

														<%=(String)form.getMailService()[checkboxes]%>
														<%}%>
														</td>
													</tr>
													</logic:present>

													<logic:present	name="BillingMatrixForm" property="billingUldType">
													<% if (checkboxes < form.getBillingUldType().length
													&& form.getBillingUldType()[checkboxes].trim().length()>0){%>
													<tr>
														<td class="iCargoTableDataTd">Uld Group
														</td>
														<td class="iCargoTableDataTd">

														<%=(String)form.getBillingUldType()[checkboxes]%>
														<%}%>
														</td>
													</tr>
													</logic:present>

													<logic:present	name="BillingMatrixForm" property="billingFlightNo">
													<% if (checkboxes < form.getBillingFlightNo().length
													&& form.getBillingFlightNo()[checkboxes].trim().length()>0){%>
													<tr>
														<td class="iCargoTableDataTd">Flt.Num
														</td>
														<td class="iCargoTableDataTd">

														<%=(String)form.getBillingFlightNo()[checkboxes]%>
														<%}%>
														</td>
													</tr>
													</logic:present>
													
													<logic:present	name="BillingMatrixForm" property="flownCarrier">
														<% if (checkboxes < form.getFlownCarrier().length
														&& form.getFlownCarrier()[checkboxes].trim().length()>0){%>
														<tr>
														<td class="iCargoTableDataTd">Flown Carrier
														</td>
														<td class="iCargoTableDataTd">

														<%=(String)form.getFlownCarrier()[checkboxes]%>
														<%}%>
														</td>
														</tr>
													</logic:present>
													
													<logic:present	name="BillingMatrixForm" property="transferedBy">
														<% if (checkboxes < form.getTransferedBy().length
														&& form.getTransferedBy()[checkboxes].trim().length()>0){%>
														<tr>
														<td class="iCargoTableDataTd">TransferedBy
														</td>
														<td class="iCargoTableDataTd">

														<%=(String)form.getTransferedBy()[checkboxes]%>
														<%}%>
														</td>
														</tr>
													</logic:present>
													<logic:present	name="BillingMatrixForm" property="mailCompanyCode">
													<% if (checkboxes < form.getMailCompanyCode().length
													&& form.getMailCompanyCode()[checkboxes].trim().length()>0){%>
													<tr>
													<td class="iCargoTableDataTd">Company
													</td>
													<td class="iCargoTableDataTd">
													<%=(String)form.getMailCompanyCode()[checkboxes]%>
													<%}%>
													</td>
													</tr>
													</logic:present>
													<logic:present	name="BillingMatrixForm" property="transferedPA">
													<% if (checkboxes < form.getTransferedPA().length
													&& form.getTransferedPA()[checkboxes].trim().length()>0){%>
													<tr>
													<td class="iCargoTableDataTd">TransferedPA
													</td>
													<td class="iCargoTableDataTd">

													<%=(String)form.getTransferedPA()[checkboxes]%>
													<%}%>
													</td>
													</tr>
													</logic:present>
													<logic:present	name="BillingMatrixForm" property="paBuilt">
													<% if (checkboxes < form.getPaBuilt().length
													&& form.getPaBuilt()[checkboxes].trim().length()>0){%>
													<tr>
													<td class="iCargoTableDataTd">PA Built
													</td>
													<td class="iCargoTableDataTd">
													<%=(String)form.getPaBuilt()[checkboxes]%>
												    <%}%>
													</td>
													</tr>
													</logic:present>
											</table>
											</td>

												<td class="iCargoTableDataTd">
										<div align="left">
										<logic:present name="billingLineVO" property="billingSector">
											<bean:define id="sector" name="billingLineVO" property="billingSector" />
													<logic:present name="KEY_ONETIMES">
														<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
													<bean:define id="parameterCode" name="oneTimeValue" property="key" />
													<logic:equal name="parameterCode" value="mailtracking.mra.billingSector">
														<bean:define id="parameterValues" name="oneTimeValue" property="value" />
														<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
															<logic:present name="parameterValue"  property="fieldValue">
																		<logic:equal name="parameterValue"
																			property="fieldValue"
																			value="<%=String.valueOf(sector)%>">
																	<bean:write name="parameterValue" property="fieldDescription" />
																		</logic:equal>
																	</logic:present>
																</logic:iterate>
															</logic:equal>
														</logic:iterate>
													</logic:present>
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
																					 <logic:equal name="form" property="overrideRounding" value = "Y">
																					<ibusiness:moneyDisplay showCurrencySymbol="false" name="billingLineCharge"  moneyproperty="aplRatChg" property="aplRatChg" overrideRounding = "true"/>																				
										  	</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
																					<ibusiness:moneyDisplay showCurrencySymbol="false" name="billingLineCharge"  moneyproperty="aplRatChg" property="aplRatChg" />
													</logic:notEqual>
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
																					
																					<div class="iCargoTableDataDiv">
																					<logic:equal name="form" property="overrideRounding" value = "Y">
																					
																					<ibusiness:moneyDisplay showCurrencySymbol="false" name="billingLineCharge"  moneyproperty="aplRatChg" property="aplRatChg" overrideRounding = "true"/>
																					</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
												<ibusiness:moneyDisplay showCurrencySymbol="false" name="billingLineCharge"  moneyproperty="aplRatChg" property="aplRatChg" />
													</logic:notEqual>
																					
																					
																					</div>
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

										<td class="iCargoTableDataTd"><div align="center">

										<logic:present	name="billingLineVO"	property="surchargeIndicator">
											<bean:write name="billingLineVO" property="surchargeIndicator"/>
											<ihtml:hidden property="surchargeIndicator" value="Y"/>
										</logic:present>
										<logic:notPresent	name="billingLineVO"	property="surchargeIndicator">
											N
											<ihtml:hidden property="surchargeIndicator" value="N"/>
										</logic:notPresent>
										</div></td>
										<td class="iCargoTableDataTd"><%
										 String rec="Receivable";
										 String pay="Payable";
										 %>
										<div align="left"><bean:define id="rvEx" name="billingLineVO" property="revenueExpenditureFlag" />
										<html:hidden name="BillingMatrixForm" property="revExpFlag"
											value="<%=String.valueOf(rvEx)%>" /> <logic:equal
											name="billingLineVO" property="revenueExpenditureFlag"
											value="R">
											<%=(String)rec%>
										</logic:equal> <logic:equal name="billingLineVO"
											property="revenueExpenditureFlag" value="E">
											<%=(String)pay%>
										</logic:equal></div>
												</td>
												<td class="iCargoTableDataTd">
													<logic:present
													name="billingLineVO"
													property="poaCode">
													<bean:write name="billingLineVO"
													property="poaCode"/>
													</logic:present>
													<logic:present
													name="billingLineVO"
													property="airlineCode">
													<bean:write name="billingLineVO"
													property="airlineCode"/>
													</logic:present>
												</td>
												<td  class="iCargoTableDataTd">
												<div align="left">
													<bean:write name="billingLineVO" property="currencyCode" />
												</div>
												</td>
										<td class="iCargoTableDataTd">
											<div align="left">
											<logic:present name="billingLineVO" property="unitCode">
														<bean:write name="billingLineVO" property="unitCode" />
											</logic:present>
											</div>
										</td>
										<%if((((BillingLineVO)billingLineVO).isTaxIncludedInRateFlag())) {%>
										<td class="iCargoTableDataTd ic_inline_chcekbox" >
										<div align="center">
											<input type="checkbox" name ="isTaxIncludedInRateFlag" indexId="rowCount" checked disabled="true">
											</div>
										</td>
										<%}else{%>
										<td class="iCargoTableDataTd" >
										<div align="center">
											<input type="checkbox" name ="isTaxIncludedInRateFlag" indexId="rowCount" disabled="true">
											</div>
										</td>
										<%}%>

												<td class="iCargoTableDataTd">
												<div align="left"><logic:present name="billingLineVO"
													property="validityStartDate">
													<bean:define id="startDate" name="billingLineVO"
														property="validityStartDate" />
													<%
													String strStartDate = ((LocalDate)startDate).toDisplayDateOnlyFormat();
												  %>
													<%=strStartDate%>
												</logic:present></div>

												</td>

												<td class="iCargoTableDataTd">
												<div align="left"><logic:present name="billingLineVO"
													property="validityEndDate">
													<bean:define id="endDate" name="billingLineVO"
														property="validityEndDate" />
													<%
													String strEndDate =((LocalDate)endDate).toDisplayDateOnlyFormat();
												  %>
													<%=strEndDate%>
												</logic:present></div>
												</td>

									<logic:present name="billingLineVO" property="validityEndDate">
										<bean:define id="blgBasis" name="billingLineVO" property="billingBasis" />
										<html:hidden property="billingBasis" name="BillingMatrixForm" value="<%=String.valueOf(blgBasis)%>" />
												</logic:present>
												<td class="iCargoTableDataTd">
												<div align="left"><bean:define id="statusval"
													name="billingLineVO" property="billingLineStatus" /> <html:hidden
													name="BillingMatrixForm" property="statusValue"
													value="<%=String.valueOf(statusval)%>" /> <logic:present
													name="KEY_ONETIMES">
													<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
														<bean:define id="parameterCode" name="oneTimeValue"
															property="key" />
														<logic:equal name="parameterCode"
															value="mra.gpabilling.ratestatus">
															<bean:define id="parameterValues" name="oneTimeValue"
																property="value" />
															<logic:iterate id="parameterValue" name="parameterValues"
																type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																<logic:present name="parameterValue"
																	property="fieldValue">

																	<logic:equal name="parameterValue"
																		property="fieldValue"
																		value="<%=String.valueOf(statusval)%>">
																		<bean:write name="parameterValue"
																			property="fieldDescription" />
																	</logic:equal>
																</logic:present>
															</logic:iterate>
														</logic:equal>
													</logic:iterate>
												</logic:present></div>

												</td>

											</tr>
											<% checkboxes++; %>
									</logic:notEqual>
								</logic:iterate>
							</logic:present>

						</tbody>
					</table>
					</div>
			</div>
			</div>
		 </div>
		 <div class="ic-foot-container paddR5">
			<div class="ic-row">
				<div class="ic-button-container">
				<ihtml:nbutton property="btnChangeEnddate"
						componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_ChangeEnddate">
						<common:message
							key="mailtracking.mra.defaults.maintainbillingmatrix.button.changeEnddate" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btnSurcharge"
						componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_Surcharge">
						<common:message
							key="mailtracking.mra.defaults.maintainbillingmatrix.button.surcharge" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btnActivate"
						componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_Activate">
						<common:message
							key="mailtracking.mra.defaults.maintainbillingmatrix.button.activate" />
					</ihtml:nbutton> <ihtml:nbutton property="btnInactivate"
						componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_InActivate">
						<common:message
							key="mailtracking.mra.defaults.maintainbillingmatrix.button.inactivate" />
					</ihtml:nbutton> <ihtml:nbutton property="btnCancel"
						componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_Cancel">
						<common:message
							key="mailtracking.mra.defaults.maintainbillingmatrix.button.cancel" />
					</ihtml:nbutton> <ihtml:nbutton property="btnCopy"
						componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_CopyBtn">
						<common:message
							key="mailtracking.mra.defaults.maintainbillingmatrix.button.Copy" />
					</ihtml:nbutton> <ihtml:nbutton property="btnSave"
						componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_SaveBtn">
						<common:message
							key="mailtacking.mra.defaults.maintainbillingmatrix.button.Save"
							scope="request" />
					</ihtml:nbutton> <ihtml:nbutton property="btnClose"
						componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_CloseBtn">
						<common:message
							key="mailtacking.mra.defaults.maintainbillingmatrix.button.Close"
							scope="request" />
					</ihtml:nbutton>
				</div>
			</div>
			</div>
		</div>
	</div>
</ihtml:form>
	</div>
<!---CONTENT ENDS-->


	</body>
</html:html>
