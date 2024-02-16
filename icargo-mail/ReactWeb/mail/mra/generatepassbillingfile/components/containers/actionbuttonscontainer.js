import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { dispatchAction, asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import ActionButtonsPanel from '../panels/Actionbuttonspanel.jsx'
import { onClose,onGenerate,onGenerateLog } from '../../actions/commonactions'

const mapStateToProps = (state) => {
    return {
        screenMode: state.filterpanelreducer.screenMode,
    }
}
const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        close: () => {
            dispatch(dispatchAction(onClose)())
        },
        generate: () => {
            dispatch(asyncDispatch(onGenerate)())
        },
        generatelog: () => {
            dispatch(dispatchAction(onGenerateLog)())
        }
    }
}

const ActionButtonsContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(ActionButtonsPanel)
export default ActionButtonsContainer

