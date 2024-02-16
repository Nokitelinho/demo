import React,{Component} from "react";
import {IButton,IMessage} from 'icoreact/lib/ico/framework/html/elements'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import icpopup, { 
    PopupFooter, PopupBody 
} from 'icoreact/lib/ico/framework/component/common/modal';
/**
 * Componenet to be called when deleting a customer
 */
class DeleteCustomerPopUpPanel extends Component{
    constructor(props) {
        super(props);

    }
    render(){
		return (
      <>
        <PopupBody>
          <div className="p-3 d-flex flex-column">
            <div className=" mb-2">
              <span>Do you want to delete this customer?</span>
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
const deleteCustomerPopUpPanel = wrapForm('adddetailsform')(DeleteCustomerPopUpPanel);
export default icpopup((deleteCustomerPopUpPanel), { title: 'Warning'});