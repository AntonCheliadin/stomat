import Vue from 'vue'

const bookingApi = Vue.resource('/api/booking/create');

export default {
    add: data => bookingApi.save({}, data)
}
