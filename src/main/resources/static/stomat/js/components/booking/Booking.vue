<template>
    <div>
        <h2 class="content-title">Make an appointment</h2>
        <v-select
                v-model="selectedReason"
                :clearable="clearable"
                :options="getReasonOptions"
                @input="onChangeReason"/>
        <FullCalendar
                defaultView="timeGridWeek"
                :plugins="calendarPlugins"
                :editable="editable"
                :selectable="selectable"
                :header="header"
                :columnHeaderFormat="columnHeaderFormat"
                :slotDuration="slotDuration"
                :slotLabelInterval="slotLabelInterval"
                :slotLabelFormat="slotLabelFormat"
                :firstDay="firstDay"
                :allDaySlot="allDaySlot"
                :minTime="minTime"
                :maxTime="maxTime"
                :eventTimeFormat="eventTimeFormat"
                :events="freeTimesToCalendarEvents"
                @eventClick="handleEventClick"
                @datesRender="datesRender"/>
    </div>
</template>

<script>
    import FullCalendar from '@fullcalendar/vue'
    import timeGridPlugin from '@fullcalendar/timegrid'
    import interactionPlugin from '@fullcalendar/interaction'
    import moment from 'moment'
    import {mapGetters, mapActions, mapMutations} from 'vuex'
    import {default as Vuedals, Component as Vuedal, Bus as VuedalsBus} from 'vuedals';
    import BookingPopup from "./BookingPopup.vue";

    export default {
        name: "Booking",
        components: {FullCalendar},
        computed: mapGetters(['freeTimesToCalendarEvents', 'getReasonOptions']),
        props: ['doctor'],
        created() {
            this.loadBackgroundWeekScheduleAction(this.doctor);
            this.loadReasons();
        },
        data() {
            return {
                selectedReason: this.findOrGetDefaultReason()(),
                clearable: false,

                calendarPlugins: [timeGridPlugin, interactionPlugin],
                editable: false,
                selectable: false,
                header: {
                    left: 'title',
                    center: '',
                    right: 'today prev,next'
                },
                columnHeaderFormat: {
                    weekday: 'short',
                    month: 'numeric',
                    day: 'numeric',
                    omitCommas: true
                },
                slotDuration: "00:30",
                slotLabelInterval: "00:30",
                slotLabelFormat: {
                    hour: '2-digit',
                    minute: '2-digit',
                    hour12: false
                },
                firstDay: 1,
                allDaySlot: false,
                minTime: "06:00:00",
                maxTime: "23:00:00",
                eventTimeFormat: {
                    hour: '2-digit',
                    minute: '2-digit',
                    hour12: false
                }
            }
        },
        methods: {
            ...mapGetters(['findOrGetDefaultReason']),
            ...mapActions(['loadFreeTimesAction', 'loadBackgroundWeekScheduleAction', 'loadReasons']),
            ...mapMutations(['setBookingCalendarDate']),
            handleEventClick(arg) {
                VuedalsBus.$emit('new', {
                    name: 'booking-popup',
                    title: this.selectedReason.label,
                    component: BookingPopup,
                    props: {
                        event: arg.event,
                        doctor: this.doctor,
                        reason: this.selectedReason
                    },
                });
            },
            datesRender: function (arg) {
                this.setBookingCalendarDate(moment(arg.view.currentStart));
                this.loadFreeTimesAction({doctor: this.doctor, reason: this.selectedReason.id});
            },
            onChangeReason(reason) {
                this.loadFreeTimesAction({doctor: this.doctor, reason: reason.id});
            }
        }
    }
</script>

<style>
    @import '~@fullcalendar/core/main.css';
    @import '~@fullcalendar/timegrid/main.css';

    .free-time {
        cursor: pointer;
    }
</style>