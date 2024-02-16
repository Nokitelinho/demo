<%--
* Project	 		: iCargo
* Module Code & Name: ULD
* File Name			: ListULDMovement.jsp
* Date				: 31-Jan-2006
* Author(s)			: A-2122
--%>
<%@ page
	import="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm"%>
<%@ include file="/jsp/includes/tlds.jsp"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>

<html:html locale="true">
<head>
	
<title><common:message bundle="listuldmovementResources"
	key="uld.defaults.misc.listUldMovement.lbl.listUldMovementTitle" /></title>
<meta name="decorator" content="mainpanelrestyledui">
<common:include src="/js/uld/defaults/ListULDMovement_Script.jsp" type="script" />
</head>
<body>
	
<%@include file="/jsp/includes/reports/printFrame.jsp"%>

<business:sessionBean id="KEY_ULDMOVEMENTDTLS" moduleName="uld.defaults"
	screenID="uld.defaults.misc.listuldmovement" method="get"
	attribute="uldMovementDetails" />


<business:sessionBean id="LIST_FILTERVO" moduleName="uld.defaults"
	screenID="uld.defaults.misc.listuldmovement" method="get"
	attribute="uldMovementFilterVO" />


<business:sessionBean id="oneTimeValues" moduleName="uld.defaults"
	screenID="uld.defaults.misc.listuldmovement" method="get"
	attribute="oneTimeValues" />


<bean:define id="form" name="listULDMovementForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm"
	toScope="page" />

