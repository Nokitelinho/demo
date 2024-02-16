<%@ include file="/jsp/includes/js_contenttype.jsp"%>
<%@ include file="/jsp/includes/ux/tlds.jsp"%>


function screenSpecificEventRegister() {
var frm = targetFormName;
with(targetFormName){
evtHandler.addEvents("btnList","list(targetFormName)",EVT_CLICK);
evtHandler.addIDEvents("invnumberlov","invokeLOV()",EVT_CLICK);
//evtHandler.addIDEvents("invnumberlov","displayLOV('showNewInvoiceLOV.do','N','Y','showNewInvoiceLOV.do',targetFormName.elements.invoiceNumber.value,'InvoiceNumber','1','invoiceNumber','',0)	",EVT_CLICK);
evtHandler.addIDEvents("edittab","showOverViewTab()",EVT_CLICK);
evtHandler.addIDEvents("btnClear","clear()",EVT_CLICK);
evtHandler.addIDEvents("btnApplyFilter","applyFiletr(targetFormName)",EVT_CLICK);
evtHandler.addIDEvents("btnClose","closefn()",EVT_CLICK);
evtHandler.addIDEvents("btnClearFilter","clearFilter(targetFormName)",EVT_CLICK);
//evtHandler.addEvents("billChkAll","updateHeaderCheckBox(targetFormName, targetFormName.elements.billChkAll, targetFormName.elements.rowId)", EVT_CLICK);
//evtHandler.addEvents("allCheck","updateHeaderCheckBox(targetFormName, targetFormName.elements.allCheck, targetFormName.elements.check)", EVT_CLICK);	
evtHandler.addEvents("btnSave","save(targetFormName)", EVT_CLICK);	//added by a-7871
evtHandler.addEvents("btnAccDetails","listaccounting()",EVT_CLICK);
evtHandler.addEvents("chequeAmount","restrictFloat(this,9,5)",EVT_KEYPRESS);
}

onscreenload();
}
function onscreenload(){

var actionFlag =targetFormName.elements.actionFlag.value;
	
jquery('#mainDiv').hide();
jquery('#header').show();
if(actionFlag=="OK"){
IC.util.widget.createDataTable("settlementTable",{tableId:"settlementTable",hasChild:false,scrollingY:'13.6vh'});
}
if(actionFlag == "CREATE"||actionFlag=="SEARCH"||actionFlag=="OK"||actionFlag=="CLOSE"||actionFlag=="SHOWCREATRE"||actionFlag=="SHOWOK"){
jquery('#mainDiv').show();
jquery('#header').hide();

IC.util.widget.createDataTable("div1",{tableId:"div1",hasChild:false,scrollingY:'35vh'
                                });

}

          
jquery('.dropdown-toggle').webuiPopover({
    content: function () {
        return jquery(this).next('.dropdown-box').html();
    },
    width: '180',
    padding: false,
    style: ' dropdown',
	
});
 
 
 jquery('body').on('click', '.filter-sec', function () {
            jquery(".filter-panel-wrap").dialog({
                classes: {
                    'ui-dialog': 'scribble-pad show-arrow fixed-dialog'
                },
                width: '250px',
                title: "Filter",
                clickOutside: true, // clicking outside the dialog will close it
                clickOutsideTrigger: ".filter-panel-wrap",
                hide: {
                    effect: "fade",
                    duration: 150
                },
                position: {
                    my: "right top+20",
                    at: "right-5 bottom",
                    of: jquery(this)
                }
           });
         });
if(targetFormName.elements.popupFlag.value =="true")
{

if(targetFormName.elements.flagFilter.value =="Y"){
var popupTitle = "Available Settlements";
		var urlString = "mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.openPopup.do?&actionFlag=LIST_SETTLE"+"&displayPage=1"+"&lastPageNum=0";
		var closeButtonIds = "btnOk";
		var optionsArray = {	
			actionUrl : urlString,
			dialogWidth:"800",
			dialogHeight:"300",
			closeButtonIds : closeButtonIds,
			popupTitle: popupTitle	
	   }
		popupUtils.openPopUp(optionsArray);
}
else if(targetFormName.elements.flagFilter.value =="N"){
var popupTitle = "Available Settlements";

var createButtonFlag=targetFormName.elements.createButtonFlag.value;
		var urlString = "mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.openPopup.do?&actionFlag=LIST_UNSETTLE"+"&displayPage=1"+"&lastPageNum=0"+"&createButtonFlag="+createButtonFlag+"";
		var closeButtonIds = "btnOk";
		var optionsArray = {	
			actionUrl : urlString,
			dialogWidth:"800",
			dialogHeight:"300",
			closeButtonIds : closeButtonIds,
			popupTitle: popupTitle	
	   }
		popupUtils.openPopUp(optionsArray);
}

}


if(targetFormName.elements.chequeFlag.value =="false"){
document.getElementById("addLink").disabled=true;
document.getElementById("deleteLink").disabled=true;

}
else if(targetFormName.elements.chequeFlag.value =="true"){
document.getElementById("addLink").disabled=false;
document.getElementById("deleteLink").disabled=false;
//addTemplateRow('captureRow','settlementBody','stlOpFlag');
}
if(actionFlag == "CREATE"||actionFlag=="SEARCH"||actionFlag=="OK"||actionFlag=="CLOSE"||actionFlag=="SHOWCREATRE"||actionFlag=="SHOWOK"){
	document.getElementById("btnAccDetails").disabled=false;
	document.getElementById("btnSave").disabled=false;
}else{
document.getElementById("btnAccDetails").disabled=true;
document.getElementById("btnSave").disabled=true;

}

}

