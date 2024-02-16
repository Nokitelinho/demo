<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
   var frm=targetFormName;

   with(frm){

		//CLICK Events
		evtHandler.addEvents("btList","listDetails(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClear","clearDetails(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClose","closeDetails(this.form)",EVT_CLICK);
		evtHandler.addEvents("btOk","lookupOk()",EVT_CLICK);
		evtHandler.addEvents("btAddToList","addToList()",EVT_CLICK);
		evtHandler.addEvents("btRemoveFromList","removeFromList()",EVT_CLICK);
		evtHandler.addEvents("btAssignMail","assignMail()",EVT_CLICK);
		evtHandler.addIDEvents("subClassLov","invokeLov(this,'subClassLov')",EVT_CLICK);
		evtHandler.addIDEvents("portLOV","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.pol.value,'Airport','1','pol','',0)", EVT_CLICK);
		evtHandler.addIDEvents("originOELov","displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.ooe.value,'OfficeOfExchange','1','ooe','',0)", EVT_CLICK);
		evtHandler.addIDEvents("destnOELov","displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.doe.value,'OfficeOfExchange','1','doe','',0)", EVT_CLICK);
		evtHandler.addEvents("receptacleSerialNumber","mailRSN()",EVT_BLUR);
		evtHandler.addEvents("despatchSerialNumber","mailDSN()",EVT_BLUR);
		evtHandler.addEvents("flightlov","callFlightPopUp()",EVT_CLICK);
		evtHandler.addEvents("btViewConDoc","viewConDoc()",EVT_CLICK);
		//a7531
		evtHandler.addEvents("attachAWB","attachAWB()",EVT_CLICK);
        evtHandler.addEvents("detachAWB","detachAWB()",EVT_CLICK);
        evtHandler.addEvents("bookedFlights","bookedFlights()",EVT_CLICK);
		evtHandler.addEvents("btSendResdit","sendResdit(this.form)",EVT_CLICK);//A-8061 Added for ICRD-82434
 
		if(frm.elements.selectMail != null){
		    evtHandler.addEvents("selectMail","toggleTableHeaderCheckbox('selectMail',targetFormName.elements.parentCheckBox)",EVT_CLICK);
		}
   	}
	//A-8061 Added for ICRD-82434 starts
		var options_arry = new Array();
		options_arry = {
		  "autoOpen" : false,
		  "width" : 500,
		  "height": 5,
		   "draggable" :false,
		  "resizable" :true
		};
	initDialog('resditEventDetails',options_arry);
	//A-8061 Added for ICRD-82434 ends
	
   	applySortOnTable("searchconsignmentdetails",new Array("None","None","None","None","None","None","Number","Number","None","None","None","String","None","None","None","None","Number","None"));
   	onScreenLoad(frm);

}
function viewConDoc(){
	if(validateSelectedCheckBoxes(targetFormName,'selectMail',1,1)){
	var chkboxes = document.getElementsByName('selectMail');
	var gpaCode="";
	var conDoc="";
	if(chkboxes != null){
			for(var index=0;index<chkboxes.length;index++){
				if(chkboxes[index].checked == true){
			if(chkboxes.length==1){
				gpaCode=targetFormName.elements.filterPACode.value;
				conDoc=targetFormName.elements.filterCondocNum.value;
			}else{
				gpaCode=targetFormName.elements.filterPACode[index].value;
				conDoc=targetFormName.elements.filterCondocNum[index].value;
			}

			}
			}
 submitForm(targetFormName,'mailtracking.defaults.consignment.listconsignment.do?conDocNo='+conDoc+
 '&paCode='+gpaCode+'&fromScreen=carditEnquiry&displayPage=1&lastPageNum=0');

		}
	}
}

