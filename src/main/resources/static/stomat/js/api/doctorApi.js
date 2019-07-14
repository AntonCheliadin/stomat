import Vue from 'vue'

const doctorList = Vue.resource('/api/doctor/list');

export default {
    get: () => doctorList.get()
}
