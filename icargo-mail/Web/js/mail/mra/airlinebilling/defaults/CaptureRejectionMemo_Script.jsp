<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister()
{
	with(targetFormName){

		evtHandler.addEvents("btList","doList()",EVT_CLICK);

		evtHandler.addEvents("btClear","doClear()",EVT_CLICK);

		//evtHandler.addEvents("btSave","doSave()",EVT_CLICK);

		evtHandler.addEvents("btSave","onSave()",EVT_CLICK);

		//evtHandler.addEvents("lnkAdd","onadd()",EVT_CLICK);
		//evtHandler.addEvents("lnkDelete","ondelete()",EVT_CLICK);
		evtHandler.addEvents("btClose","doClose()",EVT_CLICK);
		evtHandler.addEvents("btClose","resetFocus()",EVT_BLUR);//added by T-1927 for ICRD-18408
		evtHandler.addEvents("reportedAmount","computeSum(this)",EVT_BLUR);

		evtHandler.addEvents("provisionalAmount","computeSum(this)",EVT_BLUR);

		//evtHandler.addEvents("reportedAmount","calculateRejAmt(targetFormName)",EVT_BLUR);

		//evtHandler.addEvents("provisionalAmount","calculateRejAmt(targetFormName)",EVT_BLUR);

		evtHandler.addIDEvents("airlinelov","showAirline()",EVT_CLICK);

		evtHandler.addEvents("checkboxRow","updateHeaderCheckBox(targetFormName, targetFormName.elements.checkboxRow, targetFormName.elements.select)", EVT_CLICK);
		evtHandler.addEvents("select","toggleTableHeaderCheckbox('select',targetFormName.checkboxRow)", EVT_CLICK);
	}


	screenload();



}

function screenload(){

	if(!targetFormName.elements.airlineCode.disabled) {
		targetFormName.elements.airlineCode.focus();
	}
	else{
		if(targetFormName.elements.select != null){
			targetFormName.elements.select.focus();
		}
	}
	//var clientHeight = document.body.clientHeight;
	//document.getElementById('contentdiv').style.height = ((clientHeight*85)/100)+'px';

	//checkLinkStatus();

}

//Commented By Deepthi as a part of AirNZ926
/*function checkLinkStatus()
{

var flag=targetFormName.elements.linkStatusFlag.value;
	if(flag == "disable"){

		disableLinks();
		targetFormName.elements.btSave.disabled=true;

	}
	else {
		enableLinks();
		targetFormName.elements.btSave.disabled=false;
		//targetFormName.elements.clearancePeriodlov.disabled=true;
		//targetFormName.elements.airlinecodelov.disabled=true;
		//targetFormName.elements.airlinenumberlov.disabled=true;
	}

}*/

// Function for disabling the add and delete link
//Commented By Deepthi as a part of AirNZ926
/*function disableLinks()
 {

 	disableLink(document.getElementById('lnkAdd'));
 	disableLink(document.getElementById('lnkDelete'));


}*/

//Commented By Deepthi as a part of AirNZ926
// Function for enabling the add and delete link

/*function enableLinks()
 {
 	if(document.getElementById('lnkAdd').disabled) {
 		enableLink(document.getElementById('lnkAdd'));
 	}
 	if(document.getElementById('lnkDelete').disabled) {
 		enableLink(document.getElementById('lnkDelete'));
 	}


}*/


 //function for listing
function doList() {

	submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.list.do');
}

// function for saving

function onSave(){

     submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.save.do');
}

//function for clearing

function doClear() {

	submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.clear.do');
}

//function for saving

function doSave() {


	var frm = targetFormName;


	setOperationalFlag(targetFormName);

	if(rowDeleted()) {
			submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.save.do');
	}else {

		if(isFormModified(targetFormName)) {
			submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.save.do');
		}else {

			showDialog('<common:message bundle="capturerejectionmemoresources" key="mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.msg.nodatotsave"/>', 1, self);

		}
	}

}



