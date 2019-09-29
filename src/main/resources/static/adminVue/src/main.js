// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import 'expose-loader?jQuery!jquery' // eslint-disable-line
import 'expose-loader?$!jquery' // eslint-disable-line
import Vue from 'vue';
import BootstrapVue from 'bootstrap-vue';
import VueTouch from 'vue-touch';
import Trend from 'vuetrend';
import {defaultLocale, languages} from "./i18n/index";
import VueI18n from "vue-i18n";
import vSelect from 'vue-select'
import {default as Vuedals} from 'vuedals';

import './api/resource'

import store from './store';
import router from './Routes';
import App from './App';

Vue.component('v-select', vSelect);

Vue.use(BootstrapVue);
Vue.use(VueTouch);
Vue.use(Trend);

Vue.use(VueI18n);
Vue.use(Vuedals);

const i18n = new VueI18n({
  locale: 'en',
  fallbackLocale: defaultLocale,
  messages: Object.assign(languages)
});

Vue.config.productionTip = false;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  router,
  i18n,
  render: h => h(App),
});
