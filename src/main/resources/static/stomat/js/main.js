import Vue from 'vue'
import 'api/resource'
import '@babel/polyfill'
import store from 'store/store'
import App from 'pages/App.vue'
import {default as Vuedals, Component as Vuedal, Bus as VuedalsBus} from 'vuedals';
import vSelect from 'vue-select'

Vue.component('v-select', vSelect);

Vue.use(Vuedals);

new Vue({
    el: '#app',
    store,
    render: h => h(App, {
        props: {
            doctor: doctorId
        }
    })
});