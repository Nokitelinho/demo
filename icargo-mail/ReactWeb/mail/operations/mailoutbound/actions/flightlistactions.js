import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { reset } from 'redux-form';
import * as constant from '../constants/constants';
import { change} from 'icoreact/lib/ico/framework/component/common/form';
import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util';
import moment from 'moment';
export const getContainerDetailsbak = (values) => {
    const { args, getState } = values;
    const state = getState();
    const mailAcceptance = state.filterReducer.flightDetails.results[args.index]
    let flightArray = [];
    let selectedFlight = ''
    let hasData = '';
    flightArray = state.containerReducer.flightsArray ? state.containerReducer.flightsArray : "";
    if (!isEmpty(flightArray)) {
        selectedFlight = flightArray.filter((obj) => {
            const anotherObj = { ...obj, ...mailAcceptance };
            return JSON.stringify(obj) === JSON.stringify(anotherObj)
        })

        // selectedFlight = flightArray.filter((value) => {if(value.flightNumber=== mailAcceptance.flightNumber || value.flightSequenceNumber=== mailAcceptance.flightSequenceNumber) return value});
    }

    if (isEmpty(selectedFlight)) {
        hasData = true;
    } else {
        hasData = false;
    }
    return hasData;
}



export const listContainersinFlight = (values) => {
    const { args, dispatch, getState } = values;
    const state = getState();
    const  flightCarrierflag = state.filterReducer.filterValues ? state.filterReducer.filterValues.filterType : state.commonReducer.flightCarrierflag
  let containerDisplayPage='';
   let flightIndex = '';
    let mailAcceptance = {};
    let flightnotAlready=true;
    // let
    if (args) {
       
        if (flightCarrierflag === 'F') {
            if(args.flightIndex!=undefined) {
            flightIndex=args.flightIndex;
            mailAcceptance = state.filterReducer.flightDetails.results[args.flightIndex];
            }
            else {
               // mailAcceptance = state.containerReducer.mailAcceptance;
                flightIndex = state.containerReducer.selectedFlightIndex;
                mailAcceptance = state.filterReducer.flightDetails.results[flightIndex];
            

            }
           
        }
        else if (flightCarrierflag === 'C') {
            if(args.flightIndex!=undefined) {
                flightIndex=args.flightIndex;
                mailAcceptance = state.filterReducer.carrierDetails.results[args.flightIndex];
                }
                else {
                    flightIndex = state.containerReducer.selectedFlightIndex;
                    //mailAcceptance = state.containerReducer.mailAcceptance;
                    mailAcceptance = state.filterReducer.carrierDetails.results[flightIndex];
                }
               
        }
        containerDisplayPage=args.containerDisplayPage;
    }

    else {
        flightIndex = state.containerReducer.selectedFlightIndex;
        if (flightCarrierflag === 'F') {
            mailAcceptance = state.filterReducer.flightDetails.results[flightIndex];
        }
        if (flightCarrierflag === 'C') {
            mailAcceptance = state.filterReducer.carrierDetails.results[flightIndex];
            
        }
        containerDisplayPage=state.containerReducer.containerDisplayPage;
    }
    let containerPageInfo=mailAcceptance && mailAcceptance.containerPageInfo;
    // const flightCarrierFilter = state.filterReducer.filterValues ? state.filterReducer.filterValues:'';
    const flightsArray = state.containerReducer.flightsArray;
   // const flightnotAlready = getContainerDetails(mailAcceptance, flightsArray);
   
    if (flightnotAlready) {
        const data = { mailAcceptance:{...mailAcceptance,containerPageInfo:null}, flightCarrierflag, containerDisplayPage}
        const url = 'rest/mail/operations/outbound/listContainers';
        return makeRequest({
            url,
            data: { ...data }
        }).then(function (response) {
            if(response.results && response.results[0] && response.results[0].containerDisplayPage) {
                containerDisplayPage = response.results[0].containerDisplayPage
            }
            handleResponse(dispatch, response, flightsArray, flightIndex,containerDisplayPage);
            return response
        })
            .catch(error => {
                return error;
            });
    }  else {
        let containersArray='';
        let containerInfo='';
        //  dispatch( { type: 'LIST_DETAILS', data:data});
  
    mailAcceptance.containerPageInfo=containerPageInfo
    dispatch({ type: constant.LIST_CONTAINER, mailAcceptance, flightsArray, mode: 'multi', flightIndex  });
    dispatch( { type: constant.LIST_MAILBAGS, containerInfo,containersArray,mode:'multi' });
    if (!flightnotAlready) {
      return Promise.resolve({})
    }
  }
}



