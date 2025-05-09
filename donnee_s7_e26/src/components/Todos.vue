<template>
    <vContainer>
        <vCard
            class="mb-4"
            color="light-green lighten-5"
        >
            <vCardTitle>
                Create new todo
            </vCardTitle>
            <vCardText>
                <vForm
                    ref="form"
                    v-model="valid"
                >
                    <vContainer>
                        <vRow>
                            <vCol
                                cols="12"
                                sm="5"
                            >
                                <vTextField
                                    v-model="newTodo.title"
                                    :label="'title'"
                                    :rules="textRules"
                                    required
                                />
                            </vCol>
                            <vCol
                                cols="12"
                                sm="2"
                            >
                                <vTextField
                                    v-model.number="newTodo.order"
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
                                    v-model="newTodo.completed"
                                    :label="'completed'"
                                />
                            </vCol>
                            <vCol
                                cols="12"
                                sm="2"
                            >
                                <VBtn
                                    color="primary"
                                    :disabled="!valid"
                                    @click="saveNewTodo"
                                >
                                    save
                                </VBtn>
                            </vCol>
                        </vRow>
                    </vContainer>
                </vForm>
            </vCardText>
        </vCard>
        <vCard
            class="mb-4"
            color="light-green lighten-5"
        >
            <vCardTitle>
                Filters
            </vCardTitle>
            <vCardText>
                <vContainer>
                    <vRow>
                        <vCol
                            cols="8"
                            sm="4"
                        >
                            <vSelect
                                v-model="selTag"
                                :label="'tag'"
                                :items="tags"
                                item-text="title"
                                item-value="id"
                                @change="setShowTodos()"
                            />
                        </vCol>
                        <vCol
                            cols="4"
                            sm="2"
                        >
                            <VBtn
                                color="primary"
                                @click="clearFilter()"
                            >
                                clear
                            </VBtn>
                        </vCol>
                        <vCol
                            cols="6"
                            sm="3"
                        >
                            <v-switch
                                v-model="showCompleted"
                                label="completed"
                            />
                        </vCol>
                        <vCol
                            cols="6"
                            sm="3"
                        >
                            <v-switch
                                v-model="showUncompleted"
                                label="uncompleted"
                            />
                        </vCol>
                    </vRow>
                </vContainer>
            </vCardText>
        </vCard>
        <Todo
            v-for="todo in todos"
            v-show="todo.show
                && (todo.completed && showCompleted || !todo.completed && showUncompleted)"
            :key="todo.id"
            :todo="todo"
            :tags="tags"
            @saved="saved"
            @deleted="deleted"
        />
        <vSnackbar
            v-model="snackBar"
            :top="true"
            :color="snackBarColor"
            :timeout="2000"
        >
            {{ snackBarText }}
        </vSnackbar>
    </vContainer>
</template>

<script>

import service from '@/assets/js/service';
import todoComponent from '@/components/Todo.vue';

export default {
    components: {
        Todo: todoComponent,
    },

    data: () => ({
        snackBar: false,
        snackBarText: '',
        snackBarColor: '',
        newTodo: {
            title: null,
            order: null,
            completed: false,
        },
        todos: [],
        tags: [],
        selTag: null,
        showCompleted: true,
        showUncompleted: true,
        valid: false,
        textRules: [
            (v) => !!v || 'The value must not be empty',
        ],
        numberRules: [
            (v) => Number.isInteger(v) || 'The value must be an integer number',
            (v) => v > 0 || 'The value must be greater than zero',
        ],
    }),

    async mounted() {
        this.todos = await service.todos.list();
        this.todos.sort((a, b) => a.order - b.order);
        this.tags = await service.tags.list();
        this.tags.sort((a, b) => a.title - b.title);
        this.selTags = this.tags.map((t) => t.id);
        this.setShowTodos();
    },

    methods: {
        async saved(success) {
            this.snackBar = false;
            this.snackBarText = success ? 'Todo saved!' : 'Could not save todo!';
            this.snackBarColor = success ? 'success' : 'error';
            this.snackBar = true;
            this.todos = await service.todos.list();
            this.todos.sort((a, b) => a.order - b.order);
            this.setShowTodos();
        },
        async deleted(success) {
            this.snackBar = false;
            this.snackBarText = success ? 'Todo deleted!' : 'Could not delete todo!';
            this.snackBarColor = success ? 'success' : 'error';
            this.snackBar = true;
            this.todos = await service.todos.list();
            this.todos.sort((a, b) => a.order - b.order);
            this.setShowTodos();
        },
        async saveNewTodo() {
            const res = await service.todos.create(this.newTodo);
            this.newTodo = {
                title: null,
                order: null,
                completed: false,
            };
            this.saved(res !== false);
            this.$refs.form.reset();
        },

        setShowTodos() {
            for (let i = 0; i < this.todos.length; i += 1) {
                const todo = this.todos[i];
                const tags = todo.tags.map((t) => t.id);
                todo.show = tags.includes(this.selTag) || this.selTag === null;
            }
        },

        clearFilter() {
            this.selTag = null;
            this.setShowTodos();
        },

        async refresh() {
            this.todos = await service.todos.list();
            this.todos.sort((a, b) => a.order - b.order);
            this.tags = await service.tags.list();
            this.tags.sort((a, b) => a.title - b.title);
            this.selTags = this.tags.map((t) => t.id);
            this.setShowTodos();
        },
    },
};
</script>
