import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import {change,focus } from 'icoreact/lib/ico/framework/component/common/form';
import { asyncDispatch,dispatchAction} from 'icoreact/lib/ico/framework/component/common/actions';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import { openPopup } from 'icoreact/lib/ico/framework/action/openpopup'
import {reset} from 'redux-form'; 
import {listInvoicDetails} from './filteraction';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';


// For ScreenLoad
export function screenLoad(values) {
    const { args, dispatch, getState } = values;
    let mode='';
    if(args) {
        mode=args.fromScreen;
    }
    const state = getState();    
    const url='rest/mail/mra/gpareporting/invoicenquiry/screenload';
    return makeRequest({
        url,data:{}
    }).then(function(response) {
        handleScreenloadResponse(dispatch, response,mode);
        return response;
    })   
}
export function handleScreenloadResponse(dispatch, response,mode) {
  if (response.results) {
            
    dispatch({ type: 'SCREENLOAD_SUCCESS', data:response.results[0],mode:mode});

    }
   
}


//For Claim Amount Save
export function onSaveFunction(values) {
   const { args, dispatch, getState } = values;
    const state = getState();
    const displayPage = args && args.displayPage ? args.displayPage : state.filterReducer.displayPage
    const invoicFilter  = (state.form.invoicFilter.values)?state.form.invoicFilter.values:{}
    invoicFilter.displayPage=displayPage;
    let indexes=[];
    let listedmailbags=[];
    let selectedInvoicDetails=[];
    let count=0;
    listedmailbags=state.form.newMailbagsTable.values.newMailbagsTable? state.form.newMailbagsTable.values.newMailbagsTable :[];
    const length=listedmailbags.length;
    //indexes = (state.filterReducer.selectedMailbagIndex) ? state.filterReducer.selectedMailbagIndex : [];
   

  /*  for (let i = 0; i < indexes.length; i++) {
           selectedInvoicDetails[count++]=listedmailbags[indexes[i]];
    }*/
    for (let i = 0; i <length; i++) {
        if(listedmailbags[i].__opFlag == 'U'){
            const isValid = validateClaimAmount(listedmailbags[i].claimamount);
            if(!isValid){
                const message = 'Please enter a valid claim amount';
                const target = '';
                dispatch(requestValidationError(message, target));
               return Promise.resolve("Error"); 
            }
            selectedInvoicDetails[count++] = listedmailbags[i];
        }
        }

    const data = { selectedInvoicDetails,invoicFilter };
    const url = 'rest/mail/mra/gpareporting/invoicenquiry/save';
    return makeRequest({
        url,data:{...data}
    }).then(function(response) {
        handleSaveResponse(dispatch, response);
        return response;
    }).catch(error => {
        return error;
    });   
}

 export function handleSaveResponse(dispatch, response) {
    if (response.status=="success") {
        dispatch(saveSuccess());   
    }
}
export function saveSuccess() {
    return { type: 'SAVE_SUCCESS' };
}
//For Remark Save
export function onOKRemarks(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const remarks = args.remarks;
    const index = args.index;
    let count=0;
    let listedmailbags=[];
    let selectedInvoicDetails=[];
    listedmailbags=state.form.newMailbagsTable.values.newMailbagsTable? state.form.newMailbagsTable.values.newMailbagsTable :[];
    const length=listedmailbags.length;
   // const index = (state.filterReducer.selectedMailbagIndex) ? state.filterReducer.selectedMailbagIndex : [];
    const invoicFilter = (state.form.invoicFilter.values) ? state.form.invoicFilter.values : {};
   
    listedmailbags[index].remarks = remarks;
    selectedInvoicDetails[count] = listedmailbags[index];
    const data = { selectedInvoicDetails,invoicFilter };
    const url = 'rest/mail/mra/gpareporting/invoicenquiry/saveRemarks';
    return makeRequest({
        url,data:{...data}
    }).then(function(response) {
        handleRemarksSaveResponse(dispatch, response);
        return response;
    }).catch(error => {
        return error;
    });
}
export function handleRemarksSaveResponse(dispatch, response) {
    if (response.status=="success") {
        dispatch(remarkssaveSuccess());   
    }
}
export function remarkssaveSuccess() {
    return { type: 'REMARKS_SAVE_SUCCESS' };
}

