<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:   Stock Control
* File Name				:  MissingStock.jsp
* Date					:  27-Feb-2012
* Author(s)				:  Jilu Jacob

*************************************************************************/
 --%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MissingStockForm" %>
<%@ page import="com.ibsplc.icargo.business.stockcontrol.defaults.vo.MissingStockVO" %>




<html:html>

<head>


<bean:define id="form"
	name="MissingStockForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MissingStockForm"
	toScope="page" />
<title>
	
	<common:message bundle="missingstock" key="missingstock.title" />
</title>
<common:include type="script" src="/js/stockcontrol/defaults/MissingStock_Script.jsp"/>

<meta name="decorator" content="popuppanelrestyledui">
</head>
<body id="bodyStyle">
	
	
	
<business:sessionBean 
	id="missingStockVOs" 
	moduleName="stockcontrol.defaults" 
	screenID="stockcontrol.defaults.confirmstock" 
	method="get" attribute="missingStockVOs" />	
<div class="iCargoPopUpContent ic-masterbg"  style="height:100%">

<ihtml:form action="stockcontrol.defaults.missingstockscreenloadpopup.do " styleClass="ic-main-form">


<!--html:hidden property="buttonStatusFlag" /-->
<input type="hidden" name="currentDialogId" />
	<input type="hidden" name="currentDialogOption" />

