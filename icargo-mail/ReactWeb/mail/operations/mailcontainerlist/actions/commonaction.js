import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import { CommonUtil } from 'icoreact/lib/ico/framework/config/app/commonutil';
import {closePopupWindow} from 'icoreact/lib/ico/framework/component/common/modal/popuputils';

export function screenLoad(values) {
    const {dispatch} = values;
    const url='rest/mail/operations/mailcontainerlist/screenload';
    return makeRequest({
        url,data:{}
    }).then(function(response) {
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
    return { type: 'SCREENLOAD_SUCCESS', data };
}


export function onCloseFunction() {
    navigateToScreen('home.jsp', {});
     if(CommonUtil.screenConfig.isPopup === 'true') closePopupWindow();
}

export function updateSortVariables(data) {
    const { dispatch } = data;
    dispatch({ type: 'UPDATE_SORT_VARIABLE', data: data.args })
}

export function onSelectFunction(values) {
   const {args, dispatch, getState} = values;
   const state = getState();  
   let selectedContainerData=[];
   const indexes = state.commonreducer.selectedContainerIndex;   
   indexes.forEach( (ind) => {
    selectedContainerData.push(state.mailcontainerreducer.containerDetails.results[ind]); 
   } )
 
   if('MAINTAINLDPLN' === CommonUtil.screenConfig.fromScreen){ 
   const url = 'rest/mail/operations/mailcontainerlist/assignToLoadPlan'; 
   selectedContainerData.selectedContainerDetails = selectedContainerData;
      return makeRequest({
        url,
        data:selectedContainerData
      }).then(function (response) {
           if(response.status == "SUCCESS"){
              window.parent.targetFormName.action =  'operations.flthandling.loadplan.screenloaddisplayloadplan.do?fromScreen=MAINTAINLDPLN';
              window.parent.IC.util.common.childUnloadEventHandler();
              window.parent.targetFormName.submit();
              closePopupWindow();
            }
        return response;
      })
    .catch(error => {
        return error;
    });
   }
}