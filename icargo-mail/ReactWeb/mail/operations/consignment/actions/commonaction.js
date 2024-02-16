import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import {change,focus } from 'icoreact/lib/ico/framework/component/common/form';
import { asyncDispatch,dispatchAction,dispatchPrint} from 'icoreact/lib/ico/framework/component/common/actions';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import { validateMailBagForm } from './filteraction';
import { requestValidationError} from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import {  deleteRow } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { reset } from 'redux-form';
import {closePopupWindow} from 'icoreact/lib/ico/framework/component/common/modal/popuputils';
import {maintainconsignment} from './filteraction';

export function screenLoad(args) {
    const{dispatch, getState}=args;
    const state =getState();
    const navigation = state.listconsignmentreducer.navigationFilter.paCode?true:false;
    const url='rest/mail/operations/consignment/screenload';
    return makeRequest({
        url,data:{}
    }).then(function(response) {
        handleScreenloadResponse(dispatch, response, navigation);
        return response; 
    })   

}


export function handleScreenloadResponse(dispatch, response, navigation) {
    if (response.results) {
        if(navigation){
            dispatch(screenLoadSuccessNavigation(response.results[0]));
        }else{
            dispatch(screenLoadSuccess(response.results[0]));
        }
        
       
    }
}
export function screenLoadSuccess(data) {
    return { type: 'SCREENLOAD_SUCCESS', data };
}

export function screenLoadSuccessNavigation(data) {
    return { type: 'SCREENLOAD_SUCCESS_NAVIGATION', data };
}

export function deleteConsignmentAction(values){
    const {dispatch,getState} =values;
    const state =getState();
    const consignmentFilter  = state.form.consignmentFilter.values;
    const data = {...consignmentFilter};

       const url='rest/mail/operations/consignment/deleteConsignment';
      return makeRequest({
         url,data: {...data}
    }).then(function(response) {
        console.log(response);
        handleValidateConsignmentDeleteResponse(dispatch, response);
        return response;
    })
      .catch(error => {
        return { type:'DELETE_ERROR',error};
    });   

}

export function handleValidateConsignmentDeleteResponse(dispatch,response){
    if (response.status==='deleteConsignment_success') {
         dispatch(deleteConsignmentSuccess(response.status));       
     } 
 }

 export function deleteConsignmentSuccess(data){
    return{ type : 'DELETE_CONSIGNMENT_SUCCESS', data }
}

export const warningHandler = (action, dispatch) => {
    switch (action.type) {
        case "__DELEGATE_WARNING_ONOK":
            if (action.executionContext) {
                const warningCode = action.warningCode;
                if (warningCode[0] === "mail.operations.consignment.delete") {
                    dispatch(asyncDispatch(action.executionContext.functionRecord)(action.executionContext.args));
                } else if (warningCode[0] === "mail.operations.consignment.add") {
                    dispatch(asyncDispatch(action.executionContext.functionRecord)(action.executionContext.args));
                }
            }
            break;
        case "__DELEGATE_WARNING_ONCANCEL":
            if (action.executionContext) {
                const warningCode = action.warningCode;
                if ( warningCode[0] == 'mail.operations.consignment.delete' ||
        warningCode[0] == 'mail.operations.consignment.add') {
                    const warningMapValue = { [warningCode]: 'N' };
                     dispatch(asyncDispatch(maintainconsignment)({'displayPage':'1',action:'LIST'}))
                   // dispatch({ type: SET_WARNING_MAP, warningMapValue });
                }
            }
            break;
        default:
            break;
    }
}

export function printConsignment (values) {
    const {args, getState } = values;
    const state = getState();
    const consignment=state.listconsignmentreducer.consignmentmodel;
    consignment.mailsInConsignmentPage=null;
    consignment.statedWeight=null;
    if(args === 'AV7' && consignment.type!== 'AV7'){
        return Promise.reject(new Error("Consignment type is not Military Document")); 
    }
    consignment.type = args;
    const  data={consignment};
    let url = 'rest/mail/operations/consignment/printconsignment';
    return makeRequest({
        url,data: { ...data }
    }).then(function (response) {
        console.log('responseresponse:>',response);       
        return response
    }).catch(error => {
        return error;
    });

}

