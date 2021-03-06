<template>
    <div>
        <h2 class="content-title">{{$t('manage.doctor.tabs.week-schedule.title')}}</h2>
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
                :events="weekScheduleToCalendarEvents"
                :locale="locale"
                :contentHeight="contentHeight"
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
    import {getFullCalendarLocale} from "../../../../i18n/fullCalendarI18n";

    export default {
        name: "WeekSchedule",
        components: {FullCalendar},
        computed: mapGetters(['weekScheduleToCalendarEvents']),
        props: ['doctor'],
        created() {
            this.loadWeekScheduleAction(this.doctor);
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
            ...mapActions(['loadWeekScheduleAction', 'addWeekScheduleAction', 'updateWeekScheduleAction',
                'removeWeekScheduleAction']),
            handleSelect(arg) {
                this.addWeekScheduleAction(this._fullCalendarEventToScheduleItem(arg))
            },
            handleEventMove(arg) {
                this.updateWeekScheduleAction(this._fullCalendarEventToScheduleItem(arg.event))
            },
            _fullCalendarEventToScheduleItem(event) {
                let day = event.start.getDay();
                return {
                    doctor: this.doctor,
                    id: event.id ? Number(event.id) : null,
                    dayOfWeek: day === 0 ? 7 : day,
                    timeFrom: moment(event.start).format("HH:mm"),
                    timeTo: moment(event.end).format("HH:mm")
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
                this.removeWeekScheduleAction(this._fullCalendarEventToScheduleItem(arg.event))
            }
        }
    }
</script>

<style>
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