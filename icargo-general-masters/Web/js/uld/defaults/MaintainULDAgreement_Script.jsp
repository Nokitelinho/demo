<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
var _partyArr = new Hashtable();
function screenSpecificEventRegister(){
	var frm=targetFormName;
	//frm.agreementNumber.focus();
	//onScreenloadSetHeight();
	with(frm){
		evtHandler.addEvents("demurrageRate","checkValue()",EVT_BLUR);
		evtHandler.addEvents("taxes","checkValue()",EVT_BLUR);
		evtHandler.addEvents("partyCode","onPartyChange()",EVT_BLUR);
		evtHandler.addEvents("fromPartyCode","onFromPartyChange()",EVT_BLUR);
		evtHandler.addEvents("toAirlineLov","onclickLOV()",EVT_CLICK);
		evtHandler.addEvents("fromAirlineLov","onclickFromLOV()",EVT_CLICK);
		evtHandler.addIDEvents("customerlov","showTempIDLOV()",EVT_CLICK);
		evtHandler.addEvents("partyType","changed()",EVT_CHANGE);
		evtHandler.addEvents("fromPartyType","fromPartyTypeChanged()",EVT_CHANGE);
		evtHandler.addEvents("btnList","onClickList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","onClickClear()",EVT_CLICK);
		evtHandler.addEvents("btnSave","OnClickSave()",EVT_CLICK);
		evtHandler.addEvents("btnClose","onClickClose()",EVT_CLICK);
		evtHandler.addEvents("btnClose","resetFocus(this.form)",EVT_BLUR);
		evtHandler.addEvents("btnPrint","onClickPrint()",EVT_CLICK);
		evtHandler.addIDEvents("createlink","addAgreement(this.form)",EVT_CLICK);
		evtHandler.addIDEvents("modifylink","modifyUldDetails()",EVT_CLICK);
		evtHandler.addIDEvents("deletelink","onClickDelete()",EVT_CLICK);
		evtHandler.addEvents("freeLoanPeriod","validateFields(targetFormName.elements.freeLoanPeriod,-1,'Free Loan Period',3,true,true)",EVT_BLUR);
		evtHandler.addEvents("demurrageRate","validateFields(targetFormName.elements.demurrageRate,-1,'Demurrage Rate',2,true,true,12,4)",EVT_BLUR);
		evtHandler.addEvents("taxes","validateFields(targetFormName.elements.taxes,-1,'Tax Percentage',2,true,true,12,4)",EVT_BLUR);
		evtHandler.addIDEvents("agrlov","showAgreementNoLov()",EVT_CLICK);
		
		evtHandler.addEvents("allParties","onAllPartiesChange(false)",EVT_CLICK);
		evtHandler.addEvents("fromAllParties","onFromAllPartiesChange(false)",EVT_CLICK);
		//evtHandler.addIDEvents("airlinelov","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.partyCode.value,'partyCode','1','partyCode','partyName',0)",EVT_CLICK);
		//evtHandler.addEvents("partyCode","validateAlphanumeric(targetFormName.partyCode,'Party Code','Invalid Party Code',true)",EVT_BLUR);
		evtHandler.addEvents("agreementNumber","validateAlphanumeric(targetFormName.elements.agreementNumber,'Agreement Number','Invalid Agreement Number',true)",EVT_BLUR);
		evtHandler.addEvents("remarks","validateMaximumLength(targetFormName.elements.remarks,500,'Remarks','Invalid Remarks',true)",EVT_BLUR);

		evtHandler.addEvents("masterRowId","updateHeaderCheckBox(this.form,targetFormName.elements.masterRowId,targetFormName.elements.selectedChecks)",EVT_CLICK);
	    evtHandler.addEvents("selectedChecks","toggleTableHeaderCheckbox('selectedChecks',targetFormName.elements.masterRowId)",EVT_CLICK);
		evtHandler.addEvents("currencyCode","validateAlphanumeric(targetFormName.elements.currencyCode,'CurrencyCode','Invalid Currency Code',true)",EVT_BLUR);
		evtHandler.addEvents("currencyLov","displayLOV('showCurrency.do','N','Y','showCurrency.do',document.forms[1].currencyCode.value,'CurrencyCode','1','currencyCode','',0)",EVT_CLICK);
		//added by a-3278 for bug 25164 on 13Nov08
		evtHandler.addEvents("partyCode","populatePartyName()",EVT_BLUR);
		evtHandler.addEvents("fromPartyCode","populateFromPartyName()",EVT_BLUR);
		//a-3278 ends
		//Added by A-8445
evtHandler.addIDEvents("uldlov","displayLOV('showUld.do','N','Y','showUld.do',targetFormName.elements.uldTypeFilter.value,'uldTypeFilter','0','uldTypeFilter','',0)",EVT_CLICK);
		evtHandler.addEvents("btnListFilter","onClickListFilter()",EVT_CLICK);
		onLoad();

	}
}
function onScreenloadSetHeight(){
 	jquery('#div1').height((((document.body.clientHeight)*0.79)-400));
	
}
function showTempIDLOV(){
//alert("val"+targetFormName.partyCode.value);
if(targetFormName.elements.fromPartyCode.value =="ALL CUSTOMERS"){
	targetFormName.elements.fromPartyCode.value="";
}
//displayLOVCodeNameAndDesc('showCustomer.do','N','Y','showCustomer.do',targetFormName.partyCode.value,'customerCode','1','partyCode','',0)	
//displayLOV('showCustomer.do','N','Y','showCustomer.do',targetFormName.elements.partyCode.value,'customerCode','1','partyCode','',0)
			var fromPartyCode=targetFormName.elements.fromPartyCode.value;
			var StrUrl = "showCustomer.do?multiselect=N&pagination=Y&lovaction=showCustomer.do&code="+fromPartyCode+"&title=customerCode&formCount=1&lovTxtFieldName=fromPartyCode&lovDescriptionTxtFieldName=";
			var options = {url:StrUrl}
			var myWindow = CustomLovHandler.invokeCustomerLov(options);
if(targetFormName.elements.fromPartyCode.value==""){
targetFormName.elements.fromPartyCode.value ="ALL CUSTOMERS";
targetFormName.elements.fromPartyName.value=""
}
// if(targetFormName.partyCode.value=="ALL CUSTOMERS"){
		// var code="";
	// }else{
		// var code=targetFormName.partyCode.value;
	// }
	// // var strUrl = mainAction+"multiselect="+strMultiselect+"&pagination="+strPagination+"&lovaction="+
			// // strAction+"&code="+encodedStrCode+"&title="+strTitle+"&formCount="+currentFormCount+"&lovTxtFieldName="+
			// // lovValueTxtFieldName+"&lovDescriptionTxtFieldName="+strLovDescriptionTxtFieldName+"&index="+index;
			
	// var strAction="showCustomer.do";
	// var textfiledDesc="";
	// var multiselect = "N";
	// var StrUrl=strAction+"multiselect="+multiselect+"textfiledObj=partyCode&formNumber=1&textfiledDesc=partyName&customerCode="+code;
	// var myWindow = openPopUp(StrUrl, "500","400");

}
function showTempIDLOVto(){
//alert("val"+targetFormName.partyCode.value);
if(targetFormName.elements.partyCode.value =="ALL CUSTOMERS"){
	targetFormName.elements.partyCode.value="";
}
//displayLOVCodeNameAndDesc('showCustomer.do','N','Y','showCustomer.do',targetFormName.partyCode.value,'customerCode','1','partyCode','',0)	
//displayLOV('showCustomer.do','N','Y','showCustomer.do',targetFormName.elements.partyCode.value,'customerCode','1','partyCode','',0)
			var partyCode=targetFormName.elements.partyCode.value;
			var StrUrl = "showCustomer.do?multiselect=N&pagination=Y&lovaction=showCustomer.do&code="+partyCode+"&title=customerCode&formCount=1&lovTxtFieldName=partyCode&lovDescriptionTxtFieldName=";
			var options = {url:StrUrl}
			var myWindow = CustomLovHandler.invokeCustomerLov(options);
if(targetFormName.elements.partyCode.value==""){
targetFormName.elements.partyCode.value ="ALL CUSTOMERS";
targetFormName.elements.partyName.value=""
}
// if(targetFormName.partyCode.value=="ALL CUSTOMERS"){
		// var code="";
	// }else{
		// var code=targetFormName.partyCode.value;
	// }
	// // var strUrl = mainAction+"multiselect="+strMultiselect+"&pagination="+strPagination+"&lovaction="+
			// // strAction+"&code="+encodedStrCode+"&title="+strTitle+"&formCount="+currentFormCount+"&lovTxtFieldName="+
			// // lovValueTxtFieldName+"&lovDescriptionTxtFieldName="+strLovDescriptionTxtFieldName+"&index="+index;
			
	// var strAction="showCustomer.do";
	// var textfiledDesc="";
	// var multiselect = "N";
	// var StrUrl=strAction+"multiselect="+multiselect+"textfiledObj=partyCode&formNumber=1&textfiledDesc=partyName&customerCode="+code;
	// var myWindow = openPopUp(StrUrl, "500","400");

}
function resetFocus(frm){
	 if(!event.shiftKey){ 
				if((!frm.elements.agreementNumber.disabled) 
					&& (frm.elements.agreementNumber.readOnly == false)){
					frm.elements.agreementNumber.focus();
				}
				else{
					if((frm.elements.agreementStatus[0].value != 'D')
						&&(!frm.elements.partyType.disabled)){
						frm.elements.partyType.focus();	
					}
					else if(!frm.elements.masterRowId.disabled){
							frm.elements.masterRowId.focus();
					}								
				}				
	}	
}

