<template>
    <div>
        <h2 class="content-title">Extra schedule</h2>
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
                :events="eventsForExtraScheduleCalendar"
                @eventClick="handleEventClick"
                @eventDrop="handleEventMove"
                @eventResize="handleEventMove"
                @eventRender="eventRender"
                @datesRender="datesRender"
                @select="handleSelect"/>
    </div>
</template>

<script>
    import {mapGetters, mapActions, mapMutations} from 'vuex'
    import FullCalendar from '@fullcalendar/vue'
    import timeGridPlugin from '@fullcalendar/timegrid'
    import interactionPlugin from '@fullcalendar/interaction'
    import moment from 'moment'
    import ExtraSchedulePopup from "./ExtraSchedulePopup.vue";
    import {default as Vuedals, Component as Vuedal, Bus as VuedalsBus} from 'vuedals';
    import {
        fullCalendarEventToExtraSchedule,
        moveExtraSchedule
    } from "./converters/extraScheduleConverter";

    export default {
        name: "ExtraSchedule",
        components: {FullCalendar},
        computed: mapGetters(['eventsForExtraScheduleCalendar', 'extraScheduleById']),
        props: ['doctor'],
        created() {
            this.loadBackgroundWeekScheduleAction(this.doctor);
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
                eventTimeFormat: {
                    hour: '2-digit',
                    minute: '2-digit',
                    hour12: false
                }
            }
        },
        methods: {
            ...mapActions(['loadExtraScheduleAction', 'addExtraScheduleAction', 'updateExtraScheduleAction',
                'removeExtraScheduleAction', 'loadBackgroundWeekScheduleAction']),
            ...mapMutations(['setExtraCalendarDate']),
            handleSelect(event) {
                VuedalsBus.$emit('new', {
                    name: 'create-extra-schedule-popup',
                    component: ExtraSchedulePopup,
                    props: {
                        event: fullCalendarEventToExtraSchedule(event, this.doctor),
                    },
                });
            },
            handleEventClick(arg) {
                if (!arg.jsEvent.target.classList.contains('remove-btn')) {
                    VuedalsBus.$emit('new', {
                        name: 'update-extra-schedule-popup',
                        component: ExtraSchedulePopup,
                        props: {
                            event: this.extraScheduleById(arg.event.id)
                        },
                    });
                }
            },
            handleEventMove(arg) {
                let extraSchedule = this.extraScheduleById(arg.event.id);
                this.updateExtraScheduleAction(moveExtraSchedule(extraSchedule, arg.event))
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
                this.removeExtraScheduleAction(arg.event.id)
            },
            datesRender: function (arg) {
                this.setExtraCalendarDate(moment(arg.view.currentStart));
                this.loadExtraScheduleAction({
                    doctor: this.doctor,
                    from: moment(arg.view.currentStart).format("YYYY-MM-DD"),
                    to: moment(arg.view.currentEnd).format("YYYY-MM-DD")
                });
            },
        }
    }
</script>

<style>

    .extra-schedule-type-INCLUDE {
        background-color: #23b017;
    }

    .extra-schedule-type-EXCLUDE {
        background-color: #ed0000;
    }

</style>