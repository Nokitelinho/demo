<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister()
{
   var frm=targetFormName;

   
   onScreenLoad(frm);
   with(frm){
	 
     	evtHandler.addEvents("btnOk","existingMailbagOk()",EVT_CLICK);
     	evtHandler.addEvents("btnClose","closeWindow()",EVT_CLICK);
		}
		
	
}


function onScreenLoad(frm){
  	 
	if(frm.elements.popupCloseFlag.value == "Y"){
	if(frm.elements.fromScreen.value=="ReassignMail"){
		frm.elements.fromScreen.value = "";
        frm = self.opener.targetFormName;
       	frm.action="mailtracking.defaults.mailacceptance.saveassignmail.do";
       	frm.method="post";
       	frm.submit();
       	window.closeNow();
       	return;
		}
	    frm.elements.popupCloseFlag.value = "";
        frm = self.opener.targetFormName;
       	frm.action="mailtracking.defaults.mailacceptance.savemailacceptance.do";
       	frm.method="post";
       	frm.submit();
       	window.closeNow();
       	return;
    }
}


function existingMailbagOk(){
	
	frm=targetFormName;
	submitForm(frm,'mailtracking.defaults.mailacceptance.existingmailbag.ok.do');
	   
}
	
function closeWindow(){
	frm=targetFormName;
	submitForm(frm,'mailtracking.defaults.mailacceptance.existingmailbag.ok.do');
}