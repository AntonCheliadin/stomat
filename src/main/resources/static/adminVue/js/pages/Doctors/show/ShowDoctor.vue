<template>
    <b-row>
        <b-col lg="4" md="6" sm="12">
            <Widget v-if="doctor">
                <DoctorForm :doctor="doctor" deleteEnabled @delete="onDelete" @submit="onSubmit"/>
            </Widget>
        </b-col>
    </b-row>
</template>

<script>
    import Widget from '@/components/Widget/Widget';
    import {mapActions} from "vuex";
    import DoctorForm from "@/components/doctor/DoctorForm";

    export default {
        name: "ShowDoctor",
        components: {Widget, DoctorForm},
        created() {
            this.loadDoctorByIdFromUri();
        },
        data() {
            return {
                doctor: null
            };
        },
        methods: {
            ...mapActions(['loadDoctor', 'updateDoctor', 'deleteDoctor']),
            async loadDoctorByIdFromUri() {
                this.doctor = await this.loadDoctor(this.$route.params.id);
            },
            async onSubmit(submitData) {
                await this.updateDoctor(submitData);

                Messenger().post(this.$t('general.notifications.success.update'));
            },
            async onDelete() {
                await this.deleteDoctor(this.doctor);
                Messenger().post(this.$t('general.notifications.success.delete'));
                this.$router.push({name: 'DoctorsListPage'})
            },
        },
    }
</script>

<style scoped>

</style>