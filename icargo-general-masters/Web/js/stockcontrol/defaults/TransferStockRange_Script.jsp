<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	var frm=targetFormName;
	with(frm){

		if(frm.elements.checkall){
			evtHandler.addEvents("checkall","doCheckAll(this.form)",EVT_CLICK);
		}
		if(frm.elements.check){
			evtHandler.addEvents("check","undoCheckAll(this.form)",EVT_CLICK);
		}
		evtHandler.addEvents("modeNumberOfDocuments","validateFields(this,-1,'No Of Documents',0,true,true)",EVT_BLUR);
		if(frm.elements.check){
			evtHandler.addEvents("rangeFrom","validateFields(this,-1,'Range From',0,true,true)",EVT_BLUR);
			evtHandler.addEvents("rangeTo","if(validateFields(this,-1,'Range To',0,true,true)){calculateNumberOfDocs(this)}",EVT_BLUR);
			evtHandler.addEvents("noofDocs","validateFields(this,-1,'No Of Docs',0,true,true)",EVT_BLUR);
		}
		evtHandler.addEvents("transferTo","openLOV(event)",EVT_KEYPRESS);
		evtHandler.addEvents("btnTransfer","onclickTransfer()",EVT_CLICK);
		evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);
		evtHandler.addEvents("rangeFrom","validateRange(this)",EVT_FOCUS);
		evtHandler.addEvents("rangeTo","validateRange(this)",EVT_FOCUS);
		evtHandler.addEvents("modeRangeFrom","validateRange(this)",EVT_FOCUS);

	}
	setFocus();
//	DivSetVisible(true);
	callOnLoad();
}

function callFun(frm){
	if(frm == "Y"){
	var stockHolderCode=window.opener.document.forms[1].elements.stockHolderCode.value;
	var docType=window.opener.document.forms[1].elements.docType.value;
	var subType=window.opener.document.forms[1].elements.subType.value;
		window.opener.document.forms[1].action = "stockcontrol.defaults.listmonitorstock.do?stockHolderCode="+stockHolderCode+"&docType="+docType+"&subType="+subType;
		window.opener.IC.util.common.childUnloadEventHandler();
		window.opener.document.forms[1].submit();
		close();
	}
}
//Added by A-5131 for ICRD-24949
function openLOV(e){

	var code = e.keyCode;
	
	if(code == 13){
	displayLov('stockcontrol.defaults.screenloadstockholderlov.do');
	}
	
}

function onClickAdd(){
	targetFormName.elements.manual.disabled=false;
    targetFormName.action="stockcontrol.defaults.addtransferstockrange.do";
	targetFormName.submit();
	disablePage();

}

function onClickDelete(check){
	if(validateIsChecked(check)){
		targetFormName.elements.manual.disabled=false;
		targetFormName.action="stockcontrol.defaults.deletetransferstockrange.do";
		targetFormName.submit();
		disablePage();
	}

}

function displayLov(strAction){
	var stockHolderCode='transferTo';
	var stockHolderType='';
	var val=targetFormName.elements.transferTo.value;
	var typeVal='';
	var strUrl = strAction+"?code="+val+"&codeName="+stockHolderCode+"&stockHolderTypeValue="+typeVal+"&typeName="+stockHolderType+"&formNumber=0";
	openPopUp(strUrl,570,330);
}

function onClickClose(){
 	window.close();
}

function validateIsChecked(check){
	var cnt=0;
	for(var i=0;i<targetFormName.elements.length;i++) {
		if(targetFormName.elements[i].type=="checkbox") {
			if(targetFormName.elements[i].name==check) {
				if(targetFormName.elements[i].checked){
					cnt++;
				}
			}
		}
	}
	if(cnt==0){
		//alert('Please select a row!');
		showDialog({	
			msg		:	'<common:message bundle="transferstockresources" key="stockcontrol.defaults.transferstockrange.pleaseselectarow" scope="request"/>',
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName
		});
		return false;
	}
	return true;
}


function setFocus(){
	targetFormName.elements.transferTo.focus();
}

function doCheckAll(frm){
	 var chke = document.getElementsByName("check");
	 if(frm.elements.checkall.checked == true){
	 	for(var i=0; i<chke.length; i++){
	   		chke[i].checked = true;
	  	}
	 }
	 if(frm.elements.checkall.checked == false){
	 	for(var i=0; i<chke.length; i++){
		 	chke[i].checked = false;
	  	}
	 }
}
//Modified by A-5131 for ICRD-24949
function undoCheckAll(frm){
 	var chke =document.getElementsByName("check");
	var flag=0;
	for(var i=0; i<chke.length; i++){
	 	flag=0;
		if(chke[i].checked == true){
			flag=1;
		}
		if(flag==0){
			break;
		}
	}
	if(flag==1){
		frm.elements.checkall.checked=true;
	}
	else{
		frm.elements.checkall.checked=false;
	}
}

