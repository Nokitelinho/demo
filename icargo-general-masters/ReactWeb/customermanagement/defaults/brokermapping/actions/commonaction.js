import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { requestValidationError } from "icoreact/lib/ico/framework/component/common/store/commonactions";
import {asyncDispatch,dispatchAction} from "icoreact/lib/ico/framework/component/common/actions";
import { isEmpty } from "icoreact/lib/ico/framework/component/util";
import { CommonUtil} from 'icoreact/lib/ico/framework/config/app/commonutil';
import {requestInfoWithAutoClose} from 'icoreact/lib/ico/framework/component/common/store/commonactions.js';
import { change } from "icoreact/lib/ico/framework/component/common/form";
import {
  OPEN_CUSTOMERPOPUP,
  CLOSE_CUSTOMERPOPUP,
  UPDATE_CUSTOMER_SEARCH_DETAILS,
  TOOGLE_SCREEN_MODE,
  UPDATE_BROKER_MAPPING_DETAILS,
  TOGGLE_POP_UP_CLOSE,
  ADD_NEW_BROKER_POA,
  UPDATE_CONSIGNEE_DETAILS,
  UPDATE_BROKER_DETAILS,
  APPLY_BROKER_FILTERS,
  UPDATE_SORT_VARIABLE,
  CLEAR_SORT_VARIABLE,
  APPLY_CONSIGNEE_FILTERS,
  UPDATE_CONSIGNEE_SORT_VARIABLE,
  CLEAR_CONSIGNEE_SORT_VARIABLE,
  CLEAR_CONSIGNEE_FILTER
  } from "../constants/constants";
  /**
   * Added as a part of IASCB-177106
   * @param {*} values 
   */
  export function updateCustomerListform(values){
    const { dispatch, getState } = values;
    const state = getState();
    if(state.form.customerListform){
      let{address,cityCode,zipCode,stationCode,countryCode,customerCode,customerName}=state.form.customerListform.values;
      if(typeof address ==='string' && !isEmpty(address)){
        dispatch(change('customerListform','address',address.trim().toUpperCase()));
      }
      if(typeof cityCode ==='string' && !isEmpty(cityCode)){
        dispatch(change('customerListform','cityCode',cityCode.trim().toUpperCase()));
      }
      if(typeof zipCode ==='string' && !isEmpty(zipCode)){
        dispatch(change('customerListform','zipCode',zipCode.trim().toUpperCase()));
      }
      if(typeof stationCode ==='string' && !isEmpty(stationCode)){
        dispatch(change('customerListform','stationCode',stationCode.trim().toUpperCase()));
      }
      if(typeof countryCode ==='string' && !isEmpty(countryCode)){
        dispatch(change('customerListform','countryCode',countryCode.trim().toUpperCase()));
      }
      if(typeof customerCode ==='string' && !isEmpty(customerCode)){
        dispatch(change('customerListform','customerCode',customerCode.trim().toUpperCase()));
      }
      if(typeof customerName ==='string' && !isEmpty(customerName)){
        dispatch(change('customerListform','customerName',customerName.trim().toUpperCase()));
      }
    }
    dispatch(dispatchAction(validateCustomerForm)())
 
  }
  export function validateCustomerForm(values) {
    const { dispatch, getState } = values;
    let isValid = false;
    let error = null;
    const state = getState();
    const formAttributes = state.form.customerListform
    ? state.form.customerListform.values
    : null;
    const cityCode=formAttributes.cityCode ? formAttributes.cityCode:"";
    const zipCode=formAttributes.zipCode ? formAttributes.zipCode:"";
    const stationCode=formAttributes.stationCode ? formAttributes.stationCode:"";
    const countryCode=formAttributes.countryCode ? formAttributes.countryCode:"";

  if(!(isEmpty(cityCode))||!(isEmpty(zipCode))||!(isEmpty(stationCode))
          ||!(isEmpty(countryCode))){
             isValid=true; 
  }
    if (!isValid) {
      dispatch(
        requestValidationError(
          "Please enter either of city, country ,station or zip code fields",
          ""
        )
      );
    } else {
      dispatch(asyncDispatch(getCustomerListDetails)());
    }
  }
  
  export function fetchDetailsOnScreenload(args) {
    const { dispatch, getState } = args;
    const state = getState();
    const url = "rest/customermanagement/defaults/brokermapping/screenLoadBrokerMapping";
    return makeRequest({
      url,
      data: { }
    })
      .then(function(response) {
        dispatch({
            type: 'SCREENLOAD_SUCCESS',
            oneTimeValues: !isEmpty(response.results[0].oneTimeValues)
	         ? response.results[0].oneTimeValues
	        : [],
            data: response.results[0].brokerMappingFilter
          });
        return response;
      })
      .catch(error => {
        return error;
      });
  }

  export function getCustomerListDetails(values) {
    const { dispatch, getState } = values;
    const state = getState();
    const url = "rest/customermanagement/defaults/brokermapping/searchCustomer";
    const data = populateCustomerListRequest(state);
    return makeRequest({
     url,
      data
  })
  .then(function(response) {
    dispatch({
      type: UPDATE_CUSTOMER_SEARCH_DETAILS,
      data: response.results[0].customerListDetails
    });                         
    return response;
  })
    .catch(error => {
      return error;
    });
  }

  export function populateCustomerListRequest(state)
  {
    let customerListFilter= state.form.customerListform ? state.form.customerListform.values : null;
    return{customerListFilter:{...customerListFilter,stationCode:isEmpty(customerListFilter.stationCode)?null:customerListFilter.stationCode}};
  }

  export function clearFilter(values) {
    const { dispatch,getState } = values;
    const state = getState();
    dispatch(change('customerListform','customerCode', ''));
    dispatch(change('customerListform','customerType', ''));
    dispatch(change('customerListform','customerName', ''));
    dispatch(change('customerListform','cityCode', ''));
    dispatch(change('customerListform','countryCode', ''));
    dispatch(change('customerListform','stationCode', state.commonReducer.customerLovDetails.station));
    dispatch({ type: CLEAR_FILTER });
    dispatch({ type: "LIST_ERROR" });
    
  }

  export function showCustomerLovPopup(values) {
    const { dispatch,getState } = values;
    const state = getState();
    dispatch({ type: "OPEN_CUSTOMERPOPUP" })
  }

  export function closeCustomerPopup(values){
    const { dispatch } = values;
    dispatch({ type: CLOSE_CUSTOMERPOPUP});
  }

export function validateFilter(values){
    const{dispatch,getState}=values;
    const state=getState();
    const formAttributes=state.form.brokermappingform? state.form.brokermappingform.values: null;
    let validationPassed= false;
    if(formAttributes!= null && !isEmpty(formAttributes.customerCode)){
      validationPassed= true;
    }
    if(validationPassed)
    {
      dispatch(asyncDispatch(ListBrokerMappingDetails)());
    }
    else{
      dispatch({
        type:TOOGLE_SCREEN_MODE,
        data:{
          screenMode: "invokePopUp"
        }
      })
      dispatch({
        type:"SHOW_CUSTOMER_POPUP",
        data:{
          showCreateCustomerPopUp:true,
          invalidCus:""
        }
      })
    }
}

