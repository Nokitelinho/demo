import React, { PureComponent, Fragment } from 'react';
import { dispatchAction, asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import FilterPanel from '../panels/FilterPanel.jsx';
import { onlistConsignment, toggleFilter, onclearDetails } from '../../actions/filterpanelactions';
const mapStateToProps = (state) => {
      return {
            //initialValues: initialValues(state),
            initialValues:{ consignDocNo: state.filterpanelreducer.navigationFilter},
            screenMode: state.filterpanelreducer.screenMode,
            ScreeningDetails: state.filterpanelreducer.ScreeningDetails,
            ConsignerDetails: state.filterpanelreducer.ConsignerDetails,
            ConsignmentNumber: state.filterpanelreducer.consignmentNumber?state.filterpanelreducer.consignmentNumber:'',
            companyCode:state.filterpanelreducer.companyCode?state.filterpanelreducer.companyCode:'',
            paCode:state.filterpanelreducer.paCode?state.filterpanelreducer.paCode:'',
            consignmentDate:state.filterpanelreducer.consignmentDate?state.filterpanelreducer.consignmentDate.split(' ')[0]:'',
            consignmentOrigin:state.filterpanelreducer.consignmentOrigin?state.filterpanelreducer.consignmentOrigin:'',
            consigmentDest:state.filterpanelreducer.consigmentDest?state.filterpanelreducer.consigmentDest:'',
            securityStatusCode:state.filterpanelreducer.securityStatusCode?state.filterpanelreducer.securityStatusCode:'',
            filterList:state.filterpanelreducer.filterList,
            routingInConsignment:state.filterpanelreducer.routingInConsignmentVOs?state.filterpanelreducer.routingInConsignmentVOs:'',
            category:state.filterpanelreducer.category?state.filterpanelreducer.category:'',
            isSaved:state.filterpanelreducer.isSaved?state.filterpanelreducer.isSaved:''
      }

      
            
};

const mapDispatchToProps = (dispatch) => {
      return {
            onToggleFilter: (screenMode) => {
                  dispatch(toggleFilter(screenMode));
                },

            onlistConsignment:() => {
                  dispatch(asyncDispatch(onlistConsignment)({mode:'LIST'}))
            },   

            onclearDetails: () => {
                  dispatch(dispatchAction(onclearDetails)());
            },

            
      }
}

export default connectContainer(mapStateToProps, mapDispatchToProps)(FilterPanel);
