<%--****************************************************
* Project	 		: iCargo
* Module Code & Name: Mail Tracking
* File Name			: MailBagEnquiry.jsp
* Date				: 19-Jun-2006
* Author(s)			: A-1861
 ***************************************************--%>


<%@ page language="java"%>
<%@ include file="/jsp/includes/tlds.jsp"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page
	import="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"%>

<html:html>

<head>

		<%@ include file="/jsp/includes/customcss.jsp" %>

<title><common:message bundle="mailBagEnquiryResources"
		key="mailtracking.defaults.mailbagenquiry.lbl.pagetitle" /></title>
<meta name="decorator" content="mainpanel">
<common:include type="script"
	src="/js/mail/operations/MailBagEnquiry_Script.jsp" />
</head>

<body>



	<div id="pageDiv" class="iCargoContent ic-masterbg">

		<ihtml:form
			action="/mailtracking.defaults.mailbagenquiry.screenload.do">

			<bean:define id="form" name="MailBagEnquiryForm"
				type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailBagEnquiryForm"
				toScope="page" scope="request" />

			<business:sessionBean id="currentStatus"
				moduleName="mail.operations"
				screenID="mailtracking.defaults.mailBagEnquiry" method="get"
				attribute="currentStatus" />

			<business:sessionBean id="operationTypes"
				moduleName="mail.operations"
				screenID="mailtracking.defaults.mailBagEnquiry" method="get"
				attribute="operationTypes" />

			<business:sessionBean id="mailCategory"
				moduleName="mail.operations"
				screenID="mailtracking.defaults.mailBagEnquiry" method="get"
				attribute="mailCategory" />

			<business:sessionBean id="mailbagVOPage"
				moduleName="mail.operations"
				screenID="mailtracking.defaults.mailBagEnquiry" method="get"
				attribute="mailbagVOs" />

			<logic:present name="mailbagVOPage">
				<bean:define id="mailbagsPage" name="mailbagVOPage" toScope="page" />
			</logic:present>

			<ihtml:hidden property="screenStatusFlag" />
			<ihtml:hidden property="lastPageNum" />
			<ihtml:hidden property="displayPage" />
			<ihtml:hidden property="status" />
			<ihtml:hidden property="fromScreen" />
			<ihtml:hidden property="assignToFlight" />
			<ihtml:hidden property="destination" />
			<ihtml:hidden property="destinationCity" />
			<ihtml:hidden property="carrierInv" />
			<ihtml:hidden property="selCont" />
			<ihtml:hidden property="reList" />
			<ihtml:hidden property="successMailFlag" />

			<input type="hidden" name="currentDialogId" />
			<input type="hidden" name="currentDialogOption" />
			<input type=hidden name="mySearchEnabled" />
			<div class="ic-content-main">
				<div class="ic-head-container">
						
					<span class="ic-page-title ic-display-none"> <common:message
							key="mailtracking.defaults.mailbagenquiry.lbl.mailbagenquiry" />
					</span>
					<div class="ic-filter-panel">
					<div class="ic-row">
						<h4>
							<common:message
								key="mailtracking.defaults.mailbagenquiry.lbl.search" />
						</h4>
					</div>	
						<div class="ic-row ic-border" style="width: 99%;">
							<h4>
								<common:message
									key="mailtracking.defaults.mailbagenquiry.lbl.mailbag" />
							</h4>

							<div class="ic-row ic-border" style="width: 99%;">
								<div class="ic-input ic-split-16">
									<label> <common:message
											key="mailtracking.defaults.mailbagenquiry.lbl.mailbagid" />
									</label>
									<ihtml:text property="mailbagId" maxlength="29"
										componentID="CMP_MailTracking_Defaults_MailBagEnquiry_MailbagId" style="width:210px"/> <!--modified. A-8164 for ICRD 257609-->
								</div>							
								<div class="ic-input ic-split-13 ">
									<label> <common:message
											key="mailtracking.defaults.mailbagenquiry.lbl.originoe" />
									</label>
									<ihtml:text property="originOE" maxlength="6"
										componentID="CMP_MailTracking_Defaults_MailBagEnquiry_originOE" />
									<div class="lovImg">
									<img id="originOELov"
										src="<%=request.getContextPath()%>/images/lov.png" width="22"
										height="22" value="org" />
								</div>
								</div>
								<div class="ic-input ic-split-13 ">
									<label> <common:message
											key="mailtracking.defaults.mailbagenquiry.lbl.destnoe" />
									</label>
									<ihtml:text property="destnOE" maxlength="6"
										componentID="CMP_MailTracking_Defaults_MailBagEnquiry_destnOE" />
									<div class="lovImg">	
									<img id="destnOELov"
										src="<%=request.getContextPath()%>/images/lov.png" width="22"
										height="22" value="dstn" />
								</div>
								</div>
								<div class="ic-input ic-split-14">
									<label> <common:message
											key="mailtracking.defaults.mailbagenquiry.lbl.cat" />
									</label>
									<ihtml:select property="category"
										componentID="CMP_MailTracking_Defaults_MailBagEnquiry_category"
										style="width:150px">
										<logic:present name="mailCategory">
											<bean:define id="category" name="mailCategory" toScope="page" />
											<ihtml:option value="">
												<common:message key="combo.select" />
											</ihtml:option>
											<logic:iterate id="onetmvo" name="category">
												<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
												<bean:define id="value" name="onetimevo"
													property="fieldValue" />
												<bean:define id="desc" name="onetimevo"
													property="fieldDescription" />
												<html:option value="<%=value.toString()%>"><%=desc%></html:option>
											</logic:iterate>
										</logic:present>
									</ihtml:select>

								</div>
								<div class="ic-input ic-split-12">
									<label> <common:message
											key="mailtracking.defaults.mailbagenquiry.lbl.sc" />
									</label>
									<ihtml:text property="subClass" maxlength="2"
										componentID="CMP_MailTracking_Defaults_MailBagEnquiry_Sc" />
									<div class="lovImg">	
									<img id="subClassLov"
										src="<%=request.getContextPath()%>/images/lov.png" width="22"
										height="22" value="subClas" />
								</div>
								</div>
								<div class="ic-input ic-split-8">
									<label> <common:message
											key="mailtracking.defaults.mailbagenquiry.lbl.yr" />
									</label>
									<ihtml:text property="year" maxlength="1"
										componentID="CMP_MailTracking_Defaults_MailBagEnquiry_Year"
										style="text-align:right" />
								</div>
								<div class="ic-input ic-split-8 ">
									<label> <common:message
											key="mailtracking.defaults.mailbagenquiry.lbl.dsn" />
									</label>
									<ihtml:text property="dsn" maxlength="4"
										componentID="CMP_MailTracking_Defaults_MailBagEnquiry_Dsn"
										style="text-align:right" />
								</div>
								<div class="ic-input ic-split-8">
									<label> <common:message
											key="mailtracking.defaults.mailbagenquiry.lbl.rsn" />
									</label>
									<ihtml:text property="rsn" maxlength="3"
										componentID="CMP_MailTracking_Defaults_MailBagEnquiry_Rsn"
										style="text-align:right" />
								</div>


							</div>
							</div>
							<div class="ic-row">
							<h4>
								<common:message
									key="mailtracking.defaults.mailbagenquiry.lbl.operationDetails" />
							</h4>
							</div>
							<div class="ic-row ic-border" style="width: 99%;">
								<div class="ic-row">
									<div class="ic-input ic-split-16">
										<label> <common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.currentstatus" />
										</label>
										<ihtml:select property="currentStatus"
											componentID="CMP_MailTracking_Defaults_MailBagEnquiry_currentStatus">
											<logic:present name="currentStatus">
												<ihtml:option value="">
													<common:message key="combo.select" />
												</ihtml:option>
												<bean:define id="status" name="currentStatus" toScope="page" />
												<logic:iterate id="onetmvo" name="status">
													<bean:define id="onetimevo" name="onetmvo" type="OneTimeVO" />
													<bean:define id="value" name="onetimevo"
														property="fieldValue" />
													<bean:define id="desc" name="onetimevo"
														property="fieldDescription" />
													<logic:notEqual name="value" value="DMG">
														<logic:notEqual name="value" value="ASG">
															<html:option value="<%=value.toString()%>"><%=desc%></html:option>
														</logic:notEqual>
													</logic:notEqual>
												</logic:iterate>
											</logic:present>
										</ihtml:select>
									</div>
									<div class="ic-input ic-split-13">
										<label> <common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.port" />
										</label>
										<ihtml:text property="port"
											componentID="CMP_MailTracking_Defaults_MailBagEnquiry_port" />
										<div class="lovImg">
										<img id="portLOV"
											src="<%=request.getContextPath()%>/images/lov.png" width="22"
											height="22" value="port" />
									</div>
									</div>
									<div class="ic-input ic-split-13 ic-mandatory">
										<label> <common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.formDate" />
										</label>
										<ibusiness:calendar property="fromDate" type="image"
											id="incalender1" value="<%=form.getFromDate()%>"
											componentID="CMP_MailTracking_Defaults_MailBagEnquiry_FromDate"
											onblur="autoFillDate(this)" />
									</div>
									<div class="ic-input ic-split-14 ic-mandatory">
										<label> <common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.toDate" />
										</label>
										<ibusiness:calendar property="toDate" type="image"
											id="incalender2" value="<%=form.getToDate()%>"
											componentID="CMP_MailTracking_Defaults_MailBagEnquiry_ToDate"
											onblur="autoFillDate(this)" />
									</div>
									<div class="ic-input ic-split-12 ">
										<label> <common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.userid" />
										</label>
										<ihtml:text property="userId" maxlength="10"
											componentID="CMP_MailTracking_Defaults_MailBagEnquiry_Userid" />
									</div>
									<div class="ic-input ic-split-8 ic_inline_chcekbox marginT20">
										<ihtml:checkbox property="damaged"
											componentID="CMP_MailTracking_Defaults_MailBagEnquiry_damaged"
											title="Damaged" />
										<label> <common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.damaged" />
										</label>
									</div>
									<div class="ic-input ic-split-9 ic_inline_chcekbox marginT20">
										<!-- Added by A-5170 for ICRD-19555 starts  -->
										<!--<logic:notPresent name="MailBagEnquiryForm" property="transit">
					 	<input type="checkbox" name="transit" title="Transit" >
					</logic:notPresent>
					<logic:present name="MailBagEnquiryForm" property="transit">
						<logic:equal name="MailBagEnquiryForm" property="transit" value="Y">
							<input type="checkbox" name="transit" title="Transit" checked="true">
					 	</logic:equal>
					 	<logic:notEqual name="MailBagEnquiryForm" property="transit" value="Y" >
					 		<input type="checkbox" name="transit" title="Transit" >
					 	</logic:notEqual>
					 </logic:present> -->
										<logic:notPresent name="MailBagEnquiryForm" property="transit">
											<ihtml:checkbox property="transit" title="Transit" />
										</logic:notPresent>
										<logic:present name="MailBagEnquiryForm" property="transit">
											<ihtml:checkbox property="transit" title="Transit" value="Y" />
										</logic:present>
										<!-- Added by A-5170 for ICRD-19555 ends -->
										<label> <common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.transit" />
										</label>
									</div>
									<div class="ic-input ic-split-15">
										<label> <common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.rdt" />
										</label>
									<ibusiness:calendar property="reqDeliveryDate" type="image" id="reqDeliveryDate"
									value="<%=form.getReqDeliveryDate()%>"
									componentID="CMP_MailTracking_Defaults_CarditEnquiry_ReqDlvDate" onblur="autoFillDate(this)"/>											
											<ibusiness:releasetimer id="reqDeliveryTime" componentID="CMP_MailTracking_Defaults_CarditEnquiry_ReqDlvTime" property="reqDeliveryTime"
												type="asTimeComponent"
												value="<%=form.getReqDeliveryTime()%>"/>											
									</div>									
									</div>									
								<div class="ic-row  marginT10">
									<div class="ic-input ic-split-16 ">
										<label> <common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.contNo" />
										</label>
											<ihtml:text property="containerNo" style="text-transform:uppercase;width:100px;"
											componentID="CMP_MailTracking_Defaults_MailBagEnquiry_ContNo" maxlength="17" />
									</div>
									<div class="ic-input ic-split-13">
										<label> <common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.fltNo" />
										</label>
										<ibusiness:flightnumber
											carrierCodeProperty="flightCarrierCode" id="flight"
											flightCodeProperty="flightNumber"
											carriercodevalue="<%=form.getFlightCarrierCode()%>"
											flightcodevalue="<%=form.getFlightNumber()%>"
											carrierCodeMaxlength="3" flightCodeMaxlength="5"
											componentID="CMP_MailTracking_Defaults_MailBagEnquiry_FltNo" />
									</div>
									<div class="ic-input ic-split-13">
										<label> <common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.fltDate" />
										</label>
										<ibusiness:calendar property="flightDate" type="image"
											id="incalender3" value="<%=form.getFlightDate()%>"
											componentID="CMP_MailTracking_Defaults_MailBagEnquiry_FlightDate"
											onblur="autoFillDate(this)" />
									</div>
									<div class="ic-input ic-split-14 ">
										<label> <common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.consigmentnumber" />
										</label>
										<ihtml:text property="consigmentNumber"
											componentID="CMP_MailTracking_Defaults_MailBagEnquiry_ConsigmentNumber" maxlength="35" />
									</div>
									<div class="ic-input ic-split-12 ">
										<label> <common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.upucode" />
										</label>
										<ihtml:text property="upuCode"
											componentID="CMP_MailTracking_Defaults_MailBagEnquiry_UPUCode" />
									</div>
									<div class="ic-input ic-split-15">
										<label> Cardit Status </label>
										<ihtml:select property="carditStatus"
											componentID="MailTracking_Defaults_MailBagEnquiry_CARDITTYPE">
											<ihtml:option value="">
												<common:message key="combo.select" />
											</ihtml:option>
											<html:option value="Y">Available</html:option>
											<html:option value="N">Not Available</html:option>
										</ihtml:select>
									</div>
							
									<div class="ic-button-container paddR10">
										<ihtml:nbutton property="btList"
											componentID="CMP_MailTracking_Defaults_MailBagEnquiry_btList"
											accesskey="L">
											<common:message
												key="mailtracking.defaults.mailbagenquiry.btn.list" />
										</ihtml:nbutton>
										<ihtml:nbutton property="btClear"
											componentID="CMP_MailTracking_Defaults_MailBagEnquiry_btClear"
											accesskey="E">
											<common:message
												key="mailtracking.defaults.mailbagenquiry.btn.clear" />
										</ihtml:nbutton>
									</div>
								</div>
							
						</div>
					</div>

				</div>
				<div class="ic-main-container">
			<a class="panel upArrow"  collapseFilter="true"  collapseFilterCallbackFun="callbackMailBag"  href="#"></a><!--Added by A-7929 for ICRD - 224586  -->
					<div class="ic-row">
						<h4>
								<common:message
									key="mailtracking.defaults.mailbagenquiry.lbl.mailbagdetails" />
						</h4>			
						</div>

						<div class="ic-row paddR5">
						<div class="ic-col-40">
							<%
								String lstPgNo = "";
							%>
							<logic:present name="MailBagEnquiryForm" property="lastPageNum">
								<bean:define id="lastPg" name="MailBagEnquiryForm"
									property="lastPageNum" scope="request" toScope="page" />
								<%
									lstPgNo = (String) lastPg;
								%>
							</logic:present>

							<logic:present name="mailbagsPage">
								<bean:define id="pageObj" name="mailbagsPage" toScope="page" />

								<common:paginationTag
									pageURL="mailtracking.defaults.mailbagenquiry.list.do"
									name="pageObj" display="label"
									labelStyleClass="iCargoResultsLabel" lastPageNum="<%=lstPgNo%>" />
							</logic:present>
							</div>

					<div class="ic-col-60">
					<div class="ic-button-container">
							<logic:present name="mailbagsPage">
								<bean:define id="pageObj1" name="mailbagsPage" toScope="page" />
								<common:paginationTag linkStyleClass="iCargoLink"
									disabledLinkStyleClass="iCargoLink"
									pageURL="javascript:submitPage('lastPageNum','displayPage')"
									name="pageObj1" display="pages" lastPageNum="<%=lstPgNo%>"
									exportToExcel="true" exportTableId="mailbagenquiry"
									exportAction="mailtracking.defaults.mailbagenquiry.list.do"
									columnSelector="true" tableId="mailbagenquiry" fetchCount="500"/>
							</logic:present>

							<logic:notPresent name="mailbagsPage">
						&nbsp;<common:columnSelector name="enquiryChooser" tableId="mailbagenquiry" id="enquiryChooser" treetype="false"/>
				</logic:notPresent>


						</div>
					</div>
					</div>

					<div id="mailBagEnquiryTable" >
						<div class="tableContainer" id="div1" style="height: 380px">
							<table  id="mailbagenquiry"
								class="fixed-header-table" data-format-content="true"  >
								<thead>
									<tr class="ic-th-all">
										<th style="width: 2%" />
										<th style="width: 6%" /> <!--Modified by A-7929 for ICRD - 224586  -->
										<th style="width: 8%" />
										<th style="width: 6%" />

										<th style="width: 16%" />
										<th style="width: 8%" />
										<th style="width: 5%" />
										<th style="width: 7%" />
										<th style="width: 8%" />
										<th style="width: 8%" />
										<th style="width: 8%" />
										<th style="width: 6%" />
										<th style="width: 6%" />
										<th style="width: 6%" />
									</tr>
									<tr>
										<td class="iCargoTableHeaderLabel" data-ic-csid="td0"><input type="checkbox"
											name="checkAll" value="checkbox"></td>
										<td class="iCargoTableHeaderLabel" data-ic-csid="td1"><common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.latestOprn" /><span></span></td>
										<td class="iCargoTableHeaderLabel" data-ic-csid="td2"><common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.dateOfOpertn" /><span></span></td>
										<td class="iCargoTableHeaderLabel" data-ic-csid="td3"><common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.airport" /><span></span></td>
										<td class="iCargoTableHeaderLabel" data-ic-csid="td4"><common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.mailbagId" /><span></span></td>
										<td class="iCargoTableHeaderLabel" data-ic-csid="td5"><common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.rdt" /><span></span></td>												
										<td class="iCargoTableHeaderLabel" data-ic-csid="td6"><common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.inout" /><span></span></td>
										<td class="iCargoTableHeaderLabel" data-ic-csid="td7"><common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.fltDestn" /><span></span></td>
										<td class="iCargoTableHeaderLabel" data-ic-csid="td8"><common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.deparrDate" /><span></span></td>
										<td class="iCargoTableHeaderLabel" data-ic-csid="td9"><common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.contNo" /><span></span></td>
										<td class="iCargoTableHeaderLabel" data-ic-csid="td10"><common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.consignmentNo" /><span></span></td>
										<td class="iCargoTableHeaderLabel" data-ic-csid="td11"><common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.awbnumber" /><span></span></td>
                                        <td class="iCargoTableHeaderLabel" data-ic-csid="td13"><common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.weight" /><span></span></td>												
										<td class="iCargoTableHeaderLabel" data-ic-csid="td12"><common:message
												key="mailtracking.defaults.mailbagenquiry.lbl.userid" /><span></span></td>
										
									</tr>
								</thead>
								<tbody>

									<logic:present name="mailbagsPage">
										<bean:define id="mailBagVOs" name="mailbagsPage" scope="page"
											toScope="page" />

										<%
											Collection<String> selectedrows = new ArrayList<String>();
										%>

										<logic:present name="form" property="subCheck">

											<%
												String[] selectedRows = form.getSubCheck();
																for (int j = 0; j < selectedRows.length; j++) {
																	selectedrows.add(selectedRows[j]);
																}
											%>

										</logic:present>

										<logic:iterate id="mailBagVO" name="mailBagVOs"
											indexId="rowid" type="com.ibsplc.icargo.business.mail.operations.vo.MailbagVO">

												<tr >

													<td  class="iCargoTableDataTd" style="text-align:center" id="td0">

														<div >

															<%
																if (selectedrows
																							.contains(String.valueOf(rowid))) {
															%>

															<input type="checkbox" name="subCheck"
																value="<%=rowid.toString()%>" checked="true">
															<%
																} else {
															%>
															<input type="checkbox" name="subCheck"
																value="<%=rowid.toString()%>">

															<%
																}
															%>

														</div>

													</td>
													<td  class="iCargoTableDataTd" id="td1">
													<logic:present name="mailBagVO" property="latestStatus">
															<bean:define id="lateststatus" name="mailBagVO"
																property="latestStatus" toScope="page" />

															<logic:present name="currentStatus">
																<bean:define id="currentstatus" name="currentStatus"
																	toScope="page" />

																<logic:iterate id="onetmvo" name="currentstatus">
																	<bean:define id="onetimevo" name="onetmvo"
																		type="OneTimeVO" />
																	<bean:define id="onetimedesc" name="onetimevo"
																		property="fieldDescription" />

																	<logic:equal name="onetimevo" property="fieldValue"
																		value="<%=lateststatus
														.toString()%>">
																		<%=onetimedesc%>
																	</logic:equal>

																</logic:iterate>
															</logic:present>
														</logic:present></td>
													<td class="iCargoTableDataTd" id="td2"><logic:present
															name="mailBagVO" property="scannedDate">
															<bean:define id="scannedDate" name="mailBagVO"
																property="scannedDate" toScope="page" />
															<%=scannedDate.toString().substring(
											0, 11)%>
														</logic:present> <logic:notPresent name="mailBagVO" property="scannedDate">
							&nbsp;
						</logic:notPresent></td>
													<td  class="iCargoTableDataTd" id="td3"><bean:write
															name="mailBagVO" property="scannedPort" /></td>
													<td  class="iCargoTableDataTd" id="td4"><bean:write
															name="mailBagVO" property="mailbagId" /></td>
													<td  class="iCargoTableDataTd" id="td5">
													<logic:present name="mailBagVO" property="reqDeliveryTime">
														<%=mailBagVO.getReqDeliveryTime().toDisplayFormat("dd-MMM-yyyy HH:mm")%>
													</logic:present></td>																
													<td  class="iCargoTableDataTd" id="td6"><logic:present
															name="operationTypes">
															<bean:define id="status" name="currentStatus"
																toScope="page" />
															<logic:iterate id="onetmvo" name="operationTypes">
																<bean:define id="onetimevo" name="onetmvo"
																	type="OneTimeVO" />
																<bean:define id="value" name="onetimevo"
																	property="fieldValue" />
																<bean:define id="desc" name="onetimevo"
																	property="fieldDescription" />
																<logic:equal name="mailBagVO"
																	property="operationalStatus" value="<%=(String) value%>">
																	<%=desc%>
																</logic:equal>
															</logic:iterate>

														</logic:present></td>
													<td  class="iCargoTableDataTd" id="td7"><logic:present
															name="mailBagVO" property="flightDate">
															<logic:present name="mailBagVO" property="carrierCode">

																<bean:define id="carrierCode" name="mailBagVO"
																	property="carrierCode" toScope="page" />
																<bean:define id="flightNumber" name="mailBagVO"
																	property="flightNumber" toScope="page" />

																<%=carrierCode%><%=flightNumber%>

															</logic:present>
														</logic:present> <logic:notPresent name="mailBagVO" property="flightDate">
															<logic:present name="mailBagVO" property="carrierCode">
																<bean:define id="carrierCode" name="mailBagVO"
																	property="carrierCode" toScope="page" />
																<%=carrierCode%>
															</logic:present>
														</logic:notPresent></td>
													<td  class="iCargoTableDataTd" id="td8"><logic:present
															name="mailBagVO" property="flightDate">
															<bean:define id="flightDate" name="mailBagVO"
																property="flightDate" toScope="page" />
															<%=flightDate.toString().substring(
											0, 11)%>
														</logic:present> <logic:present name="mailBagVO" property="flightDate">
							&nbsp;
						</logic:present></td>
													<td  class="iCargoTableDataTd" id="td9"><logic:present
															name="mailBagVO" property="paBuiltFlag">
															<logic:equal name="mailBagVO" property="paBuiltFlag"
																value="Y">
																<bean:write name="mailBagVO" property="containerNumber" />
																<common:message
																	key="mailtracking.defaults.mailbagenquiry.lbl.shipperBuild" />
															</logic:equal>
															<logic:equal name="mailBagVO" property="paBuiltFlag"
																value="N">
																<bean:write name="mailBagVO" property="containerNumber" />
															</logic:equal>
														</logic:present> <logic:notPresent name="mailBagVO" property="paBuiltFlag">
															<bean:write name="mailBagVO" property="containerNumber" />
														</logic:notPresent></td>
													<td class="iCargoTableDataTd" id="td10">
															<logic:present name="mailBagVO" property="consignmentNumber">
																<ihtml:text componentID="CMP_MailTracking_Defaults_MailBagEnquiry_ConsigmentNumber1" indexId="rowid" readonly="true" property="consignmentNumber" name="mailBagVO" />
															</logic:present>
															<logic:notPresent name="mailBagVO" property="consignmentNumber">
																<ihtml:text componentID="CMP_MailTracking_Defaults_MailBagEnquiry_ConsigmentNumber1" indexId="rowid"  readonly="true" property="consignmentNumber" maxlength="35" value="" />
															</logic:notPresent>
															</td>
													<td  class="iCargoTableDataTd" id="td11">
													<logic:present name="mailBagVO" property="documentNumber">
													<bean:write name="mailBagVO" property="shipmentPrefix" />-
															<bean:write name="mailBagVO" property="documentNumber" />
													</logic:present>
													<logic:notPresent name="mailBagVO" property="documentNumber">
															&nbsp;
													</logic:notPresent>													
															</td>	
                                                    <td  class="iCargoTableDataTd"
														style="text-align: right" id="td13"><common:write
															name="mailBagVO" property="weight" unitFormatting="true" />
													</td>	
													<td  class="iCargoTableDataTd" id="td12"><bean:write
															name="mailBagVO" property="scannedUser" /></td>
													
												</tr>

										</logic:iterate>
									</logic:present>

								</tbody>
							</table>
							<jsp:include page="/jsp/includes/columnchooser/columnchooser.jsp"/>
						</div>

					</div>

				</div>
				<div class="ic-foot-container">
					<div class="ic-button-container paddR5">
					
						<ihtml:nbutton property="btnDeliverMail"
							componentID="BTN_MAIL_OPERATIONS_MAILBAGENQUIRY_DELIVER"
							accesskey="D">
							<common:message
								key="mailtracking.defaults.mailbagenquiry.btn.delivermail" />
						</ihtml:nbutton>
						
						<ihtml:nbutton property="btReassignMail"
							componentID="CMP_MailTracking_Defaults_MailBagEnquiry_btReassignMail"
							accesskey="M">
							<common:message
								key="mailtracking.defaults.mailbagenquiry.btn.reassignmail" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btViewHistory"
							componentID="CMP_MailTracking_Defaults_MailBagEnquiry_btViewHistory"
							accesskey="H">
							<common:message
								key="mailtracking.defaults.mailbagenquiry.btn.viewHistory" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btOffloadMail"
							componentID="CMP_MailTracking_Defaults_MailBagEnquiry_btOffloadMail"
							accesskey="I">
							<common:message
								key="mailtracking.defaults.mailbagenquiry.btn.offloadmail" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btTransferMail"
							componentID="CMP_MailTracking_Defaults_MailBagEnquiry_btTransferMail"
							accesskey="F">
							<common:message
								key="mailtracking.defaults.mailbagenquiry.btn.transferMail" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btReturnMail"
							componentID="CMP_MailTracking_Defaults_MailBagEnquiry_btReturnMail"
							accesskey="R">
							<common:message
								key="mailtracking.defaults.mailbagenquiry.btn.returnMail" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btViewDamage"
							componentID="CMP_MailTracking_Defaults_MailBagEnquiry_btViewDamage"
							accesskey="V">
							<common:message
								key="mailtracking.defaults.mailbagenquiry.btn.viewdamage" />
						</ihtml:nbutton>
						<!--
			  <ihtml:nbutton property="btViewCardit"  componentID="CMP_MailTracking_Defaults_MailBagEnquiry_btViewCardit">
				<common:message key="mailtracking.defaults.mailbagenquiry.btn.viewCardit" />
			  </ihtml:nbutton>
			  <ihtml:nbutton property="btDeleteMail"  componentID="CMP_MailTracking_Defaults_MailBagEnquiry_btDeleteMail">
				<common:message key="mailtracking.defaults.mailbagenquiry.btn.deleteMail" />
			  </ihtml:nbutton>
			  -->
						<ihtml:nbutton property="btClose"
							componentID="CMP_MailTracking_Defaults_MailBagEnquiry_btClose"
							accesskey="O">
							<common:message
								key="mailtracking.defaults.mailbagenquiry.btn.close" />
						</ihtml:nbutton>
					</div>
				</div>

			</div>

		</ihtml:form>

	</div>




	</body>

</html:html>