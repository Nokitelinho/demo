
<%--
/***********************************************************************
* Project       	 :  iCargo
* Module Code & Name 	 :  Uld
* File Name     	 :  DamageRefNoLov.jsp
* Date          	 :  16-February-2006
* Author(s)     	 :  Ayswarya.V.Thampy,A-6806
*************************************************************************/
 --%>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.DamageRefNoLovForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

<html:html>
<head>
	
		
		<title>
	<common:message bundle="damageRefNoLovResources" key="uld.defaults.lov.pagetitle" scope="request"/>
		</title>
	<common:include type="script" src="/js/uld/defaults/DamageRefNoLov_Script.jsp" />
		<meta name="decorator" content="popuppanelrestyledui">
</head>

<body>
	<bean:define id="form"
				name="DamageRefNoLovForm"
				type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.DamageRefNoLovForm"
				toScope="request" />

	<business:sessionBean	id="ListCollection"
							moduleName="uld.defaults"
							screenID="uld.defaults.damagerefnolov"
							method="get"
							attribute="ULDDamageReferenceNumberLovVOs" />
	<logic:present name="ListCollection">
	<bean:define id="lovVOs" name="ListCollection" toScope="page"/>
	</logic:present>
	<div class="iCargoPopUpContent">
		<ihtml:form action="/uld.defaults.lov.screenloadlov.do" styleclass="ic-main-form">
		<ihtml:hidden property="lastPageNum"/>
		<ihtml:hidden property="displayPage"/>
		<ihtml:hidden property="masterRowId"/>
		<ihtml:hidden property="pageURL"/>
		<ihtml:hidden property="allChecked"/>
		<div class="ic-content-main">
		<span class="ic-page-title ic-display-none"><common:message bundle="damageRefNoLovResources" key="uld.defaults.lov.pagetitle" scope="request"/></span>			
			<div class="ic-head-container"> 
				<div class="ic-filter-panel">
					<div class="ic-input-container"> 
						<div class="ic-row">
							<div class="ic-input ic-split-50">
								<label>
									<common:message bundle="damageRefNoLovResources" key="uld.defaults.lov.uldno" scope="request"/>
								</label>
								<ihtml:text componentID="ULD_DEFAULTS_LOV_ULDNO" property="uldNo" name="DamageRefNoLovForm"  />
							</div>
							<div class="ic-button-container">
								<ihtml:button property="btList" componentID="ULD_DEFAULTS_LOV_LIST">
								<common:message bundle="damageRefNoLovResources" key="uld.defaults.lov.list" scope="request"/>
								</ihtml:button>

								<ihtml:button property="btClear" styleClass="btn-inline btn-secondary" componentID="ULD_DEFAULTS_LOV_CLEAR">
								<common:message bundle="damageRefNoLovResources" key="uld.defaults.lov.clear" scope="request"/>
								</ihtml:button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-main-container">
				<div class="ic-row">
					<div class="ic-col-60">
					<logic:present name="lovVOs">
						<common:paginationTag
						pageURL="uld.defaults.list.do"
						name="ListCollection"
						display="label"
						labelStyleClass="iCargoLink"
						lastPageNum="<%=form.getLastPageNum() %>" />
					</logic:present>
					</div>
					<div class="ic-col-40">
					<logic:present name="lovVOs">
						<common:paginationTag linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
						pageURL="javascript:submitPage('lastPageNum','displayPage')"
						name="ListCollection"
						display="pages"
						lastPageNum="<%=form.getLastPageNum()%>"/>
					</logic:present>
					<logic:notPresent name="lovVOs">
					&nbsp;
					</logic:notPresent>
					</div>
				</div>
	
				<div class="ic-row">
					<div class="tableContainer" id="div1" style="height:430px">
						<table class="fixed-header-table">
							<thead>
							<tr class="iCargoTableHeadingLeft">
								<td width="3%">
								
								</td>
								<td class="iCargoTableHeader ic-center" width="26%">
								<bean:message bundle="damageRefNoLovResources" key="uld.defaults.lov.damagerefno" scope="request"/>
								</td>
							</tr>
							</thead>
							<tbody>
							<logic:present name="lovVOs">
							<logic:iterate id="ULDDamageReferenceNumberLovVO" name="lovVOs" type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageReferenceNumberLovVO" indexId="indexId">
							<logic:present  name="ULDDamageReferenceNumberLovVO" property="damageReferenceNumber">
								<tr ondblclick="submitClick('<%=ULDDamageReferenceNumberLovVO.getDamageReferenceNumber()%>');" >
									<td >
										<input type="checkbox" name="rowId" value="<%=ULDDamageReferenceNumberLovVO.getDamageReferenceNumber()%>"/>
									</td>
									<td class="ic-center" >
										<input type="hidden" name="damageRefNoTable" value="<%=ULDDamageReferenceNumberLovVO.getDamageReferenceNumber()%>"/>
										<bean:define id="damageReferenceNumber" name="ULDDamageReferenceNumberLovVO" property="damageReferenceNumber"/>
										<bean:write name="ULDDamageReferenceNumberLovVO" property="damageReferenceNumber"/>
										<ihtml:hidden value="<%=String.valueOf(damageReferenceNumber)%>" property="damageReferenceNumber"/>
									</td>
								</tr>
							</logic:present>
							</logic:iterate>
							</logic:present>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="ic-foot-container" >
				<div class="ic-button-container">
					<ihtml:button property="btOk" componentID="ULD_DEFAULTS_LOV_OK">
					<common:message bundle="damageRefNoLovResources" key="uld.defaults.lov.ok" scope="request"/>
					</ihtml:button>

					<ihtml:button property="btClose" styleClass="btn-inline btn-secondary" componentID="ULD_DEFAULTS_LOV_CLOSE">
					<common:message bundle="damageRefNoLovResources" key="uld.defaults.lov.close" scope="request"/>
					</ihtml:button>
				</div>
			</div>
		</div>
		</ihtml:form>
	</div>
		
	</body>
</html:html>