export const ShowPrintMailTag= (values) =>{
    const{ args, dispatch, getState } = values; 
    const state = getState();
    let mailBagsSelected = true;

    const selectedMailBagData=(state.commonReducer)?state.commonReducer.selectedMailBagData:[];

    if(isEmpty(selectedMailBagData)) {
        mailBagsSelected=false;
    }
    dispatch({ type: 'SHOW_PRINT_MAIL_TAG', mailBagsSelected});
}

export const selectMailBags=(values)=> {
    const { args, dispatch} = values;
    const mailBagDetails= args; 
    dispatch( { type: 'SELECT_MAIL_BAG', mailBagDetails});      
 }

 export function printMailTag(values) {
    const {getState } = values;
    const state = getState();
    const consignment=state.listconsignmentreducer.consignmentmodel;
    const consignmentFilter  = state.form.consignmentFilter.values;
    const printMailTagType = state.form.printMailTagForm.values.printMailTagType;
    consignment.mailsInConsignmentPage=null;
    consignment.statedWeight=null;
    consignment.printMailTag=printMailTagType;
    consignment.selectedMailbagIndex = state.commonReducer.selectedMailbagIndex;
    const  data={consignment, consignmentFilter};
    let url = 'rest/mail/operations/consignment/printmailtag';
    return makeRequest({
        url,data: { ...data }
    }).then(function (response) {
        console.log('responseresponse:>',response);       
        return response
    }).catch(error => {
        return error;
    });

}

export function toggleFilter(showAddConsignment) {  
    return {type: 'TOGGLE_FILTER',showAddConsignment };
  }

export function newRowCheck(curRowData) { 

    let operationFlag="";
    let newRow=true;
    operationFlag = curRowData?curRowData.__opFlag:"";
    if(operationFlag==="I"){
        newRow = false;
    }

    return newRow;
}

export function validateForm(data) {
    let isValid = true;
    let error = ""
    if(!data) {
        isValid = false;
        error = "Please enter new mailbag details"
    }else{
        if(!data.originExchangeOffice || data.originExchangeOffice==="") {
            isValid = false;
            error = "Type or select the Origin OE(Origin Office of Exchange) in the Mail Details section."
        }else if(!data.destinationExchangeOffice || data.destinationExchangeOffice==="") {
            isValid = false;
            error = "Type or select the Dest OE(Destination Office of Exchange) in the Mail Details section."
        }else if(data.originExchangeOffice.length!==6) {
            isValid = false;
            error = "Origin OE(Origin Office of Exchange) should contain 6 alphabets."
        }else if(data.destinationExchangeOffice.length!==6) {
            isValid = false;
            error = "Dest OE(Destination Office of Exchange) should contain 6 alphabets."
        }else if(!data.mailSubclass || data.mailSubclass==="") {
            isValid = false;
            error = "Type or select the SC(Sub Class."
        }else if(data.mailSubclass.length!==2) {
            isValid = false;
            error = "SC(Sub Class) should contain 2 characters."
        }else if(data.year==="") {
            isValid = false;
            error = "Type the Yr(Year) in the Mail Details section."
        }else if(!data.dsn || data.dsn==="") {
            isValid = false;
            error = "Type the DSN(Despatch Serial Number) in the Mail Details section."
        }else if(data.dsn.length!==4) {
            isValid = false;
            error = "The size of DSN(Despatch Serial Number) should be 4."
        }else if(!data.statedBags || data.statedBags==="") {
            isValid = false;
            error = "Type the Std Bags(Stated Bags) in the Mail Details section."
        
        }else if(data.statedBags!==1) {
            if(data.statedBags!=="1"){
                isValid = false;
                error = "The value of Std Bags(Stated Bags) should be one."
            }
        }
        if(!data.receptacleSerialNumber || data.receptacleSerialNumber==="") {
            if(!data.highestNumberedReceptacle || data.highestNumberedReceptacle==="") {
                if(!data.registeredOrInsuredIndicator || data.registeredOrInsuredIndicator==="") {
                isValid = true;
                error = ""
            }else{
                isValid = false;
                error = "Despatch should not have RI."
        }}else{
            isValid = false;
            error = "Despatch should not have HNI."
        }}else if(data.receptacleSerialNumber.length!==3) {
            //isValid = false;
            //error = "The size of RSN should be 3."
        }else if(isNaN(data.receptacleSerialNumber)){
                isValid = false;
                error = "The RSN should be a number."
           }else if(isNaN(data.dsn)){
                isValid = false;
                error = "The DSN(Despatch Serial Number) should be a number."
           }else if(isNaN(data.mailbagWeight.roundedDisplayValue)){
                isValid = false;
                error = "The weight should be a number."
            }else if(data.mailbagWeight.roundedDisplayValue.includes(".")&& !isNaN(data.mailbagWeight.roundedDisplayValue)){
                const num = data.mailbagWeight.roundedDisplayValue.split(".")[0];
                const dec = data.mailbagWeight.roundedDisplayValue.split(".")[1];
                if(num.length>3){
                    isValid = false;
                    error = "Invalid weight format."
                }else if(dec.length>1){
                    isValid = false;
                    error = "Invalid weight format."
                }
            }
           } 

    let validObject = {
        valid: isValid,
        msg: error
    }
    return validObject;
}