export const listmailbagsinContainers = (values) => {
    const { args, dispatch, getState } = values;
    const state = getState();
    let flightCarrierFilter = {};
    let mailbagsDisplayPage='';
    let mailbagsDSNDisplayPage='';
    let embargoInfo=false
    let containersArray=''
    let containerTransferCheck=false;
    // let mailbags=[];
    flightCarrierFilter.assignTo = state.filterReducer.filterValues.filterType ? state.filterReducer.filterValues.filterType:state.commonReducer.flightCarrierflag
    //flightCarrierFilter.assignTo = state.commonReducer.flightCarrierflag
   // let selectedContainerIndex= state
    let containerInfo = '';
    //to get if more than one container is chosen before adding container
    let selectedContCount = '';
    let isPagination=false;
    
    if (args) {
     if(args.embargoInfo) {
            embargoInfo=true;
     }
      mailbagsDisplayPage = args.mailbagsDisplayPage;
      if(args.displayPage!=null){
        mailbagsDisplayPage = args.displayPage;
      }
      if(args.isPagination){
        isPagination=args.isPagination;
      }
      if(args.containerTransferCheck){
        containerTransferCheck=args.containerTransferCheck;
      }	
     
      mailbagsDSNDisplayPage = args.mailbagsDSNDisplayPage;
      if(args.containerIndex!=null) {
      containerInfo = state.containerReducer.flightContainers?state.containerReducer.flightContainers.results[args.containerIndex]:{};
      }
      else if(isPagination){
        containerInfo = state.containerReducer.selectedContainer;
      } else if(args.applyFilter) {
        containerInfo=state.containerReducer.selectedContainer;
        if(containerInfo.companyCode === null || containerInfo.companyCode === undefined){
            containerInfo = state.containerReducer.flightContainers?state.containerReducer.flightContainers.results[0]:{}
        }
      }
      else {
        containerInfo = state.containerReducer.flightContainers?state.containerReducer.flightContainers.results[0]:{};
      }
    }

    else {
        mailbagsDisplayPage = state.mailbagReducer.mailbagsDisplayPage;
        mailbagsDSNDisplayPage =state.mailbagReducer.mailbagsDSNDisplayPage;
      //  let selectedContainerIndexes= state.containerReducer.selectedContainerIndex;
      //  let selectedContainer=selectedContainerIndexes[selectedContainerIndexes.length-1]
      //  containerInfo = state.containerReducer.flightContainers.results[selectedContainer];
        containerInfo=state.containerReducer.selectedContainer;
        if(containerInfo==null||containerInfo.companyCode === null || containerInfo.companyCode === undefined){
            containerInfo = state.containerReducer.flightContainers?state.containerReducer.flightContainers.results[0]:{}
        }
    }
     containersArray = state.mailbagReducer.containersArray;
     let mailbagfilter  = state.mailbagReducer.tableFilter;
     if(args && args.applyFilter) {
        mailbagfilter  = state.form.mailbagFilter && state.form.mailbagFilter.values ? state.form.mailbagFilter.values :{}
        dispatch({ type: 'APPLY_MAILBAG_FILTER', mailbagfilter });
     }
     const data = { containerInfo:{...containerInfo,mailbagdsnviewpagelist: null, mailbagpagelist: null ,dsnviewpagelist:null}, flightCarrierFilter,mailbagsDisplayPage,mailbagsDSNDisplayPage,mailbagFilter:mailbagfilter,embargoInfo,containerTransferCheck}
     const url = 'rest/mail/operations/outbound/listMailbags';
    //  let totalBags=0;
     if(containerInfo) {
        //  totalBags =containerInfo.totalBags;
     }
    // if( totalBags>0) {
       
        return makeRequest({
            url,
            data: { ...data }
        }).then(function (response) {
            handleResponseofcontainers(dispatch, response, containersArray, containerInfo, selectedContCount,mailbagsDisplayPage,mailbagsDSNDisplayPage);
            return response
        })
            .catch(error => {
                return error;
            });

   // }else {
       
   // dispatch({ type: constant.LIST_MAILBAGS, containerInfo, containersArray, mode: 'multi', mailbags,selectedContCount });
   // return Promise.resolve({})
 //}
}


