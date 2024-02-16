import React, { Component, Fragment } from 'react';
import DsnDetailsTable from './DsnDetailsTable.jsx';
import { Constants } from '../../constants/constants.js';

export default class DetailsPanel extends Component {

    render() {

        return (

            <Fragment>
                <div class="card animated fadeInUp">
                    <DsnDetailsTable dsnDetails={this.props.dsnDetails} attachAll={this.props.attachAll} detachAll={this.props.detachAll}
                        getNewPage={this.props.onlistCarditDsnEnquiry} onApplyDsnFilter={this.props.onApplyDsnFilter} 
                        onClearDsnFilter={this.props.onClearDsnFilter} exportToExcel={this.props.exportToExcel} selectedIndexes={this.props.selectedIndexes}
                        dsnFilterValues={this.props.dsnFilterValues} oneTimeValues={this.props.oneTimeValues} initialValues={this.props.initialValues}
                        updateSortVariables={this.props.updateSortVariables} saveSelectedIndexes={this.props.saveSelectedIndexes}
                        onRowOperation={this.props.onRowOperation} />
                    <div class="card-body table-summary">
                        <div class="row justify-content-end">
                            <div class="col-auto"><span>{Constants.TOT_BAGS} : </span><span class="font-weight-bold">{this.props.totalPieces ? this.props.totalPieces : 0}</span></div>
                            <div class="col-auto"><span>{Constants.TOT_WGT} : </span><span class="font-weight-bold">{this.props.totalWeight ? this.props.totalWeight : 0}</span></div>
                        </div>
                    </div>
                </div>
            </Fragment>

        )
    }
}