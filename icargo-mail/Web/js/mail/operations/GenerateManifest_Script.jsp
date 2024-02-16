<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
   var frm=targetFormName;
   with(frm){

     //CLICK Events
     	evtHandler.addEvents("btnPrint","printManifest()",EVT_CLICK);
		evtHandler.addEvents("btnCancel","cancelPrint()",EVT_CLICK);
		evtHandler.addEvents("btnCancel","cancelFocus()",EVT_BLUR);
   }

}


function printManifest(){
frm = targetFormName;
 
    if(frm.elements.printType.value == "Mailbag level"){
    	generateReport(frm,"/mailtracking.defaults.mailmanifest.generatemanifestmailbag.do");
    }
    
    if(frm.elements.printType.value == "AWB level"){
    	generateReport(frm,"/mailtracking.defaults.mailmanifest.generatemanifestawb.do"); 
    }
    
    if(frm.elements.printType.value == "Destn Category level"){
    	generateReport(frm,"/mailtracking.defaults.mailmanifest.generatemanifestdest.do"); 
    }
    
    if(frm.elements.printType.value == "DSN/Mailbag level"){
    	generateReport(frm,"/mailtracking.defaults.mailmanifest.generatemanifestdsnmailbag.do"); 
    }   
    
}

function cancelPrint(){
    window.close();
}
function cancelFocus(){
    targetFormName.elements.printType.focus();
}