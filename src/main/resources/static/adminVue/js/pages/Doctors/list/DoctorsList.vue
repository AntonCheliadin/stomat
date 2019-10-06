<template>
    <div class="tables-basic">
        <b-breadcrumb>
            <b-breadcrumb-item  href="/admin/dashboard">Dashboard</b-breadcrumb-item>
            <b-breadcrumb-item>Doctors</b-breadcrumb-item>
            <b-breadcrumb-item active>List</b-breadcrumb-item>
        </b-breadcrumb>
        <b-row>
            <b-col lg="12">
                <Widget title="<h5>Doctors</h5>" customHeader>
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>{{$t('general.person.firstName')}}</th>
                                <th>{{$t('general.person.fathersName')}}</th>
                                <th>{{$t('general.person.lastName')}}</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="doctor in getDoctors" v-bind:key="doctor.id">
                                <td>{{ doctor.id }}</td>
                                <td>{{ doctor.firstName }}</td>
                                <td>{{ doctor.fathersName }}</td>
                                <td>{{ doctor.lastName }}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </Widget>
            </b-col>
        </b-row>
    </div>
</template>

<script>
    import Widget from '@/components/Widget/Widget';
    import {mapGetters, mapActions} from 'vuex'

    export default {
        name: 'DoctorsList',
        components: {Widget},
        computed: mapGetters(['getDoctors']),
        created() {
            this.loadDoctors();
        },
        data() {
            return {};
        },
        methods: {
            ...mapActions(['loadDoctors']),
        },
    };
</script>

<style lang="scss">
    @import '@styles/app';
</style>
