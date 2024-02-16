import React, { Component } from 'react';
import MailbagDetailsTable from './MailbagDetailsTable.jsx';
import NoDataPanel from './NoDataPanel.jsx';
import {ICustomHeaderHolder } from 'icoreact/lib/ico/framework/component/common/grid'
export default class DetailsPanel extends Component {

    render() {
        //const mailbagsList = Object.values(this.props.mailbagslist);

        return (

            (this.props.screenMode === 'initial' || this.props.noData == true) ?
                <div className="card">
                    <div className="card-header">
                        <h4 className="col">Mail Bag Details</h4>
                    </div>
                    <div className="card-body p-0">
                        <NoDataPanel />
                    </div>
                </div>
                :
                <div className="card cardit-details-list">
                    <ICustomHeaderHolder tableId='mailbagtable' />
                     <div className="card-body p-0 flex-grow-1 d-flex">
                        <MailbagDetailsTable totalWeight={this.props.totalWeight} totalPieces={this.props.totalPieces} mailbags={this.props.mailbags} mailbagDetails={this.props.mailbagslist} getNewPage={this.props.onlistCarditEnquiry} onApplyFlightFilter={this.props.onApplyFlightFilter} onClearFlightFilter={this.props.onClearFlightFilter} exportToExcel={this.props.exportToExcel} tableFilter={this.props.tableFilter} oneTimeValues={this.props.oneTimeValues} initialValues={this.props.initialValues} updateSortVariables={this.props.updateSortVariables} saveSelectedMailbagsIndex={this.props.saveSelectedMailbagsIndex} carditAction={this.props.carditAction} carditMultipleSelectionAction={this.props.carditMultipleSelectionAction} selectedMailbagIndex={this.props.selectedMailbagIndex} onClickResdit={this.onClickResdit} onClickOK={this.props.onClickOK} navigatetoViewCondoc={this.props.navigatetoViewCondoc} saveResditMailbagData={this.props.saveResditMailbagData} displayError={this.props.displayError} form={this.props.form}bulkResditSend={this.props.bulkResditSend} selectedMailbagsId={this.props.selectedMailbagsId}/>
                    </div>
                    <div class="card-body text-right border-top fs14 flex-grow-0">
                    Total Pieces : <span className="font-weight-bold mar-r-2sm">{this.props.totalPieces?this.props.totalPieces:''}</span>TotalWeight : <span className="font-weight-bold">{this.props.totalWeight? this.props.totalWeight.roundedDisplayValue :''}</span>
                   </div>
                </div>

        )
    }
}