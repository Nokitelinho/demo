//window.onload=initForm;
function screenSpecificEventRegister()
{
	var frm=targetFormName;
	onScreenLoad();
	with(frm){
	//showDialog('simi',1,self);
	//CLICK Events
	//window.ounload = self.opener.childWindow=null;
	evtHandler.addIDEvents("addLink","handleLink('add')",EVT_CLICK);
	evtHandler.addIDEvents("deleteLink","handleLink('delete')",EVT_CLICK);

	evtHandler.addEvents("btnGenerate","generate(this.form)",EVT_CLICK);
	evtHandler.addEvents("btnSave","save(this.form)",EVT_CLICK);
	evtHandler.addEvents("btnClose","closeUld()",EVT_CLICK);


	evtHandler.addEvents("startNo","validateLength()",EVT_BLUR);
	evtHandler.addEvents("noOfUnits","validateFields(this, -1, 'NoOfUnits', 3, true, true)",EVT_BLUR);

	}


}

function validateLength() {

	if(targetFormName.elements.startNo.value != null &&
		targetFormName.elements.startNo.value != "") {
		validateFields(targetFormName.elements.startNo, 3, 'Start Number', 3, true, true);
	}


}


function save(frm) {
	//var uldType = targetFormName.uldType.value;
	//var ownerAirlineCode = targetFormName.ownerAirlineCode.value;
	submitForm(frm,'uld.defaults.savemultipleulds.do');
}


function onScreenLoad(){


	var statusFlag = targetFormName.elements.onloadStatusFlag.value;
	var uldType = targetFormName.elements.uldType.value;
	var ownerAirlineCode = targetFormName.elements.ownerAirlineCode.value;
	var uldOwnerCode = targetFormName.elements.uldOwnerCode.value;
	if(statusFlag =="generate")  {
		targetFormName.btnSave.focus();

	}
	else {
		targetFormName.startNo.focus();
	}
	if(statusFlag == "save") {
		var structuralStatus=targetFormName.elements.structuralFlag.value;
		if(targetFormName.elements.structuralFlag.value=="firstOk" ) {
			structuralStatus="afterfirstOk"
			window.opener.targetFormName.action="uld.defaults.createnewuld.do?uldType="+uldType+"&ownerAirlineCode="+ownerAirlineCode+"&uldOwnerCode="+uldOwnerCode+"&structuralFlag="+structuralStatus;
			window.opener.targetFormName.submit();
			window.opener.IC.util.common.childUnloadEventHandler(); //Added as part of BUG ICRD-262844
			window.close();
		}
		else {
			window.opener.targetFormName.action="uld.defaults.refreshmaintainuld.do?uldType="+uldType+"&ownerAirlineCode="+ownerAirlineCode+"&uldOwnerCode="+uldOwnerCode+"&structuralFlag="+structuralStatus;
			window.opener.targetFormName.submit();
			window.opener.IC.util.common.childUnloadEventHandler(); //Added as part of BUG ICRD-262844
			window.close();
		}
	}
}

function screenConfirmDialog(frm, dialogId) {

	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			frm.elements.ratelinecount.value = "0";
			enableRateline();
			frm.elements.btCreate.disabled=true;
			frm.elements.chargeHeadCode.readOnly=true;
		}
		if(dialogId == 'id_2') {
			submitForm(frm,'tariff.others.closechargeheadsscreen.do');
		}
		if(dialogId == 'id_3') {
			frm.chargeHeadBasis.disabled=false;
			submitForm(frm,'tariff.others.createchargeheads.do');
		}
		if(dialogId == 'id_4') {
			screenLinkConfirm();
		}
	}
}

function screenNonConfirmDialog(frm, dialogId) {

	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){
			clearScreen(frm);
		}
	}
}



function generate(frm){

	submitForm(frm,'uld.defaults.generateuldnumbers.do');
}

function handleLink(linkName){
	var link = linkName;
	if(link == "add") {
		addTemplateRow('uldTemplateRow','uldTableBody','uldOpFlag');
		//submitForm(targetFormName,'uld.defaults.adduldnumbers.do');
	}
	else if(link =="delete") {
		deleteTableRow('selectedRows','uldOpFlag');
		/*if(validateSelectedCheckBoxes(targetFormName,'selectedRows',10000000,1)) {
			submitForm(targetFormName,'uld.defaults.deleteuldnumbers.do');
		}*/
	}
}

function closeUld(){
	close();
}


function specifyRange(frm){
	var uldType = frm.uldType.value;
	var ownerAirlineIdentifier = frm.ownerAirlineIdentifier.value;
	var myWindow = openPopUp("uld.defaults.screenloadmultipleulds.do?uldType="+uldType+"&ownerAirlineIdentifier="+ownerAirlineIdentifier,
	"400","320");

}




