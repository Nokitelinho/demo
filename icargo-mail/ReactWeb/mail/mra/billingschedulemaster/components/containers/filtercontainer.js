import React from 'react';
import { dispatchAction, asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import FilterPanel from '../panels/FilterPanel.jsx';
import { billingschedulemasterScreenList, changeScreenMode, clearFilter } from '../../actions/filterpanelactions';
import { initialValues } from '../../selectors/filterpanelselector';

const mapStateToProps = (state) => {
  return {
    initialValues: initialValues(state),
    screenMode: state.filterpanelreducer.screenMode,
    oneTimeValues: state.commonReducer.oneTimeValues,
    filterValues:  state.filterpanelreducer.filterValues,
    filter:state.filterpanelreducer.summaryFilter?state.filterpanelreducer.summaryFilter.filter:{},
    formValues: state.form && state.form.billingFilter ? state.form.billingFilter.values : {},
    filterData: state.filterpanelreducer.summaryFilter?state.filterpanelreducer.summaryFilter.filter:{},
    parameterMap: state.detailspanelreducer.parameterMap


  }
};

const mapDispatchToProps = (dispatch) => {
  return {
    onChangeScreenMode: (newMode) => {
      dispatch(changeScreenMode(newMode));
    },
    onClearFilter: () => {
      dispatch(dispatchAction(clearFilter)());
    },

    onlistbillingdetails: () => {
      dispatch(asyncDispatch(billingschedulemasterScreenList)({ 'displayPage': '1', mode: 'LIST', 'pageSize':'30' }))
    },
  }
}

export default connectContainer(mapStateToProps, mapDispatchToProps)(FilterPanel);
