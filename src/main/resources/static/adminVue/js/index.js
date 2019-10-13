import 'expose-loader?jQuery!jquery' // eslint-disable-line
import 'expose-loader?$!jquery' // eslint-disable-line
import Vue from 'vue'
import '@babel/polyfill'
import './api/resource'
import store from './store/index'
import router from './Routes';
import App from './AdminVue.vue'
import {default as Vuedals, Component as Vuedal, Bus as VuedalsBus} from 'vuedals';
import {i18n} from "./i18n";
import BootstrapVue from 'bootstrap-vue';
import VueTouch from 'vue-touch';
import './core/fontAwesome';
import './core/validateProvider';
import './core/vueMask';
import './core/vueSelect';
import './core/notifications'

Vue.use(BootstrapVue);
Vue.use(VueTouch);
Vue.use(Vuedals);

new Vue({
    el: '#app',
    store,
    router,
    i18n,
    render: h => h(App, {})
});
