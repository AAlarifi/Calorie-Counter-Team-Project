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

      <!-- <div class="dropdown">
  <a class="btn btn-primary dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
    Menu
  </a>

  <ul class="dropdown-menu">
    <router-link to="/">
    <li><a class="btn btn-primary dropdown-item" href="#">Home</a></li>
  </router-link>
  <router-link to="/food">
    <li><a class="btn btn-primary dropdown-item" href="#">Calorie Counter</a></li>
  </router-link>
  <router-link to="/about">
    <li><a class="btn btn-primary dropdown-item" href="#">About us</a></li>
  </router-link>
  <router-link to="/signup">
    <li><a class="btn btn-primary dropdown-item" href="#">Signup</a></li>
  </router-link>
  <router-link to="/Login">
    <li><a class="btn btn-primary dropdown-item" href="#">Login</a></li>
  </router-link>
  </ul>
</div> -->


      <div class="btn-group">
        <router-link to="/">
          <v-btn class="btn btn-primary mx-3" color="white" variant="primary" v-if="isAuthenticated">Home</v-btn>
        </router-link>
        <router-link to="/food">
          <v-btn class="btn btn-primary mx-3" color="white" variant="primary" v-if="isAuthenticated">Calorie Counter</v-btn>
        </router-link>
        <router-link to="/about">
          <v-btn class=" btn btn-primary mx-3" color="white" variant="primary" v-if="!isAuthenticated">About us</v-btn>
        </router-link>
        <router-link to="/signup">
          <v-btn class=" btn btn-primary mx-3" color="white" variant="primary" v-if="!isAuthenticated">signup</v-btn>
        </router-link>
        <router-link to="/Login">
          <v-btn class=" btn btn-primary mx-3" color="white" variant="primary" v-if="!isAuthenticated">Login</v-btn>
        </router-link>
        <v-btn class=" btn btn-primary mx-3" color="white" variant="primary" @click="logoutFuncion" v-if="isAuthenticated">logout</v-btn>
      </div>
    </v-app-bar>
    <router-view></router-view>
    <v-snackbar v-model="snackbar">
      {{ snackbarError }}
      <template v-slot:actions>
        <v-btn color="purple" variant="text" @click="snackbar = false">
          Close
        </v-btn>
      </template>
    </v-snackbar>
  </v-app>
</template>

<script>
import router from "../router/index.js";
import userServices from "./../services/user.service";

export default {
  name: "App",
  router,
  data() {
    return {
      snackbarError: "",
      snackbar: false
    }
  },
  computed: {
    isAuthenticated() {
      return localStorage.getItem('session_token') !== null
    }},
  methods: {
    logoutFuncion() {
      userServices.logout()
        .then(() => {
          localStorage.removeItem('session_token')
          this.$router.push('/login')
          location.reload()
        })
        .catch((error) => {
          this.snackbar = true;
          this.snackbarError = error;
          console.log(error)
        })
    }
  }
};
</script>

<style>
.bg-grey-darken-4 {
  background-color: rgba(0, 0, 0, 0.5);
  position: relative;
  z-index: 3;
}

.dropdown {
  position: relative;
  z-index: 9999;
  overflow: auto;
}

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
  /* position: absolute; */
  top: 0;
  left: 0px;
}

.logo2 img {
  width: 450px;
  /* adjust the size as needed */
  height: 63px;
}
</style>