export function ListBrokerMappingDetails(values){
    const { dispatch, getState } = values;
    const state = getState();
    const url = "rest/customermanagement/defaults/brokermapping/list";
    const data = populateListRequest(state);
    return makeRequest({
      url,
      data
    }).then(function(response) {
      if( response.results[0].warningFlag)
      {
      dispatch({
          type:TOOGLE_SCREEN_MODE,
          data:{
            screenMode: "invokePopUp"
          }
        })
        dispatch({
          type:"SHOW_CUSTOMER_POPUP",
          data:{
            showCreateCustomerPopUp:true,
            invalidCus:"No customer exists,"
          }
        })
      }
      else
      {
        dispatch({
        type: UPDATE_BROKER_MAPPING_DETAILS,
        data:{
          brokerDetails: response.results[0].brokerDetails,
          customerDetails: response.results[0].customerDetails,
          consigneeDetails: response.results[0].consigneeDetails,
          intialBrokerDetails: response.results[0].brokerDetails,
          initialConsigneeDetails: response.results[0].consigneeDetails,
          awbDetails:response.results[0].awbDetails,
          initialAwbDetails:response.results[0].awbDetails,
          showActionButtons:false
          // copyCustomerDetails:response.results[0].customerDetails,
        }
      })
      dispatch(change('brokermappingform','customerCode',response.results[0].brokerMappingFilter.customerCode))
      dispatch({
        type: TOOGLE_SCREEN_MODE,
        data:{
          screenMode: "list",
          filterDetails: {...populateFilterDetails(state)}
        }
      })
      dispatch({type: CLEAR_SORT_VARIABLE});
      dispatch({type: CLEAR_CONSIGNEE_SORT_VARIABLE});
      }
      return response;
  })
  .catch(error => {
    return error;
  });
  
}

export function populateListRequest(state)
{
    let brokerMappingFilter= state.form.brokermappingform ? state.form.brokermappingform.values : null;
    brokerMappingFilter={...brokerMappingFilter,customerCode:brokerMappingFilter.customerCode.trim().toUpperCase()}
    return{ brokerMappingFilter};
}

export function toggleScreenMode(values) {
  const { dispatch, getState } = values;
  const state = getState();
  const screenMode =
    state.commonReducer.screenMode === "edit" ? "list" : "edit";
  dispatch({
    type:TOOGLE_SCREEN_MODE,
    data:{screenMode,
    filterDetails: populateFilterDetails(state)
  }
  });
}

