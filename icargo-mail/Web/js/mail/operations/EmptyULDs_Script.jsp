<%@ include file="/jsp/includes/js_contenttype.jsp" %>



function screenSpecificEventRegister()
{
   var frm=targetFormName;

   onScreenLoad(frm);
   with(frm){

   	//CLICK Events

     	evtHandler.addEvents("btClose","closeEmptyULDs()",EVT_CLICK);
     	evtHandler.addEvents("btUnassign","unassignEmptyULDs()",EVT_CLICK);

     	evtHandler.addEvents("checkAll","updateHeaderCheckBox(this.form,targetFormName.checkAll,targetFormName.selectULD)",EVT_CLICK);

		if(frm.elements.selectULDs != null){
			evtHandler.addEvents("selectULD","toggleTableHeaderCheckbox('selectULD',targetFormName.checkAll)",EVT_CLICK);
		}

	}
}

function onScreenLoad(frm){

	if(frm.elements.status.value == "CLOSE"){
		frm.elements.status.value = "";
		emptyULDs();
	}
}
function emptyULDs(){
var	frm=targetFormName;
 	if(frm.elements.fromScreen.value == "MAILBAG_ENQUIRY"){
        frm.elements.fromScreen.value = "";
        frm = self.opener.targetFormName;
        frm.elements.category.disabled = false;
		frm.elements.currentStatus.disabled = false;
		frm.elements.damaged.disabled = false;
		frm.elements.displayPage.value=1;
		frm.elements.lastPageNum.value=0;
		frm.action="mailtracking.defaults.mailbagenquiry.list.do";
		frm.method="post";
		frm.submit();
		window.closeNow();
		return;
    }
    if(frm.elements.fromScreen.value == "DSN_ENQUIRY"){
        frm.elements.fromScreen.value = "";
        frm = self.opener.targetFormName;
    	frm.action="mailtracking.defaults.dsnenquiry.listdetails.do";
    	frm.method="post";
    	frm.submit();
    	//window.opener.recreateTableDetails("mailtracking.defaults.dsnenquiry.list.do","div1","chkListFlow");
    	window.closeNow();
    	return;
    }
     if(frm.elements.fromScreen.value == "OFFLOAD"){
    	frm.elements.fromScreen.value = "";
    	var toscreen = frm.elements.toScreen.value;
    	
    	frm = self.opener.targetFormName;
    	frm.elements.mode.value = "NORMAL";
	frm.action="mailtracking.defaults.offload.clear.do?fromScreen="+toscreen;

	frm.method="post";
	frm.submit();
	window.closeNow();
	return;
    }
    if(frm.elements.fromScreen.value == "RETURNDSN"){
	frm.elements.fromScreen.value = "";
	frm = self.opener.targetFormName;
	frm.elements.displayPage.value=1;
	frm.elements.lastPageNum.value=0;
	frm.elements.status.value="";
	frm.action="mailtracking.defaults.dsnenquiry.listdetails.do";
	frm.method="post";
	frm.submit();
	window.closeNow();
	return;
    }
    if(frm.elements.fromScreen.value == "RETURNMAIL"){
	frm.elements.fromScreen.value = "";
	frm = self.opener.targetFormName;
	frm.action = "mailtracking.defaults.mailbagenquiry.list.do";
	frm.method="post";
	frm.submit();
	window.closeNow();
	return;
    }  
    if(frm.elements.fromScreen.value == "MAILEXPORTLIST"){
		frm.elements.fromScreen.value = "";
        var fltno=frm.elements.fromFlightNumber.value;
		var fltcarcod=frm.elements.fromFlightCarrierCode.value;			
		var assignedto=frm.elements.frmassignTo.value;					
		var frmfltdat = frm.elements.frmFlightDate.value;
		var frmdest = frm.elements.fromdestination.value;
		window.opener.targetFormName.action=appPath+="/mailtracking.defaults.mailexportlist.listflight.do?assignToFlight="+assignedto+"&flightNumber="+fltno+"&flightCarrierCode="+fltcarcod+'&carrierCode='+fltcarcod+"&depDate="+frmfltdat+"&destination="+frmdest+"&fromScreen=EMPTY_ULD";
		window.opener.targetFormName.submit(); 
		window.closeNow();  
    } 
    if(frm.elements.fromScreen.value == "REASSIGN_MAIL"){	
			frm.elements.fromScreen.value = "";
			frm = self.opener.targetFormName;		
    		frm.action = "mailtracking.defaults.reassignmail.clearreassignmail.do?closeFlag=Y";
			frm.method="post";
			frm.submit();
			window.closeNow();
			return;
    }
	
	window.closeNow(); 

}

function unassignEmptyULDs(){

	var frm = targetFormName;
	if(validateSelectedCheckBoxes(frm,'selectULD',100,1)){
	submitForm(frm,"mailtracking.defaults.emptyulds.unassign.do");
	}

}

function closeEmptyULDs(){
	var frm = targetFormName;
	submitForm(frm,"mailtracking.defaults.emptyulds.close.do");
}