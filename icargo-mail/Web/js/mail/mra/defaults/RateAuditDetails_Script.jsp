<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
function screenSpecificEventRegister()
{
	with(targetFormName){





		evtHandler.addEvents("btnList","doList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","doClear()",EVT_CLICK);
		evtHandler.addEvents("btnComputeTotal","doComputeTotal()",EVT_CLICK);
		evtHandler.addEvents("btnViewProration","doViewProration()",EVT_CLICK);
		evtHandler.addEvents("btnCCA","doCCA()",EVT_CLICK);
		evtHandler.addEvents("btnRateAudit","doRateAudit()",EVT_CLICK);
		evtHandler.addEvents("btnSave","doSave()",EVT_CLICK);
		evtHandler.addEvents("btnClose","doClose()",EVT_CLICK);
		evtHandler.addEvents("mailSCLov","invokeMailSCLov()",EVT_CLICK);
		evtHandler.addEvents("dsnNumber","mailDSN()",EVT_BLUR);


	}


	screenload();



}






function screenload(){
	if(!targetFormName.elements.dsnNumber.disabled){
		targetFormName.elements.dsnNumber.focus();
	}
	if(targetFormName.elements.listFlag.value=="N"){
		targetFormName.elements.btnCCA.disabled=true;
		targetFormName.elements.btnViewProration.disabled=true;
		disable();
	}else{
    	targetFormName.elements.dsnNumber.disabled="true";
	    targetFormName.elements.dsnDate.disabled="true";
    	if(targetFormName.elements.applyAudit.value=="Y"){
			targetFormName.elements.applyAudit.checked="true";
		}
		if(targetFormName.elements.dsnStatus.value=="Rate Audited"){
			disable();
		}
    }
    if(targetFormName.elements.showDsnPopUp.value=="TRUE"){
		targetFormName.elements.showDsnPopUp.value="FALSE";
    	openPopUp("mailtracking.mra.defaults.despatchenquiry.popup.do",725,450);
    }
}


function invokeMailSCLov(){
displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.subClass.value,'subClass','1','subClass','',0);
}

function displayDsnLov(){
	//targetFormName.lovFlag.value="true";

	var dsn=targetFormName.elements.dsnNumber.value;
	var dat=targetFormName.elements.dsnDate.value;

	var frmPg="RateAuditDetails";
	openPopUp("mailtracking.mra.defaults.despatchenquiry.popup.do?code="+dsn+"&dsnFilterDate="+dat+"&fromPage="+frmPg,725,450);


}

function doList(){
	submitForm(targetFormName,'mailtracking.mra.defaults.listrateauditdetails.listdsndetail.do');
}

function doClear(){
	submitForm(targetFormName,'mailtracking.mra.defaults.listrateauditdetails.clear.do');
}

function doComputeTotal(){
submitForm(targetFormName,'mailtracking.mra.defaults.listrateauditdetails.validate.do?validateFrom=CMPTOTBTN');
}


function doViewProration(){

	submitForm(targetFormName,'mailtracking.mra.defaults.listrateauditdetails.viewprorate.do');
}

function doCCA(){
//submitForm(targetFormName,'mailtracking.mra.defaults.maintaincca.listccadetails.do?fromScreen=RateAuditDetails');
submitForm(targetFormName,'mailtracking.mra.defaults.maintaincca.cca.do');


}

function doRateAudit(){
submitForm(targetFormName,'mailtracking.mra.defaults.listrateauditdetails.validate.do?validateFrom=RTAUDBTN');
//submitForm(targetFormName,'mailtracking.mra.defaults.listrateauditdetails.rateaudit.do');
}

function doSave(){
submitForm(targetFormName,'mailtracking.mra.defaults.listrateauditdetails.validate.do?validateFrom=SAVEBTN');
}

function doClose(){
submitForm(targetFormName,'mailtracking.mra.defaults.listrateauditdetails.close.do');
}

function disable(){
targetFormName.elements.btnComputeTotal.disabled=true;

	targetFormName.elements.btnRateAudit.disabled=true;
	targetFormName.elements.btnSave.disabled=true;
	targetFormName.elements.updWt.disabled=true;
	targetFormName.elements.category.disabled=true;
	targetFormName.elements.subClass.disabled=true;
	targetFormName.elements.ULD.disabled=true;
	targetFormName.elements.flightCarCod.disabled=true;
	targetFormName.elements.flightNo.disabled=true;
    targetFormName.elements.auditWgtCharge.disabled=true;
	targetFormName.elements.applyAudit.disabled=true;
	targetFormName.elements.billTo.disabled=true;
   	var mailSCLov = document.getElementById("mailSCLov");
  	disableField(mailSCLov);
}

function mailDSN(){

 frm=targetFormName;
 var mailDSNArr =document.getElementsByName("dsnNumber");
 var mailDSN =document.getElementsByName("dsnNumber");
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