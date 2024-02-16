 <%--
* Project	 		: iCargo
* Module Code & Name: stockcontrol
* File Name			: icargo_list_product.jsp
* Date				: 15-07-2005
* Author(s)			: Akhila.S
 --%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>

	
<bean:define id="form"
name="ListSubProductForm"
type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ListSubProductForm"
toScope="page" />
<title><common:message bundle="<%=form.getBundle()%>" key="screenTitle" scope="request"/></title>

<common:include type="script" src="/js/products/defaults/ListSubProduct_Script.jsp"/>
<meta name="decorator" content="mainpanelrestyledui">

</head>
<body id="bodyStyle">
	

<business:sessionBean id="statusVO" moduleName="product.defaults" screenID="products.defaults.listsubproducts" method="get" attribute="status" />
<business:sessionBean id="priorityVO" moduleName="product.defaults" screenID="products.defaults.listsubproducts" method="get" attribute="priority" />
<business:sessionBean id="transportModeVO" moduleName="product.defaults" screenID="products.defaults.listsubproducts" method="get" attribute="transportMode" />
<business:sessionBean id="pageSubProductVOs" moduleName="product.defaults" screenID="products.defaults.listsubproducts" method="get" attribute="pageSubProductVO" />
<bean:define id="testStatus" name="statusVO" type="java.util.Collection" />
<bean:define id="testPriority" name="priorityVO" type="java.util.Collection" />
<bean:define id="testtransportMode" name="transportModeVO" type="java.util.Collection" />

<logic:present name="statusVO" >
<bean:define id="statusOnetime" name="statusVO"  />
</logic:present>
<logic:present name="pageSubProductVOs" >
<bean:define id="subProductVO" name="pageSubProductVOs"  />
</logic:present>
<logic:present name="priorityVO">
<bean:define id="priorityOnetime" name="priorityVO"/>
</logic:present>
<logic:present name="transportModeVO">
<bean:define id="transportModeOnetime" name="transportModeVO" />
</logic:present>

<bean:define id="form"
	name="ListSubProductForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ListSubProductForm"
	toScope="page" />

