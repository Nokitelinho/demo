<%@ include file="/jsp/includes/js_contenttype.jsp" %>



function screenSpecificEventRegister(){
	var frm=targetFormName;
	with(frm){
   		onScreenLoad();
		evtHandler.addEvents("btnClose","windowClose()",EVT_CLICK);
		evtHandler.addEvents("btnOk","saveSegments()",EVT_CLICK);
		evtHandler.addEvents("originlov","viewOriginLov('originlov',this)",EVT_CLICK);
		evtHandler.addEvents("destinationlov","viewDestinationLov('destinationlov',this)",EVT_CLICK);

		if(targetFormName.errorFlag.value=="Y"){
		window.close();
		}
	}
 }
 function onScreenLoad(){


}
function windowClose(){

	close();
	}

function addDetails(){

	updateOperationFlags();
	addTemplateRow('SegmentExceptionsTemplateRow','ULDSegmentExceptionsTableBody','hiddenOperationFlag');

}

function deleteDetails(){
	/*var frm = targetFormName;
	if(validateSelectedCheckBoxes(targetFormName,'selectedRows',1000000,1)){
		showDialog('Do you want to delete the selected rows?', 4, self, frm, 'id_1');
		screenConfirmDialog(frm,'id_1');
		screenNonConfirmDialog(frm,'id_1');
			}*/
		deleteTableRow('selectedRows','hiddenOperationFlag');

		}
function saveSegments(){

updateOperationFlags();
	var opFlag = document.getElementsByName('hiddenOperationFlag');



	if(validateFormFields(targetFormName.elements.origin)){

			showDialog({msg:'Origin cannot be blank', type:1, parentWindow:self, parentForm:targetFormName, dialogId:'id_1'});
			return;
		}

		if(validateFormFields(targetFormName.elements.destination)){
				showDialog({msg:'Destination cannot be blank',type:1, parentWindow:self, parentForm:targetFormName, dialogId:'id_2'});
				return;
			}
	if(validatePoolAirline(targetFormName.elements.origin)){
		showDialog({msg:'Origin cannot be same as Destination',type:1, parentWindow:self, parentForm:targetFormName, dialogId:'id_4'});
		return;
		}
	var origin = document.getElementsByName('origin');
	var destination= document.getElementsByName('destination');
	var isDuplicate = false;
	var len=opFlag .length;
	for(var outerIndex=0;outerIndex<len-1;outerIndex++){
			if(targetFormName.elements.hiddenOperationFlag[outerIndex].value == "NA" || targetFormName.hiddenOperationFlag[outerIndex].value == "I"
				||targetFormName.elements.hiddenOperationFlag[outerIndex].value == "U"){
					for(var innerIndex=outerIndex+1;innerIndex<len;innerIndex++){

						if( targetFormName.elements.hiddenOperationFlag[innerIndex].value == "I"
									|| targetFormName.elements.hiddenOperationFlag[innerIndex].value == 'NA'||targetFormName.hiddenOperationFlag[innerIndex].value == "U"
									){

							if((targetFormName.elements.origin[outerIndex].value.toUpperCase() == targetFormName.origin[innerIndex].value.toUpperCase())&&
							(targetFormName.elements.destination[outerIndex].value.toUpperCase() == targetFormName.destination[innerIndex].value.toUpperCase())){

									isDuplicate = true;
									break;
							}
						}
					}

					}
				}
				if(isDuplicate == true){
					showDialog({msg:'Duplicate entries present in the table',type:1,parentWindow:self});
				}

	else{
	submitForm(targetFormName,"uld.defaults.savesegmentexceptions.do");
	}
	}
function viewOriginLov(name,lov){
var selected=lov.id;
var index=selected.split(name)[1];
displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.origin.value,'Origin','1','origin','',index);
}
function viewDestinationLov(name,lov){
var selected=lov.id;
var index=selected.split(name)[1];
displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.destination.value,'Destination','1','destination','',index);
}


function validateFormFields(code){

var code = eval(code);
var opFlag = document.getElementsByName('hiddenOperationFlag');


if(code != null){

	if(code.length>1){
	for(var i=0;i<code.length;i++){
		if((code[i].value==""||code[i].value.trim().length==0)&& (opFlag [i].value != 'NOOP'&&opFlag [i].value!='D')){
			return true;
			}
		}
	}else{

		if(code.value==""||code.value.trim().length==0){
			return true;
		}
	}
	}
	return false;
}

function validateFormFields(code){
var code = eval(code);
var opFlag = document.getElementsByName('hiddenOperationFlag');
if(code != null){
	if(code.length>1){
	for(var i=0;i<code.length;i++){
		if((code[i].value==""||code[i].value.trim().length==0)&& (opFlag [i].value != 'NOOP'&&opFlag [i].value!='D')){
			return true;
			}
		}
	}else{

		if(code.value==""||code.value.trim().length==0){
			return true;
		}
	}
	}
	return false;
}

function validatePoolAirline(code){
var destination=targetFormName.elements.destination;
var opFlag = document.getElementsByName('hiddenOperationFlag');
var code=eval(code);
if(code!=null){
	if(code.length>1){
	 for(var i=0;i<code.length;i++){
		if((opFlag[i].value != 'NOOP'&&opFlag[i].value!='D')){
				if(code[i].value==destination[i].value){
				return true;
			}
		 }
		}
	}else{
		if(code.value==destination.value){
		return true;
		}
	}
}
return false;
}

function updateOperationFlags(){
	var frm = targetFormName;

	var opFlag = document.getElementsByName('hiddenOperationFlag');
	var origin = document.getElementsByName('origin');
	var destination= document.getElementsByName('destination');


	if(opFlag != null && opFlag.length >1){
	for(var i=0;i<opFlag.length;i++){
		if(opFlag[i].value == 'NA'){

			if(isElementModified(origin[i])
				|| isElementModified(destination[i]))
				{
				 opFlag[i].value = 'U';
				}
		}
	  }
	}

}