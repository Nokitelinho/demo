<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/ux/tlds.jsp" %>
function screenSpecificEventRegister()
{
   var frm=targetFormName;
   with(frm){
   	//CLICK Events
     	evtHandler.addEvents("btnClose","closeCommand()",EVT_CLICK);
		evtHandler.addEvents("btnClear","clearCommand()",EVT_CLICK);
		evtHandler.addEvents("btnList","listCommand()",EVT_CLICK);
		evtHandler.addEvents("btnPrint","printCommand()",EVT_CLICK);
		evtHandler.addIDEvents("SUGGEST_mailToDisplay","getMailbagIds()",EVT_CLICK);
		targetFormName.elements.mailToDisplay.setAttribute("suggest_sel_event","takeMySuggestedValue");
		targetFormName.elements.mailToDisplay.setAttribute("direct_value","false");
		evtHandler.addIDEvents("notesseltab","showHistoryDetailsView()",EVT_CLICK);
		evtHandler.addIDEvents("historyseltab","showHistoryNotesView()",EVT_CLICK);
		jquery('#mainSection').hide();
    }
	onScreenLoad();
}
function onScreenLoad(){
	jquery('#tabs-notes').hide();	
	 jquery('#tabs-history').show();
	 jquery('#tabs-notes').hide();
	 jquery('#tabs').tabs({});
	 jquery('#historyforcetabid').show();
	 jquery('#newMajeure').show();
	 jquery('#history_Tbl').show();
	 jquery('#notesforcetabid').hide();
	 jquery('#headerDataReq').hide();
	if(targetFormName.elements.btnDisableReq.value != "Y"){
		//disableField(targetFormName.elements.btnList);
		//disableField(targetFormName.elements.mailbagId);
		enableField(targetFormName.elements.btnPrint);
		showMainTab();
		jquery('#mainSection').show();
	}
	else{
		disableField(targetFormName.elements.btnPrint);
	}
	IC.util.widget.createDataTable("mbHisTable",{tableId:"mbHisTable",childTemplatingReqrd:false,hasChild:false,scrollingY:"5vh"});
	IC.util.widget.recalculateTableContainerHeightForUx(jquery("section"),{hideEmptyBody:true})
	jquery('a#addInfo').webuiPopover({
                 closeable: false,
				 style:'cust-style',
                 placement: 'top'
            });
}
function getMailbagIds(){
	var funct_to_overwrite = "showMailbagIdList";
    var strAction = 'mail.operations.ux.mbHistory.displaymailbagidsuggest.do';
	asyncSubmit(targetFormName,strAction, funct_to_overwrite,null,null);
}
function showMailbagIdList(_result){
	if(_asyncErrorsExist) return;
	document.getElementById("tmpSpan").innerHTML=_result;
	_mailbagIdDiv=_result.document.getElementById("_ajaxMailbagIds");
	_mailbagIdDivs=_mailbagIdDiv.getElementsByTagName("div");
	_mailbagIdArr=new Array();
	for(var t=0;t<_mailbagIdDivs.length;t++){
		_mailbagId=_mailbagIdDivs[t].getAttribute("mailbagId");
		_mailbagIdArr.push(new Array(_mailbagId));
	}
	//suggestCntrlFnForCombinationFlds('mailToDisplay','mailToDisplay',_mailbagIdArr,1);	
		var options = {
		elementName:'totalViewRecords',
		elementId:'mailToDisplay' ,
		suggestArray:_mailbagIdArr ,
		isMultiValued:1 ,
		extraField:null ,
		panelHeight:null ,
		rowsToShow:4,
		panelWidth:26,
		showToolTip:false,
		layoutId:null
	};
	suggestCntrlFn(options);
	//	document.getElementById("tmpSpan").innerHTML="";
}
function takeMySuggestedValue(){
	_res = arguments[0];
	targetFormName.elements.mailbagId.value=_res[0];
	targetFormName.elements.index.value=arguments[2];
	//jQuery('#tmpSpan').text(arguments[0][1]);
	listCommand();
}
//To show filter section as edit mode
function showOverViewTab(){
	jquery('#editSearch').show();
	jquery('#searchPreview').hide();
}
//To show filter section as display mode
function showMainTab(){
	debugger;
	jquery('#editSearch').hide();
	jquery('#searchPreview').show();
}
function printCommand(){
   frm=targetFormName;
   generateReport(frm,'/mail.operations.ux.mbHistory.print.do');
}
function clearCommand(){
   frm=targetFormName;
   submitForm(frm,'mail.operations.ux.mbHistory.clear.do');
}
function listCommand(){
   frm=targetFormName;
   if(frm.elements.mailbagId.value==null || frm.elements.mailbagId.value.length == 0){
		showDialog({msg:'Invalid Mail bag entered',type:1,parentWindow:self});	
 		frm.elements.mailbagId.focus();
		return;
   }
   submitForm(frm,'mail.operations.ux.mbHistory.list.do');
   IC.util.widget.createDataTable("mbHisTable",{tableId:"mbHisTable",childTemplatingReqrd:false,hasChild:false,scrollingY:"66vh",
                                });
}
function closeCommand(){
	frm=targetFormName;
	submitForm(frm,'mail.operations.ux.mbHistory.close.do');
}
function showPane1(event,obj){	
	var retValue = showPane(event,'pane1', obj);	
	return retValue;
}
function showHistoryNotesView(){
	jquery('#tabs-history').show();
	jquery('#tabs-notes').hide();
	jquery('#tabs').tabs({});
	jquery('#historyforcetabid').show();
	 jquery('#notesforcetabid').hide();
	jquery('#history_Tbl').show();
	jquery('#notes_Tbl').hide(); 
}
function showHistoryDetailsView(){
jquery('#tabs-history').hide();
	jquery('#tabs-notes').show();
	jquery('#tabs').tabs({});
	jquery('#notesforcetabid').show();
	jquery('#historyforcetabid').hide();
	jquery('#history_Tbl').hide();
	jquery('#notes_Tbl').show();
}