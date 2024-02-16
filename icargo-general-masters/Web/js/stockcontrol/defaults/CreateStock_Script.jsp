<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
var rangeMap = new Hashtable();
function screenSpecificEventRegister(){
//onScreenloadSetHeight();
	var frm=targetFormName;

	with(frm){

		/*if(targetFormName.checkall){
			evtHandler.addEvents("checkall","doCheckAll(targetFormName)",EVT_CLICK);
		}
		if(targetFormName.check){
			evtHandler.addEvents("check","undoCheckAll(targetFormName)",EVT_CLICK);
		}*/
	//@author a-1944 on 21 May 2007 - to show color on row selection
		if(targetFormName.elements.checkall){
					evtHandler.addEvents("checkall","updateHeaderCheckBox(targetFormName,targetFormName.elements.checkall,targetFormName.elements.check)",EVT_CLICK);
				}
				if(targetFormName.elements.check){
					evtHandler.addEvents("check","toggleTableHeaderCheckbox('check',targetFormName.elements.checkall)",EVT_CLICK);
		}
		if(targetFormName.elements.check){
			evtHandler.addEvents("rangeFrom","validateRangeFrom(this)",EVT_BLUR);
			evtHandler.addEvents("rangeTo","validateRangeTo(this)",EVT_BLUR);
			evtHandler.addEvents("noOfDocs","validateFields(this,-1,'No Of Docs',0,true,true)",EVT_BLUR);
		}
		evtHandler.addEvents("totalNoOfDocs","validateFields(this,-1,'Total No Of Docs',3,true,true)",EVT_BLUR);
		evtHandler.addEvents("btsave","onClickSave()",EVT_CLICK);
		evtHandler.addEvents("btclose","doClose()",EVT_CLICK);
		evtHandler.addEvents("btclose","shiftFocus()",EVT_BLUR);
		evtHandler.addEvents("partnerAirline","showPartnerAirlines()",EVT_CLICK);
		evtHandler.addEvents("awbPrefix","populateAirlineName()",EVT_CHANGE);
		evtHandler.addEvents("docType","onChangeOfDocTyp()",EVT_CHANGE);
		evtHandler.addEvents("rangeFrom","validateRange(this)",EVT_FOCUS);
		evtHandler.addEvents("rangeTo","validateRange(this)",EVT_FOCUS);
	}
	showPartnerAirlines()	  

	setFocus();
//	DivSetVisible(true);
	callOnLoad();
}

//Added for screen resolution
function onScreenloadSetHeight(){
	var height = document.body.clientHeight;
	var width = document.body.clientWidth;
    document.getElementById('pageDiv').style.height = ((height*88)/100)+'px';
	document.getElementById('div1').style.height = (((height*85)/100)-168)+'px';

}

function validateRangeFrom(range){
	if(validateFields(range,-1,'Range From',0,true,true)){
		//calculateNumberOfDocs(range);
	}
}

function validateRangeTo(range){
	if(validateFields(range,-1,'Range To',0,true,true)){
		calculateNumberOfDocs(range);
	}
}

function onClickAdd(){
	/*var frm = targetFormName;
	submitForm(frm,"stockcontrol.defaults.addcreatestock.do");*/
	addTemplateRow('rangeTableTemplateRow','rangeTableBody','hiddenOpFlag');
}

function onClickDelete(check){
	var frm = targetFormName;
	if(validateIsChecked(check)){
		deleteTableRow('check','hiddenOpFlag');
		var sum=0;
		var numberOfDocuments = "";
		var rangeFrom = document.getElementsByName('rangeFrom');
		for(i=0;i<rangeFrom.length;i++){
			numberOfDocuments = "";
			if(frm.elements.hiddenOpFlag[i].value != "D" &&
				frm.elements.hiddenOpFlag[i].value != "NOOP"){
			   numberOfDocuments = frm.elements.noOfDocs[i].value;
			}
			if(numberOfDocuments == ""){
				numberOfDocuments = 0;
			}
			sum = parseInt(sum) + parseInt(numberOfDocuments);
		}
		frm.elements.totalNoOfDocs.value = sum;
	}
}

