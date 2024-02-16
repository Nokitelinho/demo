<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister()
    {
	var frm=targetFormName;
	with(frm){

	evtHandler.addEvents("btnClear","clearScreen(this.form)",EVT_CLICK);
	evtHandler.addEvents("btnClose","closeScreen(this.form)",EVT_CLICK);
	evtHandler.addEvents("btnList","listScreen(this.form)",EVT_CLICK);
	evtHandler.addEvents("btnSave","saveScreen(this.form)",EVT_CLICK);
	evtHandler.addEvents("flightCarrierCode","popOalValues(this)",EVT_BLUR);


	//For Links
	evtHandler.addIDEvents("addLink","addRoute(this.form)",EVT_CLICK);
	evtHandler.addIDEvents("deleteLink","deleteRoute(this.form)",EVT_CLICK);

       	//For CheckBox
	evtHandler.addEvents("checkBoxForRoutingCarrier","toggleTableHeaderCheckbox('checkBoxForRoutingCarrier', targetFormName.elements.checkAll);",EVT_CLICK);
	evtHandler.addEvents("checkAll","updateHeaderCheckBox(targetFormName,targetFormName.elements.checkAll,targetFormName.elements.checkBoxForRoutingCarrier)",EVT_CLICK);

	//For Lovs



	evtHandler.addEvents("orgairportlov","displayLOV('showCity.do','N','Y','showCity.do',targetFormName.elements.originCity.value,'Airport','1','originCity','',0)", EVT_CLICK);
	evtHandler.addEvents("dstairportlov","displayLOV('showCity.do','N','Y','showCity.do',targetFormName.elements.destCity.value,'Airport','1','destCity','',0)", EVT_CLICK);
	evtHandler.addEvents("airlineLov","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.carrier.value,'Carrier','1','carrier','',0)", EVT_CLICK);


	evtHandler.addEvents("originLov","displayoriginLov(targetFormName,this)",EVT_CLICK);
	evtHandler.addEvents("destinationLov","displaydestinationLov(targetFormName,this)",EVT_CLICK);
	evtHandler.addEvents("ownSectorFrmlov","displayownSectorFrmlov(targetFormName,this)",EVT_CLICK);
	evtHandler.addEvents("ownSectorTolov","displayownSectorTolov(targetFormName,this)",EVT_CLICK);
	evtHandler.addEvents("oalSectorFrmlov","displayoalSectorFrmlov(targetFormName,this)",EVT_CLICK);
	evtHandler.addEvents("oalSectorTolov","displayoalSectorTolov(targetFormName,this)",EVT_CLICK);
	evtHandler.addEvents("airlineLov1","displayairlineLov1(targetFormName,this)",EVT_CLICK);



	}

	onScreenLoad(frm);
      }

function onScreenLoad(frm){

	frm=targetFormName;

	//document.getElementById("btnSave").disabled="true";
	if(targetFormName.elements.checkResult.value== "Y"){
	targetFormName.elements.checkResult.value="N";
	//showDialog('No result found ! Do you want to create new one?', 4, self, frm, 'id_2');
		//screenConfirmDialog(frm,'id_2');
		//screenNonConfirmDialog(frm,'id_2');
	showDialog({msg:"No result found ! Do you want to create new one?",type:4,parentWindow:self,parentForm:document.forms[1],
	dialogId:'id_2',onClose:function(result){
		screenConfirmDialog(frm,'id_2');
		screenNonConfirmDialog(frm,'id_2');
	}});
	}

}

/**
* For Lov
*/