//For groupRemarksSave
export function ondoneGroupRemarks(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const groupRemarks = args.remarks;
    const toProcessStatus = args.toProcessStatus;
    const  selectedClaimRange = args.selectedClaimRange;
    const  selectedProcessStatus = args.selectedProcessStatus;
    const invoicFilter = (state.form.invoicFilter.values) ? state.form.invoicFilter.values : {}
    invoicFilter.selectedClaimRange=selectedClaimRange;
    invoicFilter.selectedProcessStatus =selectedProcessStatus;
   
    const data = { groupRemarks,toProcessStatus,invoicFilter };
    const url = 'rest/mail/mra/gpareporting/invoicenquiry/saveGroupRemarks';
    return makeRequest({
        url, data: { ...data }
    }).then(function (response) {
        handleGroupRemarksSaveResponse(dispatch, response);
        return response;
    }).catch(error => {
        return error;
    });
}
export function handleGroupRemarksSaveResponse(dispatch, response) {
    if (response.status == "success") {
        dispatch(groupremarkssaveSuccess());
    }
}
export function groupremarkssaveSuccess() {
    return { type: 'GROUPREMARKS_SAVE_SUCCESS' };
}

//For Bucket Sorting
export function onbucketSort(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
   // const displayPage = args && args.displayPage ? args.displayPage : state.filterReducer.displayPage;
   const displayPage ='1';
    const action= 'LIST';
    const screenMode=state.filterReducer.screenMode;
    const invoicFilter  = (state.form.invoicFilter.values)?state.form.invoicFilter.values:{};
    const  processStatus =state.form.extendedFilter.values? state.form.extendedFilter.values:[];
    // let map = new Map().set(...processStatus) 
    console.log(processStatus);

     const processStatusKeys = Object.keys(processStatus);
     let desiredProcessStatus=[];
     let desiredClaimRange=[];
     Object.entries(processStatus).forEach(([key, value]) => {
        console.log(key, value);
        if(value ===true && key!=('0-5') && key!=('6-10') && key!=('11-25') && key!=('26-50') && key!=('51-100') && key!=('101-500')  && key!=('501-1000') ){
            desiredProcessStatus.push(key);
        }
        else if(value ===true && (key===('0-5') || key===('6-10') || key===('11-25') || key===('26-50') || key===('51-100') || key===('101-500')  || key===('501-1000'))){
            desiredClaimRange.push(key);
        }
     });
     console.log("desiredProcessStatus",desiredProcessStatus);
     console.log("desiredClaimRange",desiredClaimRange);
    


    //let selectedProcessStatus=[ ...map.keys() ];
    //invoicFilter.selectedProcessStatus = args.checkboxStatus ? args.checkboxStatus :[];
    //invoicFilter.selectedClaimRange=args.selectedClaimRange? args.selectedClaimRange:[];
    invoicFilter.selectedProcessStatus = desiredProcessStatus;
    invoicFilter.selectedClaimRange=desiredClaimRange;
   	invoicFilter.displayPage=displayPage;
    const mailSubClass = state.form.extendedFilter.values? state.form.extendedFilter.values.mailSubclass:[];
   
   
    let originsplit = state.form.extendedFilter.values && state.form.extendedFilter.values.org? state.form.extendedFilter.values.org:[];
    if(originsplit.length > 0){
    const orgin =originsplit.split(',');
    invoicFilter.org = orgin;
    }else{
        invoicFilter.org = [];
    }
  
    let destSplit = state.form.extendedFilter.values&& state.form.extendedFilter.values.dest? state.form.extendedFilter.values.dest:[];
    if(destSplit .length > 0){
    const destination =destSplit.split(',');
    invoicFilter.dest = destination;
    }else{
        invoicFilter.dest = [];
    }
   
    invoicFilter.mailSubClass = mailSubClass;
    
    const data = {invoicFilter};
    const url = 'rest/mail/mra/gpareporting/invoicenquiry/list';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        handleResponse(dispatch, response,action,screenMode,data,displayPage);
        return response
    })
    .catch(error => {
        return error;
    });
 
}

function handleResponse(dispatch,response,action,screenMode,data,displayPage) {
    
    if(!isEmpty(response.results)){
        console.log(response.results[0]);
        const {invoicDetails} = response.results[0];
        const currencycode = response.results[0].currencyCode;
        if (action==='LIST') {
            if(invoicDetails !=null ) {
                if(displayPage > 1) {
                    dispatch( { type: 'LIST_SUCCESS_PAGINATION',invoicDetails,data,displayPage,currencycode });
                }
                else
                   dispatch( { type: 'LIST_SUCCESS',invoicDetails,data,currencycode });
            }
            else {
                dispatch( { type: 'NO_DATA',data}); 
            }
        }
        
    } else {
        if(!isEmpty(response.errors)){
             dispatch( { type: 'RETAIN_VALUES'});
        }
    }
}