<div class="iCargoContent"  style = "width:100%;height:100%;overflow:auto;">
<ihtml:form	action="/uld.defaults.misc.screenloadlistuldmovement.do" styleClass="ic-main-form">
<ihtml:hidden property="selectFlag" />
<ihtml:hidden property="statusFlag" />
<ihtml:hidden property="screenloadstatus" />
<ihtml:hidden property="listStatus" />
<ihtml:hidden property="transactionFlag"/>
<ihtml:hidden property="isUldValid"/>
<ihtml:hidden property="lastPageNum" />
<ihtml:hidden property="displayPage" />
<ihtml:hidden property="movementStatusFlag" />
<div class="ic-content-main">
		<span class="ic-page-title ic-display-none"><common:message
						key="uld.defaults.misc.listUldMovement.lbl.listUldMovementHeading" /></span>
		<div class="ic-head-container">
			<div class="ic-filter-panel">
				<div class="ic-input-container">
					<div class="ic-row">
									<div class="ic-input ic-mandatory ic-split-30">
								<label><common:message
									key="uld.defaults.misc.listUldMovement.lbl.ULDNumber" /> 
								</label>
								<logic:present name="LIST_FILTERVO"
										property="uldNumber">
										<bean:define id="uldNo" name="LIST_FILTERVO"
											property="uldNumber" />
											<ibusiness:uld id="uldno" uldProperty="uldNumber" uldValue="<%=uldNo.toString()%>"  componentID="CMP_ULD_DEFAULTS_MISC_LISTULDMOVEMENT_ULDNO" style="text-transform: uppercase"/>
									</logic:present> <logic:notPresent name="LIST_FILTERVO"
										property="uldNumber">
										<ibusiness:uld id="uldno" uldProperty="uldNumber" componentID="CMP_ULD_DEFAULTS_MISC_LISTULDMOVEMENT_ULDNO" style="text-transform: uppercase"/>
									</logic:notPresent>
								</div>

								<div class="ic-input ic-split-25">
								<label><common:message
									key="uld.defaults.misc.listUldMovement.lbl.FromDate" />
								</label>
								
													<logic:present name="LIST_FILTERVO"
														property="fromDate">
														<%System.out.println("present");%>
														<bean:define id="listFilter" name="LIST_FILTERVO"
															type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO"
															toScope="page" />
														<%String fromDate = "";
									if (listFilter.getFromDate() != null) {
										fromDate = TimeConvertor.toStringFormat(listFilter
												.getFromDate().toCalendar(),
												TimeConvertor.CALENDAR_DATE_FORMAT);
									}

									%>
														<ibusiness:calendar property="fromDate" type="image"
															id="fromDate"
															componentID="CMP_ULD_DEFAULTS_MISC_LISTULDMOVEMENT_FROMDATE"
															value="<%=fromDate%>" />
													</logic:present> 
							
											<logic:notPresent name="LIST_FILTERVO"
												property="fromDate">
												<%System.out.println("notpresent");%>
												<ibusiness:calendar property="fromDate" type="image"
													id="fromDate"
													componentID="CMP_ULD_DEFAULTS_MISC_LISTULDMOVEMENT_FROMDATE"
													 />
									 
											</logic:notPresent>
								</div>

								<div class="ic-input ic-split-25">
								<label><common:message
										key="uld.defaults.misc.listUldMovement.lbl.ToDate" />
								</label>
									<logic:present name="LIST_FILTERVO"	property="toDate">
											<bean:define id="listFilter" name="LIST_FILTERVO"
												type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO"
												toScope="page" />
											<%String toDate = "";
											if (listFilter.getToDate() != null) {
											toDate = TimeConvertor.toStringFormat(listFilter.getToDate()
											.toCalendar(), TimeConvertor.CALENDAR_DATE_FORMAT);
											}
											%>
										<ibusiness:calendar property="toDate" type="image" id="toDate"
											componentID="CMP_ULD_DEFAULTS_MISC_LISTULDMOVEMENT_TODATE"
											value="<%=toDate%>" />
									</logic:present> 
									<logic:notPresent name="LIST_FILTERVO" property="toDate">									
										<ibusiness:calendar property="toDate" type="image" id="toDate"
											componentID="CMP_ULD_DEFAULTS_MISC_LISTULDMOVEMENT_TODATE"
											value="" />
									</logic:notPresent>
								</div>

								<div class="ic-input ic-split-20">
									<div class="ic-button-container">
								<ihtml:nbutton property="btList"
									componentID="CMP_ULD_DEFAULTS_LISTULDMOVEMENT_LIST_BUTTON" accesskey="L">
									<common:message
										key="uld.defaults.misc.listUldMovement.btn.btList" />
									</ihtml:nbutton> <ihtml:nbutton property="btClear"
										componentID="CMP_ULD_DEFAULTS_LISTULDMOVEMENT_CLEAR_BUTTON" accesskey="E">
										<common:message
											key="uld.defaults.misc.listUldMovement.btn.btClear" />
									</ihtml:nbutton>
								</div>
						    </div>
					    </div>
				    </div>
				</div>
				
		</div>
	<div class="ic-main-container">
			<div class="ic-row">
				<h4><common:message
								key="uld.defaults.misc.listUldMovement.lbl.listUldMovementHeadinglabell" />
							</h4>
					</div>
			<div class="ic-row">
				<div class="ic-input-container ic-input-round-border">
			<div class="ic-row">	
						<div class="ic-input ic-split-25 ic-label-35">
					 <label><common:message key="uld.defaults.misc.listUldMovement.lbl.noOfMovements"/>
					</label>
					 <ihtml:text componentID="CMP_ULD_DEFAULTS_MISC_LISTULDMOVEMENT_OWNERSTATION"  property="noOfMovements" name="form" style="text-align:right"  disabled="true"/>
				</div>
						<div class="ic-input ic-split-25">
					 <label><common:message key="uld.defaults.misc.listUldMovement.lbl.noOfLoanTxns"/>
					 </label>
					<ihtml:text componentID="CMP_ULD_DEFAULTS_MISC_LISTULDMOVEMENT_OWNERSTATION"  property="noOfLoanTxns" name="form" style="text-align:right"  disabled="true"/>
				</div>
				<div class="ic-input ic-split-25">
					 <label> <common:message key="uld.defaults.misc.listUldMovement.lbl.noOfTimesDmged"/>
					 </label>
					 <ihtml:text componentID="CMP_ULD_DEFAULTS_MISC_LISTULDMOVEMENT_OWNERSTATION"  property="noOfTimesDmged" name="form" style="text-align:right"  disabled="true"/>
				</div>
						<div class="ic-input ic-split-25 ic-label-35">
					 <label><common:message key="uld.defaults.misc.listUldMovement.lbl.noOfTimesRepaired"/>
					</label>
					 <ihtml:text componentID="CMP_ULD_DEFAULTS_MISC_LISTULDMOVEMENT_OWNERSTATION"  property="noOfTimesRepaired" name="form"  style="text-align:right" disabled="true"/>
					</div>
				</div>
				</div>
			</div>
			<div class="ic-row">	
			
				<div class="ic-input ic-split-70 paddL10">
						<ihtml:select property="movement"
						componentID="CMP_ULD_DEFAULTS_LISTULDMOVEMENT_MOVEMENT" > 
						<html:option value="External Mvmts"><common:message key="uld.defaults.misc.listUldMovement.lbl.Movement.external"/></html:option>
						<html:option value="Internal Mvmts"><common:message key="uld.defaults.misc.listUldMovement.lbl.Movement.internal"/></html:option>
						<html:option value="BulidUp/BreakDown Dtls"><common:message key="uld.defaults.misc.listUldMovement.lbl.Movement.buildup"/></html:option>
						<html:option value="Damage/Repair Dtls"><common:message key="uld.defaults.misc.listUldMovement.lbl.Movement.damage"/></html:option>
						<html:option value="Transaction Dtls"><common:message key="uld.defaults.misc.listUldMovement.lbl.Movement.transaction"/></html:option>
						</ihtml:select>
						</div>
			</div>
				<div class="ic-row ic marginT5">				
					
					<div id="uldHistoryContents"  >
					<div id="div1" class="tableContainer" style="height:620px">

                      <table id="movementdetails" class="fixed-header-table" >
					
								
							<thead> 
							<tr class="ic-th-all">
						<!--	 <th style="width:8%"/> -->
						  <th style="width:5%" />
						  <th style="width:5%" />
						  <th style="width:5%" />
						  <th style="width:5%"/>
						  <th style="width:12%" />
						  <th style="width:12%" />
						  <th style="width:12%"/>
						  <th style="width:16%"/>
						  
						</tr>
										<tr class="iCargoTableHeadingLeft">
											
											<td colspan="2" class="iCargoTableHeader" ><common:message
												key="uld.defaults.misc.uldbld.lbl.FlightNo"
												scope="request" /> <span></span></td>
											<td colspan="2" class="iCargoTableHeader" ><common:message
												key="uld.defaults.misc.uldbld.lbl.FlightDate"
												scope="request" /> <span></span></td>
											<td rowspan="2" class="iCargoTableHeader"><common:message
												key="uld.defaults.misc.listUldMovement.lbl.Contents"
