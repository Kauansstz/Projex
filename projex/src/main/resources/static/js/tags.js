// tags.js — versão definitiva e resistente
(function () {
  // init só uma vez
  if (window.__tagsJsLoaded) return;
  window.__tagsJsLoaded = true;

  function init() {
    const tagInput = document.getElementById('tag-input');
    const tagContainer = document.getElementById('tag-container');
    const hiddenInput = document.getElementById('tecnologiasText');
    const form = (tagInput && tagInput.closest('form')) || document.querySelector('form');

    if (!tagInput || !tagContainer || !hiddenInput) {
      console.error('tags.js → elementos não encontrados:', { tagInput, tagContainer, hiddenInput });
      return;
    }

    // renderiza tags que já venham no hidden (edição)
    if (hiddenInput.value && hiddenInput.value.trim() !== '') {
      hiddenInput.value
        .split(';')
        .map(s => s.trim())
        .filter(Boolean)
        .forEach(addTagElement);
    }

    tagInput.addEventListener('keydown', (e) => {
      if (e.key === ';' || e.key === 'Enter') {
        e.preventDefault();
        const v = tagInput.value.replace(/;/g, '').trim();
        if (v) {
          addTagElement(v);
          tagInput.value = '';
        }
      }
    });

    // Atualiza o hidden com todas as tags (separadas por ;)
    function updateHidden() {
      const tags = Array.from(tagContainer.querySelectorAll('.tag-item')).map(el => el.dataset.val);
      hiddenInput.value = tags.join(';');
      // debug curto
      console.log('tags.js → hidden atualizado:', hiddenInput.value);
    }

    function addTagElement(text) {
      // evita duplicates visuais
      const existing = Array.from(tagContainer.querySelectorAll('.tag-item')).some(el => el.dataset.val === text);
      if (existing) return;

      const span = document.createElement('div');
      span.className = 'tag-item inline-flex items-center px-3 py-1 rounded-full mr-2 mb-3 bg-gray-200 hover:bg-gray-300 text-gray-700 rounded-lg px-1 py-1';
      span.dataset.val = text;

      const label = document.createElement('span');
      label.textContent = text;
      label.className = 'tag-label';

      const btn = document.createElement('button');
      btn.type = 'button';
      btn.className = 'tag-remove ml-2 ';
      btn.textContent = '✕';
      btn.addEventListener('click', () => {
        span.remove();
        updateHidden();
      });

      span.appendChild(label);
      span.appendChild(btn);

      // inserir antes do input (mantém input no final do container)
      tagContainer.insertBefore(span, tagInput);
      updateHidden();
    }

    // garantir que no submit hidden esteja atualizada
    if (form) {
      form.addEventListener('submit', () => updateHidden());
    }
  }

  // compatível com script carregado antes ou depois do DOMContentLoaded
  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', init);
  } else {
    // já carregou — inicializa imediatamente
    init();
  }
})();
