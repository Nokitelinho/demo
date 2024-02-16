import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';

export const onSelect = (values) => {
    const { dispatch, getState } = values;
    const state = getState();
    dispatch({ type: 'POP_OVER_OPEN' });
}
export const onOKClick = (values) => {
    const { dispatch, getState } = values;
    const state = getState();
    dispatch({ type: 'POP_OVER_CLOSE' });
}
export const onClose = (values) => {
  const { dispatch, getState } = values;
  const state = getState();
  dispatch({ type: 'POP_OVER_CLOSE_SCODE' });
}
export const onsClick = (values) => {
  const { dispatch, getState } = values;
  const state = getState();
  dispatch({ type: 'POP_OVER_OPEN_SCODE' });
}
export function deleteRows(values) {
    const {dispatch , args , getState} = values;
    const state = getState();
    const rowIndex = args.index.index;
    const serialnum=String(args.index.ScreeningDetails[rowIndex].serialNumber);
    let screeningDetails={};
    screeningDetails.serialnum=serialnum;
    const data ={screeningDetails};
  
   
    const url = 'rest/mail/operations/consignmentsecuritydeclaration/deleteScreeningDetails';
    return makeRequest({
      url, data: { ...data }
  }).then(function (response) {
          const payLoad={ type: 'DELETE_SUCCESS', response, dispatch };
           dispatchData(payLoad);
           return response;
       })
           .catch(error => {
               return error;
           });
}

export function editRows(values) {
    const { dispatch,args,getState } = values;
    const state = getState();
    const editedDetail = args.editedDetail;
    const rowIndex = args.rowIndex;
    const Screening = args.ScreeningDetails;
    const serialnum=String(Screening[rowIndex].serialNumber);
    const additionalSecurityInfo=editedDetail.values.additionalSecurityInfo;
    const pieces=editedDetail.values.pieces;
    const remarks =editedDetail.values.remarks;
    const result=editedDetail.values.result;if(result==null||result==""){
    return Promise.reject(new Error('Missing Result'));
    }
    let screeningAuthority= editedDetail.values.screeningAuthority;
    if(screeningAuthority==null||screeningAuthority==""){
        return Promise.reject(new Error('Missing SM Applicable Authority'));
    }
    let screeningLocation=editedDetail.values.screeningLocation;
    if(screeningLocation=="" || screeningLocation==null)
    {
        screeningLocation=state.filterpanelreducer.screeningLocation;
    }
    let screeningMethodCode=editedDetail.values.screeningMethodCode;
    if(screeningMethodCode==null||screeningMethodCode==""){
    return Promise.reject(new Error('Missing Method'));
    }
    let screeningRegulation=editedDetail.values.screeningRegulation;
    if(screeningRegulation==null||screeningRegulation==""){
    return Promise.reject(new Error('Missing SM Applicable Regulation'));
    }
    let securityStatusParty=state.form.detailPanelForm.values.securityStatusParty;
    if(securityStatusParty=="" || securityStatusParty==null)
    {
        securityStatusParty==state.form.detailPanelForm.values.securityStatusParty;
    }
  
        let securityStatusDate =  state.form.detailPanelForm.values.StatusDate;
        let time='';
        if(state.form.detailPanelForm.fields==undefined||state.form.detailPanelForm.fields.timedetailspanel==undefined)
        {
        let today = new Date()
          let t=new Date(today.toLocaleString('en-US', { timeZone: state.filterpanelreducer.timeZone }));
          time= (((t.getMinutes().toString()).length)==1)?( t.getHours() + ':0' + t.getMinutes()) :( t.getHours() + ':' + t.getMinutes()) ;
        }
        else
        {
          time =state.form.detailPanelForm.values.timedetailspanel;
        }
    const statedBags=editedDetail.values.statedBags!=undefined && editedDetail.values.statedBags!=""?editedDetail.values.statedBags:0;
    const statedWeight =editedDetail.values.statedWeight!=undefined && editedDetail.values.statedWeight!=""?editedDetail.values.statedWeight:0;
    const opFlag="Y";
    const consignmentNumber =Screening[rowIndex].consignmentNumber;
    const consignmentSequenceNumber = Screening[rowIndex].consignmentSequenceNumber;
    const paCode = Screening[rowIndex].paCode;
    const securityReasonCode = "SM";
   
  let screeningDetails ={additionalSecurityInfo,pieces,remarks,result,screeningAuthority,
    screeningLocation,screeningMethodCode,screeningRegulation,securityStatusParty,statedBags,
    opFlag,consignmentNumber,consignmentSequenceNumber,paCode,securityReasonCode,securityStatusDate,
    time,statedWeight,serialnum};
    const data ={screeningDetails};
  
    
    const url = 'rest/mail/operations/consignmentsecuritydeclaration/saveScreeningDetails';
    return makeRequest({
      url, data: { ...data }
  }).then(function (response) {
          const payLoad={ type: 'SAVE_SUCCESS', response, dispatch }
          dispatchData(payLoad);
          return response;
      }).catch(error => {
              return error;
          });
}

