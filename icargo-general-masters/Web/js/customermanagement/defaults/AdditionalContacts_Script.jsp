<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@include file="/jsp/includes/tlds.jsp"%>
function screenSpecificEventRegister(){

	var frm=targetFormName;

	with(frm){
		evtHandler.addIDEvents("addLink","addTempRow()",EVT_CLICK);
		evtHandler.addIDEvents("deleteLink","deleteTemplateRow()",EVT_CLICK);
		evtHandler.addEvents("btnOK","OKcommand()",EVT_CLICK);
		evtHandler.addEvents("btnClose","closeWindow()",EVT_CLICK);
		evtHandler.addEvents("contactAddress","validateContactAddress(this)",EVT_BLUR);
		evtHandler.addEvents("contactMode","validateContactAddressOnValueChange(this)",EVT_CHANGE);
		}
	reloadParent();	
}

function closeWindow(){	
	IC.util.common.childUnloadEventHandler();
	window.close();
}
function childWindowCloseEvent(){
	closeWindow();
}
		
function OKcommand(){
		if(validateMandatoryFields() == 'true') {
			var selectedContactIndex = targetFormName.elements.selectedContactIndex.value;
		var action="customermanagement.defaults.okAdditionalContacts.do?selectedContactIndex="+selectedContactIndex;
		submitForm(targetFormName, action);
}
		else {
			showDialog({	
				msg		:	"Please Enter Email/Fax/SMS details",
				type	:	2, 
				parentWindow:self
			});
		}
}

function validateMandatoryFields() {
	var validationStatus = 'true';
	for (i = 0; i < (targetFormName.elements.contactMode.length - 1); i++) { 
		if(targetFormName.elements.hiddenOpFlagForAddtContacts[i].value != "D" && 
		targetFormName.elements.contactMode[i].value != "" && targetFormName.elements.contactAddress[i].value == "") {
			validationStatus = 'false';
		}
	}
	return validationStatus;
}

function reloadParent(){
	var frm = targetFormName;
	if(frm.elements.reloadParent.value == "true"){
		window.close();
	}
}

function addTempRow() {
	addTemplateRow('addtionalCOntactsTemplateRow','listContact','hiddenOpFlagForAddtContacts')
}

function deleteTemplateRow() {
	deleteTableRow('check','hiddenOpFlagForAddtContacts');
}

function validateContactAddressOnValueChange(contactMode) {
	var contactIndex = contactMode.id.split('contactMode')[1];
	if(contactIndex == "") {
		contactIndex = "0";
	}
	var contMod =  contactMode.value;
	var contAddress = targetFormName.contactAddress[contactIndex].value;
	var obj = targetFormName.contactAddress[contactIndex];
	if(contMod == "M") {
		validateEmailTable(obj);
	}
	else if(contMod == "F") {
		validateFaxOrMobile('Fax Number', obj);
	}
	else if(contMod == "S") {
		validateFaxOrMobile('Mobile Number', obj);
	}
}

function validateContactAddress(contactAddress) {
	var contactIndex = contactAddress.id.split('contactAddress')[1];
	if(contactIndex == "") {
		contactIndex = "0";
	}
	var contMod = targetFormName.contactMode[contactIndex].value;
	if(contMod == "M") {
		validateEmailTable(contactAddress);
	}
	else if(contMod == "F") {
		validateFaxOrMobile('Fax Number', contactAddress);
	}	
	else if(contMod == "S") {
		validateFaxOrMobile('Mobile Number', contactAddress);
	}
	else {
		showDialog({	
			msg		:	"Please Select a Contact Type",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
		contactAddress.value="";
	}
}

function validateEmailTable(email){
	if(email.value.trim() != "") {
		var emailFound= validateEmail(email.value);
		if(!emailFound){
			showDialog({	
				msg		:	"Please Enter a Valid Email ID",
				type	:	1, 
				parentWindow:self,
				onClose:function(){
					email.focus();
				}
			});
		}
	}
}

function validateFaxOrMobile(type, element) {
	if(element.value.trim() != "") {
		validateFields(element,-1,type,13,'Invalid'+type,true);
	}
}