function checkValue(){
	if(targetFormName.elements.demurrageRate.value < 0){
		//showDialog('Cannot Enter Negative value for Demurrage Rate',1,self);
		showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.err.invaliddemurragerate" scope="request"/>',1,self);
		
		targetFormName.elements.demurrageRate.value ="";
		targetFormName.elements.demurrageRate.focus();
		return;
	}
	if(targetFormName.elements.taxes.value < 0){
			//showDialog('Cannot Enter Negative value for Tax Percentage',1,self);
			showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.err.invalidtaxpercentage" scope="request"/>',1,self);
			targetFormName.elements.taxes.value ="";
			targetFormName.elements.taxes.focus();
			return;
	}
	// Added by Preet on 3 rd Apr for AirNZ 517 starts
	var taxes =targetFormName.elements.taxes.value;
	if(taxes.length != 0 && taxes>100){
		//showDialog('Tax Percentage cannot exceed 100.',1,self);
		showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.err.taxpercentagecantexceed100" scope="request"/>',1,self);
		return;
	}
	if(taxes.length != 0){
	
		if(taxes.indexOf(".") == 2 && taxes.length ==6){
			targetFormName.elements.taxes.focus();
			//showDialog('Taxes can have only 2 places of decimal.',1,self);
			showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.err.taxallows2placesofdecimal" scope="request"/>',1,self);
			return;
					
			}
	}
	// Added by Preet on 3 rd Apr for AirNZ 517 ends
	
}

function partyCodeValidate(){
var frm=targetFormName;

	if(frm.elements.comboFlag.value=="airlinelov"){
		validateMaxLength(frm.elements.partyCode,3);
	}else if(frm.elements.comboFlag.value=="agentlov"){
		validateMaxLength(frm.elements.partyCode,12);
	}else if(frm.elements.comboFlag.value=="others"){
		validateMaxLength(frm.elements.partyCode,12);
	}else if(frm.elements.comboFlag.value==""){
		validateMaxLength(frm.elements.partyCode,3);
	}

}

function fromPartyTypeChanged(){
	frm=targetFormName;
	//alert("change");
	//Modified by A-5198 as part of ICRD-34626	
	frm.elements.fromAllParties.checked = false;
	frm.elements.fromPartyCode.value="";
	frm.elements.fromPartyName.value="";
	frm.elements.fromPartyCode.readOnly=false;
	frm.elements.fromPartyName.readOnly=false;
	frm.elements.comboFlag.value=""
	enableField(frm.elements.fromPartyCode);
	enableField(frm.elements.fromPartyName);
	enableField(frm.elements.fromAllParties);
	if(frm.elements.fromPartyType.value=="G"){
		frm.elements.comboFlag.value="agentlovf";
		enableField(frm.elements.fromAirlinelov);
	}
	else if(frm.elements.fromPartyType.value=="A"){
		frm.elements.comboFlag.value="airlinelov";
		enableField(frm.elements.fromAirlinelov);
		//enableField(frm.elements.airlinelov);
	}else if(frm.elements.fromPartyType.value==""){
		frm.elements.comboFlag.value="airlinelov";
		enableField(frm.elements.airlinelov);
	}else if(frm.elements.fromPartyType.value=="L"){
		frm.elements.fromAllParties.checked = true;
		disableField(frm.elements.fromAirlinelov);
		frm.elements.fromPartyCode.value="ALL";
		frm.elements.fromPartyName.value="FOR ALL PARTIES";
		frm.elements.comboFlag.value="ALLF";
		//frm.partyCode.readOnly=true;
		//frm.partyName.readOnly=true;
		disableField(frm.elements.fromPartyCode);
		//disableField(frm.elements.fromAirlinelov);
		disableField(frm.elements.fromPartyName);
		disableField(frm.elements.fromAllParties);
	}else if(frm.elements.fromPartyType.value=="C"){
		frm.elements.comboFlag.value="customerlovf";
		enableField(frm.elements.customerlov);
	
	}else if(frm.elements.fromPartyType.value=="O"){
		//frm.elements.comboFlag.value="otherslovf";
		disableField(frm.elements.partyCode);
		disableField(frm.elements.fromPartyCode);
		frm.elements.comboFlag.value="otherslovf"
	}
	else{
		frm.elements.comboFlag.value="airlinelov";
		enableField(frm.elements.fromAirlinelov);
	}
	}