//For raise claim
export function onRaiseClaim(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const raiseClaimFlag = 'Y';
    let listedmailbags = [];
    let indexes = [];
    let count = 0;
    let selectedInvoicDetails = [];
    listedmailbags = state.form.newMailbagsTable.values.newMailbagsTable ? state.form.newMailbagsTable.values.newMailbagsTable : [];
    const length = listedmailbags.length;
     indexes = (state.filterReducer.selectedMailbagIndex) ? state.filterReducer.selectedMailbagIndex : [];
    const invoicFilter = (state.form.invoicFilter.values) ? state.form.invoicFilter.values : {}
       for (let i = 0; i < indexes.length; i++) {
        selectedInvoicDetails[count++] = listedmailbags[indexes[i]];
    } 
    for(let i=0; i<indexes.length;i++){
      if(listedmailbags[indexes[i]].mailbagInvoicProcessingStatus!=null &&(listedmailbags[indexes[i]].mailbagInvoicProcessingStatus=="AMOTOBEACT"||listedmailbags[indexes[i]].mailbagInvoicProcessingStatus=="AMOTACT"))
      {const message = 'Claim cannot be raised for AMOT Mailbags.';
      const target = '';dispatch(requestValidationError(message, target));
      return Promise.resolve("Error");
    }
    }
   
    const data = { raiseClaimFlag,selectedInvoicDetails,invoicFilter };
    const url = 'rest/mail/mra/gpareporting/invoicenquiry/raiseClaim';
    return makeRequest({
        url, data: { ...data }
    }).then(function (response) {
        handleRaiseClaimResponse(dispatch, response);
        //listInvoicDetails(...values,'displayPage': '1', mode: 'LIST');
        return response;
    }).catch(error => {
        return error;
    });
}
export function handleRaiseClaimResponse(dispatch, response) {
    if (response.status == "success") {
        dispatch(raiseClaimSuccess());
    }
}
export function raiseClaimSuccess() {
    return { type: 'RAISECLAIM_SUCCESS' };
}

//Check before Accept button
/*export function validateInvoiceAmount2 (values){
    const { args, dispatch, getState } = values;
    const state = getState();
    let eligibleBagFlag ="N";
    let listedmailbags = [];
    let indexes = [];
    let count = 0;
    let selectedInvoicDetails = [];
    listedmailbags = state.form.newMailbagsTable.values.newMailbagsTable ? state.form.newMailbagsTable.values.newMailbagsTable : [];
    const length = listedmailbags.length;
     indexes = (state.filterReducer.selectedMailbagIndex) ? state.filterReducer.selectedMailbagIndex : [];
    const invoicFilter = (state.form.invoicFilter.values) ? state.form.invoicFilter.values : {}
       for (let i = 0; i < indexes.length; i++) {
        selectedInvoicDetails[count++] = listedmailbags[indexes[i]];
    } 
    const selectedlength = selectedInvoicDetails.length;
       for(let i = 0; i < selectedlength; i++){
        if(selectedInvoicDetails[i].claimamount > 0) {
            eligibleBagFlag="Y";
        }
       }
      
       dispatch({ type: 'VALIDATE_SUCCESS', eligibleBagFlag});  
       //return { type: 'VALIDATE_SUCCESS', eligibleBagFlag};
}*/
export function validateInvoiceAmount (tableValues,indexes){
    let isValid = true;
    let error = ""
    let invoicDetails =tableValues.newMailbagsTable ;
    for (let i = 0; i < indexes.length; i++) {
       if(invoicDetails[indexes[i]].claimamount>0){
        isValid=false;
       }
    } 
    let validObject = {
        valid: isValid,
        msg: error
    }
    return validObject;

}

