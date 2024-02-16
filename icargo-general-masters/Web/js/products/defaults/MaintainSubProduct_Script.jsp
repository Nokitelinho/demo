<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister(){
	var frm=targetFormName;
	callOnLoad();
	with(frm){
		evtHandler.addEvents("btnSave","submitAction(this.form,'save')",EVT_CLICK);
		//evtHandler.addEvents("btnClose","location.href('home.jsp')",EVT_CLICK);
		evtHandler.addEvents("btnClose","submitAction(this.form,'close')",EVT_CLICK);
		evtHandler.addEvents("handlingInfo","validateTextArea('hdngInfo')",EVT_BLUR);
		evtHandler.addEvents("remarks","validateTextArea('remark')",EVT_BLUR);
		evtHandler.addEvents("addRestriction","validateTextArea('addRestn')",EVT_BLUR);

		evtHandler.addEvents("minWeight","validateFields(this.form.minWeight,-1,'Minimum Weight',2,true,true)",EVT_BLUR);
		evtHandler.addEvents("maxWeight","validateFields(this.form.maxWeight,-1,'Maximum Weight',2,true,true)",EVT_BLUR);
		evtHandler.addEvents("minVolume","validateFields(this.form.minVolume,-1,'Minimum Volume',2,true,true)",EVT_BLUR);
		evtHandler.addEvents("maxVolume","validateFields(this.form.maxVolume,-1,'Maximum Volume',2,true,true)",EVT_BLUR);

		if(document.forms[1].elements.mileStoneRowId){
			evtHandler.addEvents("alertTime","validateFields(this,-1,'Alert Time',2,true,true)",EVT_BLUR);
			evtHandler.addEvents("chaserTime","validateFields(this,-1,'Chaser Time',2,true,true)",EVT_BLUR);
			evtHandler.addEvents("chaserFrequency","validateFields(this,-1,'Chaser Frequency',3,true,true)",EVT_BLUR);
			evtHandler.addEvents("maxNoOfChasers","validateFields(this,-1,'Maximum Number of Chasers',3,true,true)",EVT_BLUR);
		}

	}
}

function screenSpecificTabSetup(){
	setupPanes('container1','tab1');
	displayTabPane('container1','tab1');
}

function callOnLoad(){
	var frm = targetFormName;
	if(frm.elements.saveSuccessful.value=='Y'){
		//showDialog('<common:message bundle="MaintainSubProduct"	key="products.defaults.subprdsavesuccess" scope="request"/>',1,self);
		//location.href('home.jsp');
	//	frm.action="products.defaults.maintainsubproduct.closeaction.do";
	//		frm.submit();
	}
}

function validateTextArea(strAction){
	 if(strAction=="hdngInfo"){
		return validateMaxLength(document.forms[1].elements.handlingInfo,100)
	}else if(strAction=="remark"){
		return validateMaxLength(document.forms[1].elements.remarks,500)
	}else if(strAction=="addRestn"){
		return validateMaxLength(document.forms[1].elements.addRestriction,500)
	}
}


