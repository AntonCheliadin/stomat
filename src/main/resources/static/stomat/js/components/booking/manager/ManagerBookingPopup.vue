<template>
    <div>
        <input @input="onChange" v-model="startDate">
        <span class="err" v-text="startDateError"></span>
        <br>
        <input @input="onChange" v-model="endDate">
        <span class="err" v-text="endDateError"></span>
        <br>
        <v-select v-model="selectedReason" :options="reasons" :clearable="clearable"/>
        <span class="err" v-text="reasonError"></span>
        <br>
        <input @input="onChange" id="firstName" v-model="firstName"/>
        <label for="firstName">Имя</label>
        <span class="err" v-text="firstNameError"></span>
        <br>
        <input @input="onChange" id="lastname" v-model="lastName"/>
        <label for="lastname">Фамилия</label>
        <span class="err" v-text="lastNameError"></span>
        <br>
        <input @input="onChange" id="phone" v-model="phoneNumber"/>
        <label for="phone">телефон</label>
        <span class="err" v-text="phoneNumberError"></span>
        <br>
        <input @input="onChange" id="description" v-model="description"/>
        <label for="description">description</label>
        <span class="err" v-text="descriptionError"></span>
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
    import 'vue-select/dist/vue-select.css';

    export default {
        name: "ManagerBookingPopup",
        props: ['booking', 'reasons'],
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

                selectedReason: this.findOrGetDefaultReason()(this.booking.reason.id),
                clearable: false,

                startDateError: "",
                endDateError: "",
                reasonError: "",
                firstNameError: "",
                lastNameError: "",
                phoneNumberError: "",
                descriptionError: "",
            }
        },
        methods: {
            ...mapGetters(['findOrGetDefaultReason']),
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
                    doctor: this.doctor,
                    reason: this.selectedReason.id
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
                    console.log("Validation error!");
                    this.showErrors(response.body.errors)
                } else {
                    console.log("show error")
                }
            },
            closePopup() {
                this.$emit('vuedals:close');
            },
            showErrors(errors) {
                if (errors) {
                    for (const error of errors) {
                        this[error.field + 'Error'] = error.message;
                    }
                }
            },
            onChange({type, target}) {
                target.nextElementSibling.nextElementSibling.innerText = ""
            }
        }
    }
</script>

<style scoped>
    .err {
        color: #ff360e;
    }

</style>