<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function onScreenLoad(){
	var frm = targetFormName;
	//initial focus on page load
	if(frm.exceptionCode.disabled == false) {
	   frm.exceptionCode.focus();
	}
}

function screenSpecificEventRegister(){
	var frm = targetFormName;
	onScreenLoad();
	with(frm){
		enableButton();
		evtHandler.addEvents("btnList","onList(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnSave","onSave(this.form)",EVT_CLICK);
	evtHandler.addIDEvents("subClassFilterLOV","displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.subClass.value,'Subclass','1','subClass','',0)", EVT_CLICK);
		//evtHandler.addEvents("btnManualPro","onManual(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnDispatchEnquiry","onDispatchEnquiry(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnRouting","onRouting(this.form)",EVT_CLICK);

		evtHandler.addEvents("btnPrint","onPrint(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnMaintainPF","onMaintainPF(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnProrate","onProrate(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnVoid","onVoid(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnClear","onClear(this.form)",EVT_CLICK);

		evtHandler.addEvents("btnClose","onClose(this.form)",EVT_CLICK);
		evtHandler.addEvents("modifyRoute","modifyRoute(this.form)",EVT_CLICK);
        evtHandler.addEvents("gpaCode","validateFields(this,-1,'GPA Code',8,true,true)",EVT_BLUR);		 	 
		evtHandler.addEvents("gpaCodeLov","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.gpaCode.value,'GPA Code','1','gpaCode','',0)",EVT_CLICK);


		evtHandler.addEvents("origin","validateFields(this, -1, 'origin', 1, true, true)",EVT_BLUR);
		evtHandler.addEvents("destination","validateFields(this, -1, 'destination', 1, true, true)",EVT_BLUR);

	}
 applySortOnTable("listProrationTable",new Array("None","String","String","String","String","Date","Number","String","Date","String","String","Number","String","Date","Date","String"));

}


function enableButton(){

	if(targetFormName.elements.operationFlag.value!='U'){
		
		disableField(targetFormName.elements.modifyRoute);
		//targetFormName.btnSave.disabled=true;
		disableField(targetFormName.elements.btnSave);
		disableField(targetFormName.elements.btnVoid);
		//targetFormName.btnRouting.disabled=true;
		disableField(targetFormName.elements.btnRouting);
		//targetFormName.btnManualPro.disabled=true;
		//targetFormName.btnPrint.disabled=true;
		disableField(targetFormName.elements.btnPrint);
		//targetFormName.btnDispatchEnquiry.disabled=true;
		disableField(targetFormName.elements.btnDispatchEnquiry);
		//targetFormName.btnMaintainPF.disabled=true;
		disableField(targetFormName.elements.btnMaintainPF);
		//targetFormName.btnList.disabled=false;
		enableField(targetFormName.elements.btnList);
		}
}






function onList(frm){
	//targetFormName.btnList.disabled=true;
	disableField(targetFormName.btnList);
	frm.elements.displayPage.value = "1";
	frm.elements.lastPageNum.value = "0";


	submitForm(targetFormName,'mra.defaults.proration.listprorationexceptionslist.do?paginationMode=LIST');
}
function submitPage1(lastPg,displayPg){
 var frm=targetFormName;
	frm.elements.lastPageNum.value= lastPg;
	frm.elements.displayPage.value = displayPg;
	submitForm(frm, 'mra.defaults.proration.listprorationexceptionslist.do?paginationMode=LINK');
}
/*function onDispatchEnquiry(frm){


	var checkedRow = "";
	var selectedRowId=null;
	var isValidRow = false;
	if(frm.rowId!=null){
	if(validateSelectedCheckBoxes(frm,'rowId',1,1)){
			if(frm.rowId.length>0){
			for(var id=0;id<frm.rowId.length;id++){
				if(frm.rowId[id].checked == true){
					selectedRowId = (frm.rowId[id].value);
					break;
				}
			}
			}else{
				selectedRowId = (frm.rowId.value);
			}

			if(selectedRowId!=null){
					checkedRow = selectedRowId;
					//Bug ID: ICRD-5305 - Jeena - For Navigating to previous screen when Close button is clicked in current screen
					submitForm(frm,"mra.defaults.proration.listprorationexceptionsnavigate.do?rowId="+checkedRow+"&clickedButton="+"dispatchenquiry"+"&closeFlag="+"fromdispatchEnquiry");


		}
		}
	}
}
*/

