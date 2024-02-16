import React, { Fragment } from 'react';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { IHandsontable,ToUppercaseEditor } from 'icoreact/lib/ico/framework/component/common/handsontable'
import AddMailbagTabPanel from './AddMailbagTabPanel.jsx'
import AddMailbagTable from './AddMailbagTable.jsx'
import PropTypes from 'prop-types';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';

class AddMailBag extends React.PureComponent {
    constructor(props) {
        super(props);
        this.rowindex = '';
        //this.onSavemailbagDetails=this.onSavemailbagDetails.bind(this);
    }
    onSavemailbagDetails = () => {
        this.props.onSavemailbagDetails(this.props.newMailbags,this.props.activeMailbagAddTab)
    }
    onDeleteRow=() =>{
        this.props.onDeleteRow(this.rowindex);
    }

    setRowIndex = (index) => {
        this.rowindex = index;
    }
    reassignMailbags=() =>{
        this.props.reassignExistingMailbags();
    }
    reassignMailbagsCancel=() =>{
        this.props.closeAddMailbagPopup();
    }
   

    data= this.props.newMailbags&&this.props.newMailbags.length>0?this.props.newMailbags.map((value) => ({ ...value,mailbagWeight:value.mailbagWeight&&value.mailbagWeight.roundedDisplayValue})) :[];
    render() {

        return (
            <Fragment>
                <PopupBody>
                    <div className="card border-0 test">
                        <AddMailbagTabPanel currentTime={this.props.currentTime} currentDate={this.props.currentDate} defaultWeightUnit={this.props.defaultWeightUnit} previousRowWeight={this.props.previousRowWeight} addModifyFlag={this.props.addModifyFlag} activeMailbagAddTab={this.props.activeMailbagAddTab} changeAddMailbagTab={this.props.changeAddMailbagTab} addRow={this.props.addRow} onDeleteRow={this.onDeleteRow} onClickImportPopup={this.props.onClickImportPopup} onCloseImport={this.props.onCloseImport} showImportPopup={this.props.showImportPopup} onImportMailbags={this.props.onImportMailbags} newMailbags={this.props.newMailbags} setRowIndex={this.props.setRowIndex} rowindex={this.props.rowindex} existingMailbagPresent={this.props.existingMailbagPresent} disableForModify={this.props.disableForModify} containerJnyID={this.props.containerJnyID}/>
                        {
                            this.props.activeMailbagAddTab === 'EXCEL_VIEW' &&
                            <div  style={{'height':'450px'}}>
                            <IHandsontable 
                            tableId="mailBags"
                            colHeaders={isSubGroupEnabled('TURKISH_SPECIFIC')?['Mail bag ID', 'Origin OE', 'Dest OE','Cat','SC','Yr','DSN','RSN','HNI','RI', 'Wt', 'Vol','Scanned Date','Scanned Time','Carrier','Arrival Seal Number','Company Code','Damage Flag','Remarks','Belly Cart ID']:
                            ['Mail bag ID','Orgin','Destination', 'Origin OE', 'Dest OE','Cat','SC','Yr','DSN','RSN','HNI','RI', 'Wt','Wt Unit(H/K/L)', 'Vol','Scanned Date','Scanned Time','Carrier','Arrival Seal Number','Damage Flag','Remarks','Belly Cart ID', 'PA Built ULD']}
                            columns={isSubGroupEnabled('TURKISH_SPECIFIC')?[ // defining format of the columns
                                {
                                    data: 'mailbagId',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                },
                                {
                                    data: 'ooe',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                },
                                {
                                    data: 'doe',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                },
                                {
                                    data: 'mailCategoryCode',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                },
                                {
                                    data: 'mailSubclass',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                },
                               
                                {
                                    data: 'year',
                                    type: 'text'
                                },
                                {
                                    data: 'despatchSerialNumber',
                                    type: 'text'
                                },
                                {
                                    data: 'receptacleSerialNumber',
                                    type: 'text'
                                },
                                {
                                    data: 'highestNumberedReceptacle',
                                    type: 'text'
                                },
                                {
                                    data: 'registeredOrInsuredIndicator',
                                    type: 'text'
                                },
                                {
                                    data: 'mailbagWeight',
                                    type: 'text'
                                },
                                {
                                    data: 'mailbagVolume',
                                    type: 'text'
                                },
                                {
                                    data: 'scannedDate',
                                    type: 'text',
                                    editor: ToUppercaseEditor,
                                    readOnly:isSubGroupEnabled('AA_SPECIFIC') ?true:false,
                                },
                                {
                                    data: 'scannedTime',
                                    type: 'text',
                                    editor: ToUppercaseEditor,
                                    readOnly:isSubGroupEnabled('AA_SPECIFIC') ?true:false
                                },
                                {
                                    data: 'carrier',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                },
                                {
                                    data: 'arrivalSealNumber',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                },
                                {
                                   data: 'mailCompanyCode',
                                   type: 'text',
                                   editor: ToUppercaseEditor
                               },
                                {
                                    data: 'damageFlag',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                },
                                {
                                    data: 'mailRemarks',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                },
                                {
                                    data: 'bellyCartId',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                }
                            ]:
                            [ // defining format of the columns
                                {
                                    data: 'mailbagId',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                },
                                {
                                    data: 'mailorigin',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                },
                                {
                                    data: 'mailDestination',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                },
                                {
                                    data: 'ooe',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                },
                                {
                                    data: 'doe',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                },
                                {
                                    data: 'mailCategoryCode',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                },
                                {
                                    data: 'mailSubclass',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                },
                               
                                {
                                    data: 'year',
                                    type: 'text'
                                },
                                {
                                    data: 'despatchSerialNumber',
                                    type: 'text'
                                },
                                {
                                    data: 'receptacleSerialNumber',
                                    type: 'text'
                                },
                                {
                                    data: 'highestNumberedReceptacle',
                                    type: 'text'
                                },
                                {
                                    data: 'registeredOrInsuredIndicator',
                                    type: 'text'
                                },
                                {
                                    data: 'mailbagWeight',
                                    type: 'text'
                                },
                                {
                                    data: 'displayUnit',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                },
                                {
                                    data: 'mailbagVolume',
                                    type: 'text'
                                },
                                {
                                    data: 'scannedDate',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                },
                                {
                                    data: 'scannedTime',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                },
                                {
                                    data: 'carrier',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                },
                                {
                                    data: 'arrivalSealNumber',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                },
                                {
                                    data: 'damageFlag',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                },
                                {
                                    data: 'mailRemarks',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                },
                                {
                                    data: 'bellyCartId',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                },
                                {
                                    data: 'acceptancePostalContainerNumber',
                                    type: 'text',
                                    editor: ToUppercaseEditor
                                }


                            ]}                          
                            minSpareRows={5}
                            data={this.data}
                            height={300}
                            cellProperties={this.getCellProperties}>                            
                        </IHandsontable>
                           </div>
                        }
                        {
                            this.props.activeMailbagAddTab === 'NORMAL_VIEW' &&
                            <AddMailbagTable initialValues={this.props.updatedMailbags} previousRowWeight={this.props.previousRowWeight} newMailbags={this.props.newMailbags} onSavemailbagDetails={this.onSavemailbagDetails} populateMailbagId={this.props.populateMailbagId} mailbagOneTimeValues={this.props.mailbagOneTimeValues} setRowIndex={this.setRowIndex} rowindex={this.rowindex}  existingMailbags={this.props.existingMailbags} existingMailbagPresent={this.props.existingMailbagPresent} disableForModify={this.props.disableForModify}/>
                        }
                    </div>
                </PopupBody>
                <PopupFooter>
                  { !this.props.existingMailbagPresent &&
                    <IButton category="primary" onClick={this.onSavemailbagDetails}>Save</IButton>
                  }
                  { !this.props.existingMailbagPresent &&
                    <IButton category="default" onClick={this.props.closeAddMailbagPopup}>Close</IButton>
                  }
                  {this.props.existingMailbagPresent &&
                    <IButton category="default" onClick={this.reassignMailbags}>Reassign</IButton>
                  }
                   {this.props.existingMailbagPresent &&
                    <IButton category="default" onClick={this.reassignMailbagsCancel}>Cancel</IButton>
                  }
                </PopupFooter>
            </Fragment>
        )
    }
}

