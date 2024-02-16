import React from 'react';
import { dispatchAction, asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import FilterPanel from '../panels/FilterPanel.jsx';
import { mailruleconfigScreenList, changeScreenMode, clearFilter } from '../../actions/filterpanelactions';
import { initialValues } from '../../selectors/filterpanelselector';

const mapStateToProps = (state) => {
      return {
            initialValues: initialValues(state),
            screenMode: state.filterpanelreducer.screenMode,
            oneTimeMap: state.commonReducer.oneTimeMap,
            screenFilter: state.filterpanelreducer.screenFilter,
            mailRuleListData: state.filterpanelreducer.mailRuleListData
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
                  dispatch(dispatchAction(mailruleconfigScreenList)({}));
            },
      }
}

export default connectContainer(mapStateToProps, mapDispatchToProps)(FilterPanel);
