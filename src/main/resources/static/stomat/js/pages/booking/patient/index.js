import Vue from 'vue'
import 'api/resource'
import '@babel/polyfill'
import store from 'store/store'
import PatientBookingApp from './PatientBookingApp.vue'
import {default as Vuedals, Component as Vuedal, Bus as VuedalsBus} from 'vuedals';
import vSelect from 'vue-select'
import {defaultLocale, languages} from "../../../i18n";
import VueI18n from "vue-i18n";

Vue.component('v-select', vSelect);

Vue.use(Vuedals);
Vue.use(VueI18n);

const i18n = new VueI18n({
    locale: LANG,
    fallbackLocale: defaultLocale,
    messages: Object.assign(languages)
});

new Vue({
    el: '#app',
    store,
    i18n,
    render: h => h(PatientBookingApp)
});