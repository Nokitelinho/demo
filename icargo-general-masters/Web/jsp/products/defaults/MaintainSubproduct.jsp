<%--********************************************************************************
* Project	 		: iCargo
* Module Code & Name: stockcontrol
* File Name			: icargo_list_product.jsp
* Date				: 15-07-2005
* Author(s)			: Akhila.S
 ***********************************************************************************--%>

<%@ page language="java" %>

<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>


<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ include file="/jsp/includes/contextpath.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainSubProductForm" %>
<%@ page import="com.ibsplc.icargo.business.products.defaults.vo.RestrictionStationVO" %>
<%@ page import="com.ibsplc.icargo.business.products.defaults.vo.RestrictionSegmentVO" %>
<%@ page import="com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		
<bean:define id="form"
name="MaintainSubProductForm"
type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainSubProductForm"
toScope="page" />
<title><common:message bundle="<%=form.getBundle()%>" key="screenTitle" scope="request"/></title>
<common:include type="script"
				src="/js/products/defaults/MaintainSubProduct_Script.jsp" />
<meta name="decorator" content="mainpanelrestyledui">
	<common:include type="script"  src="/js/tabbedpane.js" />
</head>
<body id="bodyStyle">
	
<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>

<business:sessionBean id="subProductVO" moduleName="product.defaults" screenID="products.defaults.maintainsubproducts" method="get" attribute="subProductVO" />
<business:sessionBean id="productService" moduleName="product.defaults" screenID="products.defaults.maintainsubproducts" method="get" attribute="productService" />

<logic:present name="subProductVO" >
<bean:define id="retSubProductVO" name="subProductVO"  />
</logic:present>

<logic:present name="productService" >
<bean:define id="prdservice" name="productService"  />
</logic:present>


  <bean:define id="form"
	name="MaintainSubProductForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainSubProductForm"
	toScope="page" />

<div class="iCargoContent ic-masterbg" style="overflow:auto;">
		<ihtml:form action="products.defaults.maintainsubproductscreenload.do">
        <jsp:include page="/jsp/includes/tab_support.jsp" />
		<html:hidden property="nextAction" />
		<html:hidden property="lovAction" />
		<html:hidden property="parentSession"  />
		<html:hidden property="checkedExternal" />
		<html:hidden property="checkedInternal" />
		<html:hidden property="checkedTransit" />
		<html:hidden property="saveSuccessful" />
        	<html:hidden property="mode" value="<%=form.getMode()%>" />
		<input type="hidden" name="currentDialogId" />
		<input type="hidden" name="currentDialogOption" />

		<ihtml:hidden property="fromListSubproduct" />