export function addRows(values){
  const {dispatch , args, getState} = values;
  const state = getState();

  const newScreeningDetails = args.newScreeningDetails;
  const additionalSecurityInfo=args.newScreeningDetails.additionalSecurityInfo;
    if(newScreeningDetails==null||newScreeningDetails==""){
        return Promise.reject(new Error('Please enter Values'));
    }
  const pieces=args.newScreeningDetails.pieces;
  const remarks =args.newScreeningDetails.remarks;
  const result=args.newScreeningDetails.result;
    if(result==null||result==""){
    return Promise.reject(new Error('Missing Result'));
    }
  const screeningAuthority= args.newScreeningDetails.screeningAuthority;
    if(screeningAuthority==null||screeningAuthority==""){
      return Promise.reject(new Error('Missing SM Applicable Authority'));
  }
  const screeningLocation=args.newScreeningDetails.screeningLocation;
  const screeningMethodCode=args.newScreeningDetails.screeningMethodCode;
    if(screeningMethodCode==null||screeningMethodCode==""){
    return Promise.reject(new Error('Missing Method'));
}
  const screeningRegulation=args.newScreeningDetails.screeningRegulation;
    if(screeningRegulation==null||screeningRegulation==""){
    return Promise.reject(new Error('Missing SM Applicable Regulation'));
    }
  const securityStatusParty=state.form.detailPanelForm.values.securityStatusParty;
  let securityStatusDate =  state.form.detailPanelForm.values.StatusDate;
  let time='';
  if(state.form.detailPanelForm.fields==undefined||state.form.detailPanelForm.fields.timedetailspanel==undefined)
        {
          let today = new Date()
          let t=new Date(today.toLocaleString('en-US', { timeZone: state.filterpanelreducer.timeZone }));
          time= (((t.getMinutes().toString()).length)==1)?( t.getHours() + ':0' + t.getMinutes()) :( t.getHours() + ':' + t.getMinutes()) ;
        }
        else
        {
          time =state.form.detailPanelForm.values.timedetailspanel;
        }
  const statedBags=newScreeningDetails.statedBags!=undefined?newScreeningDetails.statedBags:0;
  const statedWeight =newScreeningDetails.statedWeight!=undefined?newScreeningDetails.statedWeight:0;
  const opFlag="I";
  const consignmentNumber =state.filterpanelreducer.consignmentNumber;
  const consignmentSequenceNumber = state.filterpanelreducer.consignmentSequenceNumber;
  const paCode = state.filterpanelreducer.paCode;
  const securityReasonCode = "SM";

  let screeningDetails ={additionalSecurityInfo,pieces,remarks,result,screeningAuthority,screeningLocation,
        screeningMethodCode,screeningRegulation,securityStatusParty,statedBags,opFlag,consignmentNumber,
        consignmentSequenceNumber,paCode,securityReasonCode,securityStatusDate,time,statedWeight};

  const data ={screeningDetails};

  
  const url = 'rest/mail/operations/consignmentsecuritydeclaration/saveScreeningDetails';
  return makeRequest({
    url, data: { ...data }
}).then(function (response) {
        const payLoad={ type: 'SAVE_SUCCESS', response, dispatch }
        // handleResponse(payLoad);
        dispatchData(payLoad);
        return response;
        }).catch(error => {
            return error;
        });
}
export function addRowsConsigner(values){
    const {dispatch , args, getState} = values;
    const state = getState();
    const newConsignerDetails = args.newConsignerDetails;
    if(newConsignerDetails==null||newConsignerDetails==""){
        return Promise.reject(new Error('Please enter Values'));
    }
      const agenttype = newConsignerDetails.agenttype;
      if(agenttype==null||agenttype==""){
        return Promise.reject(new Error('Missing Agent Type'));
        }
      const agentId =newConsignerDetails.agentId;
      if(agentId==null||agentId==""){
        return Promise.reject(new Error('Missing Agent ID'));
        }
      const isoCountryCode = newConsignerDetails.isoCountryCode;
      const expiryDate=newConsignerDetails.expiryDate;
      const consignmentNumber =state.filterpanelreducer.consignmentNumber;
      const consignmentSequenceNumber = state.filterpanelreducer.consignmentSequenceNumber;
      const paCode = state.filterpanelreducer.paCode;
      const securityReasonCode = "CS";
      const opFlag="I";
    let consignerDetails={agenttype,agentId,isoCountryCode,expiryDate,consignmentNumber,
        consignmentSequenceNumber,paCode,securityReasonCode,opFlag};
      const data ={consignerDetails};
       const url = 'rest/mail/operations/consignmentsecuritydeclaration/saveScreeningDetails';
      return makeRequest({
        url, data: { ...data }
    }).then(function (response) {
            const payLoad={ type: 'SAVE_SUCCESS', response, dispatch }
            dispatchData(payLoad);
            return response;
    })
            .catch(error => {
                if (error =='mail.operations.ux.mailbagsecuritydetails.msg.err.invalidcountrycode')
                {
                return Promise.reject(new Error('Invalid Country code'));
                }
                else{
                  return error  
                }
            });
 }
 export function editRowsConsigner(values){
    const {dispatch , args, getState} = values;
    const state = getState();
    const EditConsignerDetails = args.EditConsignerDetails.values;
    if(EditConsignerDetails==null||EditConsignerDetails==""){
        return Promise.reject(new Error('Please enter Values'));
    }
      const agenttype = EditConsignerDetails.agenttype;
      if(agenttype==null||agenttype==""){
        return Promise.reject(new Error('Missing Agent Type'));
        }
      const agentId =EditConsignerDetails.agentId;
      if(agentId==null||agentId==""){
        return Promise.reject(new Error('Missing Agent ID'));
        }
      const isoCountryCode = EditConsignerDetails.isoCountryCode;
      const expiryDate=EditConsignerDetails.expiryDate;
      const consignmentNumber =state.filterpanelreducer.consignmentNumber;
      const consignmentSequenceNumber = state.filterpanelreducer.consignmentSequenceNumber;
      const paCode = state.filterpanelreducer.paCode;
      const securityReasonCode = "CS";
      const opFlag="Y";
      const rowIndex = args.rowIndex;
      const serialnum=String(EditConsignerDetails.serialNumber);
    let consignerDetails={agenttype,agentId,isoCountryCode,expiryDate,consignmentNumber,
        consignmentSequenceNumber,paCode,securityReasonCode,opFlag,serialnum};
      const data ={consignerDetails};
       const url = 'rest/mail/operations/consignmentsecuritydeclaration/saveScreeningDetails';
      return makeRequest({
        url, data: { ...data }
    }).then(function (response) {
            const payLoad={ type: 'SAVE_SUCCESS', response, dispatch }
            dispatchData(payLoad);
            return response;
        }).catch(error => {
                if (error =='mail.operations.ux.mailbagsecuritydetails.msg.err.invalidcountrycode'){
                return Promise.reject(new Error('Invalid Country code'));
                }
                else{
                  return error  
                }
            });
 }
 export function deleteRowscons(values) {
    const {dispatch , args , getState} = values;
    const rowIndex = args.index.index;
    const serialnum=String(args.index.ConsignerDetails[rowIndex].serialNumber);
    let consignerDetails={};
    consignerDetails.serialnum=serialnum;
    const data ={consignerDetails};   
    const url = 'rest/mail/operations/consignmentsecuritydeclaration/deleteScreeningDetails';
    return makeRequest({
      url, data: { ...data }
  }).then(function (response) {
          const payLoad={ type: 'DELETE_SUCCESS', response, dispatch };
           dispatchData(payLoad);
           return response;
       })
        .catch(error => {
            return error;
        });
}


