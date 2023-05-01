<template>
  <v-app>
    <v-app-bar class="bg-grey-darken-4">
      <div class="logo">
        <img src="src/images/image3.jpeg" alt="My Picture">
      </div>
      <div class="logo2">
        <img src="src/images/image4.jpeg" alt="My Picture">
      </div>
      <v-app-bar-title>
      </v-app-bar-title>
      <div class="btn-group">
        <router-link to="/">
          <v-btn class="btn btn-primary mx-3" color="white" variant="primary">Home</v-btn>
        </router-link>
        <router-link to="/food">
          <v-btn class="btn btn-primary mx-3" color="white" variant="primary">Calorie Counter</v-btn>
        </router-link>
        <router-link to="/about">
          <v-btn class=" btn btn-primary mx-3" color="white" variant="primary">About us</v-btn>
        </router-link>
        <router-link to="/signup">
          <v-btn class=" btn btn-primary mx-3" color="white" variant="primary">signup</v-btn>
        </router-link>
      </div>
    </v-app-bar>
    <router-view></router-view>
  </v-app>
</template>


<style>
.btn-white {
  color: white;
}

.logo {
  position: absolute;
  top: -30;
  left: 450px;
}

.logo img {
  width: 93px;
  /* adjust the size as needed */
}

.logo2 {
  position: absolute;
  top: 0;
  left: 0px;
}

.logo2 img {
  width: 450px;
  /* adjust the size as needed */
  height: 63px;
}
</style>

<script>
import router from "../router/index.js";

export default {
  name: "App",
  router,
};
</script>
