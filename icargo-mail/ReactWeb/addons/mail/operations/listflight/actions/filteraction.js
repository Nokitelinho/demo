import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import { closePopupWindow } from 'icoreact/lib/ico/framework/component/common/modal/popuputils';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util'

export function toggleFilter(screenMode) {
    return { type: 'TOGGLE_FILTER', screenMode };
}


export function okButtonClick(values) {
    const { dispatch, getState } = values;
    const state = getState();
    let indexes = state.filterReducer.selectedFlightIndex;
    if (checkRowselectOrNot(indexes)) {
        const { args, dispatch, getState } = values;
        const state = getState();
        const url = 'rest/addons/mail/operations/listflight/ok/bookedflights';
        const data = {};
        let selectedMailBagVOs = [];
        for (var i = 0; i < state.commonReducer.selectedMailbagVos.length; i++) {
            let mailBag = {
                "companyCode": state.commonReducer.selectedMailbagVos[i].companyCode,
                "shipmentPrefix": state.commonReducer.selectedMailbagVos[i].shipmentPrefix,
                "documentNumber": state.commonReducer.selectedMailbagVos[i].masterDocumentNumber,
                "documentOwnerIdr": state.commonReducer.selectedMailbagVos[i].documentOwnerIdr,
                "duplicateNumber": state.commonReducer.selectedMailbagVos[i].duplicateNumber,
                "sequenceNumber": state.commonReducer.selectedMailbagVos[i].sequenceNumber,
                "mailbagId": state.commonReducer.selectedMailbagVos[i].mailbagId,
                "accepted": state.commonReducer.selectedMailbagVos[i].accepted,
            }
            selectedMailBagVOs.push(mailBag);
        }
        data.selectedMailBagVOs = selectedMailBagVOs;
        /*data.selectedMailBagVOs = [{ "companyCode": "AV", "shipmentPrefix": '134', "documentNumber": '88893803', "documentOwnerIdr": 0, "duplicateNumber": 0, "sequenceNumber": 1,"mailbagId": "FRCDGAAEDXBAACA95484950000555"},
        { "companyCode": "AV", "shipmentPrefix": '134', "documentNumber": '88893803', "documentOwnerIdr": 0, "duplicateNumber": 0, "sequenceNumber": 1,"mailbagId": "FRCDGAAEDXBAACA95484950000555" },
        { "companyCode": "AV", "shipmentPrefix": '134', "documentNumber": '88893803', "documentOwnerIdr": 0, "duplicateNumber": 0, "sequenceNumber": 1,"mailbagId": "FRCDGAAEDXBAACA95484950000555" }];*/
        //data.selectedMailBagVOs = state.commonReducer.selectedMailbagVos;
        var bookingFlightNumber = "";
        for (var i = 0; i < indexes.length; i++) {
            if (bookingFlightNumber == "") {
                bookingFlightNumber = indexes[i]  + "";
            }
            else {
                bookingFlightNumber = bookingFlightNumber + "," + indexes[i] ;
            }
        }

        data.bookingFlightNumber = bookingFlightNumber;
		   const fligtTab = state.commonReducer.activeTab;
        var attachmentSource = "";
        if (fligtTab === "BookingView") {
            attachmentSource = 'BKG'
        } else {
            attachmentSource = 'LODPLN'
        }
		
		  for (var i = 0; i < state.commonReducer.flightDetails.length; i++) {
            state.commonReducer.flightDetails[i].attachmentSource = attachmentSource
        }

        data.mailBookingDetailVOs = state.commonReducer.flightDetails;
        return makeRequest({
            url, data: { ...data }
        }).then(function (response) {
            if (response.status !== null && response.status === "success") {
                handleScreenloadResponse(dispatch, response, state);
            }
            return response;
        }).catch(error => {
            return error;
        });
    }
    else {
        dispatch(requestValidationError('Please select at least one row', ''));
        return;
    }

}
export function handleScreenloadResponse(dispatch, response, state) {
    window.parent.popupUtils.getPopupReference().dialog('close')
}

export function changeTab(values) {
    const { dispatch, args } = values
    const currentTab = args.currentTab;
    dispatch({ type: 'CHANGE_TAB', currentTab });
}

function checkRowselectOrNot(indexes) {
    if (indexes.length > 0) {
        return true
    }
    else {
        return false;
    }
}