export function addRowsExemption(values){
    const {dispatch , args, getState} = values;
    const state = getState();
    const newSecurityExemption = args.newSecurityExemption.values;
      const screeningMethodCode = newSecurityExemption.seScreeningReasonCode;
      if(screeningMethodCode==null||screeningMethodCode==""){
        return Promise.reject(new Error('Missing Reason for Exemption'));
        }
        if(screeningMethodCode!='MAIL')
        {
          return Promise.reject(new Error('Incorrect Reason for Exemption. Possible value Mail'));
        }
      const screeningAuthority =newSecurityExemption.seScreeningAuthority;
      if(screeningAuthority==null||screeningAuthority==""){
        return Promise.reject(new Error('Missing SE Applicable Authority'));
        }
      const screeningRegulation =newSecurityExemption.seScreeningRegulation;
      if(screeningRegulation==null||screeningRegulation==""){
        return Promise.reject(new Error('Missing SE Applicable Regulation'));
        }
      const consignmentNumber =state.filterpanelreducer.consignmentNumber;
      const consignmentSequenceNumber = state.filterpanelreducer.consignmentSequenceNumber;
      const paCode = state.filterpanelreducer.paCode;
      const securityReasonCode = "SE";
      const opFlag="I";
    let securityExemption={screeningMethodCode,screeningAuthority,screeningRegulation,consignmentNumber,
        consignmentSequenceNumber,paCode,securityReasonCode,opFlag};
      const data ={securityExemption};
       const url = 'rest/mail/operations/consignmentsecuritydeclaration/saveScreeningDetails';
      return makeRequest({
        url, data: { ...data }
    }).then(function (response) {
            const payLoad={ type: 'SAVE_SUCCESS', response, dispatch }
            dispatchData(payLoad);
            return response;
        }).catch(error => {
            return error;
        });
}
export function deleteRowsExemption(values) {
    const {dispatch , args , getState} = values;
    const rowIndex = args.index.index;
    const serialnum=String(args.index.securityExemption[rowIndex].serialNumber);
    let securityExemption={};
    securityExemption.serialnum=serialnum;
    const data ={securityExemption};   
    const url = 'rest/mail/operations/consignmentsecuritydeclaration/deleteScreeningDetails';
    return makeRequest({
      url, data: { ...data }
  }).then(function (response) {
          const payLoad={ type: 'DELETE_SUCCESS', response, dispatch };
           dispatchData(payLoad);
           return response;
       })
           .catch(error => {
               return error;
           });
}
export function editRowsExemption(values){
    const {dispatch , args, getState} = values;
    const state = getState();
    const EditsecurityExemption = args.EditsecurityExemption.values;
    const serialnum=String(EditsecurityExemption.serialNumber);
      const screeningMethodCode = EditsecurityExemption.seScreeningReasonCode;
      if(screeningMethodCode==null||screeningMethodCode==""){
        return Promise.reject(new Error('Missing Reason for Exemption'));
        }
        if(screeningMethodCode!='MAIL')
        {
            return Promise.reject(new Error('Incorrect Reason for Exemption. Possible value Mail'));
        }
      const screeningAuthority =EditsecurityExemption.seScreeningAuthority;
      if(screeningAuthority==null||screeningAuthority==""){
        return Promise.reject(new Error('Missing SE Applicable Authority'));
        }
      const screeningRegulation =EditsecurityExemption.seScreeningRegulation;
      if(screeningRegulation==null||screeningRegulation==""){
        return Promise.reject(new Error('Missing SE Applicable Regulation'));
        }
      const consignmentNumber =state.filterpanelreducer.consignmentNumber;
      const consignmentSequenceNumber = state.filterpanelreducer.consignmentSequenceNumber;
      const paCode = state.filterpanelreducer.paCode;
      const securityReasonCode = "SE";
      const opFlag="Y";
    let securityExemption={screeningMethodCode,screeningAuthority,screeningRegulation,consignmentNumber,
        consignmentSequenceNumber,paCode,securityReasonCode,opFlag,serialnum};
      const data ={securityExemption};
       const url = 'rest/mail/operations/consignmentsecuritydeclaration/saveScreeningDetails';
      return makeRequest({
        url, data: { ...data }
    }).then(function (response) {
            const payLoad={ type: 'SAVE_SUCCESS', response, dispatch }
            dispatchData(payLoad);
            return response;
        }).catch(error => {
            return error;
        });
}
export function addRowsRegulation(values){
    const {dispatch , args, getState} = values;
    const state = getState();
    const newApplicableRegulation = args.newApplicableRegulation.values;
      const applicableRegTransportDirection = newApplicableRegulation.applicableRegTransportDirection;
      if(applicableRegTransportDirection==null||applicableRegTransportDirection==""){
        return Promise.reject(new Error('Missing AR Transport Direction'));
        }
      const applicableRegBorderAgencyAuthority =newApplicableRegulation.applicableRegBorderAgencyAuthority;
      if(applicableRegBorderAgencyAuthority==null||applicableRegBorderAgencyAuthority==""){
        return Promise.reject(new Error('Missing AR Border Agency Authority'));
        }
      const applicableRegReferenceID =newApplicableRegulation.applicableRegReferenceID;
      if(applicableRegReferenceID==null||applicableRegReferenceID==""){
        return Promise.reject(new Error('Missing AR Reference ID'));
        }
      const applicableRegFlag =newApplicableRegulation.applicableRegFlag;
      if(applicableRegFlag==null||applicableRegFlag==""){
        return Promise.reject(new Error('Missing AR Flag'));
        }
      const consignmentNumber =state.filterpanelreducer.consignmentNumber;
      const consignmentSequenceNumber = state.filterpanelreducer.consignmentSequenceNumber;
      const paCode = state.filterpanelreducer.paCode;
      const securityReasonCode = "AR";
      const opFlag="I";
    let applicableRegulation={applicableRegTransportDirection,applicableRegBorderAgencyAuthority,applicableRegReferenceID,applicableRegFlag,
        consignmentNumber,consignmentSequenceNumber,paCode,securityReasonCode,opFlag};
      const data ={applicableRegulation};
       const url = 'rest/mail/operations/consignmentsecuritydeclaration/saveScreeningDetails';
      return makeRequest({
        url, data: { ...data }
    }).then(function (response) {
            const payLoad={ type: 'SAVE_SUCCESS', response, dispatch }
            dispatchData(payLoad);
            return response;
        }).catch(error => {
            return error;
        });
}
export function deleteRowsRegulation(values) {
    const {dispatch , args , getState} = values;
    const rowIndex = args.index.index;
    const serialnum=String(args.index.applicableRegulation[rowIndex].serialNumber);
    let applicableRegulation={};
    applicableRegulation.serialnum=serialnum;
    const data ={applicableRegulation};   
    const url = 'rest/mail/operations/consignmentsecuritydeclaration/deleteScreeningDetails';
    return makeRequest({
      url, data: { ...data }
  }).then(function (response) {
          const payLoad={ type: 'DELETE_SUCCESS', response, dispatch };
           dispatchData(payLoad);
           return response;
       })
           .catch(error => {
               return error;
           });
}
export function editRowsRegulation(values){
    const {dispatch , args, getState} = values;
    const state = getState();
    const EditApplicableRegulation = args.EditRegulationDetails.values;
    const serialnum=String(EditApplicableRegulation.serialNumber);
      const applicableRegTransportDirection = EditApplicableRegulation.applicableRegTransportDirection;
      if(applicableRegTransportDirection==null||applicableRegTransportDirection==""){
        return Promise.reject(new Error('Missing AR Transport Direction'));
        }
      const applicableRegBorderAgencyAuthority =EditApplicableRegulation.applicableRegBorderAgencyAuthority;
      if(applicableRegBorderAgencyAuthority==null||applicableRegBorderAgencyAuthority==""){
        return Promise.reject(new Error('Missing AR Border Agency Authority'));
        }
      const applicableRegReferenceID =EditApplicableRegulation.applicableRegReferenceID;
      if(applicableRegReferenceID==null||applicableRegReferenceID==""){
        return Promise.reject(new Error('Missing AR Reference ID'));
        }
      const applicableRegFlag =EditApplicableRegulation.applicableRegFlag;
      if(applicableRegFlag==null||applicableRegFlag==""){
        return Promise.reject(new Error('Missing AR Flag'));
        }
      const consignmentNumber =state.filterpanelreducer.consignmentNumber;
      const consignmentSequenceNumber = state.filterpanelreducer.consignmentSequenceNumber;
      const paCode = state.filterpanelreducer.paCode;
      const securityReasonCode = "AR";
      const opFlag="Y";
    let applicableRegulation={applicableRegTransportDirection,applicableRegBorderAgencyAuthority,applicableRegReferenceID,applicableRegFlag,
        consignmentNumber,consignmentSequenceNumber,paCode,securityReasonCode,opFlag,serialnum};
      const data ={applicableRegulation};
       const url = 'rest/mail/operations/consignmentsecuritydeclaration/saveScreeningDetails';
      return makeRequest({
        url, data: { ...data }
    }).then(function (response) {
            const payLoad={ type: 'SAVE_SUCCESS', response, dispatch }
            dispatchData(payLoad);
            return response;
        }).catch(error => {
            return error;
        });
}
export const onSaveSecurityStatus = (values) => {
  const { args, dispatch, getState } = values;
  const state = getState();
  const { action } = args;
  const securityStatusCode = state.form.securityPopOverForm.values ? state.form.securityPopOverForm.values.securityStatusCode : "";
  if (isEmpty(securityStatusCode)) {
      return Promise.reject(new Error("Please select security status code"));
  }
  const consignmentNumber =state.filterpanelreducer.consignmentNumber;
  const consignmentSequenceNumber = parseInt(state.filterpanelreducer.consignmentSequenceNumber);
  const paCode = state.filterpanelreducer.paCode;
  const data = { securityStatusCode, consignmentNumber, consignmentSequenceNumber,paCode};
  const url = 'rest/mail/operations/consignmentsecuritydeclaration/saveSecurityStatusCode';
  return makeRequest({
      url,
      data: { ...data }
  }).then(function (response) {
      return response
  })
      .catch(error => {
            return error;
        });
}
export const dispatchData = (payload) => {
  const {dispatch,response,type}=payload;
  dispatch({type}) 
}
