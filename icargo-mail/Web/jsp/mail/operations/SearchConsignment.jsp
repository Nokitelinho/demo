<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name 	 :  MailTracking
* File Name     	 :  SearchConsignment.jsp
* Date          	 :  23-August-2007
* Author(s)     	 :  Tarun A V

*************************************************************************/
 --%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.business.mail.operations.vo.MailbagVO"%>
<%@ page import="com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO"%>
<%@ page import="com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO"%>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>
<html:html locale="true">

<head>

		<%@ include file="/jsp/includes/customcss.jsp" %>

<title>
	<logic:equal name="SearchConsignmentForm" property="screenMode" value="MAIN">
		<common:message bundle="searchconsignmentResources" key="mailtracking.defaults.carditenquiry.listcardits.lbl.pagetitle" />
	</logic:equal>
	<logic:equal name="SearchConsignmentForm" property="screenMode" value="POPUP">
		<common:message bundle="searchconsignmentResources" key="mailtracking.defaults.carditenquiry.consignmentenquiry.lbl.pagetitle" />
	</logic:equal>
	<logic:equal name="SearchConsignmentForm" property="screenMode" value="POPUP_CARDIT">
		<common:message bundle="searchconsignmentResources" key="mailtracking.defaults.carditenquiry.lbl.carditenquiry.pagetitle" />
	</logic:equal>
</title>
	<logic:equal name="SearchConsignmentForm" property="screenMode" value="MAIN">
	   <meta name="decorator" content="mainpanel"/>
	</logic:equal>

	<logic:equal name="SearchConsignmentForm" property="screenMode" value="POPUP">
       	  <bean:define id="popup" value="true" />
	      <meta name="decorator" content="popup_panel"/>
  	</logic:equal>
  	<logic:equal name="SearchConsignmentForm" property="screenMode" value="POPUP_CARDIT">
       	  <bean:define id="popup" value="true" />
	      <meta name="decorator" content="popup_panel"/>
  	</logic:equal>
	<common:include type="script" src="/js/mail/operations/SearchConsignment_Script.jsp" />
</head>
<body>






	<logic:equal name="SearchConsignmentForm" property="screenMode" value="MAIN">
	<div class="iCargoContent ic-masterbg" style="height:100%;overflow:hidden;" >
	</logic:equal>
	<logic:equal name="SearchConsignmentForm" property="screenMode" value="POPUP">
		<div class="iCargoContent ic-masterbg" style="height:100%;overflow:hidden;" >
	</logic:equal>
	<logic:equal name="SearchConsignmentForm" property="screenMode" value="POPUP_CARDIT">
		<div class="iCargoContent ic-masterbg" style="height:100%;overflow:hidden;" >
	</logic:equal>
	<ihtml:form action="/mailtracking.defaults.mailsearch.screenload.do" styleClass="ic-main-form">
		<ihtml:hidden property="duplicateFlightStatus"/>
		<ihtml:hidden property="lastPageNum" />
		<ihtml:hidden property="displayPage" />
		<ihtml:hidden property="screenMode" />
		<ihtml:hidden property="disableButton" />
		<ihtml:hidden property="lookupClose" />
		<ihtml:hidden property="density" />
		<ihtml:hidden property="select" />
		<ihtml:hidden property="fromButton" />
		<ihtml:hidden property="awbNumber" /><!--added by A-7371 as part of ICRD-228233-->
		<ihtml:hidden property="mailCount" />
		<ihtml:hidden property="currentDialogOption" />
