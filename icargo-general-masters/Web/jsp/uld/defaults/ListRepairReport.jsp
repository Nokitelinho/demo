<%--
/***********************************************************************
* Project       	 :  iCargo
* Module Code & Name :  Uld
* File Name     	 :  ListRepairReport.jsp
* Date          	 :  06-NOV-2015
* Author(s)     	 :  REMYA K
*************************************************************************/
 --%>


<%@ page import="com.ibsplc.xibase.server.framework.persistence.query.Page"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import = "java.util.Calendar" %>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListRepairReportForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<common:include type="script" src="/js/uld/defaults/ListRepairReport_Script.jsp" />
<html:html locale="true">
<head>
		
		
	

<title>iCargo:List Repair Report</title>

<meta name="decorator" content="mainpanelrestyledui">

<%@include file="/jsp/includes/reports/printFrame.jsp" %>
</head>

<body>
	
	

<bean:define id="form"
	 name="ListRepairReportForm"
	 type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListRepairReportForm"
	 toScope="page" />


<business:sessionBean id="repairHeadStatus"
		   moduleName="uld.defaults"
		screenID="uld.defaults.listrepairreport" method="get"
	attribute="repairHead"/>


<business:sessionBean id="StatusCollection"
	        moduleName="uld.defaults"
		screenID="uld.defaults.listrepairreport" method="get"
	attribute="uldStatus"/>

	<business:sessionBean id="repairStatusCollection"
		    moduleName="uld.defaults"
		screenID="uld.defaults.listrepairreport" method="get"
	attribute="repairStatus"/>


	<business:sessionBean id="repairDetailsCollection"
		        moduleName="uld.defaults"
			screenID="uld.defaults.listrepairreport" method="get"
		attribute="uLDDamageRepairDetailsVOs"/>

	<business:sessionBean id="uLDRepairFilterVO" moduleName="uld.defaults" screenID="uld.defaults.listrepairreport" method="get" attribute="uLDRepairFilterVO" />
	    <logic:present name="uLDRepairFilterVO">
		    <bean:define id="uLDRepairFilterVO" name="uLDRepairFilterVO" />
	    </logic:present>

