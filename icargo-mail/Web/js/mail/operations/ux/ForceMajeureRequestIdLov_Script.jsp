<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	var frm=targetFormName;
	onScreenLoad(frm);
	with(frm){
		evtHandler.addIDEvents("btnListlov","submitAction('listDetails',this.form)",EVT_CLICK);
		evtHandler.addIDEvents("btnClose","window.close()",EVT_CLICK);
		evtHandler.addIDEvents("btnClearlov","submitAction('clearDetails',this.form)",EVT_CLICK);
		//evtHandler.addIDEvents("btnOk","setValues()",EVT_CLICK);
		evtHandler.addEvents("btnOk","setValues(targetFormName.elements.formCount.value,targetFormName.elements.lovTxtFieldName.value,targetFormName.elements.lovDescriptionTxtFieldName.value,targetFormName.elements.index.value)",EVT_CLICK);


		
	
}
}

function onScreenLoad(frm){
//IC.util.widget.createDataTable("lovForceTable",{tableId:"lovForceTable",hasChild:false,scrollingY:'38vh'});	
}

function submitPage(lastPageNum,displayPage){
	//var actionFlag =targetFormName.elements.actionFlag.value;	
	targetFormName.elements.lastPageNum.value=lastPageNum;
	targetFormName.elements.displayPage.value=displayPage;
	var action = "mail.operations.ux.forcemajeure.reqforcelov.do";     
	submitForm(targetFormName, action);	
}
function showEntriesReloading(obj){
	targetFormName.elements.defaultPageSize.value=obj.value;
	targetFormName.elements.displayPage.value="1";
	targetFormName.elements.lastPageNum.value="0";
	var action = "mail.operations.ux.forcemajeure.reqforcelov.do";     
	submitForm(targetFormName, action);
}

 function submitAction(actiontype,targetFormName){
	 var frm=targetFormName;
	 var type_action=actiontype;
	 
	if(type_action == 'listDetails') {
		frm.elements.displayPage.value="1";
		frm.elements.lastPageNum.value="0";
		if(frm.frmDate.value==""||frm.toDate.value==""){
			showDialog({msg:"Please enter mandatory details", type:1, parentWindow:self, parentForm:frm}); //Added by A-8164 for ICRD-316302
			return;
		}
		submitForm(frm,"mail.operations.ux.forcemajeure.reqforcelov.do?actionFlag="+type_action);
	}
	if(type_action == 'clearDetails'){
	type_action="CLEARLOV";
	frm.elements.actionFlag.value=type_action;
	submitForm(frm,"mail.operations.ux.forcemajeure.reqforcelov.do?actionFlag="+type_action);	
	}
	
	
}

function setValues(strFormCount,strLovTxtFieldName,strLovDescriptionTxtFieldName,index){
		
	var strCodeArray = "";
	if(strLovTxtFieldName != null && strLovTxtFieldName.length>0){
        if (strLovTxtFieldName === "filterValues")
            strCodeArray = window.opener.document.getElementsByName(strLovTxtFieldName);
        else {
		  strCodeArray=window.parent.document.getElementsByName(strLovTxtFieldName);
	}
	}
	var newValue = '';
	var frm = targetFormName;
	for(var i=0; i < frm.elements.length; i++){
		if(frm.elements[i].type=='checkbox'){
			if(frm.elements[i].name=='selectCheckBox'){
				if(frm.elements[i].checked == true){
					if(newValue==''){
						newValue = frm.elements[i].value;
			}else{
			showDialog({	
						msg: '<common:message bundle="CommonMessageResources"  key="framework.web.tblchklib.Amaximumofonly" scope="request"/>' + "one " + '<common:message bundle="CommonMessageResources"  key="framework.web.tblchklib.rowscanbeselected" scope="request"/>',
				type	:	1, 
						parentWindow: self
			});
			return;
					}
				}
			}
		}
	}
	var newValue1=newValue.replace(/~/g, "");
	if(index != null){
		for(var i=0; i<strCodeArray.length ; i++){
			if(i ==  index){
				if(strCodeArray[i] != null)
                    strCodeArray[i].value = newValue1;
			}
		}
	}else{
			if(strCodeArray != null){
            strCodeArray.value = newValue1;
			}				
	}
	window.close();
}


		
	
		
	
	
	





			 