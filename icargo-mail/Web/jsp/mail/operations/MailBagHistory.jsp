
<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  MailBagHistory.jsp
* Date					:  20-June-2006
* Author(s)				:  A-2047
*************************************************************************/
 --%>


<%@ page language="java"%>
<%@ include file="/jsp/includes/tlds.jsp"%>
<%@ page
	import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailBagHistoryForm"%>
<%@ page
	import="com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO"%>





<html:html>
<head>



<title><common:message bundle="mailbagHistoryResources"
		key="mailtracking.defaults.mbHistory.lbl.title" /></title>
<meta name="decorator" content="mainpanelrestyledui">
<common:include type="script"
	src="/js/mail/operations/MailBagHistory_Script.jsp" />
</head>
<body id="bodyStyle">
	<%@include file="/jsp/includes/reports/printFrame.jsp"%>
	<bean:define id="form" name="MailBagHistoryForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailBagHistoryForm"
		toScope="page" />

	<business:sessionBean id="mailBagHistoryVOs"
		moduleName="mail.operations"
		screenID="mailtracking.defaults.mailbaghistory" method="get"
		attribute="mailBagHistoryVOs" />

	<business:sessionBean id="ONETIME_STATUS"
		moduleName="mail.operations"
		screenID="mailtracking.defaults.mailbaghistory" method="get"
		attribute="oneTimeStatus" />

	<div class="iCargoPopUpContent ic-masterbg" >

		<ihtml:form action="/mailtracking.defaults.mbHistory.screenload.do"
			>
			<ihtml:hidden property="btnDisableReq" />
			<ihtml:hidden property="totalViewRecords" />
			<ihtml:hidden property="displayPopupPage" />
			<ihtml:hidden property="mailbagDuplicatePresent" />
			
			<div class="ic-content-main">
				<div class="ic-head-container">
					<span class="ic-page-title ic-display-none"> <common:message
							key="mailtracking.defaults.mbHistory.lbl.heading" />
					</span>
					<div class="ic-filter-panel">
						<div class="ic-row ">
							<div class="ic-input ic-split-40">
								<label> <common:message
										key="mailtracking.defaults.mbHistory.tooltip.mailbagid" />
								</label>
								<ihtml:text property="mailbagId"
									componentID="CMP_MAILTRACKING_DEFAULTS_MAILBAGID"
									maxlength="29" style="width:200px" />
							</div>
							<div class="ic-button-container">
								<ihtml:nbutton property="btList"
									componentID="BUT_MAILTRACKING_DEFAULTS_MBHISTORY_LIST"
									accesskey="L">
									<common:message
										key="mailtracking.defaults.mbHistory.tooltip.list" />
								</ihtml:nbutton>
								<ihtml:nbutton property="btClear"
									componentID="BUT_MAILTRACKING_DEFAULTS_MBHISTORY_CLEAR"
									accesskey="E">
									<common:message
										key="mailtracking.defaults.mbHistory.tooltip.clear" />
								</ihtml:nbutton>
							</div>

						</div>

					</div>
				</div>
				<div class="ic-main-container">
				
				<logic:present name="mailBagHistoryVOs">
				<div class="ic-row marginT5">
					<div id="divTotal">&nbsp;</div>
						
						<div class="ic-col-100 paddR5" id="_paginationLink" style="text-align:right;">
			
						<< <a href="#" id="linkFirst" name="linkFirst" class="iCargoLink">
							<common:message key="mailtracking.defaults.mbHistory.first" />  
						</a> |
							< <a href="#" id="linkPrev" name="linkPrev" class="iCargoLink">
								<common:message key="mailtracking.defaults.mbHistory.previous" /> 
							</a> |
							  <a href="#" id="linkNext" name="linkNext" class="iCargoLink">
									<common:message key="mailtracking.defaults.mbHistory.next" /> 
								</a> > |
								<a href="#" id="linkLast" name="linkLast" class="iCargoLink">
									<common:message key="mailtracking.defaults.mbHistory.last" /> 
								</a> >> |
						</div>
				</div>
				</logic:present>
					<div class="ic-border">
						<div class="ic-row">
							<div class="ic-input  ic-split-30">
								<bean:write name="form" property="mailbagId" />
							</div>
						</div>
						<div class="ic-row">
							<div class=" ic-row ic-input  ic-split-50 ic_inline_chcekbox">
								<label> <common:message
										key="mailtracking.defaults.mbHistory.lbl.orgin" />
								</label>
								<bean:write name="form" property="ooe" />
							</div>
							<div class="ic-input  ic-split-20 ic_inline_chcekbox">
								<label> <common:message
										key="mailtracking.defaults.mbHistory.lbl.year" />
								</label>
								<bean:write name="form" property="year" />
							</div>
							<div class="ic-input  ic-split-30 ic_inline_chcekbox">
								<label> <common:message
										key="mailtracking.defaults.mbHistory.lbl.RDT" />
								</label>
								<bean:write name="form" property="reqDeliveryTime" />
							</div>							
						</div>
						<div class="ic-row">
							<div class="ic-input  ic-split-50 ic_inline_chcekbox">
								<label> <common:message
										key="mailtracking.defaults.mbHistory.lbl.destn" />
								</label>
								<bean:write name="form" property="doe" />
							</div>
							<div class="ic-input  ic-split-20 ic_inline_chcekbox">
								<label> <common:message
										key="mailtracking.defaults.mbHistory.lbl.despNo" />
								</label>
								<bean:write name="form" property="dsn" />
							</div>
							<common:xgroup>
                            <common:xsubgroup id="AA_SPECIFIC">
							<!--Added by A-7540-->
							<div class="ic-input  ic-split-30 ic_inline_chcekbox">
							<label><common:message
												key="mailtracking.defaults.mbHistory.lbl.ontimedelivery" />:
							</label>
							<% int count = 0; %>
						
							<logic:present name="mailBagHistoryVOs">
								
							<logic:iterate id="mailbagHistoryVO" name="mailBagHistoryVOs"
											type="MailbagHistoryVO" indexId="rowCount">
							<% if(count == 0){ %> 
							<logic:present name="mailbagHistoryVO" property="onTimeDelivery">
													<bean:define id="onTimeDelivery" name="mailbagHistoryVO" property="onTimeDelivery" toScope="page"/>
													<% String onTimDlvFlag = "";
														if("Y".equals(onTimeDelivery)){
															onTimDlvFlag = "Yes";
														}
														else if("N".equals(onTimeDelivery)){
															onTimDlvFlag = "No";
														}
														else{
															onTimDlvFlag = "";
														}%>
													<%=onTimDlvFlag%>
													<% count++; %>
													
							 </logic:present>
							<%}%>
							 </logic:iterate>
							 <logic:notPresent name="mailbagHistoryVO" property="onTimeDelivery">
							 &nbsp;
							 </logic:notPresent>
							 
						</logic:present>
							
							</div>
						 </common:xsubgroup> 
                         </common:xgroup >
							<!--Added as a part of ICRD-197419 by a-7540 for remarks field-->
							<div class="ic-input  ic-split-30">
								<label> <common:message
										key="mailtracking.defaults.mbHistory.lbl.mailremarks" />
								</label>
								<bean:write name="form" property="mailRemarks" />
						</div>
						</div>
						<div class="ic-row">
							<div class="ic-input  ic-split-50 ic_inline_chcekbox">
								<label> <common:message
										key="mailtracking.defaults.mbHistory.lbl.catogory" />
								</label>
								<bean:write name="form" property="catogory" />
							</div>
							<div class="ic-input  ic-split-20 ic_inline_chcekbox">
								<label> <common:message
										key="mailtracking.defaults.mbHistory.lbl.rsn" />
								</label>
								<bean:write name="form" property="rsn" />
							</div>
							<div class="ic-input  ic-split-20 ic_inline_chcekbox">
								<label> <common:message
										key="mailtracking.defaults.mbHistory.lbl.actualwt" /> <!-- Added by A-8164 for ICRD-323182 -->
								</label>
								<common:write name="form" property="actualWeightMeasure" unitFormatting="true"/>
						</div>
						</div>
						<div class="ic-row">
							<div class="ic-input  ic-split-50 ic_inline_chcekbox">
								<label> <common:message
										key="mailtracking.defaults.mbHistory.lbl.subclas" />
								</label>
								<bean:write name="form" property="mailSubclass" /> <!-- Modified. A-8164 for ICRD-257677 -->
							</div>
							<div class="ic-input  ic-split-50 ic_inline_chcekbox">
								<label> <common:message
										key="mailtracking.defaults.mbHistory.lbl.wt" />
								</label>
								<common:write name="form" property="weightMeasure" unitFormatting="true"/>
							</div>
						</div>

					</div>
					<div class="ic-row paddL5" align="left">
						<b><common:message key="mailtracking.defaults.mbHistory.lbl.details" /></b>
					</div>
					<div class="ic-row">
						<div class="tableContainer" id="div1"
							style="height: 620px;">
							<table  class="fixed-header-table" >
								<!--DWLayoutTable-->
								<thead>
									<tr class="ic-th-all">
										<th style="width: 10%" />
										<th style="width: 9%" />
										<th style="width: 12%" />
										<th style="width: 8%" />

										<th style="width: 8%" />
										<th style="width: 12%" />
										<th style="width: 10%" />
										<th style="width: 10%" />
										<th style="width: 9%" />

									</tr>
									<tr class="iCargoTableHeadingLeft">
										<td height="25" class="iCargoTableHeaderLabel"><common:message
												key="mailtracking.defaults.mbHistory.lbl.date" /></td>
										<td class="iCargoTableHeaderLabel"><common:message
												key="mailtracking.defaults.mbHistory.lbl.time" /></td>
										<td class="iCargoTableHeaderLabel"><common:message
												key="mailtracking.defaults.mbHistory.lbl.actualtime" /></td>
										<td class="iCargoTableHeaderLabel"><common:message
												key="mailtracking.defaults.mbHistory.lbl.operation" /></td>
										<td class="iCargoTableHeaderLabel"><common:message
												key="mailtracking.defaults.mbHistory.lbl.airport" /></td>
										<td class="iCargoTableHeaderLabel"><common:message
												key="mailtracking.defaults.mbHistory.lbl.flt" /></td>
										<td class="iCargoTableHeaderLabel"><common:message
												key="mailtracking.defaults.mbHistory.lbl.container" /></td>
										<td class="iCargoTableHeaderLabel"><common:message
												key="mailtracking.defaults.mbHistory.lbl.pou" /></td>
										<td class="iCargoTableHeaderLabel"><common:message
												key="mailtracking.defaults.mbHistory.lbl.userid" /></td>
									</tr>
								</thead>
								<tbody>
									<logic:present name="mailBagHistoryVOs">
										<logic:iterate id="mailbagHistoryVO" name="mailBagHistoryVOs"
											type="MailbagHistoryVO" indexId="rowCount">
												<tr>
													<td class="iCargoTableDataTd"><%=mailbagHistoryVO.getScanDate()
										.toDisplayFormat("dd-MMM-yyyy")%>
													</td>
													<td class="iCargoTableDataTd"><%=mailbagHistoryVO.getScanDate()
										.toDisplayFormat("HH:mm")%></td>
													<logic:notEmpty name="mailbagHistoryVO"
														property="messageTime">
														<td class="iCargoTableDataTd"><%=mailbagHistoryVO.getMessageTime()
											.toDisplayFormat(
													"dd-MMM-yyyy HH:mm")%>
														</td>
													</logic:notEmpty>
													<logic:empty name="mailbagHistoryVO" property="messageTime">
														<td class="iCargoTableDataTd">&nbsp;</td>
													</logic:empty>


													<td class="iCargoTableDataTd"><logic:present
															name="mailbagHistoryVO" property="mailStatus">

															<bean:define id="mailStatus" name="mailbagHistoryVO"
																property="mailStatus" />
															<logic:present name="ONETIME_STATUS">

																<logic:iterate id="oneTimeStatus" name="ONETIME_STATUS"
																	type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">

																	<logic:present name="oneTimeStatus"
																		property="fieldValue">

																		<bean:define id="fieldValue" name="oneTimeStatus"
																			property="fieldValue" />

																		<logic:equal name="oneTimeStatus"
																			property="fieldValue" value="<%=(String) mailStatus%>">

																			<bean:write name="oneTimeStatus"
																				property="fieldDescription" />

																		</logic:equal>

																	</logic:present>

																</logic:iterate>

															</logic:present>
														</logic:present></td>
													<td class="iCargoTableDataTd" ><bean:write
															name="mailbagHistoryVO" property="scannedPort" /></td>
													<td class="iCargoTableDataTd" ><bean:write
															name="mailbagHistoryVO" property="carrierCode" />&nbsp; <logic:present
															name="mailbagHistoryVO" property="flightDate">
															<bean:write name="mailbagHistoryVO"
																property="flightNumber" />&nbsp;
															<%=mailbagHistoryVO.getFlightDate()
											.toDisplayFormat("dd-MMM-yyyy")%>
														</logic:present>
														<logic:present
															name="mailbagHistoryVO" property="additionalInfo">
														<bean:write
															name="mailbagHistoryVO" property="additionalInfo" /> 
														</logic:present>
														</td>
													<td class="iCargoTableDataTd" ><logic:present
															name="mailbagHistoryVO" property="paBuiltFlag">
															<logic:equal name="mailbagHistoryVO"
																property="paBuiltFlag" value="Y">
																<bean:write name="mailbagHistoryVO"
																	property="containerNumber" />
																<logic:present
																	name="mailbagHistoryVO" property="containerType">
																<logic:equal name="mailbagHistoryVO"
																		property="containerType" value="U">
																<common:message
																	key="mailtracking.defaults.mailbaghistory.lbl.sb" />
																</logic:equal>
																</logic:present>
															</logic:equal>
															<logic:equal name="mailbagHistoryVO"
																property="paBuiltFlag" value="N">
																<bean:write name="mailbagHistoryVO"
																	property="containerNumber" />
															</logic:equal>
														</logic:present> <logic:notPresent name="mailbagHistoryVO"
															property="paBuiltFlag">
															<bean:write name="mailbagHistoryVO"
																property="containerNumber" />
														</logic:notPresent></td>
													<td class="iCargoTableDataTd" ><bean:write
															name="mailbagHistoryVO" property="pou" /></td>
													<td class="iCargoTableDataTd"><bean:write
															name="mailbagHistoryVO" property="scanUser" /></td>
												</tr>
										</logic:iterate>
									</logic:present>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="ic-foot-container">
					<div class="ic-button-container paddR10">
						<ihtml:button property="btPrint"
							componentID="BUT_MAILTRACKING_DEFAULTS_MBHISTORY_PRINT">
							<common:message key="mailtracking.defaults.mbHistory.btn.print" />
						</ihtml:button>

						<ihtml:button property="btClose"
							componentID="BUT_MAILTRACKING_DEFAULTS_MBHISTORY_CLOSE">
							<common:message key="mailtracking.defaults.mbHistory.btn.close" />
						</ihtml:button>
					</div>
				</div>
			</div>
		</ihtml:form>
	</div>

	</body>
</html:html>