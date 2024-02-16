import React, { Component } from "react";
import {
  IPopover,
  IPopoverHeader,
  IPopoverBody,
} from "icoreact/lib/ico/framework/component/common/popover";
import { fetchAdditionalDetails } from "../../../actions/commonaction";
export default class FetchAdditionalDetailPopOverPanel extends Component {
  constructor(props) {
    super(props);
    this.state = {
      showAddInfo: false
    };
  }
  /**
   * function to toogle popover
   * @returns 
   */
  toogle = async () => {
    let AdditionalDetails = this.props.additionalDetails;
    let agentCode = this.props.customerCode;
    if (
      AdditionalDetails.length == 0 ||
      !AdditionalDetails.some((Obj) => Obj.customerCode.includes(agentCode))
    ) {
      this.props.showLoader();
      try{
        const response=await fetchAdditionalDetails(agentCode);
        if(response!=null &&response.results){
          this.props.showLoader();
          this.props.fetchAdditionalDetails(response,agentCode);
          this.setState({ showAddInfo: !this.state.showAddInfo }); 
        }else{
          this.props.showLoader();
          this.props.requestError(response)
        }
      }catch(error){
        return error
      }
      
    }else{
      this.setState({ showAddInfo: !this.state.showAddInfo }); 
    }
    
  };
  

  render() {
    let agentCode = this.props.customerCode;
    let AdditionalDetails = this.props.additionalDetails;
    return (
      <>
        <span
          className="ml-2"
          id={`addInfo-${this.props.rowIndex}`}   
          onClick={this.toogle}
        >
          {this.props.operationFlag != "I" ?
          <i href="#" className="ico-info-grey icon"></i>:""}
        </span>
        <IPopover
          isOpen={this.state.showAddInfo}
          target={`addInfo-${this.props.rowIndex}`}
          toggle={this.toogle}
          className="stn-popover"
          placement="right"
          innerClassName="origin_width"
        >
          <IPopoverHeader><div className="font-bold">Additional Details</div></IPopoverHeader>
          <IPopoverBody>
            <div className="overflow-auto origin_height">
              <div className="d-flex flex-column px-3 py-3">
                {AdditionalDetails != null &&
                  AdditionalDetails.length > 0 &&
                  AdditionalDetails.map((scc, index) => {
                    return scc.customerCode == agentCode ? (
                      <div key={index}>
                        {scc.additionalDetails != null
                          ? scc.additionalDetails
                              .split("\n")
                              .map((info, index) => {
                                return (
                                  <div className="d-flex mb-2" key={index}>
                                    <span>{info}</span>
                                  </div>
                                );
                              })
                          : "No additional details found"}
                      </div>
                    ) :"";
                  })}
              </div>
            </div>
          </IPopoverBody>
        </IPopover>
      </>
    );
  }
}