export const getLastRowData= (values) =>{
    const{ dispatch, getState } = values;
    const state = getState();
    const curMailBag=state.form.mailBagsTable?
    state.form.mailBagsTable.values.mailBagsTable[(state.form.mailBagsTable.values.mailBagsTable.length)-1] :null;
    dispatch({ type: 'LAST_ROW', curMailBag});  
  }

  export const calculateReseptacles= (values) =>{
    const{ args,dispatch, getState } = values; 
    const state = getState();
    const index = args;
    const rsnData=state.form.RSNPanelForm?(state.form.RSNPanelForm.values.RSNPanelForm):[];
    const rsnRange = rsnData[index];
    let reseptacles = 0;

    if(rsnRange!=null){
    const from = rsnRange.from?rsnRange.from:null;
    const to = rsnRange.to?rsnRange.to:null;

    if(from!=null){
        if(to!=null){
            reseptacles = (to - from) +1;
        }else{
            reseptacles = 1;
        }
    }else if(to!=null){
        reseptacles = Number(to) + 1;
    }else{
        reseptacles = 0;
    }
}
if(reseptacles<0){
    reseptacles=0;
}
dispatch(change('RSNPanelForm', `RSNPanelForm.${index}.receptacles`, reseptacles));

    dispatch({ type: 'RECEPTACLES', reseptacles});
} 

export const getAddMultipleData= (values) =>{
    const{ dispatch, getState } = values;
    const state = getState();
    const addMultipleData=state.form.addMultiple?state.form.addMultiple.values:null;
    const rsnData=state.form.RSNPanelForm?(state.form.RSNPanelForm.values.RSNPanelForm):[];
    //const rsnData=state.form.RSNPanelForm?state.form.RSNPanelForm.values:null;
    dispatch({ type: 'ADD_MULTIPLE_DATA', addMultipleData, rsnData});  
  }

  export function validateAddMultiple(data) {
    let isValid = true;
    let error = ""

    if(!data || data==undefined) {
        isValid = false;
        error = "Please enter details"
    }else{
        if(!data.originExchangeOffice || data.originExchangeOffice==="") {
            isValid = false;
            error = "Type or select the Origin OE(Origin Office of Exchange) in the Mail Details section."
        }else if(!data.destinationExchangeOffice || data.destinationExchangeOffice==="") {
            isValid = false;
            error = "Type or select the Dest OE(Destination Office of Exchange) in the Mail Details section."
        }else if(!data.mailSubclass || data.mailSubclass==="") {
            isValid = false;
            error = "Type or select the SC(Sub Class)."
        }else if( data.year==="") {
            isValid = false;
            error = "Type the Yr(Year) in the Mail Details section."
        }else if(!data.dsn || data.dsn==="") {
            isValid = false;
            error = "Type the DSN(Despatch Serial Number) in the Mail Details section."
        }}

    let validObject = {
        valid: isValid,
        msg: error
    }
    return validObject;
}

