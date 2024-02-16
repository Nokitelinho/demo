import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import * as constant from '../constants/constants';

export const listContainersinCarrier=(values)=> {
    const { args, dispatch, getState } = values;
    const state = getState();
    const mailFlight=state.listFlightReducer.carrierDetails.results[args[0].index];
    const carriersArray= state.containerReducer.carriersArray;
    const carriernotAlready=getContainerDetails(mailFlight,carriersArray);
    if(carriernotAlready) {
    const data ={mailFlight}
    const url = 'rest/mail/operations/outbound/listContainers';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        handleResponse(dispatch, response,carriersArray);
        return response
    })
    .catch(error => {
        return error;
    });}
  //  dispatch( { type: 'LIST_DETAILS', data:data});
   dispatch( { type: constant.LIST_CONTAINER_CARRIER,carriersArray,mode:'multi' });
      
}


function getContainerDetails(mailCarrier, carriersArray) {
    let isValid = false;
     if(!isEmpty(carriersArray)){
      const selectedCarrier = carriersArray.filter( (obj)  =>  {   const anotherObj = { ...obj, ...mailCarrier.carrierpk}; 
                               return JSON.stringify(obj)===JSON.stringify(anotherObj) } );
       if(isEmpty(selectedCarrier)){
           isValid=true;
       }
     }
    else {
        isValid=true;
    }
     return isValid;
        
}


function handleResponse(dispatch,response,carriersArray) {

    if(!isEmpty(response.results)){
        const {mailCarrier} = response.results[0];
        dispatch( { type: constant.LIST_CONTAINER_CARRIER, mailCarrier,carriersArray,mode:'multi' });
        // dispatch({type: 'CHANGE_DETAILPANEL_MODE',mode:'multi'});
       
       
    } else {
        if(!isEmpty(response.errors)){
             dispatch( { type: constant.CLEAR_TABLE});
        }
    }
}