function displayLov(strAction,nextAction,parentSession){

			var strUrl = strAction+"?nextAction="+nextAction+"&parentSession="+parentSession;
			//var myWindow = window.open(strUrl, "LOV", 'scrollbars,width=440,height=340,screenX=100,screenY=30,left=250,top=100')
				var myWindow = openPopUp(strUrl,'440','340') ;//Added by A-5219 for ICRD-41909
	}
	function deleteTableRow(strAction,checkBoxName){


			if(validateIsChecked(checkBoxName)){

				document.forms[1].action=strAction;
				document.forms[1].submit();
				disablePage();
			}

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
		 	if(cnt==0)
		 	{
				showDialog({	
						msg		:	"<common:message bundle="MaintainSubProduct" key="products.defaults.selectarow" scope="request "/>",
						type	:	1, 
						parentWindow:self,
						parentForm:document.forms[1]
				});
		 		//alert('Please select a row!');
		 		return false;
		 	}
		 	return true;
	}
	function isValid(frm){
	        if(!segmentValidation()){
				return false;
			}else if(!validateDuplicateSegmentOriginDestination()){
			return false;
			}else if(!validMilestone()){
			return false;
			}else if(!checkExternalInternal()){
			return false;
			}

			return true;

	}

	function validMilestone(){
			var minTime = document.getElementsByName('minTime');
			var maxTime = document.getElementsByName('maxTime');
			if(minTime){
				if(minTime.length){
					for(var i=0;i<minTime.length;i++){
						if(minTime[i].value=='' && maxTime[i].value==''){
						//showDialog('<common:message bundle="MaintainSubProduct"
						//	key="products.defaults.enterminormaxtim" scope="request"/>',1,self);
						showDialog({	
								msg		:	'<common:message bundle="MaintainSubProduct" key="products.defaults.enterminormaxtim" scope="request"/>',
								type	:	1, 
								parentWindow:self,
								parentForm:document.forms[1]
						});
						//	alert('Please enter either minimum time or maximum time');
							return false;
						}
					}
				}else{//only 1 Row
						if(minTime.value=='' && maxTime.value==''){
						//showDialog('<common:message bundle="MaintainSubProduct"
						//	key="products.defaults.enterminormaxtim" scope="request"/>',1,self);
						showDialog({	
								msg		:	'<common:message bundle="MaintainSubProduct" key="products.defaults.enterminormaxtim" scope="request"/>',
								type	:	1, 
								parentWindow:self,
								parentForm:document.forms[1]
						});
							//alert('Please enter either minimum time or maximum time');
							return false;
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
	 			//showDialog('<common:message bundle="MaintainSubProduct"
				//				key="products.defaults.internalorexternal" scope="request"/>',1,self);
				showDialog({	
						msg		:	'<common:message bundle="MaintainSubProduct" key="products.defaults.internalorexternal" scope="request"/>',
						type	:	1, 
						parentWindow:self,
						parentForm:document.forms[1]
				});
		 		//alert('Please specify either internal or external');
		 		return false;
		 	}
		}else{
			if(!isExternal[i].checked && !isInternal[i].checked){
	 			//showDialog('<common:message bundle="MaintainSubProduct"
				//				key="products.defaults.internalorexternal" scope="request"/>',1,self);
				showDialog({	
						msg		:	'<common:message bundle="MaintainSubProduct" key="products.defaults.internalorexternal" scope="request"/>',
						type	:	1, 
						parentWindow:self,
						parentForm:document.forms[1]
				});
				//alert('Please specify either internal or external');
				return false;
		 	}
		}
		}
	}

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
	 					//showDialog('<common:message bundle="MaintainSubProduct"
						//		key="products.defaults.segallowvilolorgrst" scope="request"/>',1,self);
						showDialog({	
								msg		:	'<common:message bundle="MaintainSubProduct" key="products.defaults.segallowvilolorgrst" scope="request"/>',
								type	:	1, 
								parentWindow:self,
								parentForm:document.forms[1]
						});
							//alert('The segment(s) allowed violates the origin(s) restricted');
							return false
						}
						}
					}
					}//End of for(j)
					}else{
					for(i=0;i<segments.length;i++){
					if(segmentOperationFlag[i].value != "D"){
					if(origins.value==segments[i].value.substring(4)){
	 					//showDialog('<common:message bundle="MaintainSubProduct"
						//		key="products.defaults.segvioldstallow" scope="request"/>',1,self);
						showDialog({	
								msg		:	'<common:message bundle="MaintainSubProduct" key="products.defaults.segvioldstallow" scope="request"/>',
								type	:	1, 
								parentWindow:self,
								parentForm:document.forms[1]
						});
						//	alert('The segment(s) restricted violates the destination(s) allowed');
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
	 						//showDialog('<common:message bundle="MaintainSubProduct"
							//	key="products.defaults.segallowviloldstrst" scope="request"/>',1,self);
							showDialog({	
									msg		:	'<common:message bundle="MaintainSubProduct" key="products.defaults.segallowviloldstrst" scope="request"/>',
									type	:	1, 
									parentWindow:self,
									parentForm:document.forms[1]
							});
								//alert('The segment(s) allowed violates the destination(s) restricted');
								return false
							}
							}
						 }
						}//End of for(j)
					}else{
						for(i=0;i<segments.length;i++){
						if(segmentOperationFlag[i].value != "D"){
						if(destn.value==segments[i].value.substring(4)){
	 						//showDialog('<common:message bundle="MaintainSubProduct"
							//	key="products.defaults.segallowviloldstrst" scope="request"/>',1,self);
							showDialog({	
									msg		:	'<common:message bundle="MaintainSubProduct" key="products.defaults.segallowviloldstrst" scope="request"/>',
									type	:	1, 
									parentWindow:self,
									parentForm:document.forms[1]
							});
							//	alert('The segment(s) allowed violates the destination(s) restricted');
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
	 						//showDialog('<common:message bundle="MaintainSubProduct"
							//	key="products.defaults.segallowvilolorgrst" scope="request"/>',1,self);
							showDialog({	
									msg		:	'<common:message bundle="MaintainSubProduct" key="products.defaults.segallowvilolorgrst" scope="request"/>',
									type	:	1, 
									parentWindow:self,
									parentForm:document.forms[1]
							});
							//alert('The segment(s) allowed violates the origin(s) restricted');
							return false
						}
						}

					}//End of for(j)
					}else{
					if(segmentOperationFlag.value != "D"){
					if(origins.value==segments.value.substring(4)){
	 						//showDialog('<common:message bundle="MaintainSubProduct"
							//	key="products.defaults.segallowvilolorgrst" scope="request"/>',1,self);
							showDialog({	
									msg		:	'<common:message bundle="MaintainSubProduct" key="products.defaults.segallowvilolorgrst" scope="request"/>',
									type	:	1, 
									parentWindow:self,
									parentForm:document.forms[1]
							});
							//alert('The segment(s) restricted violates the destination(s) allowed');
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
	 						//showDialog('<common:message bundle="MaintainSubProduct"
							//	key="products.defaults.segallowviloldstrst" scope="request"/>',1,self);
							showDialog({	
									msg		:	'<common:message bundle="MaintainSubProduct" key="products.defaults.segallowviloldstrst" scope="request"/>',
									type	:	1, 
									parentWindow:self,
									parentForm:document.forms[1]
							});
								//alert('The segment(s) allowed violates the destination(s) restricted');
								return false
							}
							}

						}//End of for(j)
					}else{
					if(segmentOperationFlag.value != "D"){

						if(destn.value==segments.value.substring(4)){
	 						//showDialog('<common:message bundle="MaintainSubProduct"
							//	key="products.defaults.segallowviloldstrst" scope="request"/>',1,self);
							showDialog({	
									msg		:	'<common:message bundle="MaintainSubProduct" key="products.defaults.segallowviloldstrst" scope="request"/>',
									type	:	1, 
									parentWindow:self,
									parentForm:document.forms[1]
							});
							//	alert('The segment(s) allowed violates the destination(s) restricted');
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
				if(segments[i].value.substring(0,3) == segments[i].value.substring(4)){
	 						//showDialog('<common:message bundle="MaintainSubProduct"
							//	key="products.defaults.segwithsameod" scope="request"/>',1,self);
							showDialog({	
									msg		:	'<common:message bundle="MaintainSubProduct" key="products.defaults.segwithsameod" scope="request"/>',
									type	:	1, 
									parentWindow:self,
									parentForm:document.forms[1]
							});
					//alert('Segment cannot have same origin and destination');
					return false;
				}
			}
		}else{
			if(segments.value.substring(0,3) == segments.value.substring(4)){
	 						//showDialog('<common:message bundle="MaintainSubProduct"
							//	key="products.defaults.segwithsameod" scope="request"/>',1,self);
							showDialog({	
									msg		:	'<common:message bundle="MaintainSubProduct" key="products.defaults.segwithsameod" scope="request"/>',
									type	:	1, 
									parentWindow:self,
									parentForm:document.forms[1]
							});
			//alert('Segment cannot have same origin and destination');
			return false;
			}
		}
	}

	return true;
}

	function submitAction(frm,strAction){
		if(strAction=='save'){

							/*if(confirm('Do you want to save the data?')){
							    checkUpdation();
								frm.action="products.defaults.saveSubProductDetails.do";
								frm.submit();
								disablePage();
							}*/
							/*showDialog('<common:message bundle="MaintainSubProduct"
										key="products.defaults.confirm" scope="request"/>',
								4, self, document.forms[1], 'id_1');
							screenConfirmDialog(document.forms[1],'id_1');
							screenNonConfirmDialog(document.forms[1],'id_1');*/

							showDialog({	
								msg		:	'<common:message bundle="MaintainSubProduct" key="products.defaults.confirm" scope="request"/>',
								type	:	4, 
								parentWindow:self,
								parentForm:document.forms[1],
								dialogId:'id_1',
								onClose: function (result) {
										if(document.forms[1].elements.currentDialogOption.value == 'Y') {
											screenConfirmDialog(document.forms[1],'id_1');
										}else if(document.forms[1].elements.currentDialogOption.value == 'N') {
											screenNonConfirmDialog(document.forms[1],'id_1');
										}
									}
							});	



			}
			//added now
			if(strAction=='close'){

			frm.action="products.defaults.maintainsubproduct.closeaction.do";
			frm.submit();

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
function onLoadFunctions(){

	if(document.forms[1].elements.lovAction.value !=''){

	displayLov(document.forms[1].elements.lovAction.value,
  				document.forms[1].elements.nextAction.value,
				document.forms[1].elements.parentSession.value);

	}
}
function addMileStone(strAction,nextAction,parentSession){
	setHdOperationFlag();
	getHiddenData();

	/*document.forms[1].action=strAction;
	document.forms[1].submit();*/
	var strUrl = strAction+"?nextAction="+nextAction+"?parentSession="+parentSession;
	//var myWindow = window.open(strUrl, "LOV", 'scrollbars,width=440,height=340,screenX=100,screenY=30,left=250,top=100');
		var myWindow = openPopUp(strUrl,'440','340') ;//Added by A-5219 for ICRD-41909

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
							//document.forms[1].milestoneOpFlag.value = "U";
							document.forms[1].elements.isRowModified.value = "1";
						}
				}

			}

			}
		}
}



