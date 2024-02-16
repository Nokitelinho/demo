<%@ include file="/jsp/includes/js_contenttype.jsp" %>



    function screenSpecificEventRegister(){

	var frm=targetFormName;



	with(frm){


	evtHandler.addEvents("btnClose","submitForm(targetFormName,'mailtracking.mra.defaults.listbillingmatrixclose.do')", EVT_CLICK);
	evtHandler.addEvents("btnList","listScreen()",EVT_CLICK);
	evtHandler.addEvents("btnClear","submitForm(targetFormName,'mailtracking.mra.defaults.listbillingmatrixclear.do')", EVT_CLICK);
	//evtHandler.addEvents("btnSave","submitForm(targetFormName,'mailtracking.mra.defaults.listbillingmatrix.save.do')", EVT_CLICK);
	//evtHandler.addEvents("btnChangeStatus", "onChangeStatus()", EVT_CLICK);
    evtHandler.addEvents("btnViewBillingLine","onViewBillingLine()",EVT_CLICK);
	evtHandler.addIDEvents("poaCodeLov","displayLOVCodeNameAndDesc('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.poaCode.value,'GPA Code','1','poaCode','poaName','poaName',0)", EVT_CLICK);
	evtHandler.addIDEvents("airlineCodeLov","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.airlineCode.value,'Airline Code','1','airlineCode','',0)",EVT_CLICK);
	evtHandler.addIDEvents("billingMatrixLov","billingMatrixLOV()",EVT_CLICK);
	evtHandler.addEvents("poaCode","populateGpaName(targetFormName)",EVT_BLUR);
	evtHandler.addEvents("btnChangeStatus", "onChangeStatusClick()", EVT_CLICK);
	onLoad();

	}
 applySortOnTable("captureAgtSettlementMemo",new Array("None","String","String","String","String","Number","Date","Date","String")); 
}
	function billingMatrixLOV(){
		var height = document.body.clientHeight;
		var _reqHeight = (height*49)/100;
	displayLOV('showBillingMatrixLOV.do','N','Y','showBillingMatrixLOV.do',targetFormName.billingMatrixId.value,'Billing Matrix Id','1','billingMatrixId','',0,_reqHeight);
	}
	
	function onLoad() {
	// initial focus on page load.
		if(targetFormName.elements.billingMatrixId.disabled == false) {
		   targetFormName.elements.billingMatrixId.focus();	
	}

	//added for screen resolution
	/*var clientHeight = document.body.clientHeight;
	var clientWidth = document.body.clientWidth;
	document.getElementById('contentdiv').style.height = ((clientHeight*90)/100)+'px';
	//document.getElementById('outertable').style.height=((clientHeight*84)/100)+'px';
	var pageTitle=30;
	var filterlegend=80;
	var filterrow=90;
	var bottomrow=40;
	var height=(clientHeight*84)/100;
	var tableheight=height-(pageTitle+filterlegend+filterrow+bottomrow);
	document.getElementById('div1').style.height=tableheight+'px';
  	//added for screen resolution ends*/
	}

	function listScreen(){

      var a="YES";
	  targetFormName.elements.displayPage.value=1;
	  targetFormName.elements.lastPageNum.value=0;
	  submitForm(targetFormName,"mailtracking.mra.defaults.listbillingmatrixlist.do?paginationMode="+a);
	}

	function submitPage(lastPg,displayPg){
	   var frm=targetFormName;

	       frm.elements.lastPageNum.value=lastPg;
	       frm.elements.displayPage.value=displayPg;
	       submitForm(frm,"mailtracking.mra.defaults.listbillingmatrixlist.do");

	}

function onChangeStatus(){

var frm = targetFormName;
var chkboxes = document.getElementsByName("checkboxes");
var selectedIndexes="";
for(var i = 0;i<chkboxes.length;i++){

	if(chkboxes[i].checked == true){
		if(selectedIndexes!=""){
		selectedIndexes = selectedIndexes+",";
		}
		selectedIndexes = selectedIndexes+chkboxes[i].value;
		//alert(chkboxes[i].value+"kkkk");
	}

}

targetFormName.elements.selectedIndexes.value=selectedIndexes;
var size;
if(chkboxes!= null){
	size = chkboxes.length;
	if(validateSelectedCheckBoxes(frm,"checkboxes",size,1))
		openPopUp("mailtracking.mra.defaults.listbillingmatrix.changestatuspopup.do?fromPage=listbillingmatrix",230,110);
	}
}

