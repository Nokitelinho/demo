<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  INV - Inventory Control
* File Name				:  CommodityLov.jsp
* Date					:  27-July-2005
* Author(s)				:  Amritha S

*************************************************************************/
 --%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>	
	

<bean:define id="form"
name="CommodityForm"
type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.CommodityLovForm"
toScope="page" />

<title><common:message bundle="<%=form.getBundle() %>" key="products.defaults.title" scope="request"/></title>
<common:include type="script" src="/js/products/defaults/CommodityLov_Script.jsp" />
<meta name="decorator" content="popuppanelrestyledui">
</head>

<body id="bodyStyle">
	

<div class="iCargoPopUpContent" style="overflow:auto;width:100%;height:100%">
<ihtml:form action="/products.defaults.screenloadCommodityLov.do" styleClass="ic-main-form">

<business:sessionBean id="listFromSession" moduleName="product.defaults"
	screenID="products.defaults.maintainproduct"
	method="get"
	attribute="commodityLovVOs" />

<business:sessionBean id="nextParentAction" moduleName="product.defaults"
	screenID="products.defaults.maintainproduct"
	method="get"
	attribute="nextAction" />

<bean:define id="nextAction" name="nextParentAction" toScope="page" />
<logic:present name="listFromSession">
	<bean:define id="pageList" name="listFromSession" toScope="page"/>
</logic:present>

<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>

<bean:define id="submitParent" name="form" property="selectedData" />

 <html:hidden property="nextAction" name="form" />
 <html:hidden property="selectedValues" name="form" />
 <html:hidden property="lastPageNumber" name="form" />
 <html:hidden property="displayPage" name="form" />
 <bean:define name="form" property="selectedValues" id="strSelectedValues" />
 
 
 
 
		<div class="ic-content-main">
			<div class="ic-head-container">
				<div class="ic-filter-panel">
					<div class="ic-row">
						<div class="ic-col-100">	
							<div class="ic-input-container">
								<div class="ic-row">
									<div class="ic-input ic-split-30">
										<label class="ic-label-20">
											<common:message  key="products.defaults.Code" scope="request"/>
										</label>
										<ihtml:text property="code" componentID="TXT_PRODUCTS_DEFAULTS_COMMODITYLOV_COMMOCITYCODE" />
									</div>
									<div class="ic-input ic-split-30">
										<label class="ic-label-25">
											<common:message  key="products.defaults.Description" scope="request"/>
										</label>
										<ihtml:text property="description" componentID="TXT_PRODUCTS_DEFAULTS_COMMODITYLOV_DESCRIPTION" />
									</div>
                                    <div class="ic-button-container">
										<ihtml:nbutton property="btnList"  componentID="BTN_PRODUCTS_DEFAULTS_COMMODITYLOV_LIST">
											<common:message  key="products.defaults.List" scope="request"/>
										</ihtml:nbutton>
										<ihtml:nbutton property="btnClear"  componentID="BTN_PRODUCTS_DEFAULTS_COMMODITYLOV_CLEAR">
											<common:message  key="products.defaults.clear" scope="request"/>
										</ihtml:nbutton>
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
							<div class="ic-col-45">
								<logic:present name="pageList">
									<common:paginationTag pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')"
										name="pageList"
										display="label"
										labelStyleClass="iCargoResultsLabel"
										lastPageNum="<%=form.getLastPageNumber() %>" />
								</logic:present>
							</div>
							<div class="ic-col-55">
								<div class="ic-button-container">
									<logic:present name="pageList">
										<common:paginationTag
											pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')"
											name="pageList"
											display="pages"
											linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
											lastPageNum="<%=form.getLastPageNumber()%>" />
									</logic:present>
								</div>
							</div>
						</div>
						<div class="ic-row">
							<div class="tableContainer" id="div1" style="height:260px"> 
								<table id="commodityLovTable" class="fixed-header-table">
									<thead>
										<tr class="iCargoTableHeadingLeft">
											<td width="11%">
												<input type="checkbox" name="selectAllCommodity"
													onclick="updateHeaderCheckBox(this.form,this,this.form.commodityChecked)"/>
											</td>
											<td width="44%" ><common:message  key="products.defaults.ComCode" scope="request"/></td>
											<td width="45%"><common:message  key="products.defaults.ComDesc" scope="request"/></td>
										</tr>
									</thead>
									<tbody>
										<logic:present name="listFromSession" >
											<bean:define id="pages" name="listFromSession"  />
											<logic:iterate id="lovVo" name="pages" indexId="rowCount">
												<bean:define id="code" name="lovVo" property="commodityCode" />
												<tr>
													<td style="text-align:center" class="iCargoTableTd">
														<div >
														<%
															if(((String)strSelectedValues).contains((String)code)){ %>
															<input type="checkbox" name="commodityChecked" value="<%=(String)code%>" 
																checked="checked"
															onclick="toggleTableHeaderCheckbox('commodityChecked',this.form.selectAllCommodity)"/>
															<%}else{ %>
																<input type="checkbox" name="commodityChecked" value="<%=(String)code%>"
															onclick="toggleTableHeaderCheckbox('commodityChecked',this.form.selectAllCommodity)"/>
														<% } %>
														</div>
													</td>
													<td class="iCargoTableTd" ><bean:write name="code" /></td>
													<td class="iCargoTableTd"><bean:write name="lovVo" property="commodityDesc" /></td>
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
			<div class="ic-foot-container">
				<div class="ic-row">
					<div class="ic-button-container">
						<html:button property="btok" styleClass="iCargoButtonSmall" title="Ok"
							onclick="closeScreen(this.form,'products.defaults.getSelectedCommodity.do','OK','<%=(String)nextAction%>')" >
							<common:message  key="products.defaults.ok" scope="request"/>
						</html:button>
						<html:button property="btclose"  styleClass="iCargoButtonSmall" title="Close"
						onclick="closeScreen(this.form,'products.defaults.removeCommodityLovFromSession.do','close','<%=(String)nextAction%>')" >
							<common:message  key="products.defaults.Close" scope="request"/>
						</html:button>
					</div>
				</div>
			</div>
		</div>
 

<html:hidden property="parentSession" />
<script language="javascript">
	callFun('<%=(String)submitParent%>','<%=(String)nextAction%>');
</script>
</ihtml:form>
</div>   

		
	</body>
</html>

