<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister()
{
	 var frm=targetFormName;
	 //onScreenloadSetHeight();
	 //added by a-3045 for bug 37 starts
	 if(frm.elements.carrierCode[0] != null ){
		var carrierCode = eval(frm.elements.carrierCode);
		if(carrierCode[0].disabled==false){	
			carrierCode[0].focus();
		}
	 }
	 //added by a-3045 for bug 37 ends
    
         onScreenLoad();
         with(frm){

         evtHandler.addIDEvents("addLink","handleLink('add')",EVT_CLICK);
	 evtHandler.addIDEvents("deleteLink","handleLink('delete')",EVT_CLICK);

	 evtHandler.addIDEvents("addFlight","handleLinkFlight('add')",EVT_CLICK);
	 evtHandler.addIDEvents("deleteFlight","handleLinkFlight('delete')",EVT_CLICK);


	 evtHandler.addEvents("btnSave","save1(this.form)",EVT_CLICK);
	 evtHandler.addEvents("btnClose","closeAction(this.form)",EVT_CLICK);
	 evtHandler.addEvents("btnClose","resetFocus(this.form)",EVT_BLUR);
	 evtHandler.addIDEvents("ownerairportLovImg","onLovFunction()",EVT_CLICK);
     evtHandler.addEvents("carrierCode","validateFields(this, -1, 'CarrierCode', 0, true, true)",EVT_BLUR);
	 //evtHandler.addEvents("flightNumber","validateFields(this, -1, 'FlightNumber', 0, true, true)",EVT_BLUR);
     evtHandler.addEvents("flightNumber","completeFlightNo(this)",EVT_BLUR);
	 evtHandler.addEvents("pointOfLading","validateFields(this, -1, 'PointOfLading', 1, true, true)",EVT_BLUR);
     evtHandler.addEvents("pointOfUnLading","validateFields(this, -1, 'PointOfUnLading', 1, true, true)",EVT_BLUR);

	evtHandler.addEvents("updateCurrentStation","updateCurrentStation(this.form)",EVT_CLICK);
	//added by a-3278 for 39777 on 04Mar09--To default Update Current Airport Flag and POU station
   	evtHandler.addEvents("pointOfUnLading","updateCurrentStationPOU(this.form)",EVT_BLUR);
   	//a-3278 ends
	evtHandler.addEvents("allCheck","updateHeaderCheckBox(this.form,this,this.form.checkForUld)",EVT_CLICK);
	evtHandler.addEvents("checkForUld","toggleTableHeaderCheckbox('checkForUld',targetFormName.elements.allCheck)",EVT_CLICK);


   
	}
       stripedTable();
       addIEonScroll();

}

function resetFocus(frm){
	 if(!event.shiftKey){ 
				 if(frm.elements.carrierCode[0] != null ){
					var carrierCode = eval(frm.elements.carrierCode);
					if(carrierCode[0].disabled==false && carrierCode[0].readOnly==false){	
						carrierCode[0].focus();
					}
				 }				
			}	
}

function showAirlineLov(obj) {
	var index = obj.id.split('airlineLov')[1];
	displayLOV('showAirline.do','N','Y','showAirline.do',document.forms[0].carrierCode.value,'CarrierCode','0','carrierCode','',index);
}

function showAirlineScreenloadLov(obj) {
	var index = obj.id.split('airlineScreenloadLov')[1];
	displayLOV('showAirline.do','N','Y','showAirline.do',document.forms[1].carrierCode.value,'CarrierCode','1','carrierCode','',index);
}

function showAirportLov(obj) {
	var index = obj.id.split('airportLov')[1];
	displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[0].pointOfLading.value,'POL','0','pointOfLading','',index);
}

function showAirportScreenloadLov(obj) {
	var index = obj.id.split('airportScreenloadLov')[1];
	displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].pointOfLading.value,'POL','1','pointOfLading','',index);
}

function showAirportPOULov(obj) {
	var index = obj.id.split('airportpouLov')[1];
	displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[0].pointOfUnLading.value,'POU','0','pointOfUnLading','',index);
}

