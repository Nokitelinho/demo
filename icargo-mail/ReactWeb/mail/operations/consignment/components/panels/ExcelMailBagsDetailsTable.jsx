import React, { Component } from 'react';
import {IHandsontable, ToUppercaseEditor} from 'icoreact/lib/ico/framework/component/common/handsontable';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';

export default class ExcelMailBagsDetailsTable extends Component {

    getCellProperties = (row,col,data)=>{
        if(row<this.props.excelmailBagsList.length ){
        return {readOnly:true}
        }
        else{
            return {readOnly:false}
        }
    }
    data= this.props.excelmailBagsList?this.props.excelmailBagsList:[];
    render() {
       
        return (
            <IHandsontable 
            tableId="mailBags"
            colHeaders={isSubGroupEnabled('TURKISH_SPECIFIC')?['Mail bag ID', 'Origin OE', 'Dest OE','Cat', 'Class','SC','Yr','DSN','RSN', 'Std Bags', 'HNI','RI', 'Wt', 'Declared Value', 'Currency', 'ULD No']:
            ['Mail bag ID', 'Origin OE', 'Dest OE','Cat', 'Class','SC','Yr','DSN','RSN', 'Std Bags', 'HNI','RI', 'Wt', 'ULD No','Origin','Destination','RDT','TSW','Service level']}
            columns={isSubGroupEnabled('TURKISH_SPECIFIC')?[ // defining format of the columns
                {
                    data: 'mailId',
                    type: 'text',
                    editor: ToUppercaseEditor
                },
                {
                    data: 'originExchangeOffice',
                    type: 'text',
                    editor: ToUppercaseEditor
                },
                {
                    data: 'destinationExchangeOffice',
                    type: 'text',
                    editor: ToUppercaseEditor
                },
                {
                    data: 'mailCategoryCode',
                    type: 'text',
                    editor: ToUppercaseEditor
                },
                {
                    data: 'mailClass',
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
                    data: 'dsn',
                    type: 'text'
                },
                {
                    data: 'receptacleSerialNumber',
                    type: 'text'
                },
                {
                    data: 'statedBags',
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
                    data: 'statedWeight',
                    type: 'text'
                },
                {
                    data: 'declaredValue',
                    type: 'text'
                },
                {
                    data: 'currencyCode',
                    type: 'text'
                },
                {
                    data: 'uldNumber',
                    type: 'text',
                    editor: ToUppercaseEditor
                }
            ]:[ // defining format of the columns
                {
                    data: 'mailId',
                    type: 'text',
                    editor: ToUppercaseEditor
                },
                {
                    data: 'originExchangeOffice',
                    type: 'text',
                    editor: ToUppercaseEditor
                },
                {
                    data: 'destinationExchangeOffice',
                    type: 'text',
                    editor: ToUppercaseEditor
                },
                {
                    data: 'mailCategoryCode',
                    type: 'text',
                    editor: ToUppercaseEditor
                },
                {
                    data: 'mailClass',
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
                    data: 'dsn',
                    type: 'text'
                },
                {
                    data: 'receptacleSerialNumber',
                    type: 'text'
                },
                {
                    data: 'statedBags',
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
                    data: 'statedWeight',
                    type: 'text'
                },
                {
                    data: 'uldNumber',
                    type: 'text',
                    editor: ToUppercaseEditor
                },
                {
                    data: 'mailOrigin',
                    type: 'text',
                    editor: ToUppercaseEditor
                
                },
                {
                    data: 'mailDestination',
                    type: 'text',
                    editor: ToUppercaseEditor
                
                },
                {
                    data: 'reqDeliveryTime',
                    type: 'text'
                    
                
                },
                {
                    data: 'transWindowEndTime',
                    type: 'text',
                    readOnly:true
                
                },
                {
                    data: 'mailServiceLevel',
                    type: 'text',
                
                },

            ]}                          
            minSpareRows={5}
            data={this.props.excelmailBagsList?this.props.excelmailBagsList:[]}
            height={300}
            cellProperties={this.getCellProperties}>                            
        </IHandsontable>
        )
    }


}
