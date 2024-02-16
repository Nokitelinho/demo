import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import DeviationListPanel from '../panels/carditlyinglistpanel/DeviationListPanel.jsx'
import { applyDeviationListFilter, onClickDeviationLlist, onClearFilter } from '../../actions/deviationlistaction';
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { reset } from 'redux-form';
import * as constant from '../../constants/constants';

const mapStateToProps = (state) => {
    return {
        oneTimeValues: state.commonReducer.oneTimeValues,
        deviationlistMailbags: state.deviationListReducer.deviationlistMailbags,
        deviationGroupMailbags: state.deviationListReducer.deviationGroupMailbags,
        deviationSummary: state.deviationListReducer.deviationSummary,
        activeDeviationListTab: state.deviationListReducer.activeDeviationListTab,
        initialValues: {
            ...state.deviationListReducer.filterValues
        },
        filterValues: state.deviationListReducer.filterValues,
        deviationListFilter: state.form.deviationListFilter,
    }
}
const mapDispatchToProps = (dispatch) => {
    return {
        changeDeviationListTab: (currentTab) => {
            dispatch({ type: constant.CHANGE_DEVIATION_TAB, data: currentTab })
            dispatch(asyncDispatch(applyDeviationListFilter)({ tabChange: true }))

        },
        onApplyFilter: (value) => {
            if (!value) {
                //dispatch({ type: constant.DEVIATION_FILTER_APPLIED })
                dispatch(asyncDispatch(applyDeviationListFilter)())
            }
            else {
                dispatch(asyncDispatch(applyDeviationListFilter)({ 'displayPage': value }))
            }
        },
        onClearFilter: () => {
            //dispatch({ type: constant.CLEAR_DEVIATIONLIST_FILTER })
            //dispatch(reset('deviationListFilter'));
            dispatch(dispatchAction(onClearFilter)())
        },

        onClickDeviationLlist: (data) => {
            dispatch(dispatchAction(onClickDeviationLlist)(data))
        }


    }
}
const DeviationListContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(DeviationListPanel)


export default DeviationListContainer