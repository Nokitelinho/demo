<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
   var frm=targetFormName;
   onScreenLoad(frm);
   with(frm){

   	//CLICK Events
	evtHandler.addEvents("btnList","listTransferMailManifest()",EVT_CLICK);
	evtHandler.addEvents("btnClear","clearTransferMailManifest()",EVT_CLICK);
	//Modified by A-6770 for ICRD-135255
	//evtHandler.addEvents("btnPrint","details()",EVT_CLICK);
	evtHandler.addEvents("btnClose","closeTransferMailManifest()",EVT_CLICK);
	evtHandler.addEvents("btnReject","rejectTransfer()",EVT_CLICK);
	evtHandler.addEvents("btnEnd","endTransfer()",EVT_CLICK);
	evtHandler.addEvents("selectMail","toggleTableHeaderCheckbox('selectMail',document.forms[1].checkAll);checkPrint()",EVT_CLICK);
    evtHandler.addEvents("checkAll","updateHeaderCheckBox(targetFormName,targetFormName.elements.checkAll,targetFormName.elements.selectMail)",EVT_CLICK);
	evtHandler.addIDEvents("airportCodeLov","invokeAirportLOV()",EVT_CLICK);
     //BLUR Events

   	}
}
 
	function invokeAirportLOV () {
		var airportCode=targetFormName.elements.airportCode.value;
		displayLOV('showAirport.do','N','Y','showAirport.do',airportCode,'Airport Code','1','airportCode','','0');
	}	
 function checkPrint(){
	
	frm=targetFormName;
	var chkbox =document.getElementsByName("selectMail");
	var trfStatus =document.getElementsByName("transferStatus");
         var sltMail = "";
         for(var i=0; i<chkbox.length;i++){
         	if(chkbox[i].checked) {
         		  sltMail = chkbox[i].value;
				  var j=i+1;
				  if(trfStatus[j].value!='Transfer Ended'){
					  disableMultiButton(document.getElementById('btnPrint'));
				  }else{
						enableMultiButton(document.getElementById('btnPrint'));
				  }
         	}
         }        
   
	 
 }
 
function onScreenLoad (frm){
	if(frm.elements.disableBtn.value =="Y"){
		frm.elements.disableBtn.value= "";
		//Modified by A-6770 for ICRD-135255
		//frm.elements.btnPrint.disabled= false;
		
	}
	else{
		//frm.elements.btnPrint.disabled= true;
		disableMultiButton(document.getElementById('CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_PRINT_CN'));
		disableMultiButton(document.getElementById('btnPrint'));
		disableMultiButton(document.getElementById('CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_PRINT_CN_DSN'));
		
	}
	
}

function listTransferMailManifest(){
	frm=targetFormName;
	frm.elements.displayPage.value=1;
    frm.elements.lastPageNum.value=0;
	//Modified by A-5220 for ICRD-21098 starts
    submitForm(frm,'mailtracking.defaults.transfermailmanifest.list.do?navigationMode=list');
	//Modified by A-5220 for ICRD-21098 ends
}
function rejectTransfer(){
	frm=targetFormName;
	var chkbox =document.getElementsByName("selectMail");
	    var flag ="N";
	if(validateSelectedCheckBoxes(frm,'selectMail','100000000000','1')){
            flag = "Y";
	}    
          if(flag == "Y"){
          var selectMail = "";
         var cnt1 = 0;
         for(var i=0; i<chkbox.length;i++){
         	if(chkbox[i].checked) {
         	    if(cnt1 == 0){
         		  selectMail = chkbox[i].value;
         		  cnt1 = 1;
         	    }else{
         		  selectMail = selectMail + "_" + chkbox[i].value;
         	    }
         	}
         }   
         frm.elements.selectMail.value=selectMail;		 
    	 submitForm(frm,'mailtracking.defaults.transfermailmanifest.rejecttransfer.do?&selectMail='+selectMail);
		 
     }
}
function endTransfer(){
	frm=targetFormName;
	var ttt =document.getElementsByName("transferMailManifest");
	var chkbox =document.getElementsByName("selectMail");
	    var flag ="N";
	if(validateSelectedCheckBoxes(frm,'selectMail','100000000','1')){
            flag = "Y";
	}    
     if(flag == "Y"){
          var selectMail = "";
         var cnt1 = 0;
         for(var i=0; i<chkbox.length;i++){
         	if(chkbox[i].checked) {
         	    if(cnt1 == 0){
         		  selectMail = chkbox[i].value;
         		  cnt1 = 1;
         	    }else{
         		  selectMail = selectMail + "_" + chkbox[i].value;
         	    }
         	}
         }   
         frm.elements.selectMail.value=selectMail;			 
		 submitForm(frm,'mailtracking.defaults.transfermailmanifest.endtransfer.do?&selectMail='+selectMail);
		 
     }
}