function onClose(frm){
	submitForm(targetFormName,'mra.defaults.proration.listprorationexceptionsclose.do');
}

function onMaintainPF(frm){
	//Bug ID: ICRD-5305 - Jeena - For Navigating to previous screen when Close button is clicked in current screen
	//var fromScreen = 'mralistexception';
	var fromScreen ='mra.defaults.proration.listprorationexceptionslist';

	var checkedRow = "";
	var selectedRowId=null;
	var isValidRow = false;
	if(frm.elements.rowId!=null){
	if(validateSelectedCheckBoxes(frm,'rowId',1,1)){
		if(frm.elements.rowId.length>0){
		for(var id=0;id<frm.elements.rowId.length;id++){
			if(frm.elements.rowId[id].checked == true){
				selectedRowId = (frm.elements.rowId[id].value);
				break;
			}
		}
		}else{
			selectedRowId = (frm.elements.rowId.value);
		}

		if(selectedRowId!=null){
					checkedRow = selectedRowId;

				//Bug ID: ICRD-5305 - Jeena - For Navigating to previous screen when Close button is clicked in current screen
				submitForm(frm,'cra.proration.maintainproratefacotrs.screenload.do?parentScreenId='+fromScreen);


		}
		}
	}
}

function onSave(frm){
if(isFormModified(frm)){
if(frm.elements.rowId==null){
//showDialog('Please select the Row', 1, self);
showDialog({msg:"<bean:message bundle="mralistprorationexceptions" key="mra.proration.listexceptions.msg.err.plsselecttherow" />",type:1,parentWindow:self});
}
var checkedRow = "";
	var selectedRowId=null;
	var isValidRow = false;
	if(frm.elements.rowId!=null){
	if(validateSelectedCheckBoxes(frm,'rowId',1,1)){
		if(frm.elements.rowId.length>0){
		for(var i=0;i<frm.elements.rowId.length;i++){
			if(frm.elements.rowId[i].checked == true){
				selectedRowId = (frm.elements.rowId[i].value);
				break;
			}
		}
		}else{
			selectedRowId = (frm.elements.rowId.value);
		}

		if(selectedRowId!=null){
					checkedRow = selectedRowId;
					if(frm.elements.assignedTime[checkedRow].value!="" && frm.elements.assignedUser[checkedRow].value == ""){
					//showDialog('Assigned Date cannot be saved without Asignee', 1, self);
					showDialog({msg:"<bean:message bundle="mralistprorationexceptions" key="mra.proration.listexceptions.msg.err.assigneddatecannotbesaved" />",type:1,parentWindow:self});
					return;
					}
					 if(frm.elements.assignedTime[checkedRow].value=="" && frm.elements.assignedUser[checkedRow].value != ""){
					//showDialog('Please Enter Assigned Date', 1, self);
					showDialog({msg:"<bean:message bundle="mralistprorationexceptions" key="mra.proration.listexceptions.msg.err.enterassigneddate" />",type:1,parentWindow:self});
					return;
					}
					submitForm(frm,"mra.defaults.proration.listprorationexceptionssave.do?rowId="+checkedRow);


		}

		}
	}}
else{
			//showDialog('No Data Modified  For Save', 1, self);

			showDialog({msg:"<bean:message bundle="mralistprorationexceptions" key="mra.proration.listexceptions.msg.err.nodatamodifiedforsave" />",type:1,parentWindow:self});
			}
}
function onPrint() {

	generateReport(targetFormName,'/mra.defaults.proration.listprorationexceptionsprint.do');


}

