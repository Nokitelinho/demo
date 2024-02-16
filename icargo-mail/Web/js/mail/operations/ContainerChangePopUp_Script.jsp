<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister(){
   var frm=targetFormName;
   onScreenLoad(frm);
  
   with(frm){

   	//CLICK Events
	evtHandler.addEvents("btnOk","OkMailChange()",EVT_CLICK);
	evtHandler.addEvents("btnClear","clearMailChange()",EVT_CLICK);
	evtHandler.addEvents("btnClose","closeMailChange()",EVT_CLICK);
	
     //BLUR Events

   	}
}

function onScreenLoad(frm){
  frm=targetFormName;
  
   if(frm.elements.popupCloseFlag.value == "Y"){
	//Modified by A-7794 as part of ICRD-224613
		window.opener.IC.util.common.childUnloadEventHandler();
		frm.elements.popupCloseFlag.value = "";
		frm = self.opener.targetFormName;
		window.opener.targetFormName.action=appPath+"/mailtracking.defaults.mailarrival.listmailarrival.do";
		window.opener.targetFormName.submit();
		window.close();
		return;
    }

	}
  
function submitAction(frm,action){
	var actionName = appPath+action;
	submitForm(frm,actionName);
}


function clearMailChange(){
   frm=targetFormName;
   submitAction(frm,'/mailtracking.defaults.containerchange.clearcontainerchange.do');   
}


function OkMailChange(){
frm=targetFormName;
   submitAction(frm,'/mailtracking.defaults.containerchange.listflight.do');
}

function closeMailChange(){
   frm=targetFormName;
   window.close();
}