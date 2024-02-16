import * as constant from '../constants/constants';

export function deleteRows(values) {
    const {dispatch , args , getState} = values;
    const state = getState();
    const rowIndex = args.index
    const ScreeningDetails = args.ScreeningDetails
    const deletedRows = state.filterpanelreducer.deletedRows?state.filterpanelreducer.deletedRows:''
    if (rowIndex.length > 0) {
      if (ScreeningDetails[rowIndex].opFlag === "I"){ 
        ScreeningDetails.splice(rowIndex, 1)
        dispatch({ type: 'ADD_DELETE_DETAILS' , ScreeningDetails });
      }else{
         const deletedRow = ScreeningDetails[rowIndex];
         deletedRow.opFlag = "D"
         ScreeningDetails.splice(rowIndex, 1)
         dispatch({ type: 'DELETE_DETAILS' , ScreeningDetails , deletedRows , deletedRow});
      }
    } 
}

export function editRows(values) {
    const { dispatch,args } = values;
    const editedDetail = args.editedDetail
    const ScreeningDetails = args.ScreeningDetails
    const rowIndex = args.rowIndex

    if(editedDetail){
    [editedDetail].map(() =>{
        if(editedDetail.anyTouched){
            [editedDetail.values].map((item) =>{
                 [ScreeningDetails[rowIndex]].map((value) =>{
                    if(item.screeningLocation !== value.screeningLocation){
                        value.screeningLocation= item.screeningLocation
                      }
                      if(item.screeningMethodCode !== value.screeningMethodCode){
                        value.screeningMethodCode= item.screeningMethodCode
                      }
                     if(item.statedBags !== value.statedBags){
                       value.statedBags= item.statedBags
                      }
                      if(item.statedWeight !== value.statedWeight){
                        value.statedWeight= item.statedWeight
                      }
                      if(item.securityStatusParty !== value.securityStatusParty){
                        value.securityStatusParty= item.securityStatusParty
                      }
                      if(item.time!=null && item.time!= value.time){
                        value.time = item.time
                        if(value.securityStatusDate!=null){
                        value.securityStatusDate = value.securityStatusDate.concat(" ").concat(value.time);
                        }
                      }
                      if(value.time==""){
                        value.time = "00:00:00"
                        if(item.securityStatusDate!=null){
                        value.securityStatusDate = item.securityStatusDate.concat(" ").concat(value.time);
                        }
                      }
                      if(item.securityStatusDate !== value.securityStatusDate){
                        if(value.securityStatusDate!=null || item.securityStatusDate!=null){
                        value.securityStatusDate= item.securityStatusDate.concat(" ").concat(value.time);
                        }
                      }
                      if(item.screeningAuthority !== value.screeningAuthority){
                        value.screeningAuthority= item.screeningAuthority
                      }
                      if(item.screeningRegulation !== value.screeningRegulation){
                        value.screeningRegulation= item.screeningRegulation
                      }
                      if(item.additionalSecurityInfo !== value.additionalSecurityInfo){
                        value.additionalSecurityInfo= item.additionalSecurityInfo
                      }
                      if(item.result !== value.result){
                        value.result= item.result
                      }
                      if(item.remarks !== value.remarks){
                        value.remarks= item.remarks
                      }
                }) 
            })   
        } 
     })
    }
    
    const modifiedList = ScreeningDetails
    modifiedList[rowIndex].opFlag === 'I' ? modifiedList[rowIndex].opFlag = 'I': modifiedList[rowIndex].opFlag = 'U'
    dispatch({ type: 'NEW_DETAILS', modifiedList });
}

export function addRows(values){
  const {dispatch , args, getState} = values
  const state = getState()
  const newScreeningDetails = args.newScreeningDetails
  const ScreeningDetails = args.ScreeningDetails

  newScreeningDetails.opFlag="I";
  newScreeningDetails.consignmentNumber =state.filterpanelreducer.consignmentNumber
  newScreeningDetails.consignmentSequenceNumber = state.filterpanelreducer.consignmentSequenceNumber
  newScreeningDetails.paCode = state.filterpanelreducer.paCode
  newScreeningDetails.securityReasonCode = "SM"

  if(newScreeningDetails.time == null){
    newScreeningDetails.time = "00:00:00";
  }
  if(newScreeningDetails.securityStatusDate!=null){
  newScreeningDetails.securityStatusDate = newScreeningDetails.securityStatusDate.concat(" ").concat(newScreeningDetails.time)
  }

  if(newScreeningDetails.pieces == true){
    newScreeningDetails.statedBags =state.filterpanelreducer.fullpiecesBags?state.filterpanelreducer.fullpiecesBags:'',
    newScreeningDetails.statedWeight =state.filterpanelreducer.fullPiecesWeight?state.filterpanelreducer.fullPiecesWeight:''
  }

  dispatch({type:constant.NEW_SCREENING_DETAILS, newScreeningDetails,ScreeningDetails});
}

export function addConsignerRows(values){
  const {dispatch , args, getState} = values
  const state = getState()
  const newConsignerDetails = args.newConsignerDetails
  const ConsignerDetails = args.ConsignerDetails

  newConsignerDetails.opFlag="I"
  newConsignerDetails.consignmentNumber =state.filterpanelreducer.consignmentNumber
  newConsignerDetails.consignmentSequenceNumber = state.filterpanelreducer.consignmentSequenceNumber
  newConsignerDetails.paCode = state.filterpanelreducer.paCode
  newConsignerDetails.screeningLocation = state.filterpanelreducer.screeningLocation?state.filterpanelreducer.screeningLocation:''
  newConsignerDetails.securityReasonCode = "CS"

  dispatch({type:constant.NEW_CONSIGNER_DETAILS, newConsignerDetails,ConsignerDetails});

}

// export function clickFullPieces(values){
//   const { args, dispatch } = values;
//     if(args == true) {
//     dispatch({ type: constant.FULL_PIECES_FLAG, data: true });
//     } else {
//          dispatch({ type: constant.FULL_PIECES_FLAG, data: false });
//     }
// }

export function changeTab(values){
  const {dispatch , args, getState} = values
  const state = getState()
  const currentTab = args.currentTab;
  const securityExemptionFormValues = state.form.securityexemptionform?state.form.securityexemptionform:state.detailspanelreducer.ExemptionForm;
  const applicaleRegFormvalues = state.form.applicableregulationinfo?state.form.applicableregulationinfo:state.detailspanelreducer.ApplicableInfoForm;

  dispatch({type:constant.CHANGE_TAB,currentTab, securityExemptionFormValues,applicaleRegFormvalues});
}
