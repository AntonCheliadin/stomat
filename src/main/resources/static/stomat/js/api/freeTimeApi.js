import Vue from 'vue'

const freeTimeApi = Vue.resource('/api/booking/free/time');

export default {
    get: data => freeTimeApi.get(data)
}
