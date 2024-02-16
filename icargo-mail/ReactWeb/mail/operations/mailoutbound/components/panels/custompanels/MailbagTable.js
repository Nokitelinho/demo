import React from 'react';
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import {  IDropdownItem, IDropdownMenu, IButtonDropdown } from 'icoreact/lib/ico/framework/component/common/dropdown';
import PropTypes from 'prop-types';
import AddMailBag from '../mailbagspanel/AddMailbag.jsx';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';


class MailbagTable extends React.PureComponent {
    constructor(props) {
        super(props);

        this.state = {
            rowClkCount: 0,
            // show:false
        }
    }
    addMailbagClk = () => {
        this.props.onLoadAddMailbagPopup('ADD_MAILBAG');
    }

   
    toggleFlightPanel=()=> {
        this.props.toggleFlightPanel();
    }
      //on row specific mailbag action
      mailbagAction = (event) => {
        let actionName = event.target.dataset.mode
        let index = event.target.dataset.index
        if (actionName === 'RETURN') {
            this.props.mailbagAction({ actionName: actionName, index: index });
          
        } 
        if (actionName === 'DAMAGE_CAPTURE') {
            this.props.mailbagAction({ actionName: actionName, index: index });
          
        } 
        
        else if (actionName === 'CHANGE SCAN TIME') {
           // this.setState({
            //    showScanTime: true
            //});
            this.props.mailbagAction({ actionName: actionName, index: index });
            
        }
        
        else if (actionName === 'REASSIGN') {
           this.props.mailbagAction({ actionName: actionName, index: index });
           
           // this.props.mailbagAction({ actionName: actionName, index: index });
        }  else if (actionName === 'VIEW_DAMAGE') {
            this.setState({
                showDamage: true
            });
            this.props.mailbagAction({ actionName: actionName, index: index });
        } 
        else if (actionName === 'DELETE') {
            
            this.props.mailbagAction({ actionName: actionName, index: index });
        }
        else if (actionName === 'OFFLOAD') {
            const selectedIndex = this.props.selectedMailbagsIndex;
            const mailbags = [];
            for(let i=0;i<selectedIndex.length;i++){
                mailbags.push(this.props.mailbagslist[selectedIndex[i]])
            }
            this.props.navigateToOffload(mailbags);
        }
         
        else if (actionName === 'ATTACH_AWB') {
            this.props.mailbagAction({ actionName: actionName, index: index });
         }
        else if (actionName === 'TRANSFER') {
            this.props.mailbagAction({ actionName: actionName, index: index });
         }
        else if (actionName === 'DETACH_AWB') {
            
            this.props.detachAWB({ actionName: actionName, index: index });
        }
    }

