import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import {reset} from 'redux-form';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import commonReducer from '../reducers/commonreducer';
import { dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';


export function toggleFilter(screenMode) {  
    return {type: 'TOGGLE_FILTER',screenMode };
  }
  
export function fetchFlightCapacityDetails(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const filter = state.form.mailTransitFilterForm.values;
    const { flightnumber, ...rest } = filter;
    const mailTransitFilter = { ...rest, ...flightnumber, flightnumber: flightnumber };
    const mailTransitList = state.filterReducer.mailTransitDetails.results;
    const data = { "mailTransitFilter": mailTransitFilter, "mailTransitList": mailTransitList };
    const url = 'rest/mail/operations/mailtransitoverview/fetchMailTransitCapacityDetails';
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        let rowData=null;
        handleResponse(dispatch, response, null, data,rowData, "LIST_CAPPACITY_DETAILS");
        return response
    })
        .catch(error => {
            return error;
        });
}
export function fetchFlightCapacityDetailPerRow(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const filter = state.form.mailTransitFilterForm.values;
   
    const { flightnumber, ...rest } = filter;
    const mailTransitFilter = { ...rest, ...flightnumber, flightnumber: flightnumber };
    let rowData=args.rowData;
    //dispatch({ type: 'UPDATE_CAPACITY_DETAILS_KEY',rowData});  
 

    const mailTransitList = [args.rowData];
    const data = { "mailTransitFilter": mailTransitFilter, "mailTransitList": mailTransitList ,rowData};
    const url = 'rest/mail/operations/mailtransitoverview/fetchMailTransitCapacityDetails';
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        handleResponse(dispatch, response, null, data,rowData ,"LIST_CAPPACITY_DETAILS_ROW");
        return response
    })
        .catch(error => {
            return error;
        });
}
export function listMailTransitOverview(values) {
    
    const { args, dispatch, getState } = values;
    const state = getState();
    const {displayPage,action} = args;

    const filter  = state.form.mailTransitFilterForm.values;
    const pageSize = args && args.pageSize ? args.pageSize : state.commonReducer.pageSize;
    filter.displayPage=displayPage;
    filter.pageNumber= displayPage;
	filter.pageSize=pageSize;
    const screenMode =  state.filterReducer.screenMode;
    const { flightnumber, ...rest } = filter;
    const mailTransitFilter = { ...rest, ...flightnumber, flightnumber: flightnumber };
    
    
                
            
               
                  
                  if(!state.form.mailTransitFilterForm.values.airportCode && !state.form.mailTransitFilterForm.values.fromDate && !state.form.mailTransitFilterForm.values.toDate){
                    return Promise.reject(new Error("Please enter Airport code,From Date and To Date"));
                  }
                else if(!state.form.mailTransitFilterForm.values.airportCode && !state.form.mailTransitFilterForm.values.fromDate){
                    return Promise.reject(new Error("Please enter Airport code and From Date"));
                }
                else if(!state.form.mailTransitFilterForm.values.fromDate && !state.form.mailTransitFilterForm.values.toDate){
                    return Promise.reject(new Error("Please enter From Date  and To Date"));
                }
                else if(!state.form.mailTransitFilterForm.values.toDate && !state.form.mailTransitFilterForm.values.airportCode){
                    return Promise.reject(new Error("Please enter To Date and AiportCode"));
                }

    else if (!state.form.mailTransitFilterForm.values.airportCode || !state.form.mailTransitFilterForm.values.fromDate || !state.form.mailTransitFilterForm.values.toDate) {
            if(!state.form.mailTransitFilterForm.values.airportCode){
                return Promise.reject(new Error("Please enter Airport code"));
            }
           else if(!state.form.mailTransitFilterForm.values.fromDate){
                return Promise.reject(new Error("Please enter From Date"));
            }
            if(!state.form.mailTransitFilterForm.values.toDate){
                return Promise.reject(new Error("Please enter To Date"));
            }
            
        }
        else{
                    

    const data = {mailTransitFilter};// same name should be given in the backend the values from frontend (airportcode frpmDate, toDate) are combined into a mailtransitfilter and then going to the backend
            const url = 'rest/mail/operations/mailtransitoverview/fetchMailTransitDetailsForEnquiry';
            return makeRequest({
                url,
                data: {...data}
            }).then(function (response) {

                
                if (args && args.mode === 'EXPORT') {
                    if(response.results!==null){
                    let exportMailbags = response.results[0].mailTransits;
                    return exportMailbags;
                    }
                }  
               else if  (screenMode!=='initial'){
                dispatch({ type: 'UPDATE_NEXT_PAGE_VALUE'})
               }
            let rowData=null;
            handleResponse(dispatch, response, action, data,rowData, "List");
                return response
                
            })
            .catch(error => {
                return error;
            });
        }
    }
              
export const clearFilter=(values)=> {
    const {dispatch} = values;  
    dispatch({ type: 'CLEAR_FILTER'});
    dispatch(reset('mailTransitFilterForm')); //to clear the values from filter   
}
export const clearFilterNoRecords=(values)=> {
    const {args,dispatch} = values; 
    let fromDate=args.mailTransitFilter.fromDate;
    let toDate=args.mailTransitFilter.toDate;
    dispatch({ type: 'CLEAR_FILTER',fromDate,toDate});
      
}


function handleResponse(dispatch, response, action, data,rowData, mode) {
    //const pageSize = data.mailTransitFilter && data.mailTransitFilter.pageSize ? data.mailTransitFilter.pageSize : 100;
    if (mode === "List") {
                if(response.results===null){
                
                    dispatch(dispatchAction(clearFilterNoRecords)(data));
            }
                else {
                    
                const{ mailTransits}=response.results[0];
               

               

                if (action==='LIST') {
                       dispatch( { type: 'LIST_SUCCESS', data, mailTransits});
                } 
        }
    }
                
    else if (mode === 'LIST_CAPPACITY_DETAILS') {
        if (response.results !== null) {
            let flightCapacityDetails = response.results[0];
            dispatch({ type: 'LIST_CAPPACITY_DETAILS', flightCapacityDetails });
        }
            }
            else if (mode === 'LIST_CAPPACITY_DETAILS_ROW') {
                if (response.results !== null) {
                    let flightCapacityDetails = response.results[0];
                    dispatch({ type: 'LIST_CAPPACITY_DETAILS_ROW', flightCapacityDetails,rowData });
                }
                    }
        }
           
            