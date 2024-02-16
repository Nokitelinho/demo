<%@ include file="/jsp/includes/js_contenttype.jsp" %>


function screenSpecificEventRegister(){


	with(targetFormName){

		evtHandler.addEvents("btnList","doList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","doClear()",EVT_CLICK);
		evtHandler.addEvents("btnViewProration","doViewProration()",EVT_CLICK);
		evtHandler.addEvents("btnActions","doActions()",EVT_CLICK);
		evtHandler.addEvents("btnClose","doClose()",EVT_CLICK);
		evtHandler.addEvents("despatchEnqTyp","doTableDetails()",EVT_CHANGE);
		evtHandler.addEvents("despatchNum","mailDSN()",EVT_BLUR);
        evtHandler.addIDEvents("dsnlov","displayLOV('mailtracking.mra.defaults.dsnlov.screenload.do','N','Y','mailtracking.mra.defaults.dsnlov.screenload.do',targetFormName.elements.despatchNum.value,'DSN No','1','despatchNum',targetFormName.elements.despatchNum.value,0)",EVT_CLICK);
		

	}
	applySortOnTable("despatchequiry",new Array("Number","String","String","None","String","Date","None","None","Number","Number","None","None","String"));

	screenload();

}
/**
*Added for screen resolution
*/
function screenload(){
	// initial focus on page load
	if(targetFormName.elements.despatchNum.disabled == false) {
	   targetFormName.elements.despatchNum.focus();
	}

	if(targetFormName.elements.listed.value=="Y" && targetFormName.elements.lovClicked.value=="Y"){
		targetFormName.elements.despatchEnqTyp.value="G";
		targetFormName.elements.lovClicked.value="";
	}
	if(targetFormName.elements.despatchEnqTyp.value!="G"){
		targetFormName.elements.remarks.value=" ";
	}
	if(targetFormName.elements.listed.value=="Y"){
		disableField(targetFormName.elements.despatchNum);
		disableField(targetFormName.elements.dsnFilterDate);
		/*targetFormName.despatchEnqTyp.enabled=true;
		targetFormName.btnViewProration.enabled=true;
		targetFormName.btnActions.enabled=true;*/
		enableField(targetFormName.elements.despatchEnqTyp);
		enableField(targetFormName.elements.btnViewProration);
		enableField(targetFormName.elements.btnActions);
	}else{
		disableField(targetFormName.elements.btnViewProration);
		disableField(targetFormName.elements.btnActions);		
	}
	if(targetFormName.elements.showDsnPopUp.value=="TRUE"){
		targetFormName.elements.showDsnPopUp.value="FALSE"
		openPopUp("mailtracking.mra.defaults.despatchenquiry.popup.do",725,450);
    }
    if(targetFormName.elements.listed.value=="NO"){

		disableField(targetFormName.elements.despatchEnqTyp);
	}
}
/**
*Function for list
*/
function doList(){

	/*if(targetFormName.dsnFilterDate.value!=""){

			submitForm(targetFormName,'mailtracking.mra.defaults.despatchenquiry.list.do');
	}
			else{
	displayLOVCodeNameAndDesc
			('showDSNSelectLov.do','N','Y','showDSNSelectLov.do',targetFormName.despatchNum.value,'despatchNum','1',
			'despatchNum','dsnFilterDate','dsnFilterDate',0);
	}*/

	var dsn=targetFormName.elements.despatchNum.value;
	var dat=targetFormName.elements.dsnFilterDate.value;
	targetFormName.elements.lovClicked.value="Y";
	var frmPg="despatchenq";
	submitForm(targetFormName,'mailtracking.mra.defaults.despatchenquiry.listdsnpopup.do');
	//openPopUp("mailtracking.mra.defaults.despatchenquiry.popup.do?code="+dsn+"&dsnFilterDate="+dat+"&fromPage="+frmPg,725,450);


}
/**
*Function for Clear
*/
function doClear(){

	submitForm(targetFormName,'mailtracking.mra.defaults.despatchenquiry.clear.do');
}

/**
*Function for ViewProration
*/
function doViewProration(){
	submitForm(targetFormName,'mailtracking.mra.defaults.despatchenquiry.viewprorate.do');
}
/**
*Function for Actions
*/
function doActions(){
	submitForm(targetFormName,'shared.audit.auditscreenload.do?parentScreen=despatchenquiry');
}