<ihtml:hidden property="currentDialogId" />
<ihtml:hidden property="notAccepted" />
		<input type=hidden name="mySearchEnabled" />

		<bean:define id="form"
			name="SearchConsignmentForm"
			type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchConsignmentForm"
			toScope="page"
			scope="request"/>

		<business:sessionBean id="oneTimeValues" moduleName="mail.operations" screenID="mailtracking.defaults.searchconsignment" method="get" attribute="oneTimeVOs" />
		<business:sessionBean id="carditEnquiryVO" moduleName="mail.operations" screenID="mailtracking.defaults.searchconsignment" method="get" attribute="carditEnquiryVO" />
		<business:sessionBean id="mailClassCollection" moduleName="mail.operations" screenID="mailtracking.defaults.searchconsignment" method="get" attribute="mailbagVOsCollection" />
		<!--Added by A-8061 for ICRD-233692 starts-->
		<business:sessionBean id="totalPcs" moduleName="mail.operations" screenID="mailtracking.defaults.searchconsignment" method="get" attribute="totalPcs" />
		<business:sessionBean id="totalWeight" moduleName="mail.operations" screenID="mailtracking.defaults.searchconsignment" method="get" attribute="totalWeight" />
		<!--Added by A-8061 for ICRD-233692 ends-->
		<div class="ic-content-main">
			<span class="ic-page-title ic-display-none">
				<logic:equal name="SearchConsignmentForm" property="screenMode" value="MAIN">
				<common:message bundle="searchconsignmentResources" key="mailtracking.defaults.carditenquiry.listcardits.lbl.pagetitle" />
				</logic:equal>
				<logic:equal name="SearchConsignmentForm" property="screenMode" value="POPUP">
				<common:message bundle="searchconsignmentResources" key="mailtracking.defaults.carditenquiry.consignmentenquiry.lbl.pagetitle" />
				</logic:equal>
				<logic:equal name="SearchConsignmentForm" property="screenMode" value="POPUP_CARDIT">
				<common:message bundle="searchconsignmentResources" key="mailtracking.defaults.carditenquiry.lbl.carditenquiry.pagetitle" />
				</logic:equal>
			</span>
			<div class="ic-head-container">
				<div class="ic-filter-panel">
			<div class="ic-row">
						<h4><common:message key="mailtracking.defaults.carditenquiry.lbl.search" /></h4>
						</div>
					<div class="ic-row">

						<div class="ic-row">
						<div class="ic-input-container ic-border">
								<div class="ic-input ic-split-22">
										<label>
											<common:message key="mailtracking.defaults.carditenquiry.lbl.mailbagid" />
										</label>
										<ihtml:text property="mailbagId" maxlength="29"  componentID="CMP_MailTracking_Defaults_SearchCosignment_MailbagId" style="width:210px"/> <!--modified. A-8164 for ICRD 257671-->	
									</div>						
								<div class="ic-input ic-split-9">
										<label>
											<common:message key="mailtracking.defaults.carditenquiry.lbl.originoe" />
										</label>
										<ihtml:text property="ooe" maxlength="6"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_originOE" />
										<div class="lovImg">
										<img id="originOELov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" value="org"/>
									</div>
									</div>
									<div class="ic-input ic-split-10">
										<label>
											<common:message key="mailtracking.defaults.carditenquiry.lbl.destinationoe" />
										</label>
											<ihtml:text property="doe" maxlength="6"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_destnOE" />
											<div class="lovImg">
											<img id="destnOELov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" value="dstn"/>
											</div>
									</div>

									<div class="ic-input ic-split-12">
										<label>
											<common:message key="mailtracking.defaults.carditenquiry.lbl.cat" />
										</label>
										<ihtml:select property="mailCategoryCode" componentID="CMP_MailTracking_Defaults_CarditEnquiry_category" style="width:140px">
										<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
										<logic:present name="oneTimeValues">
										<logic:iterate id="oneTimeValue" name="oneTimeValues">
										<bean:define id="parameterCode" name="oneTimeValue" property="key" />
										<logic:equal name="parameterCode" value="mailtracking.defaults.mailcategory">
										<bean:define id="parameterValues" name="oneTimeValue" property="value" />
										<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue">
										<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
											<html:option value="<%=(String)fieldValue%>">
												<%=(String)fieldDescription%>
											</html:option>
										</logic:present>
										</logic:iterate>
										</logic:equal>
										</logic:iterate>
										</logic:present>
										</ihtml:select>
									</div>
									<div class="ic-input ic-split-5">
										<label>
											<common:message key="mailtracking.defaults.carditenquiry.lbl.mailsubClass" />
										</label>
										<logic:notPresent name="form" property="mailSubclass">
										<ihtml:text property="mailSubclass" maxlength="2"  componentID="TXT_MailTracking_Defaults_CarditEnquiry_mailSubClass" value="" style="width:25px" />
										</logic:notPresent>
										<logic:present name="form" property="mailSubclass">
										<bean:define id="mailSubclass" name="form" property="mailSubclass" toScope="page"/>
										<ihtml:text property="mailSubclass" maxlength="2"  componentID="TXT_MailTracking_Defaults_CarditEnquiry_mailSubClass" value="<%=(String)mailSubclass%>" style="width:25px" />
										</logic:present>
										<div class="lovImg">
										<img id="subClassLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" value="subclass"/>
									</div>
									</div>
									<div class="ic-input ic-split-7">
										<label>
											<common:message key="mailtracking.defaults.carditenquiry.lbl.year" />
										</label>
										<ihtml:text property="year" maxlength="1"  componentID="CMP_MailTracking_Defaults_SearchCosignment_Year" style="width:25px"/>
									</div>
									<div class="ic-input ic-split-12">
										<label>
											<common:message key="mailtracking.defaults.carditenquiry.lbl.dsn" />
										</label>
										<ihtml:text property="despatchSerialNumber" maxlength="4"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_Dsn" />
									</div>
									<div class="ic-input ic-split-9">
										<label>
											<common:message key="mailtracking.defaults.carditenquiry.lbl.rsn" />
										</label>
										<ihtml:text property="receptacleSerialNumber" maxlength="3"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_Rsn" />
									</div>
						</div>
						</div>
						<div class="ic-row marginT5">

							<div class="ic-col-40">
							<div class="ic-input-container ic-border">
								<div class="ic-row">
									<div class="ic-input ic-split-35">
										<label>
											 <common:message key="mailtracking.defaults.carditenquiry.tooltip.consignmentNo" />
										</label>
										 <ihtml:text property="consignmentDocument" maxlength="35" componentID="CMP_MailTracking_Defaults_CarditEnquiry_ConsignmentNo" />
									</div>
									<div class="ic-input ic-split-45">
									<logic:equal name="SearchConsignmentForm" property="screenMode" value="MAIN">
										<label>
											<common:message key="mailtracking.defaults.carditenquiry.tooltip.rdt" /> 
										</label>
									<ibusiness:calendar property="reqDeliveryDate" type="image" id="reqDeliveryDate"
									value="<%=form.getReqDeliveryDate()%>"
									componentID="CMP_MailTracking_Defaults_CarditEnquiry_ReqDlvDate" onblur="autoFillDate(this)"/>											
											<ibusiness:releasetimer id="reqDeliveryTime" componentID="CMP_MailTracking_Defaults_CarditEnquiry_ReqDlvTime" property="reqDeliveryTime"
												type="asTimeComponent"
												value="<%=form.getReqDeliveryTime()%>"/>	
									</logic:equal>
									</div>
								
									<div class="ic-input">
										<label>
											<common:message key="mailtracking.defaults.mailenquiry.tooltip.PACode" />
										</label>
										<ihtml:text property="pao" maxlength="5" componentID="CMP_MailTracking_Defaults_SearchCosignment_PACode" />
										<div class="lovImg">
										<img id="PALov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onClick="displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.pao.value,'PA','1','pao','',0)">
									</div>
								</div>
								</div>
								<div class="ic-row">
									<div class="ic-input  ic-split-35">
										<label>
											 <common:message key="mailtracking.defaults.carditenquiry.tooltip.fromdate" />
										</label>
										 <ibusiness:calendar property="fromDate" type="image" id="fromDate"
							 value="<%=form.getFromDate()%>"
							componentID="CMP_MailTracking_Defaults_CarditEnquiry_FromDate" onblur="autoFillDate(this)"/>
									</div>
									<div class="ic-input   ic-split-25">
										<label>
											<common:message key="mailtracking.defaults.carditenquiry.tooltip.todate" />
										</label>
										<ibusiness:calendar property="toDate" type="image" id="toDate"
							value="<%=form.getToDate()%>"
							componentID="CMP_MailTracking_Defaults_CarditEnquiry_ToDate" onblur="autoFillDate(this)"/>
									</div>
								<!--a8061 added for ICRD-82434 starts-->	
								<div class="ic-input ic-split-25">
										<label>
											<common:message key="mailtracking.defaults.carditenquiry.tooltip.consignmentdate" />
										</label>
										   <ibusiness:calendar property="consignmentDate" type="image" id="incalender4"
							 value="<%=form.getConsignmentDate()%>"
							componentID="CMP_MailTracking_Defaults_CarditEnquiry_ConsignmentDate" onblur="autoFillDate(this)"/>	
							  <!--a8061 added for ICRD-82434 ends-->
								</div>
								</div>
							<!--a7531-->
							
								<div class="ic-row">
								<div class="ic-input ic-split-18">
										<label>

										</label>

							</div>
								<div class="ic-input ic-split-43">
								<!--Modified by A-7938 for ICRD-243964-->
                                 <!-- <label>
                                    <common:message key="mailtracking.defaults.carditenquiry.awbnumber" />
                                  </label>-->
								  <ibusiness:awb id="awb" awpProperty="shipmentPrefix" awbProperty="documentNumber" isCheckDigitMod="false"   componentID="CMP_MailTracking_Defaults_CarditEnquiry_AWB_No" />
								</div>
								<!--a8061 added for ICRD-82434 starts-->
								<div class="ic-input ic-split-26 marginT15">
								
							
								<ihtml:checkbox property="isPendingResditChecked" componentID="CMP_MailTracking_Defaults_CarditEnquiry_isPendingResditChecked" />
								
								<common:message key="mailtracking.defaults.carditenquiry.lbl.pendingResditEvent" />
									
								</div>
								
								<div class="ic-input ic-split-20">
										<label>
											<common:message key="mailtracking.defaults.carditenquiry.tooltip.awbattached" />
										</label>
										 <ihtml:select property="isAwbAttached" componentID="CMP_MailTracking_Defaults_CarditEnquiry_AWBAttached" onchange="togglePanel(1,this);"  >
										<html:option value="--Select--"></html:option>
														<html:option value="Yes"></html:option>
														<html:option value="No"></html:option>
														
												
											
										
										</ihtml:select>	 
								</div>
							 <!--a8061 added for ICRD-82434 ends-->
								</div>
							</div>
							</div>

							<div class="ic-col-50">
							<div class="ic-input-container ic-border">
								<div class="ic-row">
									<div class="ic-input ic-split-20">
										<label>
											 <common:message key="mailtracking.defaults.carditenquiry.tooltip.flightNumber" />
										</label>
										 <ibusiness:flightnumber
												 carrierCodeProperty="carrierCode"
												 id="flight"
												 flightCodeProperty="flightNumber"
												 carriercodevalue="<%=form.getCarrierCode()%>"
												 flightcodevalue="<%=form.getFlightNumber()%>"
												 carrierCodeMaxlength="3"
												 flightCodeMaxlength="5"
												 componentID="CMP_MailTracking_Defaults_SearchCosignment_FltNo"/>
												 <ihtml:nbutton property="flightlov" value="" styleClass="iCargoLovButton"/>
									</div>
									<div class="ic-input ic-split-20">
										<label>
											<common:message key="mailtracking.defaults.carditenquiry.tooltip.flightdate" />
										</label>
										<ibusiness:calendar property="flightDate" type="image" id="incalender3"
													value="<%=form.getFlightDate()%>" componentID="CMP_MailTracking_Defaults_SearchCosignment_FlightDate" onblur="autoFillDate(this)"/>
									</div>
								
									<div class="ic-input ic-split-20 marginL5">
										<label>
											<common:message key="mailtracking.defaults.carditenquiry.tooltip.departureport" />
										</label>
										 <ihtml:text property="pol" maxlength="4" componentID="CMP_MailTracking_Defaults_SearchCosignment_DeparturePort" />
										 <div class="lovImg">
											<img id="portLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" value="port"/>
									</div>
									</div>
									<div class="ic-input ic-split-18">
										<label>
											<common:message key="mailtracking.defaults.carditenquiry.tooltip.uldnumber" />
										</label>
										 <ibusiness:uld id="uldNumber" uldProperty="uldNumber" style="text-transform:uppercase;" componentID="CMP_MailTracking_Defaults_SearchCosignment_Uld_Number"/>
									</div>
									<div class="ic-input ic-split-20">
										<label>
											<common:message key="mailtracking.defaults.carditenquiry.tooltip.status" />
										</label>
										 <ihtml:select componentID="CMP_MailTracking_Defaults_SearchCosignment_Status" property="mailStatus">
													<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
													<logic:present name="oneTimeValues">
														<logic:iterate id="oneTimeValue" name="oneTimeValues">
														<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
														<logic:equal name="parameterCode" value="mailtracking.defaults.mailstatus">
														<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
														<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
														<logic:present name="parameterValue" property="fieldValue">

															<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
															<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>

															<logic:equal name="parameterValue" property="fieldValue"  value="CAP">
															<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>">
															<%=String.valueOf(fieldDescription)%>
															</ihtml:option>
															</logic:equal>

															<logic:equal name="parameterValue" property="fieldValue"  value="ACP">
															<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>">
															<%=String.valueOf(fieldDescription)%>
															</ihtml:option>
															</logic:equal>

														</logic:present>
														</logic:iterate>
														</logic:equal>
														</logic:iterate>
													</logic:present>
												</ihtml:select>
									</div>
									<!--a8061 added for ICRD-82434 starts-->
									<div class="ic-input ic-split-20">
										<label>
											<common:message key="mailtracking.defaults.carditenquiry.tooltip.flighttype" />
										</label>
										 <ihtml:select property="flightType" componentID="CMP_MailTracking_Defaults_CarditEnquiry_FlightType" onchange="togglePanel(1,this);"  >
										<logic:present name="oneTimeValues">
											<logic:iterate id="oneTimeValue" name="oneTimeValues">
											<bean:define id="parameterCode" name="oneTimeValue" property="key" />
												<logic:equal name="parameterCode" value="mailtracking.defaults.carditenquiry.flighttype">
												<bean:define id="parameterValues" name="oneTimeValue" property="value" />
													<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
														<logic:present name="parameterValue">
														<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
														<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
														<html:option value="<%=(String)fieldValue%>">
														<%=(String)fieldDescription%>
														</html:option>
														</logic:present>
													</logic:iterate>
												</logic:equal>
											</logic:iterate>
										</logic:present>
										</ihtml:select>	 
								</div>
									</div>	
									 <!--a8061 added for ICRD-82434 ends-->
								</div>
							</div>
						
							<div class="ic-button-container">