function showAirportPOUScreenloadLov(obj) {
	var index = obj.id.split('airportpouScreenloadLov')[1];
	displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].pointOfUnLading.value,'POU','1','pointOfUnLading','',index);
}


// Function to autocomplete Flight No
function completeFlightNo(flightCodeObj){

if(flightCodeObj!=null){

    var fieldValue = flightCodeObj.value;
	var fieldLength = fieldValue.length;

	if(fieldLength == 1) {
		fieldValue = "000" + fieldValue;
		flightCodeObj.value = fieldValue;
	} else if(fieldLength == 2) {
		fieldValue = "00" + fieldValue;
		flightCodeObj.value = fieldValue;
	} else if(fieldLength == 3) {
		fieldValue = "0" + fieldValue;
		flightCodeObj.value = fieldValue;
	}

}

}




 function onScreenLoad(){
   	var frm=targetFormName;
       var screen = targetFormName.elements.screenName.value;
       var flagForCheck=targetFormName.elements.flagForCheck.value;

	 if(targetFormName.elements.errorFlag.value=="Y"){

	  var dummyIndexes=targetFormName.elements.dummyCheckedIndex.value;
	  
	  var check=dummyIndexes.split(",");
	
	  for (var i=0;i<check.length-1;i++){
	 var val=check[i];
	
	  targetFormName.elements.dummyMovement[parseInt(val)].checked=true;
	 }
	 targetFormName.elements.errorFlag.value="N";
	 }
	
	if(targetFormName.elements.pageurl.value=="close")
	{
	//Modified by A-7426 as part of ICRD-201443 starts
		window.opener.targetFormName.elements.pageURL.value="fromrecorduld";
		IC.util.common.childUnloadEventHandler();
		window.opener.targetFormName.action = "uld.defaults.messaging.listulderrorlog.do";
		window.opener.targetFormName.submit();
		window.close();
	}

	if(targetFormName.elements.pageurl.value=="scm_close")
				{
					window.opener.targetFormName.elements.pageUrl.value="fromrecorduld";
					IC.util.common.childUnloadEventHandler();
					window.opener.targetFormName.action = "uld.defaults.messaging.listscmulderrorlog.do";
					window.opener.targetFormName.submit();
					window.close();
			}
	//Modified by A-7426 as part of ICRD-201443 ends
                 if( flagForCheck=='list') {

                   opener.submitForm(window.opener.targetFormName,"uld.defaults.listuld.do");
                   window.close();

  	          }

  	          else if(flagForCheck == "listmvt") {
  	 	   	var strAction = "uld.defaults.misc.listuldmovement.do";
					
				window.opener.recreateULDMvmtDetails(strAction,'uldHistoryContents');
  	 	    
  	 	    window.close();
                  
                   }

                  else if(flagForCheck == "ulddiscrepancy") {
                  frm.elements.discrepancyStatus.value="";
                  var discrepancyDate = "";
	  	  var discrepancyCode = "";
		  submitForm(frm,"uld.defaults.listulddiscrepancies.creatediscrepancy.do?discrepancyDate="+discrepancyDate+"&discrepancyCode="+discrepancyCode);
                   }

}