<div class="ic-content-main" style="background-color:white;">
	<span class="ic-page-title ic-display-none">
		<common:message  key="title" scope="request"/>
	</span>
	<div class="ic-head-container">
		<div class="ic-row">
			<div class="ic-input-container ic-input-round-border" id="subProductDetails">
				<div class="ic-row">
					<div class="ic-col-25 ic-label-30">
						<div class="ic-input ic-split-100">
							<label>
								<common:message  key="productName" scope="request"/>
							</label>
							<ihtml:text property="productName"
								componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_PRDNAME"
								readonly="true" />
						</div>
						<div class="ic-input ic-split-100">
							<label>
								<common:message  key="startDate" scope="request"/>
							</label>
							<ihtml:text property="startDate"
								componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_STARTDATE"
								readonly="true"/>
						</div>
						<div class="ic-input ic-split-100">
							<label>
								<common:message  key="transportMode" scope="request"/>
							</label>
							<ihtml:text property="transportMode"
								componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_TRANSPORTMODE"
								readonly="true"/>
						</div>
						<div class="ic-input ic-split-100">
							<label>
								<common:message  key="priority" scope="request"/>
							</label>
							<ihtml:text property="priority"
								componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_SCC"
								readonly="true"/>
						</div>
					</div>
					<div class="ic-col-25 ic-label-30">
						<div class="ic-input ic-split-100">
							<label>
								<common:message  key="status" scope="request"/>
							</label>
							<ihtml:text property="status"
								componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_PRDSTATUS"
								readonly="true" />
						</div>
						<div class="ic-input ic-split-100">
							<label>
								<common:message  key="endDate" scope="request"/>
							</label>
							<ihtml:text property="endDate"
								componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_ENDDATE"
								readonly="true" />
						</div>
						<div class="ic-input ic-split-100">
							<label>
								<common:message  key="scc" scope="request"/>
							</label>
							<ihtml:text property="productScc"
								componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_SCC"
								readonly="true" />
						</div>					
					</div>
					<div class="ic-col-50">
						<div class="ic-row">
							<h3>
								<common:message  key="services" scope="request"/>
							</h3>
						</div>
						<div class="ic-row">
							<div id="div1" class="tableContainer" style="width:100%;height:90px;">
								<table class="fixed-header-table" width="100%">
									<thead>
										<tr class="iCargoTableHeadingLeft">
										  <td width="50%"> <common:message  key="serviceCode" scope="request"/></td>
										  <td width="50%"> <common:message  key="serviceDescription" scope="request"/></td>
										</tr>
									</thead>
								  <tbody>
									<logic:present name="prdservice" >
									<bean:define id="list" name="prdservice"  toScope="page"/>
										<logic:iterate id="vo" name="list" indexId="nIndex">
											<tr>
												<td class="iCargoTableDataTd"><bean:write name="vo" property="serviceCode" /></td>
												<td class="iCargoTableDataTd"><bean:write name="vo" property="serviceDescription" /></td>
											</tr>
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
    </div>
    <div class="ic-main-container">
		<div class="ic-row">
			<div id="container1">
				<ul class="tabs">

					<li><button id="tab1" type="button" accesskey="m" class="tab" onClick="return showPane(event,'pane1',this)">
							<common:message  key="products.defaults.MilestoneTab" />
						</button>
					</li>
					<li><button id="tab2" type="button" accesskey="r" class="tab" onClick="return showPane(event,'pane2', this)">
							<common:message  key="products.defaults.RestrictionTab" />
						</button>
					</li>
				</ul>
				<div class="tab-panes">

				<div id="pane1" class="content">
					<div class="ic-row">
						<h3>Time Specification</h3>
						<div class="ic-button-container">
							<ul class="ic-list-link">
								<li>
									<a href="#" class="iCargoLink" onclick="addDataFromLOV1('products.defaults.beforeOpenSubLovMilestoneNew.do','products.defaults.screenloadMileStoneLov.do','products.defaults.selectSubProductMilestoneNew.do','MAINTAIN_SUB_PRODUCT_SESSION','div2')">Add</a>
								</li>|
								<li>
									<a href="#" class="iCargoLink" onclick="deleteTableRow1('products.defaults.deleteSubProductMilestoneNew.do','mileStoneRowId','div2')">Delete</a></td>
								</li>
							</ul>
						</div>
					</div>
					<div class="ic-row">
						<div id="div2" class="tableContainer" style="width:100%; height:250px;">
							<table class="fixed-header-table" width="100%">
								<thead>
									<tr class="iCargoTableHeadingLeft">
									  <td width="4%" class="iCargoTableHeadingCenter" align="center">
									  <input type="checkbox" name="checkAllMilestone"
									  onclick="updateHeaderCheckBox(this.form,this,this.form.mileStoneRowId)"
									  title='<common:message  bundle="MaintainSubProduct" key="milestone"
											scope="request"/>'/></td>
									  <td width="10%"> <common:message  key="milestone" scope="request"/></td>
									  <td width="7%"> <common:message  key="type" scope="request"/></td>
									  <td width="10%"> <common:message  key="minTime" scope="request"/></td>
									  <td width="10%"> <common:message  key="maxTime" scope="request"/></td>
									  <td width="6%"> <common:message  key="external" scope="request"/></td>
									  <td width="6%"> <common:message  key="internal" scope="request"/></td>
									  <td width="6%"> <common:message  key="Transit" scope="request"/></td>
									  <td width="10%"> <common:message  key="alert" scope="request"/></td>
									  <td width="9%"> <common:message  key="chaser" scope="request"/></td>
									  <td width="10%"> <common:message  key="chaserFrequency" scope="request"/></td>
									  <td width="12%"> <common:message  key="maxNo" scope="request"/></td>
									</tr>
								  </thead>
								  <tbody>
									<business:sessionBean id="milestoneVOsFromSession" moduleName="product.defaults"
										screenID="products.defaults.maintainsubproducts"
										method="get"
										attribute="productEventVOs" />


									<logic:present name="milestoneVOsFromSession" >
										  <bean:define id="eventList" name="milestoneVOsFromSession" />
											<% String operFlag = null;
											 int index = 0;
											 String external = "false";
											 String internal = "false";
											 ArrayList<ProductEventVO> list = (ArrayList<ProductEventVO>)eventList; %>

										<logic:iterate id="eventVO" name="eventList" indexId="nIndex">
											<% operFlag = ((ProductEventVO)eventVO).getOperationFlag();
												if(!"D".equals(operFlag)){
											%>

									  <tr>
										 <html:hidden property="milestoneOpFlag" value="<%=(String)operFlag%>" />
										  <html:hidden property="isRowModified" value="0" />
											<html:hidden property="isNewRowModified" value="0" />
											<td class="iCargoTableDataTd ic-center">
												<ihtml:checkbox property="mileStoneRowId"
													value="<%=new Integer(index).toString()%>"
													componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_MILESTONE"
													onclick="toggleTableHeaderCheckbox('mileStoneRowId',this.form.checkAllMilestone)"/>
											</td>

											<td class="iCargoTableDataTd"><%=((ProductEventVO)eventVO).getEventCode()%></td>
											<html:hidden property="milestone" value="<%=((ProductEventVO)eventVO).getEventCode()%>" />
											<td class="iCargoTableDataTd"><%=((ProductEventVO)eventVO).getEventType()%></td>
											<td class="iCargoTableDataTd">

											<ibusiness:releasetimer property="minTime"
											componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_MINTIME"
											id="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_MINTIME"
											type="asTimeComponent" indexId="nIndex"
											value="<%=((ProductEventVO)eventVO).getMinimumTimeStr()%>"
											/>
											</td>
											<td class="iCargoTableDataTd">
											<ibusiness:releasetimer property="maxTime"
											componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_MAXTIME"
											id="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_MAXTIME"
											type="asTimeComponent" indexId="nIndex"
											value="<%=((ProductEventVO)eventVO).getMaximumTimeStr()%>"
											/>

											</td>
											<td  class="iCargoTableTd ic-center">

											<bean:define name="eventVO" property="internal" id="isInternal" toScope="page"/>
											<bean:define name="eventVO" property="transit" id="isTransit" toScope="page"/>

											<logic:equal name="eventVO" property="external" value="true">
											<bean:define name="eventVO" property="external" id="isExternal"
												toScope="page"/>
												<input type="checkbox" name="isExternal"
												value="<%=isExternal.toString()%>"
												checked="checked" title='<common:message  bundle="MaintainSubProduct" key="products.defaults.milestone.external"
												scope="request"/>'/>
											</logic:equal>
											<logic:notEqual name="eventVO" property="external" value="true">
											<bean:define name="eventVO" property="external" id="isExternal" toScope="page"/>
												<input type="checkbox" name="isExternal"
												value="<%=isExternal.toString()%>"
												title='<common:message  bundle="MaintainSubProduct" key="products.defaults.milestone.external"
												scope="request"/>'/>
											</logic:notEqual>

											</td>

											<td  class="iCargoTableTd ic-center">

											<logic:equal name="eventVO" property="internal" value="true">
												<input type="checkbox" name="isInternal" value="<%=isInternal.toString()%>"
													checked="checked"
													title='<common:message  bundle="MaintainSubProduct"
														key="products.defaults.milestone.internal"
														scope="request"/>'/>
											</logic:equal>
											<logic:notEqual name="eventVO" property="internal" value="true">
												<input type="checkbox" name="isInternal"
												value="<%=isInternal.toString()%>"
													title='<common:message  bundle="MaintainSubProduct"
														key="products.defaults.milestone.internal"
														scope="request"/>'/>
											</logic:notEqual>
											</td>
											<td class="iCargoTableTd ic-center">
												<logic:equal name="eventVO" property="transit" value="true">
													<input type="checkbox" name="isTransit" value="<%=isTransit.toString()%>"
														checked="checked"
														title='<common:message  bundle="MaintainSubProduct" key="products.defaults.milestone.transit"
														scope="request"/>'  />
												</logic:equal>
												<logic:notEqual name="eventVO" property="transit" value="true">
													<input type="checkbox" name="isTransit"
													value="<%=isTransit.toString()%>"
													title='<common:message  bundle="MaintainSubProduct" key="products.defaults.milestone.transit"
													scope="request"/>' />
												</logic:notEqual>
													</td>

											<td class="iCargoTableDataTd">

											<ihtml:text property="alertTime"
											value="<%=new Integer(new Double(((ProductEventVO)eventVO).getAlertTime()).intValue()).toString()%>"
												componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_ALERTTIME"/></td>
											<td class="iCargoTableDataTd">

											<ihtml:text property="chaserTime"
											value="<%=new Integer(new Double(((ProductEventVO)eventVO).getChaserTime()).intValue()).toString()%>"
												componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_CHASER"/></td>

												<%
													double freq = ((ProductEventVO)eventVO).getChaserFrequency();
													int frequency = (int)freq;
												%>


											<td class="iCargoTableDataTd">

											<ihtml:text property="chaserFrequency"
											value="<%=(new Integer(frequency)).toString()%>"
														componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_FREQUENCY" /></td>
											<td class="iCargoTableDataTd">
											<ihtml:text property="maxNoOfChasers"
											value="<%=new Integer(((ProductEventVO)eventVO).getMaxNoOfChasers()).toString()%>"
														componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_MAXNOOFCHASERS" /></td>
											  </tr>

										<%
										}else{

										%>


													<bean:define name="eventVO" property="external" id="ext" />
													<bean:define name="eventVO" property="internal" id="inter" />
												<bean:define name="eventVO" property="transit" id="trans" />
												<html:hidden property="milestoneOpFlag" value="<%=(String)operFlag%>" />
												<html:hidden property="isRowModified" value="0" />
												<html:hidden property="isNewRowModified" value="0" />
												<html:hidden property="mileStoneRowId" value="<%=new Integer(index).toString()%>" />
												<html:hidden property="eventCode" value="<%=((ProductEventVO)eventVO).getEventCode()%>" />
												<html:hidden property="eventType" value="<%=((ProductEventVO)eventVO).getEventType()%>" />
												<html:hidden property="minTime" value="<%=new Double(((ProductEventVO)eventVO).getMinimumTime()).toString()%>" />
												<html:hidden property="maxTime" value="<%=new Double(((ProductEventVO)eventVO).getMaximumTime()).toString()%>" />
												<html:hidden property="isExternal" value="<%=ext.toString()%>" />
												<html:hidden property="isInternal" value="<%=inter.toString()%>" />
												<html:hidden property="isTransit" value="<%=trans.toString()%>" />
												<html:hidden property="alertTime" value="<%=new Integer(new Double(((ProductEventVO)eventVO).getAlertTime()).intValue()).toString()%>" />
												<html:hidden property="chaserTime" value="<%=new Integer(new Double(((ProductEventVO)eventVO).getChaserTime()).intValue()).toString()%>" />
												<html:hidden property="chaserFrequency" value="<%=new Double(((ProductEventVO)eventVO).getChaserFrequency()).toString()%>" />
												<html:hidden property="maxNoOfChasers" value="<%=new Integer(((ProductEventVO)eventVO).getMaxNoOfChasers()).toString()%>" />

											<%}
											index++;

											%>
											</logic:iterate>
										</logic:present>

										</tbody>
							</table>
						</div>
						<div class="ic-row">
							<div class="ic-input-container">
								<div class="ic-col-50 ic-label-30">
									<div class="ic-input ic-split-100">
										<label>
											 <common:message  key="handlingInfo" scope="request"/>
										</label>
										<ihtml:textarea
										  property="handlingInfo"
										  componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_HANDLINGINFO"
										  rows="2" cols="40" ></ihtml:textarea>
									</div>
								</div>
								<div class="ic-col-50 ic-label-30">
									<div class="ic-input ic-split-100">
										<label>
											 <common:message  key="remarks" scope="request"/>
										</label>
										<ihtml:textarea property="remarks"
										  componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_REMARKS"
										  rows="2" cols="40">
										 </ihtml:textarea>
									</div>									
								</div>
							</div>
						</div>
					</div>               
            </div>
			<div id="pane2" class="content">
				<div class="ic-col-30">
					<div class="ic-section ic-border">
						<div class="ic-row iCargoTableTitle">
							Commodity Restriction
						</div>
						<business:sessionBean id="commodityVOs" moduleName="product.defaults"
							screenID="products.defaults.maintainsubproducts" method="get" attribute="commodityVOs" />
						<div class="ic-row">
							<div class="ic-input ic-split-50">
								<ihtml:radio property="commodityStatus"
									value="Restrict" componentID="RAD_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_RESTRICTCOMMODITY" />
								<common:message  key="restrict" scope="request"/>
								<ihtml:radio property="commodityStatus"
									value="Allow" componentID="RAD_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_ALLOWCOMMODITY" />
								<common:message  key="allow" scope="request"/>
							</div>
							<div class="ic-input ic-split-50">
								<div class="ic-button-container">
									<ul class="ic-list-link">
										<li>
											<a href="#" class="iCargoLink"
												onclick="addDataFromLOV1('products.defaults.beforeOpenSubLovCommodityNew.do','products.defaults.screenloadCommodityLov.do','products.defaults.selectSubProductCommodityNew.do','MAINTAIN_SUB_PRODUCT_SESSION','div3')">										Add </a>
										</li>
										<li>
											<a href="#" class="iCargoLink" onclick="deleteTableRow1('products.defaults.deleteSubProductCommodityNew.do','commodityCheck','div3')">Delete</a>
										</li>
									</ul>
								</div>
							</div>
						</div>
						<div class="ic-row">
							<div id="div3" class="tableContainer"  style="width:100%; height:102px;">
								<table class="fixed-header-table" width="100%">
									<thead>
									 <tr class="iCargoTableHeadingLeft">
										<td width="18%" class="iCargoTableHeadingCenter" align="center">
										<input type="checkbox" name="checkbox63" value="checkbox"
										onclick="updateHeaderCheckBox(this.form,this,this.form.commodityCheck)"
											title='<common:message  bundle="MaintainSubProduct" key="products.defaults.specifycomdty"
											scope="request"/>' /></td>
										<td width="82%"> <common:message  key="commodity" scope="request"/></td>
									  </tr>
									</thead>
									<tbody>
										<logic:present name="commodityVOs" >
												<bean:define id="commodityList" name="commodityVOs"/>

											 <logic:iterate id="comdtyVO" name="commodityList" indexId="nIndex">
											
										   <logic:present name="comdtyVO" property="operationFlag">
											<bean:define id="opFlag" name="comdtyVO" property="operationFlag" />
												<logic:notEqual value="D" name="comdtyVO" property="operationFlag" >
													<logic:present name="comdtyVO" property="commodity">
														<bean:define id="commodity" name="comdtyVO" property="commodity"/>
														<tr>
														  <td class="iCargoTableDataTd" align="center">
														  <div align="center">
														  <ihtml:checkbox property="commodityCheck"
														  value="<%=(String)commodity%>"
														  componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_COMMODITY"
														  onclick="toggleTableHeaderCheckbox('commodityCheck',this.form.checkbox63)"/></td>
														  </div>
														  <td class="iCargoTableDataTd">
														  <bean:write name="commodity" />
														  <html:hidden value="<%=(String)commodity%>" property="commodity"/>
														  </td>
														</tr>
													</logic:present>
												</logic:notEqual>
											</logic:present>

											<logic:notPresent name="comdtyVO" property="operationFlag">
												<logic:present name="comdtyVO" property="commodity">
													<bean:define id="commodity" name="comdtyVO" property="commodity"/>
													<tr>
													  <td  width="18%" class="iCargoTableDataTd" align="center">
													  <div align="center">
													  <ihtml:checkbox property="commodityCheck" value="<%=(String)commodity%>"
													  componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_COMMODITY"
													  onclick="toggleTableHeaderCheckbox('commodityCheck',this.form.checkbox63)"/></td>
													  <td  width="82%" class="iCargoTableDataTd"><bean:write name="commodity" />
													  <html:hidden value="<%=(String)commodity%>" property="commodity"/>
													</div>
													  </td>
													</tr>
												</logic:present>
											</logic:notPresent>
										</logic:iterate>
									</logic:present>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="ic-section ic-border">						
						<div class="ic-row">
							<div class="ic-input ic-split-50">
								<ihtml:radio property="custGroupStatus" value="Restrict"
								   componentID="RAD_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_RESTRICTCUSTGRP" />
								<common:message  key="restrict" scope="request"/>
								<ihtml:radio property="custGroupStatus" value="Allow"
								   componentID="RAD_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_ALLOWCUSTGRP" />
								<common:message  key="allow" scope="request"/>
							</div>
							<div class="ic-input ic-split-50">
								<div class="ic-button-container">
									<ul class="ic-list-link">
										<li>
											<a href="#" class="iCargoLink"
								   onclick="addDataFromLOV1('products.defaults.beforeOpenSubLovCustomerNew.do','products.defaults.screenloadCustGroupLov.do','products.defaults.selectSubCustomerGroupNew.do','','div7')"
								 >Add</a>
										</li>
										<li>
											<a href="#" class="iCargoLink" onclick="deleteTableRow1('products.defaults.deleteSubCustomerGroupNew.do','custGroupCheck','div7')">Delete</a>
										</li>
									</ul>
								</div>
							</div>
						</div>
						<div class="ic-row">
							<business:sessionBean id="custGrpVOsFromSession" moduleName="product.defaults"
								screenID="products.defaults.maintainsubproducts" method="get" attribute="custGroupVOs" />
							<div id="div7" class="tableContainer" style="width:100%; height:125px;">
								<table class="fixed-header-table" width="100%">
									<thead>
									  <tr class="iCargoTableHeadingLeft">
										<td width="18%" align="center"><input type="checkbox"
											name="checkbox6" value="checkbox" onclick="updateHeaderCheckBox(this.form,this,this.form.custGroupCheck)"
											title='<common:message  bundle="MaintainSubProduct" key="customerGroup"
											scope="request"/>' />
										</td>
										<td width="82%">
										 <common:message  key="customerGroup" scope="request"/>
										</td>
									  </tr>
									</thead>
									<tbody>
									<logic:present name="custGrpVOsFromSession" >
									   <bean:define id="custGrpList" name="custGrpVOsFromSession"/>

											<logic:present name="custGrpList">

											  <logic:iterate id="custGroupVO" name="custGrpList" indexId="nIndex">
													

												<logic:present name="custGroupVO" property="operationFlag">
													<bean:define id="opFlag" name="custGroupVO" property="operationFlag" />
													<logic:notEqual value="D" name="custGroupVO" property="operationFlag" >
														<logic:present name="custGroupVO" property="customerGroup">
															<bean:define id="customerGroup" name="custGroupVO" property="customerGroup"/>
															<tr>
															  <td  width="18%" class="iCargoTableDataTd" align="center">
															  <div align="center">
																<ihtml:checkbox property="custGroupCheck"
																		value="<%=(String)customerGroup%>"
																		componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_CUSTGRP"
																		onclick="toggleTableHeaderCheckbox('custGroupCheck',this.form.checkbox6)"/>
															  </div></td>

															  <td  width="82%" class="iCargoTableDataTd"><%=(String)customerGroup%>
															   <html:hidden value="<%=(String)customerGroup%>" property="custGroup"/>

															  </td>
															</tr>
														</logic:present>
													</logic:notEqual>
												</logic:present>

												<logic:notPresent name="custGroupVO" property="operationFlag">
													<logic:present name="custGroupVO" property="customerGroup">
														<bean:define id="customerGroup" name="custGroupVO" property="customerGroup"/>
														<tr>
														  <td  width="18%" class="iCargoTableDataTd" align="center">
														  <div align="center">
														  <ihtml:checkbox property="custGroupCheck"
																	value="<%=(String)customerGroup%>"
																	componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_CUSTGRP"
																	onclick="toggleTableHeaderCheckbox('custGroupCheck',this.form.checkbox6)"/>
														  </div></td>

														  <td  width="82%" class="iCargoTableDataTd"><%=(String)customerGroup%>
														   <html:hidden value="<%=(String)customerGroup%>" property="custGroup"/>
														  </td>
														</tr>
													</logic:present>
												</logic:notPresent>
											</logic:iterate>
										</logic:present>
									</logic:present>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="ic-col-70">
					<div class="ic-section ic-border">
						<div class="ic-row iCargoTableTitle">
							Route Restriction
						</div>					
						<div class="ic-row">
							<business:sessionBean id="segmentVOsFromSession" moduleName="product.defaults"
								screenID="products.defaults.maintainsubproducts" method="get" attribute="segmentVOs" />
							<div class="ic-col-30">
								<div class="ic-row">
									<div class="ic-input ic-split-50">
										<ihtml:radio property="segmentStatus" value="Restrict"
											componentID="RAD_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_RESTRICTSEGMENT" />
										<common:message  key="restrict" scope="request"/>
										<ihtml:radio property="segmentStatus" value="Allow"
											componentID="RAD_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_ALLOWSEGMENT" />
										 <common:message  key="allow" scope="request"/>
									</div>
									<div class="ic-input ic-split-50">
										<div class="ic-button-container">
											<ul class="ic-list-link">
												<li>
													<a href="#" class="iCargoLink"  onclick="addRow1('products.defaults.addSubProductSegmentNew.do','segment','div4');">Add</a>
												</li>
												<li>
													<a href="#" class="iCargoLink" onclick="deleteTableRow1('products.defaults.deleteSubSegmentNew.do','segmentRowId','div4')">Delete</a>
												</li>
											</ul>
										</div>
									</div>
								</div>
								<div class="ic-row">
									<div id="div4" class="tableContainer"  style="width:100%; height:102px;">
										<table class="fixed-header-table" width="100%">
											<thead>
											  <tr class="iCargoTableHeadingLeft">
												<td width="18%" class="iCargoTableHeadingCenter" align="center">
												<input type="checkbox" name="checkbox7" value="checkbox"
												onclick="updateHeaderCheckBox(this.form,this,this.form.segmentRowId)"
												 title='<common:message  bundle="MaintainSubProduct" key="products.defaults.specifysegment"
																	scope="request"/>'/></td>
												<td width="82%">
												 <common:message  key="segment" scope="request"/>
												 </td>
											  </tr>
											</thead>
											<tbody>
												<logic:present name="segmentVOsFromSession" >

													<bean:define id="segmentList" name="segmentVOsFromSession"/>



													  <%	int rowId = 0; String hiddenSegment = "hiddenSegment"; 	%>

													<logic:iterate id="segmentVO" name="segmentList" indexId="nIndex">
														
														  <%
															String operationFlag ="";
															if(((RestrictionSegmentVO)segmentVO).getOperationFlag()!=null){
																	operationFlag = ((RestrictionSegmentVO)segmentVO).getOperationFlag();
															}
															if(!"D".equals(operationFlag)){
														  %>

														 <html:hidden property="segmentOperationFlag" value="<%=operationFlag%>" />
															<logic:present name="segmentVO" property="origin">
															<bean:define id="origin" name="segmentVO" property="origin"/>

															<logic:present name="segmentVO" property="destination">
															<bean:define id="destination" name="segmentVO" property="destination"/>


															<%
															String segmentValue=" ";
															if((!origin.toString().equals("")) && (!destination.toString().equals(""))){
																	segmentValue=origin.toString()+"-"+destination.toString();
															}

															%>
															<tr>


															  <input type="hidden" name="isSegmentRowModified" value="0" />
															  <td class="iCargoTableDataTd" align="center">
															  <div align="center">
															  <ihtml:checkbox property="segmentRowId"
																		value="<%=new Integer(rowId).toString()%>"
																		componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_SEGMENT" /></td>
															  </div>
															  <td class="iCargoTableDataTd">

															  <ibusiness:route maxlength="7"
															  componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_ROUTE"
															  id="<%=new Integer(rowId).toString()%>"
															  property="segment" indexId="nIndex"
															  value="<%=segmentValue.trim()%>" segmentLength="1"/>
															  </td>
															</tr>
														</logic:present>
														</logic:present>

														<%}else
														{%>

														<logic:present name="segmentVO" property="origin">
															<bean:define id="origin" name="segmentVO" property="origin"/>
															<logic:present name="segmentVO" property="destination">
															<bean:define id="destination" name="segmentVO" property="destination"/>
															 <html:hidden property="segmentOperationFlag" value="<%=operationFlag%>" />
																<html:hidden property="segmentRowId" value="<%=new Integer(rowId).toString()%>" />
																<html:hidden property="isSegmentRowModified" value="0" />
																<%String odPair = origin.toString()+"-"+destination.toString();%>
																<html:hidden value="<%=odPair%>" property="segment"/>
															</logic:present>
															</logic:present>

														 <% }
														 rowId++;
														 %>
													</logic:iterate>
												</logic:present>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<div class="ic-col-35">
								<%

								Collection<RestrictionStationVO> originList = new ArrayList<RestrictionStationVO>();
								Collection<RestrictionStationVO> destList = new ArrayList<RestrictionStationVO>();
								%>

							   <business:sessionBean id="stationVOsFromSession" moduleName="product.defaults"
							   screenID="products.defaults.maintainsubproducts" method="get" attribute="stationVOs" />
							   <div class="ic-row">
									<div class="ic-input ic-split-50">
										<ihtml:radio property="originStatus" value="Restrict"
											componentID="RAD_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_RESTRICTORIGIN" />
										<common:message  key="restrict" scope="request"/>
										<ihtml:radio property="originStatus" value="Allow"
											componentID="RAD_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_ALLOWORIGIN"/>
										<common:message  key="allow" scope="request"/>
									</div>
									<div class="ic-input ic-split-50">
										<div class="ic-button-container">
											<ul class="ic-list-link">
												<li>
													<a href="#" class="iCargoLink"
					  onclick="addDataFromLOV1('products.defaults.beforeOpenSubLovOriginNew.do','products.defaults.screenloadStationLov.do','products.defaults.selectSubOriginNew.do','','div5')">Add</a>
												</li>
												<li>
													<a href="#" class="iCargoLink" onclick="deleteTableRow1('products.defaults.deleteSubOriginNew.do','originCheck','div5')">Delete</a>
												</li>
											</ul>
										</div>
									</div>
								</div>
								<div class="ic-row">
									<div id="div5" class="tableContainer"  style="width:100%; height:102px;">
										  <table class="fixed-header-table" width="100%">
											<thead>
											  <tr class="iCargoTableHeadingLeft">
												<td width="18%" class="iCargoTableHeadingCenter">
												<input type="checkbox" name="checkbox72"
												value="checkbox"
												onclick="updateHeaderCheckBox(this.form,this,this.form.originCheck)"
												title='<common:message  bundle="MaintainSubProduct" key="products.defaults.specifyorigin"
													scope="request"/>'/></td>
												<td width="82%">
												 <common:message  key="origin" scope="request"/>
												</td>
											  </tr>
											</thead>
											<tbody>
											<logic:present name="stationVOsFromSession">
												<logic:iterate name="stationVOsFromSession" id="stationVO" indexId="rowCount5" >
													<bean:define name="stationVO" id="originFlag" property="isOrigin" />
													<logic:equal name="originFlag" value="true" >
													<bean:define name="stationVO" id="originStation" property="station" />
														<logic:present name="stationVO" property="operationFlag">
															<logic:notEqual name="stationVO" property="operationFlag" value="D">
															<tr>
																  <td class="iCargoTableTd" align="center">
																  <div align="center">
																  <html:checkbox property="originCheck" value="<%=(String)originStation%>"
																  onclick="toggleTableHeaderCheckbox('originCheck',this.form.checkbox72)"/></td>
																  </div>
																  <td class="iCargoTableTd"><%=originStation%>
																  <html:hidden property="origin" value="<%=(String)originStation%>" />
																  </td>
															</tr>
															</logic:notEqual>
														</logic:present>
														<logic:notPresent name="stationVO" property="operationFlag">
															<tr>
																  <td class="iCargoTableTd" align="center">
																  <div align="center">
																  <html:checkbox property="originCheck" value="<%=(String)originStation%>"
																  onclick="toggleTableHeaderCheckbox('originCheck',this.form.checkbox72)"/></td>
																  </div>
																  <td class="iCargoTableTd"><%=originStation%>
																  <html:hidden property="origin" value="<%=(String)originStation%>" />
																  </td>
															</tr>
														</logic:notPresent>
												</logic:equal>
												</logic:iterate>
											</logic:present>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<div class="ic-col-35">
								<div class="ic-row">
									<div class="ic-input ic-split-50">
										<ihtml:radio property="destinationStatus" value="Restrict"
											componentID="RAD_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_RESTRICTDESTN" />
										<common:message  key="restrict" scope="request"/>
										<ihtml:radio property="destinationStatus" value="Allow"
											componentID="RAD_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_ALLOWDESTN"/>
										<common:message  key="allow" scope="request"/>
									</div>
									<div class="ic-input ic-split-50">
										<div class="ic-button-container">
											<ul class="ic-list-link">
												<li>
													<a href="#" class="iCargoLink"
					  onclick="addDataFromLOV1('products.defaults.beforeOpenSubLovDestinationNew.do','products.defaults.screenloadStationLov.do','products.defaults.selectSubDestinationNew.do','','div6')" >Add</a>
												</li>
												<li>
													<a href="#" class="iCargoLink" onclick="deleteTableRow1('products.defaults.deleteSubDestinationNew.do','destinationCheck','div6')">Delete</a>
												</li>
											</ul>
										</div>
									</div>
								</div>
								<div class="ic-row">
									<div id="div6" class="tableContainer" style="width:100%; height:102px;">
										  <table class="fixed-header-table" width="100%">
											<thead>
											  <tr class="iCargoTableHeadingLeft">
												<td width="18%" class="iCargoTableHeadingCenter"
												align="center"><input type="checkbox" name="checkbox73" value="checkbox"
												onclick="updateHeaderCheckBox(this.form,this,this.form.destinationCheck)"
												title='<common:message  bundle="MaintainSubProduct" key="products.defaults.specifydestn"
												scope="request"/>' /></td>
												<td width="82%">
												 <common:message  key="destination" scope="request"/>
												 </td>
											  </tr>
											</thead>
											<tbody>
												<logic:present name="stationVOsFromSession">
													<logic:iterate name="stationVOsFromSession" id="stationVO" indexId="rowCount6" >
														<bean:define name="stationVO" id="originFlag" property="isOrigin" />
														<logic:equal name="originFlag" value="false" >
														<bean:define name="stationVO" id="destn" property="station" />
															<logic:present name="stationVO" property="operationFlag">
																<logic:notEqual name="stationVO" property="operationFlag" value="D">
																<tr>
																	  <td  width="18%" class="iCargoTableTd" align="center">
																	  <div align="center">
																	  <html:checkbox property="destinationCheck" value="<%=(String)destn%>"
																	  onclick="toggleTableHeaderCheckbox('destinationCheck',this.form.checkbox73)"/></td>
																	  </div>
																	  <td  width="82%" class="iCargoTableTd"><%=destn%>
																	  <html:hidden property="destination" value="<%=(String)destn%>" />
																	  </td>
																</tr>
																</logic:notEqual>
															</logic:present>
															<logic:notPresent name="stationVO" property="operationFlag">
																<tr>
																	<td class="iCargoTableTd ic-center">
																	 <html:checkbox property="destinationCheck" value="<%=(String)destn%>"
																	 onclick="toggleTableHeaderCheckbox('destinationCheck',this.form.checkbox73)"/></td>
																	<td class="iCargoTableTd"><%=destn%>
																		<html:hidden property="destination" value="<%=(String)destn%>" />
																	</td>
																</tr>
															</logic:notPresent>
														</logic:equal>
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
						<div class="ic-section ic-border">
							<div class="ic-col-20">
								<div id="div8" class="tableContainer"  style="width:100%; height:175px;">
									  <table class="fixed-header-table" width="100%">
										<thead>
										  <tr class="iCargoTableHeadingLeft">
											<td width="18%" class="iCargoTableHeadingCenter" align="center">
											<input type="checkbox" name="checkbox62" value="checkbox" onclick="updateHeaderCheckBox(this.form,this,this.form.restrictedTermsCheck)"
												title='<common:message  bundle="MaintainSubProduct" key="restrictedPaymentTerms"
												scope="request"/>'/></td>
											<td width="82%">
											 <common:message  key="restrictedPaymentTerms" scope="request"/>
											</td>
										  </tr>
										</thead>
										<tbody>

											<business:sessionBean id="paymentTermsListFromSession" moduleName="product.defaults"
											screenID="products.defaults.maintainsubproducts" method="get" attribute="restrictedPaymentTerms" />

											<logic:present name="paymentTermsListFromSession">
											   <bean:define id="paymentTermsList" name="paymentTermsListFromSession"/>

											  <logic:iterate id="paymentTermsVO" name="paymentTermsList" indexId="nIndex">
												<logic:present name="paymentTermsVO" property="paymentTerm">

													<bean:define id="term" name="paymentTermsVO" property="paymentTerm"/>
													<tr>
													  <td  width="18%" class="iCargoTableDataTd">
													  <div align="center">
													  <% MaintainSubProductForm mform = (MaintainSubProductForm)request.getAttribute("MaintainSubProductForm");
														String [] payment = mform.getRestrictedTermsCheck();
														String paymentTerm = " ";
														if(payment!=null){
														for(int i=0;i<payment.length; i++){
														if(payment[i].equals((String)term)){

																 paymentTerm =(String)term;
																 break;
														}
														}

														if(((String)term).equals(paymentTerm)){
														  %>
														  <input type="checkbox" name="restrictedTermsCheck"
														  value="<%=(String)term%>" checked="checked"
														  title='<common:message  bundle="MaintainSubProduct" key="products.defaults.restrictedpmenttrmchk"
																scope="request"/>'/>
														  <%}else{%>
														  <input type="checkbox" name="restrictedTermsCheck"
														  value="<%=(String)term%>"
														  title='<common:message  bundle="MaintainSubProduct" key="products.defaults.restrictedpmenttrmchk"
																scope="request"/>'/>

														 <%}
														 }
														 else{
														  %><input type="checkbox" name="restrictedTermsCheck"
														  value="<%=(String)term%>"
														  title='<common:message  bundle="MaintainSubProduct" key="products.defaults.restrictedpmenttrmchk"
																scope="request"/>'/>
														  <%}
														  %>
														  </div>
													  </td><td  width="82%" class="iCargoTableDataTd"><bean:write name="term" />
													  <html:hidden value="<%=(String)term%>" property="paymentRestriction"/>
													  </td>
													</tr>
												</logic:present>
												</logic:iterate>

											</logic:present>

										</tbody>
									</table>
								</div>
							</div>
							<div class="ic-col-30">
								<div class="ic-row">
									<h3>Capacity Restriction </h3>
								</div>
								<div class="ic-row ic-section ic-border" style="height:110px;width:274px;">
									<div class="ic-row">
										<div class="ic-col-23">
											&nbsp;
										</div>
										<div class="ic-col-25">
											<common:message  key="min" scope="request"/>
										</div>
										<div class="ic-col-25">
											<common:message  key="max" scope="request"/>
										</div>
										<div class="ic-col-26">
											&nbsp;
										</div>
									</div>
									<div class="ic-row">
										<div class="ic-row">
											<div class="ic-col-23">
												<label>
													<common:message  key="weight" scope="request"/>
												</label>
											</div>
											<div class="ic-col-25">
												<ihtml:text  property="minWeight"
													componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_MINWEIGHT"
													value="<%=form.getMinWeight()%>"/>
											</div>
											<div class="ic-col-25">
												<ihtml:text property="maxWeight"
													componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_MAXWEIGHT"
													value="<%=form.getMaxWeight()%>"/>
											</div>
											<div class="ic-col-26">
												<business:sessionBean id="weightUnit" moduleName="product.defaults"
													screenID="products.defaults.maintainsubproducts" method="get" attribute="weight" />
												<logic:present name="weightUnit" >

												  <bean:define id="oneTimeVOs" name="weightUnit" toScope="page"/>
													<ihtml:select property="weightUnit"
														componentID="CMB_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_WEIGHTUNIT">
													 <logic:iterate id="oneTimeVO" name="oneTimeVOs">

															 <bean:define id="defaultValue" name="oneTimeVO" property="fieldValue" />
															 <bean:define id="displayValue" name="oneTimeVO" property="fieldDescription" />
															  <html:option value="<%=(String)defaultValue%>"><%=(String)displayValue%></html:option>
													 </logic:iterate>

													</ihtml:select>
												</logic:present>
											</div>
										</div>
									</div>
									<div class="ic-row">
										<div class="ic-col-23">
											<label>
												<common:message  key="volume" scope="request"/>
											</label>
										</div>
										<div class="ic-col-25">
											<ihtml:text property="minVolume"
											componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_MINVOLUME"
											value="<%=form.getMinVolume()%>"/>
										</div>
										<div class="ic-col-25">
											<ihtml:text property="maxVolume"
											componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_MAXVOLUME"
											value="<%=form.getMaxVolume()%>"/>
										</div>
										<div class="ic-col-26">
											<business:sessionBean id="volumeUnit" moduleName="product.defaults" screenID="products.defaults.maintainsubproducts"
												method="get" attribute="volume" />
											<logic:present name="volumeUnit" >

											  <bean:define id="oneTimeVOs" name="volumeUnit"  toScope="page"/>
												<ihtml:select property="volumeUnit"
												componentID="CMB_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_VOLUMEUNIT" >
												<logic:iterate id="oneTimeVO" name="oneTimeVOs">
													<bean:define id="defaultValue" name="oneTimeVO" property="fieldValue" />
													<bean:define id="displayValue" name="oneTimeVO" property="fieldDescription" />
												<html:option value="<%=(String)defaultValue%>"><%=(String)displayValue%></html:option>
												 </logic:iterate>

												</ihtml:select>
											</logic:present>
										</div>
									</div>									
								</div>								
							</div>
							<div class="ic-col-50">
								<div class="ic-row">
									<h3><common:message  key="additionalRestriction" scope="request"/></h3>
								</div>
								<div class="ic-row">
									<ihtml:textarea
										property="addRestriction" cols="33"
										rows="6"
										componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_ADDRESTRICTION">
									</ihtml:textarea>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		    </div>
		    <!-- DIV Tabpanes End-->
			</div>
		</div>
	</div>
	<div class="ic-foot-container">
		<div class="ic-row">
			<div class="ic-button-container">
				<ihtml:nbutton property="btnSave" componentID="BTN_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_SAVE">
					<common:message  key="save" scope="request"/>
				</ihtml:nbutton>
				<ihtml:nbutton property="btnClose" componentID="BTN_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_CLOSE">
					<common:message  key="close" scope="request"/>
				</ihtml:nbutton>
			</div>
			<html:hidden property = "productCode" />
			<html:hidden property = "subProductCode" />
		    <html:hidden property = "versionNumber" />
		    <html:hidden property = "hiddenStatus" />
		    <html:hidden property = "hiddenPriority" />
		</div>
	</div>	
</div>
</ihtml:form>
</div>
<script language="javascript">
onLoadFunctions()
</script>


	</body>

</html>


