<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister()
    {
	var frm=targetFormName;
	with(frm){

	evtHandler.addEvents("btnClear","clearScreen(this.form)",EVT_CLICK);
	evtHandler.addEvents("btnClose","closeScreen(this.form)",EVT_CLICK);
	evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);//added by T-1927 for ICRD-18408
	evtHandler.addEvents("btnList","listScreen(this.form)",EVT_CLICK);
	evtHandler.addEvents("btnSave","saveScreen(this.form)",EVT_CLICK);
	evtHandler.addEvents("flightCarrierCode","popOalValues(this)",EVT_BLUR);


	//For Links
	evtHandler.addIDEvents("addLink","addRoute(this.form)",EVT_CLICK);
	evtHandler.addIDEvents("deleteLink","deleteRoute(this.form)",EVT_CLICK);

       	//For CheckBox
	evtHandler.addEvents("checkBoxForFlight","toggleTableHeaderCheckbox('checkBoxForFlight', targetFormName.checkAll);",EVT_CLICK);
	evtHandler.addEvents("checkAll","updateHeaderCheckBox(this.form,this.form.checkAll,this.form.checkBoxForFlight)",EVT_CLICK);

	//For Lovs
	evtHandler.addEvents("airportlov","showLOV(targetFormName,this)",EVT_CLICK);
	evtHandler.addEvents("poulov","showPouLOV(targetFormName,this)",EVT_CLICK);
	evtHandler.addEvents("dsn","mailDSN()",EVT_BLUR);
	evtHandler.addIDEvents("dsnlov","displayLOV('mailtracking.mra.defaults.dsnlov.screenload.do','N','Y','mailtracking.mra.defaults.dsnlov.screenload.do',targetFormName.dsn.value,'DSN No','1','dsn',targetFormName.dsn.value,0)",EVT_CLICK);

	evtHandler.addEvents("blockSpaceType","validateBSA(this)",EVT_CHANGE);
	
	evtHandler.addIDEvents("transferPALov","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.transferPA.value,'GPA Code','1','transferPA','',0)", EVT_CLICK);
	evtHandler.addIDEvents("transferAirlineLov","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.transferAirline.value,'Airline Code','1','transferAirline','',0)",EVT_CLICK);
	
	
	}

	onScreenLoad(frm);
      }

function onScreenLoad(frm){
	frm=targetFormName;
	if(targetFormName.elements.dsn.value==""){
	//document.getElementById("addLink").disabled="true";
	disableLink(document.getElementById("addLink"));
	//document.getElementById("deleteLink").disabled="true";
	disableLink(document.getElementById("deleteLink"));

	// initial focus on page load
	if (targetFormName.elements.dsn.disabled == false) {
	    frm.elements.dsn.focus();
	}

}
//added by T-1927 for ICRD-18408
else{
	if (targetFormName.elements.dsn.disabled){
		document.getElementById("addLink").focus();
	}
}
	if(targetFormName.elements.showDsnPopUp.value=="TRUE"){
			targetFormName.elements.showDsnPopUp.value="FALSE"
			openPopUp("mailtracking.mra.defaults.despatchenquiry.popup.do",725,450);
    }

}

function validateBSA(obj){


var  _rowCount =null;
	try{
		_rowCount=obj.getAttribute("rowCount");		
	}catch(err){
		return;
	}
	
	
	
if(targetFormName.elements.blockSpaceType[_rowCount].value!=null && targetFormName.elements.blockSpaceType[_rowCount].value !=""){

globalObj=obj;

	var funct_to_overwrite = "validateBSAAjax";
	var selectedBlockSpaceType = targetFormName.elements.blockSpaceType[_rowCount].value;
	var selectedFlightCarrierCode = targetFormName.elements.flightCarrierCode[_rowCount].value;
	var selectedFlightNumber = targetFormName.elements.flightNumber[_rowCount].value;
	var selectedDepartureDate = targetFormName.elements.departureDate[_rowCount].value;
	var selectedPol = targetFormName.elements.pol[_rowCount].value;
	var selectedPou = targetFormName.elements.pou[_rowCount].value;
	
	var strAction = 'mailtracking.mra.defaults.despatchrouting.validatebsa.do?rowCount='+_rowCount+'&selectedBlockSpaceType='+selectedBlockSpaceType+'&selectedFlightCarrierCode='+selectedFlightCarrierCode+'&selectedFlightNumber='+selectedFlightNumber+'&selectedDepartureDate='+selectedDepartureDate+'&selectedPol='+selectedPol+'&selectedPou='+selectedPou;

  asyncSubmit(targetFormName,strAction,funct_to_overwrite,null,null);
}else if(targetFormName.elements.blockSpaceType.value !=null && targetFormName.elements.blockSpaceType.value !="" ){

globalObj=obj;

	var funct_to_overwrite = "validateBSAAjax";
		var selectedBlockSpaceType = targetFormName.elements.blockSpaceType.value;
	var selectedFlightCarrierCode = targetFormName.elements.flightCarrierCode.value;
	var selectedFlightNumber = targetFormName.elements.flightNumber.value;
	var selectedDepartureDate = targetFormName.elements.departureDate.value;
	var selectedPol = targetFormName.elements.pol.value;
	var selectedPou = targetFormName.elements.pou.value;
	
	var strAction = 'mailtracking.mra.defaults.despatchrouting.validatebsa.do?rowCount='+_rowCount+'&selectedBlockSpaceType='+selectedBlockSpaceType+'&selectedFlightCarrierCode='+selectedFlightCarrierCode+'&selectedFlightNumber='+selectedFlightNumber+'&selectedDepartureDate='+selectedDepartureDate+'&selectedPol='+selectedPol+'&selectedPou='+selectedPou;

  asyncSubmit(targetFormName,strAction,funct_to_overwrite,null,null);
  
  
}



}

