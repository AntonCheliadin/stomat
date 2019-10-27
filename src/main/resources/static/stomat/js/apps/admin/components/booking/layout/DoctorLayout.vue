<template>
    <b-row>
        <b-col lg="6" sm="12">
            <v-select
                    v-model="selectedDoctor"
                    label="fullName"
                    :placeholder="$t('manage.doctor.layout.chooseDoctor')"
                    :options="getDoctors"
                    @input="onChangeDoctor"/>
        </b-col>
        <b-col sm="12">
            <div v-if="selectedDoctor">
                <button
                        v-for="tab in tabs"
                        v-bind:key="tab"
                        v-bind:class="['tab-button', { active: currentTab === tab }]"
                        v-on:click="currentTab = tab"
                >
                    {{ $t('manage.doctor.tabs.'+tab+'.title') }}
                </button>

                <component
                        v-bind:is="currentTabComponent"
                        v-bind:key="selectedDoctor.id"
                        v-bind:doctor="selectedDoctor.id"
                ></component>
            </div>
        </b-col>
    </b-row>
</template>

<script>
    import {mapGetters, mapActions} from 'vuex'
    import WeekSchedule from "../../schedule/WeekSchedule.vue";
    import ExtraSchedule from "../../schedule/ExtraSchedule.vue";
    import DoctorsBookings from "../manager/DoctorsBookings.vue";

    export default {
        name: "DoctorLayout",
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
                selectedDoctor: null,
                currentTab: 'doctors-bookings',
                tabs: ['doctors-bookings', 'extra-schedule', 'week-schedule']
            }
        },
        computed: {
            ...mapGetters(['getDoctors']),
            currentTabComponent: function () {
                return this.currentTab
            }
        },
        methods: {
            ...mapGetters(['findDoctorById']),
            ...mapActions(['loadDoctors', 'loadReasons']),
            onChangeDoctor(doctor) {
                this.selectedDoctor = doctor;
            },
        }
    }
</script>

<style scoped>
    @import '~@fullcalendar/core/main.css';
    @import '~@fullcalendar/timegrid/main.css';

    .tab-button {
        padding: 6px 10px;
        border-top-left-radius: 3px;
        border-top-right-radius: 3px;
        border: 1px solid #ccc;
        cursor: pointer;
        background: #f0f0f0;
        margin: 10px -1px 10px 0;
    }

    .tab-button:hover {
        background: #e0e0e0;
    }

    .tab-button.active {
        background: #b4b4b4;
    }
</style>