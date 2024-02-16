
<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  INV - Inventory Control
* File Name				:  SCCLov.jsp
* Date					:  28-July-2005
* Author(s)				:  Amritha

*************************************************************************/
 --%>
<%@ page language="java" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
			
	

<bean:define id="form"
name="SccLovForm"
type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.SccLovForm"
toScope="page" />
<title><common:message bundle="<%=form.getBundle() %>" key="products.defaults.title" scope="request"/></title>

<meta name="decorator" content="popuppanelrestyledui">
<common:include type="script" src="/js/products/defaults/SCCLov_Script.jsp" />

</head>
<body id="bodyStyle">
	

<div class="iCargoPopUpContent" style="overflow:auto;width:100%;height:100%">
<ihtml:form action="/products.defaults.screenloadSccLov.do" styleClass="ic-main-form">

<business:sessionBean id="listFromSession" moduleName="product.defaults"
	screenID="products.defaults.maintainproduct"
	method="get"
	attribute="sccLovVOs" />

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
 name="SccLovForm"
 type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.SccLovForm"
 toScope="page" />
<bean:define id="submitParent" name="form" property="selectedData" />

 <html:hidden property="nextAction" name="form" />
 <html:hidden property="selectedValues" name="form" />
 <html:hidden property="lastPageNumber" name="form" />
 <html:hidden property="displayPage" name="form" />
 <bean:define name="form" property="selectedValues" id="strSelectedValues" />
 <ihtml:hidden property="saveSelectedValues"/>

		<div class="ic-content-main">
			<div class="ic-head-container">
				<div class="ic-filter-panel">
					<div class="ic-row">
						<div class="ic-col-100">	
							<div class="ic-input-container">
								<div class="ic-row">
									<div class="ic-input ic-split-35">
										<label class="ic-label-20">
											<common:message bundle="<%=form.getBundle() %>" key="products.defaults.Code" scope="request"/>
										</label>
										<ihtml:text property="sccCode" componentID="TXT_PRODUCTS_DEFAULTS_SCCLOV_SCCCODE" />
									</div>
									<div class="ic-input ic-split-65">
										<label class="ic-label-25">
											<common:message bundle="<%=form.getBundle() %>" key="products.defaults.Description" scope="request"/>
										</label>
										<ihtml:text property="sccDescription" componentID="TXT_PRODUCTS_DEFAULTS_SCCLOV_DESCRIPTION"/>
									</div>
								</div>
								<div class="ic-row">
									<div class="ic-button-container">
										<ihtml:nbutton property="btnList" componentID="BTN_PRODUCTS_DEFAULTS_SCCLOV_LIST" >
											<common:message bundle="<%=form.getBundle()%>" key="products.defaults.list" scope="request"/>
										</ihtml:nbutton>
										<ihtml:nbutton property="btnClear" componentID="BTN_PRODUCTS_DEFAULTS_SCCLOV_CLEAR" >
											<common:message bundle="<%=form.getBundle() %>" key="products.defaults.clear" scope="request"/>
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
							<div class="tableContainer" id="div1" style="height:290px"> 
								<table id="sccLovTable" class="fixed-header-table">
									<thead>
										<tr class="iCargoTableHeadingLeft">
											<td width="10%">
												<input type="checkbox" name="selectAllDetails"/>
											</td>
											<td width="44%" >
												<common:message bundle="<%=form.getBundle() %>" key="products.defaults.SCCCode" scope="request"/>
											</td>
											<td width="46%"> 
												<common:message bundle="<%=form.getBundle() %>" key="products.defaults.SCCDescription" scope="request"/>
											</td>
										</tr>
									</thead>
									<tbody>
										<logic:present name="pageList" >
											<bean:define id="requestlist" name="pageList"  />
											<logic:iterate id="vo" name="requestlist" indexId="nIndex">
												<bean:define id="code" name="vo" property="sccCode" />
												<tr>
													<td class="iCargoTableDataTd" style="text-align:center">               
														<%
														if(((String)strSelectedValues).contains((String)code)){ %>
														<input type="checkbox" name="sccChecked" value="<%=(String)code%>"  checked="checked"/>
														<%}else{ %>
														<input type="checkbox" name="sccChecked" value="<%=(String)code%>" />
														<% } %>				
													</td>
													<td class="iCargoTableDataTd"><bean:write name="vo" property="sccCode" /></td>
													<td class="iCargoTableDataTd"><bean:write name="vo" property="sccDescription" /></td>
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
						<ihtml:button property="btok" styleClass="iCargoButtonSmall" title="Ok"
							onclick="closeScreen(this.form,'products.defaults.getSelectedSccData.do','OK','<%=(String)nextAction%>')" >
							<common:message bundle="<%=form.getBundle() %>" key="products.defaults.ok" scope="request"/>
						</ihtml:button>
						<ihtml:button property="btclose"  styleClass="iCargoButtonSmall" title="Close"
							onclick="closeScreen(this.form,'products.defaults.removeSccLovFromSession.do','close','<%=(String)nextAction%>')">
							<common:message bundle="<%=form.getBundle() %>" key="products.defaults.close" scope="request"/>
						</ihtml:button>
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

