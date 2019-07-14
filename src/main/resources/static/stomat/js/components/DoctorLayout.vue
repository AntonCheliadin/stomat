<template xmlns:v="http://www.w3.org/1999/xhtml">
    <div>

        <v-select
                v-model="selectedDoctor"
                label="fullName"
                :options="getDoctors"
                @input="onChangeDoctor"/>

        <div class="selector">
            <div class="selector-item manage-bookings-selector"
                 @click="onSelectManageBookings">{{ $t('manage.doctor.tabs.manage-booking.title') }}
            </div>
            <div class="selector-item extra-schedule-selector"
                 @click="onSelectExtraSchedule">{{ $t('manage.doctor.tabs.extra-schedule.title') }}
            </div>
            <div class="selector-item week-schedule-selector"
                 @click="onSelectWeekSchedule">{{ $t('manage.doctor.tabs.week-schedule.title') }}
            </div>
        </div>
        <div class="content">
            <div class="manage-bookings-wrap" v-if="selectedDoctor && selector === 'manage-bookings'">
                <doctors-bookings
                        :key="selectedDoctor.id"
                        v-bind:doctor="selectedDoctor.id">
                </doctors-bookings>
            </div>
            <div class="extra-schedule-wrap" v-if="selectedDoctor && selector === 'extra-schedule'">
                <extra-schedule
                        :key="selectedDoctor.id"
                        v-bind:doctor="selectedDoctor.id">

                </extra-schedule>
            </div>
            <div class="week-schedule-wrap" v-if="selectedDoctor && selector === 'week-schedule'">
                <week-schedule
                        :key="selectedDoctor.id"
                        v-bind:doctor="selectedDoctor.id">

                </week-schedule>
            </div>
        </div>
    </div>
</template>

<script>
    import {mapGetters, mapActions, mapMutations} from 'vuex'
    import WeekSchedule from "./schedule/WeekSchedule.vue";
    import ExtraSchedule from "./schedule/ExtraSchedule.vue";
    import DoctorsBookings from "./booking/manager/DoctorsBookings.vue";

    export default {
        name: "DoctorLayout",
        computed: mapGetters(['getDoctors']),
        components: {DoctorsBookings, ExtraSchedule, WeekSchedule},
        props: ['doctor'],
        created() {
            this.loadDoctors()
                .then(() => {
                    this.selectedDoctor = this.findDoctorById()(this.doctor);
                });
            this.loadReasons();
        },
        data() {
            return {
                selector: "manage-bookings",
                selectedDoctor: null,
            }
        },
        methods: {
            ...mapGetters(['findDoctorById']),
            ...mapActions(['loadDoctors', 'loadReasons']),
            onChangeDoctor(doctor) {
                this.selectedDoctor = doctor;
            },
            onSelectManageBookings() {
                this.selector = "manage-bookings"
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