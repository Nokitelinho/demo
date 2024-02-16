<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
var rangeMap = new Hashtable();
function screenSpecificEventRegister(){

    disableField(targetFormName.elements.btnAllocate);
	disableField(targetFormName.elements.btnReject);
	disableField(targetFormName.elements.btnApprove);
	disableField(targetFormName.elements.btnComplete);
	disableField(targetFormName.elements.btnEditRange);
	disableField(targetFormName.elements.btnMonitorStock);
	okEnable1();
	var frm=targetFormName;
	with(frm){

		evtHandler.addEvents("stockHolderCode","validateFields(this,-1,'Stock Holder Code',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("btnList","selectAction('list')",EVT_CLICK);
		evtHandler.addEvents("btnClear","selectAction('clear')",EVT_CLICK);
		if(targetFormName.elements.checkBox){
		
			evtHandler.addEvents("approvedStock","restrictInt(this)",EVT_KEYPRESS); 
	
			//evtHandler.addEvents("approvedStock","validateFields(this,-1,'Approved Stock',2,true,true);chk(this)",EVT_BLUR);
		}
		evtHandler.addEvents("btnReject","selectAction('reject')",EVT_CLICK);
		evtHandler.addEvents("btnApprove","selectAction('approve')",EVT_CLICK);
		evtHandler.addEvents("btnComplete","selectAction('complete')",EVT_CLICK);
		evtHandler.addEvents("startRange","validateFields(this,-1,'Start Range',0,true,true)",EVT_BLUR);//changed for ICRD-27230 since decimal values are not allowed
		evtHandler.addEvents("btnListRange","selectAction('listranges')",EVT_CLICK);
		evtHandler.addEvents("btnAllocate","selectAction('allocate')",EVT_CLICK);
		evtHandler.addEvents("btnEditRange","popupaction()",EVT_CLICK);
		evtHandler.addEvents("btnMonitorStock","viewMonitorStock()",EVT_CLICK);
		//evtHandler.addEvents("btnAllocateNew","onClickFunction('stockcontrol.defaults.screenloadallocatenewstock.do')",EVT_CLICK);
		evtHandler.addEvents("btnAllocateNew","doAllocateNewstock()",EVT_CLICK);
		//evtHandler.addEvents("btnClose","location.href('home.jsp')",EVT_CLICK);
		evtHandler.addEvents("btnClose","doClose()",EVT_CLICK);
		evtHandler.addEvents("btnClose","shiftFocus()",EVT_BLUR);
		evtHandler.addEvents("partnerAirline","showPartnerAirlines()",EVT_CLICK);
		evtHandler.addEvents("awbPrefix","populateAirlineName()",EVT_CHANGE);
		targetFormName.elements.docType.focus();
		evtHandler.addEvents("checkAll", "okEnable()",EVT_CLICK);  
		evtHandler.addEvents("checkBox", "okEnable1()",EVT_CLICK);
		evtHandler.addEvents("docType","onChangeOfDocTyp()",EVT_CHANGE);	
		evtHandler.addEvents("startRange","validateRange(this)",EVT_FOCUS);
		evtHandler.addEvents("endRange","validateRange(this)",EVT_FOCUS);
	}
	
	/*
		Toggle awb prefix combo depending on
		the status of the partner airline checkbox
		on screenload
	*/
	showPartnerAirlines();
	
	applySortOnTable("stockTable",new Array("None","StringIgnoreCase","StringIgnoreCase","Date","StringIgnoreCase","StringIgnoreCase","None","StringIgnoreCase","StringIgnoreCase"));
	applySortOnTable("rangeTable",new Array("Number","Number","Number"));

	setFocus();
//	DivSetVisible(true);
	
	if(frm.elements.reportGenerateMode.value == "generatereport") {	
	targetFormName.elements.reportGenerateMode.value="";			
	generateReport(frm,"/stockcontrol.defaults.allocatestock.generatestockallocationreport.do");
	
	
	}
	
	
	
}



function selectAction(actionType){
	var frm = targetFormName;
    if(actionType=="list"){
          if(isValid()){
          		submitForm(frm,"stockcontrol.defaults.liststock.do");
	      }
	}
	if(actionType=="reject"){
        if(validateSelectedCheckBoxes(frm,'checkBox',20,1)){		
			
			showDialog({	
					msg		:	"<common:message bundle="allocatestockresources" key="stockcontrol.defaults.allocatestock.doyouwanttorejectthestockrequest" scope="request"/>",
					type	:	4, 
					parentWindow:self,
					parentForm:targetFormName,
					dialogId:'id_3',
					onClose: function () {
								screenConfirmDialog(frm,'id_1');
			                    screenNonConfirmDialog(frm,'id_1');
							}
				});
			/*
			if(confirm('Do you want to reject the stock request(s)?')){
				submitForm(frm,"stockcontrol.defaults.rejectstock.do");
			}*/
		}
	}
	if(actionType=="approve"){
    	if(validateSelectedCheckBoxes(frm,'checkBox',20,1)){
			
			showDialog({	
					msg		:	"<common:message bundle="allocatestockresources" key="stockcontrol.defaults.allocatestock.doyouwanttoapprovethestockrequest" scope="request"/>",
					type	:	4, 
					parentWindow:self,
					parentForm:targetFormName,
					dialogId:'id_3',
					onClose: function () {
								screenConfirmDialog(frm,'id_2');
			                    screenNonConfirmDialog(frm,'id_2');
							}
				});
			/*
			if(confirm('Do you want to approve the stock requests?')){
				submitForm(frm,"stockcontrol.defaults.approvestock.do");
			}*/
		}
	}
	if(actionType=="clear"){
		submitForm(frm,"stockcontrol.defaults.clear.do");
	}
	if(actionType=="listranges"){
	     if(validateSelectedCheckBoxes(frm,'checkBox',1,1)){
         	if(start()){
         		submitForm(frm,"stockcontrol.defaults.listranges.do");
			}
    	}
	}
	if(actionType=="allocate"){
		if(validateSelectedCheckBoxes(frm,'checkBox',1,1)){
			submitForm(frm,"stockcontrol.defaults.allocatestock.do");
		}
	}
    if(actionType=="complete"){
		if(validateSelectedCheckBoxes(frm,'checkBox',20,1)){		
			
			showDialog({	
					msg		:	"<common:message bundle="allocatestockresources" key="stockcontrol.defaults.allocatestock.doyouwanttocompletethestockrequest" scope="request"/>",
					type	:	4, 
					parentWindow:self,
					parentForm:targetFormName,
					dialogId:'id_3',
					onClose: function () {
							  screenConfirmDialog(frm,'id_3');
			                  screenNonConfirmDialog(frm,'id_3');
							}
				});
			/*
			if(confirm('Do you want to Complete the stock requests?')){
				submitForm(frm,"stockcontrol.defaults.completestock.do");
			}*/
		}
	}
}

function screenConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){

	}
	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			submitForm(frm,'stockcontrol.defaults.rejectstock.do');
		}
		if(dialogId == 'id_2'){
			submitForm(frm,'stockcontrol.defaults.approvestock.do');
		}
		if(dialogId == 'id_3'){
			submitForm(frm,'stockcontrol.defaults.completestock.do');
		}
	}
}

function screenNonConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){

	}
	if(frm.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){

		}
		if(dialogId == 'id_2'){

		}
		if(dialogId == 'id_3'){

		}
	}
}

function check(){
	var frm = targetFormName;
    if(frm.elements.rowCount.value == 1){
		if(frm.elements.checkBox.checked==false){
			//alert("Please select  one row");			
			showDialog({	
						msg		:	"<common:message bundle="allocatestockresources" key="stockcontrol.defaults.allocatestock.pleaseselectonerow" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName
					});   
			return false;
		}else{
			for(var i=0;i<frm.elements.checkBox.length;i++){
   				if(frm.elements.checkBox[i].checked==true ){
					if( i>0&& isChecked == 'Y'){
					   	//alert("Please select a single row");			   
					   	
						showDialog({	
						msg		:	"<common:message bundle="allocatestockresources" key="stockcontrol.defaults.allocatestock.pleaseselectasinglerow" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName
					    }); 
						return false;
					}else{
					     return true;
					}
				}
		     }
		}
	}else{
       //alert("please select only one row");      
	   showDialog({	
						msg		:	"<common:message bundle="allocatestockresources" key="stockcontrol.defaults.allocatestock.pleaseselectonlyonerow" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName
					    }); 
       return false;
   }
}

