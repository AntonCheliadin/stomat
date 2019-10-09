import Vue from 'vue'

import { library } from '@fortawesome/fontawesome-svg-core';
import {
    faCoffee, faSpinner, faWrench, faAmbulance, faEdit, faCircle, faCheck, faChessQueen,
    faPlus, faEquals, faArrowLeft, faArrowRight, faPencilAlt, faComment, faHeadphones, faSquare,
    faCalendar, faCertificate, faEnvelope, faTimes, faBookmark, faHeart, faPlay,
    faSun, faMoon, faStar, faAlignJustify
} from '@fortawesome/free-solid-svg-icons';
import { faJs, faVuejs, faFacebookF } from '@fortawesome/free-brands-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'


library.add(
    faCoffee, faSpinner, faWrench, faAmbulance, faSquare,
    faEdit, faCircle, faCheck, faChessQueen, faHeadphones,
    faPlus, faEquals, faArrowRight, faPencilAlt, faComment,
    faCalendar, faCertificate, faEnvelope, faTimes, faBookmark,
    faHeart, faPlay, faSun, faMoon, faStar,
    faJs, faVuejs, faFacebookF, faArrowLeft, faAlignJustify);

Vue.component('font-awesome-icon', FontAwesomeIcon);