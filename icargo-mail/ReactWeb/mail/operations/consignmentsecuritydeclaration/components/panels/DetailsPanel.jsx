import React, { Component, Fragment } from 'react';
import ScreeningDetailsPanel from './ScreeningDetailsPanel.jsx';
import ConsignerDetailsPanel from './ConsignerDetailsPanel.jsx';
import SecurityExemptionPanel from './SecurityExemptionPanel.jsx';
import ApplicableRegulationPanel from './ApplicableRegulationPanel.jsx';
import AddScreeningButtonPanel from './AddScreeningButtonPanel.jsx';
import AddConsignerButtonPanel from './AddConsignerButtonPanel.jsx';
import AddExemptionButtonPanel from './AddExemptionButtonPanel.jsx';
import AddApplicableRegulationPanel from './AddApplicableRegulationPanel.jsx';
import { IButton,ITextField,ITextArea } from 'icoreact/lib/ico/framework/html/elements'
import PropTypes from 'prop-types';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { TimePicker } from 'icoreact/lib/ico/framework/component/common/time';
import { IAccordion, IAccordionItem,IAccordionItemTitle, IAccordionItemBody } from 'icoreact/lib/ico/framework/component/common/accordion';
import SecurityPopOver from './SecurityPopOver.jsx';


class DetailsPanel extends Component {
    constructor(props) {
        super(props);
        this.addConsignorDetails = this.addConsignorDetails.bind(this);
 
    }
    onsClick = () => {
        this.props.onsClick();
    }

