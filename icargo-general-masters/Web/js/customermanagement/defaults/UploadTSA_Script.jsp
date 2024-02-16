<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister()
{
   var frm=targetFormName;
   with(frm){
   	//CLICK Events
     	evtHandler.addEvents("btnLoad","submitData('UploadFile')" ,EVT_CLICK);
     	evtHandler.addEvents("btnClose","submitData('Close')",EVT_CLICK);
     }
}

function submitData(x){
    var action=x;
    var frm = targetFormName;
    if(action=='Close'){
    	window.close();
    }
    if(action=='UploadFile'){
	   	submitForm(frm,"customermanagement.defaults.customerlisting.uploadtsadatacommand.do");
    	//window.close();
    }
}