function changed(){
	frm=targetFormName;
	//alert("change");
	//Modified by A-5198 as part of ICRD-34626	
	frm.elements.allParties.checked = false;
	frm.elements.partyCode.value="";
	frm.elements.partyName.value="";
	frm.elements.partyCode.readOnly=false;
	frm.elements.partyName.readOnly=false;
	enableField(frm.elements.partyCode);
	enableField(frm.elements.partyName);
	enableField(frm.elements.allParties);
	if(frm.elements.partyType.value=="G"){
		frm.elements.toComboFlag.value="agentlov";
		enableField(frm.elements.toAirlinelov);
	}
	/* Commented by A-5198 as part of ICRD-34626	
	else if(frm.partyType.value=="O"){
		if(frm.allParties.checked){
			// Added by Preet on 3rd Apr for AirNZ 517 -starts
			frm.partyCode.value="ALL OTHERS";
			frm.partyName.value="ALL OTHERS";		
			// Added by Preet on 3rd Apr for AirNZ 517 -ends
		}
		else{
			frm.partyCode.value="";
			frm.partyName.value="";
		}
		
		enableField(frm.airlinelov);
		frm.toComboFlag.value="others";
		enableField(frm.airlinelov);
		//frm.partyCode.value="";
		frm.partyCode.readOnly=false;
		//frm.partyName.value="";
		frm.partyName.readOnly=false;
	}else if(frm.partyType.value=="C"){
		if(frm.allParties.checked){
			// Added by Preet on 3rd Apr for AirNZ 517 -starts
			frm.partyCode.value="ALL CUSTOMERS";
			frm.partyName.value="ALL CUSTOMERS";		
			// Added by Preet on 3rd Apr for AirNZ 517 -ends
		}
		else{
			frm.partyCode.value="";
			frm.partyName.value="";
		}
		
		frm.toComboFlag.value="customerlov";
		enableField(frm.airlinelov);
		//frm.partyCode.value="";
		frm.partyCode.readOnly=false;
		//frm.partyName.value="";
		frm.partyName.readOnly=false;
	}
	*/
	else if(frm.elements.partyType.value=="A"){
		frm.elements.toComboFlag.value="airlinelov";
		enableField(frm.elements.toAirlineLov);
		
	}else if(frm.elements.partyType.value==""){
		frm.elements.toComboFlag.value="airlinelov";
		enableField(frm.elements.toAirlineLov);
	}else if(frm.elements.partyType.value=="L"){
		frm.elements.allParties.checked = true;
		disableField(frm.elements.toAirlineLov);
		frm.elements.partyCode.value="ALL";
		frm.elements.partyName.value="FOR ALL PARTIES";
		frm.elements.toComboFlag.value="ALL";
		//frm.partyCode.readOnly=true;
		//frm.partyName.readOnly=true;
		
		disableField(frm.elements.partyCode);
		disableField(frm.elements.toAirlineLov);
		disableField(frm.elements.partyName);
		disableField(frm.elements.allParties);
	}else if(frm.elements.partyType.value=="C"){
		frm.elements.toComboFlag.value="customerlov";
		enableField(frm.elements.customerlov);
		
	}
	else if(frm.elements.partyType.value=="O"){
		//frm.elements.toComboFlag.value="customerlov";
		disableField(frm.elements.fromPartyCode);
		disableField(frm.elements.partyCode);
		//disableField(document.getElementById('toAirlineLov'));
		frm.elements.toComboFlag.value="otherslovf"
		
	}
	else{
		frm.elements.toComboFlag.value="airlinelov";
		enableField(frm.elements.toAirlineLov);
	}
	}
//Added by A-5198 as part of ICRD-34626	
function onAllPartiesChange(isOnScreenLoad){
	frm=targetFormName;
	//alert(isOnScreenLoad+"  "+frm.partyType.value);
	//alert("onAllPartiesChange() fired "+frm.allParties.checked);
	if(isOnScreenLoad){
		if(frm.elements.partyType.value=="L"){
			frm.elements.allParties.checked = true;
			disableField(frm.elements.allParties);
		}
	}
	if(frm.elements.allParties.checked){
		showPartyCode();
		frm.elements.partyCode.readOnly=true;
		frm.elements.partyName.readOnly=true;
		disableField(frm.elements.partyCode);
		disableField(frm.elements.airlinelov);
		disableField(frm.elements.partyName);
	}
	else if(!isOnScreenLoad){
		frm.elements.partyCode.readOnly=false;
		frm.elements.partyName.readOnly=false;
		enableField(frm.elements.partyCode);
		enableField(frm.elements.airlinelov);
		enableField(frm.elements.partyName);
		frm.elements.partyCode.value="";
		frm.elements.partyName.value="";
	}
}

