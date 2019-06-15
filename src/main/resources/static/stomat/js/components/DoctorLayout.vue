<template xmlns:v="http://www.w3.org/1999/xhtml">
    <div>
        <div class="selector">
            <div class="selector-item manage-bookings-selector"
                 @click="onSelectManageBookings">{{ $t('manage.doctor.tabs.manage-booking') }}</div>
            <div class="selector-item make-booking-selector"
                 @click="onSelectMakeBooking">{{ $t('manage.doctor.tabs.make-booking') }}</div>
            <div class="selector-item extra-schedule-selector"
                 @click="onSelectExtraSchedule">{{ $t('manage.doctor.tabs.extra-schedule') }}</div>
            <div class="selector-item week-schedule-selector"
                 @click="onSelectWeekSchedule">{{ $t('manage.doctor.tabs.week-schedule') }}</div>
        </div>
        <div class="content">
            <div class="manage-bookings-wrap" v-if="selector === 'manage-bookings'">
                <doctors-bookings v-bind:doctor="doctor"></doctors-bookings>
            </div>
            <div class="make-booking-wrap" v-if="selector === 'make-booking'">
                <booking v-bind:doctor="doctor"></booking>
            </div>
            <div class="extra-schedule-wrap" v-if="selector === 'extra-schedule'">
                <extra-schedule v-bind:doctor="doctor"></extra-schedule>
            </div>
            <div class="week-schedule-wrap" v-if="selector === 'week-schedule'">
                <week-schedule v-bind:doctor="doctor"></week-schedule>
            </div>
        </div>
    </div>
</template>

<script>
    import WeekSchedule from "./schedule/WeekSchedule.vue";
    import ExtraSchedule from "./schedule/ExtraSchedule.vue";
    import Booking from "./booking/Booking.vue";
    import DoctorsBookings from "./booking/manager/DoctorsBookings.vue";

    export default {
        name: "DoctorLayout",
        components: {DoctorsBookings, Booking, ExtraSchedule, WeekSchedule},
        props: ['doctor'],
        data() {
            return {
                selector: "manage-bookings"
            }
        },
        methods: {
            onSelectManageBookings() {
                this.selector = "manage-bookings"
            },
            onSelectMakeBooking(arg) {
                this.selector = "make-booking"
            },
            onSelectExtraSchedule() {
                this.selector = "extra-schedule"
            },
            onSelectWeekSchedule() {
                this.selector = "week-schedule"
            }
        }
    }
</script>

<style scoped>

    .selector-item {
        cursor: pointer;
    }

</style>