function validateBSAAjax(_tableInfo){

 _innerFrm = _tableInfo.document.getElementsByTagName("form")[0];
 if('nobsaexist'==_innerFrm.elements.bsaValidationStatus.value){
 showDialog({msg:'<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.err.bsanotexist" />',type:1,parentWindow:self});
 }else if('inwardbilled'==_innerFrm.elements.bsaValidationStatus.value){
 showDialog({msg:'<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.err.inwardbilled" />',type:1,parentWindow:self});
 }
 else if('nobsaforowncarrier'==_innerFrm.elements.bsaValidationStatus.value){
 showDialog({msg:'<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.err.bsanotexistforowncarrier" />',type:1,parentWindow:self});
 }
 
if('validbsa'!=_innerFrm.elements.bsaValidationStatus.value){
var _rowCount = globalObj.getAttribute("rowCount");
targetFormName.elements.blockSpaceType[_rowCount].value="";
targetFormName.elements.blockSpaceType.value="";


}
}

/**
* For Lov
*/

function showLOV(frm,lovButton)
{
var index = lovButton.id.split("airportlov")[1];
displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.pol.value,'Airport Code','1','pol','',index);
}

function showPouLOV(frm,lovButton)
{
var index = lovButton.id.split("poulov")[1];
displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.pou.value,'Airport Code','1','pou','',index);


}
 /**
*Function for close
*/

function closeScreen(frm)
{
submitForm(frm,'mailtracking.mra.defaults.despatchrouting.closescreen.do');
}

//added by T-1927 for ICRD-18408
function resetFocus(){
	if(!event.shiftKey){
		if(targetFormName.elements.dsn.disabled == false&&targetFormName.elements.dsn.readOnly == false){
			targetFormName.elements.dsn.focus();
		}
		else{
				document.getElementById("addLink").focus();
		}
	}
}

function popOalValues(obj){
var nopieces = document.getElementsByName('nopieces');
var weight= document.getElementsByName('weight');
var ownccod = targetFormName.elements.owncarcode.value;
carCode= document.getElementsByName('flightCarrierCode');

var oalPcs = targetFormName.elements.oalPcs.value;
var oalwgt = targetFormName.elements.oalwgt.value;

 						if(carCode[obj.rowCount].value==""){
 		 				//showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.carcodnull" />',1,self);
						showDialog({msg:'<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.carcodnull" />',type:1,parentWindow:self});
 		 				carCode[obj.rowCount].focus();
 		 				return;
 				        }
	 					if(carCode[obj.rowCount].value.toUpperCase()!=ownccod.toUpperCase()){
								nopieces[obj.rowCount].value=oalPcs;
	 							//weight[obj.rowCount].value=oalwgt;
	 							//nopieces[obj.rowCount].disabled=true;
								disableField(nopieces[obj.rowCount]);
	 							//weight[obj.rowCount].disabled=true;
								disableField(weight[obj.rowCount]);
	 	   				 } else if(carCode[obj.rowCount].value.toUpperCase()==ownccod.toUpperCase()){
	 	   				 		//nopieces[obj.rowCount].disabled=false;
								enableField(nopieces[obj.rowCount]);
	 	   				 		//weight[obj.rowCount].disabled=false;
								enableField(weight[obj.rowCount]);
	 	   				 }


}






