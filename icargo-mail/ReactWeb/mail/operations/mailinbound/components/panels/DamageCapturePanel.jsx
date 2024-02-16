import React from 'react';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { IButton} from 'icoreact/lib/ico/framework/html/elements';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import DamageCapture from '../../../mailbagenquiry/components/panels/DamageData.jsx';
import PropTypes from 'prop-types';


class DamageCapturePanel extends React.Component {

    render() {
        let damageCodes = [];
       if (!isEmpty(this.props.oneTimeValues)) {
            damageCodes = this.props.oneTimeValues['mailtracking.defaults.return.reasoncode'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
       }

 
            return(
            <div>
                <PopupBody>
                    <div className="pad-md pad-b-3xs">
                    <DamageCapture damageCodes={damageCodes}/>
                    </div>
                </PopupBody >
                <PopupFooter>
                    <IButton category="primary" bType="SAVE" accesskey="S" onClick={this.props.saveDamageCapture} >Save</IButton>{' '}
                    <IButton category="secondary" bType="CLOSE" accesskey="O" onClick={this.props.closeDamageCapture}>Close</IButton>{' '}
                </PopupFooter>
                </div>
            )
       
    }

}

DamageCapturePanel.propTypes = {
    oneTimeValues: PropTypes.array,
    saveDamageCapture: PropTypes.func,
    closeDamageCapture: PropTypes.func
}

export default icpopup(wrapForm('damageCapture')(DamageCapturePanel), { title: 'Damage Capture' })