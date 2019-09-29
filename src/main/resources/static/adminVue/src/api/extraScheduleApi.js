import Vue from 'vue'

const extraScheduleApi = Vue.resource('http://localhost:8080/api/manage/schedule/extra{/id}');

export default {
    get: data => extraScheduleApi.get(data),
    add: item => extraScheduleApi.save({}, item),
    update: item => extraScheduleApi.update({id: item.id}, item),
    remove: itemId => extraScheduleApi.remove({id: itemId}, {})
}
