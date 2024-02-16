<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name			:  IN - ULD Management
* File Name				:  MaintainULDAgreement.jsp
* Date					:  18-Jan-2006
* Author(s)				:  A-2046
*************************************************************************/
 --%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainULDAgreementForm"%>
<%@page import ="java.util.ArrayList"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="java.lang.Double"%>
<%@ page import="java.math.RoundingMode"%>

<html:html locale="true">
<head>
<title>iCargo : Maintain ULD Agreement</title>
<meta name="decorator" content="mainpanelrestyledui" >
<common:include type="script" src="/js/uld/defaults/MaintainULDAgreement_Script.jsp"/>
</head>
<body id="bodyStyle" >
<%@include file="/jsp/includes/reports/printFrame.jsp" %>
	<bean:define id="form"
	 name="maintainULDAgreementForm"
	 type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainULDAgreementForm" toScope="page" />


<business:sessionBean id="agreementVO"
moduleName="uld.defaults"
screenID="uld.defaults.maintainuldagreement"
method="get" attribute="uldAgreementDetails" />

<business:sessionBean id="agreementNumbers"
moduleName="uld.defaults"
screenID="uld.defaults.maintainuldagreement"
method="get" attribute="agreementNumbers"/>

<business:sessionBean id="KEY_AGREEMENTS"
moduleName="uld.defaults"
screenID="uld.defaults.maintainuldagreement"
method="get" attribute="uldAgreementPageDetails"/>
<logic:present name="KEY_AGREEMENTS">
		<bean:define id="KEY_AGREEMENTS" name="KEY_AGREEMENTS" toScope="page"/>
</logic:present>

		<div class="iCargoContent ic-masterbg">
        <ihtml:form action="/uld.defaults.screenloaduldagreement.do">
		<ihtml:hidden property="onload"/>
        <ihtml:hidden property="preview"/>
