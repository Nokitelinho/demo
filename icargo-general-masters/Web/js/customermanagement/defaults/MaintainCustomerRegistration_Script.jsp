<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@include file="/jsp/includes/tlds.jsp"%>
function screenSpecificEventRegister(){

	onLoad();
	var frm=targetFormName;

	/*Added by A-2390 for register customer link from reservation screen starts*/
		closePopUpAction();
	/*ends*/
	with(frm){


		evtHandler.addIDEvents("agentcodelov","lovScreen(this)",EVT_CLICK);


	evtHandler.addIDEvents("stationlov","displayLOV('showStation.do','N','Y','showStation.do',targetFormName.elements.station.value,'station','1','station','',0)",EVT_CLICK);
	evtHandler.addIDEvents("citylov","displayLOV('showCity.do','N','Y','showCity.do',targetFormName.elements.city.value,'city','1','city','',0)",EVT_CLICK);
	evtHandler.addIDEvents("countrylov","displayLOV('showCountry.do','N','Y','showCountry.do',targetFormName.elements.country.value,'country','1','country','',0)",EVT_CLICK);

	evtHandler.addIDEvents("airportlov","displayAirport('airportlov',this)",EVT_CLICK);
	evtHandler.addIDEvents("addcontact","addContact()",EVT_CLICK);
	evtHandler.addIDEvents("addclearingagent","addClearingAgent()",EVT_CLICK);
	evtHandler.addIDEvents("deletecontact","deleteContactDetails()",EVT_CLICK);
	evtHandler.addIDEvents("deleteclearingagent","deleteClearingAgent()",EVT_CLICK);
	evtHandler.addEvents("btnSave","registerCustomer()",EVT_CLICK);
	evtHandler.addEvents("btnClear","clearCustomerRegn()",EVT_CLICK);

	evtHandler.addEvents("fax","validateFields(targetFormName.elements.fax,-1,'Fax Number',13,'Invalid Fax Number',true);onBlurCopyBillingDetails('fax')",EVT_BLUR);
	evtHandler.addEvents("telephone","validateFields(targetFormName.elements.telephone,-1,'Telephone Number',13,'Invalid Telephone Number',true);onBlurCopyBillingDetails('telephone')",EVT_BLUR);
	evtHandler.addEvents("mobile","validateFields(targetFormName.elements.mobile,-1,'Mobile Number',13,'Invalid Mobile Number',true);onBlurCopyBillingDetails('mobile')",EVT_BLUR);
	/* Commented by A-7137 as part of CR ICRD-135495
	evtHandler.addEvents("zipCode","validateZipCode(targetFormName,this,'Invalid ZipCode Format')",EVT_BLUR); */
	//UnCommented by A-5222 bug of ICRD-43817
	evtHandler.addEvents("email","validateEmailFormat()",EVT_BLUR);
	evtHandler.addEvents("station","validateAlphanumeric(targetFormName.elements.station,'Station','Invalid Station',true)",EVT_BLUR);
	evtHandler.addEvents("customerShortCode","validateAlphanumeric(targetFormName.elements.customerShortCode,'Customer Short Code','Invalid Customer Short Code',true)",EVT_BLUR);
	evtHandler.addEvents("contactTelephone","validateTelephone(targetFormName,this)",EVT_BLUR);
	evtHandler.addEvents("contactMobile","validateMobile(targetFormName,this)",EVT_BLUR);
	evtHandler.addEvents("contactFax","validateFax(targetFormName,this)",EVT_BLUR);
	//UnCommented by A-5222 bug of ICRD-43817
	evtHandler.addEvents("contactEmail","validateEmailTable(targetFormName,this)",EVT_BLUR);
	evtHandler.addEvents("btnList","listCustomer()",EVT_CLICK);
	evtHandler.addIDEvents("customerlov","showTempIDLOV()",EVT_CLICK);
	evtHandler.addIDEvents("holdingCompanylov","showHoldingCompanyLOV()",EVT_CLICK);
	evtHandler.addEvents("btnClose","onClickClose()",EVT_CLICK);
	evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);
	evtHandler.addEvents("address1","onBlurCopyBillingDetails('address1')",EVT_BLUR);
	evtHandler.addEvents("address2","onBlurCopyBillingDetails('address2')",EVT_BLUR);
	evtHandler.addEvents("state","onBlurCopyBillingDetails('state')",EVT_BLUR);
	evtHandler.addEvents("city","onBlurCopyBillingDetails('city')",EVT_BLUR);
	evtHandler.addEvents("email","onBlurCopyBillingDetails('email')",EVT_BLUR);
	//Added by A-7604 for s7 specific cr  ICRD-233132
	evtHandler.addIDEvents("groupnamecustlov","groupLovDisplay(this)",EVT_CLICK);
	evtHandler.addIDEvents("groupcodelov","displayLOV('showCustomerGroup.do','N','Y','showCustomerGroup.do',targetFormName.elements.customerGroup.value,'customerGroup','1','customerGroup','',0)",EVT_CLICK);
	evtHandler.addEvents("customerGroup","validateAlphanumeric(targetFormName.elements.customerGroup,'Customer Group','Invalid Customer group',true)",EVT_BLUR);
	evtHandler.addEvents("customerCode","validateAlphanumeric(targetFormName.elements.customerCode,'Customer Code','Invalid Customer Code',true)",EVT_BLUR);
	//evtHandler.addEvents("custName","validateAlphanumericAndSpace(targetFormName.elements.custName,'Customer Name','Invalid Customer Name',true)",EVT_BLUR);
	evtHandler.addEvents("zone","validateAlphanumericAndSpace(targetFormName.elements.zone,'Zone','Invalid Zone',true)",EVT_BLUR);
	//evtHandler.addEvents("area","validateAlphanumericAndSpace(targetFormName.elements.area,'Area','Invalid Area',true)",EVT_BLUR);
	evtHandler.addEvents("country","validateSpaceAndAlpha(targetFormName.elements.country,'Country Name','Invalid Country',true);onBlurCopyBillingDetails('country')",EVT_BLUR);
	evtHandler.addEvents("sita","validateAlphanumericAndSpace(targetFormName.elements.sita,'SITA','Invalid SITA',true)",EVT_BLUR);
	//evtHandler.addEvents("city","validateAlphanumeric(targetFormName.elements.city,'City','Invalid City',true)",EVT_BLUR);
	//Added By Manikandan On 01/02/07  for Bug fix	for making station code and Agent code Editable	 in Agent Tab
	evtHandler.addEvents("agentStation","validateFields(this,-1,'Station',1,true,true)",EVT_BLUR);
	evtHandler.addEvents("agentCode","validateFields(this,-1,'agentCode',0,true,true)",EVT_BLUR);var calendarArr=document.getElementsByName('ccsfExpiryDate_BTN');
	//Added By A-5169 for ICRD-31552 on 30-APR-2013 starts//commenting for ICRD-175093 change
	//evtHandler.addEvents("customsLocationNo","validateInt(targetFormName.elements.customsLocationNo,'Customs Location Number','Invalid number for Customs Location Number',true),addPrefixValues()",EVT_BLUR);
	//Added By A-5169 for ICRD-31552 on 30-APR-2013 ends
	evtHandler.addEvents("zipCode","populateZipCodeDetails();onBlurCopyBillingDetails('zipCode')",EVT_BLUR);
	//Added by a-3045 for CR HA16 on 03Jun09 starts
	//Commented for ICRD-58628 by A-5163 starts
	//evtHandler.addIDEvents("addccsf","addCCSFDetails()",EVT_CLICK);
	//evtHandler.addIDEvents("deleteccsf","deleteCCSFDetails()",EVT_CLICK);	
	//evtHandler.addIDEvents("knownShipperCheck","enableEstablishedDate()",EVT_CLICK);
	//Commented for ICRD-58628 by A-5163 ends
	//Added for ICRD-58628 by A-5163 starts
	evtHandler.addIDEvents("addCertifications","addCertificationDetails()",EVT_CLICK);
	evtHandler.addIDEvents("deleteCertifications","deleteCertificationDetails()",EVT_CLICK);
	//Added for ICRD-58628 by A-5163 ends
	evtHandler.addEvents("billingFax","validateFields(targetFormName.elements.billingFax,-1,'Fax Number',13,'Invalid Billing Fax Number',true)",EVT_BLUR);
	evtHandler.addEvents("billingTelephone","validateFields(targetFormName.elements.billingTelephone,-1,'Telephone Number',13,'Invalid Billing Telephone Number',true)",EVT_BLUR);
	evtHandler.addEvents("billingZipcode","validateZipCode(targetFormName,this,'Invalid Billing ZipCode Format')",EVT_BLUR);
	//Commented by A-7550 as part of ICRD-221625 
	//evtHandler.addEvents("billingStreet","validateFields(targetFormName.elements.billingStreet,-1,'Billing Street',8,'Invalid Billing Street',true)",EVT_BLUR);
	//commented for ICRD-354415
	//evtHandler.addEvents("billingLocation","validateFields(targetFormName.elements.billingLocation,-1,'Billing Location',8,'Invalid Billing Location',true)",EVT_BLUR);
	//evtHandler.addEvents("billingCityCode","validateFields(targetFormName.elements.billingCityCode,-1,'Billing City',1,'Invalid Billing City',true)",EVT_BLUR);
	evtHandler.addEvents("billingState","validateFields(targetFormName.elements.billingState,-1,'Billing State',1,'Invalid Billing State',true)",EVT_BLUR);
	evtHandler.addEvents("billingCountry","validateFields(targetFormName.elements.billingCountry,-1,'Billing Country',1,'Invalid Billing Country',true)",EVT_BLUR);
	evtHandler.addEvents("billingEmail","validateBillingEmailFormat()",EVT_BLUR);
	//Added by A-7905 as part of ICRD-228463 starts
	evtHandler.addEvents("billingEmailOne","validateBillingEmailFormat()",EVT_BLUR);
	evtHandler.addEvents("billingEmailTwo","validateBillingEmailFormat()",EVT_BLUR);
	//Added by A-7905 as part of ICRD-228463 ends
	//Commented by a-3045 for bug53468 on 24Jul09
	//evtHandler.addEvents("remarks","validateFields(targetFormName.elements.remarks,-1,'Remarks',8,'Invalid Remarks',true)",EVT_BLUR);
	//Commented by a-3045 for bug53474 on 24Jul09
	//evtHandler.addEvents("iacNumber","validateFields(targetFormName.elements.iacNumber,-1,'IAC Number',10,'Invalid IAC Number',true)",EVT_BLUR);
	//evtHandler.addEvents("apiacsspNumber","validateFields(targetFormName.elements.apiacsspNumber,-1,'AP-IACSSP Number',10,'Invalid AP-IACSSP Number',true)",EVT_BLUR);
	evtHandler.addIDEvents("billingcitylov","displayLOV('showCity.do','N','Y','showCity.do',targetFormName.elements.billingCityCode.value,'billingCityCode','1','billingCityCode','',0)",EVT_CLICK);
	evtHandler.addIDEvents("billingcountrylov","displayLOV('showCountry.do','N','Y','showCountry.do',targetFormName.elements.billingCountry.value,'billingCountry','1','billingCountry','',0)",EVT_CLICK);
	//Added by a-3045 for CR HA16 on 03Jun09 ends
	//added by a-3278 for CR JetBlue31-1 on 23Apr10
	evtHandler.addIDEvents("billingPeriodLov","showBillingPeriodLov()",EVT_CLICK);
	evtHandler.addIDEvents("agentsccLOV","displayAgentSCC('agentsccLOV',this)",EVT_CLICK);
	//Added by A-8823 for IASCB-4841 beg
	evtHandler.addIDEvents("displayAgentOrigin","displayAgentOrigin('agentoriginLOV',this)",EVT_CLICK);
	evtHandler.addIDEvents("agentCarrierLOV","displayAgentCarrier('agentCarrierLOV',this)",EVT_CLICK);
	//Added by A-8823 for IASCB-4841 ends
	
	//JB31-1 ends
	<%-- ICRD - 1695 A-4789 06 Oct changes starts here --%>
	evtHandler.addEvents("defaultHawbLength","validateInt(targetFormName.elements.defaultHawbLength,'Default HAWB length','Invalid number for Default HAWB length',true)",EVT_BLUR);
	evtHandler.addEvents("billingCode","validateAlphanumeric(targetFormName.elements.billingCode,'Billing Code','Invalid Billing Code',true)",EVT_BLUR);
	<%-- ICRD - 1695 A-4789 06 Oct changes ends here --%>
	
		
	evtHandler.addEvents("cassBillingIndicator","cassValidation()",EVT_CLICK);
	evtHandler.addEvents("billingThroughInterline","billingThroughInterlineValidation()",EVT_CLICK);
	
	
	evtHandler.addEvents("controllingLocation","uncheckField('CONLOC')",EVT_CLICK);
	evtHandler.addEvents("sellingLocation","uncheckField('SELLOC')",EVT_CLICK);
	
	evtHandler.addIDEvents("customerlovBilling","displayCustomerLOV()",EVT_CLICK);
	evtHandler.addEvents("iataAgentCode","restrictAlphaNumeric_Hyphen(this)",EVT_KEYPRESS);
	evtHandler.addIDEvents("billingPeriodLov","showBillingPeriodLov()",EVT_CLICK);
	//Modify by A-5222 bug of ICRD-134510
	evtHandler.addIDEvents("airportCodelov","displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].elements.airportCode.value,'Airport Code','1','airportCode','',0)",EVT_CLICK);
	//evtHandler.addEvents("iatalov","showIATABillingLOV(targetFormName.elements.iataCode.value)",EVT_CLICK);
	evtHandler.addIDEvents("airlineCodeLOV","displayLOV('showAirline.do','N','Y','showAirline.do',document.forms[1].elements.airlineCode.value,'Airline Code','1','airlineCode','',0)",EVT_CLICK);
	evtHandler.addIDEvents("billingCurrencyLOV","displayLOV('showCurrency.do','N','Y','showCurrency.do',targetFormName.elements.billingCode.value,'Currency','1','billingCode','',0)",EVT_CLICK);
	evtHandler.addIDEvents("settlecurrencyLov","displayLOV('showCurrency.do','N','Y','showCurrency.do',targetFormName.elements.settlementCurrency.value,'SettlementCurrency','1','settlementCurrency','',0)",EVT_CLICK);
	evtHandler.addIDEvents("AddMiscDetails","addNotes()",EVT_CLICK);
    evtHandler.addIDEvents("DeleteMiscDetails","deleteNotes()",EVT_CLICK);
	//Added by A-5169 for ICRD-31552 on 02-MAY-2013 
	evtHandler.addEvents("customsLocationNo","prefixcutomsLocationNo()",EVT_BLUR);
	
	//Added by A-5791 for ICRD-59602
	evtHandler.addEvents("agentCode","agentNameonBlur(this)",EVT_BLUR);
	evtHandler.addIDEvents("recipientCode","CheckSpace(this);validateRecipientCode(targetFormName,this)",EVT_BLUR);
	evtHandler.addIDEvents("recipientCode","CheckSpace(this)",EVT_KEYPRESS);
	//Added by A-6841 for ICRD-152555
		//evtHandler.addIDEvents("notificationPreferences","displayNotificationPreferences(this)",EVT_CLICK);
    	//evtHandler.addIDEvents("additionalContacts","displayAdditionalContacts(this)",EVT_CLICK);
		evtHandler.addEvents("contactTypes","onChangeContactTypes(this)",EVT_CHANGE);
	//Added to fix ICRD-206390
	 evtHandler.addEvents("custName","validateSpecialCharacters(this)",EVT_BLUR);
	 evtHandler.addEvents("address1","validateSpecialCharacters(this)",EVT_BLUR);
	 evtHandler.addEvents("address2","validateSpecialCharacters(this)",EVT_BLUR);
	 evtHandler.addEvents("city","validateSpecialCharacters(this)",EVT_BLUR);
	 evtHandler.addEvents("state","validateSpecialCharacters(this)",EVT_BLUR);
     evtHandler.addEvents("remarks","validateSpecialCharacters(this)",EVT_BLUR);
	 //evtHandler.addEvents("customerType","checkCustomerTypeForKE(this)",EVT_BLUR);
	 //evtHandler.addEvents("agentRemarks","validateSpecialCharacters(this)",EVT_BLUR);
	
	 //ends
	 //Added as part of CR ICRD-253447 by A-8154
	 evtHandler.addEvents("btnActivate","onClickActivate()",EVT_CLICK);
	 evtHandler.addEvents("btnGroupList","onClickGroupList()",EVT_CLICK);//Added for IASCB-130291

	}
	
	disableMDMMasterDataForKE();
	/*by A-7567 for ICRD-305684*/
	evtHandler.addEvents("billingTo","validateApplicableToField()",EVT_CHANGE);
	
	/*Added by 201930 for IASCB-131790 start*/
	evtHandler.addEvents("cassCountry","validateFields(targetFormName.elements.cassCountry,-1,'Cass File Country',1,'Invalid Cass File Country',true)",EVT_BLUR);
	evtHandler.addIDEvents("casscountrylov","displayLOV('showCountry.do','N','Y','showCountry.do',targetFormName.elements.cassCountry.value,'cassCountry','1','cassCountry','',0)",EVT_CLICK);
	/*Added by 201930 for IASCB-131790 end*/
}
//Added by A-6841 for ICRD-152555
function displayNotificationPreferences(_index){
	var contactTypesArray = document.getElementsByName('contactTypes');
	var contactType = "";
	if(targetFormName.elements.contactTypes){
		for(var i=0; i<contactTypesArray.length; i++){
			if(contactTypesArray[i].value != "") {
				contactType = contactType.concat(contactTypesArray[i].value + "-");
			}
		}
	}
	var intex = _index.id.split('notificationPreferences')[1];
	var selectedContactType = targetFormName.elements.contactTypes[intex].value;
	if(selectedContactType != "" && (selectedContactType == "ETR" || selectedContactType == "ETRTOA")) {
		openPopUp("customermanagement.defaults.listNotificationPreferences.do?selectedContactIndex="+intex+"&contactType="+selectedContactType, "550", "450");
	}else if(selectedContactType != "" && (selectedContactType == "EBK" || selectedContactType == "QAE")){//ICRD-162691
		openPopUp("customermanagement.defaults.listNotificationPreferences.do?selectedContactIndex="+intex+"&contactType="+selectedContactType, "350", "250");
	} else if (selectedContactType != "" && selectedContactType == "EFRT") {
		openPopUp("customermanagement.defaults.listNotificationPreferences.do?selectedContactIndex="+intex+"&contactType="+selectedContactType, "550", "450");
	}
}

