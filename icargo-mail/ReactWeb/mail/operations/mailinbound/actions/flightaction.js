import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import {change} from 'icoreact/lib/ico/framework/component/common/form';
import {isEmpty} from 'icoreact/lib/ico/framework/component/util';
import { dispatchAction, asyncDispatch} from 'icoreact/lib/ico/framework/component/common/actions';
// import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { openPopup } from 'icoreact/lib/ico/framework/action/openpopup';
import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util';
import {onContainerRowSelection} from './containeraction'
import {displayError} from './commonaction'
import * as constant from '../constants/constants';
import { listMailInbound } from '../actions/filteraction';
import moment from 'moment';

export function  onRowSelection(values){  
     
    const { args, dispatch, getState } = values; 
    const state = getState();
    let deliveryFlag="N";
    if(args.stopPropgn){
        return Promise.resolve("Error");  
    }
    if(args.deliveryFlag){
        deliveryFlag="Y";
    }
    let containerFilter = {}
    if(args && args.fromTableRowClick) {
        dispatch({ type:constant.CLEAR_CONTAINER_FILTER})
    } else {
         containerFilter = state.containerReducer.filterValues
    }
    let mailinboundDetails=(args&&args.rowData)?args.rowData:state.listFlightreducer.flightDetails;  
    const pageSize=state.listFlightreducer.pageSize?state.listFlightreducer.pageSize:'100';
    mailinboundDetails={...mailinboundDetails,pageNumber:1,deliveryFlag:deliveryFlag,pageSize:pageSize,...containerFilter};
    const data={mailinboundDetails}; 
    const url = 'rest/mail/operations/mailinbound/ListContainers';
    const mode=constant.CON_LIST_ON_FLT_CLICK; 
    return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    response={...response,mailinboundDetails:mailinboundDetails}
                     handleResponse(dispatch, response,mode);
                     if(args&& (args.fromAction!=null || args.fromTableFilter)){
                        const containerDetail=response.results[0].containerDetailsCollectionPage?
                        response.results[0].containerDetailsCollectionPage.results[0]:{};
                        values={...values,args:{rowData:containerDetail}};
                        onContainerRowSelection(values);
                     }
                    return response;
                    })
                .catch(error => {
                    return error;
                });

}

export function listContainers(values){   
    const {args, dispatch, getState } = values;
    const state = getState();
    let mailinboundDetails=state.listFlightreducer?(state.listFlightreducer.flightDetails):{};
    const pageNumber=args&&args.action.displayPage?args.action.displayPage:
        state.containerReducer.displayPage?state.containerReducer.displayPage:1;
    const pageSize=state.listFlightreducer.pageSize?state.listFlightreducer.pageSize:'10';
    let containerFilter = state.containerReducer.filterValues ? state.containerReducer.filterValues: {} ;
    let embargoInfo = state.commonReducer.embargoInfo
    mailinboundDetails={...mailinboundDetails,pageNumber:pageNumber,pageSize:pageSize,...containerFilter};
    const data={mailinboundDetails,embargoInfo};
    const url = 'rest/mail/operations/mailinbound/ListContainers';
    const mode=constant.CON_LIST_ON_PAG_NXT;
    return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    response={...response,mailinboundDetails:mailinboundDetails}
                     handleResponse(dispatch, response,mode);
                    return response;
                    })
                .catch(error => {
                    return error;
                });

}

export function listContainersForOperations(values){

    const { args, getState } = values;
    const state = getState();
    const index=args?args.indexArray:null;
    const mode=args?args.actionName:null; 
    const mailinboundDetails=(state.listFlightreducer)?state.listFlightreducer.flightData.results[index]:{};
    const data={mailinboundDetails};
    const url = 'rest/mail/operations/mailinbound/ListContainers';
    return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    const responseWithData={indexArray:index,actionName:mode,response}
                    return responseWithData;
                    })
                .catch(error => {
                    return error;
                });

}

export function listContainersAlone(values){

    const { dispatch, getState } = values;
    const state = getState();
    let mailinboundDetails=state.listFlightreducer.flightDetails;  
    const pageSize=state.listFlightreducer.pageSize?state.listFlightreducer.pageSize:'10';
    mailinboundDetails={...mailinboundDetails,pageNumber:1,pageSize:pageSize};
    const data={mailinboundDetails}; 
    const url = 'rest/mail/operations/mailinbound/ListContainers';
    const mode=constant.CON_LIST_ALONE;
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        response={...response,mailinboundDetails:mailinboundDetails}
        handleResponse(dispatch, response,mode);
        return response;
        })
    .catch(error => {
        return error;
    });     
}


