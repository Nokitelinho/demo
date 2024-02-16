import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import * as constant from '../constants/constants';
import { reset } from 'redux-form';

export function applyLyingListFilter(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    let mailbagFilter  = {};
    let displayPage='';
    const activeLyingTab  = (state.lyingListReducer.activeLyingListTab)?state.lyingListReducer.activeLyingListTab:{}   
    if(args) {
        mailbagFilter= state.lyingListReducer.filterValues;
       
        if(activeLyingTab===constant.LIST_VIEW) {
            displayPage = args && args.displayPage ? args.displayPage : state.lyingListReducer.listDisplayPage
        }
        else {
         displayPage = args && args.displayPage ? args.displayPage : state.lyingListReducer.groupDisplayPage
        }
    }
    else {

        mailbagFilter=  (state.form.MailbagFilter)?state.form.MailbagFilter.values:(state.lyingListReducer.filterValues)?state.lyingListReducer.filterValues:''
        const flightNumber=(mailbagFilter.flightnumber) ?mailbagFilter.flightnumber:{};
        if(!isEmpty(flightNumber)) {
            mailbagFilter.carrierCode = flightNumber.carrierCode;  
            mailbagFilter.flightNumber=flightNumber.flightNumber;
            mailbagFilter.flightDate=flightNumber.flightDate;
        }
        displayPage='1';
    }
     
    mailbagFilter.displayPage=displayPage;
    mailbagFilter.lyingListActiveTab=activeLyingTab;
    if(isEmpty(mailbagFilter.scanPort)){
        mailbagFilter.scanPort=state.commonReducer.airportCode;
    }
    const data = {mailbagFilter};
    const url='rest/mail/operations/outbound/listLyingList';
    
   if(state.lyingListReducer.lyingFilterApplied===false) {
       return Promise.resolve({})
   }
  else {
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        handleResponse(dispatch, response,data);
        return response
    })
    .catch(error => {
        return error;
    });
  }
   
}

export function onClearFilter(values){
    const {dispatch} = values;  
    dispatch({ type: 'CLEAR_LYINGLIST_FILTER'});
    dispatch(reset('MailbagFilter'));

}
function handleResponse(dispatch,response,data) {
    
    if(!isEmpty(response.results)){
       
        const {mailbagFilter} = data;
      if(mailbagFilter.lyingListActiveTab===constant.LIST_VIEW) {
        const {lyinglistMailbags,carditSummary} = response.results[0];
        dispatch( { type: constant.LYING_LIST_VIEW,lyinglistMailbags,data ,carditSummary});
      }
      if(mailbagFilter.lyingListActiveTab===constant.GROUP_VIEW) {
        const {lyinglistGroupedMailbags,carditSummary} = response.results[0];
         dispatch( { type: constant.LYING_GROUP_VIEW,lyinglistGroupedMailbags,data,carditSummary });
      }
       
    } else {
        if(!isEmpty(response.errors)){
             dispatch( { type: constant.CLEAR_TABLE});
        }
    }
}