export function validateRSN(data) {
    let isValid = true;
    let error = "";

    if (data != undefined) {
        for (let i = 0; i < data.length; i++) {
            let from = data[i].from ? data[i].from : null;
            let to = data[i].to ? data[i].to : null;

            from = parseInt(from, 10);
            to = parseInt(to, 10);

            if (!data[i] || data[i] == undefined) {
                isValid = false;
                error = "Please enter details"
            } else {
                if (from != null) {
                    if (to != null) {
                        if (to < from) {
                            isValid = false;
                            error = "Ending RSN should always be greater than Starting RSN."
                            break;
                        }
                    }
                }
            }
        }

    }
    let validObject = {
        valid: isValid,
        msg: error
    }
    return validObject;
}

export function addMultiple(values) {
    const { dispatch, getState } = values;
    const state = getState();
    const addMultipleData = state.form.addMultiple ? state.form.addMultiple.values : null;
    const allRsnData = state.form.RSNPanelForm ? (state.form.RSNPanelForm.values.RSNPanelForm) : [];
    //const rsnData=state.form.RSNPanelForm?state.form.RSNPanelForm.values:null;
    let mailbagslist = state.form.mailBagsTable ?
        state.form.mailBagsTable.values.mailBagsTable : null;
    let routingList=state.form.routingdetails?(state.form.routingdetails.values.routingdetails):[];
    let consignment = state.listconsignmentreducer.consignmentmodel;
    consignment.consignmentDate = state.form.OtherDetails?state.form.OtherDetails.values.consignmentdate:'';
    consignment.type = state.form.OtherDetails?state.form.OtherDetails.values.consignmenttype:'';
    consignment.operation = state.form.OtherDetails?state.form.OtherDetails.values.flighttype:'';
    consignment.subType = state.form.OtherDetails?state.form.OtherDetails.values.consignmentsubtype:'';
    consignment.remarks = state.form.OtherDetails?state.form.OtherDetails.values.remarks:'';
    //const reseptacles=args;
    for (let k = 0; k < allRsnData.length; k++) {
        let rsnData = allRsnData[k];
        let reseptacles = rsnData.receptacles;

        const from = rsnData.from ? rsnData.from : 0;

        for (let i = 0; i < reseptacles; i++) {

            let mailBag = {};
            mailBag.originExchangeOffice = addMultipleData.originExchangeOffice;
            mailBag.destinationExchangeOffice = addMultipleData.destinationExchangeOffice;
            mailBag.mailCategoryCode = addMultipleData.mailCategoryCode;
            mailBag.mailClass = addMultipleData.class;
            mailBag.mailSubclass = addMultipleData.mailSubclass;
            mailBag.year = addMultipleData.year;
            const dsnValue = populateMailDsn(addMultipleData.dsn);
            mailBag.dsn = dsnValue;
            mailBag.statedBags=1;
            mailBag.highestNumberedReceptacle = addMultipleData.hni;
            mailBag.registeredOrInsuredIndicator = addMultipleData.ri;
            let rsn = Number(from) + i;
            rsn = populateMailRsn(rsn.toString());
            mailBag.receptacleSerialNumber = rsn;

            //mailBag.receptacleSerialNumber= Number(from)+i;
            mailBag.__opFlag = 'I';
            mailBag.mailbagWeight = { displayValue: 0, roundedDisplayValue: '0000', displayUnit: state.commonReducer.defWeightUnit, unitSelect: true ,disabled:false  }
            mailbagslist.push(mailBag);
        }
    }
    dispatch({ type: 'ADD_MULTIPLE_MAILBAG', mailbagslist, routingList, consignment });
}

