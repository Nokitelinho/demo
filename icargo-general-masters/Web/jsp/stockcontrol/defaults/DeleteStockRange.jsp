<%--
/***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  IN - Inventory Control
* File Name				:  DeleteStockRange.jsp
* Date					:  21-June-2017
* Author(s)				:  Debmalya K

*************************************************************************/
 --%>
<%@ page language="java" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.DeleteStockRangeForm" %>
<%@ page import = "com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO" %>

		
		<!DOCTYPE html>			
	
<html:html>
<head> 
		
		
			
	
	<bean:define id="form"
				name="DeleteStockRangeForm"
				type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.DeleteStockRangeForm"
				toScope="page" />
<title>
	<common:message bundle="<%=form.getBundle()%>" key="deletestockrange.title"/>
</title>
<common:include type="script" src="/js/stockcontrol/defaults/DeleteStockRange_Script.jsp" />
<meta name="decorator" content="popuppanelrestyledui">
</head>
<body id="bodyStyle">
	
	
<div class="iCargoPopUpContent" style="height:100%;">	
<ihtml:form action="stockcontrol.defaults.screenloaddeletestockrange.do " styleClass="ic-main-form">
<%
			DeleteStockRangeForm deleteStockRangeForm= (DeleteStockRangeForm)request.getAttribute("DeleteStockRangeForm");
		%>
	<ihtml:hidden property="selectedRowIds"/>
	<input type="hidden" name="currentDialogId" />
	<input type="hidden" name="currentDialogOption" />
	 <ihtml:hidden property="lastPageNumber" />
