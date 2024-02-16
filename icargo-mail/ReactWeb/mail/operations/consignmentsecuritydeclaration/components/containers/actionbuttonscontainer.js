import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { dispatchAction, asyncDispatch, dispatchPrint } from 'icoreact/lib/ico/framework/component/common/actions';
import ActionButtonsPanel from '../panels/Actionbuttonspanel.jsx'
import { closeScreen, onPrint, onSave} from '../../actions/commonactions'
import { onlistConsignment } from '../../actions/filterpanelactions';

const mapStateToProps = (state) => {
    return {
        screenMode: state.filterpanelreducer.screenMode,
        filterList:state.filterpanelreducer.filterList,
        mstAddionalSecurityInfo:state.detailspanelreducer.mstAddionalSecurityInfo,
        newdetailspanelcontent:state.form.detailPanelForm?state.form.detailPanelForm:'',
    }
}
const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        close: () => {
            dispatch(dispatchAction(closeScreen)())
        },

        print: () =>{
            dispatch(dispatchPrint(onPrint)())
        },
        save: (newdetailspanelcontent) => {
            dispatch(asyncDispatch(onSave)({newdetailspanelcontent})).then((response)=>{
                dispatch(asyncDispatch(onlistConsignment)({mode:'LIST'})).then((response)=>{ 
                dispatch({type:constant.CLOSE_BUTTON})
            
        });
    });
        }
    }
}

const ActionButtonsContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(ActionButtonsPanel)
export default ActionButtonsContainer

