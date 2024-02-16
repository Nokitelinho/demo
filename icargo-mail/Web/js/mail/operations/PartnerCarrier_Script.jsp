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
		evtHandler.addEvents("carrierLOV","invokeLov(this,'carrierLOV')",EVT_CLICK);
     	if(frm.rowId != null){
			evtHandler.addEvents("rowId","toggleTableHeaderCheckbox('rowId',targetFormName.checkAll)",EVT_CLICK);
		}
		evtHandler.addIDEvents("airportLOV","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.airport.value,'Airport','1','airport','',0)", EVT_CLICK);
		evtHandler.addEvents("addLink","addDetails()",EVT_CLICK);
		evtHandler.addEvents("deleteLink","deleteDetails()",EVT_CLICK);
		evtHandler.addEvents("partnerCarrierName","operationFlagChangeOnEdit(this,'operationFlag');",EVT_BLUR);
	}
}
var rcnt = 0;

function onScreenLoad(frm){

rcnt=(document.getElementsByName("rowId").length-1);
 if(frm.elements.disableSave.value == "Y"){
     disableField(frm.elements.btSave);
  }else{
     enableField(frm.elements.btSave);
  }
   if(frm.elements.viewBillingLine.value=="Y"){
     frm.elements.viewBillingLine.value="";
		var rowId= document.getElementsByName("rowId");
		  if(validateSelectedCheckBoxes(frm,'rowId',1,1)){ 
				var partnerCarrierCode = eval(frm.elements.partnerCarrierCode);
			  	var i=0;
			  	if (rowId != null) {
				 		if (rowId.length > 0) {
				 			for (i = 0; i < rowId.length; i++) {		 					
				 				var checkBoxValue = rowId[i].value;	
				 				if(rowId[i].checked){
				 				  var airlin = partnerCarrierCode[i].value; 
								  break;			
								}
				 			}
				 		}
				 }
			  	var fromScreen = "PARTNER_CARRIER";
			  	var org = frm.elements.airport.value;
				var strAction="mailtracking.mra.defaults.viewbillingline.list.do";
				var strUrl=strAction+"?fromPage="+fromScreen+"&airline="+airlin+"&origin="+org;		
				submitForm(targetFormName,strUrl);
		  
	      }
	}
	 
  if((!frm.elements.airport.disabled) && (!frm.elements.airport.readOnly)){
		frm.elements.airport.focus();
  }
  else if(!document.getElementById("addLink").disabled){
		document.getElementById("addLink").focus();
  }	 
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

	frm = targetFormName;
	if(frm.elements.disableSave.value != "Y"){
   	 displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.hiddenpartnerCarrierCode.value,'CarrierCode','1','hiddenpartnerCarrierCode','hiddenpartnerCarrierName',0,'popul()');    
	}
}


function popul(){
  	var pcode=targetFormName.elements.hiddenpartnerCarrierCode.value;
  	var pname=targetFormName.elements.hiddenpartnerCarrierName.value;
  	addTemplateRow('partnerTemplateRow','partnerTableBody','operationFlag'); 
  	var rct=targetFormName.elements.operationFlag.length;
	targetFormName.elements.partnerCarrierCode[(rct-2)].value=pcode;
	targetFormName.elements.partnerCarrierName[(rct-2)].value=pname;
	targetFormName.elements.hiddenpartnerCarrierCode.value="";
	targetFormName.elements.hiddenpartnerCarrierName.value="";
}

function invokeLov(obj,name){
  var index = obj.id.split(name)[1];
   if(name == "carrierLOV"){  
         displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.partnerCarrierCode.value,'CarrierCode','1','partnerCarrierCode','partnerCarrierName',index,'dummy()');    
   }
   
}

function dummy(){
  	 	
}

function listDetails(frm){
	submitForm(frm,"mailtracking.defaults.partnercarrier.list.do");
}

function saveDetails(frm){
	submitForm(frm,"mailtracking.defaults.partnercarrier.save.do");
}

function clearDetails(frm){
	submitForm(frm,"mailtracking.defaults.partnercarrier.clear.do");
}

function deleteDetails(){
	frm = targetFormName;
        if(frm.elements.disableSave.value != "Y"){
	     if(validateSelectedCheckBoxes(frm,'rowId',1000000000,1)){
	          deleteTableRow('rowId','operationFlag');
	     }
	}
}

function updateOperationFlag(frm){
	var operationFlag = eval(frm.elements.operationFlag);   //for operation flag
 	var rowId = eval(frm.rowId);
	var description = eval(frm.elements.partnerCarrierName);
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
function closeScreen(){

location.href = appPath + "/home.jsp";
}
