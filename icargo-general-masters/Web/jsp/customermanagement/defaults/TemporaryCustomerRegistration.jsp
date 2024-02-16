<%--
/***********************************************************************
* Project       	 :  iCargo
* Module Code & Name :  Customer Management
* File Name     	 :  TemporaryCustomerRegistration.jsp.
* Date          	 :  10-Apr-2006
* Author(s)     	 :  Dhanya.T
*************************************************************************/
 --%>

<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import = "com.ibsplc.icargo.framework.util.FormatConverter" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainTempCustomerForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="java.util.ArrayList"%>
<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>
<html:html locale="true">

<head>
		
<title>
<common:message bundle="maintaintempcustomerform" key="cm.defaults.scrntitle.temporarycustomerregistration" />
</title>

<meta name="decorator" content="mainpanelrestyledui">
<common:include type="script" src="/js/customermanagement/defaults/TemporaryCustomerRegistration_Script.jsp"/>
</head>


<body style="width:100%;" class="ic-center">
	
		

	<business:sessionBean id="KEY_TEMPCUSTOMERREG"
			   moduleName="customermanagement.defaults"
			   screenID="customermanagement.defaults.listtempcustomerform"
			   method="get"
			   attribute="tempCustomerDetails"/>


	<business:sessionBean id="KEY_LISTTEMPCUSTREG"
			   moduleName="customermanagement.defaults"
			   screenID="customermanagement.defaults.listtempcustomerform"
			   method="get"
		   	   attribute="listtempcustomerregistration"/>

    <business:sessionBean id="KEY_TEMPID"
				moduleName="customermanagement.defaults"
				screenID="customermanagement.defaults.listtempcustomerform"
				method="get"
				attribute="tempIDs" />




	<bean:define id="form" name="MaintainTempCustomerForm"  type="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainTempCustomerForm" toScope="page" />
	<div id="mainDiv" class="iCargoContent ic-masterbg" style="width:100%;overflow:auto;height:100%" >
	<ihtml:form action="/customermanagement.defaults.screenloadtemporarycustomerregistration.do">


	<ihtml:hidden property="pageURL"/>
	<ihtml:hidden property="closeFlag"/>
    <ihtml:hidden property="detailsFlag"/>
    <ihtml:hidden property="saveStatus"/>
	<ihtml:hidden property="operationMode"/>

	<ihtml:hidden property="custCodeFlag"/>

<logic:present name="KEY_TEMPID">
			
<bean:define id="KEY_TEMPID" name="KEY_TEMPID"/>
	<common:popuppaginationtag
		pageURL="/customermanagement.defaults.charter.navigatetempcustreg.do"

		linkStyleClass="iCargoLink"
		disabledLinkStyleClass="iCargoLink"
		displayPage="<%=((MaintainTempCustomerForm)form).getDisplayPage()%>"
		totalRecords="<%=String.valueOf(((ArrayList)KEY_TEMPID).size())%>" />
</logic:present>

