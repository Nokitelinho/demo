<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>



<business:sessionBean id="awbStockPage"
	     moduleName="stockcontrol.defaults"
	     screenID="stockcontrol.defaults.viewrange" method="get"
	     attribute="aWBStockPage"/>
<bean:define id="form"
	name="ViewRangeForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ViewRangeForm"
	toScope="page" />
<ihtml:form action="stockcontrol.defaults.listawbstock.do">

<div id="divAWBAJAX">
	<div class="ic-row">
		
		
				<div class="ic-col-50">
					<logic:present name="awbStockPage" >
					<bean:define id="pageObj" name="awbStockPage"  />
					<common:paginationTag pageURL="stockcontrol.defaults.listawbstock.do"
					name="pageObj"
					display="label"
					labelStyleClass="iCargoResultsLabel"
					lastPageNum="<%=form.getLastPageNumber() %>" />
					</logic:present>				    
				</div>
				<div class="ic-col-50">
					<div class="ic-button-container paddR5">
						<logic:present name="awbStockPage" >
						<bean:define id="pageObj1" name="awbStockPage"  />
						<common:paginationTag
						linkStyleClass="iCargoLink"
						disabledLinkStyleClass="iCargoLink"
						pageURL="javascript:submitPage('lastPageNum','displayPage')"
						name="pageObj1"
						display="pages"
						lastPageNum="<%=form.getLastPageNumber()%>"
						exportToExcel="true"
						exportTableId="awbStockTable"
						exportAction="stockcontrol.defaults.listawbstock.do"/>
						</logic:present>							
					</div>
				</div>	
		
		
		
	</div>
	<div class="ic-row">					
		
			<div class="tableContainer" style="height:150px;">
				<table id="awbStockTable" width="100%" class="fixed-header-table" >
					<thead>						
						<tr class="iCargoTableHeadingLeft" style="text-transform:none">
						
						<td class="iCargoTableHeaderLabel" width="100%">
							AWB Stock</td>
						</tr>
					</thead>
					<tbody>
						<logic:present name="awbStockPage" >
							<bean:define id="awbStock" name="awbStockPage" scope="page" toScope="page"/>
							<logic:iterate id="awb" name="awbStock" indexId="rowCount">
								
									<tr>
									<td class="iCargoTableDataTd ic-center"  >
										<%=String.valueOf(awb)%>
										</td>
									</tr>
								
							</logic:iterate>
						</logic:present>
					</tbody>
				</table>
			</div>
		
	</div>			
</div>
</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>