<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
   var frm=targetFormName;

   with(frm){


		evtHandler.addEvents("btList","listDetails(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClear","clearDetails(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClose","closeDetails(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClose","resetFocus()",EVT_BLUR);
		evtHandler.addEvents("btSendResdit","sendResdit(this.form)",EVT_CLICK);
		evtHandler.addEvents("searchMode","togglePanel(this);disableDetails();",EVT_CHANGE);
		evtHandler.addEvents("resdit","disablecardit()",EVT_CHANGE);
		
		evtHandler.addIDEvents("portLOV","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.departurePort.value,'Airport','1','departurePort','',0)", EVT_CLICK);
		evtHandler.addIDEvents("originOELov","displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.ooe.value,'OfficeOfExchange','1','ooe','',0)", EVT_CLICK);
		evtHandler.addIDEvents("destnOELov","displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.doe.value,'OfficeOfExchange','1','doe','',0)", EVT_CLICK);

		evtHandler.addEvents("flightNumber","validateFields(this, -1, 'Flight Number', 0, true, true)",EVT_BLUR);
		evtHandler.addEvents("departurePort","validateFields(this, -1, 'Departure Port', 1, true, true)",EVT_BLUR);
		evtHandler.addEvents("resdit","notAcceptedChange()",EVT_CHANGE);


   	}
 applySortOnTable("mailBagTable",new Array("None","String","String","String","String","Date","String")); 
 applySortOnTable("despatchTable",new Array("None","String","String","String","Number","String","String","String","Date")); 
 applySortOnTable("documentTable",new Array("None","String","String","Date"));
 
   	onScreenLoad(frm);

}

function onScreenLoad(frm){

		
        if(targetFormName.elements.searchMode == ""){

             targetFormName.elements.searchMode = "M";
        }
      
	togglePanel(targetFormName.elements.searchMode);
	
	disableDetails();
	
	if(targetFormName.elements.duplicateFlightStatus.value == "Y") {
		targetFormName.elements.duplicateFlightStatus.value = "N";
		openPopUp("flight.operation.validateFlights.do","600","280");
	}
	
	setFocus();
	
	disablecardit();

}

function resetFocus(){
	 if(!event.shiftKey){ 
		if((!targetFormName.elements.carrierCode.disabled) && (!targetFormName.elements.carrierCode.readOnly)){
			targetFormName.elements.carrierCode.focus();
		}			
	}	
}


function setFocus(){
	if(targetFormName.elements.carrierCode.disabled == false) {
		targetFormName.elements.carrierCode.focus();
	}
	else if(targetFormName.elements.btClear.disabled == false) {
		targetFormName.elements.btClear.focus();
	}

}
function togglePanel(comboObj) // 1 visible, 0 hidden
{

  if(comboObj != null) {
	   var divID = comboObj.value;
	   var comboLength = comboObj.length;
	   var obj = null;
	   var comboValues = null;

	   for(var index=0; index<comboLength; index++) {

			comboValues = comboObj.options[index].value;
			obj = document.layers ? document.layers[comboValues] :
				  document.getElementById ?  document.getElementById(comboValues).style :
				  document.all[comboValues].style;

			if(comboValues == divID) {
				iState = 1;

			} else {
				iState = 0;
			}
			obj.visibility = document.layers ? (iState ? "show" : "hide") :
						(iState ? "visible" : "hidden");
			obj.display = document.layers ? (iState ? "show" : "hide") :
						(iState ? "" : "none");
	   }
	}
}