<ihtml:hidden property="displayPage"/>
<ihtml:hidden property="canClear"/>
<ihtml:hidden property="closeStatus"/>
<ihtml:hidden property="listStatus"/>
<ihtml:hidden property="createFlag"/>
<ihtml:hidden property="comboFlag"/>
<ihtml:hidden property="toComboFlag"/>
<ihtml:hidden property="agrPartyCode"/>
<ihtml:hidden property="agrPartyName"/>
<ihtml:hidden property="typeOfParty"/>
<ihtml:hidden property="errorStatusFlag"/>
<ihtml:hidden property="lastPageNumStr" />
<ihtml:hidden property="displayPageNumStr" />
<ihtml:hidden property="uldTypeFilterWarningFlag" />
<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />
		<div class="ic-content-main">
			<div class="ic-head-container">
			<div class="ic-filter-panel">		
				<div class="ic-input-container">
			
								<div class="ic-row">
								<h4  style="text-transform : uppercase">
										<common:message key="uld.defaults.maintainuldagreement.searchcriteria" scope="request"/>
								</h4>
								<div>	
								
							
								<div class="ic-row">
									<div class="ic-input ic-split-15px ic-mandatory ic-center">
										<label><common:message key="uld.defaults.maintainuldagreement.agreementnumber" scope="request"/></label>
									
										<logic:present name="agreementVO">
										  <logic:present name="agreementVO" property="agreementNumber" >
											<ihtml:text property="agreementNumber" name="agreementVO" componentID="TXT_ULD_DEFAULTS_MAINTAINULDAGRMNT_AGREEMENTNUMBER" style="text-transform:uppercase" maxlength="12"/>
											<button type="button" class="iCargoLovButton" name="agrlov" id="agrlov" disabled="true"  />
										  </logic:present>
										  <logic:notPresent name="agreementVO" property="agreementNumber" >
											<ihtml:text property="agreementNumber" name="agreementVO" componentID="TXT_ULD_DEFAULTS_MAINTAINULDAGRMNT_AGREEMENTNUMBER" style="text-transform:uppercase" maxlength="12"/>
											<button type="button" class="iCargoLovButton" name="agrlov" id="agrlov"   />
										  </logic:notPresent>
										</logic:present>
										<logic:notPresent name="agreementVO">
											<ihtml:text property="agreementNumber" componentID="TXT_ULD_DEFAULTS_MAINTAINULDAGRMNT_AGREEMENTNUMBER" style="text-transform:uppercase" maxlength="12"/>
											<button type="button" class="iCargoLovButton" name="agrlov" id="agrlov"  />
										</logic:notPresent>
									</div>	
										
									
											<div class="ic-button-container">
												<ihtml:nbutton property="btnList" componentID="BTN_ULD_DEFAULTS_MAINTAINULDAGRMNT_LIST" accesskey="L">
												<common:message key="uld.defaults.maintainuldagreement.list" scope="request"/></ihtml:nbutton>
												  <ihtml:nbutton property="btnClear" componentID="BTN_ULD_DEFAULTS_MAINTAINULDAGRMNT_CLEAR" accesskey="C">
												 <common:message key="uld.defaults.maintainuldagreement.clear" scope="request"/>
												  </ihtml:nbutton>
											</div>
									
									
								</div>	
				</div>
			</div>
				</div>
			</div>
			</div>
			<div class="ic-main-container">
				<div class="ic-row">
				<label><h4><common:message key="uld.defaults.maintainuldagreement.agrdetails" scope="request"/></h4></label>
				</div>
				<fieldset class="ic-field-set">
				
					
						
					<div class="ic-row ic-label-33">
						
						<div class="ic-input ic-split-25">
							<label><common:message key="uld.defaults.maintainuldagreement.txntype" scope="request"/></label>
							<business:sessionBean id="txnTypes"
								  moduleName="uld.defaults"
								  screenID="uld.defaults.maintainuldagreement" method="get"
								  attribute="transactionType"/>
								  <ihtml:select property="transactionType" componentID="CMB_ULD_DEFAULTS_MAINTAINULDAGRMNT_TCTNTYP">
								  <logic:present name="txnTypes" >
									<bean:define id="txnType" name="txnTypes"/>
										<ihtml:options collection="txnType" property="fieldValue" labelProperty="fieldDescription"/>
								  </logic:present>
								</ihtml:select>
						</div>
						<div class="ic-input ic-split-25">
								<label></label>
						</div>	
						<div class="ic-input ic-split-25">
							<label><common:message key="uld.defaults.maintainuldagreement.agrstatus" scope="request"/></label>
							<business:sessionBean id="statusValues"
					  moduleName="uld.defaults"
					  screenID="uld.defaults.maintainuldagreement" method="get" attribute="agreementStatus"/>

							<logic:present name="agreementVO" property="agreementStatus">
								<logic:present name="statusValues">
									<bean:define id="statusFromVO" name="agreementVO" property="agreementStatus"/>
										<logic:iterate id="status" name="statusValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="status" property="fieldValue">
												<bean:define id="fieldDescription" name="status" property="fieldDescription"/>
													<bean:define id="fieldValue" name="status" property="fieldValue"/>
														<logic:equal name="status" property="fieldValue" value="<%=(String)statusFromVO%>">
															<ihtml:hidden name="agreementVO" property="agreementStatus"/>
																<!--Commented by A-5493 for ICRD-48370-->
																<!--<ihtml:text property="agreementStatus" disabled="true" value="<%=(String)fieldDescription%>" componentID="TXT_ULD_DEFAULTS_MAINTAINULDAGRMNT_STATUS" />-->
																<!--Commented by A-5493 ends-->
																<ihtml:text property="agreementStatus" disabled="true" value="<%=(String)fieldDescription%>" componentID="TXT_ULD_DEFAULTS_MAINTAINULDAGRMNT_AGREEMENTSTATUS"/>
														</logic:equal>
												</logic:present>
										</logic:iterate>
								</logic:present>
							</logic:present>

							<logic:notPresent name="agreementVO" property="agreementStatus">
									<ihtml:hidden property="agreementStatus" value="A"/>
									<ihtml:text property="agreementStatus" value="Active"  componentID="TXT_ULD_DEFAULTS_MAINTAINULDAGRMNT_STATUS" disabled="true"/>
								</logic:notPresent>
						</div>
						<div class="ic-input ic-split-25 ic-mandatory">
						<label><common:message key="uld.defaults.maintainuldagreement.agrdate" scope="request"/></label>
						<%String date=(form.getAgreementDate()==null?"":form.getAgreementDate());%>
					 	<ibusiness:calendar componentID="ULD_DEFAULTS_MAINTAINULDAGRMNT_CALENDAR1"
						property="agreementDate" id="agreementDate"
						type="image" value="<%=date%>" title = "Agreement Date"  />
						</div>					
					</div>
				</fieldset>	
			<!-- Space to add party details-->
			<div class="ic-row">
			<h4><common:message key="uld.defaults.maintainuldagreement.partydetails"/></h4>
		   </div>
			<div class="ic-row ">
			<div class="ic-border">
			<div class="ic-col ic-center" style="width:50%">
				<fieldset class="ic-field-set">
					<legend class="iCargoLegend">
						<common:message key="uld.defaults.maintainuldagreement.fromparty"/>
					</legend>
					<div class="ic-row">
					<business:sessionBean id="partyTypes"
							moduleName="uld.defaults"
							screenID="uld.defaults.maintainuldagreement" method="get"
							attribute="partyType" />
					<div class="ic-input ic-split-20" >
						<label><common:message key="uld.defaults.maintainuldagreement.partytype" scope="request"/></label>
						<ihtml:select property="fromPartyType" componentID="CMB_ULD_DEFAULTS_MAINTAINULDAGRMNT_FROMPARTYTYP">
								  <logic:present name="partyTypes">
								 <bean:define id="partyType" name="partyTypes"/>
								 <ihtml:options collection="partyType" property="fieldValue" labelProperty="fieldDescription"/>
								 </logic:present>
								 <ihtml:option value="L">ALL</ihtml:option>
							</ihtml:select>
					</div>	
					   <div class="ic-input ic-split-20 ic-mandatory">
							<label><common:message key="uld.defaults.maintainuldagreement.partycode" scope="request"/></label>
							<ihtml:text property="fromPartyCode" componentID="TXT_ULD_DEFAULTS_MAINTAINULDAGRMNT_FROMPARTYCODE" style="text-transform:uppercase" />
							<div class="lovImg">
						 <img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" name="fromAirlineLov" id="fromAirlineLov" alt="Airline LOV" />
						 </div>
						</div>
						<div class="ic-input ic-split-40">
						<label><common:message key="uld.defaults.maintainuldagreement.name" scope="request"/></label>
						<ihtml:text property="fromPartyName" componentID="TXT_ULD_DEFAULTS_MAINTAINULDAGRMNT_FROMPARTYNAME" style="text-transform:uppercase" maxlength="100" />
						</div>	
					    <div class="ic-input ic-split-20">
							<ihtml:checkbox property="fromAllParties"/>
									<common:message key="uld.defaults.maintainuldagreement.allparties" scope="request"/>
						</div>
					</div>	
				</fieldset>	
			</div>
			<div class="ic-col ic-center" style="width:49%">
				<fieldset class="ic-field-set">
					<legend class="iCargoLegend">
						<common:message key="uld.defaults.maintainuldagreement.toparty"/>
					</legend>
					<div class="ic-row">
					<business:sessionBean id="partyTypes"
							moduleName="uld.defaults"
							screenID="uld.defaults.maintainuldagreement" method="get"
							attribute="partyType" />
					<div class="ic-input ic-split-20" id="fromshortcodeLable">
						<label><common:message key="uld.defaults.maintainuldagreement.partytype" scope="request"/></label>
						<ihtml:select property="partyType" componentID="CMB_ULD_DEFAULTS_MAINTAINULDAGRMNT_TOPARTYTYP">
								  <logic:present name="partyTypes">
								 <bean:define id="partyType" name="partyTypes"/>
								 <ihtml:options collection="partyType" property="fieldValue" labelProperty="fieldDescription"/>
								 </logic:present>
								 <ihtml:option value="L">ALL</ihtml:option>
							</ihtml:select>
					</div>	
					   <div class="ic-input ic-split-20 ic-mandatory">
							<label><common:message key="uld.defaults.maintainuldagreement.partycode" scope="request"/></label>
							<ihtml:text property="partyCode" componentID="TXT_ULD_DEFAULTS_MAINTAINULDAGRMNT_TOPARTYCODE" style="text-transform:uppercase" />
							<div class="lovImg">
						 <img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" name="toAirlineLov" id="toAirlineLov" alt="Airline LOV" />
						 </div>
						</div>
						<div class="ic-input ic-split-40">
						<label><common:message key="uld.defaults.maintainuldagreement.name" scope="request"/></label>
						<ihtml:text property="partyName" componentID="TXT_ULD_DEFAULTS_MAINTAINULDAGRMNT_TOPARTYNAME" style="text-transform:uppercase" maxlength="100" />
						</div>	
						 <div class="ic-input ic-split-20">
							<ihtml:checkbox property="allParties"/>
									<common:message key="uld.defaults.maintainuldagreement.allparties" scope="request"/>
						</div>
					</div>	
				</fieldset>		
			</div>
		</div>
		</div>
			<!-- Space to add party details-->	
				<div class="ic-row">
					<label><h4><common:message key="uld.defaults.maintainuldagreement.demurragedetails" scope="request"/></h4></label>
				</div>
				<fieldset class="ic-field-set">
					<div class="ic-row ic-label-33">
						<div class="ic-input ic-split-25">
							<label><common:message key="uld.defaults.maintainuldagreement.freeloanperiod" scope="request"/></label>
							<ihtml:text property="freeLoanPeriod" componentID="TXT_ULD_DEFAULTS_MAINTAINULDAGRMNT_FREELOANPERIOD" maxlength="10" style="text-align:right"/>
						</div>
						<div class="ic-input ic-split-25 ic-mandatory">
						<label><common:message key="uld.defaults.maintainuldagreement.fromdate" scope="request"/></label>
									<ibusiness:calendar  componentID="ULD_DEFAULTS_MAINTAINULDAGRMNT_CALENDAR2" property="fromDate" type="image" id="fromdate"
												  	textStyleClass="iCargoTextFieldMedium"
								value="<%=form.getFromDate()%>" title = "From Date"  />
						</div>	
						<div class="ic-input ic-split-25">
							<label><common:message key="uld.defaults.maintainuldagreement.todate" scope="request"/></label>
							<ibusiness:calendar componentID="ULD_DEFAULTS_MAINTAINULDAGRMNT_CALENDAR3" property="toDate" id="todate" type="image"
						  		textStyleClass="iCargoTextFieldMedium"
								value="<%=form.getToDate()%>" title = "To Date"  />
						</div>
										
					</div>	
					
					<div class="ic-row ic-label-33">
						<div class="ic-input ic-split-25">
							<label><common:message key="uld.defaults.maintainuldagreement.demurragerate" scope="request"/></label>
							<ihtml:text property="demurrageRate" componentID="TXT_ULD_DEFAULTS_MAINTAINULDAGRMNT_DEMURRAGERATE" maxlength="17" style="text-align:right"/>
                          <ihtml:text property="currencyCode" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_CURRENCY" maxlength="3"/>
						  <div class="lovImg">
			  				<img name="currencyLov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" />
						  </div>
						</div>
						<div class="ic-input ic-split-25">
						<label> <common:message key="uld.defaults.maintainuldagreement.tax" scope="request"/></label>
							<ihtml:text property="taxes"  value="<%= getRoundedValue(form.getTaxes())%>" componentID="TXT_ULD_DEFAULTS_MAINTAINULDAGRMNT_TAXES" maxlength="6" style="text-align:right"/>

						</div>	
						<div class="ic-input ic-split-12">
							<label><common:message key="uld.defaults.maintainuldagreement.demurragefreq" scope="request"/></label>
							<business:sessionBean id="demurrageFrequencies"
                           moduleName="uld.defaults"
				            screenID="uld.defaults.maintainuldagreement" method="get"
				            attribute="demurrageFrequency"/>
						<!--<div class="select-style">commented by A-8149 as part of icrd-249377 -->
                          <ihtml:select property="demurrageFrequency" componentID="CMB_ULD_DEFAULTS_MAINTAINULDAGRMNT_DMRGFREQ" style="text-align:right">
                          <logic:present name="demurrageFrequencies">
                          <bean:define id="frequencies" name="demurrageFrequencies"/>
                          <ihtml:options collection="frequencies" property="fieldValue" labelProperty="fieldDescription"/>
                          </logic:present>
                          </ihtml:select>
						<!--</div>-->
						</div>				
					</div>
					<div class="ic-row ic-label-15">
						<div class="ic-input ic-split-50">
							<label><common:message key="uld.defaults.maintainuldagreement.remarks" scope="request"/></label>
							<ihtml:textarea property="remarks" componentID="TXT_ULD_DEFAULTS_MAINTAINULDAGRMNT_REMARKS"  rows="2" cols="145" maxlength="100">
                          </ihtml:textarea>
						</div>
					</div>
				</fieldset>	
				<div class="ic-row">
				    <!--Added by A-8445 for IASCB-28460-->
					<div class="ic-col-40">
							<logic:present name="KEY_AGREEMENTS">
								<common:paginationTag pageURL="uld.defaults.finduldagreementdetails.do"
									name="KEY_AGREEMENTS"
									display="label"
									labelStyleClass="iCargoResultsLabel"
									lastPageNum="<%=form.getLastPageNumStr()%>" />
							</logic:present>
					</div>
					<div class="ic-col-20">
							<div class="ic-input ic-split-50">
								<label><common:message key="uld.defaults.maintainuldagreement.uldtype" scope="request"/></label>
								<ihtml:text property="uldTypeFilter" componentID="TXT_ULD_DEFAULTS_MAINTAINULDAGRMNT_ULDTYP" style="text-transform:uppercase" maxlength="3"/>
								<div class="lovImg">
									<img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" id="uldlov" alt="ULD Type LOV"/>
								</div>
							</div>
							
							<div class="ic-button-container">
								<ihtml:nbutton property="btnListFilter" componentID="BTN_ULD_DEFAULTS_MAINTAINULDAGRMNT_LIST_FILTER" accesskey="L">
									<common:message key="uld.defaults.maintainuldagreement.list" scope="request"/>
								</ihtml:nbutton>
							</div>
					</div>
					
					<div class="ic-col-40 ic-right" >
						<div class="ic-button-container paddR5">
								  <logic:present name="KEY_AGREEMENTS">
								  <common:paginationTag pageURL="javascript:submitList('lastPageNum','displayPage')"
									  name="KEY_AGREEMENTS"
									  linkStyleClass="iCargoLink"
									  disabledLinkStyleClass="iCargoLink"
									  display="pages"
									  lastPageNum="<%=form.getLastPageNumStr()%>"
									  exportToExcel="false"
									  exportTableId="uldagreementdetails"
									  exportAction="uld.defaults.finduldagreementdetails.do" pageNumberFormAttribute="displayPage" />				  
			                      </logic:present>
								  
								  <a href="#" class="iCargoLink" id="createlink" ><common:message key="uld.defaults.maintainuldagreement.create" scope="request"/></a> <!--Modified by A-7978 for ICRD-249430-->
			    	| <a href="#" id="modifylink" class="iCargoLink"><common:message key="uld.defaults.maintainuldagreement.modify" scope="request"/></a>
					| <a href="#" id="deletelink" class="iCargoLink"><common:message key="uld.defaults.maintainuldagreement.delete" scope="request"/></a> <!--Modified by A-7978 for ICRD-249430-->
					&nbsp;
				        </div>
					</div>
					<!--Added by A-8445 for IASCB-28460-->
				</div>
				<div class="ic-row ic-marg-top-5">
				<!-- Modified table height by A-7359 for ICRD-258245-->
				    <div id="div1" class="tableContainer" style="height:180px">
					<table  class="fixed-header-table" cellpadding="0" cellspacing="0" width="100%">
					<thead>
					<tr  class="iCargoTableHeaderLabel">
					  <td rowspan="2" height="10px">
					  <input type="checkbox" property="masterRowId" id="masterRowId" /></td>
					  <td rowspan="2" height="10px" width="60px" class="iCargoTableHeader">
						 <common:message key="uld.defaults.maintainuldagreement.uldtype" scope="request"/>

					  </td>
					  <td rowspan="2" height="10px" class="iCargoTableHeader">
					   <common:message key="uld.defaults.maintainuldagreement.station" scope="request"/>

					 </td>
					  <td  colspan="2"   height="15px" class="iCargoTableHeader">
						<common:message key="uld.defaults.maintainuldagreement.validity" scope="request"/>
					  </td>
					  <td rowspan="2"  height="10px" width="75px;" class="iCargoTableHeader">
					   <common:message key="uld.defaults.maintainuldagreement.freeloanperiod" scope="request"/>
					  </td>
					  <td  rowspan="2" height="10px" width="95px;" class="iCargoTableHeader">
					   <common:message key="uld.defaults.maintainuldagreement.demurragefreq" scope="request"/>
					  </td>
					  <td rowspan="2" height="10px" width="85px;" class="iCargoTableHeader">
					   <common:message key="uld.defaults.maintainuldagreement.demurragerate" scope="request"/>
					  </td>
					  <td  rowspan="2" height="10px" class="iCargoTableHeader" >
					   <common:message key="uld.defaults.maintainuldagreement.tax" scope="request"/>
					  </td>
					  <td  rowspan="2" height="10px" width="130" class="iCargoTableHeader">
					   <common:message key="uld.defaults.maintainuldagreement.currency" scope="request"/>
					  </td>
					  <td rowspan="2" height="10px" width="230px;" class="iCargoTableHeader">
					   <common:message key="uld.defaults.maintainuldagreement.remarks" scope="request"/>
					  </td>
					</tr>
					<tr >

					   <td class="iCargoTableHeader" width="75px;"><common:message key="uld.defaults.maintainuldagreement.from" scope="request"/></td>
					   <td class="iCargoTableHeader" width="75px;"><common:message key="uld.defaults.maintainuldagreement.to" scope="request"/></td>

					</tr>

			        </thead>

					<tbody>
					
			           <logic:present  name="KEY_AGREEMENTS" >
			           <bean:define id="agreementDetailVO" name="KEY_AGREEMENTS"/>
						<logic:iterate id="agreementDtlsVO" name="agreementDetailVO" indexId="nIndex" type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO">
						<logic:present name="agreementDtlsVO" property="operationFlag">
			           <bean:define id="opFlag" name="agreementDtlsVO" property="operationFlag"/>
			           </logic:present>
			           <logic:notPresent name="agreementDtlsVO" property="operationFlag">
			           <bean:define id="opFlag"  value="NA"/>
			           </logic:notPresent>

			           <logic:notMatch name="opFlag" value="D">
						<tr>
						<td    class="iCargoTableDataTd" style="text-align:center">
						  
							<ihtml:checkbox property="selectedChecks"  value="<%=nIndex.toString()%>" />
						  
						</td>
						  <td   class="iCargoTableDataTd">


						   <bean:write name="agreementDtlsVO" property="uldTypeCode"/>
						  

						  </td>
						  <td class="iCargoTableDataTd">
						  <bean:write name="agreementDtlsVO" property="station"/>
						

						  </td>
						  <td   class="iCargoTableDataTd">
						  <logic:present name="agreementDtlsVO" property="agreementFromDate">

						  <%=agreementDtlsVO.getAgreementFromDate().toDisplayDateOnlyFormat()%>
						  </logic:present>
						  </td>
						  <td   class="iCargoTableDataTd">


						  <%String toDate=(agreementDtlsVO.getAgreementToDate()==null?"":agreementDtlsVO.getAgreementToDate().toDisplayDateOnlyFormat());%>
						  <%=toDate%>
						   </td>
							<td   class="iCargoTableDataTd" style="text-align:right">
						  <bean:write name="agreementDtlsVO"  format="#####" property="freeLoanPeriod"/>
						  </center>
						  </td>
						   <td   class="iCargoTableDataTd" >

						  <logic:present name="agreementDtlsVO" property="demurrageFrequency">
						  <bean:define id="frequency" name="agreementDtlsVO" property="demurrageFrequency"/>
						  <logic:present name="demurrageFrequencies">
						  <bean:define id="oneTimeFrequencies" name="demurrageFrequencies"/>

						  <logic:iterate id="oneTimeFrequency" name="oneTimeFrequencies">
						  <bean:define id="onetimevo" name="oneTimeFrequency" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"/>

						  <%if(onetimevo.getFieldValue().equals(frequency)){%>
						  <bean:write name="onetimevo" property="fieldDescription"/>

						  <%}%>
						  </logic:iterate>
						  </logic:present>
						  </logic:present>
						  </td>
						  <td class="iCargoTableDataTd" style="text-align:right">
						  <bean:write name="agreementDtlsVO"  property="demurrageRate" localeFormat="true" /></td>
						  <td   class="iCargoTableDataTd" style="text-align:right">
						  <logic:equal name="agreementDtlsVO" property="tax" value="0">
							<!-- 0.0 -->
							<bean:define id="decimalTax" value="0.0"/>
							<bean:write name="decimalTax" localeformat="true"/>
							<bean:write name="agreementDtlsVO" property="tax" localeFormat="true"/>
						   </logic:equal>
						   <logic:notEqual name="agreementDtlsVO" property="tax" value="0">
							<bean:write name="agreementDtlsVO" property="tax" localeFormat="true" />
						   </logic:notEqual>
						 </td>
						  <td   class="iCargoTableDataTd"  >


						  <bean:write name="agreementDtlsVO" property="currency"/></td>
						  <td   class="iCargoTableDataTd">
						  <bean:write name="agreementDtlsVO" property="remark"/></td>
						</tr>
						</logic:notMatch>
						
						</logic:iterate>

						</logic:present>


					</tbody>
										

					</table>
					</div>
				</div>
			</div>
			<div class="ic-foot-container">
				<div class="ic-button-container paddR5">
					<ihtml:nbutton property="btnPrint" componentID="BTN_ULD_DEFAULTS_MAINTAINULDAGRMNT_PRINT" accesskey="P">
					  <common:message key="uld.defaults.maintainuldagreement.print" scope="request"/>
					  </ihtml:nbutton>
                      <ihtml:nbutton property="btnSave" componentID="BTN_ULD_DEFAULTS_MAINTAINULDAGRMNT_SAVE" accesskey="S">
                      <common:message key="uld.defaults.maintainuldagreement.save" scope="request"/>
                      </ihtml:nbutton>
					   <ihtml:nbutton property="btnClose" componentID="BTN_ULD_DEFAULTS_MAINTAINULDAGRMNT_CLOSE" accesskey="O">
					   <common:message key="uld.defaults.maintainuldagreement.close" scope="request"/></ihtml:nbutton>
				</div>
			</div>
		</div>
			</ihtml:form>
			<%!
			
				private String getRoundedValue(double value){
			
					return new BigDecimal(value).setScale(2,RoundingMode.HALF_UP).toString();
				}
			%>
		</div>




</body>
</html:html>

