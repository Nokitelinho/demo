import React, { PureComponent, Fragment } from 'react';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import DetailsPanel from '../panels/DetailsPanel.jsx';
import * as constant from '../../constants/constants';
import {deleteRows , editRows, addRows, addConsignerRows,clickFullPieces,changeTab} from '../../actions/detailspanelactions';
import { dispatchAction, asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';




const mapStateToProps = (state) => {
    return {
        screenMode: state.filterpanelreducer.screenMode,
        activeTab: state.detailspanelreducer.activeTab,
        addbutton: state.detailspanelreducer.addbutton,
        addButtonShow : state.detailspanelreducer.addButtonShow,
        filterList:state.filterpanelreducer.filterList,
        ScreeningDetails: state.filterpanelreducer.ScreeningDetails,
        ConsignerDetails: state.filterpanelreducer.ConsignerDetails,
        securityExemption:state.filterpanelreducer.securityExemption[0],
        securityStatusCode:{securityStatusCode:state.filterpanelreducer.securityStatusCode},
        ScreeningDetailsList: state.filterpanelreducer.ScreeningDetailsList,
        ExemptionForm: state.detailspanelreducer.ExemptionForm?state.detailspanelreducer.ExemptionForm:'',
        ApplicableInfoForm: state.detailspanelreducer.ApplicableInfoForm?state.detailspanelreducer.ApplicableInfoForm:'',
        isSaved:state.filterpanelreducer.isSaved?state.filterpanelreducer.isSaved:'',
        applicableRegTransportDirection:state.filterpanelreducer.applicableRegTransportDirection?state.filterpanelreducer.applicableRegTransportDirection:'',
        applicableRegBorderAgencyAuthority:state.filterpanelreducer.applicableRegBorderAgencyAuthority?state.filterpanelreducer.applicableRegBorderAgencyAuthority:'',
        applicableRegReferenceID:state.filterpanelreducer.applicableRegReferenceID?state.filterpanelreducer.applicableRegReferenceID:'',
        applicableRegFlag:state.filterpanelreducer.applicableRegFlag?state.filterpanelreducer.applicableRegFlag:'',

        // applicableRegMainForm = state.form.applicableregulationinfo?state.form.applicableregulationinfo:'',
        // securityExemptionMainForm = state.form.securityexemptionform?state.form.securityexemptionform:'',


        initialValues:{
        applicableRegTransportDirection:state.filterpanelreducer.applicableRegTransportDirection?state.filterpanelreducer.applicableRegTransportDirection:
        (state.detailspanelreducer.ApplicableInfoForm&&state.detailspanelreducer.ApplicableInfoForm.values&&state.detailspanelreducer.ApplicableInfoForm.values.applicableRegTransportDirection)?state.detailspanelreducer.ApplicableInfoForm.values.applicableRegTransportDirection:'',

        applicableRegBorderAgencyAuthority:state.filterpanelreducer.applicableRegBorderAgencyAuthority?state.filterpanelreducer.applicableRegBorderAgencyAuthority:
        (state.detailspanelreducer.ApplicableInfoForm&&state.detailspanelreducer.ApplicableInfoForm.values&&state.detailspanelreducer.ApplicableInfoForm.values.applicableRegBorderAgencyAuthority)?state.detailspanelreducer.ApplicableInfoForm.values.applicableRegBorderAgencyAuthority:'',

        applicableRegReferenceID:state.filterpanelreducer.applicableRegReferenceID?state.filterpanelreducer.applicableRegReferenceID:
        (state.detailspanelreducer.ApplicableInfoForm&&state.detailspanelreducer.ApplicableInfoForm.values&&state.detailspanelreducer.ApplicableInfoForm.values.applicableRegReferenceID)?state.detailspanelreducer.ApplicableInfoForm.values.applicableRegReferenceID:'',

        applicableRegFlag:state.filterpanelreducer.applicableRegFlag?state.filterpanelreducer.applicableRegFlag
        :(state.detailspanelreducer.ApplicableInfoForm&&state.detailspanelreducer.ApplicableInfoForm.values&&state.detailspanelreducer.ApplicableInfoForm.values.applicableRegFlag)?state.detailspanelreducer.ApplicableInfoForm.values.applicableRegFlag:'',

        screeningLocation:state.filterpanelreducer.screeningLocation?state.filterpanelreducer.screeningLocation:''

    },
        newScreeningDetails:state.form.addScreeingButtonPanelform?state.form.addScreeingButtonPanelform:'',
        concatenatedScreeningDetails:state.filterpanelreducer.ScreeningDetails?state.filterpanelreducer.ScreeningDetails:'',
        newConsignerDetails:state.form.addConsignerButtonPanelForm?state.form.addConsignerButtonPanelForm:'',
        concatenatedConsignerDetails:state.filterpanelreducer.ConsignerDetails?state.filterpanelreducer.ConsignerDetails:'',
        oneTimeValues:state.commonReducer.oneTimeValues?state.commonReducer.oneTimeValues:'',
        editPopup:state.detailspanelreducer.editPopup?state.detailspanelreducer.editPopup:'',
        editedDetail:state.form.addScreeingButtonPanelform?state.form.addScreeingButtonPanelform:'',
        rowIndex:state.detailspanelreducer.rowIndex?state.detailspanelreducer.rowIndex:'',
        //FullPiecesFlag: state.detailspanelreducer.FullPiecesFlag,

        EditScreeningDetails:state.detailspanelreducer.EditScreeningDetails?{
         // screeningLocation: state.detailspanelreducer.EditScreeningDetails.screeningLocation?state.detailspanelreducer.EditScreeningDetails.screeningLocation:'',
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

      }:''
    }
}
const mapDispatchToProps = (dispatch) => {
    return {
        changeTab: (currentTab) => {
            dispatch(dispatchAction(changeTab)({currentTab}));
          },

        // changeTab: (currentTab) => {
        //   dispatch({type:constant.CHANGE_TAB,currentTab: currentTab });
        // },

          addButtonClick: (activebutton, popupOpen) => {
            dispatch({type:constant.ADD_BUTTON,activebutton: activebutton , addButtonShow: popupOpen});
          } ,
          
          closeButton: () => {
            dispatch({type:constant.CLOSE_BUTTON});
          } ,

          okButton: (newScreeningDetails,ScreeningDetails) =>{
            dispatch(dispatchAction(addRows)({newScreeningDetails,ScreeningDetails}))
          },

          okConsignerButton: (newConsignerDetails,ConsignerDetails) =>{
            dispatch(dispatchAction(addConsignerRows)({newConsignerDetails,ConsignerDetails}))
          },

          rowActionDelete:( index, ScreeningDetails) =>{
            dispatch(dispatchAction(deleteRows)(index,ScreeningDetails))
          },

          rowActionEdit:(EditScreeningDetails , index) =>{
            dispatch({type:constant.EDIT_DETAILS, EditScreeningDetails , index});
          },

          okEditButton:(editedDetail , rowIndex, ScreeningDetails) =>{
           dispatch(dispatchAction(editRows)({editedDetail,rowIndex,ScreeningDetails}))
          },

          // onclickPieces:(event) =>{
          //   dispatch(dispatchAction(clickFullPieces)(event.target.checked));
          // }
    }
}

export default connectContainer(mapStateToProps, mapDispatchToProps)(DetailsPanel);
