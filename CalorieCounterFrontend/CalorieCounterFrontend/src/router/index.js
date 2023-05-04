import { createRouter, createWebHistory } from 'vue-router';
import { ifAuthenticated } from '../views/components/ifAuthenticated.js';


import Home from "../views/pages/Home.vue"
import Food from "../views/pages/Food.vue"
import NotFound from "../NotFound.vue"
import About from "../views/pages/About.vue"
import Diary from "../views/pages/Diary.vue"
import Signup from "../views/pages/Signup.vue"
import Login from "../views/pages/Login.vue"


const routes = [
    { path: "/", component: Home, beforeEnter: ifAuthenticated.call },
    { path: "/food", component: Food, beforeEnter: ifAuthenticated.call },
    { path: "/About", component: About },
    { path: "/Diary", component: Diary, beforeEnter: ifAuthenticated.call },
    { path: "/Signup", component: Signup },
    { path: "/Login", component: Login },
    { path: "/:pathMatch(.*)*", component: NotFound }
]
//the error route should be the last in the array..

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router;