import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import {reset} from 'redux-form';
import { change } from 'icoreact/lib/ico/framework/component/common/form';
import { requestValidationError} from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { openPopup } from 'icoreact/lib/ico/framework/action/openpopup';
import {  dispatchAction ,asyncDispatch} from 'icoreact/lib/ico/framework/component/common/actions'; 
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import { listContainersinFlight,listmailbagsinContainers} from './flightlistactions.js'
import * as constant from '../constants/constants';
import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { applyCarditFilter} from './carditaction.js';
import moment from 'moment';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';

export function validateMailbagForm(mailbags) {
    let isValid = true;
    let error = ""
    for(var i=0;i<mailbags.length;i++) {
    if (!mailbags[i].ooe) {
        isValid = false;
        error = "Type or select the Origin OE (Office of Exchange)."
    }
    else if (!mailbags[i].doe) {
        isValid = false;
        error = "Type or select the Destination OE (Office of Exchange)."
    }
    else if (!mailbags[i].mailSubclass) {
        isValid = false;
        error = "Type or select the mailbag SC (Sub Class)."
    }
    else if (!mailbags[i].year) {
        isValid = false;
        error = "Type the mailbag Yr (Year)."
    }
    else if (!mailbags[i].despatchSerialNumber) {
        isValid = false;
        error = "Type the mailbag DSN(Despatch Serial Number)."
    }
    else if (!mailbags[i].receptacleSerialNumber) {
        isValid = false;
        error = "Type the mailbag RSN(Receptable Serial Number)."
    }
    else if (!mailbags[i].registeredOrInsuredIndicator) {
        isValid = false;
        error = "Type or select RI."
    }
    else if (!mailbags[i].mailbagWeight) {
        isValid = false;
        error = "Type the mailbag weight."
    }
   }
    

    let validObject = {
        valid: isValid,
        msg: error
    }
    return validObject;
}
//On changing mail tab to 'DSN VIEW' and pagination of DSN VIEW
export const listmailbagsindsnview = (values) => {
    const { args, dispatch, getState } = values;
    const state = getState();
    let flightCarrierFilter = {};
    let data={};
    const currentTab = args.currentTab? args.currentTab:state.mailbagReducer.activeMailbagTab;
    flightCarrierFilter.assignTo = state.commonReducer.flightCarrierflag
    const containersArray = state.mailbagReducer.containersArray;
    const containerInfo = (state.mailbagReducer.selectedContainer) ? state.mailbagReducer.selectedContainer : '';
    let mailbagsDSNDisplayPage='';
    let mailbagsDisplayPage='';
    let mailbagFilter = {};
    if (currentTab === constant.DSN_VIEW) {
        if (args.displayPage) {
           mailbagsDSNDisplayPage = args.displayPage;
        }
        else {
            mailbagsDSNDisplayPage=state.mailbagReducer.mailbagsDSNDisplayPage;
        }
        if(args.applyFilter) {
            mailbagFilter  = state.form.dsnFilter && state.form.dsnFilter.values ? state.form.dsnFilter.values :{}
            dispatch({ type: 'APPLY_MAILBAG_DSN_FILTER', dsnFilterValues: mailbagFilter});
        } else {
            mailbagFilter = state.mailbagReducer.dsnFilterValues;
        }
        data = { containerInfo:{...containerInfo,mailbagpagelist:null,mailbagdsnviewpagelist:null,dsnviewpagelist:null}, flightCarrierFilter,mailbagsDSNDisplayPage,mailbagFilter};
  
    }
    else if (args.currentTab === constant.MAIL_VIEW) {
        if (args.displayPage) {
            mailbagsDisplayPage = args.displayPage;
        }
        else {
            mailbagsDisplayPage=state.mailbagReducer.mailbagsDisplayPage;
        }
        mailbagFilter = state.mailbagReducer.tableFilter;
        data = { containerInfo:{...containerInfo,mailbagpagelist:null,mailbagdsnviewpagelist:null,dsnviewpagelist:null}, flightCarrierFilter,mailbagsDisplayPage,mailbagFilter};
  
    }
    if (containerInfo.mailbagpagelist&&containerInfo.mailbagpagelist.results||
            containerInfo.dsnviewpagelist&&containerInfo.dsnviewpagelist.results) {
        let url = '';
        if (currentTab === constant.DSN_VIEW) {
            url = 'rest/mail/operations/outbound/listMailbagsdsnview';
        } else {
            url = 'rest/mail/operations/outbound/listMailbags';
        }
        return makeRequest({
            url,
            data: { ...data }
        }).then(function (response) {
            handleResponse(dispatch, response, currentTab, containersArray,mailbagsDisplayPage,mailbagsDSNDisplayPage);
            return response
        })
            .catch(error => {
                return error;
            });
    }
    else {
        return Promise.resolve({})
    }


}

function handleResponse(dispatch, response, currentTab, containersArray,mailbagsDisplayPage,mailbagsDSNDisplayPage) {
    if (!isEmpty(response.results)) {
        const { containerInfo} = response.results[0];
        const {mailbagpagelist} =containerInfo;
        if (currentTab === constant.DSN_VIEW) {
            dispatch({ type: constant.LIST_MAILBAGS_DSN, containerInfo,mailbagsDisplayPage,mailbagsDSNDisplayPage });
        } else {
            dispatch({ type: constant.LIST_MAILBAGS, containerInfo, containersArray, mode: 'multi',mailbagpagelist,mailbagsDisplayPage });
        }

        // dispatch({type: 'CHANGE_DETAILPANEL_MODE',mode:'multi'});


    } else {
        if (!isEmpty(response.errors)) {
            dispatch({ type: constant.CLEAR_TABLE });
        }
    }
}



// export const updateSelectedMailbags=(values)=> {
    // const { getState } = values;
    // const state = getState();
    // const selectedailbags=state.listmailbagsreducer.selectedMailbags
    // const mailbags=state.mailbagReducer.mailBags;
// }
export const onLoadAddMailbagPopup = (values) => {
    const { dispatch } = values;
    // const state = getState();
    // let newMailbags=[]
   // if(args.functionName===constant.ADD_MAILBAG){
    //    newMailbags=[];
    //}
   // else {
   //     newMailbags=state.listmailbagsreducer.selectedMailbags;
  //  }
 
   // dispatch(reset(constant.NEW_MAILBAGS_TABLE));
    dispatch({ type: constant.SCREENLOAD_ADDMODIFY_MAILBAG});
}


/**
 * 
 * @param {*} weight 
 * @param {*} unit 
 * Added by A-8164 for converting the weight entered
 * in the iMeasure text field to the Hg and is appended
 * along with mailbagid
 */
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

function populateMailBagIdWeight(weight,unit){
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
     weightResult=populateWeight(String(weightResult));
     return weightResult;
}
function populateMailBagIdWeightedit (weight, unit)
{
    let weightResult="";
    if(unit=='K'){
       weightResult=weight*10;
    }   
    else if(unit=='H'){
       weightResult=weight;
    }
    else if(unit=='L'){
        weightResult=weightInKg=weight/2.16666666666667;
    }
    weightResult= Math.round(weightResult);
    weightResult=populateWeight(String(weightResult));
    return weightResult;
}
function populateWeight(weight){
    if(weight.length == 1){
        weight = "000"+weight;
    }
    if(weight.length == 2){
                weight = "00"+weight;
    }
    if(weight.length == 3){
                weight = "0"+weight;
    }
    return weight;
}

function populateMailWeightDomestic(weight, unit) {
    let weightResult = "";
    if (unit == 'H') {
        weightResult = weight * 4.5392;
    }
    else if (unit == 'K') {
        weightResult = weight * 0.45359;
    }
    else if (unit == 'L') {
        weightResult = weight;
    }
    weightResult = Math.round(weightResult);
    //weightResult = populateWeight(String(weightResult));
    return weightResult;
}

function populateVolume(volume,weight,density,displayUnit,stationVolUnt) {
  /* var volume = volume;
  var w = weight
  var wt = weight/(10*density);
  var strWt=wt.toString();
  var s = strWt.indexOf(".");
  var prefix = strWt.substring(0,s);
  var suffix = strWt.substring(s,s+5);
  if(wt != 0 && prefix == 0 && suffix < 0.01){
         volume =  0.01;
  }else{				
     volume = prefix+suffix;
   } */
   var volume =volume;
   var weightInKg=0;
   var convertedVolume=0;
   if(displayUnit=='H'){
   weightInKg=weight/10;
  }
  else if(displayUnit=='L'){
    weightInKg=weight*0.45359;
  }
  else{
    weightInKg=weight;
  }
  convertedVolume=weightInKg/density;
  if (stationVolUnt=='F'){
     volume=convertedVolume*35.31466688252347;
  }
   else if (stationVolUnt=='I'){
       volume=convertedVolume*61023.743837;
   }
   else if (stationVolUnt=='Y'){
    volume=convertedVolume*1.307950613786;
}
else if (stationVolUnt=='C'){
    volume=convertedVolume*1000000;
}
else {
    volume=convertedVolume;
}
volume = Math.round(volume*100)/100;
   return volume;
}
function populateVolumeedit(volume,weight,density,displayUnit,stationVolUnt) {
     var volume =volume;
     var weightInKg=0;
     var convertedVolume=0;
     if(displayUnit=='K'){
     weightInKg=weight/10;
    }
    else if(displayUnit=='H'){
        weightInKg=weight/10;
       }
    else if(displayUnit=='L'){
      weightInKg=weight/2.16666666666667;
    }
    else{
      weightInKg=weight;
    }
    convertedVolume=weightInKg/density;
    if (stationVolUnt=='F'){
       volume=convertedVolume*35.31466688252347;
    }
     else if (stationVolUnt=='I'){
         volume=convertedVolume*61023.743837;
     }
     else if (stationVolUnt=='Y'){
      volume=convertedVolume*1.307950613786;
  }
  else if (stationVolUnt=='C'){
      volume=convertedVolume*1000000;
  }
  else {
      volume=convertedVolume;
  }
  volume = Math.round(volume*100)/100;
     return volume;
  }
