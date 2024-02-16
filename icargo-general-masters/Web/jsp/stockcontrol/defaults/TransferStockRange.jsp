<%--
/***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  IN - Inventory Control
* File Name				:  TransferStock.jsp
* Date					:  13-Sep-2005
* Author(s)				:  Smrithi

*************************************************************************/
--%>
<%@ page language="java" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.TransferStockForm" %>
<%@ page import = "com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO" %>

<html:html>
<head>
		
			
	
	<bean:define id="form"
				name="TransferStockForm"
				type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.TransferStockForm"
				toScope="page" />
<title>
	<common:message bundle="<%=form.getBundle()%>" key="stockholder.title"/>
</title>
<common:include type="script" src="/js/stockcontrol/defaults/TransferStockRange_Script.jsp" />
<meta name="decorator" content="popuppanelrestyledui">
</head>
<body id="bodyStyle">
	
<div class="iCargoPopUpContent" style="height:100%;">	
<ihtml:form action="stockcontrol.defaults.screenloadtransferstockrange.do " styleClass="ic-main-form">
<ihtml:hidden property="transferFrom"/>
<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />
<ihtml:hidden property="documentRange"/>
 <ihtml:hidden property="lastPageNumber" />
<ihtml:hidden property="displayPage" />
<ihtml:hidden property="totalDocCount" />
<bean:define id="mode" name="form" property="mode" toScope="page"/>
<%
				TransferStockForm transferStockForm = (TransferStockForm)request.getAttribute("TransferStockForm");
				%>
	<div class="ic-content-main">
		<div class="ic-main-container">
			<div class="ic-row">
				<h4><common:message key="transferstockrange.TransferStockRange" /></h4>
			</div>
			
			<div class="ic-row" id="listTable">
				<div class="ic-col-100">
					<div class="ic-row">
						<div class="ic-section ic-border">
							<div class="ic-row">
								<div class="ic-input-container">
									<div class="ic-col-50">
										<div class="ic-input ic-split-100 ic-label-25">
											<label>
												<common:message key="transferstockrange.TransferFrom" />
											</label>
											<ihtml:text property="stockHolder"
											componentID="TXT_STOCKCONTROL_DEFAULTS_TRANSFERSTOCKRANGE_TRANSFERFROM"
											readonly="true" />						
										</div>
										<div class="ic-input ic-split-100 ic-label-25">
											<label>
												<common:message key="transferstockrange.DocType" />
											</label>
											<ihtml:text property="docType"
											componentID="TXT_STOCKCONTROL_DEFAULTS_TRANSFERSTOCKRANGE_DOCUMENTTYPE"
											readonly="true" />					
										</div>
									</div>
									<div class="ic-col-50">						
										<div class="ic-input ic-split-100 ic_inline_chcekbox marginT5 marginB5">
											<ihtml:checkbox property="manual"
											disabled="true"
											componentID="CHK_STOCKCONTROL_DEFAULTS_TRANSFERSTOCKRANGE_MANUAL" />
											<label>
												<common:message key="transferstockrange.Manual" />
											</label>												
										</div>
										<div class="ic-input ic-split-100">							
											<label>
												<common:message key="transferstockrange.SubType" />
											</label>	
											<ihtml:text property="subType"
											componentID="TXT_STOCKCONTROL_DEFAULTS_TRANSFERSTOCKRANGE_DOCUMENTSUBTYPE"
											readonly="true" />
										</div>
									</div>
								</div>
							</div>
							<business:sessionBean id="stockRangeVO"
									moduleName="stockcontrol.defaults"
									screenID="stockcontrol.defaults.transferstockrange" method="get"
									attribute="stockRangeVO"/>
									
