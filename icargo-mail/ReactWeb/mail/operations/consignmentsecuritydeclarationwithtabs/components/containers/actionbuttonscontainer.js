import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { dispatchAction, asyncDispatch, dispatchPrint } from 'icoreact/lib/ico/framework/component/common/actions';
import ActionButtonsPanel from '../panels/Actionbuttonspanel.jsx'
import { onClose, onSaveDetails, onPrint} from '../../actions/commonactions'
import {onlistConsignment} from '../../actions/filterpanelactions';


const mapStateToProps = (state) => {
    return {
        screenMode: state.filterpanelreducer.screenMode,
        filterList:state.filterpanelreducer.filterList
    }
}
const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        close: () => {
            dispatch(dispatchAction(onClose)())
        },

        saveDetails: () =>{
            dispatch(asyncDispatch(onSaveDetails)()).then(()=>{
                dispatch(dispatchAction(onlistConsignment)()) 
              }) 
        },

        print: () =>{
            dispatch(dispatchPrint(onPrint)())
        }
    }
}

const ActionButtonsContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(ActionButtonsPanel)
export default ActionButtonsContainer

