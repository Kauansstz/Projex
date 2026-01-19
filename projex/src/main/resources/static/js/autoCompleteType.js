document.addEventListener("DOMContentLoaded", () => {

    const type_certificate = [
    "Curso Livre",
    "Curso Técnico",
    "Tecnólogo",
    "Graduação",
    "Pós-Graduação",
    "MBA",
    "Mestrado",
    "Doutorado",
    "Pós-Doutorado",
    "Extensão Universitária"
    ];

    const input = document.getElementById("TypeInput");
    const list = document.getElementById("TypeList");

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

        const filtered = type_certificate.filter(inst =>
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