function list(targetFormName){

targetFormName.elements.lastPageNum.value="0";
targetFormName.elements.displayPage.value="1";

submitForm(targetFormName,'mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.list.do?actionFlag=LIST');	

}
//added by a-7871 starts ---
function save(targetFormName){

var templateIndex=document.getElementsByName("rowId").length-1;

var selIndex ='';
var isSelected=false;
var chkboxes=document.getElementsByName("check");


	for(var index=0;index<chkboxes.length;index++){
		if(chkboxes[index].checked == true){
			isSelected=true;
				if(selIndex.trim().length == 0){
					selIndex=chkboxes[index].value;
					}
					else{
					selIndex=selIndex+"-"+chkboxes[index].value;
				    }
					}
	}
	if(isSelected==false)//Added by a-7871 for ICRD-223130
	{
	selIndex="ALL";
	}
isDelete();
isModify(); //Added by A-8399 as part of ICRD-305647
updateOperationFlags(targetFormName);
submitForm(targetFormName,'mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.save.do?chequeNumberFilter='+selIndex+'&templateIndex='+templateIndex);
	
}

function updateOperationFlags(frm){

var operationFlags=[];
//var stlop=document.getElementsById("stlflag");
for(var i = 0; i<frm.elements.stlOpFlag.length;i++){ 

operationFlags.push(frm.elements.stlOpFlag[i].value);
}
	//var operationFlags = frm.elements.stlOpFlag.value;
	var chequeNo = frm.elements.chequeNumber;
	var chequeDate = frm.elements.chequeDate;
	var bankName = frm.elements.bankName;
	var branchName = frm.elements.branchName;
	var chequeAmount = frm.elements.chequeAmount;
	var deleteFlag = frm.elements.isDelete;
	var settlementRemarks = frm.elements.chequeRemarks;
	for(var index = 0; index<operationFlags.length;index++){
		if(isElementModified(chequeNo[index]) || isElementModified(chequeDate[index]) || isElementModified(bankName[index]) || isElementModified(branchName[index])|| isElementModified(chequeAmount[index])	|| isElementModified(settlementRemarks[index])||isElementModified(deleteFlag[index])){
			if("NOOP" != operationFlags[index] && "I" != operationFlags[index] && "D" != operationFlags[index]){
				operationFlags[index].value = "U";
				//alert(operationFlags[index].value);
				frm.elements.stlOpFlag[index].value = "U";
			}
		
		}
	}
}

