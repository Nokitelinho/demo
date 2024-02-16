<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{

	var frm = targetFormName;
	if((frm.elements.uldTypeCode.disabled == false) && (frm.elements.uldTypeCode.readOnly == false)){
			frm.elements.uldTypeCode.focus();
	}
	onLoad();
	with(frm)
	{
		evtHandler.addEvents("btClose","closeFun(this.frm)",EVT_CLICK);
		evtHandler.addEvents("btClose","resetFocus()",EVT_BLUR);
		evtHandler.addEvents("btList","listEvent()",EVT_CLICK);
		evtHandler.addEvents("btClear","clearEvent()",EVT_CLICK);
		evtHandler.addEvents("btPrint","printScreen(this.form)",EVT_CLICK);
		evtHandler.addEvents("uldlov","displayLOV('showUld.do','N','Y','showUld.do',targetFormName.elements.uldTypeCode.value,'uldTypeCode','1','uldTypeCode','',0)",EVT_CLICK);
		evtHandler.addEvents("facilityType","validateFacityType()",EVT_BLUR);
		evtHandler.addEvents("facilitycodelov","showFacilityCode()",EVT_CLICK);
		evtHandler.addEvents("location","selectFacilityType()",EVT_CLICK);
	}
}

function selectFacilityType(){
	if(targetFormName.elements.facilityType.value=="" ){
		//showDialog("Facility Type cannot be blank", 1, self, targetFormName, 'id_78');
		  showDialog({msg :'Facility Type cannot be blank',
				    type:1,
				    parentWindow:self,                                       
				    parentForm:targetFormName,
				    dialogId:'id_78'
                });
		targetFormName.elements.facilityType.focus();
		return;
	}
}
//A-6841
function validateFacityType(){
	if(targetFormName.elements.facilityType.value=="" ){
		targetFormName.elements.location.value="";
		targetFormName.elements.facilityType.focus();
	}
}
	
function resetFocus(){
	 if(!event.shiftKey){ 
		if((targetFormName.elements.uldTypeCode.disabled == false) && (targetFormName.elements.uldTypeCode.readOnly == false)){
			targetFormName.elements.uldTypeCode.focus();
		}								
	}
}

function closeFun(frm)
{
	location.href = appPath+"/home.jsp";
}
function listEvent()
{
	targetFormName.elements.lastPageNum.value=0;
	targetFormName.elements.displayPage.value=1;
	if(targetFormName.elements.airport.value == null || targetFormName.elements.airport.value ==""){
			//showDialog("Airport is Mandatory", 1, self, targetFormName, 'id_1');
			  showDialog({msg :'Airport is Mandatory',
				    type:1,
				    parentWindow:self,                                       
				    parentForm:targetFormName,
				    dialogId:'id_1'
                });
			targetFormName.elements.airport.focus();
			return;
	}
	submitForm(targetFormName, 'uld.defaults.scmvalidationlist.do');

}
function clearEvent()
{
	submitForm(targetFormName, 'uld.defaults.clearscmvalidation.do');

}

function submitList(strLastPageNum,strDisplayPage){
	targetFormName.elements.lastPageNum.value= strLastPageNum;
	targetFormName.elements.displayPage.value = strDisplayPage;
	submitForm(targetFormName, 'uld.defaults.scmvalidationlist.do');
}

function showFacilityCode(){

	if(targetFormName.elements.facilityType.value=="" ){
		//showDialog("Facility Type cannot be blank", 1, self, targetFormName, 'id_77');
		  showDialog({msg :'Facility Type cannot be blank',
				    type:1,
				    parentWindow:self,                                       
				    parentForm:targetFormName,
				    dialogId:'id_77'
                });
		return;
	}
	if(targetFormName.elements.airport.value=="" ){
		//showDialog("Facility Type cannot be blank", 1, self, targetFormName, 'id_78');
		  showDialog({msg :'Airport cannot be blank',
				    type:1,
				    parentWindow:self,                                       
				    parentForm:targetFormName,
				    dialogId:'id_79'
                });
		targetFormName.elements.facilityType.focus();
		return;
	}
	var textfiledDesc="";
	var currentStationForLov=targetFormName.elements.airport.value;
	var facilityTypeForLov=targetFormName.elements.facilityType.value;
	var strAction="uld.defaults.lov.screenloadfacilitycodelov.do";
	var StrUrl=strAction+"?textfiledObj=location&formNumber=1&textfiledDesc="+textfiledDesc+'&facilityTypeForLov='+facilityTypeForLov+'&currentStationForLov='+currentStationForLov;
	var myWindow = openPopUpWithHeight(StrUrl, "500");
}

function onLoad(){
	if(targetFormName.elements.statusFlag.value == "screenload_success"){
		targetFormName.elements.btPrint.disabled=true;
	}
}

function printScreen(frm){
 	generateReport(frm,"/uld.defaults.printscmvalidationlist.do");
}