function isValid(){
	var frm = targetFormName;
	if(frm.elements.docType.value==""){
		//alert("DocumentType is mandatory");		
		 showDialog({	
						msg		:	"<common:message bundle="allocatestockresources" key="stockcontrol.defaults.allocatestock.documenttypeismandatory" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,						
					   onClose: function () {					  
								 frm.elements.docType.focus();
							}
					    }); 
       
		return false;
	}else if(frm.elements.docSubType.value==""){
		//alert('DocumentSubType is mandatory');		
		 showDialog({	
						msg		:	"<common:message bundle="allocatestockresources" key="stockcontrol.defaults.allocatestock.documentsubtypeismandatory" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,						
					    onClose: function () {
								 frm.elements.docSubType.focus();
							}
					    }); 
		
		return false;
	}else if(frm.elements.status.value==""){
		//alert('Status is mandatory');		
		showDialog({	
						msg		:	"<common:message bundle="allocatestockresources" key="stockcontrol.defaults.allocatestock.statusismandatory" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,						
					    onClose: function () {
								 frm.elements.status.focus();
							}
					    }); 
		
		return false;
     } else if(frm.elements.fromDate.value==""){
		//alert('From Date is mandatory');		
		showDialog({	
						msg		:	"<common:message bundle="allocatestockresources" key="stockcontrol.defaults.allocatestock.fromdateismandatory" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,						
					    onClose: function () {
								 frm.elements.fromDate.focus();
							}
					    }); 
		
		return false;
	}else if(frm.elements.toDate.value==""){
		//alert('To Date is mandatory');		
		showDialog({	
						msg		:	"<common:message bundle="allocatestockresources" key="stockcontrol.defaults.allocatestock.todateismandatory" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,						
					    onClose: function () {
								frm.elements.toDate.focus();
							}
					    }); 
		
		return false;
	}else if(frm.elements.stockControlFor.value==""){
		//alert('StockControlFor is mandatory');		
		showDialog({	
						msg		:	"<common:message bundle="allocatestockresources" key="stockcontrol.defaults.allocatestock.stockcontrolforismandatory" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,						
					    onClose: function () {
								frm.elements.stockControlFor.focus();
							}
					    }); 		
		return false;
    }
	return true;
}

function chk(strRef){
	var frm = targetFormName;
    if(frm.elements.allocatedStock.length){
	     frm.elements.toBeallocated[strRef.id].value=(parseInt( frm.elements.approvedStock[strRef.id].value))-(parseInt( frm.elements.allocatedStock[strRef.id].value));
     }else{
   	       frm.elements.toBeallocated.value=
          (parseInt( frm.elements.approvedStock.value))-(parseInt( frm.elements.allocatedStock.value));
     }
}

function popupaction(){
   var frm = targetFormName;
   var isChecked = 'N';
   var refno;
   var stockControlFor=frm.elements.stockControlFor.value;
   if(frm.elements.checkBox){
   	   if(!frm.elements.checkBox.length){
   	       if(frm.elements.checkBox.checked==false){
	  		   //alert("Please select 1 row(s)");	   		  
   		       showDialog({	
						msg		:	"<common:message bundle="allocatestockresources" key="stockcontrol.defaults.allocatestock.pleaseselect1row(s)" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName
					    }); 
			   return;
			}else{
   	           refno = frm.elements.checkBox.value;
   	           isChecked = 'Y';
			}
   	   }else{
			for(var i=0;i<frm.elements.checkBox.length;i++){
	   	     	if(frm.elements.checkBox[i].checked==true ){
				  	if( i>0&& isChecked == 'Y'){
				   		//alert("Please select only one row");
				   		showDialog({	
						msg		:	"<common:message bundle="allocatestockresources" key="stockcontrol.defaults.allocatestock.pleaseselectonlyonerow" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName
					    }); 
						return;
				  	}
   		     		isChecked = 'Y';
   		     		refno =  frm.elements.checkBox[i].value;
	   	        }
   	      	}
   	   	}
	}
	if(isChecked == 'Y'){
		var strUrl="stockcontrol.defaults.screenloadeditrange.do?referenceNo="+refno+"&stockControlFor="+stockControlFor+"&maxRange="+targetFormName.documentRange.value;		
   	}else{
   		//alert("Please select 1 row(s)");   	  
		showDialog({	
						msg		:	"<common:message bundle="allocatestockresources" key="stockcontrol.defaults.allocatestock.pleaseselect1row(s)" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName
					    }); 
   		return;
   	}
     openPopUp(strUrl,850,500);
   	//var myWindow = window.open(strUrl, "LOV", 'status,width=800,height=450,screenX=100,screenY=30,left=70,top=100')
}

