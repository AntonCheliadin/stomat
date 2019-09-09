<template xmlns:v="http://www.w3.org/1999/xhtml">
    <div>
        <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="card">
                    <div class="body">
                        <v-select
                            v-model="selectedDoctor"
                            label="fullName"
                            :options="getDoctors"
                            @input="onChangeDoctor"/>

                        <div class="selector row m-t-10 m-b-10">
                            <div class="btn bg-light-blue waves-effect selector-item manage-bookings-selector col-sm-2 m-r-25 m-l-25"
                                @click="onSelectManageBookings">
                                {{ $t('manage.doctor.tabs.manage-booking.title') }}
                            </div>

                            <div class="btn bg-cyan waves-effect selector-item extra-schedule-selector col-sm-2 m-r-25 m-l-25"
                                @click="onSelectExtraSchedule">
                                {{ $t('manage.doctor.tabs.extra-schedule.title') }}
                            </div>

                            <div class="btn bg-teal waves-effect selector-item week-schedule-selector col-sm-2 m-r-25 m-l-25"
                                @click="onSelectWeekSchedule">
                                {{ $t('manage.doctor.tabs.week-schedule.title') }}
                            </div>
                        </div>

                        <div class="content">

                            <div class=" manage-bookings-wrap active in" v-if="selectedDoctor && selector === 'manage-bookings'">
                                <doctors-bookings
                                        :key="selectedDoctor.id"
                                        v-bind:doctor="selectedDoctor.id">
                                </doctors-bookings>
                            </div>
                            <div class=" extra-schedule-wrap" v-if="selectedDoctor && selector === 'extra-schedule'">
                                <extra-schedule
                                        :key="selectedDoctor.id"
                                        v-bind:doctor="selectedDoctor.id">
                                </extra-schedule>
                            </div>
                            <div  class=" week-schedule-wrap" v-if="selectedDoctor && selector === 'week-schedule'">
                                <week-schedule
                                        :key="selectedDoctor.id"
                                        v-bind:doctor="selectedDoctor.id">
                                </week-schedule>
                            </div>
                        </div>
                    </div>
                </div>
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