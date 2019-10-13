<template>
    <div>
        <b-form @submit="onSubmit">
            <b-form-group
                    id="doctor-group-1" :label="$t('general.person.email')" label-for="input-1">
                <b-form-input
                        id="input-1"
                        v-model="doctor.email"
                        type="email"
                        required
                        :placeholder="$t('general.person.email')"
                ></b-form-input>
            </b-form-group>

            <b-form-group id="doctor-group-2" :label="$t('general.person.firstName')" label-for="input-2">
                <b-form-input
                        id="input-2"
                        v-model="doctor.firstName"
                        required
                        :placeholder="$t('general.person.firstName')"
                ></b-form-input>
            </b-form-group>

            <b-form-group id="doctor-group-3" :label="$t('general.person.fathersName')" label-for="input-3">
                <b-form-input
                        id="input-3"
                        v-model="doctor.fathersName"
                        required
                        :placeholder="$t('general.person.fathersName')"
                ></b-form-input>
            </b-form-group>

            <b-form-group id="doctor-group-4" :label="$t('general.person.lastName')" label-for="input-4">
                <b-form-input
                        id="input-4"
                        v-model="doctor.lastName"
                        required
                        :placeholder="$t('general.person.lastName')"
                ></b-form-input>
            </b-form-group>

            <b-button v-if="deleteEnabled" variant="outline-danger" @click="onDelete">Delete</b-button>
            <b-button type="submit" variant="primary">{{$t('general.actions.save')}}</b-button>
        </b-form>
    </div>
</template>

<script>
    export default {
        name: "DoctorForm",
        props: {
            doctor: {type: Object, default: Object},
            deleteEnabled: {type: Boolean, default: false}
        },
        methods: {
            onSubmit(evt) {
                evt.preventDefault();
                this.$emit('submit', this.doctor)
            },
            onDelete() {
                this.$emit('delete')
            }
        }
    }
</script>

<style scoped>

</style>