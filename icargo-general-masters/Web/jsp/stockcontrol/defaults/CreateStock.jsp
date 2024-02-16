<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  IN - Inventory Control
* File Name				:  CreateStock.jsp
* Date					:  13-Sep-2005
* Author(s)				:  Smrithi

*************************************************************************/
 --%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.CreateStockForm" %>
<%@ page import = "com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO" %>
<html>

<head>
		
	
	
<bean:define id="form"
	name="CreateStockForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.CreateStockForm"
	toScope="page" />
		
<title>
	<common:message bundle="<%=form.getBundle()%>" key="createstock.title" />
</title>
<common:include type="script" src="/js/stockcontrol/defaults/CreateStock_Script.jsp" />
<meta name="decorator" content="mainpanelrestyledui">
</head>
<body id="bodyStyle">
	
	
	


<business:sessionBean id="collectionRangeVO"
	moduleName="stockcontrol.defaults"
	screenID="stockcontrol.defaults.createstock"
	method="get"
	attribute="collectionRangeVO"/>

	<business:sessionBean id="partnerAirlines"
	moduleName="stockcontrol.defaults"
	screenID="stockcontrol.defaults.createstock"
	method="get"
	attribute="partnerAirlines"/>


<business:sessionBean id="options"
	moduleName="stockcontrol.defaults"
	screenID="stockcontrol.defaults.createstock" method="get"
	attribute="dynamicDocType"/>