function handleResponse(dispatch, response, flightsArray, flightIndex,containerDisplayPage) {
    
    if (!isEmpty(response.results)) {
        const { mailAcceptance } = response.results[0];
        flightsArray.push(mailAcceptance);
        dispatch({ type: constant.LIST_CONTAINER, mailAcceptance, flightsArray, mode: 'multi', flightIndex,containerDisplayPage});
        // dispatch({type: 'CHANGE_DETAILPANEL_MODE',mode:'multi'});


    } else {
        if (!isEmpty(response.errors)) {
            dispatch({ type: constant.CLEAR_TABLE });
        }
    }
}

function handleResponseofcontainers(dispatch, response, containersArray, selectedContainer, selectedContCount,mailbagsDisplayPage,mailbagsDSNDisplayPage) {
    
    if (!isEmpty(response.results)) {
        const { containerInfo } = response.results[0];
         const {mailbagpagelist} =containerInfo;
         const mailbags=mailbagpagelist;
       // containersArray.push(containerInfo);
        dispatch({ type: constant.LIST_MAILBAGS, containerInfo, containersArray, mailbags,mode: 'multi', selectedContCount,mailbagsDisplayPage,mailbagsDSNDisplayPage });
        // dispatch({type: 'CHANGE_DETAILPANEL_MODE',mode:'multi'});


    } else {
        if (!isEmpty(response.errors)) {
            dispatch({ type: constant.CLEAR_TABLE });
        }
    }
}


export function performFlightScreenAction(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
     let mailAcceptanceList=[]
     let mailAcceptance=''
     let data={};
    let selectedFlights=state.listFlightReducer.selectedFlights;
    //For multiple container action, index will be not be there
   if(args.index) {
        mailAcceptance=state.filterReducer.flightDetails.results[args.index];
        data={mailAcceptance,displayPage};
    }else 
    {
        for(var i=0; i<selectedFlights.length;i++) {
          mailAcceptanceList.push(state.filterReducer.flightDetails.results[selectedFlights[i]]);
         }
       data={mailAcceptanceList,displayPage};
    
    }
  
    const displayPage = state.commonReducer.displayPage;
  
    let url = '';
    if (args.type === constant.CLOSE_FLIGHT) {
        url = 'rest/mail/operations/outbound/closeFlight';
    }
    if (args.type === constant.REOPEN_FLIGHT) {
        url = 'rest/mail/operations/outbound/reopenFlight';
    }
    if (args.type === constant.AUTO_ATTACH_AWB) {
        url = 'rest/mail/operations/outbound/autoattachAWB';
    }

    if (args.type === 'PRE-ADVICE') {
        url = 'rest/mail/operations/outbound/preAdvice';
    }
    if (args.type === constant.ULD_ANNOUNCE) {
        url = 'rest/mail/operations/outbound/sendULDAnnounce';
    }


    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        handleScreenActionResponses(dispatch, response, args);
        return response
        
    })
        .catch(error => {
            return error;
        });




}


function handleScreenActionResponses(dispatch, response, args) {
    
    if (!isEmpty(response.results)) {
        if (args.type === 'PRE-ADVICE') {
            const {mailAcceptance } = response.results[0];
            dispatch({ type: constant.PREADVICE_POPUP , mailAcceptance});
        
        }
        dispatch({type:constant.CLEAR_SELECTED_FLIGHTS})

    } else {
        
    }
}



export function clickPreadviceOK(values) {
    const {dispatch } = values;
    dispatch({ type: constant.PREADVICE_OK });
}

