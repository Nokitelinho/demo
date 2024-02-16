import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import { closePopupWindow } from 'icoreact/lib/ico/framework/component/common/modal/popuputils';

export function screenLoad(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const url = 'rest/addons/mail/operations/listflight/screenload';
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
    //data.selectedMailBagVOs = [{ "companyCode": "AV", "shipmentPrefix": '134', "documentNumber": '88893803' }, { "companyCode": "AV", "shipmentPrefix": '134', "documentNumber": '88893803' }, { "companyCode": "AV", "shipmentPrefix": '134', "documentNumber": '88893803' }];
    //data.selectedMailBagVOs = state.commonReducer.selectedMailbagVos;
	const fligtTab = state.commonReducer.activeTab;
      data.fligtTab=fligtTab;
    return makeRequest({
        url, data: { ...data }
    }).then(function (response) {
        handleScreenloadResponse(dispatch, response, state);
        return response;
    }).catch(error => {
        return error;
    });

}


export function handleScreenloadResponse(dispatch, response, state) {
    if (response.results) {
        dispatch(screenLoadSuccess(response.results[0]));
    }
}
export function screenLoadSuccess(data) {
    return { type: 'SCREENLOAD_SUCCESS', data };
}

export function onClose(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    let isPopup = state.__commonReducer.screenConfig ? state.__commonReducer.screenConfig.isPopup : '';
    if (isPopup == "true") closePopupWindow();
    else
        navigateToScreen('home.jsp', {});
}