function onFromAllPartiesChange(isOnScreenLoad){
	frm=targetFormName;
	//alert(isOnScreenLoad+"  "+frm.partyType.value);
	//alert("onAllPartiesChange() fired "+frm.allParties.checked);
	if(isOnScreenLoad){
		if(frm.elements.fromPartyType.value=="L"){
			frm.elements.fromAllParties.checked = true;
			disableField(frm.elements.fromAllParties);
		}
	}
	if(frm.elements.fromAllParties.checked){
		showFromPartyCode();
		frm.elements.fromPartyCode.readOnly=true;
		frm.elements.fromPartyName.readOnly=true;
		disableField(frm.elements.fromPartyCode);
		//disableField(frm.elements.airlinelov);
		disableField(frm.elements.fromPartyName);
	}
	else if(!isOnScreenLoad){
		frm.elements.fromPartyCode.readOnly=false;
		frm.elements.fromPartyName.readOnly=false;
		enableField(frm.elements.fromPartyCode);
		enableField(frm.elements.airlinelov);
		enableField(frm.elements.fromPartyName);
		frm.elements.fromPartyCode.value="";
		frm.elements.fromPartyName.value="";
	}
}
function onclickLOV(){
	frm=targetFormName;
	if(frm.elements.toComboFlag.value=="agentlov"){
		enableField(frm.elements.airlinelov);
		showLOV();
	}else if(frm.elements.toComboFlag.value=="agentlov"){
		disableField(frm.elements.airlinelov);
	}else if(frm.elements.toComboFlag.value=="airlinelov"){
		enableField(frm.elements.airlinelov);
		displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.partyCode.value,'partyCode','1','partyCode','partyName',0)
	showAirlineLOV();
	}else if(frm.elements.toComboFlag.value==""){
		enableField(frm.elements.airlinelov);
		displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.partyCode.value,'partyCode','1','partyCode','partyName',0)
	showAirlineLOV();
	}else if(frm.elements.toComboFlag.value=="customerlov"){
	showTempIDLOVto();
	}
}
function showAirlineLOV(){
		if(targetFormName.elements.partyCode.value=="ALL AIRLINES"){
			var strCode="";
		}else{
			var strCode=targetFormName.elements.partyCode.value;
		}
		var strAction="showAirline.do";
		var textfiledDesc="";
		var strMultiselect='N';
		var strPagination='Y';
		lovaction="showAirline.do";
		var encodedStrCode = encodeURIComponent(strCode);
		var strTitle="Airline LOV";
		var currentFormCount=1;
		var lovValueTxtFieldName="partyCode";
		var strLovDescriptionTxtFieldName="partyName";
		var index=0;
		var strUrl = strAction+"?multiselect="+strMultiselect+"&pagination="+strPagination+"&lovaction="+
			strAction+"&code="+encodedStrCode+"&title="+strTitle+"&formCount="+currentFormCount+"&lovTxtFieldName="+
			lovValueTxtFieldName+"&lovDescriptionTxtFieldName="+strLovDescriptionTxtFieldName+"&index="+index;
		openPopUp(strUrl,"500","350");
}
function onPartyChange(){
		if(targetFormName.elements.partyCode.value!="ALL AGENTS" && targetFormName.elements.partyCode.value!="ALL AIRLINES" &&targetFormName.elements.partyCode.value!="ALL CUSTOMERS"){
			if(targetFormName.elements.partyName.value=="ALL AGENTS" || targetFormName.elements.partyName.value=="ALL AIRLINES" ||targetFormName.elements.partyName.value=="ALL CUSTOMERS"){
				targetFormName.elements.partyName.value = "";
			}
		}
}
function onFromPartyChange(){
		if(targetFormName.elements.fromPartyCode.value!="ALL AGENTS" && targetFormName.elements.fromPartyCode.value!="ALL AIRLINES" &&targetFormName.elements.fromPartyCode.value!="ALL CUSTOMERS"){
			if(targetFormName.elements.fromPartyName.value=="ALL AGENTS" || targetFormName.elements.fromPartyName.value=="ALL AIRLINES" ||targetFormName.elements.fromPartyName.value=="ALL CUSTOMERS"){
				targetFormName.elements.fromPartyName.value = "";
			}
		}
}
function showLOV(){

	if(targetFormName.elements.partyCode.value=="ALL AGENTS"){
		var code="";
	}else{
		var code=targetFormName.elements.partyCode.value;
	}
	var strAction="shared.defaults.agent.screenloadagentlov.do";
	var textfiledDesc="";
	var StrUrl=strAction+"?textfiledObj=partyCode&formNumber=1&textfiledDesc=partyName&agentCode="+code;
	var myWindow = openPopUp(StrUrl, "500","400");

}

function addAgreement(frm){
var frm=targetFormName;
if(frm.elements.agreementDate.value != "" &&
	!chkdate(frm.elements.agreementDate)){

	//showDialog("Invalid Agreement Date", 1, self, frm, 'id_3');
	showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.err.invalidagreementdate" scope="request"/>',1, self, frm, 'id_3');
	
	return;
}

if(frm.elements.fromDate.value != "" &&
	!chkdate(frm.elements.fromDate)){

	//showDialog("Invalid From Date", 1, self, frm, 'id_4');
	showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.err.invalidfromdate" scope="request"/>',1, self, frm, 'id_4');
	return;
}

if(frm.elements.toDate.value != "" &&
	!chkdate(frm.elements.toDate)){

	//showDialog("Invalid To Date", 1, self, frm, 'id_5');
	showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.err.invalidtodate" scope="request"/>',1, self, frm, 'id_5');
	return;
}
var fromDate=targetFormName.elements.fromDate.value;
var toDate=targetFormName.elements.toDate.value;
var status="screen_mode_create";
if(fromDate==""){
//showDialog('Please Enter Agreement From Date', 1, self, frm, 'id_2');
showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.err.enteragreementfromdate" scope="request"/>',1, self, frm, 'id_2');
return;
	}
	else{
    targetFormName.elements.uldTypeFilterWarningFlag.value="true";
	var strActn="uld.defaults.screenloadadduldagreement.do?fromDate="+fromDate+"&toDate="+toDate+"&actionStatus="+status;

	openPopUp(strActn,620,500);
		}
}

function onClickClear(){
var frm=targetFormName;
targetFormName.elements.uldTypeFilterWarningFlag.value="false";
var path="uld.defaults.clearuldagreement.do";
//submitForm(frm,path);
submitFormWithUnsaveCheck(path);
}

function modifyUldDetails(){
var frm=targetFormName;
if(frm.elements.agreementDate.value != "" &&
	!chkdate(frm.elements.agreementDate)){

	//showDialog("Invalid Agreement Date", 1, self, frm, 'id_3');
	showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.err.invalidagreementdate" scope="request"/>',1, self, frm, 'id_3');
	return;
}

if(frm.elements.fromDate.value != "" &&
	!chkdate(frm.elements.fromDate)){

	//showDialog("Invalid From Date", 1, self, frm, 'id_4');
	showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.err.invalidfromdate" scope="request"/>',1, self, frm, 'id_4');
	return;
}

if(frm.elements.toDate.value != "" &&
	!chkdate(frm.elements.toDate)){

	//showDialog("Invalid To Date", 1, self, frm, 'id_5');
	showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.err.invalidtodate" scope="request"/>',1, self, frm, 'id_5');
	return;
}

if(validateSelectedCheckBoxes(frm,'selectedChecks',1000000000000,1)){
var fromDate=targetFormName.fromDate.value;
var toDate=targetFormName.toDate.value;
var selectedBoxes = frm.elements.selectedChecks;
var selected = '';
if(selectedBoxes.length > 1) {
	for(i = 0; i < selectedBoxes.length ; i++) {
		if(selectedBoxes[i].checked) {
            selected = selected + 'selectedChecks='+selectedBoxes[i].value+"&";
		}
	}
} else {
if(selectedBoxes.checked) {
			selected = 'selectedChecks='+selectedBoxes.value+"&";
		}
}

selected=selected.substring(0,(selected.length-1));
targetFormName.elements.uldTypeFilterWarningFlag.value="true";
var status="screen_mode_modify";
var strActn="uld.defaults.modifyuldagreement.do?" + selected + "&fromDate="+fromDate+"&toDate="+toDate+"&actionStatus="+status+"&XY="+Math.random()*123456+new Date();

openPopUp(strActn,620,500);
}
}




