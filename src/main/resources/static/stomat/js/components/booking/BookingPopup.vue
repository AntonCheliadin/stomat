<template>
    <div>
        booking popup
        <p v-text="fromDate"></p>
        <p v-text="toDate"></p>

        <br>
        <input id="firstName" v-model="firstName"/>
        <label for="firstName">Имя</label>
        <br>
        <input id="lastname" v-model="lastName"/>
        <label for="lastname">Фамилия</label>
        <br>
        <input id="phone" v-model="phoneNumber"/>
        <label for="phone">телефон</label>
        <br>
        <br>

        <button @click="save">Записаться</button>
        <button @click="close">Отменить</button>
    </div>

</template>

<script>
    import {default as Vuedals, Component as Vuedal, Bus as VuedalsBus} from 'vuedals';
    import {mapGetters, mapActions, mapMutations} from 'vuex';
    import moment from 'moment'
    import bookingApi from '../../api/bookingApi'
    import HttpStatus from "../../constants/HttpStatus";
    import BookingSuccessPopup from "./BookingSuccessPopup.vue";
    import BookingFailPopup from "./BookingFailPopup.vue";

    export default {
        name: "BookingPopup",
        props: ['event'],
        created() {
            this.bindDefaultParams();
        },
        data() {
            return {
                fromDate: moment(this.event.start).format('YYYY-MM-DD HH:mm'),
                toDate: moment(this.event.end).format('YYYY-MM-DD HH:mm'),
                firstName: "",
                lastName: "",
                phoneNumber: ""
            }
        },
        methods: {
            ...mapMutations(['setBookingParams']),
            ...mapGetters(['getBookingParams']),
            bindDefaultParams() {
                let bookingParams = this.getBookingParams();
                if (bookingParams) {
                    this.firstName = bookingParams.firstName;
                    this.lastName = bookingParams.lastName;
                    this.phoneNumber = bookingParams.phoneNumber;
                }
            },
            save() {
                let bookingParams = {
                    from: this.fromDate,
                    to: this.toDate,
                    firstName: this.firstName,
                    lastName: this.lastName,
                    phoneNumber: this.phoneNumber.replace(/\s/g, ''),
                    doctor: 10
                };
                this.setBookingParams(bookingParams);

                bookingApi.add(bookingParams)
                    .then((response) => {
                        const json = response.json();
                        this.$emit('vuedals:close');
                        this.showSuccessPopup(json);
                    }, (response) => {
                        this.$emit('vuedals:close');
                        this.showFailPopup(response.status);
                    });
            },
            close() {
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