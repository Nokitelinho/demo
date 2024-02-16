import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import ActionButtonPanel from '../panels/ActionButtonPanel.jsx';
import { asyncDispatch,dispatchAction, dispatchPrint } from 'icoreact/lib/ico/framework/component/common/actions';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { requestValidationError} from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import {  onCloseFunction,saveMailboxData,Active} from '../../actions/commonaction';
import {listMailboxDetails} from '../../actions/filteraction';

import { reset } from 'redux-form';

const mapStateToProps = (state) => {
  return {
    //listing features and activating
    isbuttondisabled:state.filterReducer.isbuttondisabled,
    mailboxStatus:state.filterReducer.mailboxStatus?state.filterReducer.mailboxStatus :'',
  }
}
const mapDispatchToProps = (dispatch) => {
  return {
    onCloseFunction:() => {
        dispatch(dispatchAction(onCloseFunction)());
    },
	 
    onSaveMailboxData: () => {

        dispatch(asyncDispatch(saveMailboxData)()).then((response)=>{
            if(!isEmpty(response)) {
                dispatch(reset('mailboxidFilter'));
                dispatch(reset('mailboxidPanel'));
                dispatch({ type: 'CLEAR_FILTER'});
              
            }
           
          });
      },
      onActive: () => {
            dispatch(asyncDispatch(Active)()).then((response)=>{
              if(!isEmpty(response)) {
                  dispatch(asyncDispatch(listMailboxDetails)({'displayPage':'1',mode:'LIST'}))
                
              }
             
            });
     
      },


  
  }
}
const ActionButtonContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(ActionButtonPanel)

export default ActionButtonContainer