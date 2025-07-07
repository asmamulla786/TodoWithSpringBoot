const API_BASE_URL = "http://localhost:8080/todos";


const fetchTodos = async () => {
    const response = await fetch(API_BASE_URL);
    return await response.json();
};

const saveTodo = async (todo) => {
    const response = await fetch(API_BASE_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(todo),
    });

    if (!response.ok) throw new Error("Failed to save todo");
    return await response.json();
};

const deleteTodo = async (id) => {
    const response = await fetch(`${API_BASE_URL}/${id}`, { method: "DELETE" });
    if (!response.ok) throw new Error("Failed to delete todo");
};

const updateTodo = async (todo) => {
    const response = await fetch(`${API_BASE_URL}/${todo.id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(todo),
    });

    if (!response.ok) throw new Error("Failed to update todo");
};

const createToggleButton = (todo) => {
    const btn = document.createElement("button");
    btn.textContent = todo.isDone ? "Done" : "Undone";
    btn.className = `toggle-btn ${todo.isDone ? "done" : "undone"}`;
    btn.addEventListener("click", async () => {
        await toggleTodoStatus(todo);
    });
    return btn;
};

const createDeleteButton = (id) => {
    const btn = document.createElement("button");
    btn.textContent = "Delete";
    btn.className = "delete-btn";
    btn.addEventListener("click", async () => {
        try {
            await deleteTodo(id);
            await renderTodos();
        } catch (error) {
            console.error("Error deleting todo:", error);
        }
    });
    return btn;
};

const createTodoTitleElement = (todo) => {
    const title = document.createElement("span");
    title.textContent = `📝 ${todo.title}`;
    title.className = "todo-title";
    return title;
};

const createTodoActionsContainer = (todo) => {
    const toggleBtn = createToggleButton(todo);
    const deleteBtn = createDeleteButton(todo.id);

    const actionContainer = document.createElement("div");
    actionContainer.className = "todo-actions";
    actionContainer.appendChild(toggleBtn);
    actionContainer.appendChild(deleteBtn);

    return actionContainer;
};


const createTodoElement = (todo) => {
    const container = document.createElement("div");
    container.className = "todo-item";

    const titleElement = createTodoTitleElement(todo);
    const actionElement = createTodoActionsContainer(todo);

    container.appendChild(titleElement);
    container.appendChild(actionElement);

    return container;
};


const renderTodos = async () => {
    const todos = await fetchTodos();
    const listContainer = document.getElementById("todo-list");
    listContainer.innerHTML = "";

    todos.forEach(todo => {
        listContainer.appendChild(createTodoElement(todo));
    });
};

const toggleTodoStatus = async (todo) => {
    const updated = { ...todo, isDone: !todo.isDone };
    try {
        await updateTodo(updated);
        await renderTodos();
    } catch (error) {
        console.error("Error updating todo:", error);
    }
};

const handleFormSubmit = async (event) => {
    event.preventDefault();
    const titleInput = document.getElementById("title");
    const todo = { title: titleInput.value, isDone: false };

    try {
        await saveTodo(todo);
        await renderTodos();
        event.target.reset();
    } catch (error) {
        console.error("Error saving todo:", error);
        alert("Error saving ToDo ❌");
    }
};

document.addEventListener("DOMContentLoaded", () => {
    renderTodos();
    document.getElementById("todo-form").addEventListener("submit", handleFormSubmit);
});