export function validateMailBag(selectedMailbag,dispatch,index){
    //if(selectedMailbag.mailbagWeight&&selectedMailbag.mailbagWeight.roundedDisplayValue
         //   &&selectedMailbag.mailbagWeight.displayUnit){
                let unit=selectedMailbag.mailbagWeight.displayUnit;
                let weight=selectedMailbag.mailbagWeight.roundedDisplayValue;
                let name = `newMailbagsTable.${index}.mailbagWeight`
                if(unit=='H'||unit=='L'){
                    if(weight.split('.')[1]!=null){
                        dispatch(requestValidationError('Weight in Pound or Hectogram should contain no decimal part', ''));
                        dispatch(change(constant.NEW_MAILBAGS_TABLE, name   ,  {displayValue : 0, roundedDisplayValue: 0}));
                        Promise.resolve({});
                    }
                }
                else if(unit=='K'){
                    if(weight.split('.')[1]!=null&&
                        weight.split('.')[1].length>1){
                            dispatch(requestValidationError('Weight in Kilogram should not contain decimal part of more than two digits', ''));
                            dispatch(change(constant.NEW_MAILBAGS_TABLE, name   ,  {displayValue : 0, roundedDisplayValue: 0}));
                            Promise.resolve({});
                            }
                }
                else{
                    let weightResult=populateMailWeight(weight,unit);
                    let weightConverted=Number.parseInt(weightResult); 
                    if(weightConverted<0&&weightConverted>9999){
                        dispatch(requestValidationError('Weight in hectogram should be between 0 and 10000', ''));
                        dispatch(change(constant.NEW_MAILBAGS_TABLE, name   ,  {displayValue : 0, roundedDisplayValue: 0}));
                        Promise.resolve({});
                    }
               }   
                        

    //}
}
export function populateMailbagId(values) {
    const { dispatch, getState, args } = values;
    const state = getState();
    const index = args.rowIndex;
    // let mailbagId="";
    const tab = state.mailbagReducer.activeMailbagAddTab;
    // let mailbags = [];
    let mailbagId = "";
    let mailbagslist = []
    // let mailbagsflag = []
    // let deletedMails = []
    let updatedMailbags = []
    if (tab === 'EXCEL_VIEW') {
        mailbagslist = state.handsontableReducer.addmailbagFilter ? state.handsontableReducer.addmailbagFilter.data : null;
        // mailbags = mailbagslist.filter((obj) => { return obj.ooe != null });
    }
    else {

        mailbagslist = state.form.newMailbagsTable ? state.form.newMailbagsTable.values.newMailbagsTable : null
        let selectedMailbagRow = mailbagslist[index];
        // validateMailBag(selectedMailbagRow,dispatch,index);
        const active = args.active?args.active.split('.')[2]:'';

        if (selectedMailbagRow.mailbagId && active === "mailbagId") {
            if (selectedMailbagRow.mailbagId.length === 29) {
                    const mailId=selectedMailbagRow.mailbagId;
                    let originDest=[];
                    const  data={mailId};
                    const url='rest/mail/operations/consignment/fetcharpcodes';
                         return  makeRequest({url,data: {...data}
                          }).then(function(response)  {
                    if(response&&response.errors&&!isEmpty(response.errors)){
                               dispatch( {type:constant.ERROR_SHOW});
                               return response;
                    }
                    if( response&&response.results&&response.results[0].orgDestAirCodes){
                        originDest=response.results[0].orgDestAirCodes;
                       dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.ooe`, selectedMailbagRow.mailbagId.substring(0, 6)));
                        dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.doe`, selectedMailbagRow.mailbagId.substring(6, 12)));
                        dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.mailCategoryCode`, selectedMailbagRow.mailbagId.substring(12, 13)));
                        dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.mailSubclass`, selectedMailbagRow.mailbagId.substring(13, 15)));
                        dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.year`, selectedMailbagRow.mailbagId.substring(15, 16)));
                        dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.despatchSerialNumber`, selectedMailbagRow.mailbagId.substring(16, 20)));
                        dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.receptacleSerialNumber`, selectedMailbagRow.mailbagId.substring(20, 23)));
                        dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.highestNumberedReceptacle`, selectedMailbagRow.mailbagId.substring(23, 24)));
                        dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.registeredOrInsuredIndicator`, selectedMailbagRow.mailbagId.substring(24, 25)));

                        
                let name = `newMailbagsTable.${index}.mailbagWeight`
                let volName = `newMailbagsTable.${index}.mailbagVolume`
                let mailWeight = populateMailWeight(selectedMailbagRow.mailbagId.substring(25, 29), state.form.newMailbagsTable.values.newMailbagsTable[index].mailbagWeight.displayUnit, dispatch);
                dispatch(change(constant.NEW_MAILBAGS_TABLE, name, { displayValue: selectedMailbagRow.mailbagId.substring(25, 29), roundedDisplayValue: mailWeight, displayUnit: state.form.newMailbagsTable.values.newMailbagsTable[index].mailbagWeight.displayUnit }));
                let mailVolume = populateVolume(selectedMailbagRow.mailbagVolume, mailWeight, state.mailbagReducer.density,state.form.newMailbagsTable.values.newMailbagsTable[index].mailbagWeight.displayUnit,state.mailbagReducer.stationVolUnt)
                dispatch(change(constant.NEW_MAILBAGS_TABLE, volName, mailVolume));
               // return Promise.resolve({})
                //state.form.newMailbagsTable.values.newMailbagsTable[index].mailbagVolume=mailVolume;
                dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.mailorigin`,originDest[0]));
                dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.mailDestination`,originDest[1]));
                return response;

            }
            })
            .catch(error => {
              return error;

            });
            }
            else if (selectedMailbagRow.mailbagId.length === 12) {

                const mailbags = [];
                //added by A-7815 as part of IASCB-39444
                if(selectedMailbagRow.mailbagWeight) {
                selectedMailbagRow = { ...selectedMailbagRow, mailbagWeight: selectedMailbagRow.mailbagWeight.roundedDisplayValue, displayUnit: selectedMailbagRow.mailbagWeight.displayUnit }
                }
                mailbags.push(selectedMailbagRow);
                let warningMessagesStatus=state.commonReducer.warningMessagesStatus?state.commonReducer.warningMessagesStatus:[];
                const data = { mailbags,addMailbagMode:'NORMAL_VIEW',warningMessagesStatus };
                const url = 'rest/mail/operations/outbound/populateMailbagId';
                return makeRequest({
                    url,
                    data: { ...data }
                }).then(function (response) {

                    handleResponseOnPopulateMailbagId(dispatch,response,index,selectedMailbagRow,state);
                    return response
                   
                })
                .catch(error => {
                    return error;
                });


            }
            //return Promise.resolve({})
        }
        else {
            const len = selectedMailbagRow.mailbagId?selectedMailbagRow.mailbagId.length:0;
            if(len!==10 && len!==12){
                if (selectedMailbagRow.ooe && selectedMailbagRow.doe && selectedMailbagRow.mailCategoryCode && selectedMailbagRow.mailSubclass && selectedMailbagRow.year && selectedMailbagRow.despatchSerialNumber && selectedMailbagRow.receptacleSerialNumber && selectedMailbagRow.mailbagWeight && selectedMailbagRow.mailbagWeight.roundedDisplayValue) {
                    let mailWeight = populateMailBagIdWeightedit(selectedMailbagRow.mailbagWeight.roundedDisplayValue, selectedMailbagRow.mailbagWeight.displayUnit, dispatch);
                    let mailVolume = populateVolumeedit(selectedMailbagRow.mailbagVolume, mailWeight, state.mailbagReducer.density,selectedMailbagRow.mailbagWeight.displayUnit,state.mailbagReducer.stationVolUnt)
                    mailbagId = mailbagId + selectedMailbagRow.ooe + selectedMailbagRow.doe + selectedMailbagRow.mailCategoryCode + selectedMailbagRow.mailSubclass + selectedMailbagRow.year + selectedMailbagRow.despatchSerialNumber + selectedMailbagRow.receptacleSerialNumber + selectedMailbagRow.highestNumberedReceptacle + selectedMailbagRow.registeredOrInsuredIndicator + mailWeight;
                    dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.mailbagId`,mailbagId));
                    dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.mailbagVolume`,mailVolume));
                    //let volName = `newMailbagsTable.${index}.mailbagVolume`;
                    //dispatch(change(constant.NEW_MAILBAGS_TABLE, volName, mailVolume));
                    //dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.mailbagWeight`, { displayValue: mailWeight, roundedDisplayValue: mailWeight, displayUnit: state.form.newMailbagsTable.values.newMailbagsTable[index].mailbagWeight.displayUnit }));
                            
                           
                }
    
                if (selectedMailbagRow.despatchSerialNumber) {
                    if (selectedMailbagRow.despatchSerialNumber.length < 4) {
                        let numberofzertoappend = 4 - selectedMailbagRow.despatchSerialNumber.length
                        let toappend = '';
                        for (var i = 0; i < numberofzertoappend; i++) {
                            toappend = toappend + 0;
                        } dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.despatchSerialNumber`,  toappend + selectedMailbagRow.despatchSerialNumber));
                           
                    }
                }
                if (selectedMailbagRow.receptacleSerialNumber) {
                    if (selectedMailbagRow.receptacleSerialNumber.length < 3) {
                        let numberofzertoappend = 3 - selectedMailbagRow.receptacleSerialNumber.length
                        let toappend = '';
                        for (var i = 0; i < numberofzertoappend; i++) {
                            toappend = toappend + 0;
                        }
                        dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.receptacleSerialNumber`,  toappend + selectedMailbagRow.receptacleSerialNumber));
                     }
                }
            }
            
            // if(selectedMailbagRow.mailbagWeight&&selectedMailbagRow.mailbagWeight.roundedDisplayValue) {
            //  if(selectedMailbagRow.mailbagWeight.roundedDisplayValue.length<4) {
            //     let numberofzertoappend=4-selectedMailbagRow.mailbagWeight.roundedDisplayValue.length
            //    let toappend='';
            //    for(var i=0;i<numberofzertoappend; i++) {
            //       toappend=toappend+0;
            //   }
            //    state.form.newMailbagsTable.values.newMailbagsTable[index].mailbagWeight.roundedDisplayValue=toappend+selectedMailbagRow.mailbagWeight.roundedDisplayValue;
            //  }
            //  var density = state.mailbagReducer.density;
            //  var weight = selectedMailbagRow.mailbagWeight.roundedDisplayValue;
            //  var volume = selectedMailbagRow.mailbagVolume;
            //    var w = weight
            //   var wt = weight/(10*density);
            //  var strWt=wt.toString();
            //  var s = strWt.indexOf(".");
            //  var prefix = strWt.substring(0,s);
            //  var suffix = strWt.substring(s,s+5);
            //   if(wt != 0 && prefix == 0 && suffix < 0.01){
            //   volume =  0.01;
            //  }else{				
            //        volume = prefix+suffix;

            // }
            //   state.form.newMailbagsTable.values.newMailbagsTable[index].mailbagVolume=volume;
            //}
           // return Promise.resolve({})
        }
      if (isSubGroupEnabled('TURKISH_SPECIFIC') ) { dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.mailCompanyCode`, selectedMailbagRow.mailCompanyCode));}
                
    }

    updatedMailbags = state.form.newMailbagsTable.values.newMailbagsTable;
    dispatch({ type: constant.UPDATE_MAILBAGS_IN_ADD_POPUP, updatedMailbags });

}


export function handleResponsescreenload(dispatch, response) {
    
    if (!isEmpty(response.results)) {
        const { mailFlight, oneTimeValues } = response.results[0];
        dispatch({ type: constant.SCREENLOAD_ADD_MAILBAG, mailFlight, oneTimeValues });
        // dispatch({type: 'CHANGE_DETAILPANEL_MODE',mode:'multi'});


    } else {
        if (!isEmpty(response.errors)) {
            dispatch({ type: constant.CLEAR_TABLE });
        }
    }
}

export const onSavemailbagDetails = (values) => {
    const { dispatch, getState,args } = values;
    const state = getState();
    const tab=state.mailbagReducer.activeMailbagAddTab;
    const mailAcceptance= state.containerReducer.mailAcceptance
    let uniqueMailbags=[];
const popupAction=state.commonReducer.popupAction;






    let mailbagslist =[]
    // let mailbagslist1 =[]
    let deletedMails=[]
    let mailbags = [];
    let showWarning='Y';
    let disableForModify=false;
    if(args&&args.showWarning){
        showWarning= args.showWarning;
    }
    if(state.commonReducer.flightCarrierflag==='C'){
        showWarning='N'
    }
    let selectedContainer = (state.mailbagReducer.selectedContainer) ? state.mailbagReducer.selectedContainer : '';
    if(selectedContainer.companyCode === null || selectedContainer.companyCode === undefined){
        selectedContainer = state.containerReducer.flightContainers?state.containerReducer.flightContainers.results[0]:{}
    }
    if(tab==='EXCEL_VIEW') {
     let excelMailbags=[];
    
     
      if(state.mailbagReducer.addModifyFlag ===constant.ADD_MAILBAG) {
        state.handsontableReducer.mailBags ? excelMailbags.push(...state.handsontableReducer.mailBags.insertedRows) : null;
        excelMailbags = excelMailbags.map((value) => ({ ...value,mailbagId:value.mailbagId?value.mailbagId:''}));
    
         mailbagslist= populateMailbagDetailsInExcel(excelMailbags,state,dispatch);
        mailbagslist = mailbagslist.map((value) => ({ ...value,operationFlag: 'I',
            mailbagWeight:value.mailbagWeight.roundedDisplayValue,displayUnit:value.mailbagWeight.displayUnit,tab:'EXCEL_VIEW'}));
     
            mailbags.push(...mailbagslist);
      }
      if(state.mailbagReducer.addModifyFlag ===constant.MODIFY_MAILBAG) {
        state.handsontableReducer.mailBags ? excelMailbags.push(...state.handsontableReducer.mailBags.data) : null;
        excelMailbags = excelMailbags.map((value) => ({ ...value,mailbagId:value.mailbagId?value.mailbagId:''}));
    
         mailbagslist= populateMailbagDetailsInExcel(excelMailbags,state,dispatch);
         if (!isEmpty(mailbagslist)) {
            mailbagslist = mailbagslist.map((value) => ({ ...value,operationFlag: 'I',
                mailbagWeight:value.mailbagWeight.roundedDisplayValue,displayUnit:value.mailbagWeight.displayUnit,mailSequenceNumber:0}));
            //to get the selected mailbag for delete during modify action
            deletedMails = state.mailbagReducer.selectedMailbags && state.mailbagReducer.selectedMailbags.length>0? state.mailbagReducer.selectedMailbags : null;
            deletedMails = deletedMails.map((value) => ({ ...value,operationFlag: 'I'}));
      
            deletedMails[0].operationFlag='D';
           // deletedMails.map((value) => ({ ...value,operationFlag:'D'}));
        }
        mailbags.push(...mailbagslist);
    mailbags.push(...deletedMails);
    }
    }
    else {
        
        if(state.mailbagReducer.addModifyFlag ===constant.ADD_MAILBAG) {
        mailbagslist=state.form.newMailbagsTable?state.form.newMailbagsTable.values.newMailbagsTable :null
        mailbagslist = mailbagslist.map((value) => ({ ...value,operationFlag: value.__opFlag,
            mailbagWeight:value.mailbagWeight.roundedDisplayValue,displayUnit:value.mailbagWeight.displayUnit}));
        deletedMails= state.form.newMailbagsTable.values ? state.form.newMailbagsTable.values.deleted :'';
        deletedMails.map((value) => ({ ...value,operationFlag:'D',
            mailbagWeight:value.mailbagWeight.roundedDisplayValue,displayUnit:value.mailbagWeight.displayUnit}));
        }
        if(state.mailbagReducer.addModifyFlag ===constant.MODIFY_MAILBAG) {
            mailbagslist=state.form.newMailbagsTable?state.form.newMailbagsTable.values.newMailbagsTable :null
           if(mailbagslist[0].mailbagId != state.mailbagReducer.selectedMailbags[0].mailbagId) {
            mailbagslist=mailbagslist.filter((value)=>{if(value.__opFlag=== "U") return value});
            if (!isEmpty(mailbagslist)) {
                mailbagslist = mailbagslist.map((value) => ({ ...value,operationFlag: 'I',
                    mailbagWeight:value.mailbagWeight.roundedDisplayValue,displayUnit:value.mailbagWeight.displayUnit,mailSequenceNumber:0}));
                //to get the selected mailbag for delete during modify action
                deletedMails = state.mailbagReducer.selectedMailbags;
                deletedMails = deletedMails.map((value) => ({ ...value,operationFlag: 'I'}));
          
                deletedMails[0].operationFlag='D';
               // deletedMails.map((value) => ({ ...value,operationFlag:'D'}));
            }
        }
        
        else if (!isEmpty(mailbagslist) && (mailbagslist[0].acceptancePostalContainerNumber != state.mailbagReducer.selectedMailbags[0].acceptancePostalContainerNumber))
            {
                mailbagslist = mailbagslist.map((value) => ({ ...value,operationFlag: 'U',
                    mailbagWeight:value.mailbagWeight.roundedDisplayValue,displayUnit:value.mailbagWeight.displayUnit,paContainerNumberUpdate:true}));
            }

        else if(!isEmpty(mailbagslist)) {
            mailbagslist = mailbagslist.map((value) => ({ ...value,operationFlag: 'U',
            mailbagWeight:value.mailbagWeight.roundedDisplayValue,displayUnit:value.mailbagWeight.displayUnit}));
 
        }

       


           
    } 

    if(selectedContainer.paCode && !isEmpty(mailbagslist)) {
        mailbagslist = mailbagslist.map((value) => ({ ...value,paCode:selectedContainer.paCode}));

    }
    mailbags.push(...mailbagslist);
    mailbags.push(...deletedMails);
   }
   uniqueMailbags = Array.from(new Set(mailbags.map(a => a.mailbagId)))
   .map(mailbagId => {
     return mailbags.find(a => a.mailbagId === mailbagId)
   })
   if(uniqueMailbags.length !=mailbags.length){
    return Promise.reject(new Error("Duplicate Mailbags present"));
   }
    let INT_REGEX = /[0-9]/
    if(mailbags != null && mailbags.length > 0){
        for (let i = 0; i < mailbags.length; i++) {
            if (mailbags[i].mailbagId.length === 29 && !(INT_REGEX).test(mailbags[i].year)) {
                return Promise.reject(new Error("Invalid Mailbags"));
            }
        }
    }
  
    const flightCarrierflag = state.commonReducer.flightCarrierflag
    let warningMessagesStatus = state.commonReducer.warningMessagesStatus;

   
    if(state.mailbagReducer.addModifyFlag ===constant.MODIFY_MAILBAG) {
    selectedContainer.mailUpdateFlag=true;
    disableForModify=state.mailbagReducer.disableForModify;
    }
    if(selectedContainer) {
       // if(selectedContainer.mailbagpagelist!=null){
       // mailbags.push(...selectedContainer.mailbagpagelist.results);
      //  }
      const data = { selectedContainer: { ...selectedContainer,operationFlag:'U',containerOperationFlag:'U',mailbagdsnviewpagelist: null, mailbagpagelist:null,dsnviewpagelist:null}, flightCarrierflag,mailAcceptance : {...mailAcceptance , containerPageInfo:null,popupAction:popupAction},mailbags,warningMessagesStatus,actionType:constant.ADD_MAILBAG, tab,showWarning,disableForModify}
      const url = 'rest/mail/operations/outbound/AddMailbag';
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        if(isEmpty(response.errors)){
            state.handsontableReducer&&state.handsontableReducer.mailBags ?
                    state.handsontableReducer.mailBags.insertedRows=[] : '';
            state.handsontableReducer&&state.handsontableReducer.mailBags ?
                    state.handsontableReducer.mailBags.data=[] : '';
        }
        handleResponseonAdding(dispatch, response,constant.ADD_MAILBAGS_CLOSE);
        return response
    })
        .catch(error => {
            return error;
        });
    }
    else {
      dispatch(requestValidationError('Mailbags should be added to selected containers'));
      return Promise.resolve({})
    }              
    
}



function populateMailbagDetailsInExcel(excelmailbags, state, dispatch) {
    let newMailbagList=[];
    for(let i = 0; i < excelmailbags.length; i++){
        if(excelmailbags[i].mailbagId===null||excelmailbags[i].mailbagId.length === 0 ){
    if(excelmailbags[i].ooe !=null&&excelmailbags[i].doe!=null  &&excelmailbags[i].mailCategoryCode!=null &&excelmailbags[i].mailSubclass!=null
        &&excelmailbags[i].year!=null &&excelmailbags[i].despatchSerialNumber!=null &&excelmailbags[i].receptacleSerialNumber!=null &&excelmailbags[i].highestNumberedReceptacle!=null
        &&excelmailbags[i].registeredOrInsuredIndicator!=null &&excelmailbags[i].mailbagWeight!=null){
            newMailbagList.push(excelmailbags[i]);
           
        }
    }
        else{
            newMailbagList.push(excelmailbags[i]);  
        }
    }
    if (state.mailbagReducer.addModifyFlag === constant.ADD_MAILBAG) {
       
        for (let i = 0; i < newMailbagList.length; i++) {
            let selectedMailbagRow = newMailbagList[i];
            if (selectedMailbagRow.mailbagId.length === 29) {
                selectedMailbagRow = modifyActionForMailbagId(selectedMailbagRow, state,dispatch);
            } else if(selectedMailbagRow.mailbagId===''||selectedMailbagRow.mailbagId.length === 0) {

                selectedMailbagRow = modifyActionForOtherMailbagDetails(selectedMailbagRow, state,dispatch);
            }else{
                 selectedMailbagRow.mailClass=selectedMailbagRow.mailbagId.substring(3, 4);
                let mailWeight = populateMailWeight(selectedMailbagRow.mailbagId.substring(10, 12), state.mailbagReducer.defaultWeightUnit, dispatch);
                selectedMailbagRow.mailbagWeight={ displayValue: selectedMailbagRow.mailbagId.substring(10, 12), roundedDisplayValue: mailWeight, displayUnit: state.mailbagReducer.defaultWeightUnit}
            }
            
        }
    }
    else if (state.mailbagReducer.addModifyFlag === constant.MODIFY_MAILBAG) {
        let mailBagDetails = state.mailbagReducer.selectedMailbags ? state.mailbagReducer.selectedMailbags[0] : [];
        let selectedMailbagRow = newMailbagList[0];
        if (mailBagDetails != null && selectedMailbagRow != null) {
            if (mailBagDetails.mailbagId === selectedMailbagRow.mailbagId) {
                selectedMailbagRow = modifyActionForOtherMailbagDetails(selectedMailbagRow, state,dispatch);
            }
            else {
                selectedMailbagRow = modifyActionForMailbagId(selectedMailbagRow, state,dispatch);
            }
        }
    }

    return newMailbagList;
}

function modifyActionForMailbagId(selectedMailbagRow,state,dispatch){
    selectedMailbagRow.ooe=selectedMailbagRow.mailbagId.substring(0, 6);
    selectedMailbagRow.doe=selectedMailbagRow.mailbagId.substring(6, 12);
    selectedMailbagRow.mailCategoryCode=selectedMailbagRow.mailbagId.substring(12, 13);
    selectedMailbagRow.mailSubclass=selectedMailbagRow.mailbagId.substring(13, 15);
    selectedMailbagRow.year=selectedMailbagRow.mailbagId.substring(15, 16);
    selectedMailbagRow.despatchSerialNumber=selectedMailbagRow.mailbagId.substring(16, 20);
    selectedMailbagRow.receptacleSerialNumber=selectedMailbagRow.mailbagId.substring(20, 23);
    selectedMailbagRow.highestNumberedReceptacle=selectedMailbagRow.mailbagId.substring(23, 24);
    selectedMailbagRow.registeredOrInsuredIndicator=selectedMailbagRow.mailbagId.substring(24, 25);
    let mailWeight = populateMailWeight(selectedMailbagRow.mailbagId.substring(25, 29), state.mailbagReducer.defaultWeightUnit, dispatch);
    selectedMailbagRow.mailbagWeight={ displayValue: selectedMailbagRow.mailbagId.substring(25, 29), roundedDisplayValue: mailWeight, displayUnit: state.mailbagReducer.defaultWeightUnit}
    let mailVolume = populateVolume(selectedMailbagRow.mailbagVolume, mailWeight, state.mailbagReducer.density,state.mailbagReducer.defaultWeightUnit,state.mailbagReducer.stationVolUnt)
    selectedMailbagRow.mailbagVolume=mailVolume;
    selectedMailbagRow.scannedDate=state.mailbagReducer.currentDate;
    selectedMailbagRow.scannedTime=state.mailbagReducer.currentTime;
    selectedMailbagRow.arrivalSealNumber=selectedMailbagRow.arrivalSealNumber && selectedMailbagRow.arrivalSealNumber!=undefined?selectedMailbagRow.arrivalSealNumber:'';
    selectedMailbagRow.carrier=selectedMailbagRow.carrier && selectedMailbagRow.carrier !=undefined?selectedMailbagRow.carrier :'';
}
function modifyActionForOtherMailbagDetails(selectedMailbagRow,state,dispatch){
    selectedMailbagRow.scannedDate= state.mailbagReducer.currentDate;
    selectedMailbagRow.scannedTime=state.mailbagReducer.currentTime;
    if(selectedMailbagRow.displayUnit==null)    {
    selectedMailbagRow.displayUnit=state.mailbagReducer.defaultWeightUnit;                      
    } 
           
    let mailIdWeight=populateMailBagIdWeight(selectedMailbagRow.mailbagWeight,selectedMailbagRow.displayUnit,dispatch);   
    selectedMailbagRow.mailbagId= selectedMailbagRow.ooe +selectedMailbagRow.doe +selectedMailbagRow.mailCategoryCode +selectedMailbagRow.mailSubclass +selectedMailbagRow.year +selectedMailbagRow.despatchSerialNumber + selectedMailbagRow.receptacleSerialNumber +selectedMailbagRow.highestNumberedReceptacle +selectedMailbagRow.registeredOrInsuredIndicator + mailIdWeight;
    let mailWeight = populateMailWeight(selectedMailbagRow.mailbagId.substring(25, 29), selectedMailbagRow.displayUnit, dispatch);
    let mailbagWeight = { displayValue: mailWeight, roundedDisplayValue: mailWeight, displayUnit: selectedMailbagRow.displayUnit}
    selectedMailbagRow.mailbagWeight= mailbagWeight;
    
           
}

export const onSaveRemarks= (values) => {
    const { dispatch, getState } = values;
    const state = getState();
    const mailAcceptance= state.containerReducer.mailAcceptance
    let mailbags = [];
    let mailbagslist =[]
    let remarks=state.form.mailRemarksForm.values?state.form.mailRemarksForm.values.remarks:''
        if(isEmpty(remarks)){
        return Promise.reject(new Error("Please Enter Remarks"))
        }
    mailbagslist=state.mailbagReducer.selectedMailbags
    if (!isEmpty(mailbagslist)) {
        mailbagslist = mailbagslist.map((value) => ({ ...value,mailRemarks:remarks,operationFlag: 'U'}));
    }
           
   
    mailbags.push(...mailbagslist);
    const flightCarrierflag = state.commonReducer.flightCarrierflag
    const selectedContainer = (state.mailbagReducer.selectedContainer) ? state.mailbagReducer.selectedContainer : '';
    //const mailFlight = (state.containerReducer.mailflight) ? state.containerReducer.mailflight : '';
    const data = { selectedContainer: { ...selectedContainer,operationFlag:'U',containerOperationFlag:'U',mailbagdsnviewpagelist: null, mailbagpagelist: null ,dsnviewpagelist:null}, flightCarrierflag,mailAcceptance : {...mailAcceptance , containerPageInfo:null},mailbags}
    // const data={containerInfo,flightCarrierflag,newMailbag,mailFlight}
    const url = 'rest/mail/operations/outbound/AddMailbag';
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        handleResponseonAdding(dispatch, response,constant.REMARKS_CLOSE);
        return response
    })
        .catch(error => {
            return error;
    });
}

export const onSaveScanTime= (values) => {
    const { dispatch, getState } = values;
    const state = getState();
    const mailAcceptance= state.containerReducer.mailAcceptance
    let mailbags = [];
    let mailbagslist =[]
    let scanDate=state.form.scanTimeForm.values?state.form.scanTimeForm.values.scanDate:''
    let scanTime=state.form.scanTimeForm.values?state.form.scanTimeForm.values.scanTime:''
    mailbagslist=state.mailbagReducer.selectedMailbags
    if (!isEmpty(mailbagslist)) {
        mailbagslist = mailbagslist.map((value) => ({ ...value,scannedDate:scanDate,scannedTime:scanTime,operationFlag: 'U'}));
    }
    mailbags.push(...mailbagslist);
    const flightCarrierflag = state.commonReducer.flightCarrierflag
    const selectedContainer = (state.mailbagReducer.selectedContainer) ? state.mailbagReducer.selectedContainer : '';
    //const mailFlight = (state.containerReducer.mailflight) ? state.containerReducer.mailflight : '';
    const data = { selectedContainer: { ...selectedContainer,operationFlag:'U',containerOperationFlag:'U',mailbagdsnviewpagelist: null, mailbagpagelist: null ,dsnviewpagelist:null}, flightCarrierflag,mailAcceptance : {...mailAcceptance , containerPageInfo:null},mailbags}
    // const data={containerInfo,flightCarrierflag,newMailbag,mailFlight}
    const url = 'rest/mail/operations/outbound/AddMailbag';
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        handleResponseonAdding(dispatch, response,constant.SCAN_TIME_CLOSE);
        return response
    })
        .catch(error => {
            return error;
    });
}

export function deleteMailBags(values) {
        const { dispatch, args } = values;
        if(args && args.warningFlag==='Y') {
            dispatch(asyncDispatch(onDeleteMailbagAction)()).then(() => {
                dispatch(asyncDispatch(applyCarditFilter)()).then(() => {
                    dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
                        dispatch(asyncDispatch(listmailbagsinContainers)());
                        });
                });
            });
        } else {
            dispatch(requestWarning([{code:"mail.operations.mailbags.deletewarn", description:"The selected mailbag(s) will be deleted from container. Do you want to continue ?"}],{functionRecord:deleteMailBags, args:{ warningFlag:'Y'}}));
            return;
        }
        
}

export const onDeleteMailbagAction= (values) => {
    const { dispatch, getState } = values;
    const state = getState();
    const mailAcceptance= state.containerReducer.mailAcceptance
    let mailbags = [];
    let mailbagslist =[]
    mailbagslist=state.mailbagReducer.selectedMailbags
    if (!isEmpty(mailbagslist)) {
        mailbagslist = mailbagslist.map((value) => ({ ...value,operationFlag: 'D'}));
    }
           
   
    mailbags.push(...mailbagslist);
    const flightCarrierflag = state.commonReducer.flightCarrierflag
    const selectedContainer = (state.mailbagReducer.selectedContainer) ? state.mailbagReducer.selectedContainer : '';
    //const mailFlight = (state.containerReducer.mailflight) ? state.containerReducer.mailflight : '';
    const data = { selectedContainer: { ...selectedContainer,operationFlag:'U',containerOperationFlag:'U',mailbagdsnviewpagelist: null, mailbagpagelist: null ,dsnviewpagelist:null}, flightCarrierflag,mailAcceptance : {...mailAcceptance , containerPageInfo:null},mailbags}
    // const data={containerInfo,flightCarrierflag,newMailbag,mailFlight}
    const url = 'rest/mail/operations/outbound/AddMailbag';
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        handleResponseonAdding(dispatch, response,constant.DELETE);
        return response
    })
        .catch(error => {
            return error;
    });
}

function handleResponseonAdding(dispatch, response,action) {
    if (response.status==='success_embargo') {
       if(action===constant.ADD_MAILBAGS_CLOSE) {
        const warningMapValue = { ['mailtracking.defaults.war.coterminus']: 'N',['mail.operations.securityscreeningwarning']:'N' };
        dispatch({ type: constant.SET_WARNING_MAP, warningMapValue });
        dispatch({ type: constant.ADD_MAILBAG_SUCCESS });
        dispatch(asyncDispatch(listContainersinFlight)());
            dispatch(asyncDispatch(listmailbagsinContainers)({embargoInfo:true,mailbagsDSNDisplayPage:'1',mailbagsDisplayPage:'1'}))
           }
    }
    if (response.status==='success') {
       if(action===constant.ADD_MAILBAGS_CLOSE) {
        const warningMapValue = { ['mailtracking.defaults.war.coterminus']: 'N',['mail.operations.securityscreeningwarning']:'N' };
        dispatch({ type: constant.SET_WARNING_MAP, warningMapValue });
        dispatch({ type: constant.ADD_MAILBAG_SUCCESS });
      //  dispatch(asyncDispatch(listContainersinFlight)());
      //  dispatch(asyncDispatch(listmailbagsinContainers)())
       }
       else if(action===constant.REMARKS_CLOSE) {
        dispatch({ type: constant.REMARKS_CLOSE });
       }
       else if(action===constant.SCAN_TIME_CLOSE) {
        dispatch({ type: constant.SCAN_TIME_CLOSE });
       }
       else if(action===constant.DO_RETURN) {
        dispatch({ type: constant.RETURN_CLOSE });
       }
       else if(action===constant.REASSIGN_EXISTING_MAILBAGS) {
        dispatch({ type: constant.EXISTING_MAILBAG_POPUP_CLOSE});  
       }
      

 
    } else if(response.status==='embargo') {
        invokeEmbargoPopup(dispatch); 
    }
    else if(isEmpty(response.errors) && response.results[0].existingMailbagFlag ==='Y'){
     
        let existingMailbags = response.results[0].existingMailbags
        let addedMailbags =response.results[0].mailbags
        dispatch({ type: constant.EXISTING_MAILBAG_POPUP_OPEN ,existingMailbags,addedMailbags});
      
    }
}
// mailbag actions integration
export function onMailBagAction(values) {

    const { args, dispatch, getState } = values;
    const state = getState();
    let indexes=[];
    let data={}
    let url = '';
    let action='';
    // let mode=args.mode;
    let mailAcceptance=''
    let selectedMailbags=[];
    // let selectedMailbag='';
    const flightCarrierflag=state.commonReducer.flightCarrierflag
    if(args.mode) {
        action=args.mode;
    }
    if(args.index) {
        indexes.push(args.index);
        // selectedMailbag = state.mailbagReducer.mailBags.results[args.index];
       
    }
    else {
        indexes=state.mailbagReducer.selectedMailbagsIndex;
       
    }
    
    // const selectedContainer = state.containerReducer.selectedContainer;
    for(var i=0; i<indexes.length;i++) {
         selectedMailbags.push(state.mailbagReducer.mailBags.results[indexes[i]]);
        
    }
    if(flightCarrierflag==='F') {
        mailAcceptance= state.containerReducer.mailAcceptance
    }
    if (action === constant.VIEW_DAMAGE){
        data={selectedMailbags}
        url = 'rest/mail/operations/mailbagenquiry/viewMailbagDamage';
        return makeRequest({
            url,
            data: {...data}
        }).then(function (response) {
            handleResponseOnModify(dispatch, response,selectedMailbags,constant.VIEW_DAMAGE)
            return response;
        })
        .catch(error => {
            return error;
        });
    } 
    else if (action=== constant.ATTACH_AWB){
         let containerDetailsCollection=[]
        let selectedContainer=state.containerReducer.selectedContainer
        selectedContainer={...selectedContainer,mailbagpagelist:null,totalWeight:null,
                           mailBagDetailsCollection:selectedMailbags!=null?selectedMailbags:null}
        containerDetailsCollection.push(selectedContainer);

        data={selectedMailbags,containerDetailsCollection}
        url = 'rest/mail/operations/outbound/screenloadattachawb';
        return makeRequest({
            url,
            data: {...data}
        }).then(function (response) {
            handleResponseOnModify(dispatch,response,selectedMailbags,constant.ATTACH_AWB);
            return response
        })
        .catch(error => {
            return error;
        });
    } else if (action=== constant.DELIVER_MAIL){
        data={selectedMailbags}
        url = 'rest/mail/operations/mailbagenquiry/deliverMailbags';
        return makeRequest({
            url,
            data: {...data}
        }).then(function (response) {
            handleResponseOnModify(dispatch, response,selectedMailbags,constant.DELIVER_MAIL)
            return response;
        })
        .catch(error => {
            return error;
        });
    }
    else if(action===constant.DETACH_AWB) {
        let containerDetailsCollection=[]
        let selectedContainer=state.containerReducer.selectedContainer
        selectedContainer={...selectedContainer,mailbagpagelist:null,totalWeight:null,
                           mailBagDetailsCollection:selectedMailbags!=null?selectedMailbags:null}
        containerDetailsCollection.push(selectedContainer);
        data={containerDetailsCollection}
        url = 'rest/mail/operations/outbound/detachAWBCommand';
        return makeRequest({
            url,
            data: {...data}
        }).then(function (response) {
            handleResponseOnModify(dispatch, response,selectedMailbags,constant.DETACH_AWB)
            return response
        })
        .catch(error => {
            return error;
        });
      }
    if (action === constant.MODIFY_MAILBAG){
        const url = 'rest/mail/operations/outbound/screenloadAddModifyContainer';
        const data={mailAcceptance : {...mailAcceptance , containerPageInfo:null}}
        return makeRequest({
            url,
            data: {...data}
        }).then(function (response) {
            handleResponseOnModify(dispatch, response,selectedMailbags,constant.MODIFY_MAILBAG)
            return response;
        })
        .catch(error => {
            return error;
        });
    }
    if (action === constant.REMARKS){
        const url = 'rest/mail/operations/outbound/screenloadAddModifyContainer';
        const data={mailAcceptance : {...mailAcceptance , containerPageInfo:null}}
        return makeRequest({
            url,
            data: {...data}
        }).then(function (response) {
            handleResponseOnModify(dispatch, response,selectedMailbags,constant.REMARKS)
            return response;
        })
        .catch(error => {
            return error;
        });
    }
    if (action === 'CHANGE SCAN TIME'){
        const url = 'rest/mail/operations/outbound/screenloadAddModifyContainer';
        const data={mailAcceptance : {...mailAcceptance , containerPageInfo:null}}
        return makeRequest({
            url,
            data: {...data}
        }).then(function (response) {
            handleResponseOnModify(dispatch, response,selectedMailbags,'CHANGE SCAN TIME')
            return response;
        })
        .catch(error => {
            return error;
        });
    }


    if (action === constant.ADD_MAILBAG){
        const url = 'rest/mail/operations/outbound/screenloadAddModifyContainer';
        const data={mailAcceptance : {...mailAcceptance , containerPageInfo:null}}
        return makeRequest({
            url,
            data: {...data}
        }).then(function (response) {
			if(state.form.newMailbagsTable !=null)
            state.form.newMailbagsTable.values.newMailbagsTable=[];
            (state.form.newMailbagsTable && state.form.newMailbagsTable.values) ? state.form.newMailbagsTable.values.newMailbagsTable=[] : ''
            handleResponseOnAction(dispatch, response,constant.ADD_MAILBAG)
            return response;
        })
        .catch(error => {
            return error;
        });
    }
if (action === constant.REASSIGN){
        const url = 'rest/mail/operations/outbound/screenloadAddModifyContainer';
        const data={mailAcceptance : {...mailAcceptance , containerPageInfo:null}}
        return makeRequest({
            url,
            data: {...data}
        }).then(function (response) {
            handleResponseOnModify(dispatch, response,selectedMailbags,constant.REASSIGN)
            return response;
        })
        .catch(error => {
            return error;
        });
    }
   
   if ((action === constant.RETURN) ||(action===constant.DAMAGE_CAPTURE)){
    const url = 'rest/mail/operations/outbound/screenloadReturnMail';
    const data={}
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        handleResponseOnModify(dispatch, response,selectedMailbags, action,values);
        return response
    })
        .catch(error => {
            return error;
        });
   }
   if (action === constant.DELETE){
    const url = 'rest/mail/operations/outbound/screenloadAddModifyContainer';
    const data={mailAcceptance : {...mailAcceptance , containerPageInfo:null}}
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        handleResponseOnModify(dispatch, response,selectedMailbags,constant.DELETE)
        return response;
    })
    .catch(error => {
        return error;
    });
   // dispatch( { type: constant.DELETE_MAILBAGS,selectedMailbags});

  }
  if (action === "TRANSFER"){
    const url = 'rest/mail/operations/outbound/screenloadAddModifyContainer';
    const data={mailAcceptance : {...mailAcceptance , containerPageInfo:null} ,actionType:"TRANSFER_MAIL"}
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        handleResponseOnModify(dispatch, response,selectedMailbags,'TRANSFER')
        return response;
    })
    .catch(error => {
        return error;
    });
}
}

function handleResponseOnAction(dispatch, response,mode) {
    if (!isEmpty(response.results)) {
     
        if (mode === constant.ADD_MAILBAG) {
            const {mailAcceptance,oneTimeValues,density,currentDate,currentTime,defWeightUnit,stationVolUnt} = response.results[0];
            dispatch( { type: constant.SCREENLOAD_ADD_MODIFY_MAILBAG, mailAcceptance,density,oneTimeValues,mode,currentDate,currentTime,defWeightUnit,stationVolUnt})
   
        } 
        
        else {
            dispatch({ type: constant.LIST_MAILBAGS,   mode: 'multi' });
        }
    } else {
        if (!isEmpty(response.errors)) {
            dispatch({ type: constant.CLEAR_TABLE });
        }
    }
}


export function handleResponseOnModify(dispatch,response,selectedMailbags,mode,values) {
    
    if(!isEmpty(response.results)){
        if(mode===constant.MODIFY_MAILBAG) {
            const {mailAcceptance,oneTimeValues,density,defWeightUnit,stationVolUnt,currentDate,currentTime} = response.results[0];
            dispatch( { type: constant.SCREENLOAD_ADD_MODIFY_MAILBAG,mailAcceptance, oneTimeValues,selectedMailbags,mode,density,defWeightUnit,stationVolUnt,currentDate,currentTime });
        }
       else if(mode===constant.REASSIGN) {
           const {currentDate,currentTime} = response.results[0];
            dispatch( { type: constant.REASSIGN_POPUP_OPEN,selectedMailbags,currentDate,currentTime });
        }
        else  if(mode===constant.REMARKS) {
            // const {mailAcceptance,oneTimeValues} = response.results[0];
            dispatch( { type: constant.REMARKS_POPUP_OPEN,selectedMailbags});
        }
        else  if(mode==='CHANGE SCAN TIME') {
            const {currentDate,currentTime} = response.results[0];
            dispatch( { type: constant.SCAN_TIME_POPUP_OPEN,selectedMailbags,currentDate,currentTime});
        }
        else  if(mode===constant.DELETE) {
           // const {mailAcceptance,oneTimeValues,currentDate,currentTime} = response.results[0];
            dispatch( { type: constant.DELETE_MAILBAGS,selectedMailbags});
        }
        else if(mode ===constant.RETURN || mode ===constant.DAMAGE_CAPTURE) {
            let {damagedMailbags ,postalAdministrations} = response.results[0];
            const { args } = values;
            let selectedMail = null;
            if(args.index) 
            {selectedMail=args.index;}
            if(damagedMailbags==undefined||damagedMailbags==null){
                damagedMailbags=[];
            }
            dispatch({type:constant.MAILBAG_RETURN_OPEN,damagedMailbags,selectedMailbags,postalAdministrations,selectedMail,mode})
        }
         else if(mode ===constant.ATTACH_AWB ) {
           dispatch({type: constant.ATTACH_AWB,attachClose:true,attachAwbDetails:response.results[0].attachAwbDetails,
                 containerDetailsToBeReused:response.results[0].containerDetailsCollection,attachAwbOneTimeValues:response.results[0].oneTimeValues});
        }
        else if(mode ===constant.DETACH_AWB ) {
            dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
                dispatch(asyncDispatch(listmailbagsinContainers)());
              })
         }
        else if(mode==='TRANSFER') {
            const {currentDate,currentTime} = response.results[0];
            const ownAirlineCode =response.results && response.results[0]&& response.results[0].ownAirlineCode?response.results[0]&& response.results[0].ownAirlineCode:'';
            let partnerCarriers = response.results && response.results[0]&& response.results[0].partnerCarriers?response.results[0]&& response.results[0].partnerCarriers:{};
             dispatch( { type: constant.TRANSFER_POPUP_OPEN,selectedMailbags,currentDate,currentTime,ownAirlineCode:ownAirlineCode,partnerCarriers:partnerCarriers });
         }
        
        
        
   }
   else if( response.status==="success"&& (mode ===constant.RETURN || mode ===constant.DAMAGE_CAPTURE))
   {
    const { args } = values;
    let selectedMail = null;
    if(args.index){
        selectedMail=args.index;
    }
    dispatch({type:constant.MAILBAG_RETURN_OPEN,selectedMail, mode})
   }
}
 export const onMailRowSelect=(values)=> {
      const { args, dispatch,  } = values;
     const  selectedMailbagsArr  = args;
     dispatch({ type: constant.ON_ROW_SELECT,selectedMailbags:selectedMailbagsArr});
     
}


export function validateFlight(values) {

    const {args,dispatch, getState } = values;
    const state = getState();
    const {fromScreen,action} = args;
    let selectedMailbags = (state.mailbagReducer.selectedMailbags) ? state.mailbagReducer.selectedMailbags : []
    let url = '';
    let flightCarrierCode = '';
    let flightNumber = '';
    let assignToFlight = '';
    let flightDate = '';
    let destination = '';
    let carrierCode = '';

    if (fromScreen === constant.REASSIGN) {
        assignToFlight = state.form.reassignForm.values.reassignFilterType === 'F' ? 'FLIGHT' : 'CARRIER';
        carrierCode = state.form.reassignForm.values.carrierCode?state.form.reassignForm.values.carrierCode:'';
        flightCarrierCode = state.form.reassignForm.values.flightnumber?state.form.reassignForm.values.flightnumber.carrierCode:'';
        flightNumber = state.form.reassignForm.values.flightnumber?state.form.reassignForm.values.flightnumber.flightNumber:'';
        
        flightDate = state.form.reassignForm.values.flightnumber?state.form.reassignForm.values.flightnumber.flightDate:'';
        destination = state.form.reassignForm.values.destination?state.form.reassignForm.values.destination:'';
    } else {

        carrierCode = state.form.transferForm.values.carrierCode?state.form.transferForm.values.carrierCode:'';
        flightCarrierCode = state.form.transferForm.values.flightnumber?state.form.transferForm.values.flightnumber.carrierCode:'';
        flightNumber = state.form.transferForm.values.flightnumber?state.form.transferForm.values.flightnumber.flightNumber:'';
        assignToFlight = state.form.transferForm.values.transferFilterType === 'F' ? 'FLIGHT' : 'CARRIER';
        flightDate = state.form.transferForm.values.flightnumber?state.form.transferForm.values.flightnumber.flightDate:'';

    }
    let oneTimeValues = state.commonReducer.oneTimeValues;
    //selectedMailbags = [mailbags[selectedMail]];
    const data = { selectedMailbags, oneTimeValues,carrierCode, flightCarrierCode, flightNumber, assignToFlight, flightDate, destination };


    url = 'rest/mail/operations/mailbagenquiry/validateFlightDetailsForEnquiry';

    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        handleResponseMailbagActions(dispatch, response, action);
        return response
    })
        .catch(error => {
            return error;
        });


}

export function listContainers(values) {

    const {args,dispatch, getState } = values;
    const state = getState();
    const {  action,fromScreen } = args;
    let selectedMailbags = (state.mailbagReducer.selectedMailbags) ? state.mailbagReducer.selectedMailbags : []
    const flightValidation = (state.mailbagReducer.flightValidation) ? state.mailbagReducer.flightValidation : {}
    let url = '';
    let flightCarrierCode = '';
    let flightNumber = '';
    let assignToFlight = '';
    let flightDate = '';
    let destination = '';
    let carrierCode = '';
    fromScreen === constant.REASSIGN?assignToFlight = state.form.reassignForm.values.reassignFilterType === 'F' ? 'FLIGHT' : 'CARRIER':"";

    if (fromScreen === constant.REASSIGN) {
        
         if('FLIGHT'=== assignToFlight){

          }
        carrierCode = state.form.reassignForm.values.carrierCode?state.form.reassignForm.values.carrierCode:'';
        flightCarrierCode = state.form.reassignForm.values.flightnumber?state.form.reassignForm.values.flightnumber.carrierCode:'';
        flightNumber = state.form.reassignForm.values.flightnumber?state.form.reassignForm.values.flightnumber.flightNumber:'';        
        flightDate = state.form.reassignForm.values.flightnumber?state.form.reassignForm.values.flightnumber.flightDate:'';
        destination = state.form.reassignForm.values.destination?state.form.reassignForm.values.destination:'';
    } else {

        carrierCode = state.form.transferForm.values.carrierCode?state.form.transferForm.values.carrierCode:'';
        flightCarrierCode = state.form.transferForm.values.flightnumber?state.form.transferForm.values.flightnumber.carrierCode:'';
        flightNumber = state.form.transferForm.values.flightnumber?state.form.transferForm.values.flightnumber.flightNumber:'';
        assignToFlight = state.form.transferForm.values.transferFilterType === 'F' ? 'FLIGHT' : 'CARRIER';
        flightDate = state.form.transferForm.values.flightnumber?state.form.transferForm.values.flightnumber.flightDate:'';

    }
    let oneTimeValues = state.commonReducer.oneTimeValues;
    //selectedMailbags = [mailbags[selectedMail]];
    const data = { selectedMailbags, oneTimeValues, carrierCode, flightCarrierCode, flightNumber, assignToFlight, flightDate, destination, flightValidation };


    url = 'rest/mail/operations/mailbagenquiry/validateMailbagDetailsForEnquiry';

    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        handleResponseMailbagActions(dispatch, response, action, destination, assignToFlight, carrierCode);
        return response
    })
        .catch(error => {
            return error;
        });


}

function handleResponseMailbagActions(dispatch, response, action,inputDestination, assignToFlight, inputCarrierCode) {
    
    if (!isEmpty(response.results)) {
        if (action === constant.VIEW_DAMAGE) {
            const { damagedMailbags } = response.results[0];
            dispatch({ type: constant.DAMAGE_SUCCESS, damagedMailbags });
        } else if (action === constant.DELIVER_MAIL) {
           
            dispatch({ type: constant.DELIVER_SUCCESS });
        } else if (action === constant.VALIDATE_FLIGHT) {
            const { flightValidation, selectedMailbags ,port, flightCarrierCode, flightDate, flightNumber} = response.results[0];
            const flightnumber = {carrierCode:flightCarrierCode, flightDate: flightDate, flightNumber:flightNumber};
            const pous=buildPous(flightValidation.flightRoute,port);
            let pou=null;
            if (!isEmpty(pous)) {
                pou=pous[0].value;
            }
            dispatch({ type: constant.FLIGHT_SUCCESS, flightValidation, selectedMailbags,pous,pou, flightnumber });
        } else if (action === constant.LIST_REASSIGN_CONTAINER) {
            const { containerDetails, destination, assignToFlight} = response.results[0];
            let assigned = '';
            assignToFlight==='FLIGHT'?assigned='F':assigned='C';
            dispatch({ type: constant.LIST_REASSIGN_CONTAINER, containerDetails, destination, assigned,inputCarrierCode });
        } else {
            const { mailbags} = response.results[0];
            if (action === 'LIST' && mailbags.results>0) {
                dispatch({ type: constant.LIST_SUCCESS, mailbags });
            }
        }

    } else {
        if (!isEmpty(response.errors)) {
            if (action === constant.VIEW_DAMAGE) {
                dispatch({ type: constant.NO_DAMAGE });
            }else if (action === constant.LIST_REASSIGN_CONTAINER) {
                //dispatch({ type: constant.NO_CONTAINER });
                let assigned = '';
                assignToFlight==='FLIGHT'?assigned='F':assigned='C';
                dispatch({ type: constant.NO_CONTAINER , inputDestination, assigned, inputCarrierCode});
            }else if (action === constant.DO_RETURN) {
                dispatch({ type: constant.NO_DATA });
            }else if (action === constant.DO_REASSIGN) {
                dispatch({ type: constant.NO_DATA });
           }else if ((action === constant.DO_TRANSFER) || (action === constant.VALIDATE_FLIGHT)) {
                dispatch({ type: constant.NO_DATA });
            }else {
                dispatch({ type: constant.CLEAR_TABLE });
            }

        }
    }

    
}

export function doReturnMail(values) {

    const { args, dispatch, getState } = values;
    const state = getState();
    const {  action } = args;
    
    let selectedMailbags=[];
    let url = '';
    let damagedMailbagList = state.form.damageData.values.damageData;
    let damagedMailbags =[];
    if(damagedMailbagList!=null && damagedMailbagList.length>0){
    for(let i=0;i< damagedMailbagList.length;i++){
        let damageData={
        fileName :  damagedMailbagList[i].fileData&&damagedMailbagList[i].fileData.length>0?damagedMailbagList[i].fileData[0].name:null,
        damageCode :  damagedMailbagList[i].damageCode,
        remarks : damagedMailbagList[i].remarks};
        damagedMailbags.push(damageData);
    }
}
    let oneTimeValues = state.commonReducer.oneTimeValues;
    let returnMailFlag='N';
    if(state.form.returnMailForm.values!=null){
     returnMailFlag = (state.form.returnMailForm.values.isReturnMail)?'Y':'N';
    }
    let indexes=[];
    if(state.mailbagReducer.selectedMailbagsIndex&&state.mailbagReducer.selectedMailbagsIndex.length>0){
       indexes=state.mailbagReducer.selectedMailbagsIndex;
    }else{
        indexes.push(state.mailbagReducer.selectedMail);
    }
    
    
    // const selectedContainer = state.containerReducer.selectedContainer;
    for(var i=0; i<indexes.length;i++) {
         selectedMailbags.push(state.mailbagReducer.mailBags.results[indexes[i]]);
        
    }
    //selectedMailbags = state.mailbagReducer.selectedMailbags;
    const data = { selectedMailbags, damagedMailbags, oneTimeValues,returnMailFlag };


    url = 'rest/mail/operations/mailbagenquiry/returnMailbags';

    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        handleResponseonAdding(dispatch, response, action);
        return response
    })
        .catch(error => {
            return error;
        });


}


export function onImportMailbags(values) {

    const {  dispatch, getState } = values;
    const state = getState();
    let newMailbags=[];
    let url='';
    let importFromContainer = (state.form.addMailbagImport) ? state.form.addMailbagImport.values : {};
    let selectedContainer = state.containerReducer.selectedContainer ? state.containerReducer.selectedContainer:{}
    importFromContainer.flightDate = selectedContainer.flightDate;
    let existingMailbags=state.mailbagReducer.mailBags?state.mailbagReducer.mailBags.results:[];
    newMailbags = state.form.newMailbagsTable ? state.form.newMailbagsTable.values.newMailbagsTable : null;
    let mailbags=[];
    if (!isEmpty(existingMailbags)) {
        mailbags = existingMailbags.map((value) => ({
            ...value,
            mailbagWeight: value.mailbagWeight.roundedDisplayValue, displayUnit: value.mailbagWeight.displayUnit
        }));
    }
    if (!isEmpty(newMailbags)) {
        mailbags = newMailbags.map((value) => ({
            ...value,
            mailbagWeight: value.mailbagWeight.roundedDisplayValue, displayUnit: value.mailbagWeight.displayUnit
        }));
    }
   
    const data = { mailbags, importFromContainer, selectedContainer: { ...selectedContainer, mailbagpagelist: null, mailbagdsnviewpagelist: null, dsnviewpagelist: null } }
    url = 'rest/mail/operations/outbound/importMailbags';

    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        if (!isEmpty(response.results)) {
            let { importedmailbags } = response.results[0];
            if (!isEmpty(importedmailbags)) {
        for (var i = 0; i < importedmailbags.length; i++) {
                    let mailbagWeight = {
                        displayValue: importedmailbags[i].mailbagId.substring(25, 29),
                        roundedDisplayValue: importedmailbags[i].mailbagWeight,
                        displayUnit: importedmailbags[i].displayUnit
                    };
                    importedmailbags[i].mailbagWeight = mailbagWeight;
                    importedmailbags[i].__opFlag = "I";
        state.form.newMailbagsTable.values.newMailbagsTable.push(importedmailbags[i]);
        }
            }
            const { selectedContainer } = response.results[0];
            let  importedMailbagDetails  = state.mailbagReducer.importedMailbagDetails;
            if (!isEmpty(importedMailbagDetails)) {
                importedmailbags.push(...importedMailbagDetails);
            }
            dispatch({ type: constant.CLOSE_IMPORT_POPUP, selectedContainer: selectedContainer, importedMailbagDetails: importedmailbags });
        }
        return response;
    }).catch(error => {
            return error;
        });


}


function handleResponseOnPopulateMailbagId(dispatch,response,index,selectedMailbagRow,state) {
    if (!isEmpty(response.results)) {
                      
        const mailbag = response.results[0] && response.results[0].mailbags && response.results[0].mailbags[0] ?
            response.results[0].mailbags[0] : {};
        if (!isEmpty(mailbag)) {
            dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.ooe`, mailbag.ooe));
            dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.doe`, mailbag.doe));
            dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.mailCategoryCode`, mailbag.mailCategoryCode));
            dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.mailSubclass`, mailbag.mailSubclass));
            dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.year`, mailbag.year));
            dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.despatchSerialNumber`, mailbag.despatchSerialNumber));
            dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.receptacleSerialNumber`, mailbag.receptacleSerialNumber));
            dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.highestNumberedReceptacle`, mailbag.highestNumberedReceptacle));
            dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.registeredOrInsuredIndicator`, mailbag.registeredOrInsuredIndicator));
            dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.mailorigin`, mailbag.mailorigin));
            dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.mailDestination`, mailbag.mailDestination));
            //state.form.newMailbagsTable.values.newMailbagsTable[index].mailbagWeight=selectedMailbagRow.mailbagId.substring(25, 29);
            //let name = `newMailbagsTable.${index}.mailbagWeight`
            let volName = `newMailbagsTable.${index}.mailbagVolume`
            let mailWeight = populateMailWeightDomestic(selectedMailbagRow.mailbagId.substring(10, 12), state.form.newMailbagsTable.values.newMailbagsTable[index].mailbagWeight.displayUnit, dispatch);
            dispatch(change(constant.NEW_MAILBAGS_TABLE, `newMailbagsTable.${index}.mailbagWeight`, { displayValue: selectedMailbagRow.mailbagId.substring(10, 12), roundedDisplayValue: selectedMailbagRow.mailbagId.substring(10, 12), displayUnit: 'L' }));
            let mailVolume = populateVolume(selectedMailbagRow.mailbagVolume, mailWeight, state.mailbagReducer.density,state.form.newMailbagsTable.values.newMailbagsTable[index].mailbagWeight.displayUnit,state.mailbagReducer.stationVolUnt)
            dispatch(change(constant.NEW_MAILBAGS_TABLE, volName, mailVolume));
            const warningMapValue = { ["mail.operation.domesticmaildoesnotexistwarning"]: 'N' };
            dispatch({ type: constant.SET_WARNING_MAP, warningMapValue });
        }
        
    }
}