function onScreenLoad(frm){

	if(frm.elements.disableButton.value == "N" || frm.elements.disableButton.value == ""){
		frm.elements.disableButton.value = "";
		if(frm.elements.screenMode.value == "MAIN"){
		//Added by A-6204 for ICRD-102488 Starts
			disableField(frm.elements.btAssignMail);
			disableField(frm.elements.btViewConDoc);
		//Added by A-6204 for ICRD-102488 Ends
		disableField(frm.elements.attachAWB);
		disableField(frm.elements.detachAWB);
		disableField(frm.elements.bookedFlights);
		disableField(frm.elements.btSendResdit);
		}
		if(frm.elements.screenMode.value == "POPUP"
		  || frm.elements.screenMode.value == "POPUP_CARDIT"){
		//Added by A-6204 for ICRD-102488 Starts
			disableField(frm.elements.btOk);
		//Added by A-6204 for ICRD-102488 Ends	
		}

	}
	if(frm.elements.disableButton.value == "Y"){
		frm.elements.disableButton.value = "";
		if(frm.elements.screenMode.value == "MAIN"){
	//Added by A-6204 for ICRD-102488 Starts		
			enableField(frm.elements.btAssignMail);
			enableField(frm.elements.btViewConDoc);
	//Added by A-6204 for ICRD-102488 Ends			
			enableField(frm.elements.btSendResdit);	
		}
		if(frm.elements.screenMode.value == "POPUP"
		   || frm.elements.screenMode.value == "POPUP_CARDIT"){
		//Added by A-6204 for ICRD-102488 Starts	
				enableField(frm.elements.btOk);
	//Added by A-6204 for ICRD-102488 Ends				

			//if(frm.lookupClose.value == "REFRESH"){
			//	frm.lookupClose.value = "";
			//	reloadParent();
		    //}
		}
	}
    if(frm.elements.lookupClose.value == "CLOSE"){
 		//var consignmentDocNum = frm.consignmentDocument.value;
        	//frm = self.opener.targetFormName;
        	//frm.action="mailtracking.defaults.mailacceptance.refreshacceptmail.do?consignmentDocNum="+consignmentDocNum;
		//frm.method="post";
		//frm.submit();
		//window.closeNow();
        	//return;
        	reloadParent();
    }
}

function invokeLov(obj,name){
	var frm=targetFormName;
	if(name == "subClassLov"){
		if("MAIN"==frm.elements.screenMode.value){
			displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.elements.mailSubclass.value,'OfficeOfExchange','1','mailSubclass','');
		}else{
			displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.elements.mailSubclass.value,'OfficeOfExchange','0','mailSubclass','');
		}
	}
}


function listDetails(frm){
   frm.elements.displayPage.value=1;
   frm.elements.lastPageNum.value=0;
   //Modified by A-5220 for ICRD-21098 starts
   submitForm(frm,'mailtracking.defaults.mailsearch.listload.do?navigationMode=list');
   //Modified by A-5220 for ICRD-21098 ends
}

function callFlightPopUp(){
	var frm = targetFormName;
	var carrierCode = 'carrierCode';
	var flightNumber = 'flightNumber';
	var filghtDate = 'flightDate';
	openPopUp('flight.operation.screenloadflightlov.do?parentScreenId=capacity.monitoring.monitorflight&carrierCodeFldName='+carrierCode+'&flightNumberFldName='+flightNumber+'&flightDateFldName='+filghtDate+'&formNumber=1','700','380');
}

function submitPage(lastPg,displayPg){
    frm=targetFormName;
    frm.elements.lastPageNum.value=lastPg;
    frm.elements.displayPage.value=displayPg;
	//Modified by A-5220 for ICRD-21098 starts
    submitForm(frm,'mailtracking.defaults.mailsearch.listload.do?navigationMode=navigation');
	//Modified by A-5220 for ICRD-21098 ends
}

function clearDetails(frm){

	submitForm(frm,'mailtracking.defaults.mailsearch.clearsearch.do');
}

