import Vue from "vue";
import VueI18n from "vue-i18n";
import en from './en.json'
import ru from './ru.json'
import ua from './ua.json'

const defaultLocale = 'en';

const languages = {
    en: en,
    ru: ru,
    ua: ua,
};

Vue.use(VueI18n);

export const i18n = new VueI18n({
    locale: LANG,
    fallbackLocale: defaultLocale,
    messages: Object.assign(languages)
});