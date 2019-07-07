import Vue from 'vue'

var bookingsActions = {
    list: {method: 'GET', url: '/api/booking/manager/list'},
    create: {method: 'POST', url: '/api/booking/patient/create'},//todo: move to separate page
    move: {method: 'PUT', url: '/api/booking/manager/move{/id}'}
};

const bookingApi = Vue.resource('/api/booking/manager{/id}', {}, bookingsActions);

export default {
    create: booking => bookingApi.create({}, booking),//by patient (with free time only)
    list: data => bookingApi.list(data),
    get: id => bookingApi.get({id: id}),
    add: booking => bookingApi.save({}, booking), //by manager (ignore validation)
    move: data => bookingApi.move({id: data.id}, data),
    update: booking => bookingApi.update({id: booking.id}, booking),
    remove: id => bookingApi.remove({id: id}),
}