export function validateNewMailbagAdded(data) {
    let error = ""
    let isValid = true;
    let tabledata=[];
    tabledata = data;
    for(var i=0;i<tabledata.length;i++) {
        let mailbag=tabledata[i];
        if(!mailbag.ooe) {
            isValid=false;
            error="Please enter Origin of exchange";
        }
        else if(!mailbag.doe) {
            isValid=false;
            error="Please enter Dest of exchange";
        }
        else if(!mailbag.mailCategoryCode) {
            isValid=false;
            error="Please enter Category Code";
        }
        else if(!mailbag.mailSubclass) {
            isValid=false;
            error="Please enter Mail subclass";
        }
        // else if(!mailbag.year) {
        //     isValid=false;
        //     error="Please enter Year";
        // }
        else if(!mailbag.despatchSerialNumber) {
            isValid=false;
            error="Please enter DSN";
        }
        else if(!mailbag.receptacleSerialNumber) {
            isValid=false;
            error="Please enter RSN";
        }  
       
        else if (!mailbag.mailbagWeight.displayValue) {
            isValid=false;
                error="Please enter Weight";
        }
       
    }

   
    let validObject = {
        valid: isValid,
        msg: error
    }
    return validObject;
}

export function closeMailbagPopup(values) {
    const { dispatch,getState } = values;
    const state = getState();
    if(state.mailbagReducer.activeMailbagAddTab==='NORMAL_VIEW'){
    dispatch(reset(constant.NEW_MAILBAGS_TABLE));
    }
    const warningMapValue = { ['mailtracking.defaults.war.coterminus']: 'N' };
    dispatch({ type: constant.SET_WARNING_MAP, warningMapValue });
    dispatch({ type: constant.ADD_MAILBAG_POPUPCLOSE })
}

