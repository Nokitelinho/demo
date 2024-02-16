<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
var _billArr;
var _newArr;

function screenSpecificEventRegister(){


   var frm=targetFormName;

	with(frm){

	//if(frm.screenStatusFlag != null && frm.screenStatusFlag.value == 'GENERATE_SUCCESS'){
		//showDialog('<common:message bundle="geninvresources" key="mailtracking.mra.gpabilling.generateinvoice.msg.invGenerated" scope="request"/>', 2, self);
	//}

	   evtHandler.addEvents("btnGenInv", "onGenInv()", EVT_CLICK);
	   //evtHandler.addEvents("btnGenInvTK", "onGenInvTK()", EVT_CLICK);
	   evtHandler.addEvents("btnClose", "submitForm(targetFormName,'mailtracking.mra.gpabilling.generateinvoice.onClose.do')", EVT_CLICK);
	   evtHandler.addEvents("btnClose", "resetFocus()", EVT_BLUR);//added by T-1927 for ICRD-18408
	   //evtHandler.addEvents("gpacodelov","displayLOVCodeNameAndDesc('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.gpacode.value,'Gpa Code','1','gpacode','gpaname','gpaname',0)",EVT_CLICK);
	   evtHandler.addIDEvents("countrylov","displayLOV('showCountry.do','N','Y','showCountry.do',targetFormName.elements.country.value,'Country Code','1','country','',0)",EVT_CLICK);
	   evtHandler.addIDEvents("gpacodelov","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.gpacode.value,'GPA Code','1','gpacode','',0)",EVT_CLICK);
	   evtHandler.addEvents("invoiceType","onSelect()",EVT_CHANGE);//Added for ICRD-211662
	  // evtHandler.addIDEvents("SUGGEST_fromDate","getBillingPeriods()",EVT_CLICK);
	   //targetFormName.fromDate.setAttribute("suggest_sel_event","takeMySuggestedValue");
	   //targetFormName.fromDate.setAttribute("direct_value","false");
	   evtHandler.addEvents("invoiceType","disableCheckBox()", EVT_BLUR); //  Added by A-7929 for ICRD-243272 
	}

	onScreenLoad();
}
//added by T-1927 for ICRD-18408
function resetFocus(){

	if(!event.shiftKey){
			if(targetFormName.elements.gpacode.disabled == false && targetFormName.elements.gpacode.readOnly== false){
			targetFormName.elements.gpacode.focus();
		}
	}
}
function onScreenLoad(){

	targetFormName.elements.gpacode.focus();

}

function onGenInv() {



	frm = targetFormName;

	//if(frm.blgPeriodFrom.value == '' || frm.blgPeriodTo.value == ''){

		//showDialog('<common:message bundle="geninvresources" key="mailtracking.mra.gpabilling.generateinvoice.msg.err.mnddatefields" scope="request"/>', 1, self);
		//return;
	//}

	submitForm(targetFormName,'mailtracking.mra.gpabilling.generateinvoice.onGenInv.do');
}
//function onGenInvTK(){
	//var frm=targetFormName;
	//submitForm(targetFormName,'mailtracking.mra.gpabilling.generateinvoice.do');
//}
function getBillingPeriods(){

asyncSubmit(targetFormName,"mailtracking.mra.gpabilling.generateinvoice.findBillingPeriod.do", "showBillingPeriodList",null,null);


}
function showBillingPeriodList(_result){


	document.getElementById("tmpSpan").innerHTML=_result;
	targetFormName.elements.blgPeriodFrom.value="";
	targetFormName.elements.blgPeriodTo.value="";

		_billDiv=_result.document.getElementById("_ajBillingPeriods");
		_billDivs=_billDiv.getElementsByTagName("div");

		_billArr=new Array();
		_newArr=new Array();

		for(var t=0;t<_billDivs.length;t++)
		{

			_fromDt=_billDivs[t].getAttribute("blgPeriodFrom");
			_toDt=_billDivs[t].getAttribute("blgPeriodTo");

			_billArr.push(new Array(_fromDt,_toDt));
			_newArr.push(new Array(_fromDt,_toDt));

	        }



	targetFormName.elements.SUGGEST_fromDate.focus();




	suggestCntrlFnForCombinationFlds('fromDate','fromDate',_billArr,1,'blgPeriodTo');
	document.getElementById("tmpSpan").innerHTML="";


}
function takeMySuggestedValue()
{


	_res = arguments[0];

	targetFormName.elements.blgPeriodFrom.value=_res[0];
	targetFormName.elements.blgPeriodTo.value=_res[1];


}
// Added by A-7929 for ICRD-243272 starts
function disableCheckBox(){

var invoiceType=targetFormName.elements.invoiceType.value;
	if(invoiceType!=null &&invoiceType=='F')
	{
	targetFormName.elements.addNew.checked=false;
	targetFormName.elements.addNew.disabled=true;
	}
	else
	{
	targetFormName.elements.addNew.disabled=false;
	}
}
// Added by A-7929 for ICRD-243272 ends/**
/*Function for onSelect*/ //Modified for ICRD-251819
function onSelect(){
var type = document.getElementById('invoiceType');
	if(type.value=="F"){
		targetFormName.elements.addNew.disabled=true;
	}else
		targetFormName.elements.addNew.disabled=false;
	}