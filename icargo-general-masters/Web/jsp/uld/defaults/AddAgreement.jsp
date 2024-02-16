<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  IN - ULD Management
* File Name				:  AddAgreement.jsp
* Date					:  18-Jan-2006
* Author(s)				:  A-2046
*************************************************************************/
 --%>



<%@ include file="/jsp/includes/tlds.jsp" %>

<html:html locale="true">
<head>

<title>Add Agreement</title>

<meta name="decorator" content="popuppanelrestyledui" >

<common:include type="script" src="/js/uld/defaults/AddAgreement_Script.jsp"/>
</head>
<body id="bodyStyle"  >


<bean:define id="form"
	 name="addULDAgreementForm"
	 type=" com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.AddULDAgreementForm"
	 toScope="page" />
<div class="iCargoPopUpContent" style="height:310px;width:620px;">	 
<ihtml:form action="/uld.defaults.screenloadadduldagreement.do" styleclass="ic-main-form">
<ihtml:hidden property="canClose"/>
<ihtml:hidden property="fromDate"/>
<ihtml:hidden property="toDate"/>
<ihtml:hidden property="selectedChecks"/>
<ihtml:hidden property="actionStatus"/>
<ihtml:hidden property="displayPage"/>
<ihtml:hidden property="lastPageNumber"/>
<ihtml:hidden property="totalRecords"/>
<ihtml:hidden property="currentPage"/>
<ihtml:hidden property="sequenceNumber"/>
<ihtml:hidden property="agreementNumber"/>

        

	<div class="ic-content-main">
		
		<div class="ic-main-container">
			<logic:equal name="addULDAgreementForm" property="actionStatus" value="screen_mode_create">
			<div class="ic-row ic-right">
				<a href="#" class="iCargoLink" id="createagreementDetails" >Create</a>
				| <a href="#" class="iCargoLink" id="deleteagreementDetails" >Delete</a>
				||
				<common:popuppaginationtag
				pageURL="javascript:selectNextAgreementDetail('lastPageNumber','displayPage')"
				linkStyleClass="iCargoLink"
				disabledLinkStyleClass="iCargoLink"
				displayPage="<%=form.getDisplayPage()%>"
				totalRecords="<%=form.getTotalRecords()%>" />
			</div>
			</logic:equal>
			
			<logic:equal name="addULDAgreementForm" property="actionStatus" value="screen_mode_modify">
			<div class="ic-row ic-right">
				<common:popuppaginationtag
					pageURL="javascript:selectNextAgreementDetail('lastPageNumber','displayPage')"
					linkStyleClass="iCargoLink"
					disabledLinkStyleClass="iCargoLink"
					displayPage="<%=form.getDisplayPage()%>"
					totalRecords="<%=form.getTotalRecords()%>" />
			</div>
			</logic:equal>
			<div class="ic-row ic-border" style="width:99%;">
			<div class="ic-input-container">
			<div class="ic-row ic-label-50">
				<div class="ic-input ic-mandatory ic-split-50">
				<label><common:message key="uld.defaults.maintainuldagreement.uldtype" scope="request"/></label>
					<ihtml:text property="uldType" componentID="TXT_ULD_DEFAULTS_MAINTAINULDAGRMNT_ULDTYP" style="text-transform:uppercase" maxlength="3"/>
					<div class="lovImg">
                      <img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" id="uldlov" alt="ULD Type LOV"/>
					</div>
				</div>
				<div class="ic-input ic-split-50">
				<label><common:message key="uld.defaults.maintainuldagreement.station" scope="request"/></label>
					<ihtml:text property="station" componentID="TXT_ULD_DEFAULTS_MAINTAINULDAGRMNT_STATION" style="text-transform:uppercase" maxlength="3"  />
					<div class="lovImg">
						<img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" id="stationlov" alt="Airport LOV"/>
					</div>	
				</div>
			</div>
			<div class="ic-row ic-label-50">
				<div class="ic-input ic-mandatory ic-split-50">
				<label><common:message key="uld.defaults.maintainuldagreement.validfrom" scope="request"/></label>
					<ibusiness:calendar property="validFrom"  id="validfrom" type="image"
					  	componentID="ULD_DEFAULTS_ADDULDAGREEMENT_CALENDAR1" value="<%=form.getValidFrom()%>" title = "Start Date"  />
				</div>
				<div class="ic-input ic-split-50">
				<label><common:message key="uld.defaults.maintainuldagreement.validto" scope="request"/></label>
					<ibusiness:calendar property="validTo" id="validto" type="image" componentID="ULD_DEFAULTS_ADDULDAGREEMENT_CALENDAR2"
						value="<%=form.getValidTo()%>" title = "End Date"  />
				</div>
			</div>
			<div class="ic-row ic-label-50">
				<div class="ic-input ic-split-50">
				<label><common:message key="uld.defaults.maintainuldagreement.freeloanperiod" scope="request"/></label>
					<ihtml:text property="freeLoanPeriod" componentID="TXT_ULD_DEFAULTS_MAINTAINULDAGRMNT_FRLNPERIOD" maxlength="10" style="text-align:right"/>
					
				</div>
				<div class="ic-input ic-split-50">
				<label> <common:message key="uld.defaults.maintainuldagreement.demurragefreq" scope="request"/></label>
					<business:sessionBean id="demurrageFrequencies"
					   moduleName="uld.defaults"
						screenID="uld.defaults.maintainuldagreement" method="get"
						attribute="demurrageFrequency"/>

					  <ihtml:select property="demurrageFrequency" componentID="CMB_ULD_DEFAULTS_ADDULDAGREEMENT_DMRGFREQ" style="text-align:right">
					  <logic:present name="demurrageFrequencies">
					  <bean:define id="frequencies" name="demurrageFrequencies"/>
					  <ihtml:options collection="frequencies" property="fieldValue" labelProperty="fieldDescription"/>
					  </logic:present>
					  </ihtml:select>
				</div>
			</div>
			<div class="ic-row ic-label-50">
				<div class="ic-input ic-split-50">
				<label><common:message key="uld.defaults.maintainuldagreement.demurragerate" scope="request"/></label>
					<ihtml:text property="demurrageRate" componentID="TXT_ULD_DEFAULTS_MAINTAINULDAGRMNT_DMGRATE" maxlength="17" style="text-align:right"/>
		            <ihtml:text property="currencyCode" componentID="TXT_ULD_DEFAULTS_ADDULDAGREEMENT_CURRENCY" maxlength="3"/>
					<div class="lovImg">
						<img name="currencyLov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" />
				</div>
				<div class="ic-input ic-split-50">
				<label><common:message key="uld.defaults.maintainuldagreement.tax" scope="request"/></label>
					 <ihtml:text property="taxes"  componentID="TXT_ULD_DEFAULTS_MAINTAINULDAGRMNT_TAXPOP" maxlength="6" style="text-align:right"/> 
				</div>
			</div>
			<div class="ic-row ic-label-25">
				<div class="ic-input ic-split-100">
				<label><common:message key="uld.defaults.maintainuldagreement.remarks" scope="request"/></label>
					<ihtml:textarea property="remarks" rows="3" cols="80" componentID="TXT_ULD_DEFAULTS_ADDULDAGREEMENT_REMARKS"></ihtml:textarea>
				</div>
				
			</div>
			</div>
			</div>
			
		</div>
		
	</div>
	<div class="ic-foot-container">
		<div class="ic-row">
			<div class="ic-button-container">
				<ihtml:nbutton  property="btnOK" componentID="BTN_ULD_DEFAULTS_ADDULDAGREEMENT_OK">
                    <common:message key="uld.defaults.maintainuldagreement.ok" scope="request"/>
                </ihtml:nbutton>
                <ihtml:nbutton property="btnClose" componentID="BTN_ULD_DEFAULTS_ADDULDAGREEMENT_CLOSE">
                    <common:message key="uld.defaults.maintainuldagreement.close" scope="request"/>
                </ihtml:nbutton>
			</div>
		</div>
	</div>

			 
			  </ihtml:form>
			  </div>  

			</body>
			</html:html>




