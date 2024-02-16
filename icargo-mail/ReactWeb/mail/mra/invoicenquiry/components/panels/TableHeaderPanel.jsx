import React from 'react';
import { Row, Col } from "reactstrap";
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import { IPopover, IPopoverHeader, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';
import { IDropdownMenu, IDropdownToggle, IDropdownItem, IDropdown } from 'icoreact/lib/ico/framework/component/common/dropdown';
const Aux =(props) =>props.children;
export default class TableHeaderPanel extends React.Component {
    constructor(props) {
        super(props);
        

    }


    moveToAction = (status) => {
        let actionName = status;
        let data = {actionName:actionName}
       // let index = event.target.dataset.index
        this.props.moveToAction(data);
      //  event.stopPropagation();
       // event.nativeEvent.stopImmediatePropagation();
    }

    render() {
        return (
            <Aux>
               <Row>
                   <Col><h4>Mail Bag Details</h4></Col>
                    <div className="card-header-btns pad-t-2xs">


                        <IDropdown disabled={this.props.filterValues ? this.props.filterValues.fromScreen==='MRA078':false} dropup={false}  >
                            <IDropdownToggle>
                               
                                    <i className="icon ico-folder mar-r-2xs" ></i>   Move to
                                                     <i className="icon ico-updown-dark ico-updown-dark-down mar-b-3xs mar-x-2xs"></i>
                                
                            </IDropdownToggle>

                            <IDropdownMenu>
                                <IDropdownItem className="fs14" onClick={() =>this.moveToAction('CLMZROPAY')}>Zero Pay (MSX) </IDropdownItem>
                                <IDropdownItem className="fs14" onClick={() =>this.moveToAction('CLMNOTINV')}>Not in  INVOIC(NPR)</IDropdownItem>
                                <IDropdownItem className="fs14" onClick={() =>this.moveToAction('CLMNOINC')}>Incentive Not Paid</IDropdownItem>
                                <IDropdownItem className="fs14" onClick={() =>this.moveToAction('CLMRATDIF')}>Rate Difference(RVX)</IDropdownItem>
                                <IDropdownItem className="fs14" onClick={() =>this.moveToAction('CLMWGTDIF')}>Weight Difference(WXX)</IDropdownItem>


                            </IDropdownMenu>
                        </IDropdown>



                    </div>
                       
                      </Row> 





                {/*{
    this.state.openPopover &&
    <IPopover isOpen={this.state.openPopover}  target={'movetobutton'} toggle={this.onClosePopUp} className="icpopover"> >
      <IPopoverHeader>
         
        </IPopoverHeader>
        <IPopoverBody>
            <div style={{ "width": "150px" }} className="queue-item">
                
               
            </div>
        </IPopoverBody>
    </IPopover>
}*/}

            </Aux>
        );
    }
}