function setNewMilestoneOperationFlag(){


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
							//document.forms[1].milestoneOpFlag.value = "I";
							document.forms[1].elements.isRowModified.value = "1";
						}
				}

			}

			}
		}
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

function addRow(strAction,str){

		if(str=='segment'){
			document.forms[1].action=strAction;
			checkUpdation();
			document.forms[1].submit();
			disablePage();
			}
	}


function deleteTableRow(strAction,checkBoxName){
		if(validateIsChecked(checkBoxName)){
			setHdOperationFlag();
			getHiddenData();
			document.forms[1].action=strAction;
			document.forms[1].submit();
			disablePage();
		}
	}

	/*function getHiddenData(){
		var isExternal = document.getElementsByName('isExternal');
		var isInternal = document.getElementsByName('isInternal');
		var externalStr ="";
		var internalStr ="";

		for(var i=0; i<isExternal.length; i++){
			if(isExternal[i].checked){
				externalStr=externalStr.concat("-true");

			}else{
				externalStr=externalStr.concat("-false");

			}
		}


		document.forms[1].checkedExternal.value=externalStr;

		for(var i=0; i<isInternal.length; i++){
			if(isInternal[i].checked){
				internalStr=internalStr.concat("-true");

			}else{
				internalStr=internalStr.concat("-false");

			}
		}
		document.forms[1].checkedInternal.value=internalStr;

	}*/

	/*Function to be called before any action*/

	function checkUpdation(){
		setHdOperationFlag();
		setNewMilestoneOperationFlag();
		getHiddenData();
		setSegementRowOperationFlag();
}


