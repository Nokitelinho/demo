<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>


<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name			:CustomerContactLOV.jsp
* Date					:
* Author(s)				:  A-2052
*************************************************************************/
 --%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.business.shared.customer.vo.CustomerContactVO" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListCustomerForm" %>

<html:html>
<head>
			
	
<title>
<common:message bundle="listcustomerform" key="customermanagement.defaults.pointredemed.customercontactlov" />
</title>
<meta name="decorator" content="popup_panel">
<common:include type="script" src="/js/customermanagement/defaults/CustomerContactLOV_Script.jsp"/>
</head>

<body id="bodyStyle">
	
	

 <business:sessionBean id="CollectionCustomerContactVOs"
 		      moduleName="customermanagement.defaults"
 		      screenID="customermanagement.defaults.customerlisting"
 		      method="get"
		      attribute="customerContactVOs"/>

<bean:define id="form"
	     name="ListCustomerForm"
	     type="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListCustomerForm"
	     toScope="page" />

<div class="iCargoPopUpContent" style="height:390px;"   >
<ihtml:form action="/customermanagement.defaults.customerlisting.screenloadcustcontactlov.do" styleClass="ic-main-form">

<ihtml:hidden property="customerCodeLov" />
<ihtml:hidden property="companyCode" />
<ihtml:hidden property="displayPage" />
<ihtml:hidden property="lastPageNum" />
<ihtml:hidden property="rowCount" />
<ihtml:hidden property="formNumber"/>
<ihtml:hidden property="textfiledObj" />
<ihtml:hidden property="textfiledDesc" />

<div class="ic-content-main">
	<span class="ic-page-title ic-display-none">
		<common:message bundle="listcustomerform" key="customermanagement.defaults.pointredemed.customercontactlov" />
	</span>
	<div class="ic-main-container">
		<div class="ic-row" id="listTable">
			<div class="ic-col-100">
				<div class="ic-row">
					<div class="ic-col-75">
						<logic:present name="CollectionCustomerContactVOs">
							<common:paginationTag
								pageURL="javascript:submitList('lastPageNum','displayPage')"
								name="CollectionCustomerContactVOs"
								display="label"
								labelStyleClass="iCargoResultsLabel"
								lastPageNum="<%=form.getLastPageNum() %>" />
						</logic:present>
					</div>
					<div class="ic-col-25">
						<div class="ic-button-container">
							<logic:present name="CollectionCustomerContactVOs">
								<common:paginationTag
									pageURL="javascript:submitList('lastPageNum','displayPage')"
									name="CollectionCustomerContactVOs"
									display="pages"
									lastPageNum="<%=form.getLastPageNum()%>"/>
							</logic:present>
						</div>
					</div>
				</div>
				<div class="ic-row">
					<div id="div1" class="tableContainer"  style="height:200px;width:100%;">
						<table cellspacing="0" class="fixed-header-table" style="width:100%">
							<thead>
								<tr>
									<td width="5%" class="iCargoTableHeadingCenter">
										&nbsp;
									</td>
									<td width="40%" class="iCargoTableHeadingLeft">
										<common:message key="customermanagement.defaults.pointredemed.lbl.customercode" />
									</td>
									<td width="60%" class="iCargoTableHeadingLeft">
										<common:message key="customermanagement.defaults.pointredemed.lbl.customername" />
									</td>
								</tr>
							</thead>
							<tbody>
								<logic:present name="CollectionCustomerContactVOs">
									<%int index = 0;%>
									<logic:iterate id="CollCustomerContactLovVO" name="CollectionCustomerContactVOs" indexId="nIndex">
										<logic:present name="CollCustomerContactLovVO" property="contactCustomerCode">
											<bean:define id="name" name="CollCustomerContactLovVO" property="contactCustomerCode" />
											<tr>
												<% String  desc=((CustomerContactVO)CollCustomerContactLovVO).getCustomerName();
												   String value=(String)name+","+(String)desc;%>
												<td class="iCargoTableTd">
													<input type="checkbox" name="suChecked" value="<%=value%>"/>												</td>
												<td class="iCargoTableTd">
													<bean:write name="CollCustomerContactLovVO" property="contactCustomerCode"/>
												</td >
												<td class="iCargoTableTd" >
													<bean:write name="CollCustomerContactLovVO" property="customerName"/>
												</td>
											</tr>
											<%index++;%>
										</logic:present>
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
				<ihtml:nbutton property="btOk" componentID="CMP_CUSTOMERMANAGEMENT_DEFAULTS_POPUP_OK_BTN" tabindex="5">
					<common:message key="customermanagement.defaults.pointredemed.btn.btok" />
				</ihtml:nbutton>
				<ihtml:nbutton property="btClose" componentID="CMP_CUSTOMERMANAGEMENT_DEFAULTS_POPUP_CLOSE_BTN"  tabindex="6">
					<common:message key="customermanagement.defaults.pointredemed.btn.btclose" />
				</ihtml:nbutton>
			</div>
		</div>
	</div>
</div>
</ihtml:form>   
</div>
			
	</body>
</html:html>
