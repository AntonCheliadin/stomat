<template>
    <div class="tables-basic">
        <b-breadcrumb>
            <b-breadcrumb-item>YOU ARE HERE</b-breadcrumb-item>
            <b-breadcrumb-item active>Tables Basic</b-breadcrumb-item>
        </b-breadcrumb>
        <h2 class="page-title">Tables - <span class="fw-semi-bold">Static</span></h2>
        <b-row>
            <b-col lg="12">
                <Widget
                        title="<h5>Table <span class='fw-semi-bold'>Styles</span></h5>"
                        customHeader settings close
                >
                    <h3>SMS</h3>
                </Widget>
            </b-col>
        </b-row>
    </div>
</template>

<script>
    import Widget from '@/components/Widget/Widget';
    import 'imports-loader?jQuery=jquery,this=>window!jquery-sparkline'; // eslint-disable-line

    export default {
        name: 'SMS',
        components: { Widget },
    };
</script>

<style scoped ></style>
