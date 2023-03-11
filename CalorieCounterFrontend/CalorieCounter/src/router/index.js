import { createRouter, createWebHistory } from 'vue-router';


import Home from "../views/pages/Home-2.vue"
import Food from "../views/pages/Food.vue"
import NotFound from "../NotFound.vue"


const routes = [
    { path: "/", component: Home },
    {path: "/food", component: Food},
    { path: "/:pathMatch(.*)*", component: NotFound}
]
//the error route should be the last in the array..

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router;