function onRouting(frm){
	var checkedRow = "";
	var selectedRowId=null;
	var isValidRow = false;
	var expStatus = document.getElementsByName("exceptionStatus");
	var validExpStatus="";
	if(frm.elements.rowId!=null){

		if(validateSelectedCheckBoxes(frm,'rowId',1,'1')){
			if(frm.elements.rowId.length>0){
				for(var id=0;id<frm.elements.rowId.length;id++){
					if(frm.elements.rowId[id].checked == true){
						selectedRowId = (frm.elements.rowId[id].value);
						if(expStatus[id].value == 'Void'){
							validExpStatus=expStatus[id].value;
						}
					}
				}
			}else{
				selectedRowId = (frm.elements.rowId.value);
			}
			if(selectedRowId!=null){
				if(validExpStatus=='Void') {


					showDialog({msg:"<bean:message bundle="mralistprorationexceptions" key="mra.proration.listexceptions.msg.err.voidmailbags" />",type:1,parentWindow:self});



				return;
				}
				}
				checkedRow = selectedRowId;
				submitForm(frm,"mra.defaults.proration.listprorationexceptionsnavigate.do?rowId="+checkedRow+"&clickedButton="+"routing");
			}
		}
	}


	/* function onRouting(frm){
	var checkedRow = "";
	var selectedRowId=null;
	var isValidRow = false;
	if(frm.elements.rowId!=null){
	if(validateSelectedCheckBoxes(frm,'rowId',1,1)){
		if(frm.elements.rowId.length>0){
		for(var id=0;id<frm.elements.rowId.length;id++){
			if(frm.elements.rowId[id].checked == true){
				selectedRowId = (frm.elements.rowId[id].value);
				break;
			}
		}
		}else{
			selectedRowId = (frm.elements.rowId.value);
		}

		if(selectedRowId!=null){
					checkedRow = selectedRowId;
				submitForm(frm,"mra.defaults.proration.listprorationexceptionsnavigate.do?rowId="+checkedRow+"&clickedButton="+"routing");


		}
		}
	}
}



/*
ICRD-21098_A-5223 : Duplicate function 'submitPage1' found, hence commenting
function submitPage1(lastPg,displayPg){
	targetFormName.elements.lastPageNum.value=lastPg;
	targetFormName.elements.displayPageNum.value=displayPg;
	submitForm(targetFormName,'mra.defaults.proration.listprorationexceptionslist.do');

}
*/
/*
 * Method to goto Manual Proration screen
 * and views details of selected row
 * for a record with status "New"
 */
function onManual(frm){
	var checkedRow = "";
	var selectedRowId=null;
	var isValidRow = false;
	if(frm.elements.rowId!=null){
	if(validateSelectedCheckBoxes(frm,'rowId',1,1)){
		if(frm.elements.rowId.length>0){
		for(var id=0;id<frm.elements.rowId.length;id++){
			if(frm.elements.rowId[id].checked == true){
				selectedRowId = (frm.elements.rowId[id].value);
				break;
			}
		}
		}else{
			selectedRowId = (frm.elements.rowId.value);
		}

		if(selectedRowId!=null){
					checkedRow = selectedRowId;

				submitForm(frm,"mra.defaults.proration.listprorationexceptionsnavigate.do?rowId="+checkedRow+"&clickedButton="+"manualproration");
		}
		}
	}
}


/*
 * Method to clear screen
 */
function onClear(frm){


	frm.elements.displayPage.value = "1";
	frm.elements.lastPageNum.value = "0";
	submitForm(targetFormName,'mra.defaults.proration.clearlistexceptions.do');
}