function onClickSave(){
	var frm = targetFormName;
	if(frm.elements.docType.value==""){
		//alert('Document type is mandatory');
		showDialog({	
				msg		:	'<common:message bundle="createstockresources" key="stockcontrol.defaults.createstock.documenttypeismandatory" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				onClose : function () {
						 frm.elements.docType.focus();
					}
			});		
		return;
	}
	if(frm.elements.subType.value==""){
		//alert('Document Sub type is mandatory');		
		showDialog({	
				msg		:	'<common:message bundle="createstockresources" key="stockcontrol.defaults.createstock.documentsubtypeismandatory" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				onClose : function () {
						 frm.elements.subType.focus();
					}
			});		
		return;
	}

	var checkValues = document.getElementsByName('check');
	var hiddenOpFlag = document.getElementsByName('hiddenOpFlag');
	var count = 0;
	if(checkValues.length > 0){
		for(var i=0;i<checkValues.length;i++){
			if(checkValues[i].parentElement.parentElement.id !=
							"rangeTableTemplateRow" && hiddenOpFlag[i].value != "D" &&
							hiddenOpFlag[i].value != "NOOP"){
				count++;
			}

		}
	}
	if(count==0){
		showDialog({	
				msg		:	'<common:message bundle="createstockresources" key="stockcontrol.defaults.createstock.pleasespecifystockrangedetails" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
			});	
		return;
	}
	/*if(!frm.check){
		//alert('Please specify stock range details');
		showDialog('<common:message bundle="createstockresources" key="stockcontrol.defaults.createstock.pleasespecifystockrangedetails" scope="request"/>',1,self);
		return;
	}*/
	if(isValidEntry()){
		//callOnLoad();	 	
		showDialog({	
				msg		:	'<common:message bundle="createstockresources" key="stockcontrol.defaults.createstock.areyousureyouwanttosave" scope="request"/>',
				type	:	4, 
				parentWindow:self,
				parentForm:frm,
				dialogId:'id_1',
				onClose : function () {
						screenConfirmDialog(frm,'id_1');
						screenNonConfirmDialog(frm,'id_1');
					}
			});
		 /*
		if(confirm('Are you sure you want to Save?')){
			submitForm(frm,"stockcontrol.defaults.savecreatestock.do");
		}*/
	}
}

function screenConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){

	}
	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			submitForm(frm,'stockcontrol.defaults.savecreatestock.do');
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



function validateIsChecked(check){
 	var cnt=0;
 	var frm = targetFormName;
	for(var i=0;i<frm.elements.length;i++) {
		if(frm.elements[i].type=="checkbox") {
			if(frm.elements[i].name==check) {
				if(frm.elements[i].checked){
					cnt++;
				}
			}
		}
	}
	if(cnt==0){
		//alert('Please select a row!');
		showDialog({	
				msg		:	'<common:message bundle="createstockresources" key="stockcontrol.defaults.createstock.pleaseselectarow" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
			});
		return false;
	}
	return true;
}

function setFocus(){
	if(!(targetFormName.elements.docType.disabled)){
		targetFormName.elements.docType.focus();
	}	
}

