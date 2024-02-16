<%@ include file="/jsp/includes/js_contenttype.jsp" %>


function screenSpecificEventRegister(){
   		var frm = targetFormName;

   		if(frm.elements.uldNo.disabled==false){
				frm.elements.uldNo.focus();
		}
   		with(frm){
   		onLoad();
   		evtHandler.addEvents("btList","list(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClear","clearScreen(this.form)",EVT_CLICK);
		//evtHandler.addEvents("btnView","view(this.form)",EVT_CLICK);
		evtHandler.addIDEvents("dmglov","lovScreen()",EVT_CLICK);
		evtHandler.addEvents("btPrint","printScreen(this.form)",EVT_CLICK);
		evtHandler.addEvents("btViewDamage","viewDamageScreen(this.form)",EVT_CLICK);
		evtHandler.addEvents("btUldHistory","uldHistoryScreen(this.form)",EVT_CLICK);
		evtHandler.addEvents("btUldDetails","uldDetailsScreen(this.form)",EVT_CLICK);
		evtHandler.addEvents("btDelete","deleteScreen(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClose","closeScreen(this.form)",EVT_CLICK);
		evtHandler.addEvents("damageRefNo","validateInt(this,'Damage Ref No','Invalid Format',true)",EVT_BLUR);
		evtHandler.addIDEvents("airportlov","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.currentStn.value,'Airport Code','1','currentStn','',0)",EVT_CLICK);
		evtHandler.addIDEvents("airportlovrep","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.reportedStn.value,'Airport Code','1','reportedStn','',0)",EVT_CLICK);
		evtHandler.addIDEvents("uldlov","displayLOV('showUld.do','N','Y','showUld.do',targetFormName.elements.uldTypeCode.value,'uldTypeCode','1','uldTypeCode','',0)",EVT_CLICK);
		evtHandler.addIDEvents("facilitycodelov","showFacilityCode()",EVT_CLICK);
		evtHandler.addIDEvents("partycodelov","showPartyCode()",EVT_CLICK);
   		}


   	 applySortOnTable("damagereporttable",new Array("None","String","Number","String","String","String","String","String","String","String","Date","String","String","String","None","String"));
	 IC.util.widget.recalculateTableContainerHeightOnCollapse();

   	}

function lovScreen(){

var uldNo = targetFormName.elements.uldNo.value;
var pageURL ="listlov";

openPopUpWithHeight("uld.defaults.lov.listscreen.do?uldNo="+uldNo+"&pageURL="+pageURL,"500");
}



function onLoad(){

	if(targetFormName.elements.listStatus.value!="N"){
		disableField(targetFormName.elements.btPrint);
		disableField(targetFormName.elements.btViewDamage);
		disableField(targetFormName.elements.btUldDetails);
		disableField(targetFormName.elements.btDelete);
	}
	if(targetFormName.elements.damageDisableStatus.value=="airline"){
	}
	if(targetFormName.elements.damageDisableStatus.value=="GHA"){
		targetFormName.elements.currentStn.disabled = true;
		targetFormName.airportlov.disabled = true;
	}

}

function list(frm){

	if(frm.elements.repairedDateFrom.value != "" &&
		!chkdate(frm.elements.repairedDateFrom)){

		showDialog({msg:"Invalid From Date",type: 1, parentWindow:self,parentForm: frm, dialogId:'id_1'});
		frm.elements.repairedDateFrom.focus();
		return;
	}
	if(frm.elements.repairedDateTo.value != "" &&
		!chkdate(frm.elements.repairedDateTo)){

		showDialog({msg:"Invalid To Date", type:1, parentWindow:self,parentForm: frm, dialogId:'id_1'});
		frm.elements.repairedDateTo.focus();
		return;
	}
     frm.elements.lastPageNum.value="0";
     frm.elements.displayPage.value="1";
	submitForm(frm,'uld.defaults.listdamagereportcommand.do?paginationMode=LIST');
}


function clearScreen(frm){
 	//submitForm(frm,'uld.defaults.clearscreen.do');
 	submitFormWithUnsaveCheck('uld.defaults.clearscreen.do');
}

function printScreen(frm){
 	generateReport(frm,"/uld.defaults.printscreen.do");
}
function damageHistoryScreen(frm){
 	submitForm(frm,'uld.defaults.damagehistoryscreen.do');
}

function viewDamageScreen(frm){
	if(validateSelectedCheckBoxes(frm,'rowId',2566000,1)){
 	//submitForm(frm,'uld.defaults.viewulddamage.do');
	submitForm(frm,'uld.defaults.ux.viewulddamage.do');//Added by A-7924 as part of ICRD-287579 
 	}
}

function uldHistoryScreen(frm){
 	submitForm(frm,'uld.defaults.uldhistoryscreen.do');
}

function uldDetailsScreen(frm){
	if(validateSelectedCheckBoxes(frm,'rowId',25,1)){

		var checked = frm.rowId;
		var selectedRows = "";

		var length= checked.length;


		if(length>1){
			var count=0;
			for(var i=0;i<length;i++){
				if(checked[i].checked == true ){

					var uldnogroup = checked[i].value;
					var splitrow = uldnogroup.split("-");
					if(selectedRows == ""){
						selectedRows = splitrow[0];
					}else{
						selectedRows=selectedRows+","+splitrow[0];
					}
				}
				count++;

			}

		}else{
			//selectedRows=checked.value;
			var splitrow = checked.value.split("-");
			selectedRows = splitrow[0];
				}

		//showDialog(selectedRows, 1, self, frm, 'id_2');
		submitForm(frm,'uld.defaults.detailuldinfo.do?uldNumbersSelected='+selectedRows+'&screenloadstatus=ListDamageReport');

	}

 	//submitForm(frm,'uld.defaults.ulddetailsscreen.do');
}

