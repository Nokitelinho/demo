<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	var frm=document.forms[0];
	with(frm){
		
		if(frm.elements.checkall){
			evtHandler.addEvents("checkall","doCheckAll(this.form)",EVT_CLICK);
		}
		if(frm.elements.check){
			evtHandler.addEvents("check","undoCheckAll(this.form)",EVT_CLICK);
		}
		if(frm.elements.check){
			evtHandler.addEvents("rangeFrom","validateRangeFrom(this)",EVT_BLUR);
			evtHandler.addEvents("rangeTo","validateRangeTo(this)",EVT_BLUR);
			evtHandler.addEvents("noofDocs","validateFields(this,-1,'No Of Docs',0,true,true)",EVT_BLUR);
		}
		evtHandler.addEvents("modeNumberOfDocuments","validateFields(this,-1,'Number of Documents',3,true,true)",EVT_BLUR);
		evtHandler.addEvents("btnReturn","onclickReturn()",EVT_CLICK);
		evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);
		evtHandler.addEvents("rangeFrom","validateRange(this)",EVT_FOCUS);
		evtHandler.addEvents("rangeTo","validateRange(this)",EVT_FOCUS);

	}
	setFocus();
//	DivSetVisible(true);
	callOnLoad();
}

function validateRangeFrom(range){
	if(validateFields(range,-1,'Range From',0,true,true)){
		
	}
}

function validateRangeTo(range){
	if(validateFields(range,-1,'Range To',0,true,true)){
		calculateNumberOfDocs(range);
	}
}

function onClickAdd(){
	//document.forms[0].elements.manual.disabled=false; //Commented as a part of ICRD-321295
	document.forms[0].action="stockcontrol.defaults.addreturnstockrange.do";
	document.forms[0].submit();
	disablePage();
}

function onClickDelete(check){
	if(validateIsChecked(check)){
		//document.forms[0].elements.manual.disabled=false; //Commented as a part of ICRD-321295
		document.forms[0].action="stockcontrol.defaults.deletereturnstockrange.do";
		document.forms[0].submit();
		disablePage();
	}
}

function onClickClose(){
 	window.close();
}

function validateIsChecked(check){
	var cnt=0;
	for(var i=0;i<document.forms[0].elements.length;i++){
		if(document.forms[0].elements[i].type=="checkbox"){
			if(document.forms[0].elements[i].name==check){
				if(document.forms[0].elements[i].checked){
					cnt++;
				}
			}
		}
	}
	if(cnt==0){
		//alert('Please select a row!');
			
			showDialog({	
				msg		:	'<common:message bundle="returnstockresources" key="stockcontrol.defaults.returnstockrange.pleaseselectarow" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName			
			});
		return false;
	}
	return true;
}

