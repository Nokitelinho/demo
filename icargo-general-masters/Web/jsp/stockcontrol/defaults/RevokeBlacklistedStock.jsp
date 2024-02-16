<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  SKCM - stock Control
* File Name				:  RevokeBlacklistedStock.jsp
* Date					:  21-Sep-2005
* Author(s)				:  Kirupakaran
*************************************************************************/
 --%>
<%@ page language="java" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

<html:html>
<head>
<bean:define id="form"
	name="RevokeBlackListedStockForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.RevokeBlackListedStockForm"
	toScope="page" />

<title ><common:message bundle="<%=form.getBundle()%>" key="stockholder.title"/></title>

<meta name="decorator" content="mainpanelrestyledui">
<common:include type="script" src="/js/stockcontrol/defaults/RevokeBlacklistedStock_Script.jsp" />
</head>
<body id="bodyStyle">
	

<div class="iCargoPopUpContent" style="overflow:auto;width:100%;height:100%">
 <ihtml:form action="/stockcontrol.defaults.screenloadrevokeblacklistedstock.do" styleClass="ic-main-form">
 <html:hidden property="revokeSuccessful" />
 <input type="hidden" name="currentDialogId" />
 <input type="hidden" name="currentDialogOption" />

  <html:errors />
	<div class="ic-content-main">
		<span class="ic-page-title ic-display-none">
			<common:message  key="stockholder.RevokeBlackListedStock" />
		</span>	
		<div class="ic-main-container">
			<business:sessionBean id="blacklistStock"
	moduleName="stockcontrol.defaults"
	screenID="stockcontrol.defaults.listblacklistedstock" method="get"
	attribute="blacklistStockVO"/>
			<div class="ic-row">
				<div class="ic-input ic-split-50">
					<label class="ic-split-35">
					<common:message  key="stockholder.DocType" />
					</label>
					<ihtml:text property="docType"
					maxlength="12"
					readonly="true"
					componentID="TXT_STOCKCONTROL_DEFAULTS_REVOKEBLACKLISTEDSTOCK_DOCUMENTTYPE"
					/>
				</div>
				<div class="ic-input ic-split-50">
					<label class="ic-split-30">
					<common:message  key="stockholder.SubType" />
					</label>
					<ihtml:text property="subType"
					maxlength="12"
					readonly="true"
					componentID="TXT_STOCKCONTROL_DEFAULTS_REVOKEBLACKLISTEDSTOCK_DOCUMENTSUBTYPE"
					/>
				</div>
			</div>
			<div class="ic-row">
				<div class="ic-input ic-mandatory ic-split-50">
					<label class="ic-label-15">
					<common:message  key="stockholder.RangeFrom" />
					</label>
					<ihtml:text property="rangeFrom"
					maxlength="11"
					componentID="TXT_STOCKCONTROL_DEFAULTS_REVOKEBLACKLISTEDSTOCK_RANGEFROM"
					tabindex="0"/>
				</div>
				<div class="ic-input ic-mandatory ic-split-50">
					<label class="ic-label-25">
					<common:message  key="stockholder.RangeTo" />
					</label>
					<ihtml:text property="rangeTo"
					maxlength="11"
					componentID="TXT_STOCKCONTROL_DEFAULTS_REVOKEBLACKLISTEDSTOCK_RANGETO"
					tabindex="1"/>
				</div>
			</div>
			<div class="ic-row">
				<div class="ic-input ic-split-100">
					<label class="ic-split-9">
					<common:message  key="stockholder.Remarks" />
					</label>
					<ihtml:textarea property="remarks"
					componentID="TXT_STOCKCONTROL_DEFAULTS_REVOKEBLACKLISTEDSTOCK_REMARKS" cols="50" rows="3"
					/>
				</div>
			</div>
		</div>
		<div class="ic-foot-container">
			<div class="ic-row">
				<div class="ic-button-container">
					<ihtml:nbutton property="btrevoke"
						componentID="BTN_STOCKCONTROL_DEFAULTS_REVOKEBLACKLISTEDSTOCK_REVOKE"
						tabindex="3">
						<common:message key="stockholder.revoke"/>
					</ihtml:nbutton>


					<ihtml:nbutton property="btclose"
						componentID="BTN_STOCKCONTROL_DEFAULTS_REVOKEBLACKLISTEDSTOCK_CLOSE"
						tabindex="4">
						<common:message key="stockholder.close"/>
					</ihtml:nbutton>
				</div>
			</div>
		</div>
	</div>
</ihtml:form>
</div>
<script language="javascript">



</script>
			
	</body>
</html:html>

