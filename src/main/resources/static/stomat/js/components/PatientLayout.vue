<template xmlns:v="http://www.w3.org/1999/xhtml">
    <div>
        <div class="content">
            <div class="make-booking-wrap">
                <v-select
                        v-model="selectedDoctor"
                        label="fullName"
                        :options="getDoctors"
                        @input="onChangeDoctor"/>

                <v-select
                        v-model="selectedReason"
                        label="name"
                        :options="getReasons"
                        @input="onChangeReason"/>

                <div class="make-booking-wrap" v-if="selectedDoctor && selectedReason">
                    <booking v-bind:doctor="selectedDoctor" v-bind:reason="selectedReason"></booking>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import {mapGetters, mapActions, mapMutations} from 'vuex'
    import Booking from "./booking/Booking.vue";
    import 'vue-select/dist/vue-select.css';

    export default {
        name: "PatientLayout",
        computed: mapGetters(['getDoctors', 'getReasons']),
        components: {Booking},
        created() {
            let that = this;
            this.loadDoctors()
                .then(() => {
                    that.selectedDoctor = that.getDoctors[0];
                });
            this.loadReasons()
                .then(() => {
                    that.selectedReason = that.findOrGetDefaultReason()()
                });
        },
        data() {
            return {
                selectedReason: null,
                selectedDoctor: null,
                // bookingDoctor: null,
                // bookingReason: null
            }
        },
        methods: {
            ...mapGetters(['findOrGetDefaultReason']),
            ...mapActions(['loadDoctors', 'loadReasons', 'loadFreeTimesAction']),
            onChangeDoctor(doctor) {
                // this.bookingDoctor = doctor;
                this.loadSlots();
            },
            onChangeReason(reason) {
                // this.bookingReason = reason;
                this.loadSlots();
            },
            loadSlots() {
                if (this.selectedDoctor && this.selectedReason) {
                    this.loadFreeTimesAction({doctor: this.selectedDoctor.id, reason: this.selectedReason.id});
                }
            }
        }
    }
</script>

<style scoped>

</style>