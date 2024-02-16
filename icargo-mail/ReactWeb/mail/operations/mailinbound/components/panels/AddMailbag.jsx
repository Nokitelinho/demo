import React, {Fragment } from 'react';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import {  IButton } from 'icoreact/lib/ico/framework/html/elements'
import icpopup, {  PopupFooter, PopupBody} from 'icoreact/lib/ico/framework/component/common/modal';
import {IHandsontable,ToUppercaseEditor} from 'icoreact/lib/ico/framework/component/common/handsontable'
import PropTypes from 'prop-types'
import AddMailbagTabPanel from './AddMailbagTabPanel.jsx'
import AddMailbagTable from './AddMailbagTable.jsx';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';

class AddMailBag extends React.PureComponent {
    constructor(props) {        
        super(props);   

        this.rowindex = '';
    }
    addRow = () => {
        this.props.addRow()
    }
    onDeleteRow = () => {
        this.props.onDeleteRow(this.rowindex)
    }
    onSavemailbagDetails=()=> {
        this.props.onSavemailbagDetails(this.props.newMailbags,this.props.activeMailbagAddTab)
    }
    setRowIndex = (index) => {
        this.rowindex = index;
    }

    // data=this.props.newMailbags&&this.props.newMailbags.length>0?this.props.newMailbags.map(element=>( isSubGroupEnabled('TURKISH_SPECIFIC') ?{
    //         'mailbagId': element.mailBagId, 'ooe': element.ooe, 'doe': element.doe, 'mailCategoryCode': element.category,
    //         'mailSubclass': element.subClass, 'year': element.year, 'despatchSerialNumber': element.dsn, 
    //         'receptacleSerialNumber': element.rsn, 'highestNumberedReceptacle': element.highestNumberedReceptacle, 
    //         'registeredOrInsuredIndicator': element.ri, 'weight':element.weight&&element.weight.roundedDisplayValue?element.weight.roundedDisplayValue:0 ,
    //         'weightUnit': element.weightUnit,'volume': element.volume,'mailCompanyCode':element.mailCompanyCode,'scannedDate': element.scannedDate,'scannedTime': element.scannedTime , 
    //         'sealNumber': element.sealNo,'arrivalSealNumber':''
    //     }:{ 'mailbagId': element.mailBagId, 'ooe': element.ooe, 'doe': element.doe, 'mailCategoryCode': element.category,
    //     'mailSubclass': element.subClass, 'year': element.year, 'despatchSerialNumber': element.dsn, 
    //     'receptacleSerialNumber': element.rsn, 'highestNumberedReceptacle': element.highestNumberedReceptacle, 
    //     'registeredOrInsuredIndicator': element.ri, 'weight':element.weight&&element.weight.roundedDisplayValue?element.weight.roundedDisplayValue:0 ,
    //     'weightUnit': element.weightUnit,'volume': element.volume,'scannedDate': element.scannedDate,'scannedTime': element.scannedTime , 
    //     'sealNumber': element.sealNo,'arrivalSealNumber':''})):new Array( isSubGroupEnabled('TURKISH_SPECIFIC') ?{
    //         'mailbagId': '', 'ooe': '', 'doe': '', 'mailCategoryCode': '', 'mailSubclass': '', 'year': '', 'despatchSerialNumber': '', 'receptacleSerialNumber': '', 'highestNumberedReceptacle': '', 'registeredOrInsuredIndicator': '',
    //         'Weight':'','weightUnit': '' ,'volume':'','mailCompanyCode':'','scannedDate':'','scannedTime':'', 'sealNumber':'','arrivalSealNumber':''
    //     }:{ 'mailbagId': '', 'ooe': '', 'doe': '', 'mailCategoryCode': '', 'mailSubclass': '', 'year': '', 'despatchSerialNumber': '', 'receptacleSerialNumber': '', 'highestNumberedReceptacle': '', 'registeredOrInsuredIndicator': '',
    //     'Weight':'','weightUnit': '' ,'volume':'','scannedDate':'','scannedTime':'', 'sealNumber':'','arrivalSealNumber':''});
    
    
    data = [];
    render() {
          
        return (
             <Fragment>
                 <PopupBody>
                    <div className="card border-0" >
                        <AddMailbagTabPanel addRow={this.addRow} onDeleteRow={this.onDeleteRow} onSavemailbagDetails={this.onSavemailbagDetails} toggleFn={this.props.toggleFn} activeMailbagAddTab={this.props.activeMailbagAddTab} changeAddMailbagTab={this.props.changeAddMailbagTab} />
                    {(this.props.activeMailbagAddTab === 'EXCEL_VIEW') ? (
                       
                       <div style={{'height':'400px'}}>  
                        <IHandsontable 
                            tableId="addmailbagExcel"
                            colHeaders={isSubGroupEnabled('TURKISH_SPECIFIC') ?['Mail bag ID', 'Origin OE', 'Dest OE','Cat','SC','Yr','DSN','RSN','HNI','RI',
                                'Wt','Wt Unit(H/K/L)', 'Vol','Company Code', 'Scan Date/Time','Scan Time', 'Seal No', 'Arival Seal No']:['Mail bag ID', 'Orgin', 'Destination', 'Origin OE', 'Dest OE','Cat','SC','Yr','DSN','RSN','HNI','RI',
                                'Wt','Wt Unit(H/K/L)', 'Vol', 'Scan Date/Time','Scan Time', 'Seal No', 'Arival Seal No', 'Remarks']}
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
                                        data: 'weight',
                                        type: 'text'
                                    },   
                                    {   
                                        data: 'weightUnit',   
                                        type: 'text',
                                        editor: ToUppercaseEditor
                                     },
                                    {
                                        data: 'volume',
                                        type: 'text'
                                    },   
                                    {
                                        data: 'mailCompanyCode',
                                        type: 'text',
                                        editor: ToUppercaseEditor
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
                                        readOnly:isSubGroupEnabled('AA_SPECIFIC') ?true:false,
                                    },	 
    {
                                        data: 'sealNumber',
                                        type: 'text'
                                    },
                                   
                                    {
                                        data: 'arrivalSealNumber',
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
                                        data: 'weight',
                                        type: 'text'
                                    },   
                                    {   
                                        data: 'weightUnit',   
                                        type: 'text',
                                        editor: ToUppercaseEditor
                                     },
                                    {
                                        data: 'volume',
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
                                        readOnly:isSubGroupEnabled('AA_SPECIFIC') ?true:false,
                                    },	 
    {
                                        data: 'sealNumber',
                                        type: 'text'
                                    },
                                   
                                    {
                                        data: 'arrivalSealNumber',
                                        type: 'text',
                                        editor: ToUppercaseEditor
                                    },
                                    {
                                        data: 'remarks',
                                        type: 'text',
                                        editor: ToUppercaseEditor
                                    }
    
    
                                ]}    
                            
                            minSpareRows={4}                                                                                 
                            data={this.data}    
                            height={200}>
                        </IHandsontable>
                        </div>
                    ) : (
                                <AddMailbagTable setRowIndex={this.setRowIndex} show={true} rowindex={this.rowindex} initialValues={this.props.updatedMailbags}
                             
                                    newMailbags={this.props.newMailbags} onSavemailbagDetails={this.onSavemailbagDetails} previousWeightUnit={this.props.previousWeightUnit}
                                    populateMailbagId={this.props.populateMailbagId} mailbagOneTimeValues={this.props.mailbagOneTimeValues}/>
                           )}
                </div>
                           </PopupBody>
                <PopupFooter>
                    <IButton category="primary" bType="SAVE" accesskey="S" onClick={this.onSavemailbagDetails}>Save</IButton>
                    <IButton category="secondary" bType="CANCEL" accesskey="N" onClick={this.props.toggleFn}>Cancel</IButton>
                </PopupFooter>
                
                
                
                  
                          
             </Fragment>        
        )
    }
}
AddMailBag.propTypes = {
    onSavemailbagDetails: PropTypes.func,
    toggleFn: PropTypes.func,
    addRow:PropTypes.func,
    onDeleteRow:PropTypes.func,
    newMailbags:PropTypes.array,
    activeMailbagAddTab:PropTypes.string,
    changeAddMailbagTab:PropTypes.func,
    updatedMailbags:PropTypes.func,
    populateMailbagId:PropTypes.func,
    previousWeightUnit:PropTypes.string,
    mailbagOneTimeValues:PropTypes.array
}
export default icpopup(wrapForm('newMailbagsTable')(AddMailBag),{title:'Add Mail bag',className:'modal_990px'})