//For Accept
export function onAccept(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const raiseClaimFlag = 'N';
    let listedmailbags = [];
    let indexes = [];
    let count = 0;
    let selectedInvoicDetails = [];
    listedmailbags = state.form.newMailbagsTable.values.newMailbagsTable ? state.form.newMailbagsTable.values.newMailbagsTable : [];
    const length = listedmailbags.length;
     indexes = (state.filterReducer.selectedMailbagIndex) ? state.filterReducer.selectedMailbagIndex : [];
    const invoicFilter = (state.form.invoicFilter.values) ? state.form.invoicFilter.values : {}
       for (let i = 0; i < indexes.length; i++) {
        selectedInvoicDetails[count++] = listedmailbags[indexes[i]];
    } 
   
    const data = { raiseClaimFlag,selectedInvoicDetails,invoicFilter };
    const url = 'rest/mail/mra/gpareporting/invoicenquiry/accept';
    return makeRequest({
        url, data: { ...data }
    }).then(function (response) {
        handleAcceptResponse(dispatch, response);
        return response;
    }).catch(error => {
        return error;
    });
}
export function handleAcceptResponse(dispatch, response) {
    if (response.status == "success") {
        dispatch(AcceptSuccess());
    }
}
export function AcceptSuccess() {
    return { type: 'ACCEPT_SUCCESS' };
}

// For Airport code
export const originInputChange = (values)  => {
    const { args, dispatch, getState } = values;
    const sortwith = args.sortwith.toUpperCase();
    const origin = args.origin;
    const state = getState();


   const  origintodisplay = origin.filter(function (obj)  {
        if (obj != null) return obj.startsWith(sortwith);
        else null;
        });

   dispatch({ type: 'AIRPORT_FILTER_SUCCESS', origintodisplay:origintodisplay,displayDestination:origintodisplay});
    
}
//For Claim History pop up

export function claimHistory(values) {
        const { args, dispatch, getState } = values;
        const state = getState();
        const index = args.index;
        let listedmailbags = [];
        listedmailbags = state.form.newMailbagsTable.values.newMailbagsTable ? state.form.newMailbagsTable.values.newMailbagsTable : [];
        const selectedInvoicDetails = listedmailbags[index];
        let mailbagId='';
        let malseqnum='';
        mailbagId = selectedInvoicDetails.mailIdr;
        malseqnum = selectedInvoicDetails.mailSequenceNumber;
    var url = 'mail.mra.gpareporting.ux.claimHistoryPopup.screenLoad.do?formCount=1&mailbagId='+mailbagId+'&malseqnum='+malseqnum ;
       
    var closeButtonIds = [];
    var optionsArray = {
        url,
        dialogWidth: "900",
        dialogHeight: "580",
        closeButtonIds: closeButtonIds,
        popupTitle: 'Claim History'
    }
    dispatch(dispatchAction(openPopup)(optionsArray));


}



//For Group move to

export function moveToAction(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const toProcessStatus = args.actionName;
    let listedmailbags = [];
    let indexes = [];
    let count = 0;
    let selectedInvoicDetails = [];
    listedmailbags = state.form.newMailbagsTable.values.newMailbagsTable ? state.form.newMailbagsTable.values.newMailbagsTable : [];
    const length = listedmailbags.length;
     indexes = (state.filterReducer.selectedMailbagIndex) ? state.filterReducer.selectedMailbagIndex : [];
    const invoicFilter = (state.form.invoicFilter.values) ? state.form.invoicFilter.values : {}
       for (let i = 0; i < indexes.length; i++) {
        selectedInvoicDetails[count++] = listedmailbags[indexes[i]];
    } 
   
    const data = { toProcessStatus,selectedInvoicDetails,invoicFilter };
    const url = 'rest/mail/mra/gpareporting/invoicenquiry/moveto';
    return makeRequest({
        url, data: { ...data }
    }).then(function (response) {
        handlemovetoResponse(dispatch, response);
        return response;
    }).catch(error => {
        return error;
    });
}

export function handlemovetoResponse() {
    return { type: 'MOVE_TO_SUCCESS' };
}

//For Individual move to
export function moveToActionIndividual(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const toProcessStatus = args.toProcessStatus;
    const index = args.index;
    let count=0;
    let listedmailbags = [];
    let selectedInvoicDetails = [];
    listedmailbags = state.form.newMailbagsTable.values.newMailbagsTable ? state.form.newMailbagsTable.values.newMailbagsTable : [];
    const length = listedmailbags.length;
    const invoicFilter = (state.form.invoicFilter.values) ? state.form.invoicFilter.values : {};

    selectedInvoicDetails[count] = listedmailbags[index];
    const data = { toProcessStatus,selectedInvoicDetails,invoicFilter };
    const url = 'rest/mail/mra/gpareporting/invoicenquiry/moveto';
    return makeRequest({
        url, data: { ...data }
    }).then(function (response) {
        handlemovetoIndividualResponse(dispatch, response);
        return response;
    }).catch(error => {
        return error;
    });
}

export function handlemovetoIndividualResponse() {
    return { type: 'MOVE_TO_SUCCESS' };
}


