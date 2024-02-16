<%--
* Project	 		: iCargo
* Module Code & Name: stockcontrol
* File Name			: icargo_list_product.jsp
* Date				: 15-07-2005
* Author(s)			: Akhila.S
 --%>

<%@ page language="java" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import = "com.ibsplc.icargo.framework.util.time.LocalDate" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	
		<bean:define id="form"
			name="ListProductForm"
			type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ListProductForm"
			toScope="page" />
		<title>
			<common:message bundle="<%=form.getBundle()%>" key="screenTitle" scope="request"/>
		</title>
		<common:include type="script" src="/js/products/defaults/ListProduct_Script.jsp" />
		<meta name="decorator" content="mainpanelrestyledui">
	</head>
	<body id="bodyStyle">
	
		<business:sessionBean id="statusVO" moduleName="product.defaults" screenID="products.defaults.listproducts" method="get" attribute="status" />
		<business:sessionBean id="priorityVO" moduleName="product.defaults" screenID="products.defaults.listproducts" method="get" attribute="priority" />
		<business:sessionBean id="transportModeVO" moduleName="product.defaults" screenID="products.defaults.listproducts" method="get" attribute="transportMode" />
		<business:sessionBean id="pageProductVOs" moduleName="product.defaults" screenID="products.defaults.listproducts" method="get" attribute="pageProductVO" />
		<business:sessionBean id="prdCtgVos" moduleName="product.defaults" screenID="products.defaults.listproducts" method="get"
													attribute="productCategories" />
		<logic:present name="prdCtgVos" >
			<bean:define id="productCategoryOneTimes" name="prdCtgVos"  />
		</logic:present>
		<logic:present name="statusVO" >
			<bean:define id="statusOnetime" name="statusVO"  />
		</logic:present>
		<logic:present name="pageProductVOs" >
			<bean:define id="productVO" name="pageProductVOs"  />
		</logic:present>
		<logic:present name="priorityVO">
			<bean:define id="priorityOnetime" name="priorityVO"/>
		</logic:present>
		<logic:present name="transportModeVO">
			<bean:define id="transportModeOnetime" name="transportModeVO" />
		</logic:present>

