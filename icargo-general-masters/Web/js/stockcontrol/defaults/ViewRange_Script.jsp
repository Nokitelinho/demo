<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	var frm=targetFormName;
	with(frm){
		evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);
		evtHandler.addEvents("btnListAWBStock","submitAction()",EVT_CLICK);
	}

	viewDetailsOnDblClick();
//	DivSetVisible(true);

}

function viewDetailsOnDblClick(){
	return;
}
function onClickImage(_index){
	
    var index1 = _index.id.split('indicator')[1];
	var rangeFrom=getCellData("availableTableDiv",index1,1);
	openPopUp("stockcontrol.defaults.utiliseddocsinfo.do?fromRange="+rangeFrom+"&&fromScreen=VIEW",200,200);
}
function submitAction(){
	var availableArr=getSelectedValues("availableTableDiv",0);
	if(availableArr != null && availableArr.length > 0) {
		
		var strAction="stockcontrol.defaults.listawbstock.do?";
		recreateTableDetails(strAction,"divAWB");	
	}
	else{
		
		showDialog({	
				msg		:	"Please select a row",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,				
			});
		return;
	}
	
}
function recreateTableDetails(strAction,divId){	
	var __extraFn="updateTableCode";
	if(arguments[2]!=null){
		__extraFn=arguments[2];
	}
	targetFormName.elements.displayPage.value=1;
	targetFormName.elements.lastPageNumber.value=0;
	asyncSubmit(targetFormName,strAction,__extraFn,null,null,divId);	
}

function updateTableCode(_tableInfo){	
	_str = _tableInfo.document.getElementById("divAWBAJAX").innerHTML;		
	document.getElementById("divAWB").innerHTML=_str;
}

function submitPage(lastPageNum,displayPage){
	targetFormName.elements.lastPageNumber.value = lastPageNum;
	targetFormName.elements.displayPage.value = displayPage;
	
	//}
	var availableArr=getSelectedValues("availableTableDiv",0);
	if(availableArr != null && availableArr.length > 0) {
		var strAction="stockcontrol.defaults.listawbstock.do?";
		recreateTableDetailsonnext(strAction,"divAWB");
	}
	else{
		
		showDialog({	
				msg		:	"Please select a row",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,				
			});
		return;
	}
}
function recreateTableDetailsonnext(strAction,divId){	
	var __extraFn="updateTableCode";
	if(arguments[2]!=null){
		__extraFn=arguments[2];
	}
	asyncSubmit(targetFormName,strAction,__extraFn,null,null,divId);	
}

