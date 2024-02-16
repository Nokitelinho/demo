import React from 'react';
import { Row, Col } from 'reactstrap'
import { icFieldArray } from 'icoreact/lib/ico/framework/component/common/form';
import { ITextField, ISelect ,ICheckbox} from 'icoreact/lib/ico/framework/html/elements'
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date'
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { commonCompValidations } from 'icoreact/lib/ico/framework/component/common/event/compvalidations'

class ContainerDetails extends React.Component {

    render() {

        let contentId= [];
        contentId = this.props.oneTimeValues?this.props.oneTimeValues['mail.operations.contentid'].map((value) => ({ value: value.fieldValue, label: value.fieldValue })):'';

        try {
            const { row, index, addRow, deleteRow, numRows, form } = this.props;
           
            
           
        let isUldFormatBulk=false;
        if(this.props.initialValues.containerDetails.length>0){
         if(this.props.initialValues.containerDetails[index].type==='B'&&/[A-Z]{1,3}[0-9]{1,5}[A-Z]{2}/.test(this.props.initialValues.containerDetails[index].containerNumber)){
           isUldFormatBulk=true;
         }
         else if(this.props.initialValues.containerDetails[index].type==='U'){
            isUldFormatBulk=true;
         }
         if(this.props.initialValues.containerDetails[index].paBuiltFlag==='Y'){
            isUldFormatBulk=false;
         }
        }
            return (
                <Row>
                      <Col xs="3">
                    <div className="form-group">
                         <ICheckbox name={`${form}.${index}.barrowFlag`} disabled={this.props.uldTobarrow==='N'||!isUldFormatBulk} label="Barrow"  />
                    </div>
                    </Col>
                    <Col xs="5">
                        <div className="form-group">
                            <ITextField name={`${form}.${index}.containerNumber`}  disabled={true} componentId="CMP_MAIL_OPERATIONS_CONSIGNMENT_CARRIERCODE" uppercase="true" />
                        </div>
                    </Col>
                    <Col xs="3">
                        <div className="form-group">
                            <ICheckbox name={`${form}.${index}.isPaBuilt`} disabled={true} label="PA Built" />
                        </div>
                    </Col>
                   
                    <Col xs="4">
                        <div className="form-group">
                            <Lov name={`${form}.${index}.destination`} lovTitle="Destination" componentId='CMP_MAIL_OPERATIONS_MAILOUTBOUND_POU' uppercase={true} dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" />
                        </div>
                    </Col> 
                    <Col xs="4">
                        <div className="form-group">
                            <ITextField name={`${form}.${index}.actualWeight`}  onKeyPress={commonCompValidations.restrictDecimal} uppercase="true" />
                        </div>
                    </Col>
                    <Col xs="5">
                        <div className="form-group">
                            <ISelect name={`${form}.${index}.contentId`} options={contentId} disabled={this.props.multipleFlag} portal={true} componentId='CMP_MAIL_OPERATIONS_MAILOUTBOUND_CONTENT_ID' />
                        </div>
                    </Col>
                    
                </Row>
            )
        }
        catch (e) {
            console.log(e);
        }
    }
}
export default icFieldArray('containerDetails')(ContainerDetails)