<html:hidden property="okSuccess" />
	<div class="ic-content-main bg-white">
		<span class="ic-page-title ic-display-none">
			<common:message key="missingstock.MissingStock" />
		</span>
		<div class="ic-head-container">	
			 <div class="ic-filter-panel">
			  		<div class="ic-row"> 
					<div class="ic-input-container">
						<div class="ic-input ic-split-33">
									<label><common:message key="stockcontrol.defaults.missingstock.fromstockholder.lbl" /></label>
									<ihtml:text property="stockHolderCode" componentID= "TXT_STOCKCONTROL_DEFAULTS_MISSINGSTOCK_FRMSTOCKHOLDER" readonly="true"/>
						</div>
						<div class="ic-input ic-split-33">
									<label><common:message key="stockcontrol.defaults.missingstock.rangefrom.lbl" /></label>
									<ihtml:text property="startRange" style="text-align:right;"  componentID= "TXT_STOCKCONTROL_DEFAULTS_MISSINGSTOCK_RANGEFRM" readonly="true"/>
						</div>
						<div class="ic-input ic-split-33">
									<label><common:message key="stockcontrol.defaults.missingstock.rangeto.lbl" /></label>
									<ihtml:text property="endRange" style="text-align:right;"  componentID= "TXT_STOCKCONTROL_DEFAULTS_MISSINGSTOCK_RANGETO" readonly="true"/>
						</div>
					</div>
					</div>
			</div>
		</div>
		<div class="ic-main-container">
			  		
					<div class="ic-row">
										<common:message key="missingstock.missingstockrange" />
									<div class="ic-button-container">
										<a class="iCargoLink" href="javascript:onClickAdd();" >
											<common:message key="missingstock.Add" /></a>  |
										<a  class="iCargoLink" href="javascript:onClickDelete('check');" >
											<common:message key="missingstock.Delete" /></a>
									</div>
					</div>
					<div class="ic-row">
							<div class="tableContainer" id="div1" style="height:228px;">
											<table width="100%"  class="fixed-header-table" width="100%">
												<thead>
													<tr class="iCargoTableHeadingLeft">
														<td class="iCargoTableHeaderLabel" align="center" width="4%">
															<ihtml:checkbox property="checkall"
															value="checkall"
															componentID="CHK_STOCKCONTROL_DEFAULTS_MISSINGSTOCK_CHECKALL"/>
															
														</td>
														<td class="iCargoTableHeaderLabel" width="30%">
															<common:message key="missingstock.RangeFrom" />
														</td>
														<td class="iCargoTableHeaderLabel" width="30%">
															<common:message key="missingstock.RangeTo" />
														</td>
														<td class="iCargoTableHeaderLabel">
															<common:message key="missingstock.remarks" />
														</td>
											  		</tr>
												</thead>
												<tbody id="rangeTableBody">
													<!-- templateRow starts-->
														<tr template="true" id="rangeTableTemplateRow" style="display:none">
															<td class="iCargoTableDataTd" align="center">
																<ihtml:checkbox property="check"
																componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_CHECK" />
																<input type="hidden" name="hiddenOpFlag" value="NOOP"/>
															</td>
															<td class="iCargoTableDataTd">
																<ihtml:text property="rangeFrom"
																	componentID= "TXT_STOCKCONTROL_DEFAULTS_MISSING_RANGEFROM"
																	maxlength="7"
																	style="text-align:right;" 
																	styleId="stockRangeF"
																value=""/>
															</td>
															<td class="iCargoTableDataTd">
																<ihtml:text property="rangeTo"
																	componentID= "TXT_STOCKCONTROL_DEFAULTS_MISSING_RANGETO"
																	maxlength="7"
																	style="text-align:right;" 
																	styleId="stockRangeT"
																value=""/>
															</td>
															
															<td class="iCargoTableDataTd">
																<ihtml:text property="remarks"
																    componentID= "TXT_STOCKCONTROL_DEFAULTS_MISSING_REMARKS"
																	maxlength="250"
																	style="text-align:right;" 
																	styleId="remarks"
																	value=""/>
															</td>
															
														</tr>
												<logic:present name="missingStockVOs">
												
													<%int index=0;%>
													<logic:iterate id="missingVO" name="missingStockVOs" indexId="nIndex">
													
													<!-- templateRow starts-->
														<tr>
															<td class="iCargoTableDataTd" align="center">
																<ihtml:checkbox property="check"
																componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_CHECK" />
																<input type="hidden" name="hiddenOpFlag" value="I"/>
															</td>
															<td class="iCargoTableDataTd">
																<ihtml:text property="rangeFrom"
																	componentID= "TXT_STOCKCONTROL_DEFAULTS_MISSING_RANGEFROM"
																	maxlength="7"
																	style="text-align:right;" 
																	styleId="stockRangeF"
																value="<%= ((MissingStockVO)missingVO).getMissingStartRange() %>"/>
															</td>
															<td class="iCargoTableDataTd">
																<ihtml:text property="rangeTo"
																	componentID= "TXT_STOCKCONTROL_DEFAULTS_MISSING_RANGETO"
																	maxlength="7"
																	style="text-align:right;" 
																	styleId="stockRangeT"
																value="<%= ((MissingStockVO)missingVO).getMissingEndRange() %>"/>
															</td>
															
															<td class="iCargoTableDataTd">
																<ihtml:text property="remarks"
																    componentID= "TXT_STOCKCONTROL_DEFAULTS_MISSING_REMARKS"
																	maxlength="250"
																	style="text-align:right;" 
																	styleId="remarks"
																	value="<%= ((MissingStockVO)missingVO).getMissingRemarks() %>"/>
															</td>
															
														</tr>
														<!-- templateRow ends-->
															 </logic:iterate>
																<%index=index+1;%>
																	</logic:present>
																
												<logic:notPresent name="missingStockVOs" >
												
												 </logic:notPresent>
												
												</tbody>
											</table>
											</div>
										</div>

</div>
<div class="ic-foot-container">
				<div class="ic-row">
				<div class="ic-button-container paddR5">
							<ihtml:button property="btok"
								componentID="BTN_STOCKCONTROL_DEFAULTS_MISSINGSTOCK_OK" >
								<common:message key="missingstock.ok" />
							</ihtml:button>
						
							<ihtml:button property="btcancel"
								componentID="BTN_STOCKCONTROL_DEFAULTS_MISSINGSTOCK_CANCEL" >
								<common:message key="missingstock.cancel" />
							</ihtml:button>
					</div>
					</div>
</div>
</div>  
</ihtml:form>
</div>   

   <jsp:include page="/jsp/includes/popupFooterSection.jsp"/>
		   <logic:present name="icargo.uilayout">
		       <logic:equal name="icargo.uilayout" value="true">
		       <jsp:include page="/jsp/includes/popupfooter_new_ui.jsp" />
		       </logic:equal>

		       <logic:notEqual name="icargo.uilayout" value="true">
		       <jsp:include page="/jsp/includes/popupfooter_new.jsp" />
		       </logic:notEqual>
		   </logic:present>
		   <common:registerCharts/>
		   <common:registerEvent />

	</body>
</html:html>
