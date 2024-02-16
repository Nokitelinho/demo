
<%--
/***********************************************************************
* Project : iCargo
* Module Code & Name : SKCM - stock Control
* File Name
: EditRange.jsp
* Date : 13-Sep-2005
* Author(s) : Kirupakaran
*************************************************************************/
--%>

<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import =
"com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO" %>
<%@ page
import =
"com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.EditRangeForm"
%>

<html:html>
	<head>
		
		


		<bean:define id="form" name="EditRangeForm"
			type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.EditRangeForm"
			toScope="page" />
		<title>
			<common:message bundle="<%=form.getBundle()%>" key="editrange.title" />
		</title>
		<meta name="decorator" content="popuppanelrestyledui">
			<common:include type="script"
				src="/js/stockcontrol/defaults/EditRange_Script.jsp" />
	</head>
</head>
	<body id="bodyStyle">


		<business:sessionBean id="allocstock" moduleName="stockcontrol.defaults"
			screenID="stockcontrol.defaults.editrange" method="get" attribute="allocatedRangeVos" />

		<business:sessionBean id="availstock" moduleName="stockcontrol.defaults"
			screenID="stockcontrol.defaults.editrange" method="get" attribute="availableRangeVos" />
		<%
		int availcount;
		int alloccount;
		EditRangeForm editRangeForm =
		(EditRangeForm)request.getAttribute("EditRangeForm");
		%>
		<div class="iCargoPopUpContent" style="overflow:auto;">
			<ihtml:form method="post"
				action="/stockcontrol.defaults.screenloadeditrange.do" styleClass="ic-main-form">
				<html:hidden property="isValidRange" />
				<html:hidden property="maxRange" />

				<div class="ic-content-main">


					<span class="ic-page-title ic-display-none">
						<common:message key="stockholder.EditRange" />
					</span>
					<div class="ic-head-container">
						<div class="ic-row">
							<div class="ic-col-100">
								<div class="ic-filter-panel">

									<div class="ic-row ic-border" style="width:99.5%">
										<div class="ic-input ic-split-20 ic-label-30 ">
											<label>
												<common:message key="stockholder.DocType" />
											</label>

											<ihtml:text property="docType"
												componentID="TXT_STOCKCONTROL_DEFAULTS_EDITRANGE_DOCUMENTTYPE"
												readonly="true" />
										</div>
										<div class="ic-input ic-split-20 ic-label-50 ">
											<label>
												<common:message key="stockholder.SubType" />
											</label>
											<ihtml:text property="subType"
												componentID="TXT_STOCKCONTROL_DEFAULTS_EDITRANGE_DOCUMENTSUBTYPE"
												readonly="true" />
										</div>
										<div class="ic-input ic-split-20 ic-label-30 ">
											<label>
												<common:message key="stockholder.ApprovedStock" />
											</label>

											<ihtml:text property="approvedStock" style="text-align:right"
												componentID="TXT_STOCKCONTROL_DEFAULTS_EDITRANGE_APPROVEDSTOCK"
												readonly="true" />
										</div>
									<!--</div>
									<div class="ic-row">-->
										<div class="ic-input ic-split-20 ic-label-50 ic_inline_chcekbox marginT10">
											<ihtml:checkbox property="manual"
												componentID="CHK_STOCKCONTROL_DEFAULTS_EDITRANGE_MANUAL"
												disabled="true" />


											<label>
												<common:message key="stockholder.Manual" />
											</label>
										</div>
										<div class="ic-input ic-mandatory ic-split-20 ic-label-50">
											<label>
												<common:message key="stockholder.StockHolderCode" />
											</label>


											<ihtml:text property="stockHolderCode"
												componentID="TXT_STOCKCONTROL_DEFAULTS_EDITRANGE_STOCKHOLDERCODE"
												readonly="true" />
										</div>
									</div>
								</div>
							</div>
						</div>

					</div>

					<div class="ic-main-container">
						<div class="ic-row">
							<div class="ic-col-45">
								<div class="ic-row">
									<div class="ic-row" style="margin-top: 12px;">
										<h4>
											<common:message key="stockholder.AvailableStock" />
										</h4>
									</div>
									<div class="ic-filter-panel">
										<div class="ic-row">
											<div class="ic-input ic-split-30 ic-label-40 ic-pad-0">

												<label>
													<common:message key="stockholder.RangeFrom" />
												</label>


												<ihtml:text property="rangeFrom" style="text-align:right"
													componentID="TXT_STOCKCONTROL_DEFAULTS_EDITRANGE_RANGEFROM" />
											</div>
											<div class="ic-input ic-split-30  ic-label-35 ic-pad-0">
												<label>
													<common:message key="stockholder.RangeTo" />
												</label>
												<ihtml:text property="rangeTo" style="text-align:right"
													componentID="TXT_STOCKCONTROL_DEFAULTS_EDITRANGE_RANGETO" />
											</div>
										<!--</div>
										<div class="ic-row">-->
											<div class="ic-input ic-split-30 ic-label-40 ic-pad-0">
												<label>
													<common:message key="stockholder.NoOfDocs" />
												</label>
												<ihtml:text property="numberOfDocs" style="text-align:right"
													componentID="TXT_STOCKCONTROL_DEFAULTS_EDITRANGE_NUMBEROFDOCUMENTS"
													readonly="true" maxlength="8" />
											</div>

										</div>
										<div class="ic-row">
											<div class="ic-button-container">
												<ihtml:button property="btnList"
													componentID="BTN_STOCKCONTROL_DEFAULTS_EDITRANGE_LIST">
													<common:message key="editrange.list" />
												</ihtml:button>
											</div>
										</div>
									</div>

									<div class="ic-row marginT5">
										<div id="div1" class="tableContainer" style="height:190px;">
											<table class="fixed-header-table" style="width:100%;"
												id="newRangeTable">
												<thead>
													<tr class="iCargoTableHeadingLeft ic-center">
														<td style="width:10%;">
															<ihtml:checkbox property="availableCheckAll"
																style="text-align:right"
																componentID="CHK_STOCKCONTROL_DEFAULTS_EDITRANGE_AVAILABLECHECKALL" />
														</td>
														<td style="width:35%;">
															<common:message key="stockholder.RANGEFROM" />
														</td>
														<td style="width:35%;">
															<common:message key="stockholder.RANGETO" />
														</td>
														<td style="width:20%;">
															<common:message key="stockholder.NOOFDOCS" />
														</td>
													</tr>
												</thead>
												<tbody>

													<%long availTotalNoOfDocs;%>
													<%
													availcount=0;
													availTotalNoOfDocs=0;
													%>
													<logic:present name="availstock">

														<logic:iterate id="availvo" name="availstock"
															indexId="nIndex">

															<tr>
																<td class="iCargoTableDataTd ic-center">
																	<ihtml:checkbox property="availableRangeNo"
																		componentID="CHK_STOCKCONTROL_DEFAULTS_EDITRANGE_AVAILABLERANGENO"
																		value="<%= String.valueOf(availcount) %>" />
																</td>
																<td style="text-align:right" class="iCargoTableDataTd"><%=
																	((RangeVO)availvo).getStartRange()%></td>
																<td style="text-align:right" class="iCargoTableDataTd"><%=
																	((RangeVO)availvo).getEndRange()%></td>
																<td style="text-align:right" class="iCargoTableDataTd"><%=((RangeVO)availvo).getNumberOfDocuments()%>
														</td>
															</tr>
															<%
															availTotalNoOfDocs=availTotalNoOfDocs+((RangeVO)availvo).getNumberOfDocuments();
															availcount++;
															%>

														</logic:iterate>

													</logic:present>
												</tbody>
											</table>
										</div>

									</div>
								</div>
							</div>
							<div class="ic-col-5">
								<div class="ic-button-container">
									<ihtml:button property="btnMoveRange" value="&gt;&gt;"
										componentID="BTN_STOCKCONTROL_DEFAULTS_EDITRANGE_MOVERANGE">
									</ihtml:button>
								</div>
							</div>
							<div class="ic-col-50">
								<div class="ic-row">
									<div class="ic-row">
										<div class="ic-button-container paddR5">
											<a class="iCargoLink" href="javascript:onClickAdd();">
												<common:message key="stockholder.Add" />
											</a>
											|
											<a class="iCargoLink" href="javascript:onClickDelete();">
												<common:message key="stockholder.Delete" />
											</a>
										</div>
									</div>
									<div class="ic-row">

										<div id="div2" class="tableContainer" style="height:190px;">
											<table class="fixed-header-table" style="width:100%;"
												id="listRangeTable">
												<thead>
													<tr class="iCargoTableHeadingLeft">
														<td style="width:10%;">
															<ihtml:checkbox property="allocatedCheckAll"
																componentID="CHK_STOCKCONTROL_DEFAULTS_EDITRANGE_ALLOCATEDCHECKALL" />
														</td>
														<td style="width:35%;">
															<common:message key="stockholder.RANGEFROM" />
														</td>
														<td style="width:35%;">
															<common:message key="stockholder.RANGETO" />
														</td>
														<td style="width:35%;">
															<common:message key="stockholder.NOOFDOCS" />
														</td>
													</tr>
												</thead>
												<tbody id="rangeTableBody">
													<logic:present name="allocstock">
														<%
														alloccount=0;
														%>
														<logic:iterate id="vo" name="allocstock"
															indexId="nIndex">

															<logic:present name="vo" property="operationFlag">
																<bean:define id="flag" name="vo" property="operationFlag" />
																<logic:notEqual name="flag" value="D">
																	<tr>
																		<td class="iCargoTableDataTd ic-center">
																			<ihtml:checkbox property="allocatedRangeNo"
																				componentID="CHK_STOCKCONTROL_DEFAULTS_EDITRANGE_ALLOCATEDRANGENO"
																				value="<%= String.valueOf(alloccount) %>" />
																			<logic:equal name="flag" value="I">
																				<ihtml:hidden property="hiddenOpFlag"
																					value="I" />
																			</logic:equal>
																			<logic:equal name="flag" value="U">
																				<ihtml:hidden property="hiddenOpFlag"
																					value="U" />
																			</logic:equal>
																		</td>
																		<td class="iCargoTableDataTd">
																			<ihtml:text property="stockRangeFrom"
																				style="text-align:right;width:99%;" styleId="stockRangeF"
																				componentID="TXT_STOCKCONTROL_DEFAULTS_EDITRANGE_STOCKRANGEFROM"
																				indexId="nIndex" value="<%= ((RangeVO)vo).getStartRange() %>" />
																		</td>
																		<td class="iCargoTableDataTd">
																			<ihtml:text property="stockRangeTo" style="text-align:right;width:99%;"
																				componentID="TXT_STOCKCONTROL_DEFAULTS_EDITRANGE_STOCKRANGETO"

																				indexId="nIndex" value="<%= ((RangeVO)vo).getEndRange() %>"
																				styleId="stockRangeT" />
																		</td>
																		<td class="iCargoTableDataTd">
																			<ihtml:text property="noOfDocs"
																				style="text-align:right;border:0px;background:;width=60px"
																				componentID="TXT_STOCKCONTROL_DEFAULTS_EDITRANGE_NUMEBROFDOCS"
																				value="<%= String.valueOf(((RangeVO)vo).getNumberOfDocuments()) %>"
																				maxlength="8" readonly="true" indexId="nIndex"
																				styleId="noOfDocs" />
																		</td>
																	</tr>
																</logic:notEqual>
																<logic:equal name="flag" value="D">
																	<ihtml:hidden property="allocatedRangeNo"
																		value="<%=String.valueOf(alloccount)%>" />
																	<ihtml:hidden property="stockRangeFrom"
																		value="" />
																	<ihtml:hidden property="stockRangeTo"
																		value="" />
																	<ihtml:hidden property="noOfDocs" value="" />
																</logic:equal>
															</logic:present>
															<logic:notPresent name="vo" property="operationFlag">
																<tr>
																	<td class="iCargoTableDataTd ic-center">
																		<ihtml:checkbox property="allocatedRangeNo"
																			componentID="CHK_STOCKCONTROL_DEFAULTS_EDITRANGE_ALLOCATEDRANGENO"
																			value="<%= String.valueOf(alloccount) %>" />
																		<ihtml:hidden property="hiddenOpFlag"
																			value="U" />
																	</td>
																	<td class="iCargoTableDataTd">
																		<ihtml:text property="stockRangeFrom"
																			style="text-align:right;width:99%;" styleId="stockRangeF"
																			componentID="TXT_STOCKCONTROL_DEFAULTS_EDITRANGE_STOCKRANGEFROM"
																			indexId="nIndex" value="<%= ((RangeVO)vo).getStartRange() %>" />
																	</td>
																	<td class="iCargoTableDataTd">
																		<ihtml:text property="stockRangeTo" style="text-align:right;width:99%;"
																			componentID="TXT_STOCKCONTROL_DEFAULTS_EDITRANGE_STOCKRANGETO"

																			indexId="nIndex" value="<%= ((RangeVO)vo).getEndRange() %>"
																			styleId="stockRangeT" />
																	</td>
																	<td class="iCargoTableDataTd">
																		<ihtml:text property="noOfDocs"
																			componentID="TXT_STOCKCONTROL_DEFAULTS_EDITRANGE_NUMEBROFDOCS"
																			style="text-align:right;border:0px;background:;width=60px"
																			value="<%= String.valueOf(((RangeVO)vo).getNumberOfDocuments()) %>"
																			maxlength="8" readonly="true" indexId="nIndex"
																			styleId="noOfDocs" />
																	</td>
																</tr>
															</logic:notPresent>

															<%
															alloccount++;
															%>

														</logic:iterate>
													</logic:present>
													<!-- templateRow starts -->
													<tr template="true" id="rangeTableTemplateRow" style="display:none">
														<td class="iCargoTableDataTd ic-center">
															<ihtml:checkbox property="allocatedRangeNo"
																componentID="CHK_STOCKCONTROL_DEFAULTS_EDITRANGE_ALLOCATEDRANGENO" />
															<ihtml:hidden property="hiddenOpFlag" value="NOOP" />
														</td>
														<td class="iCargoTableDataTd">
															<ihtml:text property="stockRangeFrom" style="text-align:right;width:99%;"
																styleId="stockRangeF"
																componentID="TXT_STOCKCONTROL_DEFAULTS_EDITRANGE_STOCKRANGEFROM"

																value="" />
														</td>
														<td class="iCargoTableDataTd">
															<ihtml:text property="stockRangeTo" style="text-align:right;width:99%;"
																componentID="TXT_STOCKCONTROL_DEFAULTS_EDITRANGE_STOCKRANGETO"

																value="" styleId="stockRangeT" />
														</td>
														<td class="iCargoTableDataTd">
															<ihtml:text property="noOfDocs"
																componentID="TXT_STOCKCONTROL_DEFAULTS_EDITRANGE_NUMEBROFDOCS"
																style="text-align:right;border:0px;background:;width=60px"
																value="0" maxlength="8" readonly="true" styleId="noOfDocs" />
														</td>
													</tr>
													<!-- templateRow ends -->

												</tbody>
											</table>
										</div>
									</div>
									<div class="ic-row">
										<div class="ic-input-round-border ">
											<div class="ic-row">

												<div class="ic-input ic-split-50 ">
													<label>
														<common:message key="stockholder.TotalnoofDocs" />
													</label>

													<ihtml:text property="availableTotalnoofDocs"
														style="text-align:right"
														componentID="TXT_STOCKCONTROL_DEFAULTS_EDITRANGE_AVAILABLETOTALNUMBEROFDOCS"
														readonly="true" maxlength="8"
														value="<%=String.valueOf(availTotalNoOfDocs)%>" />
												</div>
											<!--</div>
											<div class="ic-row">-->
												<div class="ic-input ic-split-50">
													<label>
														<common:message key="stockholder.TotalnoofDocs" />
													</label>

													<ihtml:text property="allocatedTotalnoofDocs"
														style="text-align:right"
														componentID="TXT_STOCKCONTROL_DEFAULTS_EDITRANGE_ALLOACTEDTOTALNUMBEROFDOCS"
														readonly="true" maxlength="8"
														value="<%=editRangeForm.getAllocatedTotalnoofDocs()%>" />
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
							<ihtml:button property="btnOk"
								componentID="BTN_STOCKCONTROL_DEFAULTS_EDITRANGE_OK">
								<common:message key="editrange.ok" />
							</ihtml:button>

							<ihtml:button property="btnCancel"
								componentID="BTN_STOCKCONTROL_DEFAULTS_EDITRANGE_CANCEL">
								<common:message key="editrange.cancel" />
							</ihtml:button>
						</div>
					</div>

			</ihtml:form>
		</div>



	
	</body>
</html:html>