<div class="iCargoContent ic-masterbg" id="pageDiv">
    <ihtml:form action="/uld.defaults.screenloadlistrepairreport.do">
		<ihtml:hidden property="displayPage" />
		<ihtml:hidden property="lastPageNumber" />
		<ihtml:hidden property="repairDisableStatus" />
		<ihtml:hidden property="currencyValue" />
		<input type="hidden" name="currentDialogId" />
		<input type="hidden" name="currentDialogOption" />
		<input type="hidden" name="mySearchEnabled" />
		<ihtml:hidden property="invoiceId" />
		<ihtml:hidden property="listStatus" />
			
			<div class="ic-content-main">
	            <span class="ic-page-title ic-display-none">
		            <bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listrepairreports" scope="request"/> 	
                </span>
		         	<div class="ic-head-container" >  
				        <div class="ic-filter-panel">
							<div class="ic-row">					    
								<h3>	
									<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.searchcriteria" scope="request"/>
								</h3>					   
							</div>		
				            <div class="ic-row">
								<div class="ic-input ic-label-30 ic-split-25">
									<label>
										<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.uldno" scope="request"/>
									</label>
									<logic:present name="uLDRepairFilterVO" property="uldNumber" >
										<bean:define id="uldNumber" name="uLDRepairFilterVO" property="uldNumber"/>
										<ibusiness:uld id="uldrepair" uldProperty="uldNo" uldValue="<%=uldNumber.toString()%>"  componentID="TXT_ULD_DEFAULTS_LISTREPAIRREPORT_ULDNO" style="text-transform: uppercase" maxlength="12"/>
									</logic:present>
									<logic:notPresent name="uLDRepairFilterVO" property="uldNumber" >
									   <ibusiness:uld id="uldrepair" uldProperty="uldNo" componentID="TXT_ULD_DEFAULTS_LISTREPAIRREPORT_ULDNO" style="text-transform: uppercase" maxlength="12"/>
									</logic:notPresent>
								</div>
				                <div class="ic-input ic-label-30 ic-split-25">
							    <label>
							        <bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.repairHead" scope="request"/>
							    </label>
							    <logic:present name="uLDRepairFilterVO" property="repairHead" >
			                        <bean:define id="repairHead" name="uLDRepairFilterVO" property="repairHead"/>
										<ihtml:select componentID="CMB_ULD_DEFAULTS_LISTREP_REPHD" property="repairHead" value="<%=repairHead.toString()%>">
										<ihtml:option value="">ALL</ihtml:option>
										<logic:present name="repairHeadStatus">
											<bean:define id="repairHeadValues" name="repairHeadStatus"/>
										    <ihtml:options collection="repairHeadValues" property="fieldValue" labelProperty="fieldDescription"/>
										</logic:present>
										</ihtml:select>
			                    </logic:present>
       
			                   <logic:notPresent name="uLDRepairFilterVO" property="repairHead" >
                                    <ihtml:select componentID="CMB_ULD_DEFAULTS_LISTREP_REPHD" property="repairHead" >
			                        <ihtml:option value="">ALL</ihtml:option>
			                    <logic:present name="repairHeadStatus">
				                    <bean:define id="repairHeadValues" name="repairHeadStatus"/>
			                        <ihtml:options collection="repairHeadValues" property="fieldValue" labelProperty="fieldDescription"/>
								</logic:present>
									</ihtml:select>
								</logic:notPresent>
							   </div>
								<div class="ic-input ic-label-30 ic-split-25">
									<label>
										<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.uldtypecode" scope="request"/>
									</label>
									<logic:present name="uLDRepairFilterVO" property="uldTypeCode" >
										<bean:define id="uldTypeCode" name="uLDRepairFilterVO" property="uldTypeCode"/>
										<ihtml:text componentID="TXT_ULD_DEFAULTS_LISTREPAIRREPORT_ULDTYPECODE" property="uldTypeCode" name="ListRepairReportForm" style="text-transform : uppercase" maxlength="5" value="<%=uldTypeCode.toString()%>"/>
										<div class="lovImg">
											<img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"
										onclick="displayLOV('showUld.do','N','Y','showUld.do',document.forms[1].uldTypeCode.value,'uldTypeCode','1','uldTypeCode','',0)" alt="ULD Type LOV"/>
										</div>
									</logic:present>
										<logic:notPresent name="uLDRepairFilterVO" property="uldTypeCode" >

									<ihtml:text componentID="TXT_ULD_DEFAULTS_LISTREPAIRREPORT_ULDTYPECODE" property="uldTypeCode" name="ListRepairReportForm" style="text-transform : uppercase" maxlength="5" />
									<div class="lovImg">
										<img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"
									onclick="displayLOV('showUld.do','N','Y','showUld.do',document.forms[1].uldTypeCode.value,'uldTypeCode','1','uldTypeCode','',0)" alt="ULD Type LOV"/>
									</div>
									</logic:notPresent>
								</div>
								<div class="ic-input ic-label-30 ic-split-25">
									<label>
										<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.currentstation" scope="request"/>
									</label>
										<logic:present name="uLDRepairFilterVO" property="currentStation" >
											<bean:define id="currentStation" name="uLDRepairFilterVO" property="currentStation"/>
											<ihtml:text componentID="TXT_ULD_DEFAULTS_LISTREPAIRREPORT_CURRENTSTN" property="currentStn" name="ListRepairReportForm" style="text-transform : uppercase" maxlength="3" value="<%=currentStation.toString()%>"/>
											<div class="lovImg">
												<img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" id="airportLovImg" name="airportLovImg" 
											onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].currentStn.value,'CurrentAirport','1','currentStn','',0)" alt="Airport LOV"/>
											</div>
										</logic:present>

										<logic:notPresent name="uLDRepairFilterVO" property="currentStation" >
											<ihtml:text componentID="TXT_ULD_DEFAULTS_LISTREPAIRREPORT_CURRENTSTN" property="currentStn" name="ListRepairReportForm" style="text-transform : uppercase" maxlength="3" />
											<div class="lovImg">
												<img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" id="airportLovImg" name="airportLovImg" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].currentStn.value,'CurrentAirport','1','currentStn','',0)" alt="Airport LOV"/>
											</div>
										</logic:notPresent>
								</div>
						   </div>
							<div class="ic-row">
								<div class="ic-input ic-label-30 ic-split-25">
									<label>
										<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.uldstatus" scope="request"/>
									</label>
										<logic:present name="uLDRepairFilterVO" property="uldStatus" >
											<bean:define id="uldStatus" name="uLDRepairFilterVO" property="uldStatus"/>
											<ihtml:select property="uldStatus" componentID="CMB_ULD_DEFAULTS_LISTREP_ULDSTAT" value="<%=uldStatus.toString()%>">
											<ihtml:option value="">ALL</ihtml:option>
											<logic:present name="StatusCollection">
											<bean:define id="statusValues" name="StatusCollection"/>
											<ihtml:options collection="statusValues" property="fieldValue" labelProperty="fieldDescription"/>
											</logic:present>
											</ihtml:select>
										</logic:present>
										<logic:notPresent name="uLDRepairFilterVO" property="uldStatus" >
											<ihtml:select property="uldStatus" componentID="CMB_ULD_DEFAULTS_LISTREP_ULDSTAT" >
											<ihtml:option value="">ALL</ihtml:option>
											<logic:present name="StatusCollection">
											<bean:define id="statusValues" name="StatusCollection"/>
											<ihtml:options collection="statusValues" property="fieldValue" labelProperty="fieldDescription"/>
											</logic:present>
											</ihtml:select>
										</logic:notPresent>
								</div>
								<div class="ic-input ic-label-30 ic-split-25">
									<label>
										<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.repairStatus" scope="request"/>
									</label>
										<logic:present name="uLDRepairFilterVO" property="repairStatus" >
											<bean:define id="repairStatus" name="uLDRepairFilterVO" property="repairStatus"/>
											<ihtml:select property="repairStatus" componentID="CMB_ULD_DEFAULTS_LISTREP_REPSTAT" value="<%=repairStatus.toString()%>">
												<ihtml:option value="">ALL</ihtml:option>
													<logic:present name="repairStatusCollection">
														<bean:define id="repairStatusValues" name="repairStatusCollection"/>
														<ihtml:options collection="repairStatusValues" property="fieldValue" labelProperty="fieldDescription"/>
													</logic:present>
											</ihtml:select>
										</logic:present>
										<logic:notPresent name="uLDRepairFilterVO" property="repairStatus" >
											<ihtml:select  property="repairStatus" componentID="CMB_ULD_DEFAULTS_LISTREP_REPSTAT" >
												<ihtml:option value="">ALL</ihtml:option>
													<logic:present name="repairStatusCollection">
														<bean:define id="repairStatusValues" name="repairStatusCollection"/>
														<ihtml:options collection="repairStatusValues" property="fieldValue" labelProperty="fieldDescription"/>
													</logic:present>
											</ihtml:select>
										</logic:notPresent>
								</div>
								<div class="ic-input ic-label-40 ic-split-20">	
									<label>
										<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.repairedstation" scope="request"/>	
									</label>
									<logic:present name="uLDRepairFilterVO" property="repairStation" >
										<bean:define id="repairStation" name="uLDRepairFilterVO" property="repairStation"/>
										<ihtml:text componentID="TXT_ULD_DEFAULTS_LISTREPAIRREPORT_REPORTEDSTN" property="repairedStn" name="ListRepairReportForm" style="text-transform : uppercase" maxlength="3" value="<%=repairStation.toString()%>"/>
										<div class="lovImg">
											<img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" name="airportLov" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].repairedStn.value,'CurrentAirport','1','repairedStn','',0)" alt="Airport LOV"/>
										</div>
									</logic:present>
									<logic:notPresent name="uLDRepairFilterVO" property="repairStation" >
										<ihtml:text componentID="TXT_ULD_DEFAULTS_LISTREPAIRREPORT_REPORTEDSTN" property="repairedStn" name="ListRepairReportForm" style="text-transform : uppercase" maxlength="3" />
										<div class="lovImg">
											<img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" name="airportLov" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].repairedStn.value,'CurrentAirport','1','repairedStn','',0)" alt="Airport LOV"/>
										</div>
									</logic:notPresent>
								</div>	
						   </div>	
							<div class="ic-row">
								<div class="ic-input ic-label-30 ic-split-25">	
									<label>							
										<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.repaireddatefrom" scope="request"/>
									</label>
									<logic:present name="uLDRepairFilterVO" property ="fromDate">
										<%String frmDate="";%>
										<bean:define id="fromDate" name="uLDRepairFilterVO" property ="fromDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
										<%  frmDate=TimeConvertor.toStringFormat(fromDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
										<ibusiness:calendar   id="repairedDateFrom" property="repairedDateFrom" componentID="ULD_DEFAULTS_LISTREP_FROMDATE" type="image" value="<%=frmDate%>"/>
									</logic:present>
									<logic:notPresent name="uLDRepairFilterVO" property ="fromDate">
										<ibusiness:calendar   id="repairedDateFrom" property="repairedDateFrom" componentID="ULD_DEFAULTS_LISTREP_FROMDATE" type="image" />
									</logic:notPresent>
								</div>
								<div class="ic-input ic-label-25 ic-split-30">
									<label>
										<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.repaireddateto" scope="request"/>
									</label>
									<logic:present name="uLDRepairFilterVO" property ="toDate">
										<%String toDat="";%>
										<bean:define id="toDate" name="uLDRepairFilterVO" property ="toDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
										<%  toDat=TimeConvertor.toStringFormat(toDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
										<ibusiness:calendar  id="repairedDateTo" property="repairedDateTo" componentID="ULD_DEFAULTS_LISTREP_TODATE" value="<%=toDat%>" type="image" />
									</logic:present>
									<logic:notPresent name="uLDRepairFilterVO" property ="toDate">
										<ibusiness:calendar  id="repairedDateTo" property="repairedDateTo" componentID="ULD_DEFAULTS_LISTREP_TODATE" type="image" />
									</logic:notPresent>
								</div>
								<div class="ic-button-container">
									<ihtml:nbutton property="btList" accesskey="L" componentID="BTN_ULD_DEFAULTS_LISTREPAIRREPORT_LIST">
										<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.list" scope="request"/>
									</ihtml:nbutton>
									<ihtml:nbutton property="btClear" accesskey="C" componentID="BTN_ULD_DEFAULTS_LISTREPAIRREPORT_CLEAR">
										<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.clear" scope="request"/>
									</ihtml:nbutton>
								</div>
				            </div>
								<div class="ic-row ic-label-45" style="padding-top:15px;">	 <!--Added by A-7359 for ICRD - 224586 starts here	-->
				        </div>
				        </div>
				    </div>
			<div class="ic-main-container">
			 <a class="panel upArrow"  collapseFilter="true"  collapseFilterCallbackFun="callbackListRepair"  href="#"></a> <!--Added by A-7359 for ICRD - 224586 starts here -->
			<div class="ic-row">
			            <logic:present name="repairDetailsCollection">
			                <common:paginationTag
							pageURL="javascript:submitList('lastPageNum','displayPage')"
							name="repairDetailsCollection"
							display="label"
							labelStyleClass="iCargoResultsLabel"
							lastPageNum="<%=form.getLastPageNumber() %>" />
			            </logic:present>
					
				    <div class="ic-button-container">
						<logic:present name="repairDetailsCollection">
							<common:paginationTag
							pageURL="javascript:submitList('lastPageNum','displayPage')"
							name="repairDetailsCollection"
							display="pages"
							linkStyleClass="iCargoLink"
							disabledLinkStyleClass="iCargoLink"
							lastPageNum="<%=form.getLastPageNumber()%>"
							exportToExcel="true"
							exportTableId="repairreporttable"
							exportAction="uld.defaults.listrepairreport.do"/>
						</logic:present>
				    </div>
				</div>
				<div class="ic-row">
				    <div class="tableContainer" "ic-center" id="div1" style="height:490px" >	<!--Modified by A-7359 for ICRD - 224586 starts here	-->
		     	    <table class="fixed-header-table" id="repairreporttable">
                    <thead>
						<tr class="iCargoTableHeadingLeft">
							<td width="20" rowspan="2" class="iCargoTableHeaderLabel">
								<input type="checkbox" name="masterRowId" value="all">
								<span></span>
							</td>
                            <td class="iCargoTableHeaderLabel">
		     	                <bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.uldno" scope="request"/>
                                    <span></span>
						   </td>
                            <td>
			                    <bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.dmgrefno" scope="request"/>
                                   <span></span>
						    </td>

		     	            <td class="iCargoTableHeaderLabel">
		     	                <bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.repairHead" scope="request"/>
                                  <span></span>
						    </td>

		     	            <td>
		     	                <bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.currentstation" scope="request"/>
                                  <span></span>
						   </td>

		     	            <td>
		     	                <bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.repairedstation" scope="request"/>
                                 <span></span>
						    </td>

							<td>
							    <bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.repairdate" scope="request"/>
			                     <span></span>
							</td>

							<td>
						        <bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.repairStatus" scope="request"/>
			                      <span></span>
	                        </td>

							<td>
							    <bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.uldstatus" scope="request"/>
			                     <span></span>
			                </td>

							<td>
							    <bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.amount" scope="request"/>(<%=form.getCurrencyValue() %>)
			                    <span></span>
							</td>
                            <td>
							   <bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.remarks" scope="request"/>
			                   <span></span>
			                </td>

						</tr>
		     	</thead>
                <tbody>
                    <% double totalrepairamnt=0 ;%>
				        <logic:present name="repairDetailsCollection" >
				            <logic:iterate id="repairDetailsVO" name="repairDetailsCollection" indexId="nIndex" type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairDetailsListVO">
					        <tr>
							 <td class="iCargoTableDataTd">
								<input type="checkbox" name="check" property="check" value="<%=String.valueOf(nIndex)%>"/></td>
									<td  class="iCargoTableDataTd">
										 <input type="hidden" name="uldNoTable" value="<%=repairDetailsVO.getUldNumber()%>"/>
											   <input type="hidden" name="repairHeadTable" value="<%=String.valueOf(repairDetailsVO.getRepairSequenceNumber())%>"/>
													<%String rDate="";%>
													<%  rDate=TimeConvertor.toStringFormat(repairDetailsVO.getRepairDate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
														<input type="hidden" name="repairDateTable" value="<%=rDate%>"/>
														<input type="hidden" name="currentStnTable" value="<%=String.valueOf(repairDetailsVO.getInvoiceReferenceNumber())%>"/>
													 <%
													 %>
													<bean:write name="repairDetailsVO" property="uldNumber"/>
											  </td>
													<td  class="iCargoTableDataTd" "ic-right">
														<logic:present name="repairDetailsVO" property="damageReferenceNumber">
															<bean:write name="repairDetailsVO" property="damageReferenceNumber"/>
														</logic:present>
														<logic:notPresent name="repairDetailsVO" property="damageReferenceNumber">
														
														</logic:notPresent>
													</td>
													<td  class="iCargoTableDataTd">
														<logic:present name="repairDetailsVO" property="repairHead">
															<bean:write name="repairDetailsVO" property="repairHead"/>
														</logic:present>
														<logic:notPresent name="repairDetailsVO">
														  
														</logic:notPresent>
													</td>
													<td  class="iCargoTableDataTd">
														<bean:write name="repairDetailsVO" property="currentStation"/></td>
													<td  class="iCargoTableDataTd" >
														<logic:present name="repairDetailsVO">
															<bean:write name="repairDetailsVO" property="repairedStation"/>
														</logic:present>
														<logic:notPresent name="repairDetailsVO">
														 
														</logic:notPresent>
													</td>

													<td class="iCargoTableDataTd" >
													<logic:present name="repairDetailsVO" property="repairDate">
														<%=repairDetailsVO.getRepairDate().toDisplayDateOnlyFormat()%>
													</logic:present>
													<logic:notPresent name="repairDetailsVO" property="repairDate">
													   
													</logic:notPresent>
													</td>
												   <td class="iCargoTableDataTd">
														<logic:present  name="repairDetailsVO" property="repairStatus">
															<bean:define id="repairStatus" name="repairDetailsVO" property="repairStatus" />
																<logic:present name="repairStatusCollection">
																	<logic:iterate id="StatusIterator" name="repairStatusCollection" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																		<bean:define id="StatusIterator" name="StatusIterator" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"/>
																			<% if(StatusIterator.getFieldValue().equals(repairStatus)){%>
																			<bean:write name="StatusIterator" property="fieldDescription"/>
																			<%}%>
																	</logic:iterate>
																</logic:present>
														</logic:present>
														<logic:notPresent name="repairStatusCollection">
														</logic:notPresent>
													</td>
												   <td  class="iCargoTableDataTd" >
														<logic:present  name="repairDetailsVO" property="overallStatus">
															<bean:define id="overallStatus" name="repairDetailsVO" property="overallStatus" />
																	<logic:present name="StatusCollection">
																		<logic:iterate id="StatusIterator" name="StatusCollection" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																			<bean:define id="StatusIterator" name="StatusIterator" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"/>
																			<% if(StatusIterator.getFieldValue().equals(overallStatus)){%>
																			<bean:write name="StatusIterator" property="fieldDescription"/>
																			<%}%>
																		</logic:iterate>
																	</logic:present>
														</logic:present>
														<logic:notPresent name="StatusCollection">
														</logic:notPresent>
													</td>
													<td  class="iCargoTableDataTd ic-right">
														<logic:present name="repairDetailsVO" property="repairAmount">
															<bean:write name="repairDetailsVO" property="repairAmount" localeformat="true"/>
															<bean:define id="repairAmountvalue" name="repairDetailsVO" property="repairAmount" />
															<%totalrepairamnt=totalrepairamnt+(Double.parseDouble(String.valueOf(repairAmountvalue)));%>
														</logic:present>
														<logic:notPresent name="repairDetailsVO" property="repairAmount">
															
														</logic:notPresent>
													</td>

													<td  class="iCargoTableDataTd">
														<logic:present name="repairDetailsVO" property="remarks">
															<common:write name="repairDetailsVO" property="remarks" splitLength="30"/>
														</logic:present>
														<logic:notPresent name="repairDetailsVO" property="remarks">
														   
														</logic:notPresent>
													</td>
											 </tr>
										</logic:iterate>
										</logic:present>


			    </tbody>
	        </table>
			</div>	
			</div>
			<div class="ic-label-50 ic-inline-label">	<!--Modified by A-7359 for ICRD - 224586 starts here	-->
				<div class="ic-input ic-split-100">
					<label>
						Total Repair Cost &nbsp;&nbsp;
					</label>
						<bean:define id="totalrepairamntval" value="<%=String.valueOf(totalrepairamnt)%>"/>
						<bean:write name="totalrepairamntval" localeformat="true" />	
				</div>
			</div>	
			</div>	
			<div class="ic-foot-container paddR5">	 
				<div class="ic-button-container" style="padding-bottom: 10px!important">		
					<ihtml:nbutton property="btprint" accesskey="P" componentID="BTN_ULD_DEFAULTS_LISTREPAIRREPORT_PRINT">
						<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.print" scope="request"/>
					</ihtml:nbutton>
					<ihtml:nbutton property="btgenerateinvoice" accesskey="G" componentID="BTN_ULD_DEFAULTS_LISTREPAIRREPORT_GENINVOICE">
						<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.generateinvoice" scope="request"/>
					</ihtml:nbutton>
					<ihtml:nbutton property="btUldDetails" accesskey="U" componentID="BTN_ULD_DEFAULTS_LISTREPAIRREPORT_ULDDETAILS">
						<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.ulddetails" scope="request"/>
					</ihtml:nbutton>
					<ihtml:nbutton property="btClose" accesskey="O" componentID="BTN_ULD_DEFAULTS_LISTREPAIRREPORT_CLOSE">
						<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.close" scope="request"/>
					</ihtml:nbutton>
				</div>
		    </div>
	        
           
	</ihtml:form>
	
</div>
     
	</body>
</html:html>

