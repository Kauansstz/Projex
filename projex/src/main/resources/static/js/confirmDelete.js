function openDeleteModal(actionUrl) {
  const modal = document.getElementById('deleteModal');
  const form = document.getElementById('deleteForm');

  if (!modal || !form) return;

  // Define a action correta do formulário
  form.action = actionUrl;

  // Exibe o modal
  modal.classList.remove('hidden');
  modal.classList.add('flex');

  // Impede scroll do fundo
  document.body.classList.add('overflow-hidden');
}

function closeDeleteModal() {
  const modal = document.getElementById('deleteModal');

  if (!modal) return;

  // Oculta o modal
  modal.classList.add('hidden');
  modal.classList.remove('flex');

  // Restaura scroll
  document.body.classList.remove('overflow-hidden');
}