function closeAction(frm) {

         if(frm.elements.screenName.value=="listUld"){
	        opener.submitForm(window.opener.targetFormName,'uld.defaults.listuld.do?statusFlag=recorduld');
	        window.close();
	 }

	 else if(frm.elements.screenName.value=="listUldMvt"){
	    //opener.submitForm(window.opener.targetFormName,'uld.defaults.misc.listuldmovement.do?statusFlag=recorduld');		
		var strAction = "uld.defaults.misc.listuldmovement.do?statusFlag=recorduld";				
		window.opener.recreateULDMvmtDetails(strAction,'uldHistoryContents');
	 	window.close();
	 }
	 else if(frm.elements.discrepancyStatus.value=="ulddiscrepancy"){
	  	frm.elements.discrepancyStatus.value="";
	  	var uldno = frm.elements.uldNumber.value;
	  	var pol = frm.elements.pointOfLading.value;
	  	var discrepancyDate = frm.elements.discrepancyDate.value;
	  	var discrepancyCode = frm.elements.discrepancyCode.value;
		submitForm(frm,'uld.defaults.listulddiscrepancies.creatediscrepancy.do?uldNoChild='+uldno+'&reportingStationChild='+pol+'&discrepancyDate='+discrepancyDate+'&discrepancyCode='+discrepancyCode);
	 }
	 else if(frm.elements.flagForUldDiscrepancy.value=="ulddiscrepancy"){
	  	frm.elements.flagForUldDiscrepancy.value="";
		submitForm(frm,'uld.defaults.listulddiscrepancies.creatediscrepancy.do');
		//window.close();
	 }

	if(frm.elements.pageurl.value=="fromulderrorlog" ||
		frm.elements.pageurl.value=="fromulderrorlogMessage"||
		frm.elements.pageurl.value=="fromScmUldReconcile"){
		targetFormName.action="uld.defaults.recorduld.closerecorduld.do";
		targetFormName.method="post";
		targetFormName.submit();
	}
	else {
		location.href=appPath+'/home.jsp';
	}

 }



function handleLink(linkName){
	var link = linkName;
	 if(link == "add") {
		addTemplateRow('adduldnumberTemplateRow','adduldnumberbody','hiddenOpFlagForULD');
		//added by a-3045 for bug 37 starts
		var uldNumber = document.getElementsByName('uldNumber');
		if(uldNumber != null && uldNumber.length > 0) {		
			uldNumber[uldNumber.length - 2].focus();
		}
		//added by a-3045 for bug 37 ends
		//submitForm(targetFormName,'uld.defaults.misc.adduldnumber.do');
	}
	 else if(link =="delete") {
		//if(validateSelectedCheckBoxes(targetFormName,'checkForUld',25,'1')){
		//	submitForm(targetFormName,'uld.defaults.misc.deleteuldnumber.do');
		//}
		deleteTableRow('checkForUld','hiddenOpFlagForULD');
	}
}


function handleLinkFlight(linkName){
	var link = linkName;
	 if(link == "add") {
		addTemplateRow('adduldmovementTemplateRow','adduldmovementbody','hiddenOpFlag');
		//added by a-3045 for bug 37 starts
		var carrierCode = document.getElementsByName('carrierCode');
		if(carrierCode != null && carrierCode.length > 0) {		
			carrierCode[carrierCode.length - 2].focus();
		}
		//added by a-3045 for bug 37 ends
		var content = document.getElementsByName('content');
		if(content != null && content.length > 0) {
			content[content.length - 2].value="X";
		}
		//submitForm(targetFormName,'uld.defaults.misc.adduldmovement.do');
	}
	 else if(link =="delete") {
	        var frm=targetFormName;
		frm.elements.updateCurrentStation.checked=false;
		frm.elements.currentStation.value="";
		//submitForm(targetFormName,'uld.defaults.misc.deleteuldmovement.do');
		deleteTableRow('checkForDelete','hiddenOpFlag');
	}
}


function save1(frm) {
             var frm=targetFormName;
             //added by a-3278 for 39777 on 04Mar09--To default Update Current Airport Flag and POU station
             updateCurrentStationPOU(frm);
             //a-3278 ends
	                  var remarksForValidation=frm.elements.remarks.value;
	                  if(remarksForValidation.length > 500) {
	                  showDialog('Please enter character(s) less than 500 in Remarks',1,self);
	  	             frm.elements.remarks.focus();
	  	                return ;
 	              }
				  var dummychecked=new String();
				  var length=frm.elements.dummyMovement.length;
				
				  for(var i=0;i<length;i++)
				  {
					if(frm.elements.dummyMovement[i].checked==true){
				
					dummychecked+= i+",";
					}
				  }
					frm.elements.dummyCheckedIndex.value=dummychecked;
					

 	/*
	if(checkUldNumber(frm)) {
		showDialog("ULD number cannot be blank", 1, self, frm, 'id_7');	    		
		return false;	    	
	}
	if(checkCarrierCode(frm)) {
		showDialog("Carrier code cannot be blank", 1, self, frm, 'id_7');	    		
		return false;	    	
	}
	if(checkFlightNumber(frm)) {
		showDialog("Flight number cannot be blank", 1, self, frm, 'id_7');	    		
		return false;	    	
	}
	if(checkFlightDateString(frm)) {
		showDialog("Flight date cannot be blank", 1, self, frm, 'id_7');	    		
		return false;	    	
	}
	if(checkPointOfLading(frm)) {
		showDialog("POL cannot be blank", 1, self, frm, 'id_7');	    		
		return false;	    	
	}
	if(checkPointOfUnLading(frm)) {
		showDialog("POU cannot be blank", 1, self, frm, 'id_7');	    		
		return false;	    	
	}
	if(checkContent(frm)) {
		showDialog("Content cannot be blank", 1, self, frm, 'id_7');	    		
		return false;	    	
	}*/
	        frm.elements.overrideError.value="";
            submitForm(frm,"uld.defaults.misc.saveuldmovement.do");
                }

