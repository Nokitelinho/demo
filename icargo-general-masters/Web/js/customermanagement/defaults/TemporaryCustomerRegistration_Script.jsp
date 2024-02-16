<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister(){

	var frm=targetFormName;


	frm.elements.tempId.focus();
	
	with(frm){

		evtHandler.addEvents("btClose","windowClose(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClose","resetFocus(this.form)",EVT_BLUR);
		evtHandler.addIDEvents("stationlov","displayLOV('showStation.do','N','Y','showStation.do',document.forms[1].station.value,'Station Code','1','station','',0)",EVT_CLICK);
		evtHandler.addIDEvents("countrylov","displayLOV('showCountry.do','N','Y','showCountry.do',targetFormName.countryCode.value,'countryCode','1','countryCode','',0)",EVT_CLICK);
		evtHandler.addEvents("btList","list(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClear","doClear(this.form)",EVT_CLICK);
		evtHandler.addEvents("btRequest","doSave(this.form)",EVT_CLICK);
		evtHandler.addIDEvents("tempidLOV","showTempIDLOV()",EVT_CLICK);
		//evtHandler.addIDEvents("tempidLOV","displayLOV('customermanagement.defaults.lov.screenloadtempidlov.do','N','Y','customermanagement.defaults.lov.screenloadtempidlov.do',document.forms[1].tempId.value,'Temp ID','1','station','',0)",EVT_CLICK);
		evtHandler.addEvents("tempId","validateAlphanumeric(targetFormName.elements.tempId,'Temp ID','Invalid Temp ID',true)",EVT_BLUR);
		evtHandler.addEvents("customerName","validateAlphanumericAndSpace(targetFormName.elements.customerName,'Customer Name','Invalid Customer Name',true)",EVT_BLUR);
		evtHandler.addEvents("station","validateAlphanumeric(targetFormName.elements.station,'Station','Invalid station',true)",EVT_BLUR);
		evtHandler.addEvents("phoneNo","validateFields(targetFormName.elements.phoneNo,-1,'Telephone Number',3,'Invalid Telephone Number',true)",EVT_BLUR);
		evtHandler.addEvents("faxNumber","validateFields(targetFormName.elements.faxNumber,-1,'Fax Number',13,'Invalid Fax Number',true)",EVT_BLUR);
		evtHandler.addEvents("mobileNumber","validateFields(targetFormName.elements.mobileNumber,-1,'Mobile Number',13,'Invalid Mobile Number',true)",EVT_BLUR);
		// Commented by A-7137 as part of the CR ICRD-135495
		//evtHandler.addEvents("zipCode","validateZipCode(targetFormName.elements.zipCode,this,'Invalid ZipCode Format')",EVT_BLUR);
		onScreenLoad();
	}



if(frm.closeFlag.value=="listtempcustomerform"){
	frm.elements.tempId.disabled=true;
	frm.elements.btList.disabled=true;
	frm.elements.btClear.disabled=true;
	var tempIDlov = document.getElementById('tempidLOV');
	tempIDlov.disabled=true;



}


}

function onScreenLoad(){




if(targetFormName.elements.custCodeFlag.value=="ISPRESENT"){

targetFormName.elements.active.disabled=true;
targetFormName.elements.customerName.disabled=true;
targetFormName.elements.station.disabled=true;
targetFormName.elements.stationlov.disabled=true;
targetFormName.elements.address.disabled=true;
targetFormName.elements.phoneNo.disabled=true;
targetFormName.elements.emailId.disabled=true;
targetFormName.elements.remark.disabled=true;
targetFormName.elements.btRequest.disabled=true;

}

if(targetFormName.detailsFlag.value=="detailsflag"){

var flag="detailsflag";


submitForm(frm,"customermanagement.defaults.listtempcustreg.do?detailsFlag="+flag);

}

if(targetFormName.elements.saveStatus.value=="success"&& targetFormName.elements.detailsFlag) {

var frm = targetFormName;
targetFormName.elements.saveStatus.value="";
//oClear(frm);
}
}




/*function doClose(frm) {
if(frm.closeFlag.value=="listcharteroperation"){
	submitForm(frm,'customermanagement.defaults.listtempcustreg.do');
	}else{
	submitForm(frm,'customermanagement.defaults.closetemporarycustomerregistration.do');
	}
}*/



function windowClose(frm){
 submitForm(frm,'customermanagement.defaults.closecustaction.do');
}
function resetFocus(frm){
    if(!event.shiftKey){ 
        if((!frm.elements.tempId.disabled) && (!frm.elements.tempId.disabled)){
          frm.elements.tempId.focus();
        }
	}
}

function list(frm){
	submitForm(frm,'customermanagement.defaults.listtemporarycustomerregistration.do');

}

function doClear(frm) {
	submitForm(frm,'customermanagement.defaults.cleartemporarycustomerregistration.do');
}


function submitList(strLastPageNum,strDisplayPage){


	document.forms[1].lastPageNum.value= strLastPageNum;
	document.forms[1].displayPage.value = strDisplayPage;
	document.forms[1].action ="customermanagement.defaults.listtemporarycustomerregistration.do";
	document.forms[1].submit();
	disablePage();
}


function doSave() {

   var frm = document.forms[1];
   var flag=0;
   if(frm.elements.emailId.value !="") {

	var emailFound= validateEmail(frm.elements.emailId.value);
	if(!emailFound){
	   //showDialog('Please Enter a Valid Email ID',1,self);
	   showDialog({	
				msg		:	"<common:message bundle='maintaintempcustomerform' key='customermanagement.defaults.listtempcustreg.msg.err.entervalidemail' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:frm
		});
    flag=1;

	} }
	if(flag == 0){

	 submitForm(frm,'customermanagement.defaults.savetemporarycustomerregistration.do');
 }
 }


function selectNextTempCustomerRegistration(strLastPageNum,strDisplayPage){
	var frm=document.forms[1];
	frm.elements.lastPageNum.value= strLastPageNum;
	frm.elements.displayPage.value = strDisplayPage;
	frm.elements.action ="customermanagement.defaults.charter.navigatetempcustreg.do";
	frm.submit();
	disablePage();

}


function showTempIDLOV(){
var textfiledDesc="";

var strAction="customermanagement.defaults.lov.screenloadtempidlov.do";
var StrUrl=strAction+"?textfiledObj=tempId&formNumber=1&textfiledDesc="+textfiledDesc;
//var myWindow = window.open(StrUrl, "LOV", 'width=500,height=350,screenX=100,screenY=30,left=250,top=100')
var myWindow = openPopUpWithHeight(StrUrl, "500");

}



