function isValidEntry(){
	var frm = targetFormName;
	var rangeFrom=document.getElementsByName('rangeFrom');
	var rangeTo=document.getElementsByName('rangeTo');
	var hiddenOpFlag=document.getElementsByName('hiddenOpFlag');
	if(rangeFrom.length=="1"){
		if(rangeFrom.parentElement.parentElement.id !=
						"rangeTableTemplateRow" && hiddenOpFlag.value != "D" &&
						hiddenOpFlag.value != "NOOP"){
			if(frm.elements.rangeFrom.value==""){
				 //alert('Enter the Range From field');
				
				 
				 showDialog({	
				msg		:	'<common:message bundle="createstockresources" key="stockcontrol.defaults.createstock.entertherangefromfield" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:frm,
				onClose : function () {
						frm.elements.rangeFrom.focus();
					}
			});
				 return;
			}else if(frm.elements.rangeTo.value==""){
				 //alert('Enter the Range To field');				 
				 showDialog({	
				msg		:	'<common:message bundle="createstockresources" key="stockcontrol.defaults.createstock.entertherangetofield" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:frm,
				onClose : function () {
						frm.elements.rangeTo.focus();
					}
				});
				 return;
			}
		}

	}else{
		for(var i=0;i<rangeFrom.length;i++){
			if(rangeFrom[i].parentElement.parentElement.id !=
							"rangeTableTemplateRow" && hiddenOpFlag[i].value != "D" &&
							hiddenOpFlag[i].value != "NOOP"){
				if(rangeFrom[i].value==""){
					 //alert('Enter the Range From field');
					 
					 showDialog({	
						msg		:	'<common:message bundle="createstockresources" key="stockcontrol.defaults.createstock.entertherangefromfield" scope="request"/>',
						type	:	1, 
						parentWindow:self,
						parentForm:frm,
						onClose : function () {
								frm.elements.rangeFrom[i].focus();
							}
					});
					 return;
				}else if(rangeTo[i].value==""){
					 //alert('Enter the Range To field');					 
					 showDialog({	
						msg		:	'<common:message bundle="createstockresources" key="stockcontrol.defaults.createstock.entertherangetofield" scope="request"/>',
						type	:	1, 
						parentWindow:self,
						parentForm:frm,
						onClose : function () {
								frm.elements.rangeTo[i].focus();
							}
					});
					 return;
				}
			}

		}
	}
	for(var i=0;i<(rangeFrom.length)-1;i++){
	 	for(var j=i+1;j<rangeFrom.length;j++){
			if(rangeFrom[i].parentElement.parentElement.id != "rangeTableTemplateRow"
					&& rangeFrom[j].parentElement.parentElement.id != "rangeTableTemplateRow"
					&& hiddenOpFlag[i].value != "D" && hiddenOpFlag[j].value != "D"
					&& hiddenOpFlag[i].value != "NOOP" && hiddenOpFlag[j].value != "NOOP"){

					if(rangeFrom[i].value==""  ||
						rangeFrom[i].value==""  ||
						rangeFrom[j].value=="" ||
						rangeFrom[j].value==""){
						return;
					}
					if(parseInt(rangeFrom[i].value)==parseInt(rangeFrom[j].value)
						&& rangeTo[i].value==rangeTo[j].value){
						//alert('Duplicate ranges cannot be entered');						
						showDialog({	
							msg		:	'<common:message bundle="createstockresources" key="stockcontrol.defaults.createstock.duplicaterangescannotbeentered" scope="request"/>',
							type	:	1, 
							parentWindow:self,
							parentForm:frm,
							onClose : function () {
									frm.elements.rangeFrom[j].focus();
								}
						});
						return;
					}
					if(parseInt(rangeTo[i].value)==parseInt(rangeFrom[j].value)){
						//alert('Continuous ranges cannot be entered');						
						showDialog({	
							msg		:	'<common:message bundle="createstockresources" key="stockcontrol.defaults.createstock.continuousrangescannotbeentered" scope="request"/>',
							type	:	1, 
							parentWindow:self,
							parentForm:frm,
							onClose : function () {
									frm.elements.rangeFrom[j].focus();
								}
						});
						return;
					}
					if(parseInt(rangeTo[i].value)==parseInt(rangeTo[j].value)){
						//alert('The end ranges cannot be the same');						
						showDialog({	
							msg		:	'<common:message bundle="createstockresources" key="stockcontrol.defaults.createstock.endrangescannotbethesame" scope="request"/>',
							type	:	1, 
							parentWindow:self,
							parentForm:frm,
							onClose : function () {
									frm.elements.rangeFrom[j].focus();
								}
						});
						return;
					}
					if(( (parseInt(rangeFrom[i].value)<parseInt(rangeFrom[j].value))
						&& (parseInt(rangeTo[i].value)>parseInt(rangeFrom[j].value)))){
						//alert('Overlapping ranges cannot be entered');						
						showDialog({	
							msg		:	'<common:message bundle="createstockresources" key="stockcontrol.defaults.createstock.overlappingrangescannotbeentered" scope="request"/>',
							type	:	1, 
							parentWindow:self,
							parentForm:frm,
							onClose : function () {
									frm.elements.rangeFrom[j].focus();
								}
						});
						return;
					}else if( ((parseInt(rangeTo[j].value)<parseInt(rangeTo[i].value)) &&
						(parseInt(rangeFrom[i].value)<parseInt(rangeTo[j].value)))){
						//alert('Overlapping ranges cannot be entered');						
						showDialog({	
							msg		:	'<common:message bundle="createstockresources" key="stockcontrol.defaults.createstock.overlappingrangescannotbeentered" scope="request"/>',
							type	:	1, 
							parentWindow:self,
							parentForm:frm,
							onClose : function () {
									frm.elements.rangeFrom[j].focus();
								}
						});
						return;
					}


			}
	 	}
 	}
	return true;
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
	if(flag==0){
		frm.elements.checkall.checked=false;
	}
}