export function saveIndexforGenerateManifest(values) {
    const { args, dispatch} = values;
    const index=args.index;
    dispatch({ type: constant.SAVE_INDEX_FORPRINT, index});
}

export function onGenManifestPrint (values) {
    const {getState } = values;
    const state = getState();
    const index = state.listFlightReducer.saveIndexForPrint;
	if (state.form.generateManifestForm.values == undefined ||
	state.form.generateManifestForm.values == null) {
		return Promise.reject(new Error("Print Type is mandatory"));
	}
    const printType = state.form.generateManifestForm.values.printTypes;
    const mailAcceptance=state.filterReducer.flightDetails.results[index];
    const  data={mailAcceptance};
    let url = '';
    if (printType == 'Mailbag level') {
        url = 'rest/mail/operations/outbound/MailbagManifestPrint';
    }
    if (printType == 'AWB level') {
        url = 'rest/mail/operations/outbound/AWBManifestPrint';
    }
    if (printType == 'Destn Category level') {
   
        url = 'rest/mail/operations/outbound/DestCategoryManifestPrint';
	}
    if (printType === 'DSN/Mailbag level') {
        url = 'rest/mail/operations/outbound/DSNMailbagManifestPrint';
    }
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {     
        return response
    }).catch(error => {
        return error;
    });

}
export function printCNForFlight(values) {
    const { args, dispatch, getState } = values;
    const index=args.index;
    const state = getState();
    const mailAcceptance=state.filterReducer.flightDetails.results[index];
    const  data={mailAcceptance};
    const url = 'rest/mail/operations/outbound/PrintCNForFlight';
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {     
        return response
    }).catch(error => {
        return error;
    });

}
export function handleGenerateManifestResponse(dispatch, response) {
    if (!isEmpty(response.errors)) {
        dispatch({ type: constant.MANIFEST_POPUP_CLOSE });
    }
}


// Carrier Actions



export const listContainersinCarrier = (values) => {
    const { args, dispatch, getState } = values;
    const state = getState();
    const mailCarrier = state.filterReducer.carrierDetails.results[args[0].index];
    //const flightsArray= state.containerReducer.flightsArray;
    // const flightnotAlready=getContainerDetails(mailFlight,flightsArray);
    // if(flightnotAlready) {
    const data = { mailCarrier }
    const url = 'rest/mail/operations/outbound/listContainers';
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        handleResponseforlistcarriers(dispatch, response);
        return response
    })
        .catch(error => {
            return error;
        });
    //}
    //  dispatch( { type: 'LIST_DETAILS', data:data});
    //dispatch({ type: constant.LIST_CONTAINER, mode: 'multi' });
   

}

function handleResponseforlistcarriers(dispatch, response, flightsArray) {
    
    if (!isEmpty(response.results)) {
        const { mailAcceptance } = response.results[0];
        flightsArray.push(mailAcceptance);
        dispatch({ type: constant.LIST_CONTAINER, mailAcceptance, flightsArray, mode: 'multi' });
        // dispatch({type: 'CHANGE_DETAILPANEL_MODE',mode:'multi'});


    } else {
        if (!isEmpty(response.errors)) {
            dispatch({ type: constant.CLEAR_TABLE });
        }
    }
}