function closeDetails(frm){

	if(targetFormName.elements.screenMode.value == "POPUP"
	   || targetFormName.elements.screenMode.value == "POPUP_CARDIT"){
	         window.closeNow();
	}else{
		location.href = appPath + "/home.jsp";
	}
}


function lookupOk(){
   var frm = targetFormName;
   var chkbox =document.getElementsByName("selectMail");
  if(targetFormName.elements.screenMode.value == "POPUP"){
  	 //if(validateSelectedCheckBoxes(frm,'selectMail',200000000000,1)){
		IC.util.common.childUnloadEventHandler();
      	 submitForm(frm,'mailtracking.defaults.mailacceptance.lookupokcommand.do');
 	 // }
   }else if(targetFormName.elements.screenMode.value == "POPUP_CARDIT"){
   	//if(validateSelectedCheckBoxes(frm,'selectMail',200000000000,1)){
		IC.util.common.childUnloadEventHandler();
      	 submitForm(frm,'mailtracking.defaults.mailacceptance.carditenquiryokcommand.do');
 	 // }
  }
}

function assignMail(){
	var frm = targetFormName;
	/*var chkbox =document.getElementsByName("selectMail");
	var chkDespatch = document.getElementsByName("despatchChk");
	var selected = "";
    var cnt = 0;
   if(validateSelectedCheckBoxes(frm,'selectMail',200000000000,1)){
      	for(var i=0;i<chkbox.length;i++){
      		if(chkbox[i].checked){
      			if(cnt == 0){
	      			selected = chkbox[i].value;
	      			cnt = 1;
	      		}
	      		else{
	      			selected = selected+","+chkbox[i].value;
	      		}
	      		if(chkDespatch[i].value == "N"){
					showDialog("Please select Mail Bags only.", 1, self);
					return;
	      		}
      		}
      	}
      	var strAction="mailtracking.defaults.reassignmail.screenloadreassignmail.do";
      	var frmScreen = "CONSIGNMENT";
      	var strUrl = strAction+"?container="+selected+"&fromScreen="+frmScreen;
        openPopUp(strUrl,600,350);
   }*/
   var fromScreen='carditEnquiry';
	var displayPageForCardit=frm.elements.displayPage.value;
   submitForm(frm,'mailtracking.defaults.mailacceptance.screenloadmailacceptance.do?fromScreen='+fromScreen+'&displayPageForCardit='+displayPageForCardit);

}