function displayoriginLov(frm,lovButton)
{
	var index = lovButton.id.split("originLov")[1];
		displayLOV('showCity.do','N','Y','showCity.do',targetFormName.elements.origincode.value,'Airport','1','origincode','',index);
}
function displaydestinationLov(frm,lovButton)
{
	var index = lovButton.id.split("destinationLov")[1];
			displayLOV('showCity.do','N','Y','showCity.do',targetFormName.elements.destcode.value,'Airport','1','destcode','',index);
}
function displayownSectorFrmlov(frm,lovButton)
{
	var index = lovButton.id.split("ownSectorFrmlov")[1];
			displayLOV('showCity.do','N','Y','showCity.do',targetFormName.elements.ownSectorFrm.value,'Airport','1','ownSectorFrm','',index);
}
function displayownSectorTolov(frm,lovButton)
{
	var index = lovButton.id.split("ownSectorTolov")[1];
			displayLOV('showCity.do','N','Y','showCity.do',targetFormName.elements.ownSectorTo.value,'Airport','1','ownSectorTo','',index);
}
function displayoalSectorFrmlov(frm,lovButton)
{
	var index = lovButton.id.split("oalSectorFrmlov")[1];
			displayLOV('showCity.do','N','Y','showCity.do',targetFormName.elements.oalSectorFrm.value,'Airport','1','oalSectorFrm','',index);
}
function displayoalSectorTolov(frm,lovButton)
{
	var index = lovButton.id.split("oalSectorTolov")[1];
			displayLOV('showCity.do','N','Y','showCity.do',targetFormName.elements.oalSectorTo.value,'Airport','1','oalSectorTo','',index);
}

function displayairlineLov1(frm,lovButton)
{
	var index = lovButton.id.split("airlineLov1")[1];
			displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.carriercode.value,'Carrier','1','carriercode','',index);
}



 /**
*Function for close
*/

function closeScreen(frm)
{
submitForm(frm,'mailtracking.mra.defaults.despatchroutingcarrierconfig.closescreen.do');
}



function popOalValues(obj)
{
var nopieces = document.getElementsByName('nopieces');
var weight= document.getElementsByName('weight');
var ownccod = targetFormName.owncarcode.value;
carCode= document.getElementsByName('flightCarrierCode');

var oalPcs = targetFormName.elements.oalPcs.value;
var oalwgt = targetFormName.elements.oalwgt.value;

 						if(carCode[obj.rowCount].value==""){
 		 				//showDialog('<bean:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.carcodnull" />',1,self);
						showDialog({msg:"<bean:message bundle='despatchroutingcarrierconfigresources' key='mailtracking.mra.defaults.despatchrouting.carcodnull' />",type:1,parentWindow:self,parentForm:document.forms[1],dialogId:'id_0',onClose:function(result){}});
 		 				carCode[obj.rowCount].focus();
 		 				return;
 				        }
	 					if(carCode[obj.rowCount].value.toUpperCase()!=ownccod.toUpperCase()){
								nopieces[obj.rowCount].value=oalPcs;
	 							weight[obj.rowCount].value=oalwgt;
	 							nopieces[obj.rowCount].disabled=true;
	 							weight[obj.rowCount].disabled=true;
	 	   				 } else if(carCode[obj.rowCount].value.toUpperCase()==ownccod.toUpperCase()){
	 	   				 		nopieces[obj.rowCount].disabled=false;
	 	   				 		weight[obj.rowCount].disabled=false;
	 	   				 }


}






/**
*Function for clear
*/

function clearScreen(frm)
{
submitForm(frm,'mailtracking.mra.defaults.despatchroutingcarrierconfig.clearscreen.do');
}




/**
*Function for list
*/
function listScreen(frm){

	submitForm(frm,'mailtracking.mra.defaults.despatchroutingcarrierconfig.listcarrierdetails.do');

}




/**
*Function for save
*/

