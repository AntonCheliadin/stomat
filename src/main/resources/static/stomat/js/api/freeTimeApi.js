import Vue from 'vue'

const freeTimeApi = Vue.resource('/booking/free/time');

export default {
    get: data => freeTimeApi.get(data)
}
