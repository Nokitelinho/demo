<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister(){
	var frm = targetFormName;
	with(frm){
		
		//onScreenloadSetHeight();
		evtHandler.addEvents("reqRefNo","validateFields(this,-1,'Req Ref No',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("code","validateFields(this,-1,'Stock Holder Code',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("fromDate","autoFillDate(this)",EVT_BLUR);
		evtHandler.addEvents("toDate","autoFillDate(this)",EVT_BLUR);
		evtHandler.addEvents("btList","onClickList()",EVT_CLICK);
		evtHandler.addEvents("btClear","onClickClear()",EVT_CLICK);
		if(targetFormName.elements.checkbox){
			evtHandler.addEvents("checkbox","singleSelect(this)",EVT_CLICK);
		}

		evtHandler.addEvents("btCreate","onClickCreate()",EVT_CLICK);
		evtHandler.addEvents("btDetails","onClickDetails()",EVT_CLICK);
		evtHandler.addEvents("btCancel","onClickCancel()",EVT_CLICK);
		evtHandler.addEvents("btClose","closeScreen()",EVT_CLICK);

		evtHandler.addEvents("partnerAirline","showPartnerAirlines()",EVT_CLICK);
		evtHandler.addEvents("awbPrefix","populateAirlineName()",EVT_CHANGE);
         /*Commented as a part of ICRD-243139 
		evtHandler.addEvents("checkbox","toggleTableHeaderCheckbox('checkbox', targetFormName.checkbox);",EVT_CLICK);*/
		evtHandler.addEvents("docType","onChangeOfDocTyp()",EVT_CHANGE);
	}
	applySortOnTable("stkRequestTable",new Array("None","String","String","Date","String","String","Number","String","String"));
	/*
		Enable partner airline combo if checked on screenload
	*/
	showPartnerAirlines();


	onChangeOfDocTyp();
	setFocus();
//	DivSetVisible(true);
}

function onScreenloadSetHeight(){

	var height = document.body.clientHeight;
	document.getElementById('pageDiv').style.height = ((height*85)/100)+'px';
	document.getElementById('div1').style.height = (((height*70)/100)-197)+'px';
	//document.getElementById('pageDiv').style.height = 400+'px';
}

/**
 * This function submits the form to preserve the current selected values while clicking next and prev.
 *
 * @param strlastPageNum - The last page number
 * @param strDisplayPage - The page which has to be displayed.
 */

function singleSelect(checkVal){
	var frm = targetFormName;
	for(var i=0;i<frm.elements.length;i++){
		if(frm.elements[i].type =='checkbox'){
			if(frm.elements[i].checked == true){
				//element names check added for bug 108847 by A-3767 on 31Mar11
				if(frm.elements[i].value != checkVal.value && frm.elements[i].name!='manual' && frm.elements[i].name!='partnerAirline'){
					frm.elements[i].checked = false;
				}
			}
		}
	}
}

function onClickClear(){
	var frm = targetFormName;
	submitForm(frm,"stockcontrol.defaults.clearliststockrequest.do");
}

function onClickList(){
	var frm = targetFormName;
	if(frm.elements.reqRefNo.value==""){
		if(frm.elements.docType.value==""){
			showDialog({	
				msg		:	'<common:message bundle="liststockrequestresources" key="stockcontrol.defaults.liststockrequest.documenttypeismandatory" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.docType.focus();
			return;
		}
		if(frm.elements.fromDate.value==""){
			showDialog({	
				msg		:	'<common:message bundle="liststockrequestresources" key="stockcontrol.defaults.liststockrequest.fromdateismandattory" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.fromDate.focus();
			return;
		}
		if(frm.elements.toDate.value==""){
			showDialog({	
				msg		:	'<common:message bundle="liststockrequestresources" key="stockcontrol.defaults.liststockrequest.todateismandatory" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.toDate.focus();
			return;
		}
 	}
	frm.elements.checkList.value="Y";
	frm.elements.oneRow.value="N";
	submitForm(frm,"stockcontrol.defaults.liststockrequest.do?countTotalFlag=Y");
}

function onClickCancel(){
	var frm = targetFormName;
    if(frm.elements.checkbox)	{
		if(!frm.elements.checkbox.length){
			frm.elements.oneRow.value="Y";
		}
    }
    if(validateSelectedCheckBoxes(frm,'checkbox',1,1)){
		showDialog({	
			msg		:	'<common:message bundle="liststockrequestresources" key="stockcontrol.defaults.liststockrequest.confirmcancel" scope="request"/>',
			type	:	4, 
			parentWindow: self,
			parentForm: targetFormName,
			dialogId:'id_1',
			onClose: function () {
						screenConfirmDialog(targetFormName,'id_1');
						screenNonConfirmDialog(targetFormName,'id_1');
					}
		});

    }
}

function closeScreen(){
	location.href = appPath + "/home.jsp";
	
} 


function screenConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){

	}
	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			frm.action = 'stockcontrol.defaults.cancelliststockrequest.do';
			frm.submit();
			disablePage();
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

function onClickDetails(){
   var isChecked = 'N';
   var refNo;
   var frm = targetFormName;
   if(frm.elements.checkbox){
	   if(!frm.elements.checkbox.length){
			if(frm.elements.checkbox.checked==false){
				showDialog({	
					msg		:	'<common:message bundle="liststockrequestresources" key="stockcontrol.defaults.liststockrequest.pleaseselectarow" scope="request"/>',
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
				});
				return;	
			}else{
				refNo = frm.elements.checkbox.value;
				isChecked = 'Y';
			}
		}else{
			for(var i=0;i<frm.elements.checkbox.length;i++){
				if(frm.elements.checkbox[i].checked==true ){
					if( i>0&& isChecked == 'Y'){
						showDialog({	
							msg		:	'<common:message bundle="liststockrequestresources" key="stockcontrol.defaults.liststockrequest.pleaseselectonlyonerow" scope="request"/>',
							type	:	1, 
							parentWindow:self,
							parentForm:targetFormName,
						});
						return;
					}
					isChecked = 'Y';
					refNo =  frm.elements.checkbox[i].value;
				}
			}
		}
	}
  	if(isChecked == 'Y'){
		frm.elements.fromStockRequestList.value="StockRequestList";
   		frm.action="stockcontrol.defaults.viewstockreq.do?reqRefNo="+refNo; //TODO CHANGE
   	}else{
   		showDialog({	
			msg		:	'<common:message bundle="liststockrequestresources" key="stockcontrol.defaults.liststockrequest.pleaseselectarow" scope="request"/>',
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
		return;
   	}
   	submitForm(frm,frm.action);
}

function onClickCreate(){
	
	var refNo = IC.util.dom.getElementById('reqRefNo').value;
	var frm = targetFormName;
    frm.elements.fromStockRequestList.value="StockRequestListCreate";
	frm.action="stockcontrol.defaults.fiterdetailsstockrequest.do?reqRefNo="+refNo;
	frm.submit();
    //submitForm(frm,"stockcontrol.defaults.fiterdetailsstockrequest.do?reqRefNo="+refNo);
}

function submitList(strLastPageNum,strDisplayPage){
	var frm = targetFormName;
	frm.elements.lastPageNumber.value= strLastPageNum;
	frm.elements.displayPage.value = strDisplayPage;
	submitForm(frm,"stockcontrol.defaults.liststockrequest.do");
}

function setFocus(){
	var frm = targetFormName;
	frm.elements.reqRefNo.focus();
	if(frm.elements.canCancel.value=="Y"){
		submitForm(frm,"stockcontrol.defaults.cancelliststockrequest.do");
	}
}

function displayLov(strAction){
	var frm = targetFormName;
	var stockHolderCode='code';
	var stockHolderType='stockHolderType';
	var val=frm.elements.code.value;
	var typeVal=frm.elements.stockHolderType.value;
	var strUrl = strAction+"?code="+val+"&codeName="+stockHolderCode+"&stockHolderTypeValue="+typeVal+"&typeName="+stockHolderType;
	var clientHeight = document.body.clientHeight;
	var clientWidth = document.body.clientWidth;
	var _reqWidth=(clientWidth*45)/100;
	var _reqHeight = (clientHeight*50)/100;
	openPopUp(strUrl,_reqWidth,_reqHeight);
}

/**
 *Method for excecuting after confirmation
 */
function confirmMessage(){
	 var frm = targetFormName;
     frm.elements.canCancel.value="Y";
     submitForm(frm,"stockcontrol.defaults.cancelliststockrequest.do");
}

function showPartnerAirlines(){	
	var partnerPrefix = targetFormName.elements.partnerPrefix.value;
	if(targetFormName.elements.partnerAirline.checked){
		jquery('select[name="awbPrefix"] option[value="'+partnerPrefix+'"]').remove();
		targetFormName.elements.awbPrefix.disabled=false;
	}else{
		targetFormName.elements.airlineName.value="";
		jquery('select[name="awbPrefix"]').append("<option value='" + partnerPrefix + "'> " + partnerPrefix + "</option>");
		jquery('select[name="awbPrefix"]').val(partnerPrefix);		
		targetFormName.elements.awbPrefix.disabled=true;
	}
}

function populateAirlineName(){		
	if(targetFormName.elements.awbPrefix.value!=""){
		var splits=targetFormName.elements.awbPrefix.value.split("-");
		targetFormName.elements.airlineName.value=splits[1];
	}
}
function onChangeOfDocTyp(){
	if(targetFormName.elements.docType.value=="INVOICE"){
		targetFormName.elements.partnerAirline.disabled=true;	
		targetFormName.elements.partnerAirline.checked=false;
		showPartnerAirlines();
	} else {
		targetFormName.elements.partnerAirline.disabled=false;		
		showPartnerAirlines();
	}
}