//Added by A-2280
function onViewBillingLine(){
var frm=targetFormName;
var selectedIds=document.getElementsByName('checkboxes');
var selectedId="";
   if(validateSelectedCheckBoxes(frm,"checkboxes",1,1)){
    for(var i=0;i<selectedIds.length;i++){
	   if(selectedIds[i].checked){
	     selectedId=selectedIds[i].value;
			break;
	   }
	 }

     submitForm(frm,"mailtracking.mra.defaults.listbillingmatrix.viewbillingline.do?selectedIndexes="+selectedId);

   }

}


function populateGpaName(frm){

	if(frm.elements.poaCode.value !=""){
		divIdSeg="checkScreenRefresh_new";
		recreateTableDetails('divChild',divIdSeg);


	}
	else{
		frm.elements.poaName.value="";
	}
	
}

function onChangeStatusClick(){

	var frm =targetFormName;

	var chkboxes=document.getElementsByName('checkboxes');
	var selectedId=new Array();
	if(validateSelectedCheckBoxes(targetFormName,'checkboxes',1,1)){

		var j=0;
		for(var i=0;i<chkboxes.length;i++){
			if(chkboxes[i].checked){
				selectedId[j]=chkboxes[i].value;
				j++;
			}
		}



	var sta = document.getElementsByName('statusValue');
		var selIndex ='';
		if(chkboxes != null){
			for(var index=0;index<chkboxes.length;index++){
				if(chkboxes[index].checked == true){
					if(selIndex.trim().length == 0){
						selIndex=chkboxes[index].value;
						/*if(sta[selIndex].value == "I"){

							//showDialog('<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.cancelcopy" scope="request"/>',1,self);
						    showDialog({msg:'<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.inactivechangedate" scope="request"/>',type:1,parentWindow:self});
						    return;
						}
						else if (sta[selIndex].value == "C"){

							//showDialog('<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.cancelcopy" scope="request"/>',1,self);
						    showDialog({msg:'<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.inactivechangedate" scope="request"/>',type:1,parentWindow:self});
						    return;
						}*/
						/*else if(sta[selIndex].value == "I"){

							showDialog('<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.inactivecopy" scope="request"/>',1,self);
							return;
						}
						else if(sta[index].value == "E"){
							showDialog('<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.expirecopy" scope="request"/>',1,self);
							return;

							}*/
					}
					else{
						if(sta[index].value == "I"){

							//showDialog('<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.cancelcopy" scope="request"/>',1,self);
						    showDialog({msg:'<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.inactivechangedate" scope="request"/>',type:1,parentWindow:self});
						    return;
						}
						else if (sta[index].value == "C"){

							//showDialog('<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.cancelcopy" scope="request"/>',1,self);
						    showDialog({msg:'<common:message bundle="billingmatrix" key="mailtracking.mra.defaults.listbillingmatrix.status.inactivechangedate" scope="request"/>',type:1,parentWindow:self});
						    return;
						}
						selIndex=selIndex+"-"+chkboxes[index].value;
						selIndex=selIndex+"-"+chkboxes[index].value;
					}
				}
			}
	}
	openPopUp("mailtracking.mra.defaults.listbillingmatrix.changestatuspopupclick.do?selectedIndexes="+selIndex,600,220);
	//openPopUp("mailtracking.mra.defaults.listbillingmatrix.changestatuspopup.do?checkboxes="+selectedId,460,250);
}
}




////////////////// FOR ASYNC SUBMIT ///////////////////////////////////////////////
var _currDivId="";

function clearTable(){
	document.getElementById(_currDivId).innerHTML="";
}

function recreateTableDetails(divId){
	var __extraFn="updateTableCode";
	strAction="mailtracking.mra.defaults.listbillingmatrix.populategpaname.do";

	if(arguments[2]!=null){
		__extraFn=arguments[2];
	}
	asyncSubmit(targetFormName,strAction,__extraFn,null,null,divId);
}
function checkScreenRefresh_new(_tableInfo){

	_innerFrm=_tableInfo.document.getElementsByTagName("form")[0];	
	updateTableCode(_tableInfo);
	
}

function updateTableCode(_tableInfo){
	_str=getActualData(_tableInfo);		
	document.getElementById(_tableInfo.currDivId).innerHTML=_str;  		
   	onLoad();
}

function getActualData(_tableInfo){
	//_frm=_tableInfo.document.getElementsByTagName("table")[0];	
	_frm=_tableInfo.document.getElementById("ajaxDiv");
	return _frm.outerHTML;
}