function mailRSN(){
 frm=targetFormName;
 var mailRSNArr =document.getElementsByName("receptacleSerialNumber");
 var mailRSN =document.getElementsByName("receptacleSerialNumber");
   for(var i=0;i<mailRSNArr.length;i++){
      if(mailRSNArr[i].value.length == 1){
          mailRSN[i].value = "00"+mailRSNArr[i].value;
      }
      if(mailRSNArr[i].value.length == 2){
          mailRSN[i].value = "0"+mailRSNArr[i].value;
      }
   }
}
function mailDSN(){
 frm=targetFormName;
 var mailDSNArr =document.getElementsByName("despatchSerialNumber");
 var mailDSN =document.getElementsByName("despatchSerialNumber");
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

function addToList(){

	var frm = targetFormName;
	var check =document.getElementsByName("selectMail");
	var selectedrows="";

	frm.elements.fromButton.value="ADD";

	if(validateSelectedCheckBoxes(frm,'selectMail','',1)){
		for(var i=0;i<check.length;i++) {

			if(check[i].checked){
				if(selectedrows != "")
					selectedrows = selectedrows+","+check[i].value;
				else if(selectedrows== "")
					selectedrows =check[i].value;
			}
		}
   		targetFormName.elements.select.value=selectedrows;
   		submitForm(frm,'mailtracking.defaults.mailacceptance.lookupaddtolist.do');
	}
}

function removeFromList(){

	var frm = targetFormName;
	var check =document.getElementsByName("selectMail");
	var selectedrows="";

	frm.elements.fromButton.value="REMOVE";

	if(validateSelectedCheckBoxes(frm,'selectMail','',1)){
		for(var i=0;i<check.length;i++) {
			if(check[i].checked){
				if(selectedrows != "")
					selectedrows = selectedrows+","+check[i].value;
				else if(selectedrows== "")
					selectedrows =check[i].value;
			}

		}
   		targetFormName.elements.select.value=selectedrows;
   		submitForm(frm,'mailtracking.defaults.mailacceptance.lookupaddtolist.do');
	}
}

function reloadParent() {
 	var consignmentDocNum = targetFormName.elements.consignmentDocument.value;
	window.opener.recreateMultiTableDetails("mailtracking.defaults.mailacceptance.refreshacceptmailforlookup.do?consignmentDocNum="+consignmentDocNum,"div1","div2","ajaxUpdate");
	window.closeNow();
}

//Added by a7531 for icrd-192536
  function attachAWB()
  {
var frm = targetFormName;	
var consignmentNumber=targetFormName.elements.consignmentDocument.value;
var selectedMailbagId = "";
	var chkboxes = document.getElementsByName("selectMail");

 if(consignmentNumber=="" ){
	if(validateSelectedCheckBoxes(targetFormName,'selectMail',1500,1)){
	var selectedIndex = "";
	
	var mailbagid ="";
	if(chkboxes != null){


	//modified by  a-8061 for ICRD-229330 begin
	for(var i=0;i<(chkboxes.length);i++) {
	
	   if(chkboxes[i].checked==true) {
				 selectedIndex=(i+1);
			if((chkboxes.length)>=0){			
				if(selectedMailbagId==""){
				   mailbagid =targetFormName.elements.mailbagId[selectedIndex].value;
					selectedMailbagId= mailbagid;
				}else{
					mailbagid =targetFormName.elements.mailbagId[selectedIndex].value;
					selectedMailbagId= selectedMailbagId+','+mailbagid;
				}
				var notaccepted = targetFormName.elements.notAccepted[selectedIndex].value;
				if(notaccepted!=null && notaccepted == "Y"){
					showDialog({msg:'<bean:message bundle="searchconsignmentResources" key="mailtracking.defaults.carditenquiry.err.attachtoaccepted" />',type:1,parentWindow:self});;
				return ;				
				}
				var awb = targetFormName.elements.awbNumber[selectedIndex].value;//modified by A-7371 as part of ICRD-228233
				if(awb != null && awb != 0)
			{
			 
			showDialog({msg:'<bean:message bundle="searchconsignmentResources" key="mailtracking.defaults.carditenquiry.err.selectmail" />',type:1,parentWindow:self});;
			return ;
			}else{
					// openPopUp("mailtracking.defaults.listconsignment.mailbookingpopup.screenload.do?selectedMailbagId="+selectedMailbagId,1300,650);
 
	}
			}	
	}
	}
	//if(""!=selectedMailbagId){
	
	openPopUp("mailtracking.defaults.listconsignment.mailbookingpopup.screenload.do?selectedMailbagId="+selectedMailbagId,1300,650);
	//}
	//modified by  a-8061 for ICRD-229330 end
	
	}		
	}	 
	}else{ 
	if(chkboxes != null){
for(var i=0;i<(chkboxes.length);i++) {
	
	   if(chkboxes[i].checked==true) { consignmentNumber="";
				 selectedIndex=(i+1);
			if((chkboxes.length)>=0){			
				if(selectedMailbagId==""){
				   mailbagid =targetFormName.elements.mailbagId[selectedIndex].value;
					selectedMailbagId= mailbagid;
				}else{
					mailbagid =targetFormName.elements.mailbagId[selectedIndex].value;
					selectedMailbagId= selectedMailbagId+','+mailbagid;
				}
				var notaccepted = targetFormName.elements.notAccepted[selectedIndex].value;
				if(notaccepted!=null && notaccepted == "Y"){
					showDialog({msg:'<bean:message bundle="searchconsignmentResources" key="mailtracking.defaults.carditenquiry.err.attachtoaccepted" />',type:1,parentWindow:self});;
				return ;				
				}
				var awb = targetFormName.elements.awbNumber[selectedIndex].value;//modified by A-7371 as part of ICRD-228233
				if(awb != null && awb != 0)
			{
			 
			showDialog({msg:'<bean:message bundle="searchconsignmentResources" key="mailtracking.defaults.carditenquiry.err.selectmail" />',type:1,parentWindow:self});;
			return ;
			}else{
					// openPopUp("mailtracking.defaults.listconsignment.mailbookingpopup.screenload.do?selectedMailbagId="+selectedMailbagId,1300,650);
 
	}
				
			}	
	}
	}
	} 
	
	openPopUp('mailtracking.defaults.listconsignment.mailbookingpopup.screenload.do?consignmentDocument='+consignmentNumber+'&selectedMailbagId='+selectedMailbagId,1300,650);
	}
  }


function bookedFlights()
{
var frm = targetFormName;	


  if(validateSelectedCheckBoxes(targetFormName,'selectMail',25,1)){
	var chkboxes = document.getElementsByName("selectMail");
	var selectedIndex = "";
	var selectedMailbagId = "";
	var mailbagid ="";
	//a-8061 added for ICRD-229573
	var awb="";
	var tmpAwb="";
	
	if(chkboxes != null){

	for(var i=0;i<(chkboxes.length);i++) {
	
	   if(chkboxes[i].checked==true) {
	   
	      /*if(selectedIndex==""){

				 selectedIndex=(i+1);
			}else{
				selectedIndex = selectedIndex+","+i;
                } 
				*/
				selectedIndex=(i+1);
				//a-8061 added for ICRD-229573 begin

				if(awb==""){
				
				awb=targetFormName.elements.awbNumber[selectedIndex].value;
				}else{
				tmpAwb=targetFormName.elements.awbNumber[selectedIndex].value;
					if(awb!=tmpAwb){		
					showDialog({msg:'<bean:message bundle="searchconsignmentResources" key="mailtracking.defaults.carditenquiry.err.bookedflights" />',type:1,parentWindow:self});
					return ;
					}
				}
				//a-8061 added for ICRD-229573 end
				
				
				/*if(selectedMailbagId==""){
				   mailbagid =targetFormName.elements.mailbagId[selectedIndex].value;
					selectedMailbagId= selectedMailbagId+mailbagid;
		
				}else{
					selectedMailbagId=selectedMailbagId +","+mailbagid;
				}
				*/
					if(selectedMailbagId==""){
				   mailbagid =targetFormName.elements.mailbagId[selectedIndex].value;
					selectedMailbagId= mailbagid;
				}else{
					mailbagid =targetFormName.elements.mailbagId[selectedIndex].value;
					selectedMailbagId= selectedMailbagId+','+mailbagid;
				}
				
			 	//If the selected row is already attached with AWB throw a validation and don't proceed to attach AWB(check if the AWB infos from the form for the selected row is empty or not.If it is empty pls proceed ,else throw an error message as mentioned in the document).
			/*	var awb = targetFormName.elements.documentNumber[selectedIndex].value;
				if(awb != null)
			{
			showDialog("Please detach already attached mail bag(s)", 1, self);
			}*/
				
	}
	}
	}	
	
        openPopUp('mailtracking.defaults.listconsignment.bookedflightspopup.screenload.do?selectedMailbagId='+selectedMailbagId,600,420);	
	    
 
}
 
}

function detachAWB()
{
var frm = targetFormName;	
  if(validateSelectedCheckBoxes(targetFormName,'selectMail',25,1)){
	var chkboxes = document.getElementsByName("selectMail");
	var selectedIndex = "";
	var selectedMailbagId = "";
	var mailbagid ="";
	var tmpAwb ="";
	if(chkboxes != null){

	for(var i=0;i<(chkboxes.length);i++) {
	
	   if(chkboxes[i].checked==true) {
	   
	     /* if(selectedIndex==""){*/

				 selectedIndex=(i+1);
			/*}else{
				selectedIndex = selectedIndex+","+i;
                } */
				
			if((chkboxes.length)>=0){			 
				if(selectedMailbagId==""){
				   mailbagid =targetFormName.elements.mailbagId[selectedIndex].value;
					selectedMailbagId= mailbagid;
				}else{
					mailbagid =targetFormName.elements.mailbagId[selectedIndex].value;
					selectedMailbagId= selectedMailbagId+','+mailbagid;
				}
			 	
				var awb = targetFormName.elements.awbNumber[selectedIndex].value;
				if(awb != null && awb != 0)
			{
			 
	   // submitForm(targetFormName,'mailtracking.defaults.listconsignment.mailbookingpopup.dettachMailAWB.do?selectedMailbagId='+selectedMailbagId);
			
			}else{
			showDialog({msg:'<bean:message bundle="searchconsignmentResources" key="mailtracking.defaults.carditenquiry.err.detachmailwithoutawb" />',type:1,parentWindow:self});
			return;
			}

				if(tmpAwb==""){
				tmpAwb=targetFormName.elements.awbNumber[selectedIndex].value;
				}else{
					if(awb!=tmpAwb){		
					showDialog({msg:'<bean:message bundle="searchconsignmentResources" key="mailtracking.defaults.carditenquiry.err.bookedflights" />',type:1,parentWindow:self});
					return ;
					}
				}

			}	
	}
	}

	if(selectedMailbagId!="")
	submitForm(targetFormName,'mailtracking.defaults.listconsignment.mailbookingpopup.dettachMailAWB.do?selectedMailbagId='+selectedMailbagId);
	}		
	   
 
}
}
//Added by A-7929 for ICRD - 224586 starts 
function callbackSearchConsignment(collapse,collapseFilterOrginalHeight,mainContainerHeight){   
               if(!collapse){
                              collapseFilterOrginalHeight=collapseFilterOrginalHeight*(-1);
               }
               //IC.util.widget.updateTableContainerHeight(jquery("div1"),+collapseFilterOrginalHeight);
               
}
//Added by A-7929 for ICRD - 224586  ends

//A-8061 Added for ICRD-82434 starts
function prepareAttributes(event,obj,div,divName,dialogWidth,dialogHeight){
	var invId=obj.id;
	var divId;
	var indexId=invId.split('_')[2];
	if(indexId != null && indexId != ""){
	 divId=div+indexId;
	}else{
	 divId=div+'';
	}
	IC.util.event.stopPropagation(event);
	showInfoMessage(event,divId,invId,divName,dialogWidth,dialogHeight);
}

function sendResdit(frm){

if(validateSelectedCheckBoxes(frm,'selectMail','',1)){
		submitForm(frm,'mailtracking.defaults.carditenquiry.reSendResdit.do');
	}	
}

//A-8061 Added for ICRD-82434 ends
}




function gatherFilterAttributes(reportId){

	var filters;
debugger;
	filters = [
	targetFormName.elements.ooe.value,
	targetFormName.elements.doe.value,
	targetFormName.elements.mailSubclass.value,
	targetFormName.elements.despatchSerialNumber.value,
	targetFormName.elements.consignmentDocument.value,
	targetFormName.elements.fromDate.value,
	targetFormName.elements.toDate.value,
	targetFormName.elements.pao.value,
	targetFormName.elements.documentNumber.value,
	targetFormName.elements.isAwbAttached.value,
	targetFormName.elements.carrierCode.value,
	targetFormName.elements.flightNumber.value,
	"",
	"",
	"",
	"",
	targetFormName.elements.pol.value,
	targetFormName.elements.mailStatus.value,
	"",
	"",	
	];

	return filters;
}


