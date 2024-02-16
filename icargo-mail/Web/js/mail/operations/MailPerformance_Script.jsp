<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister()
{
   var frm=targetFormName;

   onScreenLoad(frm);
   with(frm){

     	evtHandler.addEvents("btList","listDetails(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btClear","clearDetails(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btSave","saveDetails(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btClose","closeScreen()",EVT_CLICK);
		evtHandler.addEvents("btClose","resetFocus(this.form)",EVT_BLUR);
     	evtHandler.addEvents("checkAll","updateHeaderCheckBox(this.form,targetFormName.checkAll,targetFormName.rowId)",EVT_CLICK);
		
     	if(frm.rowId != null){
			evtHandler.addEvents("rowId","toggleTableHeaderCheckbox('rowId',targetFormName.checkAll)",EVT_CLICK);
		}
		evtHandler.addIDEvents("airportLOV","displayLOV('showAirport.do','Y','Y','showAirport.do',targetFormName.elements.airport.value,'Airport','1','airport','',0)", EVT_CLICK);
		
		evtHandler.addIDEvents("gpaLOV","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.pacode.value,'pacode','1','pacode','',0)",EVT_CLICK);
		
		evtHandler.addIDEvents("addLink","addDetails()",EVT_CLICK);
		evtHandler.addIDEvents("deleteLink","deleteDetails()",EVT_CLICK);
		
		evtHandler.addEvents("airportCodes","operationFlagChangeOnEdit(this,'operationFlag');",EVT_BLUR);
	}
}
var rcnt = 0;


function onScreenLoad(frm){

	var checkVal=frm.elements.screenFlag.value;
	
	if(checkVal=='ctRadioBtn'){
		document.getElementById(checkVal).checked="true";
		togglepanel('pane4','pane2','pane3','pane1','pane5');
	}
	else
		togglepanel('pane1','pane2','pane3','pane4','pane5');
}

 

function resetFocus(frm){
	 if(!event.shiftKey){ 
		 if((!frm.elements.airport.disabled) && (!frm.elements.airport.readOnly)){
				frm.elements.airport.focus();
		  }
		  else if(!document.getElementById("addLink").disabled){
				document.getElementById("addLink").focus();
		  }	
	}	
}

function addDetails(){

	addTemplateRow('coTerminusTemplateRow','coTerminusTableBody','operationFlag');
	
}



function dummy(){
  	 	
}

function listDetails(frm){
	submitForm(frm,"mailtracking.defaults.mailperformance.list.do");
}

function saveDetails(frm){
	
	var chkbox =document.getElementsByName("truckFlag");
  if(chkbox.length > 0){
    for(var i=0; i<chkbox.length;i++){
       if(chkbox[i].checked ){
          chkbox[i].value = i;
       }
    }
  }
	
	
	submitForm(frm,"mailtracking.defaults.mailperformance.save.do");
}

function clearDetails(frm){
	submitForm(frm,"mailtracking.defaults.mailperformance.clear.do");
}

function closeScreen(){

location.href = appPath + "/home.jsp";
}

function deleteDetails(){
	
		frm = targetFormName;
     
	     if(validateSelectedCheckBoxes(frm,'rowId',1000000000,1)){
	          deleteTableRow('rowId','operationFlag');
	  
	}
	
}

function updateOperationFlag(frm){
	var operationFlag = eval(frm.elements.operationFlag);  
 	var rowId = eval(frm.rowId);
	var description = eval(frm.elements.pacode);
	if (rowId != null) {
	 		if (rowId.length > 1) {
	 			for (var i = 0; i < rowId.length; i++) {
	 				var checkBoxValue = rowId[i].value;
	 				if((operationFlag[checkBoxValue].value !='D')&&
	 										(operationFlag[checkBoxValue].value !='U')) {
	 					if (description[checkBoxValue].value != description[checkBoxValue].defaultValue) {
                                if(operationFlag[checkBoxValue].value !='I'){
	 								frm.elements.operationFlag[checkBoxValue].value='U';
								}
	 					}
	 				}
				}
	 		}else {
	 			if(operationFlag.value !='D'){
	 				if (description.value !=description.defaultValue) {
							if(operationFlag.value !='I'){
	 							frm.elements.operationFlag.value = 'U';
							}
	 				}
	 			}
	 		}
	 	}
}

function togglepanel(showDivId, hideDivId1, hideDivId2, hideDivId3, hideDivId4){
	var obj = document.getElementById(showDivId).style;
	obj.visibility = "visible";
	obj.display = "";
	obj = document.getElementById(hideDivId1).style;
	obj.visibility = "hidden";
	obj.display = "none";
	obj = document.getElementById(hideDivId2).style;
	obj.visibility = "hidden";
	obj.display = "none";
	obj = document.getElementById(hideDivId3).style;
	obj.visibility = "hidden";
	obj.display = "none";
	obj = document.getElementById(hideDivId4).style;
	obj.visibility = "hidden";
	obj.display = "none";
}