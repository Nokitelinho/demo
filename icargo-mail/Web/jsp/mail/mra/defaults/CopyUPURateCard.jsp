<%--
/*************************************************************************
* Project	 		: iCargo
* Module Name	    : MRA Mailtracking
* File Name			: CopyUPURateCard.jsp
* Date				: 07/02/2013
* Author(s)			: A-5166
**************************************************************************/
 --%>
 
 <%@ page language="java" %>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListUPURateCardForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
				
	
<html:html>

<head> 
	
	
		
	<title><common:message key="mailtracking.mra.defaults.copyupuratecard.title" bundle="listupuratecardresources" /></title>
	<common:include type="script" src="/js/mail/mra/defaults/CopyUPURateCard_Script.jsp"/>
	<meta name="decorator" content="popuppanelrestyledui">
</head>
<body>
	

<bean:define id="form" name="ListUPURateCardForm" type="ListUPURateCardForm" toScope="page" />

<div class="iCargoPopUpContent" style="height:150px">
<ihtml:form action="mailtracking.mra.defaults.listupuratecard.onCopy.do" enctype="multipart/form-data" styleclass="ic-main-form">
<ihtml:hidden property="rowCount"/>
<ihtml:hidden property="copyFlag"/>
     <div class="ic-content-main">
		<span class="ic-page-title">
		    <common:message key="mailtracking.mra.defaults.copyupuratecard.pagetitle"  />
		</span>
		    <div class="ic-main-container">
					<div class="ic-row">
						<div class="ic-input ic-mandatory ic-label-40 ic-split-50 ">
						    <label>
							    <common:message key="mailtracking.mra.defaults.copyupuratecard.ratecardid"  />
						    </label>
							    <ihtml:text componentID="CMP_MRA_DEFAULTS_COPYUPRATECARD_RATECARDID_TXT" property="rateCardIDNew" maxlength="20" /> 
						</div>
					</div>
					<div class="ic-row">
					    <div class="ic-input ic-mandatory ic-label-40 ic-split-50 ">
						    <label>
							    <common:message key="mailtracking.mra.defaults.copyupuratecard.maildistfactor"  />
							</label>
							    <ihtml:text componentID="CMP_MRA_DEFAULTS_COPYUPRATECARD_MAILDISTFACTOR_TXT" property="mailDistFactor" maxlength="25" />
						</div>
						<div class="ic-input ic-mandatory ic-label-40 ic-split-50 ">
						    <label>
							    <common:message key="mailtracking.mra.defaults.copyupuratecard.airmailtkm"  />
							</label>
							    <ihtml:text componentID="CMP_MRA_DEFAULTS_COPYUPRATECARD_AIRMAILTKM_TXT" property="airmialTkm" maxlength="25" />
						</div>
					</div>
					<div class="ic-row">
                        <div class="ic-input ic-mandatory ic-label-40 ic-split-50 ">
						    <label>
							    <common:message key="mailtracking.mra.defaults.copyupuratecard.saltkm"  />
							</label>
							    <ihtml:text componentID="CMP_MRA_DEFAULTS_COPYUPRATECARD_SALTKM_TXT" property="salTkm" maxlength="25" />
						</div>
						<div class="ic-input ic-mandatory ic-label-40 ic-split-50 ">
						    <label>
							    <common:message key="mailtracking.mra.defaults.copyupuratecard.svtkm.tooltip"  />
							</label>
							    <ihtml:text componentID="CMP_MRA_DEFAULTS_COPYUPRATECARD_SVTKM_TXT" property="svTkm" maxlength="25" />
						</div>
					</div>
					<div class="ic-row">
						<div class="ic-input ic-mandatory ic-label-40 ic-split-50 ">
						    <label>
							    <common:message key="mailtracking.mra.defaults.copyupuratecard.validfrom" />
							</label>
							    <ibusiness:calendar componentID="CMP_MRA_DEFAULTS_COPYUPRATECARD_VALIDFROM_CALENDAR" id="calValidFrom" property="validFrom" type="image"/>
						</div>	
						<div class="ic-input ic-mandatory ic-label-40  ic-split-50 ">
						    <label>
							    <common:message key="mailtracking.mra.defaults.copyupuratecard.validto" />
							</label>
							    <ibusiness:calendar componentID="CMP_MRA_DEFAULTS_COPYUPRATECARD_VALIDTO_CALENDAR" id="calValidTo" property="validTo" type="image"/>
						</div>
				    </div>
			</div>
	<div class="ic-foot-container paddR5">
	    <div class="ic-button-container">
		    <ihtml:nbutton componentID="CMP_MRA_DEFAULTS_COPYUPRATECARD_OK_BUTTON" property="btOk" accesskey="O" >
			<common:message key="mailtracking.mra.defaults.copyupuratecard.btok"  />
			</ihtml:nbutton>
			
			<ihtml:nbutton componentID="CMP_MRA_DEFAULTS_COPYUPRATECARD_CLOSE_BUTTON" property="btClose" accesskey="C" >
			<common:message key="mailtracking.mra.defaults.copyupuratecard.btclose"  />
			</ihtml:nbutton>
		</div>
	</div>
</div>
</ihtml:form>
</div>
				
		
	</body>
</html:html>
