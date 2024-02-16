import React, { Component, Fragment } from 'react';
import AwbDetailsTable from './AwbDetailsTable.jsx';
import NoDataPanel from './NoDataPanel.jsx';
import LoadPlanDetailsTable from './LoadPlanDetailsTable.jsx';
import NoDataPanelLoadPlan from './NoDataPanelLoadPlan.jsx';
import NoDataPanelManifest from './NoDataPanelManifest.jsx';
import ManifestDetailsTable from './ManifestDetailsTable.jsx';

export default class DetailsPanel extends Component {

    render() {

        return (
            (this.props.activeTab==='BookingView')?
                (this.props.clearFlag === true) ?
                    <div class="card animated fadeInUp">
                        <NoDataPanel/>
                    </div>
                    :
                    <div class="card animated fadeInUp">
                        <AwbDetailsTable awbDetails={this.props.awbDetails} attachAwb={this.props.attachAwb}
                            getNewPage={this.props.onlistAwbDetails} oneTimeValues={this.props.oneTimeValues} 
                            saveSelectedAwbsIndex= {this.props.saveSelectedAwbsIndex} updateSortVariables={this.props.updateSortVariables}
                            onApplyAwbFilter = {this.props.onApplyAwbFilter} filter={this.props.filter} exportToExcel={this.props.exportToExcel}
                            onClearAwbFilter={this.props.onClearAwbFilter} />
                    </div>
              :( this.props.activeTab ==='LoadPlanView')  ?
              (this.props.clearFlag === true) ?
              <div class="card animated fadeInUp">
                  <NoDataPanelLoadPlan/>
              </div>
              :
              <div class="card animated fadeInUp">
              <LoadPlanDetailsTable loadPlanBookingDetails={this.props.loadPlanBookingDetails} getNewPage={this.props.onlistLoadPlanDetails}
                exportToExcel={this.props.LoadPlanExportToExcel} updateSortVariables={this.props.updateSortVariables} onApplyLoadPlanFilter={this.props.onApplyLoadPlanFilter}
                onClearLoadPlanFilter={this.props.onClearLoadPlanFilter} loadPlanFilter={this.props.loadPlanFilter}
                initialFlightValues={this.props.initialFlightValues} onClearLoadPlanFilter={this.props.onClearLoadPlanFilter} saveSelectedLoadPlanAwbsIndex={this.props.saveSelectedLoadPlanAwbsIndex}/>
              </div>
              :( this.props.activeTab ==='ManifestView')  ?
              (this.props.clearFlag === true) ?
              <div class="card animated fadeInUp">
                  <NoDataPanelManifest/>
              </div>
              :
              <div class="card animated fadeInUp">
              <ManifestDetailsTable manifestBookingDetails={this.props.manifestBookingDetails} getNewPage={this.props.onlistManifestDetails}
                exportToExcel={this.props.manifestExportToExcel} updateSortVariables={this.props.updateSortVariables} onApplyManifestFilter={this.props.onApplyManifestFilter}
                onClearManifestFilter={this.props.onClearManifestFilter} manifestFilter={this.props.manifestFilter}
                initialFlightValues={this.props.initialFlightValues}  saveSelectedManifestAwbsIndex={this.props.saveSelectedManifestAwbsIndex}/>
              </div>
              :''  
        )
    }
}