//On clicking flight filter popup
export function applyFlightFilter(values) {
//     const {dispatch, getState } = values;
//     const state = getState();
//   //  const {displayPage,action} = args;
//     const flightTableFilter  = (state.form.flightDetailsFilter.values)?state.form.flightDetailsFilter.values:{}
//     const { flightnumber, ...rest } = flightTableFilter;
//     const filterValues = {...flightnumber,...rest};
//     let filghtFilter={};
//     if(filterValues.carrierCode)filghtFilter.carrierCode=filterValues.carrierCode;
//     if(filterValues.flightNumber)filghtFilter.flightNumber=filterValues.flightNumber;
//     if(filterValues.flightDate)filghtFilter.flightDate=filterValues.flightDate;
//     if(filterValues.upliftAirport)filghtFilter.upliftAirport=filterValues.upliftAirport;
    
//     //flightTableFilter.flightnumber ? flightTableFilter.flightnumber.carrierCode ? flightTableFilter.carrierCode=flightTableFilter.flightnumber.carrierCode:flightTableFilter.carrierCode=null:flightTableFilter.flightnumber=null;
//     //flightTableFilter.flightnumber ? flightTableFilter.flightnumber.flightNumber ? flightTableFilter.flightNumber=flightTableFilter.flightnumber.flightNumber:flightTableFilter.flightNumber=null:flightTableFilter.flightnumber=null;
//     //flightTableFilter.flightnumber ? flightTableFilter.flightnumber.flightDate ? flightTableFilter.flightDate=flightTableFilter.flightnumber.flightDAte:flightTableFilter.flightDate=null:flightTableFilter.flightnumber=null;
//     //flightTableFilter.flightnumber=null;
//     dispatch( { type: constant.LIST_FLIGHT_FILTER,filghtFilter});
//     return Object.keys(flightTableFilter).length;


const { args, dispatch, getState } = values;
const state = getState();
let recordsPerPage = state.commonReducer.defaultPageSize;
if(args && args.recordsPerPage) {
    recordsPerPage = args.recordsPerPage;
}
let displayPage='';

let mode=state.commonReducer.displayMode;
let loggedAirport= state.commonReducer.airportCode
let flightActionsEnabled= 'false';
const assignTo = state.commonReducer.flightCarrierflag
const flightCarrierFilter = (state.form.outboundFilter.values) ? state.form.outboundFilter.values : {}
flightCarrierFilter.assignTo = assignTo;
const flightNumber = (state.form.outboundFilter.values) ? state.form.outboundFilter.values.flightnumber : {};
if(flightCarrierFilter.airportCode ===loggedAirport ){
    flightActionsEnabled='true';
}

if (!isEmpty(flightNumber)) {
        flightCarrierFilter.flightNumber = flightNumber.flightNumber;
        flightCarrierFilter.flightDate = flightNumber.flightDate;
        flightCarrierFilter.carrierCode = flightNumber.carrierCode
    }
if(args) {
             displayPage = args.displayPage;
             //for 1st page listing
            if(args.mode==='display') {
               mode='display';
           }
            flightCarrierFilter.flightDisplayPage = displayPage;
        }
       else
     {
        flightCarrierFilter.flightDisplayPage = state.filterReducer.flightDisplayPage;
        mode = state.commonReducer.displayMode;
        displayPage=state.filterReducer.flightDisplayPage
    }	

    
    const flightTableFilter  = (state.form.flightDetailsFilter.values)?state.form.flightDetailsFilter.values:{}
    const { flightnumber, ...rest } = flightTableFilter;
    const filterValues = {...flightnumber,...rest};
if(filterValues.carrierCode)flightCarrierFilter.carrierCode=filterValues.carrierCode;
if(filterValues.flightNumber)flightCarrierFilter.flightNumber=filterValues.flightNumber;
if(filterValues.flightDate)flightCarrierFilter.flightDate=filterValues.flightDate;
if(filterValues.upliftAirport)flightCarrierFilter.airportCode=filterValues.upliftAirport;
if(filterValues)flightCarrierFilter.flightStatus=filterValues.flightStatus;
if(filterValues)flightCarrierFilter.flightOperationalStatus=filterValues.flightOperationalStatus;
if(filterValues)flightCarrierFilter.destination=filterValues.destination;
//dispatch( { type: constant.LIST_FLIGHT_FILTER,flightCarrierFilter});

flightCarrierFilter.recordsPerPage = recordsPerPage;
const data = { flightCarrierFilter };
const url = 'rest/mail/operations/outbound/list';	
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        handleResponseforFilter(dispatch, response, data,mode,flightTableFilter,flightActionsEnabled);
        return response
    })
        .catch(error => {
            return error;
        });
   
}