/**Functions for alphanumeric
*/
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
	var frm = targetFormName;
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
	var frm = targetFormName;
	if(!frm.elements.rangeFrom.length){
		if(frm.elements.rangeFrom.value!=""
			&& frm.elements.rangeTo.value!=""){
				if(getLong(frm.elements.rangeTo.value)<
					getLong(frm.elements.rangeFrom.value)){
					//alert('Range To has to be greater than Range From');				
					showDialog({	
							msg		:	'<common:message bundle="createstockresources" key="stockcontrol.defaults.createstock.rangetohastobegreaterthanrangefrom" scope="request"/>',
							type	:	1, 
							parentWindow:self,
							parentForm:frm,
							onClose : function () {
									frm.elements.rangeFrom.focus();
								}
						});
					return;
				}
				frm.elements.noOfDocs.value =
					getLong(frm.elements.rangeTo.value) -
						getLong(frm.elements.rangeFrom.value) + 1;
		}else{
			if(frm.elements.rangeFrom.parentElement.parentElement.id != "rangeTableTemplateRow"
					&& frm.elements.rangeFrom.parentElement.parentElement.id != "rangeTableTemplateRow"
					&& frm.elements.hiddenOpFlag.value != "D" && frm.elements.hiddenOpFlag.value != "D"
					&& frm.elements.hiddenOpFlag.value != "NOOP" && frm.elements.hiddenOpFlag.value != "NOOP"){
				frm.elements.noOfDocs.value = "0";
			}
		}
	//	frm.elements.totalNoOfDocs.value = frm.elements.noOfDocs.value;
	}else{
		var id = obj.id.substring(11);
		if(frm.elements.rangeFrom[id].value!=""
			&& frm.elements.rangeTo[id].value!=""){
			if(getLong(frm.elements.rangeTo[id].value)<
				getLong(frm.elements.rangeFrom[id].value)){
				//alert('Range To has to be greater than Range From');				
				showDialog({	
							msg		:	'<common:message bundle="createstockresources" key="stockcontrol.defaults.createstock.rangetohastobegreaterthanrangefrom" scope="request"/>',
							type	:	1, 
							parentWindow:self,
							parentForm:frm,
							onClose : function () {
									frm.elements.rangeFrom[id].focus();
								}
						});
				return;
			}
			var rangeFrom= frm.elements.rangeFrom[id].value;
			var rangeTo = frm.elements.rangeTo[id].value;
			rangeFrom = rangeFrom.replace(/[^0-9]+/ig,"");
			rangeTo = rangeTo.replace(/[^0-9]+/ig,"");				
				if(rangeFrom.length<rangeTo.length){
				var lengthDiff=rangeTo.length-rangeFrom.length;				
				for(var i=0;i<lengthDiff;i++){
				rangeFrom="0"+rangeFrom;
				}				
                frm.elements.rangeFrom[id].value = frm.elements.rangeFrom[id].value.replace(/[0-9]/g, '')+rangeFrom;				
				}
			frm.elements.noOfDocs[id].value =(getLong(frm.elements.rangeTo[id].value)-getLong(frm.elements.rangeFrom[id].value)) + 1;
		}else{
		    frm.elements.noOfDocs[id].value = "0";
		}
		var rangeFrom=document.getElementsByName('rangeFrom');
		sum=0;
		var numberOfDocuments = "";
		for(i=0;i<rangeFrom.length;i++){
			numberOfDocuments = "";
			if(frm.elements.hiddenOpFlag[i].value != "D" &&
				frm.elements.hiddenOpFlag[i].value != "NOOP"){
			   numberOfDocuments = frm.elements.noOfDocs[i].value;
			}
			if(numberOfDocuments == ""){
				numberOfDocuments = 0;
			}
			sum = parseInt(sum) + parseInt(numberOfDocuments);
		}
		//frm.elements.totalNoOfDocs.value = sum;
	}
}