<ihtml:nbutton property="btList"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_btList" accesskey="L">
						<common:message key="mailtracking.defaults.carditenquiry.btn.list" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btClear"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_btClear" accesskey="C">
						<common:message key="mailtracking.defaults.carditenquiry.btn.clear" />
					</ihtml:nbutton>
						</div>
						</div>


					</div>
				</div>
			</div>

			<div class="ic-main-container">
			<a class="panel upArrow"  collapseFilter="true"  collapseFilterCallbackFun="callbackSearchConsignment"  href="#"> </a> <!--Added by A-7929 for ICRD - 224586  -->
				<div class="ic-row">
				<h4><common:message key="mailtracking.defaults.carditenquiry.lbl.mailbagdetails" /></h4>
				</div>
				<div class="ic-row ">
					<div >
						<div class="ic-row">
							<%String lstPgNo = "";%>

							<div class="ic-col-80"> <!--Modified by A-7929 for ICRD - 224586  -->
								<logic:present name="SearchConsignmentForm" property="lastPageNum">
								<bean:define id="lastPg" name="SearchConsignmentForm" property="lastPageNum" scope="request"  toScope="page" />
								<%
								lstPgNo = (String) lastPg;
								%>
								</logic:present>
								<logic:present name="mailClassCollection" >
								<bean:define id="pageObj" name="mailClassCollection"  />
								<common:paginationTag pageURL="mailtracking.defaults.mailsearch.listload.do"
								name="pageObj"
								display="label"
								labelStyleClass="iCargoResultsLabel"
								lastPageNum="<%=lstPgNo%>" />
								</logic:present>
								<logic:notPresent name="mailClassCollection" >
								&nbsp;
								</logic:notPresent>
							</div>
							<div class="ic-col-20" style="text-align:right"> <!--Modified by A-7929 for ICRD - 224586  -->
								<logic:present name="mailClassCollection" >
								<bean:define id="pageObj1" name="mailClassCollection"  />
								<common:paginationTag
								linkStyleClass="iCargoLink"
								disabledLinkStyleClass="iCargoLink"
								pageURL="javascript:submitPage('lastPageNum','displayPage')"
								name="pageObj1"
								display="pages"
								lastPageNum="<%=lstPgNo%>"/>
								</logic:present>
								<logic:notPresent name="mailClassCollection" >
									&nbsp;
								</logic:notPresent>
							</div>
						</div>
						<div class="ic-row">
							<logic:equal name="SearchConsignmentForm" property="screenMode" value="MAIN">
								<div class="tableContainer" id="div1"   style="height:397px">  <!--Modified by A-7929 for ICRD - 224586  -->
							</logic:equal>
							<logic:equal name="SearchConsignmentForm" property="screenMode" value="POPUP">
								<div class="tableContainer" id="div2"   style="height:269px">  <!--Modified by A-7929 for ICRD - 249966  -->
							</logic:equal>
							<logic:equal name="SearchConsignmentForm" property="screenMode" value="POPUP_CARDIT">
								<div class="tableContainer" id="div3"   style="height:595px">
							</logic:equal>
							<table id="searchconsignmentdetails" class="fixed-header-table" >
							<thead>
							<tr>
							<td class="iCargoTableHeaderLabel" width="3%">
								<input type="checkbox" name="parentCheckBox" onclick="updateHeaderCheckBox(this.form,this,this.form.selectMail);" />
							</td>
							<!--a8061 added for ICRD-82434 -->
							<td class="iCargoTableHeaderLabel" width="3%">
								
							</td>
							
                             <!--  Modified by A-7929 for ICRD-240271  -->
							<!--  modified width by A-8149 for ICRD-261108 --><td class="iCargoTableHeaderLabel" width="20%"><common:message key="mailtracking.defaults.carditenquiry.lbl.mailbagid" /></td>
							<td class="iCargoTableHeaderLabel" width="5%"><common:message key="mailtracking.defaults.mailenquiry.lbl.ooe" /></td>
							<td class="iCargoTableHeaderLabel" width="5%"><common:message key="mailtracking.defaults.mailenquiry.lbl.doe" /></td>
							<td class="iCargoTableHeaderLabel" width="4%"><common:message key="mailtracking.defaults.mailenquiry.lbl.cat" /></td>
							<td class="iCargoTableHeaderLabel" width="4%"><common:message key="mailtracking.defaults.mailenquiry.lbl.sc" /></td>
							<td class="iCargoTableHeaderLabel" width="3%"><common:message key="mailtracking.defaults.mailenquiry.lbl.yr" /></td>
							<td class="iCargoTableHeaderLabel" width="3%"><common:message key="mailtracking.defaults.mailenquiry.lbl.dsn" /><span></span></td>
							<!--  modified width by A-8149 for ICRD-261108 --><td class="iCargoTableHeaderLabel" width="4%"><common:message key="mailtracking.defaults.mailenquiry.lbl.rsn" /><span></span></td>
							<td class="iCargoTableHeaderLabel" width="3%"><common:message key="mailtracking.defaults.mailenquiry.lbl.hni" /></td>
							<td class="iCargoTableHeaderLabel" width="3%"><common:message key="mailtracking.defaults.mailenquiry.lbl.ri" /></td>
							<logic:equal name="SearchConsignmentForm" property="screenMode" value="POPUP">
							<td class="iCargoTableHeaderLabel" width="10%"><common:message key="mailtracking.defaults.mailenquiry.lbl.acceptedBags" /><span></span></td>
							</logic:equal>
							<logic:equal name="SearchConsignmentForm" property="screenMode" value="MAIN">
							    <td class="iCargoTableHeaderLabel" width="5%"><common:message key="mailtracking.defaults.mailenquiry.lbl.flightnumber" /><span></span></td>
								<!--  modified width by A-8149 for ICRD-261108 --><td class="iCargoTableHeaderLabel" width="7%"><common:message key="mailtracking.defaults.mailenquiry.lbl.flightdate" /><span></span></td>
							</logic:equal>
							<!--  modified width by A-8149 for ICRD-261108 --><td class="iCargoTableHeaderLabel" width="4%"><common:message key="mailtracking.defaults.mailenquiry.lbl.wt" /></td>
							<!--  modified width by A-8149 for ICRD-261108 --><td class="iCargoTableHeaderLabel" width="7%"><common:message key="mailtracking.defaults.mailenquiry.lbl.ConsignmentNo" /><span></span></td>
							<td class="iCargoTableHeaderLabel" width="7%"><common:message key="mailtracking.defaults.mailenquiry.lbl.ConsignmentDate" /></td>  
							 <!--Added by a7531-->
							<td class="iCargoTableHeaderLabel" width="8%"><common:message key="mailtracking.defaults.carditenquiry.awbnumber" /><span></span></td>

							<logic:equal name="SearchConsignmentForm" property="screenMode" value="MAIN">
							<td class="iCargoTableHeaderLabel" width="6%"><common:message key="mailtracking.defaults.carditenquiry.tooltip.rdt" /></td>
							</logic:equal>
							   <!--Modified by A-7929 for ICRD - 249966  -->
							<td class="iCargoTableHeaderLabel" width="6%"><common:message key="mailtracking.defaults.carditenquiry.tooltip.uldnumber"/> </td>        
							<logic:equal name="SearchConsignmentForm" property="screenMode" value="POPUP">
							<td class="iCargoTableHeaderLabel" width="5%"> <common:message key="mailtracking.defaults.carditenquiry.lbl.inlist"/> </td>
							</logic:equal>
							<!--  modified width by A-8149 for ICRD-261108 --><td class="iCargoTableHeaderLabel" width="5%"> <common:message key="mailtracking.defaults.carditenquiry.lbl.accepted"/> </td>
							</tr>
							</thead>
							<tbody>
								<logic:present name="mailClassCollection" >
								<logic:iterate id="consignmentDocumentVo" name="mailClassCollection" indexId="index" type="MailbagVO">

		                  	    <tr>
			              			<td class="iCargoTableDataTd ic-center">
						  				 	<input type="checkbox" name="selectMail" value="<%=index%>" onclick="singleSelectCb(this.form,'<%=index%>','primaryKey');" />
			              			</td>
			              		<logic:present name="consignmentDocumentVo" property="paCode">
									<bean:define id="poacode" name="consignmentDocumentVo" property="paCode" />
									<%String pacode=poacode.toString();%>
									<ihtml:hidden property="filterPACode" value="<%=pacode%>"/>
			              		</logic:present>
								 <!--a8061 added for ICRD-82434 starts-->
								<td class="iCargoTableDataTd ic-center">
								<img align="center" id="resditEvent_link_<%=String.valueOf(index)%>" src="<%= request.getContextPath()%>/images/info.gif" width="16" height="16"  name="resditEvent_link" onclick="prepareAttributes(event,this,'resditEvent_','resditEventDetails',320,120)"/> 
								 <div style="display:none" id="resditEvent_<%=String.valueOf(index)%>" name="resditEventDetails" tabindex="0">
								
								<div class="ic-row ">								
								<b><common:message key="mailtracking.defaults.carditenquiry.lbl.resditeventheader" /></b>
								</div>
								<div class="ic-row ">
								<b><common:message key="mailtracking.defaults.carditenquiry.lbl.mailbagid" />:</b>	
								<bean:write name="consignmentDocumentVo" property="mailbagId" />								
								</div>	
								<div class="ic-row ">
								<common:message key="mailtracking.defaults.carditenquiry.lbl.consignmentdocnumber" />
								<bean:write name="consignmentDocumentVo" property="consignmentNumber" />
								</div>

									<table  class="fixed-header-table">
										<thead>
											<tr>
												<td class="iCargoTableHeaderLabel"  style="text-align:left"><b><common:message key="mailtracking.defaults.carditenquiry.lbl.date" /></b></td>
												<td class="iCargoTableHeaderLabel"  style="text-align:left"><b><common:message key="mailtracking.defaults.carditenquiry.lbl.resditevent" /></b></td>
												<td class="iCargoTableHeaderLabel"  style="text-align:left"><b><common:message key="mailtracking.defaults.carditenquiry.lbl.resditstatus" /></b></td>
											</tr>
										</thead>
										<tbody>
									
									<logic:present name="consignmentDocumentVo" property="mailbagHistories">
									<bean:define id="resditEvents" name="consignmentDocumentVo" property="mailbagHistories"  scope="page" toScope="page"/>			
									<logic:iterate id="resditEvent" type="com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO" name="resditEvents" indexId="rowCount">
											<tr>
												<td class="iCargoTableDataTd" style="text-align:left;height:5%"><%=resditEvent.getMessageTime().toDisplayFormat("dd-MMM-yyyy")%></td>
												<td class="iCargoTableDataTd" style="text-align:left;height:5%">
													<logic:present name="resditEvent" property="eventCode">			
													<%String shipmentStatus=resditEvent.getEventCode();%>												
													<logic:present name="oneTimeValues">
													<logic:iterate id="oneTimeValue" name="oneTimeValues">
													<bean:define id="parameterCode" name="oneTimeValue" property="key" />
													<logic:equal name="parameterCode" value="mailtracking.defaults.mailstatus">
													<bean:define id="parameterValues" name="oneTimeValue" property="value" />
														<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
														<logic:present name="parameterValue" property="fieldValue">
														<bean:define id="fieldValue" name="parameterValue" property="fieldValue" />
														<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription" />
														<%if(shipmentStatus.equals(String.valueOf(fieldValue).toUpperCase())){%>
														<%String shipmentStatusToDisp=String.valueOf(fieldDescription);%>
														<%=shipmentStatusToDisp%>																	
														<%}%>
														</logic:present>
														</logic:iterate>
													</logic:equal>
													</logic:iterate>
													</logic:present>
													</logic:present>
												</td>
												<td class="iCargoTableDataTd" style="text-align:left;height:5%"><bean:write name="resditEvent" property="processedStatus" /></td>
											</tr>
									</logic:iterate>
									</logic:present>	
										</tbody>
									</table>
								</div>
								</td>
								<!--a8061 added for ICRD-82434 ends-->
								<td class="iCargoTableDataTd"><bean:write name="consignmentDocumentVo" property="mailbagId" />
								<ihtml:hidden property="mailbagId" value="<%=consignmentDocumentVo.getMailbagId()%>"/>
								</td>
			              		<td class="iCargoTableDataTd"><bean:write name="consignmentDocumentVo" property="ooe" />
								
			              		<td class="iCargoTableDataTd"><bean:write name="consignmentDocumentVo" property="doe" /></td>
			              		<td class="iCargoTableDataTd"><bean:write name="consignmentDocumentVo" property="mailCategoryCode" /></td>
			              		<td class="iCargoTableDataTd"><bean:write name="consignmentDocumentVo" property="mailSubclass" /></td>
								<td class="iCargoTableDataTd"><bean:write name="consignmentDocumentVo" property="year" format="####"/></td>
								<td class="iCargoTableDataTd"><bean:write name="consignmentDocumentVo" property="despatchSerialNumber" /></td>
			              		<td class="iCargoTableDataTd"><bean:write name="consignmentDocumentVo" property="receptacleSerialNumber" />
			                  	<logic:present name="consignmentDocumentVo" property="receptacleSerialNumber">
			                  		<ihtml:hidden property="despatchChk" value="Y"/>
							    </logic:present>
							    <logic:notPresent name="consignmentDocumentVo" property="receptacleSerialNumber">
			                  		<ihtml:hidden property="despatchChk" value="N"/>
							    </logic:notPresent>
						   		</td>
			              		<td class="iCargoTableDataTd"><bean:write name="consignmentDocumentVo" property="highestNumberedReceptacle" /></td>
			              		<td class="iCargoTableDataTd"><bean:write name="consignmentDocumentVo" property="registeredOrInsuredIndicator" /></td>
			              			<logic:equal name="SearchConsignmentForm" property="screenMode" value="POPUP">
			              				<td class="iCargoTableDataTd"><bean:write name="consignmentDocumentVo" property="acceptedBags" /></td>
			              			</logic:equal>
								<logic:equal name="SearchConsignmentForm" property="screenMode" value="MAIN">
			              		<td class="iCargoTableDataTd">
									<logic:present name="consignmentDocumentVo" property="carrierCode">
									<bean:define id="carrierCode" name="consignmentDocumentVo"
										property="carrierCode" toScope="page" />
									<bean:define id="flightNumber" name="consignmentDocumentVo"
										property="flightNumber" toScope="page" />
										<%
											if("-1".equals(flightNumber))
												flightNumber = "";
										%>
									<%=carrierCode%><%=flightNumber%>
									</logic:present>
									<logic:notPresent name="consignmentDocumentVo" property="carrierCode">
										&nbsp;
									</logic:notPresent>
								</td>
								<td class="iCargoTableDataTd">
									<logic:present name="consignmentDocumentVo" property="flightDate">
										<bean:define id="flightDate" name="consignmentDocumentVo" property="flightDate" toScope="page" />
										<%=flightDate.toString().substring(0, 11)%>
									</logic:present>
									<logic:notPresent name="consignmentDocumentVo" property="flightDate">
										&nbsp;
									</logic:notPresent>
								</td>
			              			</logic:equal>
			              		<td class="iCargoTableDataTd">
										 <common:write name="consignmentDocumentVo" property="weight" unitFormatting="true" />
				              	</td>
								<td class="iCargoTableDataTd">
									<logic:present name="consignmentDocumentVo" property="consignmentNumber">
										<bean:define id="condocnum" name="consignmentDocumentVo" property="consignmentNumber" />
										<%String condoc=condocnum.toString();%>
										<ihtml:hidden property="filterCondocNum" value="<%=condoc%>"/>
										<ihtml:text  componentID="CMP_MailTracking_Defaults_CarditEnquiry_ConsignmentNo" indexId="index" readonly="true" property="consignmentNumber" name="consignmentDocumentVo" />
			              					</logic:present>
									<!--<bean:write name="consignmentDocumentVo" property="consignmentNumber" /></td>-->
									<logic:notPresent name="consignmentDocumentVo" property="consignmentNumber">
										<ihtml:text  componentID="CMP_MailTracking_Defaults_CarditEnquiry_ConsignmentNo" indexId="index"  readonly="true" property="consignmentNumber" maxlength="35" value="" />
									</logic:notPresent>
								<td class="iCargoTableDataTd">

										    <logic:present name="consignmentDocumentVo" property="consignmentDate">
					                      		<%=consignmentDocumentVo.getConsignmentDate().toDisplayDateOnlyFormat().toUpperCase()%>
										    </logic:present>

								</td>	
								  <!--Added by a7531-->
								<td class="iCargoTableDataTd">
									<logic:present name="consignmentDocumentVo" property="shipmentPrefix">
									<bean:define id="shipmentPrefix" name="consignmentDocumentVo"
										property="shipmentPrefix" />
										<logic:present name="consignmentDocumentVo" property="documentNumber">
									<bean:define id="documentNumber" name="consignmentDocumentVo"
										property="documentNumber" />
										<%String sp=shipmentPrefix.toString();%>
										<%String dn=documentNumber.toString();%>
									<%=sp%>-<%=dn%>
                                  <ihtml:hidden property="awbNumber" value="<%=dn%>"/>
									</logic:present>
									</logic:present>
									<logic:notPresent name="consignmentDocumentVo" property="shipmentPrefix">
									<ihtml:hidden property="awbNumber" value="0"/>
										&nbsp;
									</logic:notPresent>
								</td>
								<logic:equal name="SearchConsignmentForm" property="screenMode" value="MAIN">
								<td class="iCargoTableDataTd">
										
										    <logic:present name="consignmentDocumentVo" property="reqDeliveryTime">
					                      		<%=consignmentDocumentVo.getReqDeliveryTime().toDisplayFormat("dd-MMM-yyyy HH:mm")%>
										    </logic:present>
									   
								</td>
								</logic:equal>								
								<td class="iCargoTableDataTd"><bean:write name="consignmentDocumentVo" property="uldNumber" /></td>
								<logic:equal name="SearchConsignmentForm" property="screenMode" value="POPUP">
								<td >

												<logic:present name="consignmentDocumentVo" property="inList">
													<logic:equal name="consignmentDocumentVo" property="inList" value="Y" >
														<!--<input type="checkbox" name="inList" value="true" checked  disabled="true" />-->
														<img id="inList" src="<%=request.getContextPath()%>/images/icon_on.gif" />
													</logic:equal>
													<logic:equal name="consignmentDocumentVo" property="inList" value="N">
														<!--<input type="checkbox" name="inList" value="false" disabled="true"/>-->
														<img id="notInList" src="<%=request.getContextPath()%>/images/icon_off.gif" />
													</logic:equal>
												</logic:present>
												<logic:notPresent name="consignmentDocumentVo" property="inList">
													<!--<input type="checkbox" name="inList" value="false" disabled="true"/>-->
													<img id="notInList" src="<%=request.getContextPath()%>/images/icon_off.gif" />
												</logic:notPresent>

								</td>
			              		</logic:equal>
								<td>

										<logic:present name="consignmentDocumentVo" property="accepted">
										
										
										<bean:define id="accepted" name="consignmentDocumentVo"
										property="accepted" />
										
										<%String mailAccepted=accepted.toString();%>
						
											<input type="hidden" name="notAccepted" value="<%=mailAccepted%>" />
											<logic:equal name="consignmentDocumentVo" property="accepted" value="Y" >
													<!--<input type="checkbox" name="accepted" value="true" checked  disabled="true" />-->
													<img id="isAccepted" src="<%=request.getContextPath()%>/images/icon_on.gif" />
											</logic:equal>
											<logic:equal name="consignmentDocumentVo" property="accepted" value="N">
													<!--<input type="checkbox" name="accepted" value="false" disabled="true" />-->
													<img id="isNotAccepted" src="<%=request.getContextPath()%>/images/icon_off.gif" />
											</logic:equal>
										</logic:present>
										<logic:notPresent name="consignmentDocumentVo" property="accepted">
												<!--<input type="checkbox" name="accepted" value="false" disabled="true"/>-->
												<img id="isNotAccepted" src="<%=request.getContextPath()%>/images/icon_off.gif" />
										</logic:notPresent>

								</td>
		              		    </tr>

								</logic:iterate>
								</logic:present>
							</tbody>
							</table>
							</div>

						</div>
					</div>
					
					<!--Added by A-8061 for ICRD-233692 starts-->
					<div class="tableContainer" style="height:23px;">
							<table class="fixed-header-table" style="height:23px;">
								<tfoot>
									<tr>
										<td style="width:50%" class="ic-right ic-bold-value"><common:message key="mailtracking.defaults.carditenquiry.lbl.grandtotal" /></td>
										<td  style="width:4%" class="ic-right ic-bold-value"><common:message key="mailtracking.defaults.carditenquiry.lbl.totalpieces" /></td>
										<td  style="width:4%" class="ic-center ic-bold-value">
										<logic:present name="totalPcs">
										<logic:notEqual name="totalPcs" value="0">
										<common:write name ="totalPcs"/>
										</logic:notEqual>
										</logic:present>
										</td>
										<td  style="width:6%" class="ic-right ic-bold-value"><common:message key="mailtracking.defaults.carditenquiry.lbl.totalweight" /></td>
										<td  style="width:5%" class="ic-center ic-bold-value">
										<logic:present name="totalWeight">
										<common:write name ="totalWeight" unitFormatting="true"/>
										</logic:present>
										</td>
										<td>&nbsp;</td>
							        </tr>
							    </tfoot>
							</table>
				</div>
					<!--Added by A-8061 for ICRD-233692 ends-->
					
				</div>
			</div>
			</div>
			</div>
			<div class="ic-foot-container">

				<div class="ic-button-container paddR5">
				<!--Added by a7531-->
				<%boolean asianaFlag = false;%>
				<common:xgroup>
					<common:xsubgroup id="ASIANA_SPECIFIC">
					 <% asianaFlag = true;%>
					</common:xsubgroup>
				</common:xgroup >
				
				<%boolean baseFlag = false;%>
				<common:xgroup>
					<common:xsubgroup id="BS_SPECIFIC">
					 <% baseFlag = true;%>
					</common:xsubgroup>
				</common:xgroup >
				<%boolean keFlag = false;%>
				<common:xgroup>
					<common:xsubgroup id="KE_SPECIFIC">
					 <% keFlag = true;%>
					</common:xsubgroup>
				</common:xgroup >
				<%boolean jxFlag = false;%>
				<common:xgroup>
					<common:xsubgroup id="JX_SPECIFIC">
					 <% jxFlag = true;%>
					</common:xsubgroup>
				</common:xgroup >
				
		<% if(asianaFlag || baseFlag || keFlag || jxFlag){%>
		
						<ihtml:miscprintmultibutton accesskey="R" property="printMiscReportButtonId"    id="PrintMiscReports_button" componentID="CMP_MailTracking_Defaults_CarditEnquiry_btnMiscReport">
						
						<common:message key="mailtracking.defaults.carditenquiry.btn.miscReport" />
						</ihtml:miscprintmultibutton>
		
				       <ihtml:nbutton property="bookedFlights"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_btnBookedFlights" accesskey="B">
						<common:message key="mailtracking.defaults.carditenquiry.btn.bookedFlights" />
						</ihtml:nbutton>
						
						<ihtml:nbutton property="attachAWB"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_btnAttachAWB" accesskey="A">
						<common:message key="mailtracking.defaults.carditenquiry.btn.attachAWB" />
						</ihtml:nbutton>
						
						<ihtml:nbutton property="detachAWB"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_btnDetachAWB" accesskey="D">
						<common:message key="mailtracking.defaults.carditenquiry.btn.detachAWB" />
						</ihtml:nbutton>
						<%}%>
						<!--a8061 added for ICRD-82434 starts-->
						<logic:equal name="SearchConsignmentForm" property="screenMode" value="MAIN">
							<ihtml:nbutton property="btSendResdit"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_SendResdit" accesskey="R">
							<common:message key="mailtracking.defaults.carditenquiry.btn.sendresdit" />
							</ihtml:nbutton>
                      	</logic:equal>
						<!--a8061 added for ICRD-82434 ends-->
						<logic:equal name="SearchConsignmentForm" property="screenMode" value="POPUP">
							<ihtml:nbutton property="btAddToList"  componentID="CMP_MailTracking_Defaults_SearchCosignment_AddToList" accesskey="T">
								<common:message key="mailtracking.defaults.mailenquiry.tooltip.addtolist" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btRemoveFromList"  componentID="CMP_MailTracking_Defaults_SearchCosignment_RemoveFromList" accesskey="R">
								<common:message key="mailtracking.defaults.mailenquiry.tooltip.removefromlist" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btOk"  componentID="CMP_MailTracking_Defaults_SearchCosignment_Ok" accesskey="K">
								<common:message key="mailtracking.defaults.mailenquiry.tooltip.ok" />
							</ihtml:nbutton>
                      	</logic:equal>
						<logic:equal name="SearchConsignmentForm" property="screenMode" value="POPUP_CARDIT">
                      		<ihtml:nbutton property="btOk"  componentID="CMP_MailTracking_Defaults_SearchCosignment_Ok" accesskey="K">
							<common:message key="mailtracking.defaults.mailenquiry.tooltip.ok" />
							</ihtml:nbutton>
                      	</logic:equal>
						 <logic:equal name="SearchConsignmentForm" property="screenMode" value="MAIN">
                      		<ihtml:nbutton property="btAssignMail"  componentID="CMP_MailTracking_Defaults_SearchCosignment_AssignMail" accesskey="A">
							<common:message key="mailtracking.defaults.carditenquiry.btn.assignmail" />
							</ihtml:nbutton>
                      	</logic:equal>
                      	<logic:equal name="SearchConsignmentForm" property="screenMode" value="MAIN">
							<ihtml:nbutton property="btViewConDoc"  componentID="CMP_MailTracking_Defaults_SearchCosignment_ViewConDoc" accesskey="V">
							<common:message key="mailtracking.defaults.carditenquiry.btn.viewcondoc" />
							</ihtml:nbutton>
                      	</logic:equal>
						<ihtml:nbutton property="btClose"  componentID="CMP_MailTracking_Defaults_CarditEnquiry_btClose" accesskey="O">
						<common:message key="mailtracking.defaults.carditenquiry.btn.close" />
						</ihtml:nbutton>
				</div>

			</div>
		</div>

	</ihtml:form>
	</div>



	</body>
</html:html>