export function applyCarrierFilter(values){
const { args, dispatch, getState } = values;
const state = getState();
let recordsPerPage = state.commonReducer.defaultPageSize;
if(args && args.recordsPerPage) {
    recordsPerPage = args.recordsPerPage;
}
let displayPage='';

let mode=state.commonReducer.displayMode;
let loggedAirport= state.commonReducer.airportCode
let flightActionsEnabled= 'false';
const assignTo = state.commonReducer.flightCarrierflag
const flightCarrierFilter = (state.form.outboundFilter.values) ? state.form.outboundFilter.values : {}
flightCarrierFilter.assignTo = assignTo;
// if(flightCarrierFilter.airportCode ===loggedAirport ){
//     flightActionsEnabled='true';
// }

if(args) {
    displayPage = args.displayPage;
     //for 1st page listing
    if(args.mode==='display') {
        mode='display';
    }
    flightCarrierFilter.carrierDisplayPage = displayPage;
} else {
    flightCarrierFilter.carrierDisplayPage = state.filterReducer.carrierDisplayPage;
    mode = state.commonReducer.displayMode;
    
}
flightCarrierFilter.carrierCode=flightCarrierFilter.carrierCode;


const carrierTableFilter  = (state.form.carrierDetailsFilter.values)?state.form.carrierDetailsFilter.values:{}
    const { upliftAirport, carrierCode, destination } = carrierTableFilter;
    const filterValues =  { upliftAirport, carrierCode, destination };

    if(filterValues.upliftAirport) {
        flightCarrierFilter.upliftAirport=filterValues.upliftAirport;
        flightCarrierFilter.airportCode=filterValues.upliftAirport
    };
    if(filterValues.carrierCode) {
        flightCarrierFilter.carrierCode=filterValues.carrierCode
    };
    if(filterValues.destination) {
        flightCarrierFilter.destination=filterValues.destination;
    } else {
        flightCarrierFilter.destination = ''
    }
    
    if(flightCarrierFilter.airportCode ===loggedAirport ){
        flightActionsEnabled='true';
    }

    flightCarrierFilter.recordsPerPage = recordsPerPage;
    const data = { flightCarrierFilter };
    const url = 'rest/mail/operations/outbound/list';

    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        handleResponseforFilter(dispatch, response, data,mode,carrierTableFilter,flightActionsEnabled);
        return response
    })
        .catch(error => {
            return error;
        });
    // const {dispatch, getState } = values;
    // const state = getState();
    // const carrierTableFilter  = (state.form.carrierDetailsFilter.values)?state.form.carrierDetailsFilter.values:{}
    // const { upliftAirport, carrierCode, destination } = carrierTableFilter;
    // const filterValues =  { upliftAirport, carrierCode, destination };
    // let carrierFilter={};
    // if(filterValues.upliftAirport)carrierFilter.upliftAirport=filterValues.upliftAirport;
    // if(filterValues.carrierCode)carrierFilter.carrierCode=filterValues.carrierCode;
    // if(filterValues.destination)carrierFilter.destination=filterValues.destination;
    // dispatch( { type: constant.LIST_CARRIER_FILTER,carrierFilter});
    // return Object.keys(carrierTableFilter).length;
}

//On clearing flight filter popup
export function onClearFlightFilter(values){
    const {dispatch, getState} = values; 
    let state =  getState();
   // dispatch({ type: constant.CLEAR_FLIGHT_FILTER});
    //dispatch(change('flightDetailsFilter', 'upliftAirport', ''));
    dispatch({
        type: "@@redux-form/INITIALIZE", meta: { form: 'flightDetailsFilter', keepDirty: true },
        payload: {...state.listFlightReducer.defaultTableFilter}
    })
    dispatch({ type: "@@redux-form/RESET", meta: { form: 'flightDetailsFilter' } })
  
}

export function onClearCarrierFilter(values){
    const {dispatch} = values;  
    dispatch({ type: constant.CLEAR_CARRIER_FILTER});
    dispatch(change('carrierDetailsFilter','destination',null));
      
    
}
//to sort flight details
export function updateSortVariables(data) {
    const { dispatch } = data;
    dispatch({ type: constant.UPDATE_FLIGHT_SORT_VARIABLE, data: data.args })

}