<logic:notPresent name="KEY_TEMPID">
</logic:notPresent>
<div class="ic-content-main">
	<span class="ic-page-title ic-display-none">
		<common:message bundle="maintaintempcustomerform" key="cm.defaults.pgtitle.temporarycustomerregistration" />
	</span>
	<div class="ic-head-container">	
		<div class="ic-filter-panel">
			<div class="ic-row">
				<div class="ic-input ic-mandatory ic-split-75">
					<label>
						<common:message bundle="maintaintempcustomerform" key="cm.defaults.temporarycustomerregistration.lbl.tempid" />
					</label>
					<ihtml:text maxlength="12" componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_TEMPID" property="tempId"  name="MaintainTempCustomerForm" style="text-transform : uppercase" tabindex="1"/>
                    <div class="lovImg">
					<img id="tempidLOV" src="<%=request.getContextPath()%>/images/lov.png" height="22" width="22" />
				    </div>
				</div>
				<div class="ic-input ic-split-25">
					<div class="ic-button-container">
						<ihtml:nbutton property="btList" accesskey="L" componentID="CMP_CM_DEFAULTS_CM_LIST_BTN" tabindex="2">
						 <common:message bundle="listtempcustomerform" key="cm.defaults.btn.btlist" />
						</ihtml:nbutton>

						<ihtml:nbutton accesskey="C" property="btClear" componentID="CMP_CM_DEFAULTS_CM_CLEAR_BTN" tabindex="3">
						 <common:message bundle="listtempcustomerform" key="cm.defaults.btn.btclear" />
						</ihtml:nbutton>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="ic-main-container">
		<div class="ic-row">
			<h4><common:message bundle="maintaintempcustomerform" key="cm.defaults.lbl.customerdetails" /></h4>
		</div>
		<div class="ic-row">
			<div class="ic-input-container ic-input-round-border" id="customerDetails">
			<div class="ic-row">
				<div class="ic-col-30 ic-label-35">
					<div class="ic-input ic-mandatory ic-split-100">
						<label>
							<common:message bundle="maintaintempcustomerform" key="cm.defaults.temporarycustomerregistration.customername" />
						</label>
						<logic:present name="KEY_TEMPCUSTOMERREG" property="tempCustName">
							 <bean:define id="tempCustName" name="KEY_TEMPCUSTOMERREG" property="tempCustName"/>
							 <ihtml:text maxlength="50" componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_CUSTOMERNAME" property="customerName" value="<%=(String)tempCustName%>" tabindex="4"/>
						</logic:present>

						<logic:notPresent name="KEY_TEMPCUSTOMERREG" property="tempCustName">
							 <ihtml:text maxlength="50" componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_CUSTOMERNAME" property="customerName" value="" tabindex="4" />
						</logic:notPresent>
					</div>
					<div class="ic-input ic-split-100">
						<label>
							<common:message bundle="maintaintempcustomerform" key="cm.defaults.temporarycustomerregistration.addressone" />
						</label>
						<logic:present name="KEY_TEMPCUSTOMERREG" property="address">
							 <bean:define id="address" name="KEY_TEMPCUSTOMERREG" property="address"/>
							 <ihtml:text maxlength="100" componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_ADDRESSONE" property="address" value="<%=(String)address%>" style="width:50%;" tabindex="8"/>
						 </logic:present>

						 <logic:notPresent name="KEY_TEMPCUSTOMERREG" property="address">
							 <ihtml:text maxlength="100" componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_ADDRESSONE" property="address" value="" style="width:50%;" tabindex="8"/>
						 </logic:notPresent>
					</div>
					<div class="ic-input ic-split-100">
						<label>
							<common:message bundle="maintaintempcustomerform" key="cm.defaults.temporarycustomerregistration.country" />
						</label>
						<logic:present name="KEY_TEMPCUSTOMERREG" property="countryCode">
							 <bean:define id="countryCode" name="KEY_TEMPCUSTOMERREG" property="countryCode"/>
							 <ihtml:text maxlength="50" componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_COUNTRY" property="countryCode" value="<%=(String)countryCode%>" tabindex="12" />
						 </logic:present>

						 <logic:notPresent name="KEY_TEMPCUSTOMERREG" property="countryCode">
							 <ihtml:text maxlength="50" componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_COUNTRY" property="countryCode" value="" tabindex="12" />
						 </logic:notPresent>
						 <div class="lovImg">
						 <img id="countrylov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" />
					     </div>
					</div>
					<div class="ic-input ic-split-100">
						<label>
							<common:message bundle="maintaintempcustomerform" key="cm.defaults.temporarycustomerregistration.emailid" />
						</label>
						<logic:present name="KEY_TEMPCUSTOMERREG" property="emailAddress">
							 <bean:define id="emailAddress" name="KEY_TEMPCUSTOMERREG" property="emailAddress"/>
							 <ihtml:text maxlength="30" componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_EMAILID" property="emailId" value="<%=(String)emailAddress%>"  tabindex="16"/>
						 </logic:present>

						 <logic:notPresent name="KEY_TEMPCUSTOMERREG" property="emailAddress">
							 <ihtml:text maxlength="30" componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_EMAILID" property="emailId" value="" tabindex="16"/>
						 </logic:notPresent>
					</div>					
				</div>
				<div class="ic-col-30 ic-label-25">
					<div class="ic-input ic-mandatory ic-split-100">
						<label>
							<common:message bundle="maintaintempcustomerform" key="cm.defaults.temporarycustomerregistration.station" />
						</label>
						 <logic:present name="KEY_TEMPCUSTOMERREG" property="station">
							 <bean:define id="station" name="KEY_TEMPCUSTOMERREG" property="station"/>
							 <ihtml:text  componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_STATION" property="station" value="<%=(String)station%>" tabindex="5"/>
						 </logic:present>

						 <logic:notPresent name="KEY_TEMPCUSTOMERREG" property="station">
							 <ihtml:text componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_STATION" property="station" value="" tabindex="5"/>
						 </logic:notPresent>
						 <div class="lovImg">
						 <img id="stationlov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" />
					     </div>
					</div>
					<div class="ic-input ic-split-100">
						<label>
							<common:message bundle="maintaintempcustomerform" key="cm.defaults.temporarycustomerregistration.addresstwo" />
						</label>
						<logic:present name="KEY_TEMPCUSTOMERREG" property="addressTwo">
						<bean:define id="addressTwo" name="KEY_TEMPCUSTOMERREG" property="addressTwo"/>
						<ihtml:text maxlength="100" componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_ADDRESSTWO" property="addressTwo" value="<%=(String)addressTwo%>" style="width:70%;" tabindex="9"/>
						</logic:present>
						
						<logic:notPresent name="KEY_TEMPCUSTOMERREG" property="addressTwo">
						<ihtml:text maxlength="100" componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_ADDRESSTWO" property="addressTwo" value="" style="width:70%;" tabindex="9"/>
						</logic:notPresent>							
					</div>
					<div class="ic-input ic-split-100">
						<label>
							<common:message bundle="maintaintempcustomerform" key="cm.defaults.temporarycustomerregistration.zipcode" />
						</label>
						<logic:present name="KEY_TEMPCUSTOMERREG" property="zipCode">
							 <bean:define id="zipCode" name="KEY_TEMPCUSTOMERREG" property="zipCode"/>
							 <ihtml:text componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_ZIPCODE" property="zipCode" value="<%=(String)zipCode%>" />
						 </logic:present>

						 <logic:notPresent name="KEY_TEMPCUSTOMERREG" property="zipCode">
							 <ihtml:text componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_ZIPCODE" property="zipCode" value="" />
						 </logic:notPresent>												
					</div>
					<div class="ic-input ic-split-100">
						<label>
							<common:message bundle="maintaintempcustomerform" key="cap.defaults.temporarycustomerregistration.lbl.remark" />
						</label>
						<logic:present name="KEY_TEMPCUSTOMERREG" property="remarks">
							 <bean:define id="remarks" name="KEY_TEMPCUSTOMERREG" property="remarks"/>
							 <ihtml:textarea  property="remark" rows="3" cols="65" style="width:70%;"
							 onblur="validateMaxLength(this,95);"
							 name="MaintainTempCustomerForm" value="<%=(String)remarks%>"
							 componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_REMARKS" tabindex="17"/>
						</logic:present>

						<logic:notPresent name="KEY_TEMPCUSTOMERREG" property="remarks">
							 <ihtml:textarea  property="remark" rows="3" cols="65" style="width:70%;"
							 componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_REMARKS"
							 onblur="validateMaxLength(this,95);" name="MaintainTempCustomerForm" value="" tabindex="17" />
						</logic:notPresent>																
					</div>					
				</div>
				<div class="ic-col-25 ic-label-25">
					<div class="ic-input ic-split-100">
						<label>
							<common:message bundle="maintaintempcustomerform" key="cm.defaults.temporarycustomerregistration.phoneNumber" />
						</label>
						<logic:present name="KEY_TEMPCUSTOMERREG" property="phoneNumber">
							 <bean:define id="phoneNumber" name="KEY_TEMPCUSTOMERREG" property="phoneNumber"/>
							 <ihtml:text style="text-align:right" maxlength="25" componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_PHONENO" property="phoneNo" value="<%=(String)phoneNumber%>" tabindex="6"/>
						 </logic:present>

						 <logic:notPresent name="KEY_TEMPCUSTOMERREG" property="phoneNumber">
							 <ihtml:text style="text-align:right" maxlength="25" componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_PHONENO" property="phoneNo" value="" tabindex="6" />
						 </logic:notPresent>
					</div>
					<div class="ic-input ic-split-100">
						<label>
							<common:message bundle="maintaintempcustomerform" key="cm.defaults.temporarycustomerregistration.fax" />
						</label>
						<logic:present name="KEY_TEMPCUSTOMERREG" property="faxNumber">
							 <bean:define id="faxNumber" name="KEY_TEMPCUSTOMERREG" property="faxNumber"/>
							 <ihtml:text style="text-align:right" maxlength="25" componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_FAX" property="faxNumber" value="<%=(String)faxNumber%>" tabindex="10" />
						 </logic:present>

						 <logic:notPresent name="KEY_TEMPCUSTOMERREG" property="faxNumber">
							 <ihtml:text style="text-align:right" maxlength="25" componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_FAX" property="faxNumber" value="" tabindex="10"/>
						 </logic:notPresent>
					</div>
					<div class="ic-input ic-split-100">
						<label>
							<common:message bundle="maintaintempcustomerform" key="cm.defaults.temporarycustomerregistration.state" />
						</label>
						<logic:present name="KEY_TEMPCUSTOMERREG" property="state">
							 <bean:define id="state" name="KEY_TEMPCUSTOMERREG" property="state"/>
							 <ihtml:text maxlength="30" componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_STATE" property="state" value="<%=(String)state%>" tabindex="14"/>
						 </logic:present>

						 <logic:notPresent name="KEY_TEMPCUSTOMERREG" property="state">
							 <ihtml:text maxlength="30" componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_STATE" property="state" value="" tabindex="14"/>
						 </logic:notPresent>
					</div>
				</div>
				<div class="ic-col-15 ic-label-25">
					<div class="ic-input ic-split-100">
						<label>
							<common:message bundle="maintaintempcustomerform" key="cm.defaults.temporarycustomerregistration.mobile" />
						</label>
						<logic:present name="KEY_TEMPCUSTOMERREG" property="mobileNumber">
							 <bean:define id="mobileNumber" name="KEY_TEMPCUSTOMERREG" property="mobileNumber"/>
							 <ihtml:text style="text-align:right" maxlength="25" componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_MOBILE" property="mobileNumber" value="<%=(String)mobileNumber%>" tabindex="7"/>
						 </logic:present>

						 <logic:notPresent name="KEY_TEMPCUSTOMERREG" property="mobileNumber">
							 <ihtml:text style="text-align:right" maxlength="25" componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_MOBILE" property="mobileNumber" value="" tabindex="7"/>
						 </logic:notPresent>
					</div>
					<div class="ic-input ic-split-100">
						<label>
							<common:message bundle="maintaintempcustomerform" key="cm.defaults.temporarycustomerregistration.city" />
						</label>
						<logic:present name="KEY_TEMPCUSTOMERREG" property="cityCode">
							 <bean:define id="cityCode" name="KEY_TEMPCUSTOMERREG" property="cityCode"/>
							 <ihtml:text maxlength="50" componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_CITY" property="cityCode" value="<%=(String)cityCode%>" tabindex="11"/>
						 </logic:present>

						 <logic:notPresent name="KEY_TEMPCUSTOMERREG" property="cityCode">
							 <ihtml:text maxlength="50" componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_CITY" property="cityCode" value="" tabindex="11"/>
						 </logic:notPresent>
					</div>
					<div class="ic-input ic-split-100 marginT20">
						 <logic:present name="KEY_TEMPCUSTOMERREG" property="activeStatus">
							<bean:define id="activeStatus" name="KEY_TEMPCUSTOMERREG" property="activeStatus" />

							<logic:equal name="activeStatus" value="A" >
								<input type="checkbox" property="activeStatus" title="Active" name="active" checked  title="<common:message bundle="maintaintempcustomerform" key="cm.defaults.temporarycustomerregistration.tooltip.active" /> " tabindex="15"/>
							</logic:equal>
							<logic:notEqual name="activeStatus" value="A">
								<input type="checkbox" property="activeStatus" title="Active" name="active"  title="<common:message bundle="maintaintempcustomerform" key="cm.defaults.temporarycustomerregistration.tooltip.active" />" tabindex="15"/>
							</logic:notEqual>

						</logic:present>
						<logic:notPresent name="KEY_TEMPCUSTOMERREG" property="activeStatus">
							<input type="checkbox" property="activeStatus" title="Active" name="active" checked  tabindex="15" />
						</logic:notPresent>
							
						<label class="ic-inline">
							<bean:message bundle="maintaintempcustomerform" key="cm.defaults.temporarycustomerregistration.lbl.active" />
						</label>							
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	
	<div class="ic-foot-container">
		<div class="ic-row">
			<div class="ic-button-container paddr5">
				<ihtml:nbutton accesskey="R" property="btRequest" componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_REQUEST_BTN" tabindex="18"><bean:message bundle="maintaintempcustomerform" key="cm.defaults.temporarycustomerregistration.lbl.btrequest"   /></ihtml:nbutton>
				<ihtml:nbutton accesskey="O" property="btClose" componentID="CMP_CM_DEFAULTS_CM_TEMPORARYCUSTOMERREGISTRATION_CLOSE_BTN" tabindex="19"><bean:message bundle="maintaintempcustomerform" key="cm.defaults.temporarycustomerregistration.lbl.btclose"  /></ihtml:nbutton>
			</div>
		</div>
	</div>	
</div>
</ihtml:form>
</div>
	
	</body>
</html:html>