/*
*
*Method To Invoke the Proration
Changed As part of ICRD-106032
*/
function onProrate(frm)
{
	var checkedRow = "";
		var selectedRowId="";
		var isValidRow = false;
		var expStatus = document.getElementsByName("exceptionStatus");
	    var validExpStatus="";
		if(frm.elements.rowId!=null){
		if(validateSelectedCheckBoxes(frm,'rowId',30,1)){
			if(frm.elements.rowId.length>0){
			for(var id=0;id<frm.elements.rowId.length;id++){
				if(frm.elements.rowId[id].checked == true){
					selectedRowId = selectedRowId+"-"+(frm.elements.rowId[id].value);
                       if(expStatus[id].value == 'Void'){
							validExpStatus=expStatus[id].value;
						}
				}
			}
			}else{
				selectedRowId = selectedRowId+"-"+(frm.elements.rowId.value);
			}

			if(selectedRowId!=null){
				checkedRow = selectedRowId.substring(1,frm.elements.rowId.length);
				if(checkedRow != null && checkedRow.length > 0){
					if(expStatus != null && expStatus.length > 0){
						if(expStatus[checkedRow].value == 'Void'){
							validExpStatus=expStatus[checkedRow].value;
						}
					}
				}
				if(validExpStatus=='Void') {

					showDialog({msg:"<bean:message bundle="mralistprorationexceptions" key="mra.proration.listexceptions.msg.err.voidmailbags" />",type:1,parentWindow:self});

					return;
				}

					submitForm(frm,"mra.defaults.proration.listprorationexceptionsprorate.do?selectedRows="+checkedRow);
			}}
	}

}

/**
 * Function for Assignee LOV for asignee fields
 */
function showAsigneeLOV(){
//	var mode = getScreenId(targetFormName.elements.value) ;
	displayLOV('showUserLOV.do','N','Y','showUserLOV.do',targetFormName.asignee.value,'UserLOV','1','asignee','',0,'','');
}
function showAssigneeLovInTable(rowId){
//	var mode = getScreenId(targetFormName) ;
	if(rowId > 0){
		displayLOV('showUserLOV.do','N','Y','showUserLOV.do',targetFormName.assignedUser[rowId].value,'Assignee','1','assignedUser','',rowId,'','');
	}else{
		displayLOV('showUserLOV.do','N','Y','showUserLOV.do',targetFormName.assignedUser.value,'Assignee','1','assignedUser','',0,'','');
	}
}

function getScreenId(frm){
	var actCode = targetFormName.action;
	var actCodeArr = actCode.split("/");
	var len = actCodeArr.length;
	var lastVal = actCodeArr[len-1];
	var scrid = getScreenIdFromActionCode(lastVal);
	var length = scrid.split("'");
	var mode = length[0];
	return mode ;
}

//Added by A-7929 for ICRD - 224586 starts
function callbackListProrationExceptions (collapse,collapseFilterOrginalHeight,mainContainerHeight){
               if(!collapse){
                              collapseFilterOrginalHeight=collapseFilterOrginalHeight*(-1);
               }
               //IC.util.widget.updateTableContainerHeight(jquery("div1"),+collapseFilterOrginalHeight);

}
//Added by A-7929 for ICRD - 224586 ends


function modifyRoute(frm){
	
	if(parseInt(document.getElementById("totalRecordCount").value) > parseInt(document.getElementById("modifyRouteDataLimit").value)){
		showDialog({msg:"Maximum number of records for bulk route update exceeded. Please refine the filter criteria",type:1,parentWindow:self});
		return ;
	}
		var popupTitle = "ROUTING DETAILS";
		var urlString="mail.mra.defaults.ux.modifyroute.defaultscreenload.do?isPopup=true&fromScreen=MRA062"
		var clientHeight = document.body.clientHeight;
	    var Clientwidth = document.body.clientWidth;
		var _tableDivHeight=(clientHeight*70)/100;
        var _tableDivWidth=(Clientwidth*75)/100;
		var closeButtonIds = [];
		var optionsArray = {	
			actionUrl : urlString,
			dialogWidth:_tableDivWidth,
			dialogHeight:_tableDivHeight,
			closeButtonIds : closeButtonIds,
			popupTitle: popupTitle	
       }
	   
	   popupUtils.openPopUp(optionsArray);

}

