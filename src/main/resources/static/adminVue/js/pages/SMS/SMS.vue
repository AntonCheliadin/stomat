<template>
    <div>
        <b-row>
            <b-col lg="4">
                <Widget>
                    <form class="">
                        <div role="group" class="form-group">
                            <label for="phone-mask" class="d-block">
                                <div md="4" xs="12">
                                    {{$t('sms.mobilePhone')}}
                                </div>
                            </label>
                            <div>
                                <div class="col-md-12">
                                    <input type="text" required
                                           class="form-control"
                                           id="phone-mask"
                                           placeholder="(___) ___-____"
                                           v-mask="'(###) ###-####'"
                                           v-model="phone">
                                </div>
                            </div>
                        </div>

                        <div role="group" class="form-group">
                            <label for="sms-body" class="d-block">
                                <div xs="12">
                                    {{$t('sms.message')}}
                                </div>
                            </label>
                            <div>
                                <div xs="12">
                                    <textarea id="sms-body" rows="3" wrap="soft" class="form-control"
                                              required
                                              v-model="message"></textarea>
                                </div>
                            </div>
                        </div>

                        <div class="clearfix">
                            <div class="float-right">
                                <b-button variant="primary" class="mr-xs" size="sm"
                                          @click="sendSms">
                                    {{$t('sms.send')}}
                                </b-button>
                            </div>
                        </div>
                    </form>
                </Widget>
            </b-col>
        </b-row>
    </div>
</template>

<script>
    import Widget from '@/components/Widget/Widget';

    export default {
        name: 'SMS',
        components: {Widget},
        data() {
            return {
                phone: "",
                message: "",
            }
        },
        methods: {
            sendSms() {
                console.log("send sms", this.phone, this.message)
            }
        }
    };
</script>

<style scoped></style>
