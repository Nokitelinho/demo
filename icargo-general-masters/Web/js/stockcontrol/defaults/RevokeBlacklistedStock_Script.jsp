<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	var frm=document.forms[0];
	with(frm){


		evtHandler.addEvents("rangeFrom","validateFields(this.form.rangeFrom,-1,'Range From',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("rangeTo","validateFields(this.form.rangeTo,-1,'Range To',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("remarks","validateRemarks(this)",EVT_BLUR);
		evtHandler.addEvents("btnotify","onClickNotify()",EVT_CLICK);
		evtHandler.addEvents("btrevoke","onClickRevoke()",EVT_CLICK);
		evtHandler.addEvents("btclose","onClickClose()",EVT_CLICK);

	}
	setFocus();
//	DivSetVisible(true);
}

function validateRemarks(remarks){
	return validateMaxLength(remarks,250);
}

function onClickClose(){
	window.opener.document.forms[1].action='stockcontrol.defaults.listblacklistedstock.do';
	window.opener.IC.util.common.childUnloadEventHandler();
	window.opener.document.forms[1].submit();
	window.close();
}

function onClickRevoke(){
	//changed for ICRD-254030
	if(document.forms[1].elements.rangeFrom.value==""){
		//alert('Please enter Range From value');
		showDialog({	
				msg		:	'<common:message bundle="revokeblacklistedstockresources" key="stockcontrol.defaults.revokeblacklistedstock.pleaseenterrangefrom" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				
			});
		document.forms[1].elements.rangeFrom.focus();
		return;
	}
	if(document.forms[1].elements.rangeTo.value==""){
		//alert('Please enter Range To value');
		showDialog({	
				msg		:	'<common:message bundle="revokeblacklistedstockresources" key="stockcontrol.defaults.revokeblacklistedstock.pleaseenterrangeto" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				
			});
		document.forms[1].elements.subType.focus();
		return;
	}
	
	
	showDialog({	
				msg		:	'<common:message bundle="revokeblacklistedstockresources" key="stockcontrol.defaults.revokeblacklistedstock.doyouwanttorevoketheblacklistedstock" scope="request"/>',
				type	:	4, 
				parentWindow:self,
				parentForm:document.forms[1],
				dialogId:'id_1',
				onClose : function () {
					screenConfirmDialog(document.forms[1],'id_1');
					screenNonConfirmDialog(document.forms[1],'id_1');
				}
			});
	/*
	if(confirm('Do you want to revoke the blacklisted stock?')){
		document.forms[0].action = 'stockcontrol.defaults.revokingblacklistedstock.do';
		document.forms[0].submit();
		disablePage();
	}*/
}

function screenConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){

	}
	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			frm.action = 'stockcontrol.defaults.revokingblacklistedstock.do';
			frm.submit();
			disablePage();
		}
	}
}

function screenNonConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){

	}
	if(frm.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){

		}
	}
}

function setFocus(){
	if(document.forms[1].elements.revokeSuccessful.value=="Y"){
	showDialog({	
				msg		:	'<common:message bundle="revokeblacklistedstockresources" key="stockcontrol.defaults.revokesuccessful" />',
				type	:	2, 
				parentWindow:self,
				parentForm:document.forms[0],
				onClose: function(){
					window.opener.document.forms[1].action = "stockcontrol.defaults.listblacklistedstock.do";
					window.opener.IC.util.common.childUnloadEventHandler();
					window.opener.document.forms[1].submit();
					window.close();
				}
				
			});
	
	
	
	}
	document.forms[0].elements.rangeFrom.focus();
}

function onClickNotify(){
}