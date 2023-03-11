<template>
  <div>
    <router-link to="/">Home</router-link>
    <router-link to="/food">Food</router-link>
    <router-view/>
    <h1>Home page </h1>
  </div>
</template>

<script>
import  router  from "../router/index.js";

export default {
  name: "App",
  router,
};
</script>
