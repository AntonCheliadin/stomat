import Vue from 'vue'

const bookingApi = Vue.resource('/api/booking/patient{/id}');

export default {
    add: booking => bookingApi.save({}, booking),//by patient (with free time only)
}