function populateFilterDetails(state) {
  const filterDetails = state.form.brokermappingform.values ? 
      state.form.brokermappingform.values : {};
  return filterDetails;
}
/**
 * Added as a part of IASCB-177106
 * Method to convert form values to uppercase.
 * Using text-transform:uppercase property to view input text as uppercase.
 * @param {*} values 
 */
 export function updateAddDetailsForm(values){
  const{dispatch,getState}=values;
  const state=getState();
  const formAttributes=state.form.adddetailsform ?state.form.adddetailsform.values: "";
  if(!isEmpty(formAttributes)){
    let {brokerCode,destination,sccCodeE,sccCodeI,orgin,brokerName,station}=formAttributes;
    if(typeof destination ==='string' && !isEmpty(destination)){
      dispatch(change('adddetailsform','destination',destination.toUpperCase()));
    }
    if(typeof brokerCode ==='string' && !isEmpty(brokerCode)){
      dispatch(change('adddetailsform','brokerCode',brokerCode.toUpperCase()));
    }
    if(typeof brokerName ==='string' && !isEmpty(brokerName)){
      dispatch(change('adddetailsform','brokerName',brokerName.toUpperCase()));
    }
    if(typeof sccCodeI ==='string' && !isEmpty(sccCodeI)){
      dispatch(change('adddetailsform','sccCodeI',sccCodeI.toUpperCase()));
    }
    if(typeof sccCodeE ==='string' && !isEmpty(sccCodeE)){
      dispatch(change('adddetailsform','sccCodeE',sccCodeE.toUpperCase()));
    }
    if(typeof orgin ==='string' && !isEmpty(orgin)){
      dispatch(change('adddetailsform','orgin',orgin.toUpperCase()));
    }
    if(typeof station ==='string' && !isEmpty(station)){
      dispatch(change('adddetailsform','station',station.toUpperCase()));
    }
  }
  dispatch(dispatchAction(validateBrokerPoa)())
}
export function validateBrokerPoa(values){
  const{dispatch,getState}=values;
  const state=getState();
  let validationPassed=true,errors="";
  const formAttributes=state.form.adddetailsform ?state.form.adddetailsform.values: " ";
  if(!isEmpty(formAttributes)){
    if (
      isEmpty(formAttributes.brokerCode) ||
      isEmpty(formAttributes.destination)
    ) {
      validationPassed = false;
      errors = "Please enter mandatory fields for POA creation";
    } else if (isEmpty(formAttributes.document)) {
      validationPassed = false;
      errors = "Document upload mandatory for POA creation";
    } else if(formAttributes.sccCodeI && formAttributes.sccCodeE){
      const sccE=formAttributes.sccCodeE;
      let sccI=formAttributes.sccCodeI.split(",");
      if(sccI.some(scci=>sccE.split(",").some(sccE=>scci.includes(sccE)))){
        validationPassed=false;
        errors="Same SCC Codes cannot be used in the Include and Exclude Section";
      }
    }
  }else{
    validationPassed=false;
    errors="Please enter mandatory fields for POA creation";
  }
  if(validationPassed){
      dispatch(asyncDispatch(validateSccOrgDest)())
  }else{
    dispatch(requestValidationError(errors, ""));
  }
}
export function validateSccOrgDest(values){
      const{dispatch,getState}=values;
      const state=getState();
      const url = "rest/customermanagement/defaults/brokermapping/validatePOA";
      const data = populateBrokerValidationDetails(state);
      return makeRequest({
        url,
        data
    })
    .then(function(response) {
        if(response.results!=null){
          let error=response.results[0].error_Code
          dispatch(requestValidationError(error,""))
        }else if(response.status==="validate_broker_sucess"){
          dispatch(dispatchAction(ValidateAddBrokerPoa)())
        }          
      return response;
    })
      .catch(error => {
        return error;
      });
}
function populateBrokerValidationDetails(state){
  const brokerDetails=state.form.adddetailsform? state.form.adddetailsform.values:"";
  if(!isEmpty(brokerDetails))
  {
    const agentCode= "";
    const agentName="";
    const station="";
    let sccCodeInclude=brokerDetails.sccCodeI?brokerDetails.sccCodeI.split(","):[];
    let sccCodeExclude=brokerDetails.sccCodeE?brokerDetails.sccCodeE.split(","):[];
    let orginExclude=brokerDetails.orgin?brokerDetails.orginRadio ==="E"?brokerDetails.orgin.split(","):[]:[];
    let orginInclude=brokerDetails.orgin?brokerDetails.orginRadio ==="I"?brokerDetails.orgin.split(","):[]:[];
    let destination=brokerDetails.destination?brokerDetails.destination.split(","):[];
    let poaFlag=false
    const validateConsigneeDetails={agentCode,agentName,station,sccCodeInclude,sccCodeExclude,orginExclude,orginInclude,destination,poaFlag}
    return {validateConsigneeDetails}
  }
}
export function ValidateAddBrokerPoa(values){
  const{dispatch,getState}=values;
  const state=getState();

  let validationPassed=true,errors=" ",generalPoa=false,specialPoa=false,destination=null,customerCode=null,sccInclude=null,sccExclude=null,orginExclude="",orginInclude="";
  const formAttributes=state.form.adddetailsform ?state.form.adddetailsform.values: " ";
    generalPoa=(isEmpty(formAttributes.sccCodeI)&&isEmpty(formAttributes.sccCodeE)&&isEmpty(formAttributes.orgin))?true:false
    specialPoa=(!isEmpty(formAttributes.sccCodeI)||!isEmpty(formAttributes.sccCodeE)||!isEmpty(formAttributes.orgin))?true:false
    sccInclude=formAttributes.sccCodeI ? formAttributes.sccCodeI:""
    sccExclude=formAttributes.sccCodeE ? formAttributes.sccCodeE:""
    orginExclude=formAttributes.orgin? formAttributes.orginRadio ==="E"?formAttributes.orgin:"":""
    orginInclude=formAttributes.orgin? formAttributes.orginRadio ==="I"?formAttributes.orgin:"":""
    if(validationPassed)
    {
      
      destination=formAttributes.destination
      customerCode=formAttributes.brokerCode
      if(generalPoa){
        let newArry=state.commonReducer.brokerDetails.filter(function(e)
        {
          if(e.destination.includes(destination)){
            if(e.agentCode==customerCode){
              validationPassed=false;
              errors="POA already available for the consignee with the listed broker";
            }else{
              validationPassed=false;
              errors="POA already available for the consignee with the different broker";
            }
          }
        })
      }
      if(specialPoa)
      {
        let newArry=state.commonReducer.brokerDetails.some(function(e){
          // let sccError=false;
          if(e.destination.includes(destination)){
            if(isEmpty(e.sccCodeInclude) && isEmpty(e.sccCodeExclude) && isEmpty(e.orginInclude) && isEmpty(e.orginExclude)){
              if(e.agentCode==customerCode){
                  validationPassed=false;
                  errors="General POA already exists for the broker. Special POA cannot be created";
              }else{
                  validationPassed=false;
                  errors="General POA already exists with other broker. Special POA cannot be created";
              }
            }else{
              
              let sccError=validateSCC(sccInclude,sccExclude,e);
              let originError=validateOrigin(orginInclude,orginExclude,e);
              
              if(!originError || !sccError){
                validationPassed=true;
              }else{
                validationPassed=false;
              }
              if(!validationPassed){
                if(e.agentCode==customerCode){
                  errors="Special POA exists for the broker. Special POA cannot be created";
                }else{
                  errors="Special POA already exists with other broker for same parameters";
                }
              }
            }
          }
          if(!validationPassed){
            return true;
          }else{
            return false;
          }
        })

      }
    }
  
  if(validationPassed)
  {
      let sccIncludeArry=sccInclude.split(",");
    let sccExcArray=sccExclude.split(",");
    let orginI=orginInclude.split(",");
    let orginE=orginExclude.split(",")
    //creating object for  Broker Poa
    let poaDetails={
      poaType:generalPoa?"General POA":specialPoa?"Special POA":" ",
      agentCode:formAttributes.brokerCode,
      agentName:formAttributes.brokerName,
      orginExclude:[],
      orginInclude:[],
      sccCodeExclude:[],
      sccCodeInclude:[],
      destination:[],
      operatonalFlag:"I",
      index:(state.commonReducer.brokerDetails.length-1)+1,
      station:formAttributes.station
    }
      if(!isEmpty(orginExclude))
      {
        for(let i=0;i<orginE.length;i++)
        {
          poaDetails.orginExclude.push(orginE[i]);
        }
      }
      if(!isEmpty(orginInclude))
      {
        for(let i=0;i<orginI.length;i++)
        {
          poaDetails.orginInclude.push(orginI[i]);
        }
      }
      if(!isEmpty(sccInclude))
      {
        for(let i=0;i<sccIncludeArry.length;i++)
        {
          poaDetails.sccCodeInclude.push(sccIncludeArry[i]);
        }
      }
      if(!isEmpty(sccExclude))
      {
        for(let i=0;i<sccExcArray.length;i++)
        {
          poaDetails.sccCodeExclude.push(sccExcArray[i]);
        }
      }
      poaDetails.destination.push(formAttributes.destination)
     
    dispatch({ type: TOGGLE_POP_UP_CLOSE });
	dispatch({ type: "CLEAR_BROKER/CONSIGNEE_FORM_VALUES" });
    dispatch({ type: "__POPUP_OPEN_STATE", popupMode: false });
    dispatch({
      type: ADD_NEW_BROKER_POA,
      data: {
        intialBrokerDetails: poaDetails,
        brokerDetails: poaDetails,
      },
    });
  } else{
    dispatch(requestValidationError(errors, ""));
  }
}
/**
 * 
 * @param {*} sccInclude 
 * @param {*} sccExclude 
 * @param {*} e 
 * @returns boolean
 */
export function validateSCC(sccInclude,sccExclude,e){
  let sccError=false
  if(isEmpty(sccInclude) && isEmpty(e.sccCodeInclude)){
    sccError=true;
  }else if(isEmpty(sccInclude) && !isEmpty(e.sccCodeInclude)){
    sccError=validateCombination(sccExclude,e.sccCodeInclude)
  }else if(!isEmpty(sccInclude) && isEmpty(e.sccCodeInclude)){
    sccError=validateCombination(e.sccCodeExclude.join(","),sccInclude.split(","));
  }else if(!isEmpty(sccInclude) && !isEmpty(e.sccCodeInclude)){
    if(e.sccCodeInclude.some(scc=>
         sccInclude.split(",").includes(scc))){
      sccError=true;
    }
  }
  return sccError;
}
/**
 * 
 * @param {*} orginInclude 
 * @param {*} originExclude 
 * @param {*} e 
 * @returns boolean
 */