function clearTransferMailManifest(){
	frm=targetFormName;
   submitForm(frm,'mailtracking.defaults.transfermailmanifest.clear.do');
}
function closeTransferMailManifest(){
   location.href = appPath + "/home.jsp";
}
function printDespatchLevel(){
	frm=targetFormName;
	var chkbox =document.getElementsByName("selectMail");
	
    var flag ="N";
	if(validateSelectedCheckBoxes(frm,'selectMail','1','1')){
            flag = "Y";
	}    
     if(flag == "Y"){
         var sltMail = "";
         for(var i=0; i<chkbox.length;i++){
         	if(chkbox[i].checked) {
         		  sltMail = chkbox[i].value;
         	}
         }        
    	 generateReport(frm,'/mailtracking.defaults.transfermailmanifest.printreport.do');
     }
}

function printCN(cnPrintType) {
	frm=targetFormName;
	var chkbox =document.getElementsByName("selectMail");
    var flag ="N";
	if(validateSelectedCheckBoxes(frm,'selectMail','1','1')){
        flag = "Y";
	}    
     if(flag == "Y"){
         var sltMail = "";
         for(var i=0; i<chkbox.length;i++){
         	if(chkbox[i].checked) {
         		  sltMail = chkbox[i].value;
         	}
         }
	   generateReport(frm,'/mailtracking.defaults.transfermailmanifest.printcnreport.do?cnReportType=MALSUM&cnPrintType='+cnPrintType);
	 }
}

function printCNDSN(cnPrintType) {
	frm=targetFormName;
	var chkbox =document.getElementsByName("selectMail");
    var flag ="N";
	if(validateSelectedCheckBoxes(frm,'selectMail','1','1')){
        flag = "Y";
	}    
     if(flag == "Y"){
         var sltMail = "";
         for(var i=0; i<chkbox.length;i++){
         	if(chkbox[i].checked) {
         		  sltMail = chkbox[i].value;
         	}
         }
	   generateReport(frm,'/mailtracking.defaults.transfermailmanifest.printcnreport.do?cnPrintType='+cnPrintType);
	 }
}


function printMailbagLevel(){
	frm=targetFormName;
	var chkbox =document.getElementsByName("selectMail");

    var flag ="N";
	if(validateSelectedCheckBoxes(frm,'selectMail','1','1')){
            flag = "Y";
	}    
     if(flag == "Y"){
         var sltMail = "";
         for(var i=0; i<chkbox.length;i++){
         	if(chkbox[i].checked) {
         		  sltMail = chkbox[i].value;
         	}
         }        
    	 generateReport(frm,'/mailtracking.defaults.transfermailmanifest.printmailbaglevelreport.do');
     }
}
function submitPage(lastPg,displayPg){
	frm=targetFormName;
  	frm.elements.lastPageNum.value=lastPg;
  	frm.elements.displayPage.value=displayPg;
	//Modified by A-5220 for ICRD-21098 starts
  	submitForm(frm,'mailtracking.defaults.transfermailmanifest.list.do?navigationMode=navigation');
	//Modified by A-5220 for ICRD-21098 ends
}