/**
*Function for clear
*/

function clearScreen(frm)
{
submitForm(frm,'mailtracking.mra.defaults.despatchrouting.clearscreen.do');
}




/**
*Function for list
*/
function listScreen(frm){


	submitForm(targetFormName,'mailtracking.mra.defaults.despatchrouting.listdsnpopup.do');

	//else{
	//openPopUp("mailtracking.mra.defaults.despatchenquiry.popup.do?code="+targetFormName.dsn.value+"&fromPage=despatchrouting",width=725,height=450);

	//}

}
/**
*Function for save
*/

function saveScreen(frm){

var opFlag= eval(targetFormName.elements.hiddenOpFlag);
var carCode= eval(targetFormName.elements.flightCarrierCode);
var fltNumber= eval(targetFormName.elements.flightNumber);
var departDate= eval(targetFormName.elements.departureDate);
var pol= eval(targetFormName.elements.pol);
var pou= eval(targetFormName.elements.pou);
var nopieces = eval(targetFormName.elements.nopieces);
var weight= eval(targetFormName.elements.weight);
var oalPcs = targetFormName.elements.oalPcs.value;
var oalwgt = targetFormName.elements.oalwgt.value;
var ownccod = targetFormName.elements.owncarcode.value;

var visrecd = -1;
var visorg = -1;
var visrec = 0;
var actvrec = new Array();

if(targetFormName.elements.transferPA.value!="" && targetFormName.elements.transferAirline.value!=""){
showDialog({msg:'<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.err.specifyonevalueonly" />',type:1,parentWindow:self});
return ;
}

 if(opFlag.length>1){
 	for (var i = 0; i <opFlag.length; i++) {
 	  if(opFlag[i].value!="NOOP"&&opFlag[i].value!="D"){

 	  visrecd=i;
 	  if(visorg==-1){
 	  visorg=i;
 	  }

 	   actvrec[visrec]=i;
 	   visrec=visrec+1;

 	  	if(carCode[i].value==""){
 		 				//showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.carcodnull" />',1,self);
						showDialog({msg:'<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.carcodnull" />',type:1,parentWindow:self});
 		 				carCode[i].focus();
 		 				return;
 				        }


  		 if(fltNumber[i].value==""){
 		 				//showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.fltnumbernull" />',1,self);
						showDialog({msg:'<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.fltnumbernull" />',type:1,parentWindow:self});
 		 				fltNumber[i].focus();
 		 				return;
 				        }

 		 if(departDate[i].value==""){
 		 				//showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.departdatenull" />',1,self);
						showDialog({msg:'<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.departdatenull" />',type:1,parentWindow:self});
 		 				departDate[i].focus();
 		 				return;
 				        }

 		if(pol[i].value==""){
 		 				//showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.polnull" />',1,self);
						showDialog({msg:'<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.polnull" />',type:1,parentWindow:self});
 		 				pol[i].focus();
 		 				return;
 				        }
 		if(pou[i].value==""){
 		 				//showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.pounull" />',1,self);
						showDialog({msg:'<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.pounull" />',type:1,parentWindow:self});
 		 				pou[i].focus();
 		 				return;
 				        }
 		if(pol[i].value.toUpperCase()==pou[i].value.toUpperCase()){
 				//showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.polpou" />',1,self);
				showDialog({msg:'<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.polpou" />',type:1,parentWindow:self});
 		 				pou[i].focus();
 		 				return;

 				}


		if(nopieces[i].value=="" || nopieces[i].value==0){
 				//showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.nopiecesnull" />',1,self);
				showDialog({msg:'<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.nopiecesnull" />',type:1,parentWindow:self});
 		 				nopieces[i].focus();
 		 				return;
 				}

		if(nopieces[i].value!= null && nopieces[i].value.indexOf(".") > 0){
				showDialog({msg:'Pieces should not have decimals',type:1,parentWindow:self});
 		 				nopieces[i].focus();
 		 				return;
 				}
 		if(weight[i].value=="" || weight[i].value==0){
 				//showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.weightnull" />',1,self);
				showDialog({msg:'<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.weightnull" />',type:1,parentWindow:self});
 		 				weight[i].focus();
 		 				return;
 				}



 		//check for routing order
	 		if(i>0){
		 		if(visrec>1){
		 						var cnt = 	visrec-1;
							 var owncarcod = targetFormName.owncarcode.value;
					 		 	if(pou[actvrec[cnt-1]].value.toUpperCase()!=pol[actvrec[cnt]].value.toUpperCase()){
					 		 		if(carCode[actvrec[cnt]].value.toUpperCase()==owncarcod.toUpperCase()){
						 					if(carCode[actvrec[cnt-1]].value.toUpperCase()==owncarcod.toUpperCase()){
									 				 if(pol[actvrec[cnt-1]].value.toUpperCase()!=pol[actvrec[cnt]].value.toUpperCase()){
									 						 		//showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.spiltpolmiss" />',1,self);
																	showDialog({msg:'<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.spiltpolmiss" />',type:1,parentWindow:self});
							 		 								pol[actvrec[cnt]].focus();
							 		 								return;
									 				 }else if(pou[actvrec[cnt-1]].value.toUpperCase()!=pou[actvrec[cnt]].value.toUpperCase()){
									 				 		//showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.spiltpoumiss" />',1,self);
															showDialog({msg:'<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.spiltpoumiss" />',type:1,parentWindow:self});
							 		 								pou[actvrec[cnt]].focus();
							 		 								return;
									 				 }

									 		}else{
									 				 //showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.routin" />',1,self);
													 showDialog({msg:'<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.routin" />',type:1,parentWindow:self});
							 		 					pol[actvrec[cnt]].focus();
							 		 					return;

									 				 }

						 				}else{
							 					//showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.routin" />',1,self);
												showDialog({msg:'<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.routin" />',type:1,parentWindow:self});
							 		 				pol[actvrec[cnt]].focus();
							 		 				return;
							 			}
							    	}
	 	      				}

	 	    	}







 			   }
 	 		}
 		}



 	   	//check for destn change
 	   	   /* if(visrecd!=-1){
		     var destn = targetFormName.destn.value;
			 	      if(pou[visrecd].value.toUpperCase()!=destn.toUpperCase()){
									showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.cannotchangeDestination" />',1,self);
						 		 				pou[visrecd].focus();
						 		 				return;
					 	}

					}
		//check for origin change
			if(visorg!=-1){
			 	     		  var orgn = targetFormName.origin.value;
			 	     		  if(pol[visorg].value.toUpperCase()!=orgn.toUpperCase()){
					 				showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.cannotchangeorigin" />',1,self);
						 		 				pol[visorg].focus();
						 		 				return;
					 				}
 				}
 				*/






submitForm(frm,'mailtracking.mra.defaults.despatchrouting.savescreen.do');
}


