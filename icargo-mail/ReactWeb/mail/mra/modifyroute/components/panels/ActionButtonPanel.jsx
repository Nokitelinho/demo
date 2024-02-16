import React, { Fragment } from 'react';
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import {IToolTip} from 'icoreact/lib/ico/framework/component/common/tooltip';
import {Key,ComponentId} from '../../constants/constants.js'
import { IMessage } from 'icoreact/lib/ico/framework/html/elements'
import {closePopupWindow} from 'icoreact/lib/ico/framework/component/common/modal/popuputils';

class ActionButtonPanel extends React.Component {

       close=()=>{     
       if (window.parent.popupUtils){
       closePopupWindow(); 
         }
         else{
             closeScreen(); 
         }
       }

    render() {

        return (
          <Fragment>
                <div>
                    <IButton category="primary" id="execute"    onClick={this.props.execute} >
                    <IMessage msgkey={Key.EXECUTE_LBL} defaultMessage="Execute"/>    
                    </IButton>
                    <IToolTip value={Key.EXECUTE_TLTP} target={'execute'} placement='top'/>            
                    <IButton category="btn btn-default" id="close"   bType="CLOSE"  onClick={this.close}>
                    <IMessage msgkey={Key.CLOSE_LBL} defaultMessage="Close"/>
                    <IToolTip value={Key.CLOSE_TLTP} target={'close'} placement='top'/>
                   </IButton>

                </div>
              
            </Fragment>
        )
    }
}


export default ActionButtonPanel;