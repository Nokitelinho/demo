<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister(){
   var frm=targetFormName;
   onScreenLoad(frm);
  
   with(frm){

   	//CLICK Events
	evtHandler.addEvents("btnList","listMailChange()",EVT_CLICK);
	evtHandler.addEvents("btnClear","clearMailChange()",EVT_CLICK);
	evtHandler.addIDEvents("addLink","addContainer()",EVT_CLICK);
	evtHandler.addEvents("btnOk","OkMailChange()",EVT_CLICK);
	evtHandler.addEvents("btnClose","closeMailChange()",EVT_CLICK);
	
     //BLUR Events

   	}
}

function onScreenLoad(frm){
  frm=targetFormName;
  
  if(frm.elements.duplicateFlightStatus.value == "Y"){
         openPopUp("flight.operation.duplicateflight.do","600","280");
  }
  if(frm.elements.addLinkFlag.value == "Y"){
        enableField(document.getElementById("addLink")); 
		disableField(frm.elements.flightScanTime);
		disableField(frm.elements.flightScanDate);
  }else{
   disableField(document.getElementById("addLink")); 
   enableField(frm.elements.flightScanTime);
   enableField(frm.elements.flightScanDate);
  }

   if(frm.elements.popupCloseFlag.value == "Y"){
		IC.util.common.childUnloadEventHandler();
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

function listMailChange(){
   frm=targetFormName;
   submitAction(frm,'/mailtracking.defaults.mailchange.listflight.do');   
}

function clearMailChange(){
   frm=targetFormName;
   submitAction(frm,'/mailtracking.defaults.mailchange.clearmailchange.do');   
}

function addContainer(){
frm=targetFormName;
	var stat = "CREATE";
	var fromScreen = "CHANGE_FLIGHT";
	var flightCarrierCode = frm.elements.fltCarrierCode.value;
	var flightNumber = frm.elements.fltNumber.value;
	var flightDate = frm.elements.arrDate.value;
	var str = "mailtracking.defaults.assigncontainer.screenloadAddUpdatePopup.do?fromScreen="+fromScreen+"&status="+stat+"&flightCarrierCode="+flightCarrierCode+"&flightNumber="+flightNumber+"&flightDate="+flightDate;
	openPopUp(str,"600","425");
	
}
function OkMailChange(){
frm=targetFormName;
if(validateSelectedCheckBoxes(frm,'selectMail','1','1')){
frm.elements.newChildCont.value=frm.elements.selectMail.value;
   submitAction(frm,'/mailtracking.defaults.mailchange.okmailchange.do');   
   }
}

function closeMailChange(){
   frm=targetFormName;
   window.close();
}