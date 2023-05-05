<template>
<v-app>
      <div>
        <nav class="navbar navbar-expand-sm bg-black text-white ">
          <div class="container-fluid">
            <a class="navbar-brand" href="/">
              <img src="src/images/image3.jpeg" alt="Website Logo" width="40" height="40"
                class="d-inline-block align-top">
            </a>
            <button class="navbar-toggler text-white" type="button" data-toggle="collapse" data-target="#navbarNav"
              aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
              <ul class="navbar-nav ">
                <li class="nav-item">
                  <router-link class="nav-link text-decoration-none  text-white" to="/about">About us</router-link>
                </li>
                <li class="nav-item">
                  <router-link class="btn btn-light " to="/login" v-if="!isAuthenticated">Login</router-link>
                </li>
                <li class="nav-item">
                  <router-link class="btn btn-light " to="/signup" v-if="!isAuthenticated">Signup</router-link>
                </li>
                <li class="nav-item">
                  <router-link class="btn btn-light " to="/food" v-if="isAuthenticated">Food Diary</router-link>
                </li>
                <li class="nav-item">
                  <router-link class="nav-link text-decoration-none  text-white" to="/"
                    v-if="isAuthenticated">Dashboard</router-link>
                </li>
                <li class="nav-item">
                  <button class="btn btn-light" @click="logoutFunction" v-if="isAuthenticated">Logout</button>
                </li>
              </ul>
            </div>
          </div>
        </nav>
        <router-view />
      </div>
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
import { createRouter, createWebHistory } from 'vue-router'


export default {
  name: "App",
  router,
  data() {
    return {
      snackbarError: "",
      snackbar: false,
      isAuthenticated: false
    }
  },
  watch: {
    $route: {
      handler: function(){ 
      this.isAuthenticated = localStorage.getItem('session_token') !== null
      },
    immediate: true
  }
  },
  methods: {
    logoutFunction() {
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
  },
  mounted() {
    this.$router = createRouter({
      history: createWebHistory(),
      routes: []
    })
  }
};
// This is an even listetener that togles the nav bar
document.addEventListener("DOMContentLoaded", function () {
  var navbarToggler = document.querySelector(".navbar-toggler");
  var navbarCollapse = document.querySelector(".navbar-collapse");

  navbarToggler.addEventListener("click", function () {
    if (navbarCollapse.style.display === "block") {
      navbarCollapse.style.display = "none";
    } else {
      navbarCollapse.style.display = "block";
    }
  });
});
</script>

<style>
/* Centers the navigation links and buttons on the top of the screen. */
/* Centers the logo/icon and the links and buttons in the navbar. */
.navbar-brand {
  display: flex;
  align-items: center;
}

.navbar-nav {
  display: flex;
  align-items: center;
}

/* Fills the toggler icon background with white, making it visible. */
.navbar-toggler-icon {
  background-color: white;
}

/* Changes the size of the nav bar. */
@media screen and (max-width: 991px) {
  .navbar-nav {
    flex-direction: column;
    align-items: center;
  }

  .navbar-nav .nav-item {
    margin-bottom: 10px;
  }

  .navbar-brand {
    margin-bottom: 20px;
  }
}
</style>
