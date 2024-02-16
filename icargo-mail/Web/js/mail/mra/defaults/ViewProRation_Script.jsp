<%@ include file="/jsp/includes/js_contenttype.jsp" %>


function screenSpecificEventRegister()
{
	with(targetFormName){

	evtHandler.addEvents("btSurcharge","surchargeProration()",EVT_CLICK);
    evtHandler.addEvents("btList","ListProRation()",EVT_CLICK);
    evtHandler.addEvents("despatchlov","displayDsnLov()",EVT_CLICK);
    evtHandler.addEvents("btnClear","clearscreen()",EVT_CLICK);
    evtHandler.addEvents("btClose","closeScreen()",EVT_CLICK);
	evtHandler.addEvents("btClose","resetFocus()",EVT_BLUR);//added by T-1927 for ICRD-18408
    //evtHandler.addEvents("btManualProration","doManualProration()",EVT_CLICK);
	
    evtHandler.addIDEvents("dsnlov","displayLOV('mailtracking.mra.defaults.dsnlov.screenload.do','N','Y','mailtracking.mra.defaults.dsnlov.screenload.do',targetFormName.dispatch.value,'DSN No','0','dispatch',targetFormName.dispatch.value,0)",EVT_CLICK);
	evtHandler.addEvents("btAWMProrate","awmProration()",EVT_CLICK);//Added by A-7371 for ICRD-234334
	}

	screenload();



}


function screenload(){

if('E'==targetFormName.elements.formStatusFlag.value){
enableField(targetFormName.btSurcharge);
}else{
disableField(targetFormName.elements.btSurcharge);
disableField(targetFormName.elements.btAWMProrate);//Added by A-7371 for ICRD-234334
}

	if(!targetFormName.elements.dispatch.disabled){
	 	targetFormName.elements.dispatch.focus();
	}
	//var clientHeight = document.body.clientHeight;
	//document.getElementById('contentdiv').style.height = ((clientHeight*85)/100)+'px';

    if(targetFormName.elements.showDsnPopUp.value=="TRUE"){
    	targetFormName.elements.showDsnPopUp.value="FALSE";
    	openPopUp("mailtracking.mra.defaults.despatchenquiry.popup.do",725,450);
    }
}

function surchargeProration() {
	targetFormName.elements.fromAction.value="viewproration";
	targetFormName.elements.sector.value="1";
	var surchg = document.getElementsByName("surCharge");
	//alert('test'+surchg[0].value);
	if(surchg[0].value==0.0){
	//showDialog('Surcharge does not exist for the selected mail',1,self);
	showDialog({msg:'Surcharge does not exist for the selected mail',type:1,parentWindow:self});
	return;
	}else{
	openPopUp("mailtracking.mra.defaults.viewproration.surchargedetails.do?fromAction=viewproration&sector=1",460,325);
	}
}

function closeScreen(){
submitForm(targetFormName,'mailtracking.mra.defaults.closeviewproration.do');

}
//added by T-1927 for ICRD-18408
function resetFocus(){
	if(!event.shiftKey){
		if(targetFormName.elements.dispatch.disabled == false&&targetFormName.elements.dispatch.readOnly == false){
			targetFormName.elements.dispatch.focus();
		}
	}
}
function doManualProration(){
submitForm(targetFormName,'mailtracking.mra.defaults.tomanualProration.do');
}

function clearscreen(){
submitForm(targetFormName,'mailtracking.mra.defaults.clearviewproration.do');
}

function ListProRation(){	
	submitForm(targetFormName,'mailtracking.mra.defaults.viewproration.listdsndetail.do');
 //openPopUp("mailtracking.mra.defaults.despatchenquiry.popup.do?code="+targetFormName.dispatch.value+"&fromPage=ViewProration",width=725,height=450);
}


function displayDsnLov(){
	
	
	displayLOVCodeNameAndDesc
				('showDSNSelectLov.do','N','Y','showDSNSelectLov.do',targetFormName.elements.dispatch.value,'dispatch','1',
		'dispatch','dsnDate','dsnDate',0);
}
function awmProration() {//Added by A-7371 for ICRD-234334

	targetFormName.elements.fromAction.value="viewawmproration";
	openPopUp("mailtracking.mra.defaults.viewproration.awmproratedetails.do?fromAction=viewawmproration&sector=1",900,400);
}