export function generateManifest(values){

    const {args, getState } = values; 
    const state = getState();
    // const mode=args?args.actionName:null; 
    const indexArray=args?args.indexArray:null;
    const mailinboundDetailsCollection=[];
    const mailinboundDetailsCollectionFromStore=(state.listFlightreducer)?state.listFlightreducer.flightData.results:{};
    indexArray.forEach(function(element) {
        mailinboundDetailsCollection.push(mailinboundDetailsCollectionFromStore[element]);
    }, this);
    const mailinboundDetails=mailinboundDetailsCollection[0];
    const data={mailinboundDetails};
    const url = 'rest/mail/operations/mailinbound/generatemanifest';
    return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    //const responseWithData={indexArray:index,actionName:mode,response}
                    //return responseWithData;
                    return response;
                    })
                .catch(error => {
                    return error;
                });

}


export function openAllMailbagsPopup(values){
    
    const {dispatch,getState} =values;
    const state = getState();
    const filterValues=state.filterReducer.filterVlues
    const fromDate=filterValues.fromDate?filterValues.fromDate:''
    const toDate=filterValues.toDate?filterValues.toDate:''
    const airportCode= filterValues.port?filterValues.port:'';
    const carrierCode=filterValues.flightNumber&&filterValues.flightNumber.carrierCode ? filterValues.flightNumber.carrierCode:'';
    const flightNumber=filterValues.flightNumber&&filterValues.flightNumber.flightNumber ?filterValues.flightNumber.flightNumber:'';
    const flightDate = filterValues.flightNumber&&filterValues.flightNumber.flightDate ? filterValues.flightNumber.flightDate:'';
    var url = "mail.operations.ux.mailbagenquiry.defaultscreenload.do?fromScreen=mail.operations.mailoutbound&isPopup=true&fromDate="+fromDate+'&todate='+toDate+'&airport='+airportCode+
                    '&carrierCode='+carrierCode+'&flightNumber='+flightNumber +'&flightDate='+flightDate;
    var closeButtonIds = ['CMP_Operations_Shipment_UX_AwbEnquiry_close'];
    var optionsArray = {
        url,
        dialogWidth: "1250",
        dialogHeight: "600",
        closeButtonIds: closeButtonIds,
        popupTitle: 'All Mailbags'
        }
    dispatch(dispatchAction(openPopup)(optionsArray));
}

export function openAllContainersPopup(values){
    
    const {dispatch,getState} =values;
    const state = getState();
    const filterValues=state.filterReducer.filterVlues
    const fromDate=filterValues.fromDate?filterValues.fromDate:''
    const toDate=filterValues.toDate?filterValues.toDate:''
    const airportCode= filterValues.port?filterValues.port:'';
    const carrierCode=filterValues.flightNumber&&filterValues.flightNumber.carrierCode ? filterValues.flightNumber.carrierCode:'';
    const flightNumber=filterValues.flightNumber&&filterValues.flightNumber.flightNumber ?filterValues.flightNumber.flightNumber:'';
    const flightDate = filterValues.flightNumber&&filterValues.flightNumber.flightDate ? filterValues.flightNumber.flightDate:'';
     var url = "mail.operations.ux.containerenquiry.defaultscreenload.do?fromScreen=mail.operations.mailoutbound&isPopup=true&fromDate="+fromDate+
                            '&todate='+toDate+'&airport='+airportCode+'&carrierCode='+carrierCode+'&flightNumber='+flightNumber +'&flightDate='+flightDate;
    var closeButtonIds = ['CMP_Operations_Shipment_UX_AwbEnquiry_close'];
    var optionsArray = {
        url,
        dialogWidth: "1250",
        dialogHeight: "600",
        closeButtonIds: closeButtonIds,
        popupTitle: 'All Containers'
        }
    dispatch(dispatchAction(openPopup)(optionsArray));
}