function disableDetails(){
	if(targetFormName.elements.flightNumber.disabled == false) {

		if(targetFormName.elements.searchMode.value == "C") {
			disableField(targetFormName.elements.ooe);
			disableField(targetFormName.elements.doe);
			disableField(targetFormName.elements.mailCategoryCode);
			disableField(targetFormName.elements.mailSubclass);
			disableField(targetFormName.elements.year);
			disableField(targetFormName.elements.despatchSerialNumber);
			disableField(targetFormName.elements.receptacleSerialNumber);
		}
		else {
			enableField(targetFormName.elements.ooe);
			enableField(targetFormName.elements.doe);
			enableField(targetFormName.elements.mailCategoryCode);
			enableField(targetFormName.elements.mailSubclass);
			enableField(targetFormName.elements.year);
			enableField(targetFormName.elements.despatchSerialNumber);
			enableField(targetFormName.elements.receptacleSerialNumber);
		}
	}
	
	if(targetFormName.elements.ooe.disabled == true) {
		disableField(document.getElementById('originOELov'));
	}else{
		enableField(document.getElementById('originOELov'));
	}
	if(targetFormName.elements.doe.disabled == true) {
		disableField(document.getElementById('destnOELov'));
	}else{
		enableField(document.getElementById('destnOELov'));
	}
	
	if(targetFormName.elements.departurePort.disabled == true) {
		disableField(document.getElementById('portLOV'));
	}else{
		enableField(document.getElementById('portLOV'));
	}
	
}

function notAcceptedChange(){
	if(targetFormName.elements.resdit.value == ""){
		disableField(targetFormName.elements.notAccepted);
	}
	else{
		enableField(targetFormName.elements.notAccepted);
	}
}

function listDetails(frm){
   if(frm.elements.notAccepted.checked){
   		frm.elements.notAccepted.value="Y";
   	}else{
   		frm.elements.notAccepted.value="N";
   	}
   submitForm(frm,'mailtracking.defaults.carditenquiry.listcarditenquiry.do');
}

function clearDetails(frm){

	submitForm(frm,'mailtracking.defaults.carditenquiry.clearafterduplicateflight.do');
}

function closeDetails(frm){
	location.href = appPath + "/home.jsp";
}



function sendResdit(frm){
	var chkbox =document.getElementsByName("selectedRows");
	if(validateSelectedCheckBoxes(frm,'selectedRows','1000000000','1')){	
	    var selectMail = "";
	    var cnt1 = 0;
	    var opt =0;
	    for(var i=0; i<chkbox.length;i++){
		if(chkbox[i].checked) {		   
		   if(cnt1 == 0){
			  selectMail = chkbox[i].value;
			  var mail=chkbox[i].value.split("~")[0];			  
			  cnt1 = 1;
			  if(mail=="M"){
			  	opt=1;
			  }
			  if(mail=="C"){
			  	opt=2;
			  }
		   }else{
			  var cont=chkbox[i].value.split("~")[0];
			  if(mail!=cont){
			  	showDialog("Select either containers or mailbags ",1,self);
         			return;
			  }
			  else{			  
			  	selectMail = selectMail + "," + chkbox[i].value;
			  }
		   }
		}
   	    }  
	
	}	
	if(opt==2){
		var strAction="mailtracking.defaults.carditenquiry.screenloadsendresdit.do";
		var strUrl=strAction+"?selCont="+selectMail;
		openPopUp(strUrl,700,170);
	}
	else{
		submitForm(targetFormName,'mailtracking.defaults.carditenquiry.sendresdit.do');
	}
	
}



function dsnPadding(){
 frm=targetFormName;
 var mailDSNArr =document.getElementsByName("dsn");
 var mailDSN =document.getElementsByName("dsn");
   for(var i=0;i<mailDSNArr.length;i++){
      if(mailDSNArr[i].value.length == 1){
          mailDSN[i].value = "000"+mailDSNArr[i].value;
      }
      if(mailDSNArr[i].value.length == 2){
                mailDSN[i].value = "00"+mailDSNArr[i].value;
      }
      if(mailDSNArr[i].value.length == 3){
                mailDSN[i].value = "0"+mailDSNArr[i].value;
      }
   }

}


function rsnPadding(){
 frm=targetFormName;
 var mailRSNArr =document.getElementsByName("rsn");
 var mailRSN =document.getElementsByName("rsn");
   for(var i=0;i<mailRSNArr.length;i++){
      if(mailRSNArr[i].value.length == 1){
          mailRSN[i].value = "00"+mailRSNArr[i].value;
      }
      if(mailRSNArr[i].value.length == 2){
          mailRSN[i].value = "0"+mailRSNArr[i].value;
      }
   }
}


function disablecardit(){

      if(targetFormName.elements.resdit.value == "") {
          targetFormName.elements.flightType.value = "C";
		  disableField(targetFormName.elements.flightType);
      }else{
	  enableField(targetFormName.elements.flightType);
      }
}