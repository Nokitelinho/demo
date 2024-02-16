<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>



function screenSpecificEventRegister() {
	onScreenLoad();	
	evtHandler.addIDEvents("btnCancel","cancelUpload()",EVT_CLICK);
	evtHandler.addIDEvents("btnOk","okAction('Ok', this.form)",EVT_CLICK);
	evtHandler.addIDEvents("btAdd","addDetail()",EVT_CLICK); 
	evtHandler.addIDEvents("btDelete","deleteDetail()",EVT_CLICK);
}


function onScreenLoad(){
	var statusFlag="";
	if(targetFormName.elements.statusFlag){
		statusFlag = targetFormName.elements.statusFlag.value;
	}
	if(statusFlag == 'remove_success'){
		window.opener.IC.util.common.childUnloadEventHandler();
		window.opener.submitForm(window.opener.targetFormName,"uld.defaults.ux.damageImageUpload.close.do");
		//var imageIndex = targetFormName.elements.imageIndex.value;
		window.close();
		//openPopUp(appPath+'/uld.defaults.ux.damageImageUploadScreenLoad.do?imageIndex='+imageIndex,500,300);
	}
	if(statusFlag == 'ok_success'){
		window.opener.IC.util.common.childUnloadEventHandler();
		window.opener.submitForm(window.opener.targetFormName,"uld.defaults.ux.damageImageUpload.close.do");
		window.close();
	}
	//alert('onScreenLoad');
	
	//colorBox();
	
	jquery('body').on('click', '.attach-file',function(){
		jquery('.attach-file').colorbox({
                maxHeight: "90%",
                maxWidth: "50%",
				photo : true,
                loop: false,
				rel: "test"
            });
	});
}

function cancelUpload(){
	clearFileMapInSession();
	window.close();
}

function okAction(actionString, frm) {	
	var action = "uld.defaults.ux.damageImageUpload.ok.do";
	submitForm(frm, action);
}


function addDetail(){
	addTemplateRow('otherRow','addDetailBody','attachmentOperationFlag');
}

function deleteDetail(){
	deleteTableRow('check','attachmentOperationFlag');
}
function removeImage(frm,rIndex){
	var imgIndex = targetFormName.elements.imageIndex.value;
	var strAction = "uld.defaults.ux.maintaindamagereport.removeImage.do?dmgIndex="+imgIndex+"&imageIndex="+rIndex;
	// remove current row
	frm.closest('tr').style.display = 'none';
	asyncSubmit(targetFormName,strAction,"removeImageCallBack",null,null);
	//submitForm(frm, strAction);
}
function removeImageCallBack(){
	targetFormName.elements.statusFlag.value = 'remove_success';
	onScreenLoad();
}
function colorBox(){
			 jquery('.attach-file').colorbox({
                maxHeight: "90%",
                maxWidth: "50%",
				photo : true,
                loop: false,
				rel: "test"
            });

}

