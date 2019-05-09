<template>
    <div>
        <FullCalendar
                defaultView="timeGridWeek"
                :plugins="calendarPlugins"
                :editable="editable"
                :selectable="selectable"
                :events="scheduleWeekCalendarEvents"
                @dateClick="handleDateClick"
                @eventClick="handleEventClick"
                @select="handleSelect"/>
    </div>
</template>

<script>
    import {mapGetters, mapActions} from 'vuex'
    import FullCalendar from '@fullcalendar/vue'
    import timeGridPlugin from '@fullcalendar/timegrid'
    import interactionPlugin from '@fullcalendar/interaction'

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
                selectable: true
            }
        },
        methods: {
            ...mapActions(['loadScheduleWeekAction', 'addScheduleWeekAction', 'updateScheduleWeekAction']),
            handleDateClick(arg) {
                console.log(arg)
            },
            handleSelect(arg) {
                console.log(arg);
                this.addScheduleWeekAction({
                    doctor: {id: 10},
                    scheduleItem: {
                        dayOfWeek: arg.start.getDay() + 1,
                        timeFrom: `${arg.start.getHours()}:${arg.start.getMinutes()}`,
                        timeTo: `${arg.end.getHours()}:${arg.end.getMinutes()}`
                    }
                })
            },
            handleEventClick(arg) {
                console.log(arg)
            }
        }
    }
</script>

<style scoped>
    @import '~@fullcalendar/core/main.css';
    @import '~@fullcalendar/timegrid/main.css';
</style>