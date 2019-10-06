<template>
    <div>
        <h2 class="content-title">{{$t('manage.doctor.tabs.make-booking.title')}}</h2>
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
                :locale="locale"
                :contentHeight="contentHeight"
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
    import {getFullCalendarLocale} from "../../i18n/fullCalendarI18n";
    import 'vue-select/dist/vue-select.css';

    export default {
        name: "Booking",
        components: {FullCalendar},
        computed: mapGetters(['freeTimesToCalendarEvents', 'getReasonOptions']),
        props: ['doctor', 'reason'],
        data() {
            return {
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
                },
                locale: getFullCalendarLocale(),
                contentHeight: "auto"
            }
        },
        methods: {
            ...mapGetters(['findOrGetDefaultReason']),
            ...mapActions(['loadFreeTimesAction', 'loadReasons']),
            ...mapMutations(['setBookingCalendarDate']),
            handleEventClick(arg) {
                VuedalsBus.$emit('new', {
                    name: 'booking-popup',
                    title: this.reason.name,
                    component: BookingPopup,
                    props: {
                        event: arg.event,
                        doctor: this.doctor,
                        reason: this.reason
                    },
                });
            },
            datesRender: function (arg) {
                this.setBookingCalendarDate(moment(arg.view.currentStart));
                this.loadFreeTimesAction({doctor: this.doctor.id, reason: this.reason.id});
            },
        }
    }
</script>

<style>
    .free-time {
        cursor: pointer;
    }
</style>