function setFocus(){
	document.forms[0].elements.docType.focus();
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

function onclickReturn(){
	var returnModes = document.getElementsByName('returnMode');
	var returnMode_value;
	for(var i = 0; i < returnModes.length; i++){
		if(returnModes[i].checked){
			returnMode_value = returnModes[i].value;
		}
	}
	var rangeFrom=document.getElementsByName('rangeFrom');
	var rangeTo=document.getElementsByName('rangeTo');
		if(document.forms[0].elements.manualchecked==true){
		 document.forms[0].elements.manualCheckFlag.value ='Y';	  
	     }
    for(var i=0;i<rangeFrom.length && returnMode_value=='ranges';i++){
    	/*
		cOMMENTED by A-7740 AS A PART OF ICRD-225106
    	if(rangeFrom[i].value == ""){
    		//alert('Enter the Range From field');
       		
			showDialog({	
				msg		:	'<common:message bundle="returnstockresources" key="stockcontrol.defaults.returnstockrange.entertherangefromfield" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,	
				onClose : function () {
					rangeFrom[i].focus();
				}
			});
       		//document.forms[0].rangeFrom[i].focus();
       		
       		return;
      	}else */
		if(rangeTo[i].value==""){
      		//alert('Enter the Range To field');
		   
			showDialog({	
				msg		:	'<common:message bundle="returnstockresources" key="stockcontrol.defaults.returnstockrange.entertherangetofield" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				onClose : function () {
					document.forms[0].elements.rangeTo[i].focus();
				}
			});
		   
		   	return;
      	}
	if(calculateNumberOfDocs(rangeTo[i])==1){
		return;
	}
}
	if(!document.forms[0].elements.check && returnMode_value=='ranges'){
		//alert('Please specify stock to be returned');
		
		showDialog({	
				msg		:	'<common:message bundle="returnstockresources" key="stockcontrol.defaults.returnstockrange.pleasespecifythestocktobereturned" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName			
			});
		return;
	}

/* 	for(var i=0;i<(rangeFrom.length)-1;i++){
		for(var j=i+1;j<rangeFrom.length;j++){
			if(rangeFrom[i].value==""  ||
				rangeFrom[i].value=="" ||
				rangeFrom[j].value=="" ||
				rangeFrom[j].value==""){
				return;
			}
			if(parseInt(rangeFrom[i].value)==parseInt(rangeFrom[j].value)
				&& rangeTo[i].value==rangeTo[j].value){
				//alert('Duplicate ranges cannot be entered');
				showDialog('<common:message bundle="returnstockresources" key="stockcontrol.defaults.returnstockrange.duplicaterangescannotbeentered" scope="request"/>',1,self);
				document.forms[0].rangeFrom[j].focus();
				return;
			}
			if(parseInt(rangeTo[i].value)==parseInt(rangeFrom[j].value)){
				//alert('Continuous ranges cannot be entered');
				showDialog('<common:message bundle="returnstockresources" key="stockcontrol.defaults.returnstockrange.continuousrangescannotbeentered" scope="request"/>',1,self);
				document.forms[0].rangeFrom[j].focus();
				return;
			}
			if(parseInt(rangeTo[i].value)==parseInt(rangeTo[j].value)){
				//alert('The end ranges cannot be the same');
				showDialog('<common:message bundle="returnstockresources" key="stockcontrol.defaults.returnstockrange.theendrangescannotbethesame" scope="request"/>',1,self);
				document.forms[0].rangeFrom[j].focus();
				return;
			}
			if(( (parseInt(rangeFrom[i].value)<parseInt(rangeFrom[j].value))
				&& (parseInt(rangeTo[i].value)>parseInt(rangeFrom[j].value)))){
				alert('Overlapping ranges cannot be entered');
				document.forms[0].rangeFrom[j].focus();
				return;
			}else if( ((parseInt(rangeTo[j].value)<parseInt(rangeTo[i].value)) &&
				(parseInt(rangeFrom[i].value)<parseInt(rangeTo[j].value)))){
				//alert('Overlapping ranges cannot be entered');
				showDialog('<common:message bundle="returnstockresources" key="stockcontrol.defaults.returnstockrange.overlappingrangescannotbenetered" scope="request"/>',1,self);
				document.forms[0].rangeFrom[j].focus();
				return;
		   }
	   }
   	}*/
	callOnLoad();

	showDialog({	
				msg		:	'<common:message bundle="returnstockresources" key="stockcontrol.defaults.returnstockrange.areyousureyouwanttoreturn" scope="request"/>',
				type	:	4, 
				parentWindow:self,
				parentForm:document.forms[0],
				dialogId:'id_1',
				onClose : function () {
					screenConfirmDialog(document.forms[0],'id_1');
					screenNonConfirmDialog(document.forms[0],'id_1');
				}
			});
 	/*if( confirm('Are you sure you want to Return?')){
  		document.forms[0].manual.disabled=false;
		document.forms[0].action="stockcontrol.defaults.returnstockrange.do";
		document.forms[0].submit();
		disablePage();
	}*/
}

function screenConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){

	}
	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			frm.action = 'stockcontrol.defaults.returnstockrange.do';
			frm.submit();
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

/************Functions for alphanumeric*******************/

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
	if(!document.forms[0].elements.rangeFrom.length){
		if(document.forms[0].elements.rangeFrom.value!=""
				&& document.forms[0].elements.rangeTo.value!=""){
				if(getLong(document.forms[0].elements.rangeTo.value)<
					getLong(document.forms[0].elements.rangeFrom.value)){
					//alert('Range To has to be greater than Range From');
					
					showDialog({	
						msg		:	'<common:message bundle="returnstockresources" key="stockcontrol.defaults.returnstockrange.rangetohastyobegreaterthanrangefrom" scope="request"/>',
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,	
							onClose : function () {
								document.forms[0].elements.check.focus();
							}
					});
					
					return 1;
				}
				document.forms[0].elements.noofDocs.value =
					getLong(document.forms[0].elements.rangeTo.value) -
						getLong(document.forms[0].elements.rangeFrom.value) + 1;

		}
		document.forms[0].elements.totalNoOfDocs.value = document.forms[0].elements.noofDocs.value;
		return 0;
	}else{
	var index = obj.id;
	//var index=Number(document.forms[0].elements.rangeFrom.length)-1;
		if(document.forms[0].elements.rangeFrom[index] && document.forms[0].elements.rangeFrom[index].value!=""
			&& document.forms[0].elements.rangeTo[index] && document.forms[0].elements.rangeTo[index].value!=""){
			if(getLong(document.forms[0].elements.rangeTo[index].value) 
				< getLong(document.forms[0].elements.rangeFrom[index].value)){
				//alert('Range To has to be greater than Range From');
				showDialog({	
				msg		:	'<common:message bundle="returnstockresources" key="stockcontrol.defaults.returnstockrange.rangetohastyobegreaterthanrangefrom" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,	
					onClose : function () {
								document.forms[0].elements.check[index].focus();
							}
			});
				
				return 1;
			}
			document.forms[0].elements.noofDocs[index].value =
				getLong(document.forms[0].elements.rangeTo[index].value) -
					getLong(document.forms[0].elements.rangeFrom[index].value) + 1;
		}
		var rangeFrom=document.getElementsByName('rangeFrom');
		sum=0;
		for(i=0;i<rangeFrom.length;i++){
			sum = parseInt(sum) + parseInt(document.forms[0].elements.noofDocs[i].value);
		}
		document.forms[0].elements.totalNoOfDocs.value = sum;
		return 0;
	}
}