//added by a-3278 for 39777 on 04Mar09--To default Update Current Airport Flag and POU station
function updateCurrentStationPOU(frm){
	frm.elements.updateCurrentStation.checked = true;
	if(frm.elements.updateCurrentStation.checked){
		var val = checkUpdateCurrentStation(frm);
		frm.elements.currentStation.value=val;
	}else{
		frm.elements.currentStation.value="";
	}
}
//a-3278 ends

function updateCurrentStation(frm){
	if(frm.elements.updateCurrentStation.checked){
		var val = checkUpdateCurrentStation(frm);
		frm.elements.currentStation.value=val;
	}else{
		frm.elements.currentStation.value="";
	}
}



function checkUpdateCurrentStation(frm) {
	var field = eval(frm.elements.pointOfUnLading);
	var str = "";
	if(field != null) {
		var size = field.length;
		if(size > 1) {
			//str = field[size-1].value;
			str = field[size-2].value;
		}else {
			str = field.value;
		}
	}
	return str;
}

function confirmPopupMessage(){
	targetFormName.elements.messageStatus.value="true";
	targetFormName.action="uld.defaults.misc.screenloadrecorduldmovement.do";
	targetFormName.method="post";
	targetFormName.submit();
}

function nonconfirmPopupMessage(){
	targetFormName.messageStatus.value="";
}

//function to confirm a message
function confirmMessage() {
	var frm = targetFormName;
	 submitForm(frm,"uld.defaults.misc.saveuldmovement.do");
}
//function not to confirm a message
function nonconfirmMessage() {
	frm.elements.overrideError.value="";
}


 function onLovFunction(){
 	var frm=targetFormName;
	 if(frm.elements.screenName.value=="listUld"){
		displayLOV('showAirport.do','N','Y','showAirport.do',frm.elements.currentStation.value,'currentStation','0','currentStation','',0)
	 }else {
		displayLOV('showAirport.do','N','Y','showAirport.do',frm.elements.currentStation.value,'currentStation','1','currentStation','',0)
	 }
 
 }


function checkUldNumber(frm){
	var uldNumber = eval(frm.elements.uldNumber); 
	var hiddenOpFlags = eval(frm.elements.hiddenOpFlagForULD); 
	if(uldNumber != null) {
		var size = uldNumber.length;
		if(size > 1) {
			for(var i=0; i<size; i++) {
			if(hiddenOpFlags[i].value != "NOOP" && hiddenOpFlags[i].value != "D"){
				if(uldNumber[i].value == "") {
					return true;					
				}
				}
			}
		 }else {
		 if(hiddenOpFlags.value != "NOOP" && hiddenOpFlags.value != "D"){
			if(uldNumber.value == "") { 				
				return true;
			}
			}
		}
	}
	return false;
}

function checkCarrierCode(frm){
	var carrierCode = eval(frm.elements.carrierCode); 
	var hiddenOpFlags = eval(frm.elements.hiddenOpFlag); 
	if(carrierCode != null) {
		var size = carrierCode.length;
		if(size > 1) {
			for(var i=0; i<size; i++) {
			if(hiddenOpFlags[i].value != "NOOP" && hiddenOpFlags[i].value != "D"){
				if(carrierCode[i].value == "") {
					return true;					
				}
				}
			}
		 }else {
		 if(hiddenOpFlags.value != "NOOP" && hiddenOpFlags.value != "D"){
			if(carrierCode.value == "") { 				
				return true;
			}
			}
		}
	}
	return false;
}


