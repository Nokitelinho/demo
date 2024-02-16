import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import * as constant from '../constants/constants';
export function applyCarditFilter(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    let carditFilter  = {}
    let displayPage='';
    const activeCarditTab  = (state.carditReducer.activeCarditTab)?state.carditReducer.activeCarditTab:{}
    if(args) {
        carditFilter= state.carditReducer.carditfilterValues;
        if(activeCarditTab===constant.LIST_VIEW) {
            displayPage = args && args.displayPage ? args.displayPage : state.carditReducer.listDisplayPage
        }
        else {
             displayPage = args && args.displayPage ? args.displayPage : state.carditReducer.groupDisplayPage
        }
	      if(state.carditReducer.carditView &&state.carditReducer.carditView==='expanded')
        {   
             if(carditFilter.airportCode == null ||carditFilter.airportCode === undefined || carditFilter.airportCode.trim().length == 0){
                carditFilter.airportCode = state.filterReducer.filterValues ?  state.filterReducer.filterValues.airportCode : state.commonReducer.airportCode;
            }
        }
    }
    else {
        carditFilter= (state.form.carditFilter)?state.form.carditFilter.values:(state.carditReducer.carditfilterValues)?state.carditReducer.carditfilterValues:{}
        let flightNumber={};
        if(!isEmpty(carditFilter)){
         flightNumber=(carditFilter.flightnumber)?carditFilter.flightnumber:{};
        }
        if(!isEmpty(flightNumber)) {
            carditFilter.carrierCode = flightNumber.carrierCode;  
            carditFilter.flightNumber=flightNumber.flightNumber;
            carditFilter.flightDate=flightNumber.flightDate;
        }

       /* commented for IASCB-47325  if(activeCarditTab===constant.LIST_VIEW) {
            displayPage = state.carditReducer.listDisplayPage
        }
        else {
             displayPage =  state.carditReducer.groupDisplayPage
        }
        */
        displayPage=1;
        }
     carditFilter.displayPage = displayPage;
    carditFilter.carditActiveTab = activeCarditTab;
    const data = {carditFilter};
    const url='rest/mail/operations/outbound/listCardit';
    
   if(state.carditReducer.carditFilterApplied===false) {
       return Promise.resolve({})
   }
  else {
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        state.carditReducer.selectedCarditIndex = [];
        handleResponse(dispatch, response,data);
        return response
    })
    .catch(error => {
        handleResponseOnError(dispatch,data);
        return error;
    });
  }
   // return Object.keys(carditFilter).length;
   
}


function handleResponse(dispatch,response,data) {
    const {carditFilter} = data;
    if(!isEmpty(response.results)){
       if(carditFilter.carditActiveTab===constant.LIST_VIEW) {
        const {carditSummary,carditMailbags} = response.results[0];
        dispatch( { type: constant.CARDIT_LIST_VIEW, carditSummary,carditMailbags,data });
      }
      if(carditFilter.carditActiveTab===constant.GROUP_VIEW) {
        const {carditSummary,carditGroupMailbags} = response.results[0];
         dispatch( { type: constant.CARDIT_GROUP_VIEW, carditSummary,carditGroupMailbags,data });
      }
       
    }
    else {
        if(carditFilter.carditActiveTab===constant.LIST_VIEW) {
            dispatch( { type: constant.SAVE_CARDIT_LIST_FILTER,data });
           }
           if(carditFilter.carditActiveTab===constant.GROUP_VIEW) {
            dispatch( { type: constant.SAVE_CARDIT_GROUP_FILTER,data });
           }
    }
    
}

function handleResponseOnError(dispatch,data) {
        const {carditFilter} = data;

        if(carditFilter.carditActiveTab===constant.LIST_VIEW) {
           dispatch( { type: constant.SAVE_CARDIT_LIST_FILTER,data });
          }
          if(carditFilter.carditActiveTab===constant.GROUP_VIEW) {
           dispatch( { type: constant.SAVE_CARDIT_GROUP_FILTER,data });
          }
  
    
}


export function validateCarditFilterForm(data) {
    let error = ""
    let isValid = true;
    if (!(data.fromDate) ||!(data.toDate)) {
            isValid = false;
            error = "Please enter from date range" 
          
    }
      

    let validObject = {
        valid: isValid,
        msg: error
    }
    return validObject;
}


