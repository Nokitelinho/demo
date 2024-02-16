import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import { handleResponse} from './handleresponse.js';
import { ActionType } from '../constants/constants.js';
import { dispatchAction} from 'icoreact/lib/ico/framework/component/common/actions';
import { openPopup } from 'icoreact/lib/ico/framework/action/openpopup';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';


// For ScreenLoad
export function screenLoad(values) {
    const { args, dispatch, getState } = values;
    const state = getState();    
    const url='rest/mail/mra/receivablemanagement/advancepayment/screenload';
    return makeRequest({
        url,data:{}
    }).then(function(response) {
        const payLoad={ type: ActionType.SCREENLOAD_SUCCESS, response, dispatch }
        handleResponse(payLoad);
        return response;
    })   
}

//For Close
export function onClose() {
    navigateToScreen('home.jsp', {});
}

//For Edit Batch
export function editBatch(values){
    const {dispatch,getState,args} =values;
    const state = getState();    
    let paymentBatchDetail={};
    paymentBatchDetail = args.selectedBatchRow;   
    let paymentBatchFilter = state.filterReducer.filterValues;
    if(args.selectedBatchRow.batchStatus !="New"){
        const message = 'Batch in "New" Status can only be edited';
        const target = '';
        dispatch(requestValidationError(message, target));
        return Promise.resolve("Error");          
    }  
    if(args.selectedBatchRow.processID){
        const message = 'Batch having attachment cannot be edited';
        const target = '';
        dispatch(requestValidationError(message, target));
        return Promise.resolve("Error");          
    }       
    const data = {paymentBatchDetail,paymentBatchFilter}
    const url = 'rest/mail/mra/receivablemanagement/advancepayment/editbatch';
    return makeRequest({
        url,data: {...data}
    }).then(function(response) {
        const payLoad={ type: ActionType.EDT_BTH, response, dispatch }
        handleResponse(payLoad);
        return response;
    })    
}
//For Delete Batch
export function deleteBatch(values){
    const {dispatch,getState,args} =values;
    const state = getState();
    let paymentBatchDetail={};
    paymentBatchDetail = args.selectedBatchRow;
    let paymentBatchFilter = state.filterReducer.filterValues;
    const data = {paymentBatchDetail,paymentBatchFilter}
    if(args.selectedBatchRow.batchStatus =="Cleared"){
        const message = 'Batch in "Cleared" Status cannot be deleted';
        const target = '';
        dispatch(requestValidationError(message, target));
        return Promise.resolve("Error");          
    }  
    const url = 'rest/mail/mra/receivablemanagement/advancepayment/deletebatch';
    return makeRequest({
        url,data: {...data}
    }).then(function(response) {
        const payLoad={ type: ActionType.DEL_BATCH, response, dispatch }
        handleResponse(payLoad);
        return response;
    })  
}
//For Delete Attachment
export function deleteAttachment(values){
    const {dispatch,getState,args} =values;
    const state = getState();
    let paymentBatchDetail={};
    paymentBatchDetail = args.selectedBatchRow;
    let paymentBatchFilter = state.filterReducer.filterValues;
    if(args.selectedBatchRow.batchStatus !="New"){
        const message = 'Attachment against Batch Status "New" can only be deleted';
        const target = '';
        dispatch(requestValidationError(message, target));
        return Promise.resolve("Error");          
    }
    if(!args.selectedBatchRow.processID){
        const message = 'Please select batch having attachment';
        const target = '';
        dispatch(requestValidationError(message, target));
        return Promise.resolve("Error");          
    }
    const data = {paymentBatchDetail,paymentBatchFilter}
    const url = 'rest/mail/mra/receivablemanagement/advancepayment/deleteattachment';
    return makeRequest({
        url,data: {...data}
    }).then(function(response) {
        const payLoad={ type: ActionType.DEL_ATCH, response, dispatch }
        handleResponse(payLoad);
        return response;
    })  
}
//For attachment upload
export function addAttachment(values){
    const {dispatch,getState,args} =values;
    const state = getState();
    const batchID = args.selectedBatchRow.batchID;
    const paCode = args.selectedBatchRow.paCode;
    const batchDate = args.selectedBatchRow.date;

    var url = "mail.mra.defaults.ux.attachfile.defaultscreenload.do?fromScreen=AdvancePayment&isPopup=true&batchID="+batchID+'&paCode='+paCode+'&batchDate='+batchDate;
    var optionsArray = {
        url,
        dialogWidth: "350",
        dialogHeight: "150",
        popupTitle: 'Attach File'
        }
    dispatch(dispatchAction(openPopup)(optionsArray));
}

export function saveSelectedBatchIndexes(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const indexes = args;
    dispatch({ type: ActionType.SAVE_SELECTED_INDEX, indexes });
}

export function navigateToListSettlementBatch(values) {
	const { dispatch, getState, args } = values;
    const state = getState();
	const paymentdetails = state.filterReducer.paymentdetails;
	const batchStatusOnetime =state.filterReducer.oneTimeValues;
	let batchStatus=state.commonReducer.oneTimeValues['mail.mra.receivablemanagement.batchstatus'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));      
    let selectedBatch=[];
    const indexes = state.commonReducer.selectedIndex ? state.commonReducer.selectedIndex : [];
	 if(indexes == undefined){
		 dispatch(requestValidationError('Please select a row', ''));  
		 return Promise.resolve("Error");  
     }
     else if(indexes.length!==1){
		dispatch(requestValidationError('Please select single row', ''));  
		return Promise.resolve("Error");  
     }else{
		 selectedBatch=paymentdetails.results[indexes[0]];
	let selectedBatchStatus=batchStatus.find((element) => {return  element.label === selectedBatch.batchStatus});   
    const params = { fromScreen: "AdvancePayment", batchId:selectedBatch.batchID,batchDate: selectedBatch.date ,paCode:selectedBatch.paCode,batchStatus:selectedBatchStatus.value,batchSequenceNum:selectedBatch.batchSequenceNum}
    navigateToScreen("mail.mra.ux.listsettlementbatch.defaultscreenload.do", params);
	}
}