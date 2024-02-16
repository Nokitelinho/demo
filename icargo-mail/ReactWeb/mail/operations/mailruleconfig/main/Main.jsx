import React from 'react';
import store from '../store/store';
import App from './App.jsx';
import { initapp } from 'icoreact/lib/ico/framework/component/common/app/init';
import config from '../../../../app.config.js';
import {SCREEN_ID,SCREEN_ACTION} from '../constants/index'
class MainPage extends React.Component {

    render() {

        return (
            <App></App>
        );
    }

}
export default initapp({
    store, screenId:SCREEN_ID ,
    screenAction: SCREEN_ACTION, appConfig: config,
    showStatusBar:false
})(MainPage);