//Added by A-8399 as part of ICRD-305647 starts ---
function isModify(){
	
	var caseClosedFlag=document.getElementsByName("caseClosed");
	
	for(var i=0; i<caseClosedFlag.length; i++){
		
			if(caseClosedFlag[i].checked){
			
				targetFormName.elements.caseClosedArray[i].value = "true";
				
			}
			else{
			
				targetFormName.elements.caseClosedArray[i].value = "false";
			}
		
	}
	
}
//Added by A-8399 ends ---
function isDelete(){
var deletedArray ="";
var val = "";
	for(var i=0;i<targetFormName.elements.length;i++) {
	if(targetFormName.elements[i].name =='isDelete') {
			if(targetFormName.elements[i].checked) {
				 val = "true";
			}
			else {
			 	 val = "false";
			}
				if(deletedArray != "")
					deletedArray = deletedArray+","+val;
				else if(deletedArray == "")
					deletedArray = val;
		}
}
targetFormName.elements.deleteArray.value=deletedArray;
}
//added by a-7871 ends ---
function showOverViewTab(){

jquery('#mainDiv').hide();
jquery('#header').show();
document.getElementById("btnList").disabled=false;

}
function clear(){
submitForm(targetFormName,'mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.clear.do');
}


function viewHistoryFn(indexValue){
WebuiPopovers.hideAll();
var action=targetFormName.actionFlag.value;
action = "VIEW";
		var popupTitle = "View History";
		var urlString = "mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.viewhistory.do?selectedIndex="+indexValue+"&actionFlag="+action;
		var closeButtonIds = "btnOk";
		var optionsArray = {	
			actionUrl : urlString,
			dialogWidth:"700",
			dialogHeight:"400",
			closeButtonIds : closeButtonIds,
			popupTitle: popupTitle	
	   }
		popupUtils.openPopUp(optionsArray);
		

	}
function remarksFn(indexValue){
 WebuiPopovers.hideAll(); 
var action=targetFormName.actionFlag.value;
action = "REMARK";
		var popupTitle = "Remarks";
		var urlString = "mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.viewhistory.do?selectedIndex="+indexValue+"&actionFlag="+action;
		var closeButtonIds = "btnOk";
		var optionsArray = {	
			actionUrl : urlString,
			dialogWidth:"500",
			dialogHeight:"300",
			closeButtonIds : closeButtonIds,
			popupTitle: popupTitle	
	   }
		popupUtils.openPopUp(optionsArray);

		
		
	}
function showfilterSection(){
jquery('#filterSection').show();
}	
function applyFiletr(targetFormName){
targetFormName.elements.lastPageNum.value="0";
targetFormName.elements.displayPage.value="1";
var mailbagId=jquery('#mailbagId').val();
var status=jquery('#status').val();

submitForm(targetFormName,"mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.search.do?mailbagId="+mailbagId+"&invoiceStatus="+status);
}
function clearFilter(targetFormName){
targetFormName.elements.lastPageNum.value="0";
targetFormName.elements.displayPage.value="1";
var mailbagId="";
var status="";
submitForm(targetFormName,"mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.search.do?mailbagId="+mailbagId+"&invoiceStatus="+status);	


}
	function closefn(){
	
	
	submitForm(targetFormName,'mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.close.do');

}


function addFn(){

addTemplateRow('captureRow','settlementBody','stlOpFlag');
}
function deleteFn(){
	
var frm = targetFormName;
var chkboxes=document.getElementsByName("rowId");
var addButton=[]
var count='';

	for(var i=0;i<chkboxes.length-1;i++){
if(chkboxes[i].checked){
			count=i;

addButton.push(frm.elements.addButton[count].value);
		if(addButton=="N"){
deleteTableRow('rowId','stlOpFlag');
}
else if(addButton=="Y"){
	showDialog({msg:'Already saved row cannot be deleted',type:1,parentWindow:self});
}
		}

}
	

}

function invokeLOV(){

	var optionsArray =getLOVOptions();
	if(optionsArray){
		//lovUtils.showLovPanel(optionsArray);
		lovUtils.displayLOV(optionsArray);
	           }
}


function getLOVOptions(){

var optionsArray;
var strUrl ='showNewInvoiceLOV.do?formCount=1';
					optionsArray = {	
						mainActionUrl				: strUrl,
						isMultiSelect				: 'N' ,
						isPageable					: 'Y',
						paginationActionUrl			: strUrl,
						lovTitle					: 'Select Invoice number',
						codeFldNameInScrn			: 'invoiceNumber',
						descriptionFldNameInScrn	: '' ,
						index						: '0',
						closeButtonIds 				: ['CMP_MRA_GPABILLING_INVLOV_CLOSE','CMP_MRA_GPABILLING_OK'],	
						dialogWidth					: 600,
						dialogHeight				: 450,
						fieldToFocus				: 'invoiceNumber',
						lovIconId					: 'invnumberlov_btn',
						maxlength					: 10
						};
 
					return optionsArray;
}