/**
*Function for Close
*/
function doClose(){
	submitForm(targetFormName,'mailtracking.mra.defaults.despatchenquiry.close.do');
}

/**
*Function for doTableDetails
*/
function doTableDetails(){
	if(targetFormName.elements.despatchEnqTyp.value=="G"){
		submitForm(targetFormName,'mailtracking.mra.defaults.despatchenquiry.gpablgdetails.do');
	}
	if(targetFormName.elements.despatchEnqTyp.value=="I"){
		submitForm(targetFormName,'mailtracking.mra.defaults.despatchenquiry.interlinebilling.do');
	}
	if(targetFormName.elements.despatchEnqTyp.value=="F"){
		submitForm(targetFormName,'mailtracking.mra.defaults.despatchenquiry.flowndetails.do');
	}
	if(targetFormName.elements.despatchEnqTyp.value=="A"){
		submitForm(targetFormName,'mailtracking.mra.defaults.despatchenquiry.accountingdetails.do');
	}
	if(targetFormName.elements.despatchEnqTyp.value=="U"){
    submitForm(targetFormName,'mailtracking.mra.defaults.despatchenquiry.uspsdetails.do');
	}
	if(targetFormName.elements.despatchEnqTyp.value=="O"){
	submitForm(targetFormName,'mailtracking.mra.defaults.despatchenquiry.outstandingbalances.do');
	}
}

/**
*Function for doLov
*/
function doLov(){

	targetFormName.elements.lovClicked.value="clicked";
	displayLOVCodeNameAndDesc
		('showDSNSelectLov.do','N','Y','showDSNSelectLov.do',targetFormName.elements.despatchNum.value,'despatchNum','1',
		'despatchNum','dsnFilterDate','dsnFilterDate',0);


}

function mailDSN(){

 frm=targetFormName;
 var mailDSNArr =document.getElementsByName("despatchNum");
 var mailDSN =document.getElementsByName("despatchNum");
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

/********************** Added for the pagination of Accounting Details tab***********************/

function submitPage(lastPg,displayPg){

	var frm=targetFormName;
//	frm.checkStatus.value="";

	frm.elements.lastPageNumber.value=lastPg;
	frm.elements.displayPage.value=displayPg;
	frm.action="mailtracking.mra.defaults.despatchenquiry.accountingdetails.do";
	frm.submit();
}


function clearTable(){
	document.getElementById(_currDivId).innerHTML="";
}

function recreateTableForDetails(strAction,divId){
 	//alert("strAction"+strAction+"divId"+divId+"targetFormName"+targetFormName);
	var _extraFn ="updateTableForCode";
	if(arguments[2]!=null){
		_extraFn=arguments[2];
	}
	//_currDivId=divId;
	asyncSubmit(targetFormName,strAction,_extraFn,null,null,divId);
}

function checkScreenRefresh_new(_tableInfo){
		//alert(_tableInfo);
		//document.getElementById("tmpSpan").innerHTML=_tableInfo;
		//alert(document.getElementById("tmpSpan").getElementsByTagName("form")[0]);
		_innerFrm=_tableInfo.document.getElementsByTagName("form")[0];
		_lastPageNumber=_innerFrm.lastPageNumber.value;
		_displayPage=_innerFrm.displayPage.value;
		_absIndex=_innerFrm.absIndex.value;
		_pageNum=_innerFrm.pageNum.value;

		targetFormName.elements.lastPageNumber.value=_lastPageNumber;
		targetFormName.elements.displayPage.value=_displayPage;
		targetFormName.elements.absIndex.value=_absIndex;
		targetFormName.elements.pageNum.value=_pageNum;


		updateTableForCode(_tableInfo);
}

function updateTableForCode(_tableInfo){

	_str=getActualData(_tableInfo);
	document.getElementById(_tableInfo.currDivId).innerHTML=_str;

}

function getActualData(_tableInfo){
	//document.getElementById("tmpSpan").innerHTML=_tableInfo;
	_frm=_tableInfo.document.getElementsByTagName("table")[0];
	return _frm.outerHTML;
}

function cleanupTmpTable(){
	document.getElementById("tmpSpan").innerHTML="";
}

