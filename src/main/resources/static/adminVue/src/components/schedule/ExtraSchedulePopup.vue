<template>
    <div>
        <h3>{{$t('manage.doctor.tabs.extra-schedule.popup.title')}}</h3>

        <p>{{$t('manage.doctor.tabs.extra-schedule.popup.type.label')}} {{event.id}}:</p>

        <input type="radio" id="typeInclude" value="INCLUDE" v-model="type">
        <label for="typeInclude">{{$t('manage.doctor.tabs.extra-schedule.popup.type.include')}}</label>
        <br>
        <input type="radio" id="typeExclude" value="EXCLUDE" v-model="type">
        <label for="typeExclude">{{$t('manage.doctor.tabs.extra-schedule.popup.type.exclude')}}</label>
        <br>
        <input id="allDay" type="checkbox" v-model="allDay" true-value="true" false-value="false">
        <label for="allDay">{{$t('manage.doctor.tabs.extra-schedule.popup.allDay')}}</label>
        <br>
        <input id="fromDate" v-text="fromDate" v-model="fromDate"/>
        <label for="fromDate">{{$t('general.range.from')}}</label>
        <br>
        <input id="toDate" v-text="toDate" v-model="toDate"/>
        <label for="toDate">{{$t('general.range.to')}}</label>
        <br>
        <button @click="save">{{$t('general.actions.save')}}</button>
        <button v-if="id" @click="deleteClick">{{$t('general.actions.delete')}}</button>
    </div>
</template>

<script>
    import {mapGetters, mapActions} from 'vuex';
    import moment from 'moment';

    export default {
        name: "ExtraSchedulePopup",
        props: ['event'],
        computed: mapGetters(['extraScheduleById']),
        created() {
        },
        data() {
            return {
                id: this.event.id,
                type: this.event.type ? this.event.type : "INCLUDE",
                fromDate: moment(this.event.fromDate).format('YYYY-MM-DD HH:mm'),
                toDate: this.event.toDate ? moment(this.event.toDate).format('YYYY-MM-DD HH:mm') : "",
                allDay: this.event.allDay,
                doctor: this.event.doctor,
            }
        },
        methods: {
            ...mapActions(['loadExtraScheduleAction', 'addExtraScheduleAction', 'updateExtraScheduleAction',
                'removeExtraScheduleAction']),
            save() {
                this.event.type = this.type;
                this.event.fromDate = this.fromDate;
                this.event.toDate = this.toDate;
                this.event.allDay = this.allDay;
                this.event.doctor = this.doctor;

                if (this.event.id == null) {
                    this.addExtraScheduleAction(this.event);
                } else {
                    this.updateExtraScheduleAction(this.event);
                }
                this.$emit('vuedals:close');
            },
            deleteClick() {
                this.removeExtraScheduleAction(this.event.id);
                this.$emit('vuedals:close');
            },
        }
    }
</script>

<style scoped>

</style>