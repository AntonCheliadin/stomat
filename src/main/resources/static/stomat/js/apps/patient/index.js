import Vue from 'vue'
import '@babel/polyfill'
import '../../api/resource'
import store from '../../store/index'
import PatientBookingApp from './PatientBookingApp.vue'
import {default as Vuedals, Component as Vuedal, Bus as VuedalsBus} from 'vuedals';
import vSelect from 'vue-select'
import {i18n} from "../../i18n";

Vue.component('v-select', vSelect);

Vue.use(Vuedals);

new Vue({
    el: '#app',
    store,
    i18n,
    render: h => h(PatientBookingApp)
});