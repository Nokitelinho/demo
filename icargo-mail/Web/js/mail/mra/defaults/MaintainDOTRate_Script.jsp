<%@ include file="/jsp/includes/js_contenttype.jsp" %>



    function screenSpecificEventRegister(){

	with(targetFormName){
		evtHandler.addEvents("btnList","list()",EVT_CLICK);
		evtHandler.addEvents("btnSave","onSave()",EVT_CLICK);
		evtHandler.addEvents("btnClear","onClear()",EVT_CLICK);
		evtHandler.addEvents("btnClose","onClose()",EVT_CLICK);
		evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);//added by T-1927 for ICRD-18408
		evtHandler.addEvents("originlov","displayLOV('showStation.do','N','Y','showStation.do',targetFormName.elements.sectorOriginCode.value,'Origin Code','1','sectorOriginCode','',0)",EVT_CLICK);
		evtHandler.addEvents("destinationlov","displayLOV('showStation.do','N','Y','showStation.do',targetFormName.elements.sectorDestinationCode.value,'Destination Code','1','sectorDestinationCode','',0)",EVT_CLICK);
		evtHandler.addEvents("orglov","showOrginLov(this)",EVT_CLICK);
		evtHandler.addEvents("destlov","showDestinationLov(this)",EVT_CLICK);
		evtHandler.addEvents("allCheck","updateHeaderCheckBox(targetFormName, targetFormName.elements.allCheck, targetFormName.elements.check)", EVT_CLICK);
		evtHandler.addEvents("check","toggleTableHeaderCheckbox('check',targetFormName.elements.allCheck)", EVT_CLICK);
	}
	onScreenLoad();

}

function showDestinationLov(obj) {

	var index = obj.id.split("destlov")[1];

	displayLOV('showStation.do','N','Y','showStation.do',document.forms[1].destinationCode.value,'destination code','1','destinationCode','',index);


}
function showOrginLov(obj) {

	var index = obj.id.split("orglov")[1];

	displayLOV('showStation.do','N','Y','showStation.do',document.forms[1].originCode.value,'origin code','1','originCode','',index);


}


function onScreenLoad() {
// initial focus on page load.
if(targetFormName.elements.sectorOriginCode.disabled == false) {
   targetFormName.elements.sectorOriginCode.focus();
}

var screenFlag=targetFormName.elements.screenFlag.value;



if(screenFlag == "screenload"){
targetFormName.elements.btnSave.disabled=true;
disableLink(btnAdd);
disableLink(btnDelete);
}


}

function list(){

submitForm(targetFormName,'mailtracking.mra.defaults.maintaindotrate.list.do');


}

function onAddLink(){

	addTemplateRow('dotRateDetailsTableRow','dotratetablebody','operationFlag');

}

function onSave(){
var origins=new Array();
	var dests=new Array();
	var check=new Array();
	var rate=new Array();
	var gcm=new Array();
	var oprflag=new Array();
	origins=document.getElementsByName("originCode");
	dests=document.getElementsByName("destinationCode");
	check=document.getElementsByName("check");
	rate=document.getElementsByName("rateCode");
	gcm=document.getElementsByName("circleMiles");
	oprflag=document.getElementsByName("operationFlag");
	var odpair=new Array();
	for(var i=0;i<check.length;i++){
		if(oprflag[i].value != "D" && oprflag[i].value != "NOOP" ){
			if(origins[i].value!="" && dests[i].value!="" && gcm[i].value!=""){
				odpair[i]=(origins[i].value.trim()+dests[i].value.trim()+gcm[i].value.trim()+rate[i].value.trim()).toUpperCase();
				}
			}

		}
	var flag="false";

	for(var j=0;j<odpair.length;j++){


				for(var k=j+1;k<odpair.length;k++){

					if(odpair[j]==odpair[k]){
						flag="true";
						break;
					}

				}
	}

if(flag=="true"){
showDialog({msg :'Duplicate O-D pairs are not allowed',type:1,parentWindow:self});
}
else{
submitForm(targetFormName,'mailtracking.mra.defaults.maintaindotrate.save.do');
}
}
function onDeleteLink(){
	var chkBoxIds = document.getElementsByName('check');

	var isError = 1;
	for(var i = 0; i < chkBoxIds.length; i++){
		if(chkBoxIds[i].checked){
			isError = 0;
		}
	}
	if(isError == 1) {
		showDialog({msg :'Please select a row',type:1,parentWindow:self});
	}
	else{
		deleteTableRow('check','operationFlag');
	}
}

function onClear(){
submitForm(targetFormName,'mailtracking.mra.defaults.maintaindotrate.clear.do');

}

function onClose(){

submitForm(targetFormName,'mailtracking.mra.defaults.maintaindotrate.close.do');
}
//added by T-1927 for ICRD-18408
function resetFocus(){
	if(!event.shiftKey){
			if(targetFormName.elements.sectorOriginCode.disabled == false && targetFormName.elements.sectorOriginCode.readOnly== false){
			targetFormName.elements.sectorOriginCode.focus();
		}
	}
}

function calculateDOT(obj){

	var name=obj.name;

		if(obj.value=="") {
			obj.value=0.0;
		}

	var gcm=document.getElementsByName('circleMiles');

	var lhrat=document.getElementsByName('lineHaulRate');

	var rtrat=document.getElementsByName('terminalHandlingRate');

	var dotrate=document.getElementsByName('dotRate');

	var rowCount = obj.id.split(obj.name)[1];

	if(obj.value != null && obj.value.trim().length >0) {

	if(gcm[rowCount].value=="") {
			gcm[rowCount].value=0.0;
		}
	if(lhrat[rowCount].value=="") {
			lhrat[rowCount].value=0.0;
		}
	if(rtrat[rowCount].value=="") {
				rtrat[rowCount].value=0.0;
		}
	dotrate[rowCount].value=(parseFloat(gcm[rowCount].value)*parseFloat(lhrat[rowCount].value)+parseFloat(rtrat[rowCount].value)).toFixed(4);


	}

}