function OnClickOK(frm){
var path="uld.defaults.selectagreementdetails.do";
submitForm(frm,path);
}

function onClickList(){
var frm=targetFormName;
targetFormName.elements.uldTypeFilter.value="";
targetFormName.elements.uldTypeFilterWarningFlag.value="false";
submitForm(frm,"uld.defaults.finduldagreementdetails.do");
}

//Added by A-8445 as a part of IASCB-28460 Starts
function submitList(strLastPageNum,strDisplayPage){
	
	var listFilterWarningFlag = targetFormName.elements.uldTypeFilterWarningFlag.value;
	if(listFilterWarningFlag=="true"){
		showDialog({msg:'Unsaved data exists. Do you want to continue ?',type :4, parentWindow:self,parentForm:frm,dialogId:'id_1',
							onClose:function(){
							screenWarningConfirmDialog(frm,strLastPageNum,strDisplayPage,'NAVIGATION','id_1');
							screenWarningNonConfirmDialog(frm,'id_1');
							}
		});
	} else {
		targetFormName.elements.lastPageNumStr.value= strLastPageNum;
		targetFormName.elements.displayPageNumStr.value = strDisplayPage;
		targetFormName.action ="uld.defaults.finduldagreementdetails.do?navigationMode=NAVIGATION";
		targetFormName.submit();
	}
}

function onClickListFilter(){
	var frm=targetFormName;
	var strLastPageNum=targetFormName.elements.lastPageNumStr.value;
	var strDisplayPage=targetFormName.elements.displayPageNumStr.value;
	var listFilterWarningFlag = targetFormName.elements.uldTypeFilterWarningFlag.value;
	if(listFilterWarningFlag=="true"){
		showDialog({msg:'Unsaved data exists. Do you want to continue ?',type :4, parentWindow:self,parentForm:frm,dialogId:'id_1',
							onClose:function(){
							screenWarningConfirmDialog(frm,strLastPageNum,strDisplayPage,'LIST','id_1');
							screenWarningNonConfirmDialog(frm,'id_1');
							}
		});
	} else {
		submitForm(frm,"uld.defaults.finduldagreementdetails.do");
	}
}

function screenWarningConfirmDialog(frm,strLastPageNum,strDisplayPage,navigationMode,dialogId){
	while(frm.elements.currentDialogId.value == ''){
		 targetFormName.elements.uldTypeFilter.value="";
	}
	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			if(navigationMode=='NAVIGATION'){
				targetFormName.elements.uldTypeFilterWarningFlag.value="false";
				targetFormName.elements.lastPageNumStr.value= strLastPageNum;
				targetFormName.elements.displayPageNumStr.value = strDisplayPage;
				targetFormName.action ="uld.defaults.finduldagreementdetails.do?navigationMode=NAVIGATION";
				targetFormName.submit();
			} else {
				var frm=targetFormName;
				targetFormName.elements.uldTypeFilterWarningFlag.value="false";
				submitForm(frm,"uld.defaults.finduldagreementdetails.do");
			}
		}
	}
}

function screenWarningNonConfirmDialog(frm,dialogId){
targetFormName.elements.uldTypeFilter.value="";
}

function screenPageWarningConfirmDialog(frm,dialogId){
	while(frm.elements.currentDialogId.value == ''){
		 
	}
	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){ 
		 targetFormName.elements.uldTypeFilterWarningFlag.value="false";
		 targetFormName.elements.lastPageNumStr.value= strLastPageNum;
		 targetFormName.elements.displayPageNumStr.value = strDisplayPage;
		 targetFormName.action ="uld.defaults.finduldagreementdetails.do?navigationMode=NAVIGATION";
		 targetFormName.submit();
		}
	}
}

function screenPageWarningNonConfirmDialog(frm,dialogId){

}
//Added by A-8445 as a part of IASCB-28460 Ends

function singleSelectCb(frm,checkVal,type1)
{


	for(var i=0;i<frm.elements.length;i++)
	{
		if((frm.elements[i].type =='checkbox' && frm.elements[i].name == type1))
		{

			if(frm.elements[i].checked == true)
			{
				if(frm.elements[i].value != checkVal)
				{


					frm.elements[i].checked = false;
				}
			}
		}
	}
}




function OnClickSave(){
var frm=targetFormName;
if(frm.elements.agreementDate.value != "" &&
	!chkdate(frm.elements.agreementDate)){

	//showDialog("Invalid Agreement Date", 1, self, frm, 'id_3');
	showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.err.invalidagreementdate" scope="request"/>',1, self, frm, 'id_3');

	return;
}

if(frm.elements.fromDate.value != "" &&
	!chkdate(frm.elements.fromDate)){

	//showDialog("Invalid From Date", 1, self, frm, 'id_4');
	showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.err.invalidfromdate" scope="request"/>',1, self, frm, 'id_4');
	return;
}

if(frm.elements.toDate.value != "" &&
	!chkdate(frm.elements.toDate)){

	//showDialog("Invalid To Date", 1, self, frm, 'id_5');
	showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.err.invalidtodate" scope="request"/>',1, self, frm, 'id_5');
	return;
}

// Added by Preet on 3 rd Apr for AirNZ 517 starts
	var taxes =targetFormName.taxes.value;
	if(taxes.length != 0 && taxes>100){
		//showDialog('Tax Percentage cannot exceed 100.',1,self);
		showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.err.taxpercentagecantexceed100" scope="request"/>',1,self);
		return;
	}
	if(taxes.length != 0){
	
		if(taxes.indexOf(".") == 2 && taxes.length ==6){
			targetFormName.taxes.focus();
			//showDialog('Taxes can have only 2 places of decimal.',1,self);
			showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.err.taxallows2placesofdecimal" scope="request"/>',1,self);
			return;
					
			}
	}
	// Added by Preet on 3 rd Apr for AirNZ 517 ends


var path="uld.defaults.createuldagreement.do";
submitForm(frm,path);
}

function onClickDelete(){
var frm=targetFormName;
var checked=frm.elements.check;
if(validateSelectedCheckBoxes(frm,'selectedChecks',1000000,1)){
//showDialog('Do you want to delete the selected agreement details?', 4, self, frm, 'id_7');
//showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.cnf.deleteselectedagreementdetails" scope="request"/>',4, self, frm, 'id_7');
//showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.deleteAgreement" scope="request"/>',4,self, frm, 'id_7'); Commented by A-5237 for ICRD-48369
	showDialog({msg:'<bean:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.cnf.deleteselectedagreementdetails" scope="request"/>',type :4, parentWindow:self,parentForm:frm,dialogId:'id_7',
						onClose:function(){
	screenConfirmDialog(frm,'id_7');
	screenNonConfirmDialog(frm,'id_7');

						}
						});	
	//screenConfirmDialog(frm,'id_7');
	//screenNonConfirmDialog(frm,'id_7');
}
}

