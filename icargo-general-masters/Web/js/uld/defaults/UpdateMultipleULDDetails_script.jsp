<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>




function screenSpecificEventRegister()
{
evtHandler.addEvents("btnSave","save(this.form)",EVT_CLICK);
evtHandler.addEvents("btSave","save(targetFormName)",EVT_CLICK);
evtHandler.addEvents("btClear","clear()",EVT_CLICK);
evtHandler.addEvents("btClearAll","clearAll()",EVT_CLICK);
evtHandler.addEvents("btClose","close()",EVT_CLICK);
//evtHandler.addEvents("damagedStatus","populateDamageDetails()",EVT_CHANGE);
//evtHandler.addEvents("oVerallStatus","populateOverallDetails()",EVT_CHANGE);
evtHandler.addEvents("operationalStatusCopyAll","populateOperationalStatus()",EVT_CLICK);
evtHandler.addEvents("damagedStatusCopyAll","populateDamagedStatus()",EVT_CLICK);
evtHandler.addEvents("uldLOV","showUldLov(targetFormName,this)",EVT_CLICK);
evtHandler.addEvents("btClose","btnClose(this.form)",EVT_CLICK);

onScreenLoad();
evtHandler.addEvents("uldNumbers","validateULDDetails(this.form,this)",EVT_BLUR);

}
function onScreenLoad(){
var frm=targetFormName;
var result = frm.elements.statusFlag.value;

if(result == "session_update"){
	var index = frm.elements.selectedRow.value;
	openPopUp("uld.defaults.misc.openDamagePopup.do?selectedRow="+index,'1200','500');
}
if(result == "action_damagesuccess"|| result == "action_mainsave"){
	setTimeout(function() {
		submitForm(frm,'uld.defaults.misc.updatemultipleulddamage.do')
	}, 1000);
}
}
function populateOperationalStatus() {

  if(document.getElementById("operationalStatusCopyAll").checked) {   
  if(targetFormName.elements.operationalStatus[0].value ==""){
	   targetFormName.elements.operationalStatus[0].disabled = false;
	   showDialog({msg:"select an operational Status", type:1, parentWindow:self});
  }
       else {
	  for(var i =0; i<25;i++){
		  targetFormName.elements.operationalStatus[i] = false;
		  targetFormName.elements.operationalStatus[i].value=targetFormName.elements.operationalStatus[0].value;
		  targetFormName.elements.operationalStatus[i].disabled = true;
		}

	  }
  	}
	else {
		for(var i =0; i<25;i++){
		  targetFormName.elements.operationalStatus[i] = false;
		  targetFormName.elements.operationalStatus[i].disabled = false;
		}
	}
}

function populateDamagedStatus() {
  if(document.getElementById("damagedStatusCopyAll").checked) {

  if(targetFormName.elements.damagedStatus[0].value ==""){
	   targetFormName.elements.damagedStatus[0].disabled = false;
	   showDialog({msg:"select a damaged Status", type:1, parentWindow:self});
  }
	  else {
 for(var i =0; i<25;i++){
		  targetFormName.elements.damagedStatus[i] = false;
		  targetFormName.elements.damagedStatus[i].value=targetFormName.elements.damagedStatus[0].value;
		  targetFormName.elements.damagedStatus[i].disabled = true;
		}
	  }
  	}
	else {
		for(var i =0; i<25;i++){
		  targetFormName.elements.damagedStatus[i] = false;
		  targetFormName.elements.damagedStatus[i].disabled = false;
		}
	}
  }

function validateULDDetails(form,obj){

    globalObj=obj;
	var ind = obj.id.split("_u")[0];
    var __extraFn="uldValidationFn";
    asyncSubmit(targetFormName,"uld.defaults.misc.validateuldDetails.do?selectedRow="+ind,__extraFn,null,null);
	

}

function uldValidationFn(_tableInfo){
var _rowCount = globalObj.id.split("_u")[0];
if(_tableInfo.document.getElementById("_ajax_operationalstatus")!=null) {


targetFormName.elements.operationalStatus[_rowCount].value=_tableInfo.document.getElementById("_ajax_operationalstatus").textContent;
}
if(_tableInfo.document.getElementById("_ajax_damagedstatus")!=null) {
targetFormName.elements.damagedStatus[_rowCount].value=_tableInfo.document.getElementById("_ajax_damagedstatus").textContent;
}
}




function save(frm){
var uldNumbers = frm.elements.uldNumbers
submitForm(frm,'uld.defaults.misc.saveDamageDetails.do');
//var index = field.getAttribute("index");

}
function showUldLov(frm,field){
//var uldNumbers =  document.getElementsByName('uldNumbers'); 
var uldNumbers = frm.elements.uldNumbers
var index = field.getAttribute("index");
frm.elements.selectedRow.value=index;
submitForm(frm,'uld.defaults.misc.showDamageDetails.do');
//openPopUp("uld.defaults.misc.showDamageDetails.do?selectedRow="+index+"&uldNumbers="+uldNumbers,'650','315');
}
function btnClose(frm){
submitFormWithUnsaveCheck('uld.defaults.misc.closecommand.do');
}




