function checkFlightNumber(frm){
	var flightNumber = eval(frm.elements.flightNumber); 
	var hiddenOpFlags = eval(frm.elements.hiddenOpFlag); 
	if(flightNumber != null) {
		var size = flightNumber.length;
		if(size > 1) {
			for(var i=0; i<size; i++) {
				if(hiddenOpFlags[i].value != "NOOP" && hiddenOpFlags[i].value != "D"){
					if(flightNumber[i].value == "") {
						return true;					
					}
				}
			}
		 }else {
			if(hiddenOpFlags.value != "NOOP" && hiddenOpFlags.value != "D"){
				if(flightNumber.value == "") { 				
					return true;
				}
			}
		}
	}
	return false;
}

function checkFlightDateString(frm){
	var flightDateString = eval(frm.elements.flightDateString); 
	var hiddenOpFlags = eval(frm.elements.hiddenOpFlag); 
	if(flightDateString != null) {
		var size = flightDateString.length;
		if(size > 1) {
			for(var i=0; i<size; i++) {
				if(hiddenOpFlags[i].value != "NOOP" && hiddenOpFlags[i].value != "D"){
					if(flightDateString[i].value == "") {
						return true;					
					}
				}
			}
		 }else {
			if(hiddenOpFlags.value != "NOOP" && hiddenOpFlags.value != "D"){
				if(flightDateString.value == "") { 				
					return true;
				}
			}
		}
	}
	return false;
}

function checkPointOfLading(frm){
	var pointOfLading = eval(frm.elements.pointOfLading); 
	var hiddenOpFlags = eval(frm.elements.hiddenOpFlag); 
	if(pointOfLading != null) {
		var size = pointOfLading.length;
		if(size > 1) {
			for(var i=0; i<size; i++) {
				if(hiddenOpFlags[i].value != "NOOP" && hiddenOpFlags[i].value != "D"){
					if(pointOfLading[i].value == "") {
						return true;					
					}
				}
			}
		 }else {
			if(hiddenOpFlags.value != "NOOP" && hiddenOpFlags.value != "D"){
				if(pointOfLading.value == "") { 				
					return true;
				}
			}
		}
	}
	return false;
}

function checkPointOfUnLading(frm){
	var pointOfUnLading = eval(frm.elements.pointOfUnLading); 
	var hiddenOpFlags = eval(frm.elements.hiddenOpFlag); 
	if(pointOfUnLading != null) {
		var size = pointOfUnLading.length;
		if(size > 1) {
			for(var i=0; i<size; i++) {
				if(hiddenOpFlags[i].value != "NOOP" && hiddenOpFlags[i].value != "D"){
					if(pointOfUnLading[i].value == "") {
						return true;					
					}
				}
			}
		 }else {
			if(hiddenOpFlags.value != "NOOP" && hiddenOpFlags.value != "D"){
				if(pointOfUnLading.value == "") { 				
					return true;
				}
			}
		}
	}
	return false;
}

function checkContent(frm){
	var content = eval(frm.elements.content); 
	var hiddenOpFlags = eval(frm.elements.hiddenOpFlag); 
	if(content != null) {
		var size = content.length;
		if(size > 1) {
			for(var i=0; i<size; i++) {
				if(hiddenOpFlags[i].value != "NOOP" && hiddenOpFlags[i].value != "D"){
					if(content[i].value == "") {
						return true;					
					}
				}
			}
		 }else {
			if(hiddenOpFlags.value != "NOOP" && hiddenOpFlags.value != "D"){
				if(content.value == "") { 				
					return true;
				}
			}
		}
	}
	return false;
}
/**
 This function will set the height of the Content Div
 of the screen on load**/

function onScreenloadSetHeight(){
	var height = document.body.clientHeight;
	var width = document.body.clientWidth;
	//document.getElementById('div1').style.height = ((height*30)/100)+'px';
	
	
 }