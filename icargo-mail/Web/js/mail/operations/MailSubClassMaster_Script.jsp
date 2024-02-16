<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister()
{
   var frm=targetFormName;

   onScreenLoad(frm);
   with(frm){

   	//CLICK Events
     	evtHandler.addEvents("btList","listDetails(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btClear","clearDetails(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btSave","saveDetails(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btClose","closeScreen()",EVT_CLICK);
		evtHandler.addEvents("btClose","resetFocus(this.form)",EVT_BLUR);

     	evtHandler.addEvents("checkAll","updateHeaderCheckBox(this.form,targetFormName.elements.checkAll,targetFormName.elements.rowId)",EVT_CLICK);

     	if(frm.elements.rowId != null){
			evtHandler.addEvents("rowId","toggleTableHeaderCheckbox('rowId',targetFormName.elements.checkAll)",EVT_CLICK);
		}

		evtHandler.addEvents("addLink","addSubClass()",EVT_CLICK);
		evtHandler.addEvents("deleteLink","deleteSubClass()",EVT_CLICK);

		evtHandler.addIDEvents("subClassFilterLOV","displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.elements.subClassFilter.value,'Subclass','1','subClassFilter','',0)", EVT_CLICK);
     
     //BLUR Events
        
        evtHandler.addEvents("description","operationFlagChangeOnEdit(this,'operationFlag');",EVT_BLUR);
        evtHandler.addEvents("subClassGroup","operationFlagChangeOnEdit(this,'operationFlag');",EVT_BLUR);
     
     
	  	}
}

function onScreenLoad(frm){
	if((!frm.elements.subClassFilter.disabled) && (!frm.elements.subClassFilter.readOnly)){
		frm.elements.subClassFilter.focus();
	}
}
	
function resetFocus(frm){
	if(!event.shiftKey){ 
		if((!frm.elements.subClassFilter.disabled) && (!frm.elements.subClassFilter.readOnly)){
			frm.elements.subClassFilter.focus();
		}	
	}
}

function listDetails(frm){

	submitForm(frm,"mailtracking.defaults.subclassmaster.list.do");
}

function saveDetails(frm){

	//updateOperationFlag(frm);
    
      var rowId =document.getElementsByName("rowId");
      
      if(rowId.length == 1){
             showDialog({msg:"No Subclass to save", type:1,parentWindow: self});
             return;
      } 

      	
      
      	if (rowId != null && rowId.length > 1) {
	   for (var i = 0; i < rowId.length; i++) {	  
		if(frm.elements.code[i].value != null && frm.elements.code[i].value.length > 0 && frm.elements.code[i].value.length != 2){
			showDialog({msg:"Subclass length should be 2",type: 1, parentWindow:self});
			return;
		}
	   }	 				
	}
	 

	submitForm(frm,"mailtracking.defaults.subclassmaster.save.do");
	
}

function clearDetails(frm){

	submitForm(frm,"mailtracking.defaults.subclassmaster.clear.do");
}
function closeScreen(){

location.href = appPath + "/home.jsp";
}
function addSubClass(){

	frm = targetFormName;

	//updateOperationFlag(frm);

	//submitForm(frm,"mailtracking.defaults.subclassmaster.add.do");
	
	addTemplateRow('subclassTemplateRow','subclassTableBody','operationFlag');
}

function deleteSubClass(){

	frm = targetFormName;
	if(validateSelectedCheckBoxes(frm,'rowId',1000000000,1)){
		//updateOperationFlag(frm);
		//submitForm(frm,"mailtracking.defaults.subclassmaster.delete.do");
		
		deleteTableRow('rowId','operationFlag');
	}
}

function updateOperationFlag(frm){

	var operationFlag = eval(frm.elements.operationFlag);   //for operation flag

 	var rowId = eval(frm.elements.rowId);

	var description = eval(frm.elements.description);
	var classGp = eval(frm.elements.subClassGroup);

	if (rowId != null) {

			if (rowId.length > 1) {

			for (var i = 0; i < rowId.length; i++) {

	 				var checkBoxValue = rowId[i].value;

					if((operationFlag[checkBoxValue].value !='D')&&
	 										(operationFlag[checkBoxValue].value !='U')) {


	 					if ((description[checkBoxValue].value != description[checkBoxValue].defaultValue) ||
	 						(classGp[checkBoxValue].value != classGp[checkBoxValue].defaultValue))	{

                                if(operationFlag[checkBoxValue].value !='I'){
	 								frm.elements.operationFlag[checkBoxValue].value='U';
								}
	 					}


	 				}


				}

	 		}else {

	 			if(operationFlag.value !='D'){

	 				if ((description.value !=description.defaultValue)||
	 					(classGp.value != classGp.defaultValue)){

							if(operationFlag.value !='I'){
	 							frm.elements.operationFlag.value = 'U';
							}
	 				}//ends if
	 			}//ends if

	 		}//ends else
	 	}//ends (if rowId!=null)



}//ends the function