<template>
  <v-app>
    <v-app-bar class="bg-grey-darken-4">
      <v-app-bar-title>
        <router-link to="/">Home</router-link>
      </v-app-bar-title>
      <router-link to="/food"><v-btn variant="text">Calorie Counter</v-btn></router-link>
      <router-link to="/About"><v-btn variant="text">About us</v-btn></router-link>
    </v-app-bar>
    <router-view></router-view>
  </v-app>
</template>

<script>
import  router  from "../router/index.js";

export default {
  name: "App",
  router,
};
</script>
