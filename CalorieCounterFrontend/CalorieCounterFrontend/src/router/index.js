import { createRouter, createWebHistory } from 'vue-router';


import Home from "../views/pages/Home.vue"
import Food from "../views/pages/Food.vue"
import NotFound from "../NotFound.vue"
import About from "../views/pages/About.vue"
import Diary from "../views/pages/Diary.vue"


const routes = [
    { path: "/", component: Home },
    {path: "/food", component: Food},
    {path: "/About", component: About},
    {path: "/Diary", component: Diary},
    { path: "/:pathMatch(.*)*", component: NotFound}
]
//the error route should be the last in the array..

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router;