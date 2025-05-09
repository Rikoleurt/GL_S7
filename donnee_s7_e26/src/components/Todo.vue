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
                            cols="12"
                            sm="5"
                        >
                            <vTextField
                                v-model="todo.title"
                                :label="'title'"
                                :rules="textRules"
                            />
                        </vCol>
                        <vCol
                            cols="12"
                            sm="2"
                        >
                            <vTextField
                                v-model.number="todo.order"
                                :label="'order'"
                                type="number"
                                :rules="numberRules"
                            />
                        </vCol>
                        <vCol
                            cols="12"
                            sm="3"
                        >
                            <vCheckbox
                                v-model="todo.completed"
                                :label="'completed'"
                            />
                        </vCol>
                        <vCol
                            cols="12"
                        >
                            <vAutocomplete
                                v-model="selTags"
                                :label="'tags'"
                                :items="tags"
                                chips
                                deletable-chips
                                multiple
                                item-text="title"
                                item-value="id"
                            />
                        </vCol>
                        <vCol
                            cols="12"
                        >
                            <VBtn
                                class="mr-4"
                                color="primary"
                                :disabled="!valid"
                                @click="save"
                            >
                                save
                            </VBtn>
                            <VBtn
                                class="mr-4"
                                color="error"
                                @click="remove"
                            >
                                delete
                            </VBtn>
                        </vCol>
                    </VRow>
                </VContainer>
            </vForm>
        </vCardText>
    </vCard>
</template>

<script>
/* eslint-disable no-await-in-loop */
import service from '@/assets/js/service';

export default {
    props: {
        todo: {
            type: Object,
            default: () => {},
        },
        tags: {
            type: Array,
            default: () => [],
        },
    },

    data: () => ({
        selTags: [],
        valid: false,
        textRules: [
            (v) => !!v || 'The value must not be empty',
        ],
        numberRules: [
            (v) => Number.isInteger(v) || 'The value must be an integer number',
            (v) => v > 0 || 'The value must be greater than zero',
        ],
    }),

    mounted() {
        this.selTags = this.todo.tags.map((t) => t.id);
    },

    methods: {
        prepareTodo() {
            return {
                title: this.todo.title,
                order: Number(this.todo.order),
                completed: this.todo.completed,
            };
        },

        async save() {
            let success;
            // Update todo
            const updatedTodo = await service.todos.update(this.todo.id, this.prepareTodo());
            success = updatedTodo !== false;
            // Check if tag associations must be updated
            const oldTags = this.todo.tags.map((t) => t.id);
            const newTags = this.selTags;
            for (let i = 0; i < newTags.length; i += 1) {
                if (!oldTags.includes(newTags[i])) {
                    const res = await service.todos.addTag(this.todo.id, { id: newTags[i] });
                    success = success && res;
                }
            }
            for (let i = 0; i < oldTags.length; i += 1) {
                if (!newTags.includes(oldTags[i])) {
                    const res = await service.todos.removeTag(this.todo.id, oldTags[i]);
                    success = success && res;
                }
            }
            this.$emit('saved', success);
        },

        async remove() {
            const success = await service.todos.delete(this.todo.id);
            this.$emit('deleted', success);
        },
    },

};
</script>
