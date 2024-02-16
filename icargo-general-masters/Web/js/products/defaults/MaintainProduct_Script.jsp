<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister(){
	var frm=targetFormName;
	with(frm){

		evtHandler.addEvents("btnDisplay","submitAction(this.form,'displayDetails')",EVT_CLICK);
		evtHandler.addEvents("btnClear","clearAction(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnClose","submitAction(this.form,'close')",EVT_CLICK);
		evtHandler.addEvents("btnSave","submitAction(this.form,'save')",EVT_CLICK);
		evtHandler.addEvents("detailDesc","validateTextArea('detdesc')",EVT_BLUR);
		evtHandler.addEvents("handlingInfo","validateTextArea('hdngInfo')",EVT_BLUR);
		evtHandler.addEvents("remarks","validateTextArea('remark')",EVT_BLUR);
		evtHandler.addEvents("addRestriction","validateTextArea('addRestn')",EVT_BLUR);

		evtHandler.addEvents("minWeight","validateFields(this.form.elements.minWeight,-1,'Minimum Weight',2,true,true)",EVT_BLUR);
		evtHandler.addEvents("maxWeight","validateFields(this.form.elements.maxWeight,-1,'Maximum Weight',2,true,true)",EVT_BLUR);
		evtHandler.addEvents("minVolume","validateFields(this.form.elements.minVolume,-1,'Minimum Volume',2,true,true)",EVT_BLUR);
		evtHandler.addEvents("maxVolume","validateFields(this.form.elements.maxVolume,-1,'Maximum Volume',2,true,true)",EVT_BLUR);
		
			evtHandler.addEvents("alertTime","validateFields(this,-1,'Alert Time',2,true,true)",EVT_BLUR);
			evtHandler.addEvents("chaserTime","validateFields(this,-1,'Chaser Time',2,true,true)",EVT_BLUR);
			evtHandler.addEvents("chaserFrequency","validateFields(this,-1,'Chaser Frequency',3,true,true)",EVT_BLUR);
			evtHandler.addEvents("maxNoOfChasers","validateFields(this,-1,'Maximum Number of Chasers',3,true,true)",EVT_BLUR);
		    evtHandler.addEvents("btnClose","setFocus()",EVT_BLUR);
		
		}
setProductCategory();
onScreenLoad();
		DivSetVisible(true);
		initialfocus();

		 if(targetFormName.elements.mode.value=="modify"){
				evtHandler.addEvents("btnListSubProduct","submitAction(this.form,'showSubProduct')",EVT_CLICK);
				if(frm.elements.btnListSubProduct!=null && frm.elements.btnListSubProduct.getAttribute("privileged") == 'Y'){
				targetFormName.elements.btnListSubProduct.disabled=false
				}
				//alert('In save' +targetFormName.btnListSubProduct.disabled);
		}else {
			targetFormName.elements.btnListSubProduct.disabled="true";
			//alert('disabling');
		}


}
function onScreenLoad(){
	if(targetFormName.elements.mode.value=="save"){
		//disableMultiButton(document.getElementById('CMP_PRODUCTS_DEFAULTS_MAINTAINPRD_CATEGORY'));
	}
}
function setProductCategory(){
	var objId = document.getElementById("CMP_PRODUCTS_DEFAULTS_MAINTAINPRD_CATEGORY");
	if(objId != null){
		
		var options = objId.options;	
		var length = options.length;	
		var selectedValues = "";
		for(var i=0; i<length; i++){
			if(options[i].selected){	
				if(selectedValues.length == 0){
					selectedValues = options[i].value;
				}else{
					selectedValues  = selectedValues+","+options[i].value;
				}
			}
		}
		targetFormName.elements.productCategory.value = selectedValues;		
	}

}
function screenSpecificTabSetup(){
    setupPanes('container1','bt1');
    displayTabPane('container1','bt1');
}