function start(){
	var frm = targetFormName;
    if( frm.elements.startRange.value==""){
    	//alert('Start Range is mandatory');        
		showDialog({	
						msg		:	"<common:message bundle="allocatestockresources" key="stockcontrol.defaults.allocatestock.startrangeismandatory" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName
					    }); 
	 	frm.elements.startRange.focus();
		return false;
    }
    return true;
}

//Added By A-2872 For Inserting LOV Instead Of combo

function displayLov(strAction){
	var frm = targetFormName;
	var stockControlFor='stockControlFor';
	var val=frm.elements.stockControlFor.value;
	var strUrl = strAction+"?code="+val+"&codeName="+stockControlFor;
	var clientHeight = document.body.clientHeight;
	var clientWidth = document.body.clientWidth;
	var _reqWidth=(clientWidth*45)/100;
	var _reqHeight = (clientHeight*50)/100;
	openPopUp(strUrl,_reqWidth,_reqHeight);
}

function displayLov1(strAction){
	var frm = targetFormName;
	var stockHolderCode='stockHolderCode';
	var stockHolderType='stockHolderType';
	var val=frm.elements.stockHolderCode.value;
	var typeVal=frm.elements.stockHolderType.value;
	var strUrl = strAction+"?code="+val+"&codeName="+stockHolderCode+"&stockHolderTypeValue="+typeVal+"&typeName="+stockHolderType;
	var clientHeight = document.body.clientHeight;
	var clientWidth = document.body.clientWidth;
	var _reqWidth=(clientWidth*45)/100;
	var _reqHeight = (clientHeight*50)/100;
	openPopUp(strUrl,_reqWidth,_reqHeight);
}


// End

/*function onClickFunction(strAction){
	var frm = targetFormName;
	submitForm(frm,strAction);
}*/


function doAllocateNewstock(){
	var frm = targetFormName;
	frm.elements.buttonStatusFlag.value="fromAllocateStock";
	//submitForm(frm,"stockcontrol.defaults.screenloadallocatenewstock.do");
	submitForm(frm,"stockcontrol.defaults.allocatenewstock.do");
}

function viewMonitorStock(){
   var frm = targetFormName;
   var isChecked = 'N';
   var refno;
   var stockControlFor=frm.elements.stockControlFor.value;
   if(frm.elements.checkBox){
	   if(!frm.elements.checkBox.length){
		   if(frm.elements.checkBox.checked==false){
		   //alert("Please select 1 row(s)");		   
		   showDialog({	
						msg		:	"<common:message bundle="allocatestockresources" key="stockcontrol.defaults.allocatestock.pleaseselect1row(s)" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName
					    }); 
			   return;
		 }else{
		   refno = frm.elements.checkBox.value;
		   isChecked = 'Y';
		 }
	   }else{
			for(var i=0;i<frm.elements.checkBox.length;i++){
				if(frm.elements.checkBox[i].checked==true ){
					if( i>0&& isChecked == 'Y'){
						//alert("Please select only one row");						
						showDialog({	
						msg		:	"<common:message bundle="allocatestockresources" key="stockcontrol.defaults.allocatestock.pleaseselectonlyonerow" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName
					    }); 
						return;
					}
					isChecked = 'Y';
					refno =  frm.elements.checkBox[i].value;
				}
			}
	   }
	}
	if(isChecked == 'Y'){
		frm.elements.closeStatus.value="fromAllocateStock1"; 
		var closesta=frm.elements.closeStatus.value;	
		frm.action="stockcontrol.defaults.viewmonitorstock.do?referenceNo="+refno+"&closeStatus="+closesta;
	}else{
	    //alert("Please select 1 row(s)");	    
		showDialog({	
						msg		:	"<common:message bundle="allocatestockresources" key="stockcontrol.defaults.allocatestock.pleaseselect1row(s)" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName
					    }); 
		return;
	}
	submitForm(frm,frm.action);
}

function setFocus(){
	var frm = targetFormName;
	if(!(targetFormName.elements.docType.disabled)){
		targetFormName.elements.docType.focus();
	}
}

function doClose(){
	var frm=targetFormName;
	submitForm(frm,"stockcontrol.defaults.closeallocatestock.do");
}
function shiftFocus(){
	if(!event.shiftKey){
			if(!(targetFormName.elements.docType.disabled)){
				targetFormName.elements.docType.focus();
			}
	}
}