<div class="iCargoContent ic-masterbg" style="overflow:auto;height:500px;">
	<ihtml:form action="products.defaults.screenloadlistsubproducts.do">
		<html:hidden property="displayPage" />
		<html:hidden property="lastPageNum" />
		<html:hidden property="checkList" />
		<input type="hidden" name="currentDialogId" />
		<input type="hidden" name="currentDialogOption" />
		<html:hidden property="buttonStatusFlag" />
		
		
		<div class="ic-content-main">
			<span class="ic-page-title ic-display-none">
				<common:message  key="title" scope="request"/>
			</span>
			<div class="ic-head-container">
				<!-- <div class="ic-row">
					<h3><common:message  key="searchCriteria" scope="request"/></h3>
				</div> -->
				<div class="ic-filter-panel">
                    <div class="ic-row"><h3><common:message  key="searchCriteria" scope="request"/></h3></div>
					<div class="ic-row">
						<div class="ic-col-100">	
							<div class="ic-input-container">
								<div class="ic-col-25 ic-label-30">
									<div class="ic-input ic-split-100">
										<label>
											 <common:message  key="productName" scope="request"/>
										</label>
										<ihtml:text property="productName" readonly="true" maxlength="30"
											componentID="TXT_PRODUCTS_DEFAULTS_LISTSUBPRODUCTS_PRDNAME" tabindex="1"/>
									</div>
									<div class="ic-input ic-split-100">
										<label>
											<common:message  key="transportMode" scope="request"/>
										</label>
										<ihtml:select property="transportMode" 
											tabindex="5" componentID="CMB_PRODUCTS_DEFAULTS_LISTSUBPRODUCTS_TRAMOD">
											<logic:present name="transportModeOnetime" >
												<html:option value="ALL">ALL</html:option>
												<logic:iterate id="oneTimeVO" name="transportModeOnetime">
													<bean:define id="defaultValue" name="oneTimeVO" property="fieldValue" />
													<html:option value="<%=(String)defaultValue%>"><%=(String)defaultValue%></html:option>
												</logic:iterate>
											</logic:present>
										</ihtml:select>
									</div>
								</div>
								<div class="ic-col-25 ic-label-25">
									<div class="ic-input ic-split-100">
										<label>
											 <common:message  key="fromDate" scope="request"/>
										</label>
										<ihtml:text property="startDate"  maxlength="11"
											componentID="TXT_PRODUCTS_DEFAULTS_LISTSUBPRODUCTS_FROMDATE" readonly="true" tabindex="2"/>
									</div>
									<div class="ic-input ic-split-100">
										<label>
											<common:message  key="priority" scope="request"/> 
										</label>
										<ihtml:select property="priority" 
											tabindex="6" componentID="CMB_PRODUCTS_DEFAULTS_LISTSUBPRODUCTS_PRIORITY">
											<html:option value="ALL">ALL</html:option>
											<logic:present name="priorityOnetime" >
												<logic:iterate id="oneTimeVO" name="priorityOnetime">
													<bean:define id="defaultValue" name="oneTimeVO" property="fieldValue" />
													<bean:define id="displayValue" name="oneTimeVO" property="fieldDescription" />
													<html:option value="<%=(String)defaultValue%>"><%=(String)displayValue%></html:option>
												</logic:iterate>
											</logic:present>
										</ihtml:select>
									</div>
								</div>
								<div class="ic-col-25 ic-label-20">
									<div class="ic-input ic-split-100">
										<label>
											 <common:message  key="toDate" scope="request"/>
										</label>
										<ihtml:text property="endDate" maxlength="11" componentID="TXT_PRODUCTS_DEFAULTS_LISTSUBPRODUCTS_TODATE"
											readonly="true" tabindex="3"/>
									</div>
									<div class="ic-input ic-split-100">
										<label>
											 <common:message  key="scc" scope="request"/> 
										</label>
										<ihtml:text property="productScc" maxlength="3" tabindex="7"
											componentID="TXT_PRODUCTS_DEFAULTS_LISTSUBPRODUCTS_SCC" />
										<input type="hidden" name="productSccDesc" value=""/>
										<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"
											onclick="displayLOV('showScc.do','N','Y','showScc.do',document.forms[1].productScc.value,'Select SCC','1','productScc','productSccDesc')" tabindex="8" /></div>
									</div>
								</div>
								<div class="ic-col-25 ic-label-15">
									<div class="ic-input ic-split-100">										
										<label>
											 <common:message  key="status" scope="request"/>
										</label>
										<ihtml:select property="status" tabindex="4" componentID="CMB_PRODUCTS_DEFAULTS_LISTSUBPRODUCTS_STATUS">
											<logic:present name="statusOnetime" >
												<html:option value="ALL">ALL</html:option>
												<logic:iterate id="oneTimeVO" name="statusOnetime">
													<bean:define id="defaultValue" name="oneTimeVO" property="fieldValue" />
													<bean:define id="diaplayValue" name="oneTimeVO" property="fieldDescription" />
													<html:option value="<%=(String)defaultValue%>"><%=(String)diaplayValue%></html:option>
												</logic:iterate>
											</logic:present>
										</ihtml:select>
									</div>
									<div class="ic-input ic-split-100">
										<div class="ic-button-container">
											<ihtml:nbutton property="btnList" 
												tabindex="9" componentID="BTN_PRODUCTS_DEFAULTS_LISTSUBPRODUCTS_LIST">
												<common:message  key="list" scope="request"/>
											</ihtml:nbutton>
											<ihtml:nbutton property="btnClear" tabindex="10"
												componentID="BTN_PRODUCTS_DEFAULTS_LISTSUBPRODUCTS_CLEAR">
												<common:message  key="clear" scope="request"/>
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
				<div class="ic-row">
					<h3>Sub Product Details</h3>
				</div>
				<div class="ic-section">
					<div class="ic-row">
						<div class="ic-col-75">
							<logic:present name="subProductVO">
								<common:paginationTag pageURL="javascript:submitListSubProducts('lastPageNum','displayPage')"
									name="subProductVO"
									display="label"
									labelStyleClass="iCargoResultsLabel"
									lastPageNum="<%=form.getLastPageNum() %>" />
							</logic:present>
						</div>
						<div class="ic-col-25 paddR10">
							<div class="ic-button-container">
								<logic:present name="subProductVO">
									<common:paginationTag
										pageURL="javascript:submitListSubProducts('lastPageNum','displayPage')"
										name="subProductVO"
										display="pages"
										lastPageNum="<%=form.getLastPageNum()%>"
										exportToExcel="true"
										exportTableId="subProductTable"
										exportAction="products.defaults.listsubproductspagination.do"/>
								</logic:present>			
							</div>
						</div>
					</div>
					<div class="ic-row">
						<div id="div2" class="tableContainer" style="height:345px">
							<table id="subProductTable" class="fixed-header-table">
								<thead>
									<tr class="iCargoTableHeadingLeft">
										<td width="3%" class="iCargoTableHeadingCenter"><span></span></td>
										<td width="22%">
											<common:message key="transportMode" scope="request"/><span></span>
										</td>
										<td width="25%">
											<common:message key="priority" scope="request"/><span></span>
										</td>
										<td width="25%">
											<common:message key="scc" scope="request"/><span></span>
										</td>
										<td width="25%">
											<common:message key="status" scope="request"/><span></span>
										</td>
									</tr>
								</thead>
								<tbody>
									<% 	int k=0;
										int rowCount=0;
										String subProductStatus = "";
										String hiddenSubProductCode = "";
										String hiddenProductCode = "";
										String hiddenVersionNumber = "";%>
										<logic:present name="subProductVO" >
											<bean:define id="list" name="subProductVO"  toScope="page"/> 
											<logic:iterate id="subProductList" name="list" indexId="nIndex">
												<bean:define id="vo" 
													name="subProductList" type="com.ibsplc.icargo.business.products.defaults.vo.SubProductVO"/>
												<%	subProductStatus = "subProductStatus";
													hiddenSubProductCode = "hiddenSubProductCode";
													hiddenProductCode = "hiddenProductCode";
													hiddenVersionNumber = "hiddenVersionNumber";%>
											  <input type="hidden" name ="<%=hiddenProductCode+rowCount%>" value="<%=vo.getProductCode()%>">
											  <input type="hidden" name ="<%=subProductStatus+rowCount%>" value="<%=vo.getStatus()%>">
											  <input type="hidden" name ="<%=hiddenSubProductCode+rowCount%>" value="<%=vo.getSubProductCode()%>">
											  <input type="hidden" name ="<%=hiddenVersionNumber+rowCount%>" value="<%=vo.getVersionNumber()%>">
												<tr>
													<td class="iCargoTableDataTd ic-center">
														<html:checkbox property="checkBox" value="<%=vo.getSubProductCode()%>"/>
													</td>
													<td class="iCargoTableDataTd">
														<bean:write name="vo" property="productTransportMode"/>
													</td>
													<td class="iCargoTableDataTd">
														<bean:write name="vo" property="productPriority"/>
													</td>
													<td class="iCargoTableDataTd">
														<bean:write name="vo" property="productScc"/>
													</td>
													<td class="iCargoTableDataTd">
														<bean:write name="vo" property="status"/>
													</td>
													<% rowCount++;%>
												</tr>
											</logic:iterate>
										</logic:present>
									<input type="hidden" name="rowCount" value=<%=rowCount%> />
								</tbody>
                            </table>
                        </div>
					</div>
				</div>
			</div>
			<div class="ic-foot-container">
				<div class="ic-row">
					<div class="ic-button-container">
						<ihtml:nbutton property="btnInactivate" tabindex="11"  componentID="BTN_PRODUCTS_DEFAULTS_LISTSUBPRODUCTS_INACTIVATE">
							<common:message  key="Inactivate" scope="request"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btnActivate" tabindex="12" componentID="BTN_PRODUCTS_DEFAULTS_LISTSUBPRODUCTS_ACTIVATE"  >
							<common:message  key="Activate" scope="request"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btnDetails" tabindex="13" componentID="BTN_PRODUCTS_DEFAULTS_LISTSUBPRODUCTS_DETAILS" >
							<common:message  key="Details" scope="request"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClose" tabindex="14"  componentID="BTN_PRODUCTS_DEFAULTS_LISTSUBPRODUCTS_CLOSE"  >
							<common:message  key="close" scope="request"/>
						</ihtml:nbutton>
					</div>					
				</div>
			</div>
			<html:hidden property="productCode"/>
			<html:hidden property="subProductCode"/>
			<html:hidden property="versionNumber"/>
		</div>                       
	</ihtml:form>
</div>

				
	</body>
</html>


