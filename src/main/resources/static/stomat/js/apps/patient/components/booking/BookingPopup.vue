<template>
    <div>
        <p v-text="startDate"></p>
        <p v-text="endDate"></p>

        <br>
        <input id="firstName" v-model="firstName"/>
        <label for="firstName">{{$t('general.person.firstName')}}</label>
        <br>
        <input id="lastname" v-model="lastName"/>
        <label for="lastname">{{$t('general.person.lastName')}}</label>
        <br>
        <input id="phone" v-model="phoneNumber"/>
        <label for="phone">{{$t('general.person.phone')}}</label>
        <br>
        <input id="description" v-model="description"/>
        <label for="description">{{$t('general.description')}}</label>
        <br>
        <br>

        <button @click="save">{{$t('manage.doctor.tabs.make-booking.submit')}}</button>
        <button @click="closePopup">{{$t('general.actions.cancel')}}</button>
    </div>

</template>

<script>
    import {Bus as VuedalsBus} from 'vuedals';
    import {mapGetters, mapMutations, mapActions} from 'vuex';
    import moment from 'moment'
    import patientBookingApi from '../../../../api/booking/patientBookingApi'
    import BookingSuccessPopup from "./BookingSuccessPopup.vue";
    import BookingFailPopup from "./BookingFailPopup.vue";
    import HttpStatus from "../../../../constants/HttpStatus";

    export default {
        name: "BookingPopup",
        props: ['event', 'doctor', 'reason'],
        created() {
            this.bindDefaultParams();
        },
        data() {
            return {
                startDate: moment(this.event.start).format('YYYY-MM-DD HH:mm'),
                endDate: moment(this.event.end).format('YYYY-MM-DD HH:mm'),
                firstName: "",
                lastName: "",
                phoneNumber: "",
                description: "",
            }
        },
        methods: {
            ...mapMutations(['setBookingParams']),
            ...mapActions(['loadFreeTimesAction']),
            ...mapGetters(['getBookingParams']),
            bindDefaultParams() {
                let bookingParams = this.getBookingParams();
                if (bookingParams) {
                    this.firstName = bookingParams.firstName;
                    this.lastName = bookingParams.lastName;
                    this.phoneNumber = bookingParams.phoneNumber;
                    this.description = bookingParams.description;
                }
            },
            save() {
                let bookingParams = {
                    startDate: this.startDate,
                    endDate: this.endDate,
                    firstName: this.firstName,
                    lastName: this.lastName,
                    phoneNumber: this.phoneNumber.replace(/\s/g, ''),
                    description: this.description,
                    doctor: this.doctor.id,
                    reason: this.reason.id,
                };
                this.setBookingParams(bookingParams);

                patientBookingApi.add(bookingParams)
                    .then((response) => {
                        this.closePopup();
                        this.showSuccessPopup(bookingParams);
                    }, (response) => {
                        if (response.status === HttpStatus.BAD_REQUEST) {
                            console.log("Validation error!")
                        } else {
                            this.closePopup();
                            this.showFailPopup(response.status);
                        }
                    });
            },
            closePopup() {
                this.loadFreeTimesAction({doctor: this.doctor.id, reason: this.reason.id});
                this.$emit('vuedals:close');
            },
            showSuccessPopup(booking) {
                VuedalsBus.$emit('new', {
                    name: 'success-booking-popup',
                    component: BookingSuccessPopup,
                    props: {
                        booking: booking
                    },
                });
            },
            showFailPopup(responseCode) {
                VuedalsBus.$emit('new', {
                    name: 'fail-booking-popup',
                    component: BookingFailPopup,
                    props: {
                        responseCode: responseCode
                    },
                });
            }
        }

    }
</script>

<style scoped>

</style>