import React,{Component} from "react";
import {IButton,IMessage} from 'icoreact/lib/ico/framework/html/elements'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { FormGroup} from "reactstrap";
import icpopup, {  
    PopupFooter, PopupBody 
} from 'icoreact/lib/ico/framework/component/common/modal';
/**
 * Component to be called when deleting a poa
 * Deletion remarks is handled in this component
 */
class DeletionRemarksPopUpPanel extends Component{
    constructor(props) {
        super(props);
        this.state = {
            
          };
          this.handleChange = this.handleChange.bind(this);
    }
    handleChange(event) {
        this.setState({ editedRemark: event.target.value });
      }
    onOk = (id) => {
    let args = {
        newRemark: this.state.editedRemark,
        ident:id
    };
    this.props.onOk(args);
    
    }
    render(){
		
        return (
          <>
            <PopupBody>
              <div className="d-flex flex-column card pad-md">
                <FormGroup className="form-group">
                  <textarea
                    className="form-control"
                    name="deletionRemarks"
                    cols="30"
                    rows="10"
                    onChange={this.handleChange}
                  ></textarea>
                </FormGroup>
              </div>
            </PopupBody>
            <PopupFooter>
              <IButton
                category="primary"
                accesskey=""
                bType=""
                componentId=""
                onClick={() => this.onOk(this.props.id)}
              >
                <IMessage
                  msgkey="customermanagement.defaults.brokermapping.ok"
                  defaultMessage="OK"
                />
              </IButton>
              <IButton
                className="btn btn-default"
                accesskey=""
                bType=""
                componentId=""
                onClick={() => this.props.onClose()}
              >
                <IMessage
                  msgkey="customermanagement.defaults.brokermapping.close"
                  defaultMessage="Close"
                />
              </IButton>
            </PopupFooter>
          </>
        );
    }
}
const aeletionRemarksPopUpPanel = wrapForm('deletionremarkform')(DeletionRemarksPopUpPanel);
export default icpopup((aeletionRemarksPopUpPanel), { title: 'ADD Remarks'});