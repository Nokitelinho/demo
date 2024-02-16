import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import MailrulelistPanel from '../panels/MailrulelistPanel.jsx';
import {
    onAddMailRule, saveSelectedMailRleConfigIndex, saveUnselectedMailRleConfigIndex,
    onDeleteMailRule,onmodiftMailRule,saveMultipleMailRleConfigIndex 
} from '../../actions/mailruletableactions';
import { dispatchAction, asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';

const mapStateToProps = (state) => {
    return {
        screenMode: state.filterpanelreducer.screenMode,
        selectedMailRleConfigIndex: state.commonReducer.selectedMailRleConfigIndex,
        mailRuleConfigList: state.filterpanelreducer.mailRuleConfigList
    }
}
const mapDispatchToProps = (dispatch) => {
    return {

        onAddMailRule: () => {
            dispatch(dispatchAction(onAddMailRule)());
        },

        saveSelectedMailRleConfigIndex: (index, action) => {
            if (action === 'SELECT') {
                dispatch(dispatchAction(saveSelectedMailRleConfigIndex)(index, action));
            } else {
                dispatch(dispatchAction(saveUnselectedMailRleConfigIndex)(index, action));
            }
        },

        onDeleteMailRule: () => {
            dispatch(dispatchAction(onDeleteMailRule)());
        },
        onmodiftMailRule: ()=>{
            dispatch(dispatchAction(onmodiftMailRule)());
        },
        saveMultipleMailRleConfigIndex: (index)=>{
            dispatch(dispatchAction(saveMultipleMailRleConfigIndex)(index));
        }
    }
}

export default connectContainer(mapStateToProps, mapDispatchToProps)(MailrulelistPanel);