<div id="pageDiv" class="iCargoContent ic-masterbg" style="overflow:auto;height:100%">
<ihtml:form action="stockcontrol.defaults.screenloadcreatestock.do ">
<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />
<html:hidden property="buttonStatusFlag" />
<html:hidden property="documentRange"/>
<div class="ic-content-main">
	<div class="ic-head-container">
		<div class="ic-filter-panel">		
			<div class="ic-row">
				<div class="ic-col-100">
					<div class="ic-input-container">
						<div class="ic-row marginT5">
							<div class="ic-input ic-split-35 ic-center ">	
								<logic:present  name="options">
								  <bean:define name="options" id="list" type="java.util.HashMap"/>
										<!--Modified by A-1927 @ NRT on 13-Jul-2007 for NCA Bug Fix starts-->
										<ibusiness:dynamicoptionlist  collection="list"
											id="docType"
											firstlistname="docType"
											componentID="TXT_STOCKCONTROL_DEFAULTS_CREATESTOCK_DYNAMICOPTIONLIST"
											lastlistname="subType" firstoptionlabel="Doc. Type"
											lastoptionlabel="Sub Type"
											optionstyleclass="iCargoComboBox"
											labelstyleclass="iCargoLabelRightAligned"
											firstselectedvalue="<%=form.getDocType()%>"
											lastselectedvalue="<%=form.getSubType()%>"
											docTypeTitle="doctype.tooltip"
											subDocTypeTitle="subdoctype.tooltip"/>
										<!--Modified by A-1927 @ NRT on 13-Jul-2007 for NCA Bug Fix ends-->
										<!--<business:dynamicoptionlist  collection="list"
											firstlistname="docType"
											lastlistname="subType" firstoptionlabel="Doc Type"
											lastoptionlabel="Sub Type"
											optionstyleclass="iCargoComboBox"
											labelstyleclass="iCargoLabelRightAligned"
											firstselectedvalue="<%=form.getDocType()%>"
											lastselectedvalue="<%=form.getSubType()%>"/>-->

								</logic:present>
							</div>
							<div class="ic-input ic-split-12 inline_filedset marginT15">											
								<ihtml:checkbox property="manual"
									componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_MANUAL"/>
								<label>
									<common:message key="createstock.Manual" />
								</label>

							</div>
							<div class="ic-input ic-split-13 inline_filedset marginT15">				
								<ihtml:checkbox property="partnerAirline" 					componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL_CHK" />
								<label>
									<common:message key="stockcontrol.defaults.createstock.partnerAirline.lbl" />
								</label>			
							</div>	
							<div class="ic-input ic-split-15 ic-label-40">			
								<label>	
								<common:message key="stockcontrol.defaults.createstock.awbPrefix.lbl" />
								</label>						
							<ihtml:select property="awbPrefix" componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL_CMB" disabled='true'>
					<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
					<logic:present name="partnerAirlines">					
						<logic:iterate id="airlineLovVO" name="partnerAirlines" type="com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO">
							<logic:present name="airlineLovVO" property="airlineNumber">
							<% String value=airlineLovVO.getAirlineNumber()+"-"+airlineLovVO.getAirlineName()+"-"+airlineLovVO.getAirlineIdentifier(); %>
							<ihtml:option value="<%=value%>"><%=airlineLovVO.getAirlineNumber()%></ihtml:option>
							</logic:present>
						</logic:iterate>
					</logic:present>
				</ihtml:select>
							</div>
							<div class="ic-input ic-split-25">	
								<label>
								<common:message key="stockcontrol.defaults.createstock.partnerAirline.lbl"/>
								</label>									
									<ihtml:text property="airlineName" componentID= "CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL" readonly="true"/>
							</div>
						</div>	
					</div>
				</div>		
			</div>	 
		</div>  
	</div>
	<div class="ic-main-container">
		<div class="ic-row">
			<div class="ic-input ic-split-50">					
				<h4><common:message key="createstock.RangeTobeCreated" /></h4>				
			</div>
			<div class="ic-input ic-split-50">	
				<div class="ic-button-container">
					<a class="iCargoLink" href="javascript:onClickAdd();" >
						<common:message key="createstock.Add" /></a>  |
					 <a  class="iCargoLink" href="javascript:onClickDelete('check');" >
					<common:message key="createstock.Delete" /></a>						
				</div>
			</div>
		</div>
		<div class="ic-row" id="listTable">							
			<div class="ic-row">
				<div class="tableContainer" id="div1" style="height:590px;" >
					<table  class="fixed-header-table" >
						<thead>
							<tr class="iCargoTableHeadingLeft" >
								<td class="iCargoTableHeaderLabel" align="center" width="3%">
								  <ihtml:checkbox property="checkall"
									value="checkall"
									componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_CHECKALL"/>
								</td>
								<td class="iCargoTableHeaderLabel" width="30%">
									<common:message key="createstock.RangeFrom" />
								</td>
								<td class="iCargoTableHeaderLabel" width="30%">
									<common:message key="createstock.RangeTo" />
								</td>
								<td class="iCargoTableHeaderLabel" width="37%" >
									<common:message key="createstock.NoofDocs" />
								</td>
							</tr>
						</thead>
						<tbody id="rangeTableBody">
                 <logic:present  name="collectionRangeVO">
											<%int index=0;%>
			     <logic:iterate id="vo" name="collectionRangeVO" indexId="nIndex">
				
													<logic:present name="vo" property="operationFlag" >
													<bean:define id="flag" name="vo" property="operationFlag" />
													<logic:notEqual name="flag" value="D">
														<tr >
                  <td class="iCargoTableDataTd" style="text-align:center;">
                  	 <ihtml:checkbox property="check"
                  		value="<%= String.valueOf(index) %>"
                  		componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_CHECK" />
															  <logic:equal name="flag" value="I">
															  	<ihtml:hidden property="hiddenOpFlag" value="I"/>
															  </logic:equal>
															  <logic:equal name="flag" value="U">
															  	<ihtml:hidden property="hiddenOpFlag" value="U"/>
															  </logic:equal>
														  </td>
														  <td class="iCargoTableDataTd" style="text-align:center;">
															<ihtml:text property="rangeFrom"
																 componentID= "TXT_STOCKCONTROL_DEFAULTS_CREATESTOCK_RANGEFROM"
																
																 styleId="stockRangeF"
																 indexId="nIndex"
																 value="<%= ((RangeVO)vo).getStartRange() %>"/>
														  </td>
														  <td class="iCargoTableDataTd" style="text-align:center;" >
															<ihtml:text property="rangeTo"
																 componentID= "TXT_STOCKCONTROL_DEFAULTS_CREATESTOCK_RANGETO"
																
																 styleId="stockRangeT"
																 indexId="nIndex"
																 value="<%= ((RangeVO)vo).getEndRange() %>"/>
														  </td>
														  <td class="iCargoTableDataTd" style="text-align:center;">
															<ihtml:text property="noOfDocs" style="text-align:center;"
																componentID= "TXT_STOCKCONTROL_DEFAULTS_CREATESTOCK_NUMBEROFDOCS"
																value="<%= new Long(((RangeVO)vo).getNumberOfDocuments()).toString() %>"
																maxlength="8"
																readonly="true"/>
														  </td>
														</tr>
													</logic:notEqual>
													<logic:equal name="flag" value="D">
														<ihtml:hidden property="check" value="<%= String.valueOf(index) %>"/>
														<ihtml:hidden property="hiddenOpFlag" value="D"/>
														<ihtml:hidden property="rangeFrom" value=""/>
														<ihtml:hidden property="rangeTo" value=""/>
														<ihtml:hidden property="noOfDocs" value=""/>
													</logic:equal>
												  </logic:present>
												  <logic:notPresent name="vo" property="operationFlag" >
													<tr >
													  <td class="iCargoTableDataTd" style="text-align:center;">
														 <ihtml:checkbox property="check"
															value="<%= String.valueOf(index) %>"
															componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_CHECK" />
														 <ihtml:hidden property="hiddenOpFlag" value="U"/>
													  </td>
													  <td class="iCargoTableDataTd" style="text-align:center;">
														<ihtml:text property="rangeFrom"
															 componentID= "TXT_STOCKCONTROL_DEFAULTS_CREATESTOCK_RANGEFROM"
															
															 styleId="stockRangeF"
															 indexId="nIndex"
															 value="<%= ((RangeVO)vo).getStartRange() %>"/>
													  </td>
													  <td class="iCargoTableDataTd" style="text-align:center;">
														<ihtml:text property="rangeTo"
															 componentID= "TXT_STOCKCONTROL_DEFAULTS_CREATESTOCK_RANGETO"
															
															 styleId="stockRangeT"
															 indexId="nIndex"
															 value="<%= ((RangeVO)vo).getEndRange() %>"/>
													  </td>
													  <td class="iCargoTableDataTd" style="text-align:center;">
														<ihtml:text property="noOfDocs"
															componentID= "TXT_STOCKCONTROL_DEFAULTS_CREATESTOCK_NUMBEROFDOCS"
															value="<%= new Long(((RangeVO)vo).getNumberOfDocuments()).toString() %>"
															maxlength="8"
															readonly="true"/>
													  </td>
													</tr>
												  </logic:notPresent>
												<%index=index+1;%>
											  
											</logic:iterate>
										</logic:present>
											<!-- templateRow starts-->
											<tr template="true" id="rangeTableTemplateRow" style="display:none">
											  <td class="iCargoTableDataTd" style="text-align:center;">
												 <ihtml:checkbox property="check"
													componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_CHECK" />
													<input type="hidden" name="hiddenOpFlag" value="NOOP"/>
											  </td>
											  <td class="iCargoTableDataTd" style="text-align:center;">
												<ihtml:text property="rangeFrom"
													 componentID= "TXT_STOCKCONTROL_DEFAULTS_CREATESTOCK_RANGEFROM"
													 
													 styleId="stockRangeF"
													 value=""/>
											  </td>
											  <td class="iCargoTableDataTd" style="text-align:center;">
												<ihtml:text property="rangeTo"
													 componentID= "TXT_STOCKCONTROL_DEFAULTS_CREATESTOCK_RANGETO"
													 
													 styleId="stockRangeT"
													 value=""/>
											  </td>
											  <td class="iCargoTableDataTd" style="text-align:center;">
												<ihtml:text property="noOfDocs"  style="text-align:center;"
													componentID= "TXT_STOCKCONTROL_DEFAULTS_CREATESTOCK_NUMBEROFDOCS"
													maxlength="8"
													readonly="true"
													value="0"/>
											  </td>
											</tr>
											<!-- templateRow ends-->
									  </tbody>
									</table>
                  	</div>
			</div>		
		</div>
	</div>
	<div class="ic-foot-container">
		<div class="ic-row paddR5">
			<div class="ic-button-container">
				<ihtml:nbutton property="btsave" accesskey="S"
					  componentID="BTN_STOCKCONTROL_DEFAULTS_CREATESTOCK_SAVE" >
					  <common:message key="createstock.save" />
				</ihtml:nbutton>
				<ihtml:nbutton property="btclose" accesskey="O"
					 componentID="BTN_STOCKCONTROL_DEFAULTS_CREATESTOCK_CLOSE" >
					 <common:message key="createstock.close" />
				</ihtml:nbutton>
			</div>
		</div>
	</div>
</div>				
				
</ihtml:form>
		   </div>
	
				
		
	</body>
</html>