function onClickClose(){

		var frm=targetFormName;
		if(targetFormName.elements.canClear.value=="canClearTrue"){		
		submitForm(frm,'uld.defaults.listuldagreements.do?agreementNumber=&partyType=&partyCode=&transactionType=&agreementStatus=&agreementFromDate=&agreementToDate=&agreementDate=&listStatus=N&closeStatus=Y');
			
		}
		else if(targetFormName.elements.closeStatus.value == "LoanBorrow"){
			submitForm(frm,'uld.defaults.transaction.refreshloanborrowuldfromdamage.do');
		}else if(targetFormName.elements.createFlag.value=="Y"){		
		submitForm(frm,'uld.defaults.listuldagreements.do?agreementNumber=&partyType=&partyCode=&transactionType=&agreementStatus=&agreementFromDate=&agreementToDate=&agreementDate=&listStatus=N&closeStatus=Y');
		targetFormName.elements.createFlag.value="";
		}else{
		    var frm=targetFormName;
			var listFilterWarningFlag = targetFormName.elements.uldTypeFilterWarningFlag.value;
			if(listFilterWarningFlag=="true"){
				showDialog({msg:'Unsaved data exists. Do you want to continue ?',type :4, parentWindow:self,parentForm:frm,dialogId:'id_1',
									onClose:function(){
									screenCloseWarningConfirmDialog(frm,'id_1');
									screenCloseWarningNonConfirmDialog(frm,'id_1');
									}
				});
			} else {
				submitForm(frm,'uld.defaults.closeaction.do');
			}
		}


}

function screenCloseWarningConfirmDialog(frm,dialogId){
	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			submitForm(frm,'uld.defaults.closeaction.do');
		}
	}
}

function screenCloseWarningNonConfirmDialog(frm,dialogId){

}


function onLoad(){


if(targetFormName.elements.partyType.value=="A"){
targetFormName.elements.toComboFlag.value="airlinelov";
targetFormName.elements.toComboFlag.defaultValue="airlinelov";
}

if(targetFormName.elements.fromPartyType.value=="A"){
targetFormName.elements.comboFlag.value="airlinelov";
targetFormName.elements.comboFlag.defaultValue="airlinelov";
}

if(targetFormName.elements.onload.value=="valid"){
targetFormName.elements.agreementNumber.readOnly=true;
		if(targetFormName.elements.agreementStatus[0].value=="D"){

		disableField(targetFormName.elements.agreementNumber);
		disableField(targetFormName.elements.partyCode);
		disableField(targetFormName.elements.partyName);
		disableField(targetFormName.elements.freeLoanPeriod);
		disableField(targetFormName.elements.fromDate);
		disableField(targetFormName.elements.toDate);
		disableField(targetFormName.elements.taxes);
		disableField(targetFormName.elements.remarks);
		disableField(targetFormName.elements.btnList);
		disableField(targetFormName.elements.demurrageRate);
		disableField(targetFormName.elements.agreementStatus);
		disableField(targetFormName.elements.demurrageFrequency);
		disableField(targetFormName.elements.currencyCode);
		disableField(targetFormName.elements.partyType);
		disableField(targetFormName.elements.transactionType);
		disableField(targetFormName.elements.agreementDate);
		disableField(document.getElementById('agrlov'));
		//alert("hi");
		disableField(targetFormName.elements.demurrageFrequency);
		disableField(targetFormName.elements.currencyCode);
		disableField(targetFormName.elements.partyType);
		disableField(targetFormName.elements.transactionType);
		disableField(targetFormName.elements.agreementDate);
		disableField(document.getElementById('agrlov'));
		
		disableLink(document.getElementById("createlink"));
		disableLink(document.getElementById("modifylink"));
		disableLink(document.getElementById("deletelink"));
		disableField(targetFormName.btnSave);
			disableField(document.getElementById('currencyLov'));
			disableField(document.getElementById('airlinelov'));
			if(document.getElementById('btn_fromdate') != null){
			  disableField(document.getElementById('btn_fromdate'));
			}
			if(document.getElementById('btn_todate') != null){
			  disableField(document.getElementById('btn_todate'));
			}
			if(document.getElementById('btn_agreementDate') != null){
			  disableField(document.getElementById('btn_agreementDate'));
			}
			
		//Added by A-5198 as part of ICRD-34626
		disableField(targetFormName.elements.allParties);
		}
		
	}
	if(targetFormName.elements.canClear.value=="canClearTrue"){
	disableField(targetFormName.btnClear);
	disableField(targetFormName.btnList);
	}

	if(targetFormName.elements.listStatus.value=="Y"){
		//showDialog("The ULD Agreement Number does not exist. Do you want to create a new one? System will auto generate the agreement number", 4, self, targetFormName, 'id_6');
		showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.err.agreementnumberdoesntexist.doyouwanttocreatnew" scope="request"/>',4, self, targetFormName, 'id_6');
		/* Commented By A-5495 for ICRD-48420
		showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.doeNotExist " scope="request"/>',4,self); 
		*/
		targetFormName.elements.listStatus.value="";
		screenConfirmDialog(targetFormName,'id_6');
	}

	if(targetFormName.elements.closeStatus.value=="LoanBorrow"){
		disableField(targetFormName.btnSave);
	}	
	
	//Added by A-5198 as part of ICRD-34626	
	//showPartyCode();
	if(targetFormName.elements.partyCode.value=="ALL AGENTS" || targetFormName.elements.partyCode.value=="ALL AIRLINES"){
		targetFormName.elements.allParties.checked = true;
	}else{
		targetFormName.elements.allParties.checked = false;
	}
	onAllPartiesChange(true);
	
	
		if((!frm.elements.agreementNumber.disabled) 
					&& (frm.elements.agreementNumber.readOnly == false)){	
					frm.elements.agreementNumber.focus();
		}
		else{				
			if((frm.elements.agreementStatus[0].value != 'D')
				&&(!frm.elements.partyType.disabled)){
				frm.elements.partyType.focus();	
			}
			else if(!frm.elements.masterRowId.disabled){
					frm.elements.masterRowId.focus();
			}								
		}
}

function onClickPrint(){
var frm=targetFormName;
var preview="true";
var path="/uld.defaults.printuldagreements.do?preview="+preview;
generateReport(frm,path);
}

