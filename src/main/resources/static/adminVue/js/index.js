import 'expose-loader?jQuery!jquery' // eslint-disable-line
import 'expose-loader?$!jquery' // eslint-disable-line
import Vue from 'vue'
import '@babel/polyfill'
import './api/resource'
import store from './store/index'
import router from './Routes';
import App from './AdminVue.vue'
import {default as Vuedals, Component as Vuedal, Bus as VuedalsBus} from 'vuedals';
import vSelect from 'vue-select'
// import {defaultLocale, languages} from "../../i18n";
import VueI18n from "vue-i18n";
import BootstrapVue from 'bootstrap-vue';
import VueTouch from 'vue-touch';


Vue.component('v-select', vSelect);

Vue.use(BootstrapVue);
Vue.use(VueTouch);
Vue.use(Vuedals);
Vue.use(VueI18n);

const i18n = new VueI18n({
    locale: LANG,
    // fallbackLocale: defaultLocale,
    // messages: Object.assign(languages)
});


import { library } from '@fortawesome/fontawesome-svg-core';
import {
    faCoffee, faSpinner, faWrench, faAmbulance, faEdit, faCircle, faCheck, faChessQueen,
    faPlus, faEquals, faArrowRight, faPencilAlt, faComment, faHeadphones, faSquare,
    faCalendar, faCertificate, faEnvelope, faTimes, faBookmark, faHeart, faPlay,
    faSun, faMoon, faStar
} from '@fortawesome/free-solid-svg-icons';
import { faJs, faVuejs, faFacebookF } from '@fortawesome/free-brands-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

library.add(
    faCoffee, faSpinner, faWrench, faAmbulance, faSquare,
    faEdit, faCircle, faCheck, faChessQueen, faHeadphones,
    faPlus, faEquals, faArrowRight, faPencilAlt, faComment,
    faCalendar, faCertificate, faEnvelope, faTimes, faBookmark,
    faHeart, faPlay, faSun, faMoon, faStar,
    faJs, faVuejs, faFacebookF);

Vue.component('font-awesome-icon', FontAwesomeIcon);



new Vue({
    el: '#app',
    store,
    router,
    i18n,
    render: h => h(App, {
        // props: {
        //     doctor: doctorId
        // }
    })
});