<ihtml:hidden property="displayPage" />
<ihtml:hidden property="totalDocCount" />
	<bean:define id="mode" name="form" property="mode" toScope="page"/>	
	<div class="ic-content-main">
		<div class="ic-main-container">
		<business:sessionBean id="stockRangeVO"
									moduleName="stockcontrol.defaults"
									screenID="stockcontrol.defaults.deletestockrange" method="get"
									attribute="stockRangeVO"/>
		<business:sessionBean id="collectionRangeVO"
									moduleName="stockcontrol.defaults"
									screenID="stockcontrol.defaults.deletestockrange" method="get"
									attribute="collectionRangeVO"/>

							 <logic:present name="stockRangeVO"  property="availableRanges">
								<bean:define id="vo" name="stockRangeVO" property="availableRanges" toScope="page"/>
							 </logic:present>
							 <business:sessionBean id="rangeVoPage"
										moduleName="stockcontrol.defaults"
										screenID="stockcontrol.defaults.deletestockrange" method="get"
										attribute="pageRangeVO"/>
		
			<div class="ic-row">
				<div class="ic-section ic-border">
					<div class="ic-row">					
								<div class="ic-row">
									<h4><common:message key="deletestockrange.AvailableStockRange" /></h4>
								</div>
								<div id="divDelete">
								<div class="ic-row">
			<div class="ic-col-50">
					<logic:present name="rangeVoPage" >
					<bean:define id="pageObj" name="rangeVoPage"  />
					<common:paginationTag pageURL="stockcontrol.defaults.deleteliststockrange.do"
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
						exportAction="stockcontrol.defaults.deleteliststockrange.do"/>
						</logic:present>							
					</div>
				</div>	
			</div>
							<div class="ic-section ic-pad-3">
								<div class="tableContainer" id="div1" style="height:200px;">
									<table class="fixed-header-table" id="availableTable" >
										<thead>
											<tr class="iCargoTableHeadingLeft">
												<td width="25%" class="iCargoTableHeaderLabel" title="Range From">
													<common:message key="deletestockrange.RangeFrom" />
												</td>
												<td width="25%" class="iCargoTableHeaderLabel" title="Range To">
													<common:message key="deletestockrange.RangeTo" />
												</td>
												<td width="25%" class="iCargoTableHeaderLabel" title="Number of Documents" style="text-transform:none">
													<common:message key="deletestockrange.NoofDocs" />
												</td>
												<td width="25%" class="iCargoTableHeaderLabel" title="Utilised Documents">
													<common:message key="deletestockrange.UtilisedDocuments" /> 
												</td>
											</tr>
									    </thead>
									    <tbody>
									  <%
										int count=0;
									   %>
									   <logic:present name="rangeVoPage" >
								<bean:define id="vo" name="rangeVoPage" scope="page" toScope="page"/>
				 <logic:iterate id="availablevo" name="vo" indexId="iIndex">
										   
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
								<div class="ic-row">
									<div class="ic-col-50">								
									</div>
									<div class="ic-col-50">	
										<div class="ic-input ic-split-100 ic-label-50">								
											<label>
											<common:message key="deletestockrange.TotalAvailableStock" />
											</label>
											<ihtml:text property="totalAvailableStock" style="text-align:right;"  
											value="<%=String.valueOf(form.getTotalDocCount())%>" 
											readonly="true" 
											componentID="TXT_STOCKCONTROL_DEFAULTS_DELETESTOCKRANGE_TOTALAVAILABLESTOCK"  />						
										</div>
									</div>
								</div>
					</div>
				
				
				</div>
			</div>
			<div class="ic-row">
				<div class="ic-section ic-border">
					<div class="ic-row">
						<div class="ic-col-15"></div>
						<div class="ic-col-70">
							<div class="ic-row">
							<div class="ic-col-50">
								<div class="ic-input ic-split-100">
									<label>
										<common:message key="deletestockrange.RangeFrom" />
									</label>
									<ihtml:text property="deleteFrom" maxlength="7" 
										componentID="TXT_STOCKCONTROL_DEFAULTS_DELETESTOCKRANGE_DELETEFROM"/>						
								</div>
								<div class="ic-input ic-split-100">
									<label>
										<common:message key="deletestockrange.NoofDocs" />
									</label>
									<ihtml:text property="noOfDocs" maxlength="7" 
										componentID="TXT_STOCKCONTROL_DEFAULTS_DELETESTOCKRANGE_NUMBEROFDOCS"/>					
								</div>
							</div>
							<div class="ic-col-50">						
								<div class="ic-input ic-split-100 ic-right">	
									<label>
										<common:message key="deletestockrange.RangeTo" />
									</label>
									<ihtml:text property="deleteTo" maxlength="7" 
										componentID="TXT_STOCKCONTROL_DEFAULTS_DELETESTOCKRANGE_DELETETO" />
								</div>
								<div class="ic-split-100 ic-right">	
									<ihtml:button property="btnList" 
									componentID="BTN_STOCKCONTROL_DEFAULTS_DELETESTOCKRANGE_List" >
									<common:message key="deletestockrange.list"/>
									</ihtml:button>
								</div>
							</div>
							</div>
							<div class="ic-row">
								<div class="tableContainer" id="div2" style="height:200px;">
									<table class="fixed-header-table" id="availableTable" >
										<thead>
											<tr class="iCargoTableHeadingLeft">
												<td width="10%" class="iCargoTableHeaderLabel" title="Check All">
													<ihtml:checkbox property="checkall"
															componentID="CHK_STOCKCONTROL_DEFAULTS_DELETESTOCKRANGE_CHECKALL" />
												</td>
												<td width="25%" class="iCargoTableHeaderLabel" title="Range From">
													<common:message key="deletestockrange.RangeFrom" />
												</td>
												<td width="25%" class="iCargoTableHeaderLabel" title="Range To">
													<common:message key="deletestockrange.RangeTo" />
												</td>
												<td width="25%" class="iCargoTableHeaderLabel" title="Number of Documents" style="text-transform:none">
													<common:message key="deletestockrange.NoofDocs" />
												</td>
											</tr>
									    </thead>
									    <tbody>
										<%
										int selectedDoc=0;
									   %>
										<logic:present name="collectionRangeVO">
											<logic:iterate id="availableRange" name="collectionRangeVO" indexId="nIndex">
											<tr>
												<td class="ic-center">
													<ihtml:checkbox property="check"
															componentID="CHK_STOCKCONTROL_DEFAULTS_DELETESTOCKRANGE_CHECK" />
												</td>
												<td class="iCargoTableDataTd" style="text-align:right;" >
												<bean:write name="availableRange" property="startRange" />
												</td>
												<td class="iCargoTableDataTd" style="text-align:right;" >
												<bean:write name="availableRange" property="endRange" />
												</td>
												<td class="iCargoTableDataTd" style="text-align:right;" >
												<bean:write name="availableRange" property="numberOfDocuments" format=""/>
												</td>
											 </tr>
											 <%
											   selectedDoc+= ((RangeVO)availableRange).getNumberOfDocuments();
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
						<div class="ic-col-65"></div>
						<div class="ic-col-35">
							<label>
								<common:message key="deletestockrange.totalNoOfDoc" />
							</label>
							<ihtml:text property="totalNoOfDocs" value="<%=String.valueOf(selectedDoc)%>" style="text-align:right;" 
										componentID="TXT_STOCKCONTROL_DEFAULTS_DELETESTOCKRANGE_TOTALNUMBEROFDOCS" readonly="true"/>
						</div>
					</div>
				</div>
			</div>
			
							
		</div>
		<div class="ic-foot-container">
			<div class="ic-row">
				<div class="ic-button-container">
				<ihtml:button property="btnDelete"
							componentID="BTN_STOCKCONTROL_DEFAULTS_DELETESTOCKRANGE_DELETE" >
							<common:message key="deletestockrange.delete"/>
				</ihtml:button>
				<ihtml:button property="btnClose"
							componentID="BTN_STOCKCONTROL_DEFAULTS_DELETESTOCKRANGE_CLOSE" >
							<common:message key="deletestockrange.close"/>
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

