<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  IN - Inventory Control
* File Name				:  ReturnStock.jsp
* Date					:  13-Sep-2005
* Author(s)				:  Smrithi

*************************************************************************/
 --%>
<%@ page language="java" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReturnStockForm" %>
<%@ page import = "com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO" %>

<html:html>
<head>
		
			
	
<bean:define id="form"
	name="ReturnStockForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReturnStockForm"
	toScope="page" />

<title><common:message bundle="<%=form.getBundle()%>" key="returnstockrange.title"/></title>
<common:include type="script" src="/js/stockcontrol/defaults/ReturnStockRange_Script.jsp" />
<meta name="decorator" content="popuppanelrestyledui">
</head>
<body id="bodyStyle">
	


<div class="iCargoPopUpContent" style="height:100%">
<ihtml:form action="stockcontrol.defaults.screenloadreturnstockrange.do" styleClass="ic-main-form">
<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />

<ihtml:hidden property="manualCheckFlag"/>
<ihtml:hidden property="documentRange"/>
<bean:define id="mode" name="form" property="mode" />
<ihtml:hidden property="remarks"/>
 <ihtml:hidden property="lastPageNumber" />
<ihtml:hidden property="displayPage" />
<ihtml:hidden property="totalDocCount" />
<div class="ic-content-main">			
	
		
		  <%
			ReturnStockForm returnStockForm = (ReturnStockForm)request.getAttribute("ReturnStockForm");
		  %>
		
	<div class="ic-main-container">
		<div class="ic-row">
			<h4><common:message key="returnstockrange.ReturnStockRange" /></h4>
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
											<common:message key="returnstockrange.StockHolder" />
										</label>
										<ihtml:text property="stockHolder"
										componentID="TXT_STOCKCONTROL_DEFAULTS_RETURNSTOCKRANGE_STOCKHOLDER"
										maxlength="12"
										readonly="true" />							
									</div>
									<div class="ic-input ic-split-100 ic-label-25">
										<label>
											<common:message key="returnstockrange.DocType" />
										</label>
										<ihtml:text property="docType"
										componentID="TXT_STOCKCONTROL_DEFAULTS_RETURNSTOCKRANGE_DOCUMENTTYPE"
										readonly="true" />						
									</div>
								</div>
								<div class="ic-col-50">						
									<div class="ic-input ic-split-100 ic_inline_chcekbox marginT5 marginB5>
										<ihtml:checkbox property="manual"
										componentID="CHK_STOCKCONTROL_DEFAULTS_RETURNSTOCKRANGE_MANUAL"
										title="Manual"
										disabled="true" />
										<label>
											<common:message key="returnstockrange.Manual" />
										</label>												
									</div>
									<div class="ic-input ic-split-100">							
										<label>
											<common:message key="returnstockrange.SubType" />
										</label>	
										<ihtml:text property="subType"
										componentID="TXT_STOCKCONTROL_DEFAULTS_RETURNSTOCKRANGE_DOCUMENTSUBTYPE"
										readonly="true" />
									</div>
								</div>
							</div>
						</div>
                                 <business:sessionBean id="stockRangeVO"
										 moduleName="stockcontrol.defaults"
										 screenID="stockcontrol.defaults.returnstockrange" method="get"
										 attribute="stockRangeVO"/>
								<business:sessionBean id="rangeVoPage"
										moduleName="stockcontrol.defaults"
										screenID="stockcontrol.defaults.returnstockrange" method="get"
										attribute="pageRangeVO"/>
						<div class="ic-row">
							<h4><common:message key="returnstockrange.AvailableStockRange" /></h4>
						</div>
						
						<div id="divReturn">
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
					<div class="ic-section ic-pad-3">
						<div class="tableContainer" id="div1" style="height:180px;">
							<table class="fixed-header-table" id="availableTable" >
								<thead>
									  <tr class="iCargoTableHeadingLeft" style="text-transform:none">
										<td width="25%" class="iCargoTableHeaderLabel" >
											<common:message key="returnstockrange.RangeFrom" /></td>
										<td width="25%" class="iCargoTableHeaderLabel" >
											<common:message key="returnstockrange.RangeTo" /></td>
										<td width="25%" class="iCargoTableHeaderLabel" >
											<common:message key="returnstockrange.NoofDocs" /></td>
										<td width="25%" class="iCargoTableHeaderLabel" >
											Utilised Documents</td>
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
                                      	<bean:write name="availablevo" property="startRange" /></td>
										<bean:define id="fromRange" name="availablevo" property="startRange" type="String"/>
								
                                      <td class="iCargoTableDataTd"  style="text-align:right;" >
                                      	<bean:write name="availablevo" property="endRange" /></td>
                                      <td  class="iCargoTableDataTd" style="text-align:right;" >
                                      	<bean:write name="availablevo" property="numberOfDocuments" format=""/></td>
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
								  </logic:present></tbody>
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
									<common:message key="returnstockrange.TotalAvailableStock" />
									</label>
									<ihtml:text property="totalAvailableStock" style="text-align:right;"
										value="<%=String.valueOf(form.getTotalDocCount())%>"
										readonly="true"
										componentID="TXT_STOCKCONTROL_DEFAULTS_RETURNSTOCKRANGE_TOTALAVAILABLESTOCK" />						
								</div>
							</div>
						</div>
					
					</div>
				</div>				
			</div>
			<div class="ic-row">
				<div class="ic-col-50">
						<div class="ic-section ic-border ic-input-round-border" style="height:200px">
					<div class="ic-row">
								<div class="ic-input ic-split-100 ic_inline_chcekbox">
									<ihtml:radio property="returnMode" value="numberOfDocuments" style="text-align:right;" 
											componentID="STOCKCONTROL_DEFAULTS_RETURNSTOCK_MODENOOFDOCS" />
									<label style="margin-top:3px;">
										<common:message key="stockcontrol.defaults.returnstock.lbl.byNumberOfDocuments"/>
									</label>
								</div>
							</div>
							<div class="ic-row">
								<div class="ic-input ic-split-100 ic-label-60 ">
									<label>
										<common:message key="stockcontrol.defaults.returnstock.lbl.rangeFrom"/>
									</label>
									<ihtml:text property="modeRangeFrom" style="text-align:right;" maxlength="7"
																componentID="STOCKCONTROL_DEFAULTS_RETURNSTOCK_RANGEFROM"
																/>
								</div>
							</div>
							<div class="ic-row">
								<div class="ic-input ic-mandatory ic-split-100 ic-label-60">
								<label>
									<common:message key="stockcontrol.defaults.returnstock.lbl.numberOfDocuments"/>
								</label>
								<ihtml:text property="modeNumberOfDocuments" style="text-align:right;" maxlength="7"
																componentID="STOCKCONTROL_DEFAULTS_RETURNSTOCK_NUMDOC"
																/>
								</div>
							</div>
						</div>
				</div>
				<div class="ic-col-50">
					<div class="ic-section ic-border" style="height:200px">
					<div class="ic-row">
						<ihtml:radio property="returnMode" value="ranges" style="text-align:right;" 
											componentID="STOCKCONTROL_DEFAULTS_RETURNSTOCK_MODERANGE" />
						<label>
							<common:message key="stockcontrol.defaults.returnstock.lbl.byRange"/>
						</label>
					</div>
					<div class="ic-button-container">
						<a class="iCargoLink" href="javascript:onClickAdd();" >
                            		<common:message key="returnstockrange.Add" /></a>
                              | <a class="iCargoLink" href="javascript:onClickDelete('check');" >
                              		<common:message key="returnstockrange.Delete" /></a>
					</div>
					<div class="ic-section ic-pad-3">
						<business:sessionBean id="collectionRangeVO" moduleName="stockcontrol.defaults"
										screenID="stockcontrol.defaults.returnstockrange"
									    method="get" attribute="collectionRangeVO"/>
						<div class="tableContainer" id="div2" style="height:220px;">
			    				<table  class="fixed-header-table" id="returnTable">
				<thead>
                                <tr class="iCargoTableHeadingLeft"  style="text-transform:none">
                                  <td width="10%" class="iCargoTableHeaderLabel">
                                  	<ihtml:checkbox property="checkall"
                                  		componentID="CHK_STOCKCONTROL_DEFAULTS_RETURNSTOCKRANGE_CHECKALL" /></td>
                                  <td width="30%" class="iCargoTableHeaderLabel">
                                  		<common:message key="returnstockrange.RangeFrom" /></td>
                                  <td width="30%" class="iCargoTableHeaderLabel">
                                  		<common:message key="returnstockrange.RangeTo" /></td>
                                  <td width="30%" class="iCargoTableHeaderLabel">
                                  		<common:message key="returnstockrange.NoofDocs" /></td>
                                </tr></thead><tbody>



                               <logic:present  name="collectionRangeVO">

							   	<%
							   		 int indexcnt=0;
							   	%>
	                          <logic:iterate id="vo" name="collectionRangeVO" indexId="nIndex">
                    	 
			<tr >
                                    <td class="iCargoTableDataTd">
                                    <div class="ic-center">
                                    	<ihtml:checkbox property="check"
										    value="<%= String.valueOf(indexcnt) %>"
										    componentID="CHK_STOCKCONTROL_DEFAULTS_RETURNSTOCKRANGE_CHECK" />
									</div>
									</td>
                                    <td class="iCargoTableDataTd">
                                    	<ihtml:text property="rangeFrom" id="<%=new Integer(nIndex).toString()%>"
											componentID="TXT_STOCKCONTROL_DEFAULTS_RETURNSTOCKRANGE_RANGEFROM"
											style="text-align:right;"
											value="<%= ((RangeVO)vo).getStartRange() %>"
											
											/></td>
                                    <td class="iCargoTableDataTd">
                                    	<ihtml:text property="rangeTo" id="<%=new Integer(nIndex).toString()%>"
											componentID="TXT_STOCKCONTROL_DEFAULTS_RETURNSTOCKRANGE_RANGETO"
											style="text-align:right"
											
											
											value="<%= ((RangeVO)vo).getEndRange() %>"  /></td>
                                    <td  class="iCargoTableDataTd">
                                    	<ihtml:text property="noofDocs" id="<%=new Integer(nIndex).toString()%>"
											componentID="TXT_STOCKCONTROL_DEFAULTS_RETURNSTOCKRANGE_NUMBEROFDOCS"
											style="text-align:right;"
											value="<%= new Long(((RangeVO)vo).getNumberOfDocuments()).toString() %>"
                                    		readonly="true" /></td>
                                  </tr>

                                  <%
								  		indexcnt=indexcnt+1;
								  %>
								  	</logic:iterate>
				                   </logic:present>

                               </tbody> </table>
                              </div>
					</div>
				</div>	
				</div>
					<div class="ic-row">
						<div class="ic-col-60">								
						</div>
						<div class="ic-col-40">	
							<div class="ic-input ic-split-100 ic-label-40">								
								<label>
								<common:message key="returnstockrange.TotalnoofDocs" />
								</label>
								<ihtml:text property="totalNoOfDocs" style="text-align:right;" 
								componentID="TXT_STOCKCONTROL_DEFAULTS_RETURNSTOCKRANGE_TOTALNUMBEROFDOCS"
								readonly="true" />								
							</div>
						</div>
					</div>
					<div class="ic-row">	
						<div class="ic-col-100">
							<div class="ic-input ic-split-100 ">
								<label>
								<common:message key="returnstockrange.Remarks" />
								</label>
								<ihtml:textarea property="remarks"
								cols="94"
								rows="2"
								componentID="TXT_STOCKCONTROL_DEFAULTS_RETURNSTOCKRANGE_REMARKS">
								</ihtml:textarea>
							</div>
						</div>
					</div>
				</div>		
			</div>
		</div>		
	<div class="ic-foot-container">
		<div class="ic-row">
			<div class="ic-button-container">
				<ihtml:button property="btnReturn"
					componentID="BTN_STOCKCONTROL_DEFAULTS_RETURNSTOCKRANGE_RETURN" >
					<common:message key="returnstockrange.Return"/>
				</ihtml:button>

				<ihtml:button property="btnClose"
					componentID="BTN_STOCKCONTROL_DEFAULTS_RETURNSTOCKRANGE_CLOSE" >
					<common:message key="returnstockrange.Close"/>
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

