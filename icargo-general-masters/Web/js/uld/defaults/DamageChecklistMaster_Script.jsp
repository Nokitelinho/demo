<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{

	var frm = targetFormName;


		with(frm)
		{
			evtHandler.addIDEvents("addEvent","addEvent()",EVT_CLICK);
			evtHandler.addIDEvents("deleteEvent","deleteEvent()",EVT_CLICK);
			evtHandler.addEvents("btSave","saveEvent()",EVT_CLICK);
			evtHandler.addEvents("btClose","closeFun(this.frm)",EVT_CLICK);
			evtHandler.addEvents("btClose","resetFocus(this.form)",EVT_BLUR);
			evtHandler.addEvents("btList","listEvent()",EVT_CLICK);
			evtHandler.addEvents("btClear","clearEvent()",EVT_CLICK);
		}
 applySortOnTable("damagechecklistmaster",new Array("None","String","String","String")); 
		onScreenLoad(frm);
}
function resetFocus(frm){
	 if(!event.shiftKey){ 
				if(!frm.section.disabled){
					frm.section.focus();
				}
				else if(!document.getElementById('addEvent').disabled){
						document.getElementById('addEvent').focus();					 
				}				
	}	
}
function onScreenLoad(frm){
	//targetFormName.section.focus();
	if(targetFormName.elements.section.disabled == false){
			targetFormName.elements.section.focus();
	}
	else if(!document.getElementById('addEvent').disabled){
		document.getElementById('addEvent').focus();
	}
	if(targetFormName.elements.disableButtons.value!="N")
			{

				targetFormName.elements.btSave.disabled=true;


			}


	if(frm.statusFlag.value == "screenload_success"){

			//targetFormName.section.focus();
				var addEvent = document.getElementById('addEvent');
				addEvent.disabled=true;
				addEvent.onclick = function() { return false; }
				var deleteEvent = document.getElementById('deleteEvent');
				deleteEvent.disabled=true;
				deleteEvent.onclick = function() { return false; }

	}

}
function addEvent()
{
var tableSectionValues =document.getElementsByName("tableSection");
var tableSectionLength=tableSectionValues.length;
//alert("tableSectionLength value is "+tableSectionLength);
//alert("section value from---------> "+targetFormName.section.value);
	addTemplateRow('uldTemplateRow','uldTableBody','hiddenOperationFlag');
	if(tableSectionLength==1){
	
	targetFormName.elements.tableSection[tableSectionLength-1].value=targetFormName.elements.section.value;
	}
	if(tableSectionLength>1){
	//alert("from lenght-- >1");
	if(targetFormName.elements.section.value!=""){
	//alert("targetFormName.tableSection[tableSectionLength-2].value"+targetFormName.tableSection[tableSectionLength-2].value);
	targetFormName.elements.tableSection[tableSectionLength-1].value=targetFormName.elements.tableSection[tableSectionLength-2].value;
	}
	else{
	
	targetFormName.elements.tableSection[tableSectionLength-1].value=targetFormName.elements.section.value;
	}
	}
	

}

function deleteEvent()
{

	deleteTableRow('selectedDamageList','hiddenOperationFlag');


}
function saveEvent()
{
			//var oprn=document.getElementsByName('hiddenOperationFlag');
			//var len=oprn.length-1;
				//for(var i=0;i<len;i++){
				//alert(i);
			//alert(oprn[i].value);


		//}

		var flag="N";
		var isDuplicate = false;

		var tableSection=document.getElementsByName('tableSection');
		var noOfPoints=document.getElementsByName('noOfPoints');
		var description=document.getElementsByName('description');
		var oprn=document.getElementsByName('hiddenOperationFlag');
		var len=oprn.length;


		for (var i = 0; i < tableSection.length; i++)
			{
				if(tableSection[i].value==null || tableSection[i].value=='')
					{

						if(oprn[i].value!='NOOP')
							{
								showDialog({msg:'Section Cannot be null', type:1, parentWindow:self});
								flag="Y";
								return;
							}

					 }


			}


		for (var i = 0; i < description.length; i++)
			{
				if(description[i].value==null || description[i].value=='')
					{

						if(oprn[i].value!='NOOP')
							{
								showDialog({msg:'Description Cannot be null', type:1, parentWindow:self});
								flag="Y";
								return;
							}

					 }


			 }


		for (var i = 0; i < noOfPoints.length; i++)
			{
				if(noOfPoints[i].value==null || noOfPoints[i].value=='')
					{

						if(oprn[i].value!='NOOP')
							{
								showDialog({msg:'No:of Points Cannot be null', type:1, parentWindow:self});
								flag="Y";
								return;
							}

					 }


			 }


		if(flag !='Y'){
			for(var outerIndex=0;outerIndex<len-1;outerIndex++){


				if(targetFormName.elements.hiddenOperationFlag[outerIndex].value == "NA" || targetFormName.elements.hiddenOperationFlag[outerIndex].value == "I"
				||targetFormName.elements.hiddenOperationFlag[outerIndex].value == "U"){

					for(var innerIndex=outerIndex+1;innerIndex<len;innerIndex++){


						if( targetFormName.elements.hiddenOperationFlag[innerIndex].value == "I"
									|| targetFormName.elements.hiddenOperationFlag[innerIndex].value == 'NA'||targetFormName.elements.hiddenOperationFlag[innerIndex].value == "U"
									){




							if((targetFormName.elements.tableSection[outerIndex].value == targetFormName.elements.tableSection[innerIndex].value)&&
							(targetFormName.elements.description[outerIndex].value == targetFormName.elements.description[innerIndex].value)){
									isDuplicate = true;
									break;
							}
						}
					}

					}
				}
				if(isDuplicate == true){
					showDialog({msg:"Duplicate entries present in the table",type:1,parentWindow:self});
				}

				else{
					submitForm(targetFormName, 'uld.defaults.savedetails.do');
					}



		}
		else
		{
				//alert("not calling save");
		}
		flag='N';
}

function closeFun(frm)
{


		location.href = appPath+"/home.jsp";


}
function listEvent()
{
			targetFormName.elements.disableButtons.value="N";
			targetFormName.elements.disableButtons.defaultValue="N";
			submitForm(targetFormName, 'uld.defaults.displaydetails.do');

}
function clearEvent()
{
			targetFormName.elements.disableButtons.value="Y";
			targetFormName.elements.disableButtons.defaultValue="Y";
			//submitForm(targetFormName, 'uld.defaults.cleardetails.do');
			submitFormWithUnsaveCheck('uld.defaults.cleardetails.do');

}