<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
<%@ page import = "com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO" %>

<business:sessionBean id="rangeVoPage"
		moduleName="stockcontrol.defaults"
		screenID="stockcontrol.defaults.returnstockrange" method="get"
		attribute="pageRangeVO"/>
<bean:define id="form"
	name="ReturnStockForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReturnStockForm"
	toScope="page" />
<ihtml:form action="stockcontrol.defaults.returnliststockrange.do">

<div id="divReturnAJAX">
	<div class="ic-row">
		
		
				<div class="ic-col-50">
					<logic:present name="rangeVoPage" >
					<bean:define id="pageObj" name="rangeVoPage"  />
					<common:paginationTag pageURL="stockcontrol.defaults.returnliststockrange.do"
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
						exportAction="stockcontrol.defaults.returnliststockrange.do"/>
						</logic:present>							
					</div>
				</div>	
		
		
		
	</div>
	<div class="ic-row">					
		
			<div class="tableContainer" id="div1" style="height:200px;">
									<table class="fixed-header-table" id="availableTable" >
					<thead>						
						<tr class="iCargoTableHeadingLeft">
												<td width="25%" class="iCargoTableHeaderLabel" title="Range From">
													<common:message key="returnstockrange.RangeFrom" />
												</td>
												<td width="25%" class="iCargoTableHeaderLabel" title="Range To">
													<common:message key="returnstockrange.RangeTo" />
												</td>
												<td width="25%" class="iCargoTableHeaderLabel" title="Number of Documents" style="text-transform:none">
													<common:message key="returnstockrange.NoofDocs" />
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
</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>