import Vue from 'vue'

var bookingsActions = {
    list: {method: 'GET', url: 'http://localhost:8080/api/manage/bookings/list'},
    move: {method: 'PUT', url: 'http://localhost:8080/api/manage/bookings/move{/id}'}
};

const bookingApi = Vue.resource('http://localhost:8080/api/manage/bookings{/id}', {}, bookingsActions);

export default {
    list: data => bookingApi.list(data),
    get: id => bookingApi.get({id: id}),
    add: booking => bookingApi.save({}, booking),
    move: data => bookingApi.move({id: data.id}, data),
    update: booking => bookingApi.update({id: booking.id}, booking),
    remove: id => bookingApi.remove({id: id}),
}
