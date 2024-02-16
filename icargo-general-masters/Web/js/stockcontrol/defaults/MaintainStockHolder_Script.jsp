<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	var frm=targetFormName;
	with(frm){
		evtHandler.addEvents("stockHolderCode","validateFields(this,-1,'Stockholder Code',0,true,true)",EVT_BLUR);
		//evtHandler.addEvents("stockHolderName","validateFields(this,-1,'Stockholder Name',8,true,true)",EVT_BLUR);
		evtHandler.addEvents("controlPrivilege","validateFields(this,-1,'Control privelege',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("contact","validateMyEmail()",EVT_BLUR);
		evtHandler.addEvents("approver","validateFields(this,-1,'Approver',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("reorderLevel","validateFields(this,-1,'Reorder Level',3,true,true)",EVT_BLUR);
		evtHandler.addEvents("reorderQuantity","validateFields(this,-1,'Reorder Quantity',3,true,true)",EVT_BLUR);
		evtHandler.addEvents("autoprocessQuantity","validateFields(this,-1,'Autoprocess Quantity',3,true,true)",EVT_BLUR);
		evtHandler.addEvents("btSave","selectAction('save')",EVT_CLICK);
		evtHandler.addEvents("btClear","clearfn()",EVT_CLICK);
		//evtHandler.addEvents("btnDetails","onClickDetails()",EVT_CLICK);
		//Modified by a-4562 for Icrd-8946 starts
		evtHandler.addEvents("btList","onClickDetails()",EVT_CLICK);
		//Modified by a-4562 for Icrd-8946 ends
		evtHandler.addEvents("btClose","closefn()",EVT_CLICK);	
		evtHandler.addEvents("btClose","setFocus()",EVT_BLUR);		
		evtHandler.addEvents("docType","onChangeOfDocTyp()",EVT_CHANGE);		
	}
	stripedTable();
	addIEonScroll();
	//DivSetVisible(true);
	onLoadFunctions();
	onScreenload();
}
function clearfn(){
	var frm = targetFormName;
	submitForm(frm,"stockcontrol.defaults.clearmaintainstockholder.do");
}
function closefn(){
	submitFormWithUnsaveCheck("stockcontrol.defaults.closestockholder.do");
}
function setFocus(){
	if(!event.shiftKey){		
			if(!(targetFormName.elements.stockHolderType.disabled)){
				targetFormName.elements.stockHolderType.focus();
			}		
		}
	}


function selectAction(actionType){
	var frm = targetFormName;
    	if(actionType=="add"){
		checkUpdation();
		frm.elements.stockHolderType.disabled=false;
		submitForm(frm,"stockcontrol.defaults.addrow.do");
	}
	if(actionType=="save"){
	   if(check()){
	      if(frm.elements.checkBox){
	      		/*showDialog('<common:message bundle="maintainstockholderresources" key="stockcontrol.defaults.maintainstockholder.doyouwanttosavethedata" scope="request"/>', 4, self,frm,'id_1');*/
				
				showDialog({	
				msg		:	'<common:message bundle="maintainstockholderresources" key="stockcontrol.defaults.maintainstockholder.doyouwanttosavethedata" scope="request"/>',
				type	:	4, 
				parentWindow:self,
				parentForm:frm,
				dialogId:'id_1',
				onClose : function () {
					screenConfirmDialog(frm,'id_1');
					screenNonConfirmDialog(frm,'id_1');
				}
			});
				/* if(confirm('Do you want to save the data?')){
					 checkUpdation();
					 frm.stockHolderType.disabled=false;
					 submitForm(frm,"stockcontrol.defaults.save.do");
				  }else{
					 return;
				  }*/
	       }else{
	          //alert("Stock is mandatory");
			  showDialog({	
				msg		:	'<common:message bundle="maintainstockholderresources" key="stockcontrol.defaults.maintainstockholder.stockismandatory" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:frm,
			});
	       }
	   }
	}
}

function screenConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){

	}
	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			checkUpdation();
			frm.elements.stockHolderType.disabled=false;
			submitForm(frm,'stockcontrol.defaults.save.do');
		}
		if(dialogId == 'id_2'){

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
			frm.elements.stockHolderCode.value="";
			frm.elements.stockHolderType.value="";
			frm.elements.stockHolderName.value="";
			frm.elements.controlPrivilege.value="";
			frm.elements.contact.value="";
		}
	}
}

function validateMyEmail() {

	if(targetFormName.elements.contact.value != 0) {
		var splitContact = targetFormName.contact.value.split(',');
		for(var i=0;i<splitContact.length;++i){
		if(validateEmail(splitContact[i])) {
		} else {
			
			showDialog({	
				msg		:	"Invalid E-Mail Format",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				
			});
			targetFormName.elements.contact.focus();
			break;
		}
		}
	}
}


