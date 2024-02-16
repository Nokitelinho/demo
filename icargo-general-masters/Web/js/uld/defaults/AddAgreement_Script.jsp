<%@ include file="/jsp/includes/js_contenttype.jsp" %>

function screenSpecificEventRegister(){

	var frm=targetFormName;
	targetFormName.elements.uldType.focus();
	with(frm){

		evtHandler.addEvents("btnOK","OnClickOK()",EVT_CLICK);
		evtHandler.addEvents("btnClose","onClickClose()",EVT_CLICK);
		evtHandler.addIDEvents("uldlov","displayLOV('showUld.do','N','Y','showUld.do',targetFormName.elements.uldType.value,'uldType','0','uldType','',0)",EVT_CLICK);
		evtHandler.addIDEvents("stationlov","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.station.value,'station','0','station','',0)",EVT_CLICK);
		evtHandler.addIDEvents("createagreementDetails","onClickCreate()",EVT_CLICK);
		evtHandler.addIDEvents("deleteagreementDetails","onClickDelete()",EVT_CLICK);
		evtHandler.addEvents("demurrageRate","validateFields(this.form.demurrageRate,-1,'Demurrage Rate',2,true,true,12,4)",EVT_BLUR);
		evtHandler.addEvents("taxes","validateFields(this.form.taxes,-1,'tax',2,true,true,12,4)" ,EVT_BLUR);
		evtHandler.addEvents("freeLoanPeriod","validateFields(this.form.freeLoanPeriod,-1,'Free Loan Period',3,true,true)", EVT_BLUR);
		evtHandler.addEvents("uldType","validateAlphanumeric(targetFormName.elements.uldType,'ULD Type Code','Invalid ULD Code',true)",EVT_BLUR);
		evtHandler.addEvents("station","validateAlphanumeric(targetFormName.elements.station,'Station Code','Invalid Station Code',true)",EVT_BLUR);
		evtHandler.addEvents("remarks","validateMaximumLength(targetFormName.elements.remarks,500,'Remarks','Invalid Remarks',true)",EVT_BLUR);
		evtHandler.addEvents("taxes","checkValue()",EVT_BLUR);
		evtHandler.addEvents("demurrageRate","checkValue()",EVT_BLUR);
		evtHandler.addEvents("currencyCode","validateAlphanumeric(targetFormName.elements.currencyCode,'CurrencyCode','Invalid Currency Code',true)",EVT_BLUR);
		evtHandler.addEvents("currencyLov","displayLOV('showCurrency.do','N','Y','showCurrency.do',targetFormName.elements.currencyCode.value,'CurrencyCode','2','currencyCode','',0)",EVT_CLICK);
		onScreenLoad();



	}
}
// Added by Preet on 3 rd Apr for AirNZ 517 starts
function checkValue(){
	if(targetFormName.elements.demurrageRate.value < 0){
		showDialog('Cannot Enter Negative value for Demurrage Rate',1,self);
		targetFormName.elements.demurrageRate.value ="";
		targetFormName.elements.demurrageRate.focus();
		return;
	}
	if(targetFormName.elements.taxes.value < 0){
			showDialog('Cannot Enter Negative value for Tax Percenatge',1,self);
			targetFormName.elements.taxes.value ="";
			targetFormName.elements.taxes.focus();
			return;
	}	
	var taxes =targetFormName.elements.taxes.value;
	if(taxes.length != 0 && taxes>100){
		showDialog('Tax Percenatge cannot exceed 100.',1,self);
	}	
}
// Added by Preet on 3 rd Apr for AirNZ 517 ends


function OnClickOK(){
var frm=targetFormName;
if(frm.elements.validFrom.value != "" &&
	!chkdate(frm.elements.validFrom)){

	showDialog("Invalid From Date", 1, self, frm, 'id_1');
	return;
}

if(frm.elements.validTo.value != "" &&
	!chkdate(frm.elements.validTo)){

	showDialog("Invalid To Date", 1, self, frm, 'id_2');
	return;
}

var path="uld.defaults.selectagreementdetails.do";
submitForm(frm,path);
}


function onScreenLoad(){

if(targetFormName.elements.canClose.value=='close'){
window.opener.IC.util.common.childUnloadEventHandler();
self.opener.submitForm(self.opener.targetFormName,'uld.defaults.refreshUldAgreement.do')
window.close();
}
}


function onClickClose(){
	if(isFormModified(targetFormName)) {
		var cnfrm=self.showWarningDialog("Unsaved data exists. Do you want to continue ?");
		    if ( cnfrm==false ){
			document.body.style.cursor = 'default';

		    }
		    else{
				window.opener.IC.util.common.childUnloadEventHandler();
		       self.opener.submitForm(self.opener.targetFormName,'uld.defaults.refreshtestuldagreement.do');
			window.close();
		    }


	}
	else{
	window.opener.IC.util.common.childUnloadEventHandler();
	self.opener.submitForm(self.opener.targetFormName,'uld.defaults.refreshtestuldagreement.do');
	window.close();
	}
}


function onClickCreate(){


var frm=targetFormName;
if(frm.elements.validFrom.value != "" &&
	!chkdate(frm.elements.validFrom)){

	showDialog("Invalid From Date", 1, self, frm, 'id_1');
	return;
}

if(frm.elements.validTo.value != "" &&
	!chkdate(frm.elements.validTo)){

	showDialog("Invalid To Date", 1, self, frm, 'id_2');
	return;
}



submitForm(frm,'uld.defaults.addagreementdetails.do');
}

function selectNextAgreementDetail(strLastPageNum,strDisplayPage){
	targetFormName.lastPageNumber.value= strLastPageNum;
	targetFormName.displayPage.value = strDisplayPage;
	targetFormName.action ="uld.defaults.selectnextagreementdetails.do";
	targetFormName.submit();
	disablePage();

}

function onClickDelete(){
var frm=document.forms['addULDAgreementForm'];
submitForm(frm,'uld.defaults.deleteagreementdetails.do');
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