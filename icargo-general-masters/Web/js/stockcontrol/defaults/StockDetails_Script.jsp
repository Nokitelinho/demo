<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister(){


	var frm = targetFormName;
	with(frm){
		evtHandler.addEvents("stockHolderCodeLov","displayStockHolderLov('stockcontrol.defaults.screenloadstockholderlov.do')",EVT_CLICK);
		evtHandler.addEvents("btnList","onClickList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","onClickClear()",EVT_CLICK);
		evtHandler.addEvents("btnClose","onclickClose()",EVT_CLICK);
		evtHandler.addEvents("btnClose","shiftFocus()",EVT_BLUR);
	}
	onScreenLoad();
}

function onScreenLoad(){
	if(!(targetFormName.elements.stockHolderType.disabled)){	
	    targetFormName.elements.stockHolderType.focus();
	}
}

function displayStockHolderLov(strAction){
	var stockHolderCode='stockHolderCode';
	var stockHolderType='stockHolderType';
	var val=targetFormName.elements.stockHolderCode.value;
	var typeVal=targetFormName.elements.stockHolderType.value;
	var strUrl = strAction+"?code="+val+"&codeName="+stockHolderCode+"&stockHolderTypeValue="+typeVal+"&typeName="+stockHolderType;
	var clientHeight = document.body.clientHeight;
	var clientWidth = document.body.clientWidth;
	
	//Added by A-2850 on 15-oct-07 for setting width and height dynamically ends
	openPopUp(strUrl,600,468);
}

function onClickList(){

	if(targetFormName.elements.stockHolderType.value==""){

		showDialog({	
				msg		:	"<common:message bundle="stockdetailsresources" key="stockcontrol.defaults.stockdetails.stockHolderTypeismandatory" scope="request"/>",
				type	:	1, 
				parentWindow: self,
				parentForm: targetFormName,
		});
		
		
	}
  	if(targetFormName.elements.docType.value==""){
		
		showDialog({	
				msg		:	"<common:message bundle="stockdetailsresources" key="stockcontrol.defaults.stockdetails.documenttypeismandatory" scope="request"/>",
				type	:	1, 
				parentWindow: self,
				parentForm: targetFormName,
		});
		targetFormName.elements.docType.focus();
		return;
		
	}
 	if(targetFormName.elements.subType.value==""){
	
		showDialog({	
				msg		:	"<common:message bundle="stockdetailsresources" key="stockcontrol.defaults.stockdetails.documentsubtypeismandatory" scope="request"/>",
				type	:	1, 
				parentWindow: self,
				parentForm: targetFormName,
		});
		targetFormName.elements.subType.focus();
		return;
	}
	if(targetFormName.elements.fromDate.value==""){
	
		showDialog({	
				msg		:	"<common:message bundle="stockdetailsresources" key="stockcontrol.defaults.stockdetails.fromdateismandatory" scope="request"/>",
				type	:	1, 
				parentWindow: self,
				parentForm: targetFormName,
		});
		targetFormName.elements.fromDate.focus();
		return;
	}
	if(targetFormName.elements.toDate.value==""){
	
		showDialog({	
				msg		:	"<common:message bundle="stockdetailsresources" key="stockcontrol.defaults.stockdetails.todateismandatory" scope="request"/>",
				type	:	1, 
				parentWindow: self,
				parentForm: targetFormName,
		});
		targetFormName.elements.toDate.focus();
		return;
	}
	targetFormName.elements.displayPage.value="1";
	targetFormName.elements.lastPageNum.value="0";

	submitForm(targetFormName,"stockcontrol.defaults.liststockdetails.do");
}

function onClickClear(){
	
	submitForm(targetFormName,"stockcontrol.defaults.clearstockdetails.do");
}

function onclickClose(){
	var frm = targetFormName;
	submitForm(frm,"stockcontrol.defaults.closestockdetails.do");
}

function shiftFocus(){
	if(!event.shiftKey){
		if(!(targetFormName.elements.stockHolderType.disabled)){	
			targetFormName.elements.stockHolderType.focus();
		}
	}
}

function submitList(lastPageNum,displayPage){
	var frm = targetFormName;
	frm.elements.lastPageNum.value=lastPageNum;
	frm.elements.displayPage.value=displayPage;
	submitForm(frm,appPath+'/stockcontrol.defaults.liststockdetails.do');
}