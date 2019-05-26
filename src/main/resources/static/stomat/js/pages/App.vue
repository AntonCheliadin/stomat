<template>
    <div>
        <doctors-bookings></doctors-bookings>
        <booking></booking>
        <extra-schedule></extra-schedule>
        <week-schedule></week-schedule>
        <vuedal></vuedal>
    </div>
</template>

<script>
    import WeekSchedule from "../components/schedule/WeekSchedule.vue";
    import ExtraSchedule from "../components/schedule/ExtraSchedule.vue";
    import Booking from "../components/booking/Booking.vue";
    import DoctorsBookings from "../components/booking/manager/DoctorsBookings.vue";
    import {Component as Vuedal} from 'vuedals';

    export default {
        components: {DoctorsBookings, Booking, ExtraSchedule, WeekSchedule, Vuedal},
        data() {
            return {}
        }
    }
</script>

<style>

</style>