    multiButtonClick=()=>{
        if(this.props.flightActionsEnabled === 'false') {
            return;
        }
        this.props.mailbagMultipleSelectionAction({ actionName: 'REASSIGN' });
    }
    render() {
         let length=0;
         length = this.props.selectedMailbagsIndex ? this.props.selectedMailbagsIndex.length : 0
        let masterDocumentNumberFlag ="Y";
		let selectedMailbagsIndex=this.props.selectedMailbagsIndex;
        if(this.props.activeMailbagTab=="MAIL_VIEW"&&length!=0){
        for(let i=0;i<selectedMailbagsIndex.length;i++){
             let masterDocumentNumber= this.props.mailbagslist[selectedMailbagsIndex[i]] && this.props.mailbagslist[selectedMailbagsIndex[i]].masterDocumentNumber;
                if(masterDocumentNumber==null){
                    masterDocumentNumberFlag="N";  
                       }
                       }
		}
        return (
       <div className="text-right">
                          <IButton onClick={this.addMailbagClk} disabled={this.props.flightActionsEnabled === 'false' || this.props.containersPresent }><i className="icon ico-plus m-0 valign-middle"></i></IButton>
                          <AddMailBag currentTime={this.props.currentTime} currentDate={this.props.currentDate} defaultWeightUnit={this.props.defaultWeightUnit} previousRowWeight={this.props.previousRowWeight} show={this.props.show} addModifyFlag={this.props.addModifyFlag}  onSavemailbagDetails={this.props.onSavemailbagDetails} activeMailbagAddTab={this.props.activeMailbagAddTab} changeAddMailbagTab={this.props.changeAddMailbagTab} addRow={this.props.addRow} onDeleteRow={this.props.onDeleteRow} resetRow={this.props.resetRow} newMailbags={this.props.newMailbags} updatedMailbags={this.props.updatedMailbags} importedMailbagDetails={this.props.importedMailbagDetails} populateMailbagId={this.props.populateMailbagId} mailbagOneTimeValues={this.props.mailbagOneTimeValues} onClickImportPopup={this.props.onClickImportPopup} onCloseImport={this.props.onCloseImport} showImportPopup={this.props.showImportPopup} containerJnyID={this.props.containerJnyID} onImportMailbags={this.props.onImportMailbags}existingMailbags={this.props.existingMailbags} existingMailbagPresent={this.props.existingMailbagPresent} reassignExistingMailbags={this.props.reassignExistingMailbags} reassignMailbagsCancel={this.props.reassignMailbagsCancel} closeAddMailbagPopup={this.props.closeAddMailbagPopup} disableForModify={this.props.disableForModify}/>
                          {(length > 1) ?
                        <IButtonDropdown id="actions" split={true}
                         data-mode="REASSIGN"
                         text="Reassign"
                         category="default"
                         className="ic-multibutton"
                         onClick={this.multiButtonClick}>
                                <IDropdownMenu right={true} >
                                    <IDropdownItem data-mode="DELETE" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_DELETE_MAILBAG" onClick={this.mailbagAction} disabled={this.props.flightActionsEnabled === 'false'}> Delete</IDropdownItem>
                                    {this.props.flightCarrierflag === 'F' && <IDropdownItem data-mode="RETURN" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_RETURN_MAILBAG" onClick={this.mailbagAction} disabled={this.props.flightActionsEnabled === 'false'}>Return</IDropdownItem>}
                                    {this.props.flightCarrierflag === 'F' &&<IDropdownItem data-mode="DAMAGE_CAPTURE" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_CAPTURE_DAMAGE" onClick={this.mailbagAction} disabled={this.props.flightActionsEnabled === 'false'}> Damage Capture </IDropdownItem>}
                                    {this.props.flightCarrierflag === 'F' &&<IDropdownItem data-mode="OFFLOAD" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_OFFLOAD_MAILBAG" onClick={this.mailbagAction} disabled={this.props.flightActionsEnabled === 'false'}>Offload</IDropdownItem>}
                                    {isSubGroupEnabled('AA_SPECIFIC')===false &&<IDropdownItem data-mode="CHANGE SCAN TIME" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_CHANGE_SCAN_TIME" onClick={this.mailbagAction} disabled={this.props.flightActionsEnabled === 'false'}>Change scan time</IDropdownItem>}
                                    {this.props.flightCarrierflag === 'F' &&<IDropdownItem data-mode="ATTACH_AWB" privilegeCheck={true}  onClick={this.mailbagAction} disabled={this.props.flightActionsEnabled === 'false'}> Attach AWB </IDropdownItem>}
                                    <IDropdownItem  data-mode="TRANSFER" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_REASSIGN_MAILBAG" onClick={this.mailbagAction} disabled={this.props.flightActionsEnabled === 'false'}> Transfer</IDropdownItem>
                                    <IDropdownItem  data-mode="DETACH_AWB" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_DETACH_AWB" onClick={this.mailbagAction} disabled={masterDocumentNumberFlag=="Y"?this.props.flightActionsEnabled === 'false':this.props.flightActionsEnabled === 'true'}>Detach AWB</IDropdownItem>
                                </IDropdownMenu>
                            </IButtonDropdown>
                             : null
                            }
                       
            </div>
        )
    }
}
MailbagTable.propTypes = {
    mailbagAction: PropTypes.func,
    flightCarrierflag: PropTypes.string,
    currentTime: PropTypes.string,
    selectedMailbagsIndex: PropTypes.number,
    mailbagMultipleSelectionAction: PropTypes.func,
    onLoadAddMailbagPopup: PropTypes.func,
    toggleFlightPanel: PropTypes.string,
    selectedFlights: PropTypes.number,
    flightActionsEnabled:PropTypes.string,
    currentDate: PropTypes.string,
    defaultWeightUnit:PropTypes.object,
    previousRowWeight:PropTypes.object,
    show:PropTypes.bool,
    addModifyFlag:PropTypes.bool,
    onSavemailbagDetails: PropTypes.func,
    activeMailbagAddTab:PropTypes.string,
    changeAddMailbagTab:PropTypes.func,
    addRow:PropTypes.func,
    onDeleteRow:PropTypes.func,
    resetRow:PropTypes.func,
    newMailbags:PropTypes.object,
    updatedMailbags:PropTypes.object,
    importedMailbagDetails:PropTypes.func,
    populateMailbagId:PropTypes.func,
    mailbagOneTimeValues:PropTypes.object,
    onClickImportPopup:PropTypes.func,
    onCloseImport:PropTypes.func,
    showImportPopup:PropTypes.bool,
    containerJnyID:PropTypes.string,
    onImportMailbags:PropTypes.func,
    existingMailbags:PropTypes.object,
    existingMailbagPresent:PropTypes.object,
    reassignExistingMailbags:PropTypes.func,
    reassignMailbagsCancel:PropTypes.func,
    closeAddMailbagPopup:PropTypes.func,
    containersPresent:PropTypes.bool
}
export default MailbagTable