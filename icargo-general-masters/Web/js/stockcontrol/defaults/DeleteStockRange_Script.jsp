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
		evtHandler.addEvents("noOfDocs","validateFields(this,-1,'No Of Documents',3,true,true)",EVT_BLUR);
		evtHandler.addEvents("deleteFrom","if(validateFields(this,-1,'Range From',0,true,true)){checkRange(this)}",EVT_BLUR);
		evtHandler.addEvents("deleteTo","if(validateFields(this,-1,'Range To',0,true,true)){checkRange(this)}",EVT_BLUR);
		evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);
		evtHandler.addEvents("btnList","onClickButton(this.form,'list')",EVT_CLICK);
		evtHandler.addEvents("btnDelete","onClickButton(this.form,'delete')",EVT_CLICK);
	}
	setFocus();
}
function onClickButton(formObj,str){
	if(str=='list'){
		submitForm(formObj, "stockcontrol.defaults.listdeletestockrange.do");
	}
	if(str=='delete'){
		var flag=0;
		var chk = document.getElementsByName("check");
		var selected="";
		for(var i=0;i<chk.length;i++){
			if(chk[i].checked){
				selected=selected.concat(i+' ');
				flag=1;
			}
		}
		if(flag){
			targetFormName.elements.selectedRowIds.value=selected;
			showDialog({	
				msg		:	'<common:message bundle="deletestockresources" key="stockcontrol.defaults.deletestockrange.areyousureyouwanttodelete" scope="request"/>',
				type	:	4, 
				parentWindow:self,
				parentForm:targetFormName,
				dialogId:'id_1',
				onClose : function () {
					screenConfirmDialog(targetFormName,'id_2');
					screenNonConfirmDialog(targetFormName,'id_2');
				}
			});
		}else{
			showDialog({	
						msg		:	'<common:message bundle="deletestockresources" key="stockcontrol.defaults.deletestockrange.pleaseselectarow" scope="request"/>',
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName
					});
		}
	}
	
}

function onClickImage(index,frmRange){
    var index1 = index;
	var rangeFrom=frmRange;
	openPopUp("stockcontrol.defaults.utiliseddocsinfo.do?fromRange="+rangeFrom+"&&fromScreen=DELETE",200,200);
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
	if(flag==1){
		frm.elements.checkall.checked=true;
	}
	else{
		frm.elements.checkall.checked=false;
	}
}

function callFun(frm){
	if(frm == "Y"){
		var stockHolderCode=window.opener.document.forms[1].elements.stockHolderCode.value;
		var docType=window.opener.document.forms[1].elements.docType.value;
		var subType=window.opener.document.forms[1].elements.subType.value;
		window.opener.document.forms[1].action = "stockcontrol.defaults.listmonitorstock.do?stockHolderCode="+stockHolderCode+"&docType="+docType+"&subType="+subType;
		IC.util.common.childUnloadEventHandler();
		window.opener.document.forms[1].submit();
		close();
	}
}

function screenConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){

	}
	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_2'){
			frm.action = 'stockcontrol.defaults.deletestockrange.do';
			frm.submit();
			disablePage();
		}
	}
}

function screenNonConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){

	}
	if(frm.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_2'){

		}
	}
}

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

function checkRange(obj){
	if(targetFormName.elements.deleteFrom.value!=""
		&& targetFormName.elements.deleteTo.value!=""){
		if(getLong(targetFormName.elements.deleteTo.value)<
			getLong(targetFormName.elements.deleteFrom.value)){
			showDialog({	
				msg		:	'<common:message bundle="deletestockresources" key="stockcontrol.defaults.deletestockrange.rangetohastobegreaterthanrangefrom" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				onClose : function () {
					targetFormName.elements.deleteTo.focus();
					targetFormName.elements.noOfDocs.value='';
				}
			});	
			return;
		}else{
			targetFormName.elements.noOfDocs.value =
				getLong(targetFormName.elements.deleteTo.value) -
					getLong(targetFormName.elements.deleteFrom.value) + 1;
		}
	}
}

function setFocus(){
	targetFormName.elements.deleteFrom.focus();
}
function updateTableCode(_tableInfo){	
	_str = _tableInfo.document.getElementById("divDeleteAJAX").innerHTML;		
	document.getElementById("divDelete").innerHTML=_str;
}

function submitPage(lastPageNum,displayPage){
	targetFormName.elements.lastPageNumber.value = lastPageNum;
	targetFormName.elements.displayPage.value=displayPage;
		var strAction="stockcontrol.defaults.deleteliststockrange.do?";
		recreateTableDetailsonnext(strAction,"divReturn");
	
}
function recreateTableDetailsonnext(strAction,divId){	
	var __extraFn="updateTableCode";
	asyncSubmit(targetFormName,strAction,__extraFn,null,null,divId);	
}