    addButtonClick = (activebutton,popupOpen) => {
        this.props.addButtonClick(activebutton, popupOpen)
    }
     addButtonConsClick = (activebutton,popupOpen) => {
        this.props.addButtonConsClick(activebutton, popupOpen)
    }
    addButtonExemptionClick = (activebutton,popupOpen) => {
        this.props.addButtonExemptionClick(activebutton, popupOpen)
    }
    addButtonRegulaionClick = (activebutton,popupOpen) => {
        this.props.addButtonRegulaionClick(activebutton, popupOpen)
    }
	   addConsignorDetails(event) {
        let actionName = event.target.dataset.mode
        this.props.addConsignorDetails({ actionName: actionName });
    }
    render() {
        return (
            <>
           
				<div class="row flex-grow-1">
					<div class="col-14 flex-grow-1 d-flex flex-column">
                    <div class="card p-0">
							<div class="card-body">
                            <div class="row">
                                 <div class="col-5">
                                <label className="form-control-label">Issuer Name</label>
                                 <ITextField name="securityStatusParty" id="securityStatusParty" type="text"  uppercase={true}></ITextField>
                                </div>
                                <div class="col-6">
                                <label className="form-control-label">Date</label>
                                <DatePicker name="StatusDate" id="StatusDate" />
                                </div>
                                <div class="col-4">
                                <label className="form-control-label">Time</label>
                                 <TimePicker  name="timedetailspanel" id="timedetailspanel"></TimePicker>
                                </div>
                                <div class="col mt-4">
                                <label class="form-control-label">Security Status Code: </label>
                                <div className="form-control-data d-flex align-items-center">
                                       {this.props.securityStatusCode}
                                        <i onClick={this.onsClick} id={"resditimage_"} class="icon ico-pencil open_code mar-l-sm" data-target="webuiPopover47" ></i>
                                        {this.props.showPopoverscode && <SecurityPopOver showPopoverscode={this.props.showPopoverscode} oneTimeValues={this.props.oneTimeValues} onClose={this.props.onClose} onsClick={this.props.onsClick} onSaveSecurityStatus={this.props.onSaveSecurityStatus} />
                                        }
                                </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                <label class="form-control-label">Additional Security Information</label>
                                <ITextArea className="textarea" name="mstAddionalSecurityInfo" id="mstAddionalSecurityInfo" maxLength="35" style={{ "width":"700px","height":"40px" } }></ITextArea>
                                </div>
                            </div>
                        </div>
                    </div>
						<div class="mt-2 d-flex flex-column flex-grow-1">
							<div class="card p-0 inner-panel">
								<div class="card-header card-header-action">
									<div class="col">
										<div class="d-flex justify-content-between align-items-center pr-2">
											<h4>Screening Details</h4>
                                            <IButton data-mode="ADD_SCREENING_DETAILS" className="btn btn-default" id="add" bType="ADD" accesskey="A" onClick={() => this.addButtonClick("AddScreeningDetails", "true") } disabled={!this.props.filterList}>Add New</IButton>
   										</div>
									</div>
								</div>
								<div class="card-body p-0 inner-panel grid-b-margin" >
									<ScreeningDetailsPanel ScreeningDetails={this.props.ScreeningDetails} editScreeningDetails={this.props.editScreeningDetails} rowActionDelete={this.props.rowActionDelete} rowActionEdit={this.props.rowActionEdit} />
								</div>
							</div>
						</div>
				    </div>
                    <div class="col-10 pl-0 flex-grow-1">
                        <div class="accordion ui-accordion ui-widget ui-helper-reset" role="tablist">
                        
                        <IAccordion>
                            <IAccordionItem>
                                 <IAccordionItemTitle>
                               < div class="d-flex w-100">
                                    <div class="col">
                                    <div class="accordion-head d-flex align-items-center"><i className="icon ico-orange-top v-middle"></i>
                                            <h4>Agent/ Consignor Details</h4>
                                            <span class="add-btn-wrap">
                                            <IButton eventPropagation={false} data-mode="ADD_CONSIGNER_DETAILS" className="btn btn-default" id="add" bType="ADD" accesskey="A" onClick={() => this.addButtonConsClick("AddConsignerDetails", "true") } disabled={!this.props.filterList}>Add New</IButton>
                                        </span> 
            </div>
                                    </div>
                                </div>                                
                                </IAccordionItemTitle>
                               
                                <IAccordionItemBody >
                                <div class="card-body p-0 inner-panel grid-b-margin" >
                                    <ConsignerDetailsPanel showITable={this.props.showITable} ConsignerDetails={this.props.ConsignerDetails} EditConsignerDetails={this.props.EditConsignerDetails} initialValues={this.props.EditConsignerDetails} deleteConsignorDetails={this.props.deleteConsignorDetails} oneTimeValues={this.props.oneTimeValues} onsaveNewConsignorDetails={this.props.onsaveNewConsignorDetails} rowActionDeletecons={this.props.rowActionDeletecons} rowActionEditcons={this.props.rowActionEditcons} />
                                </div>
                                </IAccordionItemBody>
                            </IAccordionItem>
                                <IAccordionItem>
                                <IAccordionItemTitle>
                               <div class="d-flex w-100">
                                    <div class="col">
                                    <div class="accordion-head d-flex align-items-center"><i className="icon ico-orange-top v-middle"></i>
                                        <h4>Security Exemption</h4>
                                        <span class="add-btn-wrap">
                                        <IButton eventPropagation={false} data-mode="ADD_EXEMPTION_DETAILS" className="btn btn-default" id="add" bType="ADD" accesskey="A" onClick={() => this.addButtonExemptionClick("AddSecurityExemption", "true") } disabled={!this.props.filterList}>Add New</IButton>
                                        </span> 
                                        </div>
                                    </div>
                                </div>                                
                                </IAccordionItemTitle>
                                <IAccordionItemBody >
                                <div class="card-body p-0 inner-panel grid-b-margin" >
                                    <SecurityExemptionPanel showITable={this.props.showITable} securityExemption={this.props.securityExemption} EditsecurityExemption={this.props.EditsecurityExemption} initialValues={this.props.EditsecurityExemption} deletesecurityExemption={this.props.deletesecurityExemption} 
                                    oneTimeValues={this.props.oneTimeValues} onsaveNewsecurityExemption={this.props.onsaveNewsecurityExemption} rowActionDeleteExemption={this.props.rowActionDeleteExemption} rowActionEditExemption={this.props.rowActionEditExemption} />
                                </div>
                                </IAccordionItemBody>
                            </IAccordionItem>
                            <IAccordionItem>
                                <IAccordionItemTitle>
                               <div class="d-flex w-100">
                                    <div class="col">
                                    <div class="accordion-head d-flex align-items-center"><i className="icon ico-orange-top v-middle"></i>
                                    <h4>Applicable Regulation Information</h4>                                                                   
                                        <span class="add-btn-wrap">
                                        <IButton eventPropagation={false} data-mode="ADD_REGULATION_DETAILS" className="btn btn-default" id="add" bType="ADD" accesskey="A" onClick={() => this.addButtonRegulaionClick("AddApplicableRegulation", "true") } disabled={!this.props.filterList}>Add New</IButton>
                                        </span> 
                                        </div>
                                    </div>
                                </div>                                
                                </IAccordionItemTitle>
                                <IAccordionItemBody >
                                <div class="card-body p-0 inner-panel grid-b-margin" >
                                <ApplicableRegulationPanel showITable={this.props.showITable} applicableRegulation={this.props.applicableRegulation} EditRegulationDetails={this.props.EditRegulationDetails} initialValues={this.props.EditRegulationDetails} deletesecurityExemption={this.props.deletesecurityExemption} 
                                oneTimeValues={this.props.oneTimeValues} onsaveNewApplicableRegulation={this.props.onsaveNewApplicableRegulation} rowActionDeleteRegulation={this.props.rowActionDeleteRegulation} rowActionEditRegulation={this.props.rowActionEditRegulation} />
                                 </div>
                                </IAccordionItemBody>
                            </IAccordionItem>
                        </IAccordion>
                </div>
                </div>
            </div>
            {(this.props.addbutton === "AddScreeningDetails" )? (<AddScreeningButtonPanel show={this.props.addButtonShow} closeButton={this.props.closeButton} addButton={this.props.addbutton} okEditButton ={this.props.okEditButton}
                          toggleFn={this.props.closeButton}  newScreeningDetails={this.props.newScreeningDetails.values} okButton={this.props.okButton} ScreeningDetails={this.props.ScreeningDetails} FullPiecesFlag={this.props.FullPiecesFlag} 
                          oneTimeValues ={this.props.oneTimeValues} initialValues={this.props.initialValues}/>
                      ):( <AddScreeningButtonPanel show={this.props.editPopup} initialValues={this.props.EditScreeningDetails} EditScreeningDetails={this.props.EditScreeningDetails} editedDetail={this.props.editedDetail}
                      okEditButton={this.props.okEditButton} rowIndex= {this.props.rowIndex} ScreeningDetails={this.props.ScreeningDetails} closeButton={this.props.closeButton} onclickPieces={this.props.onclickPieces} FullPiecesFlag={this.props.FullPiecesFlag}
                      oneTimeValues ={this.props.oneTimeValues}/> )}
															  
            {(this.props.addbutton === "AddConsignerDetails" )? (<AddConsignerButtonPanel show={this.props.addButtonShow} closeButton={this.props.closeButton} addButton={this.props.addbutton}
                        toggleFn={this.props.closeButton} newConsignerDetails={this.props.newConsignerDetails.values} okConsignerButton={this.props.okConsignerButton} ConsignerDetails={this.props.ConsignerDetails} oneTimeValues ={this.props.oneTimeValues} 
                        initialValues={this.props.initialValues}/>
                    ):( <AddConsignerButtonPanel show={this.props.EditConsignerDetails.editPopupcons} initialValues={this.props.ConsignerDetails[this.props.rowIndex]} EditConsignerDetails={this.props.ConsignerDetails[this.props.rowIndex]}
                        editedDetailcons={this.props.editedDetailcons} okEditConsignerButton={this.props.okEditConsignerButton} rowIndex= {this.props.rowIndex} ConsignerDetails={this.props.ConsignerDetails} 
                        closeButton={this.props.closeButton}  oneTimeValues ={this.props.oneTimeValues}/> )}
            {(this.props.addbutton === "AddSecurityExemption" )? (<AddExemptionButtonPanel show={this.props.addButtonShow} closeButton={this.props.closeButton} addButton={this.props.addbutton}
                        toggleFn={this.props.closeButton} newSecurityExemption={this.props.newSecurityExemption} okExemptionButton={this.props.okExemptionButton} securityExemption={this.props.securityExemption} oneTimeValues ={this.props.oneTimeValues} 
                        initialValues={this.props.initialValues}/>
                    ):( <AddExemptionButtonPanel show={this.props.EditsecurityExemption.editPopupex} initialValues={this.props.securityExemption[this.props.rowIndex]} EditsecurityExemption={this.props.securityExemption[this.props.rowIndex]}
                        editedDetailexemption={this.props.editedDetailexemption} okEditsecurityExemption={this.props.okEditsecurityExemption} rowIndex= {this.props.rowIndex} securityExemption={this.props.securityExemption} 
                        closeButton={this.props.closeButton}  oneTimeValues ={this.props.oneTimeValues}/> )}
            {(this.props.addbutton === "AddApplicableRegulation" )? (<AddApplicableRegulationPanel  show={this.props.addButtonShow} closeButton={this.props.closeButton} addButton={this.props.addbutton}
                        toggleFn={this.props.closeButton} newApplicableRegulation={this.props.newApplicableRegulation} okRegulationButton={this.props.okRegulationButton} onToggleFilter={this.props.onToggleFilter} ApplicableRegulation={this.props.ApplicableRegulation} 
                        oneTimeValues ={this.props.oneTimeValues} initialValues={this.props.initialValues} onSelect={this.props.onSelect} showPopover={this.props.showPopover} onOKClick={this.props.onOKClick}/>
                    ):( <AddApplicableRegulationPanel  show={this.props.EditRegulationDetails.editPopupreg} initialValues={this.props.applicableRegulation[this.props.rowIndex]} EditRegulationDetails={this.props.EditRegulationDetails}
                        editedDetailRegulation={this.props.editedDetailRegulation} okEditRegulationButton ={this.props.okEditRegulationButton} rowIndex= {this.props.rowIndex} ApplicableRegulation={this.props.ApplicableRegulation} 
                        closeButton={this.props.closeButton}  oneTimeValues ={this.props.oneTimeValues}onSelect={this.props.onSelect} showPopover={this.props.showPopover} onOKClick={this.props.onOKClick} /> )}
       
	
    </>
                
        )
    }
}
export default wrapForm('detailPanelForm')(DetailsPanel);

DetailsPanel.propTypes = {
    screenMode: PropTypes.string,
}