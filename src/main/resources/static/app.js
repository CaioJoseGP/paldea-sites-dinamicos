document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("crudForm");

    if (!form) {
        return;
    }

    const buttons = Array.from(document.querySelectorAll(".crud-mode"));
    const title = document.getElementById("crudModeTitle");
    const description = document.getElementById("crudModeDescription");
    const hint = document.getElementById("crudModeHint");
    const submit = document.getElementById("crudSubmit");

    const wrappers = {
        id: document.querySelector('[data-field="id"]'),
        nome: document.querySelector('[data-field="nome"]'),
        categoria: document.querySelector('[data-field="categoria"]'),
        preco: document.querySelector('[data-field="preco"]'),
        descricao: document.querySelector('[data-field="descricao"]')
    };

    const inputs = {
        id: document.getElementById("crudId"),
        nome: document.getElementById("crudNome"),
        categoria: document.getElementById("crudCategoria"),
        preco: document.getElementById("crudPreco"),
        descricao: document.getElementById("crudDescricao")
    };

    const endpoints = {
        inserir: form.dataset.endpointInserir,
        atualizar: form.dataset.endpointAtualizar,
        buscar: form.dataset.endpointBuscar,
        apagar: form.dataset.endpointApagar
    };

    const modes = {
        inserir: {
            action: endpoints.inserir,
            method: "post",
            title: "Cadastrar nova planta",
            description: "Inclua nome, categoria, preco e descricao para adicionar um novo item ao catalogo.",
            hint: "Use este modo para ampliar a colecao da loja com um cadastro completo.",
            button: "Salvar planta",
            tone: "primary",
            visibleFields: ["nome", "categoria", "preco", "descricao"],
            requiredFields: ["nome", "categoria", "preco", "descricao"]
        },
        atualizar: {
            action: endpoints.atualizar,
            method: "post",
            title: "Atualizar cadastro existente",
            description: "Informe o ID e revise os dados comerciais que precisam voltar para a vitrine.",
            hint: "Neste modo, o sistema usa o ID para localizar o registro e aplicar os novos dados.",
            button: "Atualizar cadastro",
            tone: "secondary",
            visibleFields: ["id", "nome", "categoria", "preco", "descricao"],
            requiredFields: ["id", "nome", "categoria", "preco", "descricao"]
        },
        buscar: {
            action: endpoints.buscar,
            method: "get",
            title: "Buscar uma planta por ID",
            description: "Preencha apenas o identificador para localizar um item especifico da colecao.",
            hint: "A busca usa somente o campo de ID e retorna o registro encontrado no painel lateral.",
            button: "Buscar planta",
            tone: "secondary",
            visibleFields: ["id"],
            requiredFields: ["id"]
        },
        apagar: {
            action: endpoints.apagar,
            method: "post",
            title: "Remover um cadastro do catalogo",
            description: "Use o ID do item para retirar a planta da operacao atual da loja.",
            hint: "A exclusao usa apenas o identificador do registro selecionado.",
            button: "Remover cadastro",
            tone: "danger",
            visibleFields: ["id"],
            requiredFields: ["id"]
        }
    };

    function applyButtonTone(tone) {
        submit.classList.remove("btn-primary", "btn-secondary", "btn-danger");

        if (tone === "danger") {
            submit.classList.add("btn-danger");
            return;
        }

        if (tone === "secondary") {
            submit.classList.add("btn-secondary");
            return;
        }

        submit.classList.add("btn-primary");
    }

    function setFieldState(fieldName, isVisible, isRequired) {
        const wrapper = wrappers[fieldName];
        const input = inputs[fieldName];

        if (!wrapper || !input) {
            return;
        }

        wrapper.hidden = !isVisible;
        input.disabled = !isVisible;
        input.required = isRequired;
    }

    function applyMode(modeName) {
        const mode = modes[modeName] || modes.inserir;

        form.action = mode.action;
        form.method = mode.method;
        title.textContent = mode.title;
        description.textContent = mode.description;
        hint.textContent = mode.hint;
        submit.textContent = mode.button;
        applyButtonTone(mode.tone);

        Object.keys(wrappers).forEach(function (fieldName) {
            const isVisible = mode.visibleFields.includes(fieldName);
            const isRequired = mode.requiredFields.includes(fieldName);
            setFieldState(fieldName, isVisible, isRequired);
        });

        buttons.forEach(function (button) {
            const isActive = button.dataset.mode === modeName;
            button.classList.toggle("is-active", isActive && modeName !== "apagar");
            button.classList.toggle("is-danger", isActive && modeName === "apagar");
            button.setAttribute("aria-pressed", isActive ? "true" : "false");
        });
    }

    buttons.forEach(function (button) {
        button.addEventListener("click", function () {
            applyMode(button.dataset.mode);
        });
    });

    applyMode(form.dataset.initialMode || "inserir");
});
