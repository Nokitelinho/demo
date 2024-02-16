
<%--
/***********************************************************************
* Project : iCargo
* Module Code & Name : IN - Inventory Control
* File
Name : AllocateStock.jsp
* Date : 2-sep-2005
* Author(s) : Akhila S

*************************************************************************/
--%>

<%@ page language="java"%>
<%@ include
file="/jsp/includes/tlds.jsp"%>
<%@include
		file="/jsp/includes/reports/printFrame.jsp"%>
<%@ page
import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page
import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page
import="com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO"%>
<%@ page
import="com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO"%>

<html:html>

	<head>
		


		<bean:define id="form" name="AllocateStockForm"
			type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.AllocateStockForm"
			toScope="page" />
		<title>
			<common:message bundle="<%=form.getBundle()%>" key="allocatestock.title" />
		</title>
		<meta name="decorator" content="mainpanelrestyledui">
			<common:include type="script"
				src="/js/stockcontrol/defaults/AllocateStock_Script.jsp" />
	</head>

	<body id="bodyStyle">
		
		



		<business:sessionBean id="prioritizedStockHolders"
			moduleName="stockcontrol.defaults" screenID="stockcontrol.defaults.allocatestock"
			method="get" attribute="prioritizedStockHolders" />
		<business:sessionBean id="stockHolderFor"
			moduleName="stockcontrol.defaults" screenID="stockcontrol.defaults.allocatestock"
			method="get" attribute="stockHolderFor" />
		<business:sessionBean id="statusVO" moduleName="stockcontrol.defaults"
			screenID="stockcontrol.defaults.allocatestock" method="get"
			attribute="status" />
		<business:sessionBean id="partnerAirlines"
			moduleName="stockcontrol.defaults" screenID="stockcontrol.defaults.allocatestock"
			method="get" attribute="partnerAirlines" />
		<logic:present name="prioritizedStockHolders">
			<bean:define id="stockHoldersFromSession" name="prioritizedStockHolders" />
		</logic:present>
		<logic:present name="stockHolderFor">
			<bean:define id="stockHolderFors" name="stockHolderFor" />
		</logic:present>
		<logic:present name="statusVO">
			<bean:define id="statusOnetime" name="statusVO" />
		</logic:present>
		<div id="pageDiv" class="iCargoContent ic-masterbg" style="overflow: auto;">
			<ihtml:form action="stockcontrol.defaults.screenloadallocatestock.do">
				<html:hidden property="closeStatus" />
				<html:hidden property="reportGenerateMode" />
				<html:hidden property="documentRange" />
				<html:hidden property="buttonStatusFlag" />
				<html:hidden property="partnerPrefix" />
				<input type="hidden" name="currentDialogId" />
				<input type="hidden" name="currentDialogOption" />


				<div class="ic-content-main">
					<span class="ic-page-title ic-display-none">
						<common:message key="allocatestock.allocatestock" />
					</span>


					<div class="ic-head-container">
						<div class="ic-filter-panel ">

							<div class="ic-row">
								<div class="ic-col-45">
									<div class="ic-input ic-split-100">
										<div class="ic-col-50" >
                                            <div class="ic-center" style="margin-left:12px;">
                                                <business:sessionBean id="map"
											moduleName="stockcontrol.defaults" screenID="stockcontrol.defaults.allocatestock"
											method="get" attribute="map" />
										<logic:present name="map">
											<bean:define id="maps" name="map" toScope="page"
												type="java.util.HashMap" />
											<!--Modified by A-1927 @ NRT on 13-Jul-2007 for NCA Bug Fix starts -->
											<ibusiness:dynamicoptionlist
												collection="maps" id="docType" firstlistname="docType"
												componentID="TXT_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_DYNAMICOPTIONLIST"
												lastlistname="docSubType" firstoptionlabel="Doc. Type"
												lastoptionlabel="  Sub Type" optionstyleclass="iCargoMediumComboBox"
												labelstyleclass="iCargoLabelRightAligned"
												firstselectedvalue="<%=form.getDocType()%>"
												lastselectedvalue="<%=form.getDocSubType()%>" docTypeTitle="doctype.tooltip"
												subDocTypeTitle="subdoctype.tooltip" tabindex='1'/>
											<!--Modified by A-1927 @ NRT on 13-Jul-2007 for NCA Bug Fix ends -->


										</logic:present>
									       </div></div>
                                       
									<div class="ic-col-50" >
									<div class="ic-input" style="margin-left:12px;">
										<label>
											<bean:message bundle="<%=form.getBundle()%>"
												key="allocatestock.stockcontrolfor" />
										</label>
										<ihtml:text property="stockControlFor" name="AllocateStockForm"
											componentID="CMB_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_STOCKCONTROLFOR"
											maxlength="12" tabindex='2'/>
										<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png"
											width="22" height="22" id="stockControlForLov"
											onclick="displayLov('stockcontrol.defaults.screenloadstockholderlov.do');" /></div>
									</div></div>
                                    </div>
									<div class="ic-split-100">
										<div class="ic-col-50">
											<div class="ic-input ic-mandatory" style="margin-left:12px;">
												<label>
													<common:message key="allocatestock.fromdate" />
												</label>
												<ibusiness:calendar id="fromDate"
													componentID="TXT_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_FROMDATE"
													property="fromDate" type="image" textStyleClass="iCargoTextFieldMedium"
													value="<%=form.getFromDate()%>" tabindex='4'/>
											</div>
										</div>
										<div class="ic-col-50">
											<div class="ic-input ic-mandatory" style="margin-left:12px;">
												<label>
													<common:message key="allocatestock.todate" />
												</label>
												<ibusiness:calendar id="toDate"
													componentID="TXT_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_TODATE"
													property="toDate" type="image" textStyleClass="iCargoTextFieldMedium"
													value="<%=form.getToDate()%>" tabindex='5'/>
											</div>
										</div>
									</div>

									<div class="ic-split-100 ic_inline_chcekbox" style="margin-left:12px;">
										<div class="ic-col-50" style="margin-top:18px;">
											<div class="ic-input">
												<ihtml:checkbox property="manual"
													componentID="CHK_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_MANUAL" tabindex='8'/>
												<label>
													<common:message key="allocatestock.manual" />
												</label>
											</div>
										</div>
										<div class="ic-col-50" style="margin-top:18px;">



											<div class="ic-input">
												<ihtml:checkbox property="partnerAirline"
													componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL_CHK" tabindex='9' />
												<label>
													<common:message
														key="stockcontrol.defaults.allocatestock.partnerAirlines.lbl" />
												</label>
											</div>
										</div>
									</div>
								</div>


								<div class="ic-col-30 ic-label-40">


									<div class="ic-input ic-split-100">
										<label>
											<common:message key="allocatestock.stockholdertype" />
										</label>
										<ihtml:select property="stockHolderType"
											componentID="CMB_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_STOCKHOLDERTYPE" tabindex='6'>

											<logic:present name="stockHoldersFromSession">
												<bean:define id="stockHolderList" name="stockHoldersFromSession" />
												<html:option value="">
													<common:message key="combo.select" />
												</html:option>
												<logic:iterate id="priorityVO" name="stockHolderList">
													<html:option
														value="<%=((StockHolderPriorityVO)priorityVO).getStockHolderType()%>">
														<%=((StockHolderPriorityVO) priorityVO)
														.getStockHolderDescription()%>
							</html:option>
												</logic:iterate>
											</logic:present>
										</ihtml:select>
									</div>
									<div class="ic-input ic-split-100">
										<label>
											<common:message
												key="stockcontrol.defaults.allocatestock.awbPrefix.lbl" />
										</label>
										<ihtml:select property="awbPrefix"
											componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL_CMB"
											disabled='true' tabindex='10'>
											<ihtml:option value="">
												<common:message key="combo.select" />
											</ihtml:option>
											<logic:present name="partnerAirlines">
												<logic:iterate id="airlineLovVO" name="partnerAirlines"
													type="com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO">
													<logic:present name="airlineLovVO" property="airlineNumber">
														<%
														String value = airlineLovVO
														.getAirlineNumber()
														+ "-"
														+
														airlineLovVO.getAirlineName()
														+ "-"
														+ airlineLovVO
														.getAirlineIdentifier();
														%>
														<ihtml:option value="<%=value%>"><%=airlineLovVO
															.getAirlineNumber()%></ihtml:option>
													</logic:present>
												</logic:iterate>
											</logic:present>
										</ihtml:select>
									</div>
					<div class="ic-input ic-split-100">
										<label>
											<common:message
												key="stockcontrol.defaults.allocatestock.partnerAirlines.lbl" />
										</label>
										<ihtml:text property="airlineName"
											componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL"
											readonly="true"  tabindex='11'/>
								</div>

								</div>
								<div class="ic-col-25 ic-label-30">
									<div class="ic-input ic-mandatory ic-split-100">
										<label>
											<common:message key="allocatestock.statusmandatory" />
										</label>
										<ihtml:select property="status"
											componentID="CMB_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_STATUS" tabindex='3'>
											<html:option value="ALL">
												<common:message key="allocatestock.all" />
											</html:option>
											<logic:present name="statusOnetime">
												<logic:iterate id="oneTimeVO" name="statusOnetime">
													<bean:define id="defaultValue" name="oneTimeVO"
														property="fieldValue" />
													<bean:define id="diaplayValue" name="oneTimeVO"
														property="fieldDescription" />
													<html:option value="<%=(String)defaultValue%>"><%=(String) diaplayValue%></html:option>
												</logic:iterate>
											</logic:present>
										</ihtml:select>
									</div>

									<div class="ic-input ic-label-90">
										<label>
											<common:message key="allocatestock.stockholdercode" />
										</label>
										<ihtml:text property="stockHolderCode"
											componentID="TXT_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_STOCKHOLDERCODE"
											maxlength="12" tabindex='7' />
										<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png"
											width="22" height="22"
											onclick="displayLov1('stockcontrol.defaults.screenloadstockholderlov.do');" /></div>
									</div>


									
								</div>

							</div>


							<div class="ic-row">
								<div class="ic-button-container paddR5">
									<ihtml:nbutton property="btnList"
										componentID="BTN_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_LIST"
										accesskey="L">
										<common:message key="allocatestock.list" />
									</ihtml:nbutton>
									<ihtml:nbutton property="btnClear"
										componentID="BTN_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_CLEAR"
										accesskey="C">
										<common:message key="allocatestock.clear" />
									</ihtml:nbutton>
								</div>
							</div>


						</div>

					</div>


					<div class="ic-main-container">

						<div class="ic-row">
							<div class="ic-col-65">

								<div class="ic-section ic-border">
									<div class="ic-row">
										<div class="tableContainer" id="div1" style="height:300px;">
											<table class="fixed-header-table" id="stockTable">
												<thead>
													<tr class="ic-th-all">
														<th style="width:3%;" />

														<th style="width:13%;" />


														<th style="width:7%;" />

														<th style="width:8%;" />
														<th style="width:7%;" />
														<th style="width:6%;" />
														<th style="width:12%;" />
														<th style="width:8%;" />

														<th style="width:8%;" />

														<th style="width:8%;" />
														<th style="width:8%;" />
														<th style="width:7%;" />

														<th style="width:5%;" />





													</tr>

													<tr class="iCargoTableHeadingLeft ic-center" onClick="this.document">
														<td rowspan="2" class="iCargoTableHeaderLabel">
															<input type="checkbox" name="checkAll" value="checkbox"
																onclick="updateHeaderCheckBox(this.form,this,this.form.checkBox)" />

														</td>
														<td rowspan="2" class="iCargoTableHeaderLabel">
															<common:message key="allocatestock.reqrefno" />
															<span></span>
														</td>
														<td rowspan="2" class="iCargoTableHeaderLabel">
															<common:message key="allocatestock.reqby" />
															<span></span>
														</td>
														<td rowspan="2" class="iCargoTableHeaderLabel" style="text-transform: none;">
															<common:message key="allocatestock.dateofrequest" />
															<span></span>
														</td>
														<td rowspan="2" class="iCargoTableHeaderLabel">
															<common:message key="allocatestock.doctype" />
															<span></span>
														</td>
														<td rowspan="2" class="iCargoTableHeaderLabel">
															<common:message key="allocatestock.subtype" />
															<span></span>
														</td>
														<td rowspan="2" class="iCargoTableHeaderLabel">
														    <common:message key="allocatestock.remarks" />
															<span></span>
														</td>
														<td class="iCargoTableHeaderLabel" colspan="4">
															<common:message key="allocatestock.stock" />
															<span></span>
														</td>
														<td rowspan="2" class="iCargoTableHeaderLabel">
															<common:message key="allocatestock.status" />
															<span></span>
														</td>
														<td rowspan="2" class="iCargoTableHeaderLabel">
															<common:message key="allocatestock.manual" />
															<span></span>
														</td>
													</tr>
													<tr class="iCargoTableHeadingLeft">
														<td class="iCargoTableHeaderLabel">
															<common:message key="allocatestock.reqstock" />
															<span></span>
														</td>
														<td class="iCargoTableHeaderLabel">
															<common:message key="allocatestock.apprstock" />
															<span></span>
														</td>
														<td class="iCargoTableHeaderLabel">
															<common:message key="allocatestock.allocstock" />
															<span></span>
														</td>
														<td class="iCargoTableHeaderLabel">
															<common:message key="allocatestock.tobealloc" />
															<span></span>
														</td>

													</tr>
												</thead>
												<tbody>
													<business:sessionBean id="pages"
														moduleName="stockcontrol.defaults" screenID="stockcontrol.defaults.allocatestock"
														method="get" attribute="pageStockRequestVO" />
													<%
													int indexCount = 0;
													%>
													<logic:present name="pages">
														<bean:define id="list" name="pages" toScope="page" />
														<logic:iterate id="productList" name="list"
															indexId="nIndex">

															<logic:present name="productList">
																<bean:define id="lp" name="productList"
																	type="com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO" />
																<%
																String manual = null;
																String reqDate =
																TimeConvertor.toStringFormat(
																((LocalDate)
																(lp.getRequestDate()))
																.toCalendar(),
																TimeConvertor.CALENDAR_DATE_FORMAT);
																String
																toBeAllocated
																= String.valueOf((lp
																.getApprovedStock())
																-
																(lp.getAllocatedStock()));
																if
																(lp.isManual()) {
																manual =
																"Yes";
																} else {
																manual = "No";
																}
																%>
																<tr height="100%">
																	<td class="iCargoTableDataTd ic-center">
																		<business:sessionBean id="check"
																			moduleName="stockcontrol.defaults" screenID="stockcontrol.defaults.allocatestock"
																			method="get" attribute="check" />
																		<logic:present name="check">
																			<bean:define id="checkedone" name="check"
																				toScope="page" />
																			<%
																			if (checkedone.equals(lp
																			.getRequestRefNumber()))
																			{
																			%>
																			<input type="checkbox" name="checkBox"
																				value="<%=lp.getRequestRefNumber()%>" checked="true" />
																			<%
																			} else {
																			%>
																			<ihtml:checkbox property="checkBox"
																				componentID="CHK_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_CHECKBOX"
																				value="<%=lp.getRequestRefNumber()%>" />
																			<%
																			}
																			%>
																		</logic:present>
																		<logic:notPresent name="check">
																			<ihtml:checkbox property="checkBox"
																				componentID="CHK_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_CHECKBOX"
																				value="<%=lp.getRequestRefNumber()%>" />
																		</logic:notPresent>
																	</td>
																	<td class="iCargoTableDataTd"><%=lp.getRequestRefNumber()%></td>
																	<td class="iCargoTableDataTd"><%=lp.getStockHolderCode()%></td>
																	<td class="iCargoTableDataTd"><%=reqDate%></td>
																	<td class="iCargoTableDataTd"><%=lp.getDocumentType()%></td>
																	<td class="iCargoTableDataTd"><%=lp.getDocumentSubType()%></td>
																	<td class="iCargoTableDataTd">
																	<%
																			String remarks="";
																			String remark="";
																			if(lp.getRemarks()!= null) {
																			remark =lp.getRemarks();
																			if(remark.length()>20){
																				remarks=remark.substring(0,20);
																				remarks=remarks.concat("*");
																			}else{
																						remarks=remark;
																					}
																					}
																	%>
																	<div title="<%=remark%>"><%=remarks %></div></td>
																	<td style="text-align: right;" class="iCargoTableDataTd"><%=lp.getRequestedStock()%>
																	</td>
																	<td class="iCargoTableDataTd">
																		<ihtml:text property="approvedStock"
																			style="border: 0px;background :;width:50px;text-align:right;"
																			value="<%=String.valueOf(((StockRequestVO)lp).getApprovedStock())%>"
																			componentID="TXT_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_APPROVEDSTOCK"
																			maxlength="8" styleId="<%=(new Integer(indexCount)).toString()%>" />
																	</td>
																	<td class="iCargoTableDataTd">
																		<ihtml:text property="allocatedStock"
																			style="border: 0px;background :;width:50px;text-align:right;"
																			value="<%=String.valueOf(((StockRequestVO)lp).getAllocatedStock())%>"
																			componentID="TXT_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_ALLOCATEDSTOCK"
																			maxlength="8" readonly="true" />
																	</td>
																	<td class="iCargoTableDataTd">
																		<ihtml:text property="toBeallocated"
																			style="border: 0px;background :;width:50px;text-align:right;"
																			value="<%=toBeAllocated%>"
																			componentID="TXT_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_TOBEALLOCATEDSTOCK"
																			maxlength="8" readonly="true" />
																	</td>
																	<td class="iCargoTableDataTd"><%=lp.getStatus()%></td>
																	<td class="iCargoTableDataTd"><%=manual%></td>
																</tr>


															</logic:present>

															<%
															indexCount++;
															%>

														</logic:iterate>
													</logic:present>
												</tbody>
											</table>
										</div>
										<html:hidden property="refNoHidden" />
									</div>




									<div class="ic-row">
										<div class="ic-input-container ic-input-round-border">
											<div class="ic-row">
												<span class="ic-label-35">
													<label>
														<common:message key="allocatestock.approvalrejectionremarks" />
													</label>
												</span>
											</div>
											<div class="ic-row">
												<div class="ic-input">
													<ihtml:textarea property="appremarks" rows="5"
														cols="100"
														componentID="TXT_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_APPREJREMARKS"
														onblur="validateMaxLength(this,250)"></ihtml:textarea>
												</div>
											</div>
										</div>
									</div>

									<div class="ic-row">

										<div class="ic-button-container">
											<ihtml:nbutton property="btnReject"
												componentID="BTN_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_REJECT"
												accesskey="R">
												<common:message key="allocatestock.reject" />
											</ihtml:nbutton>
											<ihtml:nbutton property="btnApprove"
												componentID="BTN_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_APPROVE"
												accesskey="A">
												<common:message key="allocatestock.approve" />
											</ihtml:nbutton>
											<ihtml:nbutton property="btnComplete"
												componentID="BTN_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_COMPLETE"
												accesskey="T">
												<common:message key="allocatestock.complete" />
											</ihtml:nbutton>
										</div>

									</div>



								</div>


							</div>


							<div class="ic-col-35">

								<div class="ic-section ic-border">

									<div class="ic-row">


										<div class="ic-row">

											<div class="ic-input-container ic-input-round-border">
												<div class="ic-col-35 ic-label-55">
													<label>
														<common:message key="allocatestock.startrange" />
													</label>
													<ihtml:text property="startRange" style="text-align:right;"
														componentID="TXT_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_STARTRANGE" />
												</div>
												<div class="ic-col-65">
													<div class="ic-button-container">
														<ihtml:nbutton property="btnListRange"
															componentID="BTN_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_LISTRANGE"
															accesskey="I">
															<common:message key="allocatestock.list" />
														</ihtml:nbutton>
													</div>
												</div>

											</div>
										</div>

										<div class="ic-row">

											<div class="tableContainer" id="div2" style="height: 285px;">
												<table id="rangeTable" class="fixed-header-table"
													style="width: 100%;">
													<thead>
														<tr class="iCargoTableHeadingLeft">
															<td class="iCargoTableHeaderLabel" style="width:40%;">
																<common:message key="allocatestock.rangefrom" />
																<span></span>
															</td>
															<td class="iCargoTableHeaderLabel" style="width:40%;">
																<common:message key="allocatestock.rangeto" />
																<span></span>
															</td>
															<td class="iCargoTableHeaderLabel" style="text-transform: none;"
																style="width:20%;">
																<common:message key="allocatestock.noofdocs" />
																<span></span>
															</td>
														</tr>
													</thead>
													<tbody height="100%">
														<business:sessionBean id="listrange"
															moduleName="stockcontrol.defaults" screenID="stockcontrol.defaults.allocatestock"
															method="get" attribute="rangeVO" />
														<logic:present name="listrange">
															<bean:define id="lists" name="listrange"
																toScope="page" />

															<logic:iterate id="oneList" name="lists"
																indexId="nIndex">

																<logic:present name="oneList">
																	<bean:define id="oneRangeVO" name="oneList"
																		type="com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO" />

																	<tr>
																		<td class="iCargoTableDataTd">
																			<bean:write name="oneRangeVO" property="startRange" />
																		</td>
																		<td class="iCargoTableDataTd">
																			<bean:write name="oneRangeVO" property="endRange" />
																		</td>
																		<td class="iCargoTableDataTd">
																			<bean:write name="oneRangeVO" property="numberOfDocuments"
																				format="" />
																		</td>
																	</tr>
																</logic:present>

															</logic:iterate>
														</logic:present>
													</tbody>
												</table>
											</div>

										</div>
									</div>

									<div class="ic-row">
										<div class="ic-button-container">
											<ihtml:nbutton property="btnAllocate"
												componentID="BTN_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_ALLOCATE"
												accesskey="E">
												<common:message key="allocatestock.allocate" />
											</ihtml:nbutton>
											<ihtml:nbutton property="btnEditRange"
												componentID="BTN_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_EDITRANGE"
												accesskey="R">
												<common:message key="allocatestock.editrange" />
											</ihtml:nbutton>
										</div>
									</div>



									<div class="ic-row">
										<div class="ic-input-container ic-input-round-border">
											<div class="ic-col-100">
												<div class="ic-row">
													<span class="ic-label">
														<label>
															<common:message key="allocatestock.remarks" />
														</label>
													</span>
												</div>
												<div class="ic-row">
													<div class="ic-input ic-pad-0">
														<ihtml:textarea property="remarks" rows="4"
															cols="35" componentID="TXT_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_REMARKS"></ihtml:textarea>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="ic-foot-container">

					<div class="ic-button-container paddR5">
						<ihtml:nbutton property="btnMonitorStock"
							componentID="BTN_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_MONITORSTOCK"
							accesskey="M">
							<common:message key="allocatestock.monitorstock" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnAllocateNew"
							componentID="BTN_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_ALLOCATENEW"
							accesskey="N">
							<common:message key="allocatestock.allocatenew" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClose"
							componentID="BTN_STOCKCONTROL_DEFAULTS_ALLOCATESTOCK_CLOSE"
							accesskey="O">
							<common:message key="allocatestock.close" />
						</ihtml:nbutton>
					</div>

				</div>
		</div>


	</div>
	</ihtml:form>
	</div>



		
	</body>
</html:html>



