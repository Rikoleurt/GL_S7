import Vue from 'vue';
import Router from 'vue-router';
import vuetify from './plugins/vuetify';

import todosComponent from './components/Todos.vue';
import tagsComponent from './components/Tags.vue';
import App from './App.vue';

Vue.config.productionTip = false;

const router = new Router({
    routes: [
        { path: '/', redirect: '/todos' },
        { path: '/todos', component: todosComponent },
        { path: '/tags', component: tagsComponent },
    ],
});

Vue.use(Router);

new Vue({
    vuetify,
    router,
    render: (h) => h(App),
}).$mount('#app');
