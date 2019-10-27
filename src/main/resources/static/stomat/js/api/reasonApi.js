import Vue from 'vue'

const reasonApi = Vue.resource('http://localhost:8080/api/reason/list');

export default {
    get: () => reasonApi.get()
}
