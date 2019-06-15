<template>
    <div>
        <h2 class="content-title">{{$t('manage.doctor.tabs.manage-booking.title')}}</h2>
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
                :events="bookingsToCalendarEvents"
                :locale="locale"
                @eventClick="handleEventClick"
                @eventDrop="handleEventMove"
                @eventResize="handleEventMove"
                @select="handleSelect"
                @eventRender="eventRender"
                @datesRender="datesRender"/>
    </div>
</template>

<script>
    import FullCalendar from '@fullcalendar/vue'
    import timeGridPlugin from '@fullcalendar/timegrid'
    import interactionPlugin from '@fullcalendar/interaction'
    import moment from 'moment'
    import {mapActions, mapGetters, mapMutations} from 'vuex'
    import ManagerBookingPopup from "./ManagerBookingPopup.vue"
    import {default as Vuedals, Component as Vuedal, Bus as VuedalsBus} from 'vuedals'
    import {getFullCalendarLocale} from "../../../i18n/fullCalendarI18n";

    export default {
        name: "DoctorsBookings",
        components: {FullCalendar},
        computed: mapGetters(['bookingsToCalendarEvents', 'bookingById']),
        props: ['doctor'],
        created() {
            this.loadReasons();
        },
        data() {
            return {
                calendarPlugins: [timeGridPlugin, interactionPlugin],
                editable: true,
                selectable: true,
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
                locale: getFullCalendarLocale()
            }
        },
        methods: {
            ...mapGetters(['getReasonOptions']),
            ...mapActions(['loadBookingsAction', 'moveBookingAction', 'removeBookingAction', 'loadReasons']),
            ...mapMutations(['setBookingCalendarDate']),
            handleSelect(event) {
                VuedalsBus.$emit('new', {
                    name: 'create-booking-by-manager-popup',
                    component: ManagerBookingPopup,
                    props: {
                        booking: {
                            startDate: event.start,
                            endDate: event.end,
                            patient: {},
                            doctor: this.doctor,
                            reason: {}
                        },
                        reasons: this.getReasonOptions()
                    },
                });
            },
            handleEventClick(arg) {
                if (!arg.jsEvent.target.classList.contains('remove-btn')) {
                    VuedalsBus.$emit('new', {
                        name: 'update-booking-by-manager-popup',
                        component: ManagerBookingPopup,
                        props: {
                            booking: this.bookingById(arg.event.id),
                            reasons: this.getReasonOptions()
                        },
                    });
                }
            },
            handleEventMove(arg) {
                let booking = {
                    id: arg.event.id,
                    startDate: moment(arg.event.start).format("YYYY-MM-DD HH:mm"),
                    endDate: moment(arg.event.end).format("YYYY-MM-DD HH:mm")
                };
                this.moveBookingAction(booking);
            },
            eventRender: function (arg) {
                if (arg.event.rendering !== "background") {
                    this.addRemoveBtn(arg);
                }
            },
            addRemoveBtn(arg) {
                let removeBtn = document.createElement("div");
                removeBtn.textContent = "x";
                removeBtn.className = "remove-btn";
                arg.el.firstChild.appendChild(removeBtn);
                removeBtn.addEventListener("click", () => this.handleEventRemove(arg));
            },
            handleEventRemove(arg) {
                this.removeBookingAction(arg.event.id)
            },
            datesRender: function (arg) {
                this.setBookingCalendarDate(moment(arg.view.currentStart));
                this.loadBookingsAction({doctor: this.doctor});
            },
        }
    }
</script>

<style>
    @import '~@fullcalendar/core/main.css';
    @import '~@fullcalendar/timegrid/main.css';

    .doctor-booking {
        cursor: pointer;
    }
</style>