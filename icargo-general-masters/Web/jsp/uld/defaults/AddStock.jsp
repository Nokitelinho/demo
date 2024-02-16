<%--
* Project	 		: iCargo
* Module Code & Name: Tariff
* File Name			: SetupChargeHeads.jsp
* Date				: 04-Aug-2005
* Author(s)			: A-2052
 --%>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainULDStockForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>



<html:html>
<head>

<title>
<common:message bundle="maintainuldstock" key="uld.defaults.popupscreentitle" />
</title>
	<meta name="decorator" content="popup_panel">
	<common:include type="script" src="/js/uld/defaults/AddStock_Script.jsp"/>
</head>

<body>
	

<business:sessionBean id="oneTimeValues"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.maintainuldstock"
		   method="get"
		   attribute="oneTimeValues"/>

<bean:define id="form"
		name="maintainULDStockForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainULDStockForm"
		toScope="page" />



<div class="iCargoPopUpContent" style="scroll=auto;height:280px;width:650px;">
<ihtml:form action="/uld.defaults.screenloadcreateuldsetupstock.do" styleclass="ic-main-form">

<ihtml:hidden property="fromScreen" value="<%= form.getFromScreen() %>"/>
<ihtml:hidden property="createStatus" value="<%= form.getCreateStatus() %>"/>
<ihtml:hidden property="filterStatus" value="<%= form.getFilterStatus() %>"/>
<ihtml:hidden property="flag"/>
<ihtml:hidden property="dmgdisplayPage"/>
<ihtml:hidden property="dmglastPageNum"/>
<ihtml:hidden property="dmgtotalRecords"/>
<ihtml:hidden property="dmgcurrentPageNum"/>
<ihtml:hidden property="statusFlag" value="<%= form.getStatusFlag() %>"/>
<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />
<ihtml:hidden property="stationMain"/>
<ihtml:hidden property="airlineMain"/>
<ihtml:hidden property="disableUldNature"/>

	<div class="ic-content-main" id="outerTab">
		
		<div class="ic-main-container">
			<logic:equal name="maintainULDStockForm" property="statusFlag" value="screen_mode_create">
			<div class="ic-row ic-right">
				<a href="#" class="iCargoLink" id="createdmg" >Create</a>
					| <a href="#" class="iCargoLink" id="deletedmg" >Delete</a>
					||<common:popuppaginationtag
					pageURL="javascript:selectNextDmgDetail('lastPageNum','displayPage')"
					linkStyleClass="iCargoLink"
					disabledLinkStyleClass="iCargoLink"
					displayPage="<%=form.getDmgdisplayPage()%>"
					totalRecords="<%=form.getDmgtotalRecords()%>" />
			</div>
			</logic:equal>
			
			<logic:equal name="maintainULDStockForm" property="statusFlag" value="screen_mode_modify">
			<div class="ic-row ic-right">
				<common:popuppaginationtag
					pageURL="javascript:selectNextDmgDetail('lastPageNum','displayPage')"
					linkStyleClass="iCargoLink"
					disabledLinkStyleClass="iCargoLink"
					displayPage="<%=form.getDmgdisplayPage()%>"
					totalRecords="<%=form.getDmgtotalRecords()%>" />
			</div>
			</logic:equal>
			<div class="ic-row ic-border" style="width:99%;">
			<div class="ic-input-container">
			<div class="ic-row">
				<div class="ic-input ic-mandatory ic-split-40  ic-label-45">
				<label><common:message bundle="maintainuldstock" key="uld.defaults.column1" /></label>
					<ihtml:text componentID="CMP_ULD_DEFAULTS_STOCK_MAINTAINULDSTOCK_AIRLINECODE" property="airlineCode" name="maintainULDStockForm" style="text-transform:uppercase;" maxlength="3"/>
					<img name="airlinelov" id="airlinelov" src="<%=request.getContextPath()%>/images/lov.gif" width="18" height="18" alt="Airline LOV"/>
				</div>
				<div class="ic-input ic-mandatory ic-split-30  ic-label-30">
				<label><common:message bundle="maintainuldstock" key="uld.defaults.column2" /></label>
					<ihtml:text componentID="CMP_ULD_DEFAULTS_STOCK_MAINTAINULDSTOCK_STATIONCODE" property="stationCode" name="maintainULDStockForm" maxlength="10" disabled="true" style="text-transform : uppercase"/>
				</div>
				<div class="ic-input ic-mandatory ic-split-30  ic-label-42">
				<label><common:message key="uld.defaults.lbl.uldnature" /></label>
					<ihtml:select property="uldNature" componentID="CMP_ULD_DEFAULTS_STOCK_MAINTAINULDSTOCK_ULDNATURE_DTL">
					 <logic:present name="oneTimeValues">
						<logic:iterate id="oneTimeValue" name="oneTimeValues">
							<bean:define id="parameterCode" name="oneTimeValue" property="key" />
							<logic:equal name="parameterCode" value="uld.defaults.uldnature">
							<bean:define id="parameterValues" name="oneTimeValue" property="value" />
								<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
									<logic:present name="parameterValue">
									<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
										<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
										<html:option value="<%=(String)fieldValue%>">
											<%=(String)fieldDescription%>
										</html:option>
									</logic:present>
								</logic:iterate>
							</logic:equal>
						</logic:iterate>
					</logic:present>
					</ihtml:select>
				</div>
			</div>
			<div class="ic-row">
				<div class="ic-input ic-mandatory ic-split-40  ic-label-45">
				<label><common:message bundle="maintainuldstock" key="uld.defaults.column3" /></label>
					<ihtml:text componentID="CMP_ULD_DEFAULTS_STOCK_MAINTAINULDSTOCK_ULDTYPE_DTL" property="uldTypeCode" name="maintainULDStockForm" style="text-transform : uppercase" maxlength="4"/>
					<img name="uldlovImg" id="uldlovImg"" src="<%=request.getContextPath()%>/images/lov.gif" width="18" height="18" alt="ULD Type LOV"/>
				</div>
				<div class="ic-input ic-split-30  ic-label-30">
				<label><common:message bundle="maintainuldstock" key="uld.defaults.column5" /></label>
					<ihtml:text componentID="CMP_ULD_DEFAULTS_STOCK_MAINTAINULDSTOCK_MINQTY_DTL" style="text-align:right" property="minimumQty" name="maintainULDStockForm" maxlength="5" />
				</div>
				<div class="ic-input ic-split-30  ic-label-42">
				<label><common:message bundle="maintainuldstock" key="uld.defaults.column4" /></label>
					<ihtml:text componentID="CMP_ULD_DEFAULTS_STOCK_MAINTAINULDSTOCK_MAXQTY_DTL" style="text-align:right" property="maximumQty" name="maintainULDStockForm" maxlength="5" />
				</div>
			</div>
			<div class="ic-row">
				<div class="ic-input ic-split-40  ic-label-45" id="uldGroupCodeDiv">	<!-- Modified the id by A-7359 for ICRD-290063 -->
				<label><common:message bundle="maintainuldstock" key="uld.defaults.uldGroupCode" />	</label>
					<ihtml:text componentID="CMP_ULD_DEFAULTS_STOCK_MAINTAINULDSTOCK_ULDGRP_DTL" property="uldGroupCode" name="maintainULDStockForm" maxlength="6" disabled="true" />
					
				</div>
				<div class="ic-input ic-split-30  ic-label-40">
				<label> <common:message bundle="maintainuldstock" key="uld.defaults.dwellTime" />	</label>
					<ihtml:text componentID="CMP_ULD_DEFAULTS_STOCK_MAINTAINULDSTOCK_DWLTIM_DTL" property="dwellTime" name="maintainULDStockForm" maxlength="3" />
				</div>
			</div>
			
			<div class="ic-row">
				<div class="ic-input ic-split-95">
				<label><common:message bundle="maintainuldstock" key="uld.defaults.remarks" /></label>
					<ihtml:textarea property="remarks" componentID="CMP_ULD_DEFAULTS_STOCK_MAINTAINULDSTOCK_REMARK"  rows="2" cols="50" maxlength="100"/>
				</div>
				
			</div>
			</div>
			</div>
			
		</div>
		<div class="ic-foot-container">
			<div class="ic-row">
				<div class="ic-button-container">
					<ihtml:nbutton property="btSave" componentID="CMP_ULD_DEFAULTS_STOCK_MAINTAINULDSTOCK_OK_BTN">
                       <common:message bundle="maintainuldstock" key="uld.defaults.btn.btsave" />
                    </ihtml:nbutton>
                    <input name="btCancel" type="button" value="Close" title = "Close"  class="iCargoButtonSmall" onclick="window.close()"/>
				</div>
			</div>
		</div>
	</div>

</ihtml:form>
</div>

 
</body>

</html:html>