function deleteScreen(frm){
		if(validateSelectedCheckBoxes(frm,'rowId',2566000,1)){

			var checkedrow = document.getElementsByName("rowId");
			var repairDates = document.getElementsByName("remarksTable");
			var present ="false";
			for(var i=0;i<checkedrow.length;i++) {
				if(checkedrow[i].checked){
					if(repairDates[i].value != ""){
						present="true";
						break;
					}
				}
			}
			if(present == "false") {
				showDialog({msg:'Do you want to delete?',type: 4, parentWindow:self,parentForm: frm,dialogId: 'id_1',
				onClose:function(result){
				
				screenConfirmDialog(frm,'id_1');
				}
				});
				
			}
			if(present == "true") {
				showDialog({msg:'Selected rows includes damages already closed',type: 1, parentWindow:self});
			}
		}


}



function closeScreen(frm){
submitForm(frm,'uld.defaults.closeaction.do');
 	//window.close();
 	//frm.action= appPath + "/home.jsp";
}

function submitPage(lastPg,displayPg){
  targetFormName.elements.lastPageNum.value=lastPg;
  targetFormName.elements.displayPage.value=displayPg;
  submitForm(targetFormName, appPath + '/uld.defaults.listdamagereportcommand.do?paginationMode=LINK');
}

/*function printScreen(frm) {

	generateReport(frm,"/uld.defaults.listdamagereportprint.doReport");
}*/

function chkDate(date){

 	if(date.value==""){

 	return;
 	}
         else
 	{

 	checkdate(date, 'DD-MMM-YYYY');
 	}
}

function screenConfirmDialog(frm, dialogId) {
 	while(frm.elements.currentDialogId.value == ''){
 	}
 	if(frm.elements.currentDialogOption.value == 'Y') {
 		if(dialogId == 'id_1'){

 			submitForm(frm,'uld.defaults.deletescreen.do');
 		 	}
 	}
 }

 function screenNonConfirmDialog(frm, dialogId) {

 	while(frm.elements.currentDialogId.value == ''){

 	}

 	if(frm.elements.currentDialogOption.value == 'N') {
 		if(dialogId == 'id_1'){

 		}

 	 	}
}

function viewPic(seqno,uldno){
//alert("seqno"+seqno);
        targetFormName.elements.seqNum.value=seqno;
        targetFormName.elements.selectedULDNum.value=uldno;


				var newWindow = openPopUp(
								"uld.defaults.listdamage.viewpicture.do?seqNum="+seqno+"&selectedULDNum="+uldno,
				"800","500");

}

function showFacilityCode(){
	
	if(targetFormName.elements.facilityType.value=="" ){
		showDialog({msg:"Please select a specific Facility Type", type:1,parentWindow: self,parentForm: targetFormName,dialogId: 'id_77'});
		return;
	}
	//targetFormName.currentStation.defaultValue=targetFormName.currentStation.value;
	//targetFormName.facilityType.defaultValue=targetFormName.facilityType.value;
	var textfiledDesc="";
	var currentStationForLov=targetFormName.elements.currentStation.value;
	var facilityTypeForLov=targetFormName.elements.facilityType.value;
	var strAction="uld.defaults.lov.screenloadfacilitycodelov.do";
	var StrUrl=strAction+"?textfiledObj=location&formNumber=1&textfiledDesc="+textfiledDesc+'&facilityTypeForLov='+facilityTypeForLov+'&currentStationForLov='+currentStationForLov;
	var myWindow = openPopUpWithHeight(StrUrl, "500");
} 

function showPartyCode(){
var frm=targetFormName;
if(frm.elements.partyType.value=='A'){
	displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.party.value,'party','1','party','',0)
}else if(frm.elements.partyType.value=='G'){
	var textobj = "";
	var strAction="shared.defaults.agent.screenloadagentlov.do";
	//var StrUrl=strAction+"?textfiledObj="+targetFormName.party.value+"&formNumber=1&textfiledDesc="+textobj;
	var StrUrl=strAction+"?textfiledObj=party&formNumber=1&textfiledDesc="+textobj;
	var myWindow = openPopUpWithHeight(StrUrl, "500");
}else if(frm.elements.partyType.value=='O'){

}else if(frm.elements.partyType.value==""){
	showDialog({msg:"Please select a specific Party Type", type:1,parentWindow: self,parentForm: targetFormName,dialogId: 'id_77'});
		return;
}
}
//Added by A-7359 for ICRD - 224586 starts here
function callbackListDamageReport (collapse,collapseFilterOrginalHeight,mainContainerHeight){       
               if(!collapse){
                              collapseFilterOrginalHeight=collapseFilterOrginalHeight*(-1);
               }
               IC.util.widget.updateTableContainerHeight("#div1",+collapseFilterOrginalHeight);
}
//Added by A-7359 for ICRD - 224586 ends here

function colorBox(){
		 jquery('.attach-count').colorbox({
                maxHeight: "90%",
                maxWidth: "90%",
				photo : true,
                loop: false,
				rel: function() {
                    return jquery(this).attr('rel');
                }
            });

	}