export function onApplyFlightSort(values){
    const { args, dispatch, getState } = values;
    const state = getState();
    const getFlightSortDetails = () => args;
    const getFlightDetails = () => state.listFlightreducer && state.listFlightreducer.flightData ? 
          state.listFlightreducer.flightData.results : [];
    const getFlightSortedDetails = createSelector([getFlightSortDetails, getFlightDetails], (sortDetails, flights) => {
        if (sortDetails) {
        const sortBy = sortDetails.sortBy;
        const sortorder = sortDetails.sortByItem;
            flights.sort((record1, record2) => {
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
                }else{
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
        return flights;
    });

    let fliData=getFlightSortedDetails();
    let pagedFlightData=state.listFlightreducer && state.listFlightreducer.flightData ? 
          state.listFlightreducer.flightData : []; 
    pagedFlightData={...pagedFlightData,results:fliData};
    dispatch({type: constant.APPLY_FLIGHT_SORT,flightDataAfterSort:pagedFlightData});
    if(state.commonReducer.displayMode==='multi'){
        values={...values,args:{rowData:fliData[0],fromAction:'sort'}};
        onRowSelection(values);
        return fliData;
    }
}

export function  onApplyFlightFilter(values){  
    
    const { dispatch, getState } = values;
    const state = getState();
    /*
    const getTableResults = () =>
     state.listFlightreducer && state.listFlightreducer.flightData ? 
          state.listFlightreducer.flightData.results : [];

    const getTableFilter = () => 
       state.form.flightDetailsFilter&& state.form.flightDetailsFilter.values ? 
          state.form.flightDetailsFilter.values : state.listFlightreducer.filterValues?state.listFlightreducer.filterValues:{};
    let fliData=[];
    
    const formValues= state.form.flightDetailsFilter&& state.form.flightDetailsFilter.values ? 
          state.form.flightDetailsFilter.values : state.listFlightreducer.filterValues?state.listFlightreducer.filterValues:{};
    if(isEmpty(formValues)){
        return;
    }
    */
   /*
    const getDetails = createSelector([getTableResults, getTableFilter], (results, filterValues) => {
         if (!isEmpty(filterValues)) {
            //  let flightDetails = filterValues.flightnumber;
             const {flightnumber,...rest}=filterValues;
             filterValues={...flightnumber,...rest}
            // filterValues = { ...flightDetails, upliftairport: filterValues.upliftairport };

             return results.filter((obj) => {
                 let uplAirport = obj.flightRoute.split('-')[0];
                 let flDate = obj.flightDate.split(' ')[0];
               //  if(filterValues.upliftairport==null){
               //     filterValues.upliftairport=uplAirport;
               // }
                 const currentObj = {
                     carrierCode: obj.carrierCode, flightNumber: obj.flightNo,
                     flightDate: flDate, prevPort: uplAirport
                 };
                 const anotherObj = { ...currentObj, ...filterValues };
                 if (JSON.stringify(currentObj) === JSON.stringify(anotherObj))
                     return true;
                 else
                     return false
             })

         } else {
             return results;
         }

     });
     */
    /*
    fliData=getDetails();
    let pagedFlightData=state.listFlightreducer && state.listFlightreducer.flightData ? 
          state.listFlightreducer.flightData : []; 
    pagedFlightData={...pagedFlightData,results:fliData};

    dispatch({type: constant.APPLY_FLIGHT_FILTER,filterValues:formValues,flightDataAfterFilter:pagedFlightData});
    if(state.commonReducer.displayMode==='multi'){
        values={...values,args:{rowData:fliData[0],fromAction:'filter'}};
        onRowSelection(values);
        return fliData;

    }
    */
   let action_type = state.commonReducer.displayMode ==='multi' ? constant.LIST_MULTI : constant.LIST;
   dispatch(asyncDispatch(listMailInbound)({ 'action': { type: action_type, displayPage: '1',pageSize:'100' }, fromFilter:true }))
            .then(response=> {
                if(state.commonReducer.displayMode==='multi'){
                    if(isEmpty(response.errors) && response.results && !isEmpty(response.results[0])) { 
                      if(!isEmpty(response.results[0].mailinboundDetailsCollectionPage)) {
                        let flightData = response.results[0].mailinboundDetailsCollectionPage.results[0];
                        dispatch(dispatchAction(onRowSelection)({rowData:flightData,fromAction:'filter'}));
                      }
                    }
                }
    });

}

export function onClearFlightFilter (values){  
    const { dispatch, getState } = values;
    const state = getState();
    //  const formValues=  state.form.flightDetailsFilter&& state.form.flightDetailsFilter.values ? 
        //   state.form.flightDetailsFilter.values : state.listFlightreducer.filterValues?state.listFlightreducer.filterValues:{}; 
     /*
        const flightData=state.listFlightreducer && state.listFlightreducer.flightData ? 
                state.listFlightreducer.flightData.results : [];
     dispatch(change(constant.FLIGHT_DETAILS_FILTER,'flightnumber',''))
     dispatch(change(constant.FLIGHT_DETAILS_FILTER,'upliftairport',''))
     dispatch({type: constant.CLEAR_FLIGHT_FILTER});
     if(state.commonReducer.displayMode==='multi'){
         values={...values,args:{rowData:flightData[0],fromAction:'filter'}};
        onRowSelection(values);
     } */
    //listMailInbound(values);
    dispatch({
        type: "@@redux-form/INITIALIZE", meta: { form: 'flightDetailsFilter', keepDirty: true },
        payload: {
            pol:"",
            mailstatus:"",
            flightnumber: {
                carrierCode:"",
                flightNumber:"",
                flightDate:""
            }
        }
    })
    dispatch({ type: "@@redux-form/RESET", meta: { form: 'flightDetailsFilter' } })
}

export function listcontainersinFlightOnList(values){   
    const {args, dispatch } = values;
    // const state = getState();
    const mailinboundDetails=(args.results[0].mailinboundDetailsCollectionPage)?
                                    args.results[0].mailinboundDetailsCollectionPage.results[0]:{};
    const data={mailinboundDetails};
    const url = 'rest/mail/operations/mailinbound/ListContainers';
    const mode=constant.CON_LIST_ON_FLT_LIST;
    return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                     handleResponse(dispatch, response,mode);
                    return response;
                    })
                .catch(error => {
                    return error;
                });

}



    

export function flightAllSelect(values){
    const { args, dispatch, getState } = values;
    const state=getState();
    const checked=args.checked;
    let indexArray=[];
    let i=0;
    let mailInboundDetailsCollectionPage=state.listFlightreducer?(state.listFlightreducer.flightData):{};
    let mailInboundDetailsCollection=mailInboundDetailsCollectionPage.results?mailInboundDetailsCollectionPage.results:{};
    mailInboundDetailsCollection.forEach(function(element) {
        element.checkBoxSelect=checked;
        indexArray.push(i);
        i++;
    }, this);
    const indexDetails={checked:checked,indexArray:indexArray};
    mailInboundDetailsCollectionPage={...mailInboundDetailsCollectionPage,results:mailInboundDetailsCollection};
    dispatch({type: constant.SELECT_FLIGHT_ALL, flightData:mailInboundDetailsCollectionPage,indexDetails:indexDetails})

}

export function performFlightScreenAction(values){
    const {args, dispatch, getState } = values; 
    const state = getState();
    const mode=args?args.actionName:null; 
    const indexArray=args?args.indexArray:null;
    const mailinboundDetailsCollection=[];
    const mailinboundDetailsCollectionFromStore=(state.listFlightreducer.filterFlag)?(state.listFlightreducer && state.listFlightreducer.flightDataAfterFilter ? 
          state.listFlightreducer.flightDataAfterFilter.results : []):(state.listFlightreducer && state.listFlightreducer.flightData ? 
          state.listFlightreducer.flightData.results : [])
    indexArray.forEach(function(element) {
       /* if(mode===constant.CLOSE_FLIGHT){
            if(mailinboundDetailsCollectionFromStore[element].flightOperationStatus!='Open'){
                displayError({...values,message:'Selected flight is already closed'});
                return;  
            }
        }
        if(mode===constant.OPEN_FLIGHT){
            if(mailinboundDetailsCollectionFromStore[element].flightOperationStatus!='Closed'){
                displayError({...values,message:'Selected flight is already open'});
                return;  
            }
        }*/
        mailinboundDetailsCollection.push(mailinboundDetailsCollectionFromStore[element]);
    }, this);
    const mailinboundDetails=mailinboundDetailsCollection[0]?mailinboundDetailsCollection[0]:{};
    
    const data={mailinboundDetailsCollection,mailinboundDetails};
    if(mode===constant.CLOSE_FLIGHT){
        const url = 'rest/mail/operations/mailinbound/closeFlight';
        const data={mailinboundDetailsCollection:mailinboundDetailsCollection};
        return makeRequest({
                    url,
                    data: data
                }).then(function (response) {
                    return response;
                    //onRowSelection({rowData:response.results[0].mailinboundDetails,state,dispatch});
                    })
                .catch(error => {
                    return error;
                });

    }else if(mode===constant.OPEN_FLIGHT){
        const url = 'rest/mail/operations/mailinbound/reopenFlight';
        const data={mailinboundDetailsCollection:mailinboundDetailsCollection};
        return makeRequest({
                    url,
                    data: data
                }).then(function (response) {
                    return response;
                    //onRowSelection({rowData:response.results[0].mailinboundDetails,state,dispatch});
                    })
                .catch(error => {
                    return error;
                });

    }else if(mode===constant.AUTO_ATTACH_AWB){
         const url = 'rest/mail/operations/mailinbound/autoAttachAwb';

         return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    if(!isEmpty(response.errors)){
                        dispatch( {type:constant.ERROR_SHOW});
                        return response;
                    }
                    else 
                    onRowSelection({rowData:response.results[0].mailinboundDetails,state,dispatch});
                    })
                .catch(error => {
                    return error;
                });

    }else if(mode===constant.DISCREPANCY){
        const url = 'rest/mail/operations/mailinbound/discrepancy';
        const mode=constant.DISCREPANCY
        return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    handleResponse(dispatch,response,mode);
                    return response;
                    })
                .catch(error => {
                    return error;
                }); 
    }     

    dispatch( { type: args.type});
}
export function performRowSelection(values){
   
    const {dispatch } = values;    
    dispatch( { type: constant.ON_FLIGHT_ROW_CLICK});
}