//For reset
export function onReset(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const invoicFilter  = (state.form.invoicFilter.values)?state.form.invoicFilter.values:{};
    invoicFilter.selectedProcessStatus = [];
    invoicFilter.selectedClaimRange=[];

    dispatch({ type: 'RESET_FILTER',invoicFilter});
    dispatch(reset('extendedFilter'));
  
}

export const warningHandler = (action, dispatch) => {
    switch (action.type) {
        case "__DELEGATE_WARNING_ONOK":
            if (action.executionContext) {
                const warningCode = action.warningCode;
                const warningMapValue = { [warningCode]: 'Y' };
                dispatch(asyncDispatch(action.executionContext.functionRecord)(action.executionContext.args)).then(() =>{
                    dispatch(asyncDispatch(listInvoicDetails)({ 'displayPage': '1', mode: 'LIST' }))
                     } );
            }
            break;
        case "__DELEGATE_WARNING_ONCANCEL":
            if (action.executionContext) {
                const warningCode = action.warningCode;
                const warningMapValue = { [warningCode]: 'N' };
                dispatch({ type: 'SET_WARNING_VALUE', warningMapValue });
            }
            break;
        default:
            break;
    }
}



export const selectContainers=(values)=> {
   const { args, dispatch, getState } = values;
   const state = getState(); 
   const containerDetails= args; 
   dispatch( { type: 'SELECT_CONTAINER', containerDetails});      
}



export function onCloseFunction(values) {
    const { args, dispatch, getState } = values;
    let state = getState();
    const fromScreen = state.filterReducer.filterValues ? state.filterReducer.filterValues.fromScreen:'';
    if(fromScreen === 'MRA078'){

     navigateToScreen('mail.mra.gpareporting.ux.listinvoic.list.do', {});
    }
    else{
    navigateToScreen('home.jsp', {});
    }
}

export function onReprocess(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    let listedmailbags = [];
    let indexes = [];
    let count = 0;
    let selectedInvoicDetails = [];
    listedmailbags = state.form.newMailbagsTable.values.newMailbagsTable ? state.form.newMailbagsTable.values.newMailbagsTable : [];
    const length = listedmailbags.length;
    indexes = (state.filterReducer.selectedMailbagIndex) ? state.filterReducer.selectedMailbagIndex : [];
    for (let i = 0; i < indexes.length; i++) {
        selectedInvoicDetails[count++] = listedmailbags[indexes[i]];
    }

    const data = { selectedInvoicDetails };
    const url = 'rest/mail/mra/gpareporting/invoicenquiry/reprocess';
    return makeRequest({
        url, data: { ...data }
    }).then(function (response) {
        handleReprocessResponse(dispatch, response);
        return response;
    }).catch(error => {
        return error;
    });
}


export function handleReprocessResponse(dispatch, response) {
    if (response.status == "success") {
        dispatch(reprocessSuccess());
    }
}

export function reprocessSuccess() {
    return { type: 'REPROCESS_SUCCESS' };
}

export function validateClaimAmount (claimAmount){
    
    let isValid = true;

    if(claimAmount==undefined) {
        isValid = false;
    }else{
        if(claimAmount==="") {
            isValid = false;
            }
           else if(isNaN(claimAmount)){
            if(claimAmount.includes(".")&& !isNaN(claimAmount)){
                isValid=true; 
            }
            else{
                isValid=false;
            }
           }          
        }

    return isValid;

}

//For Accept
export function onReject(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const raiseClaimFlag = 'R';
    let listedmailbags = [];
    let indexes = [];
    let count = 0;
    let selectedInvoicDetails = [];
    listedmailbags = state.form.newMailbagsTable.values.newMailbagsTable ? state.form.newMailbagsTable.values.newMailbagsTable : [];
    const length = listedmailbags.length;
     indexes = (state.filterReducer.selectedMailbagIndex) ? state.filterReducer.selectedMailbagIndex : [];
    const invoicFilter = (state.form.invoicFilter.values) ? state.form.invoicFilter.values : {}
       for (let i = 0; i < indexes.length; i++) {
        selectedInvoicDetails[count++] = listedmailbags[indexes[i]];
    } 
   
    const data = { raiseClaimFlag,selectedInvoicDetails,invoicFilter };
    const url = 'rest/mail/mra/gpareporting/invoicenquiry/accept';
    return makeRequest({
        url, data: { ...data }
    }).then(function (response) {
        handleAcceptResponse(dispatch, response);
        return response;
    }).catch(error => {
        return error;
    });
}