function submitPage(lastPageNum,displayPage){
targetFormName.elements.lastPageNum.value=lastPageNum;
targetFormName.elements.displayPage.value=displayPage;
//Added by A-7540 starts
var chequeAmount = targetFormName.elements.chequeAmount;
var caseClosedFlag=document.getElementsByName("caseClosed");
var check = new Boolean(false);
var currentSettlingAmount=document.getElementsByName("currentSettlingAmount");
	for(var index=0; index < currentSettlingAmount.length; index++){
      if(isElementModified(currentSettlingAmount[index]) || isElementModified(caseClosedFlag[index] )){
		  check = true;
		  break;
	
	    }				
     }
	 for(var index=0; index < chequeAmount.length; index++){
       if(isElementModified(chequeAmount[index]) ||  chequeAmount[index]!="" || isElementModified(caseClosedFlag[index] )){
		  check = true;
		  break;
	
	    }				
     }
	if(check == true){
	showDialog({ 
							msg: 'Do you want to save?',
							type: 4,
							parentWindow: self,
							parentForm: targetFormName,
							dialogId: 'id_1',
							onClose: function() {
								screenConfirmDialog(targetFormName, 'id_1');
								screenNonConfirmDialog(targetFormName, 'id_1');
							}
						});
	 }
	   else{			
            submitForm(targetFormName, "mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.list.do");
			} 
//Added by A-7540 ends
}
function showEntriesReloading(obj){

var actionFlag=targetFormName.elements.actionFlag.value;
	targetFormName.elements.defaultPageSize.value=obj.value;
	targetFormName.elements.displayPage.value="1";
	targetFormName.elements.lastPageNum.value="0";
	if(actionFlag=="OK"||actionFlag=="SHOWOK"||actionFlag=="SEARCH"){
	var action = "mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.list.do?actionFlag=SHOWOK";     
	submitForm(targetFormName, action);
	}
	else if(actionFlag=="CREATE"||actionFlag=="SHOWCREATRE"||actionFlag=="SEARCH"){
	var action = "mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.list.do?actionFlag=SHOWCREATRE";     
	submitForm(targetFormName, action);
	}
}

function screenConfirmDialog(frm, dialogId) {
    while (frm.elements.currentDialogId.value == '') {
			}
    if (frm.elements.currentDialogOption.value == 'Y') {
        if (dialogId == 'id_1') {
		targetFormName.elements.saveBtnFlg.value='Y';
            save(targetFormName);
			submitForm(targetFormName, "mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.list.do");
		}
	}
}

function screenNonConfirmDialog(frm, dialogId) {
    while (frm.elements.currentDialogId.value == '') {
		}
    if (frm.elements.currentDialogOption.value == 'N') {
        if (dialogId == 'id_1') {
		submitForm(targetFormName, "mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.list.do");
	}
   }
}

function enablebasecheckbox(rowID){
var rowindex=rowID;	
var checkBoxesId = document.getElementsByName('check');
var caseclosed = document.getElementsByName('caseClosed');

	if( caseclosed[rowindex].checked){
		checkBoxesId[rowindex].checked=true;
	}else{
		checkBoxesId[rowindex].checked=false;	
	}
} 



function listaccounting(){
var frm=targetFormName;
	var index='';
	
	
	if(validateSelectedCheckBoxes(targetFormName,'check',1,1)){
		var checks = document.getElementsByName('check');	
	    if(checks!=null){
		for(var i=0;i<=checks.length-1;i++){
		     		if(checks[i].checked==true ){					
				
		     	  
						if(index !=''){
							index=index +","+ i;
						}else{
							index+=i;
						}
						
		      		}
		    	}
		} 
		targetFormName.elements.selectedMailbag.value=index;
		submitForm(targetFormName,'mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.listaccdetails.do');
	}
} 
