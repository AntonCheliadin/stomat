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
                :events="extraScheduleCalendarEvents"
                @eventClick="handleEventClick"
                @eventDrop="handleEventMove"
                @eventResize="handleEventMove"
                @eventRender="eventRender"
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
        name: "ExtraSchedule",
        components: {FullCalendar},
        computed: mapGetters(['extraScheduleCalendarEvents']),
        created() {
            this.loadExtraScheduleAction(
                {
                    doctor: 10,
                    from: moment().startOf('isoWeek').format("YYYY-MM-DD"),
                    to: moment().startOf('isoWeek').add(7, 'days').format("YYYY-MM-DD")
                });
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
                slotDuration: "00:15",
                slotLabelInterval: "00:30",
                slotLabelFormat: {
                    hour: '2-digit',
                    minute: '2-digit',
                    hour12: false
                },
                firstDay: 1,
                allDaySlot: true,
                minTime: "06:00:00",
                maxTime: "23:00:00",
            }
        },
        methods: {
            ...mapActions(['loadExtraScheduleAction', 'addExtraScheduleAction', 'updateExtraScheduleAction',
                'removeExtraScheduleAction']),
            handleSelect(arg) {
                this.addExtraScheduleAction(this._fullCalendarEventToScheduleItem(arg))
            },
            handleEventClick(arg) {
                // console.log(arg)
            },
            handleEventMove(arg) {
                this.updateExtraScheduleAction(this._fullCalendarEventToScheduleItem(arg.event))
            },
            _fullCalendarEventToScheduleItem(event) {
                return {
                    item: event.item,
                    doctor: 10,
                    id: event.id ? Number(event.id) : null,
                    type: "ADDING",
                    fromDate: moment(event.start).format("YYYY-MM-DD HH:mm"),
                    toDate: moment(event.end).format("YYYY-MM-DD HH:mm")
                }
            },
            eventRender: function (arg) {
                this.addRemoveBtn(arg);
            },
            addRemoveBtn(arg) {
                let removeBtn = document.createElement("div");
                removeBtn.textContent = "x";
                removeBtn.className = "remove-btn";
                arg.el.firstChild.appendChild(removeBtn);
                removeBtn.addEventListener("click", () => this.handleEventRemove(arg));
            },
            handleEventRemove(arg) {
                this.removeExtraScheduleAction(this._fullCalendarEventToScheduleItem(arg.event))
            }
        }
    }
</script>

<style>
    @import '~@fullcalendar/core/main.css';
    @import '~@fullcalendar/timegrid/main.css';

    .remove-btn {
        color: black;
        position: absolute;
        top: 0;
        right: 0;
        width: 13px;
        height: 13px;
        text-align: center;
        border-radius: 50%;
        font-size: 9px;
        cursor: pointer;
        background-color: #FFF
    }
</style>