import Vue from 'vue'

const freeTimeApi = Vue.resource('http://localhost:8080/api/booking/free/time');

export default {
    get: data => freeTimeApi.get(data)
}