//to sort carrier details
export function updateCarrierSortVariables(data) {
    const { dispatch } = data;
    dispatch({ type: constant.UPDATE_CARRIER_SORT_VARIABLE, data: data.args })

}

function handleResponseforFilter(dispatch, response, data, mode,tableFilter,flightActionsEnabled) {
    
    if (!isEmpty(response.results)) {
        if(mode === 'display') {
        const { mailflightspage, mailcarrierspage, wareHouses } = response.results[0];
        const filterData = response.results[0].flightCarrierFilter;
        const summaryFilter = makeSummaryFilter(filterData);
        if (data.flightCarrierFilter.assignTo === 'F') {
            dispatch({ type: constant.LIST_DETAILS_FLIGHT, mailflightspage, wareHouses, data, mode,tableFilter,flightActionsEnabled,summaryFilter });
        }
        if (data.flightCarrierFilter.assignTo === 'C') {
            dispatch({ type: constant.LIST_DETAILS_CARRIER, mailcarrierspage,wareHouses, data, mode,tableFilter,flightActionsEnabled });
        }
      }
      else if(mode === 'multi') {
        const { mailflightspage, mailcarrierspage, wareHouses } = response.results[0];
        const filterData = response.results[0].flightCarrierFilter;
        const summaryFilter = makeSummaryFilter(filterData);
        if (data.flightCarrierFilter.assignTo === 'F') {
            dispatch({ type: constant.LIST_DETAILS_FLIGHT, mailflightspage, wareHouses, data, mode,tableFilter,flightActionsEnabled,summaryFilter });
        }
        if (data.flightCarrierFilter.assignTo === 'C') {
            dispatch({ type:  constant.LIST_DETAILS_CARRIER, mailcarrierspage,wareHouses, data, mode,tableFilter,flightActionsEnabled });
        }
      }
      else if(mode === constant.FETCH_CAPACITY) {
           const { flightCapacityDetails } = response.results[0];
            dispatch({ type: constant.LIST_CAPACITY_FLIGHT,flightCapacityDetails,mode});
      }
      //}

    } else {
        if (!isEmpty(response.errors)) {
            
            dispatch({ type: constant.CLEAR_TABLE });
            if(mode !== constant.FETCH_CAPACITY  && data && data.flightCarrierFilter.assignTo === 'F') {
                dispatch({ type: constant.CLEAR_FLIGHT_TABLE_DATA,tableFilter });
        }
        }
    }
}



export function onApplyFlightSort(values){
    const { args, dispatch, getState } = values;
    const state = getState();
    const getFlightSortDetails = () => args;
    const getFlightTableResults = () =>  state.filterReducer.flightDetails && state.filterReducer.flightDetails.results ? state.filterReducer.flightDetails.results : [];


    const getFlightSortedDetails = createSelector([getFlightSortDetails, getFlightTableResults], (sortDetails, flights) => {

        if (sortDetails) {
            const sortBy = sortDetails.sortBy;
            const sortorder = sortDetails.sortByItem;
            flights = flights.sort((record1, record2) => {
                let sortVal = 0;
                let data1;
                let data2;
                data1 = record1[sortBy] && typeof record1[sortBy] === "object"?record1[sortBy].systemValue:record1[sortBy];
                data2 =record2[sortBy] && typeof record2[sortBy] === "object"? record2[sortBy].systemValue:record2[sortBy];
                if(sortBy==='flightDepartureDate'){
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
                }}
                if (sortorder === 'DSC') {
                    sortVal = sortVal * -1;
                } 
                return sortVal;
             
            });
        }
        return [...flights];
      });

    let flightData=getFlightSortedDetails();
    let pagedFlightData= state.filterReducer && state.filterReducer.flightDetails ? 
    state.filterReducer.flightDetails : []; 
    pagedFlightData={...pagedFlightData,results:flightData};
    dispatch({type:'APPLY_FLIGHT_SORT',flightDataAfterSort:pagedFlightData});
    return Promise.resolve({})
    //dispatch(asyncDispatch(listmailbagsinContainers)());
}