function showPartnerAirlines(){	
	var partnerPrefix = targetFormName.elements.partnerPrefix.value;
	if(targetFormName.elements.partnerAirline.checked){
	jquery('select[name="awbPrefix"] option[value="'+partnerPrefix+'"]').remove();
	targetFormName.elements.awbPrefix.disabled=false;
		//enableField(targetFormName.awbPrefix);
	}else{
		//targetFormName.awbPrefix.value='';
		//targetFormName.airlineName.value='';		
		//disableField(targetFormName.awbPrefix);
		targetFormName.elements.airlineName.value = "";
		jquery('select[name="awbPrefix"]').append("<option value='" + partnerPrefix + "'> " + partnerPrefix + "</option>");
		jquery('select[name="awbPrefix"]').val(partnerPrefix);
		targetFormName.elements.awbPrefix.disabled=true;
	}
}

function populateAirlineName(){		
	if(targetFormName.elements.awbPrefix.value!=""){
		var splits=targetFormName.elements.awbPrefix.value.split("-");
		targetFormName.elements.airlineName.value=splits[1];
	}
}
function okEnable(){
var checks = document.getElementsByName ('checkAll');
var count = 0;
for(var i=0; i<checks.length;i++){
if(checks[i].checked == true){
count++;
}
}
if(count >= 1){
	enableField(targetFormName.elements.btnAllocate);
	enableField(targetFormName.elements.btnReject);
	enableField(targetFormName.elements.btnApprove);
	enableField(targetFormName.elements.btnComplete);
	enableField(targetFormName.elements.btnEditRange);
	enableField(targetFormName.elements.btnMonitorStock);
}else{
	disableField(targetFormName.elements.btnAllocate);
	disableField(targetFormName.elements.btnReject);
	disableField(targetFormName.elements.btnApprove);
	disableField(targetFormName.elements.btnComplete);
	disableField(targetFormName.elements.btnEditRange);
	disableField(targetFormName.elements.btnMonitorStock);
}
updateHeaderCheckBox(targetFormName,targetFormName.elements.checkAll,targetFormName.elements.checkBox);
}
function okEnable1(){
var checks = document.getElementsByName ('checkBox');
var count = 0;
for(var i=0; i<checks.length;i++){
if(checks[i].checked == true){
count++;
}
}
if(count >= 1){
	enableField(targetFormName.elements.btnAllocate);
	enableField(targetFormName.elements.btnReject);
	enableField(targetFormName.elements.btnApprove);
	enableField(targetFormName.elements.btnComplete);
	enableField(targetFormName.elements.btnEditRange);
	enableField(targetFormName.elements.btnMonitorStock);
}else{
	disableField(targetFormName.elements.btnAllocate);
	disableField(targetFormName.elements.btnReject);
	disableField(targetFormName.elements.btnApprove);
	disableField(targetFormName.elements.btnComplete);
	disableField(targetFormName.elements.btnEditRange);
	disableField(targetFormName.elements.btnMonitorStock);
	}
toggleTableHeaderCheckbox('checkBox',targetFormName.elements.checkAll);
}
function onChangeOfDocTyp(){
	if(targetFormName.elements.docType.value=="INVOICE"){
		targetFormName.elements.partnerAirline.disabled=true;	
		targetFormName.elements.partnerAirline.checked=false;
		showPartnerAirlines();
	} else {
		targetFormName.elements.partnerAirline.disabled=false;		
		showPartnerAirlines();
	}
	findDocRange();
}
function findDocRange(){
__extraFn="stateChange";

var strAction='stockcontrol.defaults.documentrange.do?docTyp='+targetFormName.elements.docType.value;
var oldOne=null;
oldOne = rangeMap.get(targetFormName.elements.docType.value);
	if(!oldOne && oldOne!='undefined'){	
		asyncSubmit(targetFormName,strAction,__extraFn,null);
	} else {	
		targetFormName.elements.documentRange.value=oldOne;
	}
}
function stateChange(tableinfo) {
targetFormName.elements.documentRange.value=tableinfo.document.getElementById('documentRange').innerHTML;
rangeMap.put(targetFormName.elements.docType.value,targetFormName.elements.documentRange.value);
}
function validateRange(object){	
			object.maxLength=targetFormName.elements.documentRange.value;
}