function displayAdditionalContacts(_index){
	var intex = _index.id.split('additionalContacts')[1];
	var selectedContactType = targetFormName.elements.contactTypes[intex].value;
	if(selectedContactType != "" && (selectedContactType == "ETR" || selectedContactType == "ETRTOA" || selectedContactType == "EFRT" || selectedContactType == "BTP")) {
		openPopUp("customermanagement.defaults.listAdditionalContacts.do?selectedContactIndex="+intex+"&contactType="+selectedContactType, "550", "300");
	}
}

function onChangeContactTypes(contactType) {
	var selectedIndex = contactType.id.charAt(contactType.id.length - 1);
	if(contactType.value != "ETR" && contactType.value != "ETRTOA" && contactType.value != "EBK" && contactType.value != "QAE" && contactType.value != "EFRT" && contactType.value != "BTP") {//ICRD-162691
		disableField(document.getElementsByName('notificationPreferences')[selectedIndex]);
		disableField(document.getElementsByName('additionalContacts')[selectedIndex]);
	}
	else {
		enableField(document.getElementsByName('notificationPreferences')[selectedIndex]);
		enableField(document.getElementsByName('additionalContacts')[selectedIndex]);
	}
}

//Added for ICRD-58628 by A-5163 starts
function addCertificationDetails(){
	addTemplateRow('certificateDetailsTemplateRow','certificateDetailsTableBody','hiddenOpFlagForCertificate');
}

function deleteCertificationDetails(){
	deleteTableRow('rowIdCertifications','hiddenOpFlagForCertificate');
}
//Added for ICRD-58628 by A-5163 ends

function displayCustomerLOV(){
	var accNo = targetFormName.elements.iataCode.value;
	var descriptionTxtFieldName = '';
	var StrUrl='showCustomer.do?code='+accNo+'&pagination=Y&lovaction=showCustomer.do&index=0&lovTxtFieldName=iataCode&lovDescriptionTxtFieldName='+descriptionTxtFieldName+'&formCount=1&multiselect=N&mode=Y';
	//var myWindow = openPopUp(StrUrl,570,330);
	var options = {url:StrUrl}
	var myWindow = CustomLovHandler.invokeCustomerLov(options);
	myWindow._parentOkFnHook='updateCust1';
	self._lovOkFnHook='updateCust1';
	}
