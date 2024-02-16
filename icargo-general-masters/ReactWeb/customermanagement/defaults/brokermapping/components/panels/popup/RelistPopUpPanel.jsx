import React,{Component} from "react";
import {IButton,IMessage} from 'icoreact/lib/ico/framework/html/elements'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import icpopup, { 
     PopupFooter, PopupBody 
} from 'icoreact/lib/ico/framework/component/common/modal';
/**
 * Component gets called when, Successfully created a temporary customer
 */
class RelistPopUpPanel extends Component{
    constructor(props) {
        super(props);

    }
    render(){
		return (
      <>
        <PopupBody>
          <div className="p-3 d-flex flex-column">
            <div className=" mb-2">
              <span>
                Details saved successfully, Do you want to relist?
              </span>
            </div>
          </div>
        </PopupBody>
        <PopupFooter>
          <IButton category="primary" onClick={() => this.props.onYes()}>
            <IMessage
              msgkey="customermanagement.defaults.brokermapping.Yes"
              defaultMessage="Yes"
            />
          </IButton>
          <IButton category="default" onClick={() => this.props.onClose()}>
            <IMessage
              msgkey="customermanagement.defaults.brokermapping.No"
              defaultMessage="No"
            />
          </IButton>
        </PopupFooter>
      </>
    );
    }
}
const relistPopUpPanel = wrapForm('adddetailsform')(RelistPopUpPanel);
export default icpopup((relistPopUpPanel), { title: 'Warning'});