function initialfocus(){
	if(document.forms[1].elements.productName.disabled==false){
		document.forms[1].elements.productName.focus();
	}
}
function validateTextArea(strAction){
	if(strAction=="detdesc"){
		return validateMaxLength(document.forms[1].elements.detailDesc,500);
	}else if(strAction=="hdngInfo"){
		return validateMaxLength(document.forms[1].elements.handlingInfo,100)
	}else if(strAction=="remark"){
		return validateMaxLength(document.forms[1].elements.remarks,500)
	}else if(strAction=="addRestn"){
		return validateMaxLength(document.forms[1].elements.addRestriction,500)
	}
}


function displayLov1(strAction,nextAction,parentSession){
	var strUrl = strAction+"?nextAction="+nextAction+"&parentSession="+parentSession;
	openPopUpWithHeight(strUrl,"500");
}
//A-6843--as part of missing jsp addition-the following popups are not used anywhere now
/*function openWindow(action){
	if(action=="productSubProductMapping"){
		var strUrl = "products.defaults.showAffectedProducts.do";
		openPopUp(strUrl,550,430);
	}
	else if(action=="productlist"){
		var strUrl = "products.defaults.showProductList.do";
		openPopUp(strUrl,550,430);
	}
}*/


function submitAction(frm,strAction){


		if(strAction=='save'){

			for(var i=0;i<targetFormName.elements.segmentOperationFlag.length;i++) {

			}
				showDialog({	
					msg		:	"<common:message bundle="MaintainProduct" key="products.defaults.confirm" scope="request"/>",
					type	:	4, 
					parentWindow:self,
					parentForm:document.forms[1],
					dialogId:'id_1',
					onClose: function () {
						screenConfirmDialog(document.forms[1],'id_1');
						screenNonConfirmDialog(document.forms[1],'id_1');
					}
				});

		}else if(strAction=='displayDetails'){
			//checkUpdation();
			frm.action="products.defaults.findProducts.do";
			frm.submit();
			disablePage();
		}else if(strAction=='showSubProduct'){
			document.forms[1].elements.buttonStatusFlag.value="fromMaintainProduct";
			frm.action="products.defaults.screenloadlistsubproducts.do";
			frm.submit();
			disablePage();
		}

		//Added now
		if(strAction=='close'){

			frm.action="products.defaults.maintainproduct.closeaction.do";
			frm.submit();

		}

}

function clearAction(frm){
	frm.action="products.defaults.clearmaintainproduct.do";
	frm.submit();
	disablePage();
}


function addRow(strAction,str,divId){
		if(str=='segment'){
			checkUpdation();
			setSegementRowOperationFlag();
			//document.forms[1].action=strAction;
			//document.forms[1].submit();
			_currDivId=divId
			//recreateTableDetails(strAction);
			//disablePage();
			addTemplateRow('segmentTemplateRow','segmentBody','hiddenOpFlag');
			}
}



function addRowSegRestriction(){

			addTemplateRow('segmentTemplateRow','segmentBody','segmentOperationFlag');

}