function showAgreementNoLov(){
	 var strUrl="uld.defaults.screenloadagreementnolov.do?agreementNo="+targetFormName.elements.agreementNumber.value;
	 openPopUpWithHeight(strUrl,"500");
	}
function screenConfirmDialog(frm, dialogId) {
 	while(frm.elements.currentDialogId.value == ''){
 	}
 	if(frm.elements.currentDialogOption.value == 'Y') {


 		if(dialogId == 'id_1'){

 			if(targetFormName.elements.canClear.value=="canClearTrue"){
					submitForm(frm,'uld.defaults.refreshlistuldagreement.do?agreementNumber=&partyType=&partyCode=&transactionType=&agreementStatus=&agreementFromDate=&agreementToDate=&agreementDate=');
			}else{
							submitForm(frm,'uld.defaults.closeaction.do');
 				}

 		 	}
 		 	if(dialogId == 'id_6'){

 		 	}
 		 		if(dialogId == 'id_7'){
						targetFormName.elements.uldTypeFilterWarningFlag.value="true";
			 			submitForm(frm,"uld.defaults.deleteuldagreement.do");
 		 	}

 	}


 }

 function screenNonConfirmDialog(frm, dialogId) {

 	while(frm.elements.currentDialogId.value == ''){

 	}

 	if(frm.elements.currentDialogOption.value == 'N') {
 		if(dialogId == 'id_1'){

 		}

 		if(dialogId == 'id_6'){

			targetFormName.elements.agreementNumber.value="";
 		}

 		if(dialogId == 'id_7'){

				submitForm(targetFormName,'uld.defaults.refreshUldAgreement.do');
				return;
 		}

 	 	}
}






function chkDate(date){

 	if(date.value==""){

 	return;
 	}
         else
 	{

 	checkdate(date, 'DD-MMM-YYYY');
 	}
}

function showPartyCode(){
	frm=targetFormName;
	//alert(frm.partyType.value+" "+frm.allParties.checked);
	if(frm.elements.partyType.value=="G" && frm.elements.allParties.checked){
		//if(frm.partyCode.value==""){
			frm.elements.partyCode.value="ALL AGENTS";
			frm.elements.partyName.value="ALL AGENTS";
			frm.elements.partyCode.defaultValue="ALL AGENTS";
			frm.elements.partyName.defaultValue="ALL AGENTS";
		//}
	}
	//Commented by A-5198 as part of ICRD-34626	
	/*
	if(frm.partyType.value=="O"){
		if(frm.partyCode.value==""){
			frm.partyCode.value="ALL OTHERS";
			frm.partyName.value="ALL OTHERS";
			frm.partyCode.defaultValue="ALL OTHERS";
			frm.partyName.defaultValue="ALL OTHERS";
		}
	}*/
	if(frm.elements.partyType.value=="A" && frm.elements.allParties.checked){
		//if(frm.partyCode.value==""){
			frm.elements.partyCode.value="ALL AIRLINES";
			frm.elements.partyName.value="ALL AIRLINES";
			frm.elements.partyCode.defaultValue="ALL AIRLINES";
			frm.elements.partyName.defaultValue="ALL AIRLINES";
		//}
	}
	//Added by A-5198 as part of ICRD-34626	
	if(frm.elements.partyType.value=="L" && frm.elements.allParties.checked){
		//if(frm.partyCode.value==""){
			frm.elements.partyCode.value="ALL";
			frm.elements.partyName.value="FOR ALL PARTIES";
			frm.elements.partyCode.defaultValue="ALL";
			frm.elements.partyName.defaultValue="ALL";
		//}
	}
	if(frm.elements.partyType.value=="C" && frm.elements.allParties.checked){
			frm.elements.partyCode.value="ALL CUSTOMERS";
			frm.elements.partyName.value="ALL CUSTOMERS";
			frm.elements.partyCode.defaultValue="ALL CUSTOMERS";
			frm.elements.partyName.defaultValue="ALL CUSTOMERS";
	}
}

function showFromPartyCode(){
	frm=targetFormName;
	//alert(frm.partyType.value+" "+frm.allParties.checked);
	if(frm.elements.fromPartyType.value=="G" && frm.elements.fromAllParties.checked){
		//if(frm.partyCode.value==""){
			frm.elements.fromPartyCode.value="ALL AGENTS";
			frm.elements.fromPartyName.value="ALL AGENTS";
			frm.elements.fromPartyCode.defaultValue="ALL AGENTS";
			frm.elements.fromPartyName.defaultValue="ALL AGENTS";
		//}
	}
	//Commented by A-5198 as part of ICRD-34626	
	/*
	if(frm.partyType.value=="O"){
		if(frm.partyCode.value==""){
			frm.partyCode.value="ALL OTHERS";
			frm.partyName.value="ALL OTHERS";
			frm.partyCode.defaultValue="ALL OTHERS";
			frm.partyName.defaultValue="ALL OTHERS";
		}
	}*/
	if(frm.elements.fromPartyType.value=="A" && frm.elements.fromAllParties.checked){
		//if(frm.partyCode.value==""){
			frm.elements.fromPartyCode.value="ALL AIRLINES";
			frm.elements.fromPartyName.value="ALL AIRLINES";
			frm.elements.fromPartyCode.defaultValue="ALL AIRLINES";
			frm.elements.fromPartyName.defaultValue="ALL AIRLINES";
		//}
	}
	//Added by A-5198 as part of ICRD-34626	
	if(frm.elements.fromPartyType.value=="L" && frm.elements.fromAllParties.checked){
		//if(frm.partyCode.value==""){
			frm.elements.fromPartyCode.value="ALL";
			frm.elements.fromPartyName.value="FOR ALL PARTIES";
			frm.elements.fromPartyCode.defaultValue="ALL";
			frm.elements.fromPartyName.defaultValue="ALL";
		//}
	}
	if(frm.elements.fromPartyType.value=="C" && frm.elements.fromAllParties.checked){
			frm.elements.fromPartyCode.value="ALL CUSTOMERS";
			frm.elements.fromPartyName.value="ALL CUSTOMERS";
			frm.elements.fromPartyCode.defaultValue="ALL CUSTOMERS";
			frm.elements.fromPartyName.defaultValue="ALL CUSTOMERS";
	}
}
function onclickFromLOV(){
	frm=targetFormName;
	if(frm.elements.comboFlag.value=="agentlovf"){
		enableField(frm.elements.fromAirlinelov);
		showFromLOV();
	}else if(frm.elements.comboFlag.value==""){
		enableField(frm.elements.fromAirlinelov);
		displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.fromPartyCode.value,'fromPartyCode','1','fromPartyCode','fromPartyName',0)
	showAirlineLOV();
	}else if(frm.elements.comboFlag.value=="airlinelov"){
		enableField(frm.elements.fromAirlinelov);
		displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.fromPartyCode.value,'fromPartyCode','1','fromPartyCode','fromPartyName',0)
	showAirlineLOV();
	}else if(frm.elements.comboFlag.value=="customerlovf"){
	showTempIDLOV();
	}
	else if(frm.elements.comboFlag.value=="otherslovf"){
	disableField(frm.elements.partyCode);
	disableField(frm.elements.fromPartyCode);
	}
}
function showFromLOV(){
	if(targetFormName.elements.fromPartyCode.value=="ALL AGENTS"){
		var code="";
	}else{
		var code=targetFormName.elements.fromPartyCode.value;
	}
	var strAction="shared.defaults.agent.screenloadagentlov.do";
	var textfiledDesc="";
	var StrUrl=strAction+"?textfiledObj=fromPartyCode&formNumber=1&textfiledDesc=fromPartyName&agentCode="+code;
	var myWindow = openPopUp(StrUrl, "500","400");
}
//****added by a-3278 for populating party name on tabout as a part of bug 25164 on 13Nov08*****

