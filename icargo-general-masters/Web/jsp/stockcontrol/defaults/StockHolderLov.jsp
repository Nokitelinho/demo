<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  STK - Stock Control
* File Name				:  StockHolderLov.jsp
* Date					:  11-Jun-2015
* Author(s)				:  Ramesh Chandra Pradhan
*************************************************************************/
 --%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.StockHolderLovForm" %>
<%@ page import="com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderLovVO"%>
<%@ page import = "com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO" %>



<html:html>
<head>
		
			
	
	<bean:define id="form"
			name="StockHolderLovForm"
			type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.StockHolderLovForm"
			toScope="page" />
<title>
	<common:message bundle="<%=form.getBundle()%>" key="stockholder.title" />
</title>
	<meta name="decorator" content="popuppanelrestyledui">
	<common:include type="script" src="/js/stockcontrol/defaults/StockHolderLov_Script.jsp" />
</head>

<body id="bodyStyle">
	
	
	
	<div class="iCargoPopUpContent ic-masterbg" id="mainDiv" style="overflow:auto; width:100%;">	
		<ihtml:form action="/stockcontrol.defaults.screenloadstockholderlov.do" styleClass="ic-main-form">

		<%
			StockHolderLovForm stockHolderLovForm= (StockHolderLovForm)request.getAttribute("StockHolderLovForm");
		%>

	<business:sessionBean id="listFromSession"
		moduleName="stockcontrol.defaults"
		screenID="stockcontrol.defaults.common.stockholderlov"
		method="get"
		attribute="stockHolderLovVOs" />

	<business:sessionBean id="stockHoldersFromSession"
		moduleName="stockcontrol.defaults"
		screenID="stockcontrol.defaults.common.stockholderlov"
		method="get"
		attribute="prioritizedStockHolders" />


	<logic:present name="listFromSession">
		<bean:define id="pageList" name="listFromSession" toScope="page"/>
	</logic:present>

		<html:hidden property="lastPageNumber" />
		<html:hidden property="displayPage"  />
		<html:hidden property="codeName"  />
		<html:hidden property="typeName"  />
		<html:hidden property="readOnly"  />
		<html:hidden property="stockHolderTypeValue"  />
		<html:hidden property="stkHolderType"/>
		<html:hidden property="formNumber"/>
		<html:hidden property="documentType"  />
		<html:hidden property="documentSubType" />
		<html:hidden property="multiselect" />
		<ihtml:hidden name="form" property="lovaction"  />
		<ihtml:hidden name="form" property="selectedValues"  />
		<ihtml:hidden name="form" property="pagination" />
		<ihtml:hidden name="form" property="formCount" />
		<ihtml:hidden name="form" property="lovTxtFieldName" />
		<ihtml:hidden name="form" property="lovDescriptionTxtFieldName" />
		<ihtml:hidden name="form" property="index" />
		<html:hidden property="title" /> 

		<div class="ic-content-main">
			<span class="ic-page-title ic-display-none">
				<common:message key="stockholder.StockHolderLov" />
			</span>
			<div class="ic-head-container">
				<div class="ic-filter-panel">
					<div class="ic-input-container">
						<div class="ic-row">
					
							<div class="ic-input ic-mandatory ic-split-25"> 
								<label class="ic-label-38">
									<common:message key="stockholder.StockHolderType" />
								</label>
								<bean:define id="readOnly" name="form" property="readOnly"/>
								<bean:define id="stockHolderTypeValue" name="form" property="stockHolderTypeValue"/>
								<logic:equal value="N" name="readOnly">
								<ihtml:select property="stockHolderType"
									componentID="CMB_STOCKCONTROL_DEFAULTS_STOCKHOLDERLOV_STOCKHOLDERTYPE"
									value="<%=form.getStockHolderType()%>" >
									<logic:present name="stockHoldersFromSession">
										<bean:define id="stockHolderList" name="stockHoldersFromSession" />
											<html:option value=""><common:message key="combo.select"/></html:option>
												<logic:iterate id="priorityVO" name="stockHolderList" >
													<html:option value= "<%=((StockHolderPriorityVO)priorityVO).getStockHolderType()%>">
													<%=((StockHolderPriorityVO)priorityVO).getStockHolderDescription()%>
													</html:option>
												</logic:iterate>
									</logic:present>
								</ihtml:select>
								</logic:equal>
								<logic:notEqual value="N" name="readOnly">
									<logic:present name="stockHoldersFromSession">
										<bean:define id="stockHolderList" name="stockHoldersFromSession" />
												<logic:iterate id="priorityVO" name="stockHolderList" >
													<logic:equal value="<%=((StockHolderPriorityVO)priorityVO).getStockHolderType()%>" name="stockHolderTypeValue">
														<ihtml:text property="stockHolderType"
															componentID="TXT_STOCKCONTROL_DEFAULTS_STOCKHOLDERLOV_STOCKHOLDERTYPE"
															value="<%=((StockHolderPriorityVO)priorityVO).getStockHolderDescription()%>"
															readonly="true"/>
													</logic:equal>
												</logic:iterate>
									</logic:present>
								</logic:notEqual>	
							</div>
							<div class="ic-input ic-split-25"> 
								<label class="ic-label-20">
									<common:message key="stockholder.Code" />
								</label>	
								<ihtml:text property="code"
										componentID="TXT_STOCKCONTROL_DEFAULTS_STOCKHOLDERLOV_STOCKHOLDERCODE"
										maxlength="12"/>
							</div>
							<div class="ic-input ic-split-25"> 
							 <label class="ic-label-27">
									<common:message key="stockholder.Description" />
								</label>
								<ihtml:text property="description"
										componentID="TXT_STOCKCONTROL_DEFAULTS_STOCKHOLDERLOV_DESCRIPTION"
										maxlength="25"/>
							</div>
                            <div class="ic-button-container">
								<ihtml:button property="btList" componentID="BTN_STOCKCONTROL_DEFAULTS_STOCKHOLDERLOV_LIST">
									<common:message key="stockholderlov.list"/>
								</ihtml:button>
								<ihtml:button property="btClear" componentID="BTN_STOCKCONTROL_DEFAULTS_STOCKHOLDERLOV_CLEAR" styleClass="btn-inline btn-secondary">
									<common:message key="stockholderlov.clear"/>
								</ihtml:button>
							</div>    
						</div>
						<!-- <div class="ic-row">
							
						</div>-->
					
					
					</div>
				</div>	
			</div>
			<div class="ic-main-container" style="overflow:hidden">	
				<div class="ic-row">
							<div class="ic-col-50">
								<logic:present name="pageList">
									<common:paginationTag pageURL="javascript:submitList('lastPageNum','displayPage')"
											name="pageList"
											display="label"
											labelStyleClass="iCargoResultsLabel"
											lastPageNum="<%=stockHolderLovForm.getLastPageNumber() %>" />
								</logic:present>
								<logic:notPresent name="pageList">
									&nbsp;
								</logic:notPresent>
							</div>
							<div class="ic-col-50">
								<div class="ic-button-container">
								<logic:present name="pageList">
									<common:paginationTag
											linkStyleClass="iCargoLink"
											disabledLinkStyleClass="iCargoLink"
											pageURL="javascript:submitList('lastPageNum','displayPage')"
											name="pageList"
											display="pages"
											lastPageNum="<%=stockHolderLovForm.getLastPageNumber()%>" />
								</logic:present>
								<logic:notPresent name="pageList">
							  		&nbsp;
								</logic:notPresent>
							</div>
						</div>
						</div>
						<div class="ic-row">
			<!-- Data Table Start -->			
		<div class="tableContainer" id="div1" style="height:180px;">
			<table class="fixed-header-table" id="stockHolderTable">
				<thead>
					<tr class="iCargoTableHeadingLeft">
						<td class="ic-center" width="11%"> &nbsp;</td>
						<td width="44%"><common:message key="stockholder.Code" /></td>
						<td width="45%"><common:message key="stockholder.Description" /></td>
					</tr>
				</thead>
				<tbody>

				<logic:present name="pageList" >

					<logic:iterate id="listSession" name="pageList" indexId="rowCount" >
						
		 	 
						
						<logic:notEqual name="form" property="multiselect" value="Y">
						<bean:define name="StockHolderLovForm" property="codeName" id="strLovTxtFieldName" />
						<bean:define name="StockHolderLovForm" property="stockHolderType" id="strLovDescriptionTxtFieldName" />			
						<tr ondblclick="setValueOnDoubleClick('<%=((StockHolderLovVO)listSession).getStockHolderCode() %>','<%=((StockHolderLovVO)listSession).getStockHolderName()%>',
						'<%= strLovTxtFieldName%>','<%=strLovDescriptionTxtFieldName %>','<%=form.getIndex() %>')">
						</logic:notEqual>
			
						<logic:equal name="form" property="multiselect" value="Y">
						<tr>
							<bean:define id="multiselect" name="form" property="multiselect" />  
							<bean:define id="strMultiselect" name="form" property="multiselect" />
							<bean:define id="strFormCount" name="form" property="formCount"/>
							<bean:define id="strLovTxtFieldName" name="form" property="lovTxtFieldName"  />
							<bean:define id="strLovDescriptionTxtFieldName" name="form" property="lovDescriptionTxtFieldName" />
							<bean:define id="strSelectedValues" name="form" property="selectedValues" />
							<bean:define id="arrayIndex" name="form" property="index" />
						<td class="iCargoTableTd" style="text-align:center;">
					
							<%

							if(((String)stockHolderLovForm.getSelectedValues()).contains(((StockHolderLovVO)listSession).getStockHolderCode())){ %>
								<input type="checkbox" name="stationChecked" value="<%=((StockHolderLovVO)listSession).getStockHolderCode()%>"  checked="checked" onclick="onMultiSelect('stationChecked');"/>
							<%}else{ %>
								<input type="checkbox" name="stationChecked" value="<%=((StockHolderLovVO)listSession).getStockHolderCode()%>"  onclick="onMultiSelect('stationChecked');"/>
							<% } %>
						</td>
						</logic:equal>

						<logic:notEqual name="form" property="multiselect" value="Y">
						<td class="iCargoTableTd" style="text-align:center;">
							<%String checkVal = ((StockHolderLovVO)listSession).getStockHolderCode();%>				
						
						<%

						if(   ((String)stockHolderLovForm.getSelectedValues()).equals(((StockHolderLovVO)listSession).getStockHolderCode())){ %>
							<input type="checkbox" name="stationChecked" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>');" checked="checked"/>
						<%}else{ %>
							<input type="checkbox" name="stationChecked" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>');" />
						<% } %>
						
						</td>
						</logic:notEqual>
						
			
							<td class="iCargoTableTd"><%= ((StockHolderLovVO)listSession).getStockHolderCode() %></td>
							<td class="iCargoTableTd"><%= ((StockHolderLovVO)listSession).getStockHolderName() %></td>
							
						</tr>

					</logic:iterate>
				</logic:present>
				</tbody>
			</table>
		</div>					
			<!-- Data Table End -->			
				
						</div>
						<div class="ic-row">
							<logic:equal name="form" property="multiselect" value="Y">
								<div id="selectiondiv" class="multiselectPanelStyle" name="Stockholders">
								</div>
							</logic:equal>
						</div>
				
			
			</div>
			<div class="ic-foot-container">
				<div class="ic-row">
					<div class="ic-button-container PaddR15">
						<ihtml:button property="btOk" componentID="BTN_STOCKCONTROL_DEFAULTS_STOCKHOLDERLOV_OK" >
							<common:message key="stockholderlov.ok"/>
						</ihtml:button>
						<ihtml:button property="btClose" componentID="BTN_STOCKCONTROL_DEFAULTS_STOCKHOLDERLOV_CLOSE" styleClass="btn-inline btn-secondary" >
							<common:message key="stockholderlov.close"/>
						</ihtml:button>
					</div>
				</div>
			</div>
		</div>

		</ihtml:form>
	</div>
	  
			
	</body>
</html:html>