function validateMailBag(selectedMailbag,dispatch,index){
    if(selectedMailbag.statedWeight&&selectedMailbag.statedWeight.roundedDisplayValue
            &&selectedMailbag.statedWeight.displayUnit){
                let unit=selectedMailbag.statedWeight.displayUnit;
                let weight=selectedMailbag.statedWeight.roundedDisplayValue;
                let name = `mailBagsTable.${index}.statedWeight`
                if(unit=='H'||unit=='L'){
                    if(weight.split('.')[1]!=null){
                        dispatch(requestValidationError('Weight in Pound or Hectogram should contain no decimal part', ''));
                        dispatch(change('mailBagsTable', name   ,  {displayValue : 0, roundedDisplayValue: 0}));
                        Promise.resolve({});
                    }
                }
                else if(unit=='K'){
                    if(weight.split('.')[1]!=null&&
                        weight.split('.')[1].length>1){
                            dispatch(requestValidationError('Weight in Kilogram should not contain decimal part of more than two digits', ''));
                            dispatch(change('mailBagsTable', name   ,  {displayValue : 0, roundedDisplayValue: 0}));
                            Promise.resolve({});
                            }
                }
                else{
                    let weightResult=populateMailWeight(weight,unit);
                    let weightConverted=Number.parseInt(weightResult); 
                    if(weightConverted<0&&weightConverted>9999){
                        dispatch(requestValidationError('Weight in hectogram should be between 0 and 10000', ''));
                        dispatch(change('mailBagsTable', name   ,  {displayValue : 0, roundedDisplayValue: 0}));
                        Promise.resolve({});
                    }
                }   
                        

    }
}


