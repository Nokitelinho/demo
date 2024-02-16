<%@ include file="/jsp/includes/js_contenttype.jsp" %>
function screenSpecificEventRegister()
{

	with(targetFormName){
	evtHandler.addEvents("btOk","saveDetails()",EVT_CLICK);
	evtHandler.addEvents("btClose","closeScreen()",EVT_CLICK);
	evtHandler.addEvents("surchargeRevRate","calcAmount(this)",EVT_BLUR);   //Added by A-7540
	}
	screenLoad();
}

function screenLoad(){
	if("close"==targetFormName.surChargeAction.value){
	window.opener.onBlur();
	window.close();
	}
	calcRevTotal();
	calcOrgTotal();
}
function saveDetails(){
	window.opener.document.getElementById('otherRevChgGrossWg').value=targetFormName.revSurChargeTotal.value;
	
	submitForm(targetFormName,'mailtracking.mra.defaults.maintaincca.surchargedetails.do?surChargeAction=Update');
}
function closeScreen(){
	submitForm(targetFormName,'mailtracking.mra.defaults.maintaincca.surchargedetails.do?surChargeAction=Close');
}
function addSurcharge(){
	addTemplateRow('surchargeTemplateRow','surchargeTableBody','surchargeOpFlag');
}
function deleteSurcharge(){
	deleteTableRow('chkSurChg','surchargeOpFlag');
	calcRevTotal();
	calcOrgTotal();
}
function  calcRevTotal(){
var total=0;
	for(var i=0;i<targetFormName.revSurCharge.length;i++){
		if(targetFormName.surchargeOpFlag[i].value!='D' && targetFormName.surchargeOpFlag[i].value!='NOOP'){
			if(targetFormName.revSurCharge[i].value!=""){
			total=total+parseFloat(targetFormName.revSurCharge[i].value.replace(",", ""));
			}
		}
	}
	targetFormName.revSurChargeTotal.value=total;
}
function  calcOrgTotal(){
var total=0;
	for(var i=0;i<targetFormName.orgSurCharge.length;i++){

		if(targetFormName.surchargeOpFlag[i].value!='D' && targetFormName.surchargeOpFlag[i].value!='NOOP'){
			if(targetFormName.orgSurCharge[i].value!=""){
			total=total+parseFloat(targetFormName.orgSurCharge[i].value.replace(",", ""));
			}
		}
	}
	targetFormName.orgSurChargeTotal.value=total;
}

function calcAmount(){
debugger;
var amount=0;
    for(var i=0;i<targetFormName.surchargeRevRate.length;i++){
	  if(targetFormName.surchargeOpFlag[i].value!='D' && targetFormName.surchargeOpFlag[i].value!='NOOP'){
	   if(targetFormName.surchargeRevRate[i].value!=""){ 
       var revRate = targetFormName.surchargeRevRate[i].value.replace(",", "");
       var mailWeight = targetFormName.elements.revGrossWeight.value;
	   //var mailWeight = targetFormName.elements.gpaCode.value;
       amount=revRate*mailWeight;
	   targetFormName.revSurCharge[i].value=amount;
	       }
	     }
	  } 
	  calcRevTotal();
	  calcOrgTotal();
}