function getHiddenData(){
	var flags = document.getElementsByName('milestoneOpFlag');
	var isExternal = document.getElementsByName('isExternal');
	var isInternal = document.getElementsByName('isInternal');
	var isTransit = document.getElementsByName('isTransit');
	//alert(document.forms[1].isExternal);
	var externalStr ="";
	var internalStr ="";
	var transitStr ="";

	if(document.forms[1].elements.isExternal){

	for(var i=0; i<flags.length; i++){
	//alert(isExternal.length);
		if (isExternal.length > 1) {
			if(isExternal[i].checked){
				//alert('checking external true');
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
	//alert(document.forms[1].checkedExternal.value);

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




function validateDuplicateSegmentOriginDestination(){
	var segments = document.forms[1].elements.segment;
	if(segments){
		if(segments.length){
			for(var i=0;i<segments.length;i++){
				if(segments[i].value.substring(0,3) == segments[i].value.substring(4)){
	 				//showDialog('<common:message bundle="MaintainSubProduct"
					//			key="products.defaults.segwithsameod" scope="request"/>',1,self);
					showDialog({	
							msg		:	'<common:message bundle="MaintainSubProduct" key="products.defaults.segwithsameod" scope="request"/>',
							type	:	1, 
							parentWindow:self,
							parentForm:document.forms[1]
					});
					//alert('Segment cannot have same origin and destination');
					return false;
				}
			}
		}else{
			if(segments.value.substring(0,3) == segments.value.substring(4)){
	 		//showDialog('<common:message bundle="MaintainSubProduct"
			//					key="products.defaults.segwithsameod" scope="request"/>',1,self);
			showDialog({	
					msg		:	'<common:message bundle="MaintainSubProduct" key="products.defaults.segwithsameod" scope="request"/>',
					type	:	1, 
					parentWindow:self,
					parentForm:document.forms[1]
			});
			//alert('Segment cannot have same origin and destination');
			return false;
			}
		}
	}

	return true;
}

function screenConfirmDialog(frm, dialogId) {

	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			 checkUpdation();
			frm.action="products.defaults.saveSubProductDetails.do";
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

/********************** Added for the new change in jsp***********************/

var _currDivId="";

function addDataFromLOV1(strAction,lovAction,nextAction,parentSession,divId){

	// to do asynSubmit to update any changed value in the milestone table
	//clearMileStoneTable();
	//alert(strAction);
	//targetFormName.action=strAction;
	//targetFormName.submit();
	document.forms[1].elements.nextAction.value=nextAction;
	document.forms[1].elements.lovAction.value=lovAction;
	document.forms[1].elements.parentSession.value=parentSession;
	checkUpdation();
	_currDivId=divId;
	recreateTableDetails(strAction);
	//alert('lovAction'+lovAction);
	//alert('nextAction'+nextAction);
	//salert('strAction'+strAction);
	displayLov(lovAction,
  				nextAction,
				parentSession);

}
function addRow1(strAction,str,divId){

		if(str=='segment'){
			//document.forms[1].action=strAction;
			checkUpdation();
			//document.forms[1].submit();
			_currDivId=divId
			recreateTableDetails(strAction);
			//disablePage();
			}
	}

function deleteTableRow1(strAction,checkBoxName,divId){
	if(validateIsChecked(checkBoxName)){
		checkUpdation();
		//document.forms[1].action=strAction;
		//document.forms[1].submit();
		_currDivId=divId;
		recreateTableDetails(strAction);
	}
}

function clearTable(){
	document.getElementById(_currDivId).innerHTML="";
}

function recreateTableDetails(strAction){
	asyncSubmit(targetFormName,strAction,"updateTableCode",null,null);

}


function recreateTableDetails1(strAction,divId){
	_currDivId=divId;
	asyncSubmit(targetFormName,strAction,"updateTableCode",null,null);

}

function updateTableCode(_tableInfo){
	//@author a-1944, framework changes	
	_str = _tableInfo.getTableData();
	document.getElementById(_currDivId).innerHTML=_str;
	
	/*_str=getActualData(_tableInfo);
	document.getElementById(_currDivId).innerHTML=_str;
	cleanupTmpTable();
	applyScrollTableStyles();
	reapplyEvents();*/

}

/*function getActualData(_tableInfo){
	document.getElementById("tmpSpan").innerHTML=_tableInfo;
	_frm=document.getElementById("tmpSpan").getElementsByTagName("table")[0];
	return _frm.outerHTML;

}

function cleanupTmpTable(){
	document.getElementById("tmpSpan").innerHTML="";
}*/