function onclickTransfer(){
   if(targetFormName.elements.transferMode.value=='ranges'){
      var rangeFrom=document.getElementsByName('rangeFrom');
      var rangeTo=document.getElementsByName('rangeTo');
      for(var i=0;i<rangeFrom.length;i++){
       	if(rangeFrom[i].value==""){
   			 //alert('Enter the Range From field');
   			//targetFormName.rangeFrom[i].focus();
   			
			showDialog({	
				msg		:	'<common:message bundle="transferstockresources" key="stockcontrol.defaults.transferstockrange.entertherangefromfield" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
					onClose : function () {
					 rangeFrom[i].focus();
				}
			});
			
			
   			return;
       	}else if(rangeTo[i].value==""){
   			//alert('Enter the Range To field');
   			
			showDialog({	
				msg		:	'<common:message bundle="transferstockresources" key="stockcontrol.defaults.transferstockrange.entertherangetofield" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,	
					onClose : function () {
					 targetFormName.elements.rangeTo[i].focus();
				}
			});
   			
   			return;
       	}
      }
   }
   else if(targetFormName.elements.transferMode.value=='numberOfDocuments'){
   	/* 
	COMMENTED by A-7740 AS A PART OF ICRD-225106
   	 if(targetFormName.elements.modeRangeFrom.value==""){
   	 	
		showDialog({	
				msg		:	'<common:message bundle="transferstockresources" key="stockcontrol.defaults.tranferstock.err.plzenterangefrom" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,	
				onClose : function () {
					 targetFormName.elements.modeRangeFrom.focus();
				}
			});
   	 	
   	 	return;
   	 } */
   	 if(targetFormName.elements.modeNumberOfDocuments.value==""){
		showDialog({	
				msg		:	'<common:message bundle="transferstockresources" key="stockcontrol.defaults.tranferstock.err.plzenternoofdocs" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,	
					onClose : function () {
					 targetFormName.elements.modeNumberOfDocuments.focus();
				}
			});
   	 	
   	 	return;
   	 }
   }
   if(targetFormName.elements.transferTo.value==""){
	   //alert('Enter the Transfer To field');
	   showDialog({	
				msg		:	'<common:message bundle="transferstockresources" key="stockcontrol.defaults.transferstockrange.enterthetransfertofield" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
					onClose : function () {
						 targetFormName.elements.transferTo.focus();
					}
			});
	  
	   return;
   }
   if(!targetFormName.elements.check && targetFormName.elements.transferMode.value=='ranges'){
		//alert('Please specify stock to be transferred');
		
		showDialog({	
				msg		:	'<common:message bundle="transferstockresources" key="stockcontrol.defaults.transferstockrange.pleasespecifystocktobetransferred" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName				
			});
		return;
	}

/*  for(var i=0;i<(rangeFrom.length)-1;i++){
    	for(var j=i+1;j<rangeFrom.length;j++){
     		if(rangeFrom[i].value==""  ||
     			rangeFrom[i].value==""  ||
     			rangeFrom[j].value=="" ||
     			rangeFrom[j].value==""){
     				return;
      		}
			if(parseInt(rangeFrom[i].value)==parseInt(rangeFrom[j].value)
				 && rangeTo[i].value==rangeTo[j].value){
			   //alert('Duplicate ranges cannot be entered');
			   showDialog('<common:message bundle="transferstockresources" key="stockcontrol.defaults.transferstockrange.duplicaterangescannotbeentered" scope="request"/>',1,self);
			   targetFormName.rangeFrom[j].focus();
			   return;
			 }
			 if(parseInt(rangeTo[i].value)==parseInt(rangeFrom[j].value)){
			   //alert('Continuous ranges cannot be entered');
			   showDialog('<common:message bundle="transferstockresources" key="stockcontrol.defaults.transferstockrange.continuousrangescannotbeentered" scope="request"/>',1,self);
			   targetFormName.rangeFrom[j].focus();
			   return;
			 }
			 if(parseInt(rangeTo[i].value)==parseInt(rangeTo[j].value)){
			   //alert('The end ranges cannot be the same');
			   showDialog('<common:message bundle="transferstockresources" key="stockcontrol.defaults.transferstockrange.theendrangescannotbethesame" scope="request"/>',1,self);
			   targetFormName.rangeFrom[j].focus();
			   return;
			 }
			 if(( (parseInt(rangeFrom[i].value)<parseInt(rangeFrom[j].value))
			 	&& (parseInt(rangeTo[i].value)>parseInt(rangeFrom[j].value)))){
				//alert('Overlapping ranges cannot be entered');
				showDialog('<common:message bundle="transferstockresources" key="stockcontrol.defaults.transferstockrange.overlappingrangescannotbenetered" scope="request"/>',1,self);
				targetFormName.rangeFrom[j].focus();
				return;
			 }else if( ((parseInt(rangeTo[j].value)<parseInt(rangeTo[i].value)) &&
				(parseInt(rangeFrom[i].value)<parseInt(rangeTo[j].value)))){
				//alert('Overlapping ranges cannot be entered');
				showDialog('<common:message bundle="transferstockresources" key="stockcontrol.defaults.transferstockrange.overlappingrangescannotbenetered" scope="request"/>',1,self);
				targetFormName.rangeFrom[j].focus();
				return;
			 }
   		}
 	}*/

	callOnLoad();
	showDialog({	
				msg		:	'<common:message bundle="transferstockresources" key="stockcontrol.defaults.transferstockrange.areyousureyouwanttotransfer" scope="request"/>',
				type	:	4, 
				parentWindow:self,
				parentForm:targetFormName,
				dialogId:'id_1',
				onClose : function () {
					screenConfirmDialog(targetFormName,'id_1');
					screenNonConfirmDialog(targetFormName,'id_1');
				}
			});
	/*
	if( confirm('Are you sure you want to Transfer?')){
		targetFormName.manual.disabled=false;
		targetFormName.action="stockcontrol.defaults.transferstockrange.do";
		targetFormName.submit();
		disablePage();
	}*/
}

function screenConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){

	}
	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			frm.action = 'stockcontrol.defaults.transferstockrange.do';
			//frm.submit();
			submitForm(targetFormName,"stockcontrol.defaults.transferstockrange.do");
			disablePage();
		}
	}
}

function screenNonConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){

	}
	if(frm.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){

		}
	}
}

/***************Functions for alphanumeric*****************/

function getLong(range){
	var  base=1;
	var sNumber=0;
	for(var i=range.length-1;i>=0;i--){
		sNumber+=base*parseInt(calculateBase(range.substring(i,i+1)));
		var j=parseInt(asciiOf(range.substring(i,i+1)));
		if (j>57) base*=26;
		else base*=10;
	}
	return sNumber;
}

function calculateBase(xChar){
	var charAscii = parseInt(asciiOf(xChar));
	var base=0;
	if(charAscii>57){
		base= charAscii-65;
	}else{
		base=parseInt(xChar);
	}
	return base;
}

function asciiOf(yChar){
	var validString = "abcdefghijklmnopqrstuvwxyz";
	var ascii = '';
	if (validString.indexOf(yChar) != "-1") {
		for(var i= 96;i<123;i++){
			var s= String.fromCharCode(i);
			if(s == yChar){
				ascii = i;
				break;
			}
		}
	}
	var validString1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	if (validString1.indexOf(yChar) != "-1") {
		for(var i= 65;i<92;i++){
			var s1= String.fromCharCode(i);
			if(s1 == yChar){
				ascii = i;
				break;
			}
		}
	}
	var validString2 = "0123456789";
		if (validString2.indexOf(yChar) != "-1") {
			for(var i= 48;i<58;i++){
				var s3= String.fromCharCode(i);
				if(s3 == yChar){
					ascii = i;
					break;
				}
			}
		}
	return ascii;
}

function calculateNumberOfDocs(obj){
	if(!targetFormName.elements.rangeFrom.length){
		if(targetFormName.elements.rangeFrom.value!=""
			&& targetFormName.elements.rangeTo.value!=""){
			if(getLong(targetFormName.elements.rangeTo.value)<
				getLong(targetFormName.elements.rangeFrom.value)){
				//alert('Range To has to be greater than Range From');
				
				showDialog({	
					msg		:	'<common:message bundle="transferstockresources" key="stockcontrol.defaults.transferstockrange.rangetohastyobegreaterthanrangefrom" scope="request"/>',
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
					onClose : function () {
						targetFormName.elements.rangeFrom.focus();
					}
				});
				
				return;
			}
			targetFormName.elements.noofDocs.value =
				getLong(targetFormName.elements.rangeTo.value) -
					getLong(targetFormName.elements.rangeFrom.value) + 1;

		}
		targetFormName.elements.totalNoOfDocs.value = targetFormName.elements.noofDocs.value;
	}else{
		var id = obj.id.substring(6);
		if(targetFormName.elements.rangeFrom[id].value!=""
			&& targetFormName.elements.rangeTo[id].value!=""){
			if(getLong(targetFormName.elements.rangeTo[id].value)<
				getLong(targetFormName.elements.rangeFrom[id].value)){
				//alert('Range To has to be greater than Range From');
				
				showDialog({	
					msg		:	'<common:message bundle="transferstockresources" key="stockcontrol.defaults.transferstockrange.rangetohastyobegreaterthanrangefrom" scope="request"/>',
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
					onClose : function () {
					 targetFormName.elements.rangeFrom[id].focus();
				}
				});
				
				return;
			}
			targetFormName.elements.noofDocs[id].value =
				getLong(targetFormName.elements.rangeTo[id].value) -
					getLong(targetFormName.elements.rangeFrom[id].value) + 1;
		}
		var rangeFrom=document.getElementsByName('rangeFrom');
		sum=0;
		for(i=0;i<rangeFrom.length;i++){
			sum = parseInt(sum) + parseInt(targetFormName.elements.noofDocs[i].value);
		}
		targetFormName.elements.totalNoOfDocs.value = sum;
	}
}

