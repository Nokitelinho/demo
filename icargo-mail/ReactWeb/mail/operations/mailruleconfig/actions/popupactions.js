
import { CLOSE_ADD_POPUP, ADD_NEW_MAILRULE,VALIDATE_URL} from '../constants';
import { resetTable,requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
export function onCloseMailRule(values) {
    const { dispatch } = values;
    dispatch({ type: CLOSE_ADD_POPUP, response: {} });
}



export function saveMailRuleConfig(values) {

    const { args, dispatch, getState } = values;
    const state = getState();
    let newMailRuleList =state.filterpanelreducer.newMailRuleList ?state.filterpanelreducer.newMailRuleList:[];
    let listValues = state.filterpanelreducer.mailRuleConfigList ? state.filterpanelreducer.mailRuleConfigList : [];
    let mailRuleConfig = {};
    let validateForm=state.form.addMessageRuleForm && state.form.addMessageRuleForm.values;
   
    if (state.form.addMessageRuleForm && state.form.addMessageRuleForm.values) {
        mailRuleConfig.ooe = state.form.addMessageRuleForm.values.ooe ? state.form.addMessageRuleForm.values.ooe : '';
        mailRuleConfig.doe = state.form.addMessageRuleForm.values.doe ? state.form.addMessageRuleForm.values.doe : '';
        mailRuleConfig.contractCarrier = state.form.addMessageRuleForm.values.contractCarrier ? state.form.addMessageRuleForm.values.contractCarrier : '';
        mailRuleConfig.originAirport = state.form.addMessageRuleForm.values.originAirport ? state.form.addMessageRuleForm.values.originAirport : '';
        mailRuleConfig.destinationAirport = state.form.addMessageRuleForm.values.destinationAirport ? state.form.addMessageRuleForm.values.destinationAirport : '';
        mailRuleConfig.categoryCode = state.form.addMessageRuleForm.values.categoryCode ? state.form.addMessageRuleForm.values.categoryCode : '';
        mailRuleConfig.subclass = state.form.addMessageRuleForm.values.subclass ? state.form.addMessageRuleForm.values.subclass : '';
        mailRuleConfig.xxresdit = state.form.addMessageRuleForm.values.xxresdit ? state.form.addMessageRuleForm.values.xxresdit : '';
        mailRuleConfig.mailboxId = state.form.addMessageRuleForm.values.mailboxId ? state.form.addMessageRuleForm.values.mailboxId : '';
        mailRuleConfig.fromDate = state.form.addMessageRuleForm.values.fromDate ? state.form.addMessageRuleForm.values.fromDate : '';
        mailRuleConfig.toDate = state.form.addMessageRuleForm.values.toDate ? state.form.addMessageRuleForm.values.toDate : '';
    
    }
    
    let validObject = {};
        validObject.valid= true;
     validObject=validateMailRuleConfigForm(mailRuleConfig);
    
       
    if(!validObject.valid){
        dispatch(requestValidationError(validObject.msg, ''));
        return Promise.resolve("Error"); 
        }
        else {
if(args&&args.validation){
    const url = VALIDATE_URL;
    let request={mailRuleConfig};
    return makeRequest({
        url,
        data: {...request}
    }).then(function (response) {
        if (response.results) {
            
        newMailRuleList.push(mailRuleConfig);
        listValues.push(mailRuleConfig);
    
   let  screenMode= state.filterpanelreducer.screenMode;
   dispatch({ type: ADD_NEW_MAILRULE, listValues,screenMode,newMailRuleList});
   dispatch({ type: CLOSE_ADD_POPUP, response: {} });
    const tableId = 'mailrulelist';
    dispatch(resetTable(tableId)); 
    }
        return response;
    })
        .catch(error => {
            return error;
        });
    }
     
        newMailRuleList.push(mailRuleConfig);
        listValues.push(mailRuleConfig);
    
   let  screenMode= state.filterpanelreducer.screenMode;
   dispatch({ type: ADD_NEW_MAILRULE, listValues,screenMode,newMailRuleList});
   dispatch({ type: CLOSE_ADD_POPUP, response: {} });
    const tableId = 'mailrulelist';
    dispatch(resetTable(tableId));  
        }
}



export function validateMailRuleConfigForm(mailRuleConfig) {
    let isValid = true;
    let error = ""
    if(mailRuleConfig==={}){
        isValid = false;
            error = "Please provide mail rule configuration"
    }
    else{
       
         if(!mailRuleConfig.xxresdit||mailRuleConfig.xxresdit===''){
            isValid = false;
            error = "Please Select XX Resdit" 
        }
       else if(!mailRuleConfig.mailboxId||mailRuleConfig.mailboxId===''){
            isValid = false;
            error = "Please Enter Mailbox ID" 
        }
    }
    let validObject = {
        valid: isValid,
        msg: error
    }
    return validObject;
}