//function for close
function doClose() {
	//modofied by A-8236 for ICRD-252166
	showDialog({msg:'Unsaved data exists.Do you want to continue?',type :3, parentWindow:self,
						onClose:function(result){
						if(result){
				submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.close.do');
			}
		}
						});
}

//added by T-1927 for ICRD-18408
function resetFocus(){

	if(!event.shiftKey){
		if(targetFormName.elements.airlineCode.disabled == false && targetFormName.elements.airlineCode.readOnly== false){
			targetFormName.elements.airlineCode.focus();
		}
		else{
			targetFormName.elements.select.focus();
		}
	}
}
function confirmMessage(frm) {
	//onAddLink()
	enableLinks();
	//onadd();
	//recreateTableDetails("mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.add.do","div1","updateDataAfterAsync");

	//submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.add.do');

}
function nonconfirmMessage(frm) {
	submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.clear.do');
}


function onadd(){

	setOperationalFlag(targetFormName);

	recreateTableDetails("mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.add.do","div1","updateDataAfterAsync");

	//submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.add.do');

}

//function ondelete(){

//var chkBoxIds = document.getElementsByName('select');
//var chkcount = chkBoxIds.length+1;
// if(validateSelectedCheckBoxes(targetFormName,'select',chkcount,'1')){
//	setOperationalFlag(targetFormName);
//	var rowId = document.getElementsByName('select');

			//recreateTableDetails("mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.delete.do","div1","updateDataAfterAsync");

			////submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.delete.do');
	//}

//}



function setOperationalFlag(targetFormName){


	var operationFlag = document.getElementsByName('operationalFlag');   //for operation flag

	var rowId = document.getElementsByName('select');

	var rejectionDate =document.getElementsByName('rejectionDate');

	var provAmt = document.getElementsByName('provisionalAmount');

	var repAmt = document.getElementsByName('reportedAmount');

	var rejAmt = document.getElementsByName('rejectedAmount');

	var remarks = document.getElementsByName('remarks');





	if (rowId != null) {

		if (rowId.length > 0) {
		       // alert(rowId.length);

			for (var i = 0; i < rowId.length; i++) {
				var checkBoxValue = rowId[i].value;

				//alert(checkBoxValue);

				if((operationFlag[checkBoxValue].value !='D') &&
						(operationFlag[checkBoxValue].value !='U')) {

					if ((rejectionDate[checkBoxValue].value != rejectionDate[checkBoxValue].defaultValue) ||
					(provAmt[checkBoxValue].value != provAmt[checkBoxValue].defaultValue) ||
					(repAmt[checkBoxValue].value != repAmt[checkBoxValue].defaultValue) ||
					(rejAmt[checkBoxValue].value != rejAmt[checkBoxValue].defaultValue) ||
					(remarks[checkBoxValue].value != remarks[checkBoxValue].defaultValue)) {
                              			if(operationFlag[checkBoxValue].value !='I'){
                               				operationFlag[checkBoxValue].value='U';


	 					}

	 				}

	 			}
	 		}

	 	}
	 }//ends (if rowId!=null)




}//ends the function



function showAirline(){

	displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',document.forms[1].airlineCode.value,'Airline code','1','airlineCode','airlineNumber','',0);

}

function showInvoiceLov(img) {
	index=img.id.split('invoiceRefNolov')[1];
	//alert(index);
	var height = document.body.clientHeight;
		var _reqHeight = (height*49)/100;
	var invoiceNo = document.getElementsByName('invoiceNos');
	displayLOV('mra.airlinebilling.defaults.showInvoiceLov.do','N','Y','mra.airlinebilling.defaults.showInvoiceLov.do',targetFormName.elements.invoiceNos.value,'Invoice Number','1','invoiceNos','',index,_reqHeight)

}
function showUPUClearancePeriods(img) {
	index=img.id.split('clearancePeriodLov')[1];
	//alert(index);
	var height = document.body.clientHeight;
		var _reqHeight = (height*49)/100;
	var clrPrd = document.getElementsByName('clearancePeriodLov');
	displayLOV('showUPUClearancePeriods.do','N','Y','showUPUClearancePeriods.do',targetFormName.elements.clearancePeriods.value,'clearance Periods','1','clearancePeriods','',index,_reqHeight)

}

