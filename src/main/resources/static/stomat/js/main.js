import Vue from 'vue'
import 'api/resource'
import '@babel/polyfill'
import store from 'store/store'
import App from 'pages/App.vue'
import {default as Vuedals, Component as Vuedal, Bus as VuedalsBus} from 'vuedals';

Vue.use(Vuedals);

new Vue({
    el: '#app',
    store,
    render: a => a(App)
});