export function onApplyCarrierSort(values){
    
    const { args, dispatch, getState } = values;
    const state = getState();
    const getCarrierSortDetails = () => args;
    const getCarrierTableResults = () =>  state.filterReducer.carrierDetails && state.filterReducer.carrierDetails.results ? state.filterReducer.carrierDetails.results : [];


    const getCarrierSortedDetails = createSelector([getCarrierSortDetails, getCarrierTableResults], (sortDetails, carriers) => {

        if (sortDetails) {
            const sortBy = sortDetails.sortBy;
            const sortorder = sortDetails.sortByItem;
            carriers = carriers.sort((record1, record2) => {
                let sortVal = 0;
                let data1;
                let data2;
                data1 = record1[sortBy] && typeof record1[sortBy] === "object"?record1[sortBy].systemValue:record1[sortBy];
                data2 =record2[sortBy] && typeof record2[sortBy] === "object"? record2[sortBy].systemValue:record2[sortBy];
            //     if(sortBy==='flightDate'){
            //       if((moment.utc(data1).diff(moment.utc(data2)))>0){
            //           sortVal=1;
            //       }else if((moment.utc(data1).diff(moment.utc(data2)))<0){
            //           sortVal=-1;
            //       }
            //   }else{
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
            // }
                if (sortorder === 'DSC') {
                    sortVal = sortVal * -1;
                } 
                return sortVal;
             
            });
        }
        return [...carriers];
      });

	  
	  
    let carrierData=getCarrierSortedDetails();
   
    let pagedCarrierData= state.filterReducer && state.filterReducer.carrierDetails ? 
    state.filterReducer.carrierDetails : []; 
    pagedCarrierData={...pagedCarrierData,results:carrierData};
   
    dispatch({type:constant.APPLY_CARRIER_SORT,carrierDataAfterSort:pagedCarrierData});
    // return Promise.resolve({})

}
export function makeSummaryFilter(data){
    let filter={};
    let popOver={};
    let count = 0;
    let popoverCount =0
    if(data.flightNumber && data.flightNumber.length > 0){
        filter={...filter, flightNumber:data.flightNumber};
        count++;
    }
    if(data.flightDate && data.flightDate.length > 0){
        filter={...filter, flightDate:data.flightDate};
        count++;
    }
    if(data.airportCode && data.airportCode.length > 0){
        filter={...filter, airportCode:data.airportCode};
        count++;
    }
    if(data.destination && data.destination.length > 0){
        filter={...filter, destination:data.destination};
        count++;
    }
    if(data.fromDate && data.fromDate.length > 0){
        filter={...filter, fromDate:data.fromDate};
        count++;
    }
    if(data.toDate && data.toDate.length > 0){
        if(count<5){
            filter={...filter, toDate:data.toDate};
            count++;
        }else{
            popOver={...popOver, toDate:data.toDate};
            popoverCount++;
        } 
    }
    if(data.flightOperationalStatus && data.flightOperationalStatus.length > 0){
        if(count<5){
            filter={...filter, flightOperationalStatus:data.flightOperationalStatus};
            count++;
        }else{
            popOver={...popOver, flightOperationalStatus:data.flightOperationalStatus};
            popoverCount++;
        }  
    }
    if(data.operatingReference && data.operatingReference.length > 0){
        if(count<5){
            filter={...filter, operatingReference:data.operatingReference};
            count++;
        }else{
            popOver={...popOver, operatingReference:data.operatingReference};
            popoverCount++;
        }  
    }
    if(data.flightStatus && data.flightStatus.length > 0){
        if(count<5){   
            filter={...filter, flightStatus:data.flightStatus};
             count++;
        }else{
            popOver={...popOver, flightStatus:data.flightStatus};
            popoverCount++;
        }  
    }
    if(data.mailFlightOnly && data.mailFlightOnly ==true){
        if(count<5){   
            filter={...filter, mailFlightOnly:data.mailFlightOnly};
             count++;
        }else{
            popOver={...popOver, mailFlightOnly:data.mailFlightOnly};
            popoverCount++;
        }  
    }
    const summaryFilter = {filter:filter, popOver:popOver, popoverCount:popoverCount};
    return summaryFilter;
}