/*function callOnLoad(){
	if(document.forms[1].rangeFrom){
		if(!document.forms[1].rangeFrom.length){
			document.forms[1].totalNoOfDocs.value = document.forms[1].noOfDocs.value;
		}else{
			var rangeFrom=document.getElementsByName('rangeFrom');
			sum=0;
			for(i=0;i<rangeFrom.length;i++){
				sum = parseInt(sum) + parseInt(document.forms[1].noOfDocs[i].value);
			}
			document.forms[1].totalNoOfDocs.value = sum;
		}
	}else{
		document.forms[1].totalNoOfDocs.value = "0";
	}
}*/

function callOnLoad(){
	var sum=0;
	if(document.forms[0].elements.rangeFrom){
		if(!document.forms[0].elements.rangeFrom.length){
		 	if(document.forms[0].elements.rangeFrom.value!=""
				&& document.forms[0].elements.rangeTo.value!=""){
				document.forms[0].elements.noofDocs.value =
					getLong(document.forms[0].elements.rangeTo.value) -
						getLong(document.forms[0].elements.rangeFrom.value) + 1;
				document.forms[0].elements.totalNoOfDocs.value=document.forms[0].elements.noofDocs.value;
			}
		}else{
			var rangeFrom=document.getElementsByName('rangeFrom');
			for(i=0;i<rangeFrom.length;i++){
				if(document.forms[0].elements.rangeFrom[i].value!=""
					&& document.forms[0].elements.rangeTo[i].value!=""){
					document.forms[0].elements.noofDocs[i].value =
						getLong(document.forms[0].elements.rangeTo[i].value) -
							getLong(document.forms[0].elements.rangeFrom[i].value) + 1;
					sum=  parseInt(sum)+parseInt(document.forms[0].elements.noofDocs[i].value);
				}
			}
			document.forms[0].elements.totalNoOfDocs.value=sum;
		}
	}else{
		document.forms[0].elements.totalNoOfDocs.value = "0";
	}
}

function callFun(frm){
	if(frm == "Y"){
	var stockHolderCode=window.opener.document.forms[1].elements.stockHolderCode.value;
	var docType=window.opener.document.forms[1].elements.docType.value;
	var subType=window.opener.document.forms[1].elements.subType.value;
		showDialog({	
				msg		:	document.forms[0].elements.totalNoOfDocs.value+' '+'<common:message bundle="returnstockresources" key="stockcontrol.defaults.returnstock.returnmessage" scope="request"/>',
				type	:	2, 
				parentWindow:self,
				parentForm:document.forms[0],
				onClose : function () {
		window.opener.document.forms[1].action = "stockcontrol.defaults.listmonitorstock.do?stockHolderCode="+stockHolderCode+"&docType="+docType+"&subType="+subType;
							window.opener.IC.util.common.childUnloadEventHandler();
		window.opener.document.forms[1].submit();
		close();
							}
			});
	}
}

function confirmPopupMessage(){
	document.forms[0].elements.warningOk.value="Y";
	document.forms[0].action="stockcontrol.defaults.returnstockrange.do";
	document.forms[0].submit();
}
function validateRange(object){

			object.maxLength=targetFormName.elements.documentRange.value;		
}

function onClickImage(index,frmRange){
    var index1 = index;
	var rangeFrom=frmRange;
	openPopUp("stockcontrol.defaults.utiliseddocsinfo.do?fromRange="+rangeFrom+"&&fromScreen=RETURN",200,200);
}

function updateTableCode(_tableInfo){	
	_str = _tableInfo.document.getElementById("divReturnAJAX").innerHTML;		
	document.getElementById("divReturn").innerHTML=_str;
}

function submitPage(lastPageNum,displayPage){
	targetFormName.elements.lastPageNumber.value = lastPageNum;
	targetFormName.elements.displayPage.value=displayPage;
		var strAction="stockcontrol.defaults.returnliststockrange.do?";
		recreateTableDetailsonnext(strAction,"divReturn");
	
}
function recreateTableDetailsonnext(strAction,divId){	
	var __extraFn="updateTableCode";
	asyncSubmit(targetFormName,strAction,__extraFn,null,null,divId);	
}