import Vue from 'vue'

const bookingApi = Vue.resource('/api/booking/submit');

export default {
    add: data => bookingApi.save({}, data)
}