function saveScreen(frm){



var opFlag= eval(targetFormName.elements.hiddenOpFlag);
var carCode= eval(targetFormName.elements.carriercode);
var origincode= eval(targetFormName.elements.origincode);
var destcode= eval(targetFormName.elements.destcode);
var ownSectorFrm= eval(targetFormName.elements.ownSectorFrm);
var ownSectorTo= eval(targetFormName.elements.ownSectorTo);
var oalSectorFrm = eval(targetFormName.elements.oalSectorFrm);
var oalSectorTo= eval(targetFormName.elements.oalSectorTo);
var carriercode= eval(targetFormName.elements.carriercode);

var validFrom=eval(targetFormName.elements.validFrom);
var validTo=eval(targetFormName.elements.validTo);


 if(opFlag != null && opFlag.length>1){
 	for (var i = 0; i <opFlag.length; i++) {
 	  if(opFlag[i].value!="NOOP"&&opFlag[i].value!="D"){



 	  	if(origincode[i].value==""){
 		 				//showDialog('<bean:message bundle="despatchroutingcarrierconfigresources" key="mailtracking.mra.defaults.despatchroutingcarrier.orginnull" />',1,self);
						showDialog({msg:"<bean:message bundle='despatchroutingcarrierconfigresources' key='mailtracking.mra.defaults.despatchroutingcarrier.orginnull' />",type:1,parentWindow:self,parentForm:document.forms[1],dialogId:'id_0',onClose:function(result){}});
 		 				origincode[i].focus();
 		 				return;
 					}



  		 if(destcode[i].value==""){
 		 				//showDialog('<bean:message bundle="despatchroutingcarrierconfigresources" key="mailtracking.mra.defaults.despatchroutingcarrier.destinationnull" />',1,self);
 		 				showDialog({msg:"<bean:message bundle='despatchroutingcarrierconfigresources' key='mailtracking.mra.defaults.despatchroutingcarrier.destinationnull' />",type:1,parentWindow:self,parentForm:document.forms[1],dialogId:'id_0',onClose:function(result){}});
						destcode[i].focus();
 		 				return;
 				        }

 		if(ownSectorFrm[i].value==""){
 		 				//showDialog('<bean:message bundle="despatchroutingcarrierconfigresources" key="mailtracking.mra.defaults.despatchroutingcarrier.ownsecfrmnull" />',1,self);
 		 				showDialog({msg:"<bean:message bundle='despatchroutingcarrierconfigresources' key='mailtracking.mra.defaults.despatchroutingcarrier.ownsecfrmnull' />",type:1,parentWindow:self,parentForm:document.forms[1],dialogId:'id_0',onClose:function(result){}});
						ownSectorFrm[i].focus();
 		 				return;
 				        }

 		if(ownSectorTo[i].value==""){
 		 				//showDialog('<bean:message bundle="despatchroutingcarrierconfigresources" key="mailtracking.mra.defaults.despatchroutingcarrier.ownsectonull" />',1,self);
 		 				showDialog({msg:"<bean:message bundle='despatchroutingcarrierconfigresources' key='mailtracking.mra.defaults.despatchroutingcarrier.ownsectonull' />",type:1,parentWindow:self,parentForm:document.forms[1],dialogId:'id_0',onClose:function(result){}});
						ownSectorTo[i].focus();
 		 				return;
 				        }
 		if(oalSectorFrm[i].value==""){
 		 				//showDialog('<bean:message bundle="despatchroutingcarrierconfigresources" key="mailtracking.mra.defaults.despatchroutingcarrier.oalsecfrmnull" />',1,self);
 		 				showDialog({msg:"<bean:message bundle='despatchroutingcarrierconfigresources' key='mailtracking.mra.defaults.despatchroutingcarrier.oalsecfrmnull' />",type:1,parentWindow:self,parentForm:document.forms[1],dialogId:'id_0',onClose:function(result){}});
						oalSectorFrm[i].focus();
 		 				return;
 				        }
 		if(oalSectorTo[i].value==""){
 				//showDialog('<bean:message bundle="despatchroutingcarrierconfigresources" key="mailtracking.mra.defaults.despatchroutingcarrier.oalsectonull" />',1,self);
				showDialog({msg:"<bean:message bundle='despatchroutingcarrierconfigresources' key='mailtracking.mra.defaults.despatchroutingcarrier.oalsectonull' />",type:1,parentWindow:self,parentForm:document.forms[1],dialogId:'id_0',onClose:function(result){}});
				oalSectorTo[i].focus();
 		 		return;

 					}
 		if(carriercode[i].value==""){
					//showDialog('<bean:message bundle="despatchroutingcarrierconfigresources" key="mailtracking.mra.defaults.despatchroutingcarrier.carriercodenull" />',1,self);
					showDialog({msg:"<bean:message bundle='despatchroutingcarrierconfigresources' key='mailtracking.mra.defaults.despatchroutingcarrier.carriercodenull' />",type:1,parentWindow:self,parentForm:document.forms[1],dialogId:'id_0',onClose:function(result){}});
					carriercode[i].focus();
					return;

		}
 		if(validFrom[i].value==""){
		 				//showDialog('<bean:message bundle="despatchroutingcarrierconfigresources" key="mailtracking.mra.defaults.despatchroutingcarrier.validfromnull" />',1,self);
		 		 		showDialog({msg:"<bean:message bundle='despatchroutingcarrierconfigresources' key='mailtracking.mra.defaults.despatchroutingcarrier.validfromnull' />",type:1,parentWindow:self,parentForm:document.forms[1],dialogId:'id_0',onClose:function(result){}});		
						validFrom[i].focus();
		 		 		return;

		 					}
		 if(validTo[i].value==""){
		  				//showDialog('<bean:message bundle="despatchroutingcarrierconfigresources" key="mailtracking.mra.defaults.despatchroutingcarrier.validtonull" />',1,self);
		  		 		showDialog({msg:"<bean:message bundle='despatchroutingcarrierconfigresources' key='mailtracking.mra.defaults.despatchroutingcarrier.validtonull' />",type:1,parentWindow:self,parentForm:document.forms[1],dialogId:'id_0',onClose:function(result){}});		
						validTo[i].focus();
		  		 		return;

		  					}





 			}
 		}
 	}




submitForm(frm,'mailtracking.mra.defaults.despatchroutingcarrierdetails.savescreen.do');
}


