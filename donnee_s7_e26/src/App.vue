<template>
    <v-app>
        <v-app-bar
            app
        >
            <vContainer>
                <vRow class="pt-5">
                    <vCol cols="1">
                        <vImg
                            alt="Vuetify Logo"
                            class="shrink mr-2"
                            contain
                            src="https://cdn.vuetifyjs.com/images/logos/vuetify-logo-dark.png"
                            transition="scale-transition"
                            width="40"
                        />
                    </vCol>
                    <vCol cols="2">
                        <vBtn text>
                            <router-link
                                to="/todos"
                            >
                                Todos
                            </router-link>
                        </vBtn>
                    </vCol>
                    <vCol cols="2">
                        <vBtn text>
                            <router-link
                                to="/tags"
                            >
                                Tags
                            </router-link>
                        </vBtn>
                    </vCol>
                    <vCol cols="5">
                        <vTextField
                            v-model="baseUrl"
                        />
                    </vCol>
                    <vCol cols="2">
                        <vBtn
                            @click="setBaseUrl()"
                        >
                            Set URL
                        </vBtn>
                    </vCol>
                </vRow>
            </vContainer>
        </v-app-bar>

        <v-content>
            <router-view ref="child" />
        </v-content>
    </v-app>
</template>

<script>

import service from '@/assets/js/service';

export default {
    name: 'App',

    data: () => ({
        baseUrl: 'http://todo.thing.zone',
    }),

    created() {
        service.setBaseUrl(this.baseUrl);
    },

    methods: {
        setBaseUrl() {
            service.setBaseUrl(this.baseUrl);
            this.$refs.child.refresh();
        },
    },

};
</script>