/*export const onApplyMailBagFilter=(values)=> {
    const {dispatch, getState } = values;
    const state = getState();
    const tableFilter  = (state.form.mailbagFilter.values)?state.form.mailbagFilter.values:{}
    tableFilter.undefined?delete tableFilter.undefined:''; 
    const { flightnumber, ...rest } = tableFilter;
    const mailbagFilter={...flightnumber,...rest};
    dispatch( { type: constant.LIST_FILTER,mailbagFilter});
    return Object.keys(mailbagFilter).length;               
}
export const onClearMailbagFilter=(values)=> {
    const {dispatch} = values;  
    dispatch({ type: constant.CLEAR_TABLE_FILTER});  
    dispatch(reset('mailbagFilter'));
}*/
export function onreassignMailSave(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const { containerIndex,paContainerNumberUpdate,paBuiltFlagUpdate,retainFlag } = args;
    const flightValidation = (state.mailbagReducer.flightValidation) ? state.mailbagReducer.flightValidation : {}
    const containers = (state.mailbagReducer.reassignContainers) ? state.mailbagReducer.reassignContainers : {}
    let selectedContainer = (state.mailbagReducer.reassignContainers) ? state.mailbagReducer.reassignContainers[containerIndex] : {}
    let selectedMailbags = (state.mailbagReducer.selectedMailbags) ? state.mailbagReducer.selectedMailbags : []
   
    if (paBuiltFlagUpdate) {
        selectedMailbags = selectedMailbags.map((value) => ({ ...value,paBuiltFlagUpdate:true,acceptancePostalContainerNumber:selectedContainer.containerNumber}));
    }
    else if (paContainerNumberUpdate){
        selectedMailbags = selectedMailbags.map((value) => ({ ...value,paContainerNumberUpdate:true,acceptancePostalContainerNumber:null}));
    }
    else if(retainFlag) {
        //when mailbag reassigned from Non PA-Built container to PA-Built container. Also when user selects 'yes' in the warning popups.
        selectedMailbags = selectedMailbags.map((value) => ({...value,acceptancePostalContainerNumber:value.paBuiltFlag=='Y' ? value.acceptancePostalContainerNumber: selectedContainer.containerNumber,paContainerNumberUpdate: value.paBuiltFlag=='Y' ? false:true}));
    }
    else {
        if(selectedContainer!==undefined){
        selectedMailbags = selectedMailbags.map((value) => ({...value,acceptancePostalContainerNumber:selectedContainer.paBuiltFlag=='Y' ? selectedContainer.containerNumber :value.acceptancePostalContainerNumber ,paContainerNumberUpdate: selectedContainer.paBuiltFlag=='Y' ? true:false}));
    }
    }
    let url = '';
    let flightCarrierCode = '';
    let flightNumber = '';
    let assignToFlight = '';
    let flightDate = '';
    let destination = '';
    let carrierCode = '';
    let scanDate = '';
    let scanTime = '';
			   

    scanDate = state.form.reassignForm.values.scanDate?state.form.reassignForm.values.scanDate:'';
    scanTime = state.form.reassignForm.values.scanTime?state.form.reassignForm.values.scanTime:'';
    carrierCode = state.form.reassignForm.values.carrierCode?state.form.reassignForm.values.carrierCode:'';
    flightCarrierCode = state.form.reassignForm.values.flightnumber?state.form.reassignForm.values.flightnumber.carrierCode:'';
    flightNumber = state.form.reassignForm.values.flightnumber?state.form.reassignForm.values.flightnumber.flightNumber:'';
    assignToFlight = state.form.reassignForm.values.reassignFilterType === 'F' ? 'FLIGHT' : 'CARRIER';
    flightDate = state.form.reassignForm.values.flightnumber?state.form.reassignForm.values.flightnumber.flightDate:'';
    destination = state.form.reassignForm.values.destination?state.form.reassignForm.values.destination:'';

    let oneTimeValues = state.commonReducer.oneTimeValues;
    selectedContainer = containers[containerIndex];
    if((selectedContainer === undefined||selectedContainer==='')){
        return Promise.reject(new Error("Please select a container")); 
    }
    let warningMessagesStatus = state.commonReducer.warningMessagesStatus;

    const data = { scanDate, scanTime, flightValidation,selectedMailbags, oneTimeValues, carrierCode,flightCarrierCode, flightNumber, assignToFlight, flightDate, destination, selectedContainer,warningMessagesStatus };
 
    url = 'rest/mail/operations/mailbagenquiry/reassignMailbags';

    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        handleResponseOnReassignSave(dispatch, response, 'CONTAINER_REASSIGN_SUCCESS');
        return response
    })
        .catch(error => {
            return error;
        });
}

