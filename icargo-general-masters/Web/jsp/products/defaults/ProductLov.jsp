<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  INV - Inventory Control
* File Name				:  ProductLov.jsp
* Date					:  27-July-2005
* Author(s)				:  Akhila S

*************************************************************************/
 --%>

<%@ page language="java" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.framework.util.time.LocalDate" %>
<%@ page import = "com.ibsplc.icargo.business.products.defaults.vo.ProductLovVO" %>


<html>
<head>
			
	
<bean:define id="form"
	name="productLovForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ProductLovForm"
	toScope="page" />
<title><common:message bundle="<%=form.getBundle() %>" key="products.defaults.title" scope="request"/></title>
<meta name="decorator" content="popuppanelrestyledui">
<common:include type="script" src="/js/products/defaults/ProductLov_Script.jsp" />
</head>

<body id="bodyStyle">
	
<div class="iCargoPopUpContent" style="position:static; width:100%; height:100%; z-index:1;">
	<ihtml:form action="products.defaults.screenloadProductLov.do" styleClass="ic-main-form">
		<bean:define id="form" name="productLovForm" 
			type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ProductLovForm" toScope="page" />
		<html:hidden property="lastPageNumber"/>
		<html:hidden property="displayPage"/>
		<html:hidden property="formNumber"/>
		<html:hidden property="productObject" />
		<html:hidden property="productCodeField" />
		<html:hidden property="sourceScreen" />
		<html:hidden property="activeProducts" />
		<html:hidden property="bookingDate" />
		<ihtml:hidden property="priorityObject" />
		<ihtml:hidden property="transportModeObject" />
		<ihtml:hidden property="rowIndex" />
		<ihtml:hidden property="invokeFunction"/> 
		<ihtml:hidden property="multiselect"/>
		<ihtml:hidden property="lovTxtFieldName"/>
		<ihtml:hidden property="lovDescriptionTxtFieldName"/>
		<ihtml:hidden property="selectedValues"  />
		<ihtml:hidden property="lastPageNum" />
		<ihtml:hidden property="customerCode"/>
		<bean:define name="form" property="productObject" id="productObject" />
		<bean:define name="form" property="priorityObject" id="priorityObject" />
		<bean:define name="form" property="transportModeObject" id="transportModeObject" />
		<bean:define name="form" property="multiselect" id="strMultiselect" />
		<bean:define name="form" property="lovTxtFieldName" id="strLovTxtFieldName" />
		<bean:define name="form" property="lovDescriptionTxtFieldName" id="strLovDescriptionTxtFieldName" />
		<bean:define name="form" property="rowIndex" id="arrayIndex" />
		<bean:define name="form" property="formNumber" id="formNumber" />
		<bean:define name="form" property="lastPageNum" id="lastPageNum"/>
		<logic:present name="productLovForm" property="pageProductLov">
			<bean:define id="pageProductLov" name="productLovForm"  property="pageProductLov" toScope="page" />
		</logic:present> 
 
		<div class="ic-content-main">
			<div class="ic-head-container">
				<div class="ic-filter-panel">
					<div class="ic-row">
						<div class="ic-col-100">	
							<div class="ic-input-container">
								<div class="ic-row">
									<div class="ic-input ic-split-33">
										<label class="ic-label-33">
											<common:message bundle="<%=form.getBundle() %>" key="products.defaults.ProductName" scope="request"/>
										</label>
										<ihtml:text property="productName" componentID="TXT_PRODUCTS_DEFAULTS_PRODUCTLOV_PRDNAME" maxlength="30"/>
									</div>
									<div class="ic-input ic-split-33">
										<label class="ic-label-20">
											<common:message bundle="<%=form.getBundle() %>" key="products.defaults.StartDate" scope="request"/>
										</label>
										<ibusiness:calendar property="startDate" id="TXT_PRODUCTS_DEFAULTS_PRODUCTLOV_STARTDATE" 
											componentID="TXT_PRODUCTS_DEFAULTS_PRODUCTLOV_STARTDATE" 
											type="image" maxlength="11" value="<%=form.getStartDate()%>"/>
									</div>
									<div class="ic-input ic-split-33">
										<label class="ic-label-20">
											 <common:message bundle="<%=form.getBundle() %>" key="products.defaults.EndDate" scope="request"/>
										</label>
										<ibusiness:calendar property="endDate" id="TXT_PRODUCTS_DEFAULTS_PRODUCTLOV_ENDDATE" 
											componentID="TXT_PRODUCTS_DEFAULTS_PRODUCTLOV_ENDDATE" type="image" value="<%=form.getEndDate()%>"/>
									</div>
								</div>
								<div class="ic-row">
								<div class="ic-input ic-split-30">
										<label class="ic-label-30">
											<common:message bundle="<%=form.getBundle() %>" key="products.defaults.sccCode" scope="request"/>
										</label>
										<ihtml:text property="sccCode" componentID="TXT_PRODUCTS_DEFAULTS_PRODUCTLOV_SCCCODE" maxlength="19"/><!--Added by A-8146 for ICRD-254587-->
									</div>
									<div class="ic-input ic-split-70">
										<div class="ic-button-container">
											<ihtml:nbutton  property="btnList" accesskey="L" componentID="BTN_PRODUCTS_DEFAULTS_PRODUCTLOV_LIST">
												<common:message bundle="<%=form.getBundle() %>" key="products.defaults.list" scope="request"/>
											</ihtml:nbutton>
											<ihtml:nbutton property="btnClear" accesskey="C" componentID="BTN_PRODUCTS_DEFAULTS_PRODUCTLOV_CLEAR">
												<common:message bundle="<%=form.getBundle() %>" key="products.defaults.Clear" scope="request"/>
											</ihtml:nbutton>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-main-container">
				<div class="ic-row" id="listTable">
					<div class="ic-col-100">
						<div class="ic-row">
							<div class="ic-col-50" style="margin-top:5px;">
								<logic:present name="pageProductLov">
									<common:paginationTag 
										pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')" 
										name="pageProductLov" 
										display="label" 
										labelStyleClass="iCargoResultsLabel" 
										lastPageNum="<%=form.getLastPageNumber() %>" />
								</logic:present>							    
							</div>
							<div class="ic-col-50">
								<div class="ic-button-container paddR5">
									<logic:present name="pageProductLov">
										<common:paginationTag 
										pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')" 
										linkStyleClass="iCargoLink" 
										disabledLinkStyleClass="iCargoLink" 
										name="pageProductLov" 
										display="pages" 
										lastPageNum="<%=form.getLastPageNumber()%>" />
									</logic:present>									
								</div>
							</div>
						</div>
						<div class="ic-row">
							<div class="tableContainer" style="height:200px">
								<table id="productLovTable" class="fixed-header-table">
									<thead>
										<tr class="iCargoTableHeadingLeft">
											<td width="6%">&nbsp; </td>
											<td width="26%" >
												<common:message bundle="<%=form.getBundle()%>" key="products.defaults.ProductName" 
													scope="request"/>						
											</td>
											<td width="33%">
												<common:message bundle="<%=form.getBundle() %>" key="products.defaults.StartDate" 
													scope="request"/>
											</td>
											<td width="35%">
												<common:message bundle="<%=form.getBundle() %>" key="products.defaults.EndDate" scope="request"/>
											</td>
										</tr>
									</thead>
									<tbody>
										<%
											String priorVal="";
											String transportMode="";%>
											<logic:present name="pageProductLov">
												<logic:iterate id="lovVo" name="pageProductLov" indexId="nIndex">
													<bean:define id="code" name="lovVo" property="productCode" />
													<bean:define id="startDate" name="lovVo" property="startDate" />
													<bean:define id="endDate" name="lovVo" property="endDate" />
													<bean:define id="name" name="lovVo" property="productName" />
													<logic:present name="lovVo" property="productPriority">
														<bean:define id="priorityValues" name="lovVo" property="productPriority" />
														<logic:iterate id="priorityVal" name="priorityValues">
															<%priorVal = priorVal+(String)priorityVal+"-";%>
														</logic:iterate>
													</logic:present>
													<logic:present  name="lovVo" property="productTransportMode">
														<bean:define id="transportModeValues" name="lovVo" property="productTransportMode" />
														<bean:define id="transModeValues" name="lovVo" property="productTransportMode" />
														<logic:iterate id="tranVal" name="transModeValues">
															<%transportMode = transportMode+(String)tranVal+"-";%>
														</logic:iterate>
													</logic:present>
													<%
														String fromDateString = TimeConvertor.toStringFormat(((LocalDate)startDate).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
														String endDateString = TimeConvertor.toStringFormat(((LocalDate)endDate).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
													%>
													
														<logic:notEqual name="productLovForm" property="multiselect" value="Y">
															<tr
ondblclick="setProductLovOnDblClick('<%=productObject%>','<%=((ProductLovVO)lovVo).getProductName()%>','<%=formNumber%>')">
														</logic:notEqual>
														<logic:equal name="productLovForm" property="multiselect" value="Y">
														<tr>
														</logic:equal> 
														<td class="iCargoTableDataTd">                 
															<logic:notEqual name="productLovForm" property="multiselect" value="Y">				  
																<input type="checkbox" name="productChecked" 
																	value="<%=((ProductLovVO)lovVo).getProductName()%>" 
																	onclick="singleSelect('<%=((ProductLovVO)lovVo).getProductName()%>');"/>
															</logic:notEqual>
															<logic:equal name="productLovForm" property="multiselect" value="Y">
																<input type="checkbox" name="productChecked" 
																	value="<%=((ProductLovVO)lovVo).getProductName()%>"  />
															</logic:equal>                  		
														</td>
														<td class="iCargoTableDataTd">
															<bean:write name="lovVo" property="productName"/>
														</td>
														<td class="iCargoTableDataTd">
															<%=fromDateString%>
														</td >
														<td class="iCargoTableDataTd">
															<%=endDateString%>
														</td>
													</tr>
												</logic:iterate>
											</logic:present>
											<logic:notPresent name="pageProductLov">
												&nbsp;
											</logic:notPresent>
										</tbody>
									</table>
								</div>
						</div>
						<div class="ic-row">
							<logic:equal name="form" property="multiselect" value="Y">
								<div id="selectiondiv" class="multiselectPanelStyle" name="Products">
								</div>
							</logic:equal>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-foot-container">
				<div class="ic-row paddR5">
					<div class="ic-button-container">
						<input type=button name="btok" class="iCargoButtonSmall" 
							onclick="setValueForDifferentModes('<%=strMultiselect%>','<%=formNumber%>','<%=strLovTxtFieldName%>','<%=strLovDescriptionTxtFieldName%>','<%=arrayIndex%>')"
							value='<common:message bundle="<%=form.getBundle() %>" key="products.defaults.ok" scope="request"/>' title="Ok"/>
						<ihtml:nbutton property="btnclose" accesskey="O" componentID="BTN_PRODUCTS_DEFAULTS_PRODUCTLOV_CLOSE">
							<common:message bundle="<%=form.getBundle() %>" key="products.defaults.close" scope="request"/>
						</ihtml:nbutton>
					</div>
				</div>
			</div>
		</div>
	</ihtml:form>
</div>		
			
	</body>

</html>