function populatePartyName(){
targetFormName.elements.typeOfParty.value='TO';
	if(targetFormName.elements.partyCode.value !=""){
	if(targetFormName.elements.partyCode.value == 'all' || targetFormName.elements.partyCode.value == 'ALL'){
		targetFormName.elements.partyName.value = "";
		targetFormName.elements.partyCode.value = "";
		showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.err.invalidpartycode" scope="request"/>',1, self);
		targetFormName.elements.partyCode.focus();
	}else if(targetFormName.elements.partyCode.value != targetFormName.elements.partyCode.defaultValue){
		targetFormName.elements.agrPartyCode.value=targetFormName.elements.partyCode.value;
		checkPartyCode(targetFormName.elements.agrPartyCode.value, targetFormName.elements.partyName);

		if(targetFormName.elements.partyName.value == ""){
			recreatePartyDetails("uld.defaults.uldagreement.populate.do","updatePartyName");		}

		}else{			
		if(targetFormName.elements.errorStatusFlag.value=="Y"){				
			targetFormName.elements.partyCode.value = "";
			//showDialog('Please enter a valid party code.', 1, self);
			showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.err.invalidpartycode" scope="request"/>',1, self);
			targetFormName.elements.partyCode.focus();
		}
	}
	}else{		
	targetFormName.elements.partyName.value = "";
	}
	
}

function populateFromPartyName(){
targetFormName.elements.typeOfParty.value='FROM';
	if(targetFormName.elements.fromPartyCode.value !=""){
	if(targetFormName.elements.fromPartyCode.value == 'all' || targetFormName.elements.fromPartyCode.value == 'ALL'){
		targetFormName.elements.fromPartyName.value = "";
		targetFormName.elements.fromPartyCode.value = "";
		showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.err.invalidpartycode" scope="request"/>',1, self);
		targetFormName.elements.fromPartyCode.focus();
	}else if(targetFormName.elements.fromPartyCode.value != targetFormName.elements.fromPartyCode.defaultValue){
		targetFormName.elements.agrPartyCode.value=targetFormName.elements.fromPartyCode.value;
		checkPartyCode(targetFormName.elements.agrPartyCode.value, targetFormName.elements.fromPartyName);
		if(targetFormName.elements.fromPartyName.value == ""){
			recreatePartyDetails("uld.defaults.uldagreement.populate.do","updateFromPartyName");		}
		}else{			
		if(targetFormName.elements.errorStatusFlag.value=="Y"){				
			targetFormName.elements.fromPartyCode.value = "";
			//showDialog('Please enter a valid party code.', 1, self);
			showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.err.invalidpartycode" scope="request"/>',1, self);
			targetFormName.elements.fromPartyCode.focus();
		}
	}
	}else{		
	targetFormName.elements.fromPartyName.value = "";
	}
}
function updateFromPartyName(_partyNameInfo){
	_str = "";
	_innerFrm=_partyNameInfo.document.getElementsByTagName("form")[0];
	targetFormName.elements.errorStatusFlag.value=_innerFrm.errorStatusFlag.value;
	if(targetFormName.elements.errorStatusFlag.value=="N"){
		_str=getPartyNameData(_partyNameInfo);
		addToPartyArray(targetFormName.elements.agrPartyCode.value,_str);
	}else {
			targetFormName.elements.fromPartyCode.value = "";
		//showDialog('Please enter a valid party code.', 1, self);
		showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.err.invalidpartycode" scope="request"/>',1, self);
		showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.validPartyCode" scope="request"/>',1,self); 
		targetFormName.elements.fromPartyCode.focus();
	}
	targetFormName.elements.fromPartyName.value=_str;
}
function updatePartyName(_partyNameInfo){

	_str = "";
	_innerFrm=_partyNameInfo.document.getElementsByTagName("form")[0];
	targetFormName.elements.errorStatusFlag.value=_innerFrm.errorStatusFlag.value;
	if(targetFormName.elements.errorStatusFlag.value=="N"){
		_str=getPartyNameData(_partyNameInfo);
		addToPartyArray(targetFormName.elements.agrPartyCode.value,_str);
	}else {
			targetFormName.elements.partyCode.value = "";
		//showDialog('Please enter a valid party code.', 1, self);
		showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.err.invalidpartycode" scope="request"/>',1, self);
		showDialog('<common:message bundle="maintainuldagreement" key="uld.defaults.maintainuldagreement.validPartyCode" scope="request"/>',1,self); 
		targetFormName.elements.partyCode.focus();
	}

	targetFormName.elements.partyName.value=_str;
}

function getPartyNameData(_partyNameInfo){
	_partyName = _partyNameInfo.document.getElementById("partyDiv").innerHTML;
	return _partyName;
}

function addToPartyArray(agrPartyCode, agrPartyName){
	_partyArr.put(agrPartyCode, agrPartyName);
}

function doesPartyExist(agrPartyCode){
	var _party = _partyArr.get(agrPartyCode);
	return _party;
}

function checkPartyCode(_val, partyNameFld){
	var party = doesPartyExist(_val);
	if(party!=null){
		partyNameFld.value = party;
	}
	else {
		partyNameFld.value = "";
	}
}

function recreatePartyDetails(strAction,__extraFn){
	//var __extraFn=strAction;
	if(arguments[1]!=null){
		__extraFn=arguments[1];
	}
	var key = "agrPartyCode="+targetFormName.elements.agrPartyCode.value;

	asyncSubmit(targetFormName,strAction,__extraFn,null,null);
}
//****added by a-3278 for populating party name on tabout as a part of bug 25164 on 13Nov08 ends*****