function handleResponseOnReassignSave(dispatch, response,action) {
    if (isEmpty(response.errors)) {
        dispatch({ type: action});
        const warningMapValue = { ['mail.operations.securityscreeningwarning']:'N' };
        dispatch({ type: constant.SET_WARNING_MAP, warningMapValue });
}
}

export function saveNewContainer(values) {

    const { args, dispatch, getState } = values;
    const state = getState();
    //const {  fromScreen } = args;
    let flightValidation = (state.mailbagReducer.flightValidation) ? state.mailbagReducer.flightValidation : {}
    let selectedContainer=   {};
     let url = '';
    let flightCarrierCode = '';
    let flightNumber = '';
    let assignToFlight = '';
    let flightDate = '';
    let destination = '';
    let carrierCode = '';
    let scanDate = '';
    let scanTime = '';
    if(args&&args.fromScreen=="TRANSFER"){
    scanDate = state.form.transferMailForm.values.scanDate?state.form.transferMailForm.values.scanDate:'';
    scanTime = state.form.transferMailForm.values.mailScanTime?state.form.transferMailForm.values.mailScanTime:'';
    carrierCode = state.form.transferMailForm.values.carrier?state.form.transferMailForm.values.carrier:'';
    flightCarrierCode = state.form.transferMailForm.values.flightnumber?state.form.transferMailForm.values.flightnumber.carrierCode:'';
    flightNumber = state.form.transferMailForm.values.flightnumber?state.form.transferMailForm.values.flightnumber.flightNumber:'';
    assignToFlight = state.form.transferMailForm.values.transferFilterType === 'F' ? 'FLIGHT' : 'CARRIER';
    flightDate = state.form.transferMailForm.values.flightnumber?state.form.transferMailForm.values.flightnumber.flightDate:'';
    destination = state.form.transferMailForm.values.uldDestination?state.form.transferMailForm.values.uldDestination:'';
   
    if('FLIGHT'=== assignToFlight){
         selectedContainer.containerNumber = state.form.transferMailForm.values.uldNumber?state.form.transferMailForm.values.uldNumber:'';
         selectedContainer.type = state.form.transferMailForm.values.barrowFlag===true?'B':'U';
         selectedContainer.remarks = state.form.transferMailForm.values.uldRemarks?state.form.transferMailForm.values.uldRemarks:'';
         selectedContainer.pou = state.form.transferMailForm.values.pou?state.form.transferMailForm.values.pou:'';
         selectedContainer.uldDestination = state.form.transferMailForm.values.uldDestination?state.form.transferMailForm.values.uldDestination:'';
         selectedContainer.paBuiltFlag = state.form.transferMailForm.values.paBuilt===true?'Y':'N';        
         selectedContainer.finalDestination = state.form.transferMailForm.values.uldDestination?state.form.transferMailForm.values.uldDestination:'';
        let mailacceptance=state.mailbagReducer?(state.mailbagReducer.flightDetailsToBeReused):{};
         flightValidation = {companyCode:mailacceptance.companyCode,carrierCode:mailacceptance.carrierCode,
            flightCarrierId:mailacceptance.carrierId,flightNumber:mailacceptance.flightNumber,
            flightSequenceNumber:mailacceptance.flightSequenceNumber,legSerialNumber:mailacceptance.legSerialNumber,flightRoute:mailacceptance.flightRoute,
            flightDate:mailacceptance.flightDate,aircraftType:mailacceptance.aircraftType,flightType:mailacceptance.flightType,
            operationalStatus:mailacceptance.flightOperationalStatus,flightStatus:mailacceptance.flightStatus,
            applicableDateAtRequestedAirport:mailacceptance.flightDate};
    }else{
        selectedContainer.containerNumber = state.form.transferMailForm.values.uldNumber?state.form.transferMailForm.values.uldNumber:'';
         selectedContainer.type = state.form.transferMailForm.values.barrowFlag===true?'B':'U';
         selectedContainer.remarks = state.form.transferMailForm.values.uldRemarks?state.form.transferMailForm.values.uldRemarks:'';
         selectedContainer.pou = state.form.transferMailForm.values.pou?state.form.transferMailForm.values.pou:'';
         selectedContainer.uldDestination = state.form.transferMailForm.values.uldDestination?state.form.transferMailForm.values.uldDestination:'';
         selectedContainer.paBuiltFlag = state.form.transferMailForm.values.paBuilt===true?'Y':'N';        
         selectedContainer.finalDestination = state.form.transferMailForm.values.uldDestination?state.form.transferMailForm.values.uldDestination:'';
    
    }
   }
   else{
    scanDate = state.form.reassignForm.values.scanDate?state.form.reassignForm.values.scanDate:'';
    scanTime = state.form.reassignForm.values.scanTime?state.form.reassignForm.values.scanTime:'';
    carrierCode = state.form.reassignForm.values.carrierCode?state.form.reassignForm.values.carrierCode:'';
    flightCarrierCode = state.form.reassignForm.values.flightnumber?state.form.reassignForm.values.flightnumber.carrierCode:'';
    flightNumber = state.form.reassignForm.values.flightnumber?state.form.reassignForm.values.flightnumber.flightNumber:'';
    assignToFlight = state.form.reassignForm.values.reassignFilterType === 'F' ? 'FLIGHT' : 'CARRIER';
    flightDate = state.form.reassignForm.values.flightnumber?state.form.reassignForm.values.flightnumber.flightDate:'';
    destination = state.form.reassignForm.values.uldDestination?state.form.reassignForm.values.uldDestination:'';
   
    if('FLIGHT'=== assignToFlight){
         selectedContainer.containerNumber = state.form.reassignForm.values.uldNumber?state.form.reassignForm.values.uldNumber:'';
         selectedContainer.type = state.form.reassignForm.values.barrowFlag===true?'B':'U';
         selectedContainer.remarks = state.form.reassignForm.values.uldRemarks?state.form.reassignForm.values.uldRemarks:'';
         selectedContainer.pou = state.form.reassignForm.values.pou?state.form.reassignForm.values.pou:'';
         selectedContainer.uldDestination = state.form.reassignForm.values.uldDestination?state.form.reassignForm.values.uldDestination:'';
         selectedContainer.paBuiltFlag = state.form.reassignForm.values.paBuilt===true?'Y':'N';        
         selectedContainer.finalDestination = state.form.reassignForm.values.uldDestination?state.form.reassignForm.values.uldDestination:'';
    }else{
        selectedContainer.containerNumber = state.form.reassignForm.values.uldNumber?state.form.reassignForm.values.uldNumber:'';
         selectedContainer.type = state.form.reassignForm.values.barrowFlag===true?'B':'U';
         selectedContainer.remarks = state.form.reassignForm.values.uldRemarks?state.form.reassignForm.values.uldRemarks:'';
         selectedContainer.pou = state.form.reassignForm.values.pou?state.form.reassignForm.values.pou:'';
         selectedContainer.uldDestination = state.form.reassignForm.values.uldDestination?state.form.reassignForm.values.uldDestination:'';
         selectedContainer.paBuiltFlag = state.form.reassignForm.values.paBuilt===true?'Y':'N';        
         selectedContainer.finalDestination = state.form.reassignForm.values.uldDestination?state.form.reassignForm.values.uldDestination:'';
    
    }

   }
    if(!selectedContainer.finalDestination || selectedContainer.finalDestination===''){
        return Promise.reject(new Error("Please enter destination"))   
    }else{
        selectedContainer.destination = selectedContainer.finalDestination
    }
    if('FLIGHT'=== assignToFlight) {
    if(selectedContainer.type === 'B'){
        if(selectedContainer.pou !==  selectedContainer.finalDestination){
            return Promise.reject(new Error("POU and Destination should be same for barrow"))
        }
    }
    }
    let oneTimeValues = state.commonReducer.oneTimeValues;
   
 
         selectedContainer.operationFlag = 'I';
         selectedContainer.flightNumber = flightNumber;
         selectedContainer.carrierCode = carrierCode;
         selectedContainer.assignedPort = state.commonReducer.airportCode;
         let showWarning='Y';
    if(args&&args.showWarning){
        showWarning= args.showWarning;
    }
   
    const data = {showWarning, scanDate, scanTime, flightValidation, oneTimeValues, carrierCode,flightCarrierCode, flightNumber, assignToFlight, flightDate, destination, selectedContainer };


    url = 'rest/mail/operations/mailbagenquiry/saveContainerDetailsForEnquiry';

    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        if(args&&args.fromScreen=="TRANSFER"){   
        handleResponseOnNewContainerSave(dispatch, response,'TRANSFER_SAVE');     
        }
        else{
        handleResponseOnNewContainerSave(dispatch, response,'REASSIGN_SAVE');
        }
        return response
    })
        .catch(error => {
            return error;
        });


}