function delRowSegRestriction(){
deleteTableRow('segmentRowId','segmentOperationFlag');
}



	function getMilestone(obj){
		if(obj=="tab2"){
			document.forms[1].action="getMilestone.do";
			document.forms[1].submit();
			disablePage();
		}
	}

	function isValid(frm){
		if(document.forms[1].elements.productDesc.value==""){
			showDialog({	
				msg		:	"<common:message bundle="MaintainProduct" key="products.defaults.invaliddescription" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});			
			return false;
		}else if(document.forms[1].elements.startDate.value==""){
			showDialog({	
				msg		:	"<common:message bundle="MaintainProduct" key="products.defaults.inavlidstartdate" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});	
			document.forms[1].elements.startDate.focus();
			return false;
		}else if(document.forms[1].elements.endDate.value==""){
			showDialog({	
				msg		:	"<common:message bundle="MaintainProduct" key="products.defaults.inavlidenddate" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			document.forms[1].elements.endDate.focus();
			return false;
		}else if(!validSelections()){
			return false;
		}else if(!segmentValidation()){
			return false;
		}else if(!validateDuplicateSegmentOriginDestination()){
			return false;
		}
		else if(!validMilestone()){
			return false;
		}else if(!checkExternalInternal()){
			return false;
		}

		return true;

	}

	function validMilestone(){

		var minTime = document.getElementsByName('minTime');
		var maxTime = document.getElementsByName('maxTime');
		var flags = document.getElementsByName('milestoneOpFlag');
		if(minTime){
		if(minTime.length){
			for(var i=0;i<minTime.length;i++){
				if(flags[i].value != "D"){
					if(minTime[i].value==''){
						showDialog({	
							msg		:	"<common:message bundle="MaintainProduct" key="products.defaults.validmintime" scope="request"/>",
							type	:	1, 
							parentWindow:self,
							parentForm:targetFormName,
						});
						return false;
					}else if(maxTime[i].value==''){
						showDialog({	
							msg		:	"<common:message bundle="MaintainProduct" key="products.defaults.validmaxtime" scope="request"/>",
							type	:	1, 
							parentWindow:self,
							parentForm:targetFormName,
						});
						return false;
					}
				}
			}
		}else{//only 1 Row
			if(flags.value != "D"){
			if(minTime.value==''){
				showDialog({	
					msg		:	"<common:message bundle="MaintainProduct" key="products.defaults.validmintime" scope="request"/>",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
				});
				return false;
			}else if(maxTime.value==''){
						showDialog({	
							msg		:	"<common:message bundle="MaintainProduct" key="products.defaults.validmaxtime" scope="request"/>",
							type	:	1, 
							parentWindow:self,
							parentForm:targetFormName,
						});
						return false;
					}
				}
			}
		}

		return true;
	}

	function validSelections(){

		var tmode = document.getElementsByName('transportMode');
		var priority = document.getElementsByName('priority');
		var scc = document.getElementsByName('sccCode');
		var services = document.getElementsByName('productServices');

		if(tmode==null || tmode.length==0){
			showDialog({	
				msg		:	"<common:message bundle="MaintainProduct" key="products.defaults.selecttramode" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			return false;
		}else if(priority==null ||priority.length==0){
			showDialog({	
				msg		:	"<common:message bundle="MaintainProduct" key="products.defaults.selectpriority" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			return false;
		}else if(scc==null ||scc.length==0){
			showDialog({	
				msg		:	"<common:message bundle="MaintainProduct" key="products.defaults.selectscc" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			return false;
		}else if(services==null ||services.length==0){
			showDialog({	
				msg		:	"<common:message bundle="MaintainProduct" key="products.defaults.selectservice" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			return false;
		}
		return true;

	}
	function isValidProductName(){

		if(document.forms[1].elements.productName.value==""){
			showDialog({	
				msg		:	"<common:message bundle="MaintainProduct" key="products.defaults.enterproductname" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			document.forms[1].elements.productName.focus();
			return false;
		}
			return true;

	}
	function validateIsChecked(checkBoxName){
	 	var cnt=0;
	 	for(var i=0;i<document.forms[1].elements.length;i++) {
	 		if(document.forms[1].elements[i].type=="checkbox") {
	 			if(document.forms[1].elements[i].name==checkBoxName) {
	 				if(document.forms[1].elements[i].checked)
	 				{
	 					cnt++;
	 				}
	 			}
	 		}
	 	}
	 	if(cnt==0){
			showDialog({	
				msg		:	"<common:message bundle="MaintainProduct" key="products.defaults.selectarow" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
	 		return false;
	 	}
	 	return true;
	}

function segmentValidation(){

		var segmentOperationFlag = document.forms[1].elements.segmentOperationFlag;
		var segments = document.forms[1].elements.segment;
		var origins = document.forms[1].elements.origin;
		var destn= document.forms[1].elements.destination;
		var segmentRestricted = document.forms[1].elements.segmentStatus[0].checked;
		var originRestricted = document.forms[1].elements.originStatus[0].checked;
		var destinationRestricted = document.forms[1].elements.destinationStatus[0].checked;
		if(segments){
		if(segments.length){
			
			if(!segmentRestricted){
				if(originRestricted){
				if(origins){
					if(origins.length){
					for(j=0;j<origins.length;j++){
					for(i=0;i<segments.length;i++){
						if(segmentOperationFlag[i].value != "D"){
						if(origins[j].value==segments[i].value.substring(0,3)){
							showDialog({	
								msg		:	"<common:message bundle="MaintainProduct"
											key="products.defaults.segallowvilolorgrst" scope="request"/>",
								type	:	1, 
								parentWindow:self,
								parentForm:targetFormName,
							});
							return false
						}
						}
					}
					}//End of for(j)
					}else{
					for(i=0;i<segments.length;i++){
					if(segmentOperationFlag[i].value != "D"){
						if(origins.value==segments[i].value.substring(4)){
							showDialog({	
								msg		:	"<common:message bundle="MaintainProduct" key="products.defaults.segvioldstallow" scope="request"/>",
								type	:	1, 
								parentWindow:self,
								parentForm:targetFormName,
							});
							return false
						}
						}
					 }

					}
				}
			}
			if(destinationRestricted){
			if(destn){
			   if(destn.length){
					  for(j=0;j<destn.length;j++){
						for(i=0;i<segments.length;i++){
						if(segmentOperationFlag[i].value != "D"){
							if(destn[j].value==segments[i].value.substring(4)){
								showDialog({	
									msg		:	"<common:message bundle="MaintainProduct"
												key="products.defaults.segallowviloldstrst" scope="request"/>",
									type	:	1, 
									parentWindow:self,
									parentForm:targetFormName,
								});
								return false
							}
							}
						 }
						}//End of for(j)
					}else{
						for(i=0;i<segments.length;i++){
						if(segmentOperationFlag[i].value != "D"){
							if(destn.value==segments[i].value.substring(4)){
								showDialog({	
									msg		:	"<common:message bundle="MaintainProduct"
												key="products.defaults.segallowviloldstrst" scope="request"/>",
									type	:	1, 
									parentWindow:self,
									parentForm:targetFormName,
								});
								return false
							}
							}
						 }

					}
				}
			}
		} //if !segment.length
		}
		else{
			
			if(!segmentRestricted){
				if(originRestricted){
				if(origins){
					if(origins.length){
					for(j=0;j<origins.length;j++){
					if(segmentOperationFlag.value != "D"){
						if(origins[j].value==segments.value.substring(0,3)){
							showDialog({	
								msg		:	"<common:message bundle="MaintainProduct"
											key="products.defaults.segallowvilolorgrst" scope="request"/>",
								type	:	1, 
								parentWindow:self,
								parentForm:targetFormName,
							});
							return false
						}
					}

					}//End of for(j)
					}else{
					if(segmentOperationFlag.value != "D"){
						if(origins.value==segments.value.substring(0,3)){
						    showDialog({	
								msg		:	"<common:message bundle="MaintainProduct"
											key="products.defaults.segallowvilolorgrst" scope="request"/>",
								type	:	1, 
								parentWindow:self,
								parentForm:targetFormName,
							});
							return false
						}
						}


					}
				}
			}
			if(destinationRestricted){
			if(destn){
			   if(destn.length){
					  for(j=0;j<destn.length;j++){
					  if(segmentOperationFlag.value != "D"){

						if(destn[j].value==segments.value.substring(4)){
							showDialog({	
								msg		:	"<common:message bundle="MaintainProduct"
											key="products.defaults.segallowviloldstrst" scope="request"/>",
								type	:	1, 
								parentWindow:self,
								parentForm:targetFormName,
							});
							return false
							}
							}

						}//End of for(j)
					}else{
					if(segmentOperationFlag.value != "D"){

						if(destn.value==segments.value.substring(4)){
								showDialog({	
									msg		:	"<common:message bundle="MaintainProduct"
												key="products.defaults.segallowviloldstrst" scope="request"/>",
									type	:	1, 
									parentWindow:self,
									parentForm:targetFormName,
								});
								return false
							}
							}


					}
				}
			}
		}


		}
		}

		return true;
}

function validateDuplicateSegmentOriginDestination(){
	var segments = document.forms[1].elements.segment;
	if(segments){
		if(segments.length){
			for(var i=0;i<segments.length;i++){
				if(segments[i].value!=""){
					if(segments[i].value.substring(0,3) == segments[i].value.substring(4)){
						showDialog({	
							msg		:	"<common:message bundle="MaintainProduct" key="products.defaults.segwithsameod" scope="request"/>",
							type	:	1, 
							parentWindow:self,
							parentForm:targetFormName,
						});
						return false;
					}
				}
			}
		}else{
			if(segments.value!=""){
				if(segments.value.substring(0,3) == segments.value.substring(4)){
					showDialog({	
						msg		:	"<common:message bundle="MaintainProduct" key="products.defaults.segwithsameod" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,
					});
					return false;
				}
			}
		}
	}

	return true;
}

function checkExternalInternal(){
	var flags = document.getElementsByName('milestoneOpFlag');
	var isExternal = document.getElementsByName('isExternal');
	var isInternal = document.getElementsByName('isInternal');
	for(var i=0; i<flags.length; i++){
		if(flags[i].value!="D"){

	   if (isExternal != null) {
		if (isExternal.length > 1) {
		 	if(!isExternal[i].checked && !isInternal[i].checked){
		 		showDialog({	
					msg		:	"<common:message bundle="MaintainProduct" key="products.defaults.internalorexternal" scope="request"/>",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
				});
		 		return false;
		 	}
		}else{
			if(!isExternal[i].checked && !isInternal[i].checked){
				showDialog({	
					msg		:	"<common:message bundle="MaintainProduct" key="products.defaults.internalorexternal" scope="request"/>",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
				});
				return false;
		 	}
		}
		}
	}

	}

	return true;
}

function addDataFromLOV(strAction,lovAction,nextAction,parentSession){
	document.forms[1].elements.nextAction.value=nextAction;
	document.forms[1].elements.lovAction.value=lovAction;
	document.forms[1].elements.parentSession.value=parentSession;
	document.forms[1].action=strAction;
	checkUpdation();
	document.forms[1].submit();
	disablePage();
}



function addDataFromCommodityLOV(strAction,lovAction,nextAction,parentSession){
	if(checkSCCSelected()){
		document.forms[1].elements.nextAction.value=nextAction;
		document.forms[1].elements.lovAction.value=lovAction;
		document.forms[1].elements.parentSession.value=parentSession;
		document.forms[1].action=strAction;
		checkUpdation();
		document.forms[1].submit();
		disablePage();
	}

}

function checkSCCSelected(){
	var scc = document.getElementsByName('sccCode');
	if(scc==null ||scc.length==0){
		showDialog({	
			msg		:	"<common:message bundle="MaintainProduct" key="products.defaults.selectscc" scope="request"/>",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
		return false;
	}
	return true;

}

function addMileStone(strAction,nextAction,parentSession){
	setHdOperationFlag();
	getHiddenData();
	var strUrl = strAction+"?nextAction="+nextAction+"?parentSession="+parentSession;
	openPopUp(strUrl,440,340);
	//var myWindow = window.open(strUrl, "LOV", 'scrollbars,width=440,height=340,screenX=100,screenY=30,left=250,top=100');

}

function getHiddenData(){
	var flags = document.getElementsByName('milestoneOpFlag');
	var isExternal = document.getElementsByName('isExternal');
	var isInternal = document.getElementsByName('isInternal');
	var isTransit = document.getElementsByName('isTransit');

	var externalStr ="";
	var internalStr ="";
	var transitStr ="";

	if(document.forms[1].elements.isExternal){

	for(var i=0; i<flags.length; i++){

		if (isExternal.length > 1) {
			if(isExternal[i].checked){

				externalStr=externalStr.concat("-true");

			}else{
				externalStr=externalStr.concat("-false");

			}

	  }else{
			if(isExternal[i].checked){

				externalStr=externalStr.concat("-true");

			}else{

				externalStr=externalStr.concat("-false");

			}

	}

	}

  }
	document.forms[1].elements.checkedExternal.value=externalStr;

	if(document.forms[1].elements.isInternal){
		for(var i=0; i<flags.length; i++){
			if (isInternal.length > 1) {
				if(isInternal[i].checked){
					internalStr=internalStr.concat("-true");

				}else{
					internalStr=internalStr.concat("-false");

				}

		  }else{
				if(isInternal[i].checked){

					internalStr=internalStr.concat("-true");

				}else{

					internalStr=internalStr.concat("-false");

				}

		}

		}

	}

	document.forms[1].elements.checkedInternal.value=internalStr;

	if(document.forms[1].elements.isTransit){
			for(var i=0; i<flags.length; i++){
				if (isTransit.length > 1) {
					if(isTransit[i].checked){
						transitStr=transitStr.concat("-true");

					}else{
						transitStr=transitStr.concat("-false");

					}

			  }else{
					if(isTransit[i].checked){

						transitStr=transitStr.concat("-true");

					}else{

						transitStr=transitStr.concat("-false");

					}

			}

			}

		}

	document.forms[1].elements.checkedTransit.value=transitStr;



}

/*Function to be called before any action*/

function checkUpdation(){
	setHdOperationFlag();
	setNewMilestoneOperationFlag();
	getHiddenData();
	setSegementRowOperationFlag();
}

function setHdOperationFlag(){


	var flags = document.getElementsByName('milestoneOpFlag');
	var modifications = document.getElementsByName('isRowModified');
	var isExternal = document.getElementsByName('isExternal');
	var isInternal = document.getElementsByName('isInternal');
	var isTransit = document.getElementsByName('isTransit');
	var minTime = document.getElementsByName('minTime');
	var maxTime = document.getElementsByName('maxTime');
	var alertTime = document.getElementsByName('alertTime');
	var chaserTime = document.getElementsByName('chaserTime');
	var chaserFrequency = document.getElementsByName('chaserFrequency');
	var maxNoOfChasers = document.getElementsByName('maxNoOfChasers');

	for(var i=0; i<flags.length; i++){

			if(flags[i].value != "I" || flags[i].value != "D") {


			if (isExternal != null) {
				if (isExternal.length > 1) {

						if (
							(isExternal[i].checked != isExternal[i].defaultChecked) ||
							(isInternal[i].checked != isInternal[i].defaultChecked) ||
							(isTransit[i].checked != isTransit[i].defaultChecked) ||
							(minTime[i].value != minTime[i].defaultValue) ||
							(maxTime[i].value != maxTime[i].defaultValue) ||
							(alertTime[i].value != alertTime[i].defaultValue) ||
							(chaserTime[i].value != chaserTime[i].defaultValue) ||
							(chaserFrequency[i].value != chaserFrequency[i].defaultValue) ||
							(maxNoOfChasers[i].value != maxNoOfChasers[i].defaultValue)
							) {
								//flags[i].value = "U";
								modifications[i].value = "1";
						}

				} else {

					if (
						(isExternal[i].checked != isExternal[i].defaultChecked) ||
						(isInternal[i].checked != isInternal[i].defaultChecked) ||
						(isTransit[i].checked != isTransit[i].defaultChecked) ||
						(minTime[i].value != minTime[i].defaultValue) ||
						(maxTime[i].value != maxTime[i].defaultValue) ||
						(alertTime[i].value != alertTime[i].defaultValue) ||
						(chaserTime[i].value != chaserTime[i].defaultValue)||
						(chaserFrequency[i].value != chaserFrequency[i].defaultValue) ||
						(maxNoOfChasers[i].value != maxNoOfChasers[i].defaultValue)
						) {
							//document.forms[1].elements.milestoneOpFlag.value = "U";
							document.forms[1].elements.isRowModified.value = "1";
						}
				}

			}

			}
		}
}



function setNewMilestoneOperationFlag(){
	//alert('setNewMilestoneOperationFlag called');

	var flags = document.getElementsByName('milestoneOpFlag');
	var modifications = document.getElementsByName('isNewRowModified');
	var isExternal = document.getElementsByName('isExternal');
	var isInternal = document.getElementsByName('isInternal');
	var isTransit = document.getElementsByName('isTransit');
	var minTime = document.getElementsByName('minTime');
	var maxTime = document.getElementsByName('maxTime');
	var alertTime = document.getElementsByName('alertTime');
	var chaserTime = document.getElementsByName('chaserTime');
	var chaserFrequency = document.getElementsByName('chaserFrequency');
	var maxNoOfChasers = document.getElementsByName('maxNoOfChasers');

	for(var i=0; i<flags.length; i++){
		if(flags[i].value == "I") {

			if (isExternal != null) {
				if (isExternal.length > 1) {


						if (
							(isExternal[i].checked != isExternal[i].defaultChecked) ||
							(isInternal[i].checked != isInternal[i].defaultChecked) ||
							(isTransit[i].checked != isTransit[i].defaultChecked) ||
							(minTime[i].value != minTime[i].defaultValue) ||
							(maxTime[i].value != maxTime[i].defaultValue) ||
							(alertTime[i].value != alertTime[i].defaultValue) ||
							(chaserTime[i].value != chaserTime[i].defaultValue)||
							(chaserFrequency[i].value != chaserFrequency[i].defaultValue) ||
							(maxNoOfChasers[i].value != maxNoOfChasers[i].defaultValue)
							) {
								//flags[i].value = "I";
								modifications[i].value = "1";
						}

				} else {

					if (
						(isExternal[i].checked != isExternal[i].defaultChecked) ||
						(isInternal[i].checked != isInternal[i].defaultChecked) ||
						(isTransit[i].checked != isTransit[i].defaultChecked) ||
						(minTime[i].value != minTime[i].defaultValue) ||
						(maxTime[i].value != maxTime[i].defaultValue) ||
						(alertTime[i].value != alertTime[i].defaultValue) ||
						(chaserTime[i].value != chaserTime[i].defaultValue)||
						(chaserFrequency[i].value != chaserFrequency[i].defaultValue) ||
						(maxNoOfChasers[i].value != maxNoOfChasers[i].defaultValue)
						) {
							//document.forms[1].elements.milestoneOpFlag.value = "I";
							document.forms[1].elements.isRowModified.value = "1";
						}
				}

			}

			}
		}
}

function setSegementRowOperationFlag(){


	var flags = document.getElementsByName('segmentOperationFlag');
	var modifications = document.getElementsByName('isSegmentRowModified');
	var segment = document.getElementsByName('segment');

	for(var i=0; i<flags.length; i++){


			if(!((flags[i].value =='I') || (flags[i].value =='D'))) {

					if (segment[i].value != segment[i].defaultValue) {

							flags[i].value = "U";
							modifications[i].value = "1";
						}


			}
		}
}


function updateCheckAllCheckBox(){
	var tmode = document.getElementsByName('transportMode');
	var priority = document.getElementsByName('priority');
	var scc = document.getElementsByName('sccCode');
	var services = document.getElementsByName('productServices');
	var mileStoneRowId = document.getElementsByName('mileStoneRowId');
	var commodity = document.getElementsByName('commodity');
	var segment = document.getElementsByName('segment');
	var origin = document.getElementsByName('origin');
	var destination = document.getElementsByName('destination');
	var custGroup = document.getElementsByName('custGroup');

	if(tmode==null || tmode.length==0){
		document.forms[1].elements.checkAllTransportmode.checked=false;
	}else if(priority==null ||priority.length==0){
		document.forms[1].elements.checkAllPriority.checked=false;
	}else if(scc==null ||scc.length==0){
		document.forms[1].elements.checkAllScc.checked=false;
	}else if(services==null ||services.length==0){
		document.forms[1].elements.checkAllService.checked=false;
	}else if(mileStoneRowId==null){
		document.forms[1].elements.checkAllMilestone.checked=false;
	}else if(commodity==null || commodity.length==0 ){
		document.forms[1].elements.checkAllCommodity.checked=false;
	}else if(segment==null || segment.length==0 ){
		document.forms[1].elements.checkAllSegment.checked=false;
	}else if(origin==null || origin.length==0 ){
		document.forms[1].elements.checkAllOrigin.checked=false;
	}else if(destination==null || destination.length==0 ){
		document.forms[1].elements.checkAllDestn.checked=false;
	}else if(custGroup==null || custGroup.length==0 ){
		document.forms[1].elements.checkAllCustGroup.checked=false;
	}

}

function onLoadFunctions(){

	if(document.forms[1].elements.lovAction.value !=''){
	displayLov1(document.forms[1].elements.lovAction.value,
  				document.forms[1].elements.nextAction.value,
				document.forms[1].elements.parentSession.value);

	}

	updateCheckAllCheckBox();
}

function confirmMessage(){
	

}

function nonconfirmMessage() {
	var frm = targetFormName;
	frm.action="products.defaults.clearmaintainproduct.do";
	frm.submit();
	disablePage();

}

function screenConfirmDialog(frm, dialogId) {

	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			checkUpdation();
			frm.action="products.defaults.saveProductDetails.do";
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

function openIcon(){
	var strUrl = "products.defaults.selecticonforproduct.do";
	openPopUp(strUrl,600,600);
}

/********************** Added for the new change in jsp***********************/

var _currDivId="";

function addDataFromLOV1(strAction,lovAction,nextAction,parentSession,divId){

	document.forms[1].elements.nextAction.value=nextAction;
	document.forms[1].elements.lovAction.value=lovAction;
	document.forms[1].parentSession.value=parentSession;
	checkUpdation();
	_currDivId=divId;
	recreateTableDetails(strAction);
	displayLov1(lovAction,nextAction,parentSession);
}

function deleteTableRow1(strAction,checkBoxName,divId){



	if(validateIsChecked(checkBoxName)){

		checkUpdation();
		_currDivId=divId;

		recreateTableDetails(strAction);


	}
}

function deleteTableRow2(strAction,checkBoxName,divId){

if(validateIsChecked(checkBoxName)){

deleteTableRow('segmentRowId','segmentOperationFlag');

}
}




function deleteTableRow2(strAction,checkBoxName,divId){

	if(validateIsChecked(checkBoxName)){
		deleteTableRow('segmentRowId','segmentOperationFlag');
	}
}




function clearTable(){
	document.getElementById(_currDivId).innerHTML="";
}

function recreateTableDetails(strAction){


	asyncSubmit(targetFormName,strAction,"updateTableCode",null,null);

}


function updateTableCode(_tableInfo){

	//@author a-1944, framework changes
	_str = _tableInfo.getTableData();
	console.log(_str);
	document.getElementById(_currDivId).innerHTML=_str;



}

function setFocus(){
  if(!event.shiftKey){
     initialfocus();
  }
}

