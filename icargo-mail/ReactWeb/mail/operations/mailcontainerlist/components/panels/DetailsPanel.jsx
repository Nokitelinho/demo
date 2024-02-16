import React, { Component } from 'react';
import MailContainerDetailsTable from './MailContainerDetailsTable.jsx';
import NoDataPanel from './NoDataPanel.jsx';



export default class DetailsPanel extends Component {
    
    constructor(props) {
        super(props);
        
    }



        

    render() {
        return (
            (this.props.screenMode === 'initial' ) ?
            <div className="card">
                <div className="card-body p-0">
                    <NoDataPanel />
                </div>
            </div>:
          
            <div className="card container-details-list">
           
                    <MailContainerDetailsTable getNewPage={this.props.onlistContainerDetails} assignedTo={this.props.assignedTo}  oneTimeValues={this.props.oneTimeValues} 
                             initialValues={this.props.initialValues}  containerDetails={this.props.containerDetails} updateSortVariables={this.props.updateSortVariables} 
                             saveSelectedContainersIndex={this.props.saveSelectedContainersIndex}
                            />
                            </div>
                        
        );
                                   
             }
            }

    