function handleResponseOnNewContainerSave(dispatch,response,action){
    if (isEmpty(response.errors)) {
    if(action=='TRANSFER_SAVE'){
        dispatch(asyncDispatch(validateFlightDetailsForTransfer)({'action':constant.LIST_TRANSFER})).then((response)=>{
            dispatch(asyncDispatch(listContainerForTransferPanel)({...response,'action':constant.LIST_TRANSFER}))})
    }
    else{
     dispatch(asyncDispatch(listContainers)({ action: constant.LIST_REASSIGN_CONTAINER, fromScreen: constant.REASSIGN }))
        dispatch({ type: constant.ADD_CONTAINER_POPUPCLOSE});
    }
}
    
}

export function openHistoryPopup(values) {
    const { args, dispatch} = values;
    //let selectedMailbags = [];
    let mailbagSelected = {};
    
    if(args){
        mailbagSelected=args.mailbag;
    }
    //selectedMailbags.push(mailbagsSelected.mailbag);
    let data = mailbagSelected;
    let mailbagId=data.mailbagId;
    let mailSequenceNumber=data.mailSequenceNumber;
    let mailBagIds=mailbagId;
    let isPopup = 'Y';
    const fromScreenId='mail.operations.ux.mailbagenquiry';
    var url = 'mail.operations.ux.mbHistory.list.do?formCount=1&mailbagId='+encodeURIComponent(mailbagId)+'&fromScreenId='+fromScreenId+'&mailSequenceNumber='+mailSequenceNumber+'&totalViewRecords='+encodeURIComponent(mailBagIds)+'&isPopUp='+isPopup;
    var closeButtonIds = ['btnClose'];
    var optionsArray = {
    url,
    dialogWidth: "1000",
    dialogHeight: "540",
    closeButtonIds: closeButtonIds,
    popupTitle: 'Mailbag History'
    }
    
    dispatch(dispatchAction(openPopup)(optionsArray));
    
    }

    export function navigateToOffload(values){
        const { args, getState,dispatch } = values;
    const state = getState();
    let mailbagSelected = [];
    let mailBags="";
    const mailAcceptance= state.containerReducer.mailAcceptance;
    if(args){
        mailbagSelected=args;
    }

    if(mailbagSelected.length>1){
        mailbagSelected.forEach(function(element) {
            mailBags=mailBags+element.mailbagId+',';
        }, this);
    }
    else{
        mailBags=mailbagSelected[0].mailbagId;
    }

        const data = {  carrierCode: mailAcceptance.carrierCode,
            flightNumber: mailAcceptance.flightNumber,
            flightDate: mailAcceptance.flightDate,
            containerNumber: mailbagSelected[0].containerNumber,
            mailbags:mailBags,
            fromScreen : "mail.operations.ux.mailoutbound",
            pageURL: "mail.operations.ux.mailoutbound",
            operation: "offloadMailbag" }
            if(mailAcceptance.flightOperationalStatus === 'C') {
    
                navigateToScreen("mail.operations.ux.offload.defaultscreenload.do", data);
                }
                else {
                    dispatch(requestValidationError('The mail cannot be offloaded from an opened flight', ''));
                }
        
    }

    export function allMailbagIconAction(values){   
        const { dispatch, getState } = values;
        const state = getState();
        let mailoutboundDetails=state.containerReducer&&state.containerReducer.mailAcceptance?(state.containerReducer.mailAcceptance):{};
        if(isEmpty(mailoutboundDetails)){
            return;
        }
        //const filterValues=state.filterReducer.filterValues;
        //const fromDate=filterValues.fromDate?filterValues.fromDate:''
        //const toDate=filterValues.toDate?filterValues.toDate:''
        //const airportCode= filterValues.airportCode?filterValues.airportCode:'';
        //const carrierCode=mailoutboundDetails.carrierCode?mailoutboundDetails.carrierCode:'';
        //const flightNumber=mailoutboundDetails.flightNumber?mailoutboundDetails.flightNumber:'';
        //const flightDate = mailoutboundDetails.flightDate?mailoutboundDetails.flightDate.split(' ')[0]:'';
        const airportCode = state.mailbagReducer.selectedContainer.airportCode ?state.mailbagReducer.selectedContainer.airportCode:'';
        const carrierCode = state.mailbagReducer.selectedContainer.carrierCode ? state.mailbagReducer.selectedContainer.carrierCode:'';
        let flightNumber = state.mailbagReducer.selectedContainer.flightNumber ? state.mailbagReducer.selectedContainer.flightNumber:'';
        let fromDate = '';
        let toDate = '';
        if(flightNumber=='-1' || flightNumber==0) {
            flightNumber='';
            //due to performance issue , hard coding value based on the soncy comment
            fromDate = moment(state.commonReducer.fromDate,'DD-MMM-YYYY').subtract('days',2).format('DD-MMM-YYYY');
            toDate = moment(state.commonReducer.fromDate,'DD-MMM-YYYY').add('days',1).format('DD-MMM-YYYY');
        }
        const flightDate = state.mailbagReducer.selectedContainer.flightDate ? state.mailbagReducer.selectedContainer.flightDate:'';
        const containerNumber = state.mailbagReducer.selectedContainer.containerNumber ;
        var url = "mail.operations.ux.mailbagenquiry.defaultscreenload.do?fromScreen=MailOutbound&isPopup=true&containerNumber="+containerNumber+'&airport='+airportCode+
        '&fromDate='+fromDate+'&toDate='+toDate+'&carrierCode='+carrierCode+'&flightDate='+flightDate+'&flightNumber='+flightNumber ;
     
        var optionsArray = {
            url,
            dialogWidth: "990",
            dialogHeight: "550",
          
            popupTitle: 'All Mailbags'
            }
        dispatch(dispatchAction(openPopup)(optionsArray));
    }
    
    

    export function performDSNAction(values) {
        const { args, dispatch, getState } = values;
        const state = getState();
        let indexes=[];
        let data={}
        let url = '';
        let mode=args.mode;
        // let mailAcceptance=''
        // let selectedDSN = '';
        let despatchDetailsList=[];
        // const flightCarrierflag=state.commonReducer.flightCarrierflag
        let containerDetailsCollection=[]
        if(args.index) {
            indexes.push(args.index);
            // selectedDSN = state.mailbagReducer.mailBagsdsnView.results[args.index];
           
        }
        else {
            indexes=state.mailbagReducer.selectedDSNIndex;
        }
        
        for(var i=0; i<indexes.length;i++) {
            despatchDetailsList.push(state.mailbagReducer.mailBagsdsnView.results[indexes[i]]);
        }
        let selectedContainer=state.containerReducer.selectedContainer
        selectedContainer={...selectedContainer,mailbagpagelist:null,totalWeight:null}
        despatchDetailsList=despatchDetailsList.map((value)=>({...value,weight:null})); 
        containerDetailsCollection.push(selectedContainer);

        // mailAcceptance= state.containerReducer.mailAcceptance
        if(mode===constant.DSN_ATTACH_AWB) {
        
        data={despatchDetailsList,containerDetailsCollection}
        url = 'rest/mail/operations/outbound/screenloadattachawb';
        return makeRequest({
            url,
            data: {...data}
        }).then(function (response) {
            handleResponseOnDSNActions(dispatch,response,constant.DSN_ATTACH_AWB);
            return response
        })
        .catch(error => {
            return error;
        });
        
      }
      else if(mode===constant.DSN_ATTACH_ROUTING) {
        
        data={despatchDetailsList}
        url = 'rest/mail/operations/outbound/validateAttachRouting';
        return makeRequest({
            url,
            data: {...data}
        }).then(function (response) {
            handleResponseOnDSNActions(dispatch,response,constant.DSN_ATTACH_ROUTING);
            return response
        })
        .catch(error => {
            return error;
        });
        
      }
      else if(mode===constant.DSN_DETACH_AWB) {
        
        data={containerDetailsCollection,despatchDetailsList}
        url = 'rest/mail/operations/outbound/detachAWBCommand';
        return makeRequest({
            url,
            data: {...data}
        }).then(function (response) {
            handleResponseOnDSNActions(dispatch,response,constant.DSN_DETACH_AWB);
            return response
        })
        .catch(error => {
            return error;
        });
        
      }
    }


    export function handleResponseOnDSNActions(dispatch,response,mode) {
    
        if(isEmpty(response.errors)){
           
           if(mode === constant.DSN_ATTACH_AWB) { 
                dispatch({type: constant.ATTACH_AWB,attachClose:true,attachAwbDetails:response.results[0].attachAwbDetails,
                            containerDetailsToBeReused:response.results[0].containerDetailsCollection,attachAwbOneTimeValues:response.results[0].oneTimeValues});
            }
            if(mode === constant.DSN_DETACH_AWB) { 
                dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
                    dispatch(asyncDispatch(listmailbagsinContainers)());
                  });
            }
    
            else if (mode === constant.DSN_ATTACH_ROUTING) {
                if(isEmpty(response.errors)){
                dispatch({type: constant.ATTACH_ROUTING, attachRoutingClose: true, attachRoutingDetails:response.results[0].attachRoutingDetails,
                             createMailInConsignmentVOs: response.results[0].createMailInConsignmentVOs});
               } 
               else {
                dispatch({type: constant.ATTACH_ROUTING_ERROR}); 
            }  
            }
            
            
        }
        
    }

    export function onReassignExistingmailbags(values) {
        const { args, dispatch, getState } = values;
        const state = getState();
        let fromDeviationList = false;
        if(state.commonReducer.activeMainTab === 'DEVIATION_LIST') {
            fromDeviationList = true;
        }
        const flightCarrierflag = state.commonReducer.flightCarrierflag
        const mailAcceptance= state.containerReducer.mailAcceptance
        const selectedContainer = (state.mailbagReducer.selectedContainer) ? state.mailbagReducer.selectedContainer : '';
        let selectedMailbags = state.form.newMailbagsTable ? state.form.newMailbagsTable.values.newMailbagsTable:[];
        let existingMailbags = state.mailbagReducer.existingMailbags ? state.mailbagReducer.existingMailbags:[];
        let mailbags = state.mailbagReducer.addedMailbags? state.mailbagReducer.addedMailbags:[]
        mailbags = mailbags.map((value) => ({ ...value,strWeight:null,mailbagWeight:value.weight.roundedDisplayValue,displayUnit:value.weight.displayUnit,volume:null}));
        //existingMailbags = existingMailbags.map((value) => ({ ...value,strWeight:null,mailbagWeight:value.weight.roundedDisplayValue,displayUnit:value.weight.displayUnit,volume:null,mailId:value.mailbagId}));
    
        let reassignFlag=[];
        if(args.action ===constant.SAVE_REASSIGN) {
           for(var i=0;i<selectedMailbags.length;i++) {
              if(selectedMailbags[i].reassign === 'Y' || selectedMailbags[i].reassign === true) {
                reassignFlag.push(i);
              }else{
                  for(let j =0; j<mailbags.length; j++){
                      if(selectedMailbags[i].mailbagId === mailbags[j].mailbagId){
                        mailbags.splice(j,1) ;
                        j--; 
                      }
                  }
              }
            }
         } 
        const data = { selectedContainer: { ...selectedContainer,operationFlag:'U',containerOperationFlag:'U',mailbagdsnviewpagelist: null, mailbagpagelist: null ,dsnviewpagelist:null,totalWeight:null}, flightCarrierflag,mailAcceptance : {...mailAcceptance , fromDeviationList:fromDeviationList, containerPageInfo:null,totalContainerWeight:null},existingMailbags,reassignFlag,mailbags}
        const url = 'rest/mail/operations/outbound/reassignExistingMailbags';
      return makeRequest({
          url,
          data: { ...data }
      }).then(function (response) {
          handleResponseonAdding(dispatch, response,constant.REASSIGN_EXISTING_MAILBAGS);
          return response
      })
          .catch(error => {
              return error;
          });
        // /dispatch({ type: 'EXISTING_MAILBAG_POPUP_CLOSE'});  
    }

