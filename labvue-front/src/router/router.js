import { createRouter, createWebHistory } from 'vue-router'

import Home from '@/views/Home.vue'
import NotFound from '@/views/NotFound.vue'
import Forbidden from '@/views/Forbidden.vue'
import Login from '@/views/secure/Login.vue'
import Products from '@/views/pages/Products.vue'

const routes = [
    { path: '/', component: Home, name: 'home' },
    { path: '/:pathMatch(.*)*', component: NotFound, name: 'notfound' },
    { path: '/403', component: Forbidden, name: 'forbidden' },

    { path: '/secure/login', component: Login, name: 'login' },
    { path: '/pages/products', component: Products, name: 'products' },
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router