/*function callOnLoad(){
	var frm = targetFormName;
	if(frm.rangeFrom){
		if(!frm.rangeFrom.length){
			frm.totalNoOfDocs.value = frm.noOfDocs.value;
		}else{
			var rangeFrom=document.getElementsByName('rangeFrom');
			sum=0;
			for(i=0;i<rangeFrom.length;i++){
				sum = parseInt(sum) + parseInt(frm.noOfDocs[i].value);
			}
			frm.totalNoOfDocs.value = sum;
		}
	}else{
		frm.totalNoOfDocs.value = "0";
	}
}*/

/*function callOnLoad(){
	var frm = targetFormName;
	var sum=0;
	if(frm.rangeFrom){
		if(!frm.rangeFrom.length){
				frm.noOfDocs.value =
					getLong(frm.rangeTo.value) -
						getLong(frm.rangeFrom.value) + 1;
				frm.totalNoOfDocs.value=frm.noOfDocs.value;
		}else{
			for(i=0;i<rangeFrom.length;i++){
				frm.noOfDocs[i].value =
					getLong(frm.rangeTo[i].value) -
						getLong(frm.rangeFrom[i].value) + 1;
					sum=parseInt(sum)+parseInt(frm.noOfDocs[i].value);
			}
			frm.totalNoOfDocs.value=sum;
		}
	}else{
		frm.totalNoOfDocs.value = "0";
	}
}*/

function callOnLoad(){
	var frm = targetFormName;
	var sum=0;
	var numberOfDocuments = "";
	var rangeFrom = document.getElementsByName('rangeFrom');
	for(i=0;i<rangeFrom.length;i++){
		numberOfDocuments = "";
		if(frm.elements.hiddenOpFlag[i].value != "D" &&
			frm.elements.hiddenOpFlag[i].value != "NOOP"){
		   numberOfDocuments = frm.elements.noOfDocs[i].value;
		}
		if(numberOfDocuments == ""){
			numberOfDocuments = 0;
		}
		sum = parseInt(sum) + parseInt(numberOfDocuments);
	}
	//frm.elements.totalNoOfDocs.value = sum;
	//frm.elements.totalNoOfDocs.defaultValue = sum;

}


function doClose(){
	var chke = document.getElementsByName("check");
		for(var i=0; i<chke.length; i++){
				chke[i].checked = false;
	}
	submitFormWithUnsaveCheck("stockcontrol.defaults.closecreatestock.do");
}
function shiftFocus(){
	if(!event.shiftKey){
		if(!(targetFormName.elements.docType.disabled)){
			targetFormName.elements.docType.focus();
		}
	}
}

function showPartnerAirlines(){	
	if(targetFormName.elements.partnerAirline.checked){
		targetFormName.elements.awbPrefix.disabled=false;
	}else{
		targetFormName.elements.airlineName.value = "";
		targetFormName.elements.awbPrefix.value = "";
		targetFormName.elements.awbPrefix.disabled=true;
	}
}

function populateAirlineName(){	
	if(targetFormName.elements.awbPrefix.value!=""){
		var splits=targetFormName.elements.awbPrefix.value.split("-");
		targetFormName.elements.airlineName.value=splits[1];
	}
	else
	{
	targetFormName.elements.airlineName.value = "";
	}
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
	if(!oldOne){	
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