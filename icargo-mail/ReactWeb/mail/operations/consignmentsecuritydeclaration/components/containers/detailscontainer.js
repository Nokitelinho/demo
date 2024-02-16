import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import DetailsPanel from '../panels/DetailsPanel.jsx';
import * as constant from '../../constants/constants';
import {deleteRows , editRows, addRows ,addRowsConsigner,deleteRowscons,editRowsConsigner,addRowsExemption,
  deleteRowsExemption,editRowsExemption,addRowsRegulation, deleteRowsRegulation, editRowsRegulation,onSelect,
  onOKClick,onSaveSecurityStatus,onClose,onsClick} from '../../actions/detailspanelactions';
import { dispatchAction, asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import {addScreenigDetails,editScreenigDetails} from '../../actions/commonactions';
import { onlistConsignment } from '../../actions/filterpanelactions';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { toggleFilter} from '../../actions/filterpanelactions';




const mapStateToProps = (state) => {
    return {
        screenMode: state.filterpanelreducer.screenMode,
        activeTab: state.detailspanelreducer.activeTab,
        addbutton: state.detailspanelreducer.addbutton,
        addButtonShow : state.detailspanelreducer.addButtonShow,
        filterList:state.filterpanelreducer.filterList,
        ScreeningDetails: state.filterpanelreducer.ScreeningDetails,
        ConsignerDetails: state.filterpanelreducer.ConsignerDetails,
        securityExemption:state.filterpanelreducer.securityExemption,
        applicableRegulation:state.filterpanelreducer.applicableRegulation,
        securityStatusCode:state.filterpanelreducer.securityStatusCode,
        ScreeningDetailsList: state.filterpanelreducer.ScreeningDetailsList,
        ExemptionForm: state.detailspanelreducer.ExemptionForm?state.detailspanelreducer.ExemptionForm:'',
        ApplicableInfoForm: state.detailspanelreducer.ApplicableInfoForm?state.detailspanelreducer.ApplicableInfoForm:'',
        isSaved:state.filterpanelreducer.isSaved?state.filterpanelreducer.isSaved:'',
        showPopover: state.detailspanelreducer.showPopover,
        showPopoverscode:state.detailspanelreducer.showPopoverscode,
        detailPanelForm:state.form.detailPanelForm,
        showITable: state.filterpanelreducer.showITable,

        initialValues:{
       
        screeningLocation:state.filterpanelreducer.screeningLocation?state.filterpanelreducer.screeningLocation:'',
        securityStatusParty:state.filterpanelreducer.securityStatusParty?state.filterpanelreducer.securityStatusParty:'',
        securityStatusDate:state.detailspanelreducer.securityStatusDate,
        time:state.detailspanelreducer.time,
        StatusDate:state.filterpanelreducer.StatusDate,
        timedetailspanel:state.filterpanelreducer.timedetailspanel,
        securityStatusCode:state.filterpanelreducer.securityStatusCode,
        mstAddionalSecurityInfo:state.filterpanelreducer.mstAddionalSecurityInfo
    },
        newScreeningDetails:state.form.addScreeingButtonPanelform?state.form.addScreeingButtonPanelform:'',
        concatenatedScreeningDetails:state.filterpanelreducer.ScreeningDetails?state.filterpanelreducer.ScreeningDetails:'',
        newConsignerDetails:state.form.addConsignerButtonPanelForm?state.form.addConsignerButtonPanelForm:'',
        concatenatedConsignerDetails:state.filterpanelreducer.ConsignerDetails?state.filterpanelreducer.ConsignerDetails:'',
        oneTimeValues:state.commonReducer.oneTimeValues?state.commonReducer.oneTimeValues:'',
        editPopup:state.detailspanelreducer.editPopup?state.detailspanelreducer.editPopup:'',
        editPopupreg:state.detailspanelreducer.editPopupreg?state.detailspanelreducer.editPopupreg:'',
        editedDetail:state.form.addScreeingButtonPanelform?state.form.addScreeingButtonPanelform:'',
        editedDetailcons:state.form.addConsignerButtonPanelForm?state.form.addConsignerButtonPanelForm:'',
        rowIndex:state.detailspanelreducer.rowIndex?state.detailspanelreducer.rowIndex:'',
        newSecurityExemption:state.form.addExemptionButtonPanelForm?state.form.addExemptionButtonPanelForm:'',
        editedDetailexemption:state.form.addExemptionButtonPanelForm?state.form.addExemptionButtonPanelForm:'',
        newApplicableRegulation:state.form.addApplicableRegulationPanelform?state.form.addApplicableRegulationPanelform:'',
        editedDetailRegulation:state.form.addApplicableRegulationPanelform?state.form.addApplicableRegulationPanelform:'',
        EditScreeningDetails:state.detailspanelreducer.EditScreeningDetails?{
          screeningLocation:state.filterpanelreducer.airportCode?state.filterpanelreducer.airportCode:state.detailspanelreducer.EditScreeningDetails.screeningLocation?state.detailspanelreducer.EditScreeningDetails.screeningLocation:'',
          screeningMethodCode: state.detailspanelreducer.EditScreeningDetails.screeningMethodCode?state.detailspanelreducer.EditScreeningDetails.screeningMethodCode:'',
          statedBags: state.detailspanelreducer.EditScreeningDetails.statedBags?state.detailspanelreducer.EditScreeningDetails.statedBags:'',
          statedWeight: state.detailspanelreducer.EditScreeningDetails.statedWeight?state.detailspanelreducer.EditScreeningDetails.statedWeight:'',
          securityStatusParty: state.detailspanelreducer.EditScreeningDetails.securityStatusParty?state.detailspanelreducer.EditScreeningDetails.securityStatusParty:'',
          securityStatusDate : state.detailspanelreducer.EditScreeningDetails.securityStatusDate?state.detailspanelreducer.EditScreeningDetails.securityStatusDate.split(' ')[0]:'',
          time:state.detailspanelreducer.EditScreeningDetails.securityStatusDate?state.detailspanelreducer.EditScreeningDetails.securityStatusDate.split(' ')[1]:'',
          screeningAuthority : state.detailspanelreducer.EditScreeningDetails.screeningAuthority?state.detailspanelreducer.EditScreeningDetails.screeningAuthority:'',
          screeningRegulation : state.detailspanelreducer.EditScreeningDetails.screeningRegulation?state.detailspanelreducer.EditScreeningDetails.screeningRegulation:'',
          additionalSecurityInfo : state.detailspanelreducer.EditScreeningDetails.additionalSecurityInfo?state.detailspanelreducer.EditScreeningDetails.additionalSecurityInfo:'',
          remarks : state.detailspanelreducer.EditScreeningDetails.remarks?state.detailspanelreducer.EditScreeningDetails.remarks:'',
          result : state.detailspanelreducer.EditScreeningDetails.result?state.detailspanelreducer.EditScreeningDetails.result:''

      }:'',
      EditConsignerDetails: state.detailspanelreducer.EditConsignerDetails?{
        agentId:state.detailspanelreducer.EditConsignerDetails.agentId,
        agenttype:state.detailspanelreducer.EditConsignerDetails.agenttype,
        isoCountryCode:state.detailspanelreducer.EditConsignerDetails.isoCountryCode,
        expiryDate:state.detailspanelreducer.EditConsignerDetails.expiryDate,
        editPopupcons:state.detailspanelreducer.editPopupcons
      }:'',
      EditsecurityExemption:state.detailspanelreducer.EditsecurityExemption?{
        screeningMethodCode:state.detailspanelreducer.EditsecurityExemption.seScreeningReasonCode,
        screeningAuthority:state.detailspanelreducer.EditsecurityExemption.seScreeningAuthority,
        screeningRegulation:state.detailspanelreducer.EditsecurityExemption.seScreeningRegulation,
        editPopupex:state.detailspanelreducer.editPopupex
      }:'',
      EditRegulationDetails:state.detailspanelreducer.EditRegulationDetails?{
        applicableRegTransportDirection:state.detailspanelreducer.EditRegulationDetails.applicableRegTransportDirection,
        applicableRegBorderAgencyAuthority:state.detailspanelreducer.EditRegulationDetails.applicableRegBorderAgencyAuthority,
        applicableRegReferenceID:state.detailspanelreducer.EditRegulationDetails.applicableRegReferenceID,
        applicableRegFlag:state.detailspanelreducer.EditRegulationDetails.applicableRegFlag,
        editPopupreg:state.detailspanelreducer.editPopupreg
      }:''
    }
}
const mapDispatchToProps = (dispatch) => {
    return {
        

    onSelect: () => {
      dispatch(dispatchAction(onSelect)());
  },
  onOKClick: () => {
    dispatch(dispatchAction(onOKClick)());
},
          onsClick: () => {
            dispatch(dispatchAction(onsClick)());
          },
          onClose: () => {
            dispatch(dispatchAction(onClose)());
          },
          addButtonClick: (activebutton, popupOpen) => {
            dispatch({type:constant.ADD_BUTTON,activebutton: 'AddScreeningDetails' , addButtonShow: 'true'});
          } ,
             addButtonConsClick: (activebutton, popupOpen) => {
            dispatch({type:'ADD_CONSIGNER_DETAILS',activebutton: activebutton , addButtonShow: popupOpen});
          } ,
          addButtonExemptionClick: (activebutton, popupOpen) => {
            dispatch({type:'ADD_EXEMPTION_DETAILS',activebutton: activebutton , addButtonShow: popupOpen});
          } ,
          addButtonRegulaionClick: (activebutton, popupOpen) => {
            dispatch({type:'ADD_REGULATION_DETAILS',activebutton: activebutton , addButtonShow: popupOpen});
          } ,
          closeButton: () => {
            dispatch({type:constant.CLOSE_BUTTON});
          } ,

          okButton: (newScreeningDetails,ScreeningDetails) =>{
            dispatch(asyncDispatch(addRows)({newScreeningDetails,ScreeningDetails})).then((response)=>{
              dispatch(asyncDispatch(onlistConsignment)({mode:'LIST'})).then((response)=>{ 
              dispatch({type:constant.CLOSE_BUTTON})
             
            });
          });
          },

          rowActionDelete:( index) =>{
              dispatch(requestWarning([{code:"mail.operations.screening.delete", description:"Do you wish to delete ?"}],{functionRecord:deleteRows,args:{index:index}}))
            },
			 rowActionDeletecons:( index) =>{
              dispatch(requestWarning([{code:"mail.operations.consigner.delete", description:"Do you wish to delete ?"}],{functionRecord:deleteRowscons,args:{index:index}}))
            },

          rowActionEdit:(EditScreeningDetails , index) =>{
            dispatch({type:constant.EDIT_DETAILS, EditScreeningDetails , index});
          },

          okEditButton:(editedDetail , rowIndex, ScreeningDetails) =>{
            dispatch(asyncDispatch(editRows)({editedDetail,rowIndex,ScreeningDetails})).then((response)=>{
              dispatch(asyncDispatch(onlistConsignment)({mode:'LIST'})).then((response)=>{ 
              dispatch({type:constant.CLOSE_BUTTON})
            });
          });
           },
		okConsignerButton: (newConsignerDetails,ConsignerDetails) =>{
            dispatch(asyncDispatch(addRowsConsigner)({newConsignerDetails,ConsignerDetails})).then((response)=>{
              dispatch(asyncDispatch(onlistConsignment)({mode:'LIST'})).then((response)=>{ 
              dispatch({type:constant.CLOSE_BUTTON})
            });
          });
          },
          okEditConsignerButton: (EditConsignerDetails,ConsignerDetails) =>{
            dispatch(asyncDispatch(editRowsConsigner)({EditConsignerDetails,ConsignerDetails})).then((response)=>{
              dispatch(asyncDispatch(onlistConsignment)({mode:'LIST'})).then((response)=>{ 
              dispatch({type:constant.CLOSE_BUTTON})
            });
          });
          },
          rowActionEditcons:(EditConsignerDetails , index) =>{
            dispatch({type:'EDIT_CONSINOR_DETAILS', EditConsignerDetails , index});
           },
		       okExemptionButton:(newSecurityExemption,securityExemption) =>{
            dispatch(asyncDispatch(addRowsExemption)({newSecurityExemption,securityExemption})).then((response)=>{
              dispatch(asyncDispatch(onlistConsignment)({mode:'LIST'})).then((response)=>{ 
              dispatch({type:constant.CLOSE_BUTTON})
            });
          });
          },
          rowActionDeleteExemption:( index) =>{
            dispatch(requestWarning([{code:"mail.operations.exemption.delete", description:"Do you wish to delete ?"}],{functionRecord:deleteRowsExemption,args:{index:index}}))
          },
          rowActionEditExemption:(EditsecurityExemption , index) =>{
            dispatch({type:'EDIT_EXEMPTION_DETAILS', EditsecurityExemption , index});
          },
          okEditsecurityExemption: (EditsecurityExemption,securityExemption) =>{
            dispatch(asyncDispatch(editRowsExemption)({EditsecurityExemption,securityExemption})).then((response)=>{
              dispatch(asyncDispatch(onlistConsignment)({mode:'LIST'})).then((response)=>{ 
              dispatch({type:constant.CLOSE_BUTTON})
            });
          });
          },
          okRegulationButton:(newApplicableRegulation,applicableRegulation) =>{
            dispatch(asyncDispatch(addRowsRegulation)({newApplicableRegulation,applicableRegulation})).then((response)=>{
              dispatch(asyncDispatch(onlistConsignment)({mode:'LIST'})).then((response)=>{ 
              dispatch({type:constant.CLOSE_BUTTON})
            });
          });
          },
          rowActionDeleteRegulation:( index) =>{
            dispatch(requestWarning([{code:"mail.operations.regulation.delete", description:"Do you wish to delete ?"}],{functionRecord:deleteRowsRegulation,args:{index:index}}))
          },
          rowActionEditRegulation:(EditRegulationDetails , index) =>{
            dispatch({type:'EDIT_REGULATION_DETAILS', EditRegulationDetails , index});
          }, 
          okEditRegulationButton: (EditRegulationDetails,applicableRegulation) =>{
            dispatch(asyncDispatch(editRowsRegulation)({EditRegulationDetails,applicableRegulation})).then((response)=>{
              dispatch(asyncDispatch(onlistConsignment)({mode:'LIST'})).then((response)=>{ 
              dispatch({type:constant.CLOSE_BUTTON})
            });
          });
          },
          onSaveSecurityStatus: () => {
            dispatch(asyncDispatch(onSaveSecurityStatus)({ action: 'LIST' })).then(() => {
                dispatch(asyncDispatch(onlistConsignment)({ action: 'LIST' })).then((response)=>{ 
                  dispatch({type:constant.CLOSE_BUTTON})
                });
            })
          }
    }
}

export default connectContainer(mapStateToProps, mapDispatchToProps)(DetailsPanel);
