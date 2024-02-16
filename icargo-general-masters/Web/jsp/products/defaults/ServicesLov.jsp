<%--
* Project	 		: iCargo
* Module Code & Name: stockcontrol
* File Name			: icargo_list_product.jsp
* Date				: 15-07-2005
* Author(s)			: Akhila.S
 --%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="com.ibsplc.icargo.business.shared.service.vo.ServiceLovVO"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
			
	
<bean:define id="form"
name="ServiceLovForm"
type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ServiceLovForm"
toScope="page" />
<title><common:message bundle="<%=form.getBundle() %>" key="products.defaults.title" scope="request"/></title>
<common:include type="script" src="/js/products/defaults/ServiceLov_Script.jsp" />
<meta name="decorator" content="popuppanelrestyledui">

</head>
<body>
	

<div class="iCargoPopUpContent" style="overflow:auto;width:100%;height:100%">
<ihtml:form action="/products.defaults.screenloadservicelov.do" styleClass="ic-main-form">

<business:sessionBean id="listFromSession" moduleName="product.defaults"
	screenID="products.defaults.maintainproduct"
	method="get"
	attribute="serviceLovVOs" />

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

<bean:define id="form"
	name="ServiceLovForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ServiceLovForm"
	toScope="page" />
<bean:define id="submitParent" name="form" property="selectedData" />

 <html:hidden property="nextAction" name="form" />
 <html:hidden property="selectedValues" name="form" />
 <html:hidden property="lastPageNumber" name="form" />
 <html:hidden property="displayPage" name="form" />
  <ihtml:hidden property="saveSelectedValues"/>
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
											<common:message bundle="<%=form.getBundle() %>" key="products.defaults.Code" scope="request"/>
										</label>
										<ihtml:text property="serviceCode" componentID="TXT_PRODUCTS_DEFAULTS_SERVICELOV_SERVICECODE"/>
									</div>
									<div class="ic-input ic-split-30">
										<label class="ic-label-25">
											<common:message bundle="<%=form.getBundle() %>" key="products.defaults.Description" scope="request"/>
										</label>
										<ihtml:text property="serviceDescription" componentID="TXT_PRODUCTS_DEFAULTS_SERVICELOV_DESCRIPTION"/>
									</div>
                                    <div class="ic-button-container">
										<ihtml:nbutton property="btnList" componentID="BTN_PRODUCTS_DEFAULTS_SERVICELOV_LIST" >
											<common:message bundle="<%=form.getBundle() %>" key="products.defaults.list" scope="request"/>
										</ihtml:nbutton>
										<ihtml:nbutton property="btnClear" componentID="BTN_PRODUCTS_DEFAULTS_SERVICELOV_CLEAR" >
											<common:message bundle="<%=form.getBundle() %>" key="products.defaults.clear" scope="request"/>
										</ihtml:nbutton>
									</div>
								</div>
								<div class="ic-row">
									
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
							<div class="ic-col-50">
								 <logic:present name="pageList">
									<common:paginationTag pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')"
										name="pageList"
										display="label"
										labelStyleClass="iCargoResultsLabel"
										lastPageNum="<%=form.getLastPageNumber() %>" />
									</logic:present>
							</div>
							<div class="ic-col-50">
								<div class="ic-button-container">
									<logic:present name="pageList">
										<common:paginationTag linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
											pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')"
											name="pageList"
											display="pages"
											lastPageNum="<%=form.getLastPageNumber()%>"/>
									</logic:present>
								</div>
							</div>
						</div>
						<div class="ic-row">
							<div class="tableContainer" id="div1" style="height:190px"> 
								<table id="servicesLovTable" class="fixed-header-table" style="width:100%">
									<thead>
										<tr class="iCargoTableHeadingLeft">
											<td width="10%" style="text-align:center">
												<input type="checkbox" name="selectAllDetails"
													onclick="updateHeaderCheckBox(this.form,this,this.form.serviceChecked)"/>
											</td>
											<td width="44%" >
												<common:message bundle="<%=form.getBundle() %>" 
													key="products.defaults.ServiceCode" scope="request"/>
											</td>
											<td width="46%">
												<common:message bundle="<%=form.getBundle() %>" 
													key="products.defaults.ServiceDescription" scope="request"/></td>
										</tr>
									</thead>
									<tbody>
										<logic:present name="pageList" >
											<logic:iterate id="vo" name="pageList" indexId="nIndex">
												<tr>
													<td class="iCargoTableDataTd" style="text-align:center">
														<%
															if(((String)strSelectedValues).contains(((ServiceLovVO)vo).getServiceCode())){ %>
																<input type="checkbox" name="serviceChecked" 
																	value="<%=((ServiceLovVO)vo).getServiceCode()%>"  checked="checked" />
														<%}else{ %>
															<input type="checkbox" name="serviceChecked" 
															value="<%=((ServiceLovVO)vo).getServiceCode()%>" 
															onclick="toggleTableHeaderCheckbox('serviceChecked',this.form.selectAllDetails)" />
														<% } %>
													</td>
													<td width="45%" class="iCargoTableDataTd"><bean:write name="vo" property="serviceCode" /></td>
													<td width="45%" class="iCargoTableDataTd">
														<bean:write name="vo" property="serviceDescrption" />
													</td>
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
						<html:button property="btok" title="OK" styleClass="iCargoButtonSmall"
							onclick="closeScreen(this.form,'products.defaults.getSelectedServiceData.do','OK','<%=(String)nextAction%>')" >
							<common:message bundle="<%=form.getBundle() %>" key="products.defaults.ok" scope="request"/>
						</html:button>
						<html:button property="btclose" title="Close" styleClass="iCargoButtonSmall"
							onclick="closeScreen(this.form,'products.defaults.removeServiceLovFromSession.do','close','<%=(String)nextAction%>')">
							<common:message bundle="<%=form.getBundle() %>" key="products.defaults.Close" scope="request"/>
						</html:button>
					</div>
				</div>
			</div>
		</div>
 
<script language="javascript">
callFun('<%=(String)submitParent%>','<%=(String)nextAction%>');
</script>
 </ihtml:form>
 </div>	 

	</body>
</html>

