import React from 'react';
import {IButtonDropdown, IDropdownItem, IDropdownMenu } from 'icoreact/lib/ico/framework/component/common/dropdown';
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import AddContainer from '../containerpanel/AddContainer.jsx';
import PropTypes from 'prop-types';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';

class ContainerTableCustom extends React.PureComponent {
    constructor(props) {
        super(props);
    }
    containerMultipleAction = (event) => {
        let actionName = event.target.dataset.mode
        this.props.containerMultipleSelectionAction({ actionName: actionName });

    }
    multiButtonClick=()=>{
        if(this.props.flightActionsEnabled === 'false') {
            return;
        }
        this.props.containerMultipleSelectionAction({ actionName: 'REASSIGN' });
    }
    render() {
        let length = 0;
        length = this.props.selectedContainerIndex ? this.props.selectedContainerIndex.length : 0
        return (
           <div className="text-right">
                 <IButton onClick={this.props.addContainerClk} disabled={this.props.flightActionsEnabled === 'false'}><i className="icon ico-plus m-0 valign-middle"></i></IButton>
                   <AddContainer show={this.props.show} toggleFn={this.props.toggle} onListToModifyContainer={this.props.onListToModifyContainer} onSaveContainer={this.props.onSaveContainer} mailAcceptance={this.props.mailAcceptance} onUnMountAddContPopup={this.props.onUnMountAddContPopup} containerAction={this.props.containerAction} onclickPABuilt={this.props.onclickPABuilt}{...this.props} PABuiltChecked={this.props.PABuiltChecked} wareHouses={this.props.wareHouses} 
                   initialValues={this.props.initialValues} onLoadAddContPopup={this.props.onLoadAddContPopup} containerActionMode={this.props.containerActionMode} enableContainerSave={this.props.enableContainerSave} populateDestForBarrow={this.props.populateDestForBarrow} isBarrow={this.props.isBarrow}/>
                 {(length > 1) ?
                      <IButtonDropdown id="actions" split={true} 
                                text="Reassign To Carrier"
                                category="default"
                                className="ic-multibutton"
                                onClick={this.multiButtonClick}>
                                <IDropdownMenu>
                                    <IDropdownItem data-mode="REASSIGN_FLIGHT" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_REASSIGN_TO_FLIGHT" onClick={this.containerMultipleAction} disabled={this.props.flightActionsEnabled === 'false'}>Reassign To Flight</IDropdownItem>
                                    {this.props.flightCarrierflag === 'F' && <IDropdownItem data-mode="ATTACH_ROUTING" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_ATTACH_ROUTING" onClick={this.containerMultipleAction} disabled={this.props.flightActionsEnabled === 'false'}>Attach Routing</IDropdownItem>}
                                    <IDropdownItem data-mode="UNASSIGN" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_UNASSIGN_CONTAINER" onClick={this.containerMultipleAction} disabled={this.props.flightActionsEnabled === 'false'}>Unassign</IDropdownItem>
                                    <IDropdownItem data-mode="OFFLOAD" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_OFFLOAD_CONTAINER" onClick={this.containerMultipleAction} disabled={this.props.flightActionsEnabled === 'false'}>Offload</IDropdownItem>
                                    <IDropdownItem data-mode="TRANSFER" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_REASSIGN_TO_FLIGHT" onClick={this.containerMultipleAction} disabled={this.props.flightActionsEnabled === 'false'}>Transfer to Carrier</IDropdownItem>
                                    <IDropdownItem data-mode="TRANSFER_FLIGHT" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_REASSIGN_TO_FLIGHT" onClick={this.containerMultipleAction} disabled={this.props.flightActionsEnabled === 'false'}>Transfer To Flight</IDropdownItem>
                                    </IDropdownMenu>
                            </IButtonDropdown>
                       : null
                    }
      </div>
        )
    }
}
ContainerTableCustom.propTypes = {
    containerMultipleSelectionAction: PropTypes.func,
    selectedContainerIndex:PropTypes.array,
    addContainerClk: PropTypes.func,
    flightActionsEnabled:PropTypes.bool,
    show:PropTypes.bool,
    toggle:PropTypes.func,
    onListToModifyContainer:PropTypes.func,
    onSaveContainer:PropTypes.func,
    mailAcceptance:PropTypes.object,
    onUnMountAddContPopup:PropTypes.func,
    containerAction:PropTypes.func,
    onclickPABuilt:PropTypes.func,
    PABuiltChecked:PropTypes.bool,
    wareHouses:PropTypes.func,
    initialValues:PropTypes.object,
    onLoadAddContPopup:PropTypes.func,
    containerActionMode:PropTypes.func,
    enableContainerSave:PropTypes.bool,
    populateDestForBarrow:PropTypes.func,
}
export default ContainerTableCustom