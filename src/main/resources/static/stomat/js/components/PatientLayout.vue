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

                <div class="make-booking-wrap" v-if="bookingDoctor && bookingReason">
                    <booking v-bind:doctor="bookingDoctor" v-bind:reason="bookingReason"></booking>
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
            this.loadDoctors();
            this.loadReasons();
        },
        data() {
            return {
                selectedReason: this.findOrGetDefaultReason()(),
                selectedDoctor: "",
                bookingDoctor: null,
                bookingReason: null
            }
        },
        methods: {
            ...mapGetters(['findOrGetDefaultReason']),
            ...mapActions(['loadDoctors', 'loadReasons', 'loadFreeTimesAction']),
            onChangeDoctor(doctor) {
                this.bookingDoctor = doctor;
                this.loadSlots();
            },
            onChangeReason(reason) {
                this.bookingReason = reason;
                this.loadSlots();
            },
            loadSlots() {
                if (this.bookingDoctor && this.bookingReason) {
                    this.loadFreeTimesAction({doctor: this.bookingDoctor.id, reason: this.bookingReason.id});
                }
            }
        }
    }
</script>

<style scoped>

</style>