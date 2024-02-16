<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister() {
	var frm = targetFormName;

	with(frm) {

		evtHandler.addEvents("btnList","submitAction('listDetails',targetFormName)",EVT_CLICK);
		evtHandler.addEvents("btnClear","submitAction('clearDetails',targetFormName)",EVT_CLICK);
		evtHandler.addEvents("btnClose","closeScreen(targetFormName)",EVT_CLICK);
		evtHandler.addIDEvents("paCode_btn","invokeLOV('PACOD')",EVT_CLICK);
		evtHandler.addIDEvents("edittab","showOverViewTab()",EVT_CLICK);
		evtHandler.addIDEvents("mailbagId_btn","invokeLOV('MAILBAGID')",EVT_CLICK);	
		evtHandler.addIDEvents("closetab","showOvercloseTab()",EVT_CLICK);		//evtHandler.addIDEvents("mailbagId_btn","displayLOV('showNewDespatchLOV.do','N','Y','showNewDespatchLOV.do',targetFormName.elements.mailbagId.value,'Mail Bags','0','mailbagId',targetFormName.mailbagId.value,0)",EVT_CLICK);

	}

	onScreenLoad();
}

function onScreenLoad(){
	var actionFlag =targetFormName.elements.actionFlag.value;
	if(actionFlag=='SHOWLIST'){	
	jquery('#headerForm').hide();	
	jquery('#headerData').show();
	}
	setTimeout(function(){
	IC.util.widget.createDataTable("newClaimTable",{tableId:"newClaimTable",hasChild:false,scrollingY:'5vh'});
	document.getElementById('newClaimTable_wrapper').style.width = '100%';
	IC.util.widget.recalculateTableContainerHeightForUx(jquery("section"),{hideEmptyBody:true})
	}, 100);
 document.getElementById("btnList").disabled=false;
 document.getElementById("btnClose").disabled=false;

 }
function showOvercloseTab(){
jquery('#headerData').show();
jquery('#headerForm').hide();
document.getElementById("btnList").disabled=true;
setTimeout(function(){
		IC.util.widget.createDataTable("newClaimTable",{tableId:"newClaimTable",hasChild:false,scrollingY:'5vh'});
		document.getElementById('newClaimTable_wrapper').style.width = '100%';
		IC.util.widget.recalculateTableContainerHeightForUx(jquery("section"),{hideEmptyBody:true}) 
	}, 100);	 
 }
function showOverViewTab(){
jquery('#headerData').hide();
jquery('#headerForm').show();
document.getElementById("btnList").disabled=false;
setTimeout(function(){
		IC.util.widget.createDataTable("newClaimTable",{tableId:"newClaimTable",hasChild:false,scrollingY:'5vh'});
		document.getElementById('newClaimTable_wrapper').style.width = '100%';
		IC.util.widget.recalculateTableContainerHeightForUx(jquery("section"),{hideEmptyBody:true}) 
	}, 100);
}

 function submitAction(actiontype,targetFormName){
	var frm = targetFormName;
	 var type_action=actiontype;
if(type_action == 'listDetails') {
	frm.elements.lastPageNum.value="0";
	frm.elements.displayPage.value="1";
	var frmDate=frm.elements.fromDate.value;
	var toDat=frm.elements.toDate.value;
	if((frmDate == "" ||frmDate == null)){
			showDialog({	
				msg		:	"<common:message bundle="ClaimDetailsResourceBundle" key="mail.mra.gpareporting.ux.claimDetails.fromdate_todate.mandatory" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.fromDate.focus();
			return;
		}else if((toDat == "" || toDat == null)){
			showDialog({	
				msg		:	"<common:message bundle="ClaimDetailsResourceBundle" key="mail.mra.gpareporting.ux.claimDetails.fromdate_todate.mandatory" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.toDate.focus();
			return;
		}
	submitForm(targetFormName,"mail.mra.gpareporting.ux.claimDetails.list.do");
}
if(type_action == 'clearDetails') {
		submitForm(targetFormName,"mail.mra.gpareporting.ux.claimDetails.clear.do");
}

}

function closeScreen(targetFormName){
	//Added as part of ICRD-343131 starts
var frm = targetFormName;
var screenname=frm.elements.fromScreen.value;
if(screenname=='MRA080')
    submitForm(targetFormName,"mail.mra.gpareporting.ux.generateandlistclaim.list.do");
else//Added as part of ICRD-343131 ends
location.href = appPath + "/home.jsp";
} 

function showEntriesforlisting(obj){
	targetFormName.elements.defaultPageSize.value=obj.value;
	targetFormName.elements.displayPage.value="1";
	targetFormName.elements.lastPageNum.value="0";
	var action = "mail.mra.gpareporting.ux.claimDetails.list.do";     
	submitForm(targetFormName, action);
}
function submitPage(lastPageNum,displayPage){
	var actionFlag =targetFormName.elements.actionFlag.value;	
	targetFormName.elements.lastPageNum.value=lastPageNum;
	targetFormName.elements.displayPage.value=displayPage;
	var action = "mail.mra.gpareporting.ux.claimDetails.list.do";     
	submitForm(targetFormName, action);	
}  
function invokeLOV(LOVType){
	var optionsArray =getLOVOptions(LOVType);
	if(optionsArray){
			lovUtils.displayLOV(optionsArray);
	}
}

function getLOVOptions(LOVType){
var optionsArray;
	switch(LOVType) {	
		case 'PACOD':
		var strUrl ='mailtracking.defaults.ux.palov.list.do?formCount=1';
		optionsArray = {	
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'PA Code',
				codeFldNameInScrn			: 'paCode',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnOk','btnClose'],	
				dialogWidth					: 600,
				dialogHeight				: 500,
				fieldToFocus				: 'paCode',
				lovIconId					: 'paCode_btn',
				maxlength					: 3
			}
			 break;
		case 'MAILBAGID':
		var strUrl ='showNewDespatchLOV.do?formCount=1';
		optionsArray = {	
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Mail Bags',
				codeFldNameInScrn			: 'mailId',
				descriptionFldNameInScrn	: 'MailID',
				index						: 0,
				closeButtonIds 				: ['btnOk','btnClose'],	
				dialogWidth					: 700,
				dialogHeight				: 650,
				fieldToFocus				: 'mailId',
				lovIconId					: 'mailbagId_btn',
				maxlength					: 30
			}
			 break;
			default:
			optionsArray = {	
			}
		}
		return optionsArray;			 

}