AddMailBag.propTypes = {
    onSavemailbagDetails:PropTypes.func,
    newMailbags:PropTypes.object,
    activeMailbagAddTab:PropTypes.string,
    onDeleteRow:PropTypes.func,
    reassignExistingMailbags:PropTypes.func,
    reassignMailbagsCancel:PropTypes.func,
    currentTime:PropTypes.string,
    currentDate:PropTypes.string,
    defaultWeightUnit:PropTypes.object,
    previousRowWeight:PropTypes.object,
    addModifyFlag:PropTypes.bool,
    changeAddMailbagTab:PropTypes.func,
    addRow:PropTypes.func,
    onClickImportPopup:PropTypes.func,
    onCloseImport:PropTypes.func,
    showImportPopup:PropTypes.bool,
    containerJnyID:PropTypes.string,
    onImportMailbags:PropTypes.func,
    setRowIndex:PropTypes.func,
    rowindex:PropTypes.number,
    existingMailbagPresent:PropTypes.bool,
    updatedMailbags:PropTypes.object,
    populateMailbagId:PropTypes.func,
    mailbagOneTimeValues:PropTypes.object,
    existingMailbags:PropTypes.object,
    closeAddMailbagPopup:PropTypes.func,
}

export default icpopup(wrapForm('newMailbagsTable')(AddMailBag), { title: 'Add Mail bag', className: 'modal_990px' })
