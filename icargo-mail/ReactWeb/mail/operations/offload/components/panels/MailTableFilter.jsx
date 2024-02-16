import React, { Fragment } from 'react';
import { ITextField, IMessage } from 'icoreact/lib/ico/framework/html/elements'
import { Row, Col, Container } from "reactstrap";
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';

class MailTableFilter extends React.PureComponent {
    constructor(props) {
        super(props);
       
    }


    render() {

      
        let containerType = [];
        if (!isEmpty(this.props.oneTimeValues)) {
            containerType = this.props.oneTimeValues['mailtracking.defaults.containertype'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }
        return (  <Fragment>
        
  
            <Row>
                <Col xs="10">
             <div className="form-group">
               <label className="form-control-label ">Mail Bag Id</label>
               <ITextField mode="edit" name="mailbagId" type="text" uppercase={true}></ITextField>
           </div>
           </Col>
                <Col xs="6">
             <div className="form-group">
               <label className="form-control-label ">Container No</label>
               <ITextField mode="edit" name="containerNo" type="text" uppercase={true} ></ITextField>
           </div>
           </Col>
                <Col xs="4">
             <div className="form-group">
                 <label className="form-control-label ">POU</label>
                 <ITextField mode="edit" name="pou" type="text" uppercase={true} ></ITextField>
             </div>
           </Col>
                <Col xs="4">
             <div className="form-group">
                 <label className="form-control-label ">Destination</label>  
                 <ITextField mode="edit" name="destination" type="text" uppercase={true}></ITextField>
             </div>
          </Col>
         
          
          
        </Row>
        </Fragment> )

    }
}
export default wrapForm('MailTableFilter')(MailTableFilter)