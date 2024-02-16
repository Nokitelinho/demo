import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { ActionType, Urls, Errors , Constants, Key } from '../constants/constants.js'
import { handleResponse} from './handleresponse.js'
import { openPopup } from 'icoreact/lib/ico/framework/action/openpopup';
import { asyncDispatch,dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { CommonUtil } from 'icoreact/lib/ico/framework/config/app/commonutil';
import { requestInfo} from 'icoreact/lib/ico/framework/component/common/store/commonactions.js';
import { listCarditDsnEnquiry} from './filteraction.js';


export const screenLoad=(args) => { 

    const{dispatch}=args;
    const url=Urls.SCREENLOAD_CARDIT_DSN_ENQUIRY;
    return makeRequest({
        url,data:{}
    }).then(function(response) {
        const payLoad={ type: ActionType.SCREENLOAD_SUCCESS, response, dispatch }
        handleResponse(payLoad);
        return response;
    })   

}

export const attachAll =(values) => {
    const { args, dispatch, getState } = values;
    const state = getState();
    let url = null;
    let optionsArray = null;
    let mailbagDetailsCollection = args&&args.results[0]&&args.results[0].mailbagDetailsCollection?
                                            args.results[0].mailbagDetailsCollection:{};
    let mailbagVoCollection = args&&args.results[0]&&args.results[0].mailbagVoCollection?
                                            args.results[0].mailbagVoCollection:{};
    let mailbagsSelected='';
    if(!isEmpty(mailbagDetailsCollection)){
        let flag=false;
        mailbagDetailsCollection.forEach(element => {
            if(element.accepted==='Y'){
                flag=true;
            }
        });
        if(flag){
            return Promise.reject(new Error(Errors.ATTACH_ACP_ERR));
        }
        mailbagDetailsCollection=mailbagDetailsCollection.filter((element)=>{
            if(element.masterDocumentNumber==null||element.masterDocumentNumber.trim().length==0)
                return true;
            else
                return false;
        })
        if(mailbagDetailsCollection.length==0){
            return Promise.reject(new Error(Errors.ATTACH_NOT_POSSIBLE));
        }
        const selectedCount=mailbagDetailsCollection.length;
        if( selectedCount>Number(state.filterReducer.mailCountFromSyspar)){
             const COUNT_HIGHER_ERROR=Errors.ATTACH_NOT_POSSIBLE_COUNT+" "+Number(state.filterReducer.mailCountFromSyspar)+" "+Errors.COUNT_ERROR;
            return Promise.reject(new Error(COUNT_HIGHER_ERROR));
        } 
        mailbagVoCollection=mailbagVoCollection.filter((element)=>{
            if(element.documentNumber==null||element.documentNumber.trim().length==0)
                return true;
        })
        const response=mailbagVoCollection;
        const payLoad={ type: ActionType.SAVE_SELECTED_MAILBAGVOS, response, dispatch }
        handleResponse(payLoad);

        url = Urls.ATTACH_AWB;
 
        optionsArray = {
            url,
            dialogWidth: "1300",
            dialogHeight: "650",
            popupTitle: Constants.LIST_BOOKING_POPUP_TITLE,
            closeButtonIds:[Constants.POPUP_CLOSE_ID]
            }
        dispatch(dispatchAction(openPopup)(optionsArray));
        return Promise.resolve({});
    }
    else{
        const filterValues=state.filterReducer.filterValues;
        if(filterValues.conDocNo!=null){
            dispatch(requestWarning([{code:Key.ATTACH_AWB_WARN,description:Errors.ATTACH_WARN}],
                                {functionRecord:attachAwbWhenNotSelected}))
            return Promise.reject({});
        }
        else{
            return Promise.reject(new Error(Errors.ATTACH_NOT_SELECTED_ERR));
        }
    }
    

}

/**
 * Method to attach awb when no 
 * select check boxes are selected. It 
 * is invoked from the warning handler.
 */
export const attachAwbWhenNotSelected = (values) =>{
    const { args, dispatch, getState } = values;
    const state = getState();
    let url = null;
    let optionsArray = null;
    let mailbagDetailsCollection = args&&args.results[0]&&args.results[0].mailbagDetailsCollection?
                                            args.results[0].mailbagDetailsCollection:{};
    let mailbagVoCollection = args&&args.results[0]&&args.results[0].mailbagVoCollection?
                                            args.results[0].mailbagVoCollection:{};
    let mailbagsSelected='';
    if(!isEmpty(mailbagDetailsCollection)){
        mailbagDetailsCollection=mailbagDetailsCollection.filter((element)=>{
            if(element.masterDocumentNumber==null||element.masterDocumentNumber.trim().length==0)
                return true;
            else
                return false;
        })
        if(mailbagDetailsCollection.length==0){
            return Promise.reject(new Error(Errors.ATTACH_NOT_POSSIBLE));
        }
        mailbagVoCollection=mailbagVoCollection.filter((element)=>{
            if(element.documentNumber==null||element.documentNumber.trim().length==0)
                return true;
        })
        const response=mailbagVoCollection;
        const payLoad={ type: ActionType.SAVE_SELECTED_MAILBAGVOS, response, dispatch }
        handleResponse(payLoad);
        url = Urls.ATTACH_AWB;
 
        optionsArray = {
            url,
            dialogWidth: "1300",
            dialogHeight: "650",
            popupTitle: Constants.LIST_BOOKING_POPUP_TITLE,
            closeButtonIds:[Constants.POPUP_CLOSE_ID]
            }
        dispatch(dispatchAction(openPopup)(optionsArray));
        return Promise.resolve({});
    }
}


export const detachAll =(values) => {
    const { args, dispatch, getState } = values;
    const state = getState();
    let mailbagDetailsCollection = args&&args.results[0]&&args.results[0].mailbagDetailsCollection?
                                            args.results[0].mailbagDetailsCollection:{};
    let mailbagVoCollection = args&&args.results[0]&&args.results[0].mailbagVoCollection?
                                            args.results[0].mailbagVoCollection:{};
    if(!isEmpty(mailbagDetailsCollection)){
        mailbagDetailsCollection= mailbagDetailsCollection.filter((element)=>{
            if(element.masterDocumentNumber!=null && element.masterDocumentNumber.trim().length>0)
                return true;
        })
        if(mailbagDetailsCollection.length==0){
            return Promise.reject(new Error(Errors.DETACH_ERROR));
        }
        const selectedCount=mailbagDetailsCollection.length;
        if( selectedCount>Number(state.filterReducer.mailCountFromSyspar)){
             const COUNT_HIGHER_ERROR=Errors.DETACH_NOT_POSSIBLE_COUNT+" "+Number(state.filterReducer.mailCountFromSyspar)+" "+Errors.COUNT_ERROR;
            return Promise.reject(new Error(COUNT_HIGHER_ERROR));
        } 
        mailbagVoCollection=mailbagVoCollection.filter((element)=>{
            if(element.documentNumber!=null&& element.documentNumber.trim().length>0)
                return true;
        })
        const carditFilter=state.filterReducer.carditFilter;
        const data={carditFilter,mailbagDetailsCollection,mailbagVoCollection}
        const url= Urls.DETACH_AWB;
        return makeRequest({
            url,
            data:{...data}
        }).then(function(response) {
            const payLoad={ type: ActionType.DETACH_SUCCESS, dispatch }
            handleResponse(payLoad);
            CommonUtil.store.dispatch(requestInfo({ "msgkey": Key.DETACH_AWB_SUCCESS, "defaultMessage": Constants.DETACH_SUCCESS, 
                                "data": [] }));
            return response;
        })
    }
    else{
        const carditFilter=state.filterReducer.carditFilter;
        if(carditFilter.documentNumber!=null&&carditFilter.documentNumber.trim().length>0){
            dispatch(requestWarning([{code:Key.DETACH_AWB_WARN,description:Errors.DETACH_WARN}],
                                {functionRecord:detachAwbWhenNotSelected}))
            return Promise.reject({});
        }
        else{
            return Promise.reject(new Error(Errors.DETACH_NOT_SELECTED_ERR));
        }
    }
}

/**
 * Method to detach awb when no 
 * select check boxes are selected. It 
 * is invoked from the warning handler.
 */
export const detachAwbWhenNotSelected = (values) => {
    const { args, dispatch, getState } = values;
    const state = getState();
    const mailbagDetailsCollection = args&&args.results[0]&&args.results[0].mailbagDetailsCollection?
                                            args.results[0].mailbagDetailsCollection:{};
    let mailbagVoCollection = args&&args.results[0]&&args.results[0].mailbagVoCollection?
                                            args.results[0].mailbagVoCollection:{};
    if(!isEmpty(mailbagDetailsCollection)){
        const carditFilter=state.filterReducer.carditFilter;
        const data={carditFilter,mailbagDetailsCollection,mailbagVoCollection}
        const url= Urls.DETACH_AWB;
        return makeRequest({
            url,
            data:{...data}
        }).then(function(response) {
            const payLoad={ type: ActionType.DETACH_SUCCESS, dispatch }
            handleResponse(payLoad);
            return response;
        })
    }
}


/**
 * Method to fetch mailbags for the
 * corresponding selected dsns.
 * 
 */
export const fetchMailbagsForDsns=(values) => {
    const { args, dispatch, getState } = values;
    const state = getState();
    let indexes = state.detailsReducer.selectedIndexes;
    if(args&&args.index){
        indexes=[];
        indexes.push(args.index);
    }
    const dsnDetailsCollectionFromStore=sortDsnData({getState});
    const {flightNumber,...rest} = state.filterReducer.filterValues;
    let carditFilter={...rest};
    let dsnDetailsCollection = [];
    if(!isEmpty(dsnDetailsCollectionFromStore)){ 
        indexes.forEach(element => {
            dsnDetailsCollection.push(dsnDetailsCollectionFromStore[element]);
        });
    }
    if(args&&args.nonSelectionOperation){
        carditFilter={...carditFilter,nonSelectionOperation:args.nonSelectionOperation,
                            totalMailbagCount:state.filterReducer.totalPieces}
    }
    const data={dsnDetailsCollection:dsnDetailsCollection,carditFilter:carditFilter}
    const url= Urls.LIST_CARDIT_MAILBAGS_DSN;
            return makeRequest({
                url,
                data:{...data}
            }).then(function(response) {
                return response;
            })
}



const filterDsnData = (state) => {
    const getTableResults = () => state.filterReducer.dsnDetails 
                                && state.filterReducer.dsnDetails.results ? 
                                    state.filterReducer.dsnDetails.results : [];
    const getTableFilter = () => state.detailsReducer.dsnFilter ? state.detailsReducer.dsnFilter : {}
    const getDetails = createSelector([getTableResults, getTableFilter], (results, filterValues) => {
                if (!isEmpty(filterValues)) {  
                    results.filter((obj) => { 
                    obj.year=obj.year.toString();
                    const anotherObj = { ...obj, ...filterValues}; 
                    if(JSON.stringify(obj)===JSON.stringify(anotherObj))
                            return true;
                    else 
                        return false 
                    })} 
                return results;       
                });
    return getDetails;
}


export const sortDsnData = (values) => {
        const { getState } = values;
        const state = getState(); 
        const getSortDetails = () => state.detailsReducer.sort;
        const getSortedDetails = createSelector([getSortDetails, filterDsnData(state)], (sortDetails, mailbags) => {
        if (sortDetails) {
            const sortBy = sortDetails.sortBy;
            const sortorder = sortDetails.sortByItem;
            mailbags.sort((record1, record2) => {
                let sortVal = 0;
                let data1;
                let data2;
                data1 = record1[sortBy] && typeof record1[sortBy] === "object"?record1[sortBy].systemValue:record1[sortBy];
                data2 =record2[sortBy] && typeof record2[sortBy] === "object"? record2[sortBy].systemValue:record2[sortBy];
                if(sortBy==='flightDate'){
                    if((moment.utc(data1).diff(moment.utc(data2)))>0){
                        sortVal=1;
                    }else if((moment.utc(data1).diff(moment.utc(data2)))<0){
                        sortVal=-1;
                    }
                }
                else{
                    if(sortBy==='weight'){
                        data1=parseFloat(data1);
                        data2=parseFloat(data2);
                    }
                if(data1===null){
                    data1='';
                }    
                if(data2===null){
                    data2='';
                }
                if (data1 > data2) {
                    sortVal = 1;
                }
                if (data1 < data2) {
                    sortVal = -1;
                }
                }
                if (sortorder === 'DSC') {
                    sortVal = sortVal * -1;
                }
                return sortVal;
            });
        }
        return mailbags;
        });
    return getSortedDetails();
}


export const warningHandler = (action, dispatch) => {
    switch (action.type) {
        case Constants.__DELEGATE_WARNING_ONOK:
            if (action.executionContext) {
                const warningCode = action.warningCode;
                if (warningCode[0] === Key.ATTACH_AWB_WARN) {
                    dispatch(asyncDispatch(fetchMailbagsForDsns)({nonSelectionOperation:Constants.ATTACH}))
                        .then((response)=>dispatch(asyncDispatch(attachAwbWhenNotSelected)(response)))
                            .then(()=> dispatch(requestWarning([{code:Key.RELIST_WARN,description:Errors.RELIST_WARN}],
                                {functionRecord:listCarditDsnEnquiry})));
                }
                else if (warningCode[0] === Key.DETACH_AWB_WARN) {
                    dispatch(asyncDispatch(fetchMailbagsForDsns)({nonSelectionOperation:Constants.DETACH}))
                        .then((response)=>dispatch(asyncDispatch(detachAwbWhenNotSelected)(response)))
                            .then(()=>dispatch(asyncDispatch(listCarditDsnEnquiry)({mode:Constants.LIST})));
                }
                else if (warningCode[0] === Key.RELIST_WARN) {
                    dispatch(asyncDispatch(listCarditDsnEnquiry)({mode:Constants.LIST}))
                }
            }
            break;
        case Constants.__DELEGATE_WARNING_ONCANCEL:
            if (action.executionContext) {
            }
            break;
        default:
            break;
    }
}