function checkUpdation(){
    setHdOperationFlag();
	getHiddenData();
}

function check(){
	var frm = targetFormName;
    if(frm.elements.stockHolderType.value==""){
        //alert("Stockholder type is mandatory");
		showDialog({	
				msg		:	'<common:message bundle="maintainstockholderresources" key="stockcontrol.defaults.maintainstockholder.stockholdertypeismandatory" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
        frm.elements.stockHolderType.focus();
        return false;
    }
    if(frm.elements.stockHolderCode.value==""){
     	//alert("Stockholder code is mandatory");
     	
		showDialog({	
				msg		:	'<common:message bundle="maintainstockholderresources" key="stockcontrol.defaults.maintainstockholder.stockholdercodeismandatory" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
     	frm.elements.stockHolderCode.focus();
        return false;
   }
   if(frm.elements.stockHolderName.value==""){
        //alert("Stockholder name is mandatory");
        
		showDialog({	
				msg		:	'<common:message bundle="maintainstockholderresources" key="stockcontrol.defaults.maintainstockholder.stockholdernameismandatory" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
        frm.elements.stockHolderName.focus();
        return false;
   }
   if(frm.elements.controlPrivilege.value==""){
        //alert("Control Privilege is mandatory");
        
		showDialog({	
				msg		:	'<common:message bundle="maintainstockholderresources" key="stockcontrol.defaults.maintainstockholder.controlprivilegeismandatory" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
        frm.elements.controlPrivilege.focus();
        return false;
   }

    if(frm.elements.contact.value!=""){
   		var splitContact = targetFormName.contact.value.split(',');
		for(var i=0;i<splitContact.length;++i){
		if(!validateEmail(splitContact[i])){
			 showDialog({	
				msg		:	'<common:message bundle="maintainstockholderresources" key="stockcontrol.defaults.maintainstockholder.validemail" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
   			//alert('Please enter a valid e-mail');
   			frm.elements.contact.focus();
   			return;
   		}
   		}
      }


   var flags = document.getElementsByName('OpFlag');
   var docType = document.getElementsByName('docType');
   var docSubType = document.getElementsByName('docSubType');
   for(var i=0;i<flags.length;i++){
   		if(flags[i].value!="D"){
   		     if(docType[i].value==""){
   		         //alert("Doc Type is mandatory");
   		         
				 showDialog({	
				msg		:	'<common:message bundle="maintainstockholderresources" key="stockcontrol.defaults.maintainstockholder.doctypeismandatory" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
   		         frm.elements.docType[i].focus();
   		         return false;
   		     }
   		     if(docSubType[i].value==""){
   		         //alert("Doc Sub Type is mandatory");
   		        
				 showDialog({	
				msg		:	'<common:message bundle="maintainstockholderresources" key="stockcontrol.defaults.maintainstockholder.docsubtypeismandatory" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
   		         frm.elements.docSubType[i].focus();
   		         return false;
   		     }
   		}
   }
    return true;
}

function setOn(){
	var frm = targetFormName;
  	frm.elements.stockHolderType.focus();
}

function deleteTableRow(strAction,checkBoxName){
	var frm = targetFormName;
	if(validateIsChecked(checkBoxName)){
		checkUpdation();
		frm.elements.stockHolderType.disabled=false;
		submitForm(frm,strAction);
	}
}

function setHdOperationFlag(){
	var frm = targetFormName;
	var flags = document.getElementsByName('OpFlag');
	var modifications = document.getElementsByName('isRowModified');
	var docType = document.getElementsByName('docType');
	var docSubType = document.getElementsByName('docSubType');
	var approver = document.getElementsByName('approver');
	var reorderLevel = document.getElementsByName('reorderLevel');
	var reorderQuantity = document.getElementsByName('reorderQuantity');
	var reorderAlertFlag = document.getElementsByName('reorderAlertFlag');
	var autoRequestFlag = document.getElementsByName('autoRequestFlag');
	var autoPopulateFlag = document.getElementsByName('autoPopulateFlag');
	var remarks = document.getElementsByName('remarks');
	for(var i=0; i<flags.length; i++){
		if(flags[i].value != "I" || flags[i].value != "D") {
			if (reorderAlertFlag != null) {
				if (reorderAlertFlag.length > 1) {
						if ((reorderAlertFlag[i].checked != reorderAlertFlag[i].defaultChecked) ||
							(autoRequestFlag[i].checked != autoRequestFlag[i].defaultChecked) ||
							(docType[i].value != docType[i].defaultValue) ||
							(docSubType[i].value != docSubType[i].defaultValue) ||
							(approver[i].value != approver[i].defaultValue) ||
							(reorderLevel[i].value != reorderLevel[i].defaultValue) ||
							(remarks[i].value != remarks[i].defaultValue)||
							(autoPopulateFlag[i].checked != autoPopulateFlag[i].defaultChecked)){
								modifications[i].value = "1";
						}
				}else{
					if((reorderAlertFlag[i].checked != reorderAlertFlag[i].defaultChecked) ||
						(autoRequestFlag[i].checked != autoRequestFlag[i].defaultChecked) ||
						(docType[i].value != docType[i].defaultValue) ||
						(docSubType[i].value != docSubType[i].defaultValue) ||
						(approver[i].value != approver[i].defaultValue) ||
						(reorderLevel[i].value != reorderLevel[i].defaultValue) ||
						(remarks[i].value != remarks[i].defaultValue)||
						(autoPopulateFlag[i].checked != autoPopulateFlag[i].defaultChecked)){
							frm.elements.isRowModified.value = "1";
						}
				}
			}
		}
	}
}

function getHiddenData(){
	var flags = document.getElementsByName('OpFlag');
	var reorderAlertFlag = document.getElementsByName('reorderAlertFlag');
	var autoRequestFlag = document.getElementsByName('autoRequestFlag');
	var autoPopulateFlag = document.getElementsByName('autoPopulateFlag');
	var externalStr ="";
	var internalStr ="";
	var autoPopulateStr ="";
	var frm = targetFormName;
	if(frm.elements.reorderAlertFlag){
		for(var i=0; i<flags.length; i++){
			if (reorderAlertFlag.length > 1){
				if(reorderAlertFlag[i].checked){
					externalStr=externalStr.concat("-true");
				}else{
					externalStr=externalStr.concat("-false");
				}
	  		}else{
				if(reorderAlertFlag[i].checked){
					externalStr=externalStr.concat("-true");
				}else{
					externalStr=externalStr.concat("-false");
				}
			}
		}
	}
	frm.elements.checkedReorder.value=externalStr;
	if(frm.elements.autoRequestFlag){
		for(var i=0; i<flags.length; i++){
			if (autoRequestFlag.length > 1) {
				if(autoRequestFlag[i].checked){
					internalStr=internalStr.concat("-true");
				}else{
					internalStr=internalStr.concat("-false");
				}
		  	}else{
				if(autoRequestFlag[i].checked){
					internalStr=internalStr.concat("-true");
				}else{
					internalStr=internalStr.concat("-false");
				}
			}
		}
	}
	frm.elements.checkedAutoRequest.value=internalStr;
	if(frm.elements.autoPopulateFlag){
		for(var i=0; i<flags.length; i++){
			if (autoPopulateFlag.length > 1){
				if(autoPopulateFlag[i].checked){
					autoPopulateStr=autoPopulateStr.concat("-true");
				}else{
					autoPopulateStr=autoPopulateStr.concat("-false");
				}
	  		}else{
				if(autoPopulateFlag[i].checked){
					autoPopulateStr=autoPopulateStr.concat("-true");
				}else{
					autoPopulateStr=autoPopulateStr.concat("-false");
				}
			}
		}
	}
	frm.elements.checkedAutoPopulate.value=autoPopulateStr;
}

function chkforlov(str){
	var docType = document.getElementsByName('docType');
	var docSubType = document.getElementsByName('docSubType');
	if(docType[str.id].value==""){
		//alert("Doc Type is mandatory");
		
		showDialog({	
				msg		:	'<common:message bundle="maintainstockholderresources" key="stockcontrol.defaults.maintainstockholder.doctypeismandatory" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
		frm.elements.docType[str.id].focus();
		return false;
	}
	if(docSubType[str.id].value==""){
		//alert("Doc Sub Type is mandatory");
		
		showDialog({	
				msg		:	'<common:message bundle="maintainstockholderresources" key="stockcontrol.defaults.maintainstockholder.docsubtypeismandatory" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				
			});
		frm.elements.docSubType[str.id].focus();
		return false;
	}
    return true;
}

function  beforeLovValidation(){
	var flags = document.getElementsByName('OpFlag');
	var docType = document.getElementsByName('docType');
	var docSubType = document.getElementsByName('docSubType');
	for(var i=0;i<flags.length;i++){
		if(flags[i].value!="D"){
			 if(docType[i].value==""){
				 //alert("Doc Type is mandatory");
				 
				 showDialog({	
				msg		:	'<common:message bundle="maintainstockholderresources" key="stockcontrol.defaults.maintainstockholder.doctypeismandatory" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				
			});
				 return false;
			 }
			 if(docSubType[i].value==""){
				  //alert("Doc Sub Type is mandatory");
				 
				  showDialog({	
				msg		:	'<common:message bundle="maintainstockholderresources" key="stockcontrol.defaults.maintainstockholder.docsubtypeismandatory" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				
			});
				  return false;
			 }
		}
 }
 return true;
}

function validateIsChecked(checkBoxName){
	var cnt=0;
	var frm = targetFormName;
	for(var i=0;i<frm.elements.length;i++){
		if(frm.elements[i].type=="checkbox"){
			if(frm.elements[i].name==checkBoxName){
				if(frm.elements[i].checked){
					cnt++;
				}
			}
		}
	}
	if(cnt==0){
		//alert('Please select a row');
		
		showDialog({	
				msg		:	'<common:message bundle="maintainstockholderresources" key="stockcontrol.defaults.maintainstockholder.pleaseselectarow" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				
			});
		return false;
	}
	return true;
}

function displayApproverLov(strAction,nextAction,lovValue,rowid){
	id=lovValue.id;
	var frm = targetFormName;
   	if(chkforlov(lovValue)){
		var flags = document.getElementsByName('OpFlag');
		var docType = document.getElementsByName('docType');
		var docSubType = document.getElementsByName('docSubType');
		/*if(id=="0"){
			frm.documentType.value=docType.value;
			frm.documentSubType.value=docSubType.value;
		}else{*/
			frm.elements.documentType.value=docType[lovValue.id].value;
			frm.elements.documentSubType.value=docSubType[lovValue.id].value;
		//}
		checkUpdation()
		frm.elements.id.value=lovValue.id;
		frm.elements.nextAction.value=nextAction;
		frm.elements.stockHolderType.disabled=false;
		submitForm(frm,"stockcontrol.defaults.update.do?approverCode="+rowid);
	}
}

function onLoadFunctions(){
	var frm = targetFormName;
	if(frm.elements.listSuccessful.value=='N'){
		frm.elements.listSuccessful.value="";
		/*showDialog('<common:message bundle="maintainstockholderresources" key="stockcontrol.defaults.maintainstockholder.warn.douwanttocreateanewone" scope="request"/>'
		, 4, self, frm, 'id_2');*/
		showDialog({	
				msg		:	'<common:message bundle="maintainstockholderresources" key="stockcontrol.defaults.maintainstockholder.warn.douwanttocreateanewone" scope="request"/>',
				type	:	4, 
				parentWindow:self,
				parentForm:targetFormName,
				dialogId:'id_2',
				onClose : function () {
					screenConfirmDialog(frm,'id_2');
					screenNonConfirmDialog(frm,'id_2');
				}
			});
		
	}
	if(frm.elements.nextAction.value !=''){
		displayApprLov( frm.elements.nextAction.value,frm.elements.id.value,
		frm.elements.documentType.value,
		frm.elements.documentSubType.value);
	}
}
function onScreenload(){
		if(!(targetFormName.elements.stockHolderType.disabled)){
			targetFormName.elements.stockHolderType.focus();		
	} 
}

function displayApprLov(strAction,lovValue,docType,docSubType){
	var strUrl =  strAction+"?id="+lovValue+"&docType="+docType+"&docSubType="+docSubType;
	/*
	* Modified by A-1944, on 17th May 2007
	* not to set the attribute scrollbars
	*/
	//modified by A-5221 for ICRD-41909 
	//var myWindow = window.open(strUrl, "LOV", 'width=650,height=350,screenX=100,screenY=30,left=250,top=100')
	var myWindow = openPopUp(strUrl,'650','350');
}

function callOnLoad(){
	var parentFrm = window.opener.targetFormName;
	parentFrm.elements.nextAction.value ="";
}

function displayLov(strAction){
	var stockHolderCode='stockHolderCode';
	var stockHolderType='stockHolderType';
	var frm = targetFormName;
	var val=frm.elements.stockHolderCode.value;
	var typeVal=frm.elements.stockHolderType.value;
	var strUrl = strAction+"?code="+val+"&codeName="+stockHolderCode+"&stockHolderTypeValue="+typeVal+"&typeName="+stockHolderType;
	openPopUp(strUrl,570,330);
}

function onClickDetails(){
	var frm = targetFormName;
	submitForm(frm,"stockcontrol.defaults.viewstockholder.do");
}
function onChangeOfDocTyp(){
	if(targetFormName.elements.docType.value=="INVOICE"){
		targetFormName.elements.awbPrefix.disabled=true;
	} else{		
		targetFormName.elements.awbPrefix.disabled=false;
	}

}