import Vue from 'vue'

const doctorsActions = {
    list: {method: 'GET', url: 'http://localhost:8080/api/doctor/list'},
};

const doctorApi = Vue.resource('http://localhost:8080/api/doctor{/id}', {}, doctorsActions);

export default {
    list: data => doctorApi.list(data),
    get: id => doctorApi.get({id: id}),
    add: doctor => doctorApi.save({}, doctor),
    update: doctor => doctorApi.update({id: doctor.id}, doctor),
}
