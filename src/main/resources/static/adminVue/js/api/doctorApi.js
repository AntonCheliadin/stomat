import Vue from 'vue'

const doctorList = Vue.resource('http://localhost:8080/api/doctor/list');

export default {
    get: () => doctorList.get()
}