function addPrefixValues(){
	var frm =targetFormName;
	var cusLocNo = frm.elements.customsLocationNo.value;
	if(cusLocNo.length == 1 && IsItNumeric(cusLocNo)){
		frm.elements.customsLocationNo.value = '000'+cusLocNo;
	}else if(cusLocNo.length == 2 && IsItNumeric(cusLocNo)){
		frm.elements.customsLocationNo.value = '00'+cusLocNo;
	}else if(cusLocNo.length == 3 && IsItNumeric(cusLocNo)){
		frm.elements.customsLocationNo.value = '0'+cusLocNo;
	}	
}
function onBlurCopyBillingDetails(obj){
var frm =targetFormName;
var companyCode = frm.elements.companyCode.value;
	if(companyCode=="B6"){
		if(obj=="address1"){
		frm.elements.billingLocation.value = eval('frm.elements.'+obj+'.value');
		}
		if(obj=="address2"){
		frm.elements.billingStreet.value = eval('frm.elements.'+obj+'.value');
		}
		if(obj=="city"){
		frm.elements.billingCityCode.value = eval('frm.elements.'+obj+'.value');
		}
		if(obj=="state"){
		frm.elements.billingState.value = eval('frm.elements.'+obj+'.value');
		}
		if(obj=="country"){
		frm.elements.billingCountry.value = eval('frm.elements.'+obj+'.value');
		}
		if(obj=="zipCode"){
		frm.elements.billingZipcode.value = eval('frm.elements.'+obj+'.value');
		}
		if(obj=="telephone"){
		frm.elements.billingTelephone.value = eval('frm.elements.'+obj+'.value');
		}
		if(obj=="fax"){
		frm.elements.billingFax.value = eval('frm.elements.'+obj+'.value');
		}
		if(obj=="email"){
		frm.elements.billingEmail.value = eval('frm.elements.'+obj+'.value');
		}
	}
}
function populateZipCodeDetails(){
var _ZipCode=targetFormName.elements.zipCode.value;
    var _counrty = targetFormName.elements.country.value;
    if(_counrty == "" && _ZipCode != ""){    	
		showDialog({	
			msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.entercountry' scope='request'/>",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
    	targetFormName.elements.zipCode.value="";
        targetFormName.elements.zipCode.focus();
        return false;
    }

    
	 if(targetFormName.elements.zipCode.value.trim() !=""){
		recreatePartyNameDetails("customermanagement.defaults.populatezipcodedetails.do?zipCode="+_ZipCode.toUpperCase(),"updateZipCodeDetails");
	}
}
function updateZipCodeDetails(_tableInfo){
	_innerFrm=_tableInfo.document.getElementsByTagName("form")[0];
	var errorFlag=_innerfrm.elements.ajaxErrorStatusFlag.value;
	if(errorFlag=="N"){
		//targetFormName.elements.country.value=_innerfrm.elements.country.value;
		targetFormName.elements.city.value=_innerfrm.elements.city.value;
		targetFormName.elements.state.value=_innerfrm.elements.state.value;
		targetFormName.elements.telephone.focus();
	}
	/*else {
		showDialog('Please enter a Valid Zip Code.', 1, self);
		targetFormName.elements.zipCode.value="";
		targetFormName.elements.zipCode.focus();
		<---Commented for Bug-90413 if invalid save as such otherwise show details--->
	}*/
}
function recreatePartyNameDetails(strAction){
	var __extraFn="updateZipCodeDetails";
	if(arguments[1]!=null){
		__extraFn=arguments[1];
	}
	asyncSubmit(targetFormName,strAction,__extraFn,null,null);
}


function displayAgentSCC(name,lov){
	var selected=lov.id;
	var index=selected.split(name)[1];
	displayLOV('showScc.do','Y','Y','showScc.do',document.forms[1].elements.agentScc.value,'Agent SCC','1','agentScc','',index);
}
function displayAgentOrigin(name,lov)
{
	var selected=lov.id;
	var index=selected.split(name)[1];
	displayLOV('showAirport.do','Y','Y','showAirport.do',document.forms[1].elements.origin.value,'Airport','1','origin','',index);
	}
//Added by A-8823 for IASCB-4841 beg
function displayAgentCarrier(name,lov){
	var selected=lov.id;
	var index=selected.split(name)[1];
	displayLOV('showAirline.do','Y','Y','showAirline.do',document.forms[1].elements.agentCarrier.value,'Agent Carrier','1','agentCarrier','',index);
}
//Added by A-8823 for IASCB-4841 end

function screenSpecificTabSetup(){

   setupPanes('container1','tab.customerdetails');
   displayTabPane('container1','tab.customerdetails');
   setSelectedTab('tab.customer'); 
   setupPanes('container2','tab.customer');
   displayTabPane('container2','tab.customer'); 
}

function addContact(){
	setSelectedTab('tab.customer');
	if(targetFormName.elements.selectedContactDetails.length>1){
		/* if(validateKeyContactRows(targetFormName.elements.contactCode)){
			showDialog({	
				msg		:	"Enter Contact Code",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			return;
		}else{ */
			selectCheckbox(targetFormName);
			addTemplateRow('custRegContactTemplateRow','custRegContactTableBody','hiddenOpFlagForCustomer');

			targetFormName.elements.contactCode[targetFormName.elements.selectedContactDetails.length-2].focus();
			//submitForm(targetFormName,"customermanagement.defaults.addkeycontactdetails.do");
		//}
		
	}else{
		selectCheckbox(targetFormName);
		addTemplateRow('custRegContactTemplateRow','custRegContactTableBody','hiddenOpFlagForCustomer');
	}

}


function addClearingAgent(){
	setSelectedTab('tab.agent');
	if(targetFormName.elements.selectedClearingAgentDetails.length>1){
		if(validateAgentKeyContactRows(targetFormName.elements.agentCode)){
			showDialog({	
				msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.enteragentcode' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			return;
		}
		if(validateAgentKeyContactRows(targetFormName.elements.agnetValidFrom)){
			showDialog({	
				msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.enterfromdate' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			return;
		}
		if(validateAgentKeyContactRows(targetFormName.elements.agnetValidTo)){
			showDialog({	
				msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.validtodate' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			return;
		}
			if(targetFormName.elements.agentType!='' && validateAgentKeyContactRows(targetFormName.elements.agentType)){
			showDialog({	
				msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.enteragenttype' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			return;
		}
		else{
			selectCheckbox(targetFormName);
			//submitForm(targetFormName,"customermanagement.defaults.addclearingagentdetails.do");
				addTemplateRow('custRegAgentTemplateRow','custRegAgentTableBody','hiddenOpFlagForAgent');
		}
	}else {
		addTemplateRow('custRegAgentTemplateRow','custRegAgentTableBody','hiddenOpFlagForAgent');
	}
}


function deleteContactDetails(){
setSelectedTab('tab.customer');
if(validateSelectedCheckBoxes(targetFormName,'selectedContactDetails',1000000,1)){
selectCheckbox(targetFormName);
//submitForm(targetFormName,"customermanagement.defaults.deletekeycontactdetails.do");
		deleteTableRow('selectedContactDetails','hiddenOpFlagForCustomer');

	}
}

function deleteClearingAgent(){

setSelectedTab('tab.agent');

if(validateSelectedCheckBoxes(targetFormName,'selectedClearingAgentDetails',1000000,1)){
selectCheckbox(targetFormName);
//submitForm(targetFormName,"customermanagement.defaults.deleteclearingagentdetails.do");
		deleteTableRow('selectedClearingAgentDetails','hiddenOpFlagForAgent');

	}


}


/*function validateKeyContactRows(code){

var code = eval(code);

if(code != null){
	if((code.length)-1>1){
	for(var i=0;i<(code.length)-1;i++){
		if(code[i].value==""){
			return true;
			}
		}
	}else{

		if(code[0].value==""){
			return true;
		}
	}
	}
	return false;
}*/

function validateKeyContactRows(code){

var frm=targetFormName;
var code = eval(code);
var hiddenOpFlags = eval(frm.elements.hiddenOpFlagForCustomer);

if(code != null ){
	var size = code.length;
	if(size>1){
		for(var i=0;i<size;i++){
			if(hiddenOpFlags[i].value != "NOOP" && hiddenOpFlags[i].value != "D"){
				if(code[i].value==""){
					return true;
				}
			}
		}
	}else {
	if(hiddenOpFlags.value != "NOOP" && hiddenOpFlags.value != "D"){
			if(code.value==""){
				return true;
			}
		}
	}
	}
	return false;
}


function validateAgentKeyContactRows(code){

var frm=targetFormName;
var code = eval(code);
var hiddenOpFlags = eval(frm.elements.hiddenOpFlagForAgent);

if(code != null ){
	var size = code.length;
	if(!isNaN(size) && !isNaN(frm.elements.hiddenOpFlagForAgent.length) && size>1){
		for(var i=0;i<size;i++){

			if(hiddenOpFlags[i].value != "NOOP" && hiddenOpFlags[i].value != "D"){
				if(code[i].value==""){
					return true;
				}
			}
		}
	}else {
	if(hiddenOpFlags.value != "NOOP" && hiddenOpFlags.value != "D"){
			if(code.value==""){
				return true;
			}
		}
	}
	}
	return false;
}





function registerCustomer(){

var leng=targetFormName.elements.aircraftTypeHandled.length;
var t1="";
for(var i=0;i<targetFormName.elements.aircraftTypeHandled.length;i++)
{ 
if(targetFormName.elements.aircraftTypeHandled[i].checked==true){
	if(t1.length==0){
	t1=targetFormName.elements.aircraftTypeHandled[i].value;
	}else{
	t1=t1+","+targetFormName.elements.aircraftTypeHandled[i].value;
	}
	}
 }
targetFormName.elements.aircraftTypeHandled.value=t1;
setSelectedTab('tab.customer');


//if(targetFormName.elements.custName.value==""){
	//showDialog('Please Enter Cust Name', 1, self, targetFormName, 'id_1');
	//return false;
//}
//if(targetFormName.elements.station.value==""){
//	showDialog('Please Enter Station', 1, self, targetFormName, 'id_1');
//	return false;
//}
//if(targetFormName.elements.address1.value==""){
	//showDialog('Please Enter Address', 1, self, targetFormName, 'id_1');
	//return false;
//}
//if(targetFormName.elements.city.value==""){
//	showDialog('Please Enter City', 1, self, targetFormName, 'id_1');
//	return false;
//}

//if(targetFormName.elements.zipCode.value==""){
	//showDialog('Please Enter Zip Code', 1, self, targetFormName, 'id_1');
	//return false;
//}
//if(targetFormName.elements.country.value==""){
//	showDialog('Please Enter Country', 1, self, targetFormName, 'id_1');
//	return false;
//}
//if(targetFormName.elements.email.value==""){
	//showDialog('Please Enter Email', 1, self, targetFormName, 'id_1');
	//return false;
//}
//if(targetFormName.elements.telephone.value==""){
	//showDialog('Please Enter Telephone', 1, self, targetFormName, 'id_1');
	//return false;
//}
//if(validateCustomerRows()){
//showDialog('Add atleast one key contact for the customer ', 1, self, targetFormName, 'id_1');
//return;
//}
if(duplicateCheck()){
	showDialog({	
		msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.duplicatecontactexist' scope='request'/>",
		type	:	1, 
		parentWindow:self,
		parentForm:targetFormName,
	});
	return;
}
/* if(validateKeyContactRows(targetFormName.elements.contactCode)){
	showDialog({	
		msg		:	"Enter Contact Code",
		type	:	1, 
		parentWindow:self,
		parentForm:targetFormName,
	});
	return;
} */
/* if(validateDuplicateAgent()){
	showDialog({	
		msg		:	"Duplicate Agent  Exists",
		type	:	1, 
		parentWindow:self,
		parentForm:targetFormName,
	});
	return;
} */
if(validateAgentKeyContactRows(targetFormName.elements.agentStation)){
	showDialog({	
		msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.enteragentstation' scope='request'/>",
		type	:	1, 
		parentWindow:self,
		parentForm:targetFormName,
	});
	return;
}

if(validateAgentKeyContactRows(targetFormName.elements.agentCode)){
	showDialog({	
		msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.enteragentcode' scope='request'/>",
		type	:	1, 
		parentWindow:self,
		parentForm:targetFormName,
	});
	return;
}
if(validateAgentKeyContactRows(targetFormName.elements.agnetValidFrom)){
	showDialog({	
		msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.enterfromdate' scope='request'/>",
		type	:	1, 
		parentWindow:self,
		parentForm:targetFormName,
	});
	return;
}
if(validateAgentKeyContactRows(targetFormName.elements.agnetValidTo)){
	showDialog({	
		msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.validtodate' scope='request'/>",
		type	:	1, 
		parentWindow:self,
		parentForm:targetFormName,
	});
	return;
}
if(targetFormName.elements.agentType!='')
{
if(validateAgentKeyContactRows(targetFormName.elements.agentType)){
	showDialog({	
		msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.enteragenttype' scope='request'/>",
		type	:	1, 
		parentWindow:self,
		parentForm:targetFormName,
	});
	return;
}
}
/*
Commented the primary-user script validation as part of ICRD-175746, Modified by A-7137
if(checkPrimaryUserFlag()){
	showDialog({	
		msg		:	"Select one primary contact",
		type	:	1, 
		parentWindow:self,
		parentForm:targetFormName,
	});
	return;
}*/
/*if(targetFormName.elements.iacNumber.value.trim() != "" && targetFormName.elements.iacExpiryDate.value.trim() == ""){
	showDialog('Enter IAC Expiry Date', 1, self, targetFormName, 'id_1');
	return;	
}
if(targetFormName.elements.iacNumber.value.trim() == "" && targetFormName.elements.iacExpiryDate.value.trim() != ""){
	showDialog('Enter IAC Number', 1, self, targetFormName, 'id_1');
	return;	
}
if(targetFormName.elements.apiacsspNumber.value.trim() != "" && targetFormName.elements.apiacsspExpiryDate.value.trim() == ""){
	showDialog('Enter AP-IACSSP Expiry Date', 1, self, targetFormName, 'id_1');
	return;	
}
if(targetFormName.elements.apiacsspNumber.value.trim() == "" && targetFormName.elements.apiacsspExpiryDate.value.trim() != ""){
	showDialog('Enter AP-IACSSP Number', 1, self, targetFormName, 'id_1');
	return;	
}*/
/*
if(duplicateCCSFCheck()){
showDialog('Duplicate CCSF Number Exists', 1, self, targetFormName, 'id_1');
return;

}*/
//Added by A-5165 for ICRD-62973
if(validateMiscDetailValue()){
	showDialog({	
		msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.enterbankvalue' scope='request'/>",
		type	:	1, 
		parentWindow:self,
		parentForm:targetFormName,
	});
	return;
}
//Added by A-5165 for ICRD-62973 ends
if(validateMiscDetailCompanyCodeValue()){
	showDialog({	
		msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.companycodemandatory' scope='request'/>",
		type	:	1, 
		parentWindow:self,
		parentForm:targetFormName,
	});
	return;
}
if(validateMultipleCompanyCode()){
	showDialog({	
		msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.onlycompanycodelinkedtocustomer' scope='request'/>",
		type	:	1, 
		parentWindow:self,
		parentForm:targetFormName,
	});
	return;
}
//Added as part or ICRD-64121
//Modified by a-6314 for ICRD-123925
if(validateMiscDateDetails()){
	showDialog({	
		msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.enterfromtodate' scope='request'/>",
		type	:	1, 
		parentWindow:self,
		parentForm:targetFormName,
	});
	return;
}
//Added as part or ICRD-64121
/*
if(validateCCSFRows(targetFormName.elements.ccsfNumber)){
showDialog('Enter CCSF Number', 1, self, targetFormName, 'id_1');
return;
}*/

/*//Added for ICRD-58628 by A-5163 starts
if(validateCertificateNumber(targetFormName.elements.customerCertificateType, targetFormName.elements.customerCertificateNumber)){
	showDialog({	
		msg		:	"<common:message bundle="maintainregcustomerform" key="customermanagement.defaults.customerregistration.certifications.entercertificatenumber" scope="request"/>",
		type	:	1, 
		parentWindow:self,
		parentForm:targetFormName,
	});
	return;
}
if(validateCertificateDetails(targetFormName.elements.customerCertificateType, targetFormName.elements.customerCertificateNumber)){
	showDialog({	
		msg		:	"<common:message bundle="maintainregcustomerform" key="customermanagement.defaults.customerregistration.certifications.entercertificatedetails" scope="request"/>",
		type	:	1, 
		parentWindow:self,
		parentForm:targetFormName,
	});
	return;
}
//Added for ICRD-58628 by A-5163 ends
*/
//Commented for ICRD-58628 by A-5163 starts
/*if(validateCCSFCityName(targetFormName.elements.ccsfCityName)){
showDialog('Enter CCSF CityName', 1, self, targetFormName, 'id_1');
return;
}
if(validateCCSFExpiryDate(targetFormName.elements.ccsfExpiryDate)){
showDialog('Enter CCSF Expiry Date', 1, self, targetFormName, 'id_1');
return;
}*/
//Commented for ICRD-58628 by A-5163 ends

/*if(validateEstablishedDate()){
showDialog('Enter Established Date', 1, self, targetFormName, 'id_1');
return;
}*/
selectCheckbox(targetFormName);
setSelectedTab('tab.customer');

/*if(targetFormName.elements.knownShipperCheck.checked){
	targetFormName.elements.knownShipper.value="Y"
}else{
	targetFormName.elements.knownShipper.value="N"
}*/

/*Commented by A-5807 for ICRD-73246
if(targetFormName.elements.stopCreditCheck.checked){
	targetFormName.elements.stopCredit.value="Y"
}else{
	targetFormName.elements.stopCredit.value="N"
}

if(targetFormName.elements.invoiceToCustomerCheck.checked){
	targetFormName.elements.invoiceToCustomer.value="Y"
}else{
	targetFormName.elements.invoiceToCustomer.value="N"
}*/
if(targetFormName.elements.vendorCheck!=null){
if(targetFormName.elements.vendorCheck.checked){
	targetFormName.elements.vendorFlag.value="Y";
}else{
	targetFormName.elements.vendorFlag.value="N";
}
}
if(targetFormName.elements.excludeRounding!=null && targetFormName.elements.excludeRounding.checked){
targetFormName.elements.excludeRounding.value = 'true';
}
//Added to fix ICRD-206390
if(validateSpecialCharacterFields(targetFormName)==false){
       	 return;
};
//ends
/*Added by A-5491 for ICRD-169509 on 08AUG2016.If bill to is Controlling then customer details are mandatory.*/
if(targetFormName.elements.billingTo.value=='C' && targetFormName.elements.iataCode.value==''){
	showDialog({	
		msg		:	'<common:message bundle="maintainregcustomerform" key="customermanagement.defaults.customerregn.msg.err.ControllingLocationforCL" scope="request"/>',
		type	:	1, 
		parentWindow:self,
		parentForm:targetFormName,
	});
	
	return;
}
if(validateFields(targetFormName.elements.billingTelephone,-1,'Telephone Number',13,'Invalid Billing Telephone Number',true)==false){
	return;
}	
//commented for ICRD-354415
/*
if(validateFields(targetFormName.elements.billingLocation,-1,'Billing Location',8,'Invalid Billing Location',true)==false){
	return;
}
*/
if(validateZipCode(targetFormName,targetFormName.billingZipcode,'Invalid Billing ZipCode Format')==false){
	return;
}
if(validateBillingEmailFormat()==false){
	return;
}
submitForm(targetFormName,"customermanagement.defaults.savecustomerregistratrion.do?aircraftTypeHandled="+t1);
}

function clearCustomerRegn(){
//Modified as part of CR ICRD-253447 by A-8154
var screenFlag = "Screenload";
setSelectedTab('tab.customer');
submitForm(targetFormName,"customermanagement.defaults.clearcustomerregistratrion.do?screenFlag="+screenFlag);

}

function validateEmailFormat(){
if(targetFormName.elements.email.value !="") {

	var splitString = [];
	var emailFound = false;
	if (targetFormName.elements.email.value.indexOf(',') > -1) {
		splitString = targetFormName.elements.email.value.split(',');
		
	}else{
		splitString.push(targetFormName.elements.email.value)
	}
	for(var i = 0; i < splitString.length; i++){
		emailFound= validateEmail(splitString[i]);
		if(!emailFound){
			break;
		}
	}
	
		if(!emailFound){
			showDialog({	
				msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.enteremailid' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				onClose:function(){	
				targetFormName.elements.email.focus();
				}
			});
			return;
		}
	}
}
//Added by A-7905 as part of ICRD-228463 starts
function validateBillingEmailFormat(){
	if(targetFormName.elements.billingEmail.value !="") {

     var splitString = [];
     var emailFound = false;
    if (targetFormName.elements.billingEmail.value.indexOf(',') > -1) {
	splitString = targetFormName.elements.billingEmail.value.split(',');
	
    }else{
	splitString.push(targetFormName.elements.billingEmail.value);
    }
    for(var i = 0; i < splitString.length; i++){
	emailFound= validateEmail(splitString[i]);
	if(!emailFound){
		break;
	  }
    }
		
		if(!emailFound){
			showDialog({	
				msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.enteremailid' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				onClose:function(){	
			targetFormName.elements.billingEmail.focus();
				}
			});
			return false;
		}
		return true;
	}


	if(targetFormName.elements.billingEmailOne.value !="") {
		
		var splitString = [];
	var emailFound = false;
    if (targetFormName.elements.billingEmailOne.value.indexOf(',') > -1) {
	splitString = targetFormName.elements.billingEmailOne.value.split(',');
	
    }else{
	splitString.push(targetFormName.elements.billingEmailOne.value);
    }
    for(var i = 0; i < splitString.length; i++){
	emailFound= validateEmail(splitString[i]);
	if(!emailFound){
		break;
	  }
    }		
		if(!emailFound){
			showDialog({	
				msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.enteremailid' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			targetFormName.elements.billingEmailOne.focus();
			return;
		}
	}
		if(targetFormName.elements.billingEmailTwo.value !="") {
			
		var splitString = [];
		var emailFound = false;
		if (targetFormName.elements.billingEmailTwo.value.indexOf(',') > -1) {
		splitString = targetFormName.elements.billingEmailTwo.value.split(',');
		
		}else{
		splitString.push(targetFormName.elements.billingEmailTwo.value);
		}
		for(var i = 0; i < splitString.length; i++){
		emailFound= validateEmail(splitString[i]);
		if(!emailFound){
			break;
		  }
		}		
		if(!emailFound){
			showDialog({	
				msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.enteremailid' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			targetFormName.elements.billingEmailTwo.focus();
			return;
		}
	}
}
//Added by A-7905 as part of ICRD-228463 ends
function validateTelephone(frm,telephone){

validateFields(telephone,-1,'Contact Telephone',13,'Invalid Contact Telephone',true)
}


function validateMobile(frm,mob){

validateFields(mob,-1,'Mobile Number',13,'Invalid Mobile Number',true);
}

function validateFax(frm,fax){
 validateFields(fax,-1,'Fax Number',13,'Invalid Fax Number',true);
}


function listCustomer(){
setSelectedTab('tab.customer');
submitForm(targetFormName,"customermanagement.defaults.listcustomerregistratrion.do");
}

function onLoad(){
	billingThroughInterlineValidation();
	cassValidation();		
	privilegeCheck();
	
	//Added for ICRD-67442 by A-5163 starts
	populateCustomerHistoryDetails();
	if(targetFormName.elements.isHistoryPresent.value == "Y"){
		targetFormName.elements.btnList.disabled = true;
	}
	if(targetFormName.elements.isLatestVersion.value != "Y" &&
			targetFormName.elements.isLatestVersion.value != ""){
		disableFormFields();
	}
	//Added for ICRD-67442 by A-5163 ends
	
	if(!targetFormName.elements.enduranceFlag[1].checked){
		targetFormName.elements.enduranceFlag[0].checked = true;		
	}
	
targetFormName.elements.customerCode.focus();
	if(targetFormName.elements.fromCustListing.value=="Y"){
		if(targetFormName.elements.frmCusLisCreate.value=="Y"){
			targetFormName.elements.frmCusLisCreate.value="";
		}else{
			targetFormName.elements.customerCode.disabled=true;
			var lov = document.getElementById('customerlov');
			lov.disabled=true;
			//targetFormName.elements.btnClear.disabled=true;//commented by A-8125 for  ICRD-264991 
			targetFormName.elements.btnList.disabled=true;
		}
	}
	if(targetFormName.elements.customerCode.value== null || targetFormName.elements.customerCode.value==""){
		targetFormName.elements.btnGroupList.disabled=true;

	}else{
		targetFormName.elements.btnGroupList.disabled=false;
	}

	if(targetFormName.elements.listStatus.value=="Y"){
		showDialog({	
			msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.createnewcustomer' scope='request'/>",
			type	:	4, 
			parentWindow: self,
			parentForm: targetFormName,
			dialogId:'id_3',
			onClose: function (result) {
				if(targetFormName.elements.currentDialogOption.value == 'Y') {
					screenConfirmDialog(targetFormName,'id_3');
				}else if(targetFormName.elements.currentDialogOption.value == 'N') {
					screenNonConfirmDialog(targetFormName,'id_3');
				}				
			}
		});
		targetFormName.elements.listStatus.value="";
	}


	if(targetFormName.elements.screenStatus.value=="UPDATE" ){
		targetFormName.elements.customerCode.disabled=true;
		var custlov = document.getElementById('customerlov');
		custlov.disabled=true;
		
			if(!targetFormName.elements.custName.disabled){
				//targetFormName.elements.custName.focus();
			}
	}

	var obj_image1 = document.getElementsByName("airportlov");
	var obj_image2 = document.getElementsByName("agentcodelov");
	var obj_OpFlag = document.getElementsByName("opFlag");
	var obj_export = document.getElementsByName("exported");
	var obj_import = document.getElementsByName("imported");
	var obj_sales = document.getElementsByName("sales");

	var obj_stn = document.getElementsByName("agentStation");
			if(obj_OpFlag!=null){
				for(var i = 0;i<obj_OpFlag.length;i++){

					if(obj_OpFlag[i].value!="D" &&
					obj_stn[i].value!="")
					{
					
					//obj_image1[i].disabled=true;
					obj_sales[i].disabled=true;
					obj_export[i].disabled=true;
					obj_import[i].disabled=true;
				/*	if(obj_OpFlag[i].value=="NA")
					{
					obj_image2[i].disabled=true;

					}else
					{
					obj_image2[i].disabled=false;

					} */
					}

				}

	}
	//enableEstablishedDate();
	privilegeValidation();
	//Added as part of CR ICRD-253447 starts
	var frm=targetFormName;
	if(targetFormName.elements.screenFlag.value == "Screenload"){
		disableField(targetFormName.elements.btnActivate);
		targetFormName.elements.screenFlag.value="";
	}else if(frm.elements.status[0].value=='D'||frm.elements.status[0].value=='N'){
	  enableField(targetFormName.elements.btnActivate);  
	}
	else if(frm.elements.status[0].value=='A'){
	  disableField(targetFormName.elements.btnActivate);  
	}
	else{
		enableField(targetFormName.elements.btnActivate);  
	}
	//Added as part of CR ICRD-253447 ends
	/*by A-7567 for ICRD-305684*/
	validateApplicableToField();
}

function screenConfirmDialog(frm, dialogId) {
 	while(frm.elements.currentDialogId.value == ''){
 	}

 	if(frm.elements.currentDialogOption.value == 'Y') {
 		if(dialogId == 'id_3'){
			enableCustomerCheckBox();
			//frm.elements.custName.focus();
 		}
 	}


 	}



 	 function screenNonConfirmDialog(frm, dialogId) {

			if(frm.elements.currentDialogOption.value == 'N') {
		 		if(dialogId == 'id_3'){
				enableCustomerCheckBox();
				targetFormName.elements.customerCode.value="";
		 		}
		 		}

}



function showTempIDLOV(){
//displayLOV('showCustomer.do','N','Y','showCustomer.do',targetFormName.elements.customerCode.value,'customerCode','1','customerCode','',0)
	var accNo = targetFormName.elements.customerCode.value;
	var descriptionTxtFieldName = '';
	var StrUrl='showCustomer.do?code='+accNo+				'&pagination=Y&lovaction=showCustomer.do&index=0&lovTxtFieldName=customerCode&lovDescriptionTxtFieldName='+descriptionTxtFieldName+'&formCount=1&multiselect=N&mode=Y';
	var options = {url:StrUrl};
	//var myWindow = openPopUp(StrUrl,800,450);
	var myWindow  = CustomLovHandler.invokeCustomerLov(options);
	myWindow._parentOkFnHook='updateCust()';
	self._lovOkFnHook='updateCust()';
}

function updateCust(){
	var values = targetFormName.elements.customerCode.value;
	var index = values.split("Ç");
	if(index[0] != 'null'){
		targetFormName.elements.customerCode.value = index[0];
	}else{
		targetFormName.elements.customerCode.value ='';
	}
	return;
}
// Added by A-7636 for ICRD-240560
function showHoldingCompanyLOV(){
	var holdingCompany = targetFormName.elements.holdingCompany.value;
	var descriptionTxtFieldName = '';
	var StrUrl='showCustomer.do?code='+holdingCompany+				'&type=HC&pagination=Y&lovaction=showCustomer.do&index=0&lovTxtFieldName=holdingCompany&lovDescriptionTxtFieldName='+descriptionTxtFieldName+'&formCount=1&multiselect=N&mode=Y';
	var options = {url:StrUrl}
	var myWindow = CustomLovHandler.invokeCustomerLov(options);
	myWindow._parentOkFnHook='updateHoldingCompany';
	self._lovOkFnHook='updateHoldingCompany';
}
function updateHoldingCompany(values){
	//var values = targetFormName.elements.holdingCompany.value;
	var index = values.split("Ç");
	if(index[0] != 'null'){
		targetFormName.elements.holdingCompany.value = index[0];
	}else{
		targetFormName.elements.holdingCompany.value ='';
	}
	return;
}
function updateCust1(values){
	//var values = targetFormName.elements.iataCode.value;
	var index = values.split("Ç");
	if(index[0] != 'null'){
		targetFormName.elements.iataCode.value = index[0];
		targetFormName.elements.clName.value = index[1];
	}else{
		targetFormName.elements.iataCode.value ='';
		targetFormName.elements.clName.value = '';
	}
	self._lovOkFnHook = '';
	return;
}




function selectCheckbox(frm){


var isActive = "";
var isPrimaryContact = "";
var isExported = "";
var isImported = "";
var isSales = "";


	var val = "";
    var formObj  = document.forms[document.forms.length - 1];
	var formElementObj = null;
	for(var cnt=0;cnt<document.forms.length;cnt++) {
	
	    formObj = document.forms[cnt];
	    for (var i = 0; i < formObj.elements.length; i++) { 
		formElementObj = formObj.elements[i];
		if(formElementObj.name =='active') {
			if(formElementObj.checked) {
				 val = "A";
			}
			else {
			 	 val = "I";
			}
				if(isActive != "")
					isActive = isActive+","+val;
				else if(isActive == "")
					isActive = val;

		}


	if(formElementObj.name =='primaryContacts') {

					if(formElementObj.checked) {
						 val = "Y";
					}
					else {
					 	 val = "N";
					}
						if(isPrimaryContact != "")
							isPrimaryContact = isPrimaryContact+","+val;
						else if(isPrimaryContact == "")
							isPrimaryContact = val;

				}


	if(formElementObj.name =='exported') {

					if(formElementObj.checked) {
						 val = "Y";
					}
					else {
					 	 val = "N";
					}
						if(isExported != "")
							isExported = isExported+","+val;
						else if(isExported == "")
							isExported = val;

				}


	if(formElementObj.name =='imported') {

					if(formElementObj.checked) {
						 val = "Y";
					}
					else {
					 	 val = "N";
					}
						if(isImported != "")
							isImported = isImported+","+val;
						else if(isImported == "")
							isImported = val;

				}


	if(formElementObj.name =='sales') {

						if(formElementObj.checked) {
							 val = "Y";
						}
						else {
						 	 val = "N";
						}
							if(isSales != "")
								isSales = isSales+","+val;
							else if(isSales == "")
								isSales = val;

				}




	}
	}
	targetFormName.elements.checkedStatus.value = isActive;
	targetFormName.elements.primaryContact.value=isPrimaryContact;
	targetFormName.elements.checkedExport.value = isExported;
	targetFormName.elements.checkedImport.value  = isImported;
	targetFormName.elements.checkedSales.value = isSales;


}

function validateEmailTable(frm,email){
if(email.value !="") {

	var emailFound= validateEmail(email.value);
		if(!emailFound){
			showDialog({	
				msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.enteremailid' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				onClose:function(){	
				document.getElementById(email.id).focus();
				}
			});
	   return;

		}
	}


}

function onClickClose(){
if(targetFormName.elements.screenStatus.value == 'maintainReservation' || targetFormName.elements.fromReservation.value == "true"){
	targetFormName.elements.screenStatus.value = '';
	targetFormName.elements.fromReservation.value = "false";
	window.close();
}
if(targetFormName.elements.screenStatus.value == 'maintainPermanentBooking' || targetFormName.elements.fromPermanentBooking.value == "true"){
	//targetFormName.elements.screenStatus.value = '';
	//targetFormName.elements.fromPermanentBooking.value = "false";
	window.close();
}


if(targetFormName.elements.fromCustListing.value=="Y"){
targetFormName.elements.fromCustListing.value="";
var status="Y";
submitForm(targetFormName,"customermanagement.defaults.listcustomerdetails.do?closeStatus="+status);
}else{
submitForm(targetFormName,"customermanagement.defaults.closeregisteraction.do");
	}
}

function resetFocus(){
    if(!event.shiftKey){ 
        if((!targetFormName.elements.customerCode.disabled) && (!targetFormName.elements.customerCode.readOnly)){		
          targetFormName.elements.customerCode.focus(); 		  
        }
		else{	
             if(!targetFormName.elements.custName.disabled){			 
		       targetFormName.elements.custName.focus();		   
		    }
		}
	}
}



function lovScreen(index) {

	var frm = targetFormName;
	var indexVal =  index.id;
	

	var index = indexVal.split("agentcodelov")[1];

	var description="agentName";

	
	var agentCode=eval(frm.elements.agentCode);




	var strAction="shared.defaults.agent.screenloadcustomeragentlov.do";

	var size = agentCode.length;

	var custcode=frm.elements.customerCode.value.toUpperCase();
	var exportflag;
	var salesflag;
	var importflag;
	var station;

	if(size > 1) {
	station=frm.elements.agentStation[index].value.toUpperCase();

	if(targetFormName.elements.agentStation[index].value==""){
		showDialog({	
			msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.enterstation' scope='request'/>",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
		return;
	}
	if(frm.elements.sales[index].checked || frm.elements.imported[index].checked || frm.elements.exported[index].checked)
	{

	if(frm.elements.sales[index].checked)
	salesflag="Y";
	else
	salesflag="N";
	if(frm.elements.imported[index].checked)
	importflag="Y";
	else
	importflag="N";
	if(frm.elements.exported[index].checked)
	exportflag="Y";
	else
	exportflag="N";
	}else{
	salesflag="";
	importflag="";
	exportflag="";
	}
	station=frm.elements.agentStation[index].value.toUpperCase();

	}else {
				station=frm.elements.agentStation[index].value.toUpperCase();
	if(frm.elements.agentStation.value==""){
		showDialog({	
			msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.enterstation' scope='request'/>",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
		return;
	}
	if(frm.elements.sales.checked || frm.elements.imported.checked || frm.elements.exported.checked)
	{
	if(frm.elements.sales.checked)
	salesflag="Y";
	else
	salesflag="N";
	if(frm.elements.imported.checked)
	importflag="Y";
	else
	importflag="N";
	if(frm.elements.exported.checked)
	exportflag="Y";
	else
	exportflag="N";
	}else{
	salesflag="";
	importflag="";
	exportflag="";
	}


	}


	var formName="customerreg";
	var agentIataCode = targetFormName.elements.iataAgentCode.value;
	if(size > 1) {
		var StrUrl=strAction+"?textfiledObj=agentCode["+index+"]&formNumber=1&textfiledDesc="+description+"&exportFlag="+exportflag+"&importFlag="+importflag+"&salesFlag="+salesflag+"&customerCode="+custcode+"&station="+station+"&index="+index+"&formName="+formName;

	}else {
		index="N";
		var StrUrl=strAction+"?textfiledObj=agentCode&formNumber=1&textfiledDesc="+description+"&exportFlag="+exportflag+"&importFlag="+importflag+"&salesFlag="+salesflag+"&customerCode="+custcode+"&station="+station+"&index="+index+"&formName="+formName;
	}
	//var myWindow = window.open(StrUrl, "LOV", 'scrollbars,width=510,height=390,screenX=100,screenY=30,left=250,top=100');
	openPopUpWithHeight(StrUrl,"500");
	
	targetFormName.elements.iataAgentCode.focus();
	targetFormName.elements.iataAgentCode.value = agentIataCode;
	targetFormName.elements.agentCode.focus();
}



function displayAirport(name,lov){
var selected=lov.id;

var index=selected.split(name)[1];


	displayLOV('showStation.do','N','Y','showStation.do',document.forms[1].elements.agentStation.value,'CurrentAirport','1','agentStation','',index);
}



/*function showGroupCodeLOV(){
 	var textfiledDesc="";
 	var customerGroup=document.forms[1].customerGroup.value;
 	var strAction="shared.defaults.customer.screenloadcustomergrouplov.do";
 	var StrUrl=strAction+"?textfiledObj=customerGroup&formNumber=1&textfiledDesc="+textfiledDesc;
 	var myWindow = window.open(StrUrl, "LOV", 'scrollbars,width=510,height=390,screenX=100,screenY=30,left=250,top=100')
}*/



function singleSelect(checkVal)

{

	for(var i=0;i<document.forms[1].elements.length;i++)
	{
		if(targetFormName.elements[i].type =='checkbox' && targetFormName.elements[i].name=='primaryContacts')
		{

			if(targetFormName.elements[i].checked == true)
			{
				if(targetFormName.elements[i].id != checkVal.id)
				{
					targetFormName.elements[i].checked = false;
				}
			}
		}
	}

}

function checkPrimaryUserFlag(){
var customerRow=document.getElementsByName('hiddenOpFlagForCustomer');
var primaryFlags = document.getElementsByName('primaryContacts');

if(primaryFlags.length>0){
if(primaryFlags.length>1){
	for(var i=0;i<primaryFlags.length;i++){
		if(customerRow[i].value=='U' || customerRow[i].value=='I'){
		if(primaryFlags[i].checked){
			return false;
		}
	}else if(customerRow[i].value=='NOOP'){
			return false;
	}
	}
	return true;
	}//else{
	//return false;
	//}
}
}

function closePopUpAction(){

	if(targetFormName.elements.fromReservation.value == "true"){
		showDialog({	
			msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.customerhascode' scope='request'/>"+targetFormName.elements.customerCode.value,
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
		targetFormName.elements.customerCode.value = "";
		targetFormName.elements.fromReservation.value = "false";
		window.opener.IC.util.common.childUnloadEventHandler();
		window.opener.submitForm(window.opener.targetFormName,"capacity.booking.maintainreservation.reloadFromRegisterCustomer.do");
		//window.opener.targetFormName.elements.customerCode.value  = targetFormName.elements.customerCode.value;
		//window.opener.targetFormName.elements.customerName.value  = targetFormName.elements.custName.value;
		window.close();
	}
	if(targetFormName.elements.fromPermanentBooking.value == "true"){
			showDialog({	
				msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.customerhascode' scope='request'/>"+targetFormName.elements.customerCode.value,
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			targetFormName.elements.customerCode.value = "";
			targetFormName.elements.fromPermanentBooking.value = "false";
			window.opener.IC.util.common.childUnloadEventHandler();
			window.opener.submitForm(window.opener.targetFormName,"capacity.booking.maintainpermanentbooking.reloadFromRegisterCustomer.do");
		//window.opener.targetFormName.elements.customerCode.value  = targetFormName.elements.customerCode.value;
		//window.opener.targetFormName.elements.customerName.value  = targetFormName.elements.custName.value;
		window.close();
	}
}

function validateCustomerRows(){

	var customerRow=document.getElementsByName('hiddenOpFlagForCustomer');
	var count=0;
	for(var i=0;i<customerRow.length;i++){
			if(customerRow[i].value=='U' || customerRow[i].value=='I'){
				count=count+1;
			}
	}
	if(count==0){
		return true;
	}else{
		return false;
	}
}
function duplicateCheck(){
	
	var flag=document.getElementsByName('hiddenOpFlagForCustomer');
	var customerCode=document.getElementsByName('contactCode');
	var customerName=document.getElementsByName('contactName');
	var contactTypes=document.getElementsByName('contactTypes');
	for(var j=0;j<customerCode.length;j++){
	var count=0;
		if(flag[j].value=='U' || flag[j].value=='I'){
			for(var k=0;k<customerCode.length;k++){
				if(customerName[j].value.trim() != "" && customerName[k].value.trim() != "" ) {
				if(flag[k].value=='U' || flag[k].value=='I'){
					if(customerName[j].value.toUpperCase()==customerName[k].value.toUpperCase() && customerCode[j].value.toUpperCase()==customerCode[k].value.toUpperCase()){//Added by A-7396 for ICRD-274900						
						if(contactTypes[j].value.toUpperCase()==contactTypes[k].value.toUpperCase()){
						count=count+1;
						}
						}
					
					}
				}
			}
		}
		
		if(count>1){
			return true;
		}
	}
	return false;

}

function validateDuplicateAgent(){
	var frm=targetFormName;
	if(frm.elements.hiddenOpFlagForAgent.length>1){
	var ccount=0;
		for(var i=0;i<frm.elements.hiddenOpFlagForAgent.length;i++){
			if(frm.elements.hiddenOpFlagForAgent[i].value=='U' || frm.elements.hiddenOpFlagForAgent[i].value=='I'){
			var count=0;
					for(var j=0;j<frm.elements.hiddenOpFlagForAgent.length;j++){
							if(frm.elements.agentCode[j].value.toUpperCase()==frm.elements.agentCode[i].value.toUpperCase() && 
									frm.elements.agentStation[j].value.toUpperCase()==frm.elements.agentStation[i].value.toUpperCase()){
									count++;
							}		
					}
			if(count>1){
				ccount++;
			}
			}
		}
	if(ccount>1){
		return true;
	}	
	}
	return false;
}
//Added by a-3045 for CR HA16 on 03Jun09 starts
//Commented for ICRD-58628 by A-5163
/*function addCCSFDetails(){
	setSelectedTab('tab.certifications');
	if(targetFormName.elements.selectedCCSFDetails.length>1){
		if(validateCCSFRows(targetFormName.elements.ccsfNumber)){
			showDialog('Enter CCSF Number', 1, self, targetFormName, 'id_1');
			return;
		}if(validateCCSFCityName(targetFormName.elements.ccsfCityName)){
			showDialog('Enter CCSF CityName', 1, self, targetFormName, 'id_1');
			return;
		}if(validateCCSFExpiryDate(targetFormName.elements.ccsfExpiryDate)){
			showDialog('Enter CCSF Expiry Date', 1, self, targetFormName, 'id_1');
			return;
		}else{
			selectCheckbox(targetFormName);
			addTemplateRow('custccsfTemplateRow','custccsfTableBody','hiddenOpFlagForCCSF');
			//targetFormName.elements.contactCode[targetFormName.elements.selectedCCSFDetails.length-2].focus();
		}
	}else{
		selectCheckbox(targetFormName);
		addTemplateRow('custccsfTemplateRow','custccsfTableBody','hiddenOpFlagForCCSF');
	}
}
function deleteCCSFDetails(){
setSelectedTab('tab.certifications');
if(validateSelectedCheckBoxes(targetFormName,'selectedCCSFDetails',1000000,1)){
		selectCheckbox(targetFormName);
		deleteTableRow('selectedCCSFDetails','hiddenOpFlagForCCSF');

	}
}*/

/*function duplicateCCSFCheck(){
	
	var flag=document.getElementsByName('hiddenOpFlagForCCSF');
	var ccsfNumber=document.getElementsByName('ccsfNumber');

	for(var j=0;j<ccsfNumber.length;j++){
	var count=0;
		if(flag[j].value=='U' || flag[j].value=='I'){
			for(var k=0;k<ccsfNumber.length;k++){
				if(flag[k].value=='U' || flag[k].value=='I'){
					
					if(ccsfNumber[j].value.trim().toUpperCase()==ccsfNumber[k].value.trim().toUpperCase()){						
						count=count+1;
					}
				}
			}
		}		
		if(count>1){
			return true;
		}
	}
	return false;

}*/

function validateCCSFRows(ccsfNum){

	var frm=targetFormName;
	var code = eval(ccsfNum);
	var hiddenOpFlags = eval(frm.elements.hiddenOpFlagForCCSF);
	if(code != null ){
		var size = code.length;
		if(size>1){
			for(var i=0;i<size;i++){
				if(hiddenOpFlags[i].value != "NOOP" && hiddenOpFlags[i].value != "D"){
					if(code[i].value.trim()==""){
						return true;
					}
				}
			}
		}else {
		if(hiddenOpFlags.value != "NOOP" && hiddenOpFlags.value != "D"){
				if(code.value.trim()==""){
					return true;
				}
			}
		}
	}
	return false;
}

function validateCCSFCityName(cityName){

	var frm=targetFormName;
	var city = eval(cityName);
	var hiddenOpFlags = eval(frm.elements.hiddenOpFlagForCCSF);
	if(city != null ){
		var size = city.length;
		if(size>1){
			for(var i=0;i<size;i++){
				if(hiddenOpFlags[i].value != "NOOP" && hiddenOpFlags[i].value != "D"){
					if(city[i].value.trim()==""){
						return true;
					}
				}
			}
		}else {
		if(hiddenOpFlags.value != "NOOP" && hiddenOpFlags.value != "D"){
				if(city.value.trim()==""){
					return true;
				}
			}
		}
	}
	return false;
}
//Added by A-5165 for ICRD-62973
function validateMiscDetailValue(){
var frm=targetFormName;
if("Y"==frm.elements.restrictionFlag.value){
			if (frm.elements.miscDetailCode!= null
					&& frm.elements.miscDetailCode.length > 1) {
				for (var i = 0; i < frm.elements.miscDetailCode.length; i++) {
					if (frm.elements.miscDetailOpFlag!=null && frm.elements.miscDetailOpFlag[i]!=null 
							&& frm.elements.miscDetailOpFlag[i].value != "NOOP" && frm.elements.miscDetailOpFlag[i].value != "D") {
						if(frm.elements.miscDetailValue[i].value==""){
							return true;
						}					
					}
				}
			}
		}
		return false;
}
//Added by A-5165 for ICRD-62973
//Added by A-8852 for IASCB-103061 starts
function validateMiscDetailCompanyCodeValue(){
	var frm=targetFormName;
	if (frm.elements.miscDetailCode!= null && frm.elements.miscDetailCode.length > 1) {
		for (var i = 0; i < frm.elements.miscDetailCode.length; i++) {
			if (frm.elements.miscDetailCode[i].value == "CMPCOD" && frm.elements.miscDetailOpFlag!=null && frm.elements.miscDetailOpFlag[i]!=null && frm.elements.miscDetailOpFlag[i].value != "D" && frm.elements.miscDetailOpFlag[i].value != "NOOP") {
				var index = frm.elements.miscDetailCode[i];
				if(frm.elements.miscDetailValue[i].value == "" || frm.elements.miscDetailValue[i].value == " "){
					return true;
				}					
			}
		}
	}
}
function validateMultipleCompanyCode(){
	var frm=targetFormName;
	var detailCodevalues = [];
	for (var i = 0; i < frm.elements.miscDetailCode.length; i++) {
		if (frm.elements.miscDetailOpFlag!=null && frm.elements.miscDetailOpFlag[i]!=null && frm.elements.miscDetailOpFlag[i].value != "D" && frm.elements.miscDetailOpFlag[i].value != "NOOP") {
		    detailCodevalues.push(frm.elements.miscDetailCode[i].value);	
	    }
	}
	var dupItem;
	const uniqueElements = new Set(detailCodevalues);
    const filteredElements = detailCodevalues.filter(item => {
        if (uniqueElements.has(item)) {
            uniqueElements.delete(item);
        } else {
			dupItem = item;
        }	
    });
	if(dupItem == "CMPCOD"){
		return true;	
	}
}
//Added by A-8852 for IASCB-103061 ends
//Added by A-5165 for ICRD-64121
function validateMiscDateDetails(){
var frm=targetFormName;
if("Y"==frm.elements.restrictionFlag.value){
			if (frm.elements.miscDetailCode!= null
					&& frm.elements.miscDetailCode.length > 1) {
				for (var i = 0; i < frm.elements.miscDetailCode.length; i++) {
					if (frm.elements.miscDetailOpFlag!=null && frm.elements.miscDetailOpFlag[i]!=null 
							&& frm.elements.miscDetailOpFlag[i].value != "NOOP" && frm.elements.miscDetailOpFlag[i].value != "D") {
						if(frm.elements.miscDetailValidFrom[i].value=="" || frm.elements.miscDetailValidTo[i].value ==""){
							return true;
						}					
					}
				}
			}
		}
		return false;
}
//Added by A-5165 for ICRD-64121
function validateCCSFExpiryDate(expDate){

	var frm=targetFormName;
	var date = eval(expDate);
	var hiddenOpFlags = eval(frm.elements.hiddenOpFlagForCCSF);
	if(date != null ){
		var size = date.length;
		if(size>1){
			for(var i=0;i<size;i++){
				if(hiddenOpFlags[i].value != "NOOP" && hiddenOpFlags[i].value != "D"){
					if(date[i].value.trim()==""){
						return true;
					}
				}
			}
		}else {
		if(hiddenOpFlags.value != "NOOP" && hiddenOpFlags.value != "D"){
				if(date.value.trim()==""){
					return true;
				}
			}
		}
	}
	return false;
}

//Added for ICRD-58628 by A-5163 starts
function validateCertificateDetails(certificateType, certificateNumber){
	var frm = targetFormName;	
	var custCertificateNumber = eval(certificateNumber);
	var custCertificateType = eval(certificateType);	
	var hiddenOpFlags = eval(frm.elements.hiddenOpFlagForCertificate);
	if (custCertificateNumber != null){
		var size = custCertificateNumber.length;		
		for(var i=0;i<size;i++){
			if(hiddenOpFlags[i].value != "NOOP" && hiddenOpFlags[i].value != "D"){
				if(custCertificateType[i].value.trim() ==""){
					return true;
				} else if(custCertificateNumber[i].value.trim()=="" 
						&& custCertificateType[i].value != "KSH" 
							&& custCertificateType[i].value != "SDK"){
					return true;
				}				
			}
		}
	}
	return false;
}

function validateCertificateNumber(certificateType, certificateNumber){
	var frm = targetFormName;	
	var custCertificateNumber = eval(certificateNumber);
	var custCertificateType = eval(certificateType);	
	var hiddenOpFlags = eval(frm.elements.hiddenOpFlagForCertificate);
	if (custCertificateNumber != null){
		var size = custCertificateNumber.length;		
		for(var i=0;i<size;i++){
			if(hiddenOpFlags[i].value != "NOOP" && hiddenOpFlags[i].value != "D"){
				if(custCertificateType[i].value.trim()!="" && (custCertificateType[i].value != "KSH" && custCertificateType[i].value != "SDK")
						&& custCertificateNumber[i].value.trim()==""){
					return true;
				}
			}
		}
	}
	return false;
}
//Added for ICRD-58628 by A-5163 ends

//Commented for ICRD-58628 by A-5163 starts
/*function validateEstablishedDate(){
	var frm=targetFormName;
	if(frm.elements.knownShipperCheck.checked){
		if(frm.elements.establishedDate.value==""){
			return true;
		}
	}
	return false;
}

function enableEstablishedDate(){
	var frm=targetFormName;
	if(frm.elements.knownShipperCheck.checked){
		frm.elements.establishedDate.disabled = false;
		document.getElementById('btn_establishedDate').disabled = false;
	}else{
		frm.elements.establishedDate.value = "";
		frm.elements.establishedDate.disabled = true;
		document.getElementById('btn_establishedDate').disabled = true;
		
	}
}*/
//Commented for ICRD-58628 by A-5163 starts
//Commented by A-5222 bug of ICRD-43817
/**function validateEmailFormat(){
	if(targetFormName.elements.billingEmail.value !="") {
	var emailFound= validateEmail(targetFormName.elements.billingEmail.value);
		if(!emailFound){
		showDialog('Please Enter a Valid Billing Email ID',1,self);
		targetFormName.elements.billingEmail.focus();
		return;
		}
	}

}*/
//Added by a-3045 for CR HA16 on 03Jun09 ends
//Added by a-3045 for bug 53620 on 20Jul09 starts
function privilegeValidation(){
//var code = eval(targetFormName.elements.ccsfNumber);
	if(targetFormName.elements.adminPrivilege.value!='Y'){
		targetFormName.elements.remarks.disabled = false;
		//targetFormName.elements.iacNumber.disabled = true;
		//targetFormName.elements.iacExpiryDate.disabled = true;
		//document.getElementById('btn_iacExpiryDate').disabled = true;
		//targetFormName.elements.apiacsspNumber.disabled = true;
		//targetFormName.elements.apiacsspExpiryDate.disabled = true;
		//document.getElementById('btn_apiacsspExpiryDate').disabled = true;
		//document.getElementById('addccsf').disabled = true;
		//document.getElementById('deleteccsf').disabled = true;
	}
	//	var calendarArr=document.getElementsByName('ccsfExpiryDate_BTN');
		//for(var i=0;i<code.length;i++){
			//targetFormName.elements.ccsfNumber[i].disabled = true;
			//targetFormName.elements.ccsfCityName[i].disabled = true;
			//targetFormName.elements.ccsfExpiryDate[i].disabled = true;
			//calendarArr[i].disabled = true;
		//}		
//Added undefined condition to avoid the script Error - by A-8487 on 29Aug18	
		if (typeof code != 'undefined'){
		}
}		
	//}
//Added by a-3045 for bug 53620 on 20Jul09 ends
//added by a-3045 for bug 53463 on 24jul09 starts
function validateZipCode(frm,zipCode,message){
zipCode.value=zipCode.value.trim();
var asciiCode = "";
var valid = true;
	for (var i = 0; i < zipCode.value.length; i++) {
		asciiCode = zipCode.value.charCodeAt(i);
		var numeral  = validateCode(asciiCode, NUMERAL_ASCIISTART, NUMERAL_ASCIIEND);
		var hyphen 	  = validateCode(asciiCode, HYPHEN, HYPHEN);
		var alphabet_caps = validateCode(asciiCode, ALPHABET_CAPS_ASCIISTART, ALPHABET_CAPS_ASCIIEND);
		var alphabet      = validateCode(asciiCode, ALPHABET_ASCIISTART, ALPHABET_ASCIIEND);
		valid = numeral || hyphen || alphabet_caps || alphabet;
		if (!valid) break ;
	}
	if (!valid) {
		showDialog({	
			msg		:	message,
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
		zipCode.value = "";
		zipCode.focus();
		return false;
	}
	return true;
} 
//added by a-3045 for bug 53463 on 24jul09 ends

//added by a-3278 for CR JetBlue31-1 on 23Apr10
function showBillingPeriodLov(){
  
  	var multiselect="N";
	var code= targetFormName.elements.billingPeriod.value;
	var title="Billing Period LOV";
	var formCount= "1";
	var lovTxtFieldName = "billingPeriod";
	var lovNameTxtFieldName="billingPeriodDescription";
	var index=0;
	//if(code==null || code.length() == 0){
	//	code="";
	//}
	openPopUpWithHeight("shared.billingperiod.billingperiodlovlist.do?multiselect="+multiselect+"&billingPeriodCode="+code+"&lovNameTxtFieldName="+lovNameTxtFieldName+"&title="+title+"&formCount="+formCount+"&lovTxtFieldName="+lovTxtFieldName+"&index="+index+"",360);
	
}
//JetBlue31-1 on 23Apr10 ends

function showIATABillingLOV(code){
	var textfiledDesc="clName";
	var formCount=1;
	var code = targetFormName.elements.iataCode.value;
	var strAction="shared.defaults.agent.screenloadagentlov.do";
	var StrUrl=strAction+"?textfiledObj=agentCode&formNumber=1&textfiledDesc="+textfiledDesc+"&agentCode="+code;
	openPopUpWithHeight(appPath+"/shared.defaults.agent.screenloadagentlov.do"+"?textfiledObj=iataCode"+"&formNumber="+formCount+"&textfiledDesc="+textfiledDesc+"&agentCode="+code,'500');
}

function cassValidation(){
		
		if(targetFormName.elements.cassBillingIndicator.checked){			
			enableField(targetFormName.elements.cassCode);	
		}else{
			disableField(targetFormName.elements.cassCode);	
		}

}
function billingThroughInterlineValidation(){

		if(targetFormName.elements.billingThroughInterline.checked){			
			enableField(targetFormName.elements.airlineCode);
			enableField(targetFormName.elements.airlineCodeLOV);
		}else{
			disableField(targetFormName.elements.airlineCode);	
			disableField(targetFormName.elements.airlineCodeLOV);	
		}
}

function unCheckCustomerType(obj){

		var checkbobObj = document.getElementsByName("customerType");	
		var id = obj.id;		
		var _splitId = id.split("CHK_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_AGENT");
		
		var index = _splitId[1];

		if(checkbobObj != null && !isNaN(checkbobObj.length)) {
			for(var i=0;i<checkbobObj.length; i++) {
				if(i==index) {
					checkbobObj[i].checked = true;
					
					if(checkbobObj[i].value=='AG'){
					
					agentprivilegeValidation();
					}
					if(checkbobObj[i].value=='CC'){
					
					cccollectorprivilegeValidation();
					}
					if(checkbobObj[i].value=='CU'){
					
					customerprivilegeValidation();
					}
					
				}
				else {
					checkbobObj[i].checked = false;
				}
			}
		}

}

function disableRecipientCode(){
	var flag=false;
	/*var customerTypeCount = targetFormName.elements.customerType.length;			
		for (var i = 0; i < customerTypeCount; i++) {	
			var customerType = targetFormName.elements.customerType[i];			
				if(customerType != null){
					if(customerType.checked){			
						var customerTypeValue = targetFormName.elements.customerType[i].value;
						if(customerTypeValue=='AG'){
							flag=true;		
						}
					}
				}
		}*/
	var customerTypeValue = targetFormName.elements.customerType.value;
	if(customerTypeValue=='AG'){
		flag=true;		
		}
		if(flag){
			if(targetFormName.elements.cassImportIdentifier.value!=""){
				targetFormName.elements.recipientCode.readOnly = false;
			}else{
				targetFormName.elements.recipientCode.value ="";
				targetFormName.elements.recipientCode.readOnly = true;
			}
		}else{
			targetFormName.elements.recipientCode.readOnly = true;
		}
}
function agentprivilegeValidation(){

		var bankdetailsTab = document.getElementById("tab.bankdetails");	
		if(bankdetailsTab != null){
		enableField(bankdetailsTab);
		}
		
		enableField(targetFormName.elements.iataAgentCode);
		enableField(targetFormName.elements.agentInformation);
		enableField(targetFormName.elements.cassIdentifier);
		enableField(targetFormName.elements.cassImportIdentifier);
		enableField(targetFormName.elements.holdingCompany);
		enableField(targetFormName.elements.normalComm);
		enableField(targetFormName.elements.normalCommFixed);
		enableField(targetFormName.elements.ownSalesFlag);
		enableField(targetFormName.elements.salesReportAgent);
		enableField(targetFormName.elements.proformaInv);
		enableField(targetFormName.elements.controllingLocation);
		enableField(targetFormName.elements.sellingLocation);
		enableField(targetFormName.elements.iataCode);
		enableField(targetFormName.elements.clName);
		enableField(targetFormName.elements.billingTo);
		enableField(targetFormName.elements.invoiceClubbingIndicator);
		enableField(targetFormName.elements.customerlovBilling);
		
		aircraftTypeHandledCheckBoxValidation('true');
		
		disableField(targetFormName.elements.airportCode);	
		disableField(targetFormName.elements.dateOfExchange);	
		disableField(targetFormName.elements.cassBillingIndicator);	
		disableField(targetFormName.elements.cassCode);	
		disableField(targetFormName.elements.billingThroughInterline);	
		disableField(targetFormName.elements.airlineCode);	
		disableField(targetFormName.elements.airlineCodeLOV);	
		disableField(targetFormName.elements.airportCodelov);	
		//ICRD-141048 - A-5127 added
		if(targetFormName.elements.cassImportIdentifier.value == ""){
			targetFormName.elements.recipientCode.readOnly = true;
		}else{
			targetFormName.elements.recipientCode.readOnly = false;
		}
}

function cccollectorprivilegeValidation(){
	
		var bankdetailsTab = document.getElementById("tab.bankdetails");	
		if(bankdetailsTab != null){
		//Modified as Part of ICRD-63021 by A-5165
		enableField(bankdetailsTab);
		}
	    		
		disableField(targetFormName.elements.iataAgentCode);
		disableField(targetFormName.elements.agentInformation);
		disableField(targetFormName.elements.cassIdentifier);
		disableField(targetFormName.elements.cassImportIdentifier);
		//disableField(targetFormName.elements.salesId);
		disableField(targetFormName.elements.holdingCompany);
		disableField(targetFormName.elements.normalComm);
		disableField(targetFormName.elements.normalCommFixed);
		disableField(targetFormName.elements.ownSalesFlag);
		disableField(targetFormName.elements.salesReportAgent);
		disableField(targetFormName.elements.proformaInv);
		disableField(targetFormName.elements.iataCode);
		disableField(targetFormName.elements.clName);
	//	disableField(targetFormName.elements.billingTo);
	//	disableField(targetFormName.elements.invoiceClubbingIndicator);
	//	disableField(targetFormName.elements.controllingLocation);
	//	disableField(targetFormName.elements.sellingLocation);
		disableField(targetFormName.elements.customerlovBilling);
		
		aircraftTypeHandledCheckBoxValidation('false');
		
		enableField(targetFormName.elements.airportCode);
		enableField(targetFormName.elements.dateOfExchange);
		enableField(targetFormName.elements.cassBillingIndicator);
		enableField(targetFormName.elements.billingThroughInterline);
		enableField(targetFormName.elements.airportCodelov);
		//Added by A-5222 bug of ICRD-136653 on 15Dec2015
		targetFormName.elements.recipientCode.readOnly = true;
		targetFormName.elements.recipientCode.value ="";
				
}

function customerprivilegeValidation(){		

        var bankdetailsTab = document.getElementById("tab.bankdetails");	
		if(bankdetailsTab != null){
		//Modified as Part of ICRD-63021 by A-5165
		enableField(bankdetailsTab);
		}
		
		
		enableField(targetFormName.elements.controllingLocation) ;
		enableField(targetFormName.elements.sellingLocation) ;
		enableField(targetFormName.elements.iataCode) ;
		enableField(targetFormName.elements.clName) ;
		enableField(targetFormName.elements.billingTo) ;
		enableField(targetFormName.elements.invoiceClubbingIndicator) ;
		enableField(targetFormName.elements.customerlovBilling) ;
		
		
		
		
		//disableField(targetFormName.elements.iataAgentCode);
		disableField(targetFormName.elements.agentInformation);
		disableField(targetFormName.elements.cassIdentifier);
		disableField(targetFormName.elements.cassImportIdentifier);
		disableField(targetFormName.elements.holdingCompany);
		disableField(targetFormName.elements.normalComm);
		disableField(targetFormName.elements.normalCommFixed);
		disableField(targetFormName.elements.ownSalesFlag);
		disableField(targetFormName.elements.salesReportAgent);
		disableField(targetFormName.elements.proformaInv);
		disableField(targetFormName.elements.airportCode);
		disableField(targetFormName.elements.dateOfExchange);
		disableField(targetFormName.elements.cassBillingIndicator);
		disableField(targetFormName.elements.cassCode);
		disableField(targetFormName.elements.billingThroughInterline);
		disableField(targetFormName.elements.airlineCode);
		disableField(targetFormName.elements.airlineCodeLOV);
		disableField(targetFormName.elements.airportCodelov);
		aircraftTypeHandledCheckBoxValidation('true');
		//Added by A-5222 bug of ICRD-136653 on 15Dec2015
		targetFormName.elements.recipientCode.readOnly = true;
		targetFormName.elements.recipientCode.value ="";
		

}


function addNotes(){
 	addTemplateRow('bankdetailsTemplateRow','bankdetailsTableBody','miscDetailOpFlag');
}
function deleteNotes(){
	var index=document.getElementsByName('rowIdmis');
	deleteTableRow('rowIdmis','miscDetailOpFlag');
}

function privilegeCheck(){
	//Added the customerTypecheck condition for ICRD-34844 by A-4772
	
	/*var customerTypeCount = targetFormName.elements.customerType.length;			
	for (var i = 0; i < customerTypeCount; i++) {	
		var customerType = targetFormName.elements.customerType[i];			
			if(customerType != null){
				if(customerType.checked){			
					var customerTypeValue = targetFormName.elements.customerType[i].value;
					if(customerTypeValue=='AG'){
						agentprivilegeValidation();				
					}
					if(customerTypeValue=='CC'){
						cccollectorprivilegeValidation();				
					}
					if(customerTypeValue=='CU')	{
						customerprivilegeValidation();
					}		
				}else{
					//if(targetFormName.elements.sourcePage.value != "SAVE"){			
					//	targetFormName.elements.recipientCode.readOnly = true;	
					//}
					if(targetFormName.elements.customerCode.value != "" 
						//&& targetFormName.elements.screenStatus.value != "maintainReservation"
						//&& targetFormName.elements.screenStatus.value != "maintainPermanentBooking"
						&& targetFormName.elements.customerTypecheckFlag.value == 'Y')	
						disableField(customerType);					
				}
			}
		}*/
		/*Added by A-4772 for ICRD-34645 starts here
		
		if(targetFormName.elements.customerTypecheckFlag.value == 'Y'){
		var lov = document.getElementById('airportCodelov');
		targetFormName.elements.airportCode.disabled=true;			
		lov.disabled=true;		
		}
		//Added by A-4772 for ICRD-34645 ends here*/
		//-----Added by A-7534 for ICRD-228903---
		var customerTypeValue = targetFormName.elements.customerType.value;
		if(customerTypeValue=='AG'){
			agentprivilegeValidation();				
		}else if(customerTypeValue=='CC'){
			cccollectorprivilegeValidation();				
		}else if(customerTypeValue=='CU' || 
					(customerTypeValue!='AG' && customerTypeValue!='CC' && customerTypeValue!='ML'&& customerTypeValue!='')){
			customerprivilegeValidation();
		}else{
			if(targetFormName.elements.customerCode.value != ""
				&& targetFormName.elements.customerTypecheckFlag.value == 'Y'){
				disableField(targetFormName.elements.customerType);	
			}
		}
		//-------------------------------------
	}

function enableCustomerCheckBox(){

	//var customerTypeCount = targetFormName.elements.customerType.length;			
	//for (var i = 0; i < customerTypeCount; i++) {	
	//	var customerType = targetFormName.elements.customerType[i];		
	//	if(customerType != null){
	//		enableField(customerType) ;
	//	}	
	//}
	enableField(targetFormName.elements.customerType);
}

function uncheckField(location){
		
	if('CONLOC'==location){		
		targetFormName.elements.sellingLocation.checked = false;			
	}else if('SELLOC'==location){
		targetFormName.elements.controllingLocation.checked = false;			
	}
}

function aircraftTypeHandledCheckBoxValidation(statuVal){
	
		var aircraftTypeHandledForDisplay=targetFormName.elements.aircraftTypeHandledList.value;
		var aircraftValues;
		if(aircraftTypeHandledForDisplay!=null && aircraftTypeHandledForDisplay!=""){
			aircraftValues=aircraftTypeHandledForDisplay.split(",");
		}
		var customerTypeCount = targetFormName.elements.customerType.length;			
	var aircraftTypeCount = targetFormName.elements.aircraftTypeHandled.length;
	for(i = 0; i < aircraftTypeCount; i++){
		var aircraftType = targetFormName.elements.aircraftTypeHandled[i];
		if(aircraftType != null && statuVal =='true'){			
			targetFormName.elements.aircraftTypeHandled[i].checked=false;
			disableField(aircraftType);			
		}else if(aircraftType != null && statuVal =='false'){			
			enableField(aircraftType) ;			
				if(aircraftValues!=null){
				var aircraftTypeValues=aircraftValues[i].split("-");
					if(aircraftTypeValues[1]=='Y'){
					targetFormName.elements.aircraftTypeHandled[i].checked=true;
					}
				}
				else{
					targetFormName.elements.aircraftTypeHandled[i].checked=true;
				}
		}
	}
}
//Added by A-5791 for ICRD-59602 starts here
function agentNameonBlur(obj){
	var length1=targetFormName.elements.agentCode.length;
	var  _rowCount =null;
	try{
		_rowCount =obj.getAttribute("rowCount");
	}catch(err){
		return;
	}

	globalObj=obj;
	targetFormName.elements.customerAgentCode.value=obj.value;
	if(targetFormName.elements.customerAgentCode.value== ""){
	targetFormName.elements.agentName[_rowCount].value ="";
	
	}else
	{
    var __extraFn="agentCodeValidationFn";
	asyncSubmit(targetFormName,'customermanagement.defaults.listagentdetails.do',__extraFn,null,null);
	}
}

function agentCodeValidationFn(_tableInfo){
var _rowCount = globalObj.getAttribute("rowCount");
	if(targetFormName.elements.agentName.length>1){
	
	targetFormName.elements.agentName[_rowCount].value=_tableInfo.document.getElementById("_ajax_agentDescription").innerHTML;
}
else{
		targetFormName.elements.agentName.value=_tableInfo.document.getElementById("_ajax_agentDescription").innerHTML;
	}
}

//Added By A-5791 for ICRD-59602 Ends Here

// Added by A-5198 for ICRD-56507
function nonconfirmMessage() {
	
}
function confirmMessage() {
	if(targetFormName.elements.duplicateStockHolder.value == 'Y'){
		targetFormName.elements.duplicateStockHolder.value="";
		submitForm(targetFormName, 'customermanagement.defaults.activatestatusforcustomers.do?duplicateStockHolderOverride=Y');
	}
	clearCustomerRegn();
}
//Added for ICRD-67442 by A-5163 starts
function populateCustomerHistoryDetails(){
	if(targetFormName.elements.sourcePage.value == "LIST"){
	}
	var populateHistory = "N";
	if(targetFormName.elements.isHistoryPopulated.value == "N"){
		/*
		 *  If save operation no need to relist the history details.
		 *  If there is history data it would have been loaded earlier.
		 *	Logic corrected for ICRD-89518.
		 */
		if(targetFormName.elements.sourcePage.value != "SAVE"){			
			populateHistory = "Y";
		}
		if(populateHistory == "Y"){
			var versionNumber = targetFormName.elements.cusVersionNumber.value;
			var sortedArray = new Array();
			sortedArray = navVersionNumberArray.sort(sortingNumeric);
			var extraFn = "updateCustomerAuditVersion";
			var tableDivId = "customerAuditVersions";
			var strAction = "customermanagement.defaults.listcustomerdetailshistory.do?verNo="+versionNumber+"&versionNumbers="+sortedArray;			asyncSubmit(targetFormName,strAction,extraFn,null,null,tableDivId);
		}
	}
}

function sortingNumeric(a,b){ 
        return (a-b);		
}

function updateCustomerAuditVersion(_tableInfo){	
	_filter 	= _tableInfo.document.getElementById("recreatedCustomerAuditVersions");
	document.getElementById("customerAuditVersions").innerHTML = _filter.innerHTML;
}

function disableFormFields(){
	targetFormName.elements.btnSave.disabled = true;
	targetFormName.elements.custName.readOnly = true;
	targetFormName.elements.station.readOnly = true;
	targetFormName.elements.customerShortCode.readOnly = true;
	targetFormName.elements.iataAgentCode.readOnly = true;
	//targetFormName.elements.stopCreditCheck.disabled = true;
	//targetFormName.elements.invoiceToCustomerCheck.disabled = true;
	//targetFormName.elements.vendorCheck.disabled = true;
	targetFormName.elements.establishedDate.disabled = true;
	targetFormName.elements.accountNumber.readOnly = true;
	targetFormName.elements.vatRegNumber.readOnly = true;
	targetFormName.elements.customerGroup.readOnly = true;
	targetFormName.elements.globalCustomer.disabled = true;
	//targetFormName.elements.knownShipperCheck.disabled = true;
	targetFormName.elements.fromDate.disabled = true;
	targetFormName.elements.toDate.disabled = true;
	targetFormName.elements.remarks.readOnly = true;
	targetFormName.elements.customerType.disabled = true;
	targetFormName.elements.address1.readOnly = true;
	targetFormName.elements.address2.readOnly = true;
	targetFormName.elements.city.readOnly = true;
	targetFormName.elements.area.readOnly = true;
	targetFormName.elements.state.readOnly = true;
	targetFormName.elements.country.readOnly = true;
	targetFormName.elements.zipCode.readOnly = true;
	targetFormName.elements.telephone.readOnly = true;
	targetFormName.elements.mobile.readOnly = true;
	targetFormName.elements.fax.readOnly = true;
	targetFormName.elements.sita.readOnly = true;
	targetFormName.elements.email.readOnly = true;
	targetFormName.elements.eoriNo.readOnly = true;
	targetFormName.elements.customsLocationNo.readOnly = true;
	targetFormName.elements.billingStreet.readOnly = true;
	targetFormName.elements.billingLocation.readOnly = true;
	targetFormName.elements.billingCityCode.readOnly = true;
	targetFormName.elements.billingState.readOnly = true;
	targetFormName.elements.billingCountry.readOnly = true;
	targetFormName.elements.billingZipcode.readOnly = true;
	targetFormName.elements.billingTelephone.readOnly = true;
	targetFormName.elements.billingFax.readOnly = true;
	targetFormName.elements.billingEmail.readOnly = true;
	targetFormName.elements.defaultNotifyMode.disabled = true;
	targetFormName.elements.masterRowId.disabled = true;
	var selectedContactDetails = document.getElementsByName("selectedContactDetails");
	var contactCodes = document.getElementsByName("contactCode");
	var contactNames = document.getElementsByName("contactName");
	var contactDesignations = document.getElementsByName("contactDesignation");
	var contactTelephones = document.getElementsByName("contactTelephone");
	var contactMobiles = document.getElementsByName("contactMobile");
	var contactFaxes = document.getElementsByName("contactFax");
	var contactSitas = document.getElementsByName("contactSita");
	var contactEmails = document.getElementsByName("contactEmail");
	var activeChecks = document.getElementsByName("active");
	var primaryContacts = document.getElementsByName("primaryContacts");
	var contactRemarks = document.getElementsByName("contactRemarks");
	if(selectedContactDetails != null){
			for(var i=0; i < selectedContactDetails.length; i++){
				selectedContactDetails[i].disabled = true;
				contactCodes[i].readOnly = true;
				contactNames[i].readOnly = true;
				contactDesignations[i].readOnly = true;
				contactTelephones[i].readOnly = true;
				contactMobiles[i].readOnly = true;
				contactFaxes[i].readOnly = true;
				contactSitas[i].readOnly = true;
				contactEmails[i].readOnly = true;
				activeChecks[i].disabled = true;
				primaryContacts[i].disabled = true;
				contactRemarks[i].readOnly = true;
			}
	}	
	disableLink(document.getElementById('addcontact'));
	disableLink(document.getElementById('deletecontact'));
	disableLink(document.getElementById('addclearingagent'));
	disableLink(document.getElementById('deleteclearingagent'));
	targetFormName.elements.masterRowId1.disabled = true;	
	var selectedClearingAgentDetails = document.getElementsByName("selectedClearingAgentDetails");
	var agentStations = document.getElementsByName("agentStation");
	var airportlovs = document.getElementsByName("airportlov");
	var exportedFlags = document.getElementsByName("exported");
	var importedFlags = document.getElementsByName("imported");
	var salesFlags = document.getElementsByName("sales");
	var agentSccs = document.getElementsByName("agentScc");
	var agentsccLOVs = document.getElementsByName("agentsccLOV");
	var agentCodes = document.getElementsByName("agentCode");
	var agentcodelovs = document.getElementsByName("agentcodelov");
	var agentRemarks = document.getElementsByName("agentRemarks");
	if(selectedClearingAgentDetails != null){
			for(var i=0; i < selectedClearingAgentDetails.length; i++){
				selectedClearingAgentDetails[i].disabled = true;
				agentStations[i].readOnly = true;
				exportedFlags[i].disabled = true;
				importedFlags[i].disabled = true;
				salesFlags[i].disabled = true;
				agentSccs[i].readOnly = true;
				agentCodes[i].readOnly = true;
				agentRemarks[i].readOnly = true;
			}
	}
	targetFormName.elements.agentInformation.disabled = true;
	targetFormName.elements.cassIdentifier.disabled = true;
	targetFormName.elements.cassImportIdentifier.disabled = true;
	targetFormName.elements.salesId.readOnly = true;
	targetFormName.elements.ownSalesFlag.disabled = true;
	targetFormName.elements.holdingCompany.readOnly = true;
	targetFormName.elements.normalComm.readOnly = true;
	targetFormName.elements.normalCommFixed.readOnly = true;
	targetFormName.elements.salesReportAgent.disabled = true;
	targetFormName.elements.controllingLocation.disabled = true;
	targetFormName.elements.sellingLocation.disabled = true;
	targetFormName.elements.proformaInv.disabled = true;
	targetFormName.elements.iataCode.readOnly = true;
	targetFormName.elements.clName.readOnly = true;
	targetFormName.elements.billingTo.disabled = true;
	targetFormName.elements.invoiceClubbingIndicator.disabled = true;
	targetFormName.elements.airportCode.readOnly = true;
	targetFormName.elements.aircraftTypeHandled.disabled = true;
	targetFormName.elements.dateOfExchange.disabled = true;
	targetFormName.elements.cassBillingIndicator.disabled = true;
	targetFormName.elements.cassCode.readOnly = true;
	targetFormName.elements.billingThroughInterline.disabled = true;
	targetFormName.elements.airlineCode.readOnly = true;
	targetFormName.elements.enduranceFlag.disabled = true;
	targetFormName.elements.endurancePercentage.readOnly = true;
	targetFormName.elements.enduranceValue.readOnly = true;
	targetFormName.elements.enduranceMaxValue.readOnly = true;
	targetFormName.elements.settlementCurrency.readOnly = true;
	targetFormName.elements.billingCode.readOnly = true;
	targetFormName.elements.billingPeriodDescription.readOnly = true;
	
	
	/*Added by 201930 for IASCB-131790 start*/
	targetFormName.elements.cassCountry.readOnly = true;
	/*Added by 201930 for IASCB-131790 end*/
	
	targetFormName.elements.billingremark.readOnly = true;
	disableLink(document.getElementById('addCertifications'));
	disableLink(document.getElementById('deleteCertifications'));
	targetFormName.elements.checkAllCertificates.disabled = true;
	var rowIdCertifications 	   = document.getElementsByName("rowIdCertifications");
	var customerCertificateTypes   = document.getElementsByName("customerCertificateType"); 
	var customerCertificateNumbers = document.getElementsByName("customerCertificateNumber");
	var certificateValidFromDates  = document.getElementsByName("certificateValidFrom");
	var certificateValidToDates    = document.getElementsByName("certificateValidTo");
	if(customerCertificateTypes != null){
			for(var i=0; i < customerCertificateTypes.length; i++){
				rowIdCertifications[i].disabled = true;
				customerCertificateTypes[i].disabled = true;
				customerCertificateNumbers[i].readOnly = true;
				certificateValidFromDates[i].disabled = true;
				certificateValidToDates[i].disabled = true;
			}
	}
}
//Added for ICRD-67442 by A-5163 ends
function validateRecipientCode(){
	if(targetFormName.elements.recipientCode.value !="") {
		var fieldLength = targetFormName.elements.recipientCode.value.length;
		if(fieldLength != 11){
			showDialog({	
				msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.max11char' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				onClose:function(){	
			targetFormName.elements.recipientCode.focus();
				}	
			});
			return;
		}
	}
}
//Added by A-7604 by for ICRD-233132
function groupLovDisplay(fld) {
	var frm=targetFormName;
	var groupType = "CUSGRP";
	var groupName="";
	var groupCategory="GEN";
	var groupDescription="";
	showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','N',groupName,groupType,groupCategory,groupDescription,'customerGroup','','1','0');
}
//Added by A-7604 by for ICRD-233132
function showParameterGroupLov(strAction,multiSelect,groupNameVal,groupTypeVal,groupCategory,groupDescription,textfieldObj,textfieldDescription,formNumber,index){
	var objCode = eval("document.forms["+formNumber+"]."+textfieldObj);
	if(objCode.length == null){
		groupNameVal = objCode.value;
	}else if(objCode.length != null){
		groupNameVal = objCode[index].value;
	}
	var strUrl = strAction+"?multiSelect="+multiSelect+"&groupName="+groupNameVal+"&groupType="+groupTypeVal+"&groupCategory="+groupCategory+"&groupDescription="+groupDescription+"&textfieldObj="+textfieldObj+"&textfieldDescription="+textfieldDescription+"&formNumber="+formNumber+"&index="+index;
	openPopUp(strUrl,550,575);
}
function CheckSpace(event){
	event.value =event.value.replace(/\s/g, "");
}

function validateSpecialCharacterFields(formObj){
if(validateSpecialCharacters(formObj.elements.custName)==false){ return false; }
if(validateSpecialCharacters(formObj.elements.address1)==false){ return false; }
if(validateSpecialCharacters(formObj.elements.address2)==false){ return false; }
if(validateSpecialCharacters(formObj.elements.city)==false){ return false; }
if(validateSpecialCharacters(formObj.elements.state)==false){ return false; }
if(validateSpecialCharacters(formObj.elements.remarks)==false){ return false; }
}

function validateSpecialCharacters(fld){
	var val=fld.value;
	var reg=/[^a-zA-Z0-9\*\+\-\_\,\(\)\:\.\@\ \/\r/\n/]+/;
	if(reg.test(val)){ 
		showDialog({	
			msg:"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.invalidchar' scope='request'/>",
			type	:	1, 
			parentWindow:self,
			onClose:function(){	
				fld.focus();
				//IC.util.common.executeFunction(callBack,false);	
				}
    			});
    	return false;
	}               
return true;
}

function showConditionalLov(id,lov){
	var selected=lov.id.split(id)[1];
	var index = selected;	
	var parameter=targetFormName.elements.miscDetailCode[index].value;	
   if(parameter == "STECOD"){   
   	displayOneTimeLOV('screenloadOneTime.do','N','N','screenloadOneTime.do',targetFormName.elements.miscDetailValue[index].value,'','State Code','0','miscDetailValue','miscDetailValue',index,'tariff.tax.statecode','');
   }else if (parameter == "BILARECOD"){
	   displayOneTimeLOV('screenloadOneTime.do','N','Y','screenloadOneTime.do',targetFormName.elements.miscDetailValue[index].value,'','Billing Area','0','miscDetailValue','miscDetailValue',index,'shared.agent.bankdetails.companycodes','');
   }
   else if (parameter == "CMPCOD"){
	   displayOneTimeLOV('screenloadOneTime.do','N','Y','screenloadOneTime.do',targetFormName.elements.miscDetailValue[index].value,'','Company Code','0','miscDetailValue','miscDetailValue',index,'shared.agent.bankdetails.companycodes','');
   }
}

//A-3791 ICRD-242968
function disableMDMMasterDataForKE(){

	if(targetFormName.elements.companyCode.value=="KE"){
		var customerTypeCount = targetFormName.elements.customerType.length;		

		var customerTypeValue = targetFormName.elements.customerType.value;
		
		/*for (var i = 0; i < customerTypeCount; i++) {	
			var customerType = targetFormName.elements.customerType[i];			
			if(customerType != null){
				if(customerType.checked){			
					var customerTypeValue = targetFormName.elements.customerType[i].value;*/
					
					if(customerTypeValue=='AG'){
						targetFormName.elements.customerShortCode.readOnly = true;
						targetFormName.elements.custName.readOnly = true;
						targetFormName.elements.station.readOnly = true;
						
						disableField(targetFormName.elements.customerType);
						
						targetFormName.elements.internalAccountHolder.disabled = true;
						targetFormName.elements.country.readOnly = true;
						
						targetFormName.elements.fromDate.readOnly = true;
						targetFormName.elements.toDate.disabled = true;
						
						//targetFormName.elements.address1.readOnly = true;
						//targetFormName.elements.address2.readOnly = true;
						//targetFormName.elements.email.readOnly = true;
						//targetFormName.elements.mobile.readOnly = true;
						//targetFormName.elements.telephone.readOnly = true;
						//targetFormName.elements.fax.readOnly = true;
						//targetFormName.elements.zipCode.readOnly = true;
						//targetFormName.elements.city.readOnly = true;
						//targetFormName.elements.state.readOnly = true;
						
						//targetFormName.elements.billingCityCode.readOnly = true;
						//targetFormName.elements.billingState.readOnly = true;
						//targetFormName.elements.billingCountry.readOnly = true;
						//targetFormName.elements.billingCode.readOnly = true;
						//targetFormName.elements.billingPeriodDescription.readOnly = true;
						
						var stationlovBtn = document.getElementById('stationlov');
						var validFromCalendar = document.getElementById('btn_validFrom');
						var validToCalendar = document.getElementById('btn_validTo');
						var countrylovBtn = document.getElementById('countrylov');
						var billingcountrylovBtn = document.getElementById('billingcountrylov');
						var billingCurrencyLOVBtn = document.getElementById('billingCurrencyLOV');
						var intaccholderlovBtn = document.getElementById('intaccholderlov');
						var holdingCompanylovBtn = document.getElementById('holdingCompanylov');
						//var customerlovBillingBtn = document.getElementById('customerlovBilling');
						
						disableField(stationlovBtn);
						disableField(validFromCalendar);
						disableField(validToCalendar);
						disableField(countrylovBtn);
						disableField(billingcountrylovBtn);
						disableField(billingCurrencyLOVBtn);
						disableField(intaccholderlovBtn);
						disableField(holdingCompanylovBtn);
						//disableField(customerlovBillingBtn);
						
						//targetFormName.elements.iataCode.readOnly = true;
						targetFormName.elements.normalComm.readOnly = true;
						targetFormName.elements.normalCommFixed.readOnly = true;
						targetFormName.elements.holdingCompany.readOnly = true;
						//targetFormName.elements.iataAgentCode.readOnly = true;
						targetFormName.elements.billingCode.readOnly = true;
						targetFormName.elements.cassIdentifier.disabled = true;
						
						//disableField(targetFormName.elements.ownSalesFlag);
						disableField(targetFormName.elements.agentInformation);
						disableField(targetFormName.elements.cassIdentifier);
						//disableField(targetFormName.elements.billingPeriod);
						
						disableField(targetFormName.elements.iataAgentCode);
					}
			/*	}
			}
		}*/
	}
}
//Added for IASCB-130291
function onClickGroupList(){
	var frm=targetFormName;
	var strUrl="customermanagement.defaults.showgroupdetailsforcustomer.do"+"?customerCode="+targetFormName.elements.customerCode.value;     
     openPopUp(strUrl,'850','650');
}
//Added as part of CR ICRD-253447 by A-8154 starts
function onClickActivate(){
	var frm=targetFormName;
	if(targetFormName.elements.customerType.value == "AG" && frm.elements.stockAutomationRequired.value=="Y"){
	if(frm.elements.status[0].value == "A" ){
		showDialog({	
			msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.createstockholder' scope='request'/>",
			type	:	4, 
			parentWindow:self,
			parentForm:targetFormName,
			dialogId:'id_4',
			onClose: function (result) {
					screenConfirmDialog(targetFormName,'id_4');
			}
		});
		return;
		} else{
	showDialog({	
		msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.activatecustomer' scope='request'/>",
		type	:	4, 
		parentWindow:self,
		parentForm:targetFormName,
		dialogId:'id_2',
		onClose: function (result) {
			screenConfirmDialog(targetFormName,'id_2');
		}
	});
}
	}
	else{
		if(frm.elements.status[0].value == "A"){
			showDialog({	
				msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.customeractive' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
			});
			return;
		} else{
			showDialog({	
				msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.activatecustomer' scope='request'/>",
				type	:	4, 
				parentWindow:self,
				parentForm:targetFormName,
				dialogId:'id_6',
				onClose: function (result) {
					screenConfirmDialog(targetFormName,'id_6');
				}
			});
		}
	}
}
function screenConfirmDialog(frm, dialogId) {
	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_2'){
			showDialog({	
				msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.createstockholderforagent' scope='request'/>",
				type	:	4, 
				parentWindow:self,
				parentForm:targetFormName,
				dialogId:'id_4',
				onClose: function (result) {
					screenConfirmDialog(targetFormName,'id_4');
					screenNonConfirmDialog(targetFormName,'id_4');
				}
			});
 		}
		if(dialogId == 'id_4'){
			frm.elements.stockAutomationUserConfirmed.value='Y';
			frm.elements.duplicateStockHolderOverride.value='N';
 			submitForm(frm,"customermanagement.defaults.activatestatusforcustomers.do");
		}
		if(dialogId == 'id_6'){
 			submitForm(frm,"customermanagement.defaults.activatestatusforcustomers.do");
		}
	}
}
function screenNonConfirmDialog(frm, dialogId) {
	if(frm.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_4'){
			frm.elements.stockAutomationUserConfirmed.value='N';
 			submitForm(frm,"customermanagement.defaults.activatestatusforcustomers.do");
		}
	}
}
//Added as part of CR ICRD-253447 by A-8154 ends
//Added by A-7978 for ICRD-235959 starts
function checkChange(obj,index) {

	if(obj.checked) {
		document.getElementsByName('preferenceValue')[index].value='Y';
	} else {
		document.getElementsByName('preferenceValue')[index].value='N';
	}
}
//Added by A-7978 for ICRD-235959 ends
function checkCustomerTypeForKE(field) {
	if(field.value == "AG") {
		showDialog({msg	:"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.creationnotallowed' scope='request'/>",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName
			});	
			
			jQuery(field)[0].value="";
	}
}//Added by A-7359 for ICRD-294843 starts
 function validateMaxLengthofRemarks(txtObj,maxLength)
 {
 	if(txtObj.value.length > maxLength) {
		showDialog({	
				msg		:	"<common:message bundle='maintainregcustomerform' key='customermanagement.defaults.customerregistration.warn.enterlessthan' scope='request'/> "+maxLength,
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
			});
			txtObj.focus();
			return false;
 	}
 	return true;
}
//Added by A-7359 for ICRD-294843 ends

/*by A-7567 for ICRD-305684*/
	function validateApplicableToField(){
		if(targetFormName.elements.billingTo.value=='C'){
		enableField(document.getElementsByName('cntLocBillingApplicableTo'));
	}else{
		document.getElementsByName('cntLocBillingApplicableTo')[0].value = "";
		disableField(document.getElementsByName('cntLocBillingApplicableTo'));
	}
	}