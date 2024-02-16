<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>


<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:
* File Name				:  TempIDLOV.jsp
* Date					:
* Author(s)				:  A-2135
*************************************************************************/
 --%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.business.shared.customer.vo.TempCustomerVO" %>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.TempIDForm" %>

<html:html>
<head>
			
	
<title>
<common:message bundle="ListTempIDLOV" key="customermanagement.defaults.TempIDLOV" />
</title>
<meta name="decorator" content="popuppanelrestyledui">
<common:include type="script" src="/js/customermanagement/defaults/TempIDLOV_Script.jsp"/>
</head>

<body id="bodyStyle">
	
	
<business:sessionBean id="CollectionTempIDVOLOVs"
		   moduleName="customermanagement.defaults"
		   screenID="customermanagement.defaults.listtempidlov"
		   method="get"
		   attribute="tempIdVOLovVOs"/>

<bean:define id="form" name="TempIDForm"  type="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.TempIDForm" toScope="page" />
<div class="iCargoPopUpContent ic-masterbg" style="position:static; width:100%; z-index:1;" >
<ihtml:form action="/customermanagement.defaults.lov.screenloadtempidlov.do" styleClass="ic-main-form">


<ihtml:hidden property="companyCode" />
<ihtml:hidden property="displayPage" />
<ihtml:hidden property="lastPageNum" />
<ihtml:hidden property="rowCount" />
<ihtml:hidden property="formNumber"/>
<ihtml:hidden property="textfiledObj" />
<ihtml:hidden property="textfiledDesc" />

<div class="ic-content-main bg-white">
	<span class="ic-page-title ic-display-none">
		<common:message bundle="ListTempIDLOV" key="customermanagement.defaults.tempidlov" />
	</span>
	<div class="ic-main-container">
		<div class="ic-row">
			<div class="ic-col-40 marginT5">
				<logic:present name="CollectionTempIDVOLOVs">
					<common:paginationTag
						pageURL="javascript:submitList('lastPageNum','displayPage')"
						name="CollectionTempIDVOLOVs"
						display="label"
						labelStyleClass="iCargoResultsLabel"
						lastPageNum="<%=form.getLastPageNum() %>" />
				</logic:present>
			</div>
			<div class="ic-button-container paddR5">
				<logic:present name="CollectionTempIDVOLOVs">
					<common:paginationTag
						pageURL="javascript:submitList('lastPageNum','displayPage')"
						name="CollectionTempIDVOLOVs"
						display="pages"
						linkStyleClass="iCargoLink"
						disabledLinkStyleClass="iCargoLink"
						lastPageNum="<%=form.getLastPageNum()%>"/>
				</logic:present>
			</div>
		</div>
		<div class="ic-row">
			<div class="tableContainer" id="div1" style="height:340px;">
				<table id="temporaryCustomerId" class="fixed-header-table" >
				<thead>
					<tr class="iCargoTableHeadingLeft">
						<td width="5%" class="iCargoTableHeaderLabel">
						&nbsp;
						</td>
						<td width="40%" class="iCargoTableHeaderLabel">
						<common:message key="customermanagement.defaults.lbl.customercode" />
						</td>
						<td width="55%" class="iCargoTableHeaderLabel">
						<common:message key="customermanagement.defaults.lbl.customerdescription" />
						</td>
					</tr>
				 </thead>
				 <tbody>
					<logic:present name="CollectionTempIDVOLOVs">
					 <%int index = 0;%>
					 <logic:iterate id="collTempIDLovVO" name="CollectionTempIDVOLOVs" indexId="nIndex">
					  <bean:define id="name" name="collTempIDLovVO" property="tempCustCode" />
					  <tr ondblclick="setValueOnDoubleClickForTempId()" >
					   <%
						String  desc=((TempCustomerVO)collTempIDLovVO).getTempCustName();
						 String value=(String)name+"-"+(String)desc;%>
							<td class="iCargoTableDataTd">
							<input type="checkbox" id="templov<%=nIndex.toString()%>" name="suChecked" onclick="singleSelectCb(<%=nIndex%>)" value="<%=value%>"/>
							</td>
							<td class="iCargoTableDataTd">
							<bean:write name="collTempIDLovVO" property="tempCustCode"/>
							</td >
							<td class="iCargoTableDataTd" >
							<bean:write name="collTempIDLovVO" property="tempCustName"/>
							</td>
					  </tr>
					  <%index++;%>
					 </logic:iterate>
					</logic:present>
			   </tbody>
			  </table>
			</div>
		</div>
	</div>
	<div class="ic-foot-container paddR5">
		<div class="ic-row">
			<div class="ic-button-container">
				<ihtml:button property="btOk"
                componentID="CMP_CUSTOMERMANAGEMENT_DEFAULTS_POPUP_OK_BTN"  tabindex="5">
				OK
				</ihtml:button>

              <ihtml:button property="btClose"
                componentID="CMP_CUSTOMERMANAGEMENT_DEFAULTS_POPUP_CLOSE_BTN"  tabindex="6" styleClass="btn-inline btn-secondary">
					<common:message key="customermanagement.defaults.btn.btclose" />
              </ihtml:button>
			</div>
		</div>
	</div>
</div>
     
</ihtml:form>
</div>
			
</body>
</html:html>
