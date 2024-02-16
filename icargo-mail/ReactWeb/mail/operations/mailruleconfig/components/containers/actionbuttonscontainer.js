import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { dispatchAction, asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import ActionButtonsPanel from '../panels/Actionbuttonspanel.jsx'
import { onClose ,saveMailRuleConfig } from '../../actions/commonactions'

const mapStateToProps = (state) => {
    return {
        screenMode: state.filterpanelreducer.screenMode,
        selectedMailRleConfigIndex: state.commonReducer.selectedMailRleConfigIndex
    }
}
const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        close: () => {
            dispatch(dispatchAction(onClose)())
        },
        saveMailRuleConfig: () => {
            dispatch(asyncDispatch(saveMailRuleConfig)())
        },
    }
}

const ActionButtonsContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(ActionButtonsPanel)
export default ActionButtonsContainer

