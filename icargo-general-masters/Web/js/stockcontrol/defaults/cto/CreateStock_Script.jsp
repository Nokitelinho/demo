<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister(){
	var frm = document.forms[0];
	with(frm){
		evtHandler.addEvents("btOK","onOk()",EVT_CLICK);
		evtHandler.addEvents("btClose","window.close();",EVT_CLICK);
		evtHandler.addEvents("address","onAddress();",EVT_BLUR);
		evtHandler.addEvents("airline","onAirline();",EVT_BLUR);
		evtHandler.addEvents("content","onContent();",EVT_BLUR);

	}
	onScreenload();
}


function onOk() {
	submitForm(document.forms[0],'stockcontrol.defaults.savestockrequest.do');
}


function onScreenload(){
	if(document.forms[0].elements.afterSave.value=="Y"){
		document.forms[0].elements.afterSave.value="N";
		window.close();
	}

}

function onAddress() {
	if(document.forms[0].elements.modeOfCommunication.value=="EMAIL"){
		if(document.forms[0].elements.address.value!=""){
			if(!validateEmail(document.forms[0].elements.address.value)){
				showDialog({	
					msg			:	"Please Enter A Valid Email",
					type		:	1, 
					parentWindow:	self,
					parentForm	:	targetFormName,
				});
				document.forms[0].elements.address.focus();
				return;
			}
		}

	}
	if(document.forms[0].elements.modeOfCommunication.value!="EMAIL"){

		validateFields(document.forms[0].elements.address,-1, "Address", 3, true, true);
		return;

	}
	return validateMaxLength(document.forms[0].elements.address,50);

}

function onAirline(){
	return validateMaxLength(document.forms[0].elements.airline,4);
}

function onContent(){
	return validateMaxLength(document.forms[0].elements.content,500);
}

 