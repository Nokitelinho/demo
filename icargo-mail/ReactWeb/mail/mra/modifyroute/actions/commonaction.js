import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import { deleteRow } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { isDirty } from 'redux-form';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { dispatchAction} from 'icoreact/lib/ico/framework/component/common/actions';
import { ActionType, Urls,Constants,Errors } from '../constants/constants.js'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { reset } from 'redux-form';
import {change } from 'icoreact/lib/ico/framework/component/common/form';

export function screenLoad(values) {
    
    const { dispatch ,getState} = values;
    const state = getState();
    const url = Urls.SCREEN_LOAD;
    return makeRequest({
        url, data: {}
    }).then(function (response) {
        handleScreenloadResponse(dispatch, response);
        return response;
    })

}

export function handleScreenloadResponse(dispatch, response) {
    if (response.results) {
        dispatch(screenLoadSuccess(response.results[0]));       
    }
}
export function screenLoadSuccess(data) {
    return { type: ActionType.SCREENLOAD_SUCCESS, data };
}


export function deleteRows(values) {
    const { dispatch, getState } = values;
    const state = getState();
    const rowIndex = state.commonReducer.selectedFlightDetailIndex ? state.commonReducer.selectedFlightDetailIndex : [];
    if (rowIndex.length > 0) {
        dispatch(deleteRow(Constants.ROUTEDETAIL_TABLE, rowIndex))
    } else {
        dispatch(requestValidationError(Errors.NOROWS_SELECTED, ''));
    }
}

export function getTotalCount(values) {
    const { dispatch, getState } = values;
    const state = getState();
    let selectedFlightDetailIndex=[];
    const flightDetails = (state.form.routeDetailsTable && state.form.routeDetailsTable.values && state.form.routeDetailsTable.values.routeDetailsTable) ?
        state.form.routeDetailsTable.values.routeDetailsTable : [];
    const count = flightDetails.length;
    for (let i = 0; i < count; i++) {
        selectedFlightDetailIndex.push(i);
    }
    dispatch({ type: ActionType.SELECTED_COUNT, selectedFlightDetailIndex });
}

export function saveUnselectedFlightDetailIndex(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const index = args;
    let indexes = state.commonReducer.selectedFlightDetailIndex ? state.commonReducer.selectedFlightDetailIndex : [];
    if (index > -1) {
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
    } else {
        indexes = [];
    }
    dispatch({ type: ActionType.SAVE_SELECTED_INDEX, indexes });
}

export function saveSelectedFlightDetailIndex(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const index = args;
    let indexes = state.commonReducer.selectedFlightDetailIndex ? state.commonReducer.selectedFlightDetailIndex : [];
    indexes.push(index);
    dispatch({ type: ActionType.SAVE_SELECTED_INDEX, indexes });
}



export function execute(values){

    const { dispatch, getState } = values;
    const state = getState();
    let validObject = {};
    const flightDetails = (state.form.routeDetailsTable && state.form.routeDetailsTable.values && state.form.routeDetailsTable.values.routeDetailsTable) ?
        state.form.routeDetailsTable.values.routeDetailsTable : [];
     
    let transferPA= (state.form.routeDetailsPanel.values && state.form.routeDetailsPanel.values.transferPA )?state.form.routeDetailsPanel.values.transferPA:""
    let transferAirline = (state.form.routeDetailsPanel.values && state.form.routeDetailsPanel.values.transferAirline)?state.form.routeDetailsPanel.values.transferAirline:""

    validObject.valid= true;
    validObject=validateRouteDetails(flightDetails,transferPA,transferAirline);
    
    let modifyRouteFilter= state.commonReducer.parentFilter;

    if(!validObject.valid){
        dispatch(requestValidationError(validObject.msg, ''));
        return Promise.resolve("Error"); 
    }else{

    const url = Urls.EXECUTE;
    return makeRequest({
        url, data: {flightDetails,modifyRouteFilter,transferPA,transferAirline}
    }).then(function (response) {
     
        handleExecuteResponse(response,values);
        
        return response;
    }) .catch(error => {
            return error;
        });

    }


}

export function handleExecuteResponse(response,values) {
    if (response.results) {
    const { dispatch,getState } = values;
    dispatch(reset(Constants.ROUTEDETAIL_TABLE));
    }
}

export function validateRouteDetails(routeDetails,transferPA,transferAirline){
    let isValid = true;
    let error = ""

    if(isEmpty(routeDetails) && isEmpty(transferPA) && isEmpty(transferAirline)){
        isValid = false;
        error =Errors.EMPTY_ROUTE_DETAILS
    }
     if(!isEmpty(transferPA) && !isEmpty(transferAirline)){
        isValid = false;
        error =Errors.ENTER_ONLYONEVALUE
    }

    for(let i=0;i<routeDetails.length;i++){
            let data = routeDetails[i];
            if(!data) {
            isValid = false;
            error = Errors.EMPTY_ROUTE_DETAILS
            }
           else if(!data.carrierCode || data.carrierCode==="") {
            isValid = false;
            error = Errors.EMPTY_CARRIERCODE
           }
            else if(!data.flightNumber || data.flightNumber==="") {
            isValid = false;
            error = Errors.EMPTY_FLIGHTNO
           }
            else if(!data.flightDate || data.flightDate==="") {
            isValid = false;
            error = Errors.EMPTY_DEPDATE
           }
            else if(!data.pol || data.pol==="") {
            isValid = false;
            error = Errors.EMPTY_POL
           }
            else if(!data.pou || data.pou==="") {
            isValid = false;
            error = Errors.EMPTY_POU
           }

    }      


    let validObject = {
        valid: isValid,
        msg: error
    }
    return validObject;


}

export const populateFlightNumber= (values) =>{
    const{ args,dispatch, getState } = values; 
    const state = getState();
    const index = args;
    const routeDetailsTable=state.form.routeDetailsTable?(state.form.routeDetailsTable.values.routeDetailsTable):[];
    let flightNumber =(routeDetailsTable && routeDetailsTable[index] && routeDetailsTable[index].flightNumber )? routeDetailsTable[index].flightNumber:'';

    if(flightNumber){
        var digits=flightNumber.length;
   var count=4-digits;
   for(let k=0;k<count;k++){
    flightNumber="0"+flightNumber;
   }
    routeDetailsTable[index].flightNumber=flightNumber;

    }
dispatch(change('routeDetailsTable', `routeDetailsTable.${index}.flightNumber`, flightNumber));

}



