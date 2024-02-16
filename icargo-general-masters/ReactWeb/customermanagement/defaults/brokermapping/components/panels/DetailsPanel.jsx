import React, { Component } from "react";
import {
  Accordion,
  AccordionItem,
  AccordionItemTitle,
  AccordionItemBody
} from "react-accessible-accordion";
import { isEmpty } from "icoreact/lib/ico/framework/component/util/util";
import { IMessage} from 'icoreact/lib/ico/framework/html/elements'

import CustomerDetailsContainer from "../containers/accordion/customerdetailscontainer";
import BrokerDetailsContainer from "../containers/accordion/brokerdetailscontainer";
import ConsigneeDetailsContainer from "../containers/accordion/consigneedetailscontainer";

export default class DetailsPanel extends Component {
  constructor(props) {
    super(props);

  }
  render() {
    let cusType=this.props.selectedCustomertype
    return (
      <div className="d-flex section-overlap">
        <div className="section-panel animated fadeInUp">
          <div className="row">
            <div className="col-24">
              <>
                <Accordion>
                  <AccordionItem
                    className="accordion-title"
                    hideBodyClassName="accordionitem_active"
                    expanded={true}
                  >
                    <AccordionItemTitle>
                      <div className="accordion-head w-100">
                        <div className="d-flex justify-content-between">
                          <div className="d-flex align-items-center">
                            <i className="icon ico-orange-top v-middle"></i>
                            <h4>
                              <IMessage
                                msgkey="customermanagement.defaults.brokermapping.accordion.customerdetails"
                                defaultMessage="Customer Details"
                              />
                            </h4>
                          </div>
                          {/* <div className={this.props.disableCustomerFields ? "hidden" : ""}> */}
                          <div className="fs12">
                            Customer Type -{" "}
                            {!isEmpty(cusType)
                              ? cusType === "AG"
                                ? "Agent"
                                : cusType === "CC"
                                ? "CC Collector"
                                : cusType === "CU"
                                ? "Customer"
                                : cusType === "GHA"
                                ? "GHA"
                                : cusType === "GPA"
                                ? "GPA"
                                : cusType === "GSA"
                                ? "GSA"
                                : cusType === "TMP"
                                ? "Temporary Customer"
                                : cusType === "HC"
                                ? "Holding Company"
                                : cusType === "INC"
                                ? "Interline Carriers"
                                : cusType === "TC"
                                ? "Trucking Company"
                                : cusType === "TSP"
                                ? "Trucking Service Provider"
                                : ""
                              : "Temporary"}
                          </div>
                          {/* </div> */}
                        </div>
                      </div>
                    </AccordionItemTitle>
                    <AccordionItemBody>
                      <CustomerDetailsContainer />
                    </AccordionItemBody>
                  </AccordionItem>
                </Accordion>
              </>
              <>
                <div className="accordion mt-3 card">
                  <Accordion>
                    <AccordionItem
                      className="accordion-title"
                      hideBodyClassName="accordionitem_active"
                      expanded={true}
                    >
                      <AccordionItemTitle>
                        <div className="accordion-head">
                          <div className="d-flex align-items-center">
                            <i className="icon ico-orange-top v-middle"></i>
                            <h4>
                              <IMessage
                                msgkey="customermanagement.defaults.brokermapping.accordion.brokerdetails"
                                defaultMessage="My Broker Details"
                              />
                            </h4>
                          </div>
                        </div>
                      </AccordionItemTitle>
                      <AccordionItemBody>
                        <BrokerDetailsContainer />
                      </AccordionItemBody>
                    </AccordionItem>
                  </Accordion>
                </div>
              </>
              <>
                {/* <div className="accordion mt-3 card">  */}
                <Accordion className="accordion mt-3 card">
                  <AccordionItem
                    className="accordion-title"
                    hideBodyClassName="accordionitem_active"
                    // expanded={true}
                  >
                    <AccordionItemTitle>
                      <div className="accordion-head">
                        <div className="d-flex align-items-center">
                          <i className="icon ico-orange-top v-middle"></i>
                          <h4>
                            <IMessage
                              msgkey="customermanagement.defaults.brokermapping.accordion.consigneedetails"
                              defaultMessage="My Consignee Details"
                            />
                          </h4>
                        </div>
                      </div>
                    </AccordionItemTitle>
                    <AccordionItemBody>
                      <ConsigneeDetailsContainer />
                    </AccordionItemBody>
                  </AccordionItem>
                </Accordion>
                {/* </div>  */}
              </>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
