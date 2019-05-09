import Vue from 'vue'
import 'api/resource'
import '@babel/polyfill'
import store from 'store/store'
import App from 'pages/App.vue'

new Vue({
    el: '#app',
    store,
    render: a => a(App)
});