export function populateMailbagId(values) {
    const { dispatch, getState, args } = values;
    const state = getState();
    const index = args;
    // let mailbagId="";
    const tab = state.listconsignmentreducer.activeMailbagAddTab;
    let mailbagId = "";
    let mailbagslist = []
    let updatedMailbags = []
    if (tab === 'EXCEL_VIEW') {
        mailbagslist = state.handsontableReducer.addmailbagFilter ? state.handsontableReducer.addmailbagFilter.data : null;
        mailbags = mailbagslist.filter((obj) => { return obj.ooe != null });
    }
    else {
        mailbagslist = state.form.mailBagsTable ? state.form.mailBagsTable.values.mailBagsTable : null;
        let selectedMailbagRow = mailbagslist[index];
        //validateMailBag(selectedMailbagRow,dispatch,index);
        const active = state.form.mailBagsTable.active ? state.form.mailBagsTable.active.split('.') : state.form.mailBagsTable.active;
        if (active) {
            if (active[2] === "mailId") {
                if (selectedMailbagRow.mailId) {
                    if (selectedMailbagRow.mailId.length === 29) {
    const mailId=selectedMailbagRow.mailId;
    let originDest=[];
    const  data={mailId};
    const url='rest/mail/operations/consignment/fetcharpcodes';
                      return  makeRequest({url,data: {...data}
                       }).then(function(response)  {
         console.log('responseresponse:>',response);       
        if( response&&response.results&&response.results[0].orgDestAirCodes){
        originDest=response.results[0].orgDestAirCodes;
                        dispatch(change('mailBagsTable', `mailBagsTable.${index}.originExchangeOffice`, selectedMailbagRow.mailId.substring(0, 6)));
                        dispatch(change('mailBagsTable', `mailBagsTable.${index}.destinationExchangeOffice`, selectedMailbagRow.mailId.substring(6, 12)));
                        dispatch(change('mailBagsTable', `mailBagsTable.${index}.mailCategoryCode`, selectedMailbagRow.mailId.substring(12, 13)));
                        dispatch(change('mailBagsTable', `mailBagsTable.${index}.mailClass`, selectedMailbagRow.mailId.substring(13, 14)));
                        dispatch(change('mailBagsTable', `mailBagsTable.${index}.mailSubclass`, selectedMailbagRow.mailId.substring(13, 15)));
                        dispatch(change('mailBagsTable', `mailBagsTable.${index}.year`, selectedMailbagRow.mailId.substring(15, 16)));
                        dispatch(change('mailBagsTable', `mailBagsTable.${index}.dsn`, selectedMailbagRow.mailId.substring(16, 20)));
                        dispatch(change('mailBagsTable', `mailBagsTable.${index}.receptacleSerialNumber`, selectedMailbagRow.mailId.substring(20, 23)));
                        dispatch(change('mailBagsTable', `mailBagsTable.${index}.highestNumberedReceptacle`, selectedMailbagRow.mailId.substring(23, 24)));
                        dispatch(change('mailBagsTable', `mailBagsTable.${index}.registeredOrInsuredIndicator`, selectedMailbagRow.mailId.substring(24, 25)));
                        dispatch(change('mailBagsTable', `mailBagsTable.${index}.mailOrigin`,originDest[0]));
                        dispatch(change('mailBagsTable', `mailBagsTable.${index}.mailDestination`,originDest[1]));

                        // state.form.mailBagsTable.values.mailBagsTable[index].statedWeight=selectedMailbagRow.mailId.substring(25, 28)+'.'+selectedMailbagRow.mailId.substring(28, 29);
                        let name = `mailBagsTable.${index}.mailbagWeight`
                        const unit = state.form.mailBagsTable.values.mailBagsTable[index].mailbagWeight &&
                            state.form.mailBagsTable.values.mailBagsTable[index].mailbagWeight.displayUnit ?
                            state.form.mailBagsTable.values.mailBagsTable[index].mailbagWeight.displayUnit :
                            state.commonReducer.defWeightUnit;
                        let mailWeight = populateMailWeight(selectedMailbagRow.mailId.substring(25, 29), unit, dispatch);
                        dispatch(change('mailBagsTable', name, { displayValue: selectedMailbagRow.mailId.substring(25, 29), roundedDisplayValue: mailWeight, displayUnit: unit, disabled: false }));
       //return Promise.resolve({});
                    return response;
                    } 
    if(response&&response.errors){
         return response;
    }
    
    })
        .catch(error => {
            return error;

    });
                       
                    }
                }
            } else {
                if(active[2] === "dsn"){
                    if (selectedMailbagRow.dsn) {
                        const dsnValue = populateMailDsn(selectedMailbagRow.dsn);
                        dispatch(change('mailBagsTable', `mailBagsTable.${index}.dsn`, dsnValue));
                    }
                }
                if(active[2] === "receptacleSerialNumber"){
                    if (selectedMailbagRow.receptacleSerialNumber) {
                        const rsn = populateMailRsn(selectedMailbagRow.receptacleSerialNumber);
                        dispatch(change('mailBagsTable', `mailBagsTable.${index}.receptacleSerialNumber`, rsn));
                    }
                }
            }
        }
        if (selectedMailbagRow.originExchangeOffice && selectedMailbagRow.destinationExchangeOffice && selectedMailbagRow.mailCategoryCode && selectedMailbagRow.mailSubclass && selectedMailbagRow.year && selectedMailbagRow.dsn && selectedMailbagRow.receptacleSerialNumber && selectedMailbagRow.mailbagWeight && selectedMailbagRow.mailbagWeight.roundedDisplayValue && selectedMailbagRow.mailbagWeight.displayUnit) {
            let data = populateMailBagIdWeight(selectedMailbagRow.mailbagWeight.roundedDisplayValue, selectedMailbagRow.mailbagWeight.displayUnit, dispatch);
            const dsnValue = populateMailDsn(selectedMailbagRow.dsn);
            const rsn = populateMailRsn(selectedMailbagRow.receptacleSerialNumber);
            mailbagId = mailbagId + selectedMailbagRow.originExchangeOffice + selectedMailbagRow.destinationExchangeOffice + selectedMailbagRow.mailCategoryCode + selectedMailbagRow.mailSubclass + selectedMailbagRow.year + dsnValue + rsn + selectedMailbagRow.highestNumberedReceptacle + selectedMailbagRow.registeredOrInsuredIndicator;
            mailbagId = mailbagId + data;
            dispatch(change('mailBagsTable', `mailBagsTable.${index}.mailId`, mailbagId));
        }
    }
    }
    function populateMailWeight(weight,unit){
        let weightResult="";
     if(unit=='H'){
            weightResult=weight;
            weightResult= Math.round(weightResult);
         }   
         else if(unit=='K'){
            weightResult=weight/10;
            weightResult= Math.round(weightResult * 10)/ 10;
         }
         else if(unit=='L'){
             weightResult=weight/4.5392;
             weightResult= Math.round(weightResult);
         }
         
         weightResult=populateWeight(String(weightResult));
         return weightResult;
    }
 function populateMailBagIdWeight(weight,unit, dispatch){
        let weightResult="";
         if(unit=='H'){
            weightResult=weight;
         }   
         else if(unit=='K'){
            weightResult=weight*10;
         }
         else if(unit=='L'){
             weightResult=weight*4.5392;
         }
         weightResult= Math.round(weightResult);
         if(weightResult>9999){
            dispatch(requestValidationError('Weight should be between 1Hg and 9999Hg', ''));
            return "0000";
         }else{
            weightResult=populateWeight(String(weightResult));
            return weightResult;
         }
        
    }
    function populateWeight(weight){
        let len = 0;
        if(weight % 1 != 0){
            len = weight.length -1;
        }else{
            len = weight.length;
        }
        if(len == 1){
            weight = "000"+weight;
        }
        if(len == 2){
                    weight = "00"+weight;
        }
        if(len == 3){
                    weight = "0"+weight;
        }
    
        return weight;
    }
    