export function onApplyMailbagSort(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const getMailbagSortDetails = () => args;
    const getMailbagDetails = () => state.mailbagReducer && state.mailbagReducer.mailBags ?
        state.mailbagReducer.mailBags.results : [];
    const getMailbagSortedDetails = createSelector([getMailbagSortDetails, getMailbagDetails], (sortDetails, mailbags) => {
        if (sortDetails) {
            const sortBy = sortDetails.sortBy;
            const sortorder = sortDetails.sortByItem;
            mailbags.sort((record1, record2) => {
                let sortVal = 0;
                let data1;
                let data2;
                data1 = record1[sortBy] && typeof record1[sortBy] === "object" ? record1[sortBy].systemValue : record1[sortBy];
                data2 = record2[sortBy] && typeof record2[sortBy] === "object" ? record2[sortBy].systemValue : record2[sortBy];
                if (data1 === null) {
                    data1 = '';
                }
                if (data2 === null) {
                    data2 = '';
                }
                if (data1 > data2) {
                    sortVal = 1;
                }
                if (data1 < data2) {
                    sortVal = -1;
                }
                if (sortorder === 'DSC') {
                    sortVal = sortVal * -1;
                }
                return sortVal;
            });
        }
        return mailbags;
    });

    let mailbagData = getMailbagSortedDetails();
    let pagedmailbagData = state.mailbagReducer && state.mailbagReducer.mailBags ?
        state.mailbagReducer.mailBags : [];
    pagedmailbagData = { ...pagedmailbagData, results: mailbagData };
    dispatch({ type: 'APPLY_MAILBAG_SORT', mailbagDataAfterSort: pagedmailbagData });
}


export function onApplyMailbagFilter(values) {
    const { dispatch, getState } = values;
    /*
    const formValues = getState().form.mailbagFilter.values;
    const tableFilter  = ( getState().form.mailbagFilter.values)? getState().form.mailbagFilter.values:{}
    tableFilter.undefined?delete tableFilter.undefined:''; 
    const { flightnumber, ...rest } = tableFilter;
    const mailbagfilter={...flightnumber,...rest};
    if (isEmpty(formValues)) {
        return;
    }
    const state = getState();
    const getTableResults = () =>
    state.mailbagReducer && state.mailbagReducer.allMailBags ?
        state.mailbagReducer.allMailBags : []

    let mailbagData = [];
    const filterValues =
        state.form.mailbagFilter.values ?
            state.form.mailbagFilter.values : {};

    Object.keys(filterValues).forEach((key) => (filterValues[key] === ''||filterValues[key] === null) && delete filterValues[key]); 

    const getTableFilter = () =>  isEmpty(filterValues)? {}: filterValues;
        

    const getMailbags = createSelector([getTableResults, getTableFilter], (results, filter) => {
        return results.filter((obj) => {
            
            const anotherObj = { ...obj, ...filter}; 
            if(JSON.stringify(obj)===JSON.stringify(anotherObj))
                 return true;
            else 
               return false 
           } )
    })

    mailbagData = getMailbags();
    let pagedmailbagData = state.mailbagReducer && state.mailbagReducer.mailBags ?
        state.mailbagReducer.mailBags : [];
    pagedmailbagData = { ...pagedmailbagData, results: mailbagData };
    dispatch({ type: 'APPLY_MAILBAG_FILTER', mailbagDataAfterFilter: pagedmailbagData,mailbagfilter });
    */
   return dispatch(asyncDispatch(listmailbagsinContainers)({applyFilter:true,mailbagsDisplayPage:1,mailbagsDSNDisplayPage:1}));
}
export function onClearMailbagFilter(values) {
    const { dispatch, getState } = values;
    const state = getState();
    //const formValues= state.form.mailbagFilter.values;  
    let mailbagData = state.mailbagReducer && state.mailbagReducer.allMailBags ?
        state.mailbagReducer.allMailBags : [];
    let pagedmailbagData = state.mailbagReducer && state.mailbagReducer.mailBags ?
        state.mailbagReducer.mailBags : [];
    pagedmailbagData = { ...pagedmailbagData, results: mailbagData };
    dispatch(reset('mailbagFilter'));
    dispatch({ type: 'APPLY_MAILBAG_FILTER', mailbagDataAfterFilter: pagedmailbagData });
}

export function onClearMailbagDSNFilter(values) {
    const { dispatch } = values;
    //dispatch(reset('dsnFilter'));
    dispatch({
        type: "@@redux-form/INITIALIZE", meta: { form: 'dsnFilter', keepDirty: true },
        payload: {}
    })
    dispatch({ type: "@@redux-form/RESET", meta: { form: 'dsnFilter' } })
}

function  buildPous (data,port){
    let destArray=data.split('-');
    destArray.reverse();
    const index = destArray.indexOf(port);
    destArray.splice(index);
    let dest=[];
    destArray.forEach(function(element) {
        dest.push({label:element,value:element});
    }, this);
    return dest.reverse();
}