function addRoute(frm){

	var count=0;
	for(var i=0;i<targetFormName.elements.hiddenOpFlag.length;i++){

		if(targetFormName.elements.hiddenOpFlag[i].value!='D'){
			count=count+1;
		}
	}
if(count==0){
	submitForm(targetFormName,'mailtracking.mra.defaults.despatchrouting.addrow.do');
	return;
}
if(validateSelectedCheckBoxes(targetFormName,'checkBoxForFlight','1','1')){
var opFlag= eval(targetFormName.elements.hiddenOpFlag);
var carCode= eval(targetFormName.elements.flightCarrierCode);
var fltNumber= eval(targetFormName.elements.flightNumber);
var departDate= eval(targetFormName.elements.departureDate);
var pol= eval(targetFormName.elements.pol);
var pou= eval(targetFormName.elements.pou);
var nopieces = eval(targetFormName.elements.nopieces);
var weight= eval(targetFormName.elements.weight);
var oalPcs = targetFormName.elements.oalPcs.value;
var oalwgt = targetFormName.elements.oalwgt.value;
 var ownccod = targetFormName.elements.owncarcode.value;
var visrec = 0;
var visorg = -1;
var actvrec = new Array();
 if(opFlag.length>1){
 	for (var i = 0; i <opFlag.length; i++) {
 	  if(opFlag[i].value!="NOOP"&&opFlag[i].value!="D"){
 	  	 actvrec[visrec]=i;
	 	  visrec=visrec+1;

	 	   if(visorg==-1){
 	  		visorg=i;
 	  		}

 	  	if(carCode[i].value==""){
 		 				//showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.carcodnull" />',1,self);
						showDialog({msg:'<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.carcodnull" />',type:1,parentWindow:self});
 		 				carCode[i].focus();
 		 				return;
 				        }


  		 if(fltNumber[i].value==""){
 		 				//showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.fltnumbernull" />',1,self);
						showDialog({msg:'<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.fltnumbernull" />',type:1,parentWindow:self});
 		 				fltNumber[i].focus();
 		 				return;
 				        }

 		 if(departDate[i].value==""){
 		 				//showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.departdatenull" />',1,self);
						showDialog({msg:'<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.departdatenull" />',type:1,parentWindow:self});
 		 				departDate[i].focus();
 		 				return;
 				        }

 		if(pol[i].value==""){
 		 				//showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.polnull" />',1,self);
						showDialog({msg:'<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.polnull" />',type:1,parentWindow:self});
 		 				pol[i].focus();
 		 				return;
 				        }
 		if(pou[i].value==""){
 		 				//showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.pounull" />',1,self);
						showDialog({msg:'<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.polnull" />',type:1,parentWindow:self});
 		 				pou[i].focus();
 		 				return;
 				        }

		if(pol[i].value.toUpperCase()==pou[i].value.toUpperCase()){
 				//showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.polpou" />',1,self);
				showDialog({msg:'<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.polpou" />',type:1,parentWindow:self});
 		 				pou[i].focus();
 		 				return;

 				}
		if(nopieces[i].value=="" || nopieces[i].value==0){
 				//showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.nopiecesnull" />',1,self);
				showDialog({msg:'<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.nopiecesnull" />',type:1,parentWindow:self});
 		 				nopieces[i].focus();
 		 				return;
 				}

 		if(weight[i].value=="" || weight[i].value==0){
 				//showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.weightnull" />',1,self);
				showDialog({msg:'<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.weightnull" />',type:1,parentWindow:self});
 		 				weight[i].focus();
 		 				return;
 				}


 		//check for routing order
	 		/*if(i>0){
		 		if(visrec>1){
		 						var cnt = 	visrec-1;
							 var owncarcod = targetFormName.owncarcode.value;
					 		 	if(pou[actvrec[cnt-1]].value.toUpperCase()!=pol[actvrec[cnt]].value.toUpperCase()){
					 		 		if(carCode[actvrec[cnt]].value.toUpperCase()==owncarcod.toUpperCase()){
						 					if(carCode[actvrec[cnt-1]].value.toUpperCase()==owncarcod.toUpperCase()){
									 				 if(pol[actvrec[cnt-1]].value.toUpperCase()!=pol[actvrec[cnt]].value.toUpperCase()){
									 						 		showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.spiltpolmiss" />',1,self);
							 		 								pol[actvrec[cnt]].focus();
							 		 								return;
									 				 }else if(pou[actvrec[cnt-1]].value.toUpperCase()!=pou[actvrec[cnt]].value.toUpperCase()){
									 				 		showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.spiltpoumiss" />',1,self);
							 		 								pou[actvrec[cnt]].focus();
							 		 								return;
									 				 }


									 		}else{
									 				 showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.routin" />',1,self);
							 		 					pol[actvrec[cnt]].focus();
							 		 					return;

									 				 }
						 				}else{
							 					showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.routin" />',1,self);
							 		 				pol[actvrec[cnt]].focus();
							 		 				return;
							 			}
							    	}
	 	      				}

	 	    	}*/

 	    	//check for origin change
			/* if(visorg!=-1){
			 	     		  var orgn = targetFormName.origin.value;
			 	     		  if(pol[visorg].value.toUpperCase()!=orgn.toUpperCase()){
					 				showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.cannotchangeorigin" />',1,self);
						 		 				pol[visorg].focus();
						 		 				return;
					 				}
 				}
 				*/







 			   }
 	 		}
 		}
 	//if(targetFormName.checkBoxForFlight == null ){
			//showDialog("Please select a row",1,self);
	//return;
	//}else{
		submitForm(targetFormName,'mailtracking.mra.defaults.despatchrouting.addrow.do');
	//}
 //addTemplateRow('RoutingDetails','RoutingDetailsBody','hiddenOpFlag');
}
}

function deleteRoute(frm){
submitForm(targetFormName,'mailtracking.mra.defaults.despatchrouting.deleterow.do');
//deleteTableRow('checkBoxForFlight','hiddenOpFlag');
}

function mailDSN(){

 frm=targetFormName;
 var mailDSNArr =document.getElementsByName("dsn");
 var mailDSN =document.getElementsByName("dsn");
   for(var i=0;i<mailDSNArr.length;i++){
      if(mailDSNArr[i].value.length == 1){
          mailDSN[i].value = "000"+mailDSNArr[i].value;
      }
      if(mailDSNArr[i].value.length == 2){
                mailDSN[i].value = "00"+mailDSNArr[i].value;
      }
      if(mailDSNArr[i].value.length == 3){
                mailDSN[i].value = "0"+mailDSNArr[i].value;
      }
   }

}