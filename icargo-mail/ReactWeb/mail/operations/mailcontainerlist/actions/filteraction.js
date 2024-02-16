import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import {reset} from 'redux-form';

export function onlistContainerDetails(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const {displayPage,action} = args;
    const segmentDestination =  state.filterreducer.containerFilter.destination;
    let containerFilter  = (state.form.containerFilter.values)?state.form.containerFilter.values:{};
    const uldFulIndFlag=state.form.containerFilter.values.uldFulIndFlag;
    if(uldFulIndFlag){
    state.form.containerFilter.values.uldFulIndFlag='Y';
    }
    const unplannedContainers=state.form.containerFilter.values.unplannedContainers;
    containerFilter.unplannedContainers=unplannedContainers;
    
    const pageSize = args && args.pageSize ? args.pageSize : state.mailcontainerreducer.pageSize; 
    containerFilter.pageSize=pageSize;

    if(displayPage) {
        containerFilter.displayPage=displayPage;
    } 
    containerFilter.destination = containerFilter.destination ? containerFilter.destination : segmentDestination ;
    const data = {containerFilter};
    const url = 'rest/mail/operations/mailcontainerlist/list';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        handleResponse(dispatch, response,action,containerFilter);
        return response;
    })
    .catch(error => {
        return error;
    });
}

export function toggleFilter(screenMode) {  
    return {type: 'TOGGLE_FILTER',screenMode };
  }

function handleResponse(dispatch,response,action, containerFilter) {
    
    if(!isEmpty(response.results)){
        console.log(response.results[0]);
        const {containerDetails} = response.results[0];
        const data = response.results[0].containerFilter;
        const summaryFilter = makeSummaryFilter(data);
        
        
        if (action==='LIST') {
           dispatch( { type: 'LIST_SUCCESS',data, containerDetails,containerFilter,summaryFilter});
        }       
    } else {
        if(!isEmpty(response.errors)){
             dispatch( { type: 'CLEAR_TABLE'});
        }
    }
}


export const clearFilter=(values)=> {
    const {dispatch, getState} = values;
    const state = getState();  
    dispatch({ type: 'CLEAR_FILTER',});
    dispatch(reset('containerFilter'));
}

function makeSummaryFilter(data){
    
    let filter={};

    if(data.destination && data.destination.length > 0){
        filter={...filter, destination:data.destination};
    }
     if(data.subclassGroup && data.subclassGroup.length > 0 && data.subclassGroup === 'true'){
            filter={...filter, subclassGroup:data.subclassGroup};
    }
    if(data.uldFulIndFlag && data.uldFulIndFlag.length > 0 && data.uldFulIndFlag==='Y'){
        filter={...filter, uldFulIndFlag:data.uldFulIndFlag};
    }
    if(data.unplannedContainers && data.unplannedContainers.length > 0 && data.unplannedContainers==='true'){
        filter={...filter, unplannedContainers:data.unplannedContainers};
    }

    if(data.assignedTo && data.assignedTo.length > 0){
        filter={...filter, assignedTo:data.assignedTo};
    }
    

    const summaryFilter = {filter:filter};
    return summaryFilter;

}