<div class="iCargoContent ic-masterbg" id="pageDiv" style="overflow:auto;height:100%">
	<ihtml:form action="products.defaults.screenloadlistproducts.do">
		<html:hidden property="displayPage" />
		<html:hidden property="lastPageNum" />
		<html:hidden property="fromListProduct" />
		<input type="hidden" name="mySearchEnabled" />
		<input type="hidden" name="currentDialogId" />
		<input type="hidden" name="currentDialogOption" />
		<html:hidden property="buttonStatusFlag" />
		<html:hidden property="screenMode" />
		
		<div class="ic-content-main">
			<span class="ic-page-title ic-display-none">
				<common:message key="title" scope="request"/>
			</span>
			<div class="ic-head-container" >
				<!-- <div class="ic-row">
					<h3><common:message  key="searchCriteria" scope="request"/></h3>
				</div> -->
				<div class="ic-filter-panel">
                    <div class="ic-row"><h3><common:message  key="searchCriteria" scope="request"/></h3></div>
					<div class="ic-row">
						<div class="ic-col-100">	
							<div class="ic-input-container">
								<!-- <div class="ic-col-30 ic-label-30">
									<div class="ic-input ic-split-100">
										<label>
											 <common:message  key="productName" scope="request"/>
										</label>
										<ihtml:text property="productName" componentID="TXT_PRODUCTS_DEFAULTS_LISTPRODUCTS_PRDNAME"
											maxlength="29" tabindex="1" />
										<img src="<%=request.getContextPath()%>/images/lov.gif" id="productLov" tabindex="2" onclick="displayProductLov('products.defaults.screenloadProductLov.do',document.forms[1].elements.productName.value,'productName','1')" />
									</div>
									<div class="ic-input ic-split-100">
										<label>
											 <common:message key="transportMode" scope="request"/>
										</label>
										<ihtml:select property="transportMode"  componentID="CMB_PRODUCTS_DEFAULTS_LISTPRODUCTS_TRAMOD"
											tabindex="5">
											<logic:present name="transportModeOnetime" >
												<html:option value="ALL">ALL</html:option>
												<logic:iterate id="oneTimeVO" name="transportModeOnetime">
													<bean:define id="defaultValue" name="oneTimeVO" property="fieldValue" />
													<html:option value="<%=(String)defaultValue%>"><%=(String)defaultValue%></html:option>
												</logic:iterate>
											</logic:present>
										</ihtml:select>
									</div>
									<div class="ic-input ic-split-100">
										<label>
											<common:message  key="fromDate" scope="request"/>
										</label>
										<ibusiness:calendar property="fromDate" type="image" id="TXT_PRODUCTS_DEFAULTS_LISTPRODUCTS_FROMDATE"
											componentID="TXT_PRODUCTS_DEFAULTS_LISTPRODUCTS_FROMDATE" value="<%=form.getFromDate()%>" />
									</div>
								</div>
								<div class="ic-col-30 ic-label-15">
									<div class="ic-input ic-split-100">
										<label>
											<common:message  key="status" scope="request"/>
										</label>
										<ihtml:select property="status" componentID="CMB_PRODUCTS_DEFAULTS_LISTPRODUCTS_STATUS"
											tabindex="3">
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
										<label>
											 <common:message  key="priority" scope="request"/>
										</label>
										<ihtml:select property="priority" componentID="CMB_PRODUCTS_DEFAULTS_LISTPRODUCTS_PRIORITY" tabindex="6">
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
									<div class="ic-input ic-split-100">
										<label>
											 <common:message  key="toDate" scope="request"/>
										</label>
										 <ibusiness:calendar property="toDate" type="image" 
											value="<%=form.getToDate()%>" componentID="TXT_PRODUCTS_DEFAULTS_LISTPRODUCTS_TODATE"  
											id="TXT_PRODUCTS_DEFAULTS_LISTPRODUCTS_TODATE"/>
									</div>
								</div>
								<div class="ic-col-40 ic-label-15">
									<div class="ic-input ic-split-100">
										<label class="ic-label-30">
										<ihtml:checkbox property="rateDefined" componentID="CHK_PRODUCTS_DEFAULTS_LISTPRODUCTS_RATEDEFINED"
							  				tabindex="4"/>
											<common:message  key="rateDefined" scope="request"/>
										</label>										
									</div>
									<div class="ic-input ic-split-100">
										<label>
											<common:message  key="scc" scope="request"/>
										</label>
										<input type="hidden" name="productSccDesc" value=""/>
										<ihtml:text property="productScc" componentID="TXT_PRODUCTS_DEFAULTS_LISTPRODUCTS_SCC"
											maxlength="3" tabindex="7" />
										<img src="<%=request.getContextPath()%>/images/lov.gif" id="sccLov"
											onclick="displayLOV('showScc.do','N','Y','showScc.do',document.forms[1].elements.productScc.value,'Select SCC','1','productScc','productSccDesc')" tabindex="8"/>
									</div>
									<div class="ic-input ic-split-100">
										<label > 
											<common:message key="products.defaults.productcategory" /></label>
											<ihtml:select 
												multiSelect="true" 
												indexId="rowCountForType"
												multiSelectNoneSelectedText="----Select-------------------------" 
												multiSelectMinWidth="165" 
												multiple="multiple" 
												property="productCategory" 
												name="form" id="CMP_PRODUCTS_DEFAULTS_LISTPRODUCTS_CATEGORY"
												componentID="CMP_PRODUCTS_DEFAULTS_LISTPRODUCTS_CATEGORY" >
												<business:sessionBean id="productCategoryOneTimes" moduleName="product.defaults"
													screenID="products.defaults.listproducts"
													method="get"
													attribute="productCategories" />
												<logic:present name="productCategoryOneTimes" >
														<logic:iterate id="oneTimeVO" name="productCategoryOneTimes">
															<bean:define id="defaultValue" name="oneTimeVO" property="fieldValue" />
															<bean:define id="displayValue" name="oneTimeVO" property="fieldDescription" />
															<html:option value="<%=(String)defaultValue%>"><%=(String)displayValue%></html:option>
														</logic:iterate>
													</logic:present>
												</ihtml:select>	
									</div>
									<div class="ic-input ic-split-100">
										<div class="ic-button-container">
											<ihtml:nbutton property="btList" 
												componentID="BTN_PRODUCTS_DEFAULTS_LISTPRODUCTS_LIST" tabindex="13" accesskey="L">
												<common:message  key="list" scope="request"/>
											</ihtml:nbutton>
											<ihtml:nbutton property="btClear"
												componentID="BTN_PRODUCTS_DEFAULTS_LISTPRODUCTS_CLEAR" tabindex="14" accesskey="C">
												<common:message  key="clear" scope="request"/>
											</ihtml:nbutton>
										</div>
									</div>
								</div>-->
                                <div class="ic-row marginB5">
                                    <div class="ic-input ic-split-25">
										<label>
											 <common:message  key="productName" scope="request"/>
										</label>
										<ihtml:text property="productName" componentID="TXT_PRODUCTS_DEFAULTS_LISTPRODUCTS_PRDNAME"
											maxlength="29" tabindex="1" />
										<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" id="productLov" tabindex="2" onclick="displayProductLov('products.defaults.screenloadProductLov.do',document.forms[1].elements.productName.value,'productName','1')" /></div>
									
                                </div>
                                    <div class="ic-input ic-split-25">
										<label>
											<common:message  key="status" scope="request"/>
										</label>
										<ihtml:select property="status" componentID="CMB_PRODUCTS_DEFAULTS_LISTPRODUCTS_STATUS"
											tabindex="3">
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
                                    <div class="ic-input ic-split-25 marginT10">
										<label class="ic-label-30">
										<ihtml:checkbox property="rateDefined" componentID="CHK_PRODUCTS_DEFAULTS_LISTPRODUCTS_RATEDEFINED"
							  				tabindex="4"/>
											<common:message  key="rateDefined" scope="request"/>
										</label>										
									</div> 
                                    <div class="ic-input ic-split-25">
										<label>
											 <common:message key="transportMode" scope="request"/>
										</label>
										<ihtml:select property="transportMode"  componentID="CMB_PRODUCTS_DEFAULTS_LISTPRODUCTS_TRAMOD"
											tabindex="5">
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
                                <div class="ic-row marginB5">
                                  <div class="ic-input ic-split-25">
										<label>
											 <common:message  key="priority" scope="request"/>
										</label>
										<ihtml:select property="priority" componentID="CMB_PRODUCTS_DEFAULTS_LISTPRODUCTS_PRIORITY" tabindex="6">
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
                                  <div class="ic-input ic-split-25">
										<label>
											<common:message  key="scc" scope="request"/>
										</label>
										<input type="hidden" name="productSccDesc" value=""/>
										<ihtml:text property="productScc" componentID="TXT_PRODUCTS_DEFAULTS_LISTPRODUCTS_SCC"
											maxlength="3" tabindex="7" />
										<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" id="sccLov"
											onclick="displayLOV('showScc.do','N','Y','showScc.do',document.forms[1].elements.productScc.value,'Select SCC','1','productScc','productSccDesc')" tabindex="8"/></div>
									</div>
                                  <div class="ic-input ic-split-25">
										<label>
											<common:message  key="fromDate" scope="request"/>
										</label>
										<ibusiness:calendar property="fromDate" type="image" id="TXT_PRODUCTS_DEFAULTS_LISTPRODUCTS_FROMDATE"
											componentID="TXT_PRODUCTS_DEFAULTS_LISTPRODUCTS_FROMDATE" value="<%=form.getFromDate()%>" />
									</div> 
                                  <div class="ic-input ic-split-25">
										<label>
											 <common:message  key="toDate" scope="request"/>
										</label>
										 <ibusiness:calendar property="toDate" type="image" 
											value="<%=form.getToDate()%>" componentID="TXT_PRODUCTS_DEFAULTS_LISTPRODUCTS_TODATE"  
											id="TXT_PRODUCTS_DEFAULTS_LISTPRODUCTS_TODATE"/>
									</div>  
                                </div>
                                <div class="ic-row marginB5">
                                    <div class="ic-input ic-split-25">
										<label > 
											<common:message key="products.defaults.productcategory" /></label>
											<ihtml:select 
												multiSelect="true" 
												indexId="rowCountForType"
												multiSelectNoneSelectedText="----Select-------------------------" 
												multiSelectMinWidth="165" 
												multiple="multiple" 
												property="productCategory" 
												name="form" id="CMP_PRODUCTS_DEFAULTS_LISTPRODUCTS_CATEGORY"
												componentID="CMP_PRODUCTS_DEFAULTS_LISTPRODUCTS_CATEGORY" >
												<business:sessionBean id="productCategoryOneTimes" moduleName="product.defaults"
													screenID="products.defaults.listproducts"
													method="get"
													attribute="productCategories" />
												<logic:present name="productCategoryOneTimes" >
														<logic:iterate id="oneTimeVO" name="productCategoryOneTimes">
															<bean:define id="defaultValue" name="oneTimeVO" property="fieldValue" />
															<bean:define id="displayValue" name="oneTimeVO" property="fieldDescription" />
															<html:option value="<%=(String)defaultValue%>"><%=(String)displayValue%></html:option>
														</logic:iterate>
													</logic:present>
												</ihtml:select>	
									</div>
									<div class="ic-input ic-split-75 ic-right">
										<div class="ic-button-container">
											<ihtml:nbutton property="btList" 
												componentID="BTN_PRODUCTS_DEFAULTS_LISTPRODUCTS_LIST" tabindex="13" accesskey="L">
												<common:message  key="list" scope="request"/>
											</ihtml:nbutton>
											<ihtml:nbutton property="btClear"
												componentID="BTN_PRODUCTS_DEFAULTS_LISTPRODUCTS_CLEAR" tabindex="14" accesskey="C">
												<common:message  key="clear" scope="request"/>
											</ihtml:nbutton>
										</div>
									</div>
									<div class="ic-row ic-label-45" style="padding-top:20px;">	
                                </div>
                                </div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-main-container">
				<div class="ic-row">
				<a class="panel upArrow"  collapseFilter="true"  collapseFilterCallbackFun=""  href="#"></a>
					<h3>Product Details</h3>
				</div>
				<div class="ic-section">
					<div class="ic-row">
						<div class="ic-col-45">
							<logic:present name="productVO">
								<common:paginationTag pageURL="javascript:submitListProducts('lastPageNum','displayPage')"
									name="productVO"
									display="label"
									labelStyleClass="iCargoResultsLabel"
									lastPageNum="<%=form.getLastPageNum() %>" />
							</logic:present>
						</div>
						<div class="ic-col-55">
							<div class="ic-button-container paddR5">
								<logic:present name="productVO">
									<common:paginationTag linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
										pageURL="javascript:submitListProducts('lastPageNum','displayPage')"
										name="productVO"
										display="pages"
										lastPageNum="<%=form.getLastPageNum()%>"
										exportToExcel="true"
										exportTableId="productTable"
										exportAction="products.defaults.listproductspagination.do"/>
								</logic:present>				
							</div>
						</div>
					</div>
					<div class="ic-row">
						<div class="tableContainer" id="div1"  style="height:418px;width:100%;">
		  					<table  class="fixed-header-table" id="productTable">
								<thead>
			  						<tr class="iCargoTableHeadingLeft">
										<td width="3%" class="iCargoTableHeadingCenter">
											&nbsp;													
										</td>
										<td width="11%">
											<common:message  key="name" scope="request"/><span></span>
										</td>
										<td width="18%">
											<common:message  key="description" scope="request"/><span></span>
										</td>
										<td width="12%">
											<common:message  key="status" scope="request"/><span></span>
										</td>
										<td width="12%">
											<common:message  key="rateDefined" scope="request"/><span></span>
										</td>
										<td width="20%">
											<common:message  key="productPriority" scope="request"/><span></span>
										</td>
										<td width="20%">
											<common:message  key="displayInProtal" scope="request"/><span></span>
										</td>
										<td width="16%">
											<common:message  key="startDate" scope="request"/><span></span>
										</td>
										<td width="16%">
											<common:message  key="endDate" scope="request"/><span></span>
										</td>
										<td width="16%">
											<common:message  key="products.defaults.productcategory" scope="request"/><span></span>
										</td>
										
			  						</tr>
								</thead>
								<tbody>
									<%  int k=0;
										int rowCount=0;
										String productStatus = "";
										String hiddenProductName = "";
										String hiddenProductCode = "";
										String rateDefined = "";
										String rateDefinedvalue = "";
										String hiddenStartDate = "";
										String hiddenEndDate = "";
										String fromDateString="";
										String endDateString="";
										String displayInPortal = "";
										String displayInPortalValue = "";
										String productPriotity = "";
									%>
									<logic:present name="productVO" >
										<bean:define id="list" name="productVO"  toScope="page"/>
										<logic:iterate id="productList" name="list" indexId="nIndex">
											<bean:define id="lp" 
												name="productList" type="com.ibsplc.icargo.business.products.defaults.vo.ProductVO"/>
											<%  productStatus = "productStatus";
												hiddenProductName = "hiddenProductName";
												hiddenProductCode = "hiddenProductCode";
												rateDefined = "rateDefined";
												hiddenStartDate = "hiddenStartDate";
												hiddenEndDate = "hiddenEndDate";
												productPriotity = "productPriotity";
												if(lp.getIsRateDefined()){
													rateDefinedvalue = "Yes";
												}else{
													rateDefinedvalue = "No";
												}
												displayInPortal = "displayInPortal";
												if(lp.getIsDisplayInPortal()){
													displayInPortalValue = "Yes";
												}else{
													displayInPortalValue = "No";
												}
												fromDateString = TimeConvertor.toStringFormat(((LocalDate)(lp.getStartDate())).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
												endDateString = TimeConvertor.toStringFormat(((LocalDate)(lp.getEndDate())).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
											%>
											<% if(k==0){%>
												<tr>
													<input type="hidden" name ="<%=productStatus+rowCount%>" value="<%=lp.getStatus()%>">
													<input type="hidden" name ="<%=hiddenProductName+rowCount%>" value="<%=lp.getProductName()%>">
													<input type="hidden" name ="<%=hiddenProductCode+rowCount%>" value="<%=lp.getProductCode()%>">
													<input type="hidden" name ="<%=rateDefined+rowCount%>" value="<%=rateDefinedvalue%>">
													<input type="hidden" name ="<%=displayInPortal+rowCount%>" value="<%=displayInPortalValue%>">
													<input type="hidden" name ="<%=productPriotity+rowCount%>" value="<%=lp.getPrdPriority()%>">
													<input type="hidden" name ="<%=hiddenStartDate+rowCount%>" value="<%=fromDateString%>">
													<input type="hidden" name ="<%=hiddenEndDate+rowCount%>" value="<%=endDateString%>">
													<td class="iCargoTableDataTd" ic-center>
														<input type="checkbox"  name="checkBox" value="<%=lp.getProductCode()%>"  onclick="singleSelectCb(this.form,'<%=lp.getProductCode()%>','checkBox');"/>
													</td>
													<td class="iCargoTableDataTd">
														<bean:write name="lp" property="productName"/>
													</td>
													<td class="iCargoTableDataTd">
														<bean:write name="lp" property="description"/>
													</td>
													<td class="iCargoTableDataTd">
														<bean:write name="lp" property="status"/>
													</td>
													<td class="iCargoTableDataTd">
														<%=rateDefinedvalue%>
													</td>
													<td class="iCargoTableDataTd">
														<bean:write name="lp" property="prdPriority"/>
													</td>
													<td class="iCargoTableDataTd">
														<%=displayInPortalValue%>
													</td>
													<td class="iCargoTableDataTd">
														<%=fromDateString%>
													</td>
													<td class="iCargoTableDataTd">
														<%=endDateString%>
													</td>
													<td>
														<bean:write name="lp" property="productCategory"/>
													</td>
													<% k=1; %>
												</tr>
											<%} else{ %>
												<tr>
													<input type="hidden" name ="<%=productStatus+rowCount%>" value="<%=lp.getStatus()%>">
													<input type="hidden" name ="<%=hiddenProductName+rowCount%>" value="<%=lp.getProductName()%>">
													<input type="hidden" name ="<%=hiddenProductCode+rowCount%>" value="<%=lp.getProductCode()%>">
													<input type="hidden" name ="<%=rateDefined+rowCount%>" value="<%=rateDefinedvalue%>">
													<input type="hidden" name ="<%=displayInPortal+rowCount%>" value="<%=displayInPortalValue%>">
													<input type="hidden" name ="<%=productPriotity+rowCount%>" value="<%=lp.getPrdPriority()%>">
													<input type="hidden" name ="<%=hiddenStartDate+rowCount%>" value="<%=fromDateString%>">
													<input type="hidden" name ="<%=hiddenEndDate+rowCount%>" value="<%=endDateString%>">												
													<td class="iCargoTableDataTd" ic-center>
														<input type="checkbox"  name="checkBox" value="<%=lp.getProductCode()%>"  
															onclick="singleSelectCb(this.form,'<%=lp.getProductCode()%>','checkBox');"/>
													</td>
													<td class="iCargoTableDataTd">
														<bean:write name="lp" property="productName"/>
													</td>
													<td class="iCargoTableDataTd">
														<bean:write name="lp" property="description"/>
													</td>
													<td class="iCargoTableDataTd">
														<bean:write name="lp" property="status"/>
													</td>
													<td class="iCargoTableDataTd">
														<%=rateDefinedvalue%>
													</td>
													<td class="iCargoTableDataTd"> 
														<bean:write name="lp" property="prdPriority"/>
													</td>
													<td class="iCargoTableDataTd">
														<%=displayInPortalValue%>
													</td>
													<td class="iCargoTableDataTd"> 
														<%=fromDateString%>
													</td>
													<td class="iCargoTableDataTd">
														<%=endDateString%>
													</td>
													<td>
														<bean:write name="lp" property="productCategory"/>
													</td>
													<% k=0;%>
												</tr>
											<% } rowCount++;%>
										</logic:iterate>
									</logic:present>
									<input type="hidden" name="rowCount" value=<%=rowCount%> />
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-foot-container" style="height:57px;">
				<div class="ic-row">
					<div class="ic-button-container paddR5">
						<ihtml:nbutton property="btnListSubProd" componentID="BTN_PRODUCTS_DEFAULTS_LISTPRODUCTS_LISTSUBPRD" tabindex="15" accesskey="U"><common:message  key="listassub" scope="request"/></ihtml:nbutton>
						<ihtml:nbutton property="btnSaveAs" componentID="BTN_PRODUCTS_DEFAULTS_LISTPRODUCTS_SAVEAS" tabindex="16" accesskey="N"><common:message  key="save" scope="request"/></ihtml:nbutton>
						<ihtml:nbutton property="btnInactivate" componentID="BTN_PRODUCTS_DEFAULTS_LISTPRODUCTS_INACTIVATE" tabindex="17" accesskey="V"><common:message  key="Inactivate" scope="request"/></ihtml:nbutton>
						<ihtml:nbutton property="btnActivate" componentID="BTN_PRODUCTS_DEFAULTS_LISTPRODUCTS_ACTIVATE" tabindex="18" accesskey="I"><common:message  key="Activate" scope="request"/></ihtml:nbutton>
						<ihtml:nbutton property="btnCreate" componentID="BTN_PRODUCTS_DEFAULTS_LISTPRODUCTS_CREATE" tabindex="19" accesskey="R"><common:message  key="Create" scope="request"/></ihtml:nbutton>
						<ihtml:nbutton property="btnDetails" componentID="BTN_PRODUCTS_DEFAULTS_LISTPRODUCTS_DETAILS" tabindex="20" accesskey="D"><common:message  key="Details" scope="request"/></ihtml:nbutton>
						<ihtml:nbutton property="btnDelete" componentID="BTN_PRODUCTS_DEFAULTS_LISTPRODUCTS_DELETE" tabindex="21" accesskey="E"><common:message  key="Delete" scope="request"/></ihtml:nbutton>
						<ihtml:nbutton property="btnClose" componentID="BTN_PRODUCTS_DEFAULTS_LISTPRODUCTS_CLOSE" tabindex="22" accesskey="O"><common:message  key="close" scope="request"/></ihtml:nbutton>
					</div>
					<input type="hidden" name="productCode"/>
					<input type="hidden" name="mode"/>
					<input type="hidden" name="startDate" />
					<input type="hidden" name="endDate" />
				</div>
			</div>
		</div>
	</ihtml:form>
</div>
	
	</body>
</html>