function addRoute(frm){
var opFlg = document.getElementsByName('hiddenOpFlag');
	var count=0;
	for(var i=0;i<opFlg.length;i++){
		if(opFlg[i].value!='D'){
			count=count+1;
		}
	}
if(count==0){
	submitForm(targetFormName,'mailtracking.mra.defaults.despatchroutingcarrierdetails.addrow.do');
	return;
}


var opFlag= eval(targetFormName.elements.hiddenOpFlag);
var carCode= eval(targetFormName.elements.carriercode);
var origincode= eval(targetFormName.elements.origincode);
var destcode= eval(targetFormName.elements.destcode);
var ownSectorFrm= eval(targetFormName.elements.ownSectorFrm);
var ownSectorTo= eval(targetFormName.elements.ownSectorTo);
var oalSectorFrm = eval(targetFormName.elements.oalSectorFrm);
var oalSectorTo= eval(targetFormName.elements.oalSectorTo);
var validFrom=eval(targetFormName.elements.validFrom);
var validTo=eval(targetFormName.elements.validTo);



 if(opFlag.length>1){
 	for (var i = 0; i <opFlag.length; i++) {
 	  if(opFlag[i].value!="NOOP"&&opFlag[i].value!="D"){

if(origincode[i].value==""){
 		 				//showDialog('<bean:message bundle="despatchroutingcarrierconfigresources" key="mailtracking.mra.defaults.despatchroutingcarrier.orginnull" />',1,self);
						showDialog({msg:"<bean:message bundle='despatchroutingcarrierconfigresources' key='mailtracking.mra.defaults.despatchroutingcarrier.orginnull' />",type:1,parentWindow:self,parentForm:document.forms[1],dialogId:'id_0',
						onClose:function(result){ origincode[i].focus(); }});
 		 				return;
 					}



  		 if(destcode[i].value==""){
 		 				//showDialog('<bean:message bundle="despatchroutingcarrierconfigresources" key="mailtracking.mra.defaults.despatchroutingcarrier.destinationnull" />',1,self);
 		 				showDialog({msg:"<bean:message bundle='despatchroutingcarrierconfigresources' key='mailtracking.mra.defaults.despatchroutingcarrier.destinationnull' />",type:1,parentWindow:self,parentForm:document.forms[1],dialogId:'id_0',
						onClose:function(result){
							destcode[i].focus();
						}});
						
 		 				return;
 				        }

 		if(ownSectorFrm[i].value==""){
 		 				//showDialog('<bean:message bundle="despatchroutingcarrierconfigresources" key="mailtracking.mra.defaults.despatchroutingcarrier.ownsecfrmnull" />',1,self);
 		 				showDialog({msg:"<bean:message bundle='despatchroutingcarrierconfigresources' key='mailtracking.mra.defaults.despatchroutingcarrier.ownsecfrmnull' />",type:1,parentWindow:self,parentForm:document.forms[1],dialogId:'id_0',
						onClose:function(result){
							ownSectorFrm[i].focus();
						}});
 		 				return;
 				        }

 		if(ownSectorTo[i].value==""){
 		 				//showDialog('<bean:message bundle="despatchroutingcarrierconfigresources" key="mailtracking.mra.defaults.despatchroutingcarrier.ownsectonull" />',1,self);
 		 				showDialog({msg:"<bean:message bundle='despatchroutingcarrierconfigresources' key='mailtracking.mra.defaults.despatchroutingcarrier.ownsectonull' />",type:1,parentWindow:self,parentForm:document.forms[1],dialogId:'id_0',
						onClose:function(result){
							ownSectorTo[i].focus();
						}});
 		 				return;
 				        }
 		if(oalSectorFrm[i].value==""){
 		 				//showDialog('<bean:message bundle="despatchroutingcarrierconfigresources" key="mailtracking.mra.defaults.despatchroutingcarrier.oalsecfrmnull" />',1,self);
 		 				showDialog({msg:"<bean:message bundle='despatchroutingcarrierconfigresources' key='mailtracking.mra.defaults.despatchroutingcarrier.oalsecfrmnull' />",type:1,parentWindow:self,parentForm:document.forms[1],dialogId:'id_0',
						onClose:function(result){
							oalSectorFrm[i].focus();
						}});
 		 				return;
 				        }
 		if(oalSectorTo[i].value==""){
 				//showDialog('<bean:message bundle="despatchroutingcarrierconfigresources" key="mailtracking.mra.defaults.despatchroutingcarrier.oalsectonull" />',1,self);
 		 		showDialog({msg:"<bean:message bundle='despatchroutingcarrierconfigresources' key='mailtracking.mra.defaults.despatchroutingcarrier.oalsectonull' />",type:1,parentWindow:self,parentForm:document.forms[1],dialogId:'id_0',
				onClose:function(result){
					oalSectorTo[i].focus();
				}});
 		 		return;

 					}
 		if(validFrom[i].value==""){
		 				//showDialog('<bean:message bundle="despatchroutingcarrierconfigresources" key="mailtracking.mra.defaults.despatchroutingcarrier.oalsectonull" />',1,self);
						showDialog({msg:"<bean:message bundle='despatchroutingcarrierconfigresources' key='mailtracking.mra.defaults.despatchroutingcarrier.oalsectonull' />",type:1,parentWindow:self,parentForm:document.forms[1],dialogId:'id_0',
						onClose:function(result){
							validFrom[i].focus();
						}});
		 		 		return;

		 					}
		 if(validTo[i].value==""){
		  				//showDialog('<bean:message bundle="despatchroutingcarrierconfigresources" key="mailtracking.mra.defaults.despatchroutingcarrier.oalsectonull" />',1,self);
		  		 		showDialog({msg:"<bean:message bundle='despatchroutingcarrierconfigresources' key='mailtracking.mra.defaults.despatchroutingcarrier.oalsectonull' />",type:1,parentWindow:self,parentForm:document.forms[1],dialogId:'id_0',
						onClose:function(result){
							validTo[i].focus();
						}});
		  		 		return;

		  					}
 			   }
 	 		}
 		}

		submitForm(targetFormName,'mailtracking.mra.defaults.despatchroutingcarrierdetails.addrow.do');


}




function deleteRoute(frm){
submitForm(targetFormName,'mailtracking.mra.defaults.despatchroutingcarrierdetails.deleterow.do');
}


function screenConfirmDialog(frm, dialogId) {
while(frm.elements.currentDialogId.value == ''){
 	}
	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_2') {

			submitForm(targetFormName,'mailtracking.mra.defaults.despatchroutingcarrierdetails.addrow.do');

		}
		}

}
function screenNonConfirmDialog(frm, dialogId) {
while(frm.elements.currentDialogId.value == ''){
 		}

if(frm.elements.currentDialogOption.value == 'N') {
		if(dialogId=='id_2'){

			}
		}

}