/*function callOnLoad(){
	if(targetFormName.rangeFrom){
		if(!targetFormName.rangeFrom.length){
				targetFormName.totalNoOfDocs.value = targetFormName.noofDocs.value;
		}else{
			var rangeFrom=document.getElementsByName('rangeFrom');
			sum=0;
				for(i=0;i<rangeFrom.length;i++){
					sum = parseInt(sum) + parseInt(targetFormName.noofDocs[i].value);
				}
			targetFormName.totalNoOfDocs.value = sum;
		}
	}else{
		targetFormName.totalNoOfDocs.value = "0";
	}
}*/

/*function callOnLoad(){
var sum=0;
	if(targetFormName.rangeFrom){
		if(!targetFormName.rangeFrom.length){
			targetFormName.noofDocs.value =
				getLong(targetFormName.rangeTo.value) -
					getLong(targetFormName.rangeFrom.value) + 1;
			targetFormName.totalNoOfDocs.value=targetFormName.noofDocs.value;
		}else{
			for(i=0;i<rangeFrom.length;i++){
				targetFormName.noOfDocs[i].value =
					getLong(targetFormName.rangeTo[i].value) -
						getLong(targetFormName.rangeFrom[i].value) + 1;
				sum+=targetFormName.noOfDocs[i].value;
			}
			targetFormName.totalNoOfDocs.value=sum;
		}
	}else{
		targetFormName.totalNoOfDocs.value = "0";
	}
}*/

function callOnLoad(){
	var sum=0;
	if(targetFormName.elements.rangeFrom){
		if(!targetFormName.elements.rangeFrom.length){
		 	if(targetFormName.elements.rangeFrom.value!=""
				&& targetFormName.elements.rangeTo.value!=""){
				targetFormName.elements.noofDocs.value =
					getLong(targetFormName.elements.rangeTo.value) -
						getLong(targetFormName.elements.rangeFrom.value) + 1;
				targetFormName.elements.totalNoOfDocs.value=targetFormName.elements.noofDocs.value;
			}
		}else{
			var rangeFrom=document.getElementsByName('rangeFrom');
			sum = 0;
			for(i=0;i<rangeFrom.length;i++){
				if(targetFormName.elements.rangeFrom[i].value!=""
					&& targetFormName.elements.rangeTo[i].value!=""){
					targetFormName.elements.noofDocs[i].value =
						getLong(targetFormName.elements.rangeTo[i].value) -
							getLong(targetFormName.elements.rangeFrom[i].value) + 1;
					sum=  parseInt(sum)+parseInt(targetFormName.elements.noofDocs[i].value);
				}
			}
			targetFormName.elements.totalNoOfDocs.value=sum;
		}
	}else{
		targetFormName.elements.totalNoOfDocs.value = "0";
	}
}
function validateRange(object){	
			object.maxLength=targetFormName.elements.documentRange.value;		
}
function onClickImage(index,frmRange){
    var index1 = index;
	var rangeFrom=frmRange;
	openPopUp("stockcontrol.defaults.utiliseddocsinfo.do?fromRange="+rangeFrom+"&&fromScreen=TRANSFER",200,200);
}


function updateTableCode(_tableInfo){	
	_str = _tableInfo.document.getElementById("divTransferAJAX").innerHTML;		
	document.getElementById("divTransfer").innerHTML=_str;
}

function submitPage(lastPageNum,displayPage){
	targetFormName.elements.lastPageNumber.value = lastPageNum;
	targetFormName.elements.displayPage.value=displayPage;
		var strAction="stockcontrol.defaults.transferliststockrange.do?";
		recreateTableDetailsonnext(strAction,"divTransfer");
	
}
function recreateTableDetailsonnext(strAction,divId){	
	var __extraFn="updateTableCode";
	asyncSubmit(targetFormName,strAction,__extraFn,null,null,divId);	
}