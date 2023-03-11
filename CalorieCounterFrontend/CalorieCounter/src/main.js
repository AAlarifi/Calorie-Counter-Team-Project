import { createApp } from 'vue'
import './style.css'
import router from "./router"
import 'bootstrap/dist/css/bootstrap.css'

// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

const vuetify = createVuetify({
   components,
  directives,
})

import App from './views/App.vue'

createApp(App).use(vuetify).use(router).mount('#app')