export function onCloseFunction(values) {
    const { getState } = values;
    const state = getState();
    const params = {
        fromScreen: "CONSIGNMENT"
    }
     let isPopup=state.__commonReducer.screenConfig?state.__commonReducer.screenConfig.isPopup:'';

    if(isPopup == "true") closePopupWindow();
    if(fromScreen==="mail.operations.ux.carditenquiry")
    //state.listconsignmentreducer.navigationFilter.conDocNo?
    navigateToScreen('mail.operations.ux.carditenquiry.defaultscreenload.do', params)
    else
    navigateToScreen('home.jsp', {})
   
}

export function deleteRows(values) {
    const { dispatch, getState } = values;
    const state = getState();
    const rowIndex = state.commonReducer.selectedMailbagIndex? state.commonReducer.selectedMailbagIndex:[];
    const mailbagslist=state.form.mailBagsTable?
                        state.form.mailBagsTable.values.mailBagsTable :null
    if(rowIndex.length>0){
        for(let i=0;i<rowIndex.length;i++){
          const index = rowIndex[(rowIndex.length)-(i+1)].toString();
          
          if(mailbagslist[index].mailStatus && mailbagslist[index].mailStatus!='NEW' && mailbagslist[index].mailStatus!='BKD'){
          dispatch(requestValidationError('Cannot delete despatch '+ mailbagslist[index].mailId +  ' , already accepted'));
          }else{
            dispatch(deleteRow("mailBagsTable", index))
          }
          
        }
     }else{
      dispatch(requestValidationError('Please select atleast one row to delete', ''));
     }
}

export function getTotalCount(values) {
    const { dispatch, getState } = values;
    const state = getState();
    let selectedMailbagsIndex = [];
    const mailbags = state.form.mailBagsTable && state.form.mailBagsTable.values.mailBagsTable? state.form.mailBagsTable.values.mailBagsTable:[];
    const count = mailbags.length;
    for (let i = 0; i < count; i++) {
        selectedMailbagsIndex.push(i);
    }
    dispatch({ type: 'COUNT', selectedMailbagsIndex});
}

function populateMailDsn(dsn){
    var digits=dsn.length;
   var count=4-digits;
   for(let k=0;k<count;k++){
    dsn="0"+dsn;
   }
   return dsn;
}

function populateMailRsn(rsn){
    var digits=rsn.length;
   var count=3-digits;
   for(let k=0;k<count;k++){
    rsn="0"+rsn;
   }
   return rsn;
}