export function isdomestic(values){
    const {dispatch,args,getState } = values;
    const state = getState();
    const index= args.rowIndex;
    const isdomesticflag = (state.form.newMailbagsTable&&state.form.newMailbagsTable.values&&state.form.newMailbagsTable.values.newMailbagsTable&&state.form.newMailbagsTable.values.newMailbagsTable[index].mailbagId && state.form.newMailbagsTable.values.newMailbagsTable[index].mailbagId.length === 12) ? 'Y': 'N'
    return Promise.resolve({isDomesticFlag:isdomesticflag,active:state.form.newMailbagsTable.active});
  }
  export function pabuiltUpdate(values) {
    const { args, dispatch } = values;
    if(args.barrowCheck){
    dispatch(change( 'reassignForm','paBuilt', false));
    }
  }

  export function populateMailBagIdForExcelView(values){
      
    const {dispatch,args,getState } = values;
    const state=getState();
    const mailbags = [];
    let excelMailbags=[];
    
     let mailbagslist=[];
    
      if(state.mailbagReducer.addModifyFlag ===constant.ADD_MAILBAG) {
        state.handsontableReducer.mailBags ? excelMailbags.push(...state.handsontableReducer.mailBags.insertedRows) : null;
        excelMailbags = excelMailbags.map((value) => ({ ...value,mailbagId:value.mailbagId?value.mailbagId:''}));
    
         mailbagslist= populateMailbagDetailsInExcel(excelMailbags,state,dispatch);
                mailbagslist = mailbagslist.map((value) => ({ ...value,operationFlag: 'I',
                mailbagWeight:value.mailbagWeight.roundedDisplayValue,displayUnit:value.mailbagWeight.displayUnit,tab:'EXCEL_VIEW'}));
         
           
        
       
        mailbags.push(...mailbagslist);
      }
      if(state.mailbagReducer.addModifyFlag ===constant.MODIFY_MAILBAG) {
        state.handsontableReducer.mailBags ? excelMailbags.push(...state.handsontableReducer.mailBags.data) : null;
        excelMailbags = excelMailbags.map((value) => ({ ...value,mailbagId:value.mailbagId?value.mailbagId:''}));
    
         mailbagslist= populateMailbagDetailsInExcel(excelMailbags,state,dispatch);
         if (!isEmpty(mailbagslist)) {
            mailbagslist = mailbagslist.map((value) => ({ ...value,operationFlag: 'I',
                    mailbagWeight:value.mailbagWeight.roundedDisplayValue,displayUnit:value.mailbagWeight.displayUnit,tab:'EXCEL_VIEW'}));
             
          
        }
       
       
        mailbags.push(...mailbagslist);
    }
    let INT_REGEX = /[0-9]/
    if(mailbags != null && mailbags.length > 0){
        for (let i = 0; i < mailbags.length; i++) {
            if (mailbags[i].mailbagId.length === 29 && !(INT_REGEX).test(mailbags[i].year)) {
                return Promise.reject(new Error("Invalid Mailbags"));
            }
        }
    }
    let warningMessagesStatus=state.commonReducer.warningMessagesStatus?state.commonReducer.warningMessagesStatus:[];
    const data = { mailbags,addMailbagMode:'EXCEL_VIEW',warningMessagesStatus}; 
    const url = 'rest/mail/operations/outbound/populateMailbagId';
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
if(response.status==='success'){
    
    let updatedMailbags=[];
    updatedMailbags =response.results&& response.results[0].mailbags?response.results[0].mailbags:[];
    const warningMapValue = { ["mail.operation.domesticmaildoesnotexistwarningonsave"]: 'N' };
    dispatch({ type: constant.SET_WARNING_MAP, warningMapValue });
    dispatch({ type: constant.UPDATE_MAILBAGS_IN_ADD_POPUP, updatedMailbags });
}
        return response
       
    })
    .catch(error => {
        return error;
    });
}
  
  
  export function onSavemailbagDetailsforExcel(values){
      
    const {dispatch,args,getState } = values;
    const state = getState();
    let showWarning='Y';
    if(args&&args.showWarning){
        showWarning= args.showWarning;
    }
    if(state.commonReducer.flightCarrierflag==='C'){
        showWarning='N'
    }
    const tab=state.mailbagReducer.activeMailbagAddTab;
    const mailAcceptance= state.containerReducer.mailAcceptance;
    let selectedContainer = (state.mailbagReducer.selectedContainer) ? state.mailbagReducer.selectedContainer : '';
    if(selectedContainer.companyCode === null || selectedContainer.companyCode === undefined){
        selectedContainer = state.containerReducer.flightContainers?state.containerReducer.flightContainers.results[0]:{}
    }
    if(state.mailbagReducer.addModifyFlag ===constant.MODIFY_MAILBAG) {
    selectedContainer.mailUpdateFlag=true;
    }
    const flightCarrierflag = state.commonReducer.flightCarrierflag
    let warningMessagesStatus = state.commonReducer.warningMessagesStatus;
   let mailbagslist = state.mailbagReducer.updatedMailbags ? state.mailbagReducer.updatedMailbags:[];
   let deletedMails = state.mailbagReducer.selectedMailbags && state.mailbagReducer.selectedMailbags.length>0? state.mailbagReducer.selectedMailbags : [];
  
   deletedMails = deletedMails.map((value) => ({ ...value,operationFlag: 'D',tab:'EXCEL_VIEW'}));
   mailbagslist = mailbagslist.map((value) => ({ ...value,operationFlag: 'I',__opFlag:'I',scannedTime:state.mailbagReducer.currentTime,scannedDate:state.mailbagReducer.currentDate,}));
   let mailbags=[];
 
        mailbags.push(...mailbagslist);
    mailbags.push(...deletedMails);
  
    if(selectedContainer) {
       
      const data = { selectedContainer:
         { ...selectedContainer,operationFlag:'U',containerOperationFlag:'U',warningMessagesStatus,
         mailbagdsnviewpagelist: null, mailbagpagelist:null,dsnviewpagelist:null},
          flightCarrierflag,mailAcceptance : {...mailAcceptance , containerPageInfo:null},
          mailbags,warningMessagesStatus,actionType:constant.ADD_MAILBAG, tab,showWarning}
      const url = 'rest/mail/operations/outbound/AddMailbag';
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        if(isEmpty(response.errors)){
            state.handsontableReducer&&state.handsontableReducer.mailBags ?
                    state.handsontableReducer.mailBags.insertedRows=[] : '';
            state.handsontableReducer&&state.handsontableReducer.mailBags ?
                    state.handsontableReducer.mailBags.data=[] : '';
        }
        handleResponseonAdding(dispatch, response,constant.ADD_MAILBAGS_CLOSE);
        return response
    })
        .catch(error => {
            return error;
        });
    }
    else {
      dispatch(requestValidationError('Mailbags should be added to selected containers'));
      return Promise.resolve({})
    } 
  }


  export function validateFlightDetailsForTransfer(values){
    const { args, getState } = values;
    const state=getState();
    let transferDetails={};
    let flightCarrierFilter = {};
    if(args.action===constant.LIST_TRANSFER){
        if(state.form.transferMailForm&&state.form.transferMailForm.values&&state.form.transferMailForm.values.transferFilterType === 'F'){
        transferDetails=state.form.transferMailForm.values?
                                state.form.transferMailForm.values.flightnumber:{};
        /*flightnumber={carrierCode:transferDetails.carrierCode,
                        flightNumber:transferDetails.flightNumber,
                        flightDate:transferDetails.flightDate};*/
        
        flightCarrierFilter.flightNumber = transferDetails.flightNumber;
        flightCarrierFilter.flightDate = transferDetails.flightDate;
        flightCarrierFilter.carrierCode = transferDetails.carrierCode;
        flightCarrierFilter.assignTo = 'F';
        if((flightCarrierFilter.carrierCode==='' ||flightCarrierFilter.carrierCode=== undefined) ||(flightCarrierFilter.flightNumber==='' ||flightCarrierFilter.flightNumber=== undefined)){
            return Promise.reject(new Error("Please specify Flight Details")); 
        }
        if(flightCarrierFilter.flightDate ==='' ||flightCarrierFilter.flightDate === undefined){
            return Promise.reject(new Error("Please enter Flight Date")); 
        }
        flightCarrierFilter={
            ...flightCarrierFilter,
            airportCode: state.commonReducer.airportCode? state.commonReducer.airportCode:'',
            flightDisplayPage:1,
            flightStatus : ['ACT','TBA']
        };
        //added as part of IASCB-36551
        if(state.filterReducer.filterValues) {
            flightCarrierFilter.operatingReference = state.filterReducer.filterValues.operatingReference
        } 
        }
        else{
            flightCarrierFilter.assignTo = 'C'; 
            flightCarrierFilter.carrierCode = state.form.transferMailForm.values.carrier;
            if(flightCarrierFilter.carrierCode==='' ||flightCarrierFilter.carrierCode=== undefined){
                return Promise.reject(new Error("Please specify Carrier")); 
            }
            flightCarrierFilter.destination  = state.form.transferMailForm.values.destination;
            if(flightCarrierFilter.destination ==="" ||flightCarrierFilter.destination=== undefined){
                return Promise.reject(new Error("Please enter Destination")); 
            }
            flightCarrierFilter.airportCode = state.form.transferMailForm.values.uplift;
            flightCarrierFilter={...flightCarrierFilter,carrierDisplayPage:1};

        }
        let  displayPage=args&&args.displayPage?args.displayPage:1;
        const data = { flightCarrierFilter };
        const url = 'rest/mail/operations/mailinbound/list';
                    return makeRequest({
                        url,
                        data: { ...data }
                    }).then(function (response) {
                        response={...response,pageNumber:args.pageNumber?args.pageNumber:1,displayPage}
                        return response;
                    })
                    .catch(error => {
                        return error;
                    });
    }
}
export function listContainerForTransferPanel(values){

    const { args, dispatch,getState } = values;
    const state=getState(); 
    
    if(args.action===constant.LIST_TRANSFER){
        const mode=constant.LIST_CONTAINERS_POPUP;
        let mailAcceptance ;
        const flightCarrierflag =  args.results[0] &&  args.results[0].flightCarrierFilter.assignTo;
        if(flightCarrierflag ==='F'){
            mailAcceptance = args.results[0]&&
        args.results[0].mailflightspage&&args.results[0].mailflightspage.results[0]?
            args.results[0].mailflightspage.results[0]:{};
        }
        else{
            mailAcceptance = args.results[0]&&
            args.results[0].mailcarrierspage&&args.results[0].mailcarrierspage.results[0]?
              args.results[0].mailcarrierspage.results[0]:{}; 
        }

       
       let containerDisplayPageForTransfer= args.displayPage?args.displayPage:state.mailbagReducer.containerDisplayPageForTransfer;
        const data = { mailAcceptance:{...mailAcceptance,containerPageInfo:null}, flightCarrierflag, containerDisplayPage:containerDisplayPageForTransfer,actionType:'Mail Transfer'}
        const url = 'rest/mail/operations/mailinbound/listContainersOutbound';
        return makeRequest({
            url,
            data: { ...data }
        }).then(function (response) {
           // handleResponse(dispatch, response, flightsArray, flightIndex,containerDisplayPage);
           let  containerDisplayPage = response.results[0].containerDisplayPage
            handleResponseForTansfer(dispatch, response,mode,containerDisplayPage);
            return response;
        })
            .catch(error => {
                return error;
            });
    }
}
function handleResponseForTansfer(dispatch, response, action,containerDisplayPage) {
if(response.errors==null || response.status=="success"){
    if (action === constant.LIST_CONTAINERS_POPUP) {
        dispatch({type: constant.LIST_CONTAINERS_POPUP,  
                     containerVosToBeReused:response.results[0].mailAcceptance.containerPageInfo,flightDetailsToBeReused:response.results[0].mailAcceptance,
                        addContainerButtonShow:false,destination:response.results[0].mailAcceptance.destination,containerDisplayPage});
                     
    } 
  }
    
}
export function clearAddContainerPopoverForTransfer(values) {
    const { dispatch, getState } = values;
    const state = getState();
    const flightnumber = state.form.transferMailForm?state.form.transferMailForm.values.flightnumber:{};
    dispatch({type: constant.CLEAR_TRANSFER_FORM, flightnumber: flightnumber}); 
    dispatch(reset("transferMailForm"));  
    
}

export function onTransferMailSave(values) {

    const { args, dispatch, getState } = values;
    const state = getState();
    const { containerIndex, action } = args;
    let mailacceptance=state.mailbagReducer?(state.mailbagReducer.flightDetailsToBeReused):{};
    const flightValidation = {companyCode:mailacceptance.companyCode,carrierCode:mailacceptance.carrierCode,
       flightCarrierId:mailacceptance.carrierId,flightNumber:mailacceptance.flightNumber,
       flightSequenceNumber:mailacceptance.flightSequenceNumber,legSerialNumber:mailacceptance.legSerialNumber,flightRoute:mailacceptance.flightRoute,
       flightDate:mailacceptance.flightDate,aircraftType:mailacceptance.aircraftType,flightType:mailacceptance.flightType,
       operationalStatus:mailacceptance.flightOperationalStatus,flightStatus:mailacceptance.flightStatus,
       applicableDateAtRequestedAirport:mailacceptance.flightDate};
    const containers = (state.mailbagReducer.transferContainers &&state.mailbagReducer.transferContainers.results) ? state.mailbagReducer.transferContainers.results : {}
    let selectedContainer = (state.mailbagReducer.transferContainers) ? state.mailbagReducer.transferContainers.results : {}
    let selectedMailbags = (state.mailbagReducer.selectedMailbags) ? state.mailbagReducer.selectedMailbags : []
    let url = '';
    let flightCarrierCode = '';
    let flightNumber = '';
    let assignToFlight = '';
    let flightDate = '';
    let destination = '';
    let carrierCode = '';
    let scanDate = '';
    let scanTime = '';
    let upliftAirport ='';

    scanDate = state.form.transferMailForm.values.scanDate?state.form.transferMailForm.values.scanDate:'';
    scanTime = state.form.transferMailForm.values.mailScanTime?state.form.transferMailForm.values.mailScanTime:'';
    carrierCode = state.form.transferMailForm.values.carrier?state.form.transferMailForm.values.carrier:'';
    flightCarrierCode = state.form.transferMailForm.values.flightnumber?state.form.transferMailForm.values.flightnumber.carrierCode:'';
    flightNumber = state.form.transferMailForm.values.flightnumber?state.form.transferMailForm.values.flightnumber.flightNumber:'';
    assignToFlight = state.form.transferMailForm.values.transferFilterType === 'F' ? 'FLIGHT' : 'CARRIER';
    flightDate = state.form.transferMailForm.values.flightnumber?state.form.transferMailForm.values.flightnumber.flightDate:'';
    destination = state.form.transferMailForm.values.destination?state.form.transferMailForm.values.destination:'';
    upliftAirport = state.form.transferMailForm.values.uplift?state.form.transferMailForm.values.uplift:'';

    let oneTimeValues = state.commonReducer.oneTimeValues;
    selectedContainer = containers[containerIndex];
    const data = { scanDate, scanTime, flightValidation,selectedMailbags, oneTimeValues, carrierCode,flightCarrierCode, flightNumber, assignToFlight, flightDate, destination, selectedContainer,upliftAirport };
    
    if(assignToFlight==='FLIGHT'){
        if(selectedContainer === undefined){
            const message = 'Please select a row';
            const target = '';
            dispatch(requestValidationError(message, target));
           return Promise.resolve("Error"); 
        }
    }else{
        if(carrierCode === undefined || carrierCode === ''){
            const message = 'Please enter carrier code';
            const target = '';
            dispatch(requestValidationError(message, target));
           return Promise.resolve("Error"); 
        }
        else if(assignToFlight==='CARRIER'&& state.mailbagReducer
                 &&state.mailbagReducer.ownAirlineCode&&state.mailbagReducer.ownAirlineCode===carrierCode
                &&(selectedContainer === undefined)){
                    return Promise.reject(new Error("Please select a container"));   
            }
    }
    let warningMessagesStatus = state.commonReducer.warningMessagesStatus;
    url = 'rest/mail/operations/mailbagenquiry/transferMailbags';
    return makeRequest({
        url,
        data: { ...data,warningMessagesStatus }
    }).then(function (response) {
        if((response.errors==null || response.status=="success")){
            dispatch({type: constant.TRANSFER_SAVE,showTransferClose:false});
            dispatch({type: constant.LIST_CONTAINERS_POPUP,  
                containerVosToBeReused:{},flightDetailsToBeReused:{},addContainerButtonShow:true});
                const warningMapValue = { ['mail.operations.securityscreeningwarning']:'N' };
                dispatch({ type: constant.SET_WARNING_MAP, warningMapValue });
            return response
        }
        else{
            return response
        }
    })
        .catch(error => {
            return error;
        });


}


  

export function onApplyDsnFilter(values) {
    const { dispatch, getState } = values;
    const formValues = getState().form.dsnFilter.values;
    if(isEmpty(formValues)){
        return;
    }
    const state=getState();
    const getTableResults = () =>
        state.mailbagReducer && state.mailbagReducer.allDsnList?
        (state.mailbagReducer.allDsnList) : [];
    let dsnData=[];    
    
    const filterValues =
        state.form.dsnFilter.values ?
            state.form.dsnFilter.values : {};
    Object.keys(filterValues).forEach((key) => (filterValues[key] === ''||filterValues[key] === null) && delete filterValues[key]); 
    
    const getTableFilter = () =>  isEmpty(filterValues)? {}: filterValues;
    
    const getDetails = createSelector([getTableResults, getTableFilter], (results, filterValues) => {
         if (!isEmpty(filterValues)) {
            return results.filter((obj) => {
                const currentObj = {
                    mailbagId:obj.dsn,
                    ooe:obj.originExchangeOffice,
                    doe:obj.destinationExchangeOffice,
                    mailCategoryCode:obj.mailCategoryCode,
                    mailSubclass:obj.mailSubclass,
                    year:obj.year,
                    awbReducer:obj.masterDocumentNumber,
                    conDocNo:obj.consignmentNumber,
                    paCode:obj.paCode,
                    routingInfo:obj.routingAvl==true?true:null,
                    plt:obj.pltEnableFlag==true?true:null,

                 };
                   
                 const anotherObj = { ...currentObj, ...filterValues };
                 if (JSON.stringify(currentObj) === JSON.stringify(anotherObj))
                     return true;
                 else
                     return false
            })

         } else {
             return results;
         }
    });
    dsnData=getDetails();
    let pagedDsnData=state.mailbagReducer && state.mailbagReducer.mailBagsdsnView ? 
          state.mailbagReducer.mailBagsdsnView : []; 
    pagedDsnData={...pagedDsnData,results:dsnData}; 
    dispatch({ type: constant.APPLY_DSN_FILTER, dsnFilterValues:formValues,dsnDataAfterFilter:pagedDsnData });

}

export function onClearDsnFilter(values) {

    const { dispatch, getState } = values;
    const state = getState();
    let dsnData = state.mailbagReducer && state.mailbagReducer.allDsnList ?
        state.mailbagReducer.allDsnList : [];
    let pagedDsnData = state.mailbagReducer && state.mailbagReducer.mailBagsdsnView ?
        state.mailbagReducer.mailBagsdsnView : [];
    pagedDsnData = { ...pagedDsnData, results: dsnData };
    dispatch(reset(constant.DSN_FILTER));  
    dispatch({ type: 'APPLY_DSN_FILTER', dsnDataAfterFilter: pagedDsnData,dsnFilterValues:[] });
}
export function invokeEmbargoPopup(dispatch){
    var url = "reco.defaults.ux.showEmbargo.do";
    //var closeButtonIds = ["CMP_Reco_Defaults_UX_ShowEmbargo_btnClose"];
    var optionsArray = {
        url,
        dialogWidth: "650",
        dialogHeight: "210",
       // closeButtonIds: closeButtonIds,
        popupTitle: 'Check Embargo'
        }
    dispatch(dispatchAction(openPopup)(optionsArray));
}

export function onClickImportPopup(values) {
    const { dispatch, getState } = values;
    const state = getState();
    let selectedContainer = state.containerReducer.selectedContainer ? state.containerReducer.selectedContainer : {};
    const url = 'rest/mail/operations/outbound/showImportPopUp';
    const data = {
        selectedContainer: {
            containerNumber: selectedContainer.containerNumber,
            flightNumber: selectedContainer.flightNumber,
            carrierId: selectedContainer.carrierId,
            flightDate: selectedContainer.flightDate,
            scannedDate: selectedContainer.scannedDate
        }
    };
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        handleClickImportPopupResponse(dispatch, response, constant.SHOW_IMPORT_POPUP);
        return response;
    }).catch(error => {
        return error;
    });

}
function handleClickImportPopupResponse(dispatch, response, action) {
    if (!isEmpty(response.results)) {
        const containerJnyID = response.results[0].containerJnyID;
        dispatch({ type: action, containerJnyID:containerJnyID });
    }else{
        dispatch({ type: action });
    }
} 

  



