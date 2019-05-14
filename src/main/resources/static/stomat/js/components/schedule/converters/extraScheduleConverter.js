import moment from "moment";


let calculateEndDate = (event) => {
    if (!event.allDay) {
        let toDate;
        if (event.end) {
            toDate = moment(event.end);
        } else {
            toDate = moment(event.start).add(1, 'hour');
        }
        return toDate.format("YYYY-MM-DD HH:mm")
    }
};

export const fullCalendarEventToExtraSchedule = (event) => {
    return {
        id: event.id ? Number(event.id) : null,
        type: event.type,
        fromDate: moment(event.start).format("YYYY-MM-DD HH:mm"),
        toDate: calculateEndDate(event),
        allDay: event.allDay
    }
};

export const moveExtraSchedule = (extraSchedule, event) => {
    extraSchedule.fromDate = moment(event.start).format("YYYY-MM-DD HH:mm");
    extraSchedule.toDate = calculateEndDate(event);
    extraSchedule.allDay = event.allDay;
    return extraSchedule;
};

// export const extraScheduleToFullCalendar = (state) => {
//     let events = [];
//     for (let scheduleItem of state.extraSchedule) {
//
//         events.push({
//             item: scheduleItem,
//             id: scheduleItem.id,
//             title: scheduleItem.id,
//             start: scheduleItem.fromDate,
//             end: scheduleItem.toDate,
//             allDay: scheduleItem.allDay
//         })
//     }
//     return events;
// };