// export function errorDisplayFunction(label){
//     dispatch(requestValidationError(label,''));
// }

function handleResponse(dispatch, response, action) {  
    if(response.errors==null){
    if (!isEmpty(response)) {
        if (action === constant.CLOSE_FLIGHT) { 
            dispatch({type:constant.CLOSE_FLIGHT,mode:'display'})
        }
        if (action === constant.OPEN_FLIGHT) { 
            dispatch({type: constant.OPEN_FLIGHT,mode:'display'})
        }
        if (action === constant.AUTO_ATTACH_AWB) { 
            dispatch({type:constant.AUTO_ATTACH_AWB,mode:'multi',containerDetails:response.results[0].containerDetailsCollection })
        }
        if (action === constant.CON_LIST_ON_FLT_LIST) { 
            dispatch({type:constant.CHANGE_DETAILPANEL_MODE,mode:'multi',containerDetails:response.results[0].containerDetailsCollection,
            mailbagData:response.results[0].containerDetailsCollection[0].mailBagDetailsCollection,flightDetails:response.results[0].mailinboundDetails,
            flightOperationDetails:response.results[0].operationalFlightVO,containerDetailsInVo:response.results[0].containerDetailsVos,
            arrivalDetailsInVo:response.results[0].mailArrivalVO,containerSelected:response.results[0].containerDetailsCollection[0],
            dsnData:response.results[0].containerDetailsCollection[0].dsnDetailsCollection,date:response.results[0].currentDate,time:response.results[0].currentTime});
        }
            if (action === constant.DISCREPANCY) { 
             dispatch({type: constant.LOAD_DISCREPANCY,mode:'multi',discrepancyData:response.results[0].discrepancyDetailsCollection});
        }
            if (action===constant.CON_LIST_ON_FLT_CLICK){
                dispatch({type: constant.LIST_CONTAINERS,mode:'multi',containerDetails:response.results[0].containerDetailsCollectionPage?
                        response.results[0].containerDetailsCollectionPage:{},containerDetail:response.results[0].containerDetailsCollectionPage?
                        response.results[0].containerDetailsCollectionPage.results[0]:{},flightDetails:response.mailinboundDetails,
                        date:response.results[0].currentDate,time:response.results[0].currentTime,
                        containerList:response.results[0].containerList?response.results[0].containerList:[]});
            }
            if (action === constant.CON_LIST_ON_PAG_NXT) { 
           dispatch({type: constant.LIST_CONTAINERS,mode:'multi',containerDetails:response.results[0].containerDetailsCollectionPage?
                        response.results[0].containerDetailsCollectionPage:{},containerDetail:response.results[0].containerDetailsCollectionPage?
                        response.results[0].containerDetailsCollectionPage.results[0]:{},flightDetails:response.mailinboundDetails,
                        date:response.results[0].currentDate,time:response.results[0].currentTime});
        }
        if(action=== constant.CON_LIST_ALONE){
            dispatch({type: constant.CON_LIST_ALONE,containerDetails:response.results[0].containerDetailsCollectionPage?
                response.results[0].containerDetailsCollectionPage:{}})
        }

        
    }
    }
}
export function printCN46(values){
    const {args, getState } = values; 
    const state = getState();
    // const mode=args?args.actionName:null; 
    const indexArray=args?args.indexArray:null;
    const mailinboundDetailsCollection=[];
    const mailinboundDetailsCollectionFromStore=(state.listFlightreducer)?state.listFlightreducer.flightData.results:{};
    indexArray.forEach(function(element) {
        mailinboundDetailsCollection.push(mailinboundDetailsCollectionFromStore[element]);
    }, this);
    const mailinboundDetails=mailinboundDetailsCollection[0];
    const data={mailinboundDetails};
    const url = 'rest/mail/operations/mailinbound/printCN46ForFlightInbound';
    return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    //const responseWithData={indexArray:index,actionName:mode,response}
                    //return responseWithData;
                    return response;
                    })
                .catch(error => {
                    return error;
                });
}