export const populateFlightNumber= (values) =>{
    const{ args,dispatch, getState } = values; 
    const state = getState();
    const index = args;
    const routingDetails=state.form.routingdetails?(state.form.routingdetails.values.routingdetails):[];
    let flightNumber = routingDetails[index].onwardFlightNumber;

    if(flightNumber){
        var digits=flightNumber.length;
   var count=4-digits;
   for(let k=0;k<count;k++){
    flightNumber="0"+flightNumber;
   }
    }
    




dispatch(change('routingdetails', `routingdetails.${index}.onwardFlightNumber`, flightNumber));

}

export function saveSelectedMailbagsIndex(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const index = args;
    let indexes = state.commonReducer.selectedMailbagIndex?state.commonReducer.selectedMailbagIndex:[];

    indexes.push(index);
    dispatch({ type: 'SAVE_SELECTED_INDEX', indexes});
}

export function saveUnselectedMailbagsIndex(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const index = args;
    let indexes = state.commonReducer.selectedMailbagIndex?state.commonReducer.selectedMailbagIndex:[];

    if(index>-1){
        let ind = -1;
        for (let i = 0; i < indexes.length; i++) {
            var element = indexes[i];
            if (element === index) {
                ind = i;
                break;
            }
        }
        if (ind > -1) {
            indexes.splice(ind, 1);
        }
    }else{
        indexes=[];
    }

        
    dispatch({ type: 'SAVE_SELECTED_INDEX', indexes});
}

export function saveSelectedMultipleMailbagIndex(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    let index = args;
    let indexes =[];
    indexes.push(...index);
    dispatch({ type: 'SAVE_SELECTED_INDEX', indexes});
}

export function navigateToConsignmentSecurity(values){
    const { getState } = values;
    const state = getState();
    const conDocNo = state.form.consignmentFilter.values?state.form.consignmentFilter.values.conDocNo:'';
    const paCode = state.form.consignmentFilter.values?state.form.consignmentFilter.values.paCode:'';
    const data ={conDocNo : conDocNo , paCode : paCode , fromScreen : "mail.operations.ux.consignment", pageURL: "mail.operations.ux.consignment"}
    navigateToScreen("mail.operations.ux.consignmentsecuritydeclaration.defaultscreenload.do", data);  
}

export function printConsignmentSummary (values) {
    const {args, getState } = values;
    const state = getState();
    const consignment=state.listconsignmentreducer.consignmentmodel;
    consignment.mailsInConsignmentPage=null;
    consignment.statedWeight=null;
    consignment.type = args;
    const  data={consignment};
    let url = 'rest/mail/operations/consignment/printconsignmentsummary';
    return makeRequest({
        url,data: { ...data }
    }).then(function (response) {
        console.log('responseresponse:>',response);       
        return response
    }).catch(error => {
        return error;
    });

}

export function transportStageQualifierDefaulting(values){
    const{args,dispatch,getState} = values;
    const state = getState();
    const name= args;
    const routingdetails =state.listconsignmentreducer.routingdetails;
    const isDomestic = state.listconsignmentreducer.isDomestic;
    if(!isDomestic){
    if(routingdetails.length!==0){
        const index = name.split('.')[1];
        routingdetails.map(routing=>{
            if(routing===index && routingdetails[index].transportStageQualifier === null){
                dispatch(change('routingdetails',name,'20'));
            }
            if(index>=routingdetails.length){
                dispatch(change('routingdetails',name,'20'));
            }
        })
    }else{
        dispatch(change('routingdetails',name,'20'));
    }
}   
}
export function validateMailbagsPresentandPrint(values){
    const { getState,args,dispatch } = values;
    const state = getState();
    const consignment=state.listconsignmentreducer.consignmentmodel;
    if(consignment.excelMailBags==null)
     {dispatch(requestValidationError('Please save mailbags to print CN summary', ''));}
    else {
        dispatch(dispatchPrint(printConsignmentSummary)(args.printType));}
}

