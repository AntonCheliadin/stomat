<template>
    <b-row>
        <b-col lg="4" md="6" sm="12">
            <Widget custom-header :title="'<h2>'+$t('doctors.add.title')+'</h2>'">
                <DoctorForm @submit="onSubmit"/>
            </Widget>
        </b-col>
    </b-row>
</template>

<script>
    import Widget from '@/apps/admin/components/Widget/Widget';
    import DoctorForm from "@/apps/admin/components/doctor/DoctorForm";
    import {mapActions} from "vuex";

    export default {
        name: "AddDoctor",
        components: {Widget, DoctorForm},
        methods: {
            ...mapActions(['createDoctor']),
            async onSubmit(submitData) {
                const doc = await this.createDoctor(submitData);
                Messenger().post(this.$t('general.notifications.success.create'));
                this.$router.push({name: 'ShowDoctorPage', params: {id: doc.id}})
            }
        }
    }
</script>

<style scoped>

</style>