export function validateOrigin(orginInclude,originExclude,e){
  let originError=false;
  if(isEmpty(orginInclude) && isEmpty(e.orginInclude)){
    originError=true;
  }else if(isEmpty(orginInclude) && !isEmpty(e.orginInclude)){
    originError=validateCombination(originExclude,e.orginInclude);
  }else if(!isEmpty(orginInclude) && isEmpty(e.orginInclude)){
    originError=validateCombination(e.orginExclude.join(","),orginInclude.split(","));
  }else if(!isEmpty(orginInclude) && !isEmpty(e.orginInclude)){
    if(e.orginInclude.some(scc=>orginInclude.split(",").includes(scc))){
        originError=true;
    }
  }
  return originError;
}
/**
 * 
 * @param {String} inputA 
 * @param {Array} inputB 
 * @returns Boolean
 */
export function validateCombination(inputA,inputB){
  let validationPassed=false;
  if(!isEmpty(inputA) && inputB.length>0){
    for(let i=0;i<inputB.length;i++){
      if(! inputA.includes(inputB[i])){
        validationPassed=true;
        break;
      }
    }
  }else{
    validationPassed=true;
  }
  return validationPassed
}

export function validateSave (values)
{
  const {dispatch,getState} =values;
  const state=getState();
  let validationPassed=true,errors="";
  const formAttributes=state.form.customerDetailsForm ?state.form.customerDetailsForm.values: "";
  if(!isEmpty(formAttributes))
  {
                                        //checking for empty spaces
    if(!formAttributes.customerName || (/^\s*$/.test(formAttributes.customerName)))
    {
      validationPassed=false;
      errors=
      "Please enter mandatory fields for Customer Details";
    }
    else if(!formAttributes.city || (/^\s*$/.test(formAttributes.city)))
    {
      validationPassed=false;
      errors=
      "Please enter mandatory fields for Customer Details";
    }
    else if(!formAttributes.country || (/^\s*$/.test(formAttributes.country)))
    {
      validationPassed=false;
      errors=
      "Please enter mandatory fields for Customer Details";
    }
    else if(!formAttributes.zipCode || (/^\s*$/.test(formAttributes.zipCode)))
    {
      validationPassed=false;
      errors=
      "Please enter mandatory fields for Customer Details";
    }
    else if(!formAttributes.station || (/^\s*$/.test(formAttributes.station)))
    {
      validationPassed=false;
      errors=
      "Please enter mandatory fields for Customer Details";
    }
  }
  else{
    validationPassed=false;
      errors=
      "Please enter mandatory fields for Customer Details";
  }
  if(validationPassed)
  {
    dispatch(asyncDispatch(SaveBrokerMappingDetails)());
  }
  else{
    dispatch(requestValidationError(errors, ""));
  }
}
export function SaveBrokerMappingDetails(values)
  {
    const { dispatch, getState } = values;
    const state = getState();
    const url = "rest/customermanagement/defaults/brokermapping/save";
    const data = populateSaveRequest(state);
    return makeRequest({
      url,
      data
    }).then(function(response) {
      sendSaveMessageResponse(response,dispatch)
      return response;
    })
    .catch(error => {
      return error;
    });
  }
  function sendSaveMessageResponse(response,dispatch)
  {
    if((!isEmpty(response) && isEmpty(response.errors) )|| !response.errors)
      {
        if(response.status==="list_success")
        {
          if(response.results!==null && response.results.length>0 && response.results[0]!=null)
          {
            dispatch(change('brokermappingform','customerCode',response.results[0]));
            dispatch({
              type:TOOGLE_SCREEN_MODE,
              data:{
                screenMode: "invokeSavePopUp"
              }
            })
            dispatch({
              type:"SHOW_RELIST_POPUP",
              data:{showRelist:true}
            })
          }
          else
          {
            let msgkey= "Details Saved successfully!"
               CommonUtil.store.dispatch(requestInfoWithAutoClose({ "msgkey": msgkey, "defaultMessage": msgkey,
                            "data": [] }));
              dispatch(change('brokermappingform','customerCode',''));
              dispatch({
                type:"CLEAR",
                data: "initial"
              })
          }
        }
       }
  }
  function populateSaveRequest(state)
  {
    let brokerDetails=state.commonReducer.intialBrokerDetails.filter(data=>{
      if(data.operatonalFlag=="I" || data.operatonalFlag=="D")
      {
        return true;
      }
      return false;
    });
    let consigneeDetails=state.commonReducer.initialConsigneeDetails.filter(data=>{
      if(data.operatonalFlag=="I" || data.operatonalFlag=="D")
      {
        return true;
      }
      return false;
    });
    let additionalNames=state.commonReducer.additionalNames;
    // let customerDetails=state.form.customerDetailsForm?{...state.form.customerDetailsForm.values,additionalNames:additionalNames}:"";
    const cusFormValues=state.form.customerDetailsForm?state.form.customerDetailsForm.values:"";
    let string;
    let customerDetails={
      additionalDetails:cusFormValues.additionalDetails? cusFormValues.additionalDetails.trim() :null,
      additionalNames:additionalNames,
      city:cusFormValues.city?removeExtraSpace(string=cusFormValues.city):null,
      country:cusFormValues.country?removeExtraSpace(string=cusFormValues.country):null,
      customerName:cusFormValues.customerName?removeExtraSpace(string=cusFormValues.customerName):null,
      station:cusFormValues.station?removeExtraSpace(string=cusFormValues.station):null,
      street:cusFormValues.street?removeExtraSpace(string=cusFormValues.street):null,
      zipCode:cusFormValues.zipCode
    }
    let brokerMappingFilter=state.form.brokermappingform?state.form.brokermappingform.values:null;
    let awbDetails=state.commonReducer.initialAwbDetails.filter(data=>{
      if(data.operatonalFlag == "I" || data.operatonalFlag=="D")
      {
        return true;
      }
      else{
        return false;
      }
    });
    return{brokerMappingFilter,brokerDetails,consigneeDetails,customerDetails,awbDetails}
  }
  export function UpdateAdditionalDetails(values)
  {
    const { dispatch, getState,args } = values;
    const state = getState();
    const formAttributes=state.form.customerDetailsForm?state.form.customerDetailsForm.values:""
    let custState=state.commonReducer.customerDetails
    if ( typeof(args.newRemark) !== "undefined" && args.newRemark !== null ) {
      custState.additionalDetails=args.newRemark;
    }
    else{
      custState.additionalDetails=formAttributes.additionalDetails;
    }
    
    //when upadting the additionalDetails need to update the customerDetails with formAttribute Values if any.
    custState.customerName=formAttributes.customerName;
    custState.street=formAttributes.street;
    custState.city=formAttributes.city;
    custState.country=formAttributes.country;
    custState.zipCode=formAttributes.zipCode;
    custState.station=formAttributes.station;
  }
  /**
   * Method to update remarks for deleted poa
   * method expects args, identifier- to identify Broker or consignee ,newRemarks-Remarks for deleted POA
   * @param {*} values 
   */
  export function updateRemarksForDeletedPOA(values) {
    const { dispatch, getState, args } = values;
    const state = getState();
    if (args.ident === "B") {
      state.commonReducer.selectedIndex.map((record) => {
        if (record.operatonalFlag == "I") {
          record.operatonalFlag = "I_D";
        } else if (record.operatonalFlag == "N") {
          record.operatonalFlag = "D";
          record.deletionRemarks = args.newRemark.trim();
        }
      });
      dispatch(dispatchAction(deleteBrokerPOA)());
      dispatch({type:"__POPUP_OPEN_STATE",popupMode: false})
    } else if (args.ident === "C") {
      state.commonReducer.selectedConsigneeIndex.map((record) => {
        if (record.operatonalFlag == "I") {
          record.operatonalFlag = "I_D";
        } else if (record.operatonalFlag == "N") {
          record.operatonalFlag = "D";
          record.deletionRemarks = args.newRemark.trim();
        }
      });
      dispatch(dispatchAction(deleteConsigneePOA)());
      dispatch({type:"__POPUP_OPEN_STATE",popupMode: false})
    }else if (args.ident === "S") {
      state.commonReducer.selectedSinglePoaIndex.map((record) => {
        if (record.operatonalFlag == "I") {
          record.operatonalFlag = "I_D";
        } else if (record.operatonalFlag == "N") {
          record.operatonalFlag = "D";
          record.deletionRemarks = args.newRemark.trim();
        }
      });
      dispatch(dispatchAction(deleteSinglePOA)());
      dispatch({type:"__POPUP_OPEN_STATE",popupMode: false})
    }
  }
  export function deleteBrokerPOA(values)
  {
    const{dispatch,getState}=values;
    const state=getState();
    let updatedBrokerDetails=state.commonReducer.brokerDetails.filter(details => details.operatonalFlag != "D").filter(details => details.operatonalFlag != "I_D");
    dispatch({
      type: UPDATE_BROKER_DETAILS,
      data:{
        updatedBrokerDetails: updatedBrokerDetails,
      }
    })
  }
  export function deleteConsigneePOA(values)
  {
    const {dispatch,getState}=values;
    const state = getState();
    let updateConsigneeDetails=state.commonReducer.consigneeDetails.filter(details => details.operatonalFlag != "D").filter(details => details.operatonalFlag != "I_D");
    dispatch({
      type: UPDATE_CONSIGNEE_DETAILS,
      data:{
        updateConsigneeDetails: updateConsigneeDetails
      }
    })
  }

  export function deleteSinglePOA(values)
  {
    const {dispatch,getState}=values;
    const state = getState();
    let updateSinglePoaDetails=state.commonReducer.awbDetails.filter(details => details.operatonalFlag != "D").filter(details => details.operatonalFlag != "I_D");
    dispatch({
      type: "UPDATE_SINGLE_POA_DETAILS",
      data:{
        updateSinglePoaDetails: updateSinglePoaDetails
      }
    })
  }

  export function deleteAddNames(values)
  {
    const {dispatch,getState}=values;
    const state = getState();
    let updateAdditionalDetails=state.commonReducer.additionalNames.filter(details => details.operationalFlag != "D").filter(details => details.operationalFlag != "I_D");
    manageCustomerDetailState(state);
    dispatch({
      type: "UPDATE_ADDNAME_DETAILS",
      data:{
        updateAdditionalDetails: updateAdditionalDetails
      }
    })
  }
  export function addAddNames(values){
    const {dispatch,getState}=values;
    const state=getState();
    const formAttributes=state.form.addnamesform?state.form.addnamesform.values:"";
    let maxNames=state.commonReducer.additionalNames.length;
    if(!isEmpty(formAttributes) && formAttributes.additionalName!=undefined && !(/^\s*$/.test(formAttributes.additionalName))){
    if(maxNames<30){
      let additionalNames={
        adlNam:removeExtraSpace(formAttributes.additionalName),
        index:state.commonReducer.additionalNames.length>0?state.commonReducer.additionalNames.length+1:0,
        operationalFlag:"I"
      }
      dispatch({type:"ADD_ADDITIONAL_NAMES",
          data:additionalNames})
        //updating Customer Details State, if any value present in customerDetailsForm
        manageCustomerDetailState(state);
    }else{
      let error="More than 30 additional names cannot be added"
      dispatch(requestValidationError(error, ""));
    }
    }else{
        let error="Please enter additional name"
        dispatch(requestValidationError(error, ""));
    }
  }
  /**
   * Function to update Customer Details State with values in form
   * @param {*} state 
   */
  function manageCustomerDetailState(state){
    let cusState=state.commonReducer.customerDetails
      const custFormAttributes=state.form.customerDetailsForm?state.form.customerDetailsForm.values:"";
      custFormAttributes.additionalDetails!=undefined?cusState.additionalDetails=custFormAttributes.additionalDetails:"";
      custFormAttributes.city!=undefined?cusState.city=custFormAttributes.city:"";
      custFormAttributes.country!=undefined?cusState.country=custFormAttributes.country:"";
      custFormAttributes.customerName!=undefined?cusState.customerName=custFormAttributes.customerName:"";
      custFormAttributes.station!=undefined?cusState.station=custFormAttributes.station:"";
      custFormAttributes.street!=undefined?cusState.street=custFormAttributes.street:"";
      custFormAttributes.zipCode!=undefined?cusState.zipCode=custFormAttributes.zipCode:"";
  }
  export function applyBrokerDetailsFilter(values) {
    const { dispatch, getState } = values;
    const state = getState();
    if (state.form.brokerDetailsFilterForm.values) {
      let {poaType,brokerCode,brokerName,sccCodeI,sccCodeE,orginInclude,orginExclude,destination,station}=state.form.brokerDetailsFilterForm.values
      dispatch({
        type: APPLY_BROKER_FILTERS,
        //data: state.form.brokerDetailsFilterForm.values removeExtraSpace
        data:{...state.form.brokerDetailsFilterForm.values,
          poaType:removeExtraSpace(poaType),
          brokerCode:removeExtraSpace(brokerCode),
          brokerName:removeExtraSpace(brokerName),
          sccCodeI:removeExtraSpace(sccCodeI),
          sccCodeE:removeExtraSpace(sccCodeE),
          orginInclude:removeExtraSpace(orginInclude),
          orginExclude:removeExtraSpace(orginExclude),
          destination:removeExtraSpace(destination),
          station:removeExtraSpace(station)
        }
      });
    }
  }
  export function validateDeleteCustomer(values){
    const{dispatch,getState}=values;
    const state=getState();
    const formAttributes=state.form.brokermappingform? state.form.brokermappingform.values: null;
    let validationPassed= false;
    if(formAttributes!= null && !isEmpty(formAttributes.customerCode))
    {
      validationPassed= true;
    }
    if(validationPassed)
    {
      dispatch(asyncDispatch(DeleteCustomerDetails)());
      dispatch(change('brokermappingform','customerCode',''));
    }
    else{
      let error="No customer details available"
      dispatch(requestValidationError(error, ""));
      
    }
  }
  export function DeleteCustomerDetails(values){
    const { dispatch, getState } = values;
    const state = getState();
    const url = "rest/customermanagement/defaults/brokermapping/delete";
    const data = populateListRequest(state);
    return makeRequest({
      url,
      data
    }).then(function(response) {
      if(response.status==="DELETE_SUCCESS"){
        CommonUtil.store.dispatch(requestInfoWithAutoClose({ "msgkey": "Customer Deleted successfully!", "defaultMessage": "Customer Deleted successfully!",
                    "data": [] }));
      dispatch({
        type:"CLEAR",
        data: "initial"
      })
      return response;
    }
  })
  .catch(error => {
    return error;
  });
  }
  /**
 * Added as a part of IASCB-177106
 * Method to convert form values to uppercase.
 * Using text-transform:uppercase property to view input text as uppercase.
 * @param {*} values 
 */
 export function updateAddConsigneeDetailsForm(values){
  const{dispatch,getState}=values;
  const state=getState();
  const formAttributes=state.form.addconsigneedetailsform? state.form.addconsigneedetailsform.values:"";
  if(!isEmpty(formAttributes)){
    let {consigneeCode,destination,sccCodeE,sccCodeI,orgin,consigneeName,station}=formAttributes;
    if(typeof destination ==='string' && !isEmpty(destination)){
      dispatch(change('addconsigneedetailsform','destination',destination.toUpperCase()));
    }
    if(typeof consigneeCode ==='string' && !isEmpty(consigneeCode)){
      dispatch(change('addconsigneedetailsform','consigneeCode',consigneeCode.toUpperCase()));
    }
    if(typeof consigneeName ==='string' && !isEmpty(consigneeName)){
      dispatch(change('addconsigneedetailsform','consigneeName',consigneeName.toUpperCase()));
    }
    if(typeof sccCodeI ==='string' && !isEmpty(sccCodeI)){
      dispatch(change('addconsigneedetailsform','sccCodeI',sccCodeI.toUpperCase()));
    }
    if(typeof sccCodeE ==='string' && !isEmpty(sccCodeE)){
      dispatch(change('addconsigneedetailsform','sccCodeE',sccCodeE.toUpperCase()));
    }
    if(typeof orgin ==='string' && !isEmpty(orgin)){
      dispatch(change('addconsigneedetailsform','orgin',orgin.toUpperCase()));
    }
    if(typeof station ==='string' && !isEmpty(station)){
      dispatch(change('addconsigneedetailsform','station',station.toUpperCase()));
    }
  }
  dispatch(dispatchAction(ValidateAddConsigneePoa)())
}
  
  export function ValidateAddConsigneePoa(values){
    const{dispatch,getState}=values;
    const state=getState();
    const formAttributes=state.form.addconsigneedetailsform? state.form.addconsigneedetailsform.values:"";
    const consigneeCode=formAttributes.consigneeCode
    const destination=formAttributes.destination
    let sccInclude=null,sccExclude=null,orginExclude="",orginInclude="",validationPassed=true,error="";
    sccInclude=formAttributes.sccCodeI ? formAttributes.sccCodeI:""
    sccExclude=formAttributes.sccCodeE ? formAttributes.sccCodeE:""
    orginExclude=formAttributes.orgin? formAttributes.orginRadio ==="E"?formAttributes.orgin:"":""
    orginInclude=formAttributes.orgin? formAttributes.orginRadio ==="I"?formAttributes.orgin:"":""
    let generalPoa=(isEmpty(formAttributes.sccCodeI)&&isEmpty(formAttributes.sccCodeE)&&isEmpty(formAttributes.orgin))?true:false
    let specialPoa=(!isEmpty(formAttributes.sccCodeI)||!isEmpty(formAttributes.sccCodeE)||!isEmpty(formAttributes.orgin))?true:false
    let uploadedFile = formAttributes.document;
	if(!isEmpty(formAttributes)) {
      if (
      isEmpty(formAttributes.consigneeCode) ||
      isEmpty(formAttributes.destination)
    ) {
      validationPassed = false;
      error = "Please enter mandatory fields for POA creation";
    } else if (isEmpty(formAttributes.document)) {
      validationPassed = false;
      error = "Document upload mandatory for POA creation";
    } else if(formAttributes.sccCodeI && formAttributes.sccCodeE){
        const sccE=formAttributes.sccCodeE;
        let sccI=formAttributes.sccCodeI.split(",");
        if(sccI.some(scci=>sccE.split(",").some(sccE=>scci.includes(sccE))))
        {
          validationPassed=false;
          error="Same SCC Codes cannot be used in the Include and Exclude Section";
        }
      }
      if(validationPassed)
      {
        if(generalPoa)
        {
        let newArry=state.commonReducer.consigneeDetails.filter(function(e)
          {
            if(e.destination.includes(destination))
            {
              if(e.agentCode==consigneeCode){
                validationPassed=false;
                error="POA already available for the consignee with the listed broker";
              }
            }
          })
        }
        //speacial poa 
        if(specialPoa)
        {
          let newArry=state.commonReducer.consigneeDetails.some(function(e)
          {
            if(e.destination.includes(destination) && (e.agentCode==consigneeCode)){
              if(isEmpty(e.sccCodeInclude) && isEmpty(e.sccCodeExclude) && isEmpty(e.orginInclude) && isEmpty(e.orginExclude)){
                    validationPassed=false;
                    error="General POA already exists for the broker. Special POA cannot be created";
                }
              else{

                let sccError=validateSCC(sccInclude,sccExclude,e);
                let originError=validateOrigin(orginInclude,orginExclude,e);
                
                if(!originError || !sccError){
                  validationPassed=true;
                }else{
                    validationPassed=false;
                }
                if(!validationPassed){
                    error="Special POA exists for the broker. Special POA cannot be created";
                  }
               }
            }
            if(!validationPassed){
              return true;
            }else{
              return false;
            }
          })
        }
      }
    }
    else{
      validationPassed=false;
      error="Please enter mandatory fields for POA creation";
    }
    if(validationPassed)
      {
        dispatch(asyncDispatch(validateConsigneeDetails)());
      }
      else{
        dispatch(requestValidationError(error,""))
      }
  }
  export function validateConsigneeDetails(values)
  {
    const{dispatch,getState}=values;
    const state=getState();
    const url = "rest/customermanagement/defaults/brokermapping/validatePOA";
    const data = populateConsigneeData(state);
    return makeRequest({
      url,
      data
    }).then(function(response) {
      if(response.status==="VALIDATE_SUCCESS")
      {
        dispatch(dispatchAction(addConsigneePOA)())
      }
      else if(response.results!=null){
        let error=response.results[0].error_code
        dispatch(requestValidationError(error,""))
      }
      return response;
  })
  .catch(error => {
    return error;
  });
  }
  function populateConsigneeData(state)
  {
    const consigneeDetails=state.form.addconsigneedetailsform? state.form.addconsigneedetailsform.values:"";
    if(!isEmpty(consigneeDetails))
    {
      const agentCode= consigneeDetails.consigneeCode?consigneeDetails.consigneeCode:"";
      const agentName=consigneeDetails.consigneeName?consigneeDetails.consigneeName:"";
      const station=consigneeDetails.station?consigneeDetails.station:"";
      let sccCodeInclude=consigneeDetails.sccCodeI?consigneeDetails.sccCodeI.split(","):[];
      let sccCodeExclude=consigneeDetails.sccCodeE?consigneeDetails.sccCodeE.split(","):[];
      let orginExclude=consigneeDetails.orgin?consigneeDetails.orginRadio ==="E"?consigneeDetails.orgin.split(","):[]:[];
      let orginInclude=consigneeDetails.orgin?consigneeDetails.orginRadio ==="I"?consigneeDetails.orgin.split(","):[]:[];
      let destination=consigneeDetails.destination?consigneeDetails.destination.split(","):[];
      let poaFlag=true
      const validateConsigneeDetails={agentCode,agentName,station,sccCodeInclude,sccCodeExclude,orginExclude,orginInclude,destination,poaFlag}
      return {validateConsigneeDetails}
    }
  }
  export function sortedList(data) {
    const { dispatch } = data;
    dispatch({ type: UPDATE_SORT_VARIABLE, data: data.args })
  }
  export function onClearFilter(values) {
    const { dispatch, getState } = values;
    const state = getState();
    dispatch({type: "CLEAR_BROKER_FILTER"});
    dispatch({type: "@@redux-form/INITIALIZE",meta: { form: "brokerDetailsFilterForm", keepDirty: true },payload: ""});
    dispatch({type: "@@redux-form/RESET",meta: { form: "brokerDetailsFilterForm" }});
  }
  export function applyConsigneeDetailsFilter(values) {
    const { dispatch, getState } = values;
    const state = getState();
    if (state.form.consigneeDetailsFilterForm.values) {
    let {poaType,consigneeCode,consigneeName,sccCodeI,sccCodeE,orginInclude,orginExclude,destination,station}=state.form.consigneeDetailsFilterForm.values
    dispatch({
        type: APPLY_CONSIGNEE_FILTERS,
        //data: state.form.consigneeDetailsFilterForm.values
        data:{...state.form.consigneeDetailsFilterForm.values,
          poaType:removeExtraSpace(poaType),
          consigneeCode:removeExtraSpace(consigneeCode),
          consigneeName:removeExtraSpace(consigneeName),
          sccCodeI:removeExtraSpace(sccCodeI),
          sccCodeE:removeExtraSpace(sccCodeE),
          orginInclude:removeExtraSpace(orginInclude),
          orginExclude:removeExtraSpace(orginExclude),
          destination:removeExtraSpace(destination),
          station:removeExtraSpace(station)
        }
    });
    }
  }
  export function sortedConsigneeList(data) {
    const { dispatch } = data;
    dispatch({ type: UPDATE_CONSIGNEE_SORT_VARIABLE, data: data.args })
  }
  export function onClearConsigneeFilter(values) {
    const { dispatch, getState } = values;
    const state = getState();
    dispatch({type: CLEAR_CONSIGNEE_FILTER});
    dispatch({type: "@@redux-form/INITIALIZE",meta: { form: "consigneeDetailsFilterForm", keepDirty: true },payload: ""});
    dispatch({ type: "@@redux-form/RESET",meta: { form: "consigneeDetailsFilterForm" }});
  }
  export function addConsigneePOA(values)
  {
    const{dispatch,getState}=values;
    const state=getState();
    const formAttributes=state.form.addconsigneedetailsform? state.form.addconsigneedetailsform.values:"";
    let generalPoa=(isEmpty(formAttributes.sccCodeI)&&isEmpty(formAttributes.sccCodeE)&&isEmpty(formAttributes.orgin))?true:false
    let specialPoa=(!isEmpty(formAttributes.sccCodeI)||!isEmpty(formAttributes.sccCodeE)||!isEmpty(formAttributes.orgin))?true:false
    let sccInclude=formAttributes.sccCodeI ? formAttributes.sccCodeI:""
    let sccExclude=formAttributes.sccCodeE ? formAttributes.sccCodeE:""
    let orginExclude=formAttributes.orgin? formAttributes.orginRadio ==="E"?formAttributes.orgin:"":""
    let orginInclude=formAttributes.orgin? formAttributes.orginRadio ==="I"?formAttributes.orgin:"":""
    let sccIncludeArry=sccInclude.split(",");
    let sccExcArray=sccExclude.split(",");
    let orginI=orginInclude.split(",");
    let orginE=orginExclude.split(",")
    //creating object for  Consignee Poa
    let consigneePOA={
      poaType:generalPoa?"General POA":specialPoa?"Special POA":" ",
      agentCode:formAttributes.consigneeCode,
      agentName:formAttributes.consigneeName,
      orginExclude:[],
      orginInclude:[],
      sccCodeExclude:[],
      sccCodeInclude:[],
      destination:[],
      operatonalFlag:"I",
      index:(state.commonReducer.consigneeDetails.length-1)+1,
      station:formAttributes.station
    }
      if(!isEmpty(orginExclude))
      {
        for(let i=0;i<orginE.length;i++)
        {
          consigneePOA.orginExclude.push(orginE[i]);
        }
      }
      if(!isEmpty(orginInclude))
      {
        for(let i=0;i<orginI.length;i++)
        {
          consigneePOA.orginInclude.push(orginI[i]);
        }
      }
      if(!isEmpty(sccInclude))
      {
        for(let i=0;i<sccIncludeArry.length;i++)
        {
          consigneePOA.sccCodeInclude.push(sccIncludeArry[i]);
        }
      }
      if(!isEmpty(sccExclude))
      {
        for(let i=0;i<sccExcArray.length;i++)
        {
          consigneePOA.sccCodeExclude.push(sccExcArray[i]);
        }
      }
      consigneePOA.destination.push(formAttributes.destination)
	
	dispatch({ type: TOGGLE_POP_UP_CLOSE });
	dispatch({ type: "CLEAR_BROKER/CONSIGNEE_FORM_VALUES" });
	dispatch({ type: "__POPUP_OPEN_STATE", popupMode: false });
	dispatch({
		type: "ADD_NEW_CONSIGNEE_POA",
		data: {
		  initialConsigneeDetails: consigneePOA,
		  consigneeDetails: consigneePOA,
		},
	});
  }
  export function validateAwb(values)
  {
    const {dispatch,getState} =values;
    const state=getState();
    let validationPassed=true,errors="";
    const formAttributes=state.form.singlePoaform.values? state.form.singlePoaform.values.awbnum:"";
    const awbFormValue=formAttributes.shipmentPrefix +" "+formAttributes.masterDocumentNumber;
    let awbDetails=state.commonReducer.awbDetails;
    if(!isEmpty(formAttributes))
    {
      if(!formAttributes.shipmentPrefix || !formAttributes.masterDocumentNumber)
      {
        validationPassed=false;
        errors="Please provide AWB details"
      }
      else if(awbDetails.some(awb=>awb.awbNumber.includes(awbFormValue)))
      {
        validationPassed=false;
        errors="Single POA already exists with the selected broker"
      }
    }
    else{
      validationPassed=false;
        errors="Please provide AWB details"
    }
    if(validationPassed)
    {
      dispatch(asyncDispatch(ValidateAwbDetails)())
    }
    else{
      dispatch(requestValidationError(errors,""))
    }
  }

  export function ValidateAwbDetails(values)
  {
    const{dispatch,getState}=values;
    const state=getState();
    const url = "rest/customermanagement/defaults/brokermapping/validateAwbDetails";
    const data = populateAwbDetails(state);
    return makeRequest({
      url,
      data
    }).then(function(response) {
      if(response.status==="VALIDATE_AWB_SUCCESS")
      {
        dispatch(dispatchAction(addSinglePOA)())
      }
      else{
        let error=response.results[0].error_Code
        dispatch(requestValidationError(error,""))
      }
      return response;
  })
  .catch(error => {
    return error;
  });
  }
  
  function populateAwbDetails(state)
  {
    const formAttributes=state.form.singlePoaform? state.form.singlePoaform.values:"";
    
    const shipmentPrefix=formAttributes.awbnum?formAttributes.awbnum.shipmentPrefix:"";
    const masterDocumentNumber=formAttributes.awbnum?formAttributes.awbnum.masterDocumentNumber:"";
    const awbFilter={shipmentPrefix,masterDocumentNumber}
    return{awbFilter}
    
  }

  export function addSinglePOA(values)
  {
    const {dispatch,getState} =values;
    const state=getState();
    const formAttributes=state.form.singlePoaform? state.form.singlePoaform.values.awbnum:"";
    
    let singlePOA={
      operatonalFlag: "I",
      poaType:"Single POA",
      sccCodeInclude:[],
      sccCodeExclude:[],
	    orginExclude:[],
	    orginInclude:[],
	    destination:[],
      station:state.commonReducer.station,
      awbNumber: formAttributes.shipmentPrefix +" "+formAttributes.masterDocumentNumber,
      index: state.commonReducer.awbDetails.length==0?0:state.commonReducer.awbDetails.length+1

    }
    dispatch({
      type:"ADD_SINGLE_POA",
      data:{
        newSinglePOA:singlePOA
      }
    })
    dispatch(change('singlePoaform','awbnum', ''));
  }
  export function validateCustomerCode(values){
    const {dispatch,args,getState}=values;
    const state=getState();
    const url = "rest/customermanagement/defaults/brokermapping/validateCustomerCode";
    const data={customerCodeValidation:{customerCode:args.customerCode.trim().toUpperCase()}}
    return makeRequest({
      url,
      data
    }).then(function(response) {
      if(response.status==="CUSTOMER_CODE_VALID")
      {
        if(args.flag==="B"){
          const customerCodeValidation=response.results[0].customerCodeValidation
          let brokerDetails={brokerCode:customerCodeValidation?customerCodeValidation.customerCode:"",
                                brokerName:customerCodeValidation?customerCodeValidation.customerName:"",
                                consigneeCode:'',consigneeName:''}
          dispatch(change('adddetailsform','brokerCode',brokerDetails.brokerCode))
          dispatch({type:"DISPLAY_SELECTED_CUSTOMERDETAILS",data:brokerDetails})
        } else if(args.flag==="C"){
          const customerCodeValidation=response.results[0].customerCodeValidation
          let consigneeDetails={brokerCode:"",brokerName:"",
                                consigneeCode:customerCodeValidation?customerCodeValidation.customerCode:"",
                                consigneeName:customerCodeValidation?customerCodeValidation.customerName:""}
          dispatch(change('addconsigneedetailsform','consigneeCode',consigneeDetails.consigneeCode))
          dispatch({type:"DISPLAY_SELECTED_CUSTOMERDETAILS",data:consigneeDetails})
        }
      }
      else{
        let error=response.results[0].error_Code
        dispatch(requestValidationError(error,""))
        dispatch(change('adddetailsform','brokerCode',''))
        dispatch(change('addconsigneedetailsform','consigneeCode',''))
        dispatch({ type: "_ON_CLEAR_BROKER_CONSIGNEE_CODE" });
      }
      return response;
  })
  .catch(error => {
    return error;
  });
  }
  export function removeSpace(values){
    const {dispatch,args,getState}=values;
    const state=getState();
    const formAttributes=state.form.customerDetailsForm?state.form.customerDetailsForm.values:""
    if((formAttributes.city!=undefined&&!isEmpty(formAttributes.city)) ||
    (formAttributes.street!=undefined&&!isEmpty(formAttributes.street)) ||
    (formAttributes.customerName!=undefined&&!isEmpty(formAttributes.customerName))){
      console.log(args);
      const custState=state.commonReducer.customerDetails
      let string
      args==="CU"?custState.customerName=removeExtraSpace(string=formAttributes.customerName):
      args==="S"?custState.street=removeExtraSpace(string=formAttributes.street):
      args==="C"?custState.city=removeExtraSpace(string=formAttributes.city):
      args==="Z"?custState.zipCode=removeExtraSpace(string=formAttributes.zipCode):""
      let updatedCustomerDetails={
        station:formAttributes.station,
        country:formAttributes.country}
      dispatch({type:"UPDATE_CUS_STATE",data:updatedCustomerDetails})
    }
   }
  function removeExtraSpace(string){
      if(string!=null){
        const manString=string.trim().toUpperCase();
        return manString
      }
  }
  /**
   * method to fetch view history details
   * @param {*} values 
   */
  export function findPoaHistoryDetails(values) {
    const { dispatch, getState } = values;
    const state = getState();
    const data = populateListRequest(state);
    const url =
      "rest/customermanagement/defaults/brokermapping/findPoaAuditHistory";
    return makeRequest({
      url,
      data,
    })
      .then(function (response) {
        dispatch({
          type: "ADD_VIEWHISTORY_DETAILS",
          data: {
            viewHistoryDetails: response.results[0].poaHistoryDetails,
          },
        });
        dispatch({type:"OPEN_VIEWHISTORY_POPUP"})
        return response;
      })
      .catch((error) => {
        return error;
      });
  }
  export function validateFile(fileObj) {
	let validationPassed = true,
    errors = "";
	if (!isEmpty(fileObj.type) && "application/pdf" !== fileObj.type) {
		validationPassed = false;
		errors = "Please upload a file in .PDF format";
	} else if (!isEmpty(fileObj.size) && fileObj.size > 2048000) {
		validationPassed = false;
		errors = "Please upload a PDF file not more than 2MB";
	}
	return { validationSuccess: validationPassed, errorMsg: errors };
  }

  export function fetchAdditionalDetails(args){
    const brokerMappingFilter= {customerCode:args}
    const data={brokerMappingFilter:brokerMappingFilter}
    const url =
      "rest/customermanagement/defaults/brokermapping/fetchAdditionalDetails";
    return makeRequest({
      url,
      data,
    })
    .catch((error) => {
      return error;
    });
}