<business:sessionBean id="rangeVoPage"
		moduleName="stockcontrol.defaults"
		screenID="stockcontrol.defaults.transferstockrange" method="get"
		attribute="pageRangeVO"/>
						<div class="ic-row">					
								<div class="ic-row">
									<h4><common:message key="transferstockrange.AvailableStockRange" /></h4>
								</div>
								
								<div id="divTransfer">
								<div class="ic-row">
			<div class="ic-col-50">
					<logic:present name="rangeVoPage" >
					<bean:define id="pageObj" name="rangeVoPage"  />
					<common:paginationTag pageURL="stockcontrol.defaults.transferliststockrange.do"
					name="pageObj"
					display="label"
					labelStyleClass="iCargoResultsLabel"
					lastPageNum="<%=form.getLastPageNumber() %>" />
					</logic:present>				    
				</div>
				<div class="ic-col-50">
					<div class="ic-button-container paddR5">
						<logic:present name="rangeVoPage" >
						<bean:define id="pageObj1" name="rangeVoPage"  />
						<common:paginationTag
						linkStyleClass="iCargoLink"
						disabledLinkStyleClass="iCargoLink"
						pageURL="javascript:submitPage('lastPageNum','displayPage')"
						name="pageObj1"
						display="pages"
						lastPageNum="<%=form.getLastPageNumber()%>"
						exportToExcel="true"
						exportTableId="availableTable"
						exportAction="stockcontrol.defaults.transferliststockrange.do"/>
						</logic:present>							
					</div>
				</div>	
			</div>
	<div class="ic-row">
							<div class="ic-section ic-pad-3">
								<div class="tableContainer" id="div1" style="height:200px;">
									<table class="fixed-header-table" id="availableTable" >
										<thead>
											<tr class="iCargoTableHeadingLeft">
												<td width="25%" class="iCargoTableHeaderLabel" title="Range From">
													<common:message key="transferstockrange.RangeFrom" />
												</td>
												<td width="25%" class="iCargoTableHeaderLabel" title="Range To">
													<common:message key="transferstockrange.RangeTo" />
												</td>
												<td width="25%" class="iCargoTableHeaderLabel" title="Number of Documents" style="text-transform:none">
													<common:message key="transferstockrange.NoofDocs" />
												</td>
												<td width="25%" class="iCargoTableHeaderLabel" title="Utilised Documents">Utilised Documents									   </td>
											</tr>
									    </thead>
									    <tbody>
									  <%
										int count=0;
									   %>
										   <logic:present name="rangeVoPage" >
							<bean:define id="vo" name="rangeVoPage" scope="page" toScope="page"/>
							<logic:iterate id="availablevo" name="vo" indexId="nIndex">
											<tr>
											  <td class="iCargoTableDataTd" style="text-align:right;" >
												<bean:write name="availablevo" property="startRange" />
												<bean:define id="fromRange" name="availablevo" property="startRange" type="String"/>
									
											   </td>
											  <td class="iCargoTableDataTd" style="text-align:right;" >
												<bean:write name="availablevo" property="endRange" />
											  </td>
											  <td class="iCargoTableDataTd" style="text-align:right;" >
												<bean:write name="availablevo" property="numberOfDocuments" format=""/>
											  </td>
											  <td style="text-align:center;">
										
											<logic:present name="availablevo" property="masterDocumentNumbers">				
												<img id="utilisedInfo" src="<%=request.getContextPath()%>/images/circle_yellow.png" onclick="onClickImage(<%=count%>,'<%=(String)fromRange%>');" />   
												
											</logic:present>
										</td>
											 </tr>
											 <%
											   count=count+1;
											  %>
											
										   </logic:iterate>
										 </logic:present>
										</tbody>
								   </table>
								</div>
								</div>
								</div>
								</div>
								<div class="ic-row">
									<div class="ic-col-50">								
									</div>
									<div class="ic-col-50">	
										<div class="ic-input ic-split-100 ic-label-50">								
											<label>
											<common:message key="transferstockrange.TotalAvailableStock" />
											</label>
											<ihtml:text property="totalAvailableStock" style="text-align:right;" 
											value="<%=String.valueOf(form.getTotalDocCount())%>"
											readonly="true"
											componentID="TXT_STOCKCONTROL_DEFAULTS_TRANSFERSTOCKRANGE_TOTALAVAILABLESTOCK"  />						
										</div>
									</div>
								</div>
							</div>
						</div>						
					</div>
					<div class="ic-row">
						<div class="ic-section ic-border">
							<div class="ic-row">
								<div class="ic-col-30">								
								</div>
								<div class="ic-col-60">	
									<div class="ic-input ic-mandatory ic-split-100 ic-label-20">								
										<label>
											<common:message key="transferstockrange.TransferTo" />
										</label>
										<ihtml:text property="transferTo"
										componentID="TXT_STOCKCONTROL_DEFAULTS_TRANSFERSTOCKRANGE_TRANSFERTO"
										maxlength="12" />
										<div class= "lovImg"><img height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22"
                                                                  onclick="displayLov('stockcontrol.defaults.screenloadstockholderlov.do');"/></div>							
									</div>
								</div>
							</div>
							<div class="ic-row">
								<div class="ic-col-50">
									<div class="ic-section ic-border ic-input-round-border" style="height:200px">
										<div class="ic-row">
											<div class="ic-input ic-split-100 ic_inline_chcekbox">
										<ihtml:radio property="transferMode" value="numberOfDocuments" style="text-align:right;" 
											componentID="RAD_STOCKCONTROL_DEFAULTS_TRANSFERSTOCKRANGE_TRANSFERMODENOOFDOCS" />
											<label style="margin-top:3px;">
												<common:message key="stockcontrol.defaults.transferstockrange.lbl.byNumberOfDocuments"/>
											</label>
											</div>
										</div>
										
										<div class="ic-row">
											<div class="ic-input ic-split-100 ic-label-60 ">
												<label>
													<common:message key="stockcontrol.defaults.transferstockrange.lbl.rangeFrom"/>
												</label>
												<ihtml:text property="modeRangeFrom" style="text-align:right;" 
																componentID="TXT_STOCKCONTROL_DEFAULTS_TRANSFERSTOCKRANGE_MODERANGEFROM"
																/>
											</div>
										</div>
										<div class="ic-row">
											<div class="ic-input ic-mandatory ic-split-100 ic-label-60">
												<label>
													<common:message key="stockcontrol.defaults.transferstockrange.lbl.numberOfDocuments"/>
												</label>
												<ihtml:text property="modeNumberOfDocuments" style="text-align:right;" 
																componentID="TXT_STOCKCONTROL_DEFAULTS_TRANSFERSTOCKRANGE_MODENUMBEROFDOCS"
																maxlength="7"/>
											</div>
										</div>
									</div>
								</div>
								<div class="ic-col-50">
									<div class="ic-section ic-border  " style="height:200px">
										<div class="ic-row">
											<div class="ic-input ic-split-100 ic_inline_chcekbox">
											<ihtml:radio property="transferMode" value="ranges" style="text-align:right;" 
											componentID="RAD_STOCKCONTROL_DEFAULTS_TRANSFERSTOCKRANGE_TRANSFERMODERANGE" />
											<label style="margin-top:3px;">
												<common:message key="stockcontrol.defaults.transferstockrange.lbl.byRange"/>
											</label>
											</div>
										</div>
										<div class="ic-row">
											<div class="ic-col-60">
												<h4><common:message key="transferstockrange.RangeToBeTransfered" /></h4>
											</div>
											<div class="ic-col-40">
												<div class="ic-button-container">
													<a class="iCargoLink" href="javascript:onClickAdd();" >
													<common:message key="transferstockrange.Add" /></a>
												|  <a class="iCargoLink" href="javascript:onClickDelete('check');" >
													<common:message key="transferstockrange.Delete" /></a>
												</div>
											</div>
										</div>
										<div class="ic-section ic-pad-3">
											<div class="tableContainer ic-center" id="div2" style="height:180px;">
											  <table  class="fixed-header-table" >
											 	<thead>
										   		  <tr class="iCargoTableHeadingLeft">
													<td width="10%" class="iCargoTableHeaderLabel" >
														<ihtml:checkbox property="checkall"
																		componentID="CHK_STOCKCONTROL_DEFAULTS_TRANSFERSTOCKRANGE_CHECKALL" />
													</td>
													<td width="30%" class="iCargoTableHeaderLabel" title="Range From">
														<common:message key="transferstockrange.RangeFrom" />
													</td>
													<td width="30%" class="iCargoTableHeaderLabel" title="Range To">
														<common:message key="transferstockrange.RangeTo" />
													</td>
													<td width="30%" class="iCargoTableHeaderLabel" title="Number of Documents" style="text-transform:none">
														<common:message key="transferstockrange.NoofDocs" />
													</td>
										 		   </tr>
										 		  </thead>
										 		  <tbody>
													<business:sessionBean id="collectionRangeVO" moduleName="stockcontrol.defaults"
																		screenID="stockcontrol.defaults.transferstockrange"
																		method="get" attribute="collectionRangeVO"/>

													<logic:present  name="collectionRangeVO">
														<%
															int index=0;
														%>
														<logic:iterate id="vo" name="collectionRangeVO"  indexId="iIndex">
														  
															<tr>
																<td class="iCargoTableDataTd" align="center">
																  <div align="center">
																	<ihtml:checkbox property="check"
																					value="<%= String.valueOf(index) %>"
																					componentID="CHK_STOCKCONTROL_DEFAULTS_TRANSFERSTOCKRANGE_CHECK" />
																   </div>
																  </td>
																  <td class="iCargoTableDataTd">
																	<ihtml:text property="rangeFrom" style="text-align:right;" 
																				componentID="TXT_STOCKCONTROL_DEFAULTS_TRANSFERSTOCKRANGE_RANGEFROM"
																				styleId="rangeF"
																				indexId="iIndex"
																				
																				value="<%= ((RangeVO)vo).getStartRange() %>" />
																	</td>
																	<td class="iCargoTableDataTd">
																		<ihtml:text property="rangeTo" style="text-align:right;" 
																					componentID="TXT_STOCKCONTROL_DEFAULTS_TRANSFERSTOCKRANGE_RANGETO"
																					styleId="rangeT"
																					indexId="iIndex"
																					
																					value="<%= ((RangeVO)vo).getEndRange() %>"  />
																	</td>
																	<td class="iCargoTableDataTd" >
																		<ihtml:text property="noofDocs" style="text-align:right;" 
																					styleId="noofDocs"
																					indexId="iIndex"
																					readonly="true"
																					componentID="TXT_STOCKCONTROL_DEFAULTS_TRANSFERSTOCKRANGE_NUMBEROFDOCS"
																					value="<%= new Long(((RangeVO)vo).getNumberOfDocuments()).toString() %>"/>
																	</td>
																</tr>
																<%
																	index=index+1;
																%>
															
														</logic:iterate>
													</logic:present>
												</tbody>
											</table>
										  </div>
										</div>										
									</div>
								</div>
							</div>
							
							<div class="ic-row">
									<div class="ic-col-50">								
									</div>
									<div class="ic-col-50">	
										<div class="ic-input ic-split-100 ic-label-50">								
											<label>
												<common:message key="transferstockrange.TotalnoofDocs" />
											</label>
											<ihtml:text property="totalNoOfDocs" style="text-align:right;" 
											componentID="TXT_STOCKCONTROL_DEFAULTS_TRANSFERSTOCKRANGE_TOTALNUMBEROFDOCS"
											readonly="true" />						
										</div>
									</div>
								</div>
							<div class="ic-row">
								<div class="ic-input ic-split-100 ">											
									<label>
										<common:message key="transferstockrange.Remarks" />
									</label>
									<ihtml:textarea property="remarks"
															cols="94"
															rows="2"
															componentID="TXT_STOCKCONTROL_DEFAULTS_TRANSFERSTOCKRANGE_REMARKS">
											</ihtml:textarea>
								</div>											
							</div>
						</div>
					</div>					
				</div>
			</div>
		</div>
		<div class="ic-foot-container">
			<div class="ic-row">
				<div class="ic-button-container">
				<ihtml:button property="btnTransfer"
							componentID="BTN_STOCKCONTROL_DEFAULTS_TRANSFERSTOCKRANGE_TRANSFER" >
							<common:message key="transferstockrange.transfer"/>
				</ihtml:button>
				<ihtml:button property="btnClose"
							componentID="BTN_STOCKCONTROL_DEFAULTS_TRANSFERSTOCKRANGE_CLOSE" >
							<common:message key="transferstockrange.close"/>
				</ihtml:button>
				</div>
			</div>
		</div>
	</div>
		
	
		
				  
				
           
		
<script language="javascript">
callFun('<%=(String)mode%>');
</script>

</ihtml:form>
</div>   


			
		  
	</body>
</html:html>