function showClearancePeriodLov(index) {
	var height = document.body.clientHeight;
		var _reqHeight = (height*49)/100;
	var clearancePeriod = document.getElementsByName('clearancePeriods');
	displayLOV('showUPUClearancePeriods.do','N','Y','showUPUClearancePeriods.do',clearancePeriod[index].value,'Clearance Period','1','clearancePeriods','',index,_reqHeight)

}





function rowDeleted() {
   	var operationFlag = document.getElementsByName('operationalFlag');   //for operation flag
   	var flag=0;
   	for(var i=0;i<operationFlag.length;i++) {
   		if(operationFlag[i].value=="D" || operationFlag[i].value=="U" ||operationFlag[i].value=="I")  {
   			flag=1;
   			break;
   		}
       }
       if(flag==0){
       	return false;
       }
       else {
        return true;
       }
}






function updateDataAfterAsync(_tableInfo){



	_innerFrm=_tableInfo.document.getElementsByTagName("form")[0];



	targetFormName.elements.linkStatusFlag.value=_innerFrm.linkStatusFlag.value;


	updateTableCode(_tableInfo);

	checkLinkStatus();

	if(_asyncErrorsExist) return;
 }


////////////////// FOR ASYNC SUBMIT ///////////////////////////////////////////////
var _currDivId="";

function clearTable(){
	document.getElementById(_currDivId).innerHTML="";
}

function recreateTableDetails(strAction,divId){

	var __extraFn="updateTableCode";

	if(arguments[2]!=null){
		__extraFn=arguments[2];
	}

	//_currDivId=divId;

	asyncSubmit(targetFormName,strAction,__extraFn,null,null,divId);
}




function updateTableCode(_tableInfo){


	_str = _tableInfo.getTableData();

	//_str=getActualData(_tableInfo);

	//document.getElementById(_currDivId).innerHTML=_str;


	document.getElementById(_tableInfo.currDivId).innerHTML=_str;


	/*cleanupTmpTable();
	reapplyEvents();
	applyScrollTableStyles();*/


}

function getActualData(_tableInfo){


	document.getElementById("tmpSpan").innerHTML=_tableInfo;

	_frm=document.getElementById("tmpSpan").getElementsByTagName("table")[0];

	return _frm.outerHTML;

}

function cleanupTmpTable(){
	document.getElementById("tmpSpan").innerHTML="";
}

//function onAddLink(){
//	addTemplateRow('targetWeightRow','targetWeightTableBody','operationalFlag');
//}

function onAddLink(){

  	var flag1=0;

  	for(var j=0;j<targetFormName.elements.operationalFlag.length-1;j++){
  	//alert(targetFormName.elements.operationalFlag[j].value)  ;
  	}

          // alert('flag1'+flag1)  ;;
            var invoiceNos=targetFormName.elements.invoiceNos;
            var clearancePeriods=targetFormName.elements.clearancePeriods;
            var rejectionDate=targetFormName.elements.rejectionDate;
            var provisionalAmount=targetFormName.elements.provisionalAmount;
            var reportedAmount=targetFormName.elements.reportedAmount;


            for(var i=0;i<invoiceNos.length-1;i++){
            if(targetFormName.elements.operationalFlag[i].value=='I' || targetFormName.elements.operationalFlag[i].value=='N'){
  	            if(invoiceNos[i].value==""){
  	             //alert('invoiceNos'+flag1);
  	            flag1=1;
  	              //alert('invoiceNos'+flag1);
  	            break;
  	            }
  	             if(clearancePeriods[i].value==""){
		    flag1=1;
		     // alert('clearancePeriods'+flag1);
		    break;
		    }
		    if(rejectionDate[i].value==""){
		    flag1=1;
		    //alert('rejectionDate'+flag1);
		    break;
  	  	     }
  	  	      if(provisionalAmount[i].value==""){
		    flag1=1;
		     //alert('provisionalAmount'+flag1);
		    break;
		    }
		     if(reportedAmount[i].value==""){
		    flag1=1;
		    //alert('reportedAmount'+flag1);
		    break;
		    }
            }
            }
          //alert('flag1--->'+flag1);
            if(flag1==1){
             showDialog('<common:message bundle="capturerejectionmemoresources" key="mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.msg.entermandatory"/>', 1, self);
             //return;
            }else{
             addTemplateRow('targetWeightRow','targetWeightTableBody','operationalFlag');
            }



            //alert(invoiceNos.length);

  	//addTemplateRow('targetWeightRow','targetWeightTableBody','operationalFlag',true);

}

