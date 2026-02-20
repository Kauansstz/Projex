document.addEventListener("DOMContentLoaded", function () {

    window.carregarPerguntas = function (categoriaId) {

        fetch('/perguntas/categoria/' + categoriaId)
            .then(response => response.json())
            .then(perguntas => {

                const container = document.getElementById("perguntasContainer");

                if (!container) return;

                if (perguntas.length === 0) {
                    container.innerHTML = `
                        <div class="text-center text-gray-500 mt-6">
                            Nenhuma pergunta encontrada nesta categoria.
                        </div>
                    `;
                    return;
                }

                let html = `
                    <div class="grid grid-cols-1 gap-6">
                `;

                perguntas.forEach((pergunta, index) => {

                    let respostasHtml = '';

                    if (pergunta.respostas) {
                        pergunta.respostas.forEach(resposta => {
                            respostasHtml += `
                                <label class="flex items-center gap-2 text-sm text-gray-700">
                                    <input type="radio"
                                           name="pergunta_${pergunta.id}"
                                           value="${resposta.id}"
                                           class="accent-blue-600">
                                    ${resposta.descricao}
                                </label>
                            `;
                        });
                    }

                    html += `
                        <div class="bg-white shadow-md rounded-lg p-6 border border-gray-200
                                    transform transition-transform duration-300 
                                    hover:-translate-y-1 hover:shadow-lg">

                            <div class="mb-4">
                                <h3 class="font-semibold text-[#1F2937]">
                                    ${index + 1}. ${pergunta.enunciado}
                                </h3>
                            </div>

                            <div class="flex flex-col gap-2">
                                ${respostasHtml}
                            </div>

                        </div>
                    `;
                });

                html += `</div>`;

                container.innerHTML = html;
            })
            .catch(error => {
                console.error("Erro ao carregar perguntas:", error);
            });
    };

});
