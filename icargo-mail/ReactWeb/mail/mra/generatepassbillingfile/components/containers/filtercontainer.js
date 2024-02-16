import React from 'react';
import { dispatchAction, asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import FilterPanel from '../panels/FilterPanel.jsx';
import { generatepassbillingfileScreenList, changeScreenMode, clearFilter,populatePeriodNumberAndDate } from '../../actions/filterpanelactions';
import { initialValues } from '../../selectors/filterpanelselector';

const mapStateToProps = (state) => {
      return {
            initialValues: initialValues(state),
            screenMode: state.filterpanelreducer.screenMode,
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

            submitFilter: () => {
                  dispatch(asyncDispatch(generatepassbillingfileScreenList)({}));
            },
            populatePeriodNumberAndDate:(populateSource)=>{
                  dispatch(asyncDispatch(populatePeriodNumberAndDate)({populateSource}));
            }
      }
}

export default connectContainer(mapStateToProps, mapDispatchToProps)(FilterPanel);
