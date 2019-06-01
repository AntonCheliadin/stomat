<template>
    <div>
        <input v-model="startDate">
        <br>
        <input v-model="endDate">
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
        <input id="description" v-model="description"/>
        <label for="description">description</label>
        <br>
        <br>

        <button @click="save">Записаться</button>
        <button @click="closePopup">Отменить</button>
    </div>
</template>

<script>
    import bookingApi from '../../../api/bookingApi'
    import HttpStatus from "../../../constants/HttpStatus";
    import moment from 'moment'
    import {mapGetters, mapMutations, mapActions} from 'vuex';

    export default {
        name: "ManagerBookingPopup",
        props: ['booking'],
        data() {
            return {
                id: this.booking.id,
                startDate: moment(this.booking.startDate).format('YYYY-MM-DD HH:mm'),
                endDate: moment(this.booking.endDate).format('YYYY-MM-DD HH:mm'),
                firstName: this.booking.patient.firstName,
                lastName: this.booking.patient.lastName,
                phoneNumber: this.booking.patient.phone,
                description: this.booking.description,
                doctor: this.booking.doctor,
            }
        },
        methods: {
            ...mapMutations(['addBooking', 'updateBooking']),
            save() {
                let bookingParams = {
                    id: this.id,
                    startDate: this.startDate,
                    endDate: this.endDate,
                    firstName: this.firstName,
                    lastName: this.lastName,
                    phoneNumber: this.phoneNumber ? this.phoneNumber.replace(/\s/g, '') : '',
                    description: this.description,
                    doctor: this.doctor
                };

                if (this.id) {
                    bookingApi.update(bookingParams)
                        .then(this.handleBookingSuccess, this.handleBookingFail);
                } else {
                    bookingApi.add(bookingParams)
                        .then(this.handleBookingSuccess, this.handleBookingFail);
                }

            },
            handleBookingSuccess(response) {
                response.json()
                    .then((json) => {
                        this.closePopup();
                        if (this.id) {
                            this.updateBooking(json)
                        } else {
                            this.addBooking(json)
                        }
                    })
            },
            handleBookingFail(response) {
                if (response.status === HttpStatus.BAD_REQUEST) {
                    console.log("Validation error!")
                } else {
                    console.log("show error")
                }
            },
            closePopup() {
                this.$emit('vuedals:close');
            }
        }
    }
</script>

<style scoped>

</style>