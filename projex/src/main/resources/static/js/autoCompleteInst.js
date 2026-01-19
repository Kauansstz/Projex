document.addEventListener("DOMContentLoaded", () => {

    const instituicoes = [
    // Técnicas / Profissionalizantes
    "SENAI",
    "SENAC",
    "SENAR",
    "SESI",
    "IFSP",
    "IFPR",
    "IFSC",
    "IFRS",
    "INSTITUTO FEDERAL",

    // Plataformas Online
    "ALURA",
    "UDEMY",
    "COURSERA",
    "EDX",
    "DIO",
    "PLURALSIGHT",
    "LINKEDIN LEARNING",
    "ROCKETSEAT",
    "TRYBE",
    "FULL CYCLE",
    "KODELAMP",
    "SOFTBLUE",

    // Faculdades / Universidades
    "USP",
    "UNICAMP",
    "UNESP",
    "UFMG",
    "UFPR",
    "UFSC",
    "UFRJ",
    "UFRGS",
    "PUC",
    "PUC-SP",
    "PUC-PR",
    "PUC-RS",
    "MACKENZIE",
    "ANHEMBI MORUMBI",
    "ESTACIO",
    "UNINOVE",
    "UNINTER",
    "UNICESUMAR",
    "UNIP",
    "FMU",
    "FATEC",
    "FAM",
    "FECAP",

    // Internacionais
    "HARVARD",
    "MIT",
    "STANFORD",
    "OXFORD",
    "CAMBRIDGE",
    "GOOGLE",
    "MICROSOFT",
    "IBM",
    "AWS",
    "ORACLE"
];

    const input = document.getElementById("instituicaoInput");
    const list = document.getElementById("instituicaoList");

    if (!input || !list) {
        console.warn("Autocomplete: elementos não encontrados.");
        return;
    }

    input.addEventListener("input", () => {
        const value = input.value.toLowerCase();
        list.innerHTML = "";

        if (!value) {
            list.classList.add("hidden");
            return;
        }

        const filtered = instituicoes.filter(inst =>
            inst.toLowerCase().includes(value)
        );

        if (filtered.length === 0) {
            list.classList.add("hidden");
            return;
        }

        filtered.forEach(inst => {
            const li = document.createElement("li");
            li.textContent = inst;
            li.className =
                "px-4 py-2 cursor-pointer hover:bg-zinc-700 text-white";

            li.addEventListener("click", () => {
                input.value = inst;
                list.classList.add("hidden");
            });

            list.appendChild(li);
        });

        list.classList.remove("hidden");
    });

    document.addEventListener("click", (e) => {
        if (!e.target.closest(".autocomplete-container")) {
            list.classList.add("hidden");
        }
    });

});