scope="request" /><span></span></td>
											<td rowspan="2" class="iCargoTableHeader"><common:message
												key="uld.defaults.misc.listUldMovement.lbl.FromAirport"
scope="request" /><span></span></td>
											<td rowspan="2" class="iCargoTableHeader" ><common:message
												key="uld.defaults.misc.listUldMovement.lbl.ToAirport"
scope="request" /><span></span></td>
											<td rowspan="2" class="iCargoTableHeader" ><common:message
												key="uld.defaults.misc.listUldMovement.lbl.MovementType"
scope="request" /><span></span></td>
											<td rowspan="2" class="iCargoTableHeader" ><common:message
												key="uld.defaults.misc.listUldMovement.lbl.MovementDate"
scope="request" /><span></span></td>
											<td rowspan="2" class="iCargoTableHeader" ><common:message
												key="uld.defaults.misc.listUldMovement.lbl.remarks"
scope="request" /><span></span></td>
										</tr>
										<!--<tr class="iCargoTableHeaderLabel">
											<td class="iCargoTableHeader" ><common:message
												key="uld.defaults.misc.listUldMovement.lbl.CarrierCode"
												scope="request" /></td>
											<td class="iCargoTableHeader" ><common:message
												key="uld.defaults.misc.listUldMovement.lbl.No"
												scope="request" /></td>
											
											<td class="iCargoTableHeader" ><common:message
												key="uld.defaults.misc.listUldMovement.lbl.FltDate"
												scope="request" /></td>
										</tr> -->

							</thead>
							<tbody >
							
							</tbody>
						</table>
					</div>
					</div>
				</div>
			</div>
			
			<div class="ic-foot-container">	
				<div class="ic-button-container paddR10">
					<ihtml:nbutton property="btHistory"
						componentID="TXT_ULD_DEFAULTS_LISTULDMOVEMENT_HISTORY_BUTTON" accesskey="H">
						<common:message
							key="uld.defaults.misc.listUldMovement.btn.btHistory" />
					</ihtml:nbutton> 
					<ihtml:nbutton property="btReport"
						componentID="TXT_ULD_DEFAULTS_LISTULDMOVEMENT_GENERATE_BUTTON" accesskey="G">
						<common:message
							key="uld.defaults.misc.listUldMovement.btn.btGenerate" />
					</ihtml:nbutton> 		
					<ihtml:nbutton property="btRecord"
						componentID="TXT_ULD_DEFAULTS_LISTULDMOVEMENT_REC_BUTTON" accesskey="R">
						<common:message key="uld.defaults.misc.listUldMovement.btn.btRecord" />
					</ihtml:nbutton> 
					<ihtml:nbutton property="btClose"
						componentID="TXT_ULD_DEFAULTS_LISTULDMOVEMENT_CLOSE_BUTTON" accesskey="O">
						<common:message key="uld.defaults.misc.listUldMovement.btn.btClose" />
					</ihtml:nbutton>
					</div>
			</div>
	</div>
	
</ihtml:form>
</div>

				
			

		

		
	</body>
</html:html>


