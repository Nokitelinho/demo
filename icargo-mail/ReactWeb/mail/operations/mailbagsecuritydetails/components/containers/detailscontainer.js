import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import DetailsPanel from '../panels/DetailsPanel.jsx';
import { addScreenigDetails, addConsignorDetails, editScreeningDetails, editConsignorDetails,deleteScreeningDetails, deleteConsignorDetails  } from '../../actions/popupactions';
import { dispatchAction,asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import {saveNewScreeningDetails } from '../../actions/detailsaction';
import {saveNewConsignorDetails } from '../../actions/detailsaction';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { listmailbagSecurityDetails, onChangeScreenMode, clearPanelFilter } from '../../actions/filteraction';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
const mapStateToProps = (state) => {
    return {
        mailbagSecurityDetails: state.listmailbagReducer.mailbagSecurityDetails,
        screeningDetails: state.listmailbagReducer.screeningDetails,
        consignerDetails: state.listmailbagReducer.consignerDetails,
        screenMode: state.listmailbagReducer.screenMode,
        screeningDetailPopupShow: state.commonReducer.screeningDetailPopupShow,
        initialValues: state.commonReducer.initialValues,
        consignorDetailsPopupShow: state.commonReducer.consignorDetailsPopupShow,
        oneTimeValues: state.listmailbagReducer.oneTimeValues,
        screeningLocation:state.listmailbagReducer.airportCode,
        securityStatusParty:state.listmailbagReducer.StatusParty,
        securityStatusDate:state.listmailbagReducer.StatusDate,
        time:state.listmailbagReducer.StatusTime
    }
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {

        addScreenigDetails: (data) => {
            dispatch(dispatchAction(addScreenigDetails)({ mode: data.actionName }))
        },
        addConsignorDetails: (data) => {
            dispatch(dispatchAction(addConsignorDetails)({ mode: data.actionName }))
        },

        closeAddContainerPopup: () => {
            dispatch({ type: "ADD_CONTAINER_POPUPCLOSE" });
        },
        editScreeningDetails: (data) => {
            dispatch(dispatchAction(editScreeningDetails)({ mode: data.actionName, index: data.index }))
        },

        editConsignorDetails: (data) => {
            dispatch(dispatchAction(editConsignorDetails)({ mode: data.actionName, index: data.index }))
        }, 
        
        deleteScreeningDetails: (data) => {
            dispatch(requestWarning([{code:"mail.operations.screening.delete", description:"Do you wish to delete ?"}],{functionRecord:deleteScreeningDetails,args:{mode: data.actionName,index:data.index}}))

                
        },

        deleteConsignorDetails: (data) => {
            dispatch(requestWarning([{code:"mail.operations.consigner.delete", description:"Do you wish to delete ?"}],{functionRecord:deleteConsignorDetails,args:{mode: data.actionName,index:data.index}}))
                
        },
        onsaveNewScreeningDetails: () =>{
            dispatch(asyncDispatch(saveNewScreeningDetails)()).then((response)=>{
                if(!isEmpty(response)) {
                    dispatch(reset('screeningDetailsPopupForm'));
                    dispatch({ type: 'CLEAR_FILTER'});                  
                }
                dispatch(asyncDispatch(listmailbagSecurityDetails)({ action: 'LIST' }))
              });
          },
          onsaveNewConsignorDetails: () =>{
            dispatch(asyncDispatch(saveNewConsignorDetails)()).then((response)=>{
                if(!isEmpty(response)) {
                    dispatch(reset('consignorDetailsPopupForm'));
                    dispatch({ type: 'CLEAR_FILTER'}); 
                }
                dispatch(asyncDispatch(listmailbagSecurityDetails)({ action: 'LIST' }))
              });
        }

    }
}



const DetailsContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(DetailsPanel)


export default DetailsContainer