<template>
    <div>
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
                :events="scheduleWeekCalendarEvents"
                @eventClick="handleEventClick"
                @eventDrop="handleEventMove"
                @eventResize="handleEventMove"
                @select="handleSelect"/>
    </div>
</template>

<script>
    import {mapGetters, mapActions} from 'vuex'
    import FullCalendar from '@fullcalendar/vue'
    import timeGridPlugin from '@fullcalendar/timegrid'
    import interactionPlugin from '@fullcalendar/interaction'
    import moment from 'moment'

    export default {
        name: "WeekSchedule",
        components: {FullCalendar},
        computed: mapGetters(['scheduleWeekCalendarEvents']),
        created() {
            this.loadScheduleWeekAction({id: 10});
        },
        data() {
            return {
                calendarPlugins: [timeGridPlugin, interactionPlugin],
                editable: true,
                selectable: true,
                header: {
                    left: '',
                    center: '',
                    right: ''
                },
                columnHeaderFormat: {
                    weekday: 'long'
                },
                slotDuration: "00:30",
                slotLabelInterval: "01:00",
                slotLabelFormat: {
                    hour: '2-digit',
                    minute: '2-digit',
                    hour12: false
                },
                firstDay: 1,
                allDaySlot: false,
                minTime: "06:00:00",
                maxTime: "23:00:00",
            }
        },
        methods: {
            ...mapActions(['loadScheduleWeekAction', 'addScheduleWeekAction', 'updateScheduleWeekAction']),
            handleSelect(arg) {
                this.addScheduleWeekAction(this._fullCalendarEventToScheduleItem(arg))
            },
            handleEventClick(arg) {
                console.log(arg)
            },
            handleEventMove(arg) {
                this.updateScheduleWeekAction(this._fullCalendarEventToScheduleItem(arg.event))
            },
            _fullCalendarEventToScheduleItem(event) {
                return {
                    doctor: 10,
                    id: event.id,
                    dayOfWeek: event.start.getDay() + 1,
                    timeFrom: moment(event.start).format("HH:mm"),
                    timeTo: moment(event.end).format("HH:mm")
                }
            }
        }
    }
</script>

<style scoped>
    @import '~@fullcalendar/core/main.css';
    @import '~@fullcalendar/timegrid/main.css';
</style>