import React from 'react';
import {IHandsontable} from 'icoreact/lib/ico/framework/component/common/handsontable';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';

export default class NoDataExcelPanel extends React.Component {
    render() {
        return (
            <IHandsontable 
            tableId="mailBags"
            colHeaders={['Mail bag ID', 'Origin OE', 'Dest OE','Cat', 'Class','SC','Yr','DSN','RSN', 'Std Bags', 'HNI','RI', 'Wt', 'ULD No','Origin','Destination','RDT','TSW','Service level']}                          
            colHeaders={isSubGroupEnabled('TURKISH_SPECIFIC')?['Mail bag ID', 'Origin OE', 'Dest OE','Cat', 'Class','SC','Yr','DSN','RSN', 'Std Bags', 'HNI','RI', 'Wt', 'Declared Value', 'Currency', 'ULD No']:
            ['Mail bag ID', 'Origin OE', 'Dest OE','Cat', 'Class','SC','Yr','DSN','RSN', 'Std Bags', 'HNI','RI', 'Wt', 'ULD No']}
            minSpareRows={0}
            height={0}
            data={this.data}>                            
        </IHandsontable>
        )
    }
}