document.addEventListener('DOMContentLoaded', () => {
    // 1. Seleciona todos os elementos que têm o atributo de link
    const botoesCopiar = document.querySelectorAll('[data-link]');

    botoesCopiar.forEach(botao => {
        botao.addEventListener('click', function() {
            // 'this' refere-se ao elemento clicado
            const url = this.getAttribute('data-link');
            const tooltip = this.querySelector('span');

            if (!url) return;

            // 2. Executa a cópia
            navigator.clipboard.writeText(url).then(() => {
                if (tooltip) {
                    const textoOriginal = tooltip.innerText;

                    // 3. Manipulação de classes do DOM para feedback
                    tooltip.innerText = "Copiado! ✅";
                    tooltip.classList.replace('bg-gray-800', 'bg-green-600');
                    tooltip.classList.add('scale-100'); // Garante que o tooltip apareça

                    // 4. Reset após 2 segundos
                    setTimeout(() => {
                        tooltip.innerText = textoOriginal;
                        tooltip.classList.replace('bg-green-600', 'bg-gray-800');
                        tooltip.classList.remove('scale-100');
                    }, 2000);
                }
            }).catch(err => {
                console.error("Erro ao copiar para o DOM: ", err);
            });
        });
    });
});