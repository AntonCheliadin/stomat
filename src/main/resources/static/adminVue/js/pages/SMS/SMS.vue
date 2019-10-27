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
                                          @click="onSend">
                                    {{$t('sms.send')}}
                                </b-button>
                            </div>
                        </div>

                        <div v-if="sendSmsResponse" class="send-sms-response">
                            {{sendSmsResponse}}
                        </div>
                    </form>
                </Widget>
            </b-col>

            <b-col lg="4">
                <Widget custom-header :title="'<h2>'+$t('sms.balance')+'</h2>'">

                    <span v-if="getBalance"><strong>{{getBalance}}</strong> UAH</span>

                    <div class="clearfix">
                        <div class="float-right">
                            <b-button variant="primary" class="mr-xs" size="sm"
                                      v-if="!getBalance"
                                      @click="onCheckBalance">
                                {{$t('sms.checkBalance')}}
                            </b-button>
                        </div>
                    </div>
                </Widget>
            </b-col>
        </b-row>
    </div>
</template>

<script>
    import Widget from '@/components/Widget/Widget';
    import {mapActions, mapGetters} from "vuex";

    export default {
        name: 'SMS',
        components: {Widget},
        data() {
            return {
                phone: "",
                message: "",
                sendSmsResponse: null
            }
        },
        computed: mapGetters('smsModule', ['getBalance']),
        methods: {
            ...mapActions('smsModule', ['fetchBalance', 'sendSms']),
            async onSend() {
                const resp = await this.sendSms({
                    recipient: this.phone,
                    message: this.message
                });

                this.sendSmsResponse = resp.documentElement.innerHTML;
            },
            onCheckBalance() {
                this.fetchBalance();
            }
        },
    };
</script>

<style scoped></style>