function onDeleteLink(){


	var chkBoxIds = document.getElementsByName('select');
	var isError = 1;
	for(var i = 0; i < chkBoxIds.length; i++){
		if(chkBoxIds[i].checked){
			isError = 0;
		}
	}
	if(isError == 1) {
		showDialog('<common:message bundle="capturerejectionmemoresources" key="pleaseselectarow" scope="request"/>',1,self);
	}
	else{
		deleteTableRow('select','operationalFlag');
	}
}


function computeSum(obj) {

var name =obj.name;

var index =obj.id.split(name)[1];
//var rrr = obj.getAttribute("rowCount");

var provisionalAmt = document.getElementsByName('provisionalAmount');
var reportedAmt = document.getElementsByName('reportedAmount');


var rejectedAmt = document.getElementsByName('rejectedAmount');

	var a=0;
	var b=0;
	var c=0;
	//alert(provisionalAmt[index].value);
	//alert(reportedAmt[index].value);

	if(validateFields(obj,-1,name,2,true,true,16,4)){
		if(obj.value == null || obj.value.trim().length == 0) {
			obj.value = 0;
		}

		if(provisionalAmt[index] != null &&
		    provisionalAmt[index].value != null &&
		    provisionalAmt[index].value.trim().length > 0){

			   a=parseFloat(provisionalAmt[index].value);

		}

		if(reportedAmt[index] != null &&
			reportedAmt[index].value != null &&
			reportedAmt[index].value.trim().length > 0) {

		   b=parseFloat(reportedAmt[index].value);
		}

		//a=provisionalAmt[index].value;
		//b=reportedAmt[index].value;
		c = a-b;


		rejectedAmt[index].value=c.toFixed(2);

	}

}


/////////////////////////////////////////////////////////////////////////////////////////

function calculateRejAmt(frm){

	var frm = targetFormName;
	var opFlags = document.getElementsByName('operationalFlag');
	var provisionalAmt = eval(frm.provisionalAmount);
	//alert(provisionalAmt.value);
	var reportedAmt    = eval(frm.reportedAmount);

	var dimlength = eval(frm.dimLength); //for column1
	var width = eval(frm.width);   //for column2
	var height = eval(frm.height)

	if (opFlags != null) {
	if(opFlags.length>1){
		for (var i = 0; i<opFlags.length; i++) {

					if(frm.provisionalAmt[i].value!=null && frm.provisionalAmt[i].value!=0){
						if(frm.reportedAmt[i].value!=null && frm.reportedAmt[i].value!=0){
								var rejAmt=frm.provisionalAmt[i].value - frm.reportedAmt[i].value;
								//alert("volume!!!!"+vol);
								frm.rejectedAmount[i].value=rejAmt;

						}
					}
	    }
	}else{
		if(frm.provisionalAmt.value!=null && frm.provisionalAmt.value!=0){
			if(frm.reportedAmt.value!=null && frm.reportedAmt.value!=0){
							var rejAmt=frm.provisionalAmt.value-frm.reportedAmt.value;
							frm.rejectedAmount.value=rejAmt;


			}
		}
	}

  }
}