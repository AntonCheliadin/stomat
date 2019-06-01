<template>
    <div>
        <doctors-bookings v-bind:doctor="doctor"></doctors-bookings>
        <booking v-bind:doctor="doctor"></booking>
        <extra-schedule v-bind:doctor="doctor"></extra-schedule>
        <week-schedule v-bind:doctor="doctor"></week-schedule>
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
        props: ['doctor']
    }
</script>

<style>

</style>