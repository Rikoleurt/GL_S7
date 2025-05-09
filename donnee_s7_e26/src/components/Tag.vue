<template>
    <vCard
        class="mb-4"
        color="brown lighten-4"
    >
        <vCardText>
            <vForm v-model="valid">
                <VContainer>
                    <vRow>
                        <vCol
                            cols="8"
                        >
                        </vCol>
                        <vCol
                            cols="2"
                        >
                        </vCol>
                        <vCol
                            cols="2"
                        >
                        </vCol>
                    </VRow>
                </VContainer>
            </vForm>
        </vCardText>
    </vCard>
</template>

<script>

import service from '@/assets/js/service';

export default {
    props: {
        tag: {
            type: Object,
            default: () => {},
        },
    },

    data: () => ({
    }),

    methods: {

    },

};
</script>
