<template>
    <vContainer>
        <vCard
            class="mb-4"
            color="light-green lighten-5"
        >
            <vCardTitle>
                Create new tag
            </vCardTitle>
            <vCardText>
                <vForm
                >
                    <vContainer>
                        <vRow>
                            <vCol
                                cols="9"
                            >
                                <vTextField
                                />
                            </vCol>
                            <vCol
                                cols="3"
                            >
                                <VBtn
                                >
                                </VBtn>
                            </vCol>
                        </vRow>
                    </vContainer>
                </vForm>
            </vCardText>
        </vCard>
    </vContainer>
</template>

<script>

import service from '@/assets/js/service';
import TagComponent from '@/components/Tag.vue';

export default {
    components: {
        Tag: TagComponent,
    },

    data: () => ({
    }),

    async mounted() {
    },

    methods: {
    },
};
</script>
