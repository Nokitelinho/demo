
<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  STK - Stock Control
* File Name				:  MaintainStockAgentMapping.jsp
* Date					:  12-Jun-2015
* Author(s)				:  Ramesh Chandra Pradhan

*************************************************************************/
 --%> 
 

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page language="java" %>
<%@ page import="org.apache.struts.Globals"%>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MaintainStockAgentMappingForm" %>

		
<html:html>

<head>
		











	
		
	<meta name="decorator" content="mainpanelrestyledui">
	<common:include type="script"  src="/js/stockcontrol/defaults/MaintainStockAgentMapping_Script.jsp" />

<bean:define id="form"
name="MaintainStockAgentMappingForm"
type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MaintainStockAgentMappingForm"
toScope="page" />

<title><common:message bundle="<%=form.getBundle() %>" key="stockholder.defaults.MaintainStockAgentMapping.title.MaintainStockAgentMapping" /></title>


	<common:include type="script"  src="/js/stockcontrol/defaults/MaintainStockAgentMapping_Script.jsp" />
</head>


<body>


	<div class="iCargoContent ic-masterbg" style="overflow:auto;width:100%" id="pageDiv">
		<ihtml:form action="stockcontrol.defaults.screenloadmaintainstockagentmapping.do">

		<bean:define id="form"
				name="MaintainStockAgentMappingForm"
				type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MaintainStockAgentMappingForm"
				toScope="page" />

		<html:hidden property="lastPageNum" />
		<html:hidden property="displayPage" />
		<html:hidden property="isErrorPresent" /> 
		<html:hidden property="commandIdentifier" />
					

		<business:sessionBean id="stockAgentPages" moduleName="stockcontrol.defaults" screenID="stockcontrol.defaults.maintainstockagentmapping" method="get" attribute="stockHolderAgentMapping" />
		
		
		<div class="ic-content-main">
			<span class="ic-page-title ic-display-none">
				<common:message key="stockholder.defaults.MaintainStockAgentMapping.label.MaintainStockAgentMapping" />
			</span>
			<div class="ic-head-container">
				<div class="ic-filter-panel">
					<div class="ic-row">
						<div class="ic-input ic-split-35 ic-label-20">
							<label>
								<common:message key="stockholder.defaults.MaintainStockAgentMapping.label.StockHolder" />
							</label>
							<ihtml:text property="stockHolder" 
										componentID="CMP_Stockcontrol_Defaults_MaintainStockAgentMapping_StockHolder"
										maxlength="12" 
										readonly="false"/>
							<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" id="stockHolderLov"  /></div>
						</div>
						<div class="ic-input ic-split-35 ic-label-15">
							<label>
								<common:message key="stockholder.defaults.MaintainStockAgentMapping.label.Agent" />
							</label>
							<ihtml:text property="agent"				
										componentID="CMP_Stockcontrol_Defaults_MaintainStockAgentMapping_Agent"
										maxlength="12"
										readonly="false"/>
                            <div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" id="agentLov" /></div>
						</div>
						<div class="ic-input ic-split-30">
							<div class="ic-button-container">
								<ihtml:nbutton property="btList" componentID="CMP_Stockcontrol_Defaults_MaintainStockAgentMapping_List" accesskey="L">
									<common:message key="stockholder.defaults.MaintainStockAgentMapping.button.List" />
								</ihtml:nbutton>
								<ihtml:nbutton property="btClear" componentID="CMP_Stockcontrol_Defaults_MaintainStockAgentMapping_Clear" accesskey="C">
									<common:message key="stockholder.defaults.MaintainStockAgentMapping.button.Clear" />
								</ihtml:nbutton>
							</div>	
						</div>
					</div>
				</div>
			</div>
			<div class="ic-main-container">
				<div class="ic-row" id="listTable">
					<div class="ic-col-100">
						<div class="ic-row">
							<div class="ic-col-60"><!--Modified by A-8146 for ICRD-260927-->
								<logic:present name="stockAgentPages">
									<common:paginationTag pageURL="stockcontrol.defaults.listmaintainstockagentmapping.do"
											 name="stockAgentPages"
											 display="label"
											 labelStyleClass="iCargoResultsLabel"
											 lastPageNum="<%=((MaintainStockAgentMappingForm)form).getLastPageNum()%>" />
								</logic:present>
								<logic:notPresent name="stockAgentPages">
									&nbsp;
								</logic:notPresent>
							</div>
							<div class="ic-col-30"><!--Modified by A-8146 for ICRD-260927-->
								<div class="ic-button-container">
									<logic:present name="stockAgentPages">
										
										<common:paginationTag pageURL="javascript:submitPage('lastPageNum','displayPage')"
													linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
													name="stockAgentPages" display="pages" lastPageNum="<%=(String)((MaintainStockAgentMappingForm)form).getLastPageNum()%>"
													exportToExcel="true"
													exportTableId="maintainStockTable"
													exportAction="stockcontrol.defaults.listmaintainstockagentmapping.do"/>
									</logic:present>
									<logic:notPresent name="stockAgentPages">
										&nbsp;
									</logic:notPresent>
								</div>
							</div>
							<div class="ic-col-10"><!--Modified by A-8146 for ICRD-260927-->
								<div class="ic-button-container paddR5">
									<ul class="ic-list-link">								
										
											 <a href="#" id="addRow" name="Add" class="iCargoLink">
												<common:message key="stockholder.defaults.MaintainStockAgentMapping.link.Add"/>
											</a> |
										
										
											<a href="#" id="deleteRow" name="Delete" class="iCargoLink">
												<common:message key="stockholder.defaults.MaintainStockAgentMapping.link.Delete"/>
											</a>
												
									</ul>
								</div>
							</div>
							<!--Modified by A-8146 for ICRD-260927 starts-->
							<!--<div class="ic-col-10">
								<div class="ic-button-container">
									<a href="#" class="iCargoLink"  id="addRow" ><common:message  key="stockholder.defaults.MaintainStockAgentMapping.link.Add" /></a>
									| <a href="#" class="iCargoLink" id="deleteRow" ><common:message  key="stockholder.defaults.MaintainStockAgentMapping.link.Delete" /></a>
							</div>-->
							<!--Modified by A-8146 for ICRD-260927 ends-->
							<!--<div class="ic-col-10">
								<div class="ic-button-container">
									<a href="#" class="iCargoLink"  id="addRow" ><common:message  key="stockholder.defaults.MaintainStockAgentMapping.link.Add" /></a>
									| <a href="#" class="iCargoLink" id="deleteRow" ><common:message  key="stockholder.defaults.MaintainStockAgentMapping.link.Delete" /></a>
							</div>
						</div>-->
						
						<div class="ic-row">
				
		<div class="tableContainer" id="div1" style="height:675px; width: 100%">
			<table class="fixed-header-table" id="maintainStockTable">
				<thead>
					<tr class="iCargoTableHeadingLeft">
						<td width="10%" class="iCargoTableHeaderLabel" style="text-align:center">
							
								<input type="checkbox" name="checkAll" value="checkbox" onclick="updateHeaderCheckBox(this.form,this.form.checkAll,this.form.checkbox)" />
							
						</td>
						<td width="45%" class="iCargoTableHeaderLabel"> <common:message key="stockholder.defaults.MaintainStockAgentMapping.label.Agent" /></td>
						<td width="45%" class="iCargoTableHeaderLabel"> <common:message key="stockholder.defaults.MaintainStockAgentMapping.label.StockHolder" /></td>
					</tr>
				</thead>
				<tbody>

					<logic:present name="stockAgentPages">
						<% int checkboxes=0; %>
						<logic:iterate id="stockAgent" name="stockAgentPages" type="com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentVO" indexId="nIndex"> 	   	        
					
							<logic:notEqual name="stockAgent" property="operationFlag" value="D">
							<tr>		
								<td class="iCargoTableDataTd">
									<div align="center">
									<ihtml:checkbox property="checkbox" value="<%= String.valueOf(checkboxes) %>" onclick="toggleTableHeaderCheckbox('checkbox',this.form.checkAll)" />
									</div>
								</td>	
								<td class="iCargoTableDataTd"> 
									<div align="center">		
										<ihtml:text property="agents"
											componentID="CMP_Stockcontrol_Defaults_MaintainStockAgentMapping_Agents"
											value="<%=stockAgent.getAgentCode() %>"
											style="align:center"
											maxlength="12"
											readonly="false"/>
										
                                        <div class="lovImgTbl valignT"><img id="TypeLov<%=nIndex%>" src="<%=request.getContextPath()%>/images/lov.png"  onclick="displayAgentsLov(<%=checkboxes %>)" width="16" height="16" /></div>
									</div>	
								</td>        
								<td class="iCargoTableDataTd"> 
									<div align="center">
										<ihtml:text property="stockHolders"
											 componentID="CMP_Stockcontrol_Defaults_MaintainStockAgentMapping_StockHolders"
											 value="<%= stockAgent.getStockHolderCode() %>"
											maxlength="12"
											readonly="false"/>
											
										<div class="lovImgTbl valignT"><img id="TypeLov<%=nIndex%>" src="<%=request.getContextPath()%>/images/lov.png"  onclick="displayStockHoldersLov(<%=checkboxes %>)" width="16" height="16"  /></div>

									</div>
								</td>	
							</tr>
						<% checkboxes++; %>
						</logic:notEqual>
						
						</logic:iterate>
					</logic:present>
				</tbody>
			</table>
		</div>
		
		
						</div>
					</div>
				</div>
			</div>
			 </div>
			
			<div class="ic-foot-container">
				<div class="ic-row">
					<div class="ic-button-container paddR5">
						<ihtml:nbutton property="btSave" componentID="CMP_Stockcontrol_Defaults_MaintainStockAgentMapping_Save" accesskey="S" >
							<common:message key="stockholder.defaults.MaintainStockAgentMapping.button.Save" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btClose" componentID="CMP_Stockcontrol_Defaults_MaintainStockAgentMapping_Close" accesskey="O">
							<common:message key="stockholder.defaults.MaintainStockAgentMapping.button.Close" />
						</ihtml:nbutton>
					</div>
				</div>
			</div>

	
		</ihtml:form>
	</div>
	
		
	</body>
</html:html>

