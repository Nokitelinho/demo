<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
		var frm=targetFormName;
		showPrompt();
	with(frm){
		evtHandler.addIDEvents("btnUpload","submitAction('upload')" ,EVT_CLICK);
		evtHandler.addIDEvents("btnClosePopUp","submitAction('close')",EVT_CLICK);
	}
}
function submitAction(args){
	if(args == 'upload'){
		submitForm(targetFormName, "mail.operations.ux.forcemajeure.upload.do");
	}else if(args == 'close'){
		window.close();
	}else if (args == 'exporterrors'){
		submitForm(targetFormName, "mail.operations.ux.forcemajeure.exporterrors.do");
	}
}
function showPrompt(){
	var frm = targetFormName;
	
	if(frm.elements.uploadStatus.value == "TC") {
		showDialog({	
			msg		:	"<common:message bundle="ForceMajeureResources" key="mail.operations.forcemajeure.info.fileuploadedsuccessfully" scope="request"/>",
			type	:	2, 
			parentWindow:self,
			parentForm:targetFormName,
			onClose: function () {
				popupUtils.closePopupDialog();
			}
		});
		
	}else if(frm.elements.uploadStatus.value == "OK") {
		showDialog({	
			msg		:	"<common:message bundle="ForceMajeureResources" key="mail.operations.forcemajeure.info.uploadedandprocessedsuccessfully" scope="request"/>",
			type	:	2, 
			parentWindow:self,
			parentForm:targetFormName,
			onClose: function () {
				popupUtils.closePopupDialog();
			}
		});
	}else if(frm.elements.uploadStatus.value == "PE") {
		showDialog({	
			msg		:	"<common:message bundle="ForceMajeureResources" key="mail.operations.forcemajeure.info.uploadedandprocessedwitherror" scope="request"/>",
			type	:	2, 
			parentWindow:self,
			parentForm:targetFormName,
			onClose: function () {
				popupUtils.closePopupDialog();
			}
		});
		submitAction('exporterrors');
	}else if(frm.elements.uploadStatus.value == "PW") {
		showDialog({	
			msg		:	"<common:message bundle="ForceMajeureResources" key="mail.operations.forcemajeure.info.uploadedandprocessedwithwarning" scope="request"/>",
			type	:	2, 
			parentWindow:self,
			parentForm:targetFormName,
			onClose: function () {
				popupUtils.closePopupDialog();
			}
		});
		submitAction('exporterrors');
	}
	
}