function getFilterDetails(){
	
	var frm = targetFormName;
	
	var  carrierCode=frm.elements.carrierCode.value;
	var  flightNumber=frm.elements.flightNumber.value;
	var  flightDate=frm.elements.flightDate.value;
	var  origin=frm.elements.origin.value;
	var  destination=frm.elements.destination.value;
	var  mailbagID=frm.elements.mailbagID.value;
	var  originExchangeOffice=frm.elements.originOfficeOfExchange.value;
	var  destinationExchangeOffice=frm.elements.destinationOfficeOfExchange.value;
	var  category=frm.elements.mailCategory.value;
	var  subClass=frm.elements.subClass.value;
	var  year=frm.elements.year.value;
	var  dsn=frm.elements.dispatchNo.value;
	var  rsn=frm.elements.receptacleSerialNumber.value;
	var  hni=frm.elements.highestNumberIndicator.value;
	var  ri=frm.elements.registeredIndicator.value;
	var  assignedStatus=frm.elements.assignedStatus.value;
	var  assignee=frm.elements.asignee.value;
	var  fromDate=frm.elements.fromDate.value;
	var  toDate=frm.elements.toDate.value;
	var exceptionCode=frm.elements.exceptionCode.value;
	var selectedMalSeqNum="";
	for(var id=0;id<frm.elements.rowId.length;id++){
				if(frm.elements.rowId[id].checked == true){
					selectedMalSeqNum = selectedMalSeqNum+","+(frm.elements.malseqnums[id].value);
				}
			}
	
	var filterDetails= {
		carrierCode:carrierCode,
		flightNumber:flightNumber,
		flightDate:flightDate,
		origin:origin,
		destination:destination,
		mailbagID:mailbagID,
		originExchangeOffice:originExchangeOffice,
		destinationExchangeOffice:destinationExchangeOffice,
		category:category,
		subClass:subClass,
		year:year,
		dsn:dsn,
		rsn:rsn,
		hni:hni,
		ri:ri,
		assignedStatus:assignedStatus,
		assignee:assignee,
		fromDate:fromDate,
		toDate:toDate,
		exceptionCode:exceptionCode,
		fromScreen:'LSTPROEXP',
		selectedMalSeqNum: selectedMalSeqNum
	};
	
	return filterDetails;
	
	
}










//Added by A-7929 for ICRD - 224586 ends
function confirmMessage() {
	submitForm(targetFormName,'mra.defaults.proration.listprorationexceptionsvoid.do');
}
function nonconfirmMessage() {
	submitForm(targetFormName,'mra.defaults.proration.listprorationexceptionslist.do?paginationMode=LIST');
}
function screenConfirmDialog(targetFormName, dialogId) {
	if(dialogId == 'id_1'){
		submitForm(targetFormName,'mra.defaults.proration.listprorationexceptionsvoid.do');
		}
	else{

	}
}
function screenNonConfirmDialog(targetFormName, dialogId) {
	if(targetFormName.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){

		}
	}
}
function onVoid(frm){
	var checkedRow = "";
	var selectedRowId="";
	var isValidRow = false;
	var expStatus = document.getElementsByName("exceptionStatus");
	var validExpStatus="";
	if(frm.elements.rowId!=null){
		if(validateSelectedCheckBoxes(frm,'rowId','','1')){
			if(frm.elements.rowId.length>0){
				for(var id=0;id<frm.elements.rowId.length;id++){
					if(frm.elements.rowId[id].checked == true){
						selectedRowId = selectedRowId+"-"+(frm.elements.rowId[id].value);
					}
				}
			}else{
				selectedRowId = selectedRowId+"-"+(frm.elements.rowId.value);
			}
			if(selectedRowId!=null){
				checkedRow = selectedRowId.substring(1,frm.elements.rowId.length);
				if(checkedRow != null && checkedRow.length > 0){
					if(expStatus != null && expStatus.length > 0){
						var split = checkedRow.split("-");
						for(var id = 0; id<split.length; id++){
							if(expStatus[split[id]].value == 'Void'){
								validExpStatus=expStatus[id].value;
							}
						}
					}
				}
				if(validExpStatus=='Void') {
					showDialog({msg:"Mailbag/(s) already voided from MRA",type:1,parentWindow:self});
					return;
				}

				submitForm(frm,"mra.defaults.proration.listprorationexceptionsvoid.do?selectedRows="+checkedRow);
			}
		}
	}
}