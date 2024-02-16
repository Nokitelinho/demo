<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  IN - Inventory Control
* File Name				:  ViewRange.jsp
* Date					:  13-Sep-2005
* Author(s)				:  Smrithi

*************************************************************************/
 --%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ViewRangeForm" %>
<%@ page import="com.ibsplc.web.component.ui.table.basic.*"%>
<%@ page import="com.ibsplc.web.component.ui.table.constant.*"%>

<html:html>
<head>
		
			
	
<bean:define id="form"
	name="ViewRangeForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ViewRangeForm"
	toScope="page" />
<title><common:message bundle="<%=form.getBundle()%>" key="stockholder.title"/></title>

<business:sessionBean id="stockRangeVO"
	     moduleName="stockcontrol.defaults"
	     screenID="stockcontrol.defaults.viewrange" method="get"
	     attribute="stockRangeVO"/>

<meta name="decorator" content="popuppanelrestyledui">
<common:include type="script" src="/js/stockcontrol/defaults/ViewRange_Script.jsp" />


	<%	
	    DataGrid availableTable = new DataGrid("table1", "icargotable1","style=\"width:100%\"", 19, 8, 6);
		
		availableTable.setEvenRowColor("#F7F7FF");
		availableTable.setOddRowColor("#EEEEEE");

		StringBuilder headerString = new StringBuilder().append("<thead><tr class=\"iCargoTableHeadingCenter\" style=\"text-transform:none\">")
		.append("<td></td>")
		.append("<td class=\"iCargoTableHeaderLabel\"> Range From </td>")
								 .append("<td class=\"iCargoTableHeaderLabel\"> Range To </td>")
								 .append("<td class=\"iCargoTableHeaderLabel\" > No. of Docs </td>")
								 .append("<td class=\"iCargoTableHeaderLabel\" > Utilised Docs </td>")
								 .append("<td class=\"iCargoTableHeaderLabel\" > Allocation Date </td></tr> </thead>");

		Header header = new Header();
		header.setHeaderString(headerString.toString());
		header.setNoOfRows(1);
		header.setRowHeight(18);
		availableTable.setHeader(header);

	%>
	   <%
	         int sum=0;
			 int check = 0;
	    %>
		<logic:present name="stockRangeVO" property="availableRanges">
			<bean:define id="vo" name="stockRangeVO"
					property="availableRanges"
					toScope="page"/>
					
				<logic:present name="vo">
				<%int rowCountAff = 0;%>
	                   		 <logic:iterate id="availablevo" name="vo">
	                   		 	<%
					 		Row row = availableTable.createRow();
						%>
						<%Column column0 = row.addColumn(0);
					column0.setCellType(CellTypes.CHECK);
					column0.setData(String.valueOf(rowCountAff));
					column0.setId("confirmedCheck");
					column0.setCustomProperties("style=\"text-align:center\" ");
					
				%>
							<%
								Column column = row.addColumn(1);								
								column.setData(String.valueOf(((RangeVO)availablevo).getStartRange()));
								column.setCustomProperties("style=\"text-align:right\"");
							%>
							<%
								Column column1 = row.addColumn(2);								
								column1.setData(String.valueOf(((RangeVO)availablevo).getEndRange()));
								column1.setCustomProperties("style=\"text-align:right\"");
							%>
							<%
								Column column2 = row.addColumn(3);
								column2.setData(String.valueOf(((RangeVO)availablevo).getNumberOfDocuments()));
								column2.setCustomProperties("style=\"text-align:right\"");
							%>
						<%
							sum+= ((RangeVO)availablevo).getNumberOfDocuments();
	
						%>
						<% Column column3 = row.addColumn(4);%>
						<logic:present name="availablevo" property="masterDocumentNumbers">
						<%String cellId="indicator"+String.valueOf(rowCountAff);
							column3.setData("<img id="+cellId+" src="+request.getContextPath()+"/images/circle_yellow.png onclick=\"onClickImage(this);\" />"); %>
						
						</logic:present>
							<% column3.setCustomProperties("style=\"text-align:center\"");%>
						<!-- Added by A-3791 for ICRD-110168-->
						<% Column column4 = row.addColumn(5);%>
						<logic:present name="availablevo" property="stockAcceptanceDate">
							<% column4.setData(String.valueOf(((RangeVO)availablevo).getStockAcceptanceDate().toDisplayDateOnlyFormat())); %>
						</logic:present>
						<% column4.setCustomProperties("style=\"text-align:center\"");%>
						<%rowCountAff++;%>
					    </logic:iterate>
				</logic:present>
		</logic:present>
		<%=availableTable.load("availableTableDiv")%>
		<!-- Modified by A-5131 for ICRD-24949 -->
	<%	
	    DataGrid allocatedTable = new DataGrid("table2", "icargotable1","style=\"width:100%\"", 19, 8, 5);
		
		allocatedTable.setEvenRowColor("#F7F7FF");
		allocatedTable.setOddRowColor("#EEEEEE");

		headerString = new StringBuilder().append("<thead><tr class=\"iCargoTableHeadingCenter\"  style=\"text-transform:none\"><td class=\"iCargoTableHeaderLabel\"> Range From </td>")
								 .append("<td class=\"iCargoTableHeaderLabel\"> Range To </td>")
								 .append("<td class=\"iCargoTableHeaderLabel\"> No. of Docs </td>")
								 .append("<td class=\"iCargoTableHeaderLabel\"> Allocated To </td>")
								 .append("<td class=\"iCargoTableHeaderLabel\" > Allocation Date </td> </tr> </thead>");

		header = new Header();
		header.setHeaderString(headerString.toString());
		header.setNoOfRows(1);
		header.setRowHeight(18);
		allocatedTable.setHeader(header);

	%>
	
	 <%
	        int allocatedSum=0;
          %>
	       <logic:present name="stockRangeVO" property="allocatedRanges">
					<bean:define id="vo" name="stockRangeVO" 
						property="allocatedRanges" 
						toScope="page"/>
			<logic:present name="vo">
                     		<logic:iterate id="allocatedvo" name="vo">
                     					<%
								Row row = allocatedTable.createRow();
							%>
								<%
									Column column = row.addColumn(0);
									column.setData(String.valueOf(((RangeVO)allocatedvo).getStartRange()));
									column.setCustomProperties("style=\"text-align:right\"");
								%>		
								<%
									Column column1 = row.addColumn(1);
									column1.setData(String.valueOf(((RangeVO)allocatedvo).getEndRange()));
									column1.setCustomProperties("style=\"text-align:right\"");
								%>
								<%
									Column column2 = row.addColumn(2);
									column2.setDataFormat("");
									column2.setData(String.valueOf(((RangeVO)allocatedvo).getNumberOfDocuments()));
									column2.setCustomProperties("style=\"text-align:right\"");
								%>
								<!-- Added by A-3791 for ICRD-110168 Starts-->
								<%
									Column column3 = row.addColumn(3);
									column3.setDataFormat("");
									column3.setData(String.valueOf(((RangeVO)allocatedvo).getStockHolderCode()));
									column3.setCustomProperties("style=\"text-align:center\"");
								%>
								<%
									Column column4 = row.addColumn(4);
									column4.setDataFormat(""); %>
									<logic:present name="allocatedvo" property="stockAcceptanceDate">
										<% column4.setData(String.valueOf(((RangeVO)allocatedvo).getStockAcceptanceDate().toDisplayDateOnlyFormat())); %>
									</logic:present>
									<% column4.setCustomProperties("style=\"text-align:center\"");
								%>
								<!-- Added by A-3791 for ICRD-110168 Starts-->
                        
				      <%
						 allocatedSum+= ((RangeVO)allocatedvo).getNumberOfDocuments();
				      %>
                      		</logic:iterate>
                      </logic:present>
              </logic:present>  
              
              <%=allocatedTable.load("allocatedTableDiv")%>

</head>
<body id="bodyStyle">
	


<div class="iCargoPopUpContent" style="height:100%;">
<ihtml:form action="/stockcontrol.defaults.screenloadviewrange.do" styleClass="ic-main-form">

 <ihtml:hidden property="lastPageNumber" />
<ihtml:hidden property="displayPage" />
	<div class="ic-content-main">			
		<div class="ic-main-container">
			<div class="ic-row">
				<h4><common:message key="viewrange.ViewRange" /></h4>
			</div>
			<div class="ic-input-round-border">		
				<div class="ic-row">
					<div class="ic-col-100">
						<div class="ic-input-container">
							<div class="ic-row">
								<div class="ic-input ic-split-25">
									<label>
										<common:message key="viewrange.StockHolder" />
									</label>
									<ihtml:text property="stockHolder"
									readonly="true"
									componentID="TXT_STOCKCONTROL_DEFAULTS_VIEWRANGE_STOCKHOLDERCODE" />
								</div>
								<div class="ic-input ic-split-25">
									<label>
										<common:message key="viewrange.DocType" />
									</label>
									<ihtml:text property="docType"
									readonly="true"
									componentID="TXT_STOCKCONTROL_DEFAULTS_VIEWRANGE_DOCUMENTTYPE" />
								</div>
								<div class="ic-input ic-split-25">
									<label>
										<common:message key="viewrange.SubType" />
									</label>
									<ihtml:text property="subType"
									readonly="true"
									componentID="TXT_STOCKCONTROL_DEFAULTS_VIEWRANGE_DOCUMENTSUBTYPE" />
								</div>
								<div class="ic-input ic-split-25 ic_inline_chcekbox marginT10">
									<ihtml:checkbox property="manual"
									componentID="CHK_STOCKCONTROL_DEFAULTS_VIEWRANGE_MANUAL"
									disabled="true" />
									<label>
										<common:message key="viewrange.Manual" />
									</label>
								</div>
							</div>
						</div>
					</div>	
				</div>
			</div>
			<div class="ic-row">
				<div class="ic-col-50">
					<div class="ic-section ic-border">
						
						<div class="ic-row">
							<h4><common:message key="viewrange.AvailableStockRange" /></h4>
						</div>
						<div id="availableTableDiv" class="ic-scrollable-data-grid-container" style="width:99%;height:190px">
							<%=availableTable.drawTable("availableTableDiv","102%")%>
						</div>
						
					</div>
				</div>
				<div class="ic-col-50">
					<div class="ic-section ic-border">
						
						<div class="ic-row">
							<h4><common:message key="viewrange.AllocatedStockRange" /></h4>
						</div>
						<div id="allocatedTableDiv" class="ic-scrollable-data-grid-container" style="height:190px">
							<%=allocatedTable.drawTable("allocatedTableDiv","100%")%>  
						</div>
						
					</div>
				</div>
			</div>
			<div class="ic-row">
				<div class="ic-col-15">
				</div>
				<div class="ic-col-35">
					<div class="ic-input ic-split-100">
							<label>
								<common:message key="viewrange.TotalAvailableStock" />
							</label>
							<ihtml:text property="totalAvailableStock" style="text-align:right;"
							value="<%=String.valueOf(sum)%>"
							readonly="true"
							componentID="TXT_STOCKCONTROL_DEFAULTS_VIEWRANGE_TOTALAVAILABLESTOCK"
							/>
					</div>
				</div>
				<div class="ic-col-15">
				</div>
				<div class="ic-col-35">
					<div class="ic-input ic-split-100">
						<label>
							<common:message key="viewrange.TotalAllocatedStock" />
						</label>
						<ihtml:text property="totalAllocatedStock" style="text-align:right;"
						value="<%=String.valueOf(allocatedSum)%>"  
						readonly="true"
						componentID="TXT_STOCKCONTROL_DEFAULTS_VIEWRANGE_TOTALALLOCATEDSTOCK"
						 />						
					</div>
				</div>
			</div>
			<div class="ic-row">
				
					<div class="ic-button-container paddR5">
						<logic:present name="form" property="docType">
						<bean:define id="doc" property="docType"  name="form"/>
						<logic:equal name="doc" value="AWB">
						
							<ihtml:button property="btnListAWBStock"
								componentID="BTN_STOCKCONTROL_DEFAULTS_VIEWRANGE_LISTAWBSTOCK">
								<common:message key="viewrange.listawbstock"/>
							</ihtml:button>
						
						</logic:equal>
						<logic:notEqual name="doc" value="AWB">
							
							<ihtml:button property="btnListAWBStock" disabled="true"
							componentID="BTN_STOCKCONTROL_DEFAULTS_VIEWRANGE_LISTAWBSTOCK">
								<common:message key="viewrange.listawbstock"/>
							</ihtml:button>
						</logic:notEqual>
						</logic:present>
					</div>
				
			</div>
			<div id="divAWB">
				<div class="ic-row">
					
						
							<div class="ic-col-45">
								<logic:present name="awbStockPage" >
								<bean:define id="pageObj" name="awbStockPage"  />
								<common:paginationTag pageURL="stockcontrol.defaults.listawbstock.do"
								name="pageObj"
								display="label"
								labelStyleClass="iCargoResultsLabel"
								lastPageNum="<%=form.getLastPageNumber() %>" />
								</logic:present>				    
							</div>
							<div class="ic-col-55">
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
					
						<div class="tableContainer" style="height:85px;">
							<table id="awbStockTable" width="100%" class="fixed-header-table" >
								<thead>
								
									<tr class="iCargoTableHeadingLeft" style="text-transform:none">
									
									<td class="iCargoTableHeaderLabel" width="100%" >
										AWB Stock</td>
									</tr>
								</thead>
								<tbody >
								</tbody>
							</table>
						</div>
					
					
				</div>
			</div>
		</div>
		<div class="ic-foot-container">
			<div class="ic-row">
				<div class="ic-button-container paddR5">
					<ihtml:button property="btnClose"
						componentID="BTN_STOCKCONTROL_DEFAULTS_VIEWRANGE_CLOSE">
						<common:message key="viewrange.close"/>
					</ihtml:button>
				</div>
			</div>
		</div>
	</div>
          

</ihtml:form>
</div>	

			
		   
	</body>
</html:html>

