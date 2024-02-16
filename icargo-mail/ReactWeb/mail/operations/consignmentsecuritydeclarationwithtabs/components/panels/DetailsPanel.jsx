import React, { Component, Fragment } from 'react';
import ScreeningDetailsPanel from './ScreeningDetailsPanel.jsx';
import ConsignerDetailsPanel from './ConsignerDetailsPanel.jsx';
import SecurityExemptionPanel from './SecurityExemptionPanel.jsx';
import ApplicableRegulationInfoPanel from './ApplicableRegulationInfoPanel.jsx';
import AddScreeningButtonPanel from './AddScreeningButtonPanel.jsx';
import AddConsignerButtonPanel from './AddConsignerButtonPanel.jsx';
import { IButton} from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';


export default class DetailsPanel extends Component {
    constructor(props) {
        super(props);
 
    }

    changeTab = (currentTab) => {
        this.props.changeTab(currentTab)
    }

    addButtonClick = (activebutton,popupOpen) => {
        this.props.addButtonClick(activebutton, popupOpen)
    }

    render() {
        return (
            <Fragment>
              <div className="card p-0 inner-panel">
                <div className="card-body p-0 inner-panel">
                  <div className="tabs inner-panel">
                    <ul className="nav nav-tabs nav-tabs-large">
                      <li className={ this.props.activeTab==="ScreeningDetails"?"nav-item ui-tabs-active ui-state-active":"nav-item"}>
                        <a className="nav-link" onClick={() => this.changeTab("ScreeningDetails") }> 
                          Screening Details 
                        </a>
                      </li>
                      <li className={ this.props.activeTab==="ConsignerDetails"?"nav-item ui-tabs-active ui-state-active":"nav-item"}>
                        <a className="nav-link" onClick={() => this.changeTab("ConsignerDetails")} >
                        Consigner Details
                        </a>
                      </li>
                      <li className={ this.props.activeTab==="SecurityExemption"?"nav-item ui-tabs-active ui-state-active":"nav-item"}>
                        <a className="nav-link" onClick={() => this.changeTab("SecurityExemption")}>
                        Security Exemption
                        </a>
                      </li>
                      <li className={ this.props.activeTab==="ApplicableRegulation"?"nav-item ui-tabs-active ui-state-active":"nav-item"}>
                        <a className="nav-link" onClick={() => this.changeTab("ApplicableRegulation")}>
                          Applicable Regulation Information
                        </a>
                      </li>
                    </ul>
                   
                        {this.props.activeTab === "ScreeningDetails" &&  
                        <div className="d-flex align-items-center justify-content-end border-bottom">
                            <div className="d-flex py-2 pr-2">
                          <IButton  className="btn-primary" id="add" bType="ADD" accesskey="A" onClick={() => this.addButtonClick("AddScreeningDetails", "true") } disabled={!this.props.filterList}>
                            Add New
                          </IButton>
                           </div>
                           </div>
                        }
                        {this.props.activeTab === "ConsignerDetails" && <div className="d-flex align-items-center justify-content-end border-bottom">
                            <div className="d-flex py-2 pr-2">
                          <IButton  className="btn-primary" id="add" bType="ADD" accesskey="A" onClick={() => this.addButtonClick("AddConsignerDetails", "true") } disabled={!this.props.filterList}>
                            Add New
                          </IButton>
                          </div>
                           </div>
                        }
                     
                      {this.props.activeTab === "ScreeningDetails" && (<ScreeningDetailsPanel ScreeningDetails={this.props.ScreeningDetails}  concatenatedScreeningDetails={this.props.concatenatedScreeningDetails} rowActionDelete={this.props.rowActionDelete} 
                      rowActionEdit={this.props.rowActionEdit} FullPiecesFlag={this.props.FullPiecesFlag} okEditButton ={this.props.okEditButton}/> )}
                      {this.props.activeTab === "ConsignerDetails" && (<ConsignerDetailsPanel ConsignerDetails={this.props.ConsignerDetails} concatenatedConsignerDetails={this.props.concatenatedConsignerDetails}/>)}
                      {this.props.activeTab === "SecurityExemption" && (<SecurityExemptionPanel initialValues={this.props.securityExemption?this.props.securityExemption:this.props.ExemptionForm.values} isSaved={this.props.isSaved} securityExemption={this.props.securityExemption}/>)}
                      {this.props.activeTab === "ApplicableRegulation" && (<ApplicableRegulationInfoPanel initialValues={this.props.initialValues} oneTimeValues={this.props.oneTimeValues} applicableRegTransportDirection= {this.props.applicableRegTransportDirection}
                      applicableRegBorderAgencyAuthority={this.props.applicableRegBorderAgencyAuthority} applicableRegReferenceID ={this.props.applicableRegReferenceID}
                      applicableRegFlag = {this.props.applicableRegFlag}/>)}


                      {/* {this.props.activeTab === "ApplicableRegulation" && (<ApplicableRegulationInfoPanel initialValues={(this.props.initialValues.applicableRegTransportDirection.length!==0|| this.props.initialValues.applicableRegBorderAgencyAuthority.length!==0 ||
                        this.props.initialValues.applicableRegReferenceID.length!==0 || this.props.initialValues.applicableRegFlag.length!==0)
                        ?this.props.initialValues:this.props.ApplicableInfoForm.values} oneTimeValues={this.props.oneTimeValues}/>)} */}

                       {/* {this.props.activeTab === "SecurityExemption" && (<SecurityExemptionPanel initialValues={this.props.securityExemption} />)}
                      {this.props.activeTab === "ApplicableRegulation" && (<ApplicableRegulationInfoPanel initialValues={this.props.initialValues} oneTimeValues={this.props.oneTimeValues}/>)}
                      */}
                      {this.props.addbutton === "AddScreeningDetails" && (<AddScreeningButtonPanel show={this.props.addButtonShow} closeButton={this.props.closeButton} addButton={this.props.addbutton}
                          toggleFn={this.props.closeButton}  newScreeningDetails={this.props.newScreeningDetails.values} okButton={this.props.okButton} ScreeningDetails={this.props.ScreeningDetails} FullPiecesFlag={this.props.FullPiecesFlag} 
                          oneTimeValues ={this.props.oneTimeValues} initialValues={this.props.initialValues}/>
                      )}
                      {this.props.addbutton === "AddConsignerDetails" && (<AddConsignerButtonPanel show={this.props.addButtonShow} closeButton={this.props.closeButton} addButton={this.props.addbutton}
                          toggleFn={this.props.closeButton} newConsignerDetails={this.props.newConsignerDetails.values} okConsignerButton={this.props.okConsignerButton} ConsignerDetails={this.props.ConsignerDetails} oneTimeValues ={this.props.oneTimeValues}/>
                      )}

                      {/* Edit */}
                      <AddScreeningButtonPanel show={this.props.editPopup} initialValues={this.props.EditScreeningDetails} EditScreeningDetails={this.props.EditScreeningDetails} editedDetail={this.props.editedDetail}
                      okEditButton={this.props.okEditButton} rowIndex= {this.props.rowIndex} ScreeningDetails={this.props.ScreeningDetails} closeButton={this.props.closeButton} onclickPieces={this.props.onclickPieces} FullPiecesFlag={this.props.FullPiecesFlag}
                      oneTimeValues ={this.props.oneTimeValues}/>
                      
                  </div>
                </div>
              </div>
            </Fragment>
        )
    }
}

DetailsPanel.propTypes = {
    screenMode: PropTypes.string,
}