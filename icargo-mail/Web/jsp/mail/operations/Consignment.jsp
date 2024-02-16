<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name 	 :  MailTracking
* File Name     	 :  Consignment.jsp
* Date          	 :  15-July-2006, 27-Oct-2015
* Author(s)     	 :  Roopak V.S.,

*************************************************************************/
 --%>


<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm"%>
<%@ page import="com.ibsplc.xibase.server.framework.persistence.query.Page"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<%@ include file="/jsp/includes/tlds.jsp" %>

<html>
	<head>

		<%@ include file="/jsp/includes/customcss.jsp" %>


		<title>
			<common:message bundle="consignmentResources" key="mailtracking.defaults.consignment.lbl.title" />
		</title>

		<meta name="decorator" content="mainpanel">

		<common:include type="script" src="/js/mail/operations/Consignment_Script.jsp"/>

	</head>
	<body>



	<%@ include file="/jsp/includes/reports/printFrame.jsp" %>

		<bean:define id="ConsignmentForm" name="ConsignmentForm" type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm" toScope="page" scope="request"/>

<business:sessionBean id="conDocVOSession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="consignmentDocumentVO" />
<business:sessionBean id="oneTimeCatSession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="oneTimeCat" />
<business:sessionBean id="oneTimeRISession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="oneTimeRSN" />
<business:sessionBean id="oneTimeHNISession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="oneTimeHNI" />
<business:sessionBean id="oneTimeMailClassSession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="oneTimeMailClass" />
<business:sessionBean id="oneTimeTypeSession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="oneTimeType" />
<business:sessionBean id="oneTimeSubTypeSession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="oneTimeSubType" />



		<div class="iCargoContent">

		<ihtml:form action="mailtracking.defaults.consignment.screenloadconsignment.do">

		<ihtml:hidden property="disableListSuccess" />
		<ihtml:hidden property="tableFocus" />
		<ihtml:hidden property="currentDialogOption" />
		<ihtml:hidden property="currentDialogId" />
		<ihtml:hidden property="duplicateFlightStatus" />
		<ihtml:hidden property="lastPageNum" />
		<ihtml:hidden property="displayPage" />
		<ihtml:hidden property="fromPopupflg" />
		<ihtml:hidden property="afterPopupSaveFlag" />
		<ihtml:hidden property="maxPageLimit" />
		<ihtml:hidden property="fromScreen" />
		<ihtml:hidden  property="newRoutingFlag"/>

			<div class="ic-content-main">
				<span class="ic-page-title ic-display-none">
					<common:message key="mailtracking.defaults.consignment.lbl.pagetitle" />
				</span>
				<div class="ic-head-container">
				<div class="ic-filter-panel">
					<div class="ic-row">
						<h4>
								<common:message key="mailtracking.defaults.consignment.lbl.docdtls" />
						</h4>
					</div>
					<div class="ic-row">
							
							<div class="ic-input ic-mandatory ic-split-30">
								<label>
									<common:message key="mailtracking.defaults.consignment.lbl.condocno" />
								</label>
								<ihtml:text property="conDocNo" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_CONDOCNO"
								value="<%=ConsignmentForm.getConDocNo()%>"  style="width:100px" maxlength="35"/>
							</div>
						
						
							<div class="ic-input ic-mandatory ic-split-35">
								<label>
									<common:message key="mailtracking.defaults.consignment.lbl.pa" />
								</label>
								<ihtml:text property="paCode" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_PA" 			value="<%=ConsignmentForm.getPaCode()%>" maxlength="5" />
								<div class="lovImg">
									<img id="paCodeLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onClick="displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.paCode.value,'PA','1','paCode','',0)">
							</div>
						</div>
						<div class="ic-col-30 ic-label-30">
							<div class="ic-button-container">
								<ihtml:nbutton property="btnList" componentID="BTN_MAILTRACKING_DEFAULTS_CONSIGNMENT_LIST" accesskey="L">
										<common:message key="mailtracking.defaults.consignment.btn.list" />
									</ihtml:nbutton>
									<ihtml:nbutton property="btnClear" componentID="BTN_MAILTRACKING_DEFAULTS_CONSIGNMENT_CLEAR" accesskey="C">
										<common:message key="mailtracking.defaults.consignment.btn.clear" />
									</ihtml:nbutton>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-main-container">
				<div class="ic-row ic-input-round-border" style="width:99%">
					 <div class="ic-input-container">
						<div class="ic-row">
							<div class="ic-col-30 ic-input-round-border ic-label-20">
								<div class="ic-input ic-split-100">
									<div class="ic-row">
										<div class="ic-input ic-split-100">
											<div class="ic-input ic-split-20">
											<label>
												<common:message key="mailtracking.defaults.consignment.lbl.inbound"/>
											</label>
										
											
											</div>
											<div class="ic-input ic-split-30">
											
											<ihtml:radio property="direction" value="I" componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGNMENT_RADIOINBOUND"/>
											</div>
											<div class="ic-input ic-split-20">
											<label>
												<common:message key="mailtracking.defaults.consignment.lbl.outbound"/>
											</label>
										</div>
											<div class="ic-input ic-split-30">
											<ihtml:radio property="direction" value="O" componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGNMENT_RADIOOUTBOUND"/>
											
									</div>
									</div>
									</div>
									<div class="ic-row">
										<div class="ic-input ic-mandatory ic-split-50">
											<label>
												<common:message key="mailtracking.defaults.consignment.lbl.condate" />
											</label>
											<logic:notPresent name="conDocVOSession" property="consignmentDate">
											<ibusiness:calendar property="conDate" id="conDate" type="image" value="" componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGNMENT_CONDATE" />
										</logic:notPresent>
										<logic:present name="conDocVOSession" property="consignmentDate">
											<bean:define id="conDate" name="conDocVOSession" property="consignmentDate" toScope="page"/>
											<%String conDt=TimeConvertor.toStringFormat(((LocalDate)conDate).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
											<ibusiness:calendar property="conDate" id="conDate" type="image" value="<%=conDt%>" componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGNMENT_CONDATE" />
										</logic:present>
										</div>
									</div>
									<div class="ic-row">
										<div class="ic-input ic-mandatory ic-split-35">
											<label>
												<common:message key="mailtracking.defaults.consignment.lbl.type" />
											</label>

											<%
											String typeValue = "" ;
										%>
										<logic:present name="conDocVOSession" property="type">
											<bean:define id="type" name="conDocVOSession" property="type" toScope="page"/>
											<%
												typeValue = (String) type;
											%>
										</logic:present>
										<ihtml:select property="type" componentID="CMB_MAILTRACKING_DEFAULTS_CONSIGNMENT_TYPE" value="<%=typeValue%>" >
											<html:option value=""> <common:message key="mailtracking.defaults.consignment.lbl.select" /> </html:option>
											<bean:define id="oneTimeTypeSess" name="oneTimeTypeSession" toScope="page" />
											<logic:iterate id="typeVO" name="oneTimeTypeSess" >
												<bean:define id="fieldValue" name="typeVO" property="fieldValue" toScope="page" />
												<html:option value="<%=(String)fieldValue %>"><%=(String)fieldValue %></html:option>
											</logic:iterate>
										</ihtml:select>
										</div>
									
										<div class="ic-input ic-split-33">
											<label>
												<common:message key="mailtracking.defaults.consignment.lbl.subType" />
											</label>

											<%
											String subTypeValue = "" ;
										%>
										<logic:present name="conDocVOSession" property="subType">
											<bean:define id="subType" name="conDocVOSession" property="subType" toScope="page"/>
											<%
												subTypeValue = (String) subType;
											%>
										</logic:present>
										<ihtml:select property="subType" componentID="CMB_MAILTRACKING_DEFAULTS_CONSIGNMENT_SUBTYPE" value="<%=subTypeValue%>" >
											<html:option value=""> <common:message key="mailtracking.defaults.consignment.lbl.select" /> </html:option>
											<bean:define id="oneTimeTypeSess" name="oneTimeSubTypeSession" toScope="page" />
											<logic:iterate id="typeVO" name="oneTimeTypeSess" >
												<bean:define id="fieldValue" name="typeVO" property="fieldValue" toScope="page" />
												<html:option value="<%=(String)fieldValue %>"><%=(String)fieldValue %></html:option>
											</logic:iterate>
										</ihtml:select>
										</div>
									</div>
									
							</div>
							</div>

							<div class="ic-col-65 ic-input-round-border">
								<div class="ic-row">
									<div class="ic-col-50">
										<h4>
											<common:message key="mailtracking.defaults.consignment.lbl.onwardrouting" />
										</h4>
									</div>
									<div class="ic-col-50">
										<div class="ic-button-container">
											<a class="iCargoLink" href="#" id="lnkAddRoute"><common:message key="mailtracking.defaults.consignment.lnk.add" /></a>

											
											<a class="iCargoLink" href="#" id="lnkDeleteRoute"><common:message key="mailtracking.defaults.consignment.lnk.delete" /></a>
										</div>
									</div>
								</div>
									<div class="ic-row">
									<div class="tableContainer"  style="height:100px;">
										<table class="fixed-header-table" id="VisitDetails">
											<thead>
												<tr>
													<td width="5%">
														<input type="checkbox" name="masterRoute" 	onclick="updateHeaderCheckBox(this.form,this,this.form.selectRoute);"/>
													</td>
													<td width="25%">
														<common:message key="mailtracking.defaults.consignment.lbl.flightno" />
													</td>
													<td width="25%">
														<common:message key="mailtracking.defaults.consignment.lbl.depdate" />
													</td>
													<td width="20%">
														<common:message key="mailtracking.defaults.consignment.lbl.pol" />
													</td>
													<td width="20%">
														<common:message key="mailtracking.defaults.consignment.lbl.pou" />
													</td>
												</tr>
											</thead>
											<tbody id="routeTableBody">
												 <% int tabNum = 7; %>
									<logic:present name="conDocVOSession" property="routingInConsignmentVOs">
									  <logic:notEmpty name="conDocVOSession" property="routingInConsignmentVOs">
										<bean:define id="routingInConsignmentVOs" name="conDocVOSession" property="routingInConsignmentVOs" scope="page" toScope="page"/>
										<% int row = ((Collection)routingInConsignmentVOs).size();%>
										<logic:iterate id="routingInConsignmentVO" name="routingInConsignmentVOs" indexId="index">
											<% tabNum = tabNum + row * index; %>
											<logic:notEqual name="routingInConsignmentVO" property="operationFlag" value="D">
												<logic:present name="routingInConsignmentVO" property="operationFlag">
													<bean:define id="operationFlag" name="routingInConsignmentVO" property="operationFlag" toScope="request" />
													<ihtml:hidden property="routeOpFlag" value="<%=((String)operationFlag)%>" />
												</logic:present>
												<logic:notPresent name="routingInConsignmentVO" property="operationFlag">
													<ihtml:hidden property="routeOpFlag" value="N" />
												</logic:notPresent>
												<tr>
													<td class="ic-center">
														<input type="checkbox" name="selectRoute" value="<%=String.valueOf(index)%>" onclick="toggleTableHeaderCheckbox(this,this.form.masterRoute);"/>
													</td>
													<td>
														<logic:notPresent name="routingInConsignmentVO" property="onwardFlightNumber">
															   <ibusiness:flightnumber id="fltNo" carrierCodeProperty="flightCarrierCode"  flightCodeProperty="flightNumber" carriercodevalue="" flightcodevalue=""  componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGNMENT_FLIGHTNO"/>
														</logic:notPresent>

														<logic:present name="routingInConsignmentVO" property="onwardCarrierCode">
														<logic:present name="routingInConsignmentVO" property="onwardFlightNumber">
																<bean:define id="carrierCode" name="routingInConsignmentVO" property="onwardCarrierCode" toScope="page"/>
																<bean:define id="flightNo" name="routingInConsignmentVO" property="onwardFlightNumber" toScope="page"/>
																<ibusiness:flightnumber id="fltNo" carrierCodeProperty="flightCarrierCode"  flightCodeProperty="flightNumber" carriercodevalue="<%=(String)carrierCode%>" flightcodevalue="<%=(String)flightNo%>"  componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGNMENT_FLIGHTNO"/>
														</logic:present>
														</logic:present>
													</td>
													<td>
														<logic:notPresent name="routingInConsignmentVO" property="onwardFlightDate">
															<ibusiness:calendar property="depDate" indexId="rowCount" id="depDate" type="image" value="" componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGNMENT_DEPDATE" />
														</logic:notPresent>
														<logic:present name="routingInConsignmentVO" property="onwardFlightDate">
														    <bean:define id="depDate" name="routingInConsignmentVO" property="onwardFlightDate" toScope="page"/>
														    <%String dpDt=TimeConvertor.toStringFormat(((LocalDate)depDate).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
														    <ibusiness:calendar property="depDate" indexId="rowCount" id="depDate" type="image" value="<%=dpDt%>" componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGNMENT_DEPDATE" />
														</logic:present>
													</td>
													<td>
														<logic:notPresent name="routingInConsignmentVO" property="pol">
															<ihtml:text property="pol" indexId="rowCount" componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGNMENT_POL" maxlength="4" value="" />
														</logic:notPresent>
														<logic:present name="routingInConsignmentVO" property="pol">
															<bean:define id="pol" name="routingInConsignmentVO" property="pol" toScope="page"/>
															<ihtml:text property="pol" indexId="rowCount" componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGNMENT_POL" maxlength="4" value="<%=(String)pol%>" />
														</logic:present>
														
															<img name="polLov" id="polLov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16">
													</td>
													<td>
														<logic:notPresent name="routingInConsignmentVO" property="pou">
															<ihtml:text property="pou" indexId="rowCount" componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGNMENT_POU" maxlength="4" value="" />
														</logic:notPresent>
														<logic:present name="routingInConsignmentVO" property="pou">
															<bean:define id="pou" name="routingInConsignmentVO" property="pou" toScope="page"/>
															<ihtml:text property="pou" indexId="rowCount" componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGNMENT_POU" maxlength="4" value="<%=(String)pou%>" />
														</logic:present>
														
															<img name="pouLov" id="pouLov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16">
													</td>
												</tr>
												</logic:notEqual>
											<logic:equal name="routingInConsignmentVO" property="operationFlag" value="D">

											   <bean:define id="onwardCarrierCode" name="routingInConsignmentVO" property="onwardCarrierCode" toScope="page"/>
											   <bean:define id="onwardFlightNumber" name="routingInConsignmentVO" property="onwardFlightNumber" toScope="page"/>
											   <bean:define id="onwardFlightDate" name="routingInConsignmentVO" property="onwardFlightDate" toScope="page"/>
											   <bean:define id="pol" name="routingInConsignmentVO" property="pol" toScope="page"/>
											   <bean:define id="pou" name="routingInConsignmentVO" property="pou" toScope="page"/>

												<ihtml:hidden property="flightCarrierCode" value="<%=onwardCarrierCode.toString()%>"/>
												<ihtml:hidden property="flightNumber" value="<%=onwardFlightNumber.toString()%>"/>
												<ihtml:hidden property="depDate" value="<%= onwardFlightDate.toString().substring(0,11) %>"/>
												<ihtml:hidden property="pol" value="<%=pol.toString()%>"/>
												<ihtml:hidden property="pou" value="<%=pou.toString()%>"/>

												<ihtml:hidden property="routeOpFlag" value="D" />

											</logic:equal>
										</logic:iterate>
									  </logic:notEmpty>
									</logic:present>

									<!-- templateRow -->
										<tr template="true" id="routeTemplateRow" style="display:none">
											<ihtml:hidden property="routeOpFlag" value="NOOP" />
											<td class="ic-center">
												<input type="checkbox" name="selectRoute" >
											</td>
											 <td>
													<ibusiness:flightnumber id="fltNo" carrierCodeProperty="flightCarrierCode"  flightCodeProperty="flightNumber" carriercodevalue="" flightcodevalue="" componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGNMENT_FLIGHTNO" readonly="false" />
											 </td>
											<td>
												<ibusiness:calendar property="depDate" indexId="index" id="depDate" type="image" value="" componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGNMENT_DEPDATE" />
											</td>
											<td>
												<ihtml:text property="pol" componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGNMENT_POL" maxlength="4" value="" />
												<!-- Modified by A-7938 for ICRD-243958-->
												<div class="lovImgTbl valignT">
												<img name="polLov" id="polLov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16">
												</div>
											</td>
											<td>
												<ihtml:text property="pou" componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGNMENT_POU" maxlength="4" value="" />
												<!-- Modified by A-7938 for ICRD-243958-->
												<div class="lovImgTbl valignT">
												<img name="pouLov" id="pouLov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16">
												</div>
											</td>

										</tr>
			<!--template row ends-->

											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					 </div>
				</div>
					<jsp:include page="Consigment_MailDetails.jsp" />
				<div class="ic-row">
					<div class="ic-input ic-split-100 paddL10">
						<label>
							<common:message key="mailtracking.defaults.consignment.lbl.remarks" />
						</label>
						
						<logic:notPresent name="conDocVOSession" property="remarks">
								<ihtml:textarea property="remarks" cols="80" rows="3" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_REMARKS" value="" ></ihtml:textarea>
							</logic:notPresent>
							<logic:present name="conDocVOSession" property="remarks">
								<bean:define id="remarks" name="conDocVOSession" property="remarks" toScope="page" />
								<ihtml:textarea property="remarks" cols="80" rows="3" value="<%=(String)remarks%>" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_REMARKS" ></ihtml:textarea>
							</logic:present>
					</div>
					<!-- Modified by A-7938 for ICRD-243958-->
					<div class="ic-row" >
							&nbsp;
				</div>
				</div>
			</div>
			<div class="ic-foot-container">
				<div class="ic-button-container paddR5">
				<ihtml:nbutton property="btnPrintMailTag"  componentID="BTN_MAILTRACKING_DEFAULTS_CONSIGNMENT_PRINTMAILTAG" accesskey="M">
						   		  <common:message key="mailtracking.defaults.consignment.btn.printmailtag" />
							</ihtml:nbutton> <!-- added by a-7871 for ICRD-234913-->
						<ihtml:nbutton property="btnPrint"  componentID="BTN_MAILTRACKING_DEFAULTS_CONSIGNMENT_PRINT" accesskey="P">
						   		  <common:message key="mailtracking.defaults.consignment.btn.print" />
							</ihtml:nbutton>
						   	<ihtml:nbutton property="btnDelete"   componentID="BTN_MAILTRACKING_DEFAULTS_CONSIGNMENT_DELETE" accesskey="E">
						   		<common:message key="mailtracking.defaults.consignment.btn.delete" />
							</ihtml:nbutton>
					  	        <ihtml:nbutton property="btnSave"   componentID="BTN_MAILTRACKING_DEFAULTS_CONSIGNMENT_SAVE" accesskey="S">
								<common:message key="mailtracking.defaults.consignment.btn.save" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btnClose"   componentID="BTN_MAILTRACKING_DEFAULTS_CONSIGNMENT_CLOSE" accesskey="O">
								<common:message key="mailtracking.defaults.consignment.btn.close" />
					  		</ihtml:nbutton>
				</div>
			</div>
			</div>
		 </ihtml:form>
		</div>

	</body>
</html>