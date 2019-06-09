import Vue from 'vue'

const reasonApi = Vue.resource